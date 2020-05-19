package com.bharatarmy.Models;

public class ExpolringCategoryModel {
    int explorecategoryIcon,expolrecategoryBgColor;
    String explorecategoryName , expolrecategorycount;

    String recommendedQuizTitle;
    String recommendedQuizImage;
    String recommendedQuizType;
    String recommendedQuizDate;

    public ExpolringCategoryModel(String recommendedQuizTitle, String recommendedQuizImage, String recommendedQuizType,
                                  String recommendedQuizDate) {
        this.recommendedQuizTitle = recommendedQuizTitle;
        this.recommendedQuizImage = recommendedQuizImage;
        this.recommendedQuizType = recommendedQuizType;
        this.recommendedQuizDate = recommendedQuizDate;
    }

    public String getRecommendedQuizTitle() {
        return recommendedQuizTitle;
    }

    public void setRecommendedQuizTitle(String recommendedQuizTitle) {
        this.recommendedQuizTitle = recommendedQuizTitle;
    }

    public String getRecommendedQuizImage() {
        return recommendedQuizImage;
    }

    public void setRecommendedQuizImage(String recommendedQuizImage) {
        this.recommendedQuizImage = recommendedQuizImage;
    }

    public String getRecommendedQuizType() {
        return recommendedQuizType;
    }

    public void setRecommendedQuizType(String recommendedQuizType) {
        this.recommendedQuizType = recommendedQuizType;
    }

    public String getRecommendedQuizDate() {
        return recommendedQuizDate;
    }

    public void setRecommendedQuizDate(String recommendedQuizDate) {
        this.recommendedQuizDate = recommendedQuizDate;
    }

    public ExpolringCategoryModel(int explorecategoryIcon, String explorecategoryName, String expolrecategorycount, int expolrecategoryBgColor) {
        this.explorecategoryIcon = explorecategoryIcon;
        this.explorecategoryName = explorecategoryName;
        this.expolrecategorycount=expolrecategorycount;
        this.expolrecategoryBgColor=expolrecategoryBgColor;
    }

    public int getExplorecategoryIcon() {
        return explorecategoryIcon;
    }

    public void setExplorecategoryIcon(int explorecategoryIcon) {
        this.explorecategoryIcon = explorecategoryIcon;
    }

    public int getExpolrecategoryBgColor() {
        return expolrecategoryBgColor;
    }

    public void setExpolrecategoryBgColor(int expolrecategoryBgColor) {
        this.expolrecategoryBgColor = expolrecategoryBgColor;
    }

    public String getExplorecategoryName() {
        return explorecategoryName;
    }

    public void setExplorecategoryName(String explorecategoryName) {
        this.explorecategoryName = explorecategoryName;
    }

    public String getExpolrecategorycount() {
        return expolrecategorycount;
    }

    public void setExpolrecategorycount(String expolrecategorycount) {
        this.expolrecategorycount = expolrecategorycount;
    }
}
