package com.nhom6.messageroomapp.data.model.conversation;

public class ConversationEditRequest {
    private Integer id;
    private Integer userId;
    private String title;
    private String description;
    private String avatar;
    private String background;

    public ConversationEditRequest() {
    }

    public ConversationEditRequest(Integer id, Integer userId, String title, String description) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.description = description;
    }

    public ConversationEditRequest(Integer id, Integer userId, String title, String description, String avatar, String background) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.avatar = avatar;
        this.background = background;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }
}
