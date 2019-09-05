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
import com.bharatarmy.databinding.ActivityTravelCitySightseenDetailBinding;
import com.google.android.material.appbar.AppBarLayout;

import java.util.ArrayList;
import java.util.List;

public class TravelCitySightseenDetailActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityTravelCitySightseenDetailBinding activityTravelCitySightseenDetailBinding;
    Context mContext;
    private MySightSeenGalleryViewPagerAdapter mySightSeenGalleryViewPagerAdapter;
    private TextView[] dots;
    ArrayList<TravelModel> cityRestaurantGalleryList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTravelCitySightseenDetailBinding= DataBindingUtil. setContentView(this,R.layout.activity_travel_city_sightseen_detail);
        mContext=TravelCitySightseenDetailActivity.this;
        init();
        setListiner();
        setDataList();
    }
    public void init() {
    }

    public void setListiner() {
        setSupportActionBar(activityTravelCitySightseenDetailBinding.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        activityTravelCitySightseenDetailBinding.backImg.setOnClickListener(this);
        activityTravelCitySightseenDetailBinding.appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
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
                    activityTravelCitySightseenDetailBinding.toolbar.setBackgroundColor(ContextCompat.getColor(mContext, R.color.heading_bg));
                    activityTravelCitySightseenDetailBinding.collapsingToolbar.setTitle("Marine Drive");
                    Typeface typeface = ResourcesCompat.getFont(mContext, R.font.helveticaneueltstdbdcn);
                    activityTravelCitySightseenDetailBinding.collapsingToolbar.setCollapsedTitleTypeface(typeface);
                    activityTravelCitySightseenDetailBinding.collapsingToolbar.setExpandedTitleTypeface(typeface);
                    activityTravelCitySightseenDetailBinding.collapsingToolbar.setCollapsedTitleGravity(Gravity.START);
                    activityTravelCitySightseenDetailBinding.collapsingToolbar.setExpandedTitleGravity(Gravity.START);

                    isShow = true;
                } else if (isShow) {
                    activityTravelCitySightseenDetailBinding.toolbar.setBackgroundColor(ContextCompat.getColor(mContext, R.color.transparent));
                    activityTravelCitySightseenDetailBinding.collapsingToolbar.setTitle(" ");
                    isShow = false;
                }

            }
        });


    }


    public void setDataList() {
//        hotel gallery List
        cityRestaurantGalleryList = new ArrayList<TravelModel>();
        cityRestaurantGalleryList.add(new TravelModel(AppConfiguration.IMAGE_URL + "marine1.jpg", "Hotel1"));
        cityRestaurantGalleryList.add(new TravelModel(AppConfiguration.IMAGE_URL + "marine2.jpg", "Hotel1"));
        cityRestaurantGalleryList.add(new TravelModel(AppConfiguration.IMAGE_URL + "marine3.jpg", "Hotel1"));
        cityRestaurantGalleryList.add(new TravelModel(AppConfiguration.IMAGE_URL + "marine4.jpg", "Hotel1"));
        cityRestaurantGalleryList.add(new TravelModel(AppConfiguration.IMAGE_URL + "marine5.jpg", "Hotel1"));
        cityRestaurantGalleryList.add(new TravelModel(AppConfiguration.IMAGE_URL + "marine6.jpg", "Hotel1"));
        cityRestaurantGalleryList.add(new TravelModel(AppConfiguration.IMAGE_URL + "marine7.jpg", "Hotel1"));
        cityRestaurantGalleryList.add(new TravelModel(AppConfiguration.IMAGE_URL + "marine8.jpg", "Hotel1"));
        addBottomDots(0);
        mySightSeenGalleryViewPagerAdapter = new MySightSeenGalleryViewPagerAdapter();
        activityTravelCitySightseenDetailBinding.sightseenGalleryViewpager.setAdapter(mySightSeenGalleryViewPagerAdapter);
        activityTravelCitySightseenDetailBinding.sightseenGalleryViewpager.addOnPageChangeListener(viewPagerPageChangeListener);

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
                TravelCitySightseenDetailActivity.this.finish();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                TravelCitySightseenDetailActivity.this.finish();
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


        activityTravelCitySightseenDetailBinding.viewPagerDotlinear.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            activityTravelCitySightseenDetailBinding.viewPagerDotlinear.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(Integer.parseInt(colorsActiveList.get(currentPage)));//colorsActive[currentPage]
    }

    private int getItem(int i) {
        return activityTravelCitySightseenDetailBinding.sightseenGalleryViewpager.getCurrentItem() + i;
    }

    public class MySightSeenGalleryViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;
        ImageView hotel_gallery_image;

        public MySightSeenGalleryViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(R.layout.hotel_gallery_viewpage, container, false);

            hotel_gallery_image = (ImageView) view.findViewById(R.id.hotel_gallery_image);

            Utils.setImageInImageView(cityRestaurantGalleryList.get(position).getCityHotelAmenitiesImage(), hotel_gallery_image, mContext);

            Log.d("HotelGalleeryAdapter : ", cityRestaurantGalleryList.get(position).getCityHotelAmenitiesImage());
            container.addView(view);

            return view;
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
