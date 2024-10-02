package com.prmproject.clothesstoremobileandroid.Data.model;

import java.util.List;

public class Seller {
    private int SellerId;
    private String Avt;
    private String Cover;
    private String CompanyName;
    private String Address;
    private String Description;
    private int UserId;
    private User User;
    private List<Product> Products;

    public int getSellerId() {
        return SellerId;
    }

    public void setSellerId(int sellerId) {
        SellerId = sellerId;
    }

    public String getAvt() {
        return Avt;
    }

    public void setAvt(String avt) {
        Avt = avt;
    }

    public String getCover() {
        return Cover;
    }

    public void setCover(String cover) {
        Cover = cover;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
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

    public List<Product> getProducts() {
        return Products;
    }

    public void setProducts(List<Product> products) {
        Products = products;
    }
}
