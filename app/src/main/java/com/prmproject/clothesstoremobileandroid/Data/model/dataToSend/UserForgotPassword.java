package com.prmproject.clothesstoremobileandroid.Data.model.dataToSend;

public class UserForgotPassword {
    private String NewPassword;
    private String Email;
    private Integer Otp;

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
}