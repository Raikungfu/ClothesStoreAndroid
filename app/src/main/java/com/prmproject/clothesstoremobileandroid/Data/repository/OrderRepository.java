package com.prmproject.clothesstoremobileandroid.Data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.prmproject.Network.RetrofitClient;
import com.prmproject.clothesstoremobileandroid.Data.model.Order;
import com.prmproject.clothesstoremobileandroid.Data.model.OrderItem;
import com.prmproject.clothesstoremobileandroid.Data.model.dataToReceive.ListResponse;
import com.prmproject.clothesstoremobileandroid.Data.model.dataToReceive.ObjectResponse;
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


    public LiveData<ListResponse> getOrders() {
        MutableLiveData<ListResponse> ordersLiveData = new MutableLiveData<>();

        apiService.getOrdersByUserId().enqueue(new Callback<List<Order>>() {
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

    public LiveData<ListResponse<OrderItem>> getOrderDetail(int orderId) {
        MutableLiveData<ListResponse<OrderItem>> orderItemsLiveData = new MutableLiveData<>();

        apiService.getOrderDetail(orderId).enqueue(new Callback<List<OrderItem>>() {
            @Override
            public void onResponse(Call<List<OrderItem>> call, Response<List<OrderItem>> response) {
                if (response.isSuccessful()) {
                    orderItemsLiveData.postValue(responseListDetail.getListData(response).getValue());
                } else {
                    orderItemsLiveData.setValue(new ListResponse<>(null, "Failed to load order items", false));
                }
            }

            @Override
            public void onFailure(Call<List<OrderItem>> call, Throwable t) {
                orderItemsLiveData.setValue(new ListResponse<>(null, t.getMessage(), false));
            }
        });

        return orderItemsLiveData;
    }
    public LiveData<ObjectResponse> updateStatus(int orderId, String newStatus) {
        MutableLiveData<ObjectResponse> responseLiveData = new MutableLiveData<>();

        apiService.updateStatus(orderId, newStatus).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    responseLiveData.postValue(new ObjectResponse(null, "Status updated successfully", true));
                } else {
                    responseLiveData.postValue(new ObjectResponse(null, "Failed to update status", false));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                responseLiveData.setValue(new ObjectResponse(null, t.getMessage(), false));
            }
        });

        return responseLiveData;
    }
}