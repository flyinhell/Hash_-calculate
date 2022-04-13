package com.eland.dao;

import com.eland.entities.SearchRecordEntity;

import java.util.List;


public interface SearchRecordDAO {
    SearchRecordEntity getById(int id);
    boolean updateSearchRecord(SearchRecordEntity searchRecordEntity);
    boolean insertSearchRecord(SearchRecordEntity searchRecordEntity);
    boolean deleteSearchRecord(int id);
    List selectSearchRecord();

}
