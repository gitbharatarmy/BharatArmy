package com.bharatarmy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.bharatarmy.Adapter.OtherMatchShowAdapter;
import com.bharatarmy.Adapter.TravelMatchDetailRecyclerAdapter;
import com.bharatarmy.R;
import com.bharatarmy.databinding.ActivityTravelMatchTicketAndHosipitalityticketdetailBinding;

import java.util.ArrayList;


public class TravelMatchTicketAndHosipitalityticketdetailActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityTravelMatchTicketAndHosipitalityticketdetailBinding activityTravelMatchTicketAndHosipitalityticketdetailBinding;
    Context mContext;
    OtherMatchShowAdapter otherMatchShowAdapter;
    ArrayList<String> othermatchshowList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTravelMatchTicketAndHosipitalityticketdetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_travel_match_ticket_and_hosipitalityticketdetail);
        mContext = TravelMatchTicketAndHosipitalityticketdetailActivity.this;

        init();
        setListiner();
        setDataValue();
    }

    public void init() {

        if (getIntent().getStringExtra("categoryName") != null) {
            activityTravelMatchTicketAndHosipitalityticketdetailBinding.toolbarTitleTxt.setText(getIntent().getStringExtra("categoryName"));
        }

//        mWebView.getSettings().setJavaScriptEnabled(true);
//        mWebView.getSettings().setDomStorageEnabled(true);
//        mWebView.getSettings().setDatabaseEnabled(true);
//        mWebView.getSettings().setDatabasePath(dbpath); //check the documentation for info about dbpath
//        mWebView.getSettings().setMinimumFontSize(1);
//        mWebView.getSettings().setMinimumLogicalFontSize(1);
    }

    public void setListiner() {
        activityTravelMatchTicketAndHosipitalityticketdetailBinding.backImg.setOnClickListener(this);
    }

    public void setDataValue() {
        activityTravelMatchTicketAndHosipitalityticketdetailBinding.shimmerViewContainer.setVisibility(View.GONE);
        othermatchshowList = new ArrayList<>();
        othermatchshowList.add("1");
        othermatchshowList.add("2");
        othermatchshowList.add("3");
        otherMatchShowAdapter = new OtherMatchShowAdapter(mContext, othermatchshowList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        activityTravelMatchTicketAndHosipitalityticketdetailBinding.otherMatchRcv.setLayoutManager(mLayoutManager);
        activityTravelMatchTicketAndHosipitalityticketdetailBinding.otherMatchRcv.setItemAnimator(new DefaultItemAnimator());
        activityTravelMatchTicketAndHosipitalityticketdetailBinding.otherMatchRcv.setAdapter(otherMatchShowAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                finish();
                break;
        }
    }
}
