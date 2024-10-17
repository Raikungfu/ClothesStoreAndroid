package com.prmproject.clothesstoremobileandroid.Data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.prmproject.Network.RetrofitClient;
import com.prmproject.clothesstoremobileandroid.Data.model.CartItem;
import com.prmproject.clothesstoremobileandroid.Data.model.dataToReceive.ListResponse;
import com.prmproject.clothesstoremobileandroid.Data.remote.CartApiService;
import com.prmproject.clothesstoremobileandroid.Data.repository.Generic.ResponseListData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class CartRepository {
    private CartApiService apiService;
    private ResponseListData<CartItem> responseListData;
    public CartRepository() {
        apiService = RetrofitClient.getInstance().create(CartApiService.class);
        responseListData=new ResponseListData<>();
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
}