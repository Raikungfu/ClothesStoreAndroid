package com.prmproject.clothesstoremobileandroid.Data.model.dataToSend;

public class ChatRoom {
    private Integer roomId;
    private Integer userCreatedId;
    private Integer userIdOther;

    public ChatRoom(Integer userIdOther) {
        this.userIdOther = userIdOther;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public Integer getUserCreatedId() {
        return userCreatedId;
    }

    public void setUserCreatedId(Integer userCreate) {
        this.userCreatedId = userCreate;
    }

    public Integer getUserIdOther() {
        return userIdOther;
    }

    public void setUserIdOther(Integer userIdOther) {
        this.userIdOther = userIdOther;
    }
}
