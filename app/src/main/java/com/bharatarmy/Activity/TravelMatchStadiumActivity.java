package com.bharatarmy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bharatarmy.Adapter.TravelMatchStadiumAdapter;
import com.bharatarmy.Fragment.MatchStadiumFilterFragment;
import com.bharatarmy.Interfaces.MorestoryClick;
import com.bharatarmy.Models.HomeTemplateModel;
import com.bharatarmy.Models.RegisterIntrestFilterDataModel;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.ApiHandler;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityTravelMatchStadiumBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class TravelMatchStadiumActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityTravelMatchStadiumBinding activityTravelMatchStadiumBinding;
    Context mContext;

    /*Stadium List*/
    RegisterIntrestFilterDataModel tournamentotherDataModel;
    ArrayList<TravelModel> tournamnetStadiumlist;


    /*Adapter List*/
   TravelMatchStadiumAdapter travelMatchStadiumAdapter;
   LinearLayoutManager stadiumLayoutManager;

    /*Advance Filter*/
    BottomSheetDialogFragment bottomSheetDialogFragment;
    ArrayList<TravelModel> tournamentadvancefilterteamDetailModel;
    ArrayList<TravelModel> tournamentfiltervenuewithteamDetailModel;
  
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTravelMatchStadiumBinding = DataBindingUtil.setContentView(this, R.layout.activity_travel_match_stadium);
        mContext = TravelMatchStadiumActivity.this;



        init();
        setListiner();
    }

    public void init() {
        if (getIntent().getStringExtra("tourtitle")!=null){
            activityTravelMatchStadiumBinding.toolbarTitleTxt.setText(getIntent().getStringExtra("tourtitle"));
        }

        activityTravelMatchStadiumBinding.shimmerViewContainerStadium.startShimmerAnimation();
        //News and updates List
        tournamnetStadiumlist = new ArrayList<TravelModel>();

        tournamnetStadiumlist.add(new TravelModel(AppConfiguration.IMAGE_URL+"Adelaide_Oval.jpg",
                "War Memorial Dr, North Adelaide SA 5006, Australia", "Adelaide Oval",
                "Adelaide Oval will be at the centre of Men’s action, hosting six Super 12 matches and a Semi-Final, with Australia, Pakistan, India, South Africa and England to all feature at the venue.",
                "Australia"));

        tournamnetStadiumlist.add(new TravelModel("https://cdn.shopify.com/s/files/1/0031/6656/8493/files/Venue-Bellerive-Banner.png?v=1559024489",
                "15 Derwent St, Bellerive TAS 7018, Australia", "Bellerive Oval",
                "Eight Men’s T20 World Cup matches will come to Hobart, including six First Round and two Super 12 fixtures. Bangladesh will be joined by three qualifiers for First Round matches, while New Zealand will feature at the Super 12 Stage.",
                "Australia"));

        tournamnetStadiumlist.add(new TravelModel("https://cdn.shopify.com/s/files/1/0031/6656/8493/files/Venue-Gabba-Banner.png?v=1559025264",
                "Vulture St, Woolloongabba QLD 4102, Australia", "The Gabba",
                "The Gabba will host four Men’s Super 12 matches, including a massive double header featuring hosts Australia, Pakistan and New Zealand. England and Afghanistan will also feature at The Gabba.",
                "Australia"));

        tournamnetStadiumlist.add(new TravelModel("https://cdn.shopify.com/s/files/1/0031/6656/8493/files/Venue-Kardinia-Banner-3.png?v=1563860608",
                "370 Moorabool St, South Geelong VIC 3220, Australia", "Kardinia Park",
                "The 2014 World T20 Champions Sri Lanka will open the Men’s Tournament at Kardinia Park Stadium and play three matches in Geelong as part of the First Round, including two contests under lights.",
                "Australia"));

        tournamnetStadiumlist.add(new TravelModel("https://cdn.shopify.com/s/files/1/0031/6656/8493/files/Manuka-Oval.png?v=1563860460",
                "Manuka Cir, Griffith ACT 2603, Australia", "Manuka Oval",
                "A three-day festival of World Cup action will be coming to the nation’s capital from February 26-28.",
                "Australia"));

        tournamnetStadiumlist.add(new TravelModel("https://cdn.shopify.com/s/files/1/0031/6656/8493/files/Venue-MCG-Banner-2_1.png?v=1563171979",
                "Brunton Ave, Richmond VIC 3002, Australia", "Melbourne Cricket Ground",
                "The hallowed turf of the Melbourne Cricket Ground will play host to the most hotly anticipated matches of the tournament when both the Men’s and Women’s T20 World Champions will be decided.",
                "Australia"));

        tournamnetStadiumlist.add(new TravelModel("https://cdn.shopify.com/s/files/1/0031/6656/8493/files/Venue-Perth-Banner.png?v=1559025136",
                "333 Victoria Park Dr, Burswood WA 6100, Australia", "Perth Stadium",
                "Perth Stadium will host opening night action of the Men’s Super 12 Stage, with India taking on South Africa, and then five Super 12 Stage matches over the following week, featuring Australia, England, Afghanistan and the West Indies.",
                "Australia"));

        tournamnetStadiumlist.add(new TravelModel("https://cdn.shopify.com/s/files/1/0031/6656/8493/files/Venue-SCG-Banner_1.png?v=1559201787",
                "Driver Ave, Moore Park NSW 2021, Australia", "Sydney Cricket Ground",
                "Blockbuster match-ups between Australia and Pakistan, England and South Africa, and then India and Afghanistan will be staged over the three weekends of the Men’s Super 12 Stage.",
                "Australia"));

        tournamnetStadiumlist.add(new TravelModel("https://cdn.shopify.com/s/files/1/0031/6656/8493/files/Venue-SydneyShowground-Banner.png?v=1559536001",
                "Grand Parade, Sydney Olympic Park NSW 2127, Australia", "Sydney Showground Stadium",
                "Sydney Showground Stadium will host a spectacular opening celebration and the first match of the ICC Women’s T20 World Cup, which will feature hosts Australia taking on India.",
                "Australia"));

        tournamnetStadiumlist.add(new TravelModel("https://cdn.shopify.com/s/files/1/0031/6656/8493/files/WACA-2.png?v=1563860704",
                "WACA Ground, Nelson Cres, East Perth WA 6004, Australia", "WACA Ground",
                "A total of nine out of the ten women’s teams will also feature at Perth’s traditional cricket home, the WACA Ground, during a three-day festival of action that includes the opening weekend of the tournament.",
                "India"));


       callTravelMatchStadiumDetailData();

    }

    public void setListiner() {
        activityTravelMatchStadiumBinding.backImg.setOnClickListener(this);
        activityTravelMatchStadiumBinding.fabLinear.setOnClickListener(this);
    }

  


    public void setDataInList() {
        /*fill match stadium list*/


        travelMatchStadiumAdapter = new TravelMatchStadiumAdapter(mContext, tournamnetStadiumlist);
        stadiumLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        activityTravelMatchStadiumBinding.travelMatchStadiumRcv.setLayoutManager(stadiumLayoutManager);
        activityTravelMatchStadiumBinding.travelMatchStadiumRcv.setItemAnimator(new DefaultItemAnimator());
        activityTravelMatchStadiumBinding.travelMatchStadiumRcv.setAdapter(travelMatchStadiumAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                finish();
                break;
            case R.id.fab_linear:
                Utils.handleClickEvent(mContext,activityTravelMatchStadiumBinding.fabLinear);
                bottomSheetDialogFragment = new MatchStadiumFilterFragment(tournamentotherDataModel, new MorestoryClick() {
                    @Override
                    public void getmorestoryClick() {
                        activityTravelMatchStadiumBinding.noRecordrel.setVisibility(View.GONE);
                        activityTravelMatchStadiumBinding.travelMatchStadiumRcv.setVisibility(View.GONE);
                        activityTravelMatchStadiumBinding.shimmerViewContainerStadium.setVisibility(View.VISIBLE);
                        activityTravelMatchStadiumBinding.shimmerViewContainerStadium.startShimmerAnimation();
//                        setAdvanceFilter();
                        
                        setFilter();

                    }
                });
                //show it
                bottomSheetDialogFragment.show(getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
                break;
        }
    }
    
    public void setFilter(){
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
                for (int j = 0; j < tournamnetStadiumlist.size(); j++) {
                    if (selectedtournamentteamname.get(k).trim().equalsIgnoreCase(String.valueOf(tournamnetStadiumlist.get(j).getButton_name()).trim())) {
                        tournamentadvancefilterteamDetailModel.add(tournamnetStadiumlist.get(j));
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
                    for (int i = 0; i < tournamnetStadiumlist.size(); i++) {
                        if (selectedvenuefinal.get(k).trim().contentEquals(tournamnetStadiumlist.get(i).getMain_titleName().trim())) {
                            tournamentfiltervenuewithteamDetailModel.add(tournamnetStadiumlist.get(i));
                        }
                    }
                }
            }
        } else {
            if (tournamentadvancefilterteamDetailModel.size() != 0) {
                activityTravelMatchStadiumBinding.noRecordrel.setVisibility(View.GONE);
                activityTravelMatchStadiumBinding.travelMatchStadiumRcv.setVisibility(View.VISIBLE);
                activityTravelMatchStadiumBinding.shimmerViewContainerStadium.setVisibility(View.GONE);
                travelMatchStadiumAdapter = new TravelMatchStadiumAdapter(mContext, tournamentadvancefilterteamDetailModel);
                stadiumLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
                activityTravelMatchStadiumBinding.travelMatchStadiumRcv.setLayoutManager(stadiumLayoutManager);
                activityTravelMatchStadiumBinding.travelMatchStadiumRcv.setItemAnimator(new DefaultItemAnimator());
                activityTravelMatchStadiumBinding.travelMatchStadiumRcv.setAdapter(travelMatchStadiumAdapter);
            }
        }
        Log.d("filterteamData :", "" + tournamentadvancefilterteamDetailModel.size());
        Log.d("filterteamvenueData:", "" + tournamentfiltervenuewithteamDetailModel.size());
        if (selectedtournamentVenuename.size() != 0) {
            if (tournamentfiltervenuewithteamDetailModel.size() != 0) {
                activityTravelMatchStadiumBinding.noRecordrel.setVisibility(View.GONE);
                activityTravelMatchStadiumBinding.travelMatchStadiumRcv.setVisibility(View.VISIBLE);
                activityTravelMatchStadiumBinding.shimmerViewContainerStadium.setVisibility(View.GONE);
                travelMatchStadiumAdapter = new TravelMatchStadiumAdapter(mContext, tournamentfiltervenuewithteamDetailModel);
                stadiumLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
                activityTravelMatchStadiumBinding.travelMatchStadiumRcv.setLayoutManager(stadiumLayoutManager);
                activityTravelMatchStadiumBinding.travelMatchStadiumRcv.setItemAnimator(new DefaultItemAnimator());
                activityTravelMatchStadiumBinding.travelMatchStadiumRcv.setAdapter(travelMatchStadiumAdapter);

            } else {
                activityTravelMatchStadiumBinding.noRecordrel.setVisibility(View.VISIBLE);
                activityTravelMatchStadiumBinding.shimmerViewContainerStadium.setVisibility(View.GONE);
                activityTravelMatchStadiumBinding.travelMatchStadiumRcv.setVisibility(View.GONE);
            }
        }

        if (tournamentadvancefilterteamDetailModel.size() == 0 && tournamentfiltervenuewithteamDetailModel.size() == 0) {
            if (selectedtournamentteamname.size() == 0 && selectedtournamentVenuename.size() == 0) {
                activityTravelMatchStadiumBinding.shimmerViewContainerStadium.setVisibility(View.GONE);
                activityTravelMatchStadiumBinding.travelMatchStadiumRcv.setVisibility(View.VISIBLE);
                activityTravelMatchStadiumBinding.noRecordrel.setVisibility(View.GONE);
                travelMatchStadiumAdapter = new TravelMatchStadiumAdapter(mContext, tournamnetStadiumlist);
                stadiumLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
                activityTravelMatchStadiumBinding.travelMatchStadiumRcv.setLayoutManager(stadiumLayoutManager);
                activityTravelMatchStadiumBinding.travelMatchStadiumRcv.setItemAnimator(new DefaultItemAnimator());
                activityTravelMatchStadiumBinding.travelMatchStadiumRcv.setAdapter(travelMatchStadiumAdapter);
            } else {
                activityTravelMatchStadiumBinding.noRecordrel.setVisibility(View.VISIBLE);
                activityTravelMatchStadiumBinding.shimmerViewContainerStadium.setVisibility(View.GONE);
                activityTravelMatchStadiumBinding.travelMatchStadiumRcv.setVisibility(View.GONE);
            }
        }
    }

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
                activityTravelMatchStadiumBinding.noRecordrel.setVisibility(View.GONE);
                activityTravelMatchStadiumBinding.travelMatchStadiumRcv.setVisibility(View.VISIBLE);
                activityTravelMatchStadiumBinding.shimmerViewContainerStadium.setVisibility(View.GONE);
                travelMatchStadiumAdapter = new travelMatchStadiumAdapter(mContext, tournamentadvancefilterteamDetailModel);
                stadiumLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
                activityTravelMatchStadiumBinding.travelMatchStadiumRcv.setLayoutManager(stadiumLayoutManager);
                activityTravelMatchStadiumBinding.travelMatchStadiumRcv.setItemAnimator(new DefaultItemAnimator());
                activityTravelMatchStadiumBinding.travelMatchStadiumRcv.setAdapter(travelMatchStadiumAdapter);
            }
        }
        Log.d("filterteamData :", "" + tournamentadvancefilterteamDetailModel.size());
        Log.d("filterteamvenueData:", "" + tournamentfiltervenuewithteamDetailModel.size());
        if (selectedtournamentVenuename.size() != 0) {
            if (tournamentfiltervenuewithteamDetailModel.size() != 0) {
                activityTravelMatchStadiumBinding.noRecordrel.setVisibility(View.GONE);
                activityTravelMatchStadiumBinding.travelMatchStadiumRcv.setVisibility(View.VISIBLE);
                activityTravelMatchStadiumBinding.shimmerViewContainerStadium.setVisibility(View.GONE);
                travelMatchStadiumAdapter = new travelMatchStadiumAdapter(mContext, tournamentfiltervenuewithteamDetailModel);
                stadiumLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
                activityTravelMatchStadiumBinding.travelMatchStadiumRcv.setLayoutManager(stadiumLayoutManager);
                activityTravelMatchStadiumBinding.travelMatchStadiumRcv.setItemAnimator(new DefaultItemAnimator());
                activityTravelMatchStadiumBinding.travelMatchStadiumRcv.setAdapter(travelMatchStadiumAdapter);

            } else {
                activityTravelMatchStadiumBinding.noRecordrel.setVisibility(View.VISIBLE);
                activityTravelMatchStadiumBinding.shimmerViewContainerStadium.setVisibility(View.GONE);
                activityTravelMatchStadiumBinding.travelMatchStadiumRcv.setVisibility(View.GONE);
            }
        }

        if (tournamentadvancefilterteamDetailModel.size() == 0 && tournamentfiltervenuewithteamDetailModel.size() == 0) {
            if (selectedtournamentteamId.size() == 0 && selectedtournamentVenuename.size() == 0) {
                activityTravelMatchStadiumBinding.shimmerViewContainerStadium.setVisibility(View.GONE);
                activityTravelMatchStadiumBinding.travelMatchStadiumRcv.setVisibility(View.VISIBLE);
                activityTravelMatchStadiumBinding.noRecordrel.setVisibility(View.GONE);
                travelMatchStadiumAdapter = new travelMatchStadiumAdapter(mContext, tournamentticketModel);
                stadiumLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
                activityTravelMatchStadiumBinding.travelMatchStadiumRcv.setLayoutManager(stadiumLayoutManager);
                activityTravelMatchStadiumBinding.travelMatchStadiumRcv.setItemAnimator(new DefaultItemAnimator());
                activityTravelMatchStadiumBinding.travelMatchStadiumRcv.setAdapter(travelMatchStadiumAdapter);
            } else {
                activityTravelMatchStadiumBinding.noRecordrel.setVisibility(View.VISIBLE);
                activityTravelMatchStadiumBinding.shimmerViewContainerStadium.setVisibility(View.GONE);
                activityTravelMatchStadiumBinding.travelMatchStadiumRcv.setVisibility(View.GONE);
            }
        }
    }*/

    // Api calling GetTravelMatchScheduleDetailData
    public void callTravelMatchStadiumDetailData() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), TravelMatchStadiumActivity.this);
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
                        activityTravelMatchStadiumBinding.shimmerViewContainerStadium.stopShimmerAnimation();
                        activityTravelMatchStadiumBinding.shimmerViewContainerStadium.setVisibility(View.GONE);
                        activityTravelMatchStadiumBinding.travelMatchStadiumRcv.setVisibility(View.VISIBLE);
                        activityTravelMatchStadiumBinding.fabLinear.setVisibility(View.VISIBLE);

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
