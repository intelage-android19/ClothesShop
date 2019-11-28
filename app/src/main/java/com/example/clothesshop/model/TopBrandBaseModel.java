package com.example.clothesshop.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TopBrandBaseModel {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("topBrands")
    @Expose
    private List<TopBrandModel> topBrands = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<TopBrandModel> getTopBrands() {
        return topBrands;
    }

    public void setTopBrands(List<TopBrandModel> topBrands) {
        this.topBrands = topBrands;
    }

}
