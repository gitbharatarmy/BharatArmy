package com.bharatarmy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import com.bharatarmy.Adapter.TravelPopularCityDetailAdapter;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityTravelPopularCityDetailBinding;
import com.google.android.material.appbar.AppBarLayout;

import java.util.ArrayList;

public class TravelPopularCityDetailActivity extends AppCompatActivity {

    ActivityTravelPopularCityDetailBinding activityTravelPopularCityDetailBinding;
    Context mContext;

    ArrayList<TravelModel> popularPackageList;

    TravelPopularCityDetailAdapter travelPopularCityDetailAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTravelPopularCityDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_travel_popular_city_detail);

        mContext = TravelPopularCityDetailActivity.this;

        init();
        setListiner();
        setDataList();
    }

    public void init() {
        setSupportActionBar(activityTravelPopularCityDetailBinding.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Utils.setImageInImageView(AppConfiguration.IMAGE_URL+"mumbai.jpg",activityTravelPopularCityDetailBinding.backdrop,mContext);
    }

    public void setListiner() {
        activityTravelPopularCityDetailBinding.appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(final AppBarLayout appBarLayout, int verticalOffset) {
                //Initialize the size of the scroll
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                //Check if the view is collapsed
                if (scrollRange + verticalOffset == 0) {
                    activityTravelPopularCityDetailBinding.toolbar.setBackgroundColor(ContextCompat.getColor(mContext, R.color.heading_bg));
                    activityTravelPopularCityDetailBinding.collapsingToolbar.setTitle("Mumbai");
                    Typeface typeface = ResourcesCompat.getFont(mContext, R.font.helveticaneueltstdbdcn);
                    activityTravelPopularCityDetailBinding.collapsingToolbar.setCollapsedTitleTypeface(typeface);
                    activityTravelPopularCityDetailBinding.collapsingToolbar.setExpandedTitleTypeface(typeface);
                    activityTravelPopularCityDetailBinding.collapsingToolbar.setCollapsedTitleGravity(Gravity.START);
                    activityTravelPopularCityDetailBinding.collapsingToolbar.setExpandedTitleGravity(Gravity.START);
//                    activityTravelPopularCityDetailBinding.tempLinear.setVisibility(View.VISIBLE);
                    isShow = true;
                } else if (isShow) {
                    activityTravelPopularCityDetailBinding.toolbar.setBackgroundColor(ContextCompat.getColor(mContext, R.color.transparent));
                    activityTravelPopularCityDetailBinding.collapsingToolbar.setTitle(" ");
//                    activityTravelPopularCityDetailBinding.tempLinear.setVisibility(View.GONE);
                    isShow = false;
                }
            }
        });
    }

    public void setDataList() {
        popularPackageList = new ArrayList<TravelModel>();
        popularPackageList.add(new TravelModel("xyz","Australian Double Dhamaka: Honeymoon & adventure at one shot",AppConfiguration.IMAGE_URL+"aus1.jpg",
                "Jet Boat Ride from Main Beach.Bungy jumping from 165 ft distance at Cairns.Great Barrier Reef Experience",
                "1k","900","true"));

        popularPackageList.add(new TravelModel("xyz","Explore the best of Australia with your soulmate",AppConfiguration.IMAGE_URL+"aus2.jpg",
                "Grand Barossa Valley Day Tour.Happy day out at the Kangaroo Island with a fun tour amidst natural highlights.Eureka Skydeck 88.Sydney Harbour Jet Boat Thrill Ride: 30 Minutes ",
                "2k","500","false"));

        popularPackageList.add(new TravelModel("xyz","Celebrate love in the Australian lands",AppConfiguration.IMAGE_URL+"aus3.jpg",
                "Delicious dinner cruise during sunset at Sydney Harbour exposed to amazing vistas and views around the arena.Super Pass: Film World, Sea World & Wet'n'Wild Water World.Morning Whale Watching Cruise.Car hire for Great Ocean Road",
                "5k","1000","false"));

        travelPopularCityDetailAdapter = new TravelPopularCityDetailAdapter(mContext, popularPackageList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        activityTravelPopularCityDetailBinding.cityDetailRcv.setLayoutManager(mLayoutManager);
        activityTravelPopularCityDetailBinding.cityDetailRcv.setItemAnimator(new DefaultItemAnimator());
        activityTravelPopularCityDetailBinding.cityDetailRcv.setAdapter(travelPopularCityDetailAdapter);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                TravelPopularCityDetailActivity.this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
