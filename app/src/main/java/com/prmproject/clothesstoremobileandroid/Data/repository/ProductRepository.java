package com.prmproject.clothesstoremobileandroid.Data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.prmproject.Network.RetrofitClient;
import com.prmproject.clothesstoremobileandroid.Data.model.Product;
import com.prmproject.clothesstoremobileandroid.Data.model.dataToReceive.ListResponse;
import com.prmproject.clothesstoremobileandroid.Data.model.dataToReceive.ObjectResponse;
import com.prmproject.clothesstoremobileandroid.Data.remote.ProductApiService;
import com.prmproject.clothesstoremobileandroid.Data.repository.Generic.ResponseListData;
import com.prmproject.clothesstoremobileandroid.Data.repository.Generic.ResponseObjectData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductRepository {
    private ProductApiService apiService;
    private ResponseListData<Product> responseListData;
    private ResponseObjectData<Product> responseObjectData;

    public ProductRepository() {
        apiService = RetrofitClient.getInstance().create(ProductApiService.class);
        responseListData = new ResponseListData<>();
        responseObjectData = new ResponseObjectData<>();
    }

    public LiveData<ListResponse> getListProduct(String orderBy, int pageNumber, Integer categoryId, Integer sellerId) {
        MutableLiveData<ListResponse> responseListLiveData = new MutableLiveData<>();

        apiService.getListProduct(orderBy, pageNumber, categoryId, sellerId).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                responseListLiveData.postValue(responseListData.getListData(response).getValue());
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                responseListLiveData.setValue(new ListResponse<>(null, t.getMessage(), false));
            }
        });

        return responseListLiveData;
    }


    public LiveData<ObjectResponse> getProductDetail(int id){
        MutableLiveData<ObjectResponse> responseObjectLiveData = new MutableLiveData<>();

        apiService.getProductDetail(id).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                responseObjectLiveData.postValue(responseObjectData.getObjectData(response).getValue());
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                responseObjectLiveData.setValue(new ObjectResponse(null, t.getMessage(), false));
            }
        });

        return responseObjectLiveData;
    }
}
