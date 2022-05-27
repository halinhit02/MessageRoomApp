package com.nhom6.messageroomapp.data.model.message;

public class MessageGetRequest {
    private Integer id;
    private Integer conversationId;
    private Integer userId;

    public MessageGetRequest(Integer id, Integer conversationId, Integer userId) {
        this.id = id;
        this.conversationId = conversationId;
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
}
