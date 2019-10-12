package com.bharatarmy.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.bharatarmy.Adapter.DisplayAddedUserAdapter;
import com.bharatarmy.Adapter.InquiryListAdapter;
import com.bharatarmy.Fragment.InquiryChildInformationFragment;
import com.bharatarmy.Interfaces.MorestoryClick;
import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.Models.ImageDetailModel;
import com.bharatarmy.Models.ImageMainModel;
import com.bharatarmy.Models.MoreDataModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.ApiHandler;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityDisplayAddedUserBinding;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class DisplayAddedUserActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityDisplayAddedUserBinding activityDisplayAddedUserBinding;
    Context mContext;
    DisplayAddedUserAdapter displayAddedUserAdapter;
    List<ImageDetailModel> addedusermodellist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDisplayAddedUserBinding = DataBindingUtil.setContentView(this, R.layout.activity_display_added_user);
        mContext = DisplayAddedUserActivity.this;

        init();
        setListiner();
    }

    public void init() {
        activityDisplayAddedUserBinding.toolbarTitleTxt.setText("User List");
        activityDisplayAddedUserBinding.shimmerViewContainer.startShimmerAnimation();
        activityDisplayAddedUserBinding.addUserInfoRcv.setVisibility(View.GONE);
        callDisplayAddedUserData();
    }



    public void setListiner() {
        activityDisplayAddedUserBinding.backImg.setOnClickListener(this);
        activityDisplayAddedUserBinding.newUserfabLinear.setOnClickListener(this);
    }

    public void callDisplayAddedUserData() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), DisplayAddedUserActivity.this);
            return;
        }

//        Utils.showDialog(mContext);
        ApiHandler.getApiService().getMyAddedUser(getDisplayUserData(), new retrofit.Callback<ImageMainModel>() {
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
                    activityDisplayAddedUserBinding.newUserfabLinear.setVisibility(View.VISIBLE);
                    if (userDataModel.getData() != null && userDataModel.getData().size()>0) {
                        activityDisplayAddedUserBinding.shimmerViewContainer.stopShimmerAnimation();
                        activityDisplayAddedUserBinding.shimmerViewContainer.setVisibility(View.GONE);
                        activityDisplayAddedUserBinding.noRecordrel.setVisibility(View.GONE);
                        activityDisplayAddedUserBinding.addUserInfoRcv.setVisibility(View.VISIBLE);
                        addedusermodellist = userDataModel.getData();
                        fillUserData();
                    }else{
                        activityDisplayAddedUserBinding.shimmerViewContainer.setVisibility(View.GONE);
                        activityDisplayAddedUserBinding.addUserInfoRcv.setVisibility(View.GONE);
                        activityDisplayAddedUserBinding.noRecordrel.setVisibility(View.VISIBLE);
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
        map.put("AddedById", String.valueOf(Utils.getAppUserId(mContext)));

        return map;
    }

    public void fillUserData() {

        displayAddedUserAdapter = new DisplayAddedUserAdapter(mContext, addedusermodellist);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        activityDisplayAddedUserBinding.addUserInfoRcv.setLayoutManager(mLayoutManager);
        activityDisplayAddedUserBinding.addUserInfoRcv.setItemAnimator(new DefaultItemAnimator());
        activityDisplayAddedUserBinding.addUserInfoRcv.setAdapter(displayAddedUserAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                finish();
                break;

            case R.id.new_userfab_linear:
                Intent userentry = new Intent(mContext, UserEntryActivity.class);
                userentry.putExtra("userEditorNew", "new");
                userentry.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(userentry);
                finish();
                break;
        }
    }


}
