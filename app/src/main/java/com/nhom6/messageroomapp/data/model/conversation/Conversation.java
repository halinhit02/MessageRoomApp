package com.nhom6.messageroomapp.data.model.conversation;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nhom6.messageroomapp.data.model.message.MessageResponse;

public class Conversation implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("creatorId")
    @Expose
    private Integer creatorId;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("channelId")
    @Expose
    private String channelId;
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("background")
    @Expose
    private String background;
    @SerializedName("latestMessage")
    @Expose
    private MessageResponse latestMessage;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("deletedAt")
    @Expose
    private String deletedAt;

    public Conversation() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
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

    public MessageResponse getLatestMessage() {
        return latestMessage;
    }

    public void setLatestMessage(MessageResponse latestMessage) {
        this.latestMessage = latestMessage;
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
        dest.writeString(this.title);
        dest.writeValue(this.creatorId);
        dest.writeString(this.description);
        dest.writeString(this.channelId);
        dest.writeString(this.avatar);
        dest.writeString(this.background);
        dest.writeParcelable(this.latestMessage, flags);
        dest.writeString(this.createdAt);
        dest.writeString(this.updatedAt);
        dest.writeString(this.deletedAt);
    }

    public void readFromParcel(Parcel source) {
        this.id = (Integer) source.readValue(Integer.class.getClassLoader());
        this.title = source.readString();
        this.creatorId = (Integer) source.readValue(Integer.class.getClassLoader());
        this.description = source.readString();
        this.channelId = source.readString();
        this.avatar = source.readString();
        this.background = source.readString();
        this.latestMessage = source.readParcelable(MessageResponse.class.getClassLoader());
        this.createdAt = source.readString();
        this.updatedAt = source.readString();
        this.deletedAt = source.readString();
    }

    protected Conversation(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.title = in.readString();
        this.creatorId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.description = in.readString();
        this.channelId = in.readString();
        this.avatar = in.readString();
        this.background = in.readString();
        this.latestMessage = in.readParcelable(MessageResponse.class.getClassLoader());
        this.createdAt = in.readString();
        this.updatedAt = in.readString();
        this.deletedAt = in.readString();
    }

    public static final Creator<Conversation> CREATOR = new Creator<Conversation>() {
        @Override
        public Conversation createFromParcel(Parcel source) {
            return new Conversation(source);
        }

        @Override
        public Conversation[] newArray(int size) {
            return new Conversation[size];
        }
    };
}

