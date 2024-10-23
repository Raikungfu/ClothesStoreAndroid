package com.prmproject.clothesstoremobileandroid.ui.Product;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.prmproject.clothesstoremobileandroid.Data.model.CartItem;
import com.prmproject.clothesstoremobileandroid.Data.model.dataToReceive.ListResponse;
import com.prmproject.clothesstoremobileandroid.Data.model.dataToReceive.ObjectResponse;
import com.prmproject.clothesstoremobileandroid.Data.model.dataToSend.CartItemDetail;
import com.prmproject.clothesstoremobileandroid.Data.repository.CartRepository;
import com.prmproject.clothesstoremobileandroid.Data.repository.ChatRepository;
import com.prmproject.clothesstoremobileandroid.Data.repository.ProductRepository;
import com.prmproject.clothesstoremobileandroid.Data.repository.ReviewRepository;

public class ProductDetailViewModel extends ViewModel {
    private ProductRepository productRepository;
    private ChatRepository chatRepository;
    private ReviewRepository reviewRepository;
    private CartRepository cartRepository;

    public ProductDetailViewModel(){
        productRepository = new ProductRepository();
        chatRepository = new ChatRepository();
        reviewRepository = new ReviewRepository();
        cartRepository=new CartRepository();
    }

    public LiveData<ObjectResponse> getProductDetail(int id) {
        return productRepository.getProductDetail(id);
    }

    public LiveData<ObjectResponse> postChat(int id) {
        return chatRepository.postChat(id);
    }

    public LiveData<ListResponse> getListReview(int productId, int page) {
        return reviewRepository.getListReview(productId, page);
    }

    public LiveData<ObjectResponse<CartItem>> addCartItem(CartItemDetail cartItemDetail) {
        return cartRepository.createCartItem(cartItemDetail);
    }

}