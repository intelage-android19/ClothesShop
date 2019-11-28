package com.example.clothesshop.model;

public class CartModel {
    String id,prodName,brand,soldBy,size;
    float price;
    int quantity;
    String prodImg;

    public CartModel(String id, String prodName, String brand, String soldBy, float price, int quantity, String prodImg,String size) {
        this.id = id;
        this.prodName = prodName;
        this.brand = brand;
        this.soldBy = soldBy;
        this.price = price;
        this.quantity = quantity;
        this.prodImg = prodImg;
        this.size=size;
    }

    public CartModel() {
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProdImg() {
        return prodImg;
    }

    public void setProdImg(String prodImg) {
        this.prodImg = prodImg;
    }
}
