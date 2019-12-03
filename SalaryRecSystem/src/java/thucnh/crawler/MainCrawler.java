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
import thucnh.dao.JobDao;
import thucnh.dao.SalaryRecDao;
import thucnh.dao.SkillDao;
import thucnh.entity.TblSkill;
import thucnh.thread.BaseThread;
import static thucnh.utils.AppConstant.*;

/**
 *
 * @author HP
 */
public class MainCrawler {

    public static void crawl(ServletContext context) {
        
        SalaryRecDao dao = SalaryRecDao.getInstance();
//        SkillDao skillDao = SkillDao.getInstance();
        
        dao.insertSalaryRec();
//        long startTime = System.nanoTime();
//        TopITWorksCrawler topITWorksCrawler = new TopITWorksCrawler(context, HOST_TOP_IT_WORKS + CATEGORY_HOST_TOP_IT_WORKS);
//        List<TblSkill> skills = topITWorksCrawler.crawl();

//        List<TblSkill> skills = getAllSkills();
//
//        long startTime = System.nanoTime();
//
//        if (skills != null && skills.size() > 0) {
//            BaseThread.getInstance().resumeThread();
//            TechnoJobsCrawler technoJobsCrawler = new TechnoJobsCrawler(HOST_TECHNO_JOBS, skills, context);
//            technoJobsCrawler.start();
//            CareerBuilderCrawler careerBuilderCrawler = new CareerBuilderCrawler(HOST_CAREER_BUILDER, skills, context);
//            careerBuilderCrawler.start();
//            if (!technoJobsCrawler.isAlive() && !careerBuilderCrawler.isAlive()) {
//                long endTime = System.nanoTime() - startTime;
//                System.out.println("Crawl time: " + TimeUnit.SECONDS.convert(endTime, TimeUnit.NANOSECONDS) + " seconds");
//            }
//        }

//        long endTime = System.nanoTime() - startTime;
//        System.out.println("Crawl time: " + TimeUnit.SECONDS.convert(endTime, TimeUnit.NANOSECONDS) + " seconds");
    }

    public static void saveToDB() {

    }

    private static List<TblSkill> getAllSkills() {
        SkillDao dao = SkillDao.getInstance();
        return dao.getAll("TblSkill.findAll");
    }
}
