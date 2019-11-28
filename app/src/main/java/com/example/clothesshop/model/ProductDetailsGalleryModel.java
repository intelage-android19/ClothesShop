package com.example.clothesshop.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductDetailsGalleryModel {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("product_auto_id")
    @Expose
    private String productAutoId;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("created_at")
    @Expose
    private String createdAt;

    public ProductDetailsGalleryModel(String id, String productAutoId, String image, String updatedAt, String createdAt) {
        this.id = id;
        this.productAutoId = productAutoId;
        this.image = image;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductAutoId() {
        return productAutoId;
    }

    public void setProductAutoId(String productAutoId) {
        this.productAutoId = productAutoId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
