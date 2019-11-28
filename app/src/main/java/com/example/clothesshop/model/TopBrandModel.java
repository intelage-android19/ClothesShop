package com.example.clothesshop.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TopBrandModel {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("main_category_auto_id")
    @Expose
    private String mainCategoryAutoId;
    @SerializedName("main_category_id")
    @Expose
    private String mainCategoryId;
    @SerializedName("main_category_name")
    @Expose
    private String mainCategoryName;
    @SerializedName("brand_image")
    @Expose
    private String brandImage;
    @SerializedName("brand_name")
    @Expose
    private String brandName;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("created_at")
    @Expose
    private String createdAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMainCategoryAutoId() {
        return mainCategoryAutoId;
    }

    public void setMainCategoryAutoId(String mainCategoryAutoId) {
        this.mainCategoryAutoId = mainCategoryAutoId;
    }

    public String getMainCategoryId() {
        return mainCategoryId;
    }

    public void setMainCategoryId(String mainCategoryId) {
        this.mainCategoryId = mainCategoryId;
    }

    public String getMainCategoryName() {
        return mainCategoryName;
    }

    public void setMainCategoryName(String mainCategoryName) {
        this.mainCategoryName = mainCategoryName;
    }

    public String getBrandImage() {
        return brandImage;
    }

    public void setBrandImage(String brandImage) {
        this.brandImage = brandImage;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

}
