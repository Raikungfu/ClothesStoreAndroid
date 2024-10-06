package com.prmproject.clothesstoremobileandroid.ui.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.RecyclerView;

import com.prmproject.clothesstoremobileandroid.Data.model.ChatMessage;
import com.prmproject.clothesstoremobileandroid.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    private final AsyncListDiffer<ChatMessage> differ;

    public MessageAdapter() {
        differ = new AsyncListDiffer<>(this, new DiffCallback<>());
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {
        private final TextView messageContent;
        private final TextView messageTime;
        private final LinearLayout messageContainer;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            messageContainer = itemView.findViewById(R.id.messageContainer);
            messageContent = itemView.findViewById(R.id.messageContent);
            messageTime = itemView.findViewById(R.id.messageTime);
        }
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_chat_message, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        ChatMessage message = differ.getCurrentList().get(position);
        if(message != null){
            holder.messageContent.setText(message.getContent());

            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS", Locale.getDefault());
            SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy", Locale.getDefault());

            String latestMessageTime = message.getTimestamp();
            if (latestMessageTime != null && !latestMessageTime.isEmpty()) {
                try {
                    Date date = inputFormat.parse(latestMessageTime);
                    if (date != null) {
                        String formattedDate = outputFormat.format(date);
                        holder.messageTime.setText(formattedDate);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                    holder.messageTime.setText("");
                }
            }

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) holder.messageContainer.getLayoutParams();

            int leftMargin = 0;
            int rightMargin = 0;

            if (message.isSender()) {
                leftMargin = 200;
                holder.messageContainer.setBackgroundResource(R.color.colorPrimary);
                holder.messageContent.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.textPrimary));
                holder.messageTime.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.textPrimary));
            } else {
                rightMargin = 200;
                holder.messageContainer.setBackgroundResource(R.color.colorSecondary);
                holder.messageContent.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.textSecondary));
                holder.messageTime.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.textSecondary));
            }

            params.setMargins(leftMargin, params.topMargin, rightMargin, params.bottomMargin);
            holder.messageContainer.setLayoutParams(params);

        }
    }

    @Override
    public int getItemCount() {
        return differ.getCurrentList().size();
    }

    public void submitList(List<ChatMessage> newChatMessages, Runnable onListChanged) {
        differ.submitList(newChatMessages, onListChanged);
    }
}
