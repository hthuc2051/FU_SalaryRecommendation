/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thucnh.crawler;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import thucnh.dao.ClusterDao;
import thucnh.dao.JobDao;
import thucnh.dao.SalaryRecDao;
import thucnh.dao.SkillDao;
import thucnh.dao.SummaryJobDao;
import thucnh.entity.TblJob;
import thucnh.entity.TblSkill;
import thucnh.kmean.KMean;
import thucnh.utils.AppHelper;

/**
 *
 * @author HP
 */
public class MainProcessor {
    // 
    public static void resetData() {
        // Reset all summary jobs to false 
        SummaryJobDao summaryJobDao = SummaryJobDao.getInstance();
        summaryJobDao.deleteAll();

        // Reset Kmean 
        // Remove clusters
        JobDao jobDao = JobDao.getInstance();
        jobDao.resetClusters();

        ClusterDao clusterDao = ClusterDao.getInstance();
        clusterDao.deleteClusters();
    }

    // Implement Kmean, Meadian, Chart summary
    public static void processCreateData() {
        resetData();
        JobDao jobDao = JobDao.getInstance();
        SalaryRecDao salaryRecDao = SalaryRecDao.getInstance();

        List<TblSkill> skills = getAllSkills();
        List<String> expLevels = jobDao.getDistinctExpLevel();
        Map<String, Integer[]> summaryJob = new HashMap();
        System.out.println("[HASH-DATA]");
        if (expLevels != null && expLevels.size() > 0) {
            for (String expLevel : expLevels) {
                if (skills != null && skills.size() > 0) {
                    for (TblSkill skill : skills) {
                        List<TblJob> jobs = jobDao.findBySkillAndExpYear(skill, expLevel);
                        if (jobs != null && jobs.size() > 0) {
                            Double[] salaryArr = jobDao.getArrSalaryBySkillAndExpLevel(jobs);
                            Arrays.sort(salaryArr);
                            KMean kmean = new KMean(skill.getId(), expLevel, jobs, salaryArr[0], salaryArr[salaryArr.length - 1]);

//                             Implements Kmean
                            kmean.init();
//                            / Prepare data for summary chart
                            if (salaryArr != null) {
                                for (int i = 0; i < salaryArr.length; i++) {
                                    int hasValue = AppHelper.hasingString(expLevel + skill.getId());

                                    Integer[] arr = new Integer[2];
                                    // setHash value
                                    arr[0] = hasValue;
                                    Double salary = salaryArr[i];
                                    System.out.println(salary + " - " + expLevel + skill.getId() + ": " + hasValue);
                                    String key = salary + "~" + hasValue;
                                    if (summaryJob.containsKey(key)) {
                                        summaryJob.get(key)[1] = summaryJob.get(key)[1] + 1;
                                    } else {
                                        arr[1] = 1;
                                        summaryJob.put(salary + "~" + hasValue, arr);
                                    }
                                }
                                // Insert salary rec 
                                salaryRecDao.insertSalaryRec(salaryArr, skill, expLevel);
                            }
                        }
                    }
                }
            }
        }

        // Generate summaries
        if (summaryJob.size() > 0) {
            SummaryJobDao summaryJobDao = SummaryJobDao.getInstance();
            summaryJobDao.generateSummaryJobs(summaryJob);
        }
    }

    private static List<TblSkill> getAllSkills() {
        SkillDao dao = SkillDao.getInstance();
        return dao.getAll("TblSkill.findAll");
    }
}
