/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thucnh.crawler.topitworks;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import thucnh.crawler.BaseCrawler;
import thucnh.dao.SkillDao;
import thucnh.entity.TblSkill;
import thucnh.utils.AppHelper;

/**
 *
 * @author HP
 */
public class TopITWorksCrawler extends BaseCrawler {

    private String url;

    public TopITWorksCrawler(ServletContext context, String url) {
        super(context);
        this.url = url;
    }

    public List<TblSkill> crawl() {
        List<TblSkill> result = null;
        try {
            TopITWorksSkillsCrawler crawler = new TopITWorksSkillsCrawler(context, url);
            Map<String, String> skills = crawler.getSkills();
            if (skills != null && skills.size() > 0) {
                result = new ArrayList<>();
                for (Map.Entry<String, String> entry : skills.entrySet()) {
                    String[] arr = entry.getKey().split("=");
                    if (arr != null && arr.length > 1) {
                        String skillName = arr[0];
                        String skillType = arr[1];
                        SkillDao dao = SkillDao.getInstance();
                        TblSkill skill = dao.insertSkill(skillType, skillName);
                        if (skill != null) {
                            result.add(skill);
                        }
                    }
                }
            }
        } catch (Exception e) {
            Logger.getLogger(TopITWorksCrawler.class.getName()).log(Level.SEVERE, e.getMessage(), e);
            e.printStackTrace();
        }
        return result;
    }

    

}
