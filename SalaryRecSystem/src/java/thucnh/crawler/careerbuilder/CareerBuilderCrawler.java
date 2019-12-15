/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thucnh.crawler.careerbuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import thucnh.crawler.BaseCrawler;
import thucnh.dao.JobDao;
import thucnh.entity.TblSkill;
import thucnh.thread.BaseThread;
import static thucnh.utils.AppConstant.*;
import static thucnh.utils.AppHelper.hasingString;
import thucnh.utils.XMLUtils;
import thucnh.xmlchecker.XmlSyntaxChecker;

/**
 *
 * @author HP
 */
public class CareerBuilderCrawler extends BaseCrawler implements Runnable {

    private String url;
    private List<TblSkill> skills;
    private static final Map<String, String> tagsMap = XMLUtils.getSignTagOfCrawler(XML_TAG_CARRER_BUILDER, ARR_CARRER_BUILDER);

    public CareerBuilderCrawler(String url, List<TblSkill> skills, ServletContext context) {
        super(context);
        this.url = url;
        this.skills = skills;
    }

    @Override
    public void run() {
        BufferedReader reader = null;
        try {
            if (url != null) {
                for (TblSkill skill : skills) {
                    System.out.println("SKILL CRAWL"+skill.getName());
                    String urlPlus = getCareerBuilderPageLink(1, skill.getName().replace(" ", "-"));
                    int totalPage = getLastPage(url + urlPlus);
                    if (totalPage > 10) {
                        totalPage = 10;
                    }
                    if (totalPage > 0) {
                        for (int i = 0; i < totalPage; i++) {
                            List<String> result = stAXParserForJobUrl(url + getCareerBuilderPageLink(i + 1, skill.getName().replace(" ", "-")), skill.getName().trim());
                            for (String link : result) {
                                Thread thread = new Thread(new CareerBuilderEachPageCrawler(link, skill, context));
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
        } catch (UnsupportedEncodingException | XMLStreamException e) {
            Logger.getLogger(CareerBuilderCrawler.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        } catch (InterruptedException ex) {
            Logger.getLogger(CareerBuilderCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Logger.getLogger(CareerBuilderCrawler.class.getName() + " - Finally_Reader").log(Level.SEVERE, e.getMessage(), e);

                }
            }
        }
    }

    public int getLastPage(String url) throws UnsupportedEncodingException, XMLStreamException {
        BufferedReader reader = null;
        try {
            if (url != null) {
                reader = getBufferedReaderForURL(url);
                if (reader != null) {
                    String line = "";
                    String document = "";
                    boolean isStart = false;
                    while ((line = reader.readLine()) != null) {
                        if (line.contains(tagsMap.get("breakLastPage"))) {
                            break;
                        }
                        if (line.contains(tagsMap.get("isStartLastPage"))) {
                            isStart = true;
                        }
                        if (isStart) {
                            document += line;
                        }
                    }
//                Check well-formed html
                    XmlSyntaxChecker checker = new XmlSyntaxChecker();
                    document = checker.checkSyntax(document);
                    return parsePagingDoc(document);
                }
            }
        } catch (IOException e) {
            Logger.getLogger(CareerBuilderCrawler.class.getName() + " - getLastPage").log(Level.SEVERE, e.getMessage(), e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Logger.getLogger(CareerBuilderCrawler.class.getName() + " - Finally_Reader Paging").log(Level.SEVERE, e.getMessage(), e);
                }
            }
        }
        return -1;
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
                        if (tagsMap.get("pagingValue").equals(tagName)) {
                            event = (XMLEvent) eventReader.next();
                            Characters textContent = event.asCharacters();
                            if (textContent != null) {
                                String totalItem = textContent.getData();
                                totalItem = totalItem.replaceAll("\\D+", "");
                                try {
                                    double totalItemFloat = Double.parseDouble(totalItem.trim());
                                    return (int) Math.ceil(totalItemFloat / CAREER_BUILDER_EACH_PAGE_ITEM);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
            eventReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lastPage;
    }

    public List<String> stAXParserForJobUrl(String url, String skillName) {
        BufferedReader reader = null;
        List<String> result = new ArrayList<>();

        try {
            if (url != null) {
                reader = getBufferedReaderForURL(url);
                String line = "";
                String document = "<document>";
                boolean isStart = false;
                if (reader != null) {
                    while ((line = reader.readLine()) != null) {
                        if (isStart && line.contains(tagsMap.get("breakUrl"))) {
                            break;
                        }
                        if (isStart) {
                            document += line.trim();
                        }
                        if (line.contains(tagsMap.get("isStartListUrl"))) {
                            isStart = true;
                        }
                    }
                    document += "</dl></div></document>"; // add </div> for div is start

                }
//                  Check well-formed html
                XmlSyntaxChecker checker = new XmlSyntaxChecker();
                document = checker.checkSyntax(document);
                document = document.trim().replace("alt", "data");
                XMLEventReader eventReader = parseStringToXMLEventReader(document);
                String link = "";
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
                            if (tagName.equals(tagsMap.get("urlItemClassCheck"))) {
                                Attribute attrClass = startElement.getAttributeByName(new QName("class"));
                                if (attrClass != null && attrClass.getValue().contains(tagsMap.get("classOfItemCheck"))) {
                                    event = (XMLEvent) eventReader.next();
                                    startElement = event.asStartElement();
                                    tagName = startElement.getName().getLocalPart();
                                    if (tagsMap.get("ItemHref").equals(tagName)) {
                                        Attribute href = startElement.getAttributeByName(new QName("href"));
                                        link = href.getValue();
                                        event = (XMLEvent) eventReader.next();
                                        Characters textContent = event.asCharacters();
                                        if (textContent != null) {
                                            String s = textContent.getData();
                                            if (s.toLowerCase().contains(skillName.toLowerCase())) {
                                                if (link != null) {
                                                    JobDao dao = JobDao.getInstance();
                                                    int hashValue = hasingString(link);
                                                    if (dao.checkExistedJob(hashValue) == null) {
                                                        result.add(link);
                                                    } else {
                                                        System.out.println("[SKIP] Job Link : " + link);
                                                    }
                                                }
                                            }else{
                                                System.out.println("[SKIP] Job Link : " + link);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException | XMLStreamException e) {
            Logger.getLogger(CareerBuilderCrawler.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Logger.getLogger(CareerBuilderCrawler.class.getName() + " - Finally_Reader").log(Level.SEVERE, e.getMessage(), e);
                }
            }
        }

        return result;
    }

}
