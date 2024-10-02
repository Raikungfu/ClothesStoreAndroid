package com.prmproject.clothesstoremobileandroid.Data.remote;

import com.prmproject.clothesstoremobileandroid.Data.model.ChatMessage;
import com.prmproject.clothesstoremobileandroid.Data.model.dataToReceive.ChatItemResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ChatApiService {
    @GET("api/Chat/listchat")
    Call<List<ChatItemResponse>> getListChat();

    @GET("api/ChatMessage/room/{roomId}")
    Call<List<ChatMessage>> getChatMessages(@Path("roomId") int roomId, @Query("token") String token);
}
