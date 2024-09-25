package com.prmproject.clothesstoremobileandroid.Data.model;

import java.util.List;

public class ProductOption {
    private int productOptionsId;
    private String name;
    private List<Option> options;

    public int getProductOptionsId() {
        return productOptionsId;
    }

    public void setProductOptionsId(int productOptionsId) {
        this.productOptionsId = productOptionsId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }
}

