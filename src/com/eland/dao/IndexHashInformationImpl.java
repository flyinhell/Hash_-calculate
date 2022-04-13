package com.eland.dao;

import com.eland.entities.IndexHashInformationEntity;
import com.eland.tools.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class IndexHashInformationImpl implements IndexHashInformationDAO {
    private static Logger logger = Logger.getLogger("Log");


    @Override
    public IndexHashInformationEntity getById(int id) {
        IndexHashInformationEntity findId = null;
        Session session = HibernateUtil.getInstance().getSession();

        Transaction transaction = session.beginTransaction();
        try {
            findId = (IndexHashInformationEntity) session.get(IndexHashInformationEntity.class, id);
            transaction.commit();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            transaction.rollback();
        } finally {
            if (session != null)
                session.close();
        }
        return findId;
    }

    @Override
    public boolean updateIndexHashInformation(IndexHashInformationEntity IndexHashInformationEntity) {
        Session session = HibernateUtil.getInstance().getSession();
        Transaction transaction = session.getTransaction();
        try {
            session.beginTransaction();
            session.merge(IndexHashInformationEntity);
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            logger.error(e.getMessage(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return false;
    }

    @Override
    public boolean insertIndexHashInformation(IndexHashInformationEntity IndexHashInformationEntity) {
        Date newTime = new Date();

        Session session = HibernateUtil.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            //時間設置
            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String formatDate = dateFormatter.format(newTime);

            //參數設置
            IndexHashInformationEntity.setCreateTime(formatDate);

            session.save(IndexHashInformationEntity);
            transaction.commit();

            return true;
        } catch (HibernateException e) {
            transaction.rollback();
            logger.error(e.getMessage(), e);
            return false;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public boolean deleteIndexHashInformation(int id) {

        Session session = HibernateUtil.getInstance().getSession();
        Transaction transaction = session.getTransaction();
        IndexHashInformationEntity IndexHashInformationEntity = new IndexHashInformationEntity();
        try {
            session.beginTransaction();

            IndexHashInformationEntity.setId(id);
            session.delete(IndexHashInformationEntity);

            transaction.commit();
            return true;
        } catch (HibernateException e) {
            transaction.rollback();
            logger.error(e.getMessage(), e);
            return false;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List selectSearchRecord() {
        List indexHashInformationList = null;

        Session session = HibernateUtil.getInstance().getSession();
        try {
            Query query = session.createQuery("select ? from com.eland.entities.IndexHashInformationEntity");
            indexHashInformationList = query.list();

        } catch (HibernateException e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return indexHashInformationList;
    }
}
