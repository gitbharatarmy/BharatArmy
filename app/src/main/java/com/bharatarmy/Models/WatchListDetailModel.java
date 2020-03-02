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


    //cart detail
    @SerializedName("BACartListId")
    @Expose
    private Integer bACartListId;
    @SerializedName("CartItemType")
    @Expose
    private String cartItemType;
    @SerializedName("TicketImageUrl")
    @Expose
    private String ticketImageUrl;
    @SerializedName("TicketCategoryName")
    @Expose
    private String ticketCategoryName;
    @SerializedName("TicketPrice")
    @Expose
    private String ticketPrice;
    @SerializedName("TicketMatchFirstCountryName")
    @Expose
    private String ticketMatchFirstCountryName;
    @SerializedName("TicketMatchSecondCountryName")
    @Expose
    private String ticketMatchSecondCountryName;
    @SerializedName("TicketMatchFirstCountryFlag")
    @Expose
    private String ticketMatchFirstCountryFlag;
    @SerializedName("TicketMatchSecondCountryFlag")
    @Expose
    private String ticketMatchSecondCountryFlag;
    @SerializedName("TicketMatchGroundName")
    @Expose
    private String ticketMatchGroundName;
    @SerializedName("TicketMatchTimeDate")
    @Expose
    private String ticketMatchTimeDate;
    @SerializedName("TicketMatchType")
    @Expose
    private String ticketMatchType;
    @SerializedName("HotelImageUrl")
    @Expose
    private String hotelImageUrl;
    @SerializedName("HotelName")
    @Expose
    private String hotelName;
    @SerializedName("HotelRating")
    @Expose
    private String hotelRating;
    @SerializedName("HotelPrice")
    @Expose
    private String hotelPrice;
    @SerializedName("HotelDescription")
    @Expose
    private String hotelDescription;
    @SerializedName("HotelRoomName")
    @Expose
    private String hotelRoomName;
    @SerializedName("HospitalityImageUrl")
    @Expose
    private String hospitalityImageUrl;
    @SerializedName("HospitalityName")
    @Expose
    private String hospitalityName;
    @SerializedName("HospitalityPrice")
    @Expose
    private String hospitalityPrice;
    @SerializedName("HospitalityDescription")
    @Expose
    private String hospitalityDescription;
    @SerializedName("HospitalityIncludes")
    @Expose
    private String hospitalityIncludes;

    public Integer getBACartListId() {
        return bACartListId;
    }

    public void setBACartListId(Integer bACartListId) {
        this.bACartListId = bACartListId;
    }

    public String getCartItemType() {
        return cartItemType;
    }

    public void setCartItemType(String cartItemType) {
        this.cartItemType = cartItemType;
    }

    public String getTicketImageUrl() {
        return ticketImageUrl;
    }

    public void setTicketImageUrl(String ticketImageUrl) {
        this.ticketImageUrl = ticketImageUrl;
    }

    public String getTicketCategoryName() {
        return ticketCategoryName;
    }

    public void setTicketCategoryName(String ticketCategoryName) {
        this.ticketCategoryName = ticketCategoryName;
    }

    public String getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(String ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public String getTicketMatchFirstCountryName() {
        return ticketMatchFirstCountryName;
    }

    public void setTicketMatchFirstCountryName(String ticketMatchFirstCountryName) {
        this.ticketMatchFirstCountryName = ticketMatchFirstCountryName;
    }

    public String getTicketMatchSecondCountryName() {
        return ticketMatchSecondCountryName;
    }

    public void setTicketMatchSecondCountryName(String ticketMatchSecondCountryName) {
        this.ticketMatchSecondCountryName = ticketMatchSecondCountryName;
    }

    public String getTicketMatchFirstCountryFlag() {
        return ticketMatchFirstCountryFlag;
    }

    public void setTicketMatchFirstCountryFlag(String ticketMatchFirstCountryFlag) {
        this.ticketMatchFirstCountryFlag = ticketMatchFirstCountryFlag;
    }

    public String getTicketMatchSecondCountryFlag() {
        return ticketMatchSecondCountryFlag;
    }

    public void setTicketMatchSecondCountryFlag(String ticketMatchSecondCountryFlag) {
        this.ticketMatchSecondCountryFlag = ticketMatchSecondCountryFlag;
    }

    public String getTicketMatchGroundName() {
        return ticketMatchGroundName;
    }

    public void setTicketMatchGroundName(String ticketMatchGroundName) {
        this.ticketMatchGroundName = ticketMatchGroundName;
    }

    public String getTicketMatchTimeDate() {
        return ticketMatchTimeDate;
    }

    public void setTicketMatchTimeDate(String ticketMatchTimeDate) {
        this.ticketMatchTimeDate = ticketMatchTimeDate;
    }

    public String getTicketMatchType() {
        return ticketMatchType;
    }

    public void setTicketMatchType(String ticketMatchType) {
        this.ticketMatchType = ticketMatchType;
    }

    public String getHotelImageUrl() {
        return hotelImageUrl;
    }

    public void setHotelImageUrl(String hotelImageUrl) {
        this.hotelImageUrl = hotelImageUrl;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getHotelRating() {
        return hotelRating;
    }

    public void setHotelRating(String hotelRating) {
        this.hotelRating = hotelRating;
    }

    public String getHotelPrice() {
        return hotelPrice;
    }

    public void setHotelPrice(String hotelPrice) {
        this.hotelPrice = hotelPrice;
    }

    public String getHotelDescription() {
        return hotelDescription;
    }

    public void setHotelDescription(String hotelDescription) {
        this.hotelDescription = hotelDescription;
    }

    public String getHotelRoomName() {
        return hotelRoomName;
    }

    public void setHotelRoomName(String hotelRoomName) {
        this.hotelRoomName = hotelRoomName;
    }

    public String getHospitalityImageUrl() {
        return hospitalityImageUrl;
    }

    public void setHospitalityImageUrl(String hospitalityImageUrl) {
        this.hospitalityImageUrl = hospitalityImageUrl;
    }

    public String getHospitalityName() {
        return hospitalityName;
    }

    public void setHospitalityName(String hospitalityName) {
        this.hospitalityName = hospitalityName;
    }

    public String getHospitalityPrice() {
        return hospitalityPrice;
    }

    public void setHospitalityPrice(String hospitalityPrice) {
        this.hospitalityPrice = hospitalityPrice;
    }

    public String getHospitalityDescription() {
        return hospitalityDescription;
    }

    public void setHospitalityDescription(String hospitalityDescription) {
        this.hospitalityDescription = hospitalityDescription;
    }

    public String getHospitalityIncludes() {
        return hospitalityIncludes;
    }

    public void setHospitalityIncludes(String hospitalityIncludes) {
        this.hospitalityIncludes = hospitalityIncludes;
    }
}
