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
import com.bharatarmy.Adapter.TravelpopularcityhotelmaplistAdapter;
import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.databinding.ActivityTravelCityRestaurantMapViewBinding;
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

public class TravelCityRestaurantMapViewActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {
    private static final int TAG_CODE_PERMISSION_LOCATION = 134;
    ActivityTravelCityRestaurantMapViewBinding activityTravelCityRestaurantMapViewBinding;
    Context mContext;
    GoogleMap googleMapj;
    SupportMapFragment mapFragment;
    List<LatLng> list;

    ArrayList<TravelModel> cityRestaurantList;
    TravelcityrestaurantmaplistAdapter travelcityrestaurantmaplistAdapter;
    String headerStr;

    Marker markerseven,markerlotus,marker180degree,markerhornby,markerlakeview,markerpeshwa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTravelCityRestaurantMapViewBinding = DataBindingUtil.setContentView(this, R.layout.activity_travel_city_restaurant_map_view);

        mContext = TravelCityRestaurantMapViewActivity.this;

        init();
        setListiner();
        setDataList();
    }


    public void init() {
        headerStr=getIntent().getStringExtra("cityName");
        activityTravelCityRestaurantMapViewBinding.toolbarTitleTxt.setText("Mumbai - Restaurant");

        activityTravelCityRestaurantMapViewBinding.cityRestaurantRcv.showShimmerAdapter();
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    public void setListiner() {
        activityTravelCityRestaurantMapViewBinding.backImg.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                TravelCityRestaurantMapViewActivity.this.finish();
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

            markerseven = googleMapj.addMarker(new MarkerOptions()
                    .position(new LatLng(18.9938, 72.8242))
                    .anchor(0.9f, 0.9f)
                    .title("Taj Mahal Hotel")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//            marker.showInfoWindow();

            markerlotus= googleMapj.addMarker(new MarkerOptions()
                    .position(new LatLng(19.1018, 72.8259))
                    .anchor(0.9f, 0.9f)
                    .title("Hotel Hyatt")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

            marker180degree= googleMapj.addMarker(new MarkerOptions()
                    .position(new LatLng(18.9934, 72.8207))
                    .anchor(0.9f, 0.9f)
                    .title("Four Season Hotel")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

            markerhornby= googleMapj.addMarker(new MarkerOptions()
                    .position(new LatLng(19.1055, 72.8625))
                    .anchor(0.9f, 0.9f)
                    .title("The Leela")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

            markerlakeview=googleMapj.addMarker(new MarkerOptions()
                    .position(new LatLng(18.9277, 72.8212))
                    .anchor(0.9f, 0.9f)
                    .title("JW Marriott Juhu")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

            markerpeshwa= googleMapj.addMarker(new MarkerOptions()
                    .position(new LatLng(19.1019, 72.8263))
                    .anchor(0.9f, 0.9f)
                    .title("JW Marriott Juhu")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

            googleMapj.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    Intent cityHotelDetail=new Intent(mContext,TravelCityRestaurantDetailActivity.class);
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
        cityRestaurantList = new ArrayList<TravelModel>();

        cityRestaurantList.add(new TravelModel("Seven Kitchens Mumbai", AppConfiguration.IMAGE_URL + "seven_kitchen.jpg", "4.3",
                "Luxurious hotel dining room with huge windows and a menu of dishes from around the world."));

        cityRestaurantList.add(new TravelModel("180 Degrees Mumbai",AppConfiguration.IMAGE_URL + "180_degree.jpg", "4.7",
                "European, Middle East and Asian dishes are prepared in the open kitchen in this hotel outlet."));

        cityRestaurantList.add(new TravelModel("Hornby's Pavilion Mumbai",AppConfiguration.IMAGE_URL + "Hornby's_Pavilion.jpg", "4.7",
                "Upscale hotel's 24-hour restaurant serving international and Indian buffets and a la carte."));

        cityRestaurantList.add(new TravelModel("Lake View Cafe Mumbai",AppConfiguration.IMAGE_URL + "lake_view.jpg",  "4.7",
                "TLake View Café presents an enviable culinary theatre featuring an extensive Asian and Indian selection with the experience accentuated by the warm and engaging ambassadors, delivering a memorable experience each time."));

        cityRestaurantList.add(new TravelModel("The Peshwa Pavilion Mumbai",AppConfiguration.IMAGE_URL + "peshwa.jpg", "4.7",
                "The Peshwa Pavilion has carved a niche for itself in the hearts of people and gives you a spellbound experience."));

        cityRestaurantList.add(new TravelModel("Lotus Cafe Mumbai",AppConfiguration.IMAGE_URL + "lotus.jpg", "4.7",
                "Lotus Cafe, named after its beautiful Lotus pond and operational round the clock, is well known in the city for its elaborate buffet spread-an excellent blend of international cuisine." ));


        travelcityrestaurantmaplistAdapter = new TravelcityrestaurantmaplistAdapter(mContext, cityRestaurantList, new image_click() {
            @Override
            public void image_more_click() {
                String getHotelData = String.valueOf(travelcityrestaurantmaplistAdapter.getData());

                getHotelData = getHotelData.substring(1, getHotelData.length() - 1);
                Log.d("hotelCLickPosition :", getHotelData);

                if (getHotelData.equalsIgnoreCase("0")) {
                    CameraUpdate center =
                            CameraUpdateFactory.newLatLng(new LatLng(18.9938, 72.8242));
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
                            CameraUpdateFactory.newLatLng(new LatLng(19.1018, 72.8259));
                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(18);

                    googleMapj.moveCamera(center);
                    googleMapj.animateCamera(zoom);
                }


            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false);
        activityTravelCityRestaurantMapViewBinding.cityRestaurantRcv.setLayoutManager(mLayoutManager);
        activityTravelCityRestaurantMapViewBinding.cityRestaurantRcv.setItemAnimator(new DefaultItemAnimator());
        activityTravelCityRestaurantMapViewBinding.cityRestaurantRcv.setAdapter(travelcityrestaurantmaplistAdapter);
        activityTravelCityRestaurantMapViewBinding.cityRestaurantRcv.hideShimmerAdapter();

    }

    // method definition
    public BitmapDescriptor getMarkerIcon(String color) {
        float[] hsv = new float[3];
        Color.colorToHSV(Color.parseColor(color), hsv);
        return BitmapDescriptorFactory.defaultMarker(hsv[0]);
    }
}
