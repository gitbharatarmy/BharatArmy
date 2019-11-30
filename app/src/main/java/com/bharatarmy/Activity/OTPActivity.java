package com.bharatarmy.Activity;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bharatarmy.Interfaces.OtpReceivedInterface;
import com.bharatarmy.Interfaces.SmsListener;
import com.bharatarmy.Models.LogginModel;
import com.bharatarmy.Models.MyScreenChnagesModel;
import com.bharatarmy.Models.OtpModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.ApiHandler;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.NetworkClient;
import com.bharatarmy.Utility.ProgressRequestBody;
import com.bharatarmy.Utility.SmsBroadcastReceiver;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.Utility.WebServices;
import com.bharatarmy.databinding.ActivityOtpBinding;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.HintRequest;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.greenrobot.eventbus.EventBus;

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

public class OTPActivity extends AppCompatActivity implements View.OnClickListener, ProgressRequestBody.UploadCallbacks ,GoogleApiClient.ConnectionCallbacks,
        OtpReceivedInterface, GoogleApiClient.OnConnectionFailedListener{

    ActivityOtpBinding activityOtpBinding;
    Context mContext;
    String otpStr, finalgetOtpStr, strWheretocome, strFirstName, strLastName, strEmail, strCountrycode, strMobileno, strPassword, strCheck;
    String phoneNoStr, countryCodeStr;
    ProgressDialog mDialog;
    File file = null;
    Uri uri, uri1;

//    otp retrive
    GoogleApiClient mGoogleApiClient;
    SmsBroadcastReceiver mSmsBroadcastReceiver;
    private int RESOLVE_HINT = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityOtpBinding = DataBindingUtil.setContentView(this, R.layout.activity_otp);

        mContext = OTPActivity.this;
        init();
        getIntentValue();
        setListiner();
    }

    public void init(){
        // init broadcast receiver
        mSmsBroadcastReceiver = new SmsBroadcastReceiver();

        //set google api client for hint request
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .enableAutoManage(this, this)
                .addApi(Auth.CREDENTIALS_API)
                .build();

        mSmsBroadcastReceiver.setOnOtpListeners(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SmsRetriever.SMS_RETRIEVED_ACTION);
        getApplicationContext().registerReceiver(mSmsBroadcastReceiver, intentFilter);

        // get mobile number from phone
//        getHintPhoneNumber();
startSMSListener();
    }

    public void getHintPhoneNumber() {
        HintRequest hintRequest =
                new HintRequest.Builder()
                        .setPhoneNumberIdentifierSupported(true)
                        .build();
        PendingIntent mIntent = Auth.CredentialsApi.getHintPickerIntent(mGoogleApiClient, hintRequest);
        try {
            startIntentSenderForResult(mIntent.getIntentSender(), RESOLVE_HINT, null, 0, 0, 0);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Result if we want hint number
        if (requestCode == RESOLVE_HINT) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    Credential credential = data.getParcelableExtra(Credential.EXTRA_KEY);
                    // credential.getId();  <-- will need to process phone number string
//                    inputMobileNumber.setText(credential.getId());
                    Log.d("id",credential.getId());
//                    activityOtpBinding.edit1.setText(credential.getId());
                }

            }
        }
    }

    public void startSMSListener() {
        SmsRetrieverClient mClient = SmsRetriever.getClient(this);
        Task<Void> mTask = mClient.startSmsRetriever();
        mTask.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override public void onSuccess(Void aVoid) {
            }
        });
        mTask.addOnFailureListener(new OnFailureListener() {
            @Override public void onFailure(@NonNull Exception e) {
                Utils.ping(mContext,"error occurs");
            }
        });
    }


    public void getIntentValue() {
        if (getIntent().getStringExtra("wheretocome") != null) {
            strWheretocome = getIntent().getStringExtra("wheretocome");

            if (strWheretocome.equalsIgnoreCase("Signup")) {
                otpStr = getIntent().getStringExtra("OTP");
                phoneNoStr = getIntent().getStringExtra("OTPmobileno");
                countryCodeStr = getIntent().getStringExtra("countrycode");

                strFirstName = getIntent().getStringExtra("signupFirstname");
                strLastName = getIntent().getStringExtra("signupLastname");
                strEmail = getIntent().getStringExtra("signupEmail");
                strCountrycode = getIntent().getStringExtra("signupCountryCode");
                strMobileno = getIntent().getStringExtra("signupMobileno");
                strPassword = getIntent().getStringExtra("signupPassword");
                strCheck = getIntent().getStringExtra("signupCheck");
            }
        }


    }

    /*use for Update profile*/
    public void callUpdateProfile() {
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
        RequestBody firstName = RequestBody.create(MediaType.parse("text/plain"), getIntent().getStringExtra("EditFirstName"));
        RequestBody lastName = RequestBody.create(MediaType.parse("text/plain"), getIntent().getStringExtra("EditLastName"));
        RequestBody email = RequestBody.create(MediaType.parse("text/plain"), getIntent().getStringExtra("Email"));
        RequestBody countryISOCode = RequestBody.create(MediaType.parse("text/plain"), AppConfiguration.currentCountryISOCode);
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
        Call<LogginModel> responseBodyCall = uploadAPIs.updateprofile(appuserId, firstName, lastName, email, countryISOCode,
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
                    Utils.storeCurrentLocationData(response.body().getCurrentLocation(), mContext);
                    Utils.ping(mContext, "Profile Updated Successfully");
//                    Intent myprofileIntent = new Intent(mContext, MyProfileActivity.class);
//                    startActivity(myprofileIntent);
                    EventBus.getDefault().post(new MyScreenChnagesModel("change"));
                       finish();
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

        activityOtpBinding.edit1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Integer textlength1 = activityOtpBinding.edit1.getText().length();
                activityOtpBinding.edit1.setBackgroundResource(R.drawable.rectangle_line);
                if (textlength1 == 1) {
                    activityOtpBinding.edit1.setBackgroundResource(R.drawable.fill_rectangle_line);
                    activityOtpBinding.edit2.requestFocus();
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
        activityOtpBinding.edit2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    //this is for backspace
                    activityOtpBinding.edit1.requestFocus();
                }
                return false;
            }
        });
        activityOtpBinding.edit3.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    //this is for backspace
                    activityOtpBinding.edit2.requestFocus();
                }
                return false;
            }
        });
        activityOtpBinding.edit4.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    //this is for backspace
                    activityOtpBinding.edit3.requestFocus();
                }
                return false;
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
//                Utils.handleClickEvent(mContext, activityOtpBinding.otpImg);
                getOtpData();
                break;
            case R.id.back_linear:
                if (strWheretocome.equalsIgnoreCase("Signup")) {
                    Intent mobileIntent = new Intent(mContext, SignUpActivity.class);
                    mobileIntent.putExtra("wheretocome", "OTP");
                    mobileIntent.putExtra("signupFirstname", strFirstName);
                    mobileIntent.putExtra("signupLastname", strLastName);
                    mobileIntent.putExtra("signupEmail", strEmail);
                    mobileIntent.putExtra("signupCountryCode", strCountrycode);
                    mobileIntent.putExtra("signupMobileno", strMobileno);
                    mobileIntent.putExtra("signupPassword", strPassword);
                    mobileIntent.putExtra("signupCheck", strCheck);
                    startActivity(mobileIntent);
                    overridePendingTransition(0, 0);
//                    finish();
                } else if (strWheretocome.equalsIgnoreCase("EditProfile")) {
                    Intent editIntent =new Intent(mContext,EditProfileActivity.class);
                    startActivity(editIntent);
                    finish();
                } else if (getIntent().getStringExtra("whereTocomeLogin") != null) {
                    finish();
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


        if (!finalgetOtpStr.equalsIgnoreCase("")) {
//            if (otpStr.equalsIgnoreCase(finalgetOtpStr)) {
                if (strWheretocome.equalsIgnoreCase("Signup")) {
                    callSignUp();
                } else if (strWheretocome.equalsIgnoreCase("EditProfile")) {
                    uri1 = getIntent().getData();
                    if (!uri1.equals("1")) {
                        uri = uri1;
                    } else {

                    }
                    callUpdateProfile();
                } else {
                    VerificationPhone();
                }
//            } else {
//                Utils.ping(mContext, "Please Enter Valid OTP");
//            }
        } else {
            Utils.ping(mContext, "Please Enter OTP");
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

    public void callSignUp() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), OTPActivity.this);
            return;
        }
        if (OTPActivity.this.getWindow().getDecorView().isShown()) {

            //Show Your Progress Dialog
            Utils.showDialog(mContext);
        }

        ApiHandler.getApiService().getSignup(getSignUpData(), new retrofit.Callback<LogginModel>() {
            @Override
            public void success(LogginModel loginModel, Response response) {

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
                        Utils.setPref(mContext, "LoginType", "Email");
                        Utils.storeLoginData(loginModel.getData(), mContext);
                        Utils.storeCurrentLocationData(loginModel.getCurrentLocation(), mContext);
                        Utils.storeLoginOtherData(loginModel.getOtherData(), mContext);
                        /*SFA Screen Goto direct*/
                        if (Utils.retriveLoginData(mContext).getMemberType().equalsIgnoreCase(",3,")){
                            Intent SFAintent = new Intent(mContext, DisplaySFAUserActivity.class);
                            startActivity(SFAintent);
                            finish();
                        }else{
                        if (getIntent().getStringExtra("whereTocomeLogin") != null) {
                            if (getIntent().getStringExtra("whereTocomeLogin").equalsIgnoreCase("more")) {
                                    if (Utils.retriveLoginData(mContext).getMemberType().equalsIgnoreCase(",3,")) {
                                        Intent SFAintent = new Intent(mContext, DisplaySFAUserActivity.class);
                                        startActivity(SFAintent);
                                        finish();
                                    } else {
                                Intent DashboardIntent = new Intent(mContext, DashboardActivity.class);
//                                DashboardIntent.putExtra("whichPageRun", "4");
                                startActivity(DashboardIntent);
                                finish();
                                    }
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
        map.put("FirstName", strFirstName);
        map.put("LastName", strLastName);
        map.put("Email", strEmail);
        map.put("PhoneNo", strMobileno);
        map.put("CountryISOCode", AppConfiguration.currentCountryISOCode);
        map.put("CountryPhoneNo", strCountrycode);
        map.put("Password", strPassword);
        map.put("OTPText", finalgetOtpStr);
        map.put("SMSSentId", otpStr);
        map.put("TokenId", Utils.getPref(mContext, "registration_id"));
        map.put("MemberId", String.valueOf(Utils.getAppUserId(mContext)));
        map.put("ModelName", Utils.getDeviceName());
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
            mobileIntent.putExtra("signupFirstname", strFirstName);
            mobileIntent.putExtra("signupLastname", strLastName);
            mobileIntent.putExtra("signupEmail", strEmail);
            mobileIntent.putExtra("signupCountryCode", strCountrycode);
            mobileIntent.putExtra("signupMobileno", strMobileno);
            mobileIntent.putExtra("signupPassword", strPassword);
            mobileIntent.putExtra("signupCheck", strCheck);
            startActivity(mobileIntent);
            overridePendingTransition(0, 0);
//                    finish();
        } else if (getIntent().getStringExtra("whereTocomeLogin") != null) {
            finish();
        } else if (strWheretocome.equalsIgnoreCase("EditProfile")) {
            Intent editIntent =new Intent(mContext,EditProfileActivity.class);
            startActivity(editIntent);
            OTPActivity.this.finish();
        } else {
            Intent mobileIntent = new Intent(mContext, MobileVerificationNewActivity.class);
            startActivity(mobileIntent);
            overridePendingTransition(0, 0);
            finish();
        }
        super.onBackPressed();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onOtpReceived(String otp) {
        activityOtpBinding.edit1.setText(otp.substring(0,1));
        activityOtpBinding.edit2.setText(otp.substring(1,2));
        activityOtpBinding.edit3.setText(otp.substring(2,3));
        activityOtpBinding.edit4.setText(otp.substring(3,4));

        Log.d("otp",otp);
    }

    @Override
    public void onOtpTimeout() {
         Utils.ping(mContext,"Timeout");
    }


    @Override
    protected void onStop() {
        super.onStop();
        Utils.dismissDialog();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Utils.dismissDialog();
    }
}
