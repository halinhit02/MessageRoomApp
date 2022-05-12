package com.nhom6.messageroomapp.data.model.user;
public class UserSearchRequest {
    public String keyword = "";
    public Integer pageIndex;
    public Integer pageSize;

    public UserSearchRequest(String keyword, Integer pageIndex, Integer pageSize) {
        this.keyword = keyword;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}

