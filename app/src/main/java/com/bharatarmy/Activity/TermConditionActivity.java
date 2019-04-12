package com.bharatarmy.Activity;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityMoreStoryBinding;
import com.bharatarmy.databinding.ActivityTermConditionBinding;
import com.bumptech.glide.Glide;

public class TermConditionActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityTermConditionBinding termConditionBinding;
    Context mContext;
    String storyHeadingStr, storyUrlStr,strShow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        termConditionBinding = DataBindingUtil.setContentView(this, R.layout.activity_term_condition);
        mContext = TermConditionActivity.this;

        getDataValue();
        setListiner();
    }

    public void setListiner() {
        termConditionBinding.backImg.setOnClickListener(this);
    }

    public void getDataValue() {
        storyHeadingStr = getIntent().getStringExtra("Story Heading");
        storyUrlStr = getIntent().getStringExtra("StroyUrl");
        Glide.with(mContext).load(R.drawable.logo).into(termConditionBinding.image);

        termConditionBinding.toolbarTitleTxt.setText(storyHeadingStr);
        termConditionBinding.moreStoryWebview.setWebViewClient(new MyWebViewClient());
        termConditionBinding.moreStoryWebview.getSettings().setJavaScriptEnabled(true);
        termConditionBinding.moreStoryWebview.loadUrl(storyUrlStr);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_img:
                TermConditionActivity.this.finish();
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
            termConditionBinding.image.setVisibility(View.VISIBLE);
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);
            termConditionBinding.image.setVisibility(View.GONE);
        }
    }
}
