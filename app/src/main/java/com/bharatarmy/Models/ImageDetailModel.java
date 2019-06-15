package com.bharatarmy.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ImageDetailModel implements Serializable {
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
    @SerializedName("Height")
    @Expose
    private Integer height;
    @SerializedName("Width")
    @Expose
    private Integer width;

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

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    //Story Detail Model
    @SerializedName("AuthorImageURL")
    @Expose
    private String authorImageURL;
    @SerializedName("strStoryAdded")
    @Expose
    private String strStoryAdded;
    @SerializedName("strStoryAddedFormat1")
    @Expose
    private String strStoryAddedFormat1;
    @SerializedName("strViewCount")
    @Expose
    private String strViewCount;
    @SerializedName("strStoryTitle")
    @Expose
    private String strStoryTitle;
    @SerializedName("strStorySubDescription")
    @Expose
    private String strStorySubDescription;
    @SerializedName("strCategoryImageUrl")
    @Expose
    private String strCategoryImageUrl;
    @SerializedName("StoryWebURL")
    @Expose
    private String storyWebURL;
    @SerializedName("strThumbImageName")
    @Expose
    private String strThumbImageName;
    @SerializedName("BAStoryId")
    @Expose
    private Integer bAStoryId;
    @SerializedName("StoryTitle")
    @Expose
    private String storyTitle;
    @SerializedName("StoryImage")
    @Expose
    private String storyImage;
    @SerializedName("StoryAdded")
    @Expose
    private String storyAdded;
    @SerializedName("ShortDescription")
    @Expose
    private String shortDescription;
    @SerializedName("AuthorName")
    @Expose
    private String authorName;
    @SerializedName("AuthorImage")
    @Expose
    private String authorImage;
    @SerializedName("StoryURL")
    @Expose
    private String storyURL;
    @SerializedName("strCategories")
    @Expose
    private String strCategories;
    @SerializedName("CategoryName")
    @Expose
    private String categoryName;
    @SerializedName("IsDisplayAsBanner")
    @Expose
    private Integer isDisplayAsBanner;
    @SerializedName("ViewCount")
    @Expose
    private Integer viewCount;
    @SerializedName("ShortDescriptionlast")
    @Expose
    private String shortDescriptionlast;
    @SerializedName("BASubCategoryName")
    @Expose
    private String bASubCategoryName;
    @SerializedName("ThumbImageName")
    @Expose
    private String thumbImageName;
    @SerializedName("CategoryImage")
    @Expose
    private String categoryImage;
    @SerializedName("AuthorId")
    @Expose
    private Integer authorId;
    @SerializedName("CategoryId")
    @Expose
    private Integer categoryId;

    public String getAuthorImageURL() {
        return authorImageURL;
    }

    public void setAuthorImageURL(String authorImageURL) {
        this.authorImageURL = authorImageURL;
    }

    public String getStrStoryAdded() {
        return strStoryAdded;
    }

    public void setStrStoryAdded(String strStoryAdded) {
        this.strStoryAdded = strStoryAdded;
    }

    public String getStrStoryAddedFormat1() {
        return strStoryAddedFormat1;
    }

    public void setStrStoryAddedFormat1(String strStoryAddedFormat1) {
        this.strStoryAddedFormat1 = strStoryAddedFormat1;
    }

    public String getStrViewCount() {
        return strViewCount;
    }

    public void setStrViewCount(String strViewCount) {
        this.strViewCount = strViewCount;
    }

    public String getStrStoryTitle() {
        return strStoryTitle;
    }

    public void setStrStoryTitle(String strStoryTitle) {
        this.strStoryTitle = strStoryTitle;
    }

    public String getStrStorySubDescription() {
        return strStorySubDescription;
    }

    public void setStrStorySubDescription(String strStorySubDescription) {
        this.strStorySubDescription = strStorySubDescription;
    }

    public String getStrCategoryImageUrl() {
        return strCategoryImageUrl;
    }

    public void setStrCategoryImageUrl(String strCategoryImageUrl) {
        this.strCategoryImageUrl = strCategoryImageUrl;
    }

    public String getStoryWebURL() {
        return storyWebURL;
    }

    public void setStoryWebURL(String storyWebURL) {
        this.storyWebURL = storyWebURL;
    }

    public String getStrThumbImageName() {
        return strThumbImageName;
    }

    public void setStrThumbImageName(String strThumbImageName) {
        this.strThumbImageName = strThumbImageName;
    }

    public Integer getBAStoryId() {
        return bAStoryId;
    }

    public void setBAStoryId(Integer bAStoryId) {
        this.bAStoryId = bAStoryId;
    }

    public String getStoryTitle() {
        return storyTitle;
    }

    public void setStoryTitle(String storyTitle) {
        this.storyTitle = storyTitle;
    }

    public String getStoryImage() {
        return storyImage;
    }

    public void setStoryImage(String storyImage) {
        this.storyImage = storyImage;
    }

    public String getStoryAdded() {
        return storyAdded;
    }

    public void setStoryAdded(String storyAdded) {
        this.storyAdded = storyAdded;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorImage() {
        return authorImage;
    }

    public void setAuthorImage(String authorImage) {
        this.authorImage = authorImage;
    }

    public String getStoryURL() {
        return storyURL;
    }

    public void setStoryURL(String storyURL) {
        this.storyURL = storyURL;
    }

    public String getStrCategories() {
        return strCategories;
    }

    public void setStrCategories(String strCategories) {
        this.strCategories = strCategories;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getIsDisplayAsBanner() {
        return isDisplayAsBanner;
    }

    public void setIsDisplayAsBanner(Integer isDisplayAsBanner) {
        this.isDisplayAsBanner = isDisplayAsBanner;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public String getShortDescriptionlast() {
        return shortDescriptionlast;
    }

    public void setShortDescriptionlast(String shortDescriptionlast) {
        this.shortDescriptionlast = shortDescriptionlast;
    }

    public String getBASubCategoryName() {
        return bASubCategoryName;
    }

    public void setBASubCategoryName(String bASubCategoryName) {
        this.bASubCategoryName = bASubCategoryName;
    }

    public String getThumbImageName() {
        return thumbImageName;
    }

    public void setThumbImageName(String thumbImageName) {
        this.thumbImageName = thumbImageName;
    }

    public String getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    //    FTP List Model
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


    //    StoryDetail
    @SerializedName("StoryId")
    @Expose
    private Integer storyId;
    @SerializedName("StoryDescription")
    @Expose
    private String storyDescription;

    public Integer getStoryId() {
        return storyId;
    }

    public void setStoryId(Integer storyId) {
        this.storyId = storyId;
    }

    public String getStoryDescription() {
        return storyDescription;
    }

    public void setStoryDescription(String storyDescription) {
        this.storyDescription = storyDescription;
    }



}
