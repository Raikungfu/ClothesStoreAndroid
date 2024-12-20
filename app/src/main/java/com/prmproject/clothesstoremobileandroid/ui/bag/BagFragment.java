package com.prmproject.clothesstoremobileandroid.ui.bag;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.prmproject.Network.Service.PaymentPaypalService;
import com.prmproject.Network.Service.PaymentVNPayService;
import com.prmproject.clothesstoremobileandroid.BuildConfig;
import com.prmproject.clothesstoremobileandroid.Data.model.CartItem;
import com.prmproject.clothesstoremobileandroid.Data.model.Order;
import com.prmproject.clothesstoremobileandroid.Data.model.dataToSend.OrderCreateViewModel;
import com.prmproject.clothesstoremobileandroid.Data.model.dataToSend.VnPaymentRequestModel;
import com.prmproject.clothesstoremobileandroid.Data.model.type.VnPayMethod;
import com.prmproject.clothesstoremobileandroid.Data.repository.OrderCallback;
import com.prmproject.clothesstoremobileandroid.Data.repository.PaypalPayment.OrderCaptureListener;
import com.prmproject.clothesstoremobileandroid.Data.repository.PaypalPayment.OrderIDListener;
import com.prmproject.clothesstoremobileandroid.MainActivity;
import com.prmproject.clothesstoremobileandroid.R;
import com.prmproject.clothesstoremobileandroid.databinding.FragmentBagBinding;
import com.prmproject.clothesstoremobileandroid.ui.Adapter.CartAdapter;

import org.json.JSONObject;

import java.util.List;

public class BagFragment extends Fragment  implements CartAdapter.OnQuantityChangeListener {
    private FragmentBagBinding binding;
    private BagViewModel cartViewModel;
    private RecyclerView cartRecyclerView;
    private CartAdapter cartAdapter;
    private List<CartItem> cartItems;
    private PaymentPaypalService paymentPaypal;
    private NavController navController;
    private PaymentVNPayService paymentVNPayService;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBagBinding.inflate(inflater, container, false);
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        paymentPaypal = new PaymentPaypalService(requireActivity());
        paymentPaypal.setReturnUrl(BuildConfig.URL_PAYPAL_RETURN);
        paymentVNPayService = new PaymentVNPayService(requireContext());

        cartViewModel = new ViewModelProvider(this).get(BagViewModel.class);

        setupRecyclerView();
        loadCartItems();

        binding.paymentMethodChangeBtn.setOnClickListener(v -> {
            if (binding.cardPaymentView.getVisibility() == View.GONE) {
                binding.cardPaymentView.setVisibility(View.VISIBLE);
                binding.cardPaymentView.animate().translationY(0).setDuration(300);
            } else {
                binding.cardPaymentView.animate().translationY(binding.cardPaymentView.getHeight()).setDuration(300).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        binding.cardPaymentView.setVisibility(View.GONE);
                    }
                });
            }
        });
        binding.radioGroupPaymentMethods.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton selectedRadioButton = binding.getRoot().findViewById(checkedId);
            String paymentMethod = selectedRadioButton.getText().toString();
            binding.paymentMethod.setText(paymentMethod);
        });

        binding.btnCheckout.setOnClickListener(v -> {
            String discountCode = binding.discountCode.getText().toString().trim();
            String paymentMethod=binding.paymentMethod.getText().toString().trim();
            createOrder(discountCode,paymentMethod);
        });

        return binding.getRoot();
    }

    private void setupRecyclerView() {
        cartRecyclerView = binding.recyclerViewCart;
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false));
    }

    private void loadCartItems() {
        cartViewModel.getCartItems().observe(getViewLifecycleOwner(), cartListResponse -> {
            if (cartListResponse != null && cartListResponse.isSuccess()) {
                cartItems = cartListResponse.getItems();
                cartAdapter = new CartAdapter(cartItems,this);
                cartRecyclerView.setAdapter(cartAdapter);
                binding.textViewNoCarts.setVisibility(cartItems.isEmpty() ? View.VISIBLE : View.GONE);

                updateTotalAmount();

            } else if (!cartListResponse.isSuccess() && cartListResponse.getCodeError() == 401) {
                ((MainActivity) requireActivity()).onMessage("Login failed! " + cartListResponse.getErrorMessage());
            } else if (!cartListResponse.isSuccess()) {
                ((MainActivity) requireActivity()).onMessage("Load data failed! " + cartListResponse.getErrorMessage());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onQuantityChanged(int total) {
        binding.txtTotal.setText("$" + total);
    }

    private void updateTotalAmount() {
        binding.txtTotal.setText("$" + getTotalAmount());
    }

    private int getTotalAmount() {
        int total = 0;
        for (CartItem item : cartItems) {
            total += item.getQuantity() * item.getProduct().getNewPrice();
        }
        return total;
    }

    private void createOrder(String discountCode,String paymentMethod) {
        OrderCreateViewModel orderCreateViewModel = new OrderCreateViewModel();
        orderCreateViewModel.setDiscountCode(discountCode);
        orderCreateViewModel.setPaymentMethod(paymentMethod);

        createOrder(orderCreateViewModel, orderResponse -> {
            if (orderResponse != null) {
                if(paymentMethod.equals("Cash")){
                    navController.navigate(R.id.navigation_myorder);
                } else if(paymentMethod.equals("Paypal")){
                    paymentPaypal.fetchAccessToken(success -> {
                        if (success) {
                            paymentPaypal.startOrder(new OrderIDListener() {
                                @Override
                                public void onOrderCreated(String orderId) {
                                    Log.d("MainActivity", "Order Created Successfully: " + orderId);
                                    paymentPaypal.captureOrder(orderId, new OrderCaptureListener() {
                                        @Override
                                        public void onSuccess(JSONObject response) {
                                            Log.d("PaymentFragment", "Capture Response: " + response.toString());
                                            cartViewModel.updateStatus(orderResponse.getOrderId(), "completed").observe(getViewLifecycleOwner(), objectResponse -> {
                                                if (objectResponse.isSuccess()) {
                                                    ((MainActivity) requireActivity()).onMessage("Payment Successful");
                                                    navController.navigate(R.id.navigation_myorder);
                                                } else {
                                                    ((MainActivity) requireActivity()).onMessage("Failed to update status: " + objectResponse.getErrorMessage());
                                                }
                                            });

                                        }

                                        @Override
                                        public void onError(String error) {
                                            Log.e("PaymentFragment", "Capture Error: " + error);
                                            ((MainActivity) requireActivity()).onMessage("Payment failed: " + error);
                                            return;
                                        }
                                    });
                                }

                                @Override
                                public void onError(String error) {
                                    Log.e("MainActivity", "Order Creation Error: " + error);
                                    return;
                                }
                            }, orderResponse.getOrderId(), orderResponse.getTotalAmount());
                        }else{
                            ((MainActivity) requireActivity()).onMessage("Fetch Access Token failed!");
                            return;
                        }
                    });
                } else if (paymentMethod.equals("ATM Card")){
                    paymentVNPayService.createPayment(new VnPaymentRequestModel(orderResponse.getOrderId(), orderResponse.getTotalAmount() * 25369, VnPayMethod.ATM.ordinal()));
                } else if (paymentMethod.equals("Visa/Mastercard")){
                    paymentVNPayService.createPayment(new VnPaymentRequestModel(orderResponse.getOrderId(), orderResponse.getTotalAmount() * 25369, VnPayMethod.CreditCard.ordinal()));
                }
            } else {
                ((MainActivity) requireActivity()).onMessage("Order creation failed: response is null.");
            }
        });


    }

    public void createOrder(OrderCreateViewModel orderCreateViewModel, OrderCallback callback) {
        cartViewModel.CreateOrder(orderCreateViewModel).observe(getViewLifecycleOwner(), orderResponse -> {
            if (orderResponse != null) {
                callback.onOrderCreated((Order) orderResponse.getItem());

                if (orderResponse.isSuccess()) {
                    ((MainActivity) requireActivity()).onMessage("Order created successfully!");
                } else {
                    ((MainActivity) requireActivity()).onMessage("Order creation failed: " + orderResponse.getErrorMessage());
                }
            } else {
                callback.onOrderCreated(null);
                ((MainActivity) requireActivity()).onMessage("Order creation failed: response is null.");
            }
        });
    }

}

