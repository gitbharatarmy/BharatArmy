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
import com.bharatarmy.Adapter.TravelMatchStadiumAdapter;
import com.bharatarmy.Adapter.TravellMatchSightseenAdapter;
import com.bharatarmy.Fragment.MatchSightseenFilterFragment;
import com.bharatarmy.Fragment.MatchStadiumFilterFragment;
import com.bharatarmy.Interfaces.MorestoryClick;
import com.bharatarmy.Models.HomeTemplateModel;
import com.bharatarmy.Models.RegisterIntrestFilterDataModel;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.ApiHandler;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityTravelMatchSightseeingBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class TravelMatchSightseeingActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityTravelMatchSightseeingBinding activityTravelMatchSightseeingBinding;
    Context mContext;

    /*Sightseen List*/
    RegisterIntrestFilterDataModel tournamentotherDataModel;
    ArrayList<TravelModel> tournamnetSightseenlist;
    ArrayList<TravelModel> tournamentcitylist;

    /*Adapter List*/
    TravellMatchSightseenAdapter travellMatchSightseenAdapter;
    LinearLayoutManager sightseenLayoutManager;

    /*Advance Filter*/
    BottomSheetDialogFragment bottomSheetDialogFragment;
    ArrayList<TravelModel> tournamentfilterCityModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTravelMatchSightseeingBinding = DataBindingUtil.setContentView(this, R.layout.activity_travel_match_sightseeing);
        mContext = TravelMatchSightseeingActivity.this;


        init();
        setListiner();
    }

    public void init() {
        if (getIntent().getStringExtra("tourtitle") != null) {
            activityTravelMatchSightseeingBinding.toolbarTitleTxt.setText(getIntent().getStringExtra("tourtitle"));
        }

        activityTravelMatchSightseeingBinding.shimmerViewContainerSightseen.startShimmerAnimation();
        //News and updates List
        tournamnetSightseenlist = new ArrayList<TravelModel>();
        Log.d("placeimage :", /*AppConfiguration.IMAGE_URL + "Pinnacle Tour.jpg"*/"https://www.gpsadventuretourswa.com.au/media/1087/wildflowers-1.jpg?width=1000&upscale=false");
        tournamnetSightseenlist.add(new TravelModel(AppConfiguration.IMAGE_URL + "Pinnacle_Tour.jpg",
                "1 Barrack Street Jetty, Perth WA 6000, Australia", "Pinnacle Tour",
                "Discover the beauty of Western Australia with ADAMS Pinnacle Tours. We have over 35 years of experience in the industry.",
                "Perth"));

        tournamnetSightseenlist.add(new TravelModel(AppConfiguration.IMAGE_URL + "Kings_Park_and_Botanic_Garden_Tour.jpg",
                "Fraser Ave, Perth WA 6005, Australia", "Kings Park and Botanic Garden",
                "The park is a mixture of grassed parkland, botanical gardens and natural bushland on Mount Eliza with two-thirds of the grounds conserved as native bushland.",
                "Perth"));

        tournamnetSightseenlist.add(new TravelModel(AppConfiguration.IMAGE_URL + "Swan_River_Tour.jpg",
                "Western Australia, Australia", "Swan River",
                "The Swan River is a river in the south west of Western Australia. Its Aboriginal Noongar name is the Derbarl Yerrigan.",
                "Perth"));

        tournamnetSightseenlist.add(new TravelModel(AppConfiguration.IMAGE_URL + "Sydney_Opera_House.jpg",
                "Bennelong Point, Sydney NSW 2000, Australia", "Sydney Opera House",
                "The Sydney Opera House is a multi-venue performing arts centre at Sydney Harbour in Sydney, New South Wales, Australia.",
                "Sydney"));

        tournamnetSightseenlist.add(new TravelModel(AppConfiguration.IMAGE_URL + "Sydney_Harbour_Bridge.jpg",
                "Sydney Harbour Bridge, Sydney NSW, Australia", "Sydney Harbour Bridge",
                "The Sydney Harbour Bridge is a heritage-listed steel through arch bridge across Sydney Harbour that carries rail, vehicular, bicycle, and pedestrian traffic between the Sydney central business district and the North Shore.",
                "Sydney"));

        tournamnetSightseenlist.add(new TravelModel(AppConfiguration.IMAGE_URL + "The_Royal_Botanic_Garden_Sydney.jpg",
                "Mrs Macquaries Rd, Sydney NSW 2000, Australia", "The Royal Botanic Garden",
                "The Royal Botanic Garden, Sydney is a heritage-listed major 30-hectare botanical garden, event venue and public recreation area located at Farm.",
                "Sydney"));

        tournamnetSightseenlist.add(new TravelModel(AppConfiguration.IMAGE_URL + "The_Sydney_Tower_Eye.jpg",
                "100 Market St, Sydney NSW 2000, Australia", "The Sydney Tower Eye",
                "Landmark building, opened in 1981, with a 4-D cinema, observation deck and outdoor viewing platform.",
                "Australia"));


        tournamentcitylist = new ArrayList<>();
        tournamentcitylist.add(new TravelModel("Geelong", "0"));
        tournamentcitylist.add(new TravelModel("Hobart", "0"));
        tournamentcitylist.add(new TravelModel("Sydney", "0"));
        tournamentcitylist.add(new TravelModel("Perth", "0"));
        tournamentcitylist.add(new TravelModel("Melbourne", "0"));
        tournamentcitylist.add(new TravelModel("Adelaide", "0"));

        activityTravelMatchSightseeingBinding.shimmerViewContainerSightseen.stopShimmerAnimation();
        activityTravelMatchSightseeingBinding.shimmerViewContainerSightseen.setVisibility(View.GONE);
        activityTravelMatchSightseeingBinding.travelMatchSightseenRcv.setVisibility(View.VISIBLE);
        activityTravelMatchSightseeingBinding.fabLinear.setVisibility(View.VISIBLE);

        setDataInList();
//        callTravelMatchStadiumDetailData();

    }

    public void setListiner() {
        activityTravelMatchSightseeingBinding.backImg.setOnClickListener(this);
        activityTravelMatchSightseeingBinding.fabLinear.setOnClickListener(this);
    }


    public void setDataInList() {
        /*fill match stadium list*/


        travellMatchSightseenAdapter = new TravellMatchSightseenAdapter(mContext, tournamnetSightseenlist);
        sightseenLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        activityTravelMatchSightseeingBinding.travelMatchSightseenRcv.setLayoutManager(sightseenLayoutManager);
        activityTravelMatchSightseeingBinding.travelMatchSightseenRcv.setItemAnimator(new DefaultItemAnimator());
        activityTravelMatchSightseeingBinding.travelMatchSightseenRcv.setAdapter(travellMatchSightseenAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                finish();
                break;
            case R.id.fab_linear:
                Utils.handleClickEvent(mContext,activityTravelMatchSightseeingBinding.fabLinear);
                bottomSheetDialogFragment = new MatchSightseenFilterFragment(tournamentcitylist, new MorestoryClick() {
                    @Override
                    public void getmorestoryClick() {
                        activityTravelMatchSightseeingBinding.noRecordrel.setVisibility(View.GONE);
                        activityTravelMatchSightseeingBinding.travelMatchSightseenRcv.setVisibility(View.GONE);
                        activityTravelMatchSightseeingBinding.shimmerViewContainerSightseen.setVisibility(View.VISIBLE);
                        activityTravelMatchSightseeingBinding.shimmerViewContainerSightseen.startShimmerAnimation();
//                        setAdvanceFilter();

                        setFilter();

                    }
                });
                //show it
                bottomSheetDialogFragment.show(getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
                break;
        }
    }

    public void setFilter() {
        //       fill the selectedcityNameList
        ArrayList<String> selectedtournamentCityname = new ArrayList<>();
        for (int i = 0; i < tournamentcitylist.size(); i++) {
            if (tournamentcitylist.get(i).getCityHotelAmenitiesName().equalsIgnoreCase("1")) {
                selectedtournamentCityname.add(tournamentcitylist.get(i).getCityHotelAmenitiesImage());
                Log.d("selectedCityName :", selectedtournamentCityname.toString());
            }
        }
        tournamentfilterCityModelList = new ArrayList<>();
        if (selectedtournamentCityname.size() != 0) {
            for (int i = 0; i < selectedtournamentCityname.size(); i++) {
                for (int j = 0; j < tournamnetSightseenlist.size(); j++) {
                    if (selectedtournamentCityname.get(i).trim().equalsIgnoreCase(String.valueOf(tournamnetSightseenlist.get(j).getButton_name()).trim())) {
                        tournamentfilterCityModelList.add(tournamnetSightseenlist.get(j));
                    }
                }
            }
        }

        if (tournamentfilterCityModelList != null) {
            if (tournamentfilterCityModelList.size() != 0) {
                activityTravelMatchSightseeingBinding.shimmerViewContainerSightseen.setVisibility(View.GONE);
                activityTravelMatchSightseeingBinding.travelMatchSightseenRcv.setVisibility(View.VISIBLE);
                activityTravelMatchSightseeingBinding.noRecordrel.setVisibility(View.GONE);
                travellMatchSightseenAdapter = new TravellMatchSightseenAdapter(mContext, tournamentfilterCityModelList);
                sightseenLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
                activityTravelMatchSightseeingBinding.travelMatchSightseenRcv.setLayoutManager(sightseenLayoutManager);
                activityTravelMatchSightseeingBinding.travelMatchSightseenRcv.setItemAnimator(new DefaultItemAnimator());
                activityTravelMatchSightseeingBinding.travelMatchSightseenRcv.setAdapter(travellMatchSightseenAdapter);
            } else {
                if (selectedtournamentCityname.size() != 0) {
                    activityTravelMatchSightseeingBinding.noRecordrel.setVisibility(View.VISIBLE);
                    activityTravelMatchSightseeingBinding.shimmerViewContainerSightseen.setVisibility(View.GONE);
                    activityTravelMatchSightseeingBinding.travelMatchSightseenRcv.setVisibility(View.GONE);
                } else {
                    activityTravelMatchSightseeingBinding.shimmerViewContainerSightseen.setVisibility(View.GONE);
                    activityTravelMatchSightseeingBinding.travelMatchSightseenRcv.setVisibility(View.VISIBLE);
                    activityTravelMatchSightseeingBinding.noRecordrel.setVisibility(View.GONE);
                    travellMatchSightseenAdapter = new TravellMatchSightseenAdapter(mContext, tournamnetSightseenlist);
                    sightseenLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
                    activityTravelMatchSightseeingBinding.travelMatchSightseenRcv.setLayoutManager(sightseenLayoutManager);
                    activityTravelMatchSightseeingBinding.travelMatchSightseenRcv.setItemAnimator(new DefaultItemAnimator());
                    activityTravelMatchSightseeingBinding.travelMatchSightseenRcv.setAdapter(travellMatchSightseenAdapter);
                }
            }
        }
    }

}
