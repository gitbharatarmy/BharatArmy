package com.bharatarmy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bharatarmy.Models.MyScreenChnagesModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityMyProfileBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MyProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private Context mContext;
    ActivityMyProfileBinding activityMyProfileBinding;
    String countryFlagStr;
    private ProgressDialog mProgressDialog;

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
            if (Utils.retriveLoginData(mContext).getName() != null) {
                activityMyProfileBinding.userShowTxt.setText(Utils.retriveLoginData(mContext).getName());
            }
            if (Utils.retriveLoginData(mContext).getEmail() != null) {
                activityMyProfileBinding.emailShowTxt.setText(Utils.retriveLoginData(mContext).getEmail());
            }
            if (Utils.retriveLoginData(mContext).getCountryISOCode() != null) {
                if (!Utils.retriveLoginData(mContext).getCountryISOCode().equalsIgnoreCase("")) {
                    activityMyProfileBinding.countryFlagImg.setVisibility(View.VISIBLE);
                    countryFlagStr = AppConfiguration.FLAG_URL + Utils.retriveLoginData(mContext).getCountryISOCode() + ".png";
                    Utils.setImageInImageView(countryFlagStr, activityMyProfileBinding.countryFlagImg, mContext);
                } else {
                    activityMyProfileBinding.countryFlagImg.setVisibility(View.GONE);
                }
            } else {
                if (Utils.retriveCurrentLocationData(mContext) != null) {
                    if (Utils.retriveCurrentLocationData(mContext).getIsoCode() != null) {
                        if (!Utils.retriveCurrentLocationData(mContext).getIsoCode().equalsIgnoreCase("")) {
                            activityMyProfileBinding.countryFlagImg.setVisibility(View.VISIBLE);
                            countryFlagStr = AppConfiguration.FLAG_URL + Utils.retriveCurrentLocationData(mContext).getIsoCode() + ".png";
                            Utils.setImageInImageView(countryFlagStr, activityMyProfileBinding.countryFlagImg, mContext);
                        } else {
                            activityMyProfileBinding.countryFlagImg.setVisibility(View.GONE);
                        }
                    }
                } else {
                    activityMyProfileBinding.countryFlagImg.setVisibility(View.GONE);
                }

            }
            if (Utils.retriveLoginData(mContext).getPhoneNo() != null && !Utils.retriveLoginData(mContext).getPhoneNo().equalsIgnoreCase("")) {
                activityMyProfileBinding.phoneShowTxt.setText(Utils.retriveLoginData(mContext).getPhoneNo());
            }
            if (Utils.retriveLoginData(mContext).getProfilePicUrl() != null) {
                Utils.setImageInImageView(Utils.retriveLoginData(mContext).getProfilePicUrl(), activityMyProfileBinding.profileImage, mContext);
            }
            if (Utils.retriveLoginData(mContext).getGender() != null) {
                if (Utils.retriveLoginData(mContext).getGender().equals(1)) {
                    activityMyProfileBinding.genderShowTxt.setText("Male");
                } else if (Utils.retriveLoginData(mContext).getGender().equals(2)) {
                    activityMyProfileBinding.genderShowTxt.setText("Female");
                } else {
                    activityMyProfileBinding.genderShowTxt.setText("-");
                }
            }

            if (Utils.retriveLoginData(mContext).getAddressline1() != null) {
                if (Utils.retriveLoginData(mContext).getAddressline2() != null) {
                    if (Utils.retriveLoginData(mContext).getArea() != null) {
                        if (Utils.retriveLoginData(mContext).getStrCityName() != null) {
                            if (Utils.retriveLoginData(mContext).getStrState() != null) {
                                if (Utils.retriveLoginData(mContext).getCountryISOCode() != null) {
                                    if (Utils.retriveLoginData(mContext).getPincode() != null) {
                                        if (!Utils.retriveLoginData(mContext).getAddressline1().equalsIgnoreCase("")) {
                                            activityMyProfileBinding.addressShowTxt.setText(Utils.retriveLoginData(mContext).getAddressline1() +
                                                    ", " + Utils.retriveLoginData(mContext).getAddressline2() +
                                                    ", " + Utils.retriveLoginData(mContext).getArea() +
                                                    ", " + Utils.retriveLoginData(mContext).getStrCityName() +
                                                    ", " + Utils.retriveLoginData(mContext).getStrState() +
                                                    ", " + Utils.getCountryNameUsingCountryCode(Utils.retriveLoginData(mContext).getCountryISOCode()) +
                                                    ", " + Utils.retriveLoginData(mContext).getPincode());
                                        }
                                    }
                                }
                            }
                        }
                    }
                }


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
                showProgressDialog();
                Utils.handleClickEvent(mContext, activityMyProfileBinding.editLinear);
                Intent intent = new Intent(mContext, EditProfileActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
//                overridePendingTransition(R.anim.slide_out_right_new, 0);
                break;
        }
    }



    @Override
    public void onBackPressed() {
        EventBus.getDefault().post(new MyScreenChnagesModel("change"));
        finish();
        super.onBackPressed();
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }
            mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

}
