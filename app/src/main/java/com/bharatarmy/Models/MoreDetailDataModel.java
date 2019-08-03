package com.bharatarmy.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MoreDetailDataModel {

    @SerializedName("LstMatches")
    @Expose
    private Object lstMatches;
    @SerializedName("strInquiryType")
    @Expose
    private String strInquiryType;
    @SerializedName("strOrderStatus")
    @Expose
    private String strOrderStatus;
    @SerializedName("strInquiryTypePrefix")
    @Expose
    private String strInquiryTypePrefix;
    @SerializedName("OrderId")
    @Expose
    private Integer orderId;
    @SerializedName("CustomerName")
    @Expose
    private String customerName;
    @SerializedName("CustomerEmail")
    @Expose
    private String customerEmail;
    @SerializedName("CustomerPhoneNo")
    @Expose
    private String customerPhoneNo;
    @SerializedName("OrderStatus")
    @Expose
    private Integer orderStatus;
    @SerializedName("OrderDate")
    @Expose
    private String orderDate;
    @SerializedName("OrderType")
    @Expose
    private Integer orderType;
    @SerializedName("ItemDescription")
    @Expose
    private String itemDescription;
    @SerializedName("OrderNo")
    @Expose
    private String orderNo;
    @SerializedName("strInquiryTypeColor")
    @Expose
    private String strInquiryTypeColor;
    @SerializedName("strInquiryTypeFontColor")
    @Expose
    private String strInquiryTypeFontColor;

    public Object getLstMatches() {
        return lstMatches;
    }

    public void setLstMatches(Object lstMatches) {
        this.lstMatches = lstMatches;
    }

    public String getStrInquiryType() {
        return strInquiryType;
    }

    public void setStrInquiryType(String strInquiryType) {
        this.strInquiryType = strInquiryType;
    }

    public String getStrOrderStatus() {
        return strOrderStatus;
    }

    public void setStrOrderStatus(String strOrderStatus) {
        this.strOrderStatus = strOrderStatus;
    }

    public String getStrInquiryTypePrefix() {
        return strInquiryTypePrefix;
    }

    public void setStrInquiryTypePrefix(String strInquiryTypePrefix) {
        this.strInquiryTypePrefix = strInquiryTypePrefix;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerPhoneNo() {
        return customerPhoneNo;
    }

    public void setCustomerPhoneNo(String customerPhoneNo) {
        this.customerPhoneNo = customerPhoneNo;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getStrInquiryTypeColor() {
        return strInquiryTypeColor;
    }

    public void setStrInquiryTypeColor(String strInquiryTypeColor) {
        this.strInquiryTypeColor = strInquiryTypeColor;
    }

    public String getStrInquiryTypeFontColor() {
        return strInquiryTypeFontColor;
    }

    public void setStrInquiryTypeFontColor(String strInquiryTypeFontColor) {
        this.strInquiryTypeFontColor = strInquiryTypeFontColor;
    }

//    ========= Inquiry detail ============
@SerializedName("strMatchType")
@Expose
private String strMatchType;
    @SerializedName("strLocalMatchDateTime")
    @Expose
    private String strLocalMatchDateTime;

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
    @SerializedName("FromCountryName")
    @Expose
    private String fromCountryName;
    @SerializedName("ToCountryId")
    @Expose
    private Integer toCountryId;
    @SerializedName("ToCountryName")
    @Expose
    private String toCountryName;
    @SerializedName("MatchImageName")
    @Expose
    private String matchImageName;
    @SerializedName("StadiumId")
    @Expose
    private Integer stadiumId;
    @SerializedName("StadiumName")
    @Expose
    private String stadiumName;
    @SerializedName("MatchUTCDate")
    @Expose
    private String matchUTCDate;
    @SerializedName("GlobalTypeName")
    @Expose
    private String globalTypeName;
    @SerializedName("ImageFileName")
    @Expose
    private String imageFileName;
    @SerializedName("BannerImage")
    @Expose
    private String bannerImage;
    @SerializedName("GlobalTypeId")
    @Expose
    private Integer globalTypeId;
    @SerializedName("GlobalTypeMasterId")
    @Expose
    private Integer globalTypeMasterId;
    @SerializedName("MatchPriceId")
    @Expose
    private Integer matchPriceId;
    @SerializedName("TypeShortDescription")
    @Expose
    private String typeShortDescription;
    @SerializedName("TourName")
    @Expose
    private String tourName;
    @SerializedName("Qty")
    @Expose
    private Integer qty;
    @SerializedName("MatchNo")
    @Expose
    private Integer matchNo;
    @SerializedName("SalePrice")
    @Expose
    private Integer salePrice;

    public String getStrMatchType() {
        return strMatchType;
    }

    public void setStrMatchType(String strMatchType) {
        this.strMatchType = strMatchType;
    }

    public String getStrLocalMatchDateTime() {
        return strLocalMatchDateTime;
    }

    public void setStrLocalMatchDateTime(String strLocalMatchDateTime) {
        this.strLocalMatchDateTime = strLocalMatchDateTime;
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

    public String getFromCountryName() {
        return fromCountryName;
    }

    public void setFromCountryName(String fromCountryName) {
        this.fromCountryName = fromCountryName;
    }

    public Integer getToCountryId() {
        return toCountryId;
    }

    public void setToCountryId(Integer toCountryId) {
        this.toCountryId = toCountryId;
    }

    public String getToCountryName() {
        return toCountryName;
    }

    public void setToCountryName(String toCountryName) {
        this.toCountryName = toCountryName;
    }

    public String getMatchImageName() {
        return matchImageName;
    }

    public void setMatchImageName(String matchImageName) {
        this.matchImageName = matchImageName;
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

    public String getMatchUTCDate() {
        return matchUTCDate;
    }

    public void setMatchUTCDate(String matchUTCDate) {
        this.matchUTCDate = matchUTCDate;
    }

    public String getGlobalTypeName() {
        return globalTypeName;
    }

    public void setGlobalTypeName(String globalTypeName) {
        this.globalTypeName = globalTypeName;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public String getBannerImage() {
        return bannerImage;
    }

    public void setBannerImage(String bannerImage) {
        this.bannerImage = bannerImage;
    }

    public Integer getGlobalTypeId() {
        return globalTypeId;
    }

    public void setGlobalTypeId(Integer globalTypeId) {
        this.globalTypeId = globalTypeId;
    }

    public Integer getGlobalTypeMasterId() {
        return globalTypeMasterId;
    }

    public void setGlobalTypeMasterId(Integer globalTypeMasterId) {
        this.globalTypeMasterId = globalTypeMasterId;
    }

    public Integer getMatchPriceId() {
        return matchPriceId;
    }

    public void setMatchPriceId(Integer matchPriceId) {
        this.matchPriceId = matchPriceId;
    }

    public String getTypeShortDescription() {
        return typeShortDescription;
    }

    public void setTypeShortDescription(String typeShortDescription) {
        this.typeShortDescription = typeShortDescription;
    }

    public String getTourName() {
        return tourName;
    }

    public void setTourName(String tourName) {
        this.tourName = tourName;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Integer getMatchNo() {
        return matchNo;
    }

    public void setMatchNo(Integer matchNo) {
        this.matchNo = matchNo;
    }

    public Integer getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Integer salePrice) {
        this.salePrice = salePrice;
    }

//    =====AssignMember List====
@SerializedName("Email")
@Expose
private String email;
    @SerializedName("PhoneNo")
    @Expose
    private String phoneNo;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("ProfilePic")
    @Expose
    private String profilePic;
    @SerializedName("IsEmailVerified")
    @Expose
    private Integer isEmailVerified;
    @SerializedName("IsNumberVerified")
    @Expose
    private Integer isNumberVerified;
    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("Gender")
    @Expose
    private Integer gender;
    @SerializedName("ProfilePicUrl")
    @Expose
    private String profilePicUrl;
    @SerializedName("CountryISOCode")
    @Expose
    private String countryISOCode;
    @SerializedName("CountryPhoneNo")
    @Expose
    private String countryPhoneNo;
    @SerializedName("IsBAAdmin")
    @Expose
    private Integer isBAAdmin;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public Integer getIsEmailVerified() {
        return isEmailVerified;
    }

    public void setIsEmailVerified(Integer isEmailVerified) {
        this.isEmailVerified = isEmailVerified;
    }

    public Integer getIsNumberVerified() {
        return isNumberVerified;
    }

    public void setIsNumberVerified(Integer isNumberVerified) {
        this.isNumberVerified = isNumberVerified;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }

    public String getCountryISOCode() {
        return countryISOCode;
    }

    public void setCountryISOCode(String countryISOCode) {
        this.countryISOCode = countryISOCode;
    }

    public String getCountryPhoneNo() {
        return countryPhoneNo;
    }

    public void setCountryPhoneNo(String countryPhoneNo) {
        this.countryPhoneNo = countryPhoneNo;
    }

    public Integer getIsBAAdmin() {
        return isBAAdmin;
    }

    public void setIsBAAdmin(Integer isBAAdmin) {
        this.isBAAdmin = isBAAdmin;
    }
}
