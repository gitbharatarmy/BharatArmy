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
    @SerializedName("BAPollData")
    @Expose
    private List<BAPollDatum> bAPollData = null;
    @SerializedName("BAQuizData")
    @Expose
    private List<BAPollDatum> bAQuizData = null;

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

    public List<BAPollDatum> getBAPollData() {
        return bAPollData;
    }

    public void setBAPollData(List<BAPollDatum> bAPollData) {
        this.bAPollData = bAPollData;
    }

    public List<BAPollDatum> getBAQuizData() {
        return bAQuizData;
    }

    public void setBAQuizData(List<BAPollDatum> bAQuizData) {
        this.bAQuizData = bAQuizData;
    }
}
