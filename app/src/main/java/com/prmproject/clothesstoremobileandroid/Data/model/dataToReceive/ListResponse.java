package com.prmproject.clothesstoremobileandroid.Data.model.dataToReceive;

import java.util.List;

public class ListResponse<T> {
    private List<T> items;
    private String errorMessage;
    private boolean success;
    private int codeError;

    public ListResponse(List<T> items, String errorMessage, boolean success) {
        this.items = items;
        this.errorMessage = errorMessage;
        this.success = success;
    }

    public ListResponse(List<T> items, String errorMessage, boolean success, int codeError) {
        this.items = items;
        this.errorMessage = errorMessage;
        this.success = success;
        this.codeError = codeError;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCodeError() {
        return codeError;
    }

    public void setCodeError(short codeError) {
        this.codeError = codeError;
    }

    public List<T> getItems() {
        return items;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean isSuccess() {
        return success;
    }


}

