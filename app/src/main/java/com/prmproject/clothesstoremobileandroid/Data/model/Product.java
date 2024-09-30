package com.prmproject.clothesstoremobileandroid.Data.model;

import java.util.List;

public class Product {
    private int ProductId;
    private String Name;
    private String Img;
    private int Quantity;
    private String Description;
    private long NewPrice;
    private Long OldPrice;
    private long QuantitySold;
    private Integer CategoryId;
    private Integer SellerId;
    private Category Category;
    private Seller Seller;
    private List<Option> Options;
    private Float RatingPoint;
    private Integer RatingCount;

    public Product() {
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

    public String getImg() {
        return Img;
    }

    public void setImg(String img) {
        Img = img;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public long getNewPrice() {
        return NewPrice;
    }

    public void setNewPrice(long newPrice) {
        NewPrice = newPrice;
    }

    public Long getOldPrice() {
        return OldPrice;
    }

    public void setOldPrice(long oldPrice) {
        OldPrice = oldPrice;
    }

    public long getQuantitySold() {
        return QuantitySold;
    }

    public void setQuantitySold(long quantitySold) {
        QuantitySold = quantitySold;
    }

    public Integer getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(Integer categoryId) {
        CategoryId = categoryId;
    }

    public Integer getSellerId() {
        return SellerId;
    }

    public void setSellerId(Integer sellerId) {
        SellerId = sellerId;
    }

    public com.prmproject.clothesstoremobileandroid.Data.model.Category getCategory() {
        return Category;
    }

    public void setCategory(com.prmproject.clothesstoremobileandroid.Data.model.Category category) {
        Category = category;
    }

    public com.prmproject.clothesstoremobileandroid.Data.model.Seller getSeller() {
        return Seller;
    }

    public void setSeller(com.prmproject.clothesstoremobileandroid.Data.model.Seller seller) {
        Seller = seller;
    }

    public List<Option> getOptions() {
        return Options;
    }

    public void setOptions(List<Option> options) {
        Options = options;
    }

    public void setOldPrice(Long oldPrice) {
        OldPrice = oldPrice;
    }

    public Float getRatingPoint() {
        return RatingPoint;
    }

    public void setRatingPoint(Float ratingPoint) {
        RatingPoint = ratingPoint;
    }

    public Integer getRatingCount() {
        return RatingCount;
    }

    public void setRatingCount(Integer ratingCount) {
        RatingCount = ratingCount;
    }
}
