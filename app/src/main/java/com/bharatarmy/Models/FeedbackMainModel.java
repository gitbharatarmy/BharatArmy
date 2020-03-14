package com.bharatarmy.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FeedbackMainModel {
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("FeedbackQuestionAnswerData")
    @Expose
    private List<FeedbackQuestionAnswerDatum> feedbackQuestionAnswerData = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<FeedbackQuestionAnswerDatum> getFeedbackQuestionAnswerData() {
        return feedbackQuestionAnswerData;
    }

    public void setFeedbackQuestionAnswerData(List<FeedbackQuestionAnswerDatum> feedbackQuestionAnswerData) {
        this.feedbackQuestionAnswerData = feedbackQuestionAnswerData;
    }
}
