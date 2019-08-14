package com.bharatarmy.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InquiryOrderTypeModel {
    @SerializedName("value")
    @Expose
    private String value;
    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("ClickStatus")
    @Expose
    private String clickstatus;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getClickstatus() {
        return clickstatus;
    }

    public void setClickstatus(String clickstatus) {
        this.clickstatus = clickstatus;
    }
}
