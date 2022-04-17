package com.nhom6.messageroomapp.data.model.base;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BaseAPIResponse<T> {
    @SerializedName("isSucceeded")
    @Expose
    public Boolean isSucceeded;
    @SerializedName("message")
    @Expose
    public String message;
    public int code = 200;
    @SerializedName("result")
    @Expose
    public T result;

    public BaseAPIResponse(Boolean isSucceeded, String message) {
        this.isSucceeded = isSucceeded;
        this.message = message;
    }

    public BaseAPIResponse(Boolean isSucceeded, String message, int code) {
        this.isSucceeded = isSucceeded;
        this.message = message;
        this.code = code;
    }

    public BaseAPIResponse(Boolean isSucceeded, String message, T result) {
        this.isSucceeded = isSucceeded;
        this.message = message;
        this.result = result;
    }

    public Boolean isSucceeded() {
        return isSucceeded;
    }

    public void setSucceeded(Boolean isSucceeded) {
        this.isSucceeded = isSucceeded;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}

