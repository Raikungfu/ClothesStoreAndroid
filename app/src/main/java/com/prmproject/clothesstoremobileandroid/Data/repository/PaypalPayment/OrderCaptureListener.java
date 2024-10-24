package com.prmproject.clothesstoremobileandroid.Data.repository.PaypalPayment;

import org.json.JSONObject;

public interface OrderCaptureListener {
    void onSuccess(JSONObject response);
    void onError(String error);
}