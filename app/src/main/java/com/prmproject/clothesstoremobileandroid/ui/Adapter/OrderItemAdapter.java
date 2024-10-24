package com.prmproject.clothesstoremobileandroid.ui.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.prmproject.clothesstoremobileandroid.Data.model.OrderItem; // Thay đổi đường dẫn nếu cần
import com.prmproject.clothesstoremobileandroid.R;
import java.util.List;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.ViewHolder> {

    private List<OrderItem> orderItems;

    public OrderItemAdapter(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_orders_detail_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderItem orderItem = orderItems.get(position);
        holder.orderProductName.setText(orderItem.getProduct().getName());
        holder.orderProductQuantity.setText("Quantity: " + orderItem.getQuantity());
        holder.orderProductDescription.setText(orderItem.getProduct().getDescription());
        holder.orderProductPrice.setText("$" + orderItem.getProduct().getNewPrice());

         Glide.with(holder.itemView.getContext()).load(orderItem.getProduct().getImg()).into(holder.orderProductImage);
    }

    @Override
    public int getItemCount() {
        return orderItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView orderProductName, orderProductQuantity, orderProductDescription, orderProductPrice;
        ImageView orderProductImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            orderProductName = itemView.findViewById(R.id.OrderProductName);
            orderProductQuantity = itemView.findViewById(R.id.OrderProductQuantity);
            orderProductDescription = itemView.findViewById(R.id.OrderProductDescription);
            orderProductPrice = itemView.findViewById(R.id.OrderProductPrice);
            orderProductImage = itemView.findViewById(R.id.OrderProductImage);
        }
    }
}
