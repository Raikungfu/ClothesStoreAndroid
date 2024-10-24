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

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.prmproject.clothesstoremobileandroid.Data.model.Product;
import com.prmproject.clothesstoremobileandroid.Data.model.Seller;
import com.prmproject.clothesstoremobileandroid.R;
import com.prmproject.clothesstoremobileandroid.databinding.FragmentShopInfoBinding;
import com.prmproject.clothesstoremobileandroid.ui.Adapter.ProductAdapter;
import com.prmproject.clothesstoremobileandroid.ui.common.AutoScrollManager;

import java.util.List;

public class ShopInfoFragment extends Fragment  implements OnMapReadyCallback {
    private FragmentShopInfoBinding binding;
    private ShopViewModel shopViewModel;
    private NavController navController;

    private RecyclerView otherProductRecyclerView;
    private RecyclerView recommendedProductRecyclerView;

    private ProductAdapter otherProductAdapter;
    private ProductAdapter recommendedProductAdapter;

    private AutoScrollManager productAutoScrollManager = new AutoScrollManager();

    private int sellerId;

    private MapView mapView;
    private GoogleMap googleMap;

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

        mapView = binding.mapView;
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this); // Get the map asynchronously
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
        loadInfoShop();
        loadProductsFromArguments();
    }

    private void setupRecyclerViews() {
//        otherProductRecyclerView = binding.otherProductsRecyclerView;
        recommendedProductRecyclerView = binding.recommendedProductsRecyclerView;

//        otherProductRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));
        recommendedProductRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));
    }

    private void loadInfoShop(){
        shopViewModel.getShopDetail(sellerId).observe(getViewLifecycleOwner(), shopDetailResponse -> {
            if (shopDetailResponse != null && shopDetailResponse.isSuccess()) {
                Seller seller = (Seller) shopDetailResponse.getItem();

                if(seller != null){
                    Glide.with(this).load(seller.getAvt()).placeholder(R.drawable.logor).error(R.drawable.logor).into(binding.shopImage);
                    binding.shopName.setText(seller.getCompanyName());
                    binding.shopDescription.setText(seller.getDescription());

                    double latitude = seller.getLatitude();
                    double longitude = seller.getLongitude();

                    if (googleMap != null) {
                        googleMap.clear();
                        LatLng shopLocation = new LatLng(latitude, longitude);
                        googleMap.addMarker(new MarkerOptions().position(shopLocation).title(seller.getCompanyName()));

                        // Di chuyển camera tới vị trí cửa hàng với mức zoom 15
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(shopLocation, 15f));
                    }
                }
            }
        });

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
        mapView.onPause();
        productAutoScrollManager.stopAutoScroll();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;

        // Đặt vị trí mặc định (ví dụ tọa độ cửa hàng)
        LatLng shopLocation = new LatLng(10.762622, 106.660172); // Đặt tọa độ cửa hàng tại đây
        googleMap.addMarker(new MarkerOptions().position(shopLocation).title("Shop Location"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(shopLocation, 15f));
    }
}