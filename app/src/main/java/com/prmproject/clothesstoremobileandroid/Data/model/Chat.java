package com.prmproject.clothesstoremobileandroid.Data.model;

import java.util.List;

public class Chat {
    private int RoomId;
    private int UserId1;
    private int UserId2;
    private User User1;
    private User User2;
    private List<ChatMessage> ChatMessages;

    public int getRoomId() {
        return RoomId;
    }

    public void setRoomId(int roomId) {
        RoomId = roomId;
    }

    public int getUserId1() {
        return UserId1;
    }

    public void setUserId1(int userId1) {
        UserId1 = userId1;
    }

    public int getUserId2() {
        return UserId2;
    }

    public void setUserId2(int userId2) {
        UserId2 = userId2;
    }

    public User getUser1() {
        return User1;
    }

    public void setUser1(User user1) {
        User1 = user1;
    }

    public User getUser2() {
        return User2;
    }

    public void setUser2(User user2) {
        User2 = user2;
    }

    public List<ChatMessage> getChatMessages() {
        return ChatMessages;
    }

    public void setChatMessages(List<ChatMessage> chatMessages) {
        ChatMessages = chatMessages;
    }
}
