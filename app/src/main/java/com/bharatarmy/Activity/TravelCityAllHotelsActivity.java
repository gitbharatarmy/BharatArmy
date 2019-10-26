package com.bharatarmy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bharatarmy.Adapter.TravelCityAllHotelAdapter;
import com.bharatarmy.Adapter.TravelPopularCityDetailAdapter;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityTravelCityAllHotelsBinding;

import java.util.ArrayList;

public class TravelCityAllHotelsActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityTravelCityAllHotelsBinding activityTravelCityAllHotelsBinding;
    Context mContext;
    ArrayList<TravelModel> cityALlHotelList;
    TravelCityAllHotelAdapter travelCityAllHotelAdapter;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTravelCityAllHotelsBinding = DataBindingUtil.setContentView(this, R.layout.activity_travel_city_all_hotels);
        mContext = TravelCityAllHotelsActivity.this;

        init();
        setLisitner();
        setDataList();

    }

    public void init() {
    }

    public void setLisitner() {
        activityTravelCityAllHotelsBinding.filterviewImg.setOnClickListener(this);
        activityTravelCityAllHotelsBinding.backImg.setOnClickListener(this);
        activityTravelCityAllHotelsBinding.mapviewImg.setOnClickListener(this);

    }

    public void setDataList() {
        cityALlHotelList = new ArrayList<>();
        cityALlHotelList = new ArrayList<TravelModel>();

        cityALlHotelList.add(new TravelModel(AppConfiguration.IMAGE_URL + "mumbai.jpg", "Taj Mahal Hotel", "Colaba, Mumbai", 4, "6000"));

        cityALlHotelList.add(new TravelModel(AppConfiguration.IMAGE_URL + "hyatt.jpg", "Hyatt Regency Mumbai", "Andheri East, Mumbai", 3, "10000"));

        cityALlHotelList.add(new TravelModel(AppConfiguration.IMAGE_URL + "fourseason.jpg", "Four Seasons Hotel Mumbai", "Upper Worli, Mumbai", 5, "5000"));

        cityALlHotelList.add(new TravelModel(AppConfiguration.IMAGE_URL + "lela.jpg", "The Leela Mumbai", "Andheri East, Mumbai", 2, "7000"));

        cityALlHotelList.add(new TravelModel(AppConfiguration.IMAGE_URL + "trident.jpg", "Trident Bandra Kurla Mumbai", "Bandra Kurla Complex, Mumbai", 4, "6000"));
        cityALlHotelList.add(new TravelModel(AppConfiguration.IMAGE_URL + "jwmarriott.jpg", "JW Marriott Mumbai Juhu", "Juhu, Mumbai", 3, "9000"));


        travelCityAllHotelAdapter = new TravelCityAllHotelAdapter(mContext, cityALlHotelList);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, 1);
        activityTravelCityAllHotelsBinding.cityAllhotellistviewRcv.setLayoutManager(staggeredGridLayoutManager);
        activityTravelCityAllHotelsBinding.cityAllhotellistviewRcv.setAdapter(travelCityAllHotelAdapter);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                TravelCityAllHotelsActivity.this.finish();
                break;
            case R.id.filterview_img:
                if (activityTravelCityAllHotelsBinding.filterCard.isShown()) {
                    activityTravelCityAllHotelsBinding.filterCard.setVisibility(View.GONE);
                } else {
                    activityTravelCityAllHotelsBinding.filterCard.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.mapview_img:
                Utils.handleClickEvent(mContext,activityTravelCityAllHotelsBinding.mapviewImg);
                Intent mapIntent=new Intent(mContext,TravelpopularCityHotelMapViewActivity.class);
                startActivity(mapIntent);
                overridePendingTransition(R.anim.slide_up_dialog,R.anim.slide_up_dialog);
                break;
        }
    }
}
