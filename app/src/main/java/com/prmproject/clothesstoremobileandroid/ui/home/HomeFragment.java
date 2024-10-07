package com.prmproject.clothesstoremobileandroid.ui.home;

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

import com.prmproject.clothesstoremobileandroid.Data.model.Category;
import com.prmproject.clothesstoremobileandroid.Data.model.Product;
import com.prmproject.clothesstoremobileandroid.Data.model.dataToReceive.ListResponse;
import com.prmproject.clothesstoremobileandroid.MainActivity;
import com.prmproject.clothesstoremobileandroid.R;
import com.prmproject.clothesstoremobileandroid.databinding.FragmentHomeBinding;
import com.prmproject.clothesstoremobileandroid.ui.Adapter.CategoryAdapter;
import com.prmproject.clothesstoremobileandroid.ui.Adapter.ProductAdapter;
import com.prmproject.clothesstoremobileandroid.ui.common.AutoScrollManager;

import java.util.List;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private HomeViewModel homeViewModel;

    private CategoryAdapter categoryAdapter;
    private List<Category> categoryList;
    private RecyclerView categoryRecyclerView;

    private ProductAdapter productAdapter, productAdapter2, productAdapter3;
    private List<Product> productList, productList2, productList3;
    private RecyclerView productRecyclerView, productRecyclerView2, productRecyclerView3;

    private AutoScrollManager bestSellerAutoScrollManager = new AutoScrollManager();
    private AutoScrollManager newestAutoScrollManager = new AutoScrollManager();
    private AutoScrollManager saleOffAutoScrollManager = new AutoScrollManager();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        categoryRecyclerView = binding.categoryRecyclerView;

        homeViewModel.getListCategory().observe(getViewLifecycleOwner(), categoryListResponse -> {
            if (categoryListResponse != null && categoryListResponse.isSuccess()) {
                categoryList = categoryListResponse.getItems();
                categoryAdapter = new CategoryAdapter(categoryList, this::onCategorySelected);
                categoryRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));
                categoryRecyclerView.setAdapter(categoryAdapter);
            }
        });

        productRecyclerView = binding.bestSellerProductsRecyclerView;
        productRecyclerView2 = binding.newestProductsRecyclerView;
        productRecyclerView3 = binding.saleOffProductsRecyclerView;

        homeViewModel.getListBestSellerProduct(null, null).observe(getViewLifecycleOwner(), productListResponse -> {
            setupRecyclerView(productRecyclerView, productList, productAdapter, productListResponse, bestSellerAutoScrollManager);
        });

        homeViewModel.getListNewestProduct(null, null).observe(getViewLifecycleOwner(), productListResponse -> {
            setupRecyclerView(productRecyclerView2, productList2, productAdapter2, productListResponse, newestAutoScrollManager);
        });

        homeViewModel.getListSaleOffProduct(null, null).observe(getViewLifecycleOwner(), productListResponse -> {
            setupRecyclerView(productRecyclerView3, productList3, productAdapter3, productListResponse, saleOffAutoScrollManager);
        });

        return binding.getRoot();
    }

    private void onCategorySelected(Category category) {
        navigateToShopFragment(category.getCategoryId());
    }

    private void navigateToShopFragment(int categoryId) {
        Bundle args = new Bundle();
        args.putInt("categoryId", categoryId);
        args.putInt("priceFrom", -1);
        args.putInt("priceTo", -1);

        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.navigation_shop, args);
    }

    private void setupRecyclerView(RecyclerView recyclerView, List<Product> productList, ProductAdapter productAdapter, ListResponse<Product> productListResponse, AutoScrollManager autoScrollManager) {
        if (productListResponse != null && productListResponse.isSuccess()) {
            productList = productListResponse.getItems();
            productAdapter = new ProductAdapter(productList, this::onProductSelected);
            recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));
            recyclerView.setAdapter(productAdapter);

            autoScrollManager.startAutoScroll(recyclerView, productAdapter, 5000);
        } else if (productListResponse != null) {
            ((MainActivity) getActivity()).onMessage(productListResponse.getErrorMessage());
        } else {
            Log.e("HomeFragment", "ListResponse is null");
        }
    }

    private void onProductSelected(Product product) {
        Bundle args = new Bundle();
        args.putInt("productId", product.getProductId());

        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.navigation_product_detail, args);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("HomeFragment", "HomeFragment destroyed");
        binding = null;
        bestSellerAutoScrollManager.stopAutoScroll();
        newestAutoScrollManager.stopAutoScroll();
        saleOffAutoScrollManager.stopAutoScroll();
    }

    @Override
    public void onPause() {
        super.onPause();
        bestSellerAutoScrollManager.stopAutoScroll();
        newestAutoScrollManager.stopAutoScroll();
        saleOffAutoScrollManager.stopAutoScroll();
    }
}
