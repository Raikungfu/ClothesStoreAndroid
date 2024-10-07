package com.prmproject.clothesstoremobileandroid.Data.remote;

import com.prmproject.clothesstoremobileandroid.Data.model.Customer;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.List;

public interface UserApiService {
    @GET("api/Auth/Customer/{userId}")
    Call<Customer> getInfoCustomer(@Path("userId") int userId);
    @POST("api/Auth/Customer/{userId}")
    Call<Customer> updateCustomer(@Path("userId") int userId,@Body Customer customer);
}
