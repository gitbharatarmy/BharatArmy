package com.bharatarmy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityFacebookLoginWithNoEmailBinding;

public class FacebookLoginWithNoEmailActivity extends AppCompatActivity implements View.OnClickListener {
  ActivityFacebookLoginWithNoEmailBinding activityFacebookLoginWithNoEmailBinding;
  Context mContext;
  String personNameStr,personImageStr,facebookfirstNameStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityFacebookLoginWithNoEmailBinding= DataBindingUtil.setContentView(this,R.layout.activity_facebook_login_with_no_email);
         mContext=FacebookLoginWithNoEmailActivity.this;

         init();
         setListiner();

    }

    public void init(){
        personNameStr=getIntent().getStringExtra("personName");
        personImageStr=getIntent().getStringExtra("personImage");
        facebookfirstNameStr=getIntent().getStringExtra("firstName");

        Utils.setImageInImageView(personImageStr,activityFacebookLoginWithNoEmailBinding.profileImage,mContext);
        activityFacebookLoginWithNoEmailBinding.nameEdt.setText(personNameStr);
        activityFacebookLoginWithNoEmailBinding.continueBtn.setText("Continue As "+facebookfirstNameStr);
    }

    public void setListiner(){
        activityFacebookLoginWithNoEmailBinding.backImg.setOnClickListener(this);
        activityFacebookLoginWithNoEmailBinding.continueBtn.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
      switch (v.getId()){
          case R.id.back_img:
              Intent returnAppLoginIntent=new Intent(mContext,AppLoginActivity.class);
              startActivity(returnAppLoginIntent);
              finish();
              break;
          case R.id.continue_btn:
              break;
          case R.id.email_edt:
              Utils.scrollScreen(activityFacebookLoginWithNoEmailBinding.signupScrollView);
              break;
      }
    }


    public void retrivetheloginData(){

    }
}
