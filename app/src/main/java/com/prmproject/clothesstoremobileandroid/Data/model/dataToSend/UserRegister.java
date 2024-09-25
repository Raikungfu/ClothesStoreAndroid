package com.prmproject.clothesstoremobileandroid.Data.model.dataToSend;

public class UserRegister {
    private String Username;
    private String Password;
    private String Email;
    private String Phone;

    public UserRegister(String username, String password, String email, String phone) {
        Username = username;
        Password = password;
        Email = email;
        Phone = phone;
    }
}
