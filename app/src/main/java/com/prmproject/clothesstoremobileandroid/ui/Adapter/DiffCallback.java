package com.prmproject.clothesstoremobileandroid.ui.Adapter;

import androidx.recyclerview.widget.DiffUtil;

import com.prmproject.clothesstoremobileandroid.Data.model.ChatMessage;

public class DiffCallback<T> extends DiffUtil.ItemCallback<T> {

    @Override
    public boolean areItemsTheSame(T oldItem, T newItem) {
        if (oldItem instanceof ChatMessage && newItem instanceof ChatMessage) {
            ChatMessage oldMessage = (ChatMessage) oldItem;
            ChatMessage newMessage = (ChatMessage) newItem;
            return oldMessage.getMessageId() == newMessage.getMessageId();
        }
        return false;
    }

    @Override
    public boolean areContentsTheSame(T oldItem, T newItem) {
        if (oldItem instanceof ChatMessage && newItem instanceof ChatMessage) {
            ChatMessage oldMessage = (ChatMessage) oldItem;
            ChatMessage newMessage = (ChatMessage) newItem;
            return oldMessage.getContent().equals(newMessage.getContent());
        }
        return false;
    }
}
