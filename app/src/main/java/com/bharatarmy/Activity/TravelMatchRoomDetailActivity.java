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
import android.content.Intent;
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

import com.bharatarmy.Models.MyScreenChnagesModel;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityTravelMatchRoomDetailBinding;
import com.bharatarmy.databinding.HotelGalleryViewpageBinding;
import com.google.android.material.appbar.AppBarLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class TravelMatchRoomDetailActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityTravelMatchRoomDetailBinding activityTravelMatchRoomDetailBinding;
    Context mContext;
    MyRoomGalleryViewPagerAdapter myRoomGalleryViewPagerAdapter;
    private TextView[] dots;
    ArrayList<TravelModel> roomGalleryList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTravelMatchRoomDetailBinding= DataBindingUtil. setContentView(this,R.layout.activity_travel_match_room_detail);
        mContext=TravelMatchRoomDetailActivity.this;
        init();
        setListiner();
        setDataList();
    }
    public void init() {
        if (getIntent().getStringExtra("roomName")!=null){
            activityTravelMatchRoomDetailBinding.hotelRoomTypenameTxt.setText(getIntent().getStringExtra("roomName"));
        }
        if (getIntent().getStringExtra("roomPrice")!=null){
            activityTravelMatchRoomDetailBinding.roomPriceTxt.setText(getIntent().getStringExtra("roomPrice"));
        }
    }

    public void setListiner() {
        setSupportActionBar(activityTravelMatchRoomDetailBinding.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);


        activityTravelMatchRoomDetailBinding.roomSelectionTxt.setOnClickListener(this);

        activityTravelMatchRoomDetailBinding.roomAppbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
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
                    activityTravelMatchRoomDetailBinding.toolbar.setBackgroundColor(ContextCompat.getColor(mContext, R.color.heading_bg));
                    activityTravelMatchRoomDetailBinding.roomCollapseToolbar.setTitle("Room Detail");
                    Typeface typeface = ResourcesCompat.getFont(mContext, R.font.helveticaneueltstdbdcn);
                    activityTravelMatchRoomDetailBinding.roomCollapseToolbar.setCollapsedTitleTypeface(typeface);
                    activityTravelMatchRoomDetailBinding.roomCollapseToolbar.setExpandedTitleTypeface(typeface);
                    activityTravelMatchRoomDetailBinding.roomCollapseToolbar.setCollapsedTitleGravity(Gravity.START);
                    activityTravelMatchRoomDetailBinding.roomCollapseToolbar.setExpandedTitleGravity(Gravity.START);

                    isShow = true;
                } else if (isShow) {
                    activityTravelMatchRoomDetailBinding.toolbar.setBackgroundColor(ContextCompat.getColor(mContext, R.color.transparent));
                    activityTravelMatchRoomDetailBinding.roomCollapseToolbar.setTitle(" ");
                    isShow = false;
                }

            }
        });
        activityTravelMatchRoomDetailBinding.backImg.setOnClickListener(this);

    }


    public void setDataList() {
//        hotel gallery List
        roomGalleryList = new ArrayList<TravelModel>();
        roomGalleryList.add(new TravelModel(AppConfiguration.IMAGE_URL + "d_hotelroom1.jpg", "Luxury Room"));
        roomGalleryList.add(new TravelModel(AppConfiguration.IMAGE_URL + "d_hotelroom2.jpg", "Luxury Grande Room"));
        roomGalleryList.add(new TravelModel(AppConfiguration.IMAGE_URL + "d_hotelroom3.jpg", "Luxury Sea View Room"));
        roomGalleryList.add(new TravelModel(AppConfiguration.IMAGE_URL + "d_hotelroom4.jpg", "Taj Club Room"));
        roomGalleryList.add(new TravelModel(AppConfiguration.IMAGE_URL + "d_hotelroom5.jpg", "Luxury Suite 1 Bedroom City View"));
        roomGalleryList.add(new TravelModel(AppConfiguration.IMAGE_URL + "d_hotelroom6.jpg", "Signature Suite"));
        
        addBottomDots(0);
        myRoomGalleryViewPagerAdapter = new MyRoomGalleryViewPagerAdapter();
        activityTravelMatchRoomDetailBinding.roomGalleryViewpager.setAdapter(myRoomGalleryViewPagerAdapter);
        activityTravelMatchRoomDetailBinding.roomGalleryViewpager.addOnPageChangeListener(viewPagerPageChangeListener);

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
               finish();
                break;
            case R.id.room_selection_txt:
//                Log.d("roomdetail position :",getIntent().getStringExtra("clickposition"));
//                EventBus.getDefault().post(new MyScreenChnagesModel(getIntent().getStringExtra("roomName"),
//                        getIntent().getStringExtra("rooImage"),getIntent().getStringExtra("clickposition")));
//                finish();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                TravelMatchRoomDetailActivity.this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @SuppressLint("ResourceAsColor")
    private void addBottomDots(int currentPage) {
        dots = new TextView[roomGalleryList.size()];
        List<String> colorsActiveList = new ArrayList<>();
        List<String> colorsInactive = new ArrayList<>();
        for (int i = 0; i < roomGalleryList.size(); i++) {
            colorsActiveList.add(String.valueOf(getResources().getColor(R.color.splash_bg_color)));
            colorsInactive.add(String.valueOf(getResources().getColor(R.color.black)));
        }


        activityTravelMatchRoomDetailBinding.viewPagerDotlinear.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            activityTravelMatchRoomDetailBinding.viewPagerDotlinear.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(Integer.parseInt(colorsActiveList.get(currentPage)));//colorsActive[currentPage]
    }

    private int getItem(int i) {
        return activityTravelMatchRoomDetailBinding.roomGalleryViewpager.getCurrentItem() + i;
    }

    public class MyRoomGalleryViewPagerAdapter extends PagerAdapter {
//        private LayoutInflater layoutInflater;
//        ImageView room_gallery_image;

        public MyRoomGalleryViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup parent, int position) {
//            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//            View view = layoutInflater.inflate(R.layout.hotel_gallery_viewpage, container, false);
//
//            room_gallery_image = (ImageView) view.findViewById(R.id.hotel_gallery_image);

            HotelGalleryViewpageBinding hotelGalleryViewpageBinding =DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.hotel_gallery_viewpage,parent,false);

            Utils.setImageInImageView(roomGalleryList.get(position).getCityHotelAmenitiesImage(),hotelGalleryViewpageBinding.hotelGalleryImage, mContext);

            Log.d("HotelGalleeryAdapter : ", roomGalleryList.get(position).getCityHotelAmenitiesImage());
           parent.addView(hotelGalleryViewpageBinding.getRoot());

            return hotelGalleryViewpageBinding.getRoot();
        }

        @Override
        public int getCount() {
            return roomGalleryList.size();
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
