package com.example.clothesshop.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductDetailsBaseClass {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("get_product")
    @Expose
    private List<ProductDetailsModel> getProduct = null;
    @SerializedName("product_gallery")
    @Expose
    private List<ProductDetailsGalleryModel> productGallery = null;
    @SerializedName("product_specifications")
    @Expose
    private List<ProductDetailsSpcificationModel> productSpecifications = null;
    @SerializedName("product_sizes")
    @Expose
    private List<ProductDetailsSizweModel> productSizes = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<ProductDetailsModel> getGetProduct() {
        return getProduct;
    }

    public void setGetProduct(List<ProductDetailsModel> getProduct) {
        this.getProduct = getProduct;
    }

    public List<ProductDetailsGalleryModel> getProductGallery() {
        return productGallery;
    }

    public void setProductGallery(List<ProductDetailsGalleryModel> productGallery) {
        this.productGallery = productGallery;
    }

    public List<ProductDetailsSpcificationModel> getProductSpecifications() {
        return productSpecifications;
    }

    public void setProductSpecifications(List<ProductDetailsSpcificationModel> productSpecifications) {
        this.productSpecifications = productSpecifications;
    }

    public List<ProductDetailsSizweModel> getProductSizes() {
        return productSizes;
    }

    public void setProductSizes(List<ProductDetailsSizweModel> productSizes) {
        this.productSizes = productSizes;
    }

}
