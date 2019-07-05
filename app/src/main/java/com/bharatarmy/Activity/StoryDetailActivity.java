package com.bharatarmy.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.bharatarmy.Models.ImageMainModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.ApiHandler;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityStoryDetailBinding;
import com.google.android.material.appbar.AppBarLayout;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class StoryDetailActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityStoryDetailBinding activityStoryDetailBinding;
    Context mContext;
    String storyHeadingStr;
    String storyUrlStr;
    String storyCategory;
    String storyAuthor;
    String storyHeaderImg;
    int storyAuthorId;
    int storyId;
    ImageMainModel storyDetailDataList;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityStoryDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_story_detail);

        mContext = StoryDetailActivity.this;

        getsetIntentValue();

        setListiner();


    }


    public void getsetIntentValue() {
        storyHeadingStr = getIntent().getStringExtra("Story Heading");
        storyUrlStr = getIntent().getStringExtra("StroyUrl");
        storyCategory = getIntent().getStringExtra("StoryCategorytype");
        storyAuthor = getIntent().getStringExtra("StoryAuthor");
        storyHeaderImg = getIntent().getStringExtra("StoryHeaderImg");
        storyId = getIntent().getIntExtra("StoryId", 0);
        storyAuthorId = getIntent().getIntExtra("StoryauthorId", 0);

        Log.d("StoryId", "" + storyId + "AuthorId :" + storyAuthorId);

        activityStoryDetailBinding.showStoryTitleTxt.setText(storyHeadingStr);

        activityStoryDetailBinding.typeTxt.setText(storyCategory);
        Utils.setImageInImageView(storyAuthor, activityStoryDetailBinding.userImage, mContext);
        Utils.setImageInImageView(storyHeaderImg, activityStoryDetailBinding.backdrop, mContext);

        callStoryDetailData();
    }


    public void setListiner() {
        activityStoryDetailBinding.shimmerViewContainer.startShimmerAnimation();
        setSupportActionBar(activityStoryDetailBinding.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        activityStoryDetailBinding.userImage.setOnClickListener(this);
        activityStoryDetailBinding.commentLinear.setOnClickListener(this);
        activityStoryDetailBinding.uprStoryComment.setOnClickListener(this);

        activityStoryDetailBinding.appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
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
                    activityStoryDetailBinding.toolbar.setBackgroundColor(ContextCompat.getColor(mContext, R.color.heading_bg));
                    activityStoryDetailBinding.collapsingToolbar.setTitle(storyHeadingStr);
                    Typeface typeface = ResourcesCompat.getFont(mContext, R.font.helveticaneueltstdbdcn);
                    activityStoryDetailBinding.collapsingToolbar.setCollapsedTitleTypeface(typeface);
                    activityStoryDetailBinding.collapsingToolbar.setExpandedTitleTypeface(typeface);
                    activityStoryDetailBinding.collapsingToolbar.setCollapsedTitleGravity(Gravity.START);
                    activityStoryDetailBinding.collapsingToolbar.setExpandedTitleGravity(Gravity.START);
                    activityStoryDetailBinding.userImage.setVisibility(View.GONE);
                    activityStoryDetailBinding.shareArticleLinear.setVisibility(View.VISIBLE);
                    ViewGroup.LayoutParams params = activityStoryDetailBinding.toolbarBottomLeftView.getLayoutParams();
                    params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    activityStoryDetailBinding.toolbarBottomLeftView.setLayoutParams(params);
                    isShow = true;
                } else if (isShow) {
                    activityStoryDetailBinding.toolbar.setBackgroundColor(ContextCompat.getColor(mContext, R.color.transparent));
                    activityStoryDetailBinding.collapsingToolbar.setTitle(" ");
                    activityStoryDetailBinding.userImage.setVisibility(View.VISIBLE);
                    activityStoryDetailBinding.shareArticleLinear.setVisibility(View.GONE);
//                    ViewGroup.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 0.85f);
//                    activityStoryDetailBinding.toolbarBottomLeftView.setLayoutParams(params);
                    ViewGroup.LayoutParams params = activityStoryDetailBinding.toolbarBottomLeftView.getLayoutParams();
                    params.width = (int) 0.85f;
                    activityStoryDetailBinding.toolbarBottomLeftView.setLayoutParams(params);
                    isShow = false;
                }

            }
        });

//        activityStoryDetailBinding.nestedScroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                if (scrollY > oldScrollY) {
//                    activityStoryDetailBinding.shareArticleLinear.setVisibility(View.VISIBLE);
//                } else {
//                    activityStoryDetailBinding.shareArticleLinear.setVisibility(View.GONE);
//                }
//            }
//        });

        activityStoryDetailBinding.shareArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(storyUrlStr);
                //share image from other application
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, AppConfiguration.SHARETEXT);
                shareIntent.putExtra(Intent.EXTRA_TEXT, storyUrlStr + "\n\n" + AppConfiguration.SHARETEXT);
                shareIntent.setType("text/plain");
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(Intent.createChooser(shareIntent, "Share It"));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                StoryDetailActivity.this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_image:
                Intent authorIntent = new Intent(mContext, StoryAuthorActivity.class);
                authorIntent.putExtra("StoryauthorId", storyAuthorId);
                mContext.startActivity(authorIntent);
                break;
            case R.id.comment_linear:
                Intent bottomcommentIntent = new Intent(mContext, CommentActivity.class);
                startActivity(bottomcommentIntent);
                break;
            case R.id.upr_story_comment:
                Intent uprcommentIntent = new Intent(mContext, CommentActivity.class);
                startActivity(uprcommentIntent);
                break;
        }
    }

    // Api calling GetStoryDetailData
    public void callStoryDetailData() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), StoryDetailActivity.this);
            return;
        }

//        Utils.showDialog(mContext);

        ApiHandler.getApiService().getStoryDetail(getStoryDetailData(), new retrofit.Callback<ImageMainModel>() {
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
                        storyDetailDataList = imageMainModel;

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

    private Map<String, String> getStoryDetailData() {
        Map<String, String> map = new HashMap<>();
        map.put("StoryId", String.valueOf(storyId));
        return map;
    }


    public void setAPIValue() {
        //Font must be placed in assets/fonts folder
        String text = "<html><style type='text/css'>@font-face { font-family: thesansplain; src: url('fonts/thesansplain.ttf'); } body p {font-family: thesansplain;}</style>"
                + "<body >" + "<p align=\"justify\" style=\"font-size: 22px; font-family: spqr;\">" + storyDetailDataList.getData().get(0).getStoryDescription() + "</p> " + "</body></html>";

        activityStoryDetailBinding.shimmerViewContainer.stopShimmerAnimation();
        activityStoryDetailBinding.shimmerViewContainer.setVisibility(View.GONE);
        activityStoryDetailBinding.auhtorStoryDetailView.setVisibility(View.VISIBLE);
        activityStoryDetailBinding.storyDetailView.getSettings().setJavaScriptEnabled(true);

        Log.d("data", storyDetailDataList.getData().get(0).getStoryDescription());
        activityStoryDetailBinding.storyDetailView.setVerticalScrollBarEnabled(false);
        activityStoryDetailBinding.storyDetailView.loadDataWithBaseURL("file:///android_asset/", text, "text/html", "UTF-8", null);

    }

}
