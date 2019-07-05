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
import android.view.View;
import android.view.ViewGroup;

import com.bharatarmy.Adapter.TravelMatchDetailAdapter;
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

    ArrayList<TravelModel> cityPlaceList;

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
        cityPlaceList = new ArrayList<TravelModel>();
        cityPlaceList.add(new TravelModel("Marine drive",
                "https://www.holidify.com/images/compressed/attractions/attr_1817.jpg",
                "4.3", "Starting from the north of Nariman Point and ending at the famous Chowpatty beach, the Marine Drive is a 3km long arc shaped road along the sea coast of South Mumbai. The coast lines the Arabian sea and is the best place to watch the sunset in Mumbai."));
        cityPlaceList.add(new TravelModel("Gateway of India",
                "South Africa tour of India, October 2019", "Catch all the action from the stands as two top test teams compete against"));
        cityPlaceList.add(new TravelModel("https://www.bharatarmy.com//Docs/e35eee60-7.jpg",
                "Bangladesh Tour of India, November 2019", "A full series involving the modern day rivalry  - India and Bangladesh. Wit"));
        cityPlaceList.add(new TravelModel("https://www.bharatarmy.com//Docs/76b0e612-9.jpg",
                "Windies Tour of India, December 2019", "Be a part of the Windies tour of India involving 3 ODIs and 3 T20s and chee"));
        cityPlaceList.add(new TravelModel("https://www.bharatarmy.com//Docs/71210037-f.jpg",
                "T20 World Cup 2020", "Australia is going to host T20 Cricket World Cup in 2020. Register your int"));


        travelPopularCityDetailAdapter = new TravelPopularCityDetailAdapter(mContext, cityPlaceList);
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
