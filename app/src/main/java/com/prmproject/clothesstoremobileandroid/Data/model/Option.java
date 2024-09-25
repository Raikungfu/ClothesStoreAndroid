package com.prmproject.clothesstoremobileandroid.Data.model;

public class Option {
    private int optionId;
    private int optionGroupId;
    private int productId;
    private String name;
    private float price;
    private ProductOption productOptions;
    private Product product;

    public int getOptionId() {
        return optionId;
    }

    public void setOptionId(int optionId) {
        this.optionId = optionId;
    }

    public int getOptionGroupId() {
        return optionGroupId;
    }

    public void setOptionGroupId(int optionGroupId) {
        this.optionGroupId = optionGroupId;
    }

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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public ProductOption getProductOptions() {
        return productOptions;
    }

    public void setProductOptions(ProductOption productOptions) {
        this.productOptions = productOptions;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
