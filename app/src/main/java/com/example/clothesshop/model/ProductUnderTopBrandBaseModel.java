package com.example.clothesshop.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductUnderTopBrandBaseModel {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("brandProducts")
    @Expose
    private List<ProductUnderTopBrandModel> brandProducts = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<ProductUnderTopBrandModel> getBrandProducts() {
        return brandProducts;
    }

    public void setBrandProducts(List<ProductUnderTopBrandModel> brandProducts) {
        this.brandProducts = brandProducts;
    }


}
