package com.bharatarmy.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ObjToCountry {
    @SerializedName("HomeTempleteIDModel")
    @Expose
    private Integer iD;
    @SerializedName("Name")
    @Expose
    private Object name;
    @SerializedName("CountryCode")
    @Expose
    private Object countryCode;
    @SerializedName("StateMasters")
    @Expose
    private List<Object> stateMasters = null;
    @SerializedName("CountryId")
    @Expose
    private Integer countryId;
    @SerializedName("CountryName")
    @Expose
    private String countryName;
    @SerializedName("CountryFlag")
    @Expose
    private String countryFlag;
    @SerializedName("IsDisplay")
    @Expose
    private Integer isDisplay;
    @SerializedName("url")
    @Expose
    private Object url;
    @SerializedName("CountryFlagUrl")
    @Expose
    private String countryFlagUrl;
    public Integer getID() {
        return iD;
    }

    public void setID(Integer iD) {
        this.iD = iD;
    }

    public Object getName() {
        return name;
    }

    public void setName(Object name) {
        this.name = name;
    }

    public Object getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(Object countryCode) {
        this.countryCode = countryCode;
    }

    public List<Object> getStateMasters() {
        return stateMasters;
    }

    public void setStateMasters(List<Object> stateMasters) {
        this.stateMasters = stateMasters;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryFlag() {
        return countryFlag;
    }

    public void setCountryFlag(String countryFlag) {
        this.countryFlag = countryFlag;
    }

    public Integer getIsDisplay() {
        return isDisplay;
    }

    public void setIsDisplay(Integer isDisplay) {
        this.isDisplay = isDisplay;
    }

    public Object getUrl() {
        return url;
    }

    public void setUrl(Object url) {
        this.url = url;
    }

    public String getCountryFlagUrl() {
        return countryFlagUrl;
    }

    public void setCountryFlagUrl(String countryFlagUrl) {
        this.countryFlagUrl = countryFlagUrl;
    }
}
