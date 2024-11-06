package com.prmproject.clothesstoremobileandroid.ui.bag;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.prmproject.clothesstoremobileandroid.Data.model.CartItem;
import com.prmproject.clothesstoremobileandroid.Data.model.Order;
import com.prmproject.clothesstoremobileandroid.Data.model.OrderItem;
import com.prmproject.clothesstoremobileandroid.Data.model.dataToReceive.ListResponse;
import com.prmproject.clothesstoremobileandroid.Data.model.dataToReceive.ObjectResponse;
import com.prmproject.clothesstoremobileandroid.Data.model.dataToSend.OrderCreateViewModel;
import com.prmproject.clothesstoremobileandroid.Data.repository.CartRepository;
import com.prmproject.clothesstoremobileandroid.Data.repository.OrderRepository;

public class BagViewModel extends ViewModel {

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;

    public BagViewModel() {
        cartRepository = new CartRepository();
        orderRepository=new OrderRepository();
    }
    public LiveData<ListResponse<CartItem>> getCartItems(){
        return cartRepository.getCartItems();
    }
    public  LiveData<ObjectResponse<Order>> CreateOrder(OrderCreateViewModel orderCreateViewModel) {
        return cartRepository.createOrder(orderCreateViewModel);
    }

    public LiveData<ObjectResponse> updateStatus(int orderId, String newStatus) {
        return orderRepository.updateStatus(orderId, newStatus);
    }
}