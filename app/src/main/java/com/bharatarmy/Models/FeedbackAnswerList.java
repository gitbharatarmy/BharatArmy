package com.bharatarmy.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeedbackAnswerList {
    public String questionAnswerImagewithTextSelect;
    public String questionAnswerTextMultiSelect;
    public String questionAnswerRatingSelect;
    public String questionAnswerTextSingleSelect;
    public String questionAnswerTextGridSingleSelect;

    public String questionAnswerText;
    public String questionAnswerImage;
    public String questionAnswerRating;


    public FeedbackAnswerList(String questionAnswerText,String questionAnswerRating){
        this.questionAnswerText = questionAnswerText;
        this.questionAnswerRating =questionAnswerRating;
    }


    public String getQuestionAnswerText() {
        return questionAnswerText;
    }

    public void setQuestionAnswerText(String questionAnswerText) {
        this.questionAnswerText = questionAnswerText;
    }

    public String getQuestionAnswerImage() {
        return questionAnswerImage;
    }

    public void setQuestionAnswerImage(String questionAnswerImage) {
        this.questionAnswerImage = questionAnswerImage;
    }

    public String getQuestionAnswerImagewithTextSelect() {
        return questionAnswerImagewithTextSelect;
    }

    public void setQuestionAnswerImagewithTextSelect(String questionAnswerImagewithTextSelect) {
        this.questionAnswerImagewithTextSelect = questionAnswerImagewithTextSelect;
    }

    public String getQuestionAnswerTextMultiSelect() {
        return questionAnswerTextMultiSelect;
    }

    public void setQuestionAnswerTextMultiSelect(String questionAnswerTextMultiSelect) {
        this.questionAnswerTextMultiSelect = questionAnswerTextMultiSelect;
    }

    public String getQuestionAnswerRatingSelect() {
        return questionAnswerRatingSelect;
    }

    public void setQuestionAnswerRatingSelect(String questionAnswerRatingSelect) {
        this.questionAnswerRatingSelect = questionAnswerRatingSelect;
    }

    public String getQuestionAnswerTextSingleSelect() {
        return questionAnswerTextSingleSelect;
    }

    public void setQuestionAnswerTextSingleSelect(String questionAnswerTextSingleSelect) {
        this.questionAnswerTextSingleSelect = questionAnswerTextSingleSelect;
    }

    public String getQuestionAnswerTextGridSingleSelect() {
        return questionAnswerTextGridSingleSelect;
    }

    public void setQuestionAnswerTextGridSingleSelect(String questionAnswerTextGridSingleSelect) {
        this.questionAnswerTextGridSingleSelect = questionAnswerTextGridSingleSelect;
    }

    public String getQuestionAnswerRating() {
        return questionAnswerRating;
    }

    public void setQuestionAnswerRating(String questionAnswerRating) {
        this.questionAnswerRating = questionAnswerRating;
    }


    /*Feedback Answer*/
    @SerializedName("AppFeedbackOptionId")
    @Expose
    private Integer appFeedbackOptionId;
    @SerializedName("Text")
    @Expose
    private String text;
    @SerializedName("ImageName")
    @Expose
    private Object imageName;
    @SerializedName("ImageUrl")
    @Expose
    private String imageUrl;

    public Integer getAppFeedbackOptionId() {
        return appFeedbackOptionId;
    }

    public void setAppFeedbackOptionId(Integer appFeedbackOptionId) {
        this.appFeedbackOptionId = appFeedbackOptionId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Object getImageName() {
        return imageName;
    }

    public void setImageName(Object imageName) {
        this.imageName = imageName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

/* question ans list array variable*/
    public String questionStr;
    public String questionanswerStr;
    public String questiontypeStr;
    public String questionanswerotherStr;


    public FeedbackAnswerList(String questionStr,String questionanswerStr,String questiontypeStr,String questionanswerotherStr){
        this.questionStr=questionStr;
        this.questionanswerStr=questionanswerStr;
        this.questiontypeStr=questiontypeStr;
        this.questionanswerotherStr=questionanswerotherStr;
    }

    public String getQuestionStr() {
        return questionStr;
    }

    public void setQuestionStr(String questionStr) {
        this.questionStr = questionStr;
    }

    public String getQuestionanswerStr() {
        return questionanswerStr;
    }

    public void setQuestionanswerStr(String questionanswerStr) {
        this.questionanswerStr = questionanswerStr;
    }

    public String getQuestiontypeStr() {
        return questiontypeStr;
    }

    public void setQuestiontypeStr(String questiontypeStr) {
        this.questiontypeStr = questiontypeStr;
    }

    public String getQuestionanswerotherStr() {
        return questionanswerotherStr;
    }

    public void setQuestionanswerotherStr(String questionanswerotherStr) {
        this.questionanswerotherStr = questionanswerotherStr;
    }
}
