package com.bharatarmy.Activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.bharatarmy.R;
import com.bharatarmy.databinding.ActivityMobileVerificationBinding;

public class MobileVerificationActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityMobileVerificationBinding mobileVerificationBinding;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mobileVerificationBinding = DataBindingUtil.setContentView(this, R.layout.activity_mobile_verification);

        mContext = MobileVerificationActivity.this;
        setListiner();
    }

    public void setListiner(){
        mobileVerificationBinding.mobileVerifyBtn.setOnClickListener(this);
        mobileVerificationBinding.backImg.setOnClickListener(this );
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_img:
                Intent loginIntent = new Intent(mContext,LoginActivity.class);
                startActivity(loginIntent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                finish();
                break;
            case R.id.mobile_verify_btn:
                Intent otpIntent = new Intent(mContext,OTPActivity.class);
                startActivity(otpIntent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                finish();
                break;
        }
    }
}
