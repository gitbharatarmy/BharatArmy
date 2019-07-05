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

import com.bharatarmy.Adapter.TravelpopularcityhotelmaplistAdapter;
import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.databinding.ActivityTravelpopularCityMapBinding;
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

public class TravelpopularCityHotelMapViewActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {
    private static final int TAG_CODE_PERMISSION_LOCATION = 134;
    ActivityTravelpopularCityMapBinding activityTravelpopularCityMapBinding;
    Context mContext;
    GoogleMap googleMapj;
    SupportMapFragment mapFragment;
    List<LatLng> list;

    ArrayList<TravelModel> cityHotelList;
    TravelpopularcityhotelmaplistAdapter travelpopularcityhotelmaplistAdapter;
String headerStr;

    Marker markertaj,markerhyatt,markerfourseason,markerleela,markermarriot,markertrident;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTravelpopularCityMapBinding = DataBindingUtil.setContentView(this, R.layout.activity_travelpopular_city_map);

        mContext = TravelpopularCityHotelMapViewActivity.this;

        init();
        setListiner();
        setDataList();
    }


    public void init() {
        headerStr=getIntent().getStringExtra("cityName");
        activityTravelpopularCityMapBinding.toolbarTitleTxt.setText("Mumbai - Hotels");

        activityTravelpopularCityMapBinding.cityHotelRcv.showShimmerAdapter();
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    public void setListiner() {
        activityTravelpopularCityMapBinding.backImg.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                TravelpopularCityHotelMapViewActivity.this.finish();
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

            markertaj = googleMapj.addMarker(new MarkerOptions()
                    .position(new LatLng(18.922028, 72.833358))
                    .anchor(0.9f, 0.9f)
                    .title("Taj Mahal Hotel")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//            marker.showInfoWindow();

            markerhyatt= googleMapj.addMarker(new MarkerOptions()
                    .position(new LatLng(19.1055, 72.8625))
                    .anchor(0.9f, 0.9f)
                    .title("Hotel Hyatt")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

            markerfourseason= googleMapj.addMarker(new MarkerOptions()
                    .position(new LatLng(18.9934, 72.8207))
                    .anchor(0.9f, 0.9f)
                    .title("Four Season Hotel")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

            markerleela= googleMapj.addMarker(new MarkerOptions()
                    .position(new LatLng(19.1055, 72.8625))
                    .anchor(0.9f, 0.9f)
                    .title("The Leela")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

            markertrident=googleMapj.addMarker(new MarkerOptions()
                    .position(new LatLng(18.9277, 72.8212))
                    .anchor(0.9f, 0.9f)
                    .title("JW Marriott Juhu")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

            markermarriot= googleMapj.addMarker(new MarkerOptions()
                    .position(new LatLng(19.1019, 72.8263))
                    .anchor(0.9f, 0.9f)
                    .title("JW Marriott Juhu")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

            googleMapj.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    Intent cityHotelDetail=new Intent(mContext,TravelCityHotelDetailsActivity.class);
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
        cityHotelList = new ArrayList<TravelModel>();

        cityHotelList.add(new TravelModel("Taj Mahal Hotel",AppConfiguration.IMAGE_URL+"mumbai.jpg", "4.3",
                "The Taj Mahal Palace opened in Mumbai, then Bombay, in 1903, giving birth to the country’s first harbour landmark. The recently trademarked flagship hotel overlooks the majestic Gateway of India."));

        cityHotelList.add(new TravelModel("Hyatt Regency Mumbai",AppConfiguration.IMAGE_URL+"hyatt.jpg", "4.7",
                "This hotel features 2 restaurants, a full-service spa and an outdoor pool. Free WiFi in public areas and free valet parking are also provided. Additionally, a fitness centre, a bar/lounge and a coffee shop/café are on-site. All 401 rooms boast deep soaking bathtubs, and offer free WiFi and iPod docking stations."));

        cityHotelList.add(new TravelModel("Four Seasons Hotel Mumbai",AppConfiguration.IMAGE_URL+"fourseason.jpg", "4.7",
                "Set 3 km from the Siddhivinayak Temple, this plush high-rise hotel with a glass facade is 8 km from both Girgaum Chowpatty beach and Mahatma Jyotiba Phule Mandai market"));

        cityHotelList.add(new TravelModel("The Leela Mumbai",AppConfiguration.IMAGE_URL+"lela.jpg",  "4.7",
                "This hotel features 2 restaurants, a full-service spa and an outdoor pool. Free WiFi in public areas and free valet parking are also provided. Additionally, a fitness centre, a bar/lounge and a coffee shop/café are on-site. All 401 rooms boast deep soaking bathtubs, and offer free WiFi and iPod docking stations."));

        cityHotelList.add(new TravelModel("Trident Bandra Kurla Mumbai",AppConfiguration.IMAGE_URL+"trident.jpg", "4.7",
                "The Trident Nariman Point is a 35-storey skyscraper hotel on Marine Drive in Nariman Point, Mumbai, India, operated by the Trident Hotels division of The Oberoi Group. On completion in 1973, it was the tallest building in South Asia, surpassing the 105 metres (344 ft) Express Towers, located next door.[1] It stayed the tallest building in South Asia until 1980, when Phiroze Jeejeebhoy Towers were completed."));

        cityHotelList.add(new TravelModel("JW Marriott Mumbai Juhu",AppConfiguration.IMAGE_URL+"jwmarriott.jpg", "4.7",
                "In a modern, gated property on Juhu Beach, this upscale hotel is 7 km from Chhatrapati Shivaji International Airport Mumbai." ));


        travelpopularcityhotelmaplistAdapter = new TravelpopularcityhotelmaplistAdapter(mContext, cityHotelList, new image_click() {
            @Override
            public void image_more_click() {
                String getHotelData = String.valueOf(travelpopularcityhotelmaplistAdapter.getData());

                getHotelData = getHotelData.substring(1, getHotelData.length() - 1);
                Log.d("hotelCLickPosition :", getHotelData);

                if (getHotelData.equalsIgnoreCase("0")) {
                    CameraUpdate center =
                            CameraUpdateFactory.newLatLng(new LatLng(18.922028, 72.833358));
                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(18);

                    googleMapj.moveCamera(center);
                    googleMapj.animateCamera(zoom);

                }
                else if(getHotelData.equalsIgnoreCase("1")){
                    CameraUpdate center =
                            CameraUpdateFactory.newLatLng(new LatLng(19.1055, 72.8625));
                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(18);

                    googleMapj.moveCamera(center);
                    googleMapj.animateCamera(zoom);
                }else if(getHotelData.equalsIgnoreCase("2")){
                    CameraUpdate center =
                            CameraUpdateFactory.newLatLng(new LatLng(18.9934, 72.8207));
                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(18);

                    googleMapj.moveCamera(center);
                    googleMapj.animateCamera(zoom);
                }else if(getHotelData.equalsIgnoreCase("3")){
                    CameraUpdate center =
                            CameraUpdateFactory.newLatLng(new LatLng(19.1055, 72.8625));
                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(18);

                    googleMapj.moveCamera(center);
                    googleMapj.animateCamera(zoom);
                }
                 else if(getHotelData.equalsIgnoreCase("4")){
                    CameraUpdate center =
                            CameraUpdateFactory.newLatLng(new LatLng(18.9277, 72.8212));
                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(18);

                    googleMapj.moveCamera(center);
                    googleMapj.animateCamera(zoom);

                }else if(getHotelData.equalsIgnoreCase("5")){
                    CameraUpdate center =
                            CameraUpdateFactory.newLatLng(new LatLng(19.1019, 72.8263));
                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(18);

                    googleMapj.moveCamera(center);
                    googleMapj.animateCamera(zoom);
                }


            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false);
        activityTravelpopularCityMapBinding.cityHotelRcv.setLayoutManager(mLayoutManager);
        activityTravelpopularCityMapBinding.cityHotelRcv.setItemAnimator(new DefaultItemAnimator());
        activityTravelpopularCityMapBinding.cityHotelRcv.setAdapter(travelpopularcityhotelmaplistAdapter);
        activityTravelpopularCityMapBinding.cityHotelRcv.hideShimmerAdapter();

    }

    // method definition
    public BitmapDescriptor getMarkerIcon(String color) {
        float[] hsv = new float[3];
        Color.colorToHSV(Color.parseColor(color), hsv);
        return BitmapDescriptorFactory.defaultMarker(hsv[0]);
    }
}
