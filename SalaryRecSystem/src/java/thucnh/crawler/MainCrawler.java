/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thucnh.crawler;

import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.servlet.ServletContext;
import thucnh.crawler.careerbuilder.CareerBuilderCrawler;
import thucnh.crawler.technojobs.TechnoJobsCrawler;
import thucnh.crawler.topitworks.TopITWorksCrawler;
import thucnh.dao.SkillDao;
import thucnh.entity.TblSkill;
import thucnh.thread.BaseThread;
import static thucnh.utils.AppConstant.*;

/**
 *
 * @author HP
 */
public class MainCrawler {

    static long startTime = System.nanoTime();
    static CareerBuilderCrawler careerBuilderCrawler = null;
    static TechnoJobsCrawler technoJobsCrawler = null;

    public static void test(ServletContext context) {
        SkillDao dao = SkillDao.getInstance();
        dao.deleteOne(3);
    }

    public static void crawlSkill(ServletContext context) {
        TopITWorksCrawler topITWorksCrawler = new TopITWorksCrawler(context, HOST_TOP_IT_WORKS + CATEGORY_HOST_TOP_IT_WORKS);
        topITWorksCrawler.crawl();
    }

    public static void crawlJobs(ServletContext context) throws InterruptedException {

        List<TblSkill> skills = getAllSkills();
        if (skills != null && skills.size() > 0) {
            BaseThread.getInstance().resumeThread();
            careerBuilderCrawler = new CareerBuilderCrawler(HOST_CAREER_BUILDER, skills, context);
            careerBuilderCrawler.start();
            technoJobsCrawler = new TechnoJobsCrawler(HOST_TECHNO_JOBS, skills, context);
            technoJobsCrawler.start();
            
        }
    }

    public static void stopCrawl() {
        BaseThread.getInstance().suspendThread();
        if (careerBuilderCrawler != null) {
            careerBuilderCrawler.suspendThread();
        }
        if (technoJobsCrawler != null) {
            careerBuilderCrawler.suspendThread();
        }

        long endTime = System.nanoTime() - startTime;
        System.out.println("Crawl time: " + TimeUnit.SECONDS.convert(endTime, TimeUnit.NANOSECONDS) + " seconds");
    }

    private static List<TblSkill> getAllSkills() {
        SkillDao dao = SkillDao.getInstance();
        return dao.getAll("TblSkill.findAll");
    }
}
