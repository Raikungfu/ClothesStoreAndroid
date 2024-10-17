package com.prmproject.clothesstoremobileandroid.ui.order;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.prmproject.clothesstoremobileandroid.R;
import com.prmproject.clothesstoremobileandroid.Data.model.OrderItem;
import com.prmproject.clothesstoremobileandroid.Data.model.dataToReceive.ListResponse;
import com.prmproject.clothesstoremobileandroid.ui.Adapter.OrderItemAdapter;
import com.prmproject.clothesstoremobileandroid.ui.order.OrdersDetailViewModel;

import java.util.List;

public class OrdersDetailFragment extends Fragment {
    private OrdersDetailViewModel ordersDetailViewModel;
    private OrderItemAdapter orderDetailAdapter;
    private RecyclerView recyclerView;
    private TextView txtOrderNumber, txtOrderDate, txtTotalAmount, txtStatus, txtAddress, txtPayment, txtShipFee, txtDiscount;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders_detail, container, false);
        recyclerView = view.findViewById(R.id.listViewProduct);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ordersDetailViewModel = new ViewModelProvider(this).get(OrdersDetailViewModel.class);

        txtOrderNumber = view.findViewById(R.id.txt_order_number);
        txtOrderDate = view.findViewById(R.id.txt_order_date);
        txtTotalAmount = view.findViewById(R.id.txt_total_amount_value);
        txtStatus = view.findViewById(R.id.txt_status);
        txtAddress = view.findViewById(R.id.address);
        txtPayment = view.findViewById(R.id.payment);
        txtShipFee = view.findViewById(R.id.shipFee);
        txtDiscount = view.findViewById(R.id.discount);

        Bundle args = getArguments();
        if (args != null) {
            int orderId = args.getInt("orderId");
            String orderDate =args.getString("orderDate");
            double totalAmount = args.getDouble("totalAmount");
            String status = args.getString("status");
            String shipAddress = args.getString("shipAddress");
            String paymentMethod = args.getString("paymentMethod");
            double shipFee = args.getDouble("shipFee");
            String discountCode = args.getString("discountCode");
            ordersDetailViewModel.getOrderDetail(orderId).observe(getViewLifecycleOwner(), this::updateUI);


                        txtOrderNumber.setText(String.valueOf(orderId));
            txtOrderDate.setText(orderDate);
            txtTotalAmount.setText("$" + totalAmount);
            txtStatus.setText(status);
            txtAddress.setText(shipAddress);
            txtPayment.setText(paymentMethod);
            txtShipFee.setText("$" + shipFee);
            txtDiscount.setText(discountCode != null ? discountCode : "No Discount");


        }

        return view;
    }

    private void updateUI(ListResponse<OrderItem> orderItemListResponse) {
        if (orderItemListResponse != null && orderItemListResponse.getItems() != null) {
            List<OrderItem> orderItems = orderItemListResponse.getItems();
            orderDetailAdapter = new OrderItemAdapter(orderItems);
            recyclerView.setAdapter(orderDetailAdapter);
        } else {
            Toast.makeText(getContext(), "Failed to load order items", Toast.LENGTH_SHORT).show();
        }
    }
}
