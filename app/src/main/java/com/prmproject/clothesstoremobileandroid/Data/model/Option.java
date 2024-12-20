package com.prmproject.clothesstoremobileandroid.Data.model;

public class Option {
    private int OptionId;
    private int OptionGroupId;
    private int ProductId;
    private String Name;
    private float Price;
    private ProductOption ProductOptions;
    private Product Product;
    private String ProductOption;

    public int getOptionId() {
        return OptionId;
    }

    public void setOptionId(int optionId) {
        OptionId = optionId;
    }

    public int getOptionGroupId() {
        return OptionGroupId;
    }

    public void setOptionGroupId(int optionGroupId) {
        OptionGroupId = optionGroupId;
    }

    public int getProductId() {
        return ProductId;
    }

    public void setProductId(int productId) {
        ProductId = productId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public float getPrice() {
        return Price;
    }

    public void setPrice(float price) {
        Price = price;
    }

    public ProductOption getProductOptions() {
        return ProductOptions;
    }

    public void setProductOptions(ProductOption productOptions) {
        ProductOptions = productOptions;
    }

    public Product getProduct() {
        return Product;
    }

    public void setProduct(Product product) {
        Product = product;
    }

    public String getProductOption() {
        return ProductOption;
    }

    public void setProductOption(String productOption) {
        ProductOption = productOption;
    }
}
