package thucnh.crawler.technojobs;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import thucnh.crawler.BaseCrawler;
import thucnh.dao.JobDao;
import thucnh.entity.TblSkill;
import thucnh.thread.BaseThread;
import static thucnh.utils.AppConstant.*;
import static thucnh.utils.AppHelper.hasingString;
import thucnh.utils.TrAXUtils;
import thucnh.utils.XMLUtils;
import thucnh.xmlchecker.XmlSyntaxChecker;

/**
 *
 * @author HP
 */
public class TechnoJobsCrawler extends BaseCrawler implements Runnable {

    private String url;
    private List<TblSkill> skills;
    private static final Map<String, String> tagsMap = XMLUtils.getSignTagOfCrawler(XML_TAG_TECHNOJOB, ARR_TECHNOJOB_TAG);

    public TechnoJobsCrawler(String url, List<TblSkill> skills, ServletContext context) {
        super(context);
        this.url = url;
        this.skills = skills;
    }

    @Override
    public void run() {
        if (url != null) {
            try {
                for (TblSkill skill : skills) {
//                    SkillDao dao = SkillDao.getInstance();
//                    TblSkill skill = dao.findByID(40);
                    int totalPage = getLastPage(url + getTechnoJobsSearchPageLink(1, skill.getName().replace(" ", "-")));
                    if (totalPage > 15) {
                        totalPage = 15;
                    }
                    if (totalPage > 0) {
                        for (int i = 0; i < totalPage; i++) {
                            List<String> result = getListJobUrl(url + getTechnoJobsSearchPageLink((i + 1), skill.getName().replace(" ", "-")), skill.getName().trim());
                            if (result != null && result.size() > 0) {
                                for (String s : result) {
                                    Thread thread = new Thread(new TechnoJobsEachPageCrawler(s, skill, context));
                                    thread.start();
                                    synchronized (BaseThread.getInstance()) {
                                        while (BaseThread.isSuspended()) {
                                            BaseThread.getInstance().wait();
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (UnsupportedEncodingException | XMLStreamException ex) {
                Logger.getLogger(TechnoJobsCrawler.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(TechnoJobsCrawler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public List<String> getListJobUrl(String url, String skillName) {
        List<String> result = null;
        BufferedReader reader = null;
        try {
            if (url != null) {
                reader = getBufferedReaderForURL(url);
                String line = "";
                String document = "<document><div>";
                boolean isStart = false;
                if (reader != null) {
                    while ((line = reader.readLine()) != null) {
                        if (isStart && line.contains(tagsMap.get("break"))) {
                            break;
                        }
                        if (isStart) {
                            document += line.trim();
                        }
                        if (line.contains(tagsMap.get("isStart"))) {
                            isStart = true;
                        }
                    }
                    document += "</div></document>";
                }

//                Check well-formed html
                XmlSyntaxChecker checker = new XmlSyntaxChecker();
                document = checker.checkSyntax(document);
                document = document.replace(":--", "a");
                // transform to xml
                String realPath = this.getContext().getRealPath("/");
                String xslFile = realPath + XSL_TECHNO_JOB_ITEMS;
                InputStream is = new ByteArrayInputStream(document.getBytes("UTF-8"));
                ByteArrayOutputStream os = TrAXUtils.transform(is, xslFile); // XML doc mà dạng stream

                // parse xml to DOM tree
                Document domTree = parseInputStreamToDOM(new ByteArrayInputStream(os.toByteArray()));

                // use xpath to traverse DOM tree to get list product link
                XPathFactory factory = XPathFactory.newInstance();
                XPath xPath = factory.newXPath();

                String exp = tagsMap.get("linkXpath");
                NodeList nodeLinkList = (NodeList) xPath.evaluate(exp, domTree, XPathConstants.NODESET);

                exp = tagsMap.get("jobNameXpath");
                NodeList nodeNameList = (NodeList) xPath.evaluate(exp, domTree, XPathConstants.NODESET);

                result = new ArrayList<>();
                String tmpLink, tmpName;
                for (int i = 0; i < nodeLinkList.getLength(); i++) {
                    Node itemLink = nodeLinkList.item(i);
                    tmpLink = itemLink.getTextContent();

                    Node itemName = nodeNameList.item(i);
                    tmpName = itemName.getTextContent().toLowerCase();
                    if (tmpName.contains(skillName.toLowerCase())) {
                        //Check existed
                        JobDao dao = JobDao.getInstance();
                        String link = HOST_TECHNO_JOBS + tmpLink;
                        int hashValue = hasingString(link);
                        if (dao.checkExistedJob(hashValue) == null) {
                            result.add(link);
                        } else {
                            System.out.println("[SKIP] Job Link : " + link);
                        }
                    }
                }
            }
        } catch (Exception e) {
            Logger.getLogger(TechnoJobsCrawler.class.getName() + " getListJobUrl").log(Level.SEVERE, e.getMessage(), e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                    Logger.getLogger(TechnoJobsCrawler.class.getName() + " - getListJobUrl_Finally_Reader").log(Level.SEVERE, e.getMessage(), e);
                }
            }
        }
        return result;
    }

    private int parsePagingDoc(String document) {
        document = document.trim();
        int lastPage = 1;
        try {
            XMLEventReader eventReader = parseStringToXMLEventReader(document);
            while (eventReader.hasNext()) {
                XMLEvent event = null;
                try {
                    event = (XMLEvent) eventReader.next();
                } catch (Exception e) {
                }
                if (event != null) {
                    if (event.isStartElement()) {
                        StartElement startElement = event.asStartElement();
                        String tagName = startElement.getName().getLocalPart();
                        if (tagsMap.get("tagLastPageValue").equals(tagName)) {
                            event = (XMLEvent) eventReader.next();
                            Characters textContent = event.asCharacters();
                            String s = textContent.getData();
                            if (s != null) {
                                String[] arr = s.split("of");
                                if (arr.length > 1) {
                                    return Integer.parseInt(arr[1].trim());
                                }
                            }
                        }
                    }
                }
            }
            eventReader.close();
        } catch (Exception e) {
        }
        return lastPage;
    }

    public int getLastPage(String url) throws UnsupportedEncodingException, XMLStreamException {
        BufferedReader reader = null;
        try {
            if (url != null) {
                reader = getBufferedReaderForURL(url);
                String line = "";
                String document = "";
                if (reader != null) {
                    while ((line = reader.readLine()) != null) {
                        if (line.contains(tagsMap.get("lastPageXML"))) {
                            document += line.trim();
                            break;
                        }
                    }
                }
//                Check well-formed html
//                XmlSyntaxChecker checker = new XmlSyntaxChecker();
//                document = checker.checkSyntax(document);
                return parsePagingDoc(document);
            }
        } catch (Exception e) {
            Logger.getLogger(TechnoJobsCrawler.class.getName() + " - getLastPage").log(Level.SEVERE, e.getMessage(), e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                    Logger.getLogger(TechnoJobsCrawler.class.getName() + " - Finally_Reader Paging").log(Level.SEVERE, e.getMessage(), e);
                }
            }
        }
        return -1;
    }

    private Document parseInputStreamToDOM(InputStream is) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document domRs = builder.parse(is);

        return domRs;
    }

}
