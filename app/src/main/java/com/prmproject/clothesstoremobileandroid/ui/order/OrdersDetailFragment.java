package com.prmproject.clothesstoremobileandroid.ui.order;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.prmproject.clothesstoremobileandroid.Data.model.Order;
import com.prmproject.clothesstoremobileandroid.R;
import com.prmproject.clothesstoremobileandroid.Data.repository.OrderRepository; // Assuming you have a repository to get the order data


//    private TextView txtOrderNumber, txtOrderDate, txtTotalAmountValue, txtStatus;
//    private TextView address, payment, shipFee, discount, totalAmount;
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_orders_detail, container, false);
//
//        // Initialize TextViews
//        txtOrderNumber = view.findViewById(R.id.txt_order_number);
//        txtOrderDate = view.findViewById(R.id.txt_order_date);
//        txtTotalAmountValue = view.findViewById(R.id.txt_total_amount_value);
//        txtStatus = view.findViewById(R.id.txt_status);
//        address = view.findViewById(R.id.address);
//        payment = view.findViewById(R.id.payment);
//        shipFee = view.findViewById(R.id.shipFee);
//        discount = view.findViewById(R.id.discount);
//        totalAmount = view.findViewById(R.id.totalAmount);
//
//        // Get the order ID from the arguments
//        if (getArguments() != null) {
//            int orderId = getArguments().getInt("orderId");
//
//            // Fetch the order details using the repository (implement the method in your repository)
//            Order order = OrderViewModel.
//
//            // Display the order details
//            if (order != null) {
//                displayOrderDetails(order);
//            }
//        }
//
//        return view;
//    }
//
//    private void displayOrderDetails(Order order) {
//        txtOrderNumber.setText(String.valueOf(order.getOrderId()));
//        txtOrderDate.setText(order.getOrderDate());
//        txtTotalAmountValue.setText("$" + order.getTotalAmount());
//        txtStatus.setText(order.getStatus());
//        address.setText(order.getShipAddress());
//        payment.setText(order.getPaymentMethod());
//        shipFee.setText("$" + order.getShipFee());
//        discount.setText(order.getDiscountCode() != null ? order.getDiscountCode() : "No discount");
//        totalAmount.setText("$" + order.getTotalAmount()); // Adjust if necessary
//    }
//}
public class OrdersDetailFragment extends Fragment {

    private TextView txtOrderNumber, txtOrderDate, txtTotalAmount, txtStatus, txtAddress, txtPayment, txtShipFee, txtDiscount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_orders_detail, container, false);

        // Initialize views
        txtOrderNumber = view.findViewById(R.id.txt_order_number);
        txtOrderDate = view.findViewById(R.id.txt_order_date);
        txtTotalAmount = view.findViewById(R.id.txt_total_amount_value);
        txtStatus = view.findViewById(R.id.txt_status);
        txtAddress = view.findViewById(R.id.address);
        txtPayment = view.findViewById(R.id.payment);
        txtShipFee = view.findViewById(R.id.shipFee);
        txtDiscount = view.findViewById(R.id.discount);

        Bundle bundle = getArguments();
        if (bundle != null) {
            int orderId = bundle.getInt("orderId");
            String orderDate = bundle.getString("orderDate");
            double totalAmount = bundle.getDouble("totalAmount");
            String status = bundle.getString("status");
            String shipAddress = bundle.getString("shipAddress");
            String paymentMethod = bundle.getString("paymentMethod");
            double shipFee = bundle.getDouble("shipFee");
            String discountCode = bundle.getString("discountCode");

            // Set the retrieved data to the respective views
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
}
