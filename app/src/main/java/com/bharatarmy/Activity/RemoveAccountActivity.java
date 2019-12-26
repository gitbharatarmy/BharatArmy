package com.bharatarmy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bharatarmy.R;
import com.bharatarmy.databinding.ActivityRemoveAccountBinding;

public class RemoveAccountActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityRemoveAccountBinding activityRemoveAccountBinding;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityRemoveAccountBinding= DataBindingUtil.setContentView(this,R.layout.activity_remove_account);

        mContext=RemoveAccountActivity.this;
        init();
        setLisitiner();
    }

    public void init(){}

    public void setLisitiner(){
        activityRemoveAccountBinding.backImg.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_img:
                whereToBack();
                break;
        }
    }


    public void whereToBack() {
        finish();
    }
}
