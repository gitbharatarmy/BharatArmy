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
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bharatarmy.Adapter.VideoDetailHorizontalVideoAdapter;
import com.bharatarmy.Models.ImageMainModel;
import com.bharatarmy.Models.LogginModel;
import com.bharatarmy.Models.LoginDataModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.ApiHandler;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityStoryDetailBinding;
import com.google.android.material.appbar.AppBarLayout;
import com.like.LikeButton;
import com.like.OnLikeListener;

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
    boolean likeflag = false;
    ImageMainModel storyDetailDataList;
    LoginDataModel postedDataList;
    public String isUpdateAvailable, isForceUpdateAvailable, currentVersionStr;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityStoryDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_story_detail);

        mContext = StoryDetailActivity.this;

        getsetIntentValue();

        setListiner();


    }


    public void getsetIntentValue() {
        if (getIntent().getStringExtra("Story Heading") != null) {
            storyHeadingStr = getIntent().getStringExtra("Story Heading");
            activityStoryDetailBinding.showStoryTitleTxt.setText(storyHeadingStr);
        }
        if (getIntent().getStringExtra("StroyUrl") != null) {
            storyUrlStr = getIntent().getStringExtra("StroyUrl");
        }
        if (getIntent().getStringExtra("StoryCategorytype") != null) {
            storyCategory = getIntent().getStringExtra("StoryCategorytype");
            activityStoryDetailBinding.typeTxt.setText(storyCategory);
        }
        if (getIntent().getStringExtra("StoryAuthor") != null) {
            storyAuthor = getIntent().getStringExtra("StoryAuthor");
            Utils.setImageInImageView(storyAuthor, activityStoryDetailBinding.userImage, mContext);
        }
        if (getIntent().getStringExtra("StoryHeaderImg") != null) {
            storyHeaderImg = getIntent().getStringExtra("StoryHeaderImg");
            Utils.setImageInImageView(storyHeaderImg, activityStoryDetailBinding.backdrop, mContext);
        }
        if (getIntent().getIntExtra("StoryId", 0) > 0) {
            storyId = getIntent().getIntExtra("StoryId", 0);
        }
        if (getIntent().getIntExtra("StoryauthorId", 0) > 0) {
            storyAuthorId = getIntent().getIntExtra("StoryauthorId", 0);
        }

        Log.d("StoryId", "" + storyId + "AuthorId :" + storyAuthorId);

        callStoryDetailData();
    }


    public void setListiner() {
        activityStoryDetailBinding.shimmerViewContainer.startShimmerAnimation();
        setSupportActionBar(activityStoryDetailBinding.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        activityStoryDetailBinding.userImage.setOnClickListener(this);
        activityStoryDetailBinding.commentLinear.setOnClickListener(this);
        activityStoryDetailBinding.uLinear.setOnClickListener(this);
        activityStoryDetailBinding.backImg.setOnClickListener(this);

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


        activityStoryDetailBinding.shareArticle.setOnClickListener(this);

        activityStoryDetailBinding.uprStoryLikeBtn.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                activityStoryDetailBinding.bottomStoryLikeBtn.setLiked(true);
                LikeArticleValue(1);
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                activityStoryDetailBinding.bottomStoryLikeBtn.setLiked(false);
                LikeArticleValue(0);
            }
        });

        activityStoryDetailBinding.bottomStoryLikeBtn.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                activityStoryDetailBinding.uprStoryLikeBtn.setLiked(true);
                LikeArticleValue(1);
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                activityStoryDetailBinding.uprStoryLikeBtn.setLiked(false);
                LikeArticleValue(0);

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
                Utils.handleClickEvent(mContext, activityStoryDetailBinding.userImage);
                Intent authorIntent = new Intent(mContext, StoryAuthorActivity.class);
                authorIntent.putExtra("StoryauthorId", storyAuthorId);
                mContext.startActivity(authorIntent);
                break;
            case R.id.comment_linear:
                commentArticleValue();
                break;
            case R.id.back_img:
                StoryDetailActivity.this.finish();
                break;
            case R.id.share_article:
                Utils.handleClickEvent(mContext, activityStoryDetailBinding.shareArticle);
                shareArticleValue();
                break;
            case R.id.uLinear:
                commentArticleValue();
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
            public void success(ImageMainModel storyDetailMainModel, Response response) {
                Utils.dismissDialog();
                if (storyDetailMainModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (storyDetailMainModel.getIsValid() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (storyDetailMainModel.getIsValid() == 0) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    return;
                }
                if (storyDetailMainModel.getIsValid() == 1) {
                    isUpdateAvailable = String.valueOf(storyDetailMainModel.getIsUpdateAvailable());
                    isForceUpdateAvailable = String.valueOf(storyDetailMainModel.getIsForceUpdate());
//                    isForceUpdateAvailable = "0";
                    currentVersionStr = String.valueOf(storyDetailMainModel.getCurrentVersion());
                    if (isUpdateAvailable.equalsIgnoreCase("1")) {
                        Utils.checkupdateApplication(mContext, StoryDetailActivity.this, isForceUpdateAvailable, currentVersionStr);
                    }
                    if (storyDetailMainModel.getData() != null) {

                        if (storyDetailMainModel.getData().size()!=0){
                            storyDetailDataList = storyDetailMainModel;

                            callPostedViewData();
                        }

//                        setAPIValue();
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
        map.put("MemberId", String.valueOf(Utils.getAppUserId(mContext)));
        map.put("TokenId", Utils.getPref(mContext, "registration_id"));

        return map;
    }


    public void setAPIValue() {
        if (postedDataList != null) {
            if (postedDataList.getLikes() != null) {
                    activityStoryDetailBinding.uprStoryTotalLikeTxt.setText(String.valueOf(postedDataList.getLikes()));
            }else {
                activityStoryDetailBinding.uprStoryTotalLikeTxt.setText("");
            }
        }
        if (postedDataList != null) {
            if (postedDataList.getPosted() != null) {
                    activityStoryDetailBinding.totalPostedTxt.setText(String.valueOf(postedDataList.getPosted()));
            }else{
                activityStoryDetailBinding.totalPostedTxt.setText("");
            }
        }
        if (postedDataList != null) {
            if (postedDataList.getComments() != null) {
                    activityStoryDetailBinding.totalCommentTxt.setText(String.valueOf(postedDataList.getComments()));
            }else{
                activityStoryDetailBinding.totalCommentTxt.setText("");
            }
        }
        if (postedDataList != null) {
            if (postedDataList.getPostView() != null) {
                    activityStoryDetailBinding.totalViewTxt.setText(String.valueOf(postedDataList.getPostView()));
            }else{
                activityStoryDetailBinding.totalViewTxt.setText("");
            }
        }

//        if (storyHeadingStr == null) {
//            if (storyHeadingStr.equalsIgnoreCase("")) {
//                storyHeadingStr = storyDetailDataList.getData().get(0).getStoryTitle();
//                activityStoryDetailBinding.showStoryTitleTxt.setText(storyHeadingStr);
//            }
//        }
//
//        if (storyUrlStr == null) {
//            if (storyUrlStr.equalsIgnoreCase("")) {
//                storyUrlStr = storyDetailDataList.getData().get(0).getStoryWebURL();
//            }
//        }
//
//        if (storyCategory == null) {
//            if (storyCategory.equalsIgnoreCase("")) {
//                storyCategory = storyDetailDataList.getData().get(0).getBASubCategoryName();
//                activityStoryDetailBinding.typeTxt.setText(storyCategory);
//            }
//        }
//
//        if (storyAuthor == null) {
//            if (storyAuthor.equalsIgnoreCase("")) {
//                storyAuthor = storyDetailDataList.getData().get(0).getAuthorImageURL();
//                Utils.setImageInImageView(storyAuthor, activityStoryDetailBinding.userImage, mContext);
//            }
//        }
//
//        if (storyHeaderImg == null) {
//            if (storyHeaderImg.equalsIgnoreCase("")) {
//                storyHeaderImg = storyDetailDataList.getData().get(0).getStrThumbImageName();
//                Utils.setImageInImageView(storyHeaderImg, activityStoryDetailBinding.backdrop, mContext);
//            }
//        }
//
//        if (storyAuthorId <= 0) {
//            storyAuthorId = storyDetailDataList.getData().get(0).getAuthorId();
//        }
//
//        if (storyId <= 0) {
//            storyId = storyDetailDataList.getData().get(0).getStoryId();
//        }
        //Font must be placed in assets/fonts folder
        String text = "<html><style type='text/css'>@font-face { font-family: thesansplain; src: url('fonts/thesansplain.ttf'); } body p {font-family: thesansplain;}</style>"
                + "<body >" + "<p align=\"justify\" style=\"font-size: 22px; font-family: spqr;\">" + storyDetailDataList.getData().get(0).getStoryDescription() + "</p> " + "</body></html>";

        activityStoryDetailBinding.shimmerViewContainer.stopShimmerAnimation();
        activityStoryDetailBinding.shimmerViewContainer.setVisibility(View.GONE);
        activityStoryDetailBinding.auhtorStoryDetailView.setVisibility(View.VISIBLE);

        if (storyDetailDataList.getData().get(0).getIsLike().equals(1)) {
            activityStoryDetailBinding.uprStoryLikeBtn.setLiked(true);
            activityStoryDetailBinding.bottomStoryLikeBtn.setLiked(true);
        } else {
            activityStoryDetailBinding.uprStoryLikeBtn.setLiked(false);
            activityStoryDetailBinding.bottomStoryLikeBtn.setLiked(false);
        }
        activityStoryDetailBinding.storyDetailView.getSettings().setJavaScriptEnabled(true);

        Log.d("data", storyDetailDataList.getData().get(0).getStoryDescription());
        activityStoryDetailBinding.storyDetailView.setVerticalScrollBarEnabled(false);
        activityStoryDetailBinding.storyDetailView.loadDataWithBaseURL("file:///android_asset/", text, "text/html", "UTF-8", null);


        activityStoryDetailBinding.storyDetailView.setWebViewClient(new WebViewClient() {


            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // check the URL, and do whatever you need to do according to the URL
//                if(url.equals("hrupin://second_activity")){
//                    Intent intent = new Intent(mContext, MoreInformationActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //ugly solution to avoid starting 2 activities, not ideal but it works
//                    startActivity(intent);

//                }


                // return true; // if you handled URL, and WebView should not load it
                return false; // for the WebView to load the URL
            }
        });
    }


    public void shareArticleValue() {
        if (Utils.isMember(mContext, "storyDetail")) {
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
    }

    public void commentArticleValue() {
        if (Utils.isMember(mContext, "storyDetail")) {
            Intent commentIntent = new Intent(mContext, CommentActivity.class);
            commentIntent.putExtra("referenceId", String.valueOf(storyId));
            commentIntent.putExtra("sourceType", "3");
            commentIntent.putExtra("pageTitle", storyHeadingStr);
            mContext.startActivity(commentIntent);
        }
    }

    public void LikeArticleValue(int likestatus) {
        if (Utils.isMember(mContext, "storyDetail")) {
            Utils.LikeMemberId = String.valueOf(Utils.getAppUserId(mContext));
            Utils.LikeReferenceId = String.valueOf(storyId);
            Utils.LikeSourceType = "3";
            Utils.LikeStatus = String.valueOf(likestatus);
            Utils.InsertLike(mContext, StoryDetailActivity.this);
            int likeunlikecount = 0;
            likeunlikecount = Integer.parseInt(activityStoryDetailBinding.uprStoryTotalLikeTxt.getText().toString());
            if (Utils.LikeStatus.equalsIgnoreCase("1")) {
                activityStoryDetailBinding.uprStoryTotalLikeTxt.setText(String.valueOf(likeunlikecount + 1));
            } else {
                activityStoryDetailBinding.uprStoryTotalLikeTxt.setText(String.valueOf(likeunlikecount - 1));
            }
        } else {
            Log.d("likestatus :",""+likestatus);
            if (likestatus == 1) {
                activityStoryDetailBinding.uprStoryLikeBtn.setLiked(false);
                activityStoryDetailBinding.bottomStoryLikeBtn.setLiked(false);
            } else {
                activityStoryDetailBinding.uprStoryLikeBtn.setLiked(true);
                activityStoryDetailBinding.bottomStoryLikeBtn.setLiked(true);
            }
        }
    }

    // Api calling GetVideoGalleryData
    public void callPostedViewData() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), StoryDetailActivity.this);
            return;
        }

//        Utils.showDialog(mContext);

        ApiHandler.getApiService().getBAPostStatistics(getPostedData(), new retrofit.Callback<LogginModel>() {
            @Override
            public void success(LogginModel postedDataModel, Response response) {
                Utils.dismissDialog();
                if (postedDataModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (postedDataModel.getIsValid() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (postedDataModel.getIsValid() == 0) {
//                    Utils.ping(mContext, getString(R.string.false_msg));
                    postedDataList = postedDataModel.getData();
                    setAPIValue();
                    return;
                }
                if (postedDataModel.getIsValid() == 1) {

                    if (postedDataModel.getData() != null) {
                        postedDataList = postedDataModel.getData();
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

    private Map<String, String> getPostedData() {
        Map<String, String> map = new HashMap<>();
        map.put("MemberId", String.valueOf(Utils.getAppUserId(mContext)));
        map.put("PostId", String.valueOf(storyId));
        map.put("SourceType", "3");

        return map;
    }
}
