package com.prmproject.clothesstoremobileandroid.Data.remote;

import com.prmproject.clothesstoremobileandroid.Data.model.CartItem;
import com.prmproject.clothesstoremobileandroid.Data.model.Order;
import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface CartApiService {
    @GET("api/CartItem/GetCartItemsOfCart")
    Call<List<CartItem>> getCartItem();
}
