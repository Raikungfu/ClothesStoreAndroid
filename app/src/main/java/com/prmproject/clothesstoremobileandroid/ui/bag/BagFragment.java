package com.prmproject.clothesstoremobileandroid.ui.bag;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.prmproject.clothesstoremobileandroid.MainActivity;
import com.prmproject.clothesstoremobileandroid.databinding.FragmentBagBinding;
import com.prmproject.clothesstoremobileandroid.ui.Adapter.CartAdapter;
import com.prmproject.clothesstoremobileandroid.Data.model.CartItem;

import java.util.List;

public class BagFragment extends Fragment  implements CartAdapter.OnQuantityChangeListener {
    private FragmentBagBinding binding;
    private BagViewModel cartViewModel;
    private RecyclerView cartRecyclerView;
    private CartAdapter cartAdapter;
    private List<CartItem> cartItems;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBagBinding.inflate(inflater, container, false);

        cartViewModel = new ViewModelProvider(this).get(BagViewModel.class);

        setupRecyclerView();
        loadCartItems();

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
                binding.textViewNoOrders.setVisibility(cartItems.isEmpty() ? View.VISIBLE : View.GONE);

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
        int total = 0;
        for (CartItem item : cartItems) {
            total += item.getQuantity() * item.getProduct().getOldPrice();
        }
        binding.txtTotal.setText("$" + total);
    }

}
