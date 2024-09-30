package com.prmproject.clothesstoremobileandroid.Data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.prmproject.Network.RetrofitClient;
import com.prmproject.clothesstoremobileandroid.Data.model.Category;
import com.prmproject.clothesstoremobileandroid.Data.model.dataToReceive.ListResponse;
import com.prmproject.clothesstoremobileandroid.Data.model.dataToSend.UserRegister;
import com.prmproject.clothesstoremobileandroid.Data.remote.CategoryApiService;
import com.prmproject.clothesstoremobileandroid.Data.repository.Generic.ResponseListData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryRepository {
    private CategoryApiService apiService;
    private ResponseListData<Category> responseListData;

    public CategoryRepository() {
        apiService = RetrofitClient.getInstance().create(CategoryApiService.class);
        responseListData = new ResponseListData<>();
    }

    public LiveData<ListResponse> getListCategory() {
        MutableLiveData<ListResponse> responseListLiveData = new MutableLiveData<>();
        apiService.getListCategory().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                responseListLiveData.postValue(responseListData.getListData(response).getValue());
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                responseListLiveData.setValue(new ListResponse<>(null, t.getMessage(), false));
            }
        });
        return responseListLiveData;
    }
}
