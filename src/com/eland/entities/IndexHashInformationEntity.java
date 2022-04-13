package com.eland.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "index_hash_information", schema = "dbo", catalog = "testdb")
public class IndexHashInformationEntity {
    private int id;
    private String indexNamePathBefore;
    private String md5Before;
    private String indexNamePathAfter;
    private String md5After;
    private String hashResult;
    private String createTime;

    @Basic
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "index_name_path_before")
    public String getIndexNamePathBefore() {
        return indexNamePathBefore;
    }

    public void setIndexNamePathBefore(String indexNamePathBefore) {
        this.indexNamePathBefore = indexNamePathBefore;
    }

    @Basic
    @Column(name = "md5_before")
    public String getMd5Before() {
        return md5Before;
    }

    public void setMd5Before(String md5Before) {
        this.md5Before = md5Before;
    }

    @Basic
    @Column(name = "index_name_path_after")
    public String getIndexNamePathAfter() {
        return indexNamePathAfter;
    }

    public void setIndexNamePathAfter(String indexNamePathAfter) {
        this.indexNamePathAfter = indexNamePathAfter;
    }

    @Basic
    @Column(name = "md5_after")
    public String getMd5After() {
        return md5After;
    }

    public void setMd5After(String md5After) {
        this.md5After = md5After;
    }

    @Basic
    @Column(name = "hash_result")
    public String getHashResult() {
        return hashResult;
    }

    public void setHashResult(String hashResult) {
        this.hashResult = hashResult;
    }

    @Basic
    @Column(name = "create_time")
    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IndexHashInformationEntity that = (IndexHashInformationEntity) o;
        return id == that.id &&
                Objects.equals(indexNamePathBefore, that.indexNamePathBefore) &&
                Objects.equals(md5Before, that.md5Before) &&
                Objects.equals(indexNamePathAfter, that.indexNamePathAfter) &&
                Objects.equals(md5After, that.md5After) &&
                Objects.equals(hashResult, that.hashResult) &&
                Objects.equals(createTime, that.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, indexNamePathBefore, md5Before, indexNamePathAfter, md5After, hashResult, createTime);
    }
}
