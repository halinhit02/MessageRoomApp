package com.nhom6.messageroomapp.data.model.base;

public class BaseGetPagingRequest {
    public Integer pageIndex;
    public Integer pageSize;
    public Integer userId;

    public BaseGetPagingRequest(Integer pageIndex, Integer pageSize, Integer userId) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.userId = userId;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}

