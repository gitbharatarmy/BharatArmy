package com.bharatarmy.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StoryDashboardData {
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
    @SerializedName("StoryWebURL")
    @Expose
    private String storyWebURL;
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
    public String getStoryWebURL() {
        return storyWebURL;
    }

    public void setStoryWebURL(String storyWebURL) {
        this.storyWebURL = storyWebURL;
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
}
