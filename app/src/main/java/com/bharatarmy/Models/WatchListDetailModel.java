package com.bharatarmy.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WatchListDetailModel {
    @SerializedName("BAWatchListImageId")
    @Expose
    private Integer bAWatchListImageId;
    @SerializedName("IsInWatchlist")
    @Expose
    private Integer isInWatchlist;
    @SerializedName("WatchGalleryURL")
    @Expose
    private String watchGalleryURL;
    @SerializedName("WatchGalleryThumbURL")
    @Expose
    private String watchGalleryThumbURL;
    @SerializedName("strAddedDuration")
    @Expose
    private String strAddedDuration;
    @SerializedName("IsLike")
    @Expose
    private Integer isLike;
    @SerializedName("AddedUserName")
    @Expose
    private String addedUserName;
    @SerializedName("IsMediaTypeId")
    @Expose
    private Integer isMediaTypeId;
    @SerializedName("TotalViews")
    @Expose
    private String totalViews;
    @SerializedName("VideoLength")
    @Expose
    private String videoLength;

    public Integer getBAWatchListImageId() {
        return bAWatchListImageId;
    }

    public void setBAWatchListImageId(Integer bAWatchListImageId) {
        this.bAWatchListImageId = bAWatchListImageId;
    }

    public Integer getIsInWatchlist() {
        return isInWatchlist;
    }

    public void setIsInWatchlist(Integer isInWatchlist) {
        this.isInWatchlist = isInWatchlist;
    }

    public String getWatchGalleryURL() {
        return watchGalleryURL;
    }

    public void setWatchGalleryURL(String watchGalleryURL) {
        this.watchGalleryURL = watchGalleryURL;
    }

    public String getWatchGalleryThumbURL() {
        return watchGalleryThumbURL;
    }

    public void setWatchGalleryThumbURL(String watchGalleryThumbURL) {
        this.watchGalleryThumbURL = watchGalleryThumbURL;
    }

    public String getStrAddedDuration() {
        return strAddedDuration;
    }

    public void setStrAddedDuration(String strAddedDuration) {
        this.strAddedDuration = strAddedDuration;
    }

    public Integer getIsLike() {
        return isLike;
    }

    public void setIsLike(Integer isLike) {
        this.isLike = isLike;
    }

    public String getAddedUserName() {
        return addedUserName;
    }

    public void setAddedUserName(String addedUserName) {
        this.addedUserName = addedUserName;
    }

    public Integer getIsMediaTypeId() {
        return isMediaTypeId;
    }

    public void setIsMediaTypeId(Integer isMediaTypeId) {
        this.isMediaTypeId = isMediaTypeId;
    }

    public String getTotalViews() {
        return totalViews;
    }

    public void setTotalViews(String totalViews) {
        this.totalViews = totalViews;
    }

    public String getVideoLength() {
        return videoLength;
    }

    public void setVideoLength(String videoLength) {
        this.videoLength = videoLength;
    }
}
