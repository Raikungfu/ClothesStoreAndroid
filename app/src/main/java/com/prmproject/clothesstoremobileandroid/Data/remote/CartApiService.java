package com.prmproject.clothesstoremobileandroid.Data.remote;

import com.prmproject.clothesstoremobileandroid.Data.model.CartItem;
import com.prmproject.clothesstoremobileandroid.Data.model.Order;
import com.prmproject.clothesstoremobileandroid.Data.model.dataToReceive.ObjectResponse;
import com.prmproject.clothesstoremobileandroid.Data.model.dataToSend.CartItemDetail;
import com.prmproject.clothesstoremobileandroid.Data.model.dataToSend.OrderCreateViewModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

import java.util.List;

public interface CartApiService {
    @GET("api/CartItem/GetCartItemsOfCart")
    Call<List<CartItem>> getCartItem();
    @POST("api/CartItem/CreateCartItem")
    Call<CartItem> createCartItem(@Body CartItemDetail cartItem);
    @POST("api/Order")
    Call<Order> createOrder(@Body OrderCreateViewModel orderCreateViewModel);
}
