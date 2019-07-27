package com.bharatarmy.Utility;

import com.bharatarmy.Models.DashboardModel;
import com.bharatarmy.Models.GetWalkthroughModel;
import com.bharatarmy.Models.HomeTemplateModel;
import com.bharatarmy.Models.ImageMainModel;
import com.bharatarmy.Models.LogginModel;
import com.bharatarmy.Models.LoginDataModel;
import com.bharatarmy.Models.OtpModel;
import com.bharatarmy.Models.TravelMainModel;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit.Callback;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

import retrofit2.Call;
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
    @POST("/GetDashboard")
    void getDashboard(@FieldMap Map<String, String> map, Callback<DashboardModel> callback);

    @FormUrlEncoded
    @POST("/GetHomeBanners")
    void getHomeBanners(@FieldMap Map<String, String> map, Callback<HomeTemplateModel> callback);

    @FormUrlEncoded
    @POST("/GetUserDetails")
    void getUserDetails(@FieldMap Map<String, String> map, Callback<LogginModel> callback);

    @FormUrlEncoded
    @POST("/SendVerificationOTP")
    void getSendVerificationOTP(@FieldMap Map<String, String> map, Callback<LogginModel> callback);

    @FormUrlEncoded
    @POST("/ValidatedBAMember")
    void getValidatedBAMember(@FieldMap Map<String, String> map, Callback<LogginModel> callback);


    /* image uploading with multiple parameter*/
    @Multipart
    @retrofit2.http.POST("/API/v1/UpdateProfile")
    Call<LogginModel> updateprofile(@Part("AppUserId") RequestBody userid, @Part("FullName") RequestBody fullname,
                                       @Part("CountryISOCode") RequestBody countryISOCode, @Part("CountryPhoneNo") RequestBody countycode,
                                       @Part("PhoneNo") RequestBody phoneno,@Part("Gender") RequestBody gender,
                                       @Part("OTPText") RequestBody otptext,@Part("SMSSentId") RequestBody smssentId,
                                       @Part MultipartBody.Part file);


    @FormUrlEncoded
    @POST("/VerifiedPhoneNo")
    void getVerifiedPhoneNo(@FieldMap Map<String, String> map, Callback<OtpModel> callback);

    @FormUrlEncoded
    @POST("/AppSignup")
    void getSignup(@FieldMap Map<String, String> map, Callback<LogginModel> callback);

    @FormUrlEncoded
    @POST("/BAGallery")
    void getBAGallery(@FieldMap Map<String, String> map, Callback<ImageMainModel> callback);

    @FormUrlEncoded
    @POST("/BAVideoGallery")
    void getBAVideoGallery(@FieldMap Map<String, String> map, Callback<ImageMainModel> callback);


    @FormUrlEncoded
    @POST("/GetBAStories")
    void getBAStories(@FieldMap Map<String, String> map, Callback<ImageMainModel> callback);

    @FormUrlEncoded
    @POST("/GetBAFTP")
    void getBAFTP(@FieldMap Map<String, String> map, Callback<ImageMainModel> callback);

    @FormUrlEncoded
    @POST("/GetStoryDetail")
    void getStoryDetail(@FieldMap Map<String, String> map, Callback<ImageMainModel> callback);

    @FormUrlEncoded
    @POST("/GetFTPDetail")
    void getFTPDetail(@FieldMap Map<String, String> map, Callback<ImageMainModel> callback);

    @FormUrlEncoded
    @POST("/GetItineraryDetailByDayNo")
    void getItineraryDetailByDayNo(@FieldMap Map<String, String> map, Callback<TravelMainModel> callback);
}
