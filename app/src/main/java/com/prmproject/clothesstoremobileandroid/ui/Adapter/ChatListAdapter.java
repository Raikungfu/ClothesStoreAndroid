package com.prmproject.clothesstoremobileandroid.ui.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.prmproject.clothesstoremobileandroid.Data.model.dataToReceive.ChatItemResponse;
import com.prmproject.clothesstoremobileandroid.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ChatViewHolder> {

    private List<ChatItemResponse> chatItems;
    private OnChatListClickListener chatListClickListener;

    public ChatListAdapter(List<ChatItemResponse> chatItems, OnChatListClickListener onChatListClickListener) {
        this.chatItems = chatItems;
        this.chatListClickListener = onChatListClickListener;
    }

    public interface OnChatListClickListener {
        void onChatListClickListener(ChatItemResponse chatItem);
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_list_chat_item, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        ChatItemResponse chatItem = chatItems.get(position);

        holder.senderName.setText(chatItem.getName());
        holder.messagePreview.setText(chatItem.getLatestMessage());

        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS", Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy", Locale.getDefault());

        String latestMessageTime = chatItem.getLatestMessageTime();
        if (latestMessageTime != null && !latestMessageTime.isEmpty()) {
            try {
                Date date = inputFormat.parse(latestMessageTime);
                if (date != null) {
                    String formattedDate = outputFormat.format(date);
                    holder.messageTime.setText(formattedDate);
                }
            } catch (ParseException e) {
                e.printStackTrace();
                holder.messageTime.setText("Error Parsing Date");
            }
        }


        Glide.with(holder.itemView.getContext())
                .load(chatItem.getAvatar())
                .placeholder(R.drawable.logor)
                .into(holder.avatar);
    }

    @Override
    public int getItemCount() {
        return chatItems.size();
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder {
        ImageView avatar;
        TextView senderName, messagePreview, messageTime;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.chat_avatar);
            senderName = itemView.findViewById(R.id.chat_sender_name);
            messagePreview = itemView.findViewById(R.id.chat_message_preview);
            messageTime = itemView.findViewById(R.id.chat_message_time);

            itemView.setOnClickListener(v -> {
                if (chatListClickListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        chatListClickListener.onChatListClickListener(chatItems.get(position));
                    }
                }
            });
        }
    }
}
