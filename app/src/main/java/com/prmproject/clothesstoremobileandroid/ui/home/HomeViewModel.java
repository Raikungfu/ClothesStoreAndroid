package com.prmproject.clothesstoremobileandroid.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.prmproject.clothesstoremobileandroid.Data.model.Category;
import com.prmproject.clothesstoremobileandroid.Data.model.dataToReceive.ListResponse;
import com.prmproject.clothesstoremobileandroid.Data.repository.CategoryRepository;
import com.prmproject.clothesstoremobileandroid.Data.repository.ProductRepository;

import java.util.List;

public class HomeViewModel extends ViewModel {

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    public HomeViewModel() {
        productRepository = new ProductRepository();
        categoryRepository = new CategoryRepository();
    }

    public LiveData<ListResponse> getListCategory() {
        return categoryRepository.getListCategory();
    }
    public LiveData<ListResponse> getListNewestProduct(Integer categoryId, Integer sellerId) {
        return productRepository.getListProduct("Newest", 1, categoryId, sellerId, null, null, null, null, null);
    }

    public LiveData<ListResponse> getListBestSellerProduct(Integer categoryId, Integer sellerId) {
        return productRepository.getListProduct("BestSeller", 1, categoryId, sellerId, null, null, null, null, null);
    }

    public LiveData<ListResponse> getListSaleOffProduct(Integer categoryId, Integer sellerId) {
        return productRepository.getListProduct("SaleOff", 1, categoryId, sellerId, null, null, null, null, null);
    }
}