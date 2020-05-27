package com.bharatarmy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import com.bharatarmy.Models.LogginModel;
import com.bharatarmy.Models.LoginDataModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.ApiHandler;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityBAQuizDetailBinding;
import com.google.android.material.appbar.AppBarLayout;

import java.util.HashMap;
import java.util.Map;

import retrofit.RetrofitError;
import retrofit.client.Response;


public class BAQuizDetailActivity extends AppCompatActivity implements View.OnClickListener {
    Context mContext;
    ActivityBAQuizDetailBinding activityBAQuizDetailBinding;
    LoginDataModel quizDetailModelslist;
    public String isUpdateAvailable, isForceUpdateAvailable, currentVersionStr;
    String clickquiztitle, quizIdStr, quizTime, quizResultType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBAQuizDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_b_a_quiz_detail);
        mContext = BAQuizDetailActivity.this;

        init();
        setListiner();

    }

    public void init() {
        clickquiztitle = getIntent().getStringExtra("clickquiztitle");
        quizIdStr = getIntent().getStringExtra("quizid");
        setSupportActionBar(activityBAQuizDetailBinding.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        activityBAQuizDetailBinding.shimmerViewContainerMain.setVisibility(View.VISIBLE);
        activityBAQuizDetailBinding.shimmerViewContainerMain.startShimmerAnimation();
        activityBAQuizDetailBinding.nestedScroll.setVisibility(View.GONE);
        callBAQuizDetailData();
    }

    public void setListiner() {
        activityBAQuizDetailBinding.backImg.setOnClickListener(this);
        activityBAQuizDetailBinding.takeQuizLinear.setOnClickListener(this);

        activityBAQuizDetailBinding.appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
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
                    activityBAQuizDetailBinding.toolbar.setBackgroundColor(ContextCompat.getColor(mContext, R.color.heading_bg));
                    activityBAQuizDetailBinding.collapsingToolbar.setTitle(activityBAQuizDetailBinding.quizMainTitleTxt.getText().toString());
                    Typeface typeface = ResourcesCompat.getFont(mContext, R.font.helveticaneueltstdbdcn);
                    activityBAQuizDetailBinding.collapsingToolbar.setCollapsedTitleTypeface(typeface);
                    activityBAQuizDetailBinding.collapsingToolbar.setExpandedTitleTypeface(typeface);
                    activityBAQuizDetailBinding.collapsingToolbar.setCollapsedTitleGravity(Gravity.START);
                    activityBAQuizDetailBinding.collapsingToolbar.setExpandedTitleGravity(Gravity.START);
                    activityBAQuizDetailBinding.shareArticle.setVisibility(View.VISIBLE);

                    isShow = true;
                } else if (isShow) {
                    activityBAQuizDetailBinding.toolbar.setBackgroundColor(ContextCompat.getColor(mContext, R.color.transparent));
                    activityBAQuizDetailBinding.collapsingToolbar.setTitle(" ");

                    isShow = false;
                }

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                whereToback();
                break;
            case R.id.take_quiz_linear:
                Intent intent = new Intent(mContext, BAQuizQuestionAnswerActivity.class);
                intent.putExtra("quiztitle", activityBAQuizDetailBinding.quizMainTitleTxt.getText().toString());
                intent.putExtra("quizTime", quizTime);
                intent.putExtra("quizeResultType", quizResultType);
                startActivity(intent);
                finish();
                break;
        }
    }

    public void whereToback() {
        finish();
    }

    // Api calling GetBAQuizDetailData
    public void callBAQuizDetailData() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), BAQuizDetailActivity.this);
            return;
        }

//        Utils.showDialog(mContext);

        ApiHandler.getApiService().getQuizDetailsById(getBAQuizDetailData(), new retrofit.Callback<LogginModel>() {
            @Override
            public void success(LogginModel quizMainModel, Response response) {
                Utils.dismissDialog();
                if (quizMainModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (quizMainModel.getIsValid() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (quizMainModel.getIsValid() == 0) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    return;
                }
                if (quizMainModel.getIsValid() == 1) {
                    isUpdateAvailable = String.valueOf(quizMainModel.getIsUpdateAvailable());
                    isForceUpdateAvailable = String.valueOf(quizMainModel.getIsForceUpdate());
                    currentVersionStr = String.valueOf(quizMainModel.getCurrentVersion());
                    if (isUpdateAvailable.equalsIgnoreCase("1")) {
                        Utils.checkupdateApplication(mContext, BAQuizDetailActivity.this, isForceUpdateAvailable, currentVersionStr);
                    }
                    if (quizMainModel.getData() != null) {
                        activityBAQuizDetailBinding.shimmerViewContainerMain.stopShimmerAnimation();
                        activityBAQuizDetailBinding.shimmerViewContainerMain.setVisibility(View.GONE);
                        activityBAQuizDetailBinding.nestedScroll.setVisibility(View.VISIBLE);


                        quizDetailModelslist = quizMainModel.getData();
                        setBAQuizDetail();
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

    private Map<String, String> getBAQuizDetailData() {
        Map<String, String> map = new HashMap<>();
        map.put("QuizId", quizIdStr);
        return map;
    }

    public void setBAQuizDetail() {
        activityBAQuizDetailBinding.shimmerViewContainerMain.stopShimmerAnimation();
        activityBAQuizDetailBinding.shimmerViewContainerMain.setVisibility(View.GONE);
        activityBAQuizDetailBinding.nestedScroll.setVisibility(View.VISIBLE);


        //Font must be placed in assets/fonts folder
        String text = "<html><style type='text/css'>@font-face { font-family: thesansplain; src: url('fonts/thesansplain.ttf'); } body p {font-family: thesansplain;}</style>"
                + "<body >" + "<p align=\"justify\" style=\"font-size: 22px; font-family: spqr;\">" + quizDetailModelslist.getQuizDescription() + "</p> " + "</body></html>";


        Utils.setImageInImageView(quizDetailModelslist.getQuizMobileBannerUrlApp(), activityBAQuizDetailBinding.backdrop, mContext);
        activityBAQuizDetailBinding.quizMainTitleTxt.setText(quizDetailModelslist.getQuizHeaderText());
        activityBAQuizDetailBinding.quizDateTxt.setText(quizDetailModelslist.getStrQuizDate());
//                activityBAQuizDetailBinding.quizTypeTxt.setText(quizDetailModelslist.getBACategoryName());
        activityBAQuizDetailBinding.quizSubtitleTxt.setText(quizDetailModelslist.getQuizShortDescription());
        activityBAQuizDetailBinding.quizDetailView.getSettings().setJavaScriptEnabled(true);


        activityBAQuizDetailBinding.quizDetailView.setVerticalScrollBarEnabled(false);
        activityBAQuizDetailBinding.quizDetailView.loadDataWithBaseURL("file:///android_asset/", text, "text/html", "UTF-8", null);

        if (quizDetailModelslist.getTimerType().equals(1) || quizDetailModelslist.getTimerType().equals(2)) {
            activityBAQuizDetailBinding.quizTimerLinear.setVisibility(View.VISIBLE);
        } else {
            activityBAQuizDetailBinding.quizTimerLinear.setVisibility(View.GONE);
        }
        quizResultType = String.valueOf(quizDetailModelslist.getDisplayResultType());
        quizTime = String.valueOf(quizDetailModelslist.getTimerValue());
        activityBAQuizDetailBinding.quizTimeTxt.setText("Timer : " + quizDetailModelslist.getTimerValue() + " seconds");
    }
}
