package com.bharatarmy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bharatarmy.Fragment.MoreFragment;
import com.bharatarmy.Interfaces.MorestoryClick;
import com.bharatarmy.Models.MyScreenChnagesModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityMyProfileBinding;

import org.greenrobot.eventbus.EventBus;

public class MyProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private Context mContext;
    ActivityMyProfileBinding activityMyProfileBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMyProfileBinding = DataBindingUtil.setContentView(this, R.layout.activity_my_profile);

        mContext = MyProfileActivity.this;

        setListiner();
        setDataValue();


    }

    public void setListiner() {
        activityMyProfileBinding.backImg.setOnClickListener(this);
        activityMyProfileBinding.editLinear.setOnClickListener(this);
    }

    public void setDataValue() {

        activityMyProfileBinding.toolbarTitleTxt.setText("Member Profile");

        if (Utils.retriveLoginData(mContext) != null) {
            activityMyProfileBinding.userShowTxt.setText(Utils.retriveLoginData(mContext).getName());
            activityMyProfileBinding.emailShowTxt.setText(Utils.retriveLoginData(mContext).getEmail());
            activityMyProfileBinding.phoneShowTxt.setText(Utils.retriveLoginData(mContext).getPhoneNo());


            Utils.setImageInImageView(Utils.retriveLoginData(mContext).getProfilePicUrl(), activityMyProfileBinding.profileImage, mContext);
            if (Utils.retriveLoginData(mContext).getGender().equals(1)) {
                activityMyProfileBinding.genderShowTxt.setText("Male");
            } else if (Utils.retriveLoginData(mContext).getGender().equals(2)) {
                activityMyProfileBinding.genderShowTxt.setText("Female");
            } else {
                activityMyProfileBinding.genderShowTxt.setText("-");
            }


            if (!Utils.retriveLoginData(mContext).getAddressline1().equalsIgnoreCase("")) {
                activityMyProfileBinding.addressShowTxt.setText(Utils.retriveLoginData(mContext).getAddressline1() +
                        ", " + Utils.retriveLoginData(mContext).getAddressline2()+
                        ", "+Utils.retriveLoginData(mContext).getArea()+
                        ", "+Utils.retriveLoginData(mContext).getStrCityName()+
                        ", "+Utils.retriveLoginData(mContext).getStrState()+
                        ", "+Utils.getCountryNameUsingCountryCode(Utils.retriveLoginData(mContext).getCountryISOCode())+
                        ", "+Utils.retriveLoginData(mContext).getPincode());
            }
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                EventBus.getDefault().post(new MyScreenChnagesModel("change"));
                finish();

                break;
            case R.id.edit_linear:
                Intent intent = new Intent(mContext, EditProfileActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        EventBus.getDefault().post(new MyScreenChnagesModel("change"));
        finish();
        super.onBackPressed();
    }
}
