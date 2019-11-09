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
import com.bharatarmy.databinding.ActivityChangePasswordBinding;

import java.util.HashMap;
import java.util.Map;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class ChangePasswordActivity extends AppCompatActivity {

    ActivityChangePasswordBinding activityChangePasswordBinding;
    Context mContext;
    String newPasswordStr,confirmPasswordStr,memberIdStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityChangePasswordBinding= DataBindingUtil.setContentView(this,R.layout.activity_change_password);

        mContext=ChangePasswordActivity.this;

        init();
        setListiner();
    }

    public void init(){
        if (getIntent().getStringExtra("memberId")!=null){
            memberIdStr=getIntent().getStringExtra("memberId");
        }
        
    }
    public void setListiner(){
        activityChangePasswordBinding.newpasswordEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              activityChangePasswordBinding.changepasswordScrollView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        View lastChild =  activityChangePasswordBinding.changepasswordScrollView.getChildAt( activityChangePasswordBinding.changepasswordScrollView.getChildCount() - 1);
                        int bottom = lastChild.getBottom() +  activityChangePasswordBinding.changepasswordScrollView.getPaddingBottom();
                        int sy =  activityChangePasswordBinding.changepasswordScrollView.getScrollY();
                        int sh =  activityChangePasswordBinding.changepasswordScrollView.getHeight();
                        int delta = bottom - (sy + sh);
                        activityChangePasswordBinding.changepasswordScrollView.smoothScrollBy(0, delta);
                    }
                }, 200);
            }
        });

        activityChangePasswordBinding.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.handleClickEvent(mContext,activityChangePasswordBinding.submitBtn);
               getChangePasswordFillData();
            }
        });
        activityChangePasswordBinding.confirmpasswordEdt.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    getChangePasswordFillData();
                }
                return false;
            }
        });
        activityChangePasswordBinding.backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getIntent().getStringExtra("whereTocomeLogin")!=null){
                    finish();
                }else{
                    Intent iLogin = new Intent(mContext, LoginwithEmailActivity.class);
                    startActivity(iLogin);
                    finish();
                }
            }
        });
    }

    public void getChangePasswordFillData(){
        newPasswordStr=activityChangePasswordBinding.newpasswordEdt.getText().toString();
        confirmPasswordStr=activityChangePasswordBinding.confirmpasswordEdt.getText().toString();
        if(!newPasswordStr.equalsIgnoreCase("")){
            if (newPasswordStr.length()>=5 && newPasswordStr.length()<=10){
                if (newPasswordStr.equalsIgnoreCase(confirmPasswordStr)) {
                   if (!memberIdStr.equalsIgnoreCase("")){
                       getChangePassword();
                   }else{
                       Utils.ping(mContext,"memberId blank");
                   }
                }else{
                    activityChangePasswordBinding.confirmpasswordEdt.setError("confirm password and new password must be same");
                }
            }else{
                activityChangePasswordBinding.newpasswordEdt.setError("password Length must be greter than 5 or less than 10");
            }

        }else{
            activityChangePasswordBinding.newpasswordEdt.setError("blank filed not allowed");
        }
    }

    public void getChangePassword() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), ChangePasswordActivity.this);
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
                        Utils.storeLoginData(changeModel.getData(),mContext);
                        Utils.storeLoginOtherData(changeModel.getOtherData(), mContext);
                        if (getIntent().getStringExtra("whereTocomeLogin")!=null){
                            if(getIntent().getStringExtra("whereTocomeLogin").equalsIgnoreCase("more")) {
                                Utils.showThanyouDialog(ChangePasswordActivity.this,"changePassword|InApp");
                            }else{
                                Utils.showThanyouDialog(ChangePasswordActivity.this,"changePassword|finishApp");
                            }
                        }else{
                            Utils.showThanyouDialog(ChangePasswordActivity.this,"changePassword");
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

    private Map<String, String> getChangePasswordData() {
        Map<String, String> map = new HashMap<>();
        map.put("NewPassword", newPasswordStr);
        map.put("MemberId",memberIdStr);
        return map;
    }
}
