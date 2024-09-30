package com.prmproject.clothesstoremobileandroid.Data.model.dataToReceive;

import java.util.List;

public class ListResponse<T> {
    private List<T> items;
    private String errorMessage;
    private boolean success;

    public ListResponse(List<T> items, String errorMessage, boolean success) {
        this.items = items;
        this.errorMessage = errorMessage;
        this.success = success;
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

