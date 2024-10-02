package com.prmproject.clothesstoremobileandroid.Data.repository.Generic;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.prmproject.clothesstoremobileandroid.Data.model.dataToReceive.ErrorResponse;
import com.prmproject.clothesstoremobileandroid.Data.model.dataToReceive.ObjectResponse;

import retrofit2.Response;

public class ResponseObjectData<T> {
    public ResponseObjectData() {
    }

    public MutableLiveData<ObjectResponse<T>> getObjectData(Response<T> response) {
        MutableLiveData<ObjectResponse<T>> tokenLiveData = new MutableLiveData<>();

        if (response.isSuccessful() && response.body() != null) {
            tokenLiveData.setValue(new ObjectResponse<>(response.body(), null, true));
        } else {
            String errorMessage = "Unknown error occurred.";
            if (response.errorBody() != null) {
                try {
                    ErrorResponse apiError = new Gson().fromJson(response.errorBody().charStream(), ErrorResponse.class);
                    errorMessage = apiError.convertErrorToString();
                } catch (Exception e) {
                    errorMessage = "Error parsing error response.";
                }
            }
            tokenLiveData.setValue(new ObjectResponse<>(null, errorMessage, false));
        }

        return tokenLiveData;
    }
}