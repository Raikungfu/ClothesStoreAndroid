package com.prmproject.clothesstoremobileandroid.Data.remote;

import com.prmproject.clothesstoremobileandroid.Data.model.dataToReceive.MessageResponse;
import com.prmproject.clothesstoremobileandroid.Data.model.dataToSend.UserForgotPassword;
import com.prmproject.clothesstoremobileandroid.Data.model.dataToSend.UserLogin;
import com.prmproject.clothesstoremobileandroid.Data.model.dataToReceive.TokenResponse;
import com.prmproject.clothesstoremobileandroid.Data.model.dataToSend.UserRegister;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApiService {
    @POST("api/Auth/login")
    Call<TokenResponse> login(@Body UserLogin user);

    @POST("api/Auth/register")
    Call<MessageResponse> register(@Body UserRegister user);

    @POST("api/Forgot/forgot-password")
    Call<TokenResponse> sendOtpToEmail(@Body UserForgotPassword user);

    @POST("api/Forgot/verify-otp")
    Call<TokenResponse> verifyOtp(@Body UserForgotPassword user);

    @POST("api/Forgot/reset-password")
    Call<MessageResponse> resetPassword(@Body UserForgotPassword user);
}
