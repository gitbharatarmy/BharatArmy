package com.bharatarmy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bharatarmy.Adapter.TravelcityrestaurantmaplistAdapter;
import com.bharatarmy.Adapter.TravelcitysightseenmaplistAdapter;
import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.databinding.ActivityTravelCityAllSightseenMapViewBinding;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class TravelCityAllSightseenMapViewActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {
    private static final int TAG_CODE_PERMISSION_LOCATION = 134;
    ActivityTravelCityAllSightseenMapViewBinding activityTravelCityAllSightseenMapViewBinding;
    Context mContext;
    GoogleMap googleMapj;
    SupportMapFragment mapFragment;
    List<LatLng> list;

    ArrayList<TravelModel> citySightseenList;
    TravelcitysightseenmaplistAdapter travelcitysightseenmaplistAdapter;
    String headerStr;

    Marker marker1,marker2,marker3,marker4,marker5,marker6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTravelCityAllSightseenMapViewBinding = DataBindingUtil.setContentView(this, R.layout.activity_travel_city_all_sightseen_map_view);

        mContext = TravelCityAllSightseenMapViewActivity.this;

        init();
        setListiner();
        setDataList();
    }


    public void init() {
        headerStr=getIntent().getStringExtra("cityName");
        activityTravelCityAllSightseenMapViewBinding.toolbarTitleTxt.setText("Mumbai - SightSeen");

        activityTravelCityAllSightseenMapViewBinding.citySightseenRcv.showShimmerAdapter();
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    public void setListiner() {
        activityTravelCityAllSightseenMapViewBinding.backImg.setOnClickListener(this);
activityTravelCityAllSightseenMapViewBinding.listImg.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                TravelCityAllSightseenMapViewActivity.this.finish();
                overridePendingTransition(R.anim.slide_out_down,R.anim.slide_out_down);
                break;
            case R.id.list_img:
                TravelCityAllSightseenMapViewActivity.this.finish();
                overridePendingTransition(R.anim.slide_out_down,R.anim.slide_out_down);
                break;
        }
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
                    CameraUpdateFactory.newLatLng(new LatLng(19.076090, 72.877426));
            CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);

            googleMapj.moveCamera(center);
            googleMapj.animateCamera(zoom);

            marker1 = googleMapj.addMarker(new MarkerOptions()
                    .position(new LatLng(18.941482, 72.823679))
                    .anchor(0.9f, 0.9f)
                    .title("Taj Mahal Hotel")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//            marker.showInfoWindow();

            marker2= googleMapj.addMarker(new MarkerOptions()
                    .position(new LatLng(18.941482, 72.823679))
                    .anchor(0.9f, 0.9f)
                    .title("Hotel Hyatt")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

            marker3= googleMapj.addMarker(new MarkerOptions()
                    .position(new LatLng(18.941482, 72.823679))
                    .anchor(0.9f, 0.9f)
                    .title("Four Season Hotel")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

            marker4= googleMapj.addMarker(new MarkerOptions()
                    .position(new LatLng(18.941482, 72.823679))
                    .anchor(0.9f, 0.9f)
                    .title("The Leela")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

            marker5=googleMapj.addMarker(new MarkerOptions()
                    .position(new LatLng( 18.941482,  72.823679))
                    .anchor(0.9f, 0.9f)
                    .title("JW Marriott Juhu")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

            marker6= googleMapj.addMarker(new MarkerOptions()
                    .position(new LatLng(18.941482, 72.823679))
                    .anchor(0.9f, 0.9f)
                    .title("JW Marriott Juhu")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

            googleMapj.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    Intent cityHotelDetail=new Intent(mContext,TravelCitySightseenDetailActivity.class);
                    startActivity(cityHotelDetail);
                    return true;
                }
            });
        } else {
            ActivityCompat.requestPermissions(this, new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION},
                    TAG_CODE_PERMISSION_LOCATION);
        }
    }

    public void setDataList() {
        citySightseenList = new ArrayList<TravelModel>();

        citySightseenList.add(new TravelModel("Marine drive", AppConfiguration.IMAGE_URL + "marinedrive.jpg", "4",
                "Marine Drive is one of the most beautifully laid boulevards in southern part of Mumbai. Scroll further and figure out more information about this natural bay."));

        citySightseenList.add(new TravelModel("Bandra Worli Sea Link",AppConfiguration.IMAGE_URL + "bandra_warli.jpg", "3",
                "Bandra Worli Sea Link is one of the few bridges that is a cable-stayed with pre-stressed concrete-steel viaducts to hold it in place."));

        citySightseenList.add(new TravelModel("Gateway of India",AppConfiguration.IMAGE_URL + "gate_way_of_india.jpg", "5",
                "The Gateway of India is one of India's most unique landmarks situated in the city of Mumbai. The colossal structure was constructed in 1924. Located at the tip of Apollo Bunder, the gateway overlooks the Mumbai harbor, bordered by the Arabian Sea in the Colaba district."));

        citySightseenList.add(new TravelModel("Global Vipassana Pagoda",AppConfiguration.IMAGE_URL + "global_vipassana_pagoda.jpg",  "3",
                "Global Vipassana Pagoda is an expression of our gratitude towards the Buddha who strived for incalculable aeons to reach Supreme Enlightenment."));

        citySightseenList.add(new TravelModel("Water Kingdom",AppConfiguration.IMAGE_URL + "water_kingdom.jpg", "4",
                "Water Kingdom Mumbai. Perhaps the biggest one in India and the oldest in Mumbai, the Water Kingdom gets a footfall of about 1.8 million people."));

        citySightseenList.add(new TravelModel("Elephanta Caves Island Tour",AppConfiguration.IMAGE_URL + "elephanta.jpg", "3",
                "Tour the Elephanta Caves or “City of Caves,” on a unique island close to Bombay." ));


        travelcitysightseenmaplistAdapter = new TravelcitysightseenmaplistAdapter(mContext, citySightseenList, new image_click() {
            @Override
            public void image_more_click() {
                String getHotelData = String.valueOf(travelcitysightseenmaplistAdapter.getData());

                getHotelData = getHotelData.substring(1, getHotelData.length() - 1);
                Log.d("hotelCLickPosition :", getHotelData);

                if (getHotelData.equalsIgnoreCase("0")) {
                    CameraUpdate center =
                            CameraUpdateFactory.newLatLng(new LatLng(18.941482, 72.823679));
                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(18);

                    googleMapj.moveCamera(center);
                    googleMapj.animateCamera(zoom);

                }
                else if(getHotelData.equalsIgnoreCase("1")){
                    CameraUpdate center =
                            CameraUpdateFactory.newLatLng(new LatLng(18.941482, 72.823679));
                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(18);

                    googleMapj.moveCamera(center);
                    googleMapj.animateCamera(zoom);
                }else if(getHotelData.equalsIgnoreCase("2")){
                    CameraUpdate center =
                            CameraUpdateFactory.newLatLng(new LatLng(18.941482, 72.823679));
                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(18);

                    googleMapj.moveCamera(center);
                    googleMapj.animateCamera(zoom);
                }else if(getHotelData.equalsIgnoreCase("3")){
                    CameraUpdate center =
                            CameraUpdateFactory.newLatLng(new LatLng(18.941482, 72.823679));
                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(18);

                    googleMapj.moveCamera(center);
                    googleMapj.animateCamera(zoom);
                }
                else if(getHotelData.equalsIgnoreCase("4")){
                    CameraUpdate center =
                            CameraUpdateFactory.newLatLng(new LatLng(18.941482,  72.823679));
                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(18);

                    googleMapj.moveCamera(center);
                    googleMapj.animateCamera(zoom);

                }else if(getHotelData.equalsIgnoreCase("5")){
                    CameraUpdate center =
                            CameraUpdateFactory.newLatLng(new LatLng(18.941482, 72.823679));
                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(18);

                    googleMapj.moveCamera(center);
                    googleMapj.animateCamera(zoom);
                }


            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false);
        activityTravelCityAllSightseenMapViewBinding.citySightseenRcv.setLayoutManager(mLayoutManager);
        activityTravelCityAllSightseenMapViewBinding.citySightseenRcv.setItemAnimator(new DefaultItemAnimator());
        activityTravelCityAllSightseenMapViewBinding.citySightseenRcv.setAdapter(travelcitysightseenmaplistAdapter);
        activityTravelCityAllSightseenMapViewBinding.citySightseenRcv.hideShimmerAdapter();

    }


}
