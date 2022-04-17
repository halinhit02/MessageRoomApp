package com.nhom6.messageroomapp.data.model.auth;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nhom6.messageroomapp.data.model.common.AppUser;

import java.io.Serializable;

public class AuthResult implements Serializable {

    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("data")
    @Expose
    private AppUser data;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public AppUser getData() {
        return data;
    }

    public void setData(AppUser data) {
        this.data = data;
    }

}

