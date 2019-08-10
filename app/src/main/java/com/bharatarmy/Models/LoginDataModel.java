package com.bharatarmy.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginDataModel {
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
    private Object profilePic;
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
    @SerializedName("OTP")
    @Expose
    private String oTP;
    @SerializedName("ProfilePicUrl")
    @Expose
    private String profilePicUrl;

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

    public Object getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(Object profilePic) {
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

    public String getOTP() {
        return oTP;
    }

    public void setOTP(String oTP) {
        this.oTP = oTP;
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }

    public Integer getIsBAAdmin() {
        return isBAAdmin;
    }

    public void setIsBAAdmin(Integer isBAAdmin) {
        this.isBAAdmin = isBAAdmin;
    }

    //    Signup Response
    @SerializedName("CountryISOCode")
    @Expose
    private String countryISOCode;
    @SerializedName("CountryPhoneNo")
    @Expose
    private String countryPhoneNo;

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


//     save Register Interest
    @SerializedName("OrderId")
    @Expose
    private Integer orderId;
    @SerializedName("OrderNo")
    @Expose
    private String orderNo;
    @SerializedName("OrderDate")
    @Expose
    private String orderDate;
    @SerializedName("CustomerName")
    @Expose
    private String customerName;
    @SerializedName("CustomerEmail")
    @Expose
    private String customerEmail;
    @SerializedName("CustomerPhoneNo")
    @Expose
    private String customerPhoneNo;
    @SerializedName("DomainId")
    @Expose
    private Integer domainId;
    @SerializedName("OrderStatus")
    @Expose
    private Integer orderStatus;
    @SerializedName("OrderType")
    @Expose
    private Integer orderType;
    @SerializedName("MemberId")
    @Expose
    private Integer memberId;
    @SerializedName("AssignBAMemberId")
    @Expose
    private Integer assignBAMemberId;
    @SerializedName("Logs")
    @Expose
    private Object logs;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
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

    public Integer getDomainId() {
        return domainId;
    }

    public void setDomainId(Integer domainId) {
        this.domainId = domainId;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getAssignBAMemberId() {
        return assignBAMemberId;
    }

    public void setAssignBAMemberId(Integer assignBAMemberId) {
        this.assignBAMemberId = assignBAMemberId;
    }

    public Object getLogs() {
        return logs;
    }

    public void setLogs(Object logs) {
        this.logs = logs;
    }
}
