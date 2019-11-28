package com.example.clothesshop.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SubCategoryBaseModel {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("allSubcategories")
    @Expose
    private List<SubCategoryModel> allSubcategories = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<SubCategoryModel> getAllSubcategories() {
        return allSubcategories;
    }

    public void setAllSubcategories(List<SubCategoryModel> allSubcategories) {
        this.allSubcategories = allSubcategories;
    }

}