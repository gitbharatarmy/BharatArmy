package com.bharatarmy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.bharatarmy.Models.LogginModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.ApiHandler;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityProfileChangePasswordBinding;

import java.util.HashMap;
import java.util.Map;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class ProfileChangePasswordActivity extends AppCompatActivity {

    ActivityProfileChangePasswordBinding activityProfileChangePasswordBinding;
    Context mContext;
    String newPasswordStr, confirmPasswordStr, memberIdStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityProfileChangePasswordBinding = DataBindingUtil.setContentView(this, R.layout.activity_profile_change_password);

        mContext = ProfileChangePasswordActivity.this;

        init();
        setListiner();
    }

    public void init() {
       memberIdStr= String.valueOf(Utils.getAppUserId(mContext));

    }

    public void setListiner() {
        activityProfileChangePasswordBinding.newpasswordEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityProfileChangePasswordBinding.changepasswordScrollView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        View lastChild = activityProfileChangePasswordBinding.changepasswordScrollView.getChildAt(activityProfileChangePasswordBinding.changepasswordScrollView.getChildCount() - 1);
                        int bottom = lastChild.getBottom() + activityProfileChangePasswordBinding.changepasswordScrollView.getPaddingBottom();
                        int sy = activityProfileChangePasswordBinding.changepasswordScrollView.getScrollY();
                        int sh = activityProfileChangePasswordBinding.changepasswordScrollView.getHeight();
                        int delta = bottom - (sy + sh);
                        activityProfileChangePasswordBinding.changepasswordScrollView.smoothScrollBy(0, delta);
                    }
                }, 200);
            }
        });

        activityProfileChangePasswordBinding.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.handleClickEvent(mContext, activityProfileChangePasswordBinding.submitBtn);
                getChangePasswordFillData();
            }
        });
        activityProfileChangePasswordBinding.confirmpasswordEdt.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    getChangePasswordFillData();
                }
                return false;
            }
        });
        activityProfileChangePasswordBinding.backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wheretoBack();
            }
        });
    }


    @Override
    public void onBackPressed() {
        wheretoBack();
        super.onBackPressed();
    }

    public void wheretoBack(){
        Intent iLogin = new Intent(mContext, ProfileSettingActivity.class);
        startActivity(iLogin);
        finish();
    }

    public void getChangePasswordFillData() {
        newPasswordStr = activityProfileChangePasswordBinding.newpasswordEdt.getText().toString();
        confirmPasswordStr = activityProfileChangePasswordBinding.confirmpasswordEdt.getText().toString();
        if (!newPasswordStr.equalsIgnoreCase("")) {
            if (newPasswordStr.length() >= 5 && newPasswordStr.length() <= 10) {
                if (newPasswordStr.equalsIgnoreCase(confirmPasswordStr)) {
                    if (!memberIdStr.equalsIgnoreCase("")) {
                        getChangePassword();
                    } else {
                        Utils.ping(mContext, "memberId blank");
                    }
                } else {
                    activityProfileChangePasswordBinding.confirmpasswordEdt.setError("confirm password and new password must be same");
                }
            } else {
                activityProfileChangePasswordBinding.newpasswordEdt.setError("password Length must be greter than 5 or less than 10");
            }

        } else {
            activityProfileChangePasswordBinding.newpasswordEdt.setError("blank filed not allowed");
        }
    }

    public void getChangePassword() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), ProfileChangePasswordActivity.this);
            return;
        }

        Utils.showDialog(mContext);
        ApiHandler.getApiService().getChangePassword(getChangePasswordData(), new retrofit.Callback<LogginModel>() {
            @Override
            public void success(LogginModel changeModel, Response response) {
                Utils.dismissDialog();

                if (changeModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (changeModel.getIsValid() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (changeModel.getIsValid() == 0) {
                    Utils.ping(mContext, changeModel.getMessage());
                    return;
                }
                if (changeModel.getIsValid() == 1) {
                    if (changeModel.getData() != null) {

                        Utils.setPref(mContext, "IsLoginUser", "1");
                        Utils.storeLoginData(changeModel.getData(), mContext);
                        Utils.storeCurrentLocationData(changeModel.getCurrentLocation(),mContext);
                        Utils.storeLoginOtherData(changeModel.getOtherData(), mContext);
                            Utils.showThanyouDialog(ProfileChangePasswordActivity.this, "changePassword|finishApp");
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

    private Map<String, String> getChangePasswordData() {
        Map<String, String> map = new HashMap<>();
        map.put("NewPassword", newPasswordStr);
        map.put("MemberId", memberIdStr);
        return map;
    }
}
