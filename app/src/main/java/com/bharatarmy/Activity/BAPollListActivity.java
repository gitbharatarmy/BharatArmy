package com.bharatarmy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.bharatarmy.Adapter.BAPollListAdapter;
import com.bharatarmy.Adapter.FeedbackTextMultiChoiceAdapter;
import com.bharatarmy.Interfaces.MorestoryClick;
import com.bharatarmy.Models.BAPollDatum;
import com.bharatarmy.Models.QuizDetailModel;
import com.bharatarmy.Models.QuizMainModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.ApiHandler;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityBAPollListBinding;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class BAPollListActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityBAPollListBinding activityBAPollListBinding;
    Context mContext;
    public String isUpdateAvailable, isForceUpdateAvailable, currentVersionStr;
    List<QuizDetailModel> pollModelList;
    BAPollListAdapter baPollListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBAPollListBinding = DataBindingUtil.setContentView(this, R.layout.activity_b_a_poll_list);
        mContext = BAPollListActivity.this;

        init();
        setListiner();
    }

    public void init() {
        activityBAPollListBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
        activityBAPollListBinding.shimmerViewContainer.startShimmerAnimation();
        activityBAPollListBinding.baPollListRcv.setVisibility(View.GONE);

        callBAPollListData();
    }

    public void setListiner() {
        activityBAPollListBinding.backImg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                finish();
                break;
        }
    }

    // Api calling GetBAPollListData
    public void callBAPollListData() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), BAPollListActivity.this);
            return;
        }

//        Utils.showDialog(mContext);

        ApiHandler.getApiService().getPollListByMember(getBAPollListData(), new retrofit.Callback<QuizMainModel>() {
            @Override
            public void success(QuizMainModel polllistMainModel, Response response) {
                Utils.dismissDialog();
                if (polllistMainModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (polllistMainModel.getIsValid() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (polllistMainModel.getIsValid() == 0) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    return;
                }
                if (polllistMainModel.getIsValid() == 1) {
                    isUpdateAvailable = String.valueOf(polllistMainModel.getIsUpdateAvailable());
                    isForceUpdateAvailable = String.valueOf(polllistMainModel.getIsForceUpdate());
                    currentVersionStr = String.valueOf(polllistMainModel.getCurrentVersion());
                    if (isUpdateAvailable.equalsIgnoreCase("1")) {
                        Utils.checkupdateApplication(mContext, BAPollListActivity.this, isForceUpdateAvailable, currentVersionStr);
                    }
                    if (polllistMainModel.getData() != null) {
                        activityBAPollListBinding.shimmerViewContainer.stopShimmerAnimation();
                        activityBAPollListBinding.shimmerViewContainer.setVisibility(View.GONE);
                        activityBAPollListBinding.baPollListRcv.setVisibility(View.VISIBLE);


                        pollModelList = polllistMainModel.getData();
                        setBAPollList();
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

    private Map<String, String> getBAPollListData() {
        Map<String, String> map = new HashMap<>();
        return map;
    }

    public void setBAPollList() {
        baPollListAdapter = new BAPollListAdapter(mContext, pollModelList);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        activityBAPollListBinding.baPollListRcv.setLayoutManager(mLinearLayoutManager);
        activityBAPollListBinding.baPollListRcv.setItemAnimator(new DefaultItemAnimator());
        activityBAPollListBinding.baPollListRcv.setAdapter(baPollListAdapter);
    }
}
