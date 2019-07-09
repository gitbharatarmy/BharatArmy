package com.bharatarmy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bharatarmy.Adapter.TravelCityAllSightseenAdapter;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.databinding.ActivityTravelCityAllSightseeingBinding;

import java.util.ArrayList;

public class TravelCityAllSightseeingActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityTravelCityAllSightseeingBinding activityTravelCityAllSightseeingBinding;
    Context mContext;
    ArrayList<TravelModel> cityALlSightseenList;
    TravelCityAllSightseenAdapter travelCityAllSightseenAdapter;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTravelCityAllSightseeingBinding = DataBindingUtil.setContentView(this, R.layout.activity_travel_city_all_sightseeing);
        mContext = TravelCityAllSightseeingActivity.this;

        init();
        setLisitner();
        setDataList();

    }

    public void init() {
    }

    public void setLisitner() {
        activityTravelCityAllSightseeingBinding.filterviewImg.setOnClickListener(this);
        activityTravelCityAllSightseeingBinding.backImg.setOnClickListener(this);
        activityTravelCityAllSightseeingBinding.mapviewImg.setOnClickListener(this);

    }

    public void setDataList() {
        cityALlSightseenList = new ArrayList<>();
        cityALlSightseenList = new ArrayList<TravelModel>();

        cityALlSightseenList.add(new TravelModel(AppConfiguration.IMAGE_URL + "marinedrive.jpg", "Marine drive", "Chaupati, Mumbai", 4, "0"));

        cityALlSightseenList.add(new TravelModel(AppConfiguration.IMAGE_URL + "bandra_warli.jpg", "Bandra Worli Sea Link", "Mumbai", 3, "10"));

        cityALlSightseenList.add(new TravelModel(AppConfiguration.IMAGE_URL + "gate_way_of_india.jpg", "Gateway of India", "Colaba, Mumbai", 5, "10"));

        cityALlSightseenList.add(new TravelModel(AppConfiguration.IMAGE_URL + "global_vipassana_pagoda.jpg", "Global Vipassana Pagoda", "Gorai Village, Mumbai", 3, "10"));

        cityALlSightseenList.add(new TravelModel(AppConfiguration.IMAGE_URL + "water_kingdom.jpg", "Water Kingdom", "Gorai, Mumbai", 4, "10"));
        cityALlSightseenList.add(new TravelModel(AppConfiguration.IMAGE_URL + "elephanta.jpg", "Elephanta Caves Island Tour", "Mumbai", 3, "10"));


        travelCityAllSightseenAdapter = new TravelCityAllSightseenAdapter(mContext, cityALlSightseenList);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, 1);
        activityTravelCityAllSightseeingBinding.cityAllsightseenlistviewRcv.setLayoutManager(staggeredGridLayoutManager);
        activityTravelCityAllSightseeingBinding.cityAllsightseenlistviewRcv.setAdapter(travelCityAllSightseenAdapter);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                TravelCityAllSightseeingActivity.this.finish();
                break;
            case R.id.filterview_img:
                if (activityTravelCityAllSightseeingBinding.filterCard.isShown()) {
                    activityTravelCityAllSightseeingBinding.filterCard.setVisibility(View.GONE);
                } else {
                    activityTravelCityAllSightseeingBinding.filterCard.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.mapview_img:
                Intent mapIntent=new Intent(mContext,TravelCityAllSightseenMapViewActivity.class);
                startActivity(mapIntent);
                overridePendingTransition(R.anim.slide_up_dialog,R.anim.slide_up_dialog);
                break;
        }
    }
}
