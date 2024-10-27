package com.prmproject.clothesstoremobileandroid.Data.repository;

import com.prmproject.clothesstoremobileandroid.Data.model.Order;

public interface OrderCallback {
    void onOrderCreated(Order orderResponse);
}
