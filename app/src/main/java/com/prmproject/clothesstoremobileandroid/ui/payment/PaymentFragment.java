package com.prmproject.clothesstoremobileandroid.ui.payment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.prmproject.clothesstoremobileandroid.BuildConfig;
import com.prmproject.clothesstoremobileandroid.Data.repository.PaypalPayment.OrderCaptureListener;
import com.prmproject.clothesstoremobileandroid.Data.repository.PaypalPayment.OrderIDListener;
import com.prmproject.clothesstoremobileandroid.MainActivity;
import com.prmproject.clothesstoremobileandroid.databinding.FragmentPaymentBinding;

import org.json.JSONObject;

public class PaymentFragment extends Fragment {
    private FragmentPaymentBinding binding;
    private float amount = 5.00f;
    private PaymentPaypal paymentPaypal;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        amount = getArguments() != null ? getArguments().getFloat("totalPayment") : -1;
        paymentPaypal = new PaymentPaypal(requireActivity());
        paymentPaypal.setAmount(amount);
        paymentPaypal.setReturnUrl(BuildConfig.URL_PAYPAL_RETURN);

        binding = FragmentPaymentBinding.inflate(inflater, container, false);

        if(amount > 0) {
            paymentPaypal.fetchAccessToken(success -> {
                if (success) {
                    binding.btnPaypalPayment.setVisibility(View.VISIBLE);
                }
            });

            binding.btnPaypalPayment.setVisibility(View.GONE);
            binding.btnPaypalPayment.setOnClickListener(v ->  paymentPaypal.startOrder(new OrderIDListener() {
                @Override
                public void onOrderCreated(String orderId) {
                    Log.d("MainActivity", "Order Created Successfully: " + orderId);
                    paymentPaypal.captureOrder(orderId, new OrderCaptureListener() {
                        @Override
                        public void onSuccess(JSONObject response) {
                            Log.d("PaymentFragment", "Capture Response: " + response.toString());
                            ((MainActivity) requireActivity()).onMessage("Payment Successful");
                        }

                        @Override
                        public void onError(String error) {
                            Log.e("PaymentFragment", "Capture Error: " + error);
                            ((MainActivity) requireActivity()).onMessage("Payment failed: " + error);
                        }
                    });
                }

                @Override
                public void onError(String error) {
                    Log.e("MainActivity", "Order Creation Error: " + error);
                }
            }));
        }else{
            binding.btnPaypalPayment.setVisibility(View.GONE);
            binding.btnDirectPayment.setVisibility(View.GONE);
            binding.btnDebitCardPayment.setVisibility(View.GONE);
        }

        return binding.getRoot();
    }
}
