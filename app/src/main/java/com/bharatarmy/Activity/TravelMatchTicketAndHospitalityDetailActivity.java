package com.bharatarmy.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
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
import com.bharatarmy.Adapter.RelatedHospitalityDetailAdapter;
import com.bharatarmy.Adapter.TravelMatchHospitalityAdapter;
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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class TravelMatchTicketAndHospitalityDetailActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {
    ActivityTravelMatchTicketAndHospitalityDetailBinding activityTravelMatchTicketAndHospitalityDetailBinding;
    Context mContext;
    //    hospitality gallery list and adapter
    ArrayList<TravelModel> travelHospitalityGalleryList;
    MyHospitalityGalleryViewPagerAdapter myHospitalityGalleryViewPagerAdapter;
    private TextView[] dots;

    //    hospitality facility list and adapter
    ArrayList<TravelModel> travelHospitalityFacilityList;
    HospitalityDetailFacilityAdapter hospitalityDetailFacilityAdapter;

    //    hospitality inclusion list and adapter
    ArrayList<TravelModel> travelHospitalityInclusionList;

    //    hospitality fixtures list and adapter
    ArrayList<TravelModel> travelHospitalityFixturesList;
    HospitalityDetailFixturesAdapter hospitalityDetailFixturesAdapter;

    //    related hospitality list and adapter
    ArrayList<TravelModel> travelHospitalityRealtedHospitalityList;
    ArrayList<TravelModel> travelHospitalityRealtedHospitalityfinalList;
    RelatedHospitalityDetailAdapter relatedHospitalityDetailAdapter;

    //    showing location in google map
    GoogleMap googleMapj;
    SupportMapFragment mapFragment;
    List<LatLng> list;
    private static final int TAG_CODE_PERMISSION_LOCATION = 134;
    Marker markercity;

    //    use for ticket price
    int hospitalitycount = 1;
    double totalamount = 0;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTravelMatchTicketAndHospitalityDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_travel_match_ticket_and_hospitality_detail);
        mContext = TravelMatchTicketAndHospitalityDetailActivity.this;

        init();
        setListiner();
        setDataValue();
    }

    public void init() {
        if (getIntent().getStringExtra("categoryName") != null) {
            activityTravelMatchTicketAndHospitalityDetailBinding.matchHospitalityTypeTagTxt.setText(getIntent().getStringExtra("categoryName"));
//            activityTravelMatchTicketAndHospitalityDetailBinding.toolbarTitleTxt.setText(getIntent().getStringExtra("categoryName"));
        }

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    public void setListiner() {
        activityTravelMatchTicketAndHospitalityDetailBinding.backImg.setOnClickListener(this);
        activityTravelMatchTicketAndHospitalityDetailBinding.bottomCartView.setOnClickListener(this);
        activityTravelMatchTicketAndHospitalityDetailBinding.hospitalityMinusLayout.setOnClickListener(this);
        activityTravelMatchTicketAndHospitalityDetailBinding.hospitalityPlusLayout.setOnClickListener(this);

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

    public void setDataValue() {
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
        travelHospitalityInclusionList.add(new TravelModel(R.drawable.ic_entertainment, "Access to win money can't buy experiences"));


        for (int i = 0; i < travelHospitalityInclusionList.size(); i++) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.hospitality_inclusion_list, null);

            TextView txt = (TextView) view.findViewById(R.id.inclusion_txt);

            txt.setText(travelHospitalityInclusionList.get(i).getMatchteamVenues());

            activityTravelMatchTicketAndHospitalityDetailBinding.inclusionLinear.addView(view);
        }
//    fixtures list
        travelHospitalityFixturesList = new ArrayList<TravelModel>();
        travelHospitalityFixturesList.add(new TravelModel("India", R.drawable.flag_india,
                "South Africa", R.drawable.south_flag, "Sydney Cricket Ground, Sydney, Australia",
                "T20"));
        travelHospitalityFixturesList.add(new TravelModel("India", R.drawable.flag_india,
                "Pakistan", R.drawable.flag_pakistan, "Sydney Cricket Ground, Sydney, Australia", "T20"));
        travelHospitalityFixturesList.add(new TravelModel("South Africa", R.drawable.south_flag, "New Zealand", R.drawable.flag_new_zealand,
                "Perth Stadium, Perth, Australia", "T20"));


        hospitalityDetailFixturesAdapter = new HospitalityDetailFixturesAdapter(mContext, travelHospitalityFixturesList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        activityTravelMatchTicketAndHospitalityDetailBinding.fixtureRcv.setLayoutManager(layoutManager);
        activityTravelMatchTicketAndHospitalityDetailBinding.fixtureRcv.setItemAnimator(new DefaultItemAnimator());
        activityTravelMatchTicketAndHospitalityDetailBinding.fixtureRcv.setAdapter(hospitalityDetailFixturesAdapter);

//        related hospitality list
        travelHospitalityRealtedHospitalityList = new ArrayList<>();
        travelHospitalityRealtedHospitalityfinalList = new ArrayList<>();

        travelHospitalityRealtedHospitalityList.add(new TravelModel("https://3.imimg.com/data3/VE/IW/MY-16198270/hotel-management-service-500x500.jpg", "Hospitality Category",
                "The Pavilion", "The Pavilion is the ultimate hospitality experience that will deliver a sophisticated, yet relaxed environment to be shared with family, friends or business associates.",
                "Extra 10% off* with Hotel.", "₹ 475", "3", "hospitality", "0"));

        travelHospitalityRealtedHospitalityList.add(new TravelModel("https://www.astiregnatia.com/assets/media/PICTURES/Astir%20Executive%20Suite%20with%20private%20pool/astir-executive-suite-pricate-pool-3-2955.jpg", "",
                "Private Suites", "Private Suites provide the ultimate hospitality experience.",
                "Extra 20% off* with Hotel.", "₹ 600", "3", "hospitality", "0"));

        travelHospitalityRealtedHospitalityList.add(new TravelModel("https://3.imimg.com/data3/VE/IW/MY-16198270/hotel-management-service-500x500.jpg", "",
                "Open Air Boxes", "Open Air Boxes are a casual entertainment option providing you and your guests everything you need for an effortless day of cricket enjoyment.",
                "Extra 20% off* with Hotel.", "₹ 650", "1", "hospitality", "0"));

        travelHospitalityRealtedHospitalityList.add(new TravelModel("https://i0.wp.com/www.perrygroup.com/wp-content/uploads/2016/01/service-pic3-1.jpg", "",
                "Club 20/20", "Club 20/20 packages suit those seeking an informal entertainment experience that still provides hospitality with outstanding service.",
                "Extra 10% off* with Hotel.", "₹ 750", "2", "hospitality", "0"));


        for (int i = 0; i < travelHospitalityRealtedHospitalityList.size(); i++) {
            if (!travelHospitalityRealtedHospitalityList.get(i).getTicket_hospitality_namecategory().trim().equalsIgnoreCase(activityTravelMatchTicketAndHospitalityDetailBinding.matchHospitalityTypeTagTxt.getText().toString().trim())) {
                travelHospitalityRealtedHospitalityfinalList.add(travelHospitalityRealtedHospitalityList.get(i));
            }
        }

        relatedHospitalityDetailAdapter = new RelatedHospitalityDetailAdapter(mContext, travelHospitalityRealtedHospitalityfinalList);
        RecyclerView.LayoutManager relatedlayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        activityTravelMatchTicketAndHospitalityDetailBinding.relatedHospitalityRcv.setLayoutManager(relatedlayoutManager);
        activityTravelMatchTicketAndHospitalityDetailBinding.relatedHospitalityRcv.setItemAnimator(new DefaultItemAnimator());
        activityTravelMatchTicketAndHospitalityDetailBinding.relatedHospitalityRcv.setAdapter(relatedHospitalityDetailAdapter);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                finish();
                break;
            case R.id.bottom_cart_view:
                Utils.handleClickEvent(mContext, activityTravelMatchTicketAndHospitalityDetailBinding.bottomCartView);
                if (Utils.isMember(mContext, "Ticket Detail")) {
                    Intent cartIntent = new Intent(mContext, AddToCartActivity.class);
                    cartIntent.putExtra("bookingItemName", activityTravelMatchTicketAndHospitalityDetailBinding.matchHospitalityTypeTagTxt.getText().toString());
                    cartIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(cartIntent);
                }
                break;
            case R.id.hospitality_minus_layout:
                if (hospitalitycount != 1) {
                    hospitalitycount = hospitalitycount - 1;
//                    if (ticketcount <= 9) {
//                        activityTravelMatchTicketAndHospitalityDetailBinding.countOfItem.setText("0" + String.valueOf(ticketcount));
//
//                    } else {
                    activityTravelMatchTicketAndHospitalityDetailBinding.countOfItem.setText(String.valueOf(hospitalitycount));
//                    }

                    totalamount = 500 * hospitalitycount;
                    activityTravelMatchTicketAndHospitalityDetailBinding.priceTxt.setText(String.valueOf(roundTwoDecimals(totalamount)));
                } else {
                    activityTravelMatchTicketAndHospitalityDetailBinding.hospitalityMinusLayout.setClickable(false);
                }

                break;
            case R.id.hospitality_plus_layout:
                activityTravelMatchTicketAndHospitalityDetailBinding.hospitalityMinusLayout.setClickable(true);
                hospitalitycount = hospitalitycount + 1;
//                if (ticketcount <= 9) {
//                    activityTravelMatchTicketAndHospitalityDetailBinding.countOfItem.setText("0" + String.valueOf(ticketcount));
//                } else {
                totalamount = 500 * hospitalitycount;
                activityTravelMatchTicketAndHospitalityDetailBinding.priceTxt.setText(String.valueOf(roundTwoDecimals(totalamount)));
                activityTravelMatchTicketAndHospitalityDetailBinding.countOfItem.setText(String.valueOf(hospitalitycount));
//                }
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

            googleMapj = googleMap;

            CameraUpdate center =
                    CameraUpdateFactory.newLatLng(new LatLng(-37.819954, 144.983398));
            CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);

            googleMapj.moveCamera(center);
            googleMapj.animateCamera(zoom);

            markercity = googleMapj.addMarker(new MarkerOptions()
                    .position(new LatLng(-37.819954, 144.983398))
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

            HotelGalleryViewpageBinding hotelGalleryViewpageBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.hotel_gallery_viewpage, parent, false);


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

    // use for ticket price round figure
    double roundTwoDecimals(double d) {
        DecimalFormat twoDForm = new DecimalFormat("#.##");
        return Double.valueOf(twoDForm.format(d));
    }
}
