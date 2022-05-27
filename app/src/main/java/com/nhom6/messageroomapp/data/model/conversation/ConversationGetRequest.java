package com.nhom6.messageroomapp.data.model.conversation;

public class ConversationGetRequest {
    private Integer id;
    private Integer userId;

    public ConversationGetRequest(Integer id, Integer userId) {
        this.id = id;
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
