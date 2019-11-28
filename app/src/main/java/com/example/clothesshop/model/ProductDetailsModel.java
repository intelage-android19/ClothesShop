package com.example.clothesshop.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductDetailsModel {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("main_category_auto_id")
    @Expose
    private String mainCategoryAutoId;
    @SerializedName("main_category_id")
    @Expose
    private String mainCategoryId;
    @SerializedName("sub_category_auto_id")
    @Expose
    private String subCategoryAutoId;
    @SerializedName("sub_category_id")
    @Expose
    private String subCategoryId;
    @SerializedName("category_auto_id")
    @Expose
    private String categoryAutoId;
    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("main_category_name")
    @Expose
    private String mainCategoryName;
    @SerializedName("sub_category_name")
    @Expose
    private String subCategoryName;
    @SerializedName("category_name")
    @Expose
    private String categoryName;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("mrpprice")
    @Expose
    private String mrpprice;
    @SerializedName("discount")
    @Expose
    private String discount;
    @SerializedName("stock")
    @Expose
    private String stock;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("new_arrivals")
    @Expose
    private String newArrivals;
    @SerializedName("top_brand_auto_id")
    @Expose
    private String topBrandAutoId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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

    public String getSubCategoryAutoId() {
        return subCategoryAutoId;
    }

    public void setSubCategoryAutoId(String subCategoryAutoId) {
        this.subCategoryAutoId = subCategoryAutoId;
    }

    public String getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(String subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getCategoryAutoId() {
        return categoryAutoId;
    }

    public void setCategoryAutoId(String categoryAutoId) {
        this.categoryAutoId = categoryAutoId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getMainCategoryName() {
        return mainCategoryName;
    }

    public void setMainCategoryName(String mainCategoryName) {
        this.mainCategoryName = mainCategoryName;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMrpprice() {
        return mrpprice;
    }

    public void setMrpprice(String mrpprice) {
        this.mrpprice = mrpprice;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
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

    public String getNewArrivals() {
        return newArrivals;
    }

    public void setNewArrivals(String newArrivals) {
        this.newArrivals = newArrivals;
    }

    public String getTopBrandAutoId() {
        return topBrandAutoId;
    }

    public void setTopBrandAutoId(String topBrandAutoId) {
        this.topBrandAutoId = topBrandAutoId;
    }

}

