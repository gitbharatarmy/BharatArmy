package com.bharatarmy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityMyProfileBinding;

public class MyProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private Context mContext;
    ActivityMyProfileBinding activityMyProfileBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMyProfileBinding= DataBindingUtil.setContentView(this,R.layout.activity_my_profile);

        mContext= MyProfileActivity.this;

        setListiner();
        setDataValue();


    }

    public void setListiner() {
        activityMyProfileBinding.backImg.setOnClickListener(this);
        activityMyProfileBinding.editLinear.setOnClickListener(this);
    }

    public void setDataValue() {

        activityMyProfileBinding.toolbarTitleTxt.setText("Member Profile");

//        activityMyProfileBinding.userShowTxt.setText(Utils.getPref(mContext,"LoginUserName"));
//        activityMyProfileBinding.emailShowTxt.setText(Utils.getPref(mContext,"LoginEmailId"));
//        activityMyProfileBinding.phoneShowTxt.setText(Utils.getPref(mContext,"LoginPhoneNo"));
//        if (Utils.getPref(mContext,"Gender").equalsIgnoreCase("1")){
//            activityMyProfileBinding.genderShowTxt.setText("Male");
//        }else if(Utils.getPref(mContext,"Gender").equalsIgnoreCase("2")){
//            activityMyProfileBinding.genderShowTxt.setText("Female");
//        }else{
//            activityMyProfileBinding.genderShowTxt.setText("");
//        }
//
//        Utils.setImageInImageView(Utils.getPref(mContext,"LoginProfilePic"),activityMyProfileBinding.profileImage,mContext);
//        Log.d("emailid",Utils.getPref(mContext,"LoginEmailId"));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                MyProfileActivity.this.finish();
                break;
            case R.id.edit_linear:
                Intent intent = new Intent(mContext, EditProfileActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        MyProfileActivity.this.finish();
        super.onBackPressed();
    }
}
