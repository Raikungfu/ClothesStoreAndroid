package com.prmproject.clothesstoremobileandroid.Data.model.dataToSend;

public class UserForgotPassword {
    private String NewPassword;
    private String Email;
    private Integer Otp;
    private String Token;

    public UserForgotPassword() {
    }

    public String getNewPassword() {
        return NewPassword;
    }

    public void setNewPassword(String newPassword) {
        NewPassword = newPassword;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public Integer getOtp() {
        return Otp;
    }

    public void setOtp(Integer otp) {
        Otp = otp;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }
}