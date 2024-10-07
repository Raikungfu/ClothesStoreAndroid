package com.prmproject.clothesstoremobileandroid.Data.model.dataToReceive;

import com.prmproject.clothesstoremobileandroid.Data.model.Category;
import com.prmproject.clothesstoremobileandroid.Data.model.ProductOption;

import java.util.List;

public class FilterListResponse {
    private List<Category> categoryList;
    private List<ProductOption> productOptionsList;

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public List<ProductOption> getProductOptionsList() {
        return productOptionsList;
    }

    public void setProductOptionsList(List<ProductOption> productOptionsList) {
        this.productOptionsList = productOptionsList;
    }
}
