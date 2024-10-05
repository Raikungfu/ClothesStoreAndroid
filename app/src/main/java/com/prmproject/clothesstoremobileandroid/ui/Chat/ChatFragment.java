package com.prmproject.clothesstoremobileandroid.ui.Chat;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionState;
import com.prmproject.Network.Service.SignalRService;
import com.prmproject.clothesstoremobileandroid.ClothesStore;
import com.prmproject.clothesstoremobileandroid.Data.model.ChatMessage;
import com.prmproject.clothesstoremobileandroid.Data.model.dataToReceive.Callback;
import com.prmproject.clothesstoremobileandroid.Data.model.dataToReceive.ChatMessageResponse;
import com.prmproject.clothesstoremobileandroid.MainActivity;
import com.prmproject.clothesstoremobileandroid.R;
import com.prmproject.clothesstoremobileandroid.databinding.FragmentChatBinding;
import com.prmproject.clothesstoremobileandroid.ui.Adapter.MessageAdapter;

import java.util.ArrayList;
import java.util.List;

public class ChatFragment extends Fragment {
    private FragmentChatBinding binding;
    private ChatViewModel chatViewModel;
    private RecyclerView recyclerView;
    private MessageAdapter messageAdapter;
    private List<ChatMessage> chatMessages;
    private SignalRService signalRService;
    private NavController navController;
    private int roomId;
    private HubConnection hubConnection;
    private boolean isLoading = false;
    private int currentPage = 0;
    private String key;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentChatBinding.inflate(inflater, container, false);
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        key = sharedPreferences.getString("TOKEN_KEY", null);

        signalRService = ((ClothesStore) requireActivity().getApplication()).getSignalRService();
        hubConnection = signalRService.getHubConnection();

        Bundle args = getArguments();
        if (args != null) {
            roomId = args.getInt("roomId", -1);
        }

        recyclerView = binding.messageRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, true));
        messageAdapter = new MessageAdapter();
        recyclerView.setAdapter(messageAdapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (!isLoading && layoutManager != null && layoutManager.findLastCompletelyVisibleItemPosition() == chatMessages.size() - 1) {
                    currentPage++;
                    loadChatMessage(currentPage, false);
                }
            }
        });

        TextView inputChat = binding.messageInput;
        ImageButton sendChatBtn = binding.sendButton;
        ImageButton mediaImageButton = binding.mediaButton;

        sendChatBtn.setOnClickListener(v -> {
            String message = inputChat.getText().toString();
            if (!message.isBlank()){
                inputChat.setText("");
                ChatMessage messageSend = new ChatMessage(message, roomId, null, null);

                if (hubConnection.getConnectionState() != HubConnectionState.CONNECTED) {
                    signalRService.reconnect();
                }

                signalRService.sendMessageToUser(messageSend, new Callback<ChatMessageResponse<ChatMessage>>() {
                    @Override
                    public void onSuccess(ChatMessageResponse<ChatMessage> result) {
                        if ("Success".equals(result.getStatus())) {
                            Gson gson = new Gson();
                            ChatMessage chatMessage = gson.fromJson(gson.toJson(result.getResponse()), ChatMessage.class);

                            if (chatMessage.getRoomId() == roomId) {
                                chatMessages.add(chatMessage);
                                getActivity().runOnUiThread(() -> {
                                    messageAdapter.submitList(chatMessages, () -> {
                                        adjustRecyclerView();
                                    });
                                });
                            }
                        } else {
                            Log.e("SignalR", "Error: " + result.getContent());
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        ((MainActivity) requireActivity()).onMessage("Error sending message: " + throwable.getMessage());
                    }
                });
            }
        });

        inputChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adjustRecyclerView();
            }
        });

        return binding.getRoot();
    }

    private void adjustRecyclerView() {
        if (chatMessages != null && chatMessages.size() > 0) recyclerView.scrollToPosition(0);
    }


    @Override
    public void onStop() {
        super.onStop();
        hubConnection.remove("ReceiveMessage");
    }


    @Override
    public void onResume() {
        super.onResume();
        if(roomId != -1) loadChatMessage(currentPage, true);

        if (hubConnection.getConnectionState() != HubConnectionState.CONNECTED) {
            signalRService.reconnect();
        }

        hubConnection.on("ReceiveMessage", (ChatMessageResponse messageResponse) -> {
            getActivity().runOnUiThread(() -> handleReceivedMessage(messageResponse));
        }, ChatMessageResponse.class);
    }

    private void handleReceivedMessage(ChatMessageResponse messageResponse) {
        try {
            if ("Success".equals(messageResponse.getStatus())) {
                Gson gson = new Gson();
                ChatMessage chatMessage = gson.fromJson(gson.toJson(messageResponse.getResponse()), ChatMessage.class);

                if (chatMessage.getRoomId() == roomId) {
                    chatMessages.add(chatMessage);
                    messageAdapter.submitList(new ArrayList<>(chatMessages), () -> {
                        adjustRecyclerView();
                    });
                }
            } else {
                Log.e("SignalR", "Error: " + messageResponse.getContent());
            }
        } catch (JsonSyntaxException e) {
            Log.e("ChatFragment", "JsonSyntaxException: " + e.getMessage());
        }
    }

    private void loadChatMessage(int page, boolean isAdjustScreen) {
        if (key != null && !key.isBlank()) {
            chatViewModel = new ViewModelProvider(this).get(ChatViewModel.class);

            chatViewModel.getChatMessages(roomId, page).observe(getViewLifecycleOwner(), chatMessagesListResponse -> {
                if (chatMessagesListResponse != null && chatMessagesListResponse.isSuccess()) {
                    if (chatMessages == null) {
                        chatMessages = new ArrayList<>();
                    }
                    chatMessages.addAll(chatMessagesListResponse.getItems());

                    messageAdapter.submitList(new ArrayList<>(chatMessages), () -> {
                        if(isAdjustScreen) adjustRecyclerView();
                    });
                }
            });
        } else {
            navController.popBackStack(R.id.navigation_home, true);
            navController.navigate(R.id.navigation_login_required);
        }
    }


}
