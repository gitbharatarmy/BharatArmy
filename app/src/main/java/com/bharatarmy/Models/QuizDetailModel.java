package com.bharatarmy.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuizDetailModel {
    @SerializedName("BAQuizId")
    @Expose
    private String bAQuizId;
    @SerializedName("QuizName")
    @Expose
    private String quizName;
    @SerializedName("QuizHeaderText")
    @Expose
    private String quizHeaderText;
    @SerializedName("QuizShortDescription")
    @Expose
    private String quizShortDescription;
    @SerializedName("QuizCategoryId")
    @Expose
    private Integer quizCategoryId;
    @SerializedName("QuizBannerImage")
    @Expose
    private String quizBannerImage;
    @SerializedName("BACategoryName")
    @Expose
    private String bACategoryName;
    @SerializedName("DisplayInListingBanner")
    @Expose
    private Integer displayInListingBanner;
    @SerializedName("DateAdded")
    @Expose
    private String dateAdded;
    @SerializedName("ThumbImage")
    @Expose
    private String thumbImage;
    @SerializedName("QuizUrl")
    @Expose
    private String quizUrl;
    @SerializedName("QuizMobileBanner")
    @Expose
    private String quizMobileBanner;
    @SerializedName("strDisplayDate")
    @Expose
    private String strDisplayDate;
    @SerializedName("QuizMobileBannerUrl")
    @Expose
    private String quizMobileBannerUrl;
    @SerializedName("ThumbImageUrl")
    @Expose
    private String thumbImageUrl;

    public String getBAQuizId() {
        return bAQuizId;
    }

    public void setBAQuizId(String bAQuizId) {
        this.bAQuizId = bAQuizId;
    }

    public String getQuizName() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public String getQuizHeaderText() {
        return quizHeaderText;
    }

    public void setQuizHeaderText(String quizHeaderText) {
        this.quizHeaderText = quizHeaderText;
    }

    public String getQuizShortDescription() {
        return quizShortDescription;
    }

    public void setQuizShortDescription(String quizShortDescription) {
        this.quizShortDescription = quizShortDescription;
    }

    public Integer getQuizCategoryId() {
        return quizCategoryId;
    }

    public void setQuizCategoryId(Integer quizCategoryId) {
        this.quizCategoryId = quizCategoryId;
    }

    public String getQuizBannerImage() {
        return quizBannerImage;
    }

    public void setQuizBannerImage(String quizBannerImage) {
        this.quizBannerImage = quizBannerImage;
    }

    public String getBACategoryName() {
        return bACategoryName;
    }

    public void setBACategoryName(String bACategoryName) {
        this.bACategoryName = bACategoryName;
    }

    public Integer getDisplayInListingBanner() {
        return displayInListingBanner;
    }

    public void setDisplayInListingBanner(Integer displayInListingBanner) {
        this.displayInListingBanner = displayInListingBanner;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getThumbImage() {
        return thumbImage;
    }

    public void setThumbImage(String thumbImage) {
        this.thumbImage = thumbImage;
    }

    public String getQuizUrl() {
        return quizUrl;
    }

    public void setQuizUrl(String quizUrl) {
        this.quizUrl = quizUrl;
    }

    public String getQuizMobileBanner() {
        return quizMobileBanner;
    }

    public void setQuizMobileBanner(String quizMobileBanner) {
        this.quizMobileBanner = quizMobileBanner;
    }

    public String getStrDisplayDate() {
        return strDisplayDate;
    }

    public void setStrDisplayDate(String strDisplayDate) {
        this.strDisplayDate = strDisplayDate;
    }

    public String getQuizMobileBannerUrl() {
        return quizMobileBannerUrl;
    }

    public void setQuizMobileBannerUrl(String quizMobileBannerUrl) {
        this.quizMobileBannerUrl = quizMobileBannerUrl;
    }

    public String getThumbImageUrl() {
        return thumbImageUrl;
    }

    public void setThumbImageUrl(String thumbImageUrl) {
        this.thumbImageUrl = thumbImageUrl;
    }


    @SerializedName("QuizDescription")
    @Expose
    private String quizDescription;
    @SerializedName("QuizAvailableIn")
    @Expose
    private String quizAvailableIn;
    @SerializedName("DisplayResultType")
    @Expose
    private Integer displayResultType;
    @SerializedName("TimerType")
    @Expose
    private Integer timerType;
    @SerializedName("TimerValue")
    @Expose
    private Integer timerValue;
    @SerializedName("PublishDate")
    @Expose
    private Object publishDate;
    @SerializedName("PublishTimezoneId")
    @Expose
    private Integer publishTimezoneId;
    @SerializedName("QuizStatus")
    @Expose
    private Integer quizStatus;
    @SerializedName("PageTitle")
    @Expose
    private String pageTitle;
    @SerializedName("SEO_Metadescription")
    @Expose
    private String sEOMetadescription;
    @SerializedName("AddedBy")
    @Expose
    private Integer addedBy;
    @SerializedName("QuestionDisplay")
    @Expose
    private Integer questionDisplay;
    @SerializedName("DisplayHomePageBanner")
    @Expose
    private Integer displayHomePageBanner;

    @SerializedName("QuizBannerUrl")
    @Expose
    private String quizBannerUrl;

    public String getQuizDescription() {
        return quizDescription;
    }

    public void setQuizDescription(String quizDescription) {
        this.quizDescription = quizDescription;
    }


    public String getQuizAvailableIn() {
        return quizAvailableIn;
    }

    public void setQuizAvailableIn(String quizAvailableIn) {
        this.quizAvailableIn = quizAvailableIn;
    }

    public Integer getDisplayResultType() {
        return displayResultType;
    }

    public void setDisplayResultType(Integer displayResultType) {
        this.displayResultType = displayResultType;
    }

    public Integer getTimerType() {
        return timerType;
    }

    public void setTimerType(Integer timerType) {
        this.timerType = timerType;
    }

    public Integer getTimerValue() {
        return timerValue;
    }

    public void setTimerValue(Integer timerValue) {
        this.timerValue = timerValue;
    }

    public Object getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Object publishDate) {
        this.publishDate = publishDate;
    }

    public Integer getPublishTimezoneId() {
        return publishTimezoneId;
    }

    public void setPublishTimezoneId(Integer publishTimezoneId) {
        this.publishTimezoneId = publishTimezoneId;
    }

    public Integer getQuizStatus() {
        return quizStatus;
    }

    public void setQuizStatus(Integer quizStatus) {
        this.quizStatus = quizStatus;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    public String getSEOMetadescription() {
        return sEOMetadescription;
    }

    public void setSEOMetadescription(String sEOMetadescription) {
        this.sEOMetadescription = sEOMetadescription;
    }

    public Integer getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(Integer addedBy) {
        this.addedBy = addedBy;
    }

    public Integer getQuestionDisplay() {
        return questionDisplay;
    }

    public void setQuestionDisplay(Integer questionDisplay) {
        this.questionDisplay = questionDisplay;
    }

    public Integer getDisplayHomePageBanner() {
        return displayHomePageBanner;
    }

    public void setDisplayHomePageBanner(Integer displayHomePageBanner) {
        this.displayHomePageBanner = displayHomePageBanner;
    }

    public String getQuizBannerUrl() {
        return quizBannerUrl;
    }

    public void setQuizBannerUrl(String quizBannerUrl) {
        this.quizBannerUrl = quizBannerUrl;
    }
}
