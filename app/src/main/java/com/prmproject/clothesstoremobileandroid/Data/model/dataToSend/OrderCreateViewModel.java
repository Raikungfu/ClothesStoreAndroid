package com.prmproject.clothesstoremobileandroid.Data.model.dataToSend;

public class OrderCreateViewModel {
    private String DiscountCode;

    public String getPaymentMethod() {
        return PaymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        PaymentMethod = paymentMethod;
    }

    private  String PaymentMethod;

    public String getDiscountCode() {
        return DiscountCode;
    }

    public void setDiscountCode(String discountCode) {
        DiscountCode = discountCode;
    }
}
