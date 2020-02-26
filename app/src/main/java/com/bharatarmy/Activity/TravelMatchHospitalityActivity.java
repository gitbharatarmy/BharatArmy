package com.bharatarmy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bharatarmy.Adapter.TravelMatchHospitalityAdapter;

import com.bharatarmy.Models.HomeTemplateModel;
import com.bharatarmy.Models.RegisterIntrestFilterDataModel;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.ApiHandler;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityTravelMatchHospitalityBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class TravelMatchHospitalityActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityTravelMatchHospitalityBinding activityTravelMatchHospitalityBinding;
    Context mContext;

    /*Stadium List*/
    RegisterIntrestFilterDataModel tournamentotherDataModel;
    ArrayList<TravelModel> tournamenthospitalitylist;


    /*Adapter List*/
    TravelMatchHospitalityAdapter travelMatchHospitalityAdapter;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    int selectedposition = -1;

    /*Advance Filter*/
    BottomSheetDialogFragment bottomSheetDialogFragment;
    ArrayList<TravelModel> tournamentadvancefilterteamDetailModel;
    ArrayList<TravelModel> tournamentfiltervenuewithteamDetailModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTravelMatchHospitalityBinding = DataBindingUtil.setContentView(this, R.layout.activity_travel_match_hospitality);
        mContext = TravelMatchHospitalityActivity.this;



        init();
        setListiner();
    }

    public void init() {
        if (getIntent().getStringExtra("tourtitle")!=null){
            activityTravelMatchHospitalityBinding.toolbarTitleTxt.setText(getIntent().getStringExtra("tourtitle"));
        }

        activityTravelMatchHospitalityBinding.shimmerViewContainerHospitality.startShimmerAnimation();



        //News and updates List
        tournamenthospitalitylist = new ArrayList<TravelModel>();

        tournamenthospitalitylist.add(new TravelModel("https://3.imimg.com/data3/VE/IW/MY-16198270/hotel-management-service-500x500.jpg", "Hospitality Category",
                "The Pavilion", "The Pavilion is the ultimate hospitality experience that will deliver a sophisticated, yet relaxed environment to be shared with family, friends or business associates.",
                "", "₹ 475", "3", "hospitality", "0"));

        tournamenthospitalitylist.add(new TravelModel("https://www.morganrichardson.co.uk/wp-content/uploads/2017/11/Hotel-Insurance.jpg", "",
                "Private Suites", "Private Suites provide the ultimate hospitality experience.",
                "Extra 20% off* with Hotel.", "₹ 600", "3", "hospitality", "0"));

        tournamenthospitalitylist.add(new TravelModel("https://www.morganrichardson.co.uk/wp-content/uploads/2017/11/Hotel-Insurance.jpg", "",
                "Open Air Boxes", "Open Air Boxes are a casual entertainment option providing you and your guests everything you need for an effortless day of cricket enjoyment.",
                "Extra 20% off* with Hotel.", "₹ 650", "1", "hospitality", "0"));

        tournamenthospitalitylist.add(new TravelModel("https://i0.wp.com/www.perrygroup.com/wp-content/uploads/2016/01/service-pic3-1.jpg", "",
                "Club 20/20", "Club 20/20 packages suit those seeking an informal entertainment experience that still provides hospitality with outstanding service.",
                "", "₹ 750", "2", "hospitality", "0"));


        callTravelMatchStadiumDetailData();

    }

    public void setListiner() {
        activityTravelMatchHospitalityBinding.backImg.setOnClickListener(this);
        activityTravelMatchHospitalityBinding.fabLinear.setOnClickListener(this);
    }




    public void setDataInList() {
        /*fill match stadium list*/
            travelMatchHospitalityAdapter = new TravelMatchHospitalityAdapter(mContext, tournamenthospitalitylist,selectedposition);
            staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, 1);
            activityTravelMatchHospitalityBinding.travelMatchHospitalityRcv.setLayoutManager(staggeredGridLayoutManager);
            activityTravelMatchHospitalityBinding.travelMatchHospitalityRcv.setAdapter(travelMatchHospitalityAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                finish();
                break;
            case R.id.fab_linear:
                break;
        }
    }

    // Api calling GetTravelMatchScheduleDetailData
    public void callTravelMatchStadiumDetailData() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), TravelMatchHospitalityActivity.this);
            return;
        }

//        Utils.showDialog(mContext);

        ApiHandler.getApiService().getTournamentFixtures(getTravelMatchStadiumDetailData(), new retrofit.Callback<HomeTemplateModel>() {
            @Override
            public void success(HomeTemplateModel tournamentmodel, Response response) {
                Utils.dismissDialog();
                if (tournamentmodel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (tournamentmodel.getIsValid() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (tournamentmodel.getIsValid() == 0) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    return;
                }
                if (tournamentmodel.getIsValid() == 1) {

                    if (tournamentmodel.getData() != null) {
                        activityTravelMatchHospitalityBinding.shimmerViewContainerHospitality.stopShimmerAnimation();
                        activityTravelMatchHospitalityBinding.shimmerViewContainerHospitality.setVisibility(View.GONE);
                        activityTravelMatchHospitalityBinding.travelMatchHospitalityRcv.setVisibility(View.VISIBLE);
                        activityTravelMatchHospitalityBinding.fabLinear.setVisibility(View.GONE);

                        if (tournamentmodel.getOtherData() != null) {
                            tournamentotherDataModel = tournamentmodel.getOtherData();
                        }
                        setDataInList();
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Utils.dismissDialog();
                error.printStackTrace();
                error.getMessage();
                Utils.ping(mContext, getString(R.string.something_wrong));
            }
        });


    }

    private Map<String, String> getTravelMatchStadiumDetailData() {
        Map<String, String> map = new HashMap<>();
        map.put("TournamentId", "11");
        map.put("MemberId", String.valueOf(Utils.getAppUserId(mContext)));
        return map;
    }
}
