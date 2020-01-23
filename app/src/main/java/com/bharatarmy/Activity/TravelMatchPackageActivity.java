package com.bharatarmy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.bharatarmy.Adapter.TravelMatchPackageAdapter;
import com.bharatarmy.Adapter.TravelMatchTicketMAinAdapter;
import com.bharatarmy.Adapter.TravelPopularPackageAdapter;
import com.bharatarmy.Models.HomeTemplateDetailModel;
import com.bharatarmy.Models.RegisterIntrestFilterDataModel;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.databinding.ActivityTravelMatchPackageBinding;

import java.util.ArrayList;

public class TravelMatchPackageActivity extends AppCompatActivity implements View.OnClickListener {
    Context mContext;
    ActivityTravelMatchPackageBinding activityTravelMatchPackageBinding;

    /*Package List*/
    ArrayList<TravelModel> travelpackageList;

    /*Adapter List*/
    TravelMatchPackageAdapter travelMatchPackageAdapter;
    LinearLayoutManager pacakgemLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTravelMatchPackageBinding = DataBindingUtil.setContentView(this, R.layout.activity_travel_match_package);
        mContext = TravelMatchPackageActivity.this;

        init();
        setListiner();
        setDataInList();


    }

    public void init() {
        if (getIntent().getStringExtra("tourtitle") != null) {
            activityTravelMatchPackageBinding.toolbarTitleTxt.setText(getIntent().getStringExtra("tourtitle"));
        }

        activityTravelMatchPackageBinding.shimmerViewContainerPackage.startShimmerAnimation();
        activityTravelMatchPackageBinding.travelMatchPackageRcv.setVisibility(View.GONE);
        activityTravelMatchPackageBinding.noRecordrel.setVisibility(View.GONE);

        /*PopularPackage List*/
        travelpackageList = new ArrayList<TravelModel>();
        travelpackageList.add(new TravelModel("xyz", "Australian Double Dhamaka: Honeymoon & adventure at one shot", AppConfiguration.IMAGE_URL + "aus1.jpg",
                "Jet Boat Ride from Main Beach.Bungy jumping from 165 ft distance at Cairns.Great Barrier Reef Experience",
                "1k", "900", "true"));

        travelpackageList.add(new TravelModel("xyz", "Explore the best of Australia with your soulmate", AppConfiguration.IMAGE_URL + "aus2.jpg",
                "Grand Barossa Valley Day Tour.Happy day out at the Kangaroo Island with a fun tour amidst natural highlights.Eureka Skydeck 88.Sydney Harbour Jet Boat Thrill Ride: 30 Minutes ",
                "2k", "500", "false"));

        travelpackageList.add(new TravelModel("xyz", "Celebrate love in the Australian lands", AppConfiguration.IMAGE_URL + "aus3.jpg",
                "Delicious dinner cruise during sunset at Sydney Harbour exposed to amazing vistas and views around the arena.Super Pass: Film World, Sea World & Wet'n'Wild Water World.Morning Whale Watching Cruise.Car hire for Great Ocean Road",
                "5k", "1000", "false"));

        activityTravelMatchPackageBinding.shimmerViewContainerPackage.stopShimmerAnimation();
        activityTravelMatchPackageBinding.shimmerViewContainerPackage.setVisibility(View.GONE);
        activityTravelMatchPackageBinding.noRecordrel.setVisibility(View.GONE);
        activityTravelMatchPackageBinding.travelMatchPackageRcv.setVisibility(View.VISIBLE);

        setDataInList();
    }

    public void setListiner() {
        activityTravelMatchPackageBinding.backImg.setOnClickListener(this);
    }

    public void setDataInList() {
        travelMatchPackageAdapter = new TravelMatchPackageAdapter(mContext, travelpackageList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        activityTravelMatchPackageBinding.travelMatchPackageRcv.setLayoutManager(layoutManager);
        activityTravelMatchPackageBinding.travelMatchPackageRcv.setItemAnimator(new DefaultItemAnimator());
        activityTravelMatchPackageBinding.travelMatchPackageRcv.setAdapter(travelMatchPackageAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                finish();
                break;
        }
    }
}
