package com.bharatarmy.Utility;

import com.bharatarmy.Models.DashboardModel;
import com.bharatarmy.Models.GetSchoolNameModel;
import com.bharatarmy.Models.GetWalkthroughModel;
import com.bharatarmy.Models.HomeTemplateModel;
import com.bharatarmy.Models.ImageMainModel;
import com.bharatarmy.Models.LogginModel;
import com.bharatarmy.Models.LoginDataModel;
import com.bharatarmy.Models.MoreDataModel;
import com.bharatarmy.Models.OtpModel;
import com.bharatarmy.Models.TravelMainModel;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit.Callback;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;


public interface WebServices {
    @FormUrlEncoded
    @POST("/GetWalkthrough")
    void getWalkthrough(@FieldMap Map<String, String> map, Callback<GetWalkthroughModel> callback);

    @FormUrlEncoded
    @POST("/AppLogin")
    void getLogin(@FieldMap Map<String, String> map, Callback<LogginModel> callback);

    @FormUrlEncoded
    @POST("/LoginwithGoogle")
    void getLoginwithGoogle(@FieldMap Map<String, String> map, Callback<LogginModel> callback);

    @FormUrlEncoded
    @POST("/ForgotPassword")
    void getForgotPassword(@FieldMap Map<String, String> map, Callback<LogginModel> callback);

    @FormUrlEncoded
    @POST("/ChangePassword")
    void getChangePassword(@FieldMap Map<String, String> map, Callback<LogginModel> callback);

    @FormUrlEncoded
    @POST("/AppSignup")
    void getSignup(@FieldMap Map<String, String> map, Callback<LogginModel> callback);

    @FormUrlEncoded
    @POST("/ApplicationUsage")
    void getApplicationUsage(@FieldMap Map<String, String> map, Callback<LogginModel> callback);

    @FormUrlEncoded
    @POST("/GetUserDetails")
    void getUserDetails(@FieldMap Map<String, String> map, Callback<LogginModel> callback);

    @FormUrlEncoded
    @POST("/GetSportsList")
    void getSportsList(@FieldMap Map<String, String> map, Callback<ImageMainModel> callback);

    @FormUrlEncoded
    @POST("/GetStatesFromCountry")
    void getStatesFromCountry(@FieldMap Map<String, String> map, Callback<ImageMainModel> callback);

    @FormUrlEncoded
    @POST("/GetCitiesFromState")
    void getCitiesFromState(@FieldMap Map<String, String> map, Callback<ImageMainModel> callback);

    @FormUrlEncoded
    @POST("/SendVerificationOTP")
    void getSendVerificationOTP(@FieldMap Map<String, String> map, Callback<LogginModel> callback);

    @FormUrlEncoded
    @POST("/ValidatedBAMember")
    void getValidatedBAMember(@FieldMap Map<String, String> map, Callback<LogginModel> callback);

    @FormUrlEncoded
    @POST("/VerifiedPhoneNo")
    void getVerifiedPhoneNo(@FieldMap Map<String, String> map, Callback<OtpModel> callback);

    @FormUrlEncoded
    @POST("/GetDashboard")
    void getDashboard(@FieldMap Map<String, String> map, Callback<DashboardModel> callback);

    @FormUrlEncoded
    @POST("/GetHomeBanners")
    void getHomeBanners(@FieldMap Map<String, String> map, Callback<HomeTemplateModel> callback);

    @FormUrlEncoded
    @POST("/GetTournamentFixtures")
    void getTournamentFixtures(@FieldMap Map<String, String> map, Callback<HomeTemplateModel> callback);

    @FormUrlEncoded
    @POST("/SaveRegisterInterest")
    void getSaveRegisterInterest(@FieldMap Map<String, String> map, Callback<LogginModel> callback);

    /* profile image uploading with multiple parameter*/
    @Multipart
    @retrofit2.http.POST("/API/v1/UpdateProfile")
    Call<LogginModel> updateprofile(@Part("AppUserId") RequestBody userid, @Part("FirstName") RequestBody firstname,
                                    @Part("LastName") RequestBody lastname,@Part("EmailId") RequestBody email,
                                    @Part("CountryISOCode") RequestBody countryISOCode, @Part("CountryPhoneNo") RequestBody countycode,
                                    @Part("PhoneNo") RequestBody phoneno, @Part("Gender") RequestBody gender,
                                    @Part("OTPText") RequestBody otptext, @Part("SMSSentId") RequestBody smssentId,
                                    @Part("Addressline1") RequestBody addressline1, @Part("Addressline2") RequestBody addressline2,
                                    @Part("Area") RequestBody area, @Part("StateId") RequestBody stateid,
                                    @Part("strStateName") RequestBody strstatename,  @Part("CityId") RequestBody cityId,
                                    @Part("strCity") RequestBody strcity, @Part("Pincode") RequestBody pincode,
                                    @Part("FacebookProfile") RequestBody facebookprofile,
                                    @Part("TwitterProfile") RequestBody twitterprofile,
                                    @Part("LinkedinProfile") RequestBody linkedinprofile,
                                    @Part("InstagramProfile") RequestBody instagramprofile,
                                    @Part MultipartBody.Part file);

    @Multipart
    @retrofit2.http.POST("/API/v1/InsertData")
    @POST("/InsertData")
    Call<LogginModel> insertData(@Part("AppUserId") RequestBody userid, @Part("FullName") RequestBody fullname,
                                 @Part("CountryISOCode") RequestBody countryISOCode, @Part("CountryPhoneNo") RequestBody countycode,
                                 @Part("PhoneNo") RequestBody phoneno, @Part("Gender") RequestBody gender,
                                 @Part("Email") RequestBody email, @Part("InterestedSports") RequestBody interestedsports,
                                 @Part("SchoolName") RequestBody schoolname, @Part("UserType") RequestBody usertype,
                                 @Part("EntryType") RequestBody entrytype,@Part("AddedByEmailId") RequestBody addedbyemailid,
                                 @Part("AddedByName") RequestBody addedbyname,
                                 @Part MultipartBody.Part file);

    /* gallery image/video uploading with multiple parameter */
    @Multipart
    @retrofit2.http.POST("/API/v1/UploadFiles_New")
    Call<LogginModel> uploadfiles(@Part MultipartBody.Part file, @Part("FileTypeId") RequestBody filetype,
                                  @Part("MemberId") RequestBody userid, @Part("MemberFullName") RequestBody memberName,
                                  @Part("VideoLength") RequestBody videoLength,
                                  @Part("Title") RequestBody videoTitle,
                                  @Part("TitleDescription") RequestBody titleDescription); //@Part("FileTypeId") RequestBody userid,

    /* gallery image/video uploading with multiple parameter */
    @Multipart
    @retrofit2.http.POST("/API/v1/UploadFiles_New")
    Call<LogginModel> uploadvideo(@Part MultipartBody.Part[] file, @Part("FileTypeId") RequestBody filetype,
                                  @Part("MemberId") RequestBody userid, @Part("MemberFullName") RequestBody memberName,
                                  @Part("VideoLength") RequestBody videoLength,
                                  @Part("Title") RequestBody videoTitle,
                                  @Part("TitleDescription") RequestBody titleDescription,
                                  @Part("height") RequestBody height,
                                  @Part("width") RequestBody width); //@Part("FileTypeId") RequestBody userid,
    //if we pass array of imagethen use MultipartBody.Part[] file

    @FormUrlEncoded
    @POST("/InsertDataEntryUser")
    void getInsertDataEntryUser(@FieldMap Map<String, String> map, Callback<LogginModel> callback);

    @FormUrlEncoded
    @POST("/MyAddedUser")
    void getMyAddedUser(@FieldMap Map<String, String> map, Callback<ImageMainModel> callback);

    @FormUrlEncoded
    @POST("/GetDataEnter")
    void getDataEnter(@FieldMap Map<String, String> map, Callback<ImageMainModel> callback);

    @FormUrlEncoded
    @POST("/GetSchoolNameFromEntry")
    void getSchoolNameFromEntry(@FieldMap Map<String, String> map, Callback<GetSchoolNameModel> callback);

    @FormUrlEncoded
    @POST("/BAGallery")
    void getBAGallery(@FieldMap Map<String, String> map, Callback<ImageMainModel> callback);

    @FormUrlEncoded
    @POST("/BAVideoGallery")
    void getBAVideoGallery(@FieldMap Map<String, String> map, Callback<ImageMainModel> callback);

    @FormUrlEncoded
    @POST("/BARelatedVideos")
    void getBARelatedVideos(@FieldMap Map<String, String> map, Callback<ImageMainModel> callback);

    @FormUrlEncoded
    @POST("/BAAlbum")
    void getBAAlbum(@FieldMap Map<String, String> map, Callback<ImageMainModel> callback);

    @FormUrlEncoded
    @POST("/BAAlbumDetail")
    void getBAAlbumDetail(@FieldMap Map<String, String> map, Callback<ImageMainModel> callback);

    @FormUrlEncoded
    @POST("/InsertBAViews")
    void getInsertBAViews(@FieldMap Map<String, String> map, Callback<ImageMainModel> callback);

    @FormUrlEncoded
    @POST("/InsertBALike")
    void getInsertBALike(@FieldMap Map<String, String> map, Callback<ImageMainModel> callback);

    @FormUrlEncoded
    @POST("/InsertBAComments")
    void getInsertBAComments(@FieldMap Map<String, String> map, Callback<ImageMainModel> callback);

    @FormUrlEncoded
    @POST("/GetCommentDataByPost")
    void getCommentDataByPost(@FieldMap Map<String, String> map, Callback<ImageMainModel> callback);

    @FormUrlEncoded
    @POST("/BAPostStatistics")
    void getBAPostStatistics(@FieldMap Map<String, String> map, Callback<LogginModel> callback);

    @FormUrlEncoded
    @POST("/GetBAFTP")
    void getBAFTP(@FieldMap Map<String, String> map, Callback<ImageMainModel> callback);

    @FormUrlEncoded
    @POST("/GetFTPDetail")
    void getFTPDetail(@FieldMap Map<String, String> map, Callback<ImageMainModel> callback);

    @FormUrlEncoded
    @POST("/GetBAStories")
    void getBAStories(@FieldMap Map<String, String> map, Callback<ImageMainModel> callback);

    @FormUrlEncoded
    @POST("/GetStoryDetail")
    void getStoryDetail(@FieldMap Map<String, String> map, Callback<ImageMainModel> callback);

    @FormUrlEncoded
    @POST("/GetItineraryDetailByDayNo")
    void getItineraryDetailByDayNo(@FieldMap Map<String, String> map, Callback<TravelMainModel> callback);

    @FormUrlEncoded
    @POST("/GetTournamntInquiry")
    void getTournamntInquiry(@FieldMap Map<String, String> map, Callback<MoreDataModel> callback);

    @FormUrlEncoded
    @POST("/GetInquiryDetail")
    void getInquiryDetail(@FieldMap Map<String, String> map, Callback<MoreDataModel> callback);

    @FormUrlEncoded
    @POST("/GetInquiryAssignUser")
    void getInquiryAssignUser(@FieldMap Map<String, String> map, Callback<MoreDataModel> callback);

    @FormUrlEncoded
    @POST("/AssingUsertoInquiry")
    void getAssingUsertoInquiry(@FieldMap Map<String, String> map, Callback<MoreDataModel> callback);
}
