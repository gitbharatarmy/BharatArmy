package com.bharatarmy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bharatarmy.Models.LogginModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.ApiHandler;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityProfileChangePasswordAlertBinding;

import java.util.HashMap;
import java.util.Map;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class ProfileChangePasswordAlertActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityProfileChangePasswordAlertBinding activityProfileChangePasswordAlertBinding;
    Context mContext;
    String emailStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityProfileChangePasswordAlertBinding = DataBindingUtil.setContentView(this, R.layout.activity_profile_change_password_alert);

        mContext = ProfileChangePasswordAlertActivity.this;

        init();
        setListiner();

    }

    public void init() {
        if (Utils.retriveLoginData(mContext)!=null){
            emailStr=Utils.retriveLoginData(mContext).getEmail();
        }
    }

    public void setListiner() {
        activityProfileChangePasswordAlertBinding.yesBtn.setOnClickListener(this);
        activityProfileChangePasswordAlertBinding.noBtn.setOnClickListener(this);
        activityProfileChangePasswordAlertBinding.backImg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.yes_btn:
                if (!emailStr.equalsIgnoreCase("")){
                    callChangePasswordOtp();
                }

                break;
            case R.id.no_btn:
                whereToBack();
                break;
            case R.id.back_img:
                whereToBack();
                break;
        }
    }

    public void whereToBack() {
        Intent alertIntent = new Intent(mContext, ProfileSettingActivity.class);
        alertIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(alertIntent);
        finish();
    }


    public void callChangePasswordOtp() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), ProfileChangePasswordAlertActivity.this);
            return;
        }

        Utils.showDialog(mContext);
        ApiHandler.getApiService().getForgotPassword(getChangePasswordOtpData(), new retrofit.Callback<LogginModel>() {
            @Override
            public void success(LogginModel changepasswordotpModel, Response response) {
                Utils.dismissDialog();

                if (changepasswordotpModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (changepasswordotpModel.getIsValid() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (changepasswordotpModel.getIsValid() == 0) {
                    Utils.ping(mContext, changepasswordotpModel.getMessage());
                    return;
                }
                if (changepasswordotpModel.getIsValid() == 1) {
                    if (changepasswordotpModel.getData() != null) {
                        Intent profileotpIntent = new Intent(mContext, ProfileChangePasswordOtpActivity.class);
                        profileotpIntent.putExtra("otpStr",changepasswordotpModel.getData().getOTP());
                        profileotpIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(profileotpIntent);
                        finish();
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

    private Map<String, String> getChangePasswordOtpData() {
        Map<String, String> map = new HashMap<>();
        map.put("EmailId", emailStr);
        map.put("MemberId", String.valueOf(Utils.getAppUserId(mContext)));
        return map;
    }

    public void whereToGo() {
        Intent profileotpIntent = new Intent(mContext, ProfileChangePasswordOtpActivity.class);
        profileotpIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(profileotpIntent);
        finish();
    }


    @Override
    public void onBackPressed() {
        whereToBack();
        super.onBackPressed();
    }
}
