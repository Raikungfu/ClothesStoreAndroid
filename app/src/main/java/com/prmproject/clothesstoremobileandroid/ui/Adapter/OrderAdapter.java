package com.prmproject.clothesstoremobileandroid.ui.Adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import com.prmproject.clothesstoremobileandroid.Data.model.Order;
import com.prmproject.clothesstoremobileandroid.R;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private List<Order> orderList;

    public OrderAdapter(List<Order> orderList) {
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_order_item, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderList.get(position);
        if (order != null) {
            holder.txtOrderNumber.setText("Order ID :"+String.valueOf(order.getOrderId()));
            holder.txtStatus.setText(order.getStatus());
            holder.txtOrderDate.setText(order.getOrderDate().toString());
            holder.txtTotalAmountValue.setText("$" + String.valueOf(order.getTotalAmount()));

            holder.detailButton.setOnClickListener(v -> {
                Bundle bundle = new Bundle();
                bundle.putInt("orderId", order.getOrderId());
                bundle.putString("orderDate", order.getOrderDate());
                bundle.putDouble("totalAmount", order.getTotalAmount());
                bundle.putString("status", order.getStatus());
                bundle.putString("shipAddress", order.getShipAddress());
                bundle.putString("paymentMethod", order.getPaymentMethod());
                bundle.putDouble("shipFee", 0);
                bundle.putString("discountCode", order.getDiscountCode());
                Navigation.findNavController(v).navigate(R.id.action_orderFragment_to_orderDetailFragment, bundle);
            });
        }
    }

    @Override
    public int getItemCount() {
        return orderList != null ? orderList.size() : 0;
    }

    public void updateOrders(List<Order> newOrderList) {
        this.orderList = newOrderList;
        notifyDataSetChanged();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView txtOrderNumber, txtStatus, txtOrderDate, txtTotalAmountValue;
        Button detailButton;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            txtOrderNumber = itemView.findViewById(R.id.txt_order_number);
            txtStatus = itemView.findViewById(R.id.txt_status);
            txtOrderDate = itemView.findViewById(R.id.txt_order_date);
            txtTotalAmountValue = itemView.findViewById(R.id.txt_total_amount_value);
            detailButton = itemView.findViewById(R.id.detail_btn);
        }
    }
}
