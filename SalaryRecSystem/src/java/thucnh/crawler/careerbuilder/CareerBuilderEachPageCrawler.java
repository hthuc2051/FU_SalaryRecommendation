/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thucnh.crawler.careerbuilder;

import java.io.BufferedReader;
import java.io.UnsupportedEncodingException;
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
import thucnh.entity.TblJob;
import thucnh.entity.TblSkill;
import thucnh.mapper.JobValidateMapper;
import thucnh.thread.BaseThread;
import static thucnh.utils.AppConstant.*;
import thucnh.utils.AppHelper;
import static thucnh.utils.AppHelper.hasingString;
import thucnh.utils.JAXBUtils;
import thucnh.utils.XMLUtils;
import thucnh.xmlchecker.XmlSyntaxChecker;

/**
 *
 * @author HP
 */
public class CareerBuilderEachPageCrawler extends BaseCrawler implements Runnable {

    private String url;
    private TblSkill skill;
    private static final Map<String, String> tagsMap = XMLUtils.getSignTagOfCrawler(XML_TAG_CARRER_BUILDER_EACH_PAGE, ARR_CARRER_BUILDER_TAG_EACH_PAGE);
    private JobValidateMapper mapper = new JobValidateMapper();

    public CareerBuilderEachPageCrawler(String url, TblSkill skill, ServletContext context) {
        super(context);
        this.url = url;
        this.skill = skill;
    }

    @Override
    public void run() {
        BufferedReader reader = null;
        try {
//            if (url != null) {
            reader = getBufferedReaderForURL(url);
            String line = "";
            String document = "<document><div>";
            boolean isStart = false;
            boolean isFound = false;
            if (reader != null) {
                while ((line = reader.readLine()) != null) {
                    if (isStart && line.contains(tagsMap.get("break1")) || line.contains(tagsMap.get("break2"))) {
                        break;
                    }
                    if (isStart) {
                        document += line.trim();
                    }
                    if (line.contains(tagsMap.get("isStart"))) {
                        isStart = true;
                    }

                }
                document += "</document>"; // add </div> for div is start

            }

//                Check well-formed html
            XmlSyntaxChecker checker = new XmlSyntaxChecker();
            document = checker.checkSyntax(document);
            stAXParserForJobDetails(document, url);
//            }
        } catch (Exception e) {
            Logger.getLogger(CareerBuilderCrawler.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                    Logger.getLogger(CareerBuilderCrawler.class.getName() + " - Finally_Reader").log(Level.SEVERE, e.getMessage(), e);

                }
            }
        }
    }

    public void stAXParserForJobDetails(String document, String url) throws UnsupportedEncodingException, XMLStreamException {

        document = document.trim();
        XMLEventReader eventReader = parseStringToXMLEventReader(document);
        double jobSalary = 0;
        String salaryStr = "";
        String jobLevel = "";
        boolean isFound = false;
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

                    if (tagName.equals(tagsMap.get("jobDetailsTag"))) {
                        event = (XMLEvent) eventReader.next();
                        Characters content = event.asCharacters();
                        if (content != null) {
                            String key = content.getData().toLowerCase();
                            if (key.contains("experience")) {
                                eventReader.next();
                                event = (XMLEvent) eventReader.next();
                                Characters expChars = event.asCharacters();
                                if (expChars != null) {
                                    double yearExp = AppHelper.convertRangeToNum(expChars.getData());
                                    if (!jobLevel.equals("")) {
                                        jobLevel = AppHelper.generateLevel("", yearExp);
                                    }
                                }
                            } else if (key.contains("salary") || key.contains("lương")) {
                                isFound = true;
                            } else if (key.contains("industry")
                                    || key.contains("deadline")
                                    || (key.contains("ngành nghề"))
                                    || (key.contains("hết hạn nộp"))) {
                                jobSalary = AppHelper.convertRangeToNum(salaryStr);
                                break;
                            }
                        }
                    }
                    if (tagName.equals("div")) {
                        Attribute attrClass = startElement.getAttributeByName(new QName("class"));
                        if (attrClass != null && attrClass.getValue().contains(tagsMap.get("jobTitle"))) {
                            eventReader.next();
                            event = (XMLEvent) eventReader.next();
                            Characters textContent = event.asCharacters();
                            if (textContent != null) {
                                String s = textContent.getData();
                                jobLevel = AppHelper.generateLevel(s, 0);
                            }
                        }
                    }
                    if (isFound && tagName.equals("label")) {
                        event = (XMLEvent) eventReader.next();
                        Characters salaryChars = event.asCharacters();
                        if (salaryChars != null) {
                            salaryStr += salaryChars + "-";
                        }
                    }

                }
            }
        }
        if (!jobLevel.equals("") && jobSalary > 0) {
            TblJob job = new TblJob();

            int hashValue = hasingString(url);
            job.setLink(url);
            job.setSkillId(skill);
            job.setExpLevel(jobLevel);
            job.setSalary(jobSalary);
            job.setHash(hashValue);

            // Validate 
            String realPath = this.getContext().getRealPath("/");
            String xsdFilePath = realPath + XSD_JOB;
            boolean isValidate = JAXBUtils.validateJobXml(xsdFilePath, mapper.marshal(job));
            if (isValidate) {
                synchronized (job) {
                    JobDao dao = JobDao.getInstance();
                    dao.insertJob(job);
                }
            }
        } else {
            System.out.println("[SKIP] Job Link : " + url);
        }
    }
}
