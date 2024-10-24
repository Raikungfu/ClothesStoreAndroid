package com.prmproject.clothesstoremobileandroid.ui.payment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
import com.prmproject.clothesstoremobileandroid.MainActivity;
import com.prmproject.clothesstoremobileandroid.databinding.FragmentPaymentBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.UUID;

public class PaymentFragment extends Fragment {
    private FragmentPaymentBinding binding;

    private final String clientID = BuildConfig.CONFIG_CLIENT_ID;
    private final String returnUrl = BuildConfig.URL_PAYPAL_RETURN;
    private String accessToken = "";
    private String uniqueId;
    private float amount = 5.00f;
    private PaymentViewModel paymentViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        paymentViewModel = new ViewModelProvider(this).get(PaymentViewModel.class);

        amount = getArguments() != null ? getArguments().getFloat("totalPayment") : -1;


        binding = FragmentPaymentBinding.inflate(inflater, container, false);
        AndroidNetworking.initialize(getContext());

        if(amount > 0) {
            fetchAccessToken();
            binding.btnPaypalPayment.setVisibility(View.GONE);
            binding.btnPaypalPayment.setOnClickListener(v -> startOrder());
        }else{
            binding.btnPaypalPayment.setVisibility(View.GONE);
            binding.btnDirectPayment.setVisibility(View.GONE);
            binding.btnDebitCardPayment.setVisibility(View.GONE);
        }

        return binding.getRoot();
    }

    private void handlerOrderID(String orderID) {
        CoreConfig config = new CoreConfig(clientID, Environment.SANDBOX);
        PayPalWebCheckoutClient payPalWebCheckoutClient = new PayPalWebCheckoutClient(getActivity(), config, returnUrl);
        payPalWebCheckoutClient.setListener(new PayPalWebCheckoutListener() {
            @Override
            public void onPayPalWebSuccess(@NonNull PayPalWebCheckoutResult result) {
                captureOrder(orderID);
                Log.d("PaymentFragment", "onPayPalWebSuccess: " + result.toString());
            }

            @Override
            public void onPayPalWebFailure(@NonNull PayPalSDKError error) {
                Log.d("PaymentFragment", "onPayPalWebFailure: " + error.toString());
            }

            @Override
            public void onPayPalWebCanceled() {
                captureOrder(orderID);
                Log.d("PaymentFragment", "onPayPalWebCanceled");
            }
        });

        PayPalWebCheckoutRequest payPalWebCheckoutRequest = new PayPalWebCheckoutRequest(orderID, PayPalWebCheckoutFundingSource.PAYPAL);
        payPalWebCheckoutClient.start(payPalWebCheckoutRequest);
    }

    private void startOrder() {
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
                        handlerOrderID(orderId);
                    }

                    @Override
                    public void onError(ANError error) {
                        Log.d("PaymentFragment", "Order Error: " + error.getMessage());
                    }
                });
    }

    private void fetchAccessToken() {
        paymentViewModel.getClientToken().observe(getViewLifecycleOwner(), response -> {
            if (response != null &&  response.getClientToken() != null) {
                accessToken = response.getClientToken();
                Log.d("PaymentFragment", accessToken);

                Toast.makeText(getContext(), "Access Token Fetched!", Toast.LENGTH_SHORT).show();
                binding.btnPaypalPayment.setVisibility(View.VISIBLE);
            } else {
                ((MainActivity) getActivity()).onMessage("Fetch Access Token failed! ");
            }
        });
    }

    public void captureOrder(String orderID) {
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
                        Toast.makeText(getContext(), "Payment Successful", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(ANError error) {
                        Log.e("PaymentFragment", "Capture Error: " + error.getErrorDetail());
                    }
                });
    }
}
