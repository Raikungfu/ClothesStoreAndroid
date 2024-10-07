package com.prmproject.clothesstoremobileandroid.Data.remote;

import com.prmproject.clothesstoremobileandroid.Data.model.Order;
import com.prmproject.clothesstoremobileandroid.Data.model.OrderItem;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;

public interface OrderApiService {
    @GET("api/Order/GetOrderItem/{userId}")
    Call<List<Order>> getOrdersByUserId(@Path("userId") int userId);

    @GET("api/OrderItem/GetOrderItem/{orderId}")
    Call<List<OrderItem>> getOrderDetail(@Path("userId") int orderId);

}
