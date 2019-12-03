/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thucnh.thread;

import java.util.Map;
import javax.servlet.ServletContext;
import thucnh.crawler.topitworks.TopITWorksSkillsCrawler;
import static thucnh.utils.AppConstant.*;

/**
 *
 * @author HP
 */
public class TechnoJobsThread {

    ServletContext context;

    public TechnoJobsThread() {
    }

    public TechnoJobsThread(ServletContext context) {
        this.context = context;
    }

    public void crawl() {
        try {
            TopITWorksSkillsCrawler crawler = new TopITWorksSkillsCrawler(context, HOST_TOP_IT_WORKS + CATEGORY_HOST_TOP_IT_WORKS);
            Map<String, String> skills = crawler.getSkills();
            System.out.println("TopITWorks Skills list");
            if (skills != null && skills.size() > 0) {
                for (Map.Entry<String, String> entry : skills.entrySet()) {
                    System.out.println(entry.getKey() + " " + entry.getValue());
                }
            }

        } catch (Exception ex) {
        }
    }
}
