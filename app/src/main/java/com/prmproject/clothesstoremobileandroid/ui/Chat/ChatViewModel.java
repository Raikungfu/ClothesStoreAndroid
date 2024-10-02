package com.prmproject.clothesstoremobileandroid.ui.Chat;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.prmproject.clothesstoremobileandroid.Data.model.dataToReceive.ListResponse;
import com.prmproject.clothesstoremobileandroid.Data.repository.ChatRepository;

public class ChatViewModel extends ViewModel {
    ChatRepository chatRepository;

    public ChatViewModel() {
        chatRepository = new ChatRepository();
    }

    public LiveData<ListResponse> getListChat(String token) {
        return chatRepository.getListChat(token);
    }

    public LiveData<ListResponse> getChatMessages(int id, String token) {
        return chatRepository.getChatMessages(id, token);
    }
}