package com.prmproject.clothesstoremobileandroid.Data.model.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.prmproject.clothesstoremobileandroid.Data.model.dataToReceive.MessageResponse;
import com.prmproject.clothesstoremobileandroid.Data.model.dataToReceive.TokenResponse;
import com.prmproject.clothesstoremobileandroid.Data.model.dataToSend.UserForgotPassword;
import com.prmproject.clothesstoremobileandroid.Data.model.dataToSend.UserLogin;
import com.prmproject.clothesstoremobileandroid.Data.model.dataToSend.UserRegister;
import com.prmproject.clothesstoremobileandroid.Data.repository.AuthRepository;

public class AuthViewModel extends ViewModel {
    private AuthRepository authRepository;

    public AuthViewModel() {
        authRepository = new AuthRepository();
    }

    public LiveData<TokenResponse> login(UserLogin user) {
        return authRepository.login(user);
    }

    public LiveData<MessageResponse> register(UserRegister user) {
        return authRepository.register(user);
    }

    public LiveData<TokenResponse> sendOtpToEmail(UserForgotPassword email) {
        return authRepository.sendOtpToEmail(email);
    }

    public LiveData<MessageResponse> resetPassword(UserForgotPassword email) {
        return authRepository.resetPassword(email);
    }

    public LiveData<TokenResponse> verifyOtp(UserForgotPassword email) {
        return authRepository.verifyOtp(email);
    }
}
