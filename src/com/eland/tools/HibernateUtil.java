package com.eland.tools;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static HibernateUtil hibernateUtil = null;
    private static Logger logger = Logger.getLogger("Log");

    public Session getSession() throws HibernateException {
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");

        SessionFactory sessionFactory = cfg.buildSessionFactory();
        Session session = sessionFactory.openSession();
        return session;
    }
    public static HibernateUtil getInstance() {

        try {
            if (hibernateUtil == null) {
                hibernateUtil = new HibernateUtil();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return hibernateUtil;
    }
}