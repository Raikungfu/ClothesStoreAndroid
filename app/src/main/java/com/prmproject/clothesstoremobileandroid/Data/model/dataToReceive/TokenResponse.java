package com.prmproject.clothesstoremobileandroid.Data.model.dataToReceive;

public class TokenResponse {
    private String Token;
    private String Message;
    private boolean Status;

    public TokenResponse(String token, boolean status) {
        Token = token;
        Status = status;
    }

    public TokenResponse(String token, String message, boolean status) {
        Token = token;
        Message = message;
        Status = status;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public boolean isStatus() {
        return Status;
    }

    public void setStatus(boolean status) {
        Status = status;
    }
}
