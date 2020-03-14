package com.bharatarmy.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeedbackAnswerList {
    @SerializedName("QuestionAnswerText")
    @Expose
    private String questionAnswerText;
    @SerializedName("QuestionAnswerImage")
    @Expose
    private String questionAnswerImage;

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


    @SerializedName("QuestionAnswerRating")
    @Expose
    private String questionAnswerRating;

    public String getQuestionAnswerRating() {
        return questionAnswerRating;
    }

    public void setQuestionAnswerRating(String questionAnswerRating) {
        this.questionAnswerRating = questionAnswerRating;
    }


}
