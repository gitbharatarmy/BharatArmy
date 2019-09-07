package com.bharatarmy.Activity;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bharatarmy.Models.LogginModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.ApiHandler;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityLoginBinding loginBinding;
    Context mContext;
    String username_str, password_str;
    private String android_id;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        mContext = LoginActivity.this;
        setListner();
        getFCMTOken();
    }

    public void setListner() {
        loginBinding.logginBtn.setOnClickListener(this);
        loginBinding.signUpTxt.setOnClickListener(this);
        loginBinding.forgotTxt.setOnClickListener(this);
        loginBinding.skipTxt.setOnClickListener(this);

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

    public void getFCMTOken() {
        android_id = Settings.Secure.getString(LoginActivity.this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        Log.d("deviceID", android_id);

        FirebaseMessaging.getInstance().setAutoInitEnabled(true);
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        token = task.getResult().getToken();

                        Log.d("token", token);
                        //getting old saved token
                        String old_token = Utils.getPref(getApplicationContext(), "registration_id");

                        if (!old_token.equalsIgnoreCase(token)) {
                            Utils.setPref(getApplicationContext(), "registration_id", token);
                            // sendRegistrationToServer(refreshedToken);
                        }


                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_up_txt:
                Intent signupIntent = new Intent(mContext, SignUpActivity.class);
                signupIntent.putExtra("wheretocome", "login_dialog_item");
                startActivity(signupIntent);
                break;
            case R.id.loggin_btn:
                verifyLoginDetails();
                break;
            case R.id.forgot_txt:
                Intent forgotIntent=new Intent(mContext,ForgotActivity.class);
                startActivity(forgotIntent);
                break;
            case R.id.skip_txt:
//                Utils.getCurrentUserIDName("0","",mContext);
//                Intent DashboardIntent = new Intent(mContext, DashboardActivity.class);
//                startActivity(DashboardIntent);
//                finish();
                break;
        }
    }

    public void getLogin() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), LoginActivity.this);
            return;
        }

        Utils.showDialog(mContext);
        ApiHandler.getApiService().getLogin(getLoginData(), new retrofit.Callback<LogginModel>() {
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
                        Utils.setPref(mContext, "CountryISOCode", loginModel.getData().getCountryISOCode());
                        Utils.setPref(mContext, "CountryPhoneNo", loginModel.getData().getCountryPhoneNo());
                        Utils.setPref(mContext, "IsBAAdmin", String.valueOf(loginModel.getData().getIsBAAdmin()));

                        Utils.storeLoginOtherData(loginModel.getOtherData(),mContext);

                        Utils.getCurrentUserIDName(String.valueOf(loginModel.getData().getId()),loginModel.getData().getName(),mContext);

                        if (loginModel.getData().getIsNumberVerified() == 0) {
                            Intent otpIntent = new Intent(mContext, MobileVerificationNewActivity.class);
                            AppConfiguration.wheretocomemobile = "Login";
                            startActivity(otpIntent);
//                            overridePendingTransition(R.anim.slide_in_left,0);
                            finish();
                        } else {
                            Intent DashboardIntent = new Intent(mContext, DashboardActivity.class);
                            startActivity(DashboardIntent);
//                            overridePendingTransition(R.anim.slide_in_left,0);
                            finish();
                        }
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
        return map;
    }

    @Override
    public void onBackPressed() {
//        finish();
//        super.onBackPressed();
    }
}
