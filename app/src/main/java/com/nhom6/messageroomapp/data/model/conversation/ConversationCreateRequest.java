package com.nhom6.messageroomapp.data.model.conversation;

import java.util.ArrayList;
import java.util.List;

public class ConversationCreateRequest {
    private List<Integer> userIds;
    private String title = "";
    private String description = "";

    public ConversationCreateRequest(List<Integer> userIds, String title, String description) {
        this.userIds = userIds;
        this.title = title;
        this.description = description;
    }

    public ConversationCreateRequest() {
        userIds = new ArrayList<>();
    }

    public List<Integer> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Integer> userIds) {
        this.userIds = userIds;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
