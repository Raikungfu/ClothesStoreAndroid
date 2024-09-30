package com.prmproject.clothesstoremobileandroid.Data.remote;

import com.prmproject.clothesstoremobileandroid.Data.model.Category;
import com.prmproject.clothesstoremobileandroid.Data.model.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ProductApiService {
    @GET("api/Product")
    Call<List<Product>> getListProduct(@Query("orderBy") String orderBy, @Query("pageNumber")  int pageNumber, @Query("categoryId") Integer categoryId, @Query("sellerId") Integer sellerId);
}