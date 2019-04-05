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
}
