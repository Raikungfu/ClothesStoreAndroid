package com.prmproject.clothesstoremobileandroid.ui.order;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.prmproject.clothesstoremobileandroid.Data.model.OrderItem;
import com.prmproject.clothesstoremobileandroid.Data.model.dataToReceive.ListResponse;
import com.prmproject.clothesstoremobileandroid.Data.repository.OrderRepository;

public class OrdersDetailViewModel extends ViewModel {
    private final OrderRepository orderRepository;
    public OrdersDetailViewModel() {
        orderRepository = new OrderRepository();
    }
    public LiveData<ListResponse<OrderItem>> getOrderDetail(int orderId){
        return orderRepository.getOrderDetail(orderId);
    }
}