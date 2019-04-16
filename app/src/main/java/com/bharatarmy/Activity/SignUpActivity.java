package com.bharatarmy.Activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;

import com.bharatarmy.Models.LogginModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.ApiHandler;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivitySignUpBinding;

import java.util.HashMap;
import java.util.Map;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    ActivitySignUpBinding activitySignUpBinding;
    Context mContext;
    String strFullName, strEmail, strCountrycode, strMobileno, strPassword, strCheck = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySignUpBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);


        mContext = SignUpActivity.this;
        setListiner();

    }

    public void setListiner() {
        activitySignUpBinding.ccp.setCountryForNameCode(AppConfiguration.currentCountry);
        activitySignUpBinding.termConditionTxt.setOnClickListener(this);
        activitySignUpBinding.signupBtn.setOnClickListener(this);
        activitySignUpBinding.closeTxt.setOnClickListener(this);

        activitySignUpBinding.termsChk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    strCheck = "1";
                } else {
                    strCheck = "0";
                }
            }
        });
    }

    public void getDataValue() {
        strFullName = activitySignUpBinding.fulluserNameEdt.getText().toString();
        strEmail = activitySignUpBinding.emailEdt.getText().toString();
        strCountrycode = activitySignUpBinding.ccp.getSelectedCountryCode();
        strMobileno = activitySignUpBinding.mobileEdt.getText().toString();
        strPassword = activitySignUpBinding.userPasswordEdt.getText().toString();

        Log.d("selectedcode",strCountrycode);

        if (!strFullName.equalsIgnoreCase("")) {
            if (!strEmail.equalsIgnoreCase("")) {
                if (Utils.isValidEmailId(strEmail)) {
                    if (!strMobileno.equalsIgnoreCase("")) {
//                        if (strMobileno.length()==10) {
                        if (!strPassword.equalsIgnoreCase("")) {
                            if (!strCheck.equalsIgnoreCase("0")) {
                                getOtpVerification();
                            } else {
                                Utils.ping(mContext, "Please accept terms & conditions");
                            }
                        } else {
                            activitySignUpBinding.userPasswordEdt.setError("Please enter password");
                        }
//                        }else{
//                            activitySignUpBinding.mobileEdt.setError("Please enter 10 digit mobile no");
//                        }
                    } else {
                        activitySignUpBinding.mobileEdt.setError("Please enter mobile no");
                    }
                } else {
                    activitySignUpBinding.emailEdt.setError("Please enter valid email address");
                }
            } else {
                activitySignUpBinding.emailEdt.setError("Please enter email address");
            }
        } else {
            activitySignUpBinding.fulluserNameEdt.setError("Please enter fullname");
        }


    }

    @Override

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.term_condition_txt:
                Intent webviewIntent = new Intent(mContext, TermConditionActivity.class);
                webviewIntent.putExtra("Story Heading", "Terms & Conditions");
                webviewIntent.putExtra("StroyUrl", "https://www.bharatarmy.com/legal/termsofuse");
                webviewIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(webviewIntent);
                break;
            case R.id.signup_btn:
                getDataValue();
                break;
            case R.id.close_txt:
                Intent iLogin=new Intent(mContext,LoginActivity.class);
                startActivity(iLogin);
                finish();
                break;
        }
    }

    public void getOtpVerification() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), SignUpActivity.this);
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
                    Utils.ping(mContext, loginModel.getMessage());
                    return;
                }
                if (loginModel.getIsValid() == 1) {
                    Intent otpIntent = new Intent(mContext, OTPActivity.class);
                    otpIntent.putExtra("OTP", loginModel.getData().getOTP());
                    otpIntent.putExtra("OTPmobileno", strMobileno);
                    otpIntent.putExtra("countrycode", strCountrycode);
                    otpIntent.putExtra("wheretocome", "Signup");
                    otpIntent.putExtra("signupFullname", strFullName);
                    otpIntent.putExtra("signupEmail", strEmail);
                    otpIntent.putExtra("signupCountryCode", strCountrycode);
                    otpIntent.putExtra("signupMobileno", strMobileno);
                    otpIntent.putExtra("signupPassword", strPassword);
                    otpIntent.putExtra("signupCheck", strCheck);
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
        map.put("AppUserId", "0");
        map.put("PhoneNo", strMobileno);
        map.put("CountryCode", strCountrycode);
        return map;
    }

}
