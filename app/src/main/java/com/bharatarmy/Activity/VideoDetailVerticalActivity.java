package com.bharatarmy.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.PictureInPictureParams;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Rational;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bharatarmy.Adapter.VideoDetailVerticalAdapter;
import com.bharatarmy.Interfaces.MyLayoutChanges;
import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.Models.ImageDetailModel;
import com.bharatarmy.Models.ImageMainModel;
import com.bharatarmy.Models.LogginModel;
import com.bharatarmy.Models.LoginDataModel;
import com.bharatarmy.Models.MyScreenChnagesModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.ApiHandler;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.MyApplicationpip;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.VideoModule.FullscreenVideoView;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class VideoDetailVerticalActivity extends AppCompatActivity implements View.OnClickListener {

    Context mContext;
    String videoUrlStr, videoNameStr, videoUserNameStr, whereToComeStr, videoLike, videoThumb;
    FullscreenVideoView fullscreenVericaleVideoView;
    LinearLayout backImg, picturemode_linear, mBottomLayout, bottom_gradiant_line;
    RecyclerView related_vertical_video_rcyList;
    ShimmerFrameLayout shimmerFrameLayout;
    VideoDetailVerticalAdapter videoDetailVerticalAdapter;
    List<ImageDetailModel> videoDetailModelsList;
    String imageClickData, videoIdStr;
    RelativeLayout video_play_layout;
    AppBarLayout appbar;
    //    Toolbar toolbar;
    CollapsingToolbarLayout collapsing_toolbar;
    ImageView vertical_backimage;
    LoginDataModel postedDataList;
    Handler timerHandler;
    Runnable timerRunnable;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail_vertical);


        mContext = VideoDetailVerticalActivity.this;
        EventBus.getDefault().register(this);

        init();
        setDataValue();

        setListiner();

    }

    public void init() {
        fullscreenVericaleVideoView = (FullscreenVideoView) findViewById(R.id.fullscreenVerticalVideoView);
        backImg = (LinearLayout) findViewById(R.id.back_img);
        related_vertical_video_rcyList = (RecyclerView) findViewById(R.id.related_vertical_video_rcyList);
        shimmerFrameLayout = (ShimmerFrameLayout) findViewById(R.id.shimmer_view_container);
        picturemode_linear = (LinearLayout) findViewById(R.id.picturemode_linear);
        mBottomLayout = (LinearLayout) findViewById(R.id.bottom_layout);
        video_play_layout = (RelativeLayout) findViewById(R.id.video_play_layout);
        appbar = (AppBarLayout) findViewById(R.id.appbar);
//        toolbar = (Toolbar) findViewById(R.id.vertical_toolbar);
        collapsing_toolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        bottom_gradiant_line = (LinearLayout) findViewById(R.id.bottom_gradiant_line);
        vertical_backimage = (ImageView) findViewById(R.id.vertical_backimage);

        shimmerFrameLayout.setVisibility(View.VISIBLE);
        shimmerFrameLayout.startShimmerAnimation();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            picturemode_linear.setVisibility(View.VISIBLE);
        } else {
            picturemode_linear.setVisibility(View.GONE);
        }
        stopScroll();
    }

    public void setDataValue() {
        videoUrlStr = getIntent().getStringExtra("videoData");
        videoNameStr = getIntent().getStringExtra("videoName");
        videoUserNameStr = getIntent().getStringExtra("videoUserName");
        whereToComeStr = getIntent().getStringExtra("WhereToVideoCome");
        videoLike = getIntent().getStringExtra("videoLike");
        videoIdStr = getIntent().getStringExtra("videoId");
        videoThumb = getIntent().getStringExtra("videoThumb");
        AppConfiguration.videoThumbStr = videoThumb;

        Log.d("videoStr", AppConfiguration.videoThumbStr);
        fullscreenVericaleVideoView.videoUrl(videoUrlStr)
                .enableAutoStart()
                .addSeekBackwardButton()
                .addSeekForwardButton();

        callRelatedVideoGalleryData();

    }

    public void setListiner() {
        backImg.setOnClickListener(this);
        picturemode_linear.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                VideoDetailVerticalActivity.this.finish();
                break;
            case R.id.picturemode_linear:
                pictureInPictureMode();
                break;
            case R.id.vertical_backimage:
//                VideoDetailVerticalActivity.this.finish();
                break;
        }
    }

    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode, Configuration newConfig) {
//        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig);
        if (isInPictureInPictureMode) {
            picturemode_linear.setVisibility(View.GONE);
            backImg.setVisibility(View.GONE);
            related_vertical_video_rcyList.setVisibility(View.GONE);
        } else {
            picturemode_linear.setVisibility(View.VISIBLE);
            backImg.setVisibility(View.VISIBLE);
            related_vertical_video_rcyList.setVisibility(View.VISIBLE);
        }
    }

    private void pictureInPictureMode() {

        backImg.setVisibility(View.GONE);

        Rational aspectRatio = new Rational(appbar.getWidth(), appbar.getHeight());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            PictureInPictureParams.Builder pictureInPictureParamsBuilder =
                    new PictureInPictureParams.Builder();
            pictureInPictureParamsBuilder.setAspectRatio(aspectRatio).build();
            enterPictureInPictureMode(pictureInPictureParamsBuilder.build());
        }

    }


    @Subscribe
    public void customEventReceived(MyScreenChnagesModel event) {
        Log.d("event :", event.getMessage());
        if (event.getMessage().equalsIgnoreCase("Full")) {

            related_vertical_video_rcyList.setVisibility(View.GONE);
            bottom_gradiant_line.setVisibility(View.GONE);
            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) appbar.getLayoutParams();
            AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) params.getBehavior();
            behavior.setDragCallback(new AppBarLayout.Behavior.DragCallback() {
                @Override
                public boolean canDrag(@NonNull AppBarLayout appBarLayout) {
                    return false;
                }
            });
            CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) appbar.getLayoutParams();
            lp.height = CoordinatorLayout.LayoutParams.MATCH_PARENT;

        } else if (event.getMessage().equalsIgnoreCase("Half")) {
            related_vertical_video_rcyList.setVisibility(View.VISIBLE);
            bottom_gradiant_line.setVisibility(View.VISIBLE);
            float heightDp = (float) (getResources().getDisplayMetrics().heightPixels / 1.3);
            CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) appbar.getLayoutParams();
            lp.height = (int) heightDp;
            AppConfiguration.screenType = "";
        }

    }

    @Override
    public void onBackPressed() {
        VideoDetailVerticalActivity.this.finish();
    }


    // Api calling GetVideoGalleryData
    public void callRelatedVideoGalleryData() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), VideoDetailVerticalActivity.this);
            return;
        }

//        Utils.showDialog(mContext);

        ApiHandler.getApiService().getBARelatedVideos(getVideoGalleryData(), new retrofit.Callback<ImageMainModel>() {
            @Override
            public void success(ImageMainModel relatedVerticalVideoModel, Response response) {
                Utils.dismissDialog();
                if (relatedVerticalVideoModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (relatedVerticalVideoModel.getIsValid() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (relatedVerticalVideoModel.getIsValid() == 0) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    return;
                }
                if (relatedVerticalVideoModel.getIsValid() == 1) {

                    if (relatedVerticalVideoModel.getData() != null) {
                        videoDetailModelsList = relatedVerticalVideoModel.getData();

                        callPostedViewData();

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

    private Map<String, String> getVideoGalleryData() {
        Map<String, String> map = new HashMap<>();
        map.put("CurrentVideoId", videoIdStr);
        map.put("PageIndex", "0");
        map.put("PageSize", "20");
        map.put("MemberId", String.valueOf(Utils.getAppUserId(mContext)));

        return map;
    }

    public void fillVideoDetailGallery() {
        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) collapsing_toolbar.getLayoutParams();
        params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED);
        videoDetailVerticalAdapter = new VideoDetailVerticalAdapter(mContext, VideoDetailVerticalActivity.this, videoDetailModelsList,
                videoNameStr, videoUserNameStr, videoLike, postedDataList, videoIdStr);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        related_vertical_video_rcyList.setLayoutManager(mLayoutManager);
        related_vertical_video_rcyList.setItemAnimator(new DefaultItemAnimator());
        related_vertical_video_rcyList.setAdapter(videoDetailVerticalAdapter);

    }


    // Api calling GetVideoGalleryData
    public void callPostedViewData() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), VideoDetailVerticalActivity.this);
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
                    shimmerFrameLayout.stopShimmerAnimation();
                    shimmerFrameLayout.setVisibility(View.GONE);
                    related_vertical_video_rcyList.setVisibility(View.VISIBLE);

                    fillVideoDetailGallery();
                    return;
                }
                if (postedDataModel.getIsValid() == 1) {

                    if (postedDataModel.getData() != null) {

                        postedDataList = postedDataModel.getData();
                        shimmerFrameLayout.stopShimmerAnimation();
                        shimmerFrameLayout.setVisibility(View.GONE);
                        related_vertical_video_rcyList.setVisibility(View.VISIBLE);
                        startScroll();

                        fillVideoDetailGallery();
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
        map.put("PostId", videoIdStr);
        map.put("SourceType", "2");
        return map;
    }

    public void stopScroll() {
        AppBarLayout.LayoutParams toolbarLayoutParams = (AppBarLayout.LayoutParams) collapsing_toolbar.getLayoutParams();
        toolbarLayoutParams.setScrollFlags(0);
        collapsing_toolbar.setLayoutParams(toolbarLayoutParams);

        CoordinatorLayout.LayoutParams appBarLayoutParams = (CoordinatorLayout.LayoutParams) appbar.getLayoutParams();
        appBarLayoutParams.setBehavior(null);
        appbar.setLayoutParams(appBarLayoutParams);
    }

    public void startScroll() {
        AppBarLayout.LayoutParams toolbarLayoutParams = (AppBarLayout.LayoutParams) collapsing_toolbar.getLayoutParams();
        toolbarLayoutParams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);
        collapsing_toolbar.setLayoutParams(toolbarLayoutParams);

        CoordinatorLayout.LayoutParams appBarLayoutParams = (CoordinatorLayout.LayoutParams) appbar.getLayoutParams();
        appBarLayoutParams.setBehavior(new AppBarLayout.Behavior());
        appbar.setLayoutParams(appBarLayoutParams);
    }
}
