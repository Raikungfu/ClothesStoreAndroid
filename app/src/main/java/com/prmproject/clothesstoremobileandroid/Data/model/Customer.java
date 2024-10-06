package com.prmproject.clothesstoremobileandroid.Data.model;
import java.util.List;

public class Customer {
    private int CustomerId;
    private String Name;
    private String Address;
    private String Avt;
    private int UserId;
    private User User;
    private List<Cart> Carts;
    private List<Order> Orders;

    public int getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(int customerId) {
        CustomerId = customerId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getAvt() {
        return Avt;
    }

    public void setAvt(String avt) {
        Avt = avt;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public com.prmproject.clothesstoremobileandroid.Data.model.User getUser() {
        return User;
    }

    public void setUser(com.prmproject.clothesstoremobileandroid.Data.model.User user) {
        User = user;
    }

    public List<Cart> getCarts() {
        return Carts;
    }

    public void setCarts(List<Cart> carts) {
        Carts = carts;
    }

    public List<Order> getOrders() {
        return Orders;
    }

    public void setOrders(List<Order> orders) {
        Orders = orders;
    }
}

