package com.nhom6.messageroomapp.data.model.message;

import java.util.List;

public class MessageCreateRequest {
    private Integer userId;
    private Integer conversationId;
    private String content = "";
    private Integer messageType;
    private List<String> fileUrls = null;

    public MessageCreateRequest(Integer userId, Integer conversationId, String content, Integer messageType) {
        this.userId = userId;
        this.conversationId = conversationId;
        this.content = content;
        this.messageType = messageType;
    }

    public MessageCreateRequest(Integer userId, Integer conversationId, Integer messageType, List<String> fileUrls) {
        this.userId = userId;
        this.conversationId = conversationId;
        this.messageType = messageType;
        this.fileUrls = fileUrls;
    }

    public MessageCreateRequest(Integer userId, Integer conversationId, String content, Integer messageType, List<String> fileUrls) {
        this.userId = userId;
        this.conversationId = conversationId;
        this.content = content;
        this.messageType = messageType;
        this.fileUrls = fileUrls;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getConversationId() {
        return conversationId;
    }

    public void setConversationId(Integer conversationId) {
        this.conversationId = conversationId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getMessageType() {
        return messageType;
    }

    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
    }

    public List<String> getFileUrls() {
        return fileUrls;
    }

    public void setFileUrls(List<String> fileUrls) {
        this.fileUrls = fileUrls;
    }
}
