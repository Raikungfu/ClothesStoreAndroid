package com.prmproject.clothesstoremobileandroid.Data.remote;

import com.prmproject.clothesstoremobileandroid.Data.model.Seller;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SellerApiService {
    @GET("api/Seller/{id}")
    Call<Seller> getSellerDetail(@Path("id") int id);
}
