package com.bharatarmy.Utility;

import com.bharatarmy.Models.DashboardModel;
import com.bharatarmy.Models.GetWalkthroughModel;
import com.bharatarmy.Models.LogginModel;

import java.util.Map;

import retrofit.Callback;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

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
}
