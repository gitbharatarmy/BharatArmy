package com.bharatarmy.Models;

public class TravelModel {
    public String match_type;
    public String match_date;
    public String match_first_Country;
    public String match_Second_country;
    public int match_first_country_flag;
    public int match_second_country_flag;
    public String match_location;
    public int match_map;

        public TravelModel(String match_type, String match_date, String match_first_Country, String match_Second_country, int match_first_country_flag, int match_second_country_flag, String match_location,int match_map) {
            this.match_type = match_type;
            this.match_date = match_date;
            this.match_first_Country = match_first_Country;
            this.match_Second_country = match_Second_country;
            this.match_first_country_flag = match_first_country_flag;
            this.match_second_country_flag = match_second_country_flag;
            this.match_location = match_location;
            this.match_map=match_map;
        }

    public String getMatch_type() {
        return match_type;
    }

    public void setMatch_type(String match_type) {
        this.match_type = match_type;
    }

    public String getMatch_date() {
        return match_date;
    }

    public void setMatch_date(String match_date) {
        this.match_date = match_date;
    }

    public String getMatch_first_Country() {
        return match_first_Country;
    }

    public void setMatch_first_Country(String match_first_Country) {
        this.match_first_Country = match_first_Country;
    }

    public String getMatch_Second_country() {
        return match_Second_country;
    }

    public void setMatch_Second_country(String match_Second_country) {
        this.match_Second_country = match_Second_country;
    }

    public int getMatch_first_country_flag() {
        return match_first_country_flag;
    }

    public void setMatch_first_country_flag(int match_first_country_flag) {
        this.match_first_country_flag = match_first_country_flag;
    }

    public int getMatch_second_country_flag() {
        return match_second_country_flag;
    }

    public void setMatch_second_country_flag(int match_second_country_flag) {
        this.match_second_country_flag = match_second_country_flag;
    }

    public String getMatch_location() {
        return match_location;
    }

    public void setMatch_location(String match_location) {
        this.match_location = match_location;
    }

    public int getMatch_map() {
        return match_map;
    }

    public void setMatch_map(int match_map) {
        this.match_map = match_map;
    }
}
