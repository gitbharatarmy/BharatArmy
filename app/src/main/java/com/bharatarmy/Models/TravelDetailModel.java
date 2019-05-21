package com.bharatarmy.Models;

public class TravelDetailModel {

    public int ticketType_img;
    public int hospitality_img;
    public String hospitality_name;
    public String transfer_name;
    public TravelDetailModel(int ticketType_img) {
      this.ticketType_img=ticketType_img;
    }

    public TravelDetailModel(int hospitality_img,String hospitality_name){
        this.hospitality_img=hospitality_img;
        this.hospitality_name=hospitality_name;
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

    public String getTransfer_name() {
        return transfer_name;
    }

    public void setTransfer_name(String transfer_name) {
        this.transfer_name = transfer_name;
    }
}
