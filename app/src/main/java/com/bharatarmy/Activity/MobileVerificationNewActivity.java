package com.bharatarmy.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bharatarmy.Country;
import com.bharatarmy.Models.LogginModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.ApiHandler;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.CountryCodePicker;
import com.bharatarmy.databinding.ActivityMobileVerificationNewBinding;


import java.util.HashMap;
import java.util.Map;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class MobileVerificationNewActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityMobileVerificationNewBinding mobileVerificationNewBinding;
    Context mContext;
    String phoneNoStr, countryCodeStr, strCheck = "0";
    AlertDialog alertDialogAndroid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mobileVerificationNewBinding = DataBindingUtil.setContentView(this, R.layout.activity_mobile_verification_new);

        mContext = MobileVerificationNewActivity.this;
        init();
        setListiner();
    }

    public void init(){
        setmarginofservererrorTxtview();
    }

    public void setmarginofservererrorTxtview(){
        if (mobileVerificationNewBinding.serverErrorTxt.isShown()){
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 0, 0, 0);
            mobileVerificationNewBinding.mobileVerifyBtn.setLayoutParams(params);
        }else{
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, getResources().getDimensionPixelOffset(R.dimen.material_margin_top), 0, 0);
            mobileVerificationNewBinding.mobileVerifyBtn.setLayoutParams(params);
        }
    }

    public void setListiner() {
        mobileVerificationNewBinding.phoneNoEdt.setOnClickListener(this);
        mobileVerificationNewBinding.ccp.setCountryForNameCode(AppConfiguration.currentCountryISOCode);
        if (Utils.retriveLoginData(mContext) != null) {
            if (Utils.retriveLoginData(mContext).getPhoneNo() != null) {
                mobileVerificationNewBinding.phoneNoEdt.setText(Utils.retriveLoginData(mContext).getPhoneNo());
            }
        }
        mobileVerificationNewBinding.termConditionTxt.setOnClickListener(this);
        mobileVerificationNewBinding.mobileVerifyBtn.setOnClickListener(this);
        mobileVerificationNewBinding.backImg.setOnClickListener(this);
        mobileVerificationNewBinding.mobileVerifyBtn.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    getMobileverificationData();
                }
                return false;
            }
        });

        mobileVerificationNewBinding.ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected(Country selectedCountry) {
                AppConfiguration.currentCountryISOCode = mobileVerificationNewBinding.ccp.getSelectedCountryNameCode();
            }
        });

        mobileVerificationNewBinding.termsChk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    strCheck = "1";
                } else {
                    strCheck = "0";
                }
            }
        });

        mobileVerificationNewBinding.phoneNoEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    setmarginofservererrorTxtview();
                    mobileVerificationNewBinding.phoneNumberErrorTxt.setVisibility(View.GONE);
                } else if (s.toString().equalsIgnoreCase("")) {
                    setmarginofservererrorTxtview();
                    mobileVerificationNewBinding.phoneNumberErrorTxt.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                if (AppConfiguration.wheretocomemobile.equalsIgnoreCase("splash")) {
                    Intent a = new Intent(Intent.ACTION_MAIN);
                    a.addCategory(Intent.CATEGORY_HOME);
                    a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(a);
                    finish();
                } else {
                    Intent loginIntent = new Intent(mContext, LoginwithEmailActivity.class);
                    startActivity(loginIntent);
//                overridePendingTransition(R.anim.slide_in_left,0);
                    finish();
                }
                break;
            case R.id.mobile_verify_btn:
                Utils.handleClickEvent(mContext, mobileVerificationNewBinding.mobileVerifyBtn);
                getMobileverificationData();
                break;
            case R.id.term_condition_txt:
                Utils.handleClickEvent(mContext, mobileVerificationNewBinding.termConditionTxt);
                Intent privacypolicyIntent = new Intent(mContext, MoreInformationActivity.class);
                privacypolicyIntent.putExtra("Story Heading", "Privacy Policy");
                privacypolicyIntent.putExtra("StroyUrl", AppConfiguration.TERMSURL);
                privacypolicyIntent.putExtra("whereTocome", "aboutus");
                privacypolicyIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(privacypolicyIntent);
                break;
            case R.id.phone_no_edt:
                Utils.scrollScreen(mobileVerificationNewBinding.mobileverificationScrollView);
                break;
        }
    }


    public void getMobileverificationData() {
        phoneNoStr = mobileVerificationNewBinding.phoneNoEdt.getText().toString();
        countryCodeStr = mobileVerificationNewBinding.ccp.getSelectedCountryCode();
        if (countryCodeStr.length() > 0) {
            if (phoneNoStr.length() > 0) {
                if (Utils.isValidPhoneNumber(phoneNoStr)) {
//                    boolean status = Utils.validateNumber(mContext, countryCodeStr, phoneNoStr);
//                    if (status) {
                    if (!strCheck.equalsIgnoreCase("0")) {
                        getOtpVerification();
                    } else {
                        Utils.ping(mContext, getResources().getString(R.string.signup_privacy_error));
                    }
//                    } else {
//                        mobileVerificationNewBinding.phoneNoEdt.setError("Invalid Phone Number");
//                    }
                } else {
                    mobileVerificationNewBinding.phoneNumberErrorTxt.setVisibility(View.VISIBLE);
                    mobileVerificationNewBinding.phoneNumberErrorTxt.setText(getResources().getString(R.string.signup_phone_number_error));
                }
            } else {
                mobileVerificationNewBinding.phoneNumberErrorTxt.setVisibility(View.VISIBLE);
                mobileVerificationNewBinding.phoneNumberErrorTxt.setText(getResources().getString(R.string.signup_blankphone_number_error));
            }
        } else {
            Utils.ping(mContext, getResources().getString(R.string.signup_county_code_error));
        }

    }

    public void getOtpVerification() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), MobileVerificationNewActivity.this);
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
                    otpIntent.putExtra("OTPmobileno", phoneNoStr);
                    otpIntent.putExtra("countrycode", countryCodeStr);
                    otpIntent.putExtra("wheretocome", "Login");
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
        map.put("AppUserId", String.valueOf(Utils.getAppUserId(mContext)));
        map.put("PhoneNo", phoneNoStr);
        map.put("CountryCode", countryCodeStr);
        return map;
    }

}
