package com.eland.dao;
import com.eland.entities.IndexHashInformationEntity;

import java.util.List;

public interface IndexHashInformationDAO {
    IndexHashInformationEntity getById(int id);
    boolean updateIndexHashInformation(IndexHashInformationEntity IndexHashInformationEntity);
    boolean insertIndexHashInformation(IndexHashInformationEntity IndexHashInformationEntity);
    boolean deleteIndexHashInformation(int id);
    List selectSearchRecord();

}
