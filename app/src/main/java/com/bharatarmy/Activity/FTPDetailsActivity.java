package com.bharatarmy.Activity;

import android.content.Context;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityFtpdetailsBinding;
import com.bumptech.glide.Glide;

public class FTPDetailsActivity extends BaseActivity implements View.OnClickListener {

    ActivityFtpdetailsBinding ftpdetailsBinding;
    Context mContext;
    String ftpmaintitleStr, ftpdateStr, ftpshortdescStr, ftptourdescStr, ftpbannerimgStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ftpdetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_ftpdetails);
        mContext = FTPDetailsActivity.this;

        setDataValue();
        setListiner();
        setTitleText(ftpmaintitleStr);
        setBackButton(FTPDetailsActivity.this);
    }

    public void setDataValue() {
        ftpmaintitleStr = getIntent().getStringExtra("ftpmaintitle");
        ftpdateStr = getIntent().getStringExtra("ftpdate");
        ftpshortdescStr = getIntent().getStringExtra("ftpshortdesc");
        ftptourdescStr = getIntent().getStringExtra("ftptourdesc");
        ftpbannerimgStr = getIntent().getStringExtra("ftpbannerimg");

        Log.d("webview",ftptourdescStr);
//        Glide.with(mContext)
//                .load(ftpbannerimgStr)
//                .placeholder(R.drawable.progress_animation)
//                .into(ftpdetailsBinding.ftpDetailBannerImg);
        Utils.setImageInImageView(ftpbannerimgStr,ftpdetailsBinding.ftpDetailBannerImg,mContext);

        ftpdetailsBinding.ftpMainTitleTxt.setText(ftpmaintitleStr);
        ftpdetailsBinding.ftpdateTxt.setText(ftpdateStr);
        ftpdetailsBinding.ftpShortTitleTxt.setText(ftpshortdescStr);
        ftpdetailsBinding.ftpDetailView.getSettings().setJavaScriptEnabled(true);
        ftpdetailsBinding.ftpDetailView.loadDataWithBaseURL("", ftptourdescStr, "text/html", "UTF-8", "");
    }

    public void setListiner(){
        ftpdetailsBinding.inquriyBtn.setOnClickListener(this);
        ftpdetailsBinding.booknowBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
     switch (v.getId()){
         case R.id.inquriy_btn:
             break;
         case R.id.booknow_btn:
             break;
     }

    }
}
