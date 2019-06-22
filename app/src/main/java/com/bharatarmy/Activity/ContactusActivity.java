package com.bharatarmy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.os.Bundle;

import com.bharatarmy.R;
import com.bharatarmy.databinding.ActivityContactusBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ContactusActivity extends BaseActivity implements OnMapReadyCallback {

    ActivityContactusBinding activityContactusBinding;
    Context mContext;
    SupportMapFragment mapFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityContactusBinding= DataBindingUtil.setContentView(this,R.layout.activity_contactus);

        mContext=ContactusActivity.this;

        setTitleText("Contact Us");
        setBackButton(ContactusActivity.this);
        setMapValue();

    }

    public void setMapValue(){
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

                googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(37.4233438, -122.0728817))
                        .title("LinkedIn")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

                googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(37.4629101,-122.2449094))
                        .title("Facebook")
                        .snippet("Facebook HQ: Menlo Park"));

                googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(37.3092293, -122.1136845))
                        .title("Apple"));

                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.4233438, -122.0728817), 10));
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
}
