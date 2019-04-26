package com.bharatarmy.Activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.bharatarmy.Models.LogginModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.ApiHandler;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityLoginNewBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class LoginNewActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityLoginNewBinding activityLoginNewBinding;
    Context mContext;
    String username_str, password_str;
    private String android_id;
    String token;

    private Handler imageSwitcherHandler;
    private ArrayList<Integer> layouts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View decorView = getWindow().getDecorView();
// Hide both the navigation bar and the status bar.
// SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
// a general rule, you should design your app to hide the status bar whenever you
// hide the navigation bar.
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        activityLoginNewBinding = DataBindingUtil.setContentView(this, R.layout.activity_login_new);

        mContext = LoginNewActivity.this;
        setDataValue();
        setListner();
        getFCMTOken();
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
    public void setDataValue() {
        Animation in = AnimationUtils.loadAnimation(mContext, R.anim.fade_in);
        Animation out = AnimationUtils.loadAnimation(mContext, R.anim.fade_out);

        activityLoginNewBinding.imageView.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView myView = new ImageView(mContext);
                myView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                myView.setLayoutParams(new
                        ImageSwitcher.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT));
                return myView;
            }
        });
        activityLoginNewBinding.imageView.setImageResource(R.drawable.login41);
        activityLoginNewBinding.imageView.setInAnimation(in);
        activityLoginNewBinding.imageView.setOutAnimation(out);
        layouts = new ArrayList<Integer>();

        layouts.add(R.drawable.login_new_1);
        layouts.add(R.drawable.login_new_2);
        layouts.add(R.drawable.login_new_3);

        imageSwitcherHandler = new Handler();
        Runnable runnable = new Runnable() {
            int i = 0;

            public void run() {
                activityLoginNewBinding.imageView.setImageResource(layouts.get(i));
                i++;
                if (i > layouts.size() - 1) {
                    activityLoginNewBinding.imageView.setImageResource(R.drawable.login41);
                    i = 0;
                }
                imageSwitcherHandler.postDelayed(this, 7000);  //for interval...
            }
        };
        imageSwitcherHandler.postDelayed(runnable, 7000); //for initial delay..
    }

    public void setListner() {
        activityLoginNewBinding.logginBtn.setOnClickListener(this);
        activityLoginNewBinding.signUpTxt.setOnClickListener(this);

        activityLoginNewBinding.userPasswordEdt.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    verifyLoginDetails();
                }
                return false;
            }
        });
    }

    public void verifyLoginDetails() {
        username_str = activityLoginNewBinding.userNameEdt.getText().toString();
        password_str = activityLoginNewBinding.userPasswordEdt.getText().toString();

        if (!username_str.equalsIgnoreCase("") & !password_str.equalsIgnoreCase("")) {
            if (Utils.isValidEmailId(username_str)) {
                getLogin();
            } else {
                activityLoginNewBinding.userNameEdt.setError("Please enter valid email address");
            }

        } else {
            activityLoginNewBinding.userNameEdt.setError("Blank field not allowed");
            activityLoginNewBinding.userPasswordEdt.setError("Blank field not allowed");
        }

    }

    public void getFCMTOken() {
        android_id = Settings.Secure.getString(LoginNewActivity.this.getContentResolver(),
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
                Intent signupIntent = new Intent(mContext, SignupNewActivity.class);
                startActivity(signupIntent);
                break;
            case R.id.loggin_btn:
                verifyLoginDetails();
                break;
        }
    }

    public void getLogin() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), LoginNewActivity.this);
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
        map.put("UserName", username_str);
        map.put("Password", password_str);
        map.put("TokenId", Utils.getPref(mContext, "registration_id"));
        return map;
    }
}
