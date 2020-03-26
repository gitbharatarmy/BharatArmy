package com.bharatarmy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.bharatarmy.Models.LogginModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.ApiHandler;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityFacebookLoginWithNoEmailBinding;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;

import java.util.HashMap;
import java.util.Map;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class FacebookLoginWithNoEmailActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityFacebookLoginWithNoEmailBinding activityFacebookLoginWithNoEmailBinding;
    Context mContext;
    String personNameStr, personImageStr, facebookfirstNameStr, personEmailStr, personNumberStr, personCountryDialCodeStr;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityFacebookLoginWithNoEmailBinding = DataBindingUtil.setContentView(this, R.layout.activity_facebook_login_with_no_email);
        mContext = FacebookLoginWithNoEmailActivity.this;

        init();
        setListiner();

    }

    public void init() {
        personNameStr = getIntent().getStringExtra("personName");
        personImageStr = getIntent().getStringExtra("personImage");
        facebookfirstNameStr = getIntent().getStringExtra("firstName");


        Utils.setImageInImageView(personImageStr, activityFacebookLoginWithNoEmailBinding.profileImage, mContext);
        activityFacebookLoginWithNoEmailBinding.nameEdt.setText(personNameStr);
        activityFacebookLoginWithNoEmailBinding.continueBtn.setText("Continue As " + facebookfirstNameStr);


        if (AppConfiguration.currentCountryISOCode != null) {
            if (!AppConfiguration.currentCountryISOCode.equalsIgnoreCase("")) {
                activityFacebookLoginWithNoEmailBinding.ccp.setCountryForNameCode(AppConfiguration.currentCountryISOCode);
            }
        }


    }

    public void setListiner() {
        activityFacebookLoginWithNoEmailBinding.backImg.setOnClickListener(this);
        activityFacebookLoginWithNoEmailBinding.continueBtn.setOnClickListener(this);

        activityFacebookLoginWithNoEmailBinding.phoneNoEdt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    facebookLogin();
                }
                return false;
            }
        });

    }

    public void facebookLogin() {
        personNameStr = activityFacebookLoginWithNoEmailBinding.nameEdt.getText().toString();
        personEmailStr = activityFacebookLoginWithNoEmailBinding.emailEdt.getText().toString();
        personNumberStr = activityFacebookLoginWithNoEmailBinding.phoneNoEdt.getText().toString();
        personCountryDialCodeStr = activityFacebookLoginWithNoEmailBinding.ccp.getSelectedCountryCode();
        AppConfiguration.currentCountryISOCode = activityFacebookLoginWithNoEmailBinding.ccp.getSelectedCountryNameCode();

        Log.d("facebookLoginData :", "Name :" + personNameStr + "Email :" + personEmailStr +
                "personNumber :" + personNumberStr + "countrycode :" + personCountryDialCodeStr +
                "countryISO :" + AppConfiguration.currentCountryISOCode);

        if (!personNameStr.equalsIgnoreCase("")) {
            if (!personEmailStr.equalsIgnoreCase("")) {
                if (Utils.isValidEmailId(personEmailStr)) {
                    if (personCountryDialCodeStr.length() > 0) {
                        if (!personNumberStr.equalsIgnoreCase("")) {
                            if (Utils.isValidPhoneNumber(personNumberStr)) {
                                callFacebookSignUp();
                            } else {
                                activityFacebookLoginWithNoEmailBinding.phoneNoEdt.setError("Invalid Phone Number");
                            }
                        } else {
                            activityFacebookLoginWithNoEmailBinding.phoneNoEdt.setError("Phone Number is required");
                        }
                    } else {
                        Utils.ping(mContext, "Country Code is required");
                    }
                } else {
                    activityFacebookLoginWithNoEmailBinding.emailEdt.setError("Invalid Email Address");
                }
            } else {
                activityFacebookLoginWithNoEmailBinding.emailEdt.setError("Email Address is required");
            }
        } else {
            activityFacebookLoginWithNoEmailBinding.nameEdt.setError("Name is required");
        }
    }

    public void facebooklogout() {
        new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/",
                null, HttpMethod.DELETE, new GraphRequest
                .Callback() {
            @Override
            public void onCompleted(GraphResponse graphResponse) {

                LoginManager.getInstance().logOut();
//                Intent returnAppLoginIntent = new Intent(mContext, AppLoginActivity.class);
//                startActivity(returnAppLoginIntent);
                finish();
                hideProgressDialog();
            }
        }).executeAsync();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                showProgressDialog();
                facebooklogout();
                break;
            case R.id.continue_btn:
                facebookLogin();
                break;
            case R.id.email_edt:
                Utils.scrollScreen(activityFacebookLoginWithNoEmailBinding.signupScrollView);
                break;
        }
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        showProgressDialog();
        facebooklogout();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if ((mProgressDialog != null) && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    //    Use for facebook signup data entery in database
    public void callFacebookSignUp() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), FacebookLoginWithNoEmailActivity.this);
            return;
        }

        Utils.showDialog(mContext);
        ApiHandler.getApiService().getLoginwithFacebook(getFacebookSignUpData(), new retrofit.Callback<LogginModel>() {
            @Override
            public void success(LogginModel loginModel, Response response) {
                Utils.dismissDialog();
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
                    if (loginModel.getData() != null) {
                        Utils.setPref(mContext, "IsSkipLogin", "");
                        Utils.setPref(mContext, "IsLoginUser", "1");
                        Utils.setPref(mContext, "LoginType", "Facebook");
                        Utils.storeLoginData(loginModel.getData(), mContext);
                        Utils.storeCurrentLocationData(loginModel.getCurrentLocation(), mContext);
                        Utils.storeLoginOtherData(loginModel.getOtherData(), mContext);
                        if (Utils.whereTocomeLogin != null) {
                            if (Utils.whereTocomeLogin.equalsIgnoreCase("more")) {
                                Intent DashboardIntent = new Intent(mContext, DashboardActivity.class);
//                                DashboardIntent.putExtra("whichPageRun", "4");
                                startActivity(DashboardIntent);
                                finish();
                            } else {
                                finish();
                            }
                        } else {
                            Intent DashboardIntent = new Intent(mContext, DashboardActivity.class);
                            AppConfiguration.position = 0;
                            startActivity(DashboardIntent);
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

    private Map<String, String> getFacebookSignUpData() {
        Map<String, String> map = new HashMap<>();
        map.put("email", personEmailStr);
        map.put("Name", personNameStr);
        map.put("Image", personImageStr);
        map.put("TokenId", Utils.getPref(mContext, "registration_id"));
        map.put("ModelName", Utils.getDeviceName());
        map.put("PhoneNo", personNumberStr);
        map.put("CountryISOCode", AppConfiguration.currentCountryISOCode);
        map.put("CountryDialCode", personCountryDialCodeStr);

        return map;
    }
}
