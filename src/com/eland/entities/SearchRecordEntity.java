package com.eland.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "search_record", schema = "dbo", catalog = "testdb")
public class SearchRecordEntity {
    private String keyword;
    private Integer totalCount;
    private String searchTime;
    private int id;

    @Basic
    @Column(name = "keyword", nullable = true, length = 50)
    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @Basic
    @Column(name = "total_count", nullable = true)
    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    @Basic
    @Column(name = "search_time", nullable = true, length = 50)
    public String getSearchTime() {
        return searchTime;
    }

    public void setSearchTime(String searchTime) {
        this.searchTime = searchTime;
    }

    @Basic
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchRecordEntity that = (SearchRecordEntity) o;
        return id == that.id &&
                Objects.equals(keyword, that.keyword) &&
                Objects.equals(totalCount, that.totalCount) &&
                Objects.equals(searchTime, that.searchTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(keyword, totalCount, searchTime, id);
    }
}
