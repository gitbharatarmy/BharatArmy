package com.bharatarmy.Activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.bharatarmy.Models.LogginModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.ApiHandler;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityMobileVerificationBinding;

import java.util.HashMap;
import java.util.Map;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class MobileVerificationActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityMobileVerificationBinding mobileVerificationBinding;
    Context mContext;
    String phoneNoStr,countryCodeStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mobileVerificationBinding = DataBindingUtil.setContentView(this, R.layout.activity_mobile_verification);

        mContext = MobileVerificationActivity.this;
        setListiner();
    }

    public void setListiner(){
        mobileVerificationBinding.ccp.setCountryForNameCode(AppConfiguration.currentCountry);
//        mobileVerificationBinding.ccp.setCountryForPhoneCode(91);
        mobileVerificationBinding.phoneNoEdt.setText(Utils.getPref(mContext,"LoginPhoneNo"));

        mobileVerificationBinding.mobileVerifyBtn.setOnClickListener(this);
        mobileVerificationBinding.backImg.setOnClickListener(this );
        mobileVerificationBinding.mobileVerifyBtn.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId== EditorInfo.IME_ACTION_DONE){
                    getMobileverificationData();
                }


                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_img:
                if (AppConfiguration.wheretocomemobile.equalsIgnoreCase("splash")){
                    Intent a = new Intent(Intent.ACTION_MAIN);
                    a.addCategory(Intent.CATEGORY_HOME);
                    a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(a);
                    finish();
                }else {
                    Intent loginIntent = new Intent(mContext, LoginActivity.class);
                    startActivity(loginIntent);
//                overridePendingTransition(R.anim.slide_in_left,0);
                    finish();
                }
                break;
            case R.id.mobile_verify_btn:
               getMobileverificationData();
                break;
        }
    }

    public void getMobileverificationData(){
        phoneNoStr=mobileVerificationBinding.phoneNoEdt.getText().toString();
        countryCodeStr=mobileVerificationBinding.ccp.getSelectedCountryCode();

        if (phoneNoStr.equalsIgnoreCase("")){
            mobileVerificationBinding.phoneNoEdt.setError("Please Enter phone number");
        }else{
//            if (phoneNoStr.length()==10) {
                getOtpVerification();
//            }
        }
    }
    public void getOtpVerification() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), MobileVerificationActivity.this);
            return;
        }

        Utils.showDialog(mContext);
        ApiHandler.getApiService().getSendVerificationOTP(getOtpVerificationData(), new retrofit.Callback<LogginModel>() {
            @Override
            public void success(LogginModel loginModel, Response response) {
                Utils.dismissDialog();
                if (loginModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (loginModel.getIsValid() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (loginModel.getIsValid() == 0) {
                    Utils.ping(mContext,loginModel.getMessage());
                    return;
                }
                if (loginModel.getIsValid() == 1) {
                    Intent otpIntent=new Intent(mContext,OTPActivity.class);
                    otpIntent.putExtra("OTP",loginModel.getData().getOTP());
                    otpIntent.putExtra("OTPmobileno",phoneNoStr);
                    otpIntent.putExtra("countrycode",countryCodeStr);
                    otpIntent.putExtra("wheretocome","Login");
                    startActivity(otpIntent);
                    finish();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Utils.dismissDialog();
                error.printStackTrace();
                error.getMessage();
                Utils.ping(mContext, getString(R.string.something_wrong));
            }
        });

    }

    private Map<String, String> getOtpVerificationData() {
        Map<String, String> map = new HashMap<>();
        map.put("AppUserId", Utils.getPref(mContext,"AppUserId"));
        map.put("PhoneNo", phoneNoStr);
        map.put("CountryCode", countryCodeStr);
        return map;
    }






}
