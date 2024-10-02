package com.prmproject.clothesstoremobileandroid.Data.model;

public class ChatMessage {
    private int MessageId;
    private int RoomId;
    private int SenderId;
    private String Content;
    private String Media;
    private String Icon;
    private String Timestamp;
    private Chat Chat;
    private User User;
    private boolean IsSender;

    public int getMessageId() {
        return MessageId;
    }

    public void setMessageId(int messageId) {
        MessageId = messageId;
    }

    public int getRoomId() {
        return RoomId;
    }

    public void setRoomId(int roomId) {
        RoomId = roomId;
    }

    public int getSenderId() {
        return SenderId;
    }

    public void setSenderId(int senderId) {
        SenderId = senderId;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getMedia() {
        return Media;
    }

    public void setMedia(String media) {
        Media = media;
    }

    public String getIcon() {
        return Icon;
    }

    public void setIcon(String icon) {
        Icon = icon;
    }

    public String getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(String timestamp) {
        Timestamp = timestamp;
    }

    public com.prmproject.clothesstoremobileandroid.Data.model.Chat getChat() {
        return Chat;
    }

    public void setChat(com.prmproject.clothesstoremobileandroid.Data.model.Chat chat) {
        Chat = chat;
    }

    public com.prmproject.clothesstoremobileandroid.Data.model.User getUser() {
        return User;
    }

    public void setUser(com.prmproject.clothesstoremobileandroid.Data.model.User user) {
        User = user;
    }

    public boolean isSender() {
        return IsSender;
    }

    public void setSender(boolean sender) {
        IsSender = sender;
    }
}

