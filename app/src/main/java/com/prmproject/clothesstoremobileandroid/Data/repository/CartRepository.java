package com.prmproject.clothesstoremobileandroid.Data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.prmproject.Network.RetrofitClient;
import com.prmproject.clothesstoremobileandroid.Data.model.CartItem;
import com.prmproject.clothesstoremobileandroid.Data.model.Chat;
import com.prmproject.clothesstoremobileandroid.Data.model.Order;
import com.prmproject.clothesstoremobileandroid.Data.model.dataToReceive.ListResponse;
import com.prmproject.clothesstoremobileandroid.Data.model.dataToReceive.ObjectResponse;
import com.prmproject.clothesstoremobileandroid.Data.model.dataToSend.CartItemDetail;
import com.prmproject.clothesstoremobileandroid.Data.model.dataToSend.OrderCreateViewModel;
import com.prmproject.clothesstoremobileandroid.Data.remote.CartApiService;
import com.prmproject.clothesstoremobileandroid.Data.repository.Generic.ResponseListData;
import com.prmproject.clothesstoremobileandroid.Data.repository.Generic.ResponseObjectData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class CartRepository {
    private CartApiService apiService;
    private ResponseListData<CartItem> responseListData;
    private ResponseObjectData<CartItem> responseData;
    public CartRepository() {
        apiService = RetrofitClient.getInstance().create(CartApiService.class);
        responseListData=new ResponseListData<>();
        responseData = new ResponseObjectData<>();

    }
    public LiveData<ListResponse<CartItem>> getCartItems (){
        MutableLiveData<ListResponse<CartItem>> orderItemsLiveData = new MutableLiveData<>();

        apiService.getCartItem().enqueue(new Callback<List<CartItem>>() {
            @Override
            public void onResponse(Call<List<CartItem>> call, Response<List<CartItem>> response) {
                if (response.isSuccessful()) {
                    orderItemsLiveData.postValue(responseListData.getListData(response).getValue());
                } else {
                    orderItemsLiveData.setValue(new ListResponse<>(null, "Failed to load cart items", false));
                }
            }

            @Override
            public void onFailure(Call<List<CartItem>> call, Throwable t) {
                orderItemsLiveData.setValue(new ListResponse<>(null, t.getMessage(), false));
            }
        });

        return orderItemsLiveData;
    }


    public LiveData<ObjectResponse<CartItem>> createCartItem(CartItemDetail cartItem) {
        MutableLiveData<ObjectResponse<CartItem>> result = new MutableLiveData<>();

        apiService.createCartItem(cartItem).enqueue(new Callback<CartItem>() {
            @Override
            public void onResponse(Call<CartItem> call, Response<CartItem> response) {
                result.postValue(responseData.getObjectData(response).getValue());
            }

            @Override
            public void onFailure(Call<CartItem> call, Throwable t) {
                result.postValue(null);
            }
        });

        return result;
    }
    public LiveData<ObjectResponse<Order>> createOrder(OrderCreateViewModel orderCreateViewModel) {
        MutableLiveData<ObjectResponse<Order>> orderLiveData = new MutableLiveData<>();

        apiService.createOrder(orderCreateViewModel).enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if (response.isSuccessful()) {
                    orderLiveData.postValue(new ObjectResponse<>(response.body(), "Order created successfully", true));
                } else {
                    orderLiveData.postValue(new ObjectResponse<>(null, "Failed to create order", false));
                }
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                orderLiveData.postValue(new ObjectResponse<>(null, t.getMessage(), false));
            }
        });

        return orderLiveData;
    }
    public LiveData<ObjectResponse<Void>> deleteCartItem(int cartItemId) {
        MutableLiveData<ObjectResponse<Void>> result = new MutableLiveData<>();

        apiService.deleteCartItem(cartItemId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    result.postValue(new ObjectResponse<>(null, "Cart item deleted successfully", true));
                } else {
                    result.postValue(new ObjectResponse<>(null, "Failed to delete cart item", false));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                result.postValue(new ObjectResponse<>(null, t.getMessage(), false));
            }
        });

        return result;
    }
}

