package com.bharatarmy.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WalkthroughData {
    @SerializedName("CategoryName")
    @Expose
    private String categoryName;
    @SerializedName("CategoryImage")
    @Expose
    private String categoryImage;
    @SerializedName("CategoryImageURL")
    @Expose
    private String categoryImageURL;
    @SerializedName("BannerImage")
    @Expose
    private String bannerImage;
    @SerializedName("BannerImageURL")
    @Expose
    private String bannerImageURL;
    @SerializedName("HeaderText")
    @Expose
    private String headerText;
    @SerializedName("SubHeaderText")
    @Expose
    private String subHeaderText;
    @SerializedName("ParagraphText")
    @Expose
    private String paragraphText;
    @SerializedName("BulletsPoint")
    @Expose
    private List<BulletsPoint> bulletsPoint = null;
    @SerializedName("BulletLayoutType")
    @Expose
    private Integer bulletLayoutType;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }

    public String getCategoryImageURL() {
        return categoryImageURL;
    }

    public void setCategoryImageURL(String categoryImageURL) {
        this.categoryImageURL = categoryImageURL;
    }

    public String getBannerImage() {
        return bannerImage;
    }

    public void setBannerImage(String bannerImage) {
        this.bannerImage = bannerImage;
    }

    public String getBannerImageURL() {
        return bannerImageURL;
    }

    public void setBannerImageURL(String bannerImageURL) {
        this.bannerImageURL = bannerImageURL;
    }

    public String getHeaderText() {
        return headerText;
    }

    public void setHeaderText(String headerText) {
        this.headerText = headerText;
    }
    public String getSubHeaderText() {
        return subHeaderText;
    }

    public void setSubHeaderText(String subHeaderText) {
        this.subHeaderText = subHeaderText;
    }
    public String getParagraphText() {
        return paragraphText;
    }

    public void setParagraphText(String paragraphText) {
        this.paragraphText = paragraphText;
    }

    public List<BulletsPoint> getBulletsPoint() {
        return bulletsPoint;
    }

    public void setBulletsPoint(List<BulletsPoint> bulletsPoint) {
        this.bulletsPoint = bulletsPoint;
    }

    public Integer getBulletLayoutType() {
        return bulletLayoutType;
    }

    public void setBulletLayoutType(Integer bulletLayoutType) {
        this.bulletLayoutType = bulletLayoutType;
    }

}
