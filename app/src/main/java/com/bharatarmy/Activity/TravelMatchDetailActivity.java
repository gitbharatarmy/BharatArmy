package com.bharatarmy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bharatarmy.Adapter.TravelMatchDetailHotelRecyclerAdapter;
import com.bharatarmy.Adapter.TravelMatchDetailPackageRecyclerAdapter;
import com.bharatarmy.Adapter.TravelMatchDetailRecyclerAdapter;
import com.bharatarmy.Adapter.TravelMatchDetailSightseensRecyclerAdapter;
import com.bharatarmy.Adapter.TravelMatchDetailTicketsRecyclerAdapter;
import com.bharatarmy.Fragment.MatchFilterFragment;
import com.bharatarmy.Models.MyScreenChnagesModel;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityTravelMatchDetailNewBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;


// remove code 18/07/2019

public class TravelMatchDetailActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityTravelMatchDetailNewBinding travelMatchDetailBinding;
    Context mContext;
    String bgImageStr, tourMatchNameStr = "", titleNameStr,selectedroomNameStr="",selectedroomImageStr="",selectedposition="";
    BottomSheetDialogFragment bottomSheetDialogFragment;

    TravelMatchDetailRecyclerAdapter travelMatchDetailRecyclerAdapter;
    List<String> listDataHeader = new ArrayList<>();

    TravelMatchDetailTicketsRecyclerAdapter travelMatchDetailTicketsRecyclerAdapter;
    List<String> listtickets = new ArrayList<>();

    public static List<TravelModel> popularPackageList;

    TravelMatchDetailPackageRecyclerAdapter travelMatchDetailPackageRecyclerAdapter;

    ArrayList<TravelModel> matchHotelList;
    TravelMatchDetailHotelRecyclerAdapter travelMatchDetailHotelRecyclerAdapter;

    ArrayList<TravelModel> matchSightseenList;
    TravelMatchDetailSightseensRecyclerAdapter travelMatchDetailSightseensRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        travelMatchDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_travel_match_detail_new);
        EventBus.getDefault().register(this);
        mContext = TravelMatchDetailActivity.this;
        init();
        setListiner();
        setscheduleListValue();
    }

    public void init() {
        bgImageStr = getIntent().getStringExtra("bgImage");
        tourMatchNameStr = getIntent().getStringExtra("tourName");
        Utils.setImageInImageView("https://cdn.drivebird.com/user-content/140000000001/2017/09/627c6d094ccd59cdcf10035482d7497f.jpg",
                travelMatchDetailBinding.mainMatchBgImage, mContext);

        titleNameStr = "schedule";
    }

    public void setListiner() {
        travelMatchDetailBinding.scheduleMainLinear.performClick();
        travelMatchDetailBinding.scheduleMainLinear.setOnClickListener(this);
        travelMatchDetailBinding.ticketsMainLinear.setOnClickListener(this);
        travelMatchDetailBinding.packageMainLinear.setOnClickListener(this);
        travelMatchDetailBinding.hotelMainLinear.setOnClickListener(this);
        travelMatchDetailBinding.siteseenMainLinear.setOnClickListener(this);
        travelMatchDetailBinding.backImg.setOnClickListener(this);
        travelMatchDetailBinding.fabLinear.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.schedule_main_linear:
                titleNameStr = "schedule";
                travelMatchDetailBinding.fabLinear.setVisibility(View.VISIBLE);
                travelMatchDetailBinding.scheduleSelectedLinear.setBackground(getResources().getDrawable(R.drawable.bg_gradiant_line));
                travelMatchDetailBinding.ticktesSelectedLinear.setBackground(getResources().getDrawable(R.drawable.line_shape));
                travelMatchDetailBinding.packageSelectedLinear.setBackground(getResources().getDrawable(R.drawable.line_shape));
                travelMatchDetailBinding.hotelSelectedLinear.setBackground(getResources().getDrawable(R.drawable.line_shape));
                travelMatchDetailBinding.siteseenSelectedLinear.setBackground(getResources().getDrawable(R.drawable.line_shape));
                setscheduleListValue();
                break;
            case R.id.tickets_main_linear:
                titleNameStr = "tickets";
                travelMatchDetailBinding.fabLinear.setVisibility(View.VISIBLE);
                travelMatchDetailBinding.ticktesSelectedLinear.setBackground(getResources().getDrawable(R.drawable.bg_gradiant_line));
                travelMatchDetailBinding.scheduleSelectedLinear.setBackground(getResources().getDrawable(R.drawable.line_shape));
                travelMatchDetailBinding.packageSelectedLinear.setBackground(getResources().getDrawable(R.drawable.line_shape));
                travelMatchDetailBinding.hotelSelectedLinear.setBackground(getResources().getDrawable(R.drawable.line_shape));
                travelMatchDetailBinding.siteseenSelectedLinear.setBackground(getResources().getDrawable(R.drawable.line_shape));
                setticketsListValue();
                break;
            case R.id.package_main_linear:
                titleNameStr = "packages";
                travelMatchDetailBinding.fabLinear.setVisibility(View.GONE);
                travelMatchDetailBinding.packageSelectedLinear.setBackground(getResources().getDrawable(R.drawable.bg_gradiant_line));
                travelMatchDetailBinding.ticktesSelectedLinear.setBackground(getResources().getDrawable(R.drawable.line_shape));
                travelMatchDetailBinding.scheduleSelectedLinear.setBackground(getResources().getDrawable(R.drawable.line_shape));
                travelMatchDetailBinding.hotelSelectedLinear.setBackground(getResources().getDrawable(R.drawable.line_shape));
                travelMatchDetailBinding.siteseenSelectedLinear.setBackground(getResources().getDrawable(R.drawable.line_shape));
                setpacakgeListValue();
                break;
            case R.id.hotel_main_linear:
                titleNameStr = "hotels";
                travelMatchDetailBinding.fabLinear.setVisibility(View.GONE);
                travelMatchDetailBinding.hotelSelectedLinear.setBackground(getResources().getDrawable(R.drawable.bg_gradiant_line));
                travelMatchDetailBinding.scheduleSelectedLinear.setBackground(getResources().getDrawable(R.drawable.line_shape));
                travelMatchDetailBinding.ticktesSelectedLinear.setBackground(getResources().getDrawable(R.drawable.line_shape));
                travelMatchDetailBinding.packageSelectedLinear.setBackground(getResources().getDrawable(R.drawable.line_shape));
                travelMatchDetailBinding.siteseenSelectedLinear.setBackground(getResources().getDrawable(R.drawable.line_shape));
                sethotelListValue();
                break;
            case R.id.siteseen_main_linear:
                titleNameStr = "sightseens";
                travelMatchDetailBinding.fabLinear.setVisibility(View.GONE);
                travelMatchDetailBinding.siteseenSelectedLinear.setBackground(getResources().getDrawable(R.drawable.bg_gradiant_line));
                travelMatchDetailBinding.hotelSelectedLinear.setBackground(getResources().getDrawable(R.drawable.line_shape));
                travelMatchDetailBinding.scheduleSelectedLinear.setBackground(getResources().getDrawable(R.drawable.line_shape));
                travelMatchDetailBinding.ticktesSelectedLinear.setBackground(getResources().getDrawable(R.drawable.line_shape));
                travelMatchDetailBinding.packageSelectedLinear.setBackground(getResources().getDrawable(R.drawable.line_shape));
                setsightseensListValue();
                break;
            case R.id.back_img:
                TravelMatchDetailActivity.this.finish();
                break;
            case R.id.fab_linear:
//                bottomSheetDialogFragment = new MatchFilterFragment();
//                //show it
//                bottomSheetDialogFragment.show(getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
                break;

        }

    }

    @Subscribe
    public void customEventReceived(MyScreenChnagesModel event) {
        Log.d("event :",event.getPosition());
        if (!event.getRoomName().equalsIgnoreCase("")) {
            selectedroomNameStr=event.getRoomName();
            selectedroomImageStr=event.getRoomImage();
            selectedposition=event.getPosition();

            if (travelMatchDetailRecyclerAdapter!=null) {
                travelMatchDetailRecyclerAdapter.notifyItemChanged(Integer.parseInt(selectedposition), selectedposition+"|"+selectedroomNameStr+"|"+selectedroomImageStr);//
            }

            if (travelMatchDetailHotelRecyclerAdapter!=null){
                travelMatchDetailHotelRecyclerAdapter.notifyItemChanged(Integer.parseInt(selectedposition),selectedposition+"|"+selectedroomNameStr+"|"+selectedroomImageStr);
            }
        }

    }

    public void setscheduleListValue() {
        listDataHeader = new ArrayList<String>();

        for (int j = 0; j < 4; j++) {
            listDataHeader.add(String.valueOf(j));

        }
        travelMatchDetailRecyclerAdapter = new TravelMatchDetailRecyclerAdapter(mContext, listDataHeader, titleNameStr,
                selectedroomNameStr,selectedroomImageStr);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        travelMatchDetailBinding.matchRcv.setLayoutManager(mLayoutManager);
        travelMatchDetailBinding.matchRcv.setItemAnimator(new DefaultItemAnimator());
        travelMatchDetailBinding.matchRcv.setAdapter(travelMatchDetailRecyclerAdapter);
        travelMatchDetailRecyclerAdapter.notifyDataSetChanged();
    }

    public void setpacakgeListValue() {
        popularPackageList = new ArrayList<TravelModel>();

        popularPackageList.add(new TravelModel("xyz", "Australian Double Dhamaka: Honeymoon & adventure at one shot", AppConfiguration.IMAGE_URL + "aus1.jpg",
                "Jet Boat Ride from Main Beach.Bungy jumping from 165 ft distance at Cairns.Great Barrier Reef Experience",
                "1k", "900", "true"));

        popularPackageList.add(new TravelModel("xyz", "Explore the best of Australia with your soulmate", AppConfiguration.IMAGE_URL + "aus2.jpg",
                "Grand Barossa Valley Day Tour.Happy day out at the Kangaroo Island with a fun tour amidst natural highlights.Eureka Skydeck 88.Sydney Harbour Jet Boat Thrill Ride: 30 Minutes ",
                "2k", "500", "false"));

        popularPackageList.add(new TravelModel("xyz", "Celebrate love in the Australian lands", AppConfiguration.IMAGE_URL + "aus3.jpg",
                "Delicious dinner cruise during sunset at Sydney Harbour exposed to amazing vistas and views around the arena.Super Pass: Film World, Sea World & Wet'n'Wild Water World.Morning Whale Watching Cruise.Car hire for Great Ocean Road",
                "5k", "1000", "false"));
        travelMatchDetailPackageRecyclerAdapter = new TravelMatchDetailPackageRecyclerAdapter(mContext, titleNameStr, popularPackageList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        travelMatchDetailBinding.matchRcv.setLayoutManager(mLayoutManager);
        travelMatchDetailBinding.matchRcv.setItemAnimator(new DefaultItemAnimator());
        travelMatchDetailBinding.matchRcv.setAdapter(travelMatchDetailPackageRecyclerAdapter);
        travelMatchDetailRecyclerAdapter.notifyDataSetChanged();
    }

    public void setticketsListValue() {
        listtickets = new ArrayList<String>();

        for (int j = 0; j < 2; j++) {
            listtickets.add(String.valueOf(j));

        }
        travelMatchDetailTicketsRecyclerAdapter = new TravelMatchDetailTicketsRecyclerAdapter(mContext, listtickets, titleNameStr);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        travelMatchDetailBinding.matchRcv.setLayoutManager(mLayoutManager);
        travelMatchDetailBinding.matchRcv.setItemAnimator(new DefaultItemAnimator());
        travelMatchDetailBinding.matchRcv.setAdapter(travelMatchDetailTicketsRecyclerAdapter);
        travelMatchDetailRecyclerAdapter.notifyDataSetChanged();
    }

    public void sethotelListValue() {
        matchHotelList = new ArrayList<>();
        matchHotelList = new ArrayList<TravelModel>();

        matchHotelList.add(new TravelModel(AppConfiguration.IMAGE_URL + "mumbai.jpg", "Taj Mahal Hotel", "Colaba, Mumbai", 4, "6000"));

        matchHotelList.add(new TravelModel(AppConfiguration.IMAGE_URL + "hyatt.jpg", "Hyatt Regency Mumbai", "Andheri East, Mumbai", 3, "10000"));

        matchHotelList.add(new TravelModel(AppConfiguration.IMAGE_URL + "fourseason.jpg", "Four Seasons Hotel Mumbai", "Upper Worli, Mumbai", 5, "5000"));

        matchHotelList.add(new TravelModel(AppConfiguration.IMAGE_URL + "lela.jpg", "The Leela Mumbai", "Andheri East, Mumbai", 2, "7000"));

        matchHotelList.add(new TravelModel(AppConfiguration.IMAGE_URL + "trident.jpg", "Trident Bandra Kurla Mumbai", "Bandra Kurla Complex, Mumbai", 4, "6000"));
        matchHotelList.add(new TravelModel(AppConfiguration.IMAGE_URL + "jwmarriott.jpg", "JW Marriott Mumbai Juhu", "Juhu, Mumbai", 3, "9000"));


        travelMatchDetailHotelRecyclerAdapter = new TravelMatchDetailHotelRecyclerAdapter(mContext, matchHotelList, titleNameStr,selectedroomNameStr,selectedroomImageStr);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        travelMatchDetailBinding.matchRcv.setLayoutManager(mLayoutManager);
        travelMatchDetailBinding.matchRcv.setItemAnimator(new DefaultItemAnimator());
        travelMatchDetailBinding.matchRcv.setAdapter(travelMatchDetailHotelRecyclerAdapter);
        travelMatchDetailRecyclerAdapter.notifyDataSetChanged();
    }
    
    public void setsightseensListValue(){
        matchSightseenList = new ArrayList<>();
        matchSightseenList = new ArrayList<TravelModel>();

        matchSightseenList.add(new TravelModel(AppConfiguration.IMAGE_URL + "marinedrive.jpg", "Marine drive", "Chaupati, Mumbai", 4, "0"));

        matchSightseenList.add(new TravelModel(AppConfiguration.IMAGE_URL + "bandra_warli.jpg", "Bandra Worli Sea Link", "Mumbai", 3, "10"));

        matchSightseenList.add(new TravelModel(AppConfiguration.IMAGE_URL + "gate_way_of_india.jpg", "Gateway of India", "Colaba, Mumbai", 5, "10"));

        matchSightseenList.add(new TravelModel(AppConfiguration.IMAGE_URL + "global_vipassana_pagoda.jpg", "Global Vipassana Pagoda", "Gorai Village, Mumbai", 3, "10"));

        matchSightseenList.add(new TravelModel(AppConfiguration.IMAGE_URL + "water_kingdom.jpg", "Water Kingdom", "Gorai, Mumbai", 4, "10"));
        matchSightseenList.add(new TravelModel(AppConfiguration.IMAGE_URL + "elephanta.jpg", "Elephanta Caves Island Tour", "Mumbai", 3, "10"));


        travelMatchDetailSightseensRecyclerAdapter = new TravelMatchDetailSightseensRecyclerAdapter(mContext, matchSightseenList, titleNameStr);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        travelMatchDetailBinding.matchRcv.setLayoutManager(mLayoutManager);
        travelMatchDetailBinding.matchRcv.setItemAnimator(new DefaultItemAnimator());
        travelMatchDetailBinding.matchRcv.setAdapter(travelMatchDetailSightseensRecyclerAdapter);
        travelMatchDetailRecyclerAdapter.notifyDataSetChanged();
    }
}
