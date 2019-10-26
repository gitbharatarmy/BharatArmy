package com.bharatarmy.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
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
import com.bharatarmy.meghWebView;
import com.bharatarmy.CountryCodePicker;
import com.bharatarmy.databinding.ActivityMobileVerificationNewBinding;
import com.bumptech.glide.Glide;


import java.util.HashMap;
import java.util.Map;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class MobileVerificationNewActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityMobileVerificationNewBinding mobileVerificationNewBinding;
    Context mContext;
    String phoneNoStr, countryCodeStr, strCheck = "0";
    AlertDialog alertDialogAndroid;
    meghWebView webView;
    //     WebView webView;
    Button agree_btn;
    TextView close_btn;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mobileVerificationNewBinding = DataBindingUtil.setContentView(this, R.layout.activity_mobile_verification_new);

        mContext = MobileVerificationNewActivity.this;
        setListiner();
    }

    public void setListiner() {
        mobileVerificationNewBinding.ccp.setCountryForNameCode(AppConfiguration.currentCountry);
        mobileVerificationNewBinding.phoneNoEdt.setText(Utils.getPref(mContext, "LoginPhoneNo"));
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
                mobileVerificationNewBinding.codeTxt.setText("+" + selectedCountry.getPhoneCode());
                        AppConfiguration.currentCountry=mobileVerificationNewBinding.ccp.getSelectedCountryNameCode();
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
                    Intent loginIntent = new Intent(mContext, LoginActivity.class);
                    startActivity(loginIntent);
//                overridePendingTransition(R.anim.slide_in_left,0);
                    finish();
                }
                break;
            case R.id.mobile_verify_btn:
                Utils.handleClickEvent(mContext,mobileVerificationNewBinding.mobileVerifyBtn);
                getMobileverificationData();
                break;
            case R.id.term_condition_txt:
                Utils.handleClickEvent(mContext,mobileVerificationNewBinding.termConditionTxt);
                termconditionDialog();
                break;
        }
    }


    public void getMobileverificationData() {
        phoneNoStr = mobileVerificationNewBinding.phoneNoEdt.getText().toString();
        countryCodeStr = mobileVerificationNewBinding.codeTxt.getText().toString();
        if (countryCodeStr.length() > 0) {
            if (phoneNoStr.length() > 0) {
                if (Utils.isValidPhoneNumber(phoneNoStr)) {
//                    boolean status = Utils.validateNumber(mContext, countryCodeStr, phoneNoStr);
//                    if (status) {
                        if (!strCheck.equalsIgnoreCase("0")) {
                             getOtpVerification ();
                        } else {
                            Utils.ping(mContext, "Please accept the privacy policy.");
                        }
//                    } else {
//                        mobileVerificationNewBinding.phoneNoEdt.setError("Invalid Phone Number");
//                    }
                } else {
                    mobileVerificationNewBinding.phoneNoEdt.setError("Invalid Phone Number");
                }
            } else {
                mobileVerificationNewBinding.phoneNoEdt.setError("Phone Number is required");
            }
        } else {
            mobileVerificationNewBinding.codeTxt.setError("Country Code is required");
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


    public void termconditionDialog() {
        LayoutInflater lInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = lInflater.inflate(R.layout.mobile_term_condition, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(mContext);
        alertDialogBuilderUserInput.setView(layout);

        alertDialogAndroid = alertDialogBuilderUserInput.create();
        alertDialogAndroid.setCancelable(false);
        alertDialogAndroid.show();
        Window window = alertDialogAndroid.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        WindowManager.LayoutParams wlp = window.getAttributes();
        window.setGravity(Gravity.LEFT | Gravity.TOP);
        wlp.x = 1;
        wlp.y = 100;
        wlp.flags = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        window.setAttributes(wlp);
        alertDialogAndroid.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialogAndroid.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);

        Drawable d = new ColorDrawable(getResources().getColor(R.color.black_dialog));
//        d.setAlpha(100);
        alertDialogAndroid.getWindow().setBackgroundDrawable(d);
        alertDialogAndroid.show();

        webView = (meghWebView) layout.findViewById(R.id.webView);
        image = (ImageView) layout.findViewById(R.id.image);
        agree_btn = (Button) layout.findViewById(R.id.agree_btn);
//        close_btn = (Button) layout.findViewById(R.id.close_btn);
        close_btn = (TextView) layout.findViewById(R.id.close_btn1);
        Glide.with(mContext).load(R.drawable.logo).into(image);
        image.setVisibility(View.VISIBLE);

        webView.setWebViewClient(new MyWebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(AppConfiguration.TERMSURL);
        webView.setVerticalScrollBarEnabled(true);
        webView.setOnClickListener(this);


//        agree_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getOtpVerification();
//            }
//        });
        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogAndroid.dismiss();
            }
        });
    }

    public class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            image.setVisibility(View.VISIBLE);
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);
            image.setVisibility(View.GONE);
        }
    }


}
