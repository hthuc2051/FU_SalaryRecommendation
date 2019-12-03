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
import thucnh.xmlchecker.XmlSyntaxChecker;

/**
 *
 * @author HP
 */
public class CareerBuilderCrawler extends BaseCrawler implements Runnable {

    private String url;
    private List<TblSkill> skills;

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
                    int totalPage = getLastPage(url + getCareerBuilderPageLink(1, skill.getName().replace(" ", "-")));
                    if (totalPage > 6) {
                        totalPage = 6;
                    }
                    for (int i = 0; i < totalPage; i++) {
                        List<String> result = stAXParserForJobUrl(getCareerBuilderPageLink(i + 1, skill.getName().replace(" ", "-")), skill.getName().trim());
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
                String line = "";
                String document = "";
                boolean isStart = false;
                if (reader != null) {
                    while ((line = reader.readLine()) != null) {
                        if (line.contains("<span class=\"col-sm-2 styled-select job-sorter-col")) {
                            break;
                        }
                        if (line.contains("<div class=\"ais-stats")) {
                            isStart = true;
                        }
                        if (isStart) {
                            document += line;
                        }
                    }
                }
//                Check well-formed html
                XmlSyntaxChecker checker = new XmlSyntaxChecker();
                document = checker.checkSyntax(document);
                return parsePagingDoc(document);
            }
        } catch (IOException | XMLStreamException e) {
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

    private int parsePagingDoc(String document) throws UnsupportedEncodingException, XMLStreamException {
        document = document.trim();
        XMLEventReader eventReader = parseStringToXMLEventReader(document);
        int lastPage = 1;
        while (eventReader.hasNext()) {
            XMLEvent event = (XMLEvent) eventReader.next();
            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                String tagName = startElement.getName().getLocalPart();
                if ("span".equals(tagName)) {
                    event = (XMLEvent) eventReader.next();
                    Characters textContent = event.asCharacters();
                    if (textContent != null) {
                        String totalItem = textContent.getData();
                        totalItem = totalItem.replaceAll("\\D+", "");
                        double totalItemFloat = Double.parseDouble(totalItem.trim());
                        return (int) Math.ceil(totalItemFloat / CAREER_BUILDER_EACH_PAGE_ITEM);
                    }

                }
            }
        }
        eventReader.close();
        return lastPage;
    }

    public List<String> stAXParserForJobUrl(String url, String skillName) throws UnsupportedEncodingException, XMLStreamException {
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
                        if (isStart && line.contains("<div class=\"paginationTwoStatus")) {
                            break;
                        }
                        if (isStart) {
                            document += line.trim();
                        }
                        if (line.contains("<div class=\"col-sm-12 col-md-9 col-ListJobCate")) {
                            isStart = true;
                        }
                    }
                    document += "</dl></div></document>"; // add </div> for div is start

                }
//                  Check well-formed html
                XmlSyntaxChecker checker = new XmlSyntaxChecker();
                document = checker.checkSyntax(document);
                document = document.trim();
                XMLEventReader eventReader = parseStringToXMLEventReader(document);
                String link = "";
                while (eventReader.hasNext()) {
                    XMLEvent event = (XMLEvent) eventReader.next();
                    if (event.isStartElement()) {
                        StartElement startElement = event.asStartElement();
                        String tagName = startElement.getName().getLocalPart();
                        // Check deeper
//                if (tagName.equals("span")) {
//                    Attribute attrClass = startElement.getAttributeByName(new QName("class"));
//                    if (attrClass != null && attrClass.getValue().contains("jobtitle")) {
//                        event = (XMLEvent) eventReader.next();
//                        startElement = event.asStartElement();
//                        tagName = startElement.getName().getLocalPart();
//                        
//                    }
//                }
                        if (tagName.equals("h")) {
                            Attribute attrClass = startElement.getAttributeByName(new QName("class"));
                            if (attrClass != null && attrClass.getValue().contains("job")) {
                                event = (XMLEvent) eventReader.next();
                                startElement = event.asStartElement();
                                tagName = startElement.getName().getLocalPart();
                                if ("a".equals(tagName)) {
                                    Attribute href = startElement.getAttributeByName(new QName("href"));
                                    link = HOST_CAREER_BUILDER + href.getValue();
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
