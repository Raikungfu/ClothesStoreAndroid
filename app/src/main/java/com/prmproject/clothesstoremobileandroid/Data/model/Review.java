package com.prmproject.clothesstoremobileandroid.Data.model;

public class Review {
    private int ReviewId;
    private int ProductId;
    private int OrderId;
    private int CustomerId;
    private int Rating;
    private String Comment;
    private Product Product;
    private Customer Customer;
    private Order Order;

    public int getReviewId() {
        return ReviewId;
    }

    public void setReviewId(int reviewId) {
        ReviewId = reviewId;
    }

    public int getProductId() {
        return ProductId;
    }

    public void setProductId(int productId) {
        ProductId = productId;
    }

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

    public int getRating() {
        return Rating;
    }

    public void setRating(int rating) {
        Rating = rating;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public com.prmproject.clothesstoremobileandroid.Data.model.Product getProduct() {
        return Product;
    }

    public void setProduct(com.prmproject.clothesstoremobileandroid.Data.model.Product product) {
        Product = product;
    }

    public com.prmproject.clothesstoremobileandroid.Data.model.Customer getCustomer() {
        return Customer;
    }

    public void setCustomer(com.prmproject.clothesstoremobileandroid.Data.model.Customer customer) {
        Customer = customer;
    }

    public com.prmproject.clothesstoremobileandroid.Data.model.Order getOrder() {
        return Order;
    }

    public void setOrder(com.prmproject.clothesstoremobileandroid.Data.model.Order order) {
        Order = order;
    }
}

