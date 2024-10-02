package com.prmproject.clothesstoremobileandroid.Data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.prmproject.Network.RetrofitClient;
import com.prmproject.clothesstoremobileandroid.Data.model.ChatMessage;
import com.prmproject.clothesstoremobileandroid.Data.model.dataToReceive.ChatItemResponse;
import com.prmproject.clothesstoremobileandroid.Data.model.dataToReceive.ListResponse;
import com.prmproject.clothesstoremobileandroid.Data.remote.ChatApiService;
import com.prmproject.clothesstoremobileandroid.Data.repository.Generic.ResponseListData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatRepository {
    private ChatApiService apiService;
    private ResponseListData<ChatItemResponse> responseListData;
    private ResponseListData<ChatMessage> responseListMessageData;

    public ChatRepository() {
        apiService = RetrofitClient.getInstance().create(ChatApiService.class);
        responseListData = new ResponseListData<>();
        responseListMessageData = new ResponseListData<>();
    }

    public LiveData<ListResponse> getListChat() {
        MutableLiveData<ListResponse> responseListLiveData = new MutableLiveData<>();

        apiService.getListChat().enqueue(new Callback<List<ChatItemResponse>>() {
            @Override
            public void onResponse(Call<List<ChatItemResponse>> call, Response<List<ChatItemResponse>> response) {
                responseListLiveData.postValue(responseListData.getListData(response).getValue());
            }

            @Override
            public void onFailure(Call<List<ChatItemResponse>> call, Throwable t) {
                responseListLiveData.setValue(new ListResponse<>(null, t.getMessage(), false));
            }
        });

        return responseListLiveData;
    }

    public LiveData<ListResponse> getChatMessages(int id, String token) {
        MutableLiveData<ListResponse> responseListLiveData = new MutableLiveData<>();

        apiService.getChatMessages(id, token).enqueue(new Callback<List<ChatMessage>>() {
            @Override
            public void onResponse(Call<List<ChatMessage>> call, Response<List<ChatMessage>> response) {
                responseListLiveData.postValue(responseListMessageData.getListData(response).getValue());
            }

            @Override
            public void onFailure(Call<List<ChatMessage>> call, Throwable t) {
                responseListLiveData.setValue(new ListResponse<>(null, t.getMessage(), false));
            }
        });

        return responseListLiveData;
    }
}
