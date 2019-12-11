/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thucnh.dao;

import java.util.List;
import javax.persistence.EntityManager;
import thucnh.entity.TblUser;
import thucnh.utils.DBUtils;

/**
 *
 * @author HP
 */
public class UserDao extends BaseDao<TblUser, Integer> {

    public UserDao() {
    }

    private static UserDao instance;
    private static final Object LOCK = new Object();

    public static UserDao getInstance() {
        synchronized (LOCK) {
            if (instance == null) {
                instance = new UserDao();
            }
        }
        return instance;
    }

    public String checkLogin(String username, String password) {
        EntityManager manager = DBUtils.getEntityManager();
        String role = "ERROR";
        List<TblUser> result = null;
        try {
            result = manager.createNamedQuery("TblUser.checkLogin", TblUser.class)
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .getResultList();

            if (result != null && result.size() > 0) {
                return result.get(0).getRole();
            }
        } finally {
            if (manager != null) {
                manager.close();
            }
        }
        return role;
    }
}
