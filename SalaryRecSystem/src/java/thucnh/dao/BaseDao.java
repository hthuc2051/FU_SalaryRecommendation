/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thucnh.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import thucnh.utils.DBUtils;

/**
 *
 * @author HP
 * @param <T>
 * @param <PK>
 */
public class BaseDao<T, PK extends Serializable> {

    protected Class<T> entityClass;

    public BaseDao() {
        ParameterizedType genericSuperClass = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass = (Class<T>) genericSuperClass.getActualTypeArguments()[0];

    }

    public T create(T t) {
        EntityManager manager = DBUtils.getEntityManager();
        try {
            EntityTransaction transaction = manager.getTransaction();
            transaction.begin();
            manager.persist(t);
            manager.flush();
            transaction.commit();
            return t;
        } finally {
            if (manager != null) {
                manager.close();
            }
        }
    }

    public T update(T t) {
        EntityManager manager = DBUtils.getEntityManager();
        try {
            EntityTransaction transaction = manager.getTransaction();
            transaction.begin();
            manager.merge(t);
            manager.flush();
            transaction.commit();
            return t;
        } finally {
            if (manager != null) {
                manager.close();
            }
        }
    }

    public boolean delete(T t) {
        EntityManager manager = DBUtils.getEntityManager();
        try {
            EntityTransaction transaction = manager.getTransaction();
            transaction.begin();
            manager.remove(t);
            manager.flush();
            transaction.commit();
            return true;
        } finally {
            if (manager != null) {
                manager.close();
            }
        }
    }

    public T findByID(PK id) {
        EntityManager manager = DBUtils.getEntityManager();
        try {
            EntityTransaction transaction = manager.getTransaction();
            transaction.begin();
            T result = manager.find(entityClass, id);
            transaction.commit();
            return result;
        } finally {
            if (manager != null) {
                manager.close();
            }
        }
    }

    public List<T> getAll(String namedQuery) {
        EntityManager manager = DBUtils.getEntityManager();
        try {
            EntityTransaction transaction = manager.getTransaction();
            transaction.begin();
            List<T> result = manager.createNamedQuery(namedQuery, entityClass).getResultList();
            transaction.commit();
            return result;
        } finally {
            if (manager != null) {
                manager.close();
            }
        }
    }

}
