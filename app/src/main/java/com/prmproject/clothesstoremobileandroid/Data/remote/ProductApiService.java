package com.prmproject.clothesstoremobileandroid.Data.remote;

import com.prmproject.clothesstoremobileandroid.Data.model.Product;
import com.prmproject.clothesstoremobileandroid.Data.model.dataToReceive.FilterListResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ProductApiService {
    @GET("api/Product")
    Call<List<Product>> getListProduct(@Query("orderBy") String orderBy, @Query("pageNumber")  int pageNumber, @Query("categoryId") Integer categoryId, @Query("sellerId") Integer sellerId, @Query("name") String name, @Query("priceFrom") Integer priceFrom, @Query("priceTo") Integer priceTo, @Query("listOptionId") int[] listOptionId, @Query("listCategoryId") int[] listCategoryId);

    @GET("api/Product/{id}")
    Call<Product> getProductDetail(@Path("id") int id);

    @GET("api/Product/GetListCategoryAndProductOptions")
    Call<FilterListResponse> getFilterList();
}