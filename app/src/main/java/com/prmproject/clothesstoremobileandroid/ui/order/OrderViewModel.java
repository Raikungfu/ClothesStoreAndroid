package com.prmproject.clothesstoremobileandroid.ui.order;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.prmproject.clothesstoremobileandroid.Data.model.dataToReceive.ListResponse;
import com.prmproject.clothesstoremobileandroid.Data.repository.OrderRepository;

public class OrderViewModel extends ViewModel {
    private final OrderRepository orderRepository;

    public OrderViewModel() {
        orderRepository = new OrderRepository();
    }

    public LiveData<ListResponse> getOrders(int userId) {
        return orderRepository.getOrders(userId);
    }
}
