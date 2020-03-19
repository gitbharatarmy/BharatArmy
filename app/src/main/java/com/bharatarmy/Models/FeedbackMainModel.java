package com.bharatarmy.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FeedbackMainModel {
    //    @SerializedName("Status")
//    @Expose
//    private String status;
//    @SerializedName("FeedbackQuestionAnswerData")
//    @Expose
//    private List<FeedbackQuestionAnswerDatum> feedbackQuestionAnswerData = null;
//
//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }
//
//    public List<FeedbackQuestionAnswerDatum> getFeedbackQuestionAnswerData() {
//        return feedbackQuestionAnswerData;
//    }
//
//    public void setFeedbackQuestionAnswerData(List<FeedbackQuestionAnswerDatum> feedbackQuestionAnswerData) {
//        this.feedbackQuestionAnswerData = feedbackQuestionAnswerData;
//    }
    @SerializedName("Message")
    @Expose
    private Object message;
    @SerializedName("MessageId")
    @Expose
    private Integer messageId;
    @SerializedName("IsValid")
    @Expose
    private Integer isValid;
    @SerializedName("Data")
    @Expose
    private LoginDataModel data;
    @SerializedName("OtherValue")
    @Expose
    private String otherValue;
    @SerializedName("OtherData")
    @Expose
    private Object otherData;
    @SerializedName("CurrentLocation")
    @Expose
    private Object currentLocation;
    @SerializedName("GlobalData")
    @Expose
    private Object globalData;
    @SerializedName("RecordCount")
    @Expose
    private Integer recordCount;

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public LoginDataModel getData() {
        return data;
    }

    public void setData(LoginDataModel data) {
        this.data = data;
    }

    public String getOtherValue() {
        return otherValue;
    }

    public void setOtherValue(String otherValue) {
        this.otherValue = otherValue;
    }

    public Object getOtherData() {
        return otherData;
    }

    public void setOtherData(Object otherData) {
        this.otherData = otherData;
    }

    public Object getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Object currentLocation) {
        this.currentLocation = currentLocation;
    }

    public Object getGlobalData() {
        return globalData;
    }

    public void setGlobalData(Object globalData) {
        this.globalData = globalData;
    }

    public Integer getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(Integer recordCount) {
        this.recordCount = recordCount;
    }

}
