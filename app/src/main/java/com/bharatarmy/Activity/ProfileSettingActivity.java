package com.bharatarmy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bharatarmy.R;
import com.bharatarmy.databinding.ActivityProfileSettingBinding;
import com.google.android.gms.common.data.DataBufferIterator;

public class ProfileSettingActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityProfileSettingBinding activityProfileSettingBinding;
    Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityProfileSettingBinding = DataBindingUtil.setContentView(this, R.layout.activity_profile_setting);

        mContext=ProfileSettingActivity.this;

        init();
        setLisitner();

    }

    public void init(){}

    public void setLisitner(){
        activityProfileSettingBinding.changePasswordRel.setOnClickListener(this);
        activityProfileSettingBinding.notificationRel.setOnClickListener(this);
        activityProfileSettingBinding.backImg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.change_password_rel:
                Intent alertIntent =new Intent(mContext,ProfileChangePasswordAlertActivity.class);
                startActivity(alertIntent);
                finish();
                break;
            case R.id.notification_rel:
                Intent notificationalertIntent =new Intent(mContext,AllNotificationActivity.class);
                startActivity(notificationalertIntent);
                finish();
                break;
            case R.id.back_img:
                finish();
                break;
        }
    }
}
