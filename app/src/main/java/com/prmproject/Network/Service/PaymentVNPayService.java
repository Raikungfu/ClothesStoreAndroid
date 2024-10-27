package com.prmproject.Network.Service;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.prmproject.Network.RetrofitClient;
import com.prmproject.clothesstoremobileandroid.Data.model.dataToSend.CheckoutResponse;
import com.prmproject.clothesstoremobileandroid.Data.model.dataToSend.VnPaymentRequestModel;
import com.prmproject.clothesstoremobileandroid.Data.remote.PaymentApiService;
import com.prmproject.clothesstoremobileandroid.PaymentWebViewActivity;

import retrofit2.Call;
import retrofit2.Callback;

public class PaymentVNPayService {
    private static final int REQUEST_CODE_PAYMENT = 222;
    private Context context;
    private PaymentApiService apiService;

    public PaymentVNPayService(Context context) {
        this.context = context;
        apiService = RetrofitClient.getInstance().create(PaymentApiService.class);
    }


    public void createPayment(VnPaymentRequestModel model) {
        Call<CheckoutResponse> call = apiService.createPaymentUrl(model);

        call.enqueue(new Callback<CheckoutResponse>() {
            @Override
            public void onResponse(Call<CheckoutResponse> call, retrofit2.Response<CheckoutResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String paymentUrl = response.body().getPaymentUrl();

                    Intent intent = new Intent(context, PaymentWebViewActivity.class);
                    intent.putExtra("paymentUrl", paymentUrl);
                    ((Activity) context).startActivityForResult(intent, REQUEST_CODE_PAYMENT);
                } else {
                    Toast.makeText(context, "Lỗi khi nhận URL thanh toán", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CheckoutResponse> call, Throwable t) {
                Toast.makeText(context, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
