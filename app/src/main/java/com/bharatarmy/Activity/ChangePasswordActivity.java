package com.bharatarmy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityChangePasswordBinding;

public class ChangePasswordActivity extends AppCompatActivity {

    ActivityChangePasswordBinding activityChangePasswordBinding;
    Context mContext;
    String newPasswordStr,confirmPasswordStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityChangePasswordBinding= DataBindingUtil.setContentView(this,R.layout.activity_change_password);

        mContext=ChangePasswordActivity.this;

        init();
        setListiner();
    }

    public void init(){}
    public void setListiner(){
        activityChangePasswordBinding.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               getChangePasswordData();
            }
        });
    }

    public void getChangePasswordData(){
        newPasswordStr=activityChangePasswordBinding.newpasswordEdt.getText().toString();
        confirmPasswordStr=activityChangePasswordBinding.confirmpasswordEdt.getText().toString();
        if(!newPasswordStr.equalsIgnoreCase("")){
            if (newPasswordStr.length()>=5 && newPasswordStr.length()<=10){
                if (newPasswordStr.equalsIgnoreCase(confirmPasswordStr)) {
                    Intent dashboardIntent=new Intent(mContext,DashboardActivity.class);
                    startActivity(dashboardIntent);
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
}
