package com.prmproject.clothesstoremobileandroid.ui.Chat;


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
import com.prmproject.clothesstoremobileandroid.MainActivity;
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
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);

            chatViewModel = new ViewModelProvider(this).get(ChatViewModel.class);
            recyclerView = binding.recyclerViewChat;

            chatViewModel.getListChat().observe(getViewLifecycleOwner(), chatListResponse -> {
                if (chatListResponse != null && chatListResponse.isSuccess()) {
                    chatItems = chatListResponse.getItems();
                    chatListAdapter = new ChatListAdapter(chatItems, this::navigateToChatMessageFragment);
                    recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, true));
                    recyclerView.setAdapter(chatListAdapter);
                } else if (!chatListResponse.isSuccess() && chatListResponse.getCodeError() == 401) {
                    ((MainActivity) requireActivity()).onMessage("Login failed! " + chatListResponse.getErrorMessage());
                    requireLogin();
                }else if (!chatListResponse.isSuccess()){
                    ((MainActivity) requireActivity()).onMessage("Load data failed! " + chatListResponse.getErrorMessage());
                }
            });
        return binding.getRoot();
    }

    private void requireLogin(){
        navController.popBackStack(R.id.navigation_home, true);
        navController.navigate(R.id.navigation_login_required);
    }

    private void navigateToChatMessageFragment(ChatItemResponse chatItemResponse) {
        Bundle args = new Bundle();
        args.putInt("roomId", chatItemResponse.getRoomId());

        navController.navigate(R.id.action_navigation_list_chat_to_navigation_chat, args);
    }
}