package com.prmproject.clothesstoremobileandroid.ui.shop;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.prmproject.clothesstoremobileandroid.Data.model.dataToReceive.ListResponse;
import com.prmproject.clothesstoremobileandroid.Data.repository.CategoryRepository;
import com.prmproject.clothesstoremobileandroid.Data.repository.ProductRepository;

import java.util.List;

public class ShopViewModel extends ViewModel {
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    public ShopViewModel() {
        productRepository = new ProductRepository();
        categoryRepository = new CategoryRepository();
    }

    public LiveData<ListResponse> getListCategory() {
        return categoryRepository.getListCategory();
    }

    public LiveData<ListResponse> getListProduct(String filter, Integer pageNumber, Integer categoryId, Integer sellerId, String name, Long priceFrom, Long priceTo, int[] listOptionId, int[] listCategoryId) {
        return productRepository.getListProduct(filter, pageNumber, categoryId, sellerId, name, priceFrom, priceTo, listOptionId, listCategoryId);
    }
}