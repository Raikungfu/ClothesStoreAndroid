package com.prmproject.clothesstoremobileandroid.Data.remote;

import com.prmproject.clothesstoremobileandroid.Data.model.dataToReceive.ChatItemResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ChatApiService {
    @GET("api/Chat/listchat")
    Call<List<ChatItemResponse>> getListChat(@Query("token") String orderBy);
}
