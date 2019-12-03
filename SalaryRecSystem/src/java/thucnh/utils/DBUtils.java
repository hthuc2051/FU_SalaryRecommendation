/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thucnh.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author HP
 */
public class DBUtils {
    
    private static final Object LOCK = new Object();
    private static EntityManagerFactory manager;

    public static EntityManager getEntityManager() {
        synchronized (LOCK) {
            if (manager == null) {
                try {
                    manager = Persistence.createEntityManagerFactory("SalaryRecommendationSystemPU");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        return manager.createEntityManager();
    }
}
