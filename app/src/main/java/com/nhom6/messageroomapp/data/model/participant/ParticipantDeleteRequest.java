package com.nhom6.messageroomapp.data.model.participant;

public class ParticipantDeleteRequest {
    private Integer conversationId;
    private Integer userId;
    private Integer leaverId;

    public ParticipantDeleteRequest(Integer conversationId, Integer userId, Integer leaverId) {
        this.conversationId = conversationId;
        this.userId = userId;
        this.leaverId = leaverId;
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

    public Integer getLeaverId() {
        return leaverId;
    }

    public void setLeaverId(Integer leaverId) {
        this.leaverId = leaverId;
    }
}
