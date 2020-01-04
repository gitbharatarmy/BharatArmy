package com.bharatarmy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bharatarmy.Adapter.TravelMatchHotelAdapter;
import com.bharatarmy.Adapter.TravelMatchTicketMAinAdapter;
import com.bharatarmy.Fragment.MatchHotelFilterFragment;
import com.bharatarmy.Fragment.MatchTicketFilterFragment;
import com.bharatarmy.Interfaces.MorestoryClick;
import com.bharatarmy.Models.HomeTemplateDetailModel;
import com.bharatarmy.Models.HomeTemplateModel;
import com.bharatarmy.Models.MyScreenChnagesModel;
import com.bharatarmy.Models.RegisterIntrestFilterDataModel;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.ApiHandler;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityTravelMatchHotelBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class TravelMatchHotelActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityTravelMatchHotelBinding activityTravelMatchHotelBinding;
    Context mContext;

    /*Hotel List*/
//    ArrayList<HomeTemplateDetailModel> tournamnethotellist;
    RegisterIntrestFilterDataModel tournamentotherDataModel;
    ArrayList<TravelModel> tournamnethotellist;
    ArrayList<TravelModel> tournamnetcitylist;
    ArrayList<TravelModel> tournamnetbyteamlist;
    ArrayList<TravelModel> tournamnetratinglist;

    /*Adapter List*/
    TravelMatchHotelAdapter travelMatchHotelAdapter;
    LinearLayoutManager hotelmLayoutManager;

    /*Advance Filter   rating, by team, city*/
    BottomSheetDialogFragment bottomSheetDialogFragment;
//    ArrayList<TravelModel> tournamentfilterRatingModelList;
//    ArrayList<TravelModel> tournamentfilterTeamModelList;
//    ArrayList<TravelModel> tournamentfilterCityModelList;
//    ArrayList<TravelModel> finalFilterModelList;


//    ArrayList<TravelModel> tournamentadvancefilterteamDetailModel;
//    ArrayList<TravelModel> tournamentfiltercitywithteamDetailModel;
//    ArrayList<TravelModel> tournamentfiltercitywithteamratingDetailModel;

    ArrayList<TravelModel> tournamentfilterRatingModelList;
    ArrayList<TravelModel> tournamentfilterRatingwithTeamModelList;
    ArrayList<TravelModel> tournamentfilterRatingwithCityModelList;


    String selectedroomImageStr = "", selectedroomNameStr = "", selectedposition = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTravelMatchHotelBinding = DataBindingUtil.setContentView(this, R.layout.activity_travel_match_hotel);
        mContext = TravelMatchHotelActivity.this;
        EventBus.getDefault().register(this);


        init();
        setListiner();
    }

    public void init() {
        if (getIntent().getStringExtra("tourtitle") != null) {
            activityTravelMatchHotelBinding.toolbarTitleTxt.setText(getIntent().getStringExtra("tourtitle"));
        }

        activityTravelMatchHotelBinding.shimmerViewContainerHotel.startShimmerAnimation();

        tournamnethotellist = new ArrayList<>();
        tournamnethotellist.add(new TravelModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSCtpFvQulc666pbmJhIdodJxCSFhPlACieZOemcK3qb5w95acjRQ&s",
                "PAN PACIFIC PERTH", "Bharat Army Experience Package with Accommodation Stay.",
                4, "₹ 10000", "Geelong", "Australia"));

        tournamnethotellist.add(new TravelModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQDXmTnFIhLvlcFmZzc4BXBQURhFFV5J8bKoCsIK3e6QM74BnEj&s",
                "FOUR POINTS BY SHERATON PERTH", "Bharat Army Experience Package with Accommodation Stay.",
                3, "₹ 9000", "Sydney", "India"));


        tournamnetcitylist = new ArrayList<>();
        tournamnetcitylist.add(new TravelModel("Geelong", "0"));
        tournamnetcitylist.add(new TravelModel("Hobart", "0"));
        tournamnetcitylist.add(new TravelModel("Sydney", "0"));
        tournamnetcitylist.add(new TravelModel("Perth", "0"));
        tournamnetcitylist.add(new TravelModel("Melbourne", "0"));
        tournamnetcitylist.add(new TravelModel("Adelaide", "0"));

        tournamnetratinglist = new ArrayList<>();
        tournamnetratinglist.add(new TravelModel(2, "0"));
        tournamnetratinglist.add(new TravelModel(3, "0"));
        tournamnetratinglist.add(new TravelModel(4, "0"));
        tournamnetratinglist.add(new TravelModel(5, "0"));

        tournamnetbyteamlist = new ArrayList<>();
        tournamnetbyteamlist.add(new TravelModel("https://www.bharatarmy.com/Content/images/flags-mini/small/in.png",
                /*"Geelong",*/"India", "0"));
        tournamnetbyteamlist.add(new TravelModel("https://www.bharatarmy.com/Content/images/flags-mini/small/egg.png",
                /*"Hobart",*/"Englang", "0"));
        tournamnetbyteamlist.add(new TravelModel("https://www.bharatarmy.com/Content/images/flags-mini/small/au.png",
                /*"Sydney",*/"Australia", "0"));
        tournamnetbyteamlist.add(new TravelModel("https://www.bharatarmy.com/Content/images/flags-mini/small/sou.png",
                /* "Perth",*/"South Africa", "0"));
        tournamnetbyteamlist.add(new TravelModel("https://www.bharatarmy.com/Content/images/flags-mini/small/nz.png",
                /* "Melbourne",*/"New Zealand", "0"));
        tournamnetbyteamlist.add(new TravelModel("https://www.bharatarmy.com/Content/images/flags-mini/small/pk.png",
                /*"Adelaide",*/ "Pakistan", "0"));
        tournamnetbyteamlist.add(new TravelModel("https://www.bharatarmy.com/Content/images/flags-mini/small/wl.png",
                /* "Geelong",*/"West Indies", "0"));


        activityTravelMatchHotelBinding.shimmerViewContainerHotel.stopShimmerAnimation();
        activityTravelMatchHotelBinding.shimmerViewContainerHotel.setVisibility(View.GONE);
        activityTravelMatchHotelBinding.travelMatchHotelRcv.setVisibility(View.VISIBLE);
        activityTravelMatchHotelBinding.fabLinear.setVisibility(View.VISIBLE);
        setDataInList();
    }

    public void setListiner() {
        activityTravelMatchHotelBinding.backImg.setOnClickListener(this);
        activityTravelMatchHotelBinding.fabLinear.setOnClickListener(this);
    }

    public void setDataInList() {
        /*fill match hotel list*/
        travelMatchHotelAdapter = new TravelMatchHotelAdapter(mContext, tournamnethotellist);
        hotelmLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        activityTravelMatchHotelBinding.travelMatchHotelRcv.setLayoutManager(hotelmLayoutManager);
        activityTravelMatchHotelBinding.travelMatchHotelRcv.setItemAnimator(new DefaultItemAnimator());
        activityTravelMatchHotelBinding.travelMatchHotelRcv.setAdapter(travelMatchHotelAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                finish();
                break;
            case R.id.fab_linear:
                Utils.handleClickEvent(mContext, activityTravelMatchHotelBinding.fabLinear);
                bottomSheetDialogFragment = new MatchHotelFilterFragment(tournamentotherDataModel, tournamnetcitylist, tournamnetratinglist, tournamnetbyteamlist, new MorestoryClick() {
                    @Override
                    public void getmorestoryClick() {
                        activityTravelMatchHotelBinding.noRecordrel.setVisibility(View.GONE);
                        activityTravelMatchHotelBinding.travelMatchHotelRcv.setVisibility(View.GONE);
                        activityTravelMatchHotelBinding.shimmerViewContainerHotel.setVisibility(View.VISIBLE);
                        activityTravelMatchHotelBinding.shimmerViewContainerHotel.startShimmerAnimation();
                        setFilter();

                    }
                });
                //show it
                bottomSheetDialogFragment.show(getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
                break;
        }
    }

    public void setFilter() {
//        fill the selectedRatingList
        ArrayList<String> selectedtournamentRatingname = new ArrayList<>();
        for (int i = 0; i < tournamnetratinglist.size(); i++) {
            if (tournamnetratinglist.get(i).getMatchteamVenues().equalsIgnoreCase("1")) {
                selectedtournamentRatingname.add(String.valueOf(tournamnetratinglist.get(i).getMatchteamFlag()));
                Log.d("selectedRatingName :", selectedtournamentRatingname.toString());
            }
        }
//       fill the selectedteamNameList
        ArrayList<String> selectedtournamentTeamname = new ArrayList<>();
        for (int i = 0; i < tournamnetbyteamlist.size(); i++) {
            if (tournamnetbyteamlist.get(i).getPopularcity_image_count().equalsIgnoreCase("1")) {
                selectedtournamentTeamname.add(String.valueOf(tournamnetbyteamlist.get(i).getPopularcity_name()));
                Log.d("selectedteamname :", selectedtournamentTeamname.toString());
            }
        }
//       fill the selectedcityNameList
        ArrayList<String> selectedtournamentCityname = new ArrayList<>();
        for (int i = 0; i < tournamnetcitylist.size(); i++) {
            if (tournamnetcitylist.get(i).getCityHotelAmenitiesName().equalsIgnoreCase("1")) {
                selectedtournamentCityname.add(tournamnetcitylist.get(i).getCityHotelAmenitiesImage());
                Log.d("selectedCityName :", selectedtournamentCityname.toString());
            }
        }
        tournamentfilterRatingModelList = new ArrayList<>();
        tournamentfilterRatingwithTeamModelList = new ArrayList<>();
        tournamentfilterRatingwithCityModelList = new ArrayList<>();

        if (selectedtournamentRatingname.size() != 0) {
            for (int i = 0; i < selectedtournamentRatingname.size(); i++) {
                for (int j = 0; j < tournamnethotellist.size(); j++) {
                    if (selectedtournamentRatingname.get(i).trim().equalsIgnoreCase(String.valueOf(tournamnethotellist.get(j).getCityHotelRatingStr()).trim())) {
                        tournamentfilterRatingModelList.add(tournamnethotellist.get(j));
                    }
                }
            }
        }

        if (selectedtournamentTeamname.size() != 0) {
            for (int i = 0; i < selectedtournamentTeamname.size(); i++) {
                if (tournamentfilterRatingModelList.size() != 0) {
                    for (int j = 0; j < tournamentfilterRatingModelList.size(); j++) {
                        if (selectedtournamentTeamname.get(i).trim().equalsIgnoreCase(String.valueOf(tournamentfilterRatingModelList.get(j).getCityHotelTeamStr()).trim())) {
                            tournamentfilterRatingwithTeamModelList.add(tournamentfilterRatingModelList.get(j));
                        }
                    }
                } else {
                    for (int j = 0; j < tournamnethotellist.size(); j++) {
                        if (selectedtournamentTeamname.get(i).trim().equalsIgnoreCase(String.valueOf(tournamnethotellist.get(j).getCityHotelTeamStr()).trim())) {
                            tournamentfilterRatingwithTeamModelList.add(tournamnethotellist.get(j));
                        }
                    }
                }
            }
        } else {
            if (tournamentfilterRatingModelList.size() != 0) {
                activityTravelMatchHotelBinding.shimmerViewContainerHotel.setVisibility(View.GONE);
                activityTravelMatchHotelBinding.travelMatchHotelRcv.setVisibility(View.VISIBLE);
                activityTravelMatchHotelBinding.noRecordrel.setVisibility(View.GONE);
                travelMatchHotelAdapter = new TravelMatchHotelAdapter(mContext, tournamentfilterRatingModelList);
                hotelmLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
                activityTravelMatchHotelBinding.travelMatchHotelRcv.setLayoutManager(hotelmLayoutManager);
                activityTravelMatchHotelBinding.travelMatchHotelRcv.setItemAnimator(new DefaultItemAnimator());
                activityTravelMatchHotelBinding.travelMatchHotelRcv.setAdapter(travelMatchHotelAdapter);
            }
        }

        if (selectedtournamentCityname.size() != 0) {
            for (int i = 0; i < selectedtournamentCityname.size(); i++) {
                if (tournamentfilterRatingwithTeamModelList.size() != 0) {
                    for (int j = 0; j < tournamentfilterRatingwithTeamModelList.size(); j++) {
                        if (selectedtournamentCityname.get(i).trim().equalsIgnoreCase(tournamentfilterRatingwithTeamModelList.get(j).getCityHotelCityStr().trim())) {
                            tournamentfilterRatingwithCityModelList.add(tournamentfilterRatingwithTeamModelList.get(j));
                        }
                    }
                } else if (tournamentfilterRatingModelList.size() != 0) {
                    for (int j = 0; j < tournamentfilterRatingModelList.size(); j++) {
                        if (selectedtournamentCityname.get(i).trim().equalsIgnoreCase(tournamentfilterRatingModelList.get(j).getCityHotelCityStr().trim())) {
                            tournamentfilterRatingwithCityModelList.add(tournamentfilterRatingModelList.get(j));
                        }
                    }
                } else {
                    for (int j = 0; j < tournamnethotellist.size(); j++) {
                        if (selectedtournamentCityname.get(i).trim().equalsIgnoreCase(String.valueOf(tournamnethotellist.get(j).getCityHotelCityStr()).trim())) {
                            tournamentfilterRatingwithCityModelList.add(tournamnethotellist.get(j));
                        }
                    }
                }
            }
        } else {
            if (tournamentfilterRatingwithTeamModelList.size() != 0) {
                if (selectedtournamentRatingname.size() == 0) {
                    if (tournamentfilterRatingModelList.size() == 0 && tournamentfilterRatingwithTeamModelList.size() != 0) {
                        activityTravelMatchHotelBinding.shimmerViewContainerHotel.setVisibility(View.GONE);
                        activityTravelMatchHotelBinding.travelMatchHotelRcv.setVisibility(View.VISIBLE);
                        activityTravelMatchHotelBinding.noRecordrel.setVisibility(View.GONE);
                        travelMatchHotelAdapter = new TravelMatchHotelAdapter(mContext, tournamentfilterRatingwithTeamModelList);
                        hotelmLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
                        activityTravelMatchHotelBinding.travelMatchHotelRcv.setLayoutManager(hotelmLayoutManager);
                        activityTravelMatchHotelBinding.travelMatchHotelRcv.setItemAnimator(new DefaultItemAnimator());
                        activityTravelMatchHotelBinding.travelMatchHotelRcv.setAdapter(travelMatchHotelAdapter);
                    } else {
                        activityTravelMatchHotelBinding.shimmerViewContainerHotel.setVisibility(View.GONE);
                        activityTravelMatchHotelBinding.travelMatchHotelRcv.setVisibility(View.VISIBLE);
                        activityTravelMatchHotelBinding.noRecordrel.setVisibility(View.GONE);
                        travelMatchHotelAdapter = new TravelMatchHotelAdapter(mContext, tournamentfilterRatingwithTeamModelList);
                        hotelmLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
                        activityTravelMatchHotelBinding.travelMatchHotelRcv.setLayoutManager(hotelmLayoutManager);
                        activityTravelMatchHotelBinding.travelMatchHotelRcv.setItemAnimator(new DefaultItemAnimator());
                        activityTravelMatchHotelBinding.travelMatchHotelRcv.setAdapter(travelMatchHotelAdapter);
                    }

                } else {
                    if (tournamentfilterRatingModelList.size() != 0 && tournamentfilterRatingwithTeamModelList.size() != 0) {
                        activityTravelMatchHotelBinding.shimmerViewContainerHotel.setVisibility(View.GONE);
                        activityTravelMatchHotelBinding.travelMatchHotelRcv.setVisibility(View.VISIBLE);
                        activityTravelMatchHotelBinding.noRecordrel.setVisibility(View.GONE);
                        travelMatchHotelAdapter = new TravelMatchHotelAdapter(mContext, tournamentfilterRatingwithTeamModelList);
                        hotelmLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
                        activityTravelMatchHotelBinding.travelMatchHotelRcv.setLayoutManager(hotelmLayoutManager);
                        activityTravelMatchHotelBinding.travelMatchHotelRcv.setItemAnimator(new DefaultItemAnimator());
                        activityTravelMatchHotelBinding.travelMatchHotelRcv.setAdapter(travelMatchHotelAdapter);
                    } else {
                        activityTravelMatchHotelBinding.noRecordrel.setVisibility(View.VISIBLE);
                        activityTravelMatchHotelBinding.shimmerViewContainerHotel.setVisibility(View.GONE);
                        activityTravelMatchHotelBinding.travelMatchHotelRcv.setVisibility(View.GONE);
                    }
                }

            } else {
                if (selectedtournamentTeamname.size() == 0) {
                    if (tournamentfilterRatingModelList.size() != 0) {
                        activityTravelMatchHotelBinding.shimmerViewContainerHotel.setVisibility(View.GONE);
                        activityTravelMatchHotelBinding.travelMatchHotelRcv.setVisibility(View.VISIBLE);
                        activityTravelMatchHotelBinding.noRecordrel.setVisibility(View.GONE);
                        travelMatchHotelAdapter = new TravelMatchHotelAdapter(mContext, tournamentfilterRatingModelList);
                        hotelmLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
                        activityTravelMatchHotelBinding.travelMatchHotelRcv.setLayoutManager(hotelmLayoutManager);
                        activityTravelMatchHotelBinding.travelMatchHotelRcv.setItemAnimator(new DefaultItemAnimator());
                        activityTravelMatchHotelBinding.travelMatchHotelRcv.setAdapter(travelMatchHotelAdapter);
                    }
                } else {
                    activityTravelMatchHotelBinding.noRecordrel.setVisibility(View.VISIBLE);
                    activityTravelMatchHotelBinding.shimmerViewContainerHotel.setVisibility(View.GONE);
                    activityTravelMatchHotelBinding.travelMatchHotelRcv.setVisibility(View.GONE);
                }

            }
        }

        Log.d("filterRatingData:", "" + tournamentfilterRatingModelList.size());
        Log.d("filterRatingTeamData:", "" + tournamentfilterRatingwithTeamModelList.size());
        Log.d("filterRatingCityData:", "" + tournamentfilterRatingwithCityModelList.size());

        if (selectedtournamentCityname.size() != 0) {
            if (tournamentfilterRatingwithCityModelList.size() != 0) {
                if (selectedtournamentRatingname.size() == 0 && selectedtournamentTeamname.size() == 0) {
                    activityTravelMatchHotelBinding.shimmerViewContainerHotel.setVisibility(View.GONE);
                    activityTravelMatchHotelBinding.travelMatchHotelRcv.setVisibility(View.VISIBLE);
                    activityTravelMatchHotelBinding.noRecordrel.setVisibility(View.GONE);
                    travelMatchHotelAdapter = new TravelMatchHotelAdapter(mContext, tournamentfilterRatingwithCityModelList);
                    hotelmLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
                    activityTravelMatchHotelBinding.travelMatchHotelRcv.setLayoutManager(hotelmLayoutManager);
                    activityTravelMatchHotelBinding.travelMatchHotelRcv.setItemAnimator(new DefaultItemAnimator());
                    activityTravelMatchHotelBinding.travelMatchHotelRcv.setAdapter(travelMatchHotelAdapter);
                } else {
                    if (tournamentfilterRatingwithTeamModelList.size() != 0 && tournamentfilterRatingModelList.size() != 0) {
                        activityTravelMatchHotelBinding.shimmerViewContainerHotel.setVisibility(View.GONE);
                        activityTravelMatchHotelBinding.travelMatchHotelRcv.setVisibility(View.VISIBLE);
                        activityTravelMatchHotelBinding.noRecordrel.setVisibility(View.GONE);
                        travelMatchHotelAdapter = new TravelMatchHotelAdapter(mContext, tournamentfilterRatingwithCityModelList);
                        hotelmLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
                        activityTravelMatchHotelBinding.travelMatchHotelRcv.setLayoutManager(hotelmLayoutManager);
                        activityTravelMatchHotelBinding.travelMatchHotelRcv.setItemAnimator(new DefaultItemAnimator());
                        activityTravelMatchHotelBinding.travelMatchHotelRcv.setAdapter(travelMatchHotelAdapter);
                    } else if (tournamentfilterRatingwithTeamModelList.size() != 0 && tournamentfilterRatingModelList.size() == 0) {
                        if (selectedtournamentRatingname.size()==0){
                            activityTravelMatchHotelBinding.shimmerViewContainerHotel.setVisibility(View.GONE);
                            activityTravelMatchHotelBinding.travelMatchHotelRcv.setVisibility(View.VISIBLE);
                            activityTravelMatchHotelBinding.noRecordrel.setVisibility(View.GONE);
                            travelMatchHotelAdapter = new TravelMatchHotelAdapter(mContext, tournamentfilterRatingwithCityModelList);
                            hotelmLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
                            activityTravelMatchHotelBinding.travelMatchHotelRcv.setLayoutManager(hotelmLayoutManager);
                            activityTravelMatchHotelBinding.travelMatchHotelRcv.setItemAnimator(new DefaultItemAnimator());
                            activityTravelMatchHotelBinding.travelMatchHotelRcv.setAdapter(travelMatchHotelAdapter);
                        }else{
                            activityTravelMatchHotelBinding.noRecordrel.setVisibility(View.VISIBLE);
                            activityTravelMatchHotelBinding.shimmerViewContainerHotel.setVisibility(View.GONE);
                            activityTravelMatchHotelBinding.travelMatchHotelRcv.setVisibility(View.GONE);
                        }
                    }else if (tournamentfilterRatingwithTeamModelList.size() == 0 && tournamentfilterRatingModelList.size() != 0) {
                        if (selectedtournamentTeamname.size()==0){
                            activityTravelMatchHotelBinding.shimmerViewContainerHotel.setVisibility(View.GONE);
                            activityTravelMatchHotelBinding.travelMatchHotelRcv.setVisibility(View.VISIBLE);
                            activityTravelMatchHotelBinding.noRecordrel.setVisibility(View.GONE);
                            travelMatchHotelAdapter = new TravelMatchHotelAdapter(mContext, tournamentfilterRatingwithCityModelList);
                            hotelmLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
                            activityTravelMatchHotelBinding.travelMatchHotelRcv.setLayoutManager(hotelmLayoutManager);
                            activityTravelMatchHotelBinding.travelMatchHotelRcv.setItemAnimator(new DefaultItemAnimator());
                            activityTravelMatchHotelBinding.travelMatchHotelRcv.setAdapter(travelMatchHotelAdapter);
                        }else{
                            activityTravelMatchHotelBinding.noRecordrel.setVisibility(View.VISIBLE);
                            activityTravelMatchHotelBinding.shimmerViewContainerHotel.setVisibility(View.GONE);
                            activityTravelMatchHotelBinding.travelMatchHotelRcv.setVisibility(View.GONE);
                        }
                    } else {
                        activityTravelMatchHotelBinding.noRecordrel.setVisibility(View.VISIBLE);
                        activityTravelMatchHotelBinding.shimmerViewContainerHotel.setVisibility(View.GONE);
                        activityTravelMatchHotelBinding.travelMatchHotelRcv.setVisibility(View.GONE);
                    }

                }
            } else {
                activityTravelMatchHotelBinding.noRecordrel.setVisibility(View.VISIBLE);
                activityTravelMatchHotelBinding.shimmerViewContainerHotel.setVisibility(View.GONE);
                activityTravelMatchHotelBinding.travelMatchHotelRcv.setVisibility(View.GONE);
            }
        }


        if (tournamentfilterRatingModelList.size() == 0 && tournamentfilterRatingwithTeamModelList.size() == 0 && tournamentfilterRatingwithCityModelList.size() == 0) {
            if (selectedtournamentRatingname.size() == 0 && selectedtournamentTeamname.size() == 0 && selectedtournamentCityname.size() == 0) {
                activityTravelMatchHotelBinding.shimmerViewContainerHotel.setVisibility(View.GONE);
                activityTravelMatchHotelBinding.travelMatchHotelRcv.setVisibility(View.VISIBLE);
                activityTravelMatchHotelBinding.noRecordrel.setVisibility(View.GONE);
                travelMatchHotelAdapter = new TravelMatchHotelAdapter(mContext, tournamnethotellist);
                hotelmLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
                activityTravelMatchHotelBinding.travelMatchHotelRcv.setLayoutManager(hotelmLayoutManager);
                activityTravelMatchHotelBinding.travelMatchHotelRcv.setItemAnimator(new DefaultItemAnimator());
                activityTravelMatchHotelBinding.travelMatchHotelRcv.setAdapter(travelMatchHotelAdapter);
            } else {
                activityTravelMatchHotelBinding.noRecordrel.setVisibility(View.VISIBLE);
                activityTravelMatchHotelBinding.shimmerViewContainerHotel.setVisibility(View.GONE);
                activityTravelMatchHotelBinding.travelMatchHotelRcv.setVisibility(View.GONE);
            }
        }
//       fill the tournamentTeamFilter
//        tournamentadvancefilterteamDetailModel = new ArrayList<>();
//        tournamentfiltercitywithteamDetailModel = new ArrayList<>();
//        tournamentfiltercitywithteamratingDetailModel = new ArrayList<>();
//
//        if (selectedtournamentRatingname.size() != 0) {
//            if (tournamentfiltercitywithteamDetailModel.size() != 0) {
//                for (int k = 0; k < selectedtournamentRatingname.size(); k++) {
//                    if (tournamentfiltercitywithteamDetailModel.size() != 0) {
//                        for (int i = 0; i < tournamentfiltercitywithteamDetailModel.size(); i++) {
//                            if (selectedtournamentRatingname.get(k).trim().equalsIgnoreCase(String.valueOf(tournamentfiltercitywithteamDetailModel.get(i).getCityHotelRatingStr()).trim())) {
//                                tournamentfiltercitywithteamratingDetailModel.add(tournamentfiltercitywithteamDetailModel.get(i));
//                            }
//                        }
//                    }
//                }
//            } else if (tournamentadvancefilterteamDetailModel.size() != 0) {
//                for (int k = 0; k < selectedtournamentRatingname.size(); k++) {
//                    for (int i = 0; i < tournamentadvancefilterteamDetailModel.size(); i++) {
//                        if (selectedtournamentRatingname.get(k).trim().equalsIgnoreCase(String.valueOf(tournamentadvancefilterteamDetailModel.get(i).getCityHotelRatingStr()).trim())) {
//                            tournamentfiltercitywithteamratingDetailModel.add(tournamentadvancefilterteamDetailModel.get(i));
//                        }
//                    }
//                }
//
//            } else {
//                for (int k = 0; k < selectedtournamentRatingname.size(); k++) {
//                    for (int i = 0; i < tournamnethotellist.size(); i++) {
//                        if (selectedtournamentRatingname.get(k).trim().equalsIgnoreCase(String.valueOf(tournamnethotellist.get(i).getCityHotelRatingStr()).trim())) {
//                            tournamentfiltercitywithteamratingDetailModel.add(tournamnethotellist.get(i));
//                        }
//                    }
//                }
//
//            }
//        } else {
//            if (tournamentfiltercitywithteamDetailModel.size() != 0) {
//                activityTravelMatchHotelBinding.noRecordrel.setVisibility(View.GONE);
//                activityTravelMatchHotelBinding.travelMatchHotelRcv.setVisibility(View.VISIBLE);
//                activityTravelMatchHotelBinding.shimmerViewContainerHotel.setVisibility(View.GONE);
//                travelMatchHotelAdapter = new TravelMatchHotelAdapter(mContext, tournamentfiltercitywithteamDetailModel);
//                hotelmLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
//                activityTravelMatchHotelBinding.travelMatchHotelRcv.setLayoutManager(hotelmLayoutManager);
//                activityTravelMatchHotelBinding.travelMatchHotelRcv.setItemAnimator(new DefaultItemAnimator());
//                activityTravelMatchHotelBinding.travelMatchHotelRcv.setAdapter(travelMatchHotelAdapter);
//            } else if (tournamentadvancefilterteamDetailModel.size() != 0) {
//                activityTravelMatchHotelBinding.noRecordrel.setVisibility(View.GONE);
//                activityTravelMatchHotelBinding.travelMatchHotelRcv.setVisibility(View.VISIBLE);
//                activityTravelMatchHotelBinding.shimmerViewContainerHotel.setVisibility(View.GONE);
//                travelMatchHotelAdapter = new TravelMatchHotelAdapter(mContext, tournamentadvancefilterteamDetailModel);
//                hotelmLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
//                activityTravelMatchHotelBinding.travelMatchHotelRcv.setLayoutManager(hotelmLayoutManager);
//                activityTravelMatchHotelBinding.travelMatchHotelRcv.setItemAnimator(new DefaultItemAnimator());
//                activityTravelMatchHotelBinding.travelMatchHotelRcv.setAdapter(travelMatchHotelAdapter);
//            } else {
//                activityTravelMatchHotelBinding.noRecordrel.setVisibility(View.VISIBLE);
//                activityTravelMatchHotelBinding.shimmerViewContainerHotel.setVisibility(View.GONE);
//                activityTravelMatchHotelBinding.travelMatchHotelRcv.setVisibility(View.GONE);
//            }
//
//        if (selectedtournamentTeamname.size() != 0) {
//            if (tournamentfiltercitywithteamratingDetailModel.size() != 0) {
//                for (int k = 0; k < selectedtournamentTeamname.size(); k++) {
//                    for (int j = 0; j < tournamentfiltercitywithteamratingDetailModel.size(); j++) {
//                        if (selectedtournamentTeamname.get(k).trim().equalsIgnoreCase(String.valueOf(tournamentfiltercitywithteamratingDetailModel.get(j).getCityHotelTeamStr()).trim())) {
//                            tournamentadvancefilterteamDetailModel.add(tournamentfiltercitywithteamratingDetailModel.get(j));
//                        }
//                    }
//                }
//            } else {
//                for (int k = 0; k < selectedtournamentTeamname.size(); k++) {
//                    for (int j = 0; j < tournamnethotellist.size(); j++) {
//                        if (selectedtournamentTeamname.get(k).trim().equalsIgnoreCase(String.valueOf(tournamnethotellist.get(j).getCityHotelTeamStr()).trim())) {
//                            tournamentadvancefilterteamDetailModel.add(tournamnethotellist.get(j));
//                        }
//                    }
//                }
//            }
//
//        }
//
//
//        if (selectedtournamentCityname.size() != 0) {
//            for (int k = 0; k < selectedtournamentCityname.size(); k++) {
//                if (tournamentadvancefilterteamDetailModel.size() != 0) {
//                    for (int i = 0; i < tournamentadvancefilterteamDetailModel.size(); i++) {
//                        if (selectedtournamentCityname.get(k).trim().equalsIgnoreCase(tournamentadvancefilterteamDetailModel.get(i).getCityHotelCityStr().trim())) {
//                            tournamentfiltercitywithteamDetailModel.add(tournamentadvancefilterteamDetailModel.get(i));
//                        }
//                    }
//                } else {
//                    for (int i = 0; i < tournamnethotellist.size(); i++) {
//                        if (selectedtournamentCityname.get(k).trim().equalsIgnoreCase(tournamnethotellist.get(i).getCityHotelCityStr().trim())) {
//                            tournamentfiltercitywithteamDetailModel.add(tournamnethotellist.get(i));
//                        }
//                    }
//                }
//            }
//        } else {
//            if (tournamentadvancefilterteamDetailModel.size() != 0) {
//                activityTravelMatchHotelBinding.noRecordrel.setVisibility(View.GONE);
//                activityTravelMatchHotelBinding.travelMatchHotelRcv.setVisibility(View.VISIBLE);
//                activityTravelMatchHotelBinding.shimmerViewContainerHotel.setVisibility(View.GONE);
//                travelMatchHotelAdapter = new TravelMatchHotelAdapter(mContext, tournamentadvancefilterteamDetailModel);
//                hotelmLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
//                activityTravelMatchHotelBinding.travelMatchHotelRcv.setLayoutManager(hotelmLayoutManager);
//                activityTravelMatchHotelBinding.travelMatchHotelRcv.setItemAnimator(new DefaultItemAnimator());
//                activityTravelMatchHotelBinding.travelMatchHotelRcv.setAdapter(travelMatchHotelAdapter);
//            }
//        }
//
//        tournamentfiltercitywithteamratingDetailModel = new ArrayList<>();
//
//            /*else if (tournamentadvancefilterteamDetailModel.size() != 0) {
//                activityTravelMatchHotelBinding.noRecordrel.setVisibility(View.GONE);
//                activityTravelMatchHotelBinding.travelMatchHotelRcv.setVisibility(View.VISIBLE);
//                activityTravelMatchHotelBinding.shimmerViewContainerHotel.setVisibility(View.GONE);
//                travelMatchHotelAdapter = new TravelMatchHotelAdapter(mContext, tournamentadvancefilterteamDetailModel);
//                hotelmLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
//                activityTravelMatchHotelBinding.travelMatchHotelRcv.setLayoutManager(hotelmLayoutManager);
//                activityTravelMatchHotelBinding.travelMatchHotelRcv.setItemAnimator(new DefaultItemAnimator());
//                activityTravelMatchHotelBinding.travelMatchHotelRcv.setAdapter(travelMatchHotelAdapter);
//            }*/
//        }
//
//        if (tournamentfiltercitywithteamratingDetailModel.size() != 0) {
//            activityTravelMatchHotelBinding.shimmerViewContainerHotel.setVisibility(View.GONE);
//            activityTravelMatchHotelBinding.travelMatchHotelRcv.setVisibility(View.VISIBLE);
//            activityTravelMatchHotelBinding.noRecordrel.setVisibility(View.GONE);
//            travelMatchHotelAdapter = new TravelMatchHotelAdapter(mContext, tournamentfiltercitywithteamratingDetailModel);
//            hotelmLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
//            activityTravelMatchHotelBinding.travelMatchHotelRcv.setLayoutManager(hotelmLayoutManager);
//            activityTravelMatchHotelBinding.travelMatchHotelRcv.setItemAnimator(new DefaultItemAnimator());
//            activityTravelMatchHotelBinding.travelMatchHotelRcv.setAdapter(travelMatchHotelAdapter);
//        }
//        Log.d("filterteamData :", "" + tournamentadvancefilterteamDetailModel.size());
//        Log.d("filterteamcityData:", "" + tournamentfiltercitywithteamDetailModel.size());
//        Log.d("filterteamcityratingData:", "" + tournamentfiltercitywithteamratingDetailModel.size());
//
//
//        if (tournamentadvancefilterteamDetailModel.size() == 0 && tournamentfiltercitywithteamDetailModel.size() == 0 && tournamentfiltercitywithteamratingDetailModel.size() == 0) {
//            if (selectedtournamentTeamname.size() == 0 && selectedtournamentCityname.size() == 0 && selectedtournamentRatingname.size() == 0) {
//                activityTravelMatchHotelBinding.shimmerViewContainerHotel.setVisibility(View.GONE);
//                activityTravelMatchHotelBinding.travelMatchHotelRcv.setVisibility(View.VISIBLE);
//                activityTravelMatchHotelBinding.noRecordrel.setVisibility(View.GONE);
//                travelMatchHotelAdapter = new TravelMatchHotelAdapter(mContext, tournamnethotellist);
//                hotelmLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
//                activityTravelMatchHotelBinding.travelMatchHotelRcv.setLayoutManager(hotelmLayoutManager);
//                activityTravelMatchHotelBinding.travelMatchHotelRcv.setItemAnimator(new DefaultItemAnimator());
//                activityTravelMatchHotelBinding.travelMatchHotelRcv.setAdapter(travelMatchHotelAdapter);
//            } else {
//                activityTravelMatchHotelBinding.noRecordrel.setVisibility(View.VISIBLE);
//                activityTravelMatchHotelBinding.shimmerViewContainerHotel.setVisibility(View.GONE);
//                activityTravelMatchHotelBinding.travelMatchHotelRcv.setVisibility(View.GONE);
//            }
//        }


    }


    @Subscribe
    public void customEventReceived(MyScreenChnagesModel event) {
        Log.d("event :", event.getRoomName());
        if (!event.getRoomName().equalsIgnoreCase("")) {
            selectedroomNameStr = event.getRoomName();
            selectedroomImageStr = event.getRoomImage();
            selectedposition = event.getPosition();

            if (travelMatchHotelAdapter != null) {
                travelMatchHotelAdapter.notifyItemChanged(Integer.parseInt(selectedposition), selectedposition + "|" + selectedroomNameStr + "|" + selectedroomImageStr);//
            }

        }

    }


}
