package com.bharatarmy.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BAShopProductSpecification {
    public String sizeSelectionStr;

    @SerializedName("BAShopProductSpecification")
    @Expose
    private String bAShopProductSpecification;

    public String getBAShopProductSpecification() {
        return bAShopProductSpecification;
    }

    public void setBAShopProductSpecification(String bAShopProductSpecification) {
        this.bAShopProductSpecification = bAShopProductSpecification;
    }

    @SerializedName("Size")
    @Expose
    private String size;

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @SerializedName("BAShopProductImage")
    @Expose
    private String bAShopProductImage;

    public String getBAShopProductImage() {
        return bAShopProductImage;
    }

    public void setBAShopProductImage(String bAShopProductImage) {
        this.bAShopProductImage = bAShopProductImage;
    }

    public String getSizeSelectionStr() {
        return sizeSelectionStr;
    }

    public void setSizeSelectionStr(String sizeSelectionStr) {
        this.sizeSelectionStr = sizeSelectionStr;
    }

    @SerializedName("BAShopProductColor")
    @Expose
    private String bAShopProductColor;
    @SerializedName("BAShopProductColorCode")
    @Expose
    private String bAShopProductColorCode;

    public String getBAShopProductColor() {
        return bAShopProductColor;
    }

    public void setBAShopProductColor(String bAShopProductColor) {
        this.bAShopProductColor = bAShopProductColor;
    }

    public String getBAShopProductColorCode() {
        return bAShopProductColorCode;
    }

    public void setBAShopProductColorCode(String bAShopProductColorCode) {
        this.bAShopProductColorCode = bAShopProductColorCode;
    }

}
