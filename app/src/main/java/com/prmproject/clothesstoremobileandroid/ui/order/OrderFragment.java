package com.prmproject.clothesstoremobileandroid.ui.order;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.prmproject.clothesstoremobileandroid.Data.model.Order;
import com.prmproject.clothesstoremobileandroid.MainActivity;
import com.prmproject.clothesstoremobileandroid.R;
import com.prmproject.clothesstoremobileandroid.databinding.FragmentOrderBinding;
import com.prmproject.clothesstoremobileandroid.ui.Adapter.OrderAdapter;

import java.util.List;

public class OrderFragment extends Fragment {
    private FragmentOrderBinding binding;
    private OrderViewModel orderViewModel;
    private RecyclerView orderRecyclerView;
    private OrderAdapter orderAdapter;
    private List<Order> orderItems;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentOrderBinding.inflate(inflater, container, false);

        orderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);

        setupRecyclerView();

        loadOrders();

        return binding.getRoot();
    }

    private void setupRecyclerView() {
        orderRecyclerView = binding.recyclerViewOrders;
        orderRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false));
    }

    private void loadOrders() {
        orderViewModel.getOrders().observe(getViewLifecycleOwner(), orderListResponse -> {
            if (orderListResponse != null && orderListResponse.isSuccess()) {
                orderItems = orderListResponse.getItems();
                orderAdapter = new OrderAdapter(orderItems);
                orderRecyclerView.setAdapter(orderAdapter);
            } else if (!orderListResponse.isSuccess() && orderListResponse.getCodeError() == 401) {
                ((MainActivity) requireActivity()).onMessage("Login failed! " + orderListResponse.getErrorMessage());
            } else if (!orderListResponse.isSuccess()) {
                ((MainActivity) requireActivity()).onMessage("Load data failed! " + orderListResponse.getErrorMessage());
            }
            else {
                binding.textViewNoOrders.setVisibility(View.VISIBLE);}
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
//package com.prmproject.clothesstoremobileandroid.ui.order;
//
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.annotation.NonNull;
//import androidx.fragment.app.Fragment;
//import androidx.lifecycle.ViewModelProvider;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.prmproject.Network.RetrofitClient;
//import com.prmproject.clothesstoremobileandroid.Data.model.Order;
//import com.prmproject.clothesstoremobileandroid.R;
//import com.prmproject.clothesstoremobileandroid.databinding.FragmentOrderBinding;
//import com.prmproject.clothesstoremobileandroid.ui.Adapter.OrderAdapter;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class OrderFragment extends Fragment {
//    private FragmentOrderBinding binding;
//    private OrderViewModel orderViewModel;
//    private RecyclerView orderRecyclerView;
//    private OrderAdapter orderAdapter;
//
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        binding = FragmentOrderBinding.inflate(inflater, container, false);
//        orderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);
//
//        setupRecyclerView();
//        loadOrdersFromArguments();
//
//        return binding.getRoot();
//    }
//
//    private void setupRecyclerView() {
//        orderRecyclerView = binding.recyclerViewOrders;
//        orderRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
//        orderAdapter = new OrderAdapter(new ArrayList<>());
//        orderRecyclerView.setAdapter(orderAdapter);
//    }
//
//    private void loadOrdersFromArguments() {
//        Bundle args = getArguments();
//        if (args != null) {
//            int userId = args.getInt("userId", -1);
//            if (userId != -1) {
//                // Set token before making the API call
//                setTokenFromPreferences();
//                loadOrdersByUserId(userId);
//            }
//        }
//    }
//
//    private void setTokenFromPreferences() {
//        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserSession", Context.MODE_PRIVATE);
//        String token = sharedPreferences.getString("TOKEN_KEY", null);
//        if (token != null) {
//            RetrofitClient.updateToken(token);
//        }
//    }
//
//    private void loadOrdersByUserId(int userId) {
//        if (isUserLoggedIn()) {
//            orderViewModel.getOrders(userId).observe(getViewLifecycleOwner(), orderListResponse -> {
//                if (orderListResponse != null && orderListResponse.isSuccess()) {
//                    List<Order> orderList = orderListResponse.getItems();
//                    orderAdapter.updateOrders(orderList);
//                } else {
//                    binding.textViewNoOrders.setVisibility(View.VISIBLE);
//                }
//            });
//        } else {
//            requireLogin();
//        }
//    }
//
//    private boolean isUserLoggedIn() {
//        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserSession", Context.MODE_PRIVATE);
//        String token = sharedPreferences.getString("TOKEN_KEY", null);
//        return token != null && !token.isEmpty();
//    }
//
//    private void requireLogin() {
//        // Navigate to login page if the user is not logged in
//        // navController.navigate(R.id.navigation_login_required);
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        binding = null;
//    }
//}
