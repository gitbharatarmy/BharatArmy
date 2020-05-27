package com.bharatarmy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.bharatarmy.R;
import com.bharatarmy.databinding.ActivityBAPollListBinding;

public class BAPollListActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityBAPollListBinding activityBAPollListBinding;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBAPollListBinding = DataBindingUtil.setContentView(this, R.layout.activity_b_a_poll_list);
        mContext = BAPollListActivity.this;

        init();
        setListiner();
    }

    public void init(){}

    public void setListiner(){}

    @Override
    public void onClick(View v) {

    }
}
