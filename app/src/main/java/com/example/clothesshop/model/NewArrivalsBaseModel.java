package com.example.clothesshop.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewArrivalsBaseModel {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("newarrivals")
    @Expose
    private List<NewArrivalModel> newarrivals = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<NewArrivalModel> getNewarrivals() {
        return newarrivals;
    }

    public void setNewarrivals(List<NewArrivalModel> newarrivals) {
        this.newarrivals = newarrivals;
    }

}

