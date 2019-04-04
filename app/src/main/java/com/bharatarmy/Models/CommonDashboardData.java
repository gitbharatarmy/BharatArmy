package com.bharatarmy.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CommonDashboardData {
    @SerializedName("PageHeaderText")
    @Expose
    private String pageHeaderText;
    @SerializedName("PageHeaderDesc")
    @Expose
    private String pageHeaderDesc;
    @SerializedName("PageViewMoreText")
    @Expose
    private String pageViewMoreText;
    @SerializedName("AdvertImage")
    @Expose
    private String advertImage;
    @SerializedName("AdvertImageurl")
    @Expose
    private String advertImageurl;

    public String getPageHeaderText() {
        return pageHeaderText;
    }

    public void setPageHeaderText(String pageHeaderText) {
        this.pageHeaderText = pageHeaderText;
    }

    public String getPageHeaderDesc() {
        return pageHeaderDesc;
    }

    public void setPageHeaderDesc(String pageHeaderDesc) {
        this.pageHeaderDesc = pageHeaderDesc;
    }

    public String getPageViewMoreText() {
        return pageViewMoreText;
    }

    public void setPageViewMoreText(String pageViewMoreText) {
        this.pageViewMoreText = pageViewMoreText;
    }

    public String getAdvertImage() {
        return advertImage;
    }

    public void setAdvertImage(String advertImage) {
        this.advertImage = advertImage;
    }

    public String getAdvertImageurl() {
        return advertImageurl;
    }

    public void setAdvertImageurl(String advertImageurl) {
        this.advertImageurl = advertImageurl;
    }

}
