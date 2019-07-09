package com.bharatarmy.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TravelDetailModel implements Serializable {
    @SerializedName("ItineraryId")
    @Expose
    private Integer itineraryId;
    @SerializedName("DayNo")
    @Expose
    private Integer dayNo;
    @SerializedName("TemplateTypeId")
    @Expose
    private Integer templateTypeId;
    @SerializedName("TimetToVisit")
    @Expose
    private String timetToVisit;
    @SerializedName("ImageName")
    @Expose
    private String imageName;
    @SerializedName("ImageURL")
    @Expose
    private String imageURL;
    @SerializedName("ItemText")
    @Expose
    private String itemText;
    @SerializedName("ItemDescription")
    @Expose
    private String itemDescription;
    @SerializedName("SupplierId")
    @Expose
    private Integer supplierId;
    @SerializedName("SupplierTypeId")
    @Expose
    private Integer supplierTypeId;

    public Integer getItineraryId() {
        return itineraryId;
    }

    public void setItineraryId(Integer itineraryId) {
        this.itineraryId = itineraryId;
    }

    public Integer getDayNo() {
        return dayNo;
    }

    public void setDayNo(Integer dayNo) {
        this.dayNo = dayNo;
    }

    public Integer getTemplateTypeId() {
        return templateTypeId;
    }

    public void setTemplateTypeId(Integer templateTypeId) {
        this.templateTypeId = templateTypeId;
    }

    public String getTimetToVisit() {
        return timetToVisit;
    }

    public void setTimetToVisit(String timetToVisit) {
        this.timetToVisit = timetToVisit;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getItemText() {
        return itemText;
    }

    public void setItemText(String itemText) {
        this.itemText = itemText;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public Integer getSupplierTypeId() {
        return supplierTypeId;
    }

    public void setSupplierTypeId(Integer supplierTypeId) {
        this.supplierTypeId = supplierTypeId;
    }
}
