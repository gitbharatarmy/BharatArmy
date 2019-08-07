package com.bharatarmy.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InquiryOtherDataModel {
    @SerializedName("Status")
    @Expose
    private List<InquiryStatusModel> status = null;
    @SerializedName("Types")
    @Expose
    private List<InquiryOrderTypeModel> types = null;

    public List<InquiryStatusModel> getStatus() {
        return status;
    }

    public void setStatus(List<InquiryStatusModel> status) {
        this.status = status;
    }

    public List<InquiryOrderTypeModel> getTypes() {
        return types;
    }

    public void setTypes(List<InquiryOrderTypeModel> types) {
        this.types = types;
    }

}
