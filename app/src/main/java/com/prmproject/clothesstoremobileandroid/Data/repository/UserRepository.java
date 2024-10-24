package com.prmproject.clothesstoremobileandroid.Data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.prmproject.Network.RetrofitClient;
import com.prmproject.clothesstoremobileandroid.Data.model.Customer;
import com.prmproject.clothesstoremobileandroid.Data.model.User;
import com.prmproject.clothesstoremobileandroid.Data.model.dataToReceive.ObjectResponse;
import com.prmproject.clothesstoremobileandroid.Data.model.dataToReceive.ProfileResponse;
import com.prmproject.clothesstoremobileandroid.Data.model.dataToReceive.UserResponse;
import com.prmproject.clothesstoremobileandroid.Data.remote.UserApiService;
import com.prmproject.clothesstoremobileandroid.Data.repository.Generic.ResponseObjectData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.HashMap;
import java.util.Map;

public class UserRepository {
    private UserApiService apiService;
    private ResponseObjectData<Customer> responseObjectData;
    private ResponseObjectData<ProfileResponse> responseProfileObjectData;
    private ResponseObjectData<UserResponse> responseUserData;


    public UserRepository() {
        apiService = RetrofitClient.getInstance().create(UserApiService.class);
        responseObjectData = new ResponseObjectData<>();
        responseProfileObjectData=new ResponseObjectData<>();
responseUserData=new ResponseObjectData<>();
    }

    public LiveData<ObjectResponse> getInfo() {
        MutableLiveData<ObjectResponse> responseObjectLiveData = new MutableLiveData<>();

        apiService.getInfo().enqueue(new Callback<ProfileResponse>() {
            @Override

            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                responseObjectLiveData.postValue(responseProfileObjectData.getObjectData(response).getValue());
            }


            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                responseObjectLiveData.setValue(new ObjectResponse(null, t.getMessage(), false));
            }
        });

        return responseObjectLiveData;
    }


    public LiveData<ObjectResponse> getUser() {
        MutableLiveData<ObjectResponse> responseObjectLiveData = new MutableLiveData<>();

        apiService.getUsers().enqueue(new Callback<UserResponse>() {
            @Override

            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                responseObjectLiveData.postValue(responseUserData.getObjectData(response).getValue());
            }


            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                responseObjectLiveData.setValue(new ObjectResponse(null, t.getMessage(), false));
            }
        });

        return responseObjectLiveData;
    }

    public LiveData<ObjectResponse> updateCustomer(Customer customer) {
        MutableLiveData<ObjectResponse> responseObjectLiveData = new MutableLiveData<>();

        apiService.updateCustomer(customer).enqueue(new Callback<Customer>() {
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


//    public LiveData<ObjectResponse> changePassword(String oldPassword, String newPassword) {
//        MutableLiveData<ObjectResponse> responseObjectLiveData = new MutableLiveData<>();
//
//        Map<String, String> passwords = new HashMap<>();
//        passwords.put("OldPassword", oldPassword);
//        passwords.put("NewPassword", newPassword);
//
//        apiService.changePassword(passwords).enqueue(new Callback<ObjectResponse>() {
//            @Override
//            public void onResponse(Call<ObjectResponse> call, Response<ObjectResponse> response) {
//                if (response.isSuccessful()) {
//                    responseObjectLiveData.postValue(response.body());
//                } else {
//                    responseObjectLiveData.postValue(new ObjectResponse(null, "Password change failed", false));
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ObjectResponse> call, Throwable t) {
//                responseObjectLiveData.setValue(new ObjectResponse(null, t.getMessage(), false));
//            }
//        });
//
//        return responseObjectLiveData;
//    }
}

