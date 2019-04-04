package com.bharatarmy.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UpcommingDashboardModel {
    public String Str1 = "", Str2 = "", Str3 = "";

    @SerializedName("DisplayAsPopup")
    @Expose
    private Integer displayAsPopup;
    @SerializedName("FutureTourImageURL")
    @Expose
    private String futureTourImageURL;
    @SerializedName("FutureTourThumbImageURL")
    @Expose
    private String futureTourThumbImageURL;
    @SerializedName("strShortDescription")
    @Expose
    private String strShortDescription;
    @SerializedName("FutureTourId")
    @Expose
    private Integer futureTourId;
    @SerializedName("TourName")
    @Expose
    private String tourName;
    @SerializedName("TourLocation")
    @Expose
    private String tourLocation;
    @SerializedName("MainCategoryId")
    @Expose
    private Integer mainCategoryId;
    @SerializedName("CategoryTypes")
    @Expose
    private String categoryTypes;
    @SerializedName("SubCategoryId")
    @Expose
    private String subCategoryId;
    @SerializedName("TourStartDate")
    @Expose
    private String tourStartDate;
    @SerializedName("TourEndDate")
    @Expose
    private String tourEndDate;
    @SerializedName("TourShortDescription")
    @Expose
    private String tourShortDescription;
    @SerializedName("TourDescription")
    @Expose
    private String tourDescription;
    @SerializedName("IsActive")
    @Expose
    private Integer isActive;
    @SerializedName("TourImage")
    @Expose
    private String tourImage;
    @SerializedName("TourFileName")
    @Expose
    private String tourFileName;
    @SerializedName("DateAdded")
    @Expose
    private String dateAdded;
    @SerializedName("TourThumbImage")
    @Expose
    private String tourThumbImage;
    @SerializedName("CategoryName")
    @Expose
    private String categoryName;
    @SerializedName("strDomains")
    @Expose
    private String strDomains;
    @SerializedName("PageURL")
    @Expose
    private String pageURL;
    @SerializedName("PageTitle")
    @Expose
    private String pageTitle;
    @SerializedName("SEOKeyword")
    @Expose
    private String sEOKeyword;
    @SerializedName("SEODescription")
    @Expose
    private String sEODescription;
    @SerializedName("DisplayContactForm")
    @Expose
    private Integer displayContactForm;
    @SerializedName("SelectOptionText")
    @Expose
    private String selectOptionText;
    @SerializedName("SelectOptionData")
    @Expose
    private String selectOptionData;

    public Integer getDisplayAsPopup() {
        return displayAsPopup;
    }

    public void setDisplayAsPopup(Integer displayAsPopup) {
        this.displayAsPopup = displayAsPopup;
    }

    public String getFutureTourImageURL() {
        return futureTourImageURL;
    }

    public void setFutureTourImageURL(String futureTourImageURL) {
        this.futureTourImageURL = futureTourImageURL;
    }

    public String getFutureTourThumbImageURL() {
        return futureTourThumbImageURL;
    }

    public void setFutureTourThumbImageURL(String futureTourThumbImageURL) {
        this.futureTourThumbImageURL = futureTourThumbImageURL;
    }

    public String getStrShortDescription() {
        return strShortDescription;
    }

    public void setStrShortDescription(String strShortDescription) {
        this.strShortDescription = strShortDescription;
    }

    public Integer getFutureTourId() {
        return futureTourId;
    }

    public void setFutureTourId(Integer futureTourId) {
        this.futureTourId = futureTourId;
    }

    public String getTourName() {
        return tourName;
    }

    public void setTourName(String tourName) {
        this.tourName = tourName;
    }

    public String getTourLocation() {
        return tourLocation;
    }

    public void setTourLocation(String tourLocation) {
        this.tourLocation = tourLocation;
    }

    public Integer getMainCategoryId() {
        return mainCategoryId;
    }

    public void setMainCategoryId(Integer mainCategoryId) {
        this.mainCategoryId = mainCategoryId;
    }

    public String getCategoryTypes() {
        return categoryTypes;
    }

    public void setCategoryTypes(String categoryTypes) {
        this.categoryTypes = categoryTypes;
    }

    public String getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(String subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getTourStartDate() {
        return tourStartDate;
    }

    public void setTourStartDate(String tourStartDate) {
        this.tourStartDate = tourStartDate;
    }

    public String getTourEndDate() {
        return tourEndDate;
    }

    public void setTourEndDate(String tourEndDate) {
        this.tourEndDate = tourEndDate;
    }

    public String getTourShortDescription() {
        return tourShortDescription;
    }

    public void setTourShortDescription(String tourShortDescription) {
        this.tourShortDescription = tourShortDescription;
    }

    public String getTourDescription() {
        return tourDescription;
    }

    public void setTourDescription(String tourDescription) {
        this.tourDescription = tourDescription;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public String getTourImage() {
        return tourImage;
    }

    public void setTourImage(String tourImage) {
        this.tourImage = tourImage;
    }

    public String getTourFileName() {
        return tourFileName;
    }

    public void setTourFileName(String tourFileName) {
        this.tourFileName = tourFileName;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getTourThumbImage() {
        return tourThumbImage;
    }

    public void setTourThumbImage(String tourThumbImage) {
        this.tourThumbImage = tourThumbImage;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getStrDomains() {
        return strDomains;
    }

    public void setStrDomains(String strDomains) {
        this.strDomains = strDomains;
    }

    public String getPageURL() {
        return pageURL;
    }

    public void setPageURL(String pageURL) {
        this.pageURL = pageURL;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    public String getSEOKeyword() {
        return sEOKeyword;
    }

    public void setSEOKeyword(String sEOKeyword) {
        this.sEOKeyword = sEOKeyword;
    }

    public String getSEODescription() {
        return sEODescription;
    }

    public void setSEODescription(String sEODescription) {
        this.sEODescription = sEODescription;
    }

    public Integer getDisplayContactForm() {
        return displayContactForm;
    }

    public void setDisplayContactForm(Integer displayContactForm) {
        this.displayContactForm = displayContactForm;
    }

    public String getSelectOptionText() {
        return selectOptionText;
    }

    public void setSelectOptionText(String selectOptionText) {
        this.selectOptionText = selectOptionText;
    }

    public String getSelectOptionData() {
        return selectOptionData;
    }

    public void setSelectOptionData(String selectOptionData) {
        this.selectOptionData = selectOptionData;
    }

    public String getStr1() {
        return Str1;
    }

    public void setStr1(String str1) {
        this.Str1 = str1;
    }

    public String getStr2() {
        return Str2;
    }

    public void setStr2(String str2) {
        this.Str2 = str2;
    }

    public String getStr3() {
        return Str3;
    }

    public void setStr3(String str3) {
        this.Str3 = str3;
    }

}
