/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thucnh.utils;

/**
 *
 * @author HP
 */
public class AppConstant {

//    Servlet
    public static final String SERVLET_CRAWLER = "CrawlerServlet";

//    Jsp
    public static final String HOME = "homePage/home.jsp";
    public static final String USER = "userPage/user.jsp";
    public static final String ADMIN = "adminPage/admin.jsp";

//    Time
    public static final int TIME_OUT = 120000;
    public static final double EXCHANGE_RATE = 108.401084;

//    Host
    public static final String HOST_CAREER_BUILDER = "https://careerbuilder.vn";
    public static final String CAREER_BUILDER_IT_JOBS = "/jobs/it-software-c1-en.html";

    public static final String HOST_TOP_IT_WORKS = "https://www.topitworks.com";
    public static final String CATEGORY_HOST_TOP_IT_WORKS = "/vi/skills/browse";

    public static final String HOST_TECHNO_JOBS = "https://www.technojobs.co.uk";

    // RECORDS PER PAGE
    public static final int CAREER_BUILDER_EACH_PAGE_ITEM = 50;

    // XSL
    public static final String XSL_TECHNO_JOB_ITEMS = "WEB-INF/documents/technoJobItems.xsl";
    public static final String XSL_TECHNO_JOB_DETAILS = "WEB-INF/documents/jobDetails.xsl";

    //    XSD
    public static final String XSD_JOB = "WEB-INF/documents/job.xsd";
    public static final String XSD_SKILL = "WEB-INF/documents/skill.xsd";

    // PDF
    public static final String JAXB_XML_FOR_PDF = "WEB-INF/documents/jobs.xml";
    public static final String JAXB_XSL_FOR_PDF = "WEB-INF/documents/jobs.xsl";
    public static final String JAXB_FO_FOR_PDF = "WEB-INF/documents/job.fo";
    public static final String FOP_FAC_PATH = "E:/CN8/XML/FU_SalaryRecommendation/PDF/fop.xconf";

    public static String getTechnoJobsSearchPageLink(int page, String skill) {
        return "/search.phtml?page=" + page + "&row_offset=10&keywords=" + skill + "&salary=0&jobtype=all&postedwithin=all&radius=25";
    }

    public static String getCareerBuilderPageLink(int page, String skill) {
        return "/jobs/" + skill + "-kc1-page-" + page + "-en.html";
    }

    // FOR Crawler
    public static final String XML_TAG_TECHNOJOB = "E:/CN8/XML/FU_SalaryRecommendation/SalaryRecSystem/web/WEB-INF/documents/"
            + "technoJobCrawlerTag.xml";
    public static final String[] ARR_TECHNOJOB_TAG = {"break", "isStart", "linkXpath", "jobNameXpath", "lastPageXML", "tagLastPageValue"};

    public static final String XML_TAG_TECHNOJOB_EACH_PAGE = "E:/CN8/XML/FU_SalaryRecommendation/SalaryRecSystem/web/WEB-INF/documents/"
            + "technoJobEachPageTag.xml";
    public static final String[] ARR_TECHNOJOB_TAG_EACH_PAGE = {"break", "isStart", "isFound", "jobNameXpath", "jobSalaryXpath"};

    public static final String XML_TAG_CARRER_BUILDER = "E:/CN8/XML/FU_SalaryRecommendation/SalaryRecSystem/web/WEB-INF/documents/"
            + "careerBuilderCrawlerTag.xml";
    public static final String[] ARR_CARRER_BUILDER = {
        "breakLastPage",
        "isStartLastPage",
        "pagingValue",
        "breakUrl",
        "isStartListUrl",
        "urlItemClassCheck",
        "classOfItemCheck",
        "ItemHref",};

    public static final String XML_TAG_CARRER_BUILDER_EACH_PAGE = "E:/CN8/XML/FU_SalaryRecommendation/SalaryRecSystem/web/WEB-INF/documents/"
            + "careerBuilderCrawlerEachPageTag.xml";
    public static final String[] ARR_CARRER_BUILDER_TAG_EACH_PAGE = {
        "break1", "break2", "isStart","jobTitle" ,"jobDetailsTag"};
}
