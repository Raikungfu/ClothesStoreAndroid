package com.prmproject.clothesstoremobileandroid.Data.model;
import java.util.List;

public class Cart {
    private int CartId;
    private int CustomerId;
    private Customer Customer;
    private List<Product> Products;

    public int getCartId() {
        return CartId;
    }

    public void setCartId(int cartId) {
        CartId = cartId;
    }

    public int getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(int customerId) {
        CustomerId = customerId;
    }

    public com.prmproject.clothesstoremobileandroid.Data.model.Customer getCustomer() {
        return Customer;
    }

    public void setCustomer(com.prmproject.clothesstoremobileandroid.Data.model.Customer customer) {
        Customer = customer;
    }

    public List<Product> getProducts() {
        return Products;
    }

    public void setProducts(List<Product> products) {
        Products = products;
    }
}

