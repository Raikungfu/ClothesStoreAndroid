package com.prmproject.clothesstoremobileandroid.Data.model.dataToReceive;

public class ChatItemResponse {
    private int RoomId;
    private String Name;
    private String LatestMessageTime;
    private String Avatar;
    private String LatestMessage;
    private  boolean IsOnline;
    private boolean IsTyping;
    private int UserId;

    public int getRoomId() {
        return RoomId;
    }

    public void setRoomId(int roomId) {
        RoomId = roomId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLatestMessageTime() {
        return LatestMessageTime;
    }

    public void setLatestMessageTime(String latestMessageTime) {
        LatestMessageTime = latestMessageTime;
    }

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String avatar) {
        Avatar = avatar;
    }

    public String getLatestMessage() {
        return LatestMessage;
    }

    public void setLatestMessage(String latestMessage) {
        LatestMessage = latestMessage;
    }

    public boolean isOnline() {
        return IsOnline;
    }

    public void setOnline(boolean online) {
        IsOnline = online;
    }

    public boolean isTyping() {
        return IsTyping;
    }

    public void setTyping(boolean typing) {
        IsTyping = typing;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }
}
