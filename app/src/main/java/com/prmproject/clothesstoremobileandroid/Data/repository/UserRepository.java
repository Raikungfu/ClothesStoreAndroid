package com.prmproject.clothesstoremobileandroid.Data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.prmproject.Network.RetrofitClient;
import com.prmproject.clothesstoremobileandroid.Data.model.Customer;
import com.prmproject.clothesstoremobileandroid.Data.model.Product;
import com.prmproject.clothesstoremobileandroid.Data.model.dataToReceive.ObjectResponse;
import com.prmproject.clothesstoremobileandroid.Data.remote.UserApiService;
import com.prmproject.clothesstoremobileandroid.Data.repository.Generic.ResponseObjectData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {
    private UserApiService apiService;
    private ResponseObjectData<Customer> responseObjectData;

    public UserRepository() {
        apiService = RetrofitClient.getInstance().create(UserApiService.class);
        responseObjectData = new ResponseObjectData<>();

    }

    public LiveData<ObjectResponse> getInfoCustomer(int id) {
        MutableLiveData<ObjectResponse> responseObjectLiveData = new MutableLiveData<>();

        apiService.getInfoCustomer(id).enqueue(new Callback<Customer>() {
            @Override

            public void onResponse(Call<Customer> call, Response<Customer> response) {
                responseObjectLiveData.postValue(responseObjectData.getObjectData(response).getValue());
            }


            @Override
            public void onFailure(Call<Customer> call, Throwable t) {
                responseObjectLiveData.setValue(new ObjectResponse(null, t.getMessage(), false));
            }
        });

        return responseObjectLiveData;
    }

    public LiveData<ObjectResponse> updateCustomer(int userId, Customer customer) {
        MutableLiveData<ObjectResponse> responseObjectLiveData = new MutableLiveData<>();

        apiService.updateCustomer(userId, customer).enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {
                responseObjectLiveData.postValue(responseObjectData.getObjectData(response).getValue());
            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {
                responseObjectLiveData.setValue(new ObjectResponse(null, t.getMessage(), false));
            }
        });

        return responseObjectLiveData;
    }
}

