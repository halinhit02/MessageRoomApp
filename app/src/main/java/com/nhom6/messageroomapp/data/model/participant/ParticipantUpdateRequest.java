package com.nhom6.messageroomapp.data.model.participant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ParticipantUpdateRequest {
    @SerializedName("conversationId")
    @Expose
    private Integer conversationId;
    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("otherId")
    @Expose
    private Integer otherId;
    @SerializedName("nickName")
    @Expose
    private String nickName = "";
    @SerializedName("isAdmin")
    @Expose
    private Boolean isAdmin;

    public ParticipantUpdateRequest(Integer conversationId, Integer userId, Integer otherId, String nickName, Boolean isAdmin) {
        this.conversationId = conversationId;
        this.userId = userId;
        this.otherId = otherId;
        this.nickName = nickName;
        this.isAdmin = isAdmin;
    }

    public ParticipantUpdateRequest(Integer conversationId, Integer userId, Integer otherId, Boolean isAdmin) {
        this.conversationId = conversationId;
        this.userId = userId;
        this.otherId = otherId;
        this.isAdmin = isAdmin;
    }

    public Integer getConversationId() {
        return conversationId;
    }

    public void setConversationId(Integer conversationId) {
        this.conversationId = conversationId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getOtherId() {
        return otherId;
    }

    public void setOtherId(Integer otherId) {
        this.otherId = otherId;
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

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }
}
