package com.bharatarmy.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bharatarmy.Models.LogginModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.ApiHandler;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;

import com.bharatarmy.databinding.ActivityLoginwithemailBinding;


import java.util.HashMap;
import java.util.Map;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class LoginwithEmailActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityLoginwithemailBinding loginBinding;
    Context mContext;
    String username_str, password_str;
    public String isUpdateAvailable, isForceUpdateAvailable, currentVersionStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding = DataBindingUtil.setContentView(this, R.layout.activity_loginwithemail);
        mContext = LoginwithEmailActivity.this;
        setListner();

    }

    public void setListner() {
        loginBinding.logginBtn.setOnClickListener(this);
        loginBinding.signUpTxt.setOnClickListener(this);
        loginBinding.forgotTxt.setOnClickListener(this);
        loginBinding.backImg.setOnClickListener(this);


        loginBinding.userPasswordEdt.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    verifyLoginDetails();
                }
                return false;
            }
        });
        loginBinding.userPasswordEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                password_str = loginBinding.userPasswordEdt.getText().toString();

                if (!password_str.equalsIgnoreCase("")) {

                }
            }
        });
    }

    public void verifyLoginDetails() {
        username_str = loginBinding.userNameEdt.getText().toString();
        password_str = loginBinding.userPasswordEdt.getText().toString();

        if (!username_str.equalsIgnoreCase("") & !password_str.equalsIgnoreCase("")) {
            if (Utils.isValidEmailId(username_str)) {
                if (password_str.length() >= 5 && password_str.length() <= 10) {
                    getLogin();
                } else {
                    loginBinding.userPasswordEdt.setError("Password Length must be greter than 5 or less than 10");
                }
            } else {
                loginBinding.userNameEdt.setError("Please enter valid email address");
            }

        } else {
            loginBinding.userNameEdt.setError("Blank field not allowed");
            loginBinding.userPasswordEdt.setError("Blank field not allowed");
        }

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_up_txt:
                Utils.handleClickEvent(mContext, loginBinding.signUpTxt);
                Intent signupIntent = new Intent(mContext, SignUpActivity.class);
//                signupIntent.putExtra("whereTocomeLogin", getIntent().getStringExtra("whereTocomeLogin"));
                startActivity(signupIntent);
//                finish();
                break;
            case R.id.loggin_btn:
//                Utils.handleClickEvent(mContext, loginBinding.logginBtn);
                verifyLoginDetails();
                break;
            case R.id.forgot_txt:
                Utils.handleClickEvent(mContext, loginBinding.forgotTxt);
                Intent forgotIntent = new Intent(mContext, ForgotActivity.class);
//                forgotIntent.putExtra("whereTocomeLogin", getIntent().getStringExtra("whereTocomeLogin"));
                startActivity(forgotIntent);
//                finish();
                break;
            case R.id.back_img:
                whereToBack();
                break;
            case R.id.user_name_edt:
                Utils.scrollScreen(loginBinding.scrollView);
                break;
        }
    }

    public void getLogin() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), LoginwithEmailActivity.this);
            return;
        }

        Utils.showDialog(mContext);
        ApiHandler.getApiService().getLogin(getLoginData(), new retrofit.Callback<LogginModel>() {
            @Override
            public void success(LogginModel loginModel, Response response) {
//                Utils.dismissDialog();

                if (loginModel == null) {
                    Utils.dismissDialog();
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (loginModel.getIsValid() == null) {
                    Utils.dismissDialog();
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (loginModel.getIsValid() == 0) {
                    Utils.dismissDialog();
                    Utils.ping(mContext, loginModel.getMessage());
                    return;
                }
                if (loginModel.getIsValid() == 1) {
                    isUpdateAvailable = String.valueOf(loginModel.getIsUpdateAvailable());
                    isForceUpdateAvailable = String.valueOf(loginModel.getIsForceUpdate());
//                    isForceUpdateAvailable = "0";
                    currentVersionStr = String.valueOf(loginModel.getCurrentVersion());
                    if (isUpdateAvailable.equalsIgnoreCase("1")) {
                        Utils.checkupdateApplication(mContext, LoginwithEmailActivity.this, isForceUpdateAvailable, currentVersionStr);
                    }
                    if (loginModel.getData() != null) {
                        Utils.setPref(mContext, "IsSkipLogin", "");
                        Utils.setPref(mContext, "IsLoginUser", "1");
                        Utils.storeLoginData(loginModel.getData(), mContext);
                        Utils.storeCurrentLocationData(loginModel.getCurrentLocation(),mContext);
                        Utils.storeLoginOtherData(loginModel.getOtherData(), mContext);

                        if (loginModel.getData().getIsNumberVerified() == 0) {
                            Intent otpIntent = new Intent(mContext, MobileVerificationNewActivity.class);
                            AppConfiguration.wheretocomemobile = "Login";
                            startActivity(otpIntent);
                            finish();
                        } else {
                            Utils.setPref(mContext, "LoginType", "Email");
                            goToAfterLogin();
                           Utils.dismissDialog();
                        }
                    }else{
                        Utils.dismissDialog();
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

    private Map<String, String> getLoginData() {
        Map<String, String> map = new HashMap<>();
        map.put("Email", username_str);
        map.put("Password", password_str);
        map.put("TokenId", Utils.getPref(mContext, "registration_id"));
        map.put("ModelName",Utils.getDeviceName());
        return map;
    }

    @Override
    public void onBackPressed() {
        whereToBack();
        super.onBackPressed();
    }

    public void whereToBack() {
        Intent appLoginIntent = new Intent(mContext, AppLoginActivity.class);
        appLoginIntent.putExtra("whereTocomeLogin", getIntent().getStringExtra("whereTocomeLogin"));
        startActivity(appLoginIntent);
        overridePendingTransition(R.anim.slide_in_left_new,0);
//        finish();
    }

    public void goToAfterLogin() {
        if (Utils.whereTocomeLogin != null) {
            if (Utils.whereTocomeLogin.equalsIgnoreCase("more")) {
                /*Direct goto SFA Screen*/
                if (Utils.retriveLoginData(mContext).getMemberType().equalsIgnoreCase(",3,")) {
                    Intent SFAintent = new Intent(mContext, DisplaySFAUserActivity.class);
                    startActivity(SFAintent);
                    finish();
                } else {
                    Intent DashboardIntent = new Intent(mContext, DashboardActivity.class);
//                    DashboardIntent.putExtra("whichPageRun", "4");
                    startActivity(DashboardIntent);
                    finish();
                }
            } else if (Utils.whereTocomeLogin.equalsIgnoreCase("Feedback")) {
                Intent DashboardIntent = new Intent(mContext, DashboardActivity.class);
                DashboardIntent.putExtra("whichPageRun", "3");
                startActivity(DashboardIntent);
                finish();
            }else{
                finish();
            }
        } else {
            if (Utils.retriveLoginData(mContext).getMemberType().equalsIgnoreCase(",3,")) {
                Intent SFAintent = new Intent(mContext, DisplaySFAUserActivity.class);
                startActivity(SFAintent);
                finish();
            } else {
                Intent DashboardIntent = new Intent(mContext, DashboardActivity.class);
                startActivity(DashboardIntent);
                finish();
            }

        }
    }
}
