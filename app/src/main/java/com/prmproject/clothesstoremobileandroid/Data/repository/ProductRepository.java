package com.prmproject.clothesstoremobileandroid.Data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.prmproject.Network.RetrofitClient;
import com.prmproject.clothesstoremobileandroid.Data.model.Category;
import com.prmproject.clothesstoremobileandroid.Data.model.Product;
import com.prmproject.clothesstoremobileandroid.Data.model.dataToReceive.ListResponse;
import com.prmproject.clothesstoremobileandroid.Data.remote.CategoryApiService;
import com.prmproject.clothesstoremobileandroid.Data.remote.ProductApiService;
import com.prmproject.clothesstoremobileandroid.Data.repository.Generic.ResponseListData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductRepository {
    private ProductApiService apiService;
    private ResponseListData<Product> responseListData;

    public ProductRepository() {
        apiService = RetrofitClient.getInstance().create(ProductApiService.class);
        responseListData = new ResponseListData<>();
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
}
