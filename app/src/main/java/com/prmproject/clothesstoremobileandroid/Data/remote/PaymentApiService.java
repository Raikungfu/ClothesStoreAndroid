package com.prmproject.clothesstoremobileandroid.Data.remote;

import com.prmproject.clothesstoremobileandroid.Data.model.dataToReceive.MessageResponse;
import com.prmproject.clothesstoremobileandroid.Data.model.dataToReceive.TokenResponse;
import com.prmproject.clothesstoremobileandroid.Data.model.dataToSend.CheckoutResponse;
import com.prmproject.clothesstoremobileandroid.Data.model.dataToSend.VnPaymentRequestModel;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PaymentApiService {

    @POST("api/Payments/get-access-token")
    Call<TokenResponse> getPaymentToken();

    @POST("api/Payments/create-order")
    Call<MessageResponse> processPayment(@Body HashMap<String, String> paymentData);

    @POST("api/Payments/create-vnpay-payment-link")
    Call<CheckoutResponse> createPaymentUrl(@Body VnPaymentRequestModel model);
}
