package com.bharatarmy.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImageDetailModel {
    @SerializedName("BAGalleryId")
    @Expose
    private Integer bAGalleryId;
    @SerializedName("GalleryName")
    @Expose
    private String galleryName;
    @SerializedName("GalleryImage")
    @Expose
    private String galleryImage;
    @SerializedName("GalleryURL")
    @Expose
    private String galleryURL;
@SerializedName("CurrentSie")
@Expose
private int currentSize;

@SerializedName("NextLimit")
@Expose
private int nextLimit;
    public Integer getBAGalleryId() {
        return bAGalleryId;
    }

    public void setBAGalleryId(Integer bAGalleryId) {
        this.bAGalleryId = bAGalleryId;
    }

    public String getGalleryName() {
        return galleryName;
    }

    public void setGalleryName(String galleryName) {
        this.galleryName = galleryName;
    }

    public String getGalleryImage() {
        return galleryImage;
    }

    public void setGalleryImage(String galleryImage) {
        this.galleryImage = galleryImage;
    }

    public String getGalleryURL() {
        return galleryURL;
    }

    public void setGalleryURL(String galleryURL) {
        this.galleryURL = galleryURL;
    }

    public int getCurrentSize() {
        return currentSize;
    }

    public void setCurrentSize(int currentSize) {
        this.currentSize = currentSize;
    }

    public int getNextLimit() {
        return nextLimit;
    }

    public void setNextLimit(int nextLimit) {
        this.nextLimit = nextLimit;
    }
}
