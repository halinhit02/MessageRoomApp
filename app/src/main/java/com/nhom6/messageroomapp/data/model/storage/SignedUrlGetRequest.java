package com.nhom6.messageroomapp.data.model.storage;

public class SignedUrlGetRequest {
    private String folderName;
    private int id;
    private String fileName;

    public SignedUrlGetRequest(FolderName folderName, int id, String fileName) {
        this.folderName = folderName.name();
        this.id = id;
        this.fileName = fileName;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(FolderName folderName) {
        this.folderName = folderName.name();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}

