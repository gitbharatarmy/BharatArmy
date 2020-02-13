package com.bharatarmy.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HomeTemplateDetailModel {
    @SerializedName("MainHeaderText")
    @Expose
    private String mainHeaderText;
    @SerializedName("MainHaderImage")
    @Expose
    private String mainHaderImage;
    @SerializedName("MainHeaderImageUrl")
    @Expose
    private String mainHeaderImageUrl;
    @SerializedName("SecondHeaderType")
    @Expose
    private Integer secondHeaderType;
    @SerializedName("SecondHeaderImage")
    @Expose
    private String secondHeaderImage;
    @SerializedName("SecondHeaderImageUrl")
    @Expose
    private String secondHeaderImageUrl;
    @SerializedName("SecondHeaderTag")
    @Expose
    private String secondHeaderTag;
    @SerializedName("SecondHeaderText")
    @Expose
    private String secondHeaderText;
    @SerializedName("SecondHeaderSubText")
    @Expose
    private String secondHeaderSubText;
    @SerializedName("SecondHeaderButtonText")
    @Expose
    private String secondHeaderButtonText;
    @SerializedName("SecondHaderButtonColor")
    @Expose
    private String secondHaderButtonColor;
    @SerializedName("SecondHaderButtonFontColor")
    @Expose
    private String secondHaderButtonFontColor;
    @SerializedName("ReferenceId")
    @Expose
    private String referenceId;
    @SerializedName("RedirectUrl")
    @Expose
    private Object redirectUrl;

    public String getMainHeaderText() {
        return mainHeaderText;
    }

    public void setMainHeaderText(String mainHeaderText) {
        this.mainHeaderText = mainHeaderText;
    }

    public String getMainHaderImage() {
        return mainHaderImage;
    }

    public void setMainHaderImage(String mainHaderImage) {
        this.mainHaderImage = mainHaderImage;
    }

    public String getMainHeaderImageUrl() {
        return mainHeaderImageUrl;
    }

    public void setMainHeaderImageUrl(String mainHeaderImageUrl) {
        this.mainHeaderImageUrl = mainHeaderImageUrl;
    }

    public Integer getSecondHeaderType() {
        return secondHeaderType;
    }

    public void setSecondHeaderType(Integer secondHeaderType) {
        this.secondHeaderType = secondHeaderType;
    }

    public String getSecondHeaderImage() {
        return secondHeaderImage;
    }

    public void setSecondHeaderImage(String secondHeaderImage) {
        this.secondHeaderImage = secondHeaderImage;
    }

    public String getSecondHeaderImageUrl() {
        return secondHeaderImageUrl;
    }

    public void setSecondHeaderImageUrl(String secondHeaderImageUrl) {
        this.secondHeaderImageUrl = secondHeaderImageUrl;
    }

    public String getSecondHeaderTag() {
        return secondHeaderTag;
    }

    public void setSecondHeaderTag(String secondHeaderTag) {
        this.secondHeaderTag = secondHeaderTag;
    }

    public String getSecondHeaderText() {
        return secondHeaderText;
    }

    public void setSecondHeaderText(String secondHeaderText) {
        this.secondHeaderText = secondHeaderText;
    }

    public String getSecondHeaderSubText() {
        return secondHeaderSubText;
    }

    public void setSecondHeaderSubText(String secondHeaderSubText) {
        this.secondHeaderSubText = secondHeaderSubText;
    }

    public String getSecondHeaderButtonText() {
        return secondHeaderButtonText;
    }

    public void setSecondHeaderButtonText(String secondHeaderButtonText) {
        this.secondHeaderButtonText = secondHeaderButtonText;
    }

    public String getSecondHaderButtonColor() {
        return secondHaderButtonColor;
    }

    public void setSecondHaderButtonColor(String secondHaderButtonColor) {
        this.secondHaderButtonColor = secondHaderButtonColor;
    }

    public String getSecondHaderButtonFontColor() {
        return secondHaderButtonFontColor;
    }

    public void setSecondHaderButtonFontColor(String secondHaderButtonFontColor) {
        this.secondHaderButtonFontColor = secondHaderButtonFontColor;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public Object getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(Object redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    @SerializedName("SiteURL")
    @Expose
    private String siteURL;
    @SerializedName("StoryDetails")
    @Expose
    private HomeTempleteStoryDetailModel storyDetails;


    public String getSiteURL() {
        return siteURL;
    }

    public void setSiteURL(String siteURL) {
        this.siteURL = siteURL;
    }

    public HomeTempleteStoryDetailModel getStoryDetails() {
        return storyDetails;
    }

    public void setStoryDetails(HomeTempleteStoryDetailModel storyDetails) {
        this.storyDetails = storyDetails;
    }


//    ****************** Register Interest Detail **************************

//   String

    @SerializedName("ObjFromCountry")
    @Expose
    private ObjFromCountry objFromCountry;
    @SerializedName("ObjToCountry")
    @Expose
    private ObjToCountry objToCountry;
    @SerializedName("strMatchDateTime")
    @Expose
    private String strMatchDateTime;
    @SerializedName("strMatchDateWithoutTime")
    @Expose
    private String strMatchDateWithoutTime;
    @SerializedName("strMatchType")
    @Expose
    private String strMatchType;
    @SerializedName("strItemText")
    @Expose
    private String strItemText;
    @SerializedName("TournamentMatchId")
    @Expose
    private Integer tournamentMatchId;
    @SerializedName("TournamentId")
    @Expose
    private Integer tournamentId;
    @SerializedName("MatchTypeId")
    @Expose
    private Integer matchTypeId;
    @SerializedName("FromCountryId")
    @Expose
    private Integer fromCountryId;
    @SerializedName("DbFromCountryName")
    @Expose
    private String dbFromCountryName;
    @SerializedName("ToCountryId")
    @Expose
    private Integer toCountryId;
    @SerializedName("DbToCountryName")
    @Expose
    private String dbToCountryName;
    @SerializedName("MatchUTCDate")
    @Expose
    private String matchUTCDate;
    @SerializedName("TourName")
    @Expose
    private String tourName;
    @SerializedName("StadiumId")
    @Expose
    private Integer stadiumId;
    @SerializedName("StadiumName")
    @Expose
    private String stadiumName;
    @SerializedName("MatchNo")
    @Expose
    private Integer matchNo;
    @SerializedName("CheckboxStatus")
    @Expose
    private String check;


    public ObjFromCountry getObjFromCountry() {
        return objFromCountry;
    }

    public void setObjFromCountry(ObjFromCountry objFromCountry) {
        this.objFromCountry = objFromCountry;
    }

    public ObjToCountry getObjToCountry() {
        return objToCountry;
    }

    public void setObjToCountry(ObjToCountry objToCountry) {
        this.objToCountry = objToCountry;
    }

    public String getStrMatchDateTime() {
        return strMatchDateTime;
    }

    public void setStrMatchDateTime(String strMatchDateTime) {
        this.strMatchDateTime = strMatchDateTime;
    }

    public String getStrMatchDateWithoutTime() {
        return strMatchDateWithoutTime;
    }

    public void setStrMatchDateWithoutTime(String strMatchDateWithoutTime) {
        this.strMatchDateWithoutTime = strMatchDateWithoutTime;
    }

    public String getStrMatchType() {
        return strMatchType;
    }

    public void setStrMatchType(String strMatchType) {
        this.strMatchType = strMatchType;
    }

    public String getStrItemText() {
        return strItemText;
    }

    public void setStrItemText(String strItemText) {
        this.strItemText = strItemText;
    }

    public Integer getTournamentMatchId() {
        return tournamentMatchId;
    }

    public void setTournamentMatchId(Integer tournamentMatchId) {
        this.tournamentMatchId = tournamentMatchId;
    }

    public Integer getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(Integer tournamentId) {
        this.tournamentId = tournamentId;
    }

    public Integer getMatchTypeId() {
        return matchTypeId;
    }

    public void setMatchTypeId(Integer matchTypeId) {
        this.matchTypeId = matchTypeId;
    }

    public Integer getFromCountryId() {
        return fromCountryId;
    }

    public void setFromCountryId(Integer fromCountryId) {
        this.fromCountryId = fromCountryId;
    }

    public String getDbFromCountryName() {
        return dbFromCountryName;
    }

    public void setDbFromCountryName(String dbFromCountryName) {
        this.dbFromCountryName = dbFromCountryName;
    }

    public Integer getToCountryId() {
        return toCountryId;
    }

    public void setToCountryId(Integer toCountryId) {
        this.toCountryId = toCountryId;
    }

    public String getDbToCountryName() {
        return dbToCountryName;
    }

    public void setDbToCountryName(String dbToCountryName) {
        this.dbToCountryName = dbToCountryName;
    }

    public String getMatchUTCDate() {
        return matchUTCDate;
    }

    public void setMatchUTCDate(String matchUTCDate) {
        this.matchUTCDate = matchUTCDate;
    }

    public String getTourName() {
        return tourName;
    }

    public void setTourName(String tourName) {
        this.tourName = tourName;
    }

    public Integer getStadiumId() {
        return stadiumId;
    }

    public void setStadiumId(Integer stadiumId) {
        this.stadiumId = stadiumId;
    }

    public String getStadiumName() {
        return stadiumName;
    }

    public void setStadiumName(String stadiumName) {
        this.stadiumName = stadiumName;
    }

    public Integer getMatchNo() {
        return matchNo;
    }

    public void setMatchNo(Integer matchNo) {
        this.matchNo = matchNo;
    }

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }

}
