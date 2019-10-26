package com.bharatarmy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.bharatarmy.Adapter.TravelCityAllRestaurantAdapter;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityTravelCityAllRestaurantsBinding;

import java.util.ArrayList;

public class TravelCityAllRestaurantsActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityTravelCityAllRestaurantsBinding activityTravelCityAllRestaurantsBinding;
    Context mContext;
    ArrayList<TravelModel> cityALlRestaurantList;
    TravelCityAllRestaurantAdapter travelCityAllRestaurantAdapter;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTravelCityAllRestaurantsBinding = DataBindingUtil.setContentView(this, R.layout.activity_travel_city_all_restaurants);
        mContext = TravelCityAllRestaurantsActivity.this;

        init();
        setLisitner();
        setDataList();

    }

    public void init() {
    }

    public void setLisitner() {
        activityTravelCityAllRestaurantsBinding.filterviewImg.setOnClickListener(this);
        activityTravelCityAllRestaurantsBinding.backImg.setOnClickListener(this);
        activityTravelCityAllRestaurantsBinding.mapviewImg.setOnClickListener(this);

    }

    public void setDataList() {
        cityALlRestaurantList = new ArrayList<>();
        cityALlRestaurantList = new ArrayList<TravelModel>();

        cityALlRestaurantList.add(new TravelModel(AppConfiguration.IMAGE_URL + "seven_kitchen.jpg", "Seven Kitchens Mumbai", "Level 9M, Mumbai", 4, "10"));

        cityALlRestaurantList.add(new TravelModel(AppConfiguration.IMAGE_URL + "180_degree.jpg", "180 Degrees Mumbai", "Western Suburbs, Mumbai", 3, "0"));

        cityALlRestaurantList.add(new TravelModel(AppConfiguration.IMAGE_URL + "Hornby's_Pavilion.jpg", "Hornby's Pavilion Mumbai", "Parel, Mumbai", 5, "1"));

        cityALlRestaurantList.add(new TravelModel(AppConfiguration.IMAGE_URL + "lake_view.jpg", "Lake View Cafe Mumbai", "Eastern Suburbs, Mumbai", 3, "0"));

        cityALlRestaurantList.add(new TravelModel(AppConfiguration.IMAGE_URL + "peshwa.jpg", "The Peshwa Pavilion Mumbai", "Andheri East, Mumbai", 4, "0"));
        cityALlRestaurantList.add(new TravelModel(AppConfiguration.IMAGE_URL + "lotus.jpg", "Lotus Cafe Mumbai", "Juhu, Mumbai", 3, "10"));


        travelCityAllRestaurantAdapter = new TravelCityAllRestaurantAdapter(mContext, cityALlRestaurantList);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, 1);
        activityTravelCityAllRestaurantsBinding.cityAllrestaurantlistviewRcv.setLayoutManager(staggeredGridLayoutManager);
        activityTravelCityAllRestaurantsBinding.cityAllrestaurantlistviewRcv.setAdapter(travelCityAllRestaurantAdapter);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                TravelCityAllRestaurantsActivity.this.finish();
                break;
            case R.id.filterview_img:
                if (activityTravelCityAllRestaurantsBinding.filterCard.isShown()) {
                    activityTravelCityAllRestaurantsBinding.filterCard.setVisibility(View.GONE);
                } else {
                    activityTravelCityAllRestaurantsBinding.filterCard.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.mapview_img:
                Utils.handleClickEvent(mContext,activityTravelCityAllRestaurantsBinding.mapviewImg);
                Intent mapIntent=new Intent(mContext,TravelCityRestaurantMapViewActivity.class);
                startActivity(mapIntent);
                overridePendingTransition(R.anim.slide_up_dialog,R.anim.slide_up_dialog);
                break;
        }
    }
}
