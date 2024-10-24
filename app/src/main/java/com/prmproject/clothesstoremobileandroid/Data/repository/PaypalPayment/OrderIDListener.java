package com.prmproject.clothesstoremobileandroid.Data.repository.PaypalPayment;

public interface OrderIDListener {
    void onOrderCreated(String orderId);
    void onError(String error);
}
