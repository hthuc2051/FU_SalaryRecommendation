/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thucnh.dao;

import java.util.List;
import javax.persistence.EntityManager;
import thucnh.entity.TblJob;
import thucnh.entity.TblSkill;
import thucnh.utils.DBUtils;

/**
 *
 * @author HP
 */
public class JobDao extends BaseDao<TblJob, Integer> {

    public JobDao() {
    }

    private static JobDao instance;
    private static final Object LOCK = new Object();

    public static JobDao getInstance() {
        synchronized (LOCK) {
            if (instance == null) {
                instance = new JobDao();
            }
        }
        return instance;
    }

    public synchronized TblJob checkExistedJob(int hashValue) {
        EntityManager manager = DBUtils.getEntityManager();
        try {
            List<TblJob> result = manager.createNamedQuery("TblJob.findByHash", TblJob.class)
                    .setParameter("hash", hashValue)
                    .getResultList();
            if (result != null && result.size() > 0) {
                return result.get(0);
            }
        } finally {
            if (manager != null) {
                manager.close();
            }
        }
        return null;
    }

    public void insertJob(TblJob job) {
        JobDao dao = getInstance();
        // create new category if category does not exist
        if (dao.create(job) != null) {
            System.out.println("[INSERT] Job Link : " + job.getLink());
        } else {
            System.out.println("[SKIP] Job Link : " + job.getLink());
        }
    }

//    public Double[] getArrSalaryBySkillAndExpLevel(TblSkill skill, String expLevel) {
//        EntityManager manager = DBUtils.getEntityManager();
//        Double[] arr = null;
//        try {
//            List<Double> result = manager.createNamedQuery("TblJob.findSalaryBySkillAndExpLevel", Double.class)
//                    .setParameter("skill", skill)
//                    .setParameter("expLevel", expLevel)
//                    .getResultList();
//
//            if (result != null && result.size() > 0) {
//                arr = new Double[result.size()];
//                for (int i = 0; i < arr.length; i++) {
//                    arr[i] = result.get(i);
//                }
//                return arr;
//            }
//        } finally {
//            if (manager != null) {
//                manager.close();
//            }
//        }
//        return null;
//    }

    public Double[] getArrSalaryBySkillAndExpLevel(List<TblJob> jobs) {
        Double[] arr = null;
        if (jobs != null && jobs.size() > 0) {
            arr = new Double[jobs.size()];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = jobs.get(i).getSalary();
            }
            return arr;
        }
        return null;
    }

    public List<TblJob> findBySkillAndExpYear(TblSkill skill, String expLevel) {
        EntityManager manager = DBUtils.getEntityManager();
        List<TblJob> result = null;
        try {
            result = manager.createNamedQuery("TblJob.findBySkillAndExpYear", TblJob.class)
                    .setParameter("skill", skill)
                    .setParameter("expLevel", expLevel)
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

    public List<String> getDistinctExpLevel() {
        EntityManager manager = DBUtils.getEntityManager();
        try {
            List<String> result = manager.createNamedQuery("TblJob.findDistinctJobs", String.class)
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
//        public List<TblJob> getTopTenRelatedJob(double salary){
//            List<TblJob> data = getAll("TblJob.findAll");
//        }
}
