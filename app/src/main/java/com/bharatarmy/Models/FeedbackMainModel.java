package com.bharatarmy.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FeedbackMainModel {
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
    private List<LoginDataModel> otherData = null;
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
    @SerializedName("VersionCode")
    @Expose
    private Integer versionCode;
    @SerializedName("CurrentVersion")
    @Expose
    private Double currentVersion;
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

    public List<LoginDataModel> getOtherData() {
        return otherData;
    }

    public void setOtherData(List<LoginDataModel> otherData) {
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
    public Integer getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(Integer versionCode) {
        this.versionCode = versionCode;
    }

    public Double getCurrentVersion() {
        return currentVersion;
    }

    public void setCurrentVersion(Double currentVersion) {
        this.currentVersion = currentVersion;
    }

}
