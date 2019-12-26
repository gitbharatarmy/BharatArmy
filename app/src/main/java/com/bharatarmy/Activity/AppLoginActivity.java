package com.bharatarmy.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.androidquery.AQuery;
import com.bharatarmy.Adapter.AppDisplayItemAdapter;
import com.bharatarmy.Models.GalleryImageModel;
import com.bharatarmy.Models.LogginModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.ApiHandler;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityAppLoginBinding;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.facebook.FacebookSdk.setAutoLogAppEventsEnabled;

/* Google web client id
 * 718860760622-parj58505tgu73h704s5qq1kuffojg2r.apps.googleusercontent.com

 * Client Secrect
 * RjGdOJJn_XZ_gkRbFGM2MiFh
 *
 * Api Key
 * AIzaSyBw5itHJQUAxBijO1FUxH-sLaQqFC1lOtM
 *
 * project name bharatarmy-237313
 * email id use id android.developer@bharatarmy.com
 * */

/* Facebook AppId
 * 569015383846103
 * Facebook HAshKey
 * Bmce+9aHdOoVtE7fS3B07tfj7Bc=
 * New Hashkey
 * XEU6Vgp4OyPDRlnGOWyeDpffRUQ=
 * Relase krti vakhte app icon mukvo 1024*1024
 * before realese please check facebook developer console
 * https://developers.facebook.com/apps/569015383846103/dashboard/*/

/*Facebook mate hash key generate kri tyre keystore password : BharatArmy*/
public class AppLoginActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = AppLoginActivity.class.getSimpleName();

    ActivityAppLoginBinding activityAppLoginBinding;
    Context mContext;
    private String android_id;
    String token;
    /* google sign in*/
    private GoogleSignInOptions gso;
    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 007;
    private ProgressDialog mProgressDialog;
    /* AppDisplay List*/
    AppDisplayItemAdapter appDisplayItemAdapter;
    List<GalleryImageModel> displayItemList;

    /* facebook sign in*/
    CallbackManager callbackManager;
    private AQuery aQuery;
    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;
    String email;
    /* ImageSlider*/
    LinearLayoutManager layoutManager;

    /*Login String*/
    String personNameStr, personEmailStr, personImageStr, facebookpersonIdStr, facebookNameStr;

    //Remove extra code 09/11/2019 & code backup in 09/11/2019
    private FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            GraphRequest request = GraphRequest.newMeRequest(
                    loginResult.getAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response) {
                            Log.v("LoginActivity", response.toString());

                            // Application code
                            try {
                                Log.d("tttttt", object.getString("id"));
                                String birthday = "";
//                                if(object.has("birthday")){
//                                    birthday = object.getString("birthday"); // 01/31/1980 format
//                                }
                                facebookNameStr = object.getString("first_name");
                                personNameStr = object.getString("first_name") + " " + object.getString("last_name");
                                facebookpersonIdStr = object.getString("id");
                                personImageStr = "https://graph.facebook.com/" + facebookpersonIdStr + "/picture?type=large";
                                if (object.has("email")) {
                                    personEmailStr = object.getString("email");
                                    callFacebookSignUp();
                                } else {
                                    showProgressDialog();
                                    Intent fbloginIntent = new Intent(mContext, FacebookLoginWithNoEmailActivity.class);
                                    fbloginIntent.putExtra("firstName", facebookNameStr);
                                    fbloginIntent.putExtra("personName", personNameStr);
                                    fbloginIntent.putExtra("personImage", personImageStr);
                                    startActivity(fbloginIntent);
                                    finish();

                                }

                                Log.d("Profile", "personName:" + personNameStr + " \n" + "Email: " + personEmailStr + " \n" + "HomeTempleteIDModel: " + facebookpersonIdStr + " \n" + "Birth Date: " + birthday);
                                Log.d("aswwww", personImageStr);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id, first_name, last_name, email, location"); // gender, birthday,
            request.setParameters(parameters);
            request.executeAsync();

        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException error) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this);


        activityAppLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_app_login);
        mContext = AppLoginActivity.this;

        init();
        setListiner();
        getFCMTOken();
    }

    public void init() {
        googleLogin();
        cycleRecyclerview();
    }

    public void setListiner() {
        activityAppLoginBinding.skipLinear.setOnClickListener(this);
        activityAppLoginBinding.loginWithemailLinear.setOnClickListener(this);
        activityAppLoginBinding.loginwithgoogleLinear.setOnClickListener(this);
        activityAppLoginBinding.loginwithfacebookLinear.setOnClickListener(this);
        activityAppLoginBinding.loginButton.setOnClickListener(this);
        activityAppLoginBinding.termsOfServiceLinear.setOnClickListener(this);
        activityAppLoginBinding.privacyPolicyLinear.setOnClickListener(this);
        activityAppLoginBinding.contentPolicyLinear.setOnClickListener(this);

        activityAppLoginBinding.displayAppItemRcv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    activityAppLoginBinding.displayAppItemRcv.stopScroll();
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    autoScroll();
                }
                return true;
            }
        });

    }

    //    marquee style recyclerview
    public void cycleRecyclerview() {
        activityAppLoginBinding.shimmerViewContainer.setVisibility(View.GONE);

        displayItemList = new ArrayList<>();
        displayItemList.add(new GalleryImageModel(AppConfiguration.MAINDASHBOARDIMAGEURL + "BA-01.png"));
        displayItemList.add(new GalleryImageModel(AppConfiguration.MAINDASHBOARDIMAGEURL + "BA-02.png"));
        displayItemList.add(new GalleryImageModel(AppConfiguration.MAINDASHBOARDIMAGEURL + "BA-03.png"));
//        displayItemList.add(new GalleryImageModel(AppConfiguration.MAINDASHBOARDIMAGEURL + "BA-04.png"));


        appDisplayItemAdapter = new AppDisplayItemAdapter(mContext, displayItemList);
        autoScroll();
        layoutManager = new LinearLayoutManager(mContext) {
            @Override
            public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
                LinearSmoothScroller smoothScroller = new LinearSmoothScroller(mContext) {
                    private static final float SPEED = 4000f;// Change this value (default=25f)

                    @Override
                    protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                        return SPEED / displayMetrics.densityDpi;
                    }
                };
                smoothScroller.setTargetPosition(position);
                startSmoothScroll(smoothScroller);
            }

        };
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        activityAppLoginBinding.displayAppItemRcv.setLayoutManager(layoutManager);
        activityAppLoginBinding.displayAppItemRcv.setAdapter(appDisplayItemAdapter);
    }

    public void autoScroll() {
        final int speedScroll = 0;
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            int count = 0;

            @Override
            public void run() {
                if (count < appDisplayItemAdapter.getItemCount()) {
                    activityAppLoginBinding.displayAppItemRcv.smoothScrollToPosition(++count);
                    handler.postDelayed(this, speedScroll);
                } else {
                    count = 0;
                }
            }
        };
        handler.postDelayed(runnable, speedScroll);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_withemail_linear:
                Intent loginemail = new Intent(mContext, LoginwithEmailActivity.class);
                loginemail.putExtra("whereTocomeLogin", getIntent().getStringExtra("whereTocomeLogin"));
                startActivity(loginemail);
                finish();
                break;

            case R.id.skip_linear:
                Utils.handleClickEvent(mContext, activityAppLoginBinding.skipLinear);
                Utils.setPref(mContext, "IsSkipLogin", "1");
                Intent DashboardIntent = new Intent(mContext, DashboardActivity.class);
                startActivity(DashboardIntent);
                finish();
                break;

            case R.id.loginwithgoogle_linear:
//                Utils.handleClickEvent(mContext, activityAppLoginBinding.loginwithgoogleLinear);
                showProgressDialog();
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
                break;

            case R.id.loginwithfacebook_linear:
                Utils.handleClickEvent(mContext, activityAppLoginBinding.loginwithfacebookLinear);
                if (personEmailStr != null) {
                    if (!personEmailStr.equalsIgnoreCase("")) {
                        facebooklogout();
                    } else {
                        activityAppLoginBinding.loginButton.performClick();
                    }
                } else {
                    activityAppLoginBinding.loginButton.performClick();
                }
//
                break;

            case R.id.login_button:
                facebookLogin();
                break;

            case R.id.terms_of_service_linear:
                privacyscreenGo("Terms of Service");
                break;
            case R.id.privacy_policy_linear:
                privacyscreenGo("Privacy Policy");
                break;
            case R.id.content_policy_linear:
                privacyscreenGo("Content Policy");
                break;
        }
    }

    public void privacyscreenGo(String text) {
        Intent privacypolicyIntent = new Intent(mContext, MoreInformationActivity.class);
        privacypolicyIntent.putExtra("Story Heading", text);
        privacypolicyIntent.putExtra("StroyUrl", AppConfiguration.TERMSURL);
        privacypolicyIntent.putExtra("whereTocome", "aboutus");
        privacypolicyIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(privacypolicyIntent);
        startActivity(privacypolicyIntent);
    }


    //    get firebase token
    public void getFCMTOken() {
        android_id = Settings.Secure.getString(AppLoginActivity.this.getContentResolver(),
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

                        // Get new Instance HomeTempleteIDModel token
                        token = task.getResult().getToken();

                        Log.d("token", token);
                        //getting old saved token
                        String old_token = Utils.getPref(getApplicationContext(), "registration_id");

                        if (!old_token.equalsIgnoreCase(token)) {
                            Utils.setPref(getApplicationContext(), "registration_id", token);
                            // sendRegistrationToServer(re
                            // reshedToken);
                        }


                    }
                });
    }

    //    User Login with facebook
    public void facebookLogin() {
//        AppEventsLogger.activateApp(this);
        setAutoLogAppEventsEnabled(false);
        callbackManager = CallbackManager.Factory.create();
        aQuery = new AQuery(this);

        callbackManager = CallbackManager.Factory.create();

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldToken, AccessToken newToken) {

            }
        };

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {

            }
        };

        accessTokenTracker.startTracking();
        profileTracker.startTracking();

        activityAppLoginBinding.loginButton.setReadPermissions(Arrays.asList("public_profile", "email"));  //"user_birthday", "user_friends"
        activityAppLoginBinding.loginButton.registerCallback(callbackManager, callback);

    }

    public void facebooklogout() {
        new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/",
                null, HttpMethod.DELETE, new GraphRequest
                .Callback() {
            @Override
            public void onCompleted(GraphResponse graphResponse) {

                LoginManager.getInstance().logOut();

                personEmailStr="";
            }
        }).executeAsync();
    }

    //    User Login with google
    public void googleLogin() {
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getResources().getString(R.string.web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//        Utils.ping(mContext,connectionResult.toString());
    }

    //    give google sign in result
    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            hideProgressDialog();
            GoogleSignInAccount acct = result.getSignInAccount();

            Log.e(TAG, "display name: " + acct.getDisplayName()
                    + "Family name:" + acct.getFamilyName()
                    + "Given name:" + acct.getGivenName());
            if (acct.getDisplayName() != null) {
                personNameStr = acct.getDisplayName();
            } else {
                personNameStr = "";
            }

            if (acct.getPhotoUrl() != null) {
                personImageStr = acct.getPhotoUrl().toString();
            } else {
                personImageStr = "";
            }

            personEmailStr = acct.getEmail();

            Log.e(TAG, "Name: " + personNameStr + ", email: " + personEmailStr
                    + ", Image: " + personImageStr);
            callGoogleSignUp();

        } else {
            hideProgressDialog();
//            Utils.ping(mContext, "error occured");
        }


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (callbackManager != null) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }


//        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            handleSignInResult(result);
        }

    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.verify_credential));
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
    protected void onDestroy() {
        if (profileTracker != null & accessTokenTracker != null) {
            profileTracker.stopTracking();
            accessTokenTracker.stopTracking();
        }

        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if ((mProgressDialog != null) && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    //    Use for google signup data entery in database
    public void callGoogleSignUp() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), AppLoginActivity.this);
            return;
        }

        Utils.showDialog(mContext);
        ApiHandler.getApiService().getLoginwithGoogle(getGoogleSignUpData(), new retrofit.Callback<LogginModel>() {
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
                        Utils.setPref(mContext, "LoginType", "Gmail");
                        Utils.storeLoginData(loginModel.getData(), mContext);
                        Utils.storeCurrentLocationData(loginModel.getCurrentLocation(), mContext);
                        Utils.storeLoginOtherData(loginModel.getOtherData(), mContext);
                        if (getIntent().getStringExtra("whereTocomeLogin") != null) {
                            if (getIntent().getStringExtra("whereTocomeLogin").equalsIgnoreCase("more")) {
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
                facebooklogout();
                Utils.ping(mContext, getString(R.string.something_wrong));
            }
        });

    }

    private Map<String, String> getGoogleSignUpData() {
        Map<String, String> map = new HashMap<>();
        map.put("email", personEmailStr);
        map.put("Name", personNameStr);
        map.put("Image", personImageStr);
        map.put("TokenId", Utils.getPref(mContext, "registration_id"));
        map.put("ModelName", Utils.getDeviceName());
        return map;
    }

    //    Use for facebook signup data entery in database
    public void callFacebookSignUp() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), AppLoginActivity.this);
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
                        if (getIntent().getStringExtra("whereTocomeLogin") != null) {
                            if (getIntent().getStringExtra("whereTocomeLogin").equalsIgnoreCase("more")) {
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
        map.put("PhoneNo", "");
        map.put("CountryISOCode", "");
        map.put("CountryDialCode", "");
        return map;
    }

    @Override
    public void onBackPressed() {
        if (getIntent().getStringExtra("whereTocomeLogin") != null) {
            if (getIntent().getStringExtra("whereTocomeLogin").equalsIgnoreCase("profile")) {
                Intent DashboardIntent = new Intent(mContext, DashboardActivity.class);
//                                DashboardIntent.putExtra("whichPageRun", "4");
                startActivity(DashboardIntent);
                finish();
            }else{
                finish();
            }
        }else{
            finish();
        }
        super.onBackPressed();
    }
}
