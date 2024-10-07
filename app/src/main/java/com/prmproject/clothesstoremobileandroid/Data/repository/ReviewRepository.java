package com.prmproject.clothesstoremobileandroid.Data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.prmproject.Network.RetrofitClient;
import com.prmproject.clothesstoremobileandroid.Data.model.Review;
import com.prmproject.clothesstoremobileandroid.Data.model.dataToReceive.ListResponse;
import com.prmproject.clothesstoremobileandroid.Data.remote.ReviewApiService;
import com.prmproject.clothesstoremobileandroid.Data.repository.Generic.ResponseListData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewRepository {
    private ReviewApiService apiService;
    private ResponseListData<Review> responseListData;

    public ReviewRepository() {
        apiService = RetrofitClient.getInstance().create(ReviewApiService.class);
        responseListData = new ResponseListData<>();
    }

    public LiveData<ListResponse> getListReview(int productId, int page) {
        MutableLiveData<ListResponse> responseListLiveData = new MutableLiveData<>();

        apiService.getListReview(productId, page).enqueue(new Callback<List<Review>>() {
            @Override
            public void onResponse(Call<List<Review>> call, Response<List<Review>> response) {
                responseListLiveData.postValue(responseListData.getListData(response).getValue());
            }

            @Override
            public void onFailure(Call<List<Review>> call, Throwable t) {
                responseListLiveData.setValue(new ListResponse<>(null, t.getMessage(), false));
            }
        });

        return responseListLiveData;
    }
}
