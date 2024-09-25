package com.prmproject.clothesstoremobileandroid.Data.model;

import java.util.List;

public class Category {
    private int categoryId;
    private String name;
    private String img;
    private List<Product> products;

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
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

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
// Getters and Setters
}
