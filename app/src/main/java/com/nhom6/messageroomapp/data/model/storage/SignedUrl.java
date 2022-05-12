package com.nhom6.messageroomapp.data.model.storage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignedUrl {
    @SerializedName("signedUrl")
    @Expose
    private String signedUrl;
    @SerializedName("fileUrl")
    @Expose
    private String fileUrl;

    public String getSignedUrl() {
        return signedUrl;
    }

    public void setSignedUrl(String signedUrl) {
        this.signedUrl = signedUrl;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

}
