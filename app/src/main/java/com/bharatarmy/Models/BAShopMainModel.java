package com.bharatarmy.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BAShopMainModel {
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("ShopData")
    @Expose
    private List<BAShopListModel> shopData = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<BAShopListModel> getShopData() {
        return shopData;
    }

    public void setShopData(List<BAShopListModel> shopData) {
        this.shopData = shopData;
    }
}
