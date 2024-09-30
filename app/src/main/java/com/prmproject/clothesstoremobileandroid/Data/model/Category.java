package com.prmproject.clothesstoremobileandroid.Data.model;

import java.util.List;

public class Category {
    private int CategoryId;
    private String Name;
    private String Img;
    private List<Product> Products;

    public int getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(int categoryId) {
        this.CategoryId = categoryId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getImg() {
        return Img;
    }

    public void setImg(String img) {
        this.Img = img;
    }

    public List<Product> getProducts() {
        return Products;
    }

    public void setProducts(List<Product> products) {
        this.Products = products;
    }
}
