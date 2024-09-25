package com.prmproject.clothesstoremobileandroid.Data.model.dataToReceive;

public class MessageResponse {
    private String Message;
    private boolean Status;

    public MessageResponse(String message, boolean status) {
        Message = message;
        Status = status;
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
