package com.prmproject.clothesstoremobileandroid.Data.model.dataToReceive;

import com.prmproject.clothesstoremobileandroid.Data.model.Admin;
import com.prmproject.clothesstoremobileandroid.Data.model.Customer;
import com.prmproject.clothesstoremobileandroid.Data.model.Seller;
import com.prmproject.clothesstoremobileandroid.Data.model.User;
import com.google.gson.annotations.SerializedName;

public class ProfileResponse {
    @SerializedName("User")
    private User user;

    @SerializedName("Customer")
    private Customer customer;

    @SerializedName("Seller")
    private Seller seller;

    @SerializedName("Admin")
    private Admin admin;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }
}
