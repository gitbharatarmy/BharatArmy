package com.bharatarmy.Utility;

import com.bharatarmy.Models.DashboardModel;
import com.bharatarmy.Models.GetWalkthroughModel;
import com.bharatarmy.Models.LogginModel;

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
    @POST("/GetUserDetails")
    void getUserDetails(@FieldMap Map<String, String> map, Callback<LogginModel> callback);

    @FormUrlEncoded
    @POST("/SendVerificationOTP")
    void getSendVerificationOTP(@FieldMap Map<String, String> map, Callback<LogginModel> callback);

    @Multipart
    @retrofit2.http.POST("/upload.php")
//    Call<ResponseBody> uploadImage(@Part MultipartBody.Part file/*,@Part("File") RequestBody name, @PartMap Map<String, RequestBody> partMap*/);
    Call<ResponseBody> uploadImage(@Part ("uploaded_file") RequestBody file);
}
