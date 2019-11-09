package com.bharatarmy.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bharatarmy.Interfaces.SmsListener;
import com.bharatarmy.Models.LogginModel;
import com.bharatarmy.Models.OtpModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.ApiHandler;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.NetworkClient;
import com.bharatarmy.Utility.ProgressRequestBody;
import com.bharatarmy.Utility.SmsReceiver;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.Utility.WebServices;
import com.bharatarmy.databinding.ActivityOtpBinding;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class OTPActivity extends AppCompatActivity implements View.OnClickListener, ProgressRequestBody.UploadCallbacks {

    ActivityOtpBinding activityOtpBinding;
    Context mContext;
    String otpStr, finalgetOtpStr, strWheretocome, strFullName, strEmail, strCountrycode, strMobileno, strPassword, strCheck;
    String phoneNoStr, countryCodeStr;
    ProgressDialog mDialog;
    File file = null;
    Uri uri, uri1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityOtpBinding = DataBindingUtil.setContentView(this, R.layout.activity_otp);

        mContext = OTPActivity.this;
        getIntentValue();
        setListiner();
    }

    public void getIntentValue() {
        if (getIntent().getStringExtra("wheretocome")!=null){
            strWheretocome = getIntent().getStringExtra("wheretocome");

            if (strWheretocome.equalsIgnoreCase("Signup")) {
                otpStr = getIntent().getStringExtra("OTP");
                phoneNoStr = getIntent().getStringExtra("OTPmobileno");
                countryCodeStr = getIntent().getStringExtra("countrycode");

                strFullName = getIntent().getStringExtra("signupFullname");
                strEmail = getIntent().getStringExtra("signupEmail");
                strCountrycode = getIntent().getStringExtra("signupCountryCode");
                strMobileno = getIntent().getStringExtra("signupMobileno");
                strPassword = getIntent().getStringExtra("signupPassword");
                strCheck = getIntent().getStringExtra("signupCheck");
            }
        }


    }

    /*use for Update profile*/
    public void getUpdateProfile() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), OTPActivity.this);
            return;
        }
        MultipartBody.Part body = null;
        Log.d("uri", uri.toString());
        if (uri != null) {

            String filePath = Utils.getFilePathFromUri(mContext, uri);

            mDialog = new ProgressDialog(mContext);
            mDialog.setCancelable(false);
            mDialog.setMessage("Uploading Profile Picture");
            mDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mDialog.setIndeterminate(false);
            mDialog.show();
            try {
                file = new File(filePath);

            } catch (Exception e) {
                e.printStackTrace();
            }
            if (file != null) {
                if (file.exists()) {
                    filePath = file.getAbsolutePath();
                }
            }

            if (file != null) {
                if (file.exists()) {

                    ProgressRequestBody fileBody = new ProgressRequestBody(file, this);
                    body = MultipartBody.Part.createFormData("image", file.getName(), fileBody);

                }
            }
        } else {
            Utils.showDialog(mContext);
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), "");

            body = MultipartBody.Part.createFormData("file", "", requestFile);
        }
        Retrofit retrofit = NetworkClient.getRetrofitClient(this);
        WebServices uploadAPIs = retrofit.create(WebServices.class);

        RequestBody appuserId = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(Utils.getAppUserId(mContext)));
        RequestBody fullname = RequestBody.create(MediaType.parse("text/plain"), getIntent().getStringExtra("EditFullName"));
        RequestBody countryISOCode = RequestBody.create(MediaType.parse("text/plain"), AppConfiguration.currentCountry);
        RequestBody countycode = RequestBody.create(MediaType.parse("text/plain"), getIntent().getStringExtra("countryCode"));
        RequestBody phoneno = RequestBody.create(MediaType.parse("text/plain"), getIntent().getStringExtra("NewPhoneNumber"));
        RequestBody gender = RequestBody.create(MediaType.parse("text/plain"), getIntent().getStringExtra("gender"));
        RequestBody otptext = RequestBody.create(MediaType.parse("text/plain"), finalgetOtpStr);
        RequestBody smssentId = RequestBody.create(MediaType.parse("text/plaim"), getIntent().getStringExtra("OTP"));
        RequestBody addressLine1 = RequestBody.create(MediaType.parse("text/plain"), getIntent().getStringExtra("addressLine1"));
        RequestBody addressLine2 = RequestBody.create(MediaType.parse("text/plain"), getIntent().getStringExtra("addressLine2"));
        RequestBody area = RequestBody.create(MediaType.parse("text/plain"), getIntent().getStringExtra("area"));
        RequestBody state = RequestBody.create(MediaType.parse("text/plain"), getIntent().getStringExtra("stateName"));
        RequestBody city = RequestBody.create(MediaType.parse("text/plain"), getIntent().getStringExtra("stateId"));
        RequestBody stateId = RequestBody.create(MediaType.parse("text/plain"), getIntent().getStringExtra("cityName"));
        RequestBody citiesId = RequestBody.create(MediaType.parse("text/plain"), getIntent().getStringExtra("cityId"));
        RequestBody pincode = RequestBody.create(MediaType.parse("text/plain"), getIntent().getStringExtra("pincode"));
        RequestBody facebookprofile = RequestBody.create(MediaType.parse("text/plain"), getIntent().getStringExtra("facebookprofile"));
        RequestBody twitterprofile = RequestBody.create(MediaType.parse("text/plain"), getIntent().getStringExtra("twitterprofile"));
        RequestBody linkedinprofile = RequestBody.create(MediaType.parse("text/plain"), getIntent().getStringExtra("linkedinprofile"));
        RequestBody instagramprofile = RequestBody.create(MediaType.parse("text/plain"), getIntent().getStringExtra("instagramprofile"));

//        ShowProgressDialog();
        Call<LogginModel> responseBodyCall = uploadAPIs.updateprofile(appuserId, fullname, countryISOCode,
                countycode, phoneno, gender, otptext, smssentId, addressLine1, addressLine2, area, stateId,
                state, citiesId, city, pincode, facebookprofile, twitterprofile, linkedinprofile, instagramprofile, body);

        Log.d("File", "" + responseBodyCall);
        responseBodyCall.enqueue(new Callback<LogginModel>() {

            @Override
            public void onResponse(Call<LogginModel> call, retrofit2.Response<LogginModel> response) {
                if (uri != null) {
                    mDialog.dismiss();
                } else {
                    Utils.dismissDialog();
                }
                if (response.body().getIsValid() == 1) {
                    Utils.storeLoginData(response.body().getData(), mContext);

                    Utils.ping(mContext, "Profile Updated Successfully");
                    Intent myprofileIntent = new Intent(mContext, MyProfileActivity.class);
                    startActivity(myprofileIntent);

                } else {
                    Utils.ping(mContext, response.body().getMessage());
                }


            }

            @Override
            public void onFailure(Call<LogginModel> call, Throwable t) {
                Log.d("failure", "message = " + t.getMessage());
                Log.d("failure", "cause = " + t.getCause());
                if (uri != null) {
                    mDialog.dismiss();
                } else {
                    Utils.dismissDialog();
                }
            }
        });


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
        activityOtpBinding.edit4.setOnEditorActionListener(new EditText.OnEditorActionListener() {
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
                Utils.handleClickEvent(mContext,activityOtpBinding.otpImg);
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
                } else if (strWheretocome.equalsIgnoreCase("EditProfile")) {
//                    Intent editIntent =new Intent(mContext,EditProfileActivity.class);
//                    startActivity(editIntent);
                    finish();
                } else if(getIntent().getStringExtra("whereTocomeLogin")!=null){
                    finish();
                }else {
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

//        if (otpStr.equalsIgnoreCase(finalgetOtpStr)) {
        if (!finalgetOtpStr.equalsIgnoreCase("")) {
            if (strWheretocome.equalsIgnoreCase("Signup")) {
                getSignUp();
            } else if (strWheretocome.equalsIgnoreCase("EditProfile")) {
                uri1 = getIntent().getData();
                if (!uri1.equals("1")) {
                    uri = uri1;
                } else {

                }
                getUpdateProfile();
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
        map.put("AppUserId", String.valueOf(Utils.getAppUserId(mContext)));
        map.put("PhoneNo", phoneNoStr);
        map.put("CountryCode", countryCodeStr);
        return map;
    }

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
                        Utils.setPref(mContext, "IsLoginUser", "1");

                        Utils.storeLoginData(loginModel.getData(), mContext);
                        Utils.storeLoginOtherData(loginModel.getOtherData(), mContext);
                        if (Utils.retriveLoginData(mContext).getMemberType().equalsIgnoreCase(",3,")){
                            Intent SFAintent = new Intent(mContext, DisplaySFAUserActivity.class);
                            startActivity(SFAintent);
                            finish();
                        }else{
                            if (getIntent().getStringExtra("whereTocomeLogin")!=null){
                                if(getIntent().getStringExtra("whereTocomeLogin").equalsIgnoreCase("more")) {
                                    if (Utils.retriveLoginData(mContext).getMemberType().equalsIgnoreCase(",3,")) {
                                        Intent SFAintent = new Intent(mContext, DisplaySFAUserActivity.class);
                                        startActivity(SFAintent);
                                        finish();
                                    } else {
                                        Intent DashboardIntent = new Intent(mContext, DashboardActivity.class);
                                        DashboardIntent.putExtra("whichPageRun", "5");
                                        startActivity(DashboardIntent);
                                        finish();
                                    }
                                }else{
                                    finish();
                                }
                            }else{
                                Intent DashboardIntent = new Intent(mContext, DashboardActivity.class);
                                AppConfiguration.position = 0;
                                startActivity(DashboardIntent);
                                finish();
                            }

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

    private Map<String, String> getSignUpData() {
        Map<String, String> map = new HashMap<>();
        map.put("FullName", strFullName);
        map.put("Email", strEmail);
        map.put("PhoneNo", strMobileno);
        map.put("CountryISOCode", AppConfiguration.currentCountry);
        map.put("CountryPhoneNo", strCountrycode);
        map.put("Password", strPassword);
        map.put("OTPText", finalgetOtpStr);
        map.put("SMSSentId", otpStr);
        map.put("TokenId", Utils.getPref(mContext, "registration_id"));
        return map;
    }

    @Override
    public void onProgressUpdate(int percentage) {
        mDialog.setProgress(percentage);
    }

    @Override
    public void onError() {
        mDialog.dismiss();
        Utils.ping(mContext, "Try Again");
    }

    @Override
    public void onFinish() {
        mDialog.setProgress(100);
    }

    @Override
    public void onBackPressed() {
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
        }else if(getIntent().getStringExtra("whereTocomeLogin")!=null){
          finish();
        } else if (strWheretocome.equalsIgnoreCase("EditProfile")) {
            OTPActivity.this.finish();
        } else {
            Intent mobileIntent = new Intent(mContext, MobileVerificationNewActivity.class);
            startActivity(mobileIntent);
            overridePendingTransition(0, 0);
            finish();
        }
        super.onBackPressed();
    }
}
