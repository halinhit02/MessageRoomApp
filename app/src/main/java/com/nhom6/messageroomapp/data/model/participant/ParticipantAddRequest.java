package com.nhom6.messageroomapp.data.model.participant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ParticipantAddRequest {
    @SerializedName("conversationId")
    @Expose
    private Integer conversationId;
    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("joinerId")
    @Expose
    private Integer joinerId;

    public ParticipantAddRequest(Integer conversationId, Integer userId, Integer joinerId) {
        this.conversationId = conversationId;
        this.userId = userId;
        this.joinerId = joinerId;
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

    public Integer getJoinerId() {
        return joinerId;
    }

    public void setJoinerId(Integer joinerId) {
        this.joinerId = joinerId;
    }
}
