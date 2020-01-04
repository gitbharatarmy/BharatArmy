package com.bharatarmy.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RegisterIntrestFilterDataModel {
    @SerializedName("Countries")
    @Expose
    private List<InquiryStatusModel> countries = null;
    @SerializedName("Stadiums")
    @Expose
    private List<InquiryStatusModel> stadiums = null;
    @SerializedName("City")
    @Expose
    private List<InquiryStatusModel> city = null;

    public List<InquiryStatusModel> getCountries() {
        return countries;
    }

    public void setCountries(List<InquiryStatusModel> countries) {
        this.countries = countries;
    }

    public List<InquiryStatusModel> getStadiums() {
        return stadiums;
    }

    public void setStadiums(List<InquiryStatusModel> stadiums) {
        this.stadiums = stadiums;
    }

    public List<InquiryStatusModel> getCity() {
        return city;
    }

    public void setCity(List<InquiryStatusModel> city) {
        this.city = city;
    }
}
