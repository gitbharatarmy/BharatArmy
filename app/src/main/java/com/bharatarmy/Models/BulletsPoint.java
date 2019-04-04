package com.bharatarmy.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BulletsPoint {
    @SerializedName("BulletImage")
    @Expose
    private String bulletImage;
    @SerializedName("BulletImageURL")
    @Expose
    private String bulletImageURL;
    @SerializedName("BulletName")
    @Expose
    private String bulletName;

    public String getBulletImage() {
        return bulletImage;
    }

    public void setBulletImage(String bulletImage) {
        this.bulletImage = bulletImage;
    }

    public String getBulletImageURL() {
        return bulletImageURL;
    }

    public void setBulletImageURL(String bulletImageURL) {
        this.bulletImageURL = bulletImageURL;
    }

    public String getBulletName() {
        return bulletName;
    }

    public void setBulletName(String bulletName) {
        this.bulletName = bulletName;
    }
}
