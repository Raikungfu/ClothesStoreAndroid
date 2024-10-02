package com.prmproject.clothesstoremobileandroid.Data.model;

public class Admin {
    private int AdminId;
    private String Avt;
    private String Cover;
    private int UserId;
    private User User;

    public int getAdminId() {
        return AdminId;
    }

    public void setAdminId(int adminId) {
        AdminId = adminId;
    }

    public String getAvt() {
        return Avt;
    }

    public void setAvt(String avt) {
        Avt = avt;
    }

    public String getCover() {
        return Cover;
    }

    public void setCover(String cover) {
        Cover = cover;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public com.prmproject.clothesstoremobileandroid.Data.model.User getUser() {
        return User;
    }

    public void setUser(com.prmproject.clothesstoremobileandroid.Data.model.User user) {
        User = user;
    }
}

