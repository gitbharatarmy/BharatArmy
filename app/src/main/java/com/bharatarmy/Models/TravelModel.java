package com.bharatarmy.Models;

public class TravelModel {
    String match_image;
    String match_title;
    String match_shortDesc;

    int popularcity_image;
    String popularcity_name;

    String popularcity_image_count;

    public  TravelModel(String match_image,String match_title,String match_shortDesc){
        this.match_image=match_image;
        this.match_title=match_title;
        this.match_shortDesc=match_shortDesc;
    }


    public String getMatch_image() {
        return match_image;
    }

    public void setMatch_image(String match_image) {
        this.match_image = match_image;
    }

    public String getMatch_title() {
        return match_title;
    }

    public void setMatch_title(String match_title) {
        this.match_title = match_title;
    }

    public String getMatch_shortDesc() {
        return match_shortDesc;
    }

    public void setMatch_shortDesc(String match_shortDesc) {
        this.match_shortDesc = match_shortDesc;
    }




    public TravelModel(int popularcity_image,String popularcity_name,String popularcity_image_count){
        this.popularcity_image=popularcity_image;
        this.popularcity_name=popularcity_name;
        this.popularcity_image_count=popularcity_image_count;
    }


    public int getPopularcity_image() {
        return popularcity_image;
    }

    public void setPopularcity_image(int popularcity_image) {
        this.popularcity_image = popularcity_image;
    }

    public String getPopularcity_name() {
        return popularcity_name;
    }

    public void setPopularcity_name(String popularcity_name) {
        this.popularcity_name = popularcity_name;
    }

    public String getPopularcity_image_count() {
        return popularcity_image_count;
    }

    public void setPopularcity_image_count(String popularcity_image_count) {
        this.popularcity_image_count = popularcity_image_count;
    }
}
