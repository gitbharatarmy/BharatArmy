package com.bharatarmy.Activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bharatarmy.R;
import com.bharatarmy.databinding.ActivityLocationMapBinding;
import com.squareup.picasso.Picasso;

public class LocationMapActivity extends BaseActivity {

    ActivityLocationMapBinding locationMapBinding;
    Context mContext;
    String locationMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locationMapBinding = DataBindingUtil.setContentView(this, R.layout.activity_location_map);
        mContext = LocationMapActivity.this;

        setBackButton(LocationMapActivity.this);
        setTitleText("View Stadium Map");

        Picasso.with(mContext)
                .load(R.drawable.first_match_map)
                .placeholder(R.drawable.progress_animation)
                .into(locationMapBinding.imageFull);


        locationMapBinding.imageFull.getPositionAnimator().enter(locationMapBinding.imageDetailImg, false);
    }
}
