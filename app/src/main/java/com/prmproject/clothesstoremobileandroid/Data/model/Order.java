package com.prmproject.clothesstoremobileandroid.Data.model;

import java.util.Date;

public class Order {
    private int OrderId;
    private int CustomerId;
    private String ShipName;
    private String ShipMail;
    private String ShipPhone;
    private String ShipAddress;
    private String OrderDate;
    private int ShipFee;
    private String DiscountCode;
    private double TotalAmount;
    private String PaymentMethod;
    private String Status;
    private Customer Customer;

    public int getOrderId() {
        return OrderId;
    }

    public void setOrderId(int orderId) {
        OrderId = orderId;
    }

    public int getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(int customerId) {
        CustomerId = customerId;
    }

    public String getShipName() {
        return ShipName;
    }

    public void setShipName(String shipName) {
        ShipName = shipName;
    }

    public String getShipMail() {
        return ShipMail;
    }

    public void setShipMail(String shipMail) {
        ShipMail = shipMail;
    }

    public String getShipPhone() {
        return ShipPhone;
    }

    public void setShipPhone(String shipPhone) {
        ShipPhone = shipPhone;
    }

    public String getShipAddress() {
        return ShipAddress;
    }

    public void setShipAddress(String shipAddress) {
        ShipAddress = shipAddress;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(String orderDate) {
        OrderDate = orderDate;
    }

    public int getShipFee() {
        return ShipFee;
    }

    public void setShipFee(int shipFee) {
        ShipFee = shipFee;
    }

    public String getDiscountCode() {
        return DiscountCode;
    }

    public void setDiscountCode(String discountCode) {
        DiscountCode = discountCode;
    }

    public double getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        TotalAmount = totalAmount;
    }

    public String getPaymentMethod() {
        return PaymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        PaymentMethod = paymentMethod;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public com.prmproject.clothesstoremobileandroid.Data.model.Customer getCustomer() {
        return Customer;
    }

    public void setCustomer(com.prmproject.clothesstoremobileandroid.Data.model.Customer customer) {
        Customer = customer;
    }
}
