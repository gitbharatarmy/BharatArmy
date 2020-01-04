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
//                bottomSheetDialogFragment = new MatchStadiumFilterFragment(tournamentotherDataModel, new MorestoryClick() {
//                    @Override
//                    public void getmorestoryClick() {
//                        activityTravelMatchHospitalityBinding.noRecordrel.setVisibility(View.GONE);
//                        activityTravelMatchHospitalityBinding.travelMatchHospitalityRcv.setVisibility(View.GONE);
//                        activityTravelMatchHospitalityBinding.shimmerViewContainerHospitality.setVisibility(View.VISIBLE);
//                        activityTravelMatchHospitalityBinding.shimmerViewContainerHospitality.startShimmerAnimation();
////                        setAdvanceFilter();
//
//                        setFilter();
//
//                    }
//                });
//                //show it
//                bottomSheetDialogFragment.show(getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
//                break;
        }
    }

 /*   public void setFilter(){
        ArrayList<String> selectedtournamentteamname = new ArrayList<>();
        for (int i = 0; i < tournamentotherDataModel.getCountries().size(); i++) {
            if (tournamentotherDataModel.getCountries().get(i).getTeamSelected().equalsIgnoreCase("1")) {
                selectedtournamentteamname.add(String.valueOf(tournamentotherDataModel.getCountries().get(i).getCountryName()));
                Log.d("selectedteamId :", selectedtournamentteamname.toString());
            }
        }
        String tournamentnameStr;
        tournamentnameStr = "";
        if (selectedtournamentteamname.size() != 0) {
            for (String s : selectedtournamentteamname) {
                tournamentnameStr = tournamentnameStr + "," + s;
            }
            Log.d("tournamentnameStr", tournamentnameStr);
            tournamentnameStr = tournamentnameStr.substring(1, tournamentnameStr.length());
            Log.d("finalstatusStr", tournamentnameStr);
        }

        ArrayList<String> selectedtournamentVenuename = new ArrayList<>();
        for (int i = 0; i < tournamentotherDataModel.getStadiums().size(); i++) {
            if (tournamentotherDataModel.getStadiums().get(i).getVenueSelected().equalsIgnoreCase("1")) {
                selectedtournamentVenuename.add(tournamentotherDataModel.getStadiums().get(i).getLabel());
                Log.d("selectedVenueName :", selectedtournamentVenuename.toString());
            }
        }

        tournamentadvancefilterteamDetailModel = new ArrayList<>();
        if (selectedtournamentteamname.size() != 0) {
            for (int k = 0; k < selectedtournamentteamname.size(); k++) {
                for (int j = 0; j < tournamenthospitalitylist.size(); j++) {
                    if (selectedtournamentteamname.get(k).trim().equalsIgnoreCase(String.valueOf(tournamenthospitalitylist.get(j).getButton_name()).trim())) {
                        tournamentadvancefilterteamDetailModel.add(tournamenthospitalitylist.get(j));
                    }
                }
            }
        }
        tournamentfiltervenuewithteamDetailModel = new ArrayList<>();
        ArrayList<String> selectedvenuefinal = new ArrayList<>();
        if (selectedtournamentVenuename.size()!=0){
            for (int i=0;i<selectedtournamentVenuename.size();i++){
                selectedvenuefinal.add(selectedtournamentVenuename.get(i).substring(0,selectedtournamentVenuename.get(i).indexOf(",")));
            }

        }


        if (selectedvenuefinal.size() != 0) {
            for (int k = 0; k < selectedvenuefinal.size(); k++) {
                if (tournamentadvancefilterteamDetailModel.size() != 0) {
                    for (int i = 0; i < tournamentadvancefilterteamDetailModel.size(); i++) {
                        if (selectedvenuefinal.get(k).trim().equalsIgnoreCase(tournamentadvancefilterteamDetailModel.get(i).getMain_titleName().trim())) {
                            tournamentfiltervenuewithteamDetailModel.add(tournamentadvancefilterteamDetailModel.get(i));
                        }
                    }
                } else {
                    for (int i = 0; i < tournamenthospitalitylist.size(); i++) {
                        if (selectedvenuefinal.get(k).trim().contentEquals(tournamenthospitalitylist.get(i).getMain_titleName().trim())) {
                            tournamentfiltervenuewithteamDetailModel.add(tournamenthospitalitylist.get(i));
                        }
                    }
                }
            }
        } else {
            if (tournamentadvancefilterteamDetailModel.size() != 0) {
                activityTravelMatchHospitalityBinding.noRecordrel.setVisibility(View.GONE);
                activityTravelMatchHospitalityBinding.travelMatchHospitalityRcv.setVisibility(View.VISIBLE);
                activityTravelMatchHospitalityBinding.shimmerViewContainerHospitality.setVisibility(View.GONE);
                travelMatchStadiumAdapter = new TravelMatchStadiumAdapter(mContext, tournamentadvancefilterteamDetailModel);
                stadiumLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
                activityTravelMatchHospitalityBinding.travelMatchStadiumRcv.setLayoutManager(stadiumLayoutManager);
                activityTravelMatchHospitalityBinding.travelMatchStadiumRcv.setItemAnimator(new DefaultItemAnimator());
                activityTravelMatchHospitalityBinding.travelMatchStadiumRcv.setAdapter(travelMatchStadiumAdapter);
            }
        }
        Log.d("filterteamData :", "" + tournamentadvancefilterteamDetailModel.size());
        Log.d("filterteamvenueData:", "" + tournamentfiltervenuewithteamDetailModel.size());
        if (selectedtournamentVenuename.size() != 0) {
            if (tournamentfiltervenuewithteamDetailModel.size() != 0) {
                activityTravelMatchHospitalityBinding.noRecordrel.setVisibility(View.GONE);
                activityTravelMatchHospitalityBinding.travelMatchStadiumRcv.setVisibility(View.VISIBLE);
                activityTravelMatchHospitalityBinding.shimmerViewContainerStadium.setVisibility(View.GONE);
                travelMatchStadiumAdapter = new TravelMatchStadiumAdapter(mContext, tournamentfiltervenuewithteamDetailModel);
                stadiumLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
                activityTravelMatchHospitalityBinding.travelMatchStadiumRcv.setLayoutManager(stadiumLayoutManager);
                activityTravelMatchHospitalityBinding.travelMatchStadiumRcv.setItemAnimator(new DefaultItemAnimator());
                activityTravelMatchHospitalityBinding.travelMatchStadiumRcv.setAdapter(travelMatchStadiumAdapter);

            } else {
                activityTravelMatchHospitalityBinding.noRecordrel.setVisibility(View.VISIBLE);
                activityTravelMatchHospitalityBinding.shimmerViewContainerStadium.setVisibility(View.GONE);
                activityTravelMatchHospitalityBinding.travelMatchStadiumRcv.setVisibility(View.GONE);
            }
        }

        if (tournamentadvancefilterteamDetailModel.size() == 0 && tournamentfiltervenuewithteamDetailModel.size() == 0) {
            if (selectedtournamentteamname.size() == 0 && selectedtournamentVenuename.size() == 0) {
                activityTravelMatchHospitalityBinding.shimmerViewContainerStadium.setVisibility(View.GONE);
                activityTravelMatchHospitalityBinding.travelMatchStadiumRcv.setVisibility(View.VISIBLE);
                activityTravelMatchHospitalityBinding.noRecordrel.setVisibility(View.GONE);
                travelMatchStadiumAdapter = new TravelMatchStadiumAdapter(mContext, tournamnetStadiumlist);
                stadiumLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
                activityTravelMatchHospitalityBinding.travelMatchStadiumRcv.setLayoutManager(stadiumLayoutManager);
                activityTravelMatchHospitalityBinding.travelMatchStadiumRcv.setItemAnimator(new DefaultItemAnimator());
                activityTravelMatchHospitalityBinding.travelMatchStadiumRcv.setAdapter(travelMatchStadiumAdapter);
            } else {
                activityTravelMatchHospitalityBinding.noRecordrel.setVisibility(View.VISIBLE);
                activityTravelMatchHospitalityBinding.shimmerViewContainerStadium.setVisibility(View.GONE);
                activityTravelMatchHospitalityBinding.travelMatchStadiumRcv.setVisibility(View.GONE);
            }
        }
    }*/

   /* public void setAdvanceFilter() {
        ArrayList<String> selectedtournamentteamId = new ArrayList<>();
        for (int i = 0; i < tournamentotherDataModel.getCountries().size(); i++) {
            if (tournamentotherDataModel.getCountries().get(i).getTeamSelected().equalsIgnoreCase("1")) {
                selectedtournamentteamId.add(String.valueOf(tournamentotherDataModel.getCountries().get(i).getCountryId()));
                Log.d("selectedteamId :", selectedtournamentteamId.toString());
            }
        }
        String tournamentIdStr;
        tournamentIdStr = "";
        if (selectedtournamentteamId.size() != 0) {
            for (String s : selectedtournamentteamId) {
                tournamentIdStr = tournamentIdStr + "," + s;
            }
            Log.d("tournamentIdStr", tournamentIdStr);
            tournamentIdStr = tournamentIdStr.substring(1, tournamentIdStr.length());
            Log.d("finalstatusStr", tournamentIdStr);
        }

        ArrayList<String> selectedtournamentVenuename = new ArrayList<>();
        for (int i = 0; i < tournamentotherDataModel.getStadiums().size(); i++) {
            if (tournamentotherDataModel.getStadiums().get(i).getVenueSelected().equalsIgnoreCase("1")) {
                selectedtournamentVenuename.add(tournamentotherDataModel.getStadiums().get(i).getLabel());
                Log.d("selectedVenueName :", selectedtournamentVenuename.toString());
            }
        }

        tournamentadvancefilterteamDetailModel = new ArrayList<>();
        if (selectedtournamentteamId.size() != 0) {
            for (int k = 0; k < selectedtournamentteamId.size(); k++) {
                for (int j = 0; j < tournamentticketModel.size(); j++) {
                    if (selectedtournamentteamId.get(k).trim().equalsIgnoreCase(String.valueOf(tournamentticketModel.get(j).getFromCountryId()).trim())
                            || selectedtournamentteamId.get(k).trim().equalsIgnoreCase(String.valueOf(tournamentticketModel.get(j).getToCountryId()).trim())) {
                        tournamentadvancefilterteamDetailModel.add(tournamentticketModel.get(j));
                    }
                }
            }
        }
        tournamentfiltervenuewithteamDetailModel = new ArrayList<>();
        if (selectedtournamentVenuename.size() != 0) {
            for (int k = 0; k < selectedtournamentVenuename.size(); k++) {
                if (tournamentadvancefilterteamDetailModel.size() != 0) {
                    for (int i = 0; i < tournamentadvancefilterteamDetailModel.size(); i++) {
                        if (selectedtournamentVenuename.get(k).trim().equalsIgnoreCase(tournamentadvancefilterteamDetailModel.get(i).getStadiumName().trim())) {
                            tournamentfiltervenuewithteamDetailModel.add(tournamentadvancefilterteamDetailModel.get(i));
                        }
                    }
                } else {
                    for (int i = 0; i < tournamentticketModel.size(); i++) {
                        if (selectedtournamentVenuename.get(k).trim().equalsIgnoreCase(tournamentticketModel.get(i).getStadiumName().trim())) {
                            tournamentfiltervenuewithteamDetailModel.add(tournamentticketModel.get(i));
                        }
                    }
                }
            }
        } else {
            if (tournamentadvancefilterteamDetailModel.size() != 0) {
                activityTravelMatchHospitalityBinding.noRecordrel.setVisibility(View.GONE);
                activityTravelMatchHospitalityBinding.travelMatchStadiumRcv.setVisibility(View.VISIBLE);
                activityTravelMatchHospitalityBinding.shimmerViewContainerStadium.setVisibility(View.GONE);
                travelMatchStadiumAdapter = new travelMatchStadiumAdapter(mContext, tournamentadvancefilterteamDetailModel);
                stadiumLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
                activityTravelMatchHospitalityBinding.travelMatchStadiumRcv.setLayoutManager(stadiumLayoutManager);
                activityTravelMatchHospitalityBinding.travelMatchStadiumRcv.setItemAnimator(new DefaultItemAnimator());
                activityTravelMatchHospitalityBinding.travelMatchStadiumRcv.setAdapter(travelMatchStadiumAdapter);
            }
        }
        Log.d("filterteamData :", "" + tournamentadvancefilterteamDetailModel.size());
        Log.d("filterteamvenueData:", "" + tournamentfiltervenuewithteamDetailModel.size());
        if (selectedtournamentVenuename.size() != 0) {
            if (tournamentfiltervenuewithteamDetailModel.size() != 0) {
                activityTravelMatchHospitalityBinding.noRecordrel.setVisibility(View.GONE);
                activityTravelMatchHospitalityBinding.travelMatchStadiumRcv.setVisibility(View.VISIBLE);
                activityTravelMatchHospitalityBinding.shimmerViewContainerStadium.setVisibility(View.GONE);
                travelMatchStadiumAdapter = new travelMatchStadiumAdapter(mContext, tournamentfiltervenuewithteamDetailModel);
                stadiumLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
                activityTravelMatchHospitalityBinding.travelMatchStadiumRcv.setLayoutManager(stadiumLayoutManager);
                activityTravelMatchHospitalityBinding.travelMatchStadiumRcv.setItemAnimator(new DefaultItemAnimator());
                activityTravelMatchHospitalityBinding.travelMatchStadiumRcv.setAdapter(travelMatchStadiumAdapter);

            } else {
                activityTravelMatchHospitalityBinding.noRecordrel.setVisibility(View.VISIBLE);
                activityTravelMatchHospitalityBinding.shimmerViewContainerStadium.setVisibility(View.GONE);
                activityTravelMatchHospitalityBinding.travelMatchStadiumRcv.setVisibility(View.GONE);
            }
        }

        if (tournamentadvancefilterteamDetailModel.size() == 0 && tournamentfiltervenuewithteamDetailModel.size() == 0) {
            if (selectedtournamentteamId.size() == 0 && selectedtournamentVenuename.size() == 0) {
                activityTravelMatchHospitalityBinding.shimmerViewContainerStadium.setVisibility(View.GONE);
                activityTravelMatchHospitalityBinding.travelMatchStadiumRcv.setVisibility(View.VISIBLE);
                activityTravelMatchHospitalityBinding.noRecordrel.setVisibility(View.GONE);
                travelMatchStadiumAdapter = new travelMatchStadiumAdapter(mContext, tournamentticketModel);
                stadiumLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
                activityTravelMatchHospitalityBinding.travelMatchStadiumRcv.setLayoutManager(stadiumLayoutManager);
                activityTravelMatchHospitalityBinding.travelMatchStadiumRcv.setItemAnimator(new DefaultItemAnimator());
                activityTravelMatchHospitalityBinding.travelMatchStadiumRcv.setAdapter(travelMatchStadiumAdapter);
            } else {
                activityTravelMatchHospitalityBinding.noRecordrel.setVisibility(View.VISIBLE);
                activityTravelMatchHospitalityBinding.shimmerViewContainerStadium.setVisibility(View.GONE);
                activityTravelMatchHospitalityBinding.travelMatchStadiumRcv.setVisibility(View.GONE);
            }
        }
    }*/

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
