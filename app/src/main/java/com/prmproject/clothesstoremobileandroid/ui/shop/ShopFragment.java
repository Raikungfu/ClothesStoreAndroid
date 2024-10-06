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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.prmproject.clothesstoremobileandroid.Data.model.Category;
import com.prmproject.clothesstoremobileandroid.Data.model.Product;
import com.prmproject.clothesstoremobileandroid.R;
import com.prmproject.clothesstoremobileandroid.databinding.FragmentShopBinding;
import com.prmproject.clothesstoremobileandroid.ui.Adapter.CategoryAdapter;
import com.prmproject.clothesstoremobileandroid.ui.Adapter.ProductAdapter;
import com.prmproject.clothesstoremobileandroid.ui.common.AutoScrollManager;

import java.util.List;

public class ShopFragment extends Fragment {
    private FragmentShopBinding binding;
    private ShopViewModel shopViewModel;
    private NavController navController;

    private RecyclerView categoryRecyclerView;
    private RecyclerView productRecyclerView;

    private CategoryAdapter categoryAdapter;
    private ProductAdapter productAdapter;

    private AutoScrollManager categoryAutoScrollManager = new AutoScrollManager();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentShopBinding.inflate(inflater, container, false);
        shopViewModel = new ViewModelProvider(this).get(ShopViewModel.class);
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);

        setupRecyclerViews();

        shopViewModel.getListCategory().observe(getViewLifecycleOwner(), categoryListResponse -> {
            if (categoryListResponse != null && categoryListResponse.isSuccess()) {
                List<Category> categoryList = categoryListResponse.getItems();
                categoryAdapter = new CategoryAdapter(categoryList, this::onCategorySelected);
                categoryRecyclerView.setAdapter(categoryAdapter);
                categoryAutoScrollManager.startAutoScroll(categoryRecyclerView, categoryAdapter, 5000);
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadProductsFromArguments();
    }

    private void setupRecyclerViews() {
        categoryRecyclerView = binding.categoryRecyclerView;
        productRecyclerView = binding.productRecyclerView;

        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));
        productRecyclerView.setLayoutManager(new GridLayoutManager(requireActivity(), 2));
    }

    private void loadProductsFromArguments() {
        Bundle args = getArguments();
        if (args != null) {
            int categoryId = args.getInt("categoryId", -1);
            int priceFrom = args.getInt("priceFrom", -1);
            int priceTo = args.getInt("priceTo", -1);
            Log.d("PriceCheck", "Price From: " + priceFrom + ", Price To: " + priceTo);

            String name = args.getString("name", "");
            int[] listOptionId = args.getIntArray("listOptionId");
            int[] listCategoryId = args.getIntArray("listCategoryId");
            if (!name.isEmpty() || (listOptionId != null && listOptionId.length > 0) || (listCategoryId != null && listCategoryId.length > 0) || priceFrom >= 0 || priceTo >= 0) {
                loadProductsByCategory("BestSeller", categoryId == -1 ? null : categoryId, name, priceFrom == -1 ? null : priceFrom, priceTo == -1 ? null : priceTo, listOptionId, listCategoryId);
            } else {
                loadProductsByCategory("BestSeller", categoryId == -1 ? null : categoryId, null, null, null, null, null);
            }
            Log.d("ShopFragment", "Loaded products for categoryId: " + categoryId);
        } else {
            loadProductsByCategory("BestSeller", null, null, null, null, null, null);
        }
    }

    private void onCategorySelected(Category category) {
        loadProductsByCategory("BestSeller", category.getCategoryId(), null, null, null, null, null);
    }

    private void loadProductsByCategory(String filter, Integer categoryId, String name, Integer priceFrom, Integer priceTo, int[] listOptionId, int[] listCategoryId) {
        shopViewModel.getListProduct(filter, 1, categoryId, null, name, priceFrom, priceTo, listOptionId, listCategoryId).observe(getViewLifecycleOwner(), productListResponse -> {
            if (productListResponse != null && productListResponse.isSuccess()) {
                List<Product> productList = productListResponse.getItems();
                productAdapter = new ProductAdapter(productList, this::onProductSelected);
                productRecyclerView.setAdapter(productAdapter);
            }
        });
    }

    private void onProductSelected(Product product) {
        Bundle args = new Bundle();
        args.putInt("productId", product.getProductId());
        navController.navigate(R.id.navigation_product_detail, args);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("ShopFragment", "ShopFragment destroyed");
        binding = null;
        categoryAutoScrollManager.stopAutoScroll();
    }

    @Override
    public void onPause() {
        super.onPause();
        categoryAutoScrollManager.stopAutoScroll();
    }
}
