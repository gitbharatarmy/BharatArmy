package com.bharatarmy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.bharatarmy.Adapter.NotificationDetailShowAdapter;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.databinding.ActivityNotificationDetailBinding;

import java.util.ArrayList;

public class NotificationDetailActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityNotificationDetailBinding activityNotificationDetailBinding;
    Context mContext;
    NotificationDetailShowAdapter notificationDetailShowAdapter;
    ArrayList<String> notificationDetailList;
    LinearLayoutManager notificationLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityNotificationDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_notification_detail);

        mContext = NotificationDetailActivity.this;

        init();
        setListiner();
    }

    public void init() {
        activityNotificationDetailBinding.toolbarTitleTxt.setText("Notification Detail");


        notificationDetailList = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            notificationDetailList.add(String.valueOf(i));
        }

        notificationDetailShowAdapter = new NotificationDetailShowAdapter(mContext, notificationDetailList);
        notificationLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        activityNotificationDetailBinding.notificationDetailRcv.setLayoutManager(notificationLayoutManager);
        activityNotificationDetailBinding.notificationDetailRcv.setItemAnimator(new DefaultItemAnimator());
        activityNotificationDetailBinding.notificationDetailRcv.setAdapter(notificationDetailShowAdapter);
    }

    public void setListiner() {
        activityNotificationDetailBinding.backImg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                finish();
                break;
        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
