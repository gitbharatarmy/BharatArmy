package com.bharatarmy.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityMoreStoryBinding;

public class MoreStoryActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityMoreStoryBinding moreStoryBinding;
    Context mContext;
    String storyHeadingStr, storyUrlStr;
    public static Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        moreStoryBinding = DataBindingUtil.setContentView(this, R.layout.activity_more_story);
        mContext = MoreStoryActivity.this;

        getDataValue();
        setListiner();
    }

    public void setListiner() {
        moreStoryBinding.backImg.setOnClickListener(this);
    }

    public void getDataValue() {
        storyHeadingStr = getIntent().getStringExtra("Story Heading");
        storyUrlStr = getIntent().getStringExtra("StroyUrl");

        moreStoryBinding.toolbarTitleTxt.setText(storyHeadingStr);
        moreStoryBinding.moreStoryWebview.getSettings().setJavaScriptEnabled(true);
//        moreStoryBinding.moreStoryWebview.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
//
//        moreStoryBinding.moreStoryWebview.getSettings().setBuiltInZoomControls(true);
//        moreStoryBinding.moreStoryWebview.getSettings().setUseWideViewPort(true);
//        moreStoryBinding.moreStoryWebview.getSettings().setLoadWithOverviewMode(true);

//        Utils.showDialog(mContext);
        if (dialog == null) {
            dialog = new Dialog(mContext);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.progressbar_dialog);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(true);
            dialog.show();
        }
        moreStoryBinding.moreStoryWebview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Utils.dismissDialog();
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Utils.ping(mContext,"Error for loading");

            }
        });
        moreStoryBinding.moreStoryWebview.loadUrl(storyUrlStr);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_img:
               MoreStoryActivity.this.finish();
                break;
        }
    }
}
