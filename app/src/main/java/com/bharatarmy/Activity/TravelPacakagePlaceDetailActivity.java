package com.bharatarmy.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityTravelPacakagePlaceDetailBinding;
import com.bharatarmy.databinding.HotelGalleryViewpageBinding;
import com.google.android.material.appbar.AppBarLayout;

import java.util.ArrayList;
import java.util.List;

public class TravelPacakagePlaceDetailActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityTravelPacakagePlaceDetailBinding activityTravelPacakagePlaceDetailBinding;
    Context mContext;
    private MyPacakgePlaceGalleryViewPagerAdapter myPacakgePlaceGalleryViewPagerAdapter;
    private TextView[] dots;
    ArrayList<TravelModel> cityRestaurantGalleryList;
    String titleStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTravelPacakagePlaceDetailBinding= DataBindingUtil. setContentView(this,R.layout.activity_travel_pacakage_place_detail);
        mContext=TravelPacakagePlaceDetailActivity.this;
        init();
        setListiner();
        setDataList();
    }
    public void init() {
        titleStr=getIntent().getStringExtra("placeName");
        activityTravelPacakagePlaceDetailBinding.pacakgeplacennameTxt.setText(titleStr);
    }

    public void setListiner() {
        setSupportActionBar(activityTravelPacakagePlaceDetailBinding.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        activityTravelPacakagePlaceDetailBinding.appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
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
                    activityTravelPacakagePlaceDetailBinding.toolbar.setBackgroundColor(ContextCompat.getColor(mContext, R.color.heading_bg));
                    activityTravelPacakagePlaceDetailBinding.collapsingToolbar.setTitle(titleStr);
                    Typeface typeface = ResourcesCompat.getFont(mContext, R.font.helveticaneueltstdbdcn);
                    activityTravelPacakagePlaceDetailBinding.collapsingToolbar.setCollapsedTitleTypeface(typeface);
                    activityTravelPacakagePlaceDetailBinding.collapsingToolbar.setExpandedTitleTypeface(typeface);
                    activityTravelPacakagePlaceDetailBinding.collapsingToolbar.setCollapsedTitleGravity(Gravity.START);
                    activityTravelPacakagePlaceDetailBinding.collapsingToolbar.setExpandedTitleGravity(Gravity.START);

                    isShow = true;
                } else if (isShow) {
                    activityTravelPacakagePlaceDetailBinding.toolbar.setBackgroundColor(ContextCompat.getColor(mContext, R.color.transparent));
                    activityTravelPacakagePlaceDetailBinding.collapsingToolbar.setTitle(" ");
                    isShow = false;
                }

            }
        });

activityTravelPacakagePlaceDetailBinding.backImg.setOnClickListener(this);
    }


    public void setDataList() {
//        hotel gallery List
        cityRestaurantGalleryList = new ArrayList<TravelModel>();
        cityRestaurantGalleryList.add(new TravelModel(AppConfiguration.IMAGE_URL + "zoo1.jpg", "Hotel1"));
        cityRestaurantGalleryList.add(new TravelModel(AppConfiguration.IMAGE_URL + "zoo2.jpg", "Hotel1"));
        cityRestaurantGalleryList.add(new TravelModel(AppConfiguration.IMAGE_URL + "zoo3.jpg", "Hotel1"));
        cityRestaurantGalleryList.add(new TravelModel(AppConfiguration.IMAGE_URL + "zoo4.jpg", "Hotel1"));
        cityRestaurantGalleryList.add(new TravelModel(AppConfiguration.IMAGE_URL + "zoo5.jpg", "Hotel1"));
        cityRestaurantGalleryList.add(new TravelModel(AppConfiguration.IMAGE_URL + "zoo6.jpg", "Hotel1"));
        cityRestaurantGalleryList.add(new TravelModel(AppConfiguration.IMAGE_URL + "zoo7.jpg", "Hotel1"));
        cityRestaurantGalleryList.add(new TravelModel(AppConfiguration.IMAGE_URL + "zoo8.jpg", "Hotel1"));
        addBottomDots(0);
        myPacakgePlaceGalleryViewPagerAdapter = new MyPacakgePlaceGalleryViewPagerAdapter();
        activityTravelPacakagePlaceDetailBinding.pacakgeplaceGalleryViewpager.setAdapter(myPacakgePlaceGalleryViewPagerAdapter);
        activityTravelPacakagePlaceDetailBinding.pacakgeplaceGalleryViewpager.addOnPageChangeListener(viewPagerPageChangeListener);

    }
    //  viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);//position

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };
    @Override

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                TravelPacakagePlaceDetailActivity.this.finish();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                TravelPacakagePlaceDetailActivity.this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @SuppressLint("ResourceAsColor")
    private void addBottomDots(int currentPage) {
        dots = new TextView[cityRestaurantGalleryList.size()];
        List<String> colorsActiveList = new ArrayList<>();
        List<String> colorsInactive = new ArrayList<>();
        for (int i = 0; i < cityRestaurantGalleryList.size(); i++) {
            colorsActiveList.add(String.valueOf(getResources().getColor(R.color.splash_bg_color)));
            colorsInactive.add(String.valueOf(getResources().getColor(R.color.black)));
        }


        activityTravelPacakagePlaceDetailBinding.viewPagerDotlinear.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            activityTravelPacakagePlaceDetailBinding.viewPagerDotlinear.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(Integer.parseInt(colorsActiveList.get(currentPage)));//colorsActive[currentPage]
    }

    private int getItem(int i) {
        return activityTravelPacakagePlaceDetailBinding.pacakgeplaceGalleryViewpager.getCurrentItem() + i;
    }

    public class MyPacakgePlaceGalleryViewPagerAdapter extends PagerAdapter {
//        private LayoutInflater layoutInflater;
//        ImageView hotel_gallery_image;

        public MyPacakgePlaceGalleryViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup parent, int position) {
//            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//            View view = layoutInflater.inflate(R.layout.hotel_gallery_viewpage, container, false);
//
//            hotel_gallery_image = (ImageView) view.findViewById(R.id.hotel_gallery_image);

            HotelGalleryViewpageBinding hotelGalleryViewpageBinding =DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.hotel_gallery_viewpage,parent,false);


            Utils.setImageInImageView(cityRestaurantGalleryList.get(position).getCityHotelAmenitiesImage(),hotelGalleryViewpageBinding.hotelGalleryImage, mContext);

            Log.d("HotelGalleeryAdapter : ", cityRestaurantGalleryList.get(position).getCityHotelAmenitiesImage());
            parent.addView(hotelGalleryViewpageBinding.getRoot());

            return hotelGalleryViewpageBinding.getRoot();
        }

        @Override
        public int getCount() {
            return cityRestaurantGalleryList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return super.getItemPosition(object);
        }
    }
}
