package com.bharatarmy.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BAShopListModel {
    @SerializedName("BAShopListId")
    @Expose
    private Integer bAShopListId;
    @SerializedName("BAShopProductName")
    @Expose
    private String bAShopProductName;
    @SerializedName("BAShopProductPrice")
    @Expose
    private String bAShopProductPrice;
    @SerializedName("BAShopProductDescription")
    @Expose
    private String bAShopProductDescription;
    @SerializedName("BAShopProductImage")
    @Expose
    private String bAShopProductImage;
    @SerializedName("BAShopProductDetailImage")
    @Expose
    private List<BAShopProductSpecification> bAShopProductDetailImage = null;
    @SerializedName("BAShopProductSpecification")
    @Expose
    private List<BAShopProductSpecification> bAShopProductSpecification = null;
    @SerializedName("BAShopProductSize")
    @Expose
    private List<BAShopProductSpecification> bAShopProductSize = null;
    @SerializedName("BAShopProductDetailColor")
    @Expose
    private List<BAShopProductSpecification> bAShopProductDetailColor = null;
    public Integer getBAShopListId() {
        return bAShopListId;
    }

    public void setBAShopListId(Integer bAShopListId) {
        this.bAShopListId = bAShopListId;
    }

    public String getBAShopProductName() {
        return bAShopProductName;
    }

    public void setBAShopProductName(String bAShopProductName) {
        this.bAShopProductName = bAShopProductName;
    }

    public String getBAShopProductPrice() {
        return bAShopProductPrice;
    }

    public void setBAShopProductPrice(String bAShopProductPrice) {
        this.bAShopProductPrice = bAShopProductPrice;
    }

    public String getBAShopProductDescription() {
        return bAShopProductDescription;
    }

    public void setBAShopProductDescription(String bAShopProductDescription) {
        this.bAShopProductDescription = bAShopProductDescription;
    }

    public String getBAShopProductImage() {
        return bAShopProductImage;
    }

    public void setBAShopProductImage(String bAShopProductImage) {
        this.bAShopProductImage = bAShopProductImage;
    }

    public List<BAShopProductSpecification> getBAShopProductDetailImage() {
        return bAShopProductDetailImage;
    }

    public void setBAShopProductDetailImage(List<BAShopProductSpecification> bAShopProductDetailImage) {
        this.bAShopProductDetailImage = bAShopProductDetailImage;
    }

    public List<BAShopProductSpecification> getBAShopProductSpecification() {
        return bAShopProductSpecification;
    }

    public void setBAShopProductSpecification(List<BAShopProductSpecification> bAShopProductSpecification) {
        this.bAShopProductSpecification = bAShopProductSpecification;
    }

    public List<BAShopProductSpecification> getBAShopProductSize() {
        return bAShopProductSize;
    }

    public void setBAShopProductSize(List<BAShopProductSpecification> bAShopProductSize) {
        this.bAShopProductSize = bAShopProductSize;
    }

    public List<BAShopProductSpecification> getbAShopProductDetailColor() {
        return bAShopProductDetailColor;
    }

    public void setbAShopProductDetailColor(List<BAShopProductSpecification> bAShopProductDetailColor) {
        this.bAShopProductDetailColor = bAShopProductDetailColor;
    }
}
