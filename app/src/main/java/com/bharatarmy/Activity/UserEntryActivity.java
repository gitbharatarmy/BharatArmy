package com.bharatarmy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bharatarmy.Models.LogginModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.ApiHandler;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityUserEntryBinding;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class UserEntryActivity extends BaseActivity implements View.OnClickListener {
    ActivityUserEntryBinding activityUserEntryBinding;
    Context mContext;
    String userEmailStr="", userPasswordStr="", userNameStr="",userIdStr="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUserEntryBinding = DataBindingUtil.setContentView(this, R.layout.activity_user_entry);

        mContext = UserEntryActivity.this;



        init();
        setListiner();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save_btn:
                getInsertDataValue();
                break;
            case R.id.back_img:
                Intent userListIntent=new Intent(mContext,DisplayAddedUserActivity.class);
                userListIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(userListIntent);
                finish();
                break;
        }
    }

    public void init(){
        activityUserEntryBinding.toolbarTitleTxt.setText("User Entry");

        if (getIntent().getStringExtra("userEditorNew")!=null){
            if (getIntent().getStringExtra("userEditorNew").equalsIgnoreCase("edit")){
                activityUserEntryBinding.nameEdt.setText(getIntent().getStringExtra("userName"));
                activityUserEntryBinding.emailEdt.setText(getIntent().getStringExtra("userEmail"));
                userIdStr=getIntent().getStringExtra("userId");
                activityUserEntryBinding.emailEdt.setFocusable(false);
                activityUserEntryBinding.emailEdt.setClickable(false);
                activityUserEntryBinding.emailEdt.setBackground(getResources().getDrawable(R.drawable.not_edit_value_bg));
            }else{
                userIdStr="0";
            }
        }
    }

    public void setListiner() {
        activityUserEntryBinding.saveBtn.setOnClickListener(this);
activityUserEntryBinding.backImg.setOnClickListener(this);
        activityUserEntryBinding.passwordEdt.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                   getInsertDataValue();
                }
                return false;
            }
        });
    }

    public void getInsertDataValue() {
        userNameStr = activityUserEntryBinding.nameEdt.getText().toString();
        userEmailStr = activityUserEntryBinding.emailEdt.getText().toString();
        userPasswordStr = activityUserEntryBinding.passwordEdt.getText().toString();

        if (!userNameStr.equalsIgnoreCase("")) {
            if (!userEmailStr.equalsIgnoreCase("")) {
                if (Utils.isValidEmailId(userEmailStr)) {
                    if (getIntent().getStringExtra("userEditorNew").equalsIgnoreCase("edit")) {
                        if (!userPasswordStr.equalsIgnoreCase("")) {
                            if (userPasswordStr.length() >= 5 && userPasswordStr.length() <= 10) {
                                getInsertUserData();
                            } else {
                                activityUserEntryBinding.passwordEdt.setError("Password Length must be greter than 5 or less than 10");
                            }
                        } else {
                            getInsertUserData();
                        }
                    }else{
                        if (!userPasswordStr.equalsIgnoreCase("")) {
                            if (userPasswordStr.length() >= 5 && userPasswordStr.length() <= 10) {
                                getInsertUserData();
                            } else {
                                activityUserEntryBinding.passwordEdt.setError("Password Length must be greter than 5 or less than 10");
                            }
                        } else {
                            activityUserEntryBinding.passwordEdt.setError("Password is required");
                        }
                    }
                } else {
                    activityUserEntryBinding.emailEdt.setError("Invalid Email Address");
                }
            } else {
                activityUserEntryBinding.emailEdt.setError("Email Address is required");
            }
        }else{
            activityUserEntryBinding.nameEdt.setError("Name is required");
        }
    }

    // call the User Entry
    public void getInsertUserData() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), UserEntryActivity.this);
            return;
        }

        Utils.showDialog(mContext);
        ApiHandler.getApiService().getInsertDataEntryUser(getuserentryData(), new retrofit.Callback<LogginModel>() {
            @Override
            public void success(LogginModel userDataModel, Response response) {
                Utils.dismissDialog();
                if (userDataModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (userDataModel.getIsValid() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (userDataModel.getIsValid() == 0) {
                    Utils.ping(mContext, userDataModel.getMessage());
                    return;
                }
                if (userDataModel.getIsValid() == 1) {
                       if (userDataModel.getData().getBAMemberId()<0){
                           Utils.ping(mContext,userDataModel.getData().getMemberName());
                       }else {
                           Utils.ping(mContext,"Sucessfully enter");
                           activityUserEntryBinding.nameEdt.setText("");
                           activityUserEntryBinding.emailEdt.setText("");
                           activityUserEntryBinding.passwordEdt.setText("");
                           activityUserEntryBinding.emailEdt.setBackground(null);
                           Intent userListIntent=new Intent(mContext,DisplayAddedUserActivity.class);
                           userListIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                           startActivity(userListIntent);
                           finish();
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

    private Map<String, String> getuserentryData() {
        Map<String, String> map = new HashMap<>();
        map.put("Id",userIdStr);
        map.put("Name", userNameStr);
        map.put("EmailId", userEmailStr);
        map.put("Password", userPasswordStr);
        map.put("AddedById", String.valueOf(Utils.getAppUserId(mContext)));
        return map;
    }

    @Override
    public void onBackPressed() {
        Intent userListIntent=new Intent(mContext,DisplayAddedUserActivity.class);
        userListIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(userListIntent);
        finish();
    }
}
