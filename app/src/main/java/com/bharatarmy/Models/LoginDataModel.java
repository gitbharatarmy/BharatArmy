package com.bharatarmy.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

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
    @SerializedName("Addressline1")
    @Expose
    private String addressline1;
    @SerializedName("Addressline2")
    @Expose
    private String addressline2;
    @SerializedName("Area")
    @Expose
    private String area;
    @SerializedName("strState")
    @Expose
    private String strState;
    @SerializedName("StateId")
    @Expose
    private Integer stateId;
    @SerializedName("strCityName")
    @Expose
    private String strCityName;
    @SerializedName("CityId")
    @Expose
    private Integer cityId;
    @SerializedName("Pincode")
    @Expose
    private String pincode;
    @SerializedName("MemberType")
    @Expose
    private String memberType;
    @SerializedName("FirstName")
    @Expose
    private String firstName;
    @SerializedName("LastName")
    @Expose
    private String lastName;

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

    public String getAddressline1() {
        return addressline1;
    }

    public void setAddressline1(String addressline1) {
        this.addressline1 = addressline1;
    }

    public String getAddressline2() {
        return addressline2;
    }

    public void setAddressline2(String addressline2) {
        this.addressline2 = addressline2;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getStrState() {
        return strState;
    }

    public void setStrState(String strState) {
        this.strState = strState;
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public String getStrCityName() {
        return strCityName;
    }

    public void setStrCityName(String strCityName) {
        this.strCityName = strCityName;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getMemberType() {
        return memberType;
    }

    public void setMemberType(String memberType) {
        this.memberType = memberType;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

//    post view Detail

    @SerializedName("PostId")
    @Expose
    private Integer postId;
    @SerializedName("Likes")
    @Expose
    private Integer likes;
    @SerializedName("Posted")
    @Expose
    private Integer posted;
    @SerializedName("Comments")
    @Expose
    private Integer comments;
    @SerializedName("PostView")
    @Expose
    private Integer postView;

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getPosted() {
        return posted;
    }

    public void setPosted(Integer posted) {
        this.posted = posted;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public Integer getPostView() {
        return postView;
    }

    public void setPostView(Integer postView) {
        this.postView = postView;
    }

    //    SFA user entry
//    MemberName
    @SerializedName("BAMemberId")
    @Expose
    private Integer bAMemberId;

    @SerializedName("MemberName")
    @Expose
    private String memberName;

    public Integer getBAMemberId() {
        return bAMemberId;
    }

    public void setBAMemberId(Integer bAMemberId) {
        this.bAMemberId = bAMemberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }


    //    Current Country Code
    @SerializedName("IPAddress")
    @Expose
    private Object iPAddress;
    @SerializedName("IsoCode")
    @Expose
    private String isoCode;
    @SerializedName("GeoNameId")
    @Expose
    private Object geoNameId;
    @SerializedName("CurrencyId")
    @Expose
    private Integer currencyId;
    @SerializedName("CurrencyName")
    @Expose
    private Object currencyName;
    @SerializedName("CurrencySymbol")
    @Expose
    private Object currencySymbol;
    @SerializedName("CurrencyPrefix")
    @Expose
    private Integer currencyPrefix;
    @SerializedName("CurrentExchangeRate")
    @Expose
    private Object currentExchangeRate;
    @SerializedName("RateMargin")
    @Expose
    private Object rateMargin;
    @SerializedName("TotalExchangeRate")
    @Expose
    private Object totalExchangeRate;
    @SerializedName("LogoFileName")
    @Expose
    private Object logoFileName;
    @SerializedName("ShortName")
    @Expose
    private Object shortName;
    @SerializedName("CountryIds")
    @Expose
    private Object countryIds;

    public Object getIPAddress() {
        return iPAddress;
    }

    public void setIPAddress(Object iPAddress) {
        this.iPAddress = iPAddress;
    }

    public String getIsoCode() {
        return isoCode;
    }

    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
    }


    public Object getGeoNameId() {
        return geoNameId;
    }

    public void setGeoNameId(Object geoNameId) {
        this.geoNameId = geoNameId;
    }

    public Integer getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Integer currencyId) {
        this.currencyId = currencyId;
    }

    public Object getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(Object currencyName) {
        this.currencyName = currencyName;
    }

    public Object getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(Object currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public Integer getCurrencyPrefix() {
        return currencyPrefix;
    }

    public void setCurrencyPrefix(Integer currencyPrefix) {
        this.currencyPrefix = currencyPrefix;
    }

    public Object getCurrentExchangeRate() {
        return currentExchangeRate;
    }

    public void setCurrentExchangeRate(Object currentExchangeRate) {
        this.currentExchangeRate = currentExchangeRate;
    }

    public Object getRateMargin() {
        return rateMargin;
    }

    public void setRateMargin(Object rateMargin) {
        this.rateMargin = rateMargin;
    }

    public Object getTotalExchangeRate() {
        return totalExchangeRate;
    }

    public void setTotalExchangeRate(Object totalExchangeRate) {
        this.totalExchangeRate = totalExchangeRate;
    }

    public Object getLogoFileName() {
        return logoFileName;
    }

    public void setLogoFileName(Object logoFileName) {
        this.logoFileName = logoFileName;
    }

    public Object getShortName() {
        return shortName;
    }

    public void setShortName(Object shortName) {
        this.shortName = shortName;
    }

    public Object getCountryIds() {
        return countryIds;
    }

    public void setCountryIds(Object countryIds) {
        this.countryIds = countryIds;
    }

   // feedback data
    @SerializedName("HeaderTypeText")
    @Expose
    private String headerTypeText;
    @SerializedName("FeedbackQuestion")
    @Expose
    private String feedbackQuestion;
    @SerializedName("FeedbackDescription")
    @Expose
    private Object feedbackDescription;
    @SerializedName("FeedbackType")
    @Expose
    private Integer feedbackType;
    @SerializedName("Options")
    @Expose
    private List<FeedbackAnswerList> options = null;
    @SerializedName("IsRequired")
    @Expose
    private Integer isRequired;
    @SerializedName("AnswerValue")
    @Expose
    private String answerValue;


    public String getHeaderTypeText() {
        return headerTypeText;
    }

    public void setHeaderTypeText(String headerTypeText) {
        this.headerTypeText = headerTypeText;
    }

    public String getFeedbackQuestion() {
        return feedbackQuestion;
    }

    public void setFeedbackQuestion(String feedbackQuestion) {
        this.feedbackQuestion = feedbackQuestion;
    }

    public Object getFeedbackDescription() {
        return feedbackDescription;
    }

    public void setFeedbackDescription(Object feedbackDescription) {
        this.feedbackDescription = feedbackDescription;
    }

    public Integer getFeedbackType() {
        return feedbackType;
    }

    public void setFeedbackType(Integer feedbackType) {
        this.feedbackType = feedbackType;
    }

    public List<FeedbackAnswerList> getOptions() {
        return options;
    }

    public void setOptions(List<FeedbackAnswerList> options) {
        this.options = options;
    }

    public Integer getIsRequired() {
        return isRequired;
    }

    public void setIsRequired(Integer isRequired) {
        this.isRequired = isRequired;
    }
    public String getAnswerValue() {
        return answerValue;
    }

    public void setAnswerValue(String answerValue) {
        this.answerValue = answerValue;
    }


//    quiz detail
@SerializedName("BAQuizId")
@Expose
private String bAQuizId;
    @SerializedName("QuizName")
    @Expose
    private String quizName;
    @SerializedName("QuizHeaderText")
    @Expose
    private String quizHeaderText;
    @SerializedName("QuizShortDescription")
    @Expose
    private String quizShortDescription;
    @SerializedName("QuizDescription")
    @Expose
    private String quizDescription;
    @SerializedName("QuizCategoryId")
    @Expose
    private Integer quizCategoryId;
    @SerializedName("QuizBannerImage")
    @Expose
    private String quizBannerImage;
    @SerializedName("QuizAvailableIn")
    @Expose
    private String quizAvailableIn;
    @SerializedName("DisplayResultType")
    @Expose
    private Integer displayResultType;
    @SerializedName("TimerType")
    @Expose
    private Integer timerType;
    @SerializedName("TimerValue")
    @Expose
    private Integer timerValue;
    @SerializedName("PublishDate")
    @Expose
    private Object publishDate;
    @SerializedName("PublishTimezoneId")
    @Expose
    private Integer publishTimezoneId;
    @SerializedName("QuizStatus")
    @Expose
    private Integer quizStatus;
    @SerializedName("PageTitle")
    @Expose
    private String pageTitle;
    @SerializedName("SEO_Metadescription")
    @Expose
    private String sEOMetadescription;
    @SerializedName("DateAdded")
    @Expose
    private String dateAdded;
    @SerializedName("QuizUrl")
    @Expose
    private String quizUrl;
    @SerializedName("AddedBy")
    @Expose
    private Integer addedBy;
    @SerializedName("QuestionDisplay")
    @Expose
    private Integer questionDisplay;
    @SerializedName("DisplayInListingBanner")
    @Expose
    private Integer displayInListingBanner;
    @SerializedName("DisplayHomePageBanner")
    @Expose
    private Integer displayHomePageBanner;
    @SerializedName("ThumbImage")
    @Expose
    private String thumbImage;
    @SerializedName("QuizMobileBanner")
    @Expose
    private String quizMobileBanner;
    @SerializedName("QuizBannerUrl")
    @Expose
    private String quizBannerUrl;
    @SerializedName("QuizMobileBannerUrl")
    @Expose
    private String quizMobileBannerUrl;
    @SerializedName("ThumbImageUrl")
    @Expose
    private String thumbImageUrl;
    @SerializedName("strQuizDate")
    @Expose
    private String strQuizDate;
    @SerializedName("QuizMobileBannerUrl_App")
    @Expose
    private String quizMobileBannerUrlApp;
    public String getBAQuizId() {
        return bAQuizId;
    }

    public void setBAQuizId(String bAQuizId) {
        this.bAQuizId = bAQuizId;
    }

    public String getQuizName() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public String getQuizHeaderText() {
        return quizHeaderText;
    }

    public void setQuizHeaderText(String quizHeaderText) {
        this.quizHeaderText = quizHeaderText;
    }

    public String getQuizShortDescription() {
        return quizShortDescription;
    }

    public void setQuizShortDescription(String quizShortDescription) {
        this.quizShortDescription = quizShortDescription;
    }

    public String getQuizDescription() {
        return quizDescription;
    }

    public void setQuizDescription(String quizDescription) {
        this.quizDescription = quizDescription;
    }

    public Integer getQuizCategoryId() {
        return quizCategoryId;
    }

    public void setQuizCategoryId(Integer quizCategoryId) {
        this.quizCategoryId = quizCategoryId;
    }

    public String getQuizBannerImage() {
        return quizBannerImage;
    }

    public void setQuizBannerImage(String quizBannerImage) {
        this.quizBannerImage = quizBannerImage;
    }

    public String getQuizAvailableIn() {
        return quizAvailableIn;
    }

    public void setQuizAvailableIn(String quizAvailableIn) {
        this.quizAvailableIn = quizAvailableIn;
    }

    public Integer getDisplayResultType() {
        return displayResultType;
    }

    public void setDisplayResultType(Integer displayResultType) {
        this.displayResultType = displayResultType;
    }

    public Integer getTimerType() {
        return timerType;
    }

    public void setTimerType(Integer timerType) {
        this.timerType = timerType;
    }

    public Integer getTimerValue() {
        return timerValue;
    }

    public void setTimerValue(Integer timerValue) {
        this.timerValue = timerValue;
    }

    public Object getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Object publishDate) {
        this.publishDate = publishDate;
    }

    public Integer getPublishTimezoneId() {
        return publishTimezoneId;
    }

    public void setPublishTimezoneId(Integer publishTimezoneId) {
        this.publishTimezoneId = publishTimezoneId;
    }

    public Integer getQuizStatus() {
        return quizStatus;
    }

    public void setQuizStatus(Integer quizStatus) {
        this.quizStatus = quizStatus;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    public String getSEOMetadescription() {
        return sEOMetadescription;
    }

    public void setSEOMetadescription(String sEOMetadescription) {
        this.sEOMetadescription = sEOMetadescription;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getQuizUrl() {
        return quizUrl;
    }

    public void setQuizUrl(String quizUrl) {
        this.quizUrl = quizUrl;
    }

    public Integer getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(Integer addedBy) {
        this.addedBy = addedBy;
    }

    public Integer getQuestionDisplay() {
        return questionDisplay;
    }

    public void setQuestionDisplay(Integer questionDisplay) {
        this.questionDisplay = questionDisplay;
    }

    public Integer getDisplayInListingBanner() {
        return displayInListingBanner;
    }

    public void setDisplayInListingBanner(Integer displayInListingBanner) {
        this.displayInListingBanner = displayInListingBanner;
    }

    public Integer getDisplayHomePageBanner() {
        return displayHomePageBanner;
    }

    public void setDisplayHomePageBanner(Integer displayHomePageBanner) {
        this.displayHomePageBanner = displayHomePageBanner;
    }

    public String getThumbImage() {
        return thumbImage;
    }

    public void setThumbImage(String thumbImage) {
        this.thumbImage = thumbImage;
    }

    public String getQuizMobileBanner() {
        return quizMobileBanner;
    }

    public void setQuizMobileBanner(String quizMobileBanner) {
        this.quizMobileBanner = quizMobileBanner;
    }

    public String getQuizBannerUrl() {
        return quizBannerUrl;
    }

    public void setQuizBannerUrl(String quizBannerUrl) {
        this.quizBannerUrl = quizBannerUrl;
    }

    public String getQuizMobileBannerUrl() {
        return quizMobileBannerUrl;
    }

    public void setQuizMobileBannerUrl(String quizMobileBannerUrl) {
        this.quizMobileBannerUrl = quizMobileBannerUrl;
    }

    public String getThumbImageUrl() {
        return thumbImageUrl;
    }

    public void setThumbImageUrl(String thumbImageUrl) {
        this.thumbImageUrl = thumbImageUrl;
    }
    public String getStrQuizDate() {
        return strQuizDate;
    }

    public void setStrQuizDate(String strQuizDate) {
        this.strQuizDate = strQuizDate;
    }

    public String getQuizMobileBannerUrlApp() {
        return quizMobileBannerUrlApp;
    }

    public void setQuizMobileBannerUrlApp(String quizMobileBannerUrlApp) {
        this.quizMobileBannerUrlApp = quizMobileBannerUrlApp;
    }
}
