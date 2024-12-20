package com.prmproject.clothesstoremobileandroid.Data.remote;

import com.prmproject.clothesstoremobileandroid.Data.model.Customer;
import com.prmproject.clothesstoremobileandroid.Data.model.User;
import com.prmproject.clothesstoremobileandroid.Data.model.dataToReceive.ObjectResponse;
import com.prmproject.clothesstoremobileandroid.Data.model.dataToReceive.ProfileResponse;

import com.prmproject.clothesstoremobileandroid.Data.model.dataToReceive.UserResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

import java.util.Map;

public interface UserApiService {
    @GET("api/Auth/GetProfile")
    Call<ProfileResponse> getInfo();
    @GET("api/Auth/GetUserNameAndAvt")
    Call<UserResponse> getUsers();

    @POST("api/Auth/Customer/{userId}")
    Call<Customer> updateCustomer(@Body Customer customer);

//    @POST("change-password")
//    Call<ObjectResponse> changePassword(@Body Map<String, String> passwords);
}
