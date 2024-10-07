package com.prmproject.clothesstoremobileandroid.Data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.prmproject.Network.RetrofitClient;
import com.prmproject.clothesstoremobileandroid.Data.model.Order;
import com.prmproject.clothesstoremobileandroid.Data.model.OrderItem;
import com.prmproject.clothesstoremobileandroid.Data.model.dataToReceive.ListResponse;
import com.prmproject.clothesstoremobileandroid.Data.remote.OrderApiService;
import com.prmproject.clothesstoremobileandroid.Data.repository.Generic.ResponseListData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class OrderRepository {
    private OrderApiService apiService;
    private ResponseListData<Order> responseListData;
    private ResponseListData<OrderItem> responseListDetail;


    public OrderRepository() {
        apiService = RetrofitClient.getInstance().create(OrderApiService.class);
        responseListData = new ResponseListData<>();
        responseListDetail=new ResponseListData<>();
    }


    public LiveData<ListResponse> getOrders(int userId) {
        MutableLiveData<ListResponse> ordersLiveData = new MutableLiveData<>();

        apiService.getOrdersByUserId(userId).enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                if (response.isSuccessful()) {
                    ordersLiveData.postValue(responseListData.getListData(response).getValue());
                }
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                ordersLiveData.setValue(new ListResponse<>(null, t.getMessage(), false));
            }
        });

        return ordersLiveData;
    }

    public LiveData<ListResponse> getOrderDetail(int orderId) {
        MutableLiveData<ListResponse> orderDetailLiveData = new MutableLiveData<>();

        apiService.getOrderDetail(orderId).enqueue(new Callback<List<OrderItem>>() {
            @Override
            public void onResponse(Call<List<OrderItem>> call, Response<List<OrderItem>> responseDetail) {
                orderDetailLiveData.postValue(responseListDetail.getListData(responseDetail).getValue());
            }

            @Override
            public void onFailure(Call<List<OrderItem>> call, Throwable t) {
                orderDetailLiveData.setValue(new ListResponse<>(null, t.getMessage(), false));
            }
        });

        return orderDetailLiveData;
    }
}
//package com.prmproject.clothesstoremobileandroid.Data.repository;
//
//import android.util.Log;
//import androidx.lifecycle.LiveData;
//import androidx.lifecycle.MutableLiveData;
//import com.prmproject.Network.RetrofitClient;
//import com.prmproject.clothesstoremobileandroid.Data.model.Order;
//import com.prmproject.clothesstoremobileandroid.Data.model.dataToReceive.ListResponse;
//import com.prmproject.clothesstoremobileandroid.Data.remote.OrderApiService;
//import com.prmproject.clothesstoremobileandroid.Data.repository.Generic.ResponseListData;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//import java.util.List;
//
//public class OrderRepository {
//    private OrderApiService apiService;
//    private ResponseListData<Order> responseListData;
//
//    public OrderRepository() {
//        apiService = RetrofitClient.getInstance().create(OrderApiService.class);
//        responseListData = new ResponseListData<>();
//    }
//
//    public LiveData<ListResponse> getOrders(int userId) {
//        MutableLiveData<ListResponse> ordersLiveData = new MutableLiveData<>();
//
//        apiService.getOrdersByUserId(userId).enqueue(new Callback<List<Order>>() {
//            @Override
//            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
//                if (response.isSuccessful()) {
//                    if (response.body() != null) {
//                        // Log the successful response and set the data
//                        ordersLiveData.postValue(responseListData.getListData(response).getValue());
//                    } else {
//                        // Log a null response body case
//                        Log.e("OrderRepository", "Response successful but body is null");
//                        ordersLiveData.setValue(new ListResponse<>(null, "Response body is null", false));
//                    }
//                } else {
//                    // Log the unsuccessful response code and message
//                    Log.e("OrderRepository", "Failed response: " + response.code() + " - " + response.message());
//                    ordersLiveData.setValue(new ListResponse<>(null, "Failed to load orders: " + response.message(), false));
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Order>> call, Throwable t) {
//                // Log the failure message
//                Log.e("OrderRepository", "API call failed: " + t.getMessage(), t);
//                ordersLiveData.setValue(new ListResponse<>(null, t.getMessage(), false));
//            }
//        });
//
//        return ordersLiveData;
//    }
//}
