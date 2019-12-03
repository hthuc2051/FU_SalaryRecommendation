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
    public static String SERVLET_CRAWLER = "CrawlerServlet";

//    Jsp
    public static String HOME = "index.jsp";

//    Time
    public static final int TIME_OUT = 120000;
    public static final double EXCHANGE_RATE = 108.401084;

//    Host
    public static String HOST_CAREER_BUILDER = "https://careerbuilder.vn";
    public static String CAREER_BUILDER_IT_JOBS = "/jobs/it-software-c1-en.html";

    public static String HOST_TOP_IT_WORKS = "https://www.topitworks.com";
    public static String CATEGORY_HOST_TOP_IT_WORKS = "/vi/skills/browse";

    public static String HOST_TECHNO_JOBS = "https://www.technojobs.co.uk";

    public static String getTechnoJobsSearchPageLink(int page, String skill) {
        return "/search.phtml?page=" + page + "&row_offset=10&keywords=" + skill + "&salary=0&jobtype=all&postedwithin=all&radius=25";
    }

    public static String getCareerBuilderPageLink(int page, String skill) {
        return "/jobs/" + skill + "-kc1-page-" + page + "-en.html";
    }
    // RECORDS PER PAGE
    public static final int CAREER_BUILDER_EACH_PAGE_ITEM = 50;

    // XSL
    public static String XSL_TECHNO_JOB_ITEMS = "WEB-INF/documents/technoJobItems.xsl";
    public static String XSL_TECHNO_JOB_DETAILS = "WEB-INF/documents/jobDetails.xsl";

}
