package com.bharatarmy.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TravelDataModel {
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("FixturesData")
    @Expose
    private List<HomeTemplateDetailModel> fixturesData = null;
    @SerializedName("RelatedHospitalityData")
    @Expose
    private List<HomeTemplateDetailModel> relatedHospitalityData = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<HomeTemplateDetailModel> getFixturesData() {
        return fixturesData;
    }

    public void setFixturesData(List<HomeTemplateDetailModel> fixturesData) {
        this.fixturesData = fixturesData;
    }

    public List<HomeTemplateDetailModel> getRelatedHospitalityData() {
        return relatedHospitalityData;
    }

    public void setRelatedHospitalityData(List<HomeTemplateDetailModel> relatedHospitalityData) {
        this.relatedHospitalityData = relatedHospitalityData;
    }

}
