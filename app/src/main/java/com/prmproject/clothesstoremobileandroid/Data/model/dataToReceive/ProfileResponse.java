package com.prmproject.clothesstoremobileandroid.Data.model.dataToReceive;

import com.prmproject.clothesstoremobileandroid.Data.model.Admin;
import com.prmproject.clothesstoremobileandroid.Data.model.Customer;
import com.prmproject.clothesstoremobileandroid.Data.model.Seller;
import com.prmproject.clothesstoremobileandroid.Data.model.User;

public class ProfileResponse {
    private User User;
    private Customer Customer;
    private Seller Seller;
    private Admin Admin;

    public User getUser() {
        return User;
    }

    public void setUser(User user) {
        User = user;
    }

    public Customer getCustomer() {
        return Customer;
    }

    public void setCustomer(Customer customer) {
        Customer = customer;
    }

    public Seller getSeller() {
        return Seller;
    }

    public void setSeller(Seller seller) {
        Seller = seller;
    }

    public Admin getAdmin() {
        return Admin;
    }

    public void setAdmin(Admin admin) {
        Admin = admin;
    }
}
