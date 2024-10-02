package com.prmproject.clothesstoremobileandroid.Data.model;
public class CartItem {
    private int CartItemId;
    private int Quantity;
    private int CartId;
    private int ProductId;
    private Cart Cart;
    private Product Product;

    public int getCartItemId() {
        return CartItemId;
    }

    public void setCartItemId(int cartItemId) {
        CartItemId = cartItemId;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public int getCartId() {
        return CartId;
    }

    public void setCartId(int cartId) {
        CartId = cartId;
    }

    public int getProductId() {
        return ProductId;
    }

    public void setProductId(int productId) {
        ProductId = productId;
    }

    public com.prmproject.clothesstoremobileandroid.Data.model.Cart getCart() {
        return Cart;
    }

    public void setCart(com.prmproject.clothesstoremobileandroid.Data.model.Cart cart) {
        Cart = cart;
    }

    public com.prmproject.clothesstoremobileandroid.Data.model.Product getProduct() {
        return Product;
    }

    public void setProduct(com.prmproject.clothesstoremobileandroid.Data.model.Product product) {
        Product = product;
    }
}

