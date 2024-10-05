package com.prmproject.clothesstoremobileandroid.ui.Product;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.prmproject.clothesstoremobileandroid.Data.model.dataToReceive.ObjectResponse;
import com.prmproject.clothesstoremobileandroid.Data.repository.ChatRepository;
import com.prmproject.clothesstoremobileandroid.Data.repository.ProductRepository;

public class ProductDetailViewModel extends ViewModel {
    private ProductRepository productRepository;
    private ChatRepository chatRepository;

    public ProductDetailViewModel(){
        productRepository = new ProductRepository();
        chatRepository = new ChatRepository();
    }

    public LiveData<ObjectResponse> getProductDetail(int id) {
        return productRepository.getProductDetail(id);
    }

    public LiveData<ObjectResponse> postChat(int id) {
        return chatRepository.postChat(id);
    }
}