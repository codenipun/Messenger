package com.codenipun.task_chatapp.Models;

public class messageModel {
    String messageId, senderId, message;
    long timeStamp;

    public messageModel() {
    }

    public messageModel(String senderId, String message, long timeStamp) {
        this.senderId = senderId;
        this.message = message;
        this.timeStamp = timeStamp;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
