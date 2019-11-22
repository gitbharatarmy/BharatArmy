package com.bharatarmy.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LogginModel {
    @SerializedName("Message")
    @Expose
    private String message;
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
    private Object otherValue;
    @SerializedName("OtherData")
    @Expose
    private List<LoginOtherDataModel> otherData = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
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

    public Object getOtherValue() {
        return otherValue;
    }

    public void setOtherValue(Object otherValue) {
        this.otherValue = otherValue;
    }


    public List<LoginOtherDataModel> getOtherData() {
        return otherData;
    }

    public void setOtherData(List<LoginOtherDataModel> otherData) {
        this.otherData = otherData;
    }


    @SerializedName("CurrentLocation")
    @Expose
    private LoginDataModel currentLocation;

    public LoginDataModel getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(LoginDataModel currentLocation) {
        this.currentLocation = currentLocation;
    }
}
