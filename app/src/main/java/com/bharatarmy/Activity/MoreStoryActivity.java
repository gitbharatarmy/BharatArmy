package com.bharatarmy.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityMoreStoryBinding;

public class MoreStoryActivity extends Activity implements View.OnClickListener {

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
        moreStoryBinding.moreStoryWebview.setWebViewClient(new MyWebViewClient());
        moreStoryBinding.moreStoryWebview.getSettings().setJavaScriptEnabled(true);
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


    public class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            moreStoryBinding.progressbar1.setVisibility(View.VISIBLE);
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);

            moreStoryBinding.progressbar1.setVisibility(View.GONE);
        }
    }

}
