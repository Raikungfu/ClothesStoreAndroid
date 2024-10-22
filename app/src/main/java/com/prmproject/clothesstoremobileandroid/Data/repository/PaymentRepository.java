package com.prmproject.clothesstoremobileandroid.Data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.prmproject.Network.RetrofitClient;
import com.prmproject.clothesstoremobileandroid.Data.model.dataToReceive.MessageResponse;
import com.prmproject.clothesstoremobileandroid.Data.model.dataToReceive.TokenResponse;
import com.prmproject.clothesstoremobileandroid.Data.remote.PaymentApiService;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentRepository {
    private PaymentApiService apiService;

    public PaymentRepository() {
        apiService = RetrofitClient.getInstance().create(PaymentApiService.class);
    }

    public LiveData<TokenResponse> getClientToken() {
        MutableLiveData<TokenResponse> tokenLiveData = new MutableLiveData<>();
        apiService.getPaymentToken().enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                tokenLiveData.postValue(response.body());
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                tokenLiveData.setValue(new TokenResponse("", t.getMessage(), false));
            }
        });
        return tokenLiveData;
    }

    public void sendPaymentNonce(String nonce, String amount, PaymentCallback callback) {
        HashMap<String, String> paymentData = new HashMap<>();
        paymentData.put("amount", amount);
        paymentData.put("paymentNonce", nonce);

        apiService.processPayment(paymentData).enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess("Payment successful");
                } else {
                    callback.onFailure(new Exception("Payment failed: " + (response.body() != null ? response.body().getMessage() : response.message())));
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                callback.onFailure(new Exception("Payment failed: " + t.getMessage()));
            }
        });
    }

    public interface PaymentCallback {
        void onSuccess(String message);
        void onFailure(Exception e);
    }
}