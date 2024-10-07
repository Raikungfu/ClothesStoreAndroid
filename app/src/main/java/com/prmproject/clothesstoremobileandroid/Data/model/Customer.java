package com.prmproject.clothesstoremobileandroid.Data.model;
import java.util.List;

public class Customer {
    private int CustomerId;
    private String Name;
    private String Address;
    private String Avt;
    private int UserId;
    private User user;
    private List<Cart> carts;
    private List<Order> orders;

    public int getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(int customerId) {
        this.CustomerId = customerId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        this.Address = address;
    }

    public String getAvt() {
        return Avt;
    }

    public void setAvt(String avt) {
        this.Avt = avt;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        this.UserId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Cart> getCarts() {
        return carts;
    }

    public void setCarts(List<Cart> carts) {
        this.carts = carts;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
// Getters and Setters
}

