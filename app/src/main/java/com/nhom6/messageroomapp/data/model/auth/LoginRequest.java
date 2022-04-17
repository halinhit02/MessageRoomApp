package com.nhom6.messageroomapp.data.model.auth;

import android.text.TextUtils;
import android.util.Patterns;

import androidx.annotation.NonNull;

public class LoginRequest {

    private String username;
    private String password;

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NonNull
    @Override
    public String toString() {
        return "{" +
                "\"username\": \"" + username + "\"" +
                ", \"password\": \"" + password + "\"" +
                '}';
    }

    public boolean isUerNameValid() {
        return !username.isEmpty();//!TextUtils.isEmpty(username) && Patterns.EMAIL_ADDRESS.matcher(username).matches();
    }

    public boolean isPasswordValid() {
        return !TextUtils.isEmpty(password) && password.length() >= 6;
    }
}

