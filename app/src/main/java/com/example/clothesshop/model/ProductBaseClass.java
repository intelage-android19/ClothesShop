package com.example.clothesshop.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductBaseClass {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("allProducts")
    @Expose
    private List<ProductModel> allProducts = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<ProductModel> getAllProducts() {
        return allProducts;
    }

    public void setAllProducts(List<ProductModel> allProducts) {
        this.allProducts = allProducts;
    }
}