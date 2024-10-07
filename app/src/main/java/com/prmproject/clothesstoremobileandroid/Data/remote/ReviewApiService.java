package com.prmproject.clothesstoremobileandroid.Data.remote;

import com.prmproject.clothesstoremobileandroid.Data.model.Review;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ReviewApiService {
    @GET("api/Review/GetReviewsForProduct/{productId}")
    Call<List<Review>> getListReview(@Path("productId") int productId, @Query("page") int page);
}