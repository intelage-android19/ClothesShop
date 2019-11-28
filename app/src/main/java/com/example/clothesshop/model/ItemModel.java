package com.example.clothesshop.model;

public class ItemModel {
    String id,prodName,brand,soldBy;
    float price;
    String prodImg;

    public ItemModel(String id, String prodName, String brand, String soldBy, float price, String prodImg) {
        this.id = id;
        this.prodName = prodName;
        this.brand = brand;
        this.soldBy = soldBy;
        this.price = price;
        this.prodImg = prodImg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSoldBy() {
        return soldBy;
    }

    public void setSoldBy(String soldBy) {
        this.soldBy = soldBy;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getProdImg() {
        return prodImg;
    }

    public void setProdImg(String prodImg) {
        this.prodImg = prodImg;
    }
}
