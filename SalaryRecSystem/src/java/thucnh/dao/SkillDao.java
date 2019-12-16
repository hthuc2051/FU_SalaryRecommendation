/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thucnh.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import thucnh.entity.TblSkill;
import thucnh.mapper.SkillValidateMapper;
import thucnh.utils.AppHelper;
import thucnh.utils.DBUtils;
import thucnh.utils.JAXBUtils;

/**
 *
 * @author HP
 */
public class SkillDao extends BaseDao<TblSkill, Integer> {

    SkillValidateMapper mapper = new SkillValidateMapper();

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

    public synchronized List<TblSkill> getInRange(int from, int to) {
        EntityManager manager = DBUtils.getEntityManager();
        try {
            List<TblSkill> result = manager.createNamedQuery("TblSkill.findByRange", TblSkill.class)
                    .setFirstResult(from)
                    .setMaxResults(to)
                    .getResultList();
            if (result != null && !result.isEmpty()) {
                return result;
            }
        } finally {
            if (manager != null) {
                manager.close();
            }
        }
        return null;
    }

    public synchronized TblSkill insertSkill(String type, String name, String xsdFilePath) {
        synchronized (LOCK) {
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
                try {
                    boolean isValidate = JAXBUtils.validateSkillXml(xsdFilePath, mapper.marshal(skill));
                    if (isValidate) {
                        TblSkill result = dao.create(skill);
                        if (result != null) {
                            System.out.println("[INSERT] Skill : Type: " + type + "- Name :" + name);
                            return result;
                        }
                    }
                } catch (Exception e) {
                }

            } else {
                System.out.println("[SKIP] Skill : Type: " + type + "- Name :" + name);
            }
            return null;
        }
    }

    public boolean deleteOne(Integer id) {
        EntityManager manager = DBUtils.getEntityManager();
        try {
            EntityTransaction transaction = manager.getTransaction();
            transaction.begin();
            int check = manager.createQuery("UPDATE TblSkill  SET active = false Where id = :id")
                    .setParameter("id", id)
                    .executeUpdate();
            transaction.commit();
            return check > 0;
        } finally {
            if (manager != null) {
                manager.close();
            }
        }
    }

}
