package com.bharatarmy.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.databinding.DataBindingUtil;

import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityMoreStoryBinding;
import com.bumptech.glide.Glide;


public class MoreStoryActivity extends BaseActivity implements View.OnClickListener {

    ActivityMoreStoryBinding moreStoryBinding;
    Context mContext;
    String storyHeadingStr, storyUrlStr, strShow, strWheretocome;
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
        moreStoryBinding.shareImg.setOnClickListener(this);
    }

    public void getDataValue() {
        storyHeadingStr = getIntent().getStringExtra("Story Heading");
        storyUrlStr = getIntent().getStringExtra("StroyUrl");
        strWheretocome = getIntent().getStringExtra("whereTocome");


        moreStoryBinding.toolbarTitleTxt.setText(storyHeadingStr);
        if (strWheretocome.equalsIgnoreCase("storylistadp")) {
            moreStoryBinding.shareImg.setVisibility(View.VISIBLE);
        } else {
            moreStoryBinding.shareImg.setVisibility(View.GONE);
        }
        Glide.with(mContext).load(R.drawable.logo).into(moreStoryBinding.image);
//        moreStoryBinding.toolbarTitleTxt.setText(storyHeadingStr);
        moreStoryBinding.moreStoryWebview.setWebViewClient(new MyWebViewClient());
        moreStoryBinding.moreStoryWebview.getSettings().setJavaScriptEnabled(true);
        moreStoryBinding.moreStoryWebview.loadUrl(storyUrlStr);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                MoreStoryActivity.this.finish();
                break;
            case R.id.share_img:
                Utils.handleClickEvent(mContext,moreStoryBinding.shareImg);
                Uri uri= Uri.parse(storyUrlStr);
                //share image from other application
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, AppConfiguration.SHARETEXT);
                shareIntent.putExtra(Intent.EXTRA_TEXT,storyUrlStr+"\n\n"+AppConfiguration.SHARETEXT);
                shareIntent.setType("text/plain");
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(Intent.createChooser(shareIntent, "Share It"));
                break;
        }
    }


    public class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
//            moreStoryBinding.progressbar1.setVisibility(View.VISIBLE);
            moreStoryBinding.image.setVisibility(View.VISIBLE);
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);

            moreStoryBinding.image.setVisibility(View.GONE);
        }
    }

}
