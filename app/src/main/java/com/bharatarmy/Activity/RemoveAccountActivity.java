package com.bharatarmy.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.bharatarmy.R;
import com.bharatarmy.UploadService;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityRemoveAccountBinding;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class RemoveAccountActivity extends AppCompatActivity implements View.OnClickListener,GoogleApiClient.OnConnectionFailedListener {

    ActivityRemoveAccountBinding activityRemoveAccountBinding;
    Context mContext;
    private GoogleApiClient mGoogleApiClient;
    private GoogleSignInOptions gso;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityRemoveAccountBinding = DataBindingUtil.setContentView(this, R.layout.activity_remove_account);

        mContext = RemoveAccountActivity.this;
        init();
        setLisitiner();
    }

    public void init() {
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();


        mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                .enableAutoManage(RemoveAccountActivity.this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mGoogleApiClient.connect();
    }

    public void setLisitiner() {
        activityRemoveAccountBinding.backImg.setOnClickListener(this);
        activityRemoveAccountBinding.yesBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                whereToBack();
                break;
            case R.id.yes_btn:
               showThanyouDialog();
                break;
        }
    }


    public void whereToBack() {
        finish();
    }


    public void showThanyouDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
        LayoutInflater inflater = RemoveAccountActivity.this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.thankyou_dialog_item, null);
        dialogBuilder.setView(dialogView);
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        TextView dialog_headertxt = (TextView) dialogView.findViewById(R.id.dialog_headertxt);
        TextView dialog_descriptiontxt = (TextView) dialogView.findViewById(R.id.dialog_descriptiontxt);
        TextView hometxt = (TextView) dialogView.findViewById(R.id.home_txt);

        dialog_headertxt.setText("");
        dialog_descriptiontxt.setText("Your Account Removal Is in Process. Once done you will get mail");
        hometxt.setText("Exit Application");

        hometxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (Utils.getPref(mContext, "LoginType") != null) {
                        if (Utils.getPref(mContext, "LoginType").equalsIgnoreCase("Email")) {
                            emailSignOut();
                        } else if (Utils.getPref(mContext, "LoginType").equalsIgnoreCase("Facebook")) {
                            facebookSignOut();
                        } else if (Utils.getPref(mContext, "LoginType").equalsIgnoreCase("Gmail")) {
                            googleSignOut();
                        }
                    }
                    alertDialog.dismiss();


                } catch (Exception e) {

                }
            }
        });
        try {
            alertDialog.show();
        } catch (Exception e) {

        }

    }

    public void logoutFuncation() {
        // Write your code here to execute after dialog
        Utils.removeLoginData(mContext);
        Utils.setPref(mContext, "IsSkipLogin", "");
        Utils.setPref(mContext, "IsLoginUser", "");
        Utils.setPref(mContext, "UserName", "");
        Utils.setPref(mContext, "entryType", "");
        Utils.setPref(mContext, "feedbackgiveflag", "");
        Intent ilogin = new Intent(mContext, AppLoginActivity.class);
        Utils.whereTocomeLogin = "Remove Account";
        startActivity(ilogin);
        finish();
    }
    public void emailSignOut() {
        // Write your code here to execute after dialog
        logoutFuncation();
    }
    public void googleSignOut() {
        if (mGoogleApiClient.isConnected()) {
            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                    new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status status) {
//                            // Write your code here to execute after dialog
                            logoutFuncation();
                        }
                    });


        } else {
            Utils.ping(mContext, "error occured");
        }
    }


    public void facebookSignOut() {
        new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/",
                null, HttpMethod.DELETE, new GraphRequest
                .Callback() {
            @Override
            public void onCompleted(GraphResponse graphResponse) {

                LoginManager.getInstance().logOut();

                // Write your code here to execute after dialog
                logoutFuncation();

            }
        }).executeAsync();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onPause() {
        super.onPause();
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.stopAutoManage(RemoveAccountActivity.this);
            mGoogleApiClient.disconnect();
        }
    }
}
