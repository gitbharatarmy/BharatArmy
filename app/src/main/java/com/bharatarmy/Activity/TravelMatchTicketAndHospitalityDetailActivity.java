package com.bharatarmy.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Html;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bharatarmy.Adapter.HospitalityDetailFacilityAdapter;
import com.bharatarmy.Adapter.HospitalityDetailFixturesAdapter;
import com.bharatarmy.Adapter.UpcomingDashboardAdapter;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityTravelMatchTicketAndHospitalityDetailBinding;
import com.bharatarmy.databinding.HotelGalleryViewpageBinding;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class TravelMatchTicketAndHospitalityDetailActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {
    ActivityTravelMatchTicketAndHospitalityDetailBinding activityTravelMatchTicketAndHospitalityDetailBinding;
    Context mContext;
    ArrayList<TravelModel> travelHospitalityGalleryList;
    ArrayList<TravelModel> travelHospitalityFacilityList;
    ArrayList<TravelModel> travelHospitalityInclusionList;
    ArrayList<TravelModel> travelHospitalityFixturesList;
    MyHospitalityGalleryViewPagerAdapter myHospitalityGalleryViewPagerAdapter;
    private TextView[] dots;
    HospitalityDetailFacilityAdapter hospitalityDetailFacilityAdapter;
    HospitalityDetailFixturesAdapter hospitalityDetailFixturesAdapter;

    GoogleMap googleMapj;
    SupportMapFragment mapFragment;
    List<LatLng> list;
    private static final int TAG_CODE_PERMISSION_LOCATION = 134;
    Marker markercity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTravelMatchTicketAndHospitalityDetailBinding= DataBindingUtil. setContentView(this,R.layout.activity_travel_match_ticket_and_hospitality_detail);
    mContext=TravelMatchTicketAndHospitalityDetailActivity.this;
    
    init();
    setListiner();
    setDataValue();
    }

    public void init(){
        if (getIntent().getStringExtra("categoryName")!=null) {
            activityTravelMatchTicketAndHospitalityDetailBinding.toolbarTitleTxt.setText(getIntent().getStringExtra("categoryName"));
        }

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }
    
    public void setListiner(){
        activityTravelMatchTicketAndHospitalityDetailBinding.backImg.setOnClickListener(this);


        activityTravelMatchTicketAndHospitalityDetailBinding.customView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        activityTravelMatchTicketAndHospitalityDetailBinding.parentScrollview.requestDisallowInterceptTouchEvent(true);
                        // Disable touch on transparent view
                        return false;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        activityTravelMatchTicketAndHospitalityDetailBinding.parentScrollview.requestDisallowInterceptTouchEvent(false);
                        return true;

                    case MotionEvent.ACTION_MOVE:
                        activityTravelMatchTicketAndHospitalityDetailBinding.parentScrollview.requestDisallowInterceptTouchEvent(true);
                        return false;

                    default:
                        return true;
                }
            }
        });
    }
    
    public void setDataValue(){
//    viewpager fill dataList
        travelHospitalityGalleryList = new ArrayList<TravelModel>();
        travelHospitalityGalleryList.add(new TravelModel("https://3.imimg.com/data3/VE/IW/MY-16198270/hotel-management-service-500x500.jpg", "Hotel1"));
        travelHospitalityGalleryList.add(new TravelModel("https://i0.wp.com/www.perrygroup.com/wp-content/uploads/2016/01/service-pic3-1.jpg", "Hotel1"));
        travelHospitalityGalleryList.add(new TravelModel("https://www.morganrichardson.co.uk/wp-content/uploads/2017/11/Hotel-Insurance.jpg", "Hotel1"));
        travelHospitalityGalleryList.add(new TravelModel("https://media-cdn.tripadvisor.com/media/photo-s/12/33/09/4a/boardroom.jpg", "Hotel1"));
        travelHospitalityGalleryList.add(new TravelModel("https://4107m27y9dp1th7n63rhxul1-wpengine.netdna-ssl.com/wp-content/uploads/2016/06/collage@2x-min.png", "Hotel1"));
        travelHospitalityGalleryList.add(new TravelModel("http://www.horizonhotels.com/d/horizonhotels/media/Hero_Optimized/__thumbs_1598_587_crop/85364709.jpg?1418405590", "Hotel1"));
        travelHospitalityGalleryList.add(new TravelModel("http://www.dietzelintl.com/wp-content/uploads/2015/10/Dietzel-Test-Banner-1100x423.jpg", "Hotel1"));


        addBottomDots(0);
        myHospitalityGalleryViewPagerAdapter = new MyHospitalityGalleryViewPagerAdapter();
        activityTravelMatchTicketAndHospitalityDetailBinding.hospitalityGalleryViewpager.setAdapter(myHospitalityGalleryViewPagerAdapter);
        activityTravelMatchTicketAndHospitalityDetailBinding.hospitalityGalleryViewpager.addOnPageChangeListener(viewPagerPageChangeListener);

//    hospitality facitlity List
        travelHospitalityFacilityList = new ArrayList<TravelModel>();
        travelHospitalityFacilityList.add(new TravelModel(R.drawable.ic_premium_seating, "Mumbai"));
        travelHospitalityFacilityList.add(new TravelModel(R.drawable.ic_meal, "Dehli"));
        travelHospitalityFacilityList.add(new TravelModel(R.drawable.ic_wine, "Ahemedabad"));
        travelHospitalityFacilityList.add(new TravelModel(R.drawable.ic_entertainment, "Indore"));


        hospitalityDetailFacilityAdapter = new HospitalityDetailFacilityAdapter(mContext, travelHospitalityFacilityList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        activityTravelMatchTicketAndHospitalityDetailBinding.hospitalityFacilityRcv.setLayoutManager(mLayoutManager);
        activityTravelMatchTicketAndHospitalityDetailBinding.hospitalityFacilityRcv.setItemAnimator(new DefaultItemAnimator());
        activityTravelMatchTicketAndHospitalityDetailBinding.hospitalityFacilityRcv.setAdapter(hospitalityDetailFacilityAdapter);

//    inclusion list
        travelHospitalityInclusionList = new ArrayList<TravelModel>();
        travelHospitalityInclusionList.add(new TravelModel(R.drawable.ic_premium_seating, "Live entertainment throughout the day"));
        travelHospitalityInclusionList.add(new TravelModel(R.drawable.ic_meal, "Extensive menu with in room live chef sation"));
        travelHospitalityInclusionList.add(new TravelModel(R.drawable.ic_wine, "5 hour premium beverage package"));
        travelHospitalityInclusionList.add(new TravelModel(R.drawable.ic_entertainment, "Premium seating"));
        travelHospitalityInclusionList.add(new TravelModel(R.drawable.ic_entertainment,"Access to win money can't buy experiences"));


        for (int i=0;i<travelHospitalityInclusionList.size();i++){
            View view =LayoutInflater.from(mContext).inflate(R.layout.hospitality_inclusion_list,null);

            TextView txt=(TextView)view.findViewById(R.id.inclusion_txt);

            txt.setText(travelHospitalityInclusionList.get(i).getMatchteamVenues());

            activityTravelMatchTicketAndHospitalityDetailBinding.inclusionLinear.addView(view);
        }
//    fixtures list
        travelHospitalityFixturesList=new ArrayList<TravelModel>();
        travelHospitalityFixturesList.add(new TravelModel("India",R.drawable.flag_india,
                 "South Africa",R.drawable.south_flag,"Match 2 | Karen Rolton Oval",
                "T20 | WOMEN'S"));
        travelHospitalityFixturesList.add(new TravelModel("India",R.drawable.flag_india,
                "Pakistan",R.drawable.flag_pakistan,"Allan Border Field","T20 | MEN'S"));
        travelHospitalityFixturesList.add(new TravelModel("South Africa",R.drawable.south_flag,"New Zealand",R.drawable.flag_new_zealand,
                "Match 3 | Karen Rolton Oval","T20 | MEN'S"));

        hospitalityDetailFixturesAdapter=new HospitalityDetailFixturesAdapter(mContext,travelHospitalityFixturesList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        activityTravelMatchTicketAndHospitalityDetailBinding.fixtureRcv.setLayoutManager(layoutManager);
        activityTravelMatchTicketAndHospitalityDetailBinding.fixtureRcv.setItemAnimator(new DefaultItemAnimator());
        activityTravelMatchTicketAndHospitalityDetailBinding.fixtureRcv.setAdapter(hospitalityDetailFixturesAdapter);
    }
    
    
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_img:
                finish();
                break;
        }
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
    
    @SuppressLint("ResourceAsColor")
    private void addBottomDots(int currentPage) {
        dots = new TextView[travelHospitalityGalleryList.size()];
        List<String> colorsActiveList = new ArrayList<>();
        List<String> colorsInactive = new ArrayList<>();
        for (int i = 0; i < travelHospitalityGalleryList.size(); i++) {
            colorsActiveList.add(String.valueOf(getResources().getColor(R.color.splash_bg_color)));
            colorsInactive.add(String.valueOf(getResources().getColor(R.color.black)));
        }


        activityTravelMatchTicketAndHospitalityDetailBinding.viewPagerDotlinear.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            activityTravelMatchTicketAndHospitalityDetailBinding.viewPagerDotlinear.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(Integer.parseInt(colorsActiveList.get(currentPage)));//colorsActive[currentPage]
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);
            googleMap.getUiSettings().setMyLocationButtonEnabled(true);
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

            googleMapj=googleMap;

            CameraUpdate center =
                    CameraUpdateFactory.newLatLng(new LatLng( -37.819954, 144.983398));
            CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);

            googleMapj.moveCamera(center);
            googleMapj.animateCamera(zoom);

            markercity = googleMapj.addMarker(new MarkerOptions()
                    .position(new LatLng( -37.819954, 144.983398))
                    .title("Melbourne")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

        } else {
            ActivityCompat.requestPermissions(this, new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION},
                    TAG_CODE_PERMISSION_LOCATION);
        }
    }


    public class MyHospitalityGalleryViewPagerAdapter extends PagerAdapter {
//        private LayoutInflater layoutInflater;
//        ImageView hospitality_gallery_image;

        public MyHospitalityGalleryViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup parent, int position) {

            HotelGalleryViewpageBinding hotelGalleryViewpageBinding =DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.hotel_gallery_viewpage,parent,false);


            Utils.setImageInImageView(travelHospitalityGalleryList.get(position).getCityHotelAmenitiesImage(), hotelGalleryViewpageBinding.hotelGalleryImage, mContext);

            Log.d("HotelGalleeryAdapter : ", travelHospitalityGalleryList.get(position).getCityHotelAmenitiesImage());
            parent.addView(hotelGalleryViewpageBinding.getRoot());

            return hotelGalleryViewpageBinding.getRoot();
        }

        @Override
        public int getCount() {
            return travelHospitalityGalleryList.size();
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
