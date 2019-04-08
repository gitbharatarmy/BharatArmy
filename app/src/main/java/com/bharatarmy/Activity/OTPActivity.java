package com.bharatarmy.Activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.bharatarmy.Interfaces.SmsListener;
import com.bharatarmy.Models.LogginModel;
import com.bharatarmy.Models.OtpModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.ApiHandler;
import com.bharatarmy.Utility.SmsReceiver;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityOtpBinding;

import java.util.HashMap;
import java.util.Map;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class OTPActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityOtpBinding activityOtpBinding;
    Context mContext;
    String otpStr, edit1Str, edit2Str, edit3Str, edit4Str, finalgetOtpStr;
    String phoneNoStr, countryCodeStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityOtpBinding = DataBindingUtil.setContentView(this, R.layout.activity_otp);

        mContext = OTPActivity.this;
        setListiner();
    }

    public void setListiner() {

        otpStr = getIntent().getStringExtra("OTP");
        phoneNoStr=getIntent().getStringExtra("OTPmobileno");
        countryCodeStr=getIntent().getStringExtra("countrycode");

        activityOtpBinding.noTxt.setText("+"+countryCodeStr+"-"+phoneNoStr);

        SmsReceiver.bindListener(new SmsListener() {
            @Override
            public void messageReceived(String messageText) {
//                ed.setText(messageText);
            }
        });

        activityOtpBinding.edit1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Integer textlength1 = activityOtpBinding.edit1.getText().length();

                if (textlength1 >= 1) {
                    activityOtpBinding.edit2.requestFocus();
                } else {
                    activityOtpBinding.edit1.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        activityOtpBinding.edit2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Integer textlength1 = activityOtpBinding.edit2.getText().length();

                if (textlength1 == 1) {
                    activityOtpBinding.edit3.requestFocus();
                } else {
                    activityOtpBinding.edit1.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        activityOtpBinding.edit3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Integer textlength1 = activityOtpBinding.edit3.getText().length();

                if (textlength1 == 1) {
                    activityOtpBinding.edit4.requestFocus();
                } else {
                    activityOtpBinding.edit2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        activityOtpBinding.edit4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Integer textlength1 = activityOtpBinding.edit4.getText().length();

                if (textlength1 == 0) {
                    activityOtpBinding.edit3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        activityOtpBinding.otpImg.setOnClickListener(this);
        activityOtpBinding.otpImg.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    getOtpData();
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.otp_img:
                getOtpData();
                break;
            case R.id.back_img:
                Intent mobileIntent = new Intent(mContext, MobileVerificationActivity.class);
                startActivity(mobileIntent);
                overridePendingTransition(R.anim.slide_in_left, 0);
                finish();
                break;
        }
    }

    public void getOtpData() {

        finalgetOtpStr = activityOtpBinding.edit1.getText().toString() +
                activityOtpBinding.edit2.getText().toString() +
                activityOtpBinding.edit3.getText().toString() +
                activityOtpBinding.edit4.getText().toString();
        Log.d("finalOtpStr", finalgetOtpStr);
        Log.d("otpStr", otpStr);

        if (otpStr.equalsIgnoreCase(finalgetOtpStr)) {
            VerificationPhone();

        } else {
            Utils.ping(mContext, "Please Enter Valid OTP");
        }

    }

    public void VerificationPhone() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), OTPActivity.this);
            return;
        }

        Utils.showDialog(mContext);
        ApiHandler.getApiService().getVerifiedPhoneNo(getphoneVerificationData(), new retrofit.Callback<OtpModel>() {
            @Override
            public void success(OtpModel loginModel, Response response) {
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
                    Utils.ping(mContext, String.valueOf(loginModel.getMessage()));
                    return;
                }
//                9574252404
                if (loginModel.getIsValid() == 1) {
                    Intent DashboardIntent = new Intent(mContext, DashboardActivity.class);
                    startActivity(DashboardIntent);
                    overridePendingTransition(0, 0);
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

    private Map<String, String> getphoneVerificationData() {
        Map<String, String> map = new HashMap<>();
        map.put("AppUserId", Utils.getPref(mContext, "AppUserId"));
        map.put("PhoneNo", phoneNoStr);
        map.put("CountryCode", countryCodeStr);
        return map;
    }
//       try
//    {
//        InputMethodManager imm=
//                (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
//    }
//            catch(Exception e)
//    {}
//            if (ed.getText().toString().equals(otp_generated))
//    {
//        Toast.makeText(OtpVerificationActivity.this, "OTP Verified
//                Successfully !", Toast.LENGTH_SHORT).show();
//    }
}
