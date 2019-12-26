package com.bharatarmy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import com.bharatarmy.R;
import com.bharatarmy.databinding.ActivityAllNotificationBinding;

public class AllNotificationActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityAllNotificationBinding activityAllNotificationBinding;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAllNotificationBinding = DataBindingUtil.setContentView(this, R.layout.activity_all_notification);

        mContext = AllNotificationActivity.this;

        init();
        setListiner();
    }

    public void init(){}

    public void setListiner(){
        activityAllNotificationBinding.backImg.setOnClickListener(this);

     activityAllNotificationBinding.mobileNotificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
         @Override
         public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

         }
     });

        activityAllNotificationBinding.smsNotificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });

        activityAllNotificationBinding.emailNotificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_img:
               whereToBack();
                break;
        }

    }

    public void whereToBack() {
        Intent alertIntent = new Intent(mContext, ProfileSettingActivity.class);
        startActivity(alertIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        whereToBack();
    }
}
