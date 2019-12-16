/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thucnh.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import thucnh.entity.SummaryJob;
import thucnh.utils.AppHelper;
import thucnh.utils.DBUtils;

/**
 *
 * @author HP
 */
public class SummaryJobDao extends BaseDao<SummaryJob, Integer> {

    public SummaryJobDao() {
    }

    private static SummaryJobDao instance;
    private static final Object LOCK = new Object();

    public static SummaryJobDao getInstance() {
        synchronized (LOCK) {
            if (instance == null) {
                instance = new SummaryJobDao();
            }
        }
        return instance;
    }

    public void generateSummaryJobs(Map<String, Integer[]> map) {
        SummaryJob summaryItem;
        SummaryJobDao dao = getInstance();

        if (map != null && map.size() > 0) {
            for (Map.Entry<String, Integer[]> entry : map.entrySet()) {
                String salary = entry.getKey();
                Integer[] hash_quantityJob = entry.getValue();
                if (hash_quantityJob.length > 1) {
                    try {
                        summaryItem = new SummaryJob();
                        summaryItem.setExpSkillHash(hash_quantityJob[0]);
                        summaryItem.setNoOfJobs(hash_quantityJob[1]);
                        summaryItem.setSalary(Double.parseDouble(salary.split("~")[0]));
                        summaryItem.setActive(true);
                        summaryItem.setDate(AppHelper.getCurDateTime());
                        dao.create(summaryItem);
                    } catch (Exception e) {
                    }
                }
            }
        }
    }

    public List<SummaryJob> findTop10ForChart(Double salaryRec, Integer expSkillHash) {
        List<SummaryJob> result = null;
        Map<SummaryJob, Double> summaryMap = new HashMap<>();
        SummaryJobDao dao = getInstance();
        List<SummaryJob> list = dao.getListByExpSkillHash(expSkillHash);
        for (SummaryJob summaryJob : list) {
            Double distance = AppHelper.calculateDistance(salaryRec, summaryJob.getSalary());
            if (summaryMap.size() < 11) {
                summaryMap.put(summaryJob, distance);
            } else {
                Map<SummaryJob, Double> comparedMap = AppHelper.sortByValue(summaryMap);

                for (Map.Entry<SummaryJob, Double> entry : comparedMap.entrySet()) {
                    if (entry.getValue() >= distance) {
                        summaryMap.remove(entry.getKey());
                        summaryMap.put(summaryJob, distance);
                        break;
                    }
                }
            }
        }
        if (summaryMap.size() > 0) {
            result = new ArrayList<>();
            for (Map.Entry<SummaryJob, Double> entry : summaryMap.entrySet()) {
                result.add(entry.getKey());
            }
        }
        return result;
    }

    private List<SummaryJob> getListByExpSkillHash(Integer hashValue) {
        EntityManager manager = DBUtils.getEntityManager();
        try {
            List<SummaryJob> result = manager.createNamedQuery("SummaryJob.findByExpSkillHash", SummaryJob.class)
                    .setParameter("expSkillHash", hashValue)
                    .getResultList();
            if (result != null && result.size() > 0) {
                return result;
            }
        } finally {
            if (manager != null) {
                manager.close();
            }
        }
        return null;
    }

    public void deleteAll() {
        EntityManager manager = DBUtils.getEntityManager();
        try {
            EntityTransaction transaction = manager.getTransaction();
            transaction.begin();
            manager.createNativeQuery("UPDATE SummaryJob SET active = 0").executeUpdate();
            transaction.commit();
        } finally {
            if (manager != null) {
                manager.close();
            }
        }
    }

    public boolean deleteOne(Integer id) {
        SummaryJobDao dao = getInstance();
        SummaryJob entity = dao.findByID(id);
        if (entity != null) {
            entity.setActive(false);
            if (dao.update(entity) != null) {
                return true;
            }
        }
        return false;
    }
}
