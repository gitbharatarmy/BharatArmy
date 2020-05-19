package com.bharatarmy.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.bharatarmy.Models.LogginModel;
import com.bharatarmy.ProgressDrawable;
import com.bharatarmy.R;
import com.bharatarmy.Utility.ApiHandler;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;

import com.bharatarmy.appenum.PasswordStrength;
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
        init();
        setListner();

    }

    public void init() {
     setmarginofservererrorTxtview();
    }

    public void setmarginofservererrorTxtview(){
        if (loginBinding.serverErrorTxt.isShown()){
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 0, 0, 0);
            loginBinding.logginBtn.setLayoutParams(params);
        }else{
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, getResources().getDimensionPixelOffset(R.dimen.material_margin_top), 0, 0);
            loginBinding.logginBtn.setLayoutParams(params);
        }
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

        loginBinding.userNameEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    loginBinding.emailErrorTxt.setVisibility(View.GONE);
                    loginBinding.serverErrorTxt.setVisibility(View.GONE);
                    setmarginofservererrorTxtview();
                }else if (s.toString().equalsIgnoreCase("")){
                    loginBinding.emailErrorTxt.setVisibility(View.GONE);
                    loginBinding.serverErrorTxt.setVisibility(View.GONE);
                    setmarginofservererrorTxtview();
                }
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
                if (s.length() > 0) {
                    loginBinding.serverErrorTxt.setVisibility(View.GONE);
                    loginBinding.passwordErrorTxt.setVisibility(View.GONE);
                    setmarginofservererrorTxtview();
                } else if (s.toString().equalsIgnoreCase("")) {
                    loginBinding.serverErrorTxt.setVisibility(View.GONE);
                    loginBinding.passwordErrorTxt.setVisibility(View.GONE);
                    setmarginofservererrorTxtview();
                }
            }
        });


    }


    public void verifyLoginDetails() {
        username_str = loginBinding.userNameEdt.getText().toString();
        password_str = loginBinding.userPasswordEdt.getText().toString();


        if (!username_str.equalsIgnoreCase("")) {
            if (Utils.isValidEmailId(username_str)) {
                if (!password_str.equalsIgnoreCase("")) {
                    if (password_str.length() >= 5 && password_str.length() <= 10) {
                        getLogin();
                    } else {
                        loginBinding.passwordErrorTxt.setVisibility(View.VISIBLE);
                        loginBinding.passwordErrorTxt.setText(getResources().getString(R.string.login_user_password_error));
                    }
                } else {
                    loginBinding.passwordErrorTxt.setVisibility(View.VISIBLE);
                    loginBinding.passwordErrorTxt.setText(getResources().getString(R.string.usererror));
                }
            } else {
                if (!password_str.equalsIgnoreCase("")) {
                    if (password_str.length() >= 5 && password_str.length() <= 10) {

                    } else {
                        loginBinding.passwordErrorTxt.setVisibility(View.VISIBLE);
                        loginBinding.passwordErrorTxt.setText(getResources().getString(R.string.login_user_password_error));
                    }
                } else {
                    loginBinding.passwordErrorTxt.setVisibility(View.VISIBLE);
                    loginBinding.passwordErrorTxt.setText(getResources().getString(R.string.usererror));
                }
                loginBinding.emailErrorTxt.setVisibility(View.VISIBLE);
                loginBinding.emailErrorTxt.setText(getResources().getString(R.string.login_user_email_error));
            }
        } else {
            loginBinding.emailErrorTxt.setVisibility(View.VISIBLE);
            loginBinding.emailErrorTxt.setText(getResources().getString(R.string.usererror));
            if (password_str.equalsIgnoreCase("")) {
                loginBinding.passwordErrorTxt.setVisibility(View.VISIBLE);
            }
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
                    loginBinding.serverErrorTxt.setVisibility(View.VISIBLE);
                    loginBinding.serverErrorTxt.setText(loginModel.getMessage());
                    setmarginofservererrorTxtview();
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
                        Utils.storeCurrentLocationData(loginModel.getCurrentLocation(), mContext);
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
                    } else {
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
        map.put("ModelName", Utils.getDeviceName());
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
        overridePendingTransition(R.anim.slide_in_left_new, 0);
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
            } else {
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
