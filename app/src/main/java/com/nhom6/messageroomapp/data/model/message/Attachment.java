package com.nhom6.messageroomapp.data.model.message;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Attachment implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("messageId")
    @Expose
    private Integer messageId;
    @SerializedName("thumbUrl")
    @Expose
    private String thumbUrl;
    @SerializedName("fileUrl")
    @Expose
    private String fileUrl;
    @SerializedName("isDeleted")
    @Expose
    private Boolean isDeleted;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("deletedAt")
    @Expose
    private String deletedAt;

    public Attachment() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
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
        dest.writeValue(this.messageId);
        dest.writeString(this.thumbUrl);
        dest.writeString(this.fileUrl);
        dest.writeValue(this.isDeleted);
        dest.writeString(this.createdAt);
        dest.writeString(this.updatedAt);
        dest.writeString(this.deletedAt);
    }

    public void readFromParcel(Parcel source) {
        this.id = (Integer) source.readValue(Integer.class.getClassLoader());
        this.messageId = (Integer) source.readValue(Integer.class.getClassLoader());
        this.thumbUrl = source.readString();
        this.fileUrl = source.readString();
        this.isDeleted = (Boolean) source.readValue(Boolean.class.getClassLoader());
        this.createdAt = source.readString();
        this.updatedAt = source.readString();
        this.deletedAt = source.readString();
    }

    protected Attachment(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.messageId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.thumbUrl = in.readString();
        this.fileUrl = in.readString();
        this.isDeleted = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.createdAt = in.readString();
        this.updatedAt = in.readString();
        this.deletedAt = in.readString();
    }

    public static final Creator<Attachment> CREATOR = new Creator<Attachment>() {
        @Override
        public Attachment createFromParcel(Parcel source) {
            return new Attachment(source);
        }

        @Override
        public Attachment[] newArray(int size) {
            return new Attachment[size];
        }
    };
}

