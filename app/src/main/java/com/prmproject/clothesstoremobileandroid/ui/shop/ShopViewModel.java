package com.prmproject.clothesstoremobileandroid.ui.shop;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.prmproject.clothesstoremobileandroid.Data.model.dataToReceive.ListResponse;
import com.prmproject.clothesstoremobileandroid.Data.model.dataToReceive.ObjectResponse;
import com.prmproject.clothesstoremobileandroid.Data.repository.CategoryRepository;
import com.prmproject.clothesstoremobileandroid.Data.repository.ProductRepository;
import com.prmproject.clothesstoremobileandroid.Data.repository.ShopRepository;

public class ShopViewModel extends ViewModel {
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    private ShopRepository shopRepository;

    public ShopViewModel() {
        productRepository = new ProductRepository();
        categoryRepository = new CategoryRepository();
        shopRepository = new ShopRepository();
    }

    public LiveData<ListResponse> getListCategory() {
        return categoryRepository.getListCategory();
    }

    public LiveData<ListResponse> getListProduct(String filter, Integer pageNumber, Integer categoryId, Integer sellerId, String name, Integer priceFrom, Integer priceTo, int[] listOptionId, int[] listCategoryId) {
        return productRepository.getListProduct(filter, pageNumber, categoryId, sellerId, name, priceFrom, priceTo, listOptionId, listCategoryId);
    }

    public LiveData<ObjectResponse> getShopDetail(int id) {
        return shopRepository.getShopDetail(id);
    }
}