/* package com.prmproject.clothesstoremobileandroid;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.braintreepayments.api.BraintreeClient;
import com.braintreepayments.api.DataCollector;
import com.braintreepayments.api.PayPalAccountNonce;
import com.braintreepayments.api.PayPalClient;
import com.braintreepayments.api.PayPalListener;
import com.prmproject.clothesstoremobileandroid.Data.repository.PaymentRepository;

import java.util.Random;

public class PaymentActivity extends AppCompatActivity implements PayPalListener {
    private static final String TAG = "PaymentActivity";
    private static final String AMOUNT = "10";
    private static final String CURRENCY = "USD";

    private BraintreeClient braintreeClient;
    private PayPalClient payPalClient;
    private DataCollector dataCollector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment); // Đảm bảo bạn đã set layout cho activity

        // Lấy thông tin từ intent
        String paymentSubmitted = getIntent().getStringExtra("payment_submitted");

        // Khởi tạo Braintree client
        braintreeClient = new BraintreeClient(this, new PaymentRepository());
        payPalClient = new PayPalClient(this, braintreeClient);
        payPalClient.setListener(this);

        dataCollector = new DataCollector(braintreeClient);

        // Tiến hành thanh toán (nếu cần)
        if (paymentSubmitted != null && paymentSubmitted.equals("true")) {
            startPayPalPayment();
        }
    }

    private void startPayPalPayment() {
        // Logic để bắt đầu thanh toán PayPal (nếu cần)
        // payPalClient.startPayPalPayment(AMOUNT, CURRENCY);
    }

    @Override
    public void onPayPalSuccess(@NonNull PayPalAccountNonce payPalAccountNonce) {
        String nonce = payPalAccountNonce.getString();
        collectDeviceData(nonce);
    }

    private void collectDeviceData(String nonce) {
        dataCollector.collectDeviceData(this, (deviceData, error) -> {
            if (error != null) {
                Log.e(TAG, "Device Data Collection Error: " + error.getMessage());
            } else {
                // Tạo ID đơn hàng ngẫu nhiên
                String orderId = generateOrderId();
                // Logic để gửi nonce và deviceData tới server (nếu cần)
                // sendPaymentDataToServer(nonce, deviceData, orderId);
            }
        });
    }

    private String generateOrderId() {
        Random random = new Random();
        return String.valueOf(Math.abs(random.nextInt())).substring(0, 4);
    }

    @Override
    public void onPayPalFailure(@NonNull Exception error) {
        Toast.makeText(this, "Payment failed: " + error.getMessage(), Toast.LENGTH_LONG).show();
        finish();
    }
}
*/