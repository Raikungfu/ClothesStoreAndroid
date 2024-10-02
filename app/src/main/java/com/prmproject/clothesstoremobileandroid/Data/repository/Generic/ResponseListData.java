package com.prmproject.clothesstoremobileandroid.Data.repository.Generic;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.prmproject.clothesstoremobileandroid.Data.model.dataToReceive.ErrorResponse;
import com.prmproject.clothesstoremobileandroid.Data.model.dataToReceive.ListResponse;

import java.util.List;

import retrofit2.Response;

public class ResponseListData<T> {
    public ResponseListData() {
    }

    public MutableLiveData<ListResponse<T>> getListData(Response<List<T>> response) {
        MutableLiveData<ListResponse<T>> tokenLiveData = new MutableLiveData<>();

        if (response.isSuccessful() && response.body() != null) {
            tokenLiveData.setValue(new ListResponse<>(response.body(), null, true));
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

            tokenLiveData.setValue(new ListResponse<>(null, errorMessage, false, response.code()));
        }

        return tokenLiveData;
    }
}
