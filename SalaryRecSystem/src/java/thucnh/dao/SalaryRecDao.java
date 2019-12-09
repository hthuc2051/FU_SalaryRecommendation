/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thucnh.dao;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import thucnh.entity.TblJob;
import thucnh.entity.TblSalaryRec;
import thucnh.entity.TblSkill;
import thucnh.kmean.KMean;
import thucnh.utils.AppHelper;
import thucnh.utils.DBUtils;

/**
 *
 * @author HP
 */
public class SalaryRecDao extends BaseDao<TblSalaryRec, Integer> {

    public SalaryRecDao() {
    }

    private static SalaryRecDao instance;
    private static final Object LOCK = new Object();

    public static SalaryRecDao getInstance() {
        synchronized (LOCK) {
            if (instance == null) {
                instance = new SalaryRecDao();
            }
        }
        return instance;
    }

    public synchronized TblSalaryRec checkExistedSalaryRec(TblSkill skill, String expLevel) {
        EntityManager manager = DBUtils.getEntityManager();
        try {
            List<TblSalaryRec> result = manager.createNamedQuery("TblSalaryRec.checkExistedRecSalary", TblSalaryRec.class)
                    .setParameter("skill", skill)
                    .setParameter("expLevel", expLevel)
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


    public void insertSalaryRec(Double[] salaryArr, TblSkill skill, String expLevel) {
        Double salary = generateMedianSalary(salaryArr);
        TblSalaryRec salaryRec = null;
        SalaryRecDao salaryRecDao = getInstance();
        salaryRec = checkExistedSalaryRec(skill, expLevel);
        if (salaryRec == null) {
            salaryRec = new TblSalaryRec();
            salaryRec.setSkillId(skill);
            salaryRec.setSalaryRec(salary);
            salaryRec.setExpLevel(expLevel);
            TblSalaryRec result = salaryRecDao.create(salaryRec);
            if (result != null) {
                System.out.println("[CREATE-SALARY-REC]: " + salaryRec.getSkillId().getName() + " - " + expLevel + " : " + salaryRec.getSalaryRec());
            }
        } else {
            salaryRec.setSalaryRec(salary);
            salaryRecDao.update(salaryRec);
            System.out.println("[UPDATE-SALARY-REC]: " + salaryRec.getSkillId().getName() + " - " + expLevel + " : " + salaryRec.getSalaryRec());
        }
    }

    public double generateMedianSalary(Double[] arr) {

        int n = arr.length;
        Arrays.sort(arr); // O(n log(n))

        // Finding median
        if (n % 2 != 0) {
            return arr[n / 2];
        }
        return (arr[(n - 1) / 2] + arr[n / 2]) / 2.0;
    }

}
