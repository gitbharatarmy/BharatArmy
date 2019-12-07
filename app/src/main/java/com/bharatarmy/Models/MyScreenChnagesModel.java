package com.bharatarmy.Models;

public class MyScreenChnagesModel {
    String message;

    String roomName;
    String roomImage;
    String position;

    int ImageLikeposition;

    String privacyimage;
    String privacyname;

    String updateprofile;
    String screen;


    public MyScreenChnagesModel(String message){
        this.message=message;
    }
    public String getMessage() {
        return message;
    }

    public MyScreenChnagesModel(int ImageLikeposition){
        this.ImageLikeposition=ImageLikeposition;
    }

    public int getImageLikeposition() {
        return ImageLikeposition;
    }

    public void setImageLikeposition(int imageLikeposition) {
        ImageLikeposition = imageLikeposition;
    }

    public MyScreenChnagesModel(String roomName, String roomImage, String position){
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



    public MyScreenChnagesModel(String privacyname,String privacyimage){
        this.privacyimage=privacyimage;
        this.privacyname=privacyname;
    }

    public String getPrivacyimage() {
        return privacyimage;
    }

    public void setPrivacyimage(String privacyimage) {
        this.privacyimage = privacyimage;
    }

    public String getPrivacyname() {
        return privacyname;
    }

    public void setPrivacyname(String privacyname) {
        this.privacyname = privacyname;
    }


}
