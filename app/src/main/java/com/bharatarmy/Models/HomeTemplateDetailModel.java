package com.bharatarmy.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HomeTemplateDetailModel {
    @SerializedName("MainHeaderText")
    @Expose
    private String mainHeaderText;
    @SerializedName("MainHaderImage")
    @Expose
    private String mainHaderImage;
    @SerializedName("MainHeaderImageUrl")
    @Expose
    private String mainHeaderImageUrl;
    @SerializedName("SecondHeaderType")
    @Expose
    private Integer secondHeaderType;
    @SerializedName("SecondHeaderImage")
    @Expose
    private String secondHeaderImage;
    @SerializedName("SecondHeaderImageUrl")
    @Expose
    private String secondHeaderImageUrl;
    @SerializedName("SecondHeaderTag")
    @Expose
    private String secondHeaderTag;
    @SerializedName("SecondHeaderText")
    @Expose
    private String secondHeaderText;
    @SerializedName("SecondHeaderSubText")
    @Expose
    private String secondHeaderSubText;
    @SerializedName("SecondHeaderButtonText")
    @Expose
    private String secondHeaderButtonText;
    @SerializedName("SecondHaderButtonColor")
    @Expose
    private String secondHaderButtonColor;
    @SerializedName("SecondHaderButtonFontColor")
    @Expose
    private String secondHaderButtonFontColor;

    public String getMainHeaderText() {
        return mainHeaderText;
    }

    public void setMainHeaderText(String mainHeaderText) {
        this.mainHeaderText = mainHeaderText;
    }

    public String getMainHaderImage() {
        return mainHaderImage;
    }

    public void setMainHaderImage(String mainHaderImage) {
        this.mainHaderImage = mainHaderImage;
    }

    public String getMainHeaderImageUrl() {
        return mainHeaderImageUrl;
    }

    public void setMainHeaderImageUrl(String mainHeaderImageUrl) {
        this.mainHeaderImageUrl = mainHeaderImageUrl;
    }

    public Integer getSecondHeaderType() {
        return secondHeaderType;
    }

    public void setSecondHeaderType(Integer secondHeaderType) {
        this.secondHeaderType = secondHeaderType;
    }

    public String getSecondHeaderImage() {
        return secondHeaderImage;
    }

    public void setSecondHeaderImage(String secondHeaderImage) {
        this.secondHeaderImage = secondHeaderImage;
    }

    public String getSecondHeaderImageUrl() {
        return secondHeaderImageUrl;
    }

    public void setSecondHeaderImageUrl(String secondHeaderImageUrl) {
        this.secondHeaderImageUrl = secondHeaderImageUrl;
    }

    public String getSecondHeaderTag() {
        return secondHeaderTag;
    }

    public void setSecondHeaderTag(String secondHeaderTag) {
        this.secondHeaderTag = secondHeaderTag;
    }

    public String getSecondHeaderText() {
        return secondHeaderText;
    }

    public void setSecondHeaderText(String secondHeaderText) {
        this.secondHeaderText = secondHeaderText;
    }

    public String getSecondHeaderSubText() {
        return secondHeaderSubText;
    }

    public void setSecondHeaderSubText(String secondHeaderSubText) {
        this.secondHeaderSubText = secondHeaderSubText;
    }

    public String getSecondHeaderButtonText() {
        return secondHeaderButtonText;
    }

    public void setSecondHeaderButtonText(String secondHeaderButtonText) {
        this.secondHeaderButtonText = secondHeaderButtonText;
    }

    public String getSecondHaderButtonColor() {
        return secondHaderButtonColor;
    }

    public void setSecondHaderButtonColor(String secondHaderButtonColor) {
        this.secondHaderButtonColor = secondHaderButtonColor;
    }

    public String getSecondHaderButtonFontColor() {
        return secondHaderButtonFontColor;
    }

    public void setSecondHaderButtonFontColor(String secondHaderButtonFontColor) {
        this.secondHaderButtonFontColor = secondHaderButtonFontColor;
    }

}
