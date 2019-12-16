/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thucnh.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import thucnh.entity.TblCluster;
import thucnh.utils.DBUtils;

/**
 *
 * @author HP
 */
public class ClusterDao extends BaseDao<TblCluster, Integer> {

    public ClusterDao() {
    }

    private static ClusterDao instance;
    private static final Object LOCK = new Object();

    public static ClusterDao getInstance() {
        synchronized (LOCK) {
            if (instance == null) {
                instance = new ClusterDao();
            }
        }
        return instance;
    }

    public List<TblCluster> findClustersByHash(Integer hashValue) {
        EntityManager manager = DBUtils.getEntityManager();
        List<TblCluster> result = null;
        try {
            result = manager.createNamedQuery("TblCluster.findByHash", TblCluster.class)
                    .setParameter("hash", hashValue)
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

    public void deleteClusters() {
        EntityManager manager = DBUtils.getEntityManager();
        try {
            EntityTransaction transaction = manager.getTransaction();
            transaction.begin();
            manager.createNativeQuery("Delete FROM TblCluster").executeUpdate();
            transaction.commit();

        } finally {
            if (manager != null) {
                manager.close();
            }
        }
    }
}
