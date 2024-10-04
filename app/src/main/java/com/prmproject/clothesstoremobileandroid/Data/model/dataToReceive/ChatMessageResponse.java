package com.prmproject.clothesstoremobileandroid.Data.model.dataToReceive;

public class ChatMessageResponse<T> {
    private T Response;
    private String Content;
    private String Status;

    public T getResponse() {
        return Response;
    }

    public void setResponse(T response) {
        Response = response;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
