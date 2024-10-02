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

import com.prmproject.clothesstoremobileandroid.Data.model.dataToReceive.ChatItemResponse;
import com.prmproject.clothesstoremobileandroid.R;
import com.prmproject.clothesstoremobileandroid.databinding.FragmentListChatBinding;
import com.prmproject.clothesstoremobileandroid.ui.Adapter.ChatListAdapter;

import java.util.List;

public class ChatListFragment extends Fragment {
    private FragmentListChatBinding binding;
    private ChatViewModel chatViewModel;
    private RecyclerView recyclerView;
    private ChatListAdapter chatListAdapter;
    private List<ChatItemResponse> chatItems;
    NavController navController;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentListChatBinding.inflate(inflater, container, false);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        String key = sharedPreferences.getString("TOKEN_KEY", null);
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);

        if (key != null && !key.isBlank()) {
            chatViewModel = new ViewModelProvider(this).get(ChatViewModel.class);
            recyclerView = binding.recyclerViewChat;

            chatViewModel.getListChat(key).observe(getViewLifecycleOwner(), categoryListResponse -> {
                if (categoryListResponse != null && categoryListResponse.isSuccess()) {
                    chatItems = categoryListResponse.getItems();
                    chatListAdapter = new ChatListAdapter(chatItems, this::navigateToChatMessageFragment);
                    recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));
                    recyclerView.setAdapter(chatListAdapter);
                }
            });

        }else{
            navController.popBackStack(R.id.navigation_list_chat, true);
            navController.navigate(R.id.navigation_login_required);
        }
        return binding.getRoot();
    }

    private void navigateToChatMessageFragment(ChatItemResponse chatItemResponse) {
        Bundle args = new Bundle();
        args.putInt("roomId", chatItemResponse.getRoomId());

        navController.navigate(R.id.navigation_chat, args);
    }
}
