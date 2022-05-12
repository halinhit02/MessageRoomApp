package com.nhom6.messageroomapp.data.model.storage;

import java.io.File;

public class FileUploadRequest {
    private String signedUrl;
    private File file;

    public FileUploadRequest(String signedUrl, File file) {
        this.signedUrl = signedUrl;
        this.file = file;
    }

    public String getSignedUrl() {
        return signedUrl;
    }

    public void setSignedUrl(String signedUrl) {
        this.signedUrl = signedUrl;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
