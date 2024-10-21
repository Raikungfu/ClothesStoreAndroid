package com.prmproject.clothesstoremobileandroid.Data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.prmproject.Network.RetrofitClient;
import com.prmproject.clothesstoremobileandroid.Data.model.Seller;
import com.prmproject.clothesstoremobileandroid.Data.model.dataToReceive.ObjectResponse;
import com.prmproject.clothesstoremobileandroid.Data.remote.SellerApiService;
import com.prmproject.clothesstoremobileandroid.Data.repository.Generic.ResponseObjectData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShopRepository {
    private SellerApiService apiService;
    private ResponseObjectData<Seller> responseObjectData;

    public ShopRepository() {
        apiService = RetrofitClient.getInstance().create(SellerApiService.class);
        responseObjectData = new ResponseObjectData<>();
    }


    public LiveData<ObjectResponse> getShopDetail(int id){
        MutableLiveData<ObjectResponse> responseObjectLiveData = new MutableLiveData<>();

        apiService.getSellerDetail(id).enqueue(new Callback<Seller>() {
            @Override
            public void onResponse(Call<Seller> call, Response<Seller> response) {
                responseObjectLiveData.postValue(responseObjectData.getObjectData(response).getValue());
            }

            @Override
            public void onFailure(Call<Seller> call, Throwable t) {
                responseObjectLiveData.setValue(new ObjectResponse(null, t.getMessage(), false));
            }
        });

        return responseObjectLiveData;
    }
}
