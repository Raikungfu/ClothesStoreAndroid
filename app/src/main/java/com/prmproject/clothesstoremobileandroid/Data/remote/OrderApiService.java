package com.prmproject.clothesstoremobileandroid.Data.remote;

import com.prmproject.clothesstoremobileandroid.Data.model.Order;
import com.prmproject.clothesstoremobileandroid.Data.model.OrderItem;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

import java.util.List;

public interface OrderApiService {
    @GET("api/Order/GetOrderItem")
    Call<List<Order>> getOrdersByUserId();

    @GET("api/OrderItem/GetOrderItemDetail/{orderId}")
    Call<List<OrderItem>> getOrderDetail(@Path("orderId") int orderId);
    @PUT("api/Order/update-status/{id}")
    Call<Void> updateStatus(@Path("id") int orderId, @Body String newStatus);
}
