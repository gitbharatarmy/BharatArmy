package com.bharatarmy.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DashboardDataModel {
    @SerializedName("CommonData")
    @Expose
    private CommonDashboardData commonData;
    @SerializedName("Upcomming")
    @Expose
    private List<UpcommingDashboardModel> upcomming = null;
    @SerializedName("Stories")
    @Expose
    private List<StoryDashboardData> stories = null;

    public CommonDashboardData getCommonData() {
        return commonData;
    }

    public void setCommonData(CommonDashboardData commonData) {
        this.commonData = commonData;
    }

    public List<UpcommingDashboardModel> getUpcomming() {
        return upcomming;
    }

    public void setUpcomming(List<UpcommingDashboardModel> upcomming) {
        this.upcomming = upcomming;
    }

    public List<StoryDashboardData> getStories() {
        return stories;
    }

    public void setStories(List<StoryDashboardData> stories) {
        this.stories = stories;
    }

}
