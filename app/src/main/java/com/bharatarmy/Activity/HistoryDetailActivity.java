package com.bharatarmy.Activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bharatarmy.R;
import com.bharatarmy.databinding.ActivityHistoryDetailBinding;

public class HistoryDetailActivity extends AppCompatActivity {

    ActivityHistoryDetailBinding activityHistoryDetailBinding;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityHistoryDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_history_detail);

        mContext = HistoryDetailActivity.this;
        setListiner();
    }

    public void setListiner(){}

    public void setDataValue(){

    }
}
