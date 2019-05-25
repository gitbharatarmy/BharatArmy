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
import com.bharatarmy.Utility.AppConfiguration;
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
    String otpStr, finalgetOtpStr, strWheretocome, strFullName, strEmail, strCountrycode, strMobileno, strPassword, strCheck;
    String phoneNoStr, countryCodeStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityOtpBinding = DataBindingUtil.setContentView(this, R.layout.activity_otp);

        mContext = OTPActivity.this;
        getIntentValue();
        setListiner();
    }

    public void getIntentValue() {
        otpStr = getIntent().getStringExtra("OTP");
        phoneNoStr = getIntent().getStringExtra("OTPmobileno");
        countryCodeStr = getIntent().getStringExtra("countrycode");
        strWheretocome = getIntent().getStringExtra("wheretocome");

        if (strWheretocome.equalsIgnoreCase("Signup")) {
            strFullName = getIntent().getStringExtra("signupFullname");
            strEmail = getIntent().getStringExtra("signupEmail");
            strCountrycode = getIntent().getStringExtra("signupCountryCode");
            strMobileno = getIntent().getStringExtra("signupMobileno");
            strPassword = getIntent().getStringExtra("signupPassword");
            strCheck = getIntent().getStringExtra("signupCheck");
        }

    }

    public void setListiner() {


        activityOtpBinding.noTxt.setText("+" + strCountrycode + "-" + strMobileno);

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
                activityOtpBinding.edit1.setBackgroundResource(R.drawable.rectangle_line);
                if (textlength1 >= 1) {
                    activityOtpBinding.edit1.setBackgroundResource(R.drawable.fill_rectangle_line);
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
                activityOtpBinding.edit2.setBackgroundResource(R.drawable.rectangle_line);
                if (textlength1 == 1) {
                    activityOtpBinding.edit2.setBackgroundResource(R.drawable.fill_rectangle_line);
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
                activityOtpBinding.edit3.setBackgroundResource(R.drawable.rectangle_line);
                if (textlength1 == 1) {
                    activityOtpBinding.edit3.setBackgroundResource(R.drawable.fill_rectangle_line);
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
                activityOtpBinding.edit4.setBackgroundResource(R.drawable.fill_rectangle_line);
                if (textlength1 == 0) {
                    activityOtpBinding.edit4.setBackgroundResource(R.drawable.rectangle_line);
                    activityOtpBinding.edit3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        activityOtpBinding.otpImg.setOnClickListener(this);
        activityOtpBinding.backLinear.setOnClickListener(this);
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
            case R.id.back_linear:
                if (strWheretocome.equalsIgnoreCase("Signup")) {
                    Intent mobileIntent = new Intent(mContext, SignUpActivity.class);
                    mobileIntent.putExtra("wheretocome", "OTP");
                    mobileIntent.putExtra("signupFullname", strFullName);
                    mobileIntent.putExtra("signupEmail", strEmail);
                    mobileIntent.putExtra("signupCountryCode", strCountrycode);
                    mobileIntent.putExtra("signupMobileno", strMobileno);
                    mobileIntent.putExtra("signupPassword", strPassword);
                    mobileIntent.putExtra("signupCheck", strCheck);
                    startActivity(mobileIntent);
                    overridePendingTransition(0, 0);
//                    finish();
                } else {
                    Intent mobileIntent = new Intent(mContext, MobileVerificationNewActivity.class);
                    startActivity(mobileIntent);
                    overridePendingTransition(0, 0);
//                    finish();
                }
                break;
        }
    }

    public void getOtpData() {

        finalgetOtpStr = activityOtpBinding.edit1.getText().toString() +
                activityOtpBinding.edit2.getText().toString() +
                activityOtpBinding.edit3.getText().toString() +
                activityOtpBinding.edit4.getText().toString();
        Log.d("finalOtpStr", finalgetOtpStr);
//        Log.d("otpStr", otpStr);

        if (otpStr.equalsIgnoreCase(finalgetOtpStr)) {
            if (strWheretocome.equalsIgnoreCase("Signup")) {
                getSignUp();
            } else {
                VerificationPhone();
            }
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

    public void getSignUp() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), OTPActivity.this);
            return;
        }

        Utils.showDialog(mContext);
        ApiHandler.getApiService().getSignup(getSignUpData(), new retrofit.Callback<LogginModel>() {
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

                        Intent DashboardIntent = new Intent(mContext, DashboardActivity.class);
                        AppConfiguration.position = 0;
                        startActivity(DashboardIntent);
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

    private Map<String, String> getSignUpData() {
        Map<String, String> map = new HashMap<>();
        map.put("FullName", strFullName);
        map.put("Email", strEmail);
        map.put("Code", strCountrycode);
        map.put("PhoneNo", strMobileno);
        map.put("Password", strPassword);
        return map;
    }
}
