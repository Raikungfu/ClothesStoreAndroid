package com.prmproject.clothesstoremobileandroid.Data.model.dataToReceive;

import android.text.TextUtils;

import java.util.List;
import java.util.Map;

public class ErrorResponse {
    private String title;
    private int status;
    private Map<String, List<String>> errors;
    private StringBuilder errorMessages;

    public String getTitle() {
        return title;
    }

    public int getStatus() {
        return status;
    }

    public Map<String, List<String>> getErrors() {
        return errors;
    }

    public StringBuilder getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(StringBuilder errorMessages) {
        this.errorMessages = errorMessages;
    }

    public void convertErrorToString(){
        this.errorMessages = new StringBuilder();
        for (Map.Entry<String, List<String>> entry : this.getErrors().entrySet()) {
            this.errorMessages.append(entry.getKey()).append(": ")
                    .append(TextUtils.join(", ", entry.getValue())).append("\n");
        }
    }
}
