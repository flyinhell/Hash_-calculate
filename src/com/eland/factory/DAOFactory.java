package com.eland.factory;

import com.eland.dao.IndexHashInformationDAO;
import com.eland.dao.IndexHashInformationImpl;
import com.eland.dao.SearchRecordDAO;
import com.eland.dao.SearchRecordDaoImpl;

public  class DAOFactory {
    public static SearchRecordDAO getSearchRecordInstance(){
        return new SearchRecordDaoImpl();
    }
    public static IndexHashInformationDAO getIndexHashInformationInstance(){
        return new IndexHashInformationImpl();
    }
}
