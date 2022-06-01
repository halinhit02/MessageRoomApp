package com.nhom6.messageroomapp.data.model.participant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nhom6.messageroomapp.data.model.common.AppUser;

import java.io.Serializable;

public class Participant implements Serializable {
    @SerializedName("conversationId")
    @Expose
    private Integer conversationId;
    @SerializedName("user")
    @Expose
    private AppUser user;
    @SerializedName("nickName")
    @Expose
    private String nickName;
    @SerializedName("isAdmin")
    @Expose
    private Boolean isAdmin;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;

    public Participant(Integer conversationId, AppUser user, String nickName) {
        this.conversationId = conversationId;
        this.user = user;
        this.nickName = nickName;
    }

    public Integer getConversationId() {
        return conversationId;
    }

    public void setConversationId(Integer conversationId) {
        this.conversationId = conversationId;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
