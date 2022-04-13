package com.eland.dao;

import com.eland.entities.SearchRecordEntity;
import com.eland.jdbc.SearchRecord;
import com.eland.tools.HibernateUtil;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SearchRecordDaoImpl implements SearchRecordDAO {
    private static Logger logger = Logger.getLogger("Log");

    public List selectKeywordSearchNum() {
        List searchRecordList = null;

        Session session = HibernateUtil.getInstance().getSession();
        try {
            Query query = session.createQuery("select ? from com.eland.entities.SearchRecordEntity");
            searchRecordList = query.list();

        } catch (HibernateException e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return searchRecordList;
    }

    /**
     * 將testdb.dbo.search_search 的資料全部放入List
     *
     * @return
     */
    public List<SearchRecord> selectSearchRecord() {
        List searchRecordList = null;

        Session session = HibernateUtil.getInstance().getSession();
        try {
            searchRecordList = session.createQuery("from com.eland.entities.SearchRecordEntity").list();

        } catch (HibernateException e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return searchRecordList;
    }

    /**
     * 透過primary key 取得資料
     *
     * @param id
     * @return
     */
    @Override
    public SearchRecordEntity getById(int id) {
        SearchRecordEntity findId = null;
        Session session = HibernateUtil.getInstance().getSession();

        Transaction transaction = session.beginTransaction();
        try {
            findId = (SearchRecordEntity) session.get(SearchRecordEntity.class, id);
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

    /**
     * 新增資料
     *
     * @param searchRecordEntity
     * @return
     */
    @Override
    public boolean updateSearchRecord(SearchRecordEntity searchRecordEntity) {

        Session session = HibernateUtil.getInstance().getSession();
        Transaction transaction = session.getTransaction();
        try {
            session.beginTransaction();
            session.merge(searchRecordEntity);
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

    /**
     * Insert資料
     *
     * @param searchRecordEntity
     * @return
     */
    @Override
    public boolean insertSearchRecord(SearchRecordEntity searchRecordEntity) {
        Date newTime = new Date();

        Session session = HibernateUtil.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            //時間設置
            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String formatDate = dateFormatter.format(newTime);

            //參數設置
            searchRecordEntity.setSearchTime(formatDate);

            session.save(searchRecordEntity);
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

    /**
     * 刪除資料
     *
     * @param id
     * @return
     */
    @Override
    public boolean deleteSearchRecord(int id) {



        Session session = HibernateUtil.getInstance().getSession();
        Transaction transaction = session.getTransaction();
        SearchRecordEntity searchRecordEntity = new SearchRecordEntity();
        try {
            session.beginTransaction();

            searchRecordEntity.setId(id);
            session.delete(searchRecordEntity);

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
}
