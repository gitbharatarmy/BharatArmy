package com.bharatarmy.Models;

public class TravelDetailModel {

    public int ticketType_img;
    public int hospitality_img;
    public String hospitality_name;
    public String hospitality_stdium;
    public int hospitality_price;
    public String hospitality_inclusion;
    public String hospitality_inclusion_type;
    public int hospitality_inclusion_icon;
    public String transfer_name;
    public String ticketName;
    public int hospitalityquantity;
    public int ticketprice;
    public TravelDetailModel(int ticketType_img,String ticketName,int ticketprice) {
      this.ticketType_img=ticketType_img;
      this.ticketName=ticketName;
      this.ticketprice=ticketprice;
    }

    public TravelDetailModel(int hospitality_img,String hospitality_name,String hospitality_stdium,int hospitality_price,String hospitality_inclusion,String hospitality_inclusion_type,int hospitality_inclusion_icon ){
        this.hospitality_img=hospitality_img;
        this.hospitality_name=hospitality_name;
        this.hospitality_stdium=hospitality_stdium;
        this.hospitality_price=hospitality_price;
        this.hospitality_inclusion=hospitality_inclusion;
        this.hospitality_inclusion_type=hospitality_inclusion_type;
        this.hospitality_inclusion_icon=hospitality_inclusion_icon;
    }

    public TravelDetailModel(String transfer_name){
        this.transfer_name=transfer_name;
    }


    public int getTicketType_img() {
        return ticketType_img;
    }

    public void setTicketType_img(int ticketType_img) {
        this.ticketType_img = ticketType_img;
    }

    public String getTicketName() {
        return ticketName;
    }

    public void setTicketName(String ticketName) {
        this.ticketName = ticketName;
    }

    public int getTicketprice() {
        return ticketprice;
    }

    public void setTicketprice(int ticketprice) {
        this.ticketprice = ticketprice;
    }

    public String getHospitality_stdium() {
        return hospitality_stdium;
    }

    public void setHospitality_stdium(String hospitality_stdium) {
        this.hospitality_stdium = hospitality_stdium;
    }

    public int getHospitality_price() {
        return hospitality_price;
    }

    public void setHospitality_price(int hospitality_price) {
        this.hospitality_price = hospitality_price;
    }

    public String getHospitality_inclusion() {
        return hospitality_inclusion;
    }

    public void setHospitality_inclusion(String hospitality_inclusion) {
        this.hospitality_inclusion = hospitality_inclusion;
    }

    public String getHospitality_inclusion_type() {
        return hospitality_inclusion_type;
    }

    public void setHospitality_inclusion_type(String hospitality_inclusion_type) {
        this.hospitality_inclusion_type = hospitality_inclusion_type;
    }


    public int getHospitality_inclusion_icon() {
        return hospitality_inclusion_icon;
    }

    public void setHospitality_inclusion_icon(int hospitality_inclusion_icon) {
        this.hospitality_inclusion_icon = hospitality_inclusion_icon;
    }

    public int getHospitality_img() {
        return hospitality_img;
    }

    public void setHospitality_img(int hospitality_img) {
        this.hospitality_img = hospitality_img;
    }

    public String getHospitality_name() {
        return hospitality_name;
    }

    public void setHospitality_name(String hospitality_name) {
        this.hospitality_name = hospitality_name;
    }

    public int getHospitalityquantity() {
        return hospitalityquantity;
    }

    public void setHospitalityquantity(int hospitalityquantity) {
        this.hospitalityquantity = hospitalityquantity;
    }

    public String getTransfer_name() {
        return transfer_name;
    }

    public void setTransfer_name(String transfer_name) {
        this.transfer_name = transfer_name;
    }
}
