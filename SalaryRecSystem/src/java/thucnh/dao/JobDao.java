/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thucnh.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import thucnh.dto.JobDto;
import thucnh.dto.JobPdf;
import thucnh.entity.TblCluster;
import thucnh.entity.TblJob;
import thucnh.entity.TblSkill;
import thucnh.mapper.JobMapper;
import thucnh.mapper.PdfMapper;
import thucnh.utils.AppHelper;
import thucnh.utils.DBUtils;

/**
 *
 * @author HP
 */
public class JobDao extends BaseDao<TblJob, Integer> {

    JobMapper mapper = new JobMapper();
    PdfMapper pdfMapper = new PdfMapper();

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

    public synchronized void insertJob(TblJob job) {
        JobDao dao = getInstance();
        // create new category if category does not exist
        if (dao.create(job) != null) {
            System.out.println("[INSERT] Job Link : " + job.getLink());
        } else {
            System.out.println("[SKIP] Job Link : " + job.getLink());
        }
    }

    public boolean deleteOne(Integer id) {
        EntityManager manager = DBUtils.getEntityManager();
        try {
            EntityTransaction transaction = manager.getTransaction();
            transaction.begin();
            int check = manager.createQuery("UPDATE TblJob  SET active = false Where id = :id")
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

    public void resetClusters() {
        EntityManager manager = DBUtils.getEntityManager();
        try {
            EntityTransaction transaction = manager.getTransaction();
            transaction.begin();
            manager.createNativeQuery("UPDATE TblJob SET clusterId = null").executeUpdate();
            transaction.commit();
        } finally {
            if (manager != null) {
                manager.close();
            }
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

    public List<TblJob> findBySalaryRange(Double from, Double to) {
        EntityManager manager = DBUtils.getEntityManager();
        List<TblJob> result = null;
        try {
            result = manager.createNamedQuery("TblJob.findBySalaryRange", TblJob.class)
                    .setParameter("from", from)
                    .setParameter("to", to)
                    .getResultList();

            if (result != null && result.size() > 20) {
                return result.subList(0, 20);
            }
        } finally {
            if (manager != null) {
                manager.close();
            }
        }
        return result;
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

    public List<JobPdf> getTopTenRelatedJob(Double salaryRec, Integer expSkillHash) {
        List<JobPdf> result = null;
        ClusterDao clusterDao = ClusterDao.getInstance();
        Double minDistanceCentroid = 0.0;
        Map<TblJob, Double> top10JobsForPdfMap = new HashMap<>();
        List<TblCluster> clusters = clusterDao.findClustersByHash(expSkillHash);
        int index = 0;
        if (clusters != null && clusters.size() > 0) {
            result = new ArrayList<>();
            minDistanceCentroid = AppHelper.calculateDistance(clusters.get(0).getCentroid(), salaryRec);;
            for (int i = 1; i < clusters.size(); i++) {
                Double distance = AppHelper.calculateDistance(clusters.get(i).getCentroid(), salaryRec);
                if (distance <= minDistanceCentroid) {
                    minDistanceCentroid = distance;
                    index = i;
                }
            }
            Collection<TblJob> jobsByCentroid = clusters.get(index).getTblJobCollection();
            for (TblJob job : jobsByCentroid) {
                Double distance = AppHelper.calculateDistance(job.getSalary(), salaryRec);
                if (top10JobsForPdfMap.size() < 10) {
                    top10JobsForPdfMap.put(job, job.getSalary());
                } else {
                    Map<TblJob, Double> comparedMap = AppHelper.sortByValue(top10JobsForPdfMap);
                    for (Map.Entry<TblJob, Double> entry : comparedMap.entrySet()) {
                        if (entry.getValue() >= distance) {
                            top10JobsForPdfMap.remove(entry.getKey());
                            top10JobsForPdfMap.put(job, distance);
                            break;
                        }
                    }
                }
            }
            if (top10JobsForPdfMap.size() > 0) {
                result = new ArrayList<>();
                for (Map.Entry<TblJob, Double> entry : top10JobsForPdfMap.entrySet()) {
                    JobPdf dto = pdfMapper.marshal(entry.getKey());
                    dto.setExpLevel(AppHelper.getFullLevelStr(dto.getExpLevel()));
                    result.add(dto);
                }
            }
        }
        return result;
    }

}
