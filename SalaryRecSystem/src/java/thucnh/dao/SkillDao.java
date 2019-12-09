/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thucnh.dao;

import java.util.List;
import javax.persistence.EntityManager;
import thucnh.entity.TblSkill;
import thucnh.utils.AppHelper;
import thucnh.utils.DBUtils;

/**
 *
 * @author HP
 */
public class SkillDao extends BaseDao<TblSkill, Integer> {

    public SkillDao() {
    }

    private static SkillDao instance;
    private static final Object LOCK = new Object();

    public static SkillDao getInstance() {
        synchronized (LOCK) {
            if (instance == null) {
                instance = new SkillDao();
            }
        }
        return instance;
    }

    public synchronized TblSkill checkExistedSkill(int hashValue) {
        EntityManager manager = DBUtils.getEntityManager();
        try {
            List<TblSkill> result = manager.createNamedQuery("TblSkill.findByHash", TblSkill.class)
                    .setParameter("hash", hashValue)
                    .getResultList();
            if (result != null && !result.isEmpty()) {
                return result.get(0);
            }
        } finally {
            if (manager != null) {
                manager.close();
            }
        }
        return null;
    }

    public TblSkill insertSkill(String type, String name) {
        TblSkill skill;
        SkillDao dao = getInstance();
        int hasValue = AppHelper.hasingString(name);
        skill = checkExistedSkill(hasValue);
        // create new category if category does not exist
        if (skill == null) {
            skill = new TblSkill();
            skill.setName(name);
            skill.setType(type);
            skill.setHash(hasValue);
            TblSkill result = dao.create(skill);
            if (result != null) {
                System.out.println("[INSERT] Skill : Type: " + type + "- Name :" + name);
                return result;
            }
        } else {
            System.out.println("[SKIP] Skill : Type: " + type + "- Name :" + name);
        }
        return null;
    }

}
