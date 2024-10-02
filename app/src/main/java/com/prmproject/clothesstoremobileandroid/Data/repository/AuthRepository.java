package com.prmproject.clothesstoremobileandroid.Data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.prmproject.Network.RetrofitClient;
import com.prmproject.clothesstoremobileandroid.Data.model.dataToReceive.ErrorResponse;
import com.prmproject.clothesstoremobileandroid.Data.model.dataToReceive.MessageResponse;
import com.prmproject.clothesstoremobileandroid.Data.model.dataToSend.UserForgotPassword;
import com.prmproject.clothesstoremobileandroid.Data.model.dataToSend.UserLogin;
import com.prmproject.clothesstoremobileandroid.Data.model.dataToReceive.TokenResponse;
import com.prmproject.clothesstoremobileandroid.Data.model.dataToSend.UserRegister;
import com.prmproject.clothesstoremobileandroid.Data.remote.AuthApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthRepository {
    private AuthApiService apiService;

    public AuthRepository() {
        apiService = RetrofitClient.getInstance().create(AuthApiService.class);
    }

    public LiveData<TokenResponse> login(UserLogin user) {
        MutableLiveData<TokenResponse> tokenLiveData = new MutableLiveData<>();
        apiService.login(user).enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                tokenLiveData.postValue(responseToken(response).getValue());
                RetrofitClient.updateToken(responseToken(response).getValue().getToken());
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                tokenLiveData.setValue(new TokenResponse("", t.getMessage(), false));
            }
        });
        return tokenLiveData;
    }

    public LiveData<MessageResponse> register(UserRegister user) {
        MutableLiveData<MessageResponse> messageLiveData = new MutableLiveData<>();
        apiService.register(user).enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                messageLiveData.postValue(responseMessage(response).getValue());
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                messageLiveData.setValue(new MessageResponse(t.getMessage(), false));
            }
        });
        return messageLiveData;
    }

    public LiveData<TokenResponse> sendOtpToEmail(UserForgotPassword user) {
        MutableLiveData<TokenResponse> tokenLiveData = new MutableLiveData<>();
        apiService.sendOtpToEmail(user).enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                tokenLiveData.postValue(responseToken(response).getValue());
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                tokenLiveData.setValue(new TokenResponse("", t.getMessage(), false));
            }
        });
        return tokenLiveData;
    }

    public LiveData<MessageResponse> resetPassword(UserForgotPassword user, String token) {
        RetrofitClient.updateToken(token);
        MutableLiveData<MessageResponse> messageLiveData = new MutableLiveData<>();
        apiService.resetPassword(user).enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                messageLiveData.postValue(responseMessage(response).getValue());
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                messageLiveData.setValue(new MessageResponse(t.getMessage(), false));
            }
        });
        return messageLiveData;
    }


    public LiveData<TokenResponse> verifyOtp(UserForgotPassword user, String token) {
        RetrofitClient.updateToken(token);
        MutableLiveData<TokenResponse> tokenLiveData = new MutableLiveData<>();
        apiService.verifyOtp(user).enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                tokenLiveData.postValue(responseToken(response).getValue());
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                tokenLiveData.setValue(new TokenResponse("", t.getMessage(), false));
            }
        });
        return tokenLiveData;
    }

    private MutableLiveData<TokenResponse> responseToken(Response<TokenResponse> response){
        MutableLiveData<TokenResponse> tokenLiveData = new MutableLiveData<>();
        if (response.isSuccessful() && response.body() != null) {
            tokenLiveData.setValue(new TokenResponse(response.body().getToken(), response.body().getMessage(), true));
        } else {
            if (response.errorBody() != null) {
                try {
                    ErrorResponse apiError = new Gson().fromJson(response.errorBody().charStream(), ErrorResponse.class);
                    apiError.convertErrorToString();
                    tokenLiveData.setValue(new TokenResponse(null, apiError.getErrorMessages().toString(), false));
                } catch (Exception e) {
                    tokenLiveData.setValue(new TokenResponse(null, "Error parsing error response.", false));
                }
            } else {
                tokenLiveData.setValue(new TokenResponse(null, "Unknown error occurred.", false));
            }
        }
        return tokenLiveData;
    }

    private MutableLiveData<MessageResponse> responseMessage(Response<MessageResponse> response){
        MutableLiveData<MessageResponse> messageLiveData = new MutableLiveData<>();
        if (response.isSuccessful() && response.body() != null) {
            messageLiveData.setValue(new MessageResponse(response.body().getMessage(), true));
        } else {
            if (response.errorBody() != null) {
                try {
                    ErrorResponse apiError = new Gson().fromJson(response.errorBody().charStream(), ErrorResponse.class);
                    apiError.convertErrorToString();
                    messageLiveData.setValue(new MessageResponse(apiError.getErrorMessages().toString(), false));
                } catch (Exception e) {
                    messageLiveData.setValue(new MessageResponse("Error parsing error response.", false));
                }
            } else {
                messageLiveData.setValue(new MessageResponse("Unknown error occurred.", false));
            }
        }
        return messageLiveData;
    }
}
