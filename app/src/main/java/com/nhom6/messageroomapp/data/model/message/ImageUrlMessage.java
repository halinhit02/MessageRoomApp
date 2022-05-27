package com.nhom6.messageroomapp.data.model.message;

import io.agora.rtm.RtmMessage;

public class ImageUrlMessage extends RtmMessage {

    private String fileUrl;

    @Override
    public void setText(String s) {
        fileUrl = s;
    }

    @Override
    public String getText() {
        return fileUrl;
    }

    @Override
    public void setRawMessage(byte[] bytes) {

    }

    @Override
    public void setRawMessage(byte[] bytes, String s) {

    }

    @Override
    public byte[] getRawMessage() {
        return new byte[0];
    }

    @Override
    public int getMessageType() {
        return 0;
    }

    @Override
    public long getServerReceivedTs() {
        return 0;
    }

    @Override
    public boolean isOfflineMessage() {
        return false;
    }
}
