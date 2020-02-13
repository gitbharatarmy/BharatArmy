package com.bharatarmy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import com.bharatarmy.R;
import com.bharatarmy.databinding.ActivityAllNotificationSettingBinding;

public class AllNotificationSettingActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityAllNotificationSettingBinding activityAllNotificationSettingBinding;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAllNotificationSettingBinding = DataBindingUtil.setContentView(this, R.layout.activity_all_notification_setting);

        mContext = AllNotificationSettingActivity.this;

        init();
        setListiner();
    }

    public void init(){}

    public void setListiner(){
        activityAllNotificationSettingBinding.backImg.setOnClickListener(this);

     activityAllNotificationSettingBinding.mobileNotificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
         @Override
         public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

         }
     });

        activityAllNotificationSettingBinding.smsNotificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });

        activityAllNotificationSettingBinding.emailNotificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
//        Intent alertIntent = new Intent(mContext, ProfileSettingActivity.class);
//        startActivity(alertIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        whereToBack();
    }
}
