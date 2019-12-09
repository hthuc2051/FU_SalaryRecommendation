/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thucnh.dao;

import thucnh.entity.TblCluster;

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
}
