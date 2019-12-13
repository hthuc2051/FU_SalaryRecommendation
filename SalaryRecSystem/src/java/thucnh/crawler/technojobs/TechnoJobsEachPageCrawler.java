/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thucnh.crawler.technojobs;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
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
import thucnh.utils.TrAXUtils;
import thucnh.utils.XMLUtils;
import thucnh.xmlchecker.XmlSyntaxChecker;

/**
 *
 * @author HP
 */
public class TechnoJobsEachPageCrawler extends BaseCrawler implements Runnable {

    private String url;
    private TblSkill skill;
    private static final Map<String, String> tagsMap = XMLUtils.getSignTagOfCrawler(XML_TAG_TECHNOJOB_EACH_PAGE, ARR_TECHNOJOB_TAG_EACH_PAGE);
    private JobValidateMapper mapper = new JobValidateMapper();

    public TechnoJobsEachPageCrawler(String url, TblSkill skill, ServletContext context) {
        super(context);
        this.url = url;
        this.skill = skill;
    }

    @Override
    public void run() {
        BufferedReader reader = null;
        try {
            if (url != null) {
                reader = getBufferedReaderForURL(url);
                String line = "";
                String document = "<document>";
                boolean isStart = false;
                boolean isFound = false;
                if (reader != null) {
                    while ((line = reader.readLine()) != null) {
                        if (isStart && line.contains(tagsMap.get("break"))) {
                            break;
                        }
                        if (isStart) {
                            document += line.trim();
                        }
                        if (isFound && line.contains(tagsMap.get("isStart"))) {
                            isStart = true;
                        }
                        if (line.contains(tagsMap.get("isFound"))) {
                            isFound = true;
                        }
                    }
                    document += "</document>";
                }

//                Check well-formed html
                XmlSyntaxChecker checker = new XmlSyntaxChecker();
                document = checker.checkSyntax(document);

//              transform to xml
                String realPath = this.getContext().getRealPath("/");
                String xslFile = realPath + XSL_TECHNO_JOB_DETAILS;
                InputStream is = new ByteArrayInputStream(document.getBytes("UTF-8"));
                ByteArrayOutputStream os = TrAXUtils.transform(is, xslFile); // XML doc mà dạng stream

                // parse xml to DOM tree
                Document domTree = parseInputStreamToDOM(new ByteArrayInputStream(os.toByteArray()));

                // use xpath to traverse DOM tree to get list product link
                XPathFactory factory = XPathFactory.newInstance();
                XPath xPath = factory.newXPath();

                String exp = tagsMap.get("jobNameXpath");
                String jobName = (String) xPath.evaluate(exp, domTree, XPathConstants.STRING);

                exp = tagsMap.get("jobSalaryXpath");
                String jobSalary = (String) xPath.evaluate(exp, domTree, XPathConstants.STRING);

//               
                String jobLevel = AppHelper.generateLevel(jobName, 0);
                double temp = AppHelper.convertRangeToNum(jobSalary);
                double salary = AppHelper.getBeautySalary(temp);
                if (salary > 0
                        && jobName.toLowerCase().contains(skill.getName().toLowerCase())
                        && !jobLevel.equals("")) {
                    TblJob job = new TblJob();
                    int hashValue = hasingString(url);
                    job.setLink(url);
                    job.setSkillId(skill);
                    job.setExpLevel(jobLevel);
                    job.setSalary(salary);
                    job.setHash(hashValue);

                    // Validate 
                    String xsdFilePath = realPath + XSD_JOB;
                    boolean isValidate = JAXBUtils.validateJobXml(xsdFilePath, mapper.marshal(job));
                    if (isValidate) {
                        synchronized (job) {
                            JobDao dao = JobDao.getInstance();
                            dao.insertJob(job);
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
    }

    private Document parseInputStreamToDOM(InputStream is) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document domRs = builder.parse(is);

        return domRs;
    }

}
