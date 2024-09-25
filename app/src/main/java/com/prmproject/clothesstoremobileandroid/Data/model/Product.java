package com.prmproject.clothesstoremobileandroid.Data.model;

import java.util.List;

public class Product {
    private int productId;
    private String name;
    private String img;
    private int quantity;
    private String description;
    private long newPrice;
    private long oldPrice;
    private long quantitySold;
    private int categoryId;
    private int sellerId;
    private Category category;
    private Seller seller;
    private List<Option> options;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(long newPrice) {
        this.newPrice = newPrice;
    }

    public long getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(long oldPrice) {
        this.oldPrice = oldPrice;
    }

    public long getQuantitySold() {
        return quantitySold;
    }

    public void setQuantitySold(long quantitySold) {
        this.quantitySold = quantitySold;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }
}
