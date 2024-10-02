package com.prmproject.clothesstoremobileandroid.ui.Chat;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.prmproject.clothesstoremobileandroid.Data.model.ChatMessage;
import com.prmproject.clothesstoremobileandroid.R;
import com.prmproject.clothesstoremobileandroid.databinding.FragmentChatBinding;
import com.prmproject.clothesstoremobileandroid.ui.Adapter.MessageAdapter;

import java.util.List;

public class ChatFragment extends Fragment {
    private FragmentChatBinding binding;
    private ChatViewModel chatViewModel;
    private RecyclerView recyclerView;
    private MessageAdapter messageAdapter;
    private List<ChatMessage> chatMessages;
    NavController navController;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentChatBinding.inflate(inflater, container, false);
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);

        loadChatFromArguments();
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadChatFromArguments();
    }

    private void loadChatFromArguments() {
        Bundle args = getArguments();
        if (args != null) {
            int roomId = args.getInt("roomId", -1);
            if(roomId != -1) loadChatMessage(roomId);
        }
    }


    private void loadChatMessage(int id) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        String key = sharedPreferences.getString("TOKEN_KEY", null);

        if (key != null && !key.isBlank()) {
            chatViewModel = new ViewModelProvider(this).get(ChatViewModel.class);
            recyclerView = binding.messageRecyclerView;

            chatViewModel.getChatMessages(id, key).observe(getViewLifecycleOwner(), chatMessagesListResponse -> {
                if (chatMessagesListResponse != null && chatMessagesListResponse.isSuccess()) {
                    chatMessages = chatMessagesListResponse.getItems();
                    messageAdapter = new MessageAdapter(chatMessages);
                    recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));
                    recyclerView.setAdapter(messageAdapter);
                }
            });

        }else{
            navController.popBackStack(R.id.navigation_chat, true);
            navController.navigate(R.id.navigation_login_required);
        }
    }

}
