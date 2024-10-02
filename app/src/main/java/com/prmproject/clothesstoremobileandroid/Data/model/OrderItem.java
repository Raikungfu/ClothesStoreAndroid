package com.prmproject.clothesstoremobileandroid.Data.model;

public class OrderItem {
    private int OrderItemId;
    private int OrderId;
    private int ProductId;
    private String Note;
    private int Quantity;
    private Order Order;
    private Product Product;

    public int getOrderItemId() {
        return OrderItemId;
    }

    public void setOrderItemId(int orderItemId) {
        OrderItemId = orderItemId;
    }

    public int getOrderId() {
        return OrderId;
    }

    public void setOrderId(int orderId) {
        OrderId = orderId;
    }

    public int getProductId() {
        return ProductId;
    }

    public void setProductId(int productId) {
        ProductId = productId;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public com.prmproject.clothesstoremobileandroid.Data.model.Order getOrder() {
        return Order;
    }

    public void setOrder(com.prmproject.clothesstoremobileandroid.Data.model.Order order) {
        Order = order;
    }

    public com.prmproject.clothesstoremobileandroid.Data.model.Product getProduct() {
        return Product;
    }

    public void setProduct(com.prmproject.clothesstoremobileandroid.Data.model.Product product) {
        Product = product;
    }
}
