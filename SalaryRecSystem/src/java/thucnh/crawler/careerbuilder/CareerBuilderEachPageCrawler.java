/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thucnh.crawler.careerbuilder;

import java.io.BufferedReader;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import thucnh.crawler.BaseCrawler;
import thucnh.dao.JobDao;
import thucnh.entity.TblJob;
import thucnh.entity.TblSkill;
import static thucnh.utils.AppConstant.*;
import thucnh.utils.AppHelper;
import static thucnh.utils.AppHelper.hasingString;
import thucnh.utils.JAXBUtils;
import thucnh.xmlchecker.XmlSyntaxChecker;

/**
 *
 * @author HP
 */
public class CareerBuilderEachPageCrawler extends BaseCrawler implements Runnable {

    private String url;
    private TblSkill skill;

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
                    if (isStart && line.contains("<div class=\"MarBot20") || line.contains("benefits-template\"")) {
                        break;
                    }
                    if (isStart) {
                        document += line.trim();
                    }
                    if (line.contains("<div class=\"MyJobDetail")) {
                        isFound = true;
                    }
                    if (isFound && (line.contains("<div class=\"box2Detail") || line.contains("id=\"showScroll"))) {
                        isStart = true;
                    }
                }
                document += "</document>"; // add </div> for div is start

            }

//                Check well-formed html
            XmlSyntaxChecker checker = new XmlSyntaxChecker();
            document = checker.checkSyntax(document);
            stAXParserForJobDetails(document);
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

    public void stAXParserForJobDetails(String document) throws UnsupportedEncodingException, XMLStreamException {

        document = document.trim();
        XMLEventReader eventReader = parseStringToXMLEventReader(document);
        double jobSalary = 0;
        String salaryStr = "";
        String jobLevel = "";
        boolean isFound = false;
        while (eventReader.hasNext()) {
            XMLEvent event = (XMLEvent) eventReader.next();
            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                String tagName = startElement.getName().getLocalPart();

                if (tagName.equals("span")) {
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
                                jobLevel = AppHelper.generateLevel("", yearExp);
                            }
                        } else if (key.contains("salary")) {
                            isFound = true;
                        } else if (key.contains("industry") || key.contains("deadline")) {
                            jobSalary = AppHelper.convertRangeToNum(salaryStr);
                            break;
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
            boolean isValidate = JAXBUtils.validateJobXml(xsdFilePath, job);
            if (isValidate) {
                JobDao dao = JobDao.getInstance();
                dao.insertJob(job);
            }
        }

    }
}
