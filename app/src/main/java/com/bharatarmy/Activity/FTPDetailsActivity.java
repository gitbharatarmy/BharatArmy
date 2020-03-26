package com.bharatarmy.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.DataBindingUtil;

import com.bharatarmy.Models.ImageMainModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.ApiHandler;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityFtpdetailsBinding;

import com.google.android.material.appbar.AppBarLayout;

import java.util.HashMap;
import java.util.Map;

import retrofit.RetrofitError;
import retrofit.client.Response;

// change the page design and development 19-06-2019
public class FTPDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityFtpdetailsBinding ftpdetailsBinding;
    Context mContext;
    String ftpmaintitleStr, ftpdateStr, ftpshortdescStr, ftptourdescStr, ftpbannerimgStr,ftpstr1,ftpstr2,ftpstr3,ftpIdStr;
    int ftpId;
    ImageMainModel ftpDetailDataList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ftpdetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_ftpdetails);
        mContext = FTPDetailsActivity.this;

        setDataValue();
        setListiner();

    }

    public void setDataValue() {
        ftpmaintitleStr = getIntent().getStringExtra("ftpmaintitle");
        ftpdateStr = getIntent().getStringExtra("ftpdate");
        ftpshortdescStr = getIntent().getStringExtra("ftpshortdesc");
        ftptourdescStr = getIntent().getStringExtra("ftptourdesc");
        ftpbannerimgStr = getIntent().getStringExtra("ftpbannerimg");
        ftpstr1=getIntent().getStringExtra("str1");
        ftpstr2=getIntent().getStringExtra("str2");
        ftpstr3=getIntent().getStringExtra("str3");
        ftpId=getIntent().getIntExtra("ftpId",0);
        ftpIdStr= String.valueOf(ftpId);

        Log.d("webview", ftptourdescStr);
        Utils.setImageInImageView(ftpbannerimgStr, ftpdetailsBinding.backdrop, mContext);
        ftpdetailsBinding.ftpMainTitleTxt.setText(ftpmaintitleStr);
        ftpdetailsBinding.ftpdateTxt.setText(ftpdateStr);
        ftpdetailsBinding.ftpShortTitleTxt.setText(ftpshortdescStr);

        ftpdetailsBinding.shimmerViewContainer.startShimmerAnimation();
        setSupportActionBar(ftpdetailsBinding.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);


        if (!ftpstr1.equalsIgnoreCase("")) {
            if (!ftpstr1.equalsIgnoreCase("1")){
                ftpdetailsBinding.linear1Txt.setVisibility(View.VISIBLE);
                ftpdetailsBinding.linear1Txt.setText(ftpstr1);
            }else {
                ftpdetailsBinding.linear1Txt.setVisibility(View.GONE);
            }
        }

        if (!ftpstr2.equalsIgnoreCase("")) {
            if(!ftpstr2.equalsIgnoreCase("1")){
                ftpdetailsBinding.linear2Txt.setVisibility(View.VISIBLE);
                ftpdetailsBinding.linear2Txt.setText(ftpstr2);
            }else {
                ftpdetailsBinding.linear2Txt.setVisibility(View.GONE);
            }
        }

        if (!ftpstr3.equalsIgnoreCase("")) {
            if (!ftpstr3.equalsIgnoreCase("1")){
                ftpdetailsBinding.linear3Txt.setVisibility(View.VISIBLE);
                ftpdetailsBinding.linear3Txt.setText(ftpstr3);
            }else {
                ftpdetailsBinding.linear3Txt.setVisibility(View.GONE);
            }

        }
        callFTPDetailData();
    }

    public void setListiner() {
        ftpdetailsBinding.inquriyBtn.setOnClickListener(this);
        ftpdetailsBinding.ftpComment.setOnClickListener(this);
        ftpdetailsBinding.backImg.setOnClickListener(this);

        ftpdetailsBinding.appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(final AppBarLayout appBarLayout, int verticalOffset) {
                //Initialize the size of the scroll
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                //Check if the view is collapsed
                if (scrollRange + verticalOffset == 0) {
                    ftpdetailsBinding.toolbar.setBackgroundColor(ContextCompat.getColor(mContext, R.color.heading_bg));
                    ftpdetailsBinding.collapsingToolbar.setTitle(ftpmaintitleStr);
                    Typeface typeface = ResourcesCompat.getFont(mContext, R.font.helveticaneueltstdbdcn);
                    ftpdetailsBinding.collapsingToolbar.setCollapsedTitleTypeface(typeface);
                    ftpdetailsBinding.collapsingToolbar.setExpandedTitleTypeface(typeface);
                    ftpdetailsBinding.collapsingToolbar.setCollapsedTitleGravity(Gravity.START);
                    ftpdetailsBinding.collapsingToolbar.setExpandedTitleGravity(Gravity.START);
                    ftpdetailsBinding.shareArticleLinear.setVisibility(View.VISIBLE);

                    isShow = true;
                } else if (isShow) {
                    ftpdetailsBinding.toolbar.setBackgroundColor(ContextCompat.getColor(mContext, R.color.transparent));
                    ftpdetailsBinding.collapsingToolbar.setTitle(" ");

                    isShow = false;
                }

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.inquriy_btn:
                if(Utils.isMember(mContext,"FTP Detail")){

                }
                break;
            case R.id.ftp_comment:
                Intent commentIntent = new Intent(mContext, CommentActivity.class);
                commentIntent.putExtra("referenceId", ftpIdStr);
                commentIntent.putExtra("sourceType", "3");
                startActivity(commentIntent);
                break;
            case R.id.back_img:
                FTPDetailsActivity.this.finish();
                break;

        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                FTPDetailsActivity.this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Api calling GetStoryDetailData
    public void callFTPDetailData() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), FTPDetailsActivity.this);
            return;
        }

//        Utils.showDialog(mContext);

        ApiHandler.getApiService().getFTPDetail(getFtpDetailData(), new retrofit.Callback<ImageMainModel>() {
            @Override
            public void success(ImageMainModel imageMainModel, Response response) {
                Utils.dismissDialog();
                if (imageMainModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (imageMainModel.getIsValid() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (imageMainModel.getIsValid() == 0) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    return;
                }
                if (imageMainModel.getIsValid() == 1) {

                    if (imageMainModel.getData() != null) {
                        ftpDetailDataList = imageMainModel;
                        setAPIValue();
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

    private Map<String, String> getFtpDetailData() {
        Map<String, String> map = new HashMap<>();
        map.put("FTPId", String.valueOf(ftpId));
        map.put("MemberId", String.valueOf(Utils.getAppUserId(mContext)));

        return map;
    }


    public void setAPIValue() {
        //Font must be placed in assets/fonts folder
        String text = "<html><style type='text/css'>@font-face { font-family: thesansplain; src: url('fonts/thesansplain.ttf'); } body p {font-family: thesansplain;}</style>"
                + "<body >" + "<p align=\"justify\" style=\"font-size: 22px; font-family: spqr;\">" + ftpDetailDataList.getData().get(0).getDescription() + "</p> " + "</body></html>";


        ftpdetailsBinding.shimmerViewContainer.stopShimmerAnimation();
        ftpdetailsBinding.shimmerViewContainer.setVisibility(View.GONE);
        ftpdetailsBinding.ftpDetailLinear.setVisibility(View.VISIBLE);

        ftpdetailsBinding.ftpDetailView.getSettings().setJavaScriptEnabled(true);

        Log.d("data", ftpDetailDataList.getData().get(0).getDescription());
        ftpdetailsBinding.ftpDetailView.setVerticalScrollBarEnabled(false);
        ftpdetailsBinding.ftpDetailView.loadDataWithBaseURL("file:///android_asset/", text, "text/html", "UTF-8", null);
        ftpdetailsBinding.shareArticleLinear.setVisibility(View.VISIBLE);
        ftpdetailsBinding.bottomGradiantView.setVisibility(View.VISIBLE);
    }

}
