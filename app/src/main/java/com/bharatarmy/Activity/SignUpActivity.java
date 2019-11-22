package com.bharatarmy.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bharatarmy.Country;
import com.bharatarmy.CountryCodePicker;
import com.bharatarmy.Models.LogginModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.ApiHandler;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.meghWebView;
import com.bharatarmy.databinding.ActivitySignUpBinding;
import com.bumptech.glide.Glide;

import java.util.HashMap;
import java.util.Map;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    // dhaval sir nu 9574252404
    ActivitySignUpBinding activitySignUpBinding;
    Context mContext;
    String strFirstName,strLastName,strEmail, strCountrycode, strMobileno, strPassword, strCheck = "0",
            strbckFirstName,strbckLastName, strbckEmail, strbckCountrycode, strbckMobileno, strbckPassword, strbckCheck;
    AlertDialog alertDialogAndroid;
    Button agree_btn;
    meghWebView webView;
    TextView close_btn;
    ImageView image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySignUpBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);

        mContext = SignUpActivity.this;
        init();
        setListiner();
    }

    public void init() {
        if (AppConfiguration.currentCountryISOCode!=null){
            if (!AppConfiguration.currentCountryISOCode.equalsIgnoreCase("")){
                activitySignUpBinding.ccp.setCountryForNameCode(AppConfiguration.currentCountryISOCode);
            }
        }

        if (getIntent().getStringExtra("wheretocome") != null) {
            if (getIntent().getStringExtra("wheretocome").equalsIgnoreCase("OTP")) {
                strbckFirstName = getIntent().getStringExtra("signupFirstname");
                strbckLastName =getIntent().getStringExtra("signupLastname") ;
                strbckEmail = getIntent().getStringExtra("signupEmail");
                strbckCountrycode = getIntent().getStringExtra("signupCountryCode");
                strbckMobileno = getIntent().getStringExtra("signupMobileno");
                strbckPassword = getIntent().getStringExtra("signupPassword");
                strbckCheck = getIntent().getStringExtra("signupCheck");


                activitySignUpBinding.firstNameEdt.setText(strbckFirstName);
                activitySignUpBinding.lastNameEdt.setText(strbckLastName);
                activitySignUpBinding.emailEdt.setText(strbckEmail);
                activitySignUpBinding.ccp.setCountryForNameCode(strbckCountrycode);
                activitySignUpBinding.mobileEdt.setText(strbckMobileno);
                activitySignUpBinding.userPasswordEdt.setText(strbckPassword);

                if (strbckCheck.equalsIgnoreCase("1")) {
                    strCheck = "1";
                    activitySignUpBinding.termsChk.setChecked(true);
                }
            }
        }
    }


    // set the All Listiner and Data
    public void setListiner() {
        activitySignUpBinding.termConditionTxt.setOnClickListener(this);
        activitySignUpBinding.signupBtn.setOnClickListener(this);
        activitySignUpBinding.closeTxt.setOnClickListener(this);
        activitySignUpBinding.firstNameEdt.setOnClickListener(this);

        activitySignUpBinding.ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected(Country selectedCountry) {
                AppConfiguration.currentCountryISOCode = activitySignUpBinding.ccp.getSelectedCountryNameCode();
            }
        });

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

        activitySignUpBinding.userPasswordEdt.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT){
                    Utils.hideKeyboard(SignUpActivity.this);
                }
                return false;
            }
        });

    }

    // get the data user fill for singup
    public void getDataValue() {
        strFirstName = activitySignUpBinding.firstNameEdt.getText().toString();
        strLastName=activitySignUpBinding.lastNameEdt.getText().toString();
        strEmail = activitySignUpBinding.emailEdt.getText().toString();
        strCountrycode = activitySignUpBinding.ccp.getSelectedCountryCode();
        strMobileno = activitySignUpBinding.mobileEdt.getText().toString();
        strPassword = activitySignUpBinding.userPasswordEdt.getText().toString();
        AppConfiguration.currentCountryISOCode = activitySignUpBinding.ccp.getSelectedCountryNameCode();

        Log.d("selectedcode", strCountrycode);

        if (!strFirstName.equalsIgnoreCase("")) {
            if(!strLastName.equalsIgnoreCase("")) {
                if (!strEmail.equalsIgnoreCase("")) {
                    if (Utils.isValidEmailId(strEmail)) {
                        if (strCountrycode.length() > 0) {
                            if (strMobileno.length() > 0) {
                                if (Utils.isValidPhoneNumber(strMobileno)) {
//                                    boolean status = Utils.validateUsing_libphonenumber(mContext, strCountrycode, strMobileno);
//                                if (status) {
                                    if (!strPassword.equalsIgnoreCase("")) {
                                        if (strPassword.length() >= 5 && strPassword.length() <= 10) {
                                            if (!strCheck.equalsIgnoreCase("0")) {
                                                getOtpVerification();
                                            } else {
                                                Utils.ping(mContext, "Check the privacy policy");
                                            }

                                        } else {
                                            activitySignUpBinding.userPasswordEdt.setError("Password Length must be greter than 5 or less than 10");
                                        }
                                    } else {
                                        activitySignUpBinding.userPasswordEdt.setError("Password is required");
                                    }
//                                } else {
//                                    activitySignUpBinding.mobileEdt.setError("Invalid Phone Number");
//                                }
                                } else {
                                    activitySignUpBinding.mobileEdt.setError("Invalid Phone Number");
                                }
                            } else {
                                activitySignUpBinding.mobileEdt.setError("Phone Number is required");
                            }
                        } else {
                            Utils.ping(mContext, "Country Code is required");
                        }
                    } else {
                        activitySignUpBinding.emailEdt.setError("Invalid Email Address");
                    }
                } else {
                    activitySignUpBinding.emailEdt.setError("Email Address is required");
                }
            }else{
                activitySignUpBinding.lastNameEdt.setError("Last Name is required");
            }
        } else {
            activitySignUpBinding.firstNameEdt.setError("First Name is required");
        }


    }

    @Override

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.term_condition_txt:
                Utils.handleClickEvent(mContext, activitySignUpBinding.termConditionTxt);
                termconditionDialog();
                break;
            case R.id.signup_btn:
                Utils.handleClickEvent(mContext, activitySignUpBinding.signupBtn);
                getDataValue();
                break;
            case R.id.close_txt:
                   whereToBack();
                break;
            case R.id.fulluser_name_edt:
                Utils.scrollScreen(activitySignUpBinding.signupScrollView);
                break;
        }
    }


    // call the Otp Verification service and get the otp
    public void getOtpVerification() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), SignUpActivity.this);
            return;
        }

        Utils.showDialog(mContext);
        ApiHandler.getApiService().getValidatedBAMember(getOtpVerificationData(), new retrofit.Callback<LogginModel>() {
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
                    otpIntent.putExtra("signupFirstname", strFirstName);
                    otpIntent.putExtra("signupLastname",strLastName);
                    otpIntent.putExtra("signupEmail", strEmail);
                    otpIntent.putExtra("signupCountryCode", strCountrycode);
                    otpIntent.putExtra("signupMobileno", strMobileno);
                    otpIntent.putExtra("signupPassword", strPassword);
                    otpIntent.putExtra("signupCheck", strCheck);
                    otpIntent.putExtra("whereTocomeLogin", getIntent().getStringExtra("whereTocomeLogin"));
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
        map.put("AppUserId",String.valueOf(Utils.getAppUserId(mContext)));
        map.put("Email", strEmail);
        map.put("PhoneNo", strMobileno);
        map.put("CountryPhoneNo", strCountrycode);
        return map;
    }

    // use for show the terms & condition
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
        Glide.with(mContext).load(R.drawable.logo_new).into(image);
        image.setVisibility(View.VISIBLE);

        webView.setWebViewClient(new MyWebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(AppConfiguration.TERMSURL);
        webView.setVerticalScrollBarEnabled(true);
        webView.setOnClickListener(this);

        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogAndroid.dismiss();
            }
        });
    }

    // use for webview adavnce facility funcation
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


    @Override
    public void onBackPressed() {
        whereToBack();
        super.onBackPressed();
    }

    public void whereToBack(){
        Intent iLogin = new Intent(mContext, LoginwithEmailActivity.class);
        iLogin.putExtra("whereTocomeLogin",getIntent().getStringExtra("whereTocomeLogin"));
        startActivity(iLogin);
        finish();
    }
}

