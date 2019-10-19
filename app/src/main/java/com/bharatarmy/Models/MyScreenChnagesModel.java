package com.bharatarmy.Models;

public class MyScreenChnagesModel {
    String message;

    String roomName;
    String roomImage;
    String position;


    public MyScreenChnagesModel(String message){
        this.message=message;
    }
    public String getMessage() {
        return message;
    }


    public MyScreenChnagesModel(String roomName,String roomImage,String position){
        this.roomName=roomName;
        this.roomImage=roomImage;
        this.position=position;
}


    public String getRoomName() {
        return roomName;
    }

    public String getRoomImage() {
        return roomImage;
    }

    public String getPosition() {
        return position;
    }
}
