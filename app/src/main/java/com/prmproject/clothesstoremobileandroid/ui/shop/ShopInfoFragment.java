package com.prmproject.clothesstoremobileandroid.ui.shop;

import android.os.Bundle;
import android.util.Log;
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

import com.prmproject.clothesstoremobileandroid.Data.model.Product;
import com.prmproject.clothesstoremobileandroid.R;
import com.prmproject.clothesstoremobileandroid.databinding.FragmentShopInfoBinding;
import com.prmproject.clothesstoremobileandroid.ui.Adapter.ProductAdapter;
import com.prmproject.clothesstoremobileandroid.ui.common.AutoScrollManager;

import java.util.List;

public class ShopInfoFragment extends Fragment {
    private FragmentShopInfoBinding binding;
    private ShopViewModel shopViewModel;
    private NavController navController;

    private RecyclerView otherProductRecyclerView;
    private RecyclerView recommendedProductRecyclerView;

    private ProductAdapter otherProductAdapter;
    private ProductAdapter recommendedProductAdapter;

    private AutoScrollManager productAutoScrollManager = new AutoScrollManager();

    private int sellerId;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentShopInfoBinding.inflate(inflater, container, false);
        shopViewModel = new ViewModelProvider(this).get(ShopViewModel.class);
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        Bundle args = getArguments();
        if (args != null) {
            sellerId = args.getInt("shopId", -1);
        }

        setupRecyclerViews();
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadProductsFromArguments();
    }

    private void setupRecyclerViews() {
        otherProductRecyclerView = binding.otherProductsRecyclerView;
        recommendedProductRecyclerView = binding.recommendedProductsRecyclerView;

        otherProductRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));
        recommendedProductRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));
    }

    private void loadProductsFromArguments() {
        shopViewModel.getListProduct("BestSeller", 1, null, sellerId, null, null, null, null, null).observe(getViewLifecycleOwner(), productListResponse -> {
            if (productListResponse != null && productListResponse.isSuccess()) {
                List<Product> productList = productListResponse.getItems();
                recommendedProductAdapter = new ProductAdapter(productList, this::onProductSelected);
                recommendedProductRecyclerView.setAdapter(recommendedProductAdapter);
                productAutoScrollManager.startAutoScroll(recommendedProductRecyclerView, recommendedProductAdapter, 5000);
            }
        });
    }

    private void loadProductsByCategory(String filter, Integer categoryId, String name, Integer priceFrom, Integer priceTo, int[] listOptionId, int[] listCategoryId) {

    }

    private void onProductSelected(Product product) {
        Bundle args = new Bundle();
        args.putInt("productId", product.getProductId());
        navController.navigate(R.id.navigation_product_detail, args);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("ShopInfoFragment", "Shop Info Fragment destroyed");
        binding = null;
        productAutoScrollManager.stopAutoScroll();
    }

    @Override
    public void onPause() {
        super.onPause();
        productAutoScrollManager.stopAutoScroll();
    }
}