package com.nhom6.messageroomapp.data.model.message;

import com.nhom6.messageroomapp.data.model.participant.Participant;

import io.agora.rtm.RtmMessage;

public class MessageClient {

    private Participant participant;
    private MessageResponse serverMessage;
    private RtmMessage clientMessage;
    private String date;
    private boolean isClient = false;
    private boolean isInfoDisplayed = true;
    private boolean isSent = true;

    public MessageClient(MessageResponse serverMessage, String date) {
        this.serverMessage = serverMessage;
        this.date = date;
    }

    public MessageClient(RtmMessage clientMessage, String date, boolean isClient) {
        this.clientMessage = clientMessage;
        this.date = date;
        this.isClient = isClient;
    }

    public MessageClient(RtmMessage clientMessage, String date, boolean isClient, boolean isSent) {
        this.clientMessage = clientMessage;
        this.date = date;
        this.isClient = isClient;
        this.isSent = isSent;
    }

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    public MessageResponse getServerMessage() {
        return serverMessage;
    }

    public void setServerMessage(MessageResponse serverMessage) {
        this.serverMessage = serverMessage;
    }

    public RtmMessage getClientMessage() {
        return clientMessage;
    }

    public void setClientMessage(RtmMessage clientMessage) {
        this.clientMessage = clientMessage;
    }

    public boolean isClient() {
        return isClient;
    }

    public void setClient(boolean client) {
        isClient = client;
    }

    public boolean isInfoDisplayed() {
        return isInfoDisplayed;
    }

    public void setInfoDisplayed(boolean infoDisplayed) {
        isInfoDisplayed = infoDisplayed;
    }

    public boolean isSent() {
        return isSent;
    }

    public void setSent(boolean sent) {
        isSent = sent;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
