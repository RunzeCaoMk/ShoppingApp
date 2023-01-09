package com.cao.shoppingApp.DAO;

import com.cao.shoppingApp.config.HibernateConfigUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class DAO {

    SessionFactory sessionFactory = HibernateConfigUtil.getSessionFactory();
    Session session = sessionFactory.openSession();

    public DAO() {}

    public Session getSession() {
        return session;
    }

    public void begin() {
        session.beginTransaction();
    }

    public void commit() {
        session.getTransaction().commit();
    }

    public void rollback() {
        session.getTransaction().rollback();
        session.close();
    }

    public void close() {
        session.close();
    }
}
