package com.prmproject.clothesstoremobileandroid.Data.model;

import java.util.List;

public class ProductOption {
    private int ProductOptionsId;
    private String Name;
    private List<Option> Options;
    private String NameDescription;

    public int getProductOptionsId() {
        return ProductOptionsId;
    }

    public void setProductOptionsId(int productOptionsId) {
        ProductOptionsId = productOptionsId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public List<Option> getOptions() {
        return Options;
    }

    public void setOptions(List<Option> options) {
        Options = options;
    }

    public String getNameDescription() {
        return NameDescription;
    }

    public void setNameDescription(String nameDescription) {
        NameDescription = nameDescription;
    }
}

