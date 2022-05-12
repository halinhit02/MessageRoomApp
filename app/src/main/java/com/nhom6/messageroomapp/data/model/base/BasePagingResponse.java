package com.nhom6.messageroomapp.data.model.base;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class BasePagingResponse<T> {
    @SerializedName("items")
    @Expose
    public List<T> items = new ArrayList<>();
    @SerializedName("totalRecord")
    @Expose
    public Integer totalRecord;

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public Integer getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(Integer totalRecord) {
        this.totalRecord = totalRecord;
    }
}
