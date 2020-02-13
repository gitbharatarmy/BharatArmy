package com.bharatarmy.Models;

public class WatchListModel {
    Integer BAWatchListImageId;
    Integer IsInWatchlist;
    String WatchGalleryURL;
    String WatchGalleryThumbURL;
    String strAddedDuration;
    Integer IsLike;
    String AddedUserName;
    Integer IsMediaTypeId;
    String TotalViews;
    String VideoLength;


    public WatchListModel(Integer BAWatchListImageId, Integer IsInWatchlist, String WatchGalleryURL,
                          String WatchGalleryThumbURL, String strAddedDuration,
                          Integer IsLike, String AddedUserName, Integer IsMediaTypeId, String TotalViews, String VideoLength) {

        this.BAWatchListImageId = BAWatchListImageId;
        this.IsInWatchlist = IsInWatchlist;
        this.WatchGalleryURL = WatchGalleryURL;
        this.WatchGalleryThumbURL = WatchGalleryThumbURL;
        this.strAddedDuration = strAddedDuration;
        this.IsLike = IsLike;
        this.AddedUserName = AddedUserName;
        this.IsMediaTypeId = IsMediaTypeId;
        this.TotalViews = TotalViews;
        this.VideoLength = VideoLength;
    }

    public Integer getBAWatchListImageId() {
        return BAWatchListImageId;
    }

    public void setBAWatchListImageId(Integer BAWatchListImageId) {
        this.BAWatchListImageId = BAWatchListImageId;
    }

    public Integer getIsInWatchlist() {
        return IsInWatchlist;
    }

    public void setIsInWatchlist(Integer isInWatchlist) {
        IsInWatchlist = isInWatchlist;
    }

    public String getWatchGalleryURL() {
        return WatchGalleryURL;
    }

    public void setWatchGalleryURL(String watchGalleryURL) {
        WatchGalleryURL = watchGalleryURL;
    }

    public String getWatchGalleryThumbURL() {
        return WatchGalleryThumbURL;
    }

    public void setWatchGalleryThumbURL(String watchGalleryThumbURL) {
        WatchGalleryThumbURL = watchGalleryThumbURL;
    }

    public String getStrAddedDuration() {
        return strAddedDuration;
    }

    public void setStrAddedDuration(String strAddedDuration) {
        this.strAddedDuration = strAddedDuration;
    }

    public Integer getIsLike() {
        return IsLike;
    }

    public void setIsLike(Integer isLike) {
        IsLike = isLike;
    }

    public String getAddedUserName() {
        return AddedUserName;
    }

    public void setAddedUserName(String addedUserName) {
        AddedUserName = addedUserName;
    }

    public Integer getIsMediaTypeId() {
        return IsMediaTypeId;
    }

    public void setIsMediaTypeId(Integer isMediaTypeId) {
        IsMediaTypeId = isMediaTypeId;
    }

    public String getTotalViews() {
        return TotalViews;
    }

    public void setTotalViews(String totalViews) {
        TotalViews = totalViews;
    }

    public String getVideoLength() {
        return VideoLength;
    }

    public void setVideoLength(String videoLength) {
        VideoLength = videoLength;
    }
}
