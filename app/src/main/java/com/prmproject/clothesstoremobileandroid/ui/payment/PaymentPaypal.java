package com.prmproject.clothesstoremobileandroid.ui.payment;


import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.paypal.android.corepayments.CoreConfig;
import com.paypal.android.corepayments.Environment;
import com.paypal.android.corepayments.PayPalSDKError;
import com.paypal.android.paypalwebpayments.PayPalWebCheckoutClient;
import com.paypal.android.paypalwebpayments.PayPalWebCheckoutFundingSource;
import com.paypal.android.paypalwebpayments.PayPalWebCheckoutListener;
import com.paypal.android.paypalwebpayments.PayPalWebCheckoutRequest;
import com.paypal.android.paypalwebpayments.PayPalWebCheckoutResult;
import com.prmproject.clothesstoremobileandroid.BuildConfig;
import com.prmproject.clothesstoremobileandroid.Data.repository.PaypalPayment.AccessTokenCallback;
import com.prmproject.clothesstoremobileandroid.Data.repository.PaypalPayment.OrderCaptureListener;
import com.prmproject.clothesstoremobileandroid.Data.repository.PaypalPayment.OrderIDListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.UUID;

public class PaymentPaypal {
    private final String clientID = BuildConfig.CONFIG_CLIENT_ID;
    private String returnUrl;
    private String accessToken = "";
    private String uniqueId;
    private float amount = 5.00f;
    private PaymentViewModel paymentViewModel;
    private FragmentActivity activity;

    public PaymentPaypal(FragmentActivity activity) {
        this.activity = activity;
        this.paymentViewModel = new ViewModelProvider(activity).get(PaymentViewModel.class);
        AndroidNetworking.initialize(activity.getApplicationContext());
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    private void handlerOrderID(String orderID, OrderIDListener listener) {
        CoreConfig config = new CoreConfig(clientID, Environment.SANDBOX);
        PayPalWebCheckoutClient payPalWebCheckoutClient = new PayPalWebCheckoutClient(activity, config, returnUrl);
        payPalWebCheckoutClient.setListener(new PayPalWebCheckoutListener() {
            @Override
            public void onPayPalWebSuccess(@NonNull PayPalWebCheckoutResult result) {
                if (listener != null) {
                    listener.onOrderCreated(orderID);
                }
                Log.d("PaymentFragment", "onPayPalWebSuccess: " + result.toString());
            }

            @Override
            public void onPayPalWebFailure(@NonNull PayPalSDKError error) {
                if (listener != null) {
                    listener.onError(error.getMessage());
                }
                Log.d("PaymentFragment", "onPayPalWebFailure: " + error.toString());
            }

            @Override
            public void onPayPalWebCanceled() {
                if (listener != null) {
                    listener.onOrderCreated(orderID);
                }
                Log.d("PaymentFragment", "onPayPalWebCanceled");
            }
        });

        PayPalWebCheckoutRequest payPalWebCheckoutRequest = new PayPalWebCheckoutRequest(orderID, PayPalWebCheckoutFundingSource.PAYPAL);
        payPalWebCheckoutClient.start(payPalWebCheckoutRequest);
    }
    public void startOrder(OrderIDListener listener) {
        uniqueId = UUID.randomUUID().toString();
        JSONObject orderRequestJson = new JSONObject();
        try {
            orderRequestJson.put("intent", "CAPTURE");
            orderRequestJson.put("purchase_units", new JSONArray().put(new JSONObject().put("reference_id", uniqueId)
                    .put("amount", new JSONObject().put("currency_code", "USD").put("value", amount))));
            orderRequestJson.put("payment_source", new JSONObject().put("paypal", new JSONObject().put("experience_context", new JSONObject()
                    .put("payment_method_preference", "IMMEDIATE_PAYMENT_REQUIRED")
                    .put("brand_name", "SH Developer")
                    .put("locale", "en-US")
                    .put("landing_page", "LOGIN")
                    .put("shipping_preference", "NO_SHIPPING")
                    .put("user_action", "PAY_NOW")
                    .put("return_url", returnUrl)
                    .put("cancel_url", "https://example.com/cancelUrl"))));

        } catch (Exception e) {
            e.printStackTrace();
        }

        AndroidNetworking.post("https://api-m.sandbox.paypal.com/v2/checkout/orders")
                .addHeaders("Authorization", "Bearer " + accessToken)
                .addHeaders("Content-Type", "application/json")
                .addHeaders("PayPal-Request-Id", uniqueId)
                .addJSONObjectBody(orderRequestJson)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("PaymentFragment", "Order Response: " + response.toString());
                        String orderId = response.optString("id");
                        handlerOrderID(orderId, listener);
                    }

                    @Override
                    public void onError(ANError error) {
                        Log.d("PaymentFragment", "Order Error: " + error.getMessage());
                        if (listener != null) {
                            listener.onError(error.getMessage());
                        }
                    }
                });
    }


    public void fetchAccessToken(AccessTokenCallback callback) {
        paymentViewModel.getClientToken().observe(activity, response -> {
            if (response != null && response.getClientToken() != null) {
                accessToken = response.getClientToken();
                Log.d("PaymentFragment", accessToken);

                Toast.makeText(activity.getApplicationContext(), "Access Token Fetched!", Toast.LENGTH_SHORT).show();
                callback.onAccessTokenFetched(true);
            } else {
                Toast.makeText(activity.getApplicationContext(), "Fetch Access Token failed!", Toast.LENGTH_SHORT).show();
                callback.onAccessTokenFetched(false);
            }
        });
    }

    public void captureOrder(String orderID, OrderCaptureListener listener) {
        AndroidNetworking.post("https://api-m.sandbox.paypal.com/v2/checkout/orders/" + orderID + "/capture")
                .addHeaders("Authorization", "Bearer " + accessToken)
                .addHeaders("Content-Type", "application/json")
                .addJSONObjectBody(new JSONObject())
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("PaymentFragment", "Capture Response: " + response.toString());
                        Toast.makeText(activity.getApplicationContext(), "Payment Successful", Toast.LENGTH_SHORT).show();

                        if (listener != null) {
                            listener.onSuccess(response);
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        Log.e("PaymentFragment", "Capture Error: " + error.getErrorDetail());

                        if (listener != null) {
                            listener.onError(error.getErrorDetail());
                        }
                    }
                });
    }

}
