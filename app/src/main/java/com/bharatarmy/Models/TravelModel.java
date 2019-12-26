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

    String matchroom_name;
    String people_count1;
    String people_count2;
    String room_price;
    String room_image;
    String offers;

    String ticket_hospitality_bannerImage;
    String ticket_hospitality_mainheader_title;
    String ticket_hospitality_namecategory;
    String ticket_hospitality_desc;
    String ticket_hospitality_offers;
    String ticket_hospitality_price;
    String ticket_hospitality_inclusion;
    String ticket_hospitality_clickhere;
    String ticket_hospitality_selected;


    String firstcountryname;
    int firstcountryflag;
    String secondcountryname;
    int secondcountryflag;
    String groundname;
    String matchtype;

    int facilityIcon;
    String facilityName;
    String facilityOffer;


    int travelschedulefirstcountryflage;
    String travelschedulefirstcountryname;
    int travelschedulesecondcountryflag;
    String travelschedulesecondcountryname;
    String travelscheduletimedate;
    String travelscheduleground;
    String travelscheduleType;
    String travelmatchimage;

    public TravelModel(int travelschedulefirstcountryflage, String travelschedulefirstcountryname,
                       int travelschedulesecondcountryflag, String travelschedulesecondcountryname,
                       String travelscheduletimedate, String travelscheduleground, String travelscheduleType ,
                       String travelmatchimage) {

        this.travelschedulefirstcountryflage = travelschedulefirstcountryflage;
        this.travelschedulefirstcountryname = travelschedulefirstcountryname;
        this.travelschedulesecondcountryflag = travelschedulesecondcountryflag;
        this.travelschedulesecondcountryname = travelschedulesecondcountryname;
        this.travelscheduletimedate = travelscheduletimedate;
        this.travelscheduleground = travelscheduleground;
        this.travelscheduleType = travelscheduleType;
        this.travelmatchimage=travelmatchimage;
    }

    public int getTravelschedulefirstcountryflage() {
        return travelschedulefirstcountryflage;
    }

    public void setTravelschedulefirstcountryflage(int travelschedulefirstcountryflage) {
        this.travelschedulefirstcountryflage = travelschedulefirstcountryflage;
    }

    public String getTravelschedulefirstcountryname() {
        return travelschedulefirstcountryname;
    }

    public void setTravelschedulefirstcountryname(String travelschedulefirstcountryname) {
        this.travelschedulefirstcountryname = travelschedulefirstcountryname;
    }

    public int getTravelschedulesecondcountryflag() {
        return travelschedulesecondcountryflag;
    }

    public void setTravelschedulesecondcountryflag(int travelschedulesecondcountryflag) {
        this.travelschedulesecondcountryflag = travelschedulesecondcountryflag;
    }

    public String getTravelschedulesecondcountryname() {
        return travelschedulesecondcountryname;
    }

    public void setTravelschedulesecondcountryname(String travelschedulesecondcountryname) {
        this.travelschedulesecondcountryname = travelschedulesecondcountryname;
    }

    public String getTravelscheduletimedate() {
        return travelscheduletimedate;
    }

    public void setTravelscheduletimedate(String travelscheduletimedate) {
        this.travelscheduletimedate = travelscheduletimedate;
    }

    public String getTravelscheduleground() {
        return travelscheduleground;
    }

    public void setTravelscheduleground(String travelscheduleground) {
        this.travelscheduleground = travelscheduleground;
    }

    public String getTravelscheduleType() {
        return travelscheduleType;
    }

    public void setTravelscheduleType(String travelscheduleType) {
        this.travelscheduleType = travelscheduleType;
    }

    public String getTravelmatchimage() {
        return travelmatchimage;
    }

    public void setTravelmatchimage(String travelmatchimage) {
        this.travelmatchimage = travelmatchimage;
    }

    public TravelModel(String bg_iamge, String bg_name, String main_titleName, String main_desc, String button_name) {
        this.bg_iamge = bg_iamge;
        this.bg_name = bg_name;
        this.main_titleName = main_titleName;
        this.main_desc = main_desc;
        this.button_name = button_name;
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


    public TravelModel(int matchteamFlag, String matchteamVenues) {
        this.matchteamFlag = matchteamFlag;
        this.matchteamVenues = matchteamVenues;
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

    public TravelModel(int changeMonth, int changeYear) {
        this.changeMonth = changeMonth;
        this.changeYear = changeYear;
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

    public TravelModel(int facilityIcon, String facilityName, String facilityOffer) {
        this.facilityIcon = facilityIcon;
        this.facilityName = facilityName;
        this.facilityOffer = facilityOffer;
    }

    public int getFacilityIcon() {
        return facilityIcon;
    }

    public void setFacilityIcon(int facilityIcon) {
        this.facilityIcon = facilityIcon;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public String getFacilityOffer() {
        return facilityOffer;
    }

    public void setFacilityOffer(String facilityOffer) {
        this.facilityOffer = facilityOffer;
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


    public TravelModel(String cityHotelRoomGallery, String cityHotelRoomType, int cityHotelRoomHeight, int cityHotelRoomWidth) {
        this.cityHotelRoomGallery = cityHotelRoomGallery;
        this.cityHotelRoomType = cityHotelRoomType;
        this.cityHotelRoomHeight = cityHotelRoomHeight;
        this.cityHotelRoomWidth = cityHotelRoomWidth;
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
    public TravelModel(String cityHotelAmenitiesImage, String cityHotelAmenitiesName) {
        this.cityHotelAmenitiesImage = cityHotelAmenitiesImage;
        this.cityHotelAmenitiesName = cityHotelAmenitiesName;
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
    public TravelModel(String cityAllHotelImage, String cityAllHotelName, String cityAllHotelLocation,
                       int cityAllHotelRating, String cityAllHotelPrice) {
        this.cityAllHotelImage = cityAllHotelImage;
        this.cityAllHotelName = cityAllHotelName;
        this.cityAllHotelLocation = cityAllHotelLocation;
        this.cityAllHotelRating = cityAllHotelRating;
        this.cityAllHotelPrice = cityAllHotelPrice;
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

    //    room type for match hotel
    public TravelModel(String matchroom_name, String people_count1, String people_count2,
                       String room_price, String room_image, String offers) {
        this.matchroom_name = matchroom_name;
        this.people_count1 = people_count1;
        this.people_count2 = people_count2;
        this.room_price = room_price;
        this.room_image = room_image;
        this.offers = offers;
    }

    public String getMatchroom_name() {
        return matchroom_name;
    }

    public void setMatchroom_name(String matchroom_name) {
        this.matchroom_name = matchroom_name;
    }

    public String getPeople_count1() {
        return people_count1;
    }

    public void setPeople_count1(String people_count1) {
        this.people_count1 = people_count1;
    }

    public String getPeople_count2() {
        return people_count2;
    }

    public void setPeople_count2(String people_count2) {
        this.people_count2 = people_count2;
    }

    public String getRoom_price() {
        return room_price;
    }

    public void setRoom_price(String room_price) {
        this.room_price = room_price;
    }

    public String getRoom_image() {
        return room_image;
    }

    public void setRoom_image(String room_image) {
        this.room_image = room_image;
    }

    public String getOffers() {
        return offers;
    }

    public void setOffers(String offers) {
        this.offers = offers;
    }


    //    Ticket and Hospitality
    public TravelModel(String ticket_hospitality_bannerImage, String ticket_hospitality_mainheader_title,
                       String ticket_hospitality_namecategory, String ticket_hospitality_desc,
                       String ticket_hospitality_offers, String ticket_hospitality_price,
                       String ticket_hospitality_inclusion, String ticket_hospitality_clickhere,
                       String ticket_hospitality_selected) {


        this.ticket_hospitality_bannerImage = ticket_hospitality_bannerImage;
        this.ticket_hospitality_mainheader_title = ticket_hospitality_mainheader_title;
        this.ticket_hospitality_namecategory = ticket_hospitality_namecategory;
        this.ticket_hospitality_desc = ticket_hospitality_desc;
        this.ticket_hospitality_offers = ticket_hospitality_offers;
        this.ticket_hospitality_price = ticket_hospitality_price;
        this.ticket_hospitality_inclusion = ticket_hospitality_inclusion;
        this.ticket_hospitality_clickhere = ticket_hospitality_clickhere;
        this.ticket_hospitality_selected = ticket_hospitality_selected;
    }

    public String getTicket_hospitality_bannerImage() {
        return ticket_hospitality_bannerImage;
    }

    public void setTicket_hospitality_bannerImage(String ticket_hospitality_bannerImage) {
        this.ticket_hospitality_bannerImage = ticket_hospitality_bannerImage;
    }

    public String getTicket_hospitality_mainheader_title() {
        return ticket_hospitality_mainheader_title;
    }

    public void setTicket_hospitality_mainheader_title(String ticket_hospitality_mainheader_title) {
        this.ticket_hospitality_mainheader_title = ticket_hospitality_mainheader_title;
    }

    public String getTicket_hospitality_namecategory() {
        return ticket_hospitality_namecategory;
    }

    public void setTicket_hospitality_namecategory(String ticket_hospitality_namecategory) {
        this.ticket_hospitality_namecategory = ticket_hospitality_namecategory;
    }

    public String getTicket_hospitality_desc() {
        return ticket_hospitality_desc;
    }

    public void setTicket_hospitality_desc(String ticket_hospitality_desc) {
        this.ticket_hospitality_desc = ticket_hospitality_desc;
    }

    public String getTicket_hospitality_offers() {
        return ticket_hospitality_offers;
    }

    public void setTicket_hospitality_offers(String ticket_hospitality_offers) {
        this.ticket_hospitality_offers = ticket_hospitality_offers;
    }

    public String getTicket_hospitality_price() {
        return ticket_hospitality_price;
    }

    public void setTicket_hospitality_price(String ticket_hospitality_price) {
        this.ticket_hospitality_price = ticket_hospitality_price;
    }

    public String getTicket_hospitality_inclusion() {
        return ticket_hospitality_inclusion;
    }

    public void setTicket_hospitality_inclusion(String ticket_hospitality_inclusion) {
        this.ticket_hospitality_inclusion = ticket_hospitality_inclusion;
    }

    public String getTicket_hospitality_clickhere() {
        return ticket_hospitality_clickhere;
    }

    public void setTicket_hospitality_clickhere(String ticket_hospitality_clickhere) {
        this.ticket_hospitality_clickhere = ticket_hospitality_clickhere;
    }

    public String getTicket_hospitality_selected() {
        return ticket_hospitality_selected;
    }

    public void setTicket_hospitality_selected(String ticket_hospitality_selected) {
        this.ticket_hospitality_selected = ticket_hospitality_selected;
    }

    public TravelModel(String firstcountryname, int firstcountryflag, String secondcountryname,
                       int secondcountryflag, String groundname, String matchtype) {
        this.firstcountryname = firstcountryname;
        this.firstcountryflag = firstcountryflag;
        this.secondcountryname = secondcountryname;
        this.secondcountryflag = secondcountryflag;
        this.groundname = groundname;
        this.matchtype = matchtype;
    }

    public String getFirstcountryname() {
        return firstcountryname;
    }

    public void setFirstcountryname(String firstcountryname) {
        this.firstcountryname = firstcountryname;
    }

    public int getFirstcountryflag() {
        return firstcountryflag;
    }

    public void setFirstcountryflag(int firstcountryflag) {
        this.firstcountryflag = firstcountryflag;
    }

    public String getSecondcountryname() {
        return secondcountryname;
    }

    public void setSecondcountryname(String secondcountryname) {
        this.secondcountryname = secondcountryname;
    }

    public int getSecondcountryflag() {
        return secondcountryflag;
    }

    public void setSecondcountryflag(int secondcountryflag) {
        this.secondcountryflag = secondcountryflag;
    }

    public String getGroundname() {
        return groundname;
    }

    public void setGroundname(String groundname) {
        this.groundname = groundname;
    }

    public String getMatchtype() {
        return matchtype;
    }

    public void setMatchtype(String matchtype) {
        this.matchtype = matchtype;
    }
}
