package com.prmproject.clothesstoremobileandroid.Data.model.dataToReceive;

public class ObjectResponse<T> {
    private T item;
    private String errorMessage;
    private boolean success;
    private int codeError;

    public ObjectResponse(T item, String errorMessage, boolean success) {
        this.item = item;
        this.errorMessage = errorMessage;
        this.success = success;
    }

    public ObjectResponse(T item, String errorMessage, boolean success, int codeError) {
        this.item = item;
        this.errorMessage = errorMessage;
        this.success = success;
        this.codeError = codeError;
    }

    public T getItem() {
        return item;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean isSuccess() {
        return success;
    }

    public int getCodeError() {
        return codeError;
    }
}
