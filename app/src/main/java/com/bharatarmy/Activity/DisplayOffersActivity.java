package com.bharatarmy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityDisplayOffersBinding;

public class DisplayOffersActivity extends AppCompatActivity implements View.OnClickListener {
 ActivityDisplayOffersBinding activityDisplayOffersBinding;
 Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       activityDisplayOffersBinding= DataBindingUtil. setContentView(this,R.layout.activity_display_offers);

       mContext=DisplayOffersActivity.this;

       init();
       setListiner();
    }

    public void init(){
        Utils.setImageInImageView("http://devenv.bharatarmy.com//Docs/Media/77f83c07-8620-4f4c-b309-7e0aa3297d4e-Vizag.jpg",
                activityDisplayOffersBinding.offersImg,mContext);
    }

    public void setListiner(){
        activityDisplayOffersBinding.closeLinear.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.close_linear:
                finish();
                break;
        }
    }
}
