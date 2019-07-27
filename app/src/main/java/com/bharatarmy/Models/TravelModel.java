package com.bharatarmy.Models;

public class TravelModel {
    String match_image;
    String match_title;
    String match_shortDesc;

    String popularcity_image;
    String popularcity_name;
    String popularcity_image_count;


    String tourCityName;
    String tourCountryName;
    String tourImage;
    String tourDescription;
    String tourTotalView;
    String tourTotalComment;
    String bAImage;


    int position;


    String popularCityPlaceName;
    String popularCityPlaceImage;
    String popularCityPlaceRating;
    String popularCityPlaceDescription;

    String cityAllHotelImage;
    String cityAllHotelName;
    String cityAllHotelLocation;
    int cityAllHotelRating;
    String cityAllHotelPrice;


    String cityHotelRoomGallery;
    String cityHotelRoomType;
    int cityHotelRoomHeight;
    int cityHotelRoomWidth;

    String cityHotelAmenitiesImage;
    String cityHotelAmenitiesName;


    int changeMonth;
    int changeYear;


    int matchteamFlag;
    String matchteamVenues;

    String bg_iamge;
    String bg_name;
    String main_titleName;
    String main_desc;
    String button_name;


    public TravelModel(String bg_iamge,String bg_name,String main_titleName,String main_desc,String button_name){
        this.bg_iamge=bg_iamge;
        this.bg_name=bg_name;
        this.main_titleName=main_titleName;
        this.main_desc=main_desc;
        this.button_name=button_name;
    }

    public String getBg_iamge() {
        return bg_iamge;
    }

    public void setBg_iamge(String bg_iamge) {
        this.bg_iamge = bg_iamge;
    }

    public String getBg_name() {
        return bg_name;
    }

    public void setBg_name(String bg_name) {
        this.bg_name = bg_name;
    }

    public String getMain_titleName() {
        return main_titleName;
    }

    public void setMain_titleName(String main_titleName) {
        this.main_titleName = main_titleName;
    }

    public String getMain_desc() {
        return main_desc;
    }

    public void setMain_desc(String main_desc) {
        this.main_desc = main_desc;
    }

    public String getButton_name() {
        return button_name;
    }

    public void setButton_name(String button_name) {
        this.button_name = button_name;
    }

    public TravelModel(){}


    public TravelModel(int matchteamFlag,String matchteamVenues){
        this.matchteamFlag=matchteamFlag;
        this.matchteamVenues=matchteamVenues;
    }

    public int getMatchteamFlag() {
        return matchteamFlag;
    }

    public void setMatchteamFlag(int matchteamFlag) {
        this.matchteamFlag = matchteamFlag;
    }

    public String getMatchteamVenues() {
        return matchteamVenues;
    }

    public void setMatchteamVenues(String matchteamVenues) {
        this.matchteamVenues = matchteamVenues;
    }

    public TravelModel(int changeMonth, int changeYear){
        this.changeMonth=changeMonth;
        this.changeYear=changeYear;
    }

    public int getChangeMonth() {
        return changeMonth;
    }

    public void setChangeMonth(int changeMonth) {
        this.changeMonth = changeMonth;
    }

    public int getChangeYear() {
        return changeYear;
    }

    public void setChangeYear(int changeYear) {
        this.changeYear = changeYear;
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


    public TravelModel(String popularcity_image, String popularcity_name, String popularcity_image_count) {
        this.popularcity_image = popularcity_image;
        this.popularcity_name = popularcity_name;
        this.popularcity_image_count = popularcity_image_count;
    }


    public String getPopularcity_image() {
        return popularcity_image;
    }

    public void setPopularcity_image(String popularcity_image) {
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


    public TravelModel(String tourCityName, String tourCountryName, String tourImage,
                       String tourDescription, String tourTotalView, String tourTotalComment, String bAImage) {

        this.tourCityName = tourCityName;
        this.tourCountryName = tourCountryName;
        this.tourImage = tourImage;
        this.tourDescription = tourDescription;
        this.tourTotalComment = tourTotalComment;
        this.tourTotalView = tourTotalView;
        this.bAImage = bAImage;

    }

    public String getTourCityName() {
        return tourCityName;
    }

    public void setTourCityName(String tourCityName) {
        this.tourCityName = tourCityName;
    }

    public String getTourCountryName() {
        return tourCountryName;
    }

    public void setTourCountryName(String tourCountryName) {
        this.tourCountryName = tourCountryName;
    }

    public String getTourImage() {
        return tourImage;
    }

    public void setTourImage(String tourImage) {
        this.tourImage = tourImage;
    }

    public String getTourDescription() {
        return tourDescription;
    }

    public void setTourDescription(String tourDescription) {
        this.tourDescription = tourDescription;
    }

    public String getTourTotalView() {
        return tourTotalView;
    }

    public void setTourTotalView(String tourTotalView) {
        this.tourTotalView = tourTotalView;
    }

    public String getTourTotalComment() {
        return tourTotalComment;
    }

    public void setTourTotalComment(String tourTotalComment) {
        this.tourTotalComment = tourTotalComment;
    }

    public String getbAImage() {
        return bAImage;
    }

    public void setbAImage(String bAImage) {
        this.bAImage = bAImage;
    }


    public TravelModel(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }


    //    popular city
    public TravelModel(String popularCityPlaceName, String popularCityPlaceImage, String popularCityPlaceRating,
                       String popularCityPlaceDescription) {

        this.popularCityPlaceName = popularCityPlaceName;
        this.popularCityPlaceImage = popularCityPlaceImage;
        this.popularCityPlaceRating = popularCityPlaceRating;
        this.popularCityPlaceDescription = popularCityPlaceDescription;

    }

    public String getPopularCityPlaceName() {
        return popularCityPlaceName;
    }

    public void setPopularCityPlaceName(String popularCityPlaceName) {
        this.popularCityPlaceName = popularCityPlaceName;
    }

    public String getPopularCityPlaceImage() {
        return popularCityPlaceImage;
    }

    public void setPopularCityPlaceImage(String popularCityPlaceImage) {
        this.popularCityPlaceImage = popularCityPlaceImage;
    }

    public String getPopularCityPlaceRating() {
        return popularCityPlaceRating;
    }

    public void setPopularCityPlaceRating(String popularCityPlaceRating) {
        this.popularCityPlaceRating = popularCityPlaceRating;
    }

    public String getPopularCityPlaceDescription() {
        return popularCityPlaceDescription;
    }

    public void setPopularCityPlaceDescription(String popularCityPlaceDescription) {
        this.popularCityPlaceDescription = popularCityPlaceDescription;
    }


    public TravelModel(String cityHotelRoomGallery, String cityHotelRoomType,int cityHotelRoomHeight,int cityHotelRoomWidth) {
        this.cityHotelRoomGallery = cityHotelRoomGallery;
        this.cityHotelRoomType = cityHotelRoomType;
        this.cityHotelRoomHeight=cityHotelRoomHeight;
        this.cityHotelRoomWidth=cityHotelRoomWidth;
    }

    public String getCityHotelRoomGallery() {
        return cityHotelRoomGallery;
    }

    public void setCityHotelRoomGallery(String cityHotelRoomGallery) {
        this.cityHotelRoomGallery = cityHotelRoomGallery;
    }

    public String getCityHotelRoomType() {
        return cityHotelRoomType;
    }

    public void setCityHotelRoomType(String cityHotelRoomType) {
        this.cityHotelRoomType = cityHotelRoomType;
    }

    public int getCityHotelRoomHeight() {
        return cityHotelRoomHeight;
    }

    public void setCityHotelRoomHeight(int cityHotelRoomHeight) {
        this.cityHotelRoomHeight = cityHotelRoomHeight;
    }

    public int getCityHotelRoomWidth() {
        return cityHotelRoomWidth;
    }

    public void setCityHotelRoomWidth(int cityHotelRoomWidth) {
        this.cityHotelRoomWidth = cityHotelRoomWidth;
    }

//    Amenities
    public TravelModel(String cityHotelAmenitiesImage,String cityHotelAmenitiesName){
        this.cityHotelAmenitiesImage=cityHotelAmenitiesImage;
        this.cityHotelAmenitiesName=cityHotelAmenitiesName;
    }

    public String getCityHotelAmenitiesImage() {
        return cityHotelAmenitiesImage;
    }

    public void setCityHotelAmenitiesImage(String cityHotelAmenitiesImage) {
        this.cityHotelAmenitiesImage = cityHotelAmenitiesImage;
    }

    public String getCityHotelAmenitiesName() {
        return cityHotelAmenitiesName;
    }

    public void setCityHotelAmenitiesName(String cityHotelAmenitiesName) {
        this.cityHotelAmenitiesName = cityHotelAmenitiesName;
    }


//    CityAllHotel
    public TravelModel(String cityAllHotelImage,String cityAllHotelName,String cityAllHotelLocation, int cityAllHotelRating, String cityAllHotelPrice){
        this.cityAllHotelImage=cityAllHotelImage;
        this.cityAllHotelName=cityAllHotelName;
        this.cityAllHotelLocation=cityAllHotelLocation;
        this.cityAllHotelRating=cityAllHotelRating;
        this.cityAllHotelPrice=cityAllHotelPrice;
    }

    public String getCityAllHotelImage() {
        return cityAllHotelImage;
    }

    public void setCityAllHotelImage(String cityAllHotelImage) {
        this.cityAllHotelImage = cityAllHotelImage;
    }

    public String getCityAllHotelName() {
        return cityAllHotelName;
    }

    public void setCityAllHotelName(String cityAllHotelName) {
        this.cityAllHotelName = cityAllHotelName;
    }

    public String getCityAllHotelLocation() {
        return cityAllHotelLocation;
    }

    public void setCityAllHotelLocation(String cityAllHotelLocation) {
        this.cityAllHotelLocation = cityAllHotelLocation;
    }

    public int getCityAllHotelRating() {
        return cityAllHotelRating;
    }

    public void setCityAllHotelRating(int cityAllHotelRating) {
        this.cityAllHotelRating = cityAllHotelRating;
    }

    public String getCityAllHotelPrice() {
        return cityAllHotelPrice;
    }

    public void setCityAllHotelPrice(String cityAllHotelPrice) {
        this.cityAllHotelPrice = cityAllHotelPrice;
    }
}
