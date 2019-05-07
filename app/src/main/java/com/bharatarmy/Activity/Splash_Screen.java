package com.bharatarmy.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.bharatarmy.Models.LogginModel;
import com.bharatarmy.Models.WalkthroughData;
import com.bharatarmy.R;
import com.bharatarmy.Utility.ApiHandler;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bumptech.glide.Glide;


import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class Splash_Screen extends AppCompatActivity  {


    //Splash screen timer
    private static int SPLASH_TIME_OUT = 5000;
    Context mContext;
ImageView imageView;

    String country;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash__screen);
        mContext = Splash_Screen.this;
        imageView=(ImageView)findViewById(R.id.imgLogo);
        Glide.with(mContext)
                .load(R.drawable.logo)
                .into(imageView);
        AppConfiguration.currentCountry="";
//        String Country_code= getApplicationContext().getResources().getConfiguration().locale.getISO3Country();
//        AppConfiguration.currentCountry = Country_code;// Country_code;
        TelephonyManager tm = (TelephonyManager)this.getSystemService(getApplicationContext().TELEPHONY_SERVICE);
        String countryCodeValue = tm.getNetworkCountryIso();
        AppConfiguration.currentCountry=countryCodeValue.toUpperCase();//countryCodeValue;
        Log.d("Country_code",countryCodeValue +"|"+AppConfiguration.currentCountry);


//        Log.d("Utils.Country_code",Utils.getUserCountry(mContext));

        /* User Id verification*/
        if (Utils.getPref(mContext, "AppUserId").equalsIgnoreCase("")) {
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    if (!Utils.getPref(mContext, "LoginUserName").equalsIgnoreCase("")) {
                        Intent i = new Intent(Splash_Screen.this, DashboardActivity.class);
                        startActivity(i);
                        finish();
                    } else {
                        Intent i = new Intent(Splash_Screen.this, TimerActivity.class);
                        startActivity(i);
                        finish();
                    }
                }
            }, SPLASH_TIME_OUT);
        } else {
            getLoginUserDetail();
        }

    }


    /*use for update user detail*/
    public void getLoginUserDetail() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), Splash_Screen.this);
            return;
        }

//        Utils.showDialog(mContext);
        ApiHandler.getApiService().getUserDetails(getLoginUserData(), new retrofit.Callback<LogginModel>() {
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
                    Utils.ping(mContext, loginModel.getMessage());
                    return;
                }
                if (loginModel.getIsValid() == 1) {
                    if (loginModel.getData() != null) {
                        Utils.setPref(mContext, "LoginUserName", loginModel.getData().getName());
                        Utils.setPref(mContext, "LoginEmailId", loginModel.getData().getEmail());
                        Utils.setPref(mContext, "LoginPhoneNo", loginModel.getData().getPhoneNo());
                        Utils.setPref(mContext, "LoginProfilePic", String.valueOf(loginModel.getData().getProfilePicUrl()));
                        Utils.setPref(mContext, "EmailVerified", String.valueOf(loginModel.getData().getIsEmailVerified()));
                        Utils.setPref(mContext, "PhoneVerified", String.valueOf(loginModel.getData().getIsNumberVerified()));
                        Utils.setPref(mContext, "AppUserId", String.valueOf(loginModel.getData().getId()));
                        Utils.setPref(mContext, "Gender", String.valueOf(loginModel.getData().getGender()));

                        /*  Mobile verification use */
                        if (Utils.getPref(mContext, "PhoneVerified").equalsIgnoreCase("0")) {
                            Intent DashboardIntent = new Intent(mContext, MobileVerificationNewActivity.class);
                            AppConfiguration.wheretocomemobile = "splash";
                            startActivity(DashboardIntent);
//                            overridePendingTransition(R.anim.slide_in_left,0);
                            finish();
                        } else {
                            Intent DashboardIntent = new Intent(mContext, DashboardActivity.class);
                            startActivity(DashboardIntent);
//                            overridePendingTransition(R.anim.slide_in_left,0);
                            finish();
                        }
                    }

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

    private Map<String, String> getLoginUserData() {
        Map<String, String> map = new HashMap<>();
        map.put("AppUserId", Utils.getPref(mContext, "AppUserId"));
        map.put("TokenId", Utils.getPref(mContext, "registration_id"));
        return map;
    }
}
