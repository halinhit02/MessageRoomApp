package com.nhom6.messageroomapp.data.model.message;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MessageResponse implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("conversationId")
    @Expose
    private Integer conversationId;
    @SerializedName("senderId")
    @Expose
    private Integer senderId;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("isDeleted")
    @Expose
    private Boolean isDeleted;
    @SerializedName("messageType")
    @Expose
    private Integer messageType = -1;
    @SerializedName("attachments")
    @Expose
    private List<Attachment> attachments = new ArrayList<>();
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("deletedAt")
    @Expose
    private String deletedAt;

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

    public Integer getSenderId() {
        return senderId;
    }

    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Integer getMessageType() {
        return messageType;
    }

    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
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

    public String getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeValue(this.conversationId);
        dest.writeValue(this.senderId);
        dest.writeString(this.content);
        dest.writeValue(this.isDeleted);
        dest.writeValue(this.messageType);
        dest.writeTypedList(this.attachments);
        dest.writeString(this.createdAt);
        dest.writeString(this.updatedAt);
        dest.writeString(this.deletedAt);
    }

    public void readFromParcel(Parcel source) {
        this.id = (Integer) source.readValue(Integer.class.getClassLoader());
        this.conversationId = (Integer) source.readValue(Integer.class.getClassLoader());
        this.senderId = (Integer) source.readValue(Integer.class.getClassLoader());
        this.content = source.readString();
        this.isDeleted = (Boolean) source.readValue(Boolean.class.getClassLoader());
        this.messageType = (Integer) source.readValue(Integer.class.getClassLoader());
        this.attachments = source.createTypedArrayList(Attachment.CREATOR);
        this.createdAt = source.readString();
        this.updatedAt = source.readString();
        this.deletedAt = source.readString();
    }

    public MessageResponse() {
    }

    protected MessageResponse(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.conversationId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.senderId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.content = in.readString();
        this.isDeleted = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.messageType = (Integer) in.readValue(Integer.class.getClassLoader());
        this.attachments = in.createTypedArrayList(Attachment.CREATOR);
        this.createdAt = in.readString();
        this.updatedAt = in.readString();
        this.deletedAt = in.readString();
    }

    public static final Creator<MessageResponse> CREATOR = new Creator<MessageResponse>() {
        @Override
        public MessageResponse createFromParcel(Parcel source) {
            return new MessageResponse(source);
        }

        @Override
        public MessageResponse[] newArray(int size) {
            return new MessageResponse[size];
        }
    };
}

