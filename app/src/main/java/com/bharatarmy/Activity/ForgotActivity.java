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
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityForgotBinding;

import java.util.HashMap;
import java.util.Map;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class ForgotActivity extends AppCompatActivity {

    ActivityForgotBinding activityForgotBinding;
    Context mContext;
    String emailStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityForgotBinding= DataBindingUtil.setContentView(this,R.layout.activity_forgot);

        mContext=ForgotActivity.this;

        init();
        setListiner();
    }

    public void init(){}

    public void setListiner(){
        activityForgotBinding.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                emailStr=activityForgotBinding.forgotPasswordemailEdt.getText().toString();
//                getForgotPassword();
                Intent otpIntent=new Intent(mContext,ForgotPasswordOtpActivity.class);
                startActivity(otpIntent);
                finish();
            }
        });

        activityForgotBinding.backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    public void getForgotPassword() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), ForgotActivity.this);
            return;
        }

        Utils.showDialog(mContext);
        ApiHandler.getApiService().getLogin(getForgotData(), new retrofit.Callback<LogginModel>() {
            @Override
            public void success(LogginModel forgotModel, Response response) {
                Utils.dismissDialog();

                if (forgotModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (forgotModel.getIsValid() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (forgotModel.getIsValid() == 0) {
                    Utils.ping(mContext, forgotModel.getMessage());
                    return;
                }
                if (forgotModel.getIsValid() == 1) {
                    if (forgotModel.getData() != null) {
                        Intent otpIntent=new Intent(mContext,ForgotPasswordOtpActivity.class);
                        startActivity(otpIntent);
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

    private Map<String, String> getForgotData() {
        Map<String, String> map = new HashMap<>();
        map.put("Email", emailStr);
        map.put("TokenId", Utils.getPref(mContext, "registration_id"));
        return map;
    }
}
