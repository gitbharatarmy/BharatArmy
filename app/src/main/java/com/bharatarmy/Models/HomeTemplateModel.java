package com.bharatarmy.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HomeTemplateModel {
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
    private List<HomeTemplateDetailModel> data = null;
    @SerializedName("OtherValue")
    @Expose
    private Object otherValue;
    @SerializedName("OtherData")
    @Expose
    private RegisterIntrestFilterDataModel otherData;
    @SerializedName("CurrentLocation")
    @Expose
    private Object currentLocation;
    @SerializedName("GlobalData")
    @Expose
    private Object globalData;
    @SerializedName("RecordCount")
    @Expose
    private Integer recordCount;
    @SerializedName("IsUpdateAvailable")
    @Expose
    private Integer isUpdateAvailable;
    @SerializedName("IsForceUpdate")
    @Expose
    private Integer isForceUpdate;

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

    public List<HomeTemplateDetailModel> getData() {
        return data;
    }

    public void setData(List<HomeTemplateDetailModel> data) {
        this.data = data;
    }

    public Object getOtherValue() {
        return otherValue;
    }

    public void setOtherValue(Object otherValue) {
        this.otherValue = otherValue;
    }

    public RegisterIntrestFilterDataModel getOtherData() {
        return otherData;
    }

    public void setOtherData(RegisterIntrestFilterDataModel otherData) {
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

    public Integer getIsUpdateAvailable() {
        return isUpdateAvailable;
    }

    public void setIsUpdateAvailable(Integer isUpdateAvailable) {
        this.isUpdateAvailable = isUpdateAvailable;
    }

    public Integer getIsForceUpdate() {
        return isForceUpdate;
    }

    public void setIsForceUpdate(Integer isForceUpdate) {
        this.isForceUpdate = isForceUpdate;
    }
}
