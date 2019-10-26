package com.bharatarmy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bharatarmy.Adapter.DisplaySFAUserAdapter;
import com.bharatarmy.Models.ImageDetailModel;
import com.bharatarmy.Models.ImageMainModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.ApiHandler;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityDisplaySfauserBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class DisplaySFAUserActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityDisplaySfauserBinding activityDisplaySfauserBinding;
    Context mContext;
    DisplaySFAUserAdapter displaySFAUserAdapter;
    List<ImageDetailModel> sfausermodellist;
    ArrayList<String> schoolName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDisplaySfauserBinding = DataBindingUtil.setContentView(this, R.layout.activity_display_sfauser);
        mContext = DisplaySFAUserActivity.this;

        init();
        setListiner();
    }

    public void init() {
        if (Utils.retriveLoginData(mContext) != null) {
            if (Utils.retriveLoginData(mContext).getMemberType().equalsIgnoreCase(",3,")) {
                activityDisplaySfauserBinding.backImg.setVisibility(View.GONE);
                activityDisplaySfauserBinding.logoutImg.setVisibility(View.VISIBLE);
                activityDisplaySfauserBinding.shimmerViewContainer.setVisibility(View.GONE);
                activityDisplaySfauserBinding.shimmerViewContainer.startShimmerAnimation();
                activityDisplaySfauserBinding.SFAUserInfoRcv.setVisibility(View.GONE);
                activityDisplaySfauserBinding.noOfRecordrel.setVisibility(View.VISIBLE);
            }else{
                activityDisplaySfauserBinding.backImg.setVisibility(View.VISIBLE);
                activityDisplaySfauserBinding.logoutImg.setVisibility(View.GONE);
                activityDisplaySfauserBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
                activityDisplaySfauserBinding.shimmerViewContainer.startShimmerAnimation();
                activityDisplaySfauserBinding.SFAUserInfoRcv.setVisibility(View.GONE);
                activityDisplaySfauserBinding.noOfRecordrel.setVisibility(View.GONE);
            }
        }

        activityDisplaySfauserBinding.toolbarTitleTxt.setText("SFA Data Entry");

        callDisplaySFAUserData();
    }



    public void setListiner() {
        activityDisplaySfauserBinding.backImg.setOnClickListener(this);
        activityDisplaySfauserBinding.sfanewUserfabLinear.setOnClickListener(this);
        activityDisplaySfauserBinding.backImg.setOnClickListener(this);
        activityDisplaySfauserBinding.logoutImg.setOnClickListener(this);
    }

    public void callDisplaySFAUserData() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), DisplaySFAUserActivity.this);
            return;
        }

//        Utils.showDialog(mContext);
        ApiHandler.getApiService().getDataEnter(getDisplayUserData(), new retrofit.Callback<ImageMainModel>() {
            @Override
            public void success(ImageMainModel userDataModel, Response response) {
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
                    Utils.ping(mContext, mContext.getString(R.string.false_msg));
                    return;
                }
                if (userDataModel.getIsValid() == 1) {
                    activityDisplaySfauserBinding.sfanewUserfabLinear.setVisibility(View.VISIBLE);
                    if (userDataModel.getData() != null && userDataModel.getData().size()>0) {

                        if (Utils.retriveLoginData(mContext) != null) {
                            if (Utils.retriveLoginData(mContext).getMemberType().equalsIgnoreCase(",3,")) {
                                activityDisplaySfauserBinding.shimmerViewContainer.stopShimmerAnimation();
                                activityDisplaySfauserBinding.shimmerViewContainer.setVisibility(View.GONE);
                                activityDisplaySfauserBinding.noRecordrel.setVisibility(View.GONE);
                                activityDisplaySfauserBinding.SFAUserInfoRcv.setVisibility(View.GONE);
                                activityDisplaySfauserBinding.noOfRecordrel.setVisibility(View.VISIBLE);
                            }else{
                                activityDisplaySfauserBinding.shimmerViewContainer.stopShimmerAnimation();
                                activityDisplaySfauserBinding.shimmerViewContainer.setVisibility(View.GONE);
                                activityDisplaySfauserBinding.noRecordrel.setVisibility(View.GONE);
                                activityDisplaySfauserBinding.SFAUserInfoRcv.setVisibility(View.VISIBLE);
                                activityDisplaySfauserBinding.noOfRecordrel.setVisibility(View.GONE);

                            }
                        }

                        sfausermodellist = userDataModel.getData();
                        fillSFAUserData();
                    }else{
                        activityDisplaySfauserBinding.shimmerViewContainer.setVisibility(View.GONE);
                        activityDisplaySfauserBinding.SFAUserInfoRcv.setVisibility(View.GONE);
                        activityDisplaySfauserBinding.noRecordrel.setVisibility(View.VISIBLE);
                        activityDisplaySfauserBinding.noOfRecordrel.setVisibility(View.GONE);
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

    private Map<String, String> getDisplayUserData() {
        Map<String, String> map = new HashMap<>();
        map.put("AppUserId", String.valueOf(Utils.getAppUserId(mContext)));

        return map;
    }

    public void fillSFAUserData() {
        activityDisplaySfauserBinding.countTxt.setText(String.valueOf(sfausermodellist.size()));

        displaySFAUserAdapter = new DisplaySFAUserAdapter(mContext, sfausermodellist);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        activityDisplaySfauserBinding.SFAUserInfoRcv.setLayoutManager(mLayoutManager);
        activityDisplaySfauserBinding.SFAUserInfoRcv.setItemAnimator(new DefaultItemAnimator());
        activityDisplaySfauserBinding.SFAUserInfoRcv.setAdapter(displaySFAUserAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                finish();
                break;

            case R.id.sfanew_userfab_linear:
                Intent userentry = new Intent(mContext, SportsInterestActivity.class);
                userentry.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mContext.startActivity(userentry);
                finish();
                break;
            case R.id.logout_img:
                android.app.AlertDialog.Builder alertDialog2 = new android.app.AlertDialog.Builder(this);
                alertDialog2.setTitle("Logout Confirm");
                alertDialog2.setMessage("Are you sure you want logout?");
                alertDialog2.setIcon(R.drawable.app_logo);
                alertDialog2.setCancelable(false);
                alertDialog2.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to execute after dialog
                                Utils.removeLoginData(mContext);
                                Utils.setPref(mContext, "IsSkipLogin", "");
                                Utils.setPref(mContext, "IsLoginUser", "");
                                Utils.ping(mContext, "You are logout suceessfully");
                                Intent ilogin = new Intent(mContext, LoginActivity.class);
                                ilogin.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                ilogin.putExtra("whereTocomeLogin", "more");
                                startActivity(ilogin);
                                finish();
                            }
                        });
                alertDialog2.setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to execute after dialog

                                dialog.cancel();
                            }
                        });
                alertDialog2.show();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (Utils.retriveLoginData(mContext) != null) {
            if (Utils.retriveLoginData(mContext).getMemberType().equalsIgnoreCase(",3,")) {
                finish();
            } else {
                finish();
            }
        }
    }
}
