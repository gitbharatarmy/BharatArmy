package com.bharatarmy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.PictureInPictureParams;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Rational;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bharatarmy.Adapter.VideoDetailHorizontalVideoAdapter;
import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.Models.ImageDetailModel;
import com.bharatarmy.Models.ImageMainModel;
import com.bharatarmy.Models.LogginModel;
import com.bharatarmy.Models.LoginDataModel;
import com.bharatarmy.Models.MyScreenChnagesModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.ApiHandler;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.VideoModule.FullscreenVideoView;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class VideoDetailHorizontalActivity extends AppCompatActivity implements View.OnClickListener {

    Context mContext;
    String videoUrlStr, videoNameStr,videoUserNameStr, whereToComeStr, videoLike,videoThumbStr,videoDescriptionStr;
    FullscreenVideoView fullscreenHorizontalVideoView;
    LinearLayout backImg, picturemode_linear;
    RecyclerView related_horizontal_video_rcyList;
    ShimmerFrameLayout shimmerFrameLayout;
    VideoDetailHorizontalVideoAdapter relatedhorizontalVideoAdapter;
    List<ImageDetailModel> videoDetailModelsList;
    String imageClickData,videoIdStr;
    LoginDataModel postedDataList;
ImageView videoView_thumbnail;
    View mBottomLayout;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail_horizontal);

        mContext = VideoDetailHorizontalActivity.this;

        EventBus.getDefault().register(this);

        init();
        setDataValue();

        setListiner();

    }

    public void init() {
        fullscreenHorizontalVideoView = (FullscreenVideoView) findViewById(R.id.fullscreenHorizontalVideoView);
        backImg = (LinearLayout) findViewById(R.id.back_img);
        related_horizontal_video_rcyList = (RecyclerView) findViewById(R.id.related_horizontal_video_rcyList);
        shimmerFrameLayout=(ShimmerFrameLayout)findViewById(R.id.shimmer_view_container);
        picturemode_linear = (LinearLayout) findViewById(R.id.picturemode_linear);
        mBottomLayout = findViewById(R.id.bottom_layout);

        shimmerFrameLayout.setVisibility(View.VISIBLE);
        shimmerFrameLayout.startShimmerAnimation();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            picturemode_linear.setVisibility(View.VISIBLE);
        }else{
            picturemode_linear.setVisibility(View.GONE);
        }


    }

    public void setDataValue() {
        videoUrlStr = getIntent().getStringExtra("videoData");
        videoNameStr = getIntent().getStringExtra("videoName");
        videoUserNameStr=getIntent().getStringExtra("videoUserName");
        whereToComeStr = getIntent().getStringExtra("WhereToVideoCome");
        videoLike=getIntent().getStringExtra("videoLike");
        videoIdStr = getIntent().getStringExtra("videoId");
        videoThumbStr=getIntent().getStringExtra("videoThumb");


        AppConfiguration.videoThumbStr=videoThumbStr;




        fullscreenHorizontalVideoView.videoUrl(videoUrlStr)
                .enableAutoStart()
                .addSeekBackwardButton()
                .addSeekForwardButton();


        callRelatedVideoGalleryData();


    }

    @Subscribe
    public void customEventReceived(MyScreenChnagesModel event){
        Log.d("event :",event.getMessage());
        if (event.getMessage().equalsIgnoreCase("true")){
//            videoView_thumbnail.setVisibility(View.GONE);
            fullscreenHorizontalVideoView.getLayoutParams().height= RecyclerView.LayoutParams.WRAP_CONTENT;
        }else{
            fullscreenHorizontalVideoView.getLayoutParams().height= RecyclerView.LayoutParams.WRAP_CONTENT;
        }
    }

    public void setListiner() {
        backImg.setOnClickListener(this);
        picturemode_linear.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                VideoDetailHorizontalActivity.this.finish();
                break;
            case R.id.picturemode_linear:
                pictureInPictureMode();
                break;

        }
    }


    private void pictureInPictureMode() {

        Rational aspectRatio = new Rational(fullscreenHorizontalVideoView.getWidth(), fullscreenHorizontalVideoView.getHeight());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            PictureInPictureParams.Builder pictureInPictureParamsBuilder =
                    new PictureInPictureParams.Builder();
            pictureInPictureParamsBuilder.setAspectRatio(aspectRatio).build();
            enterPictureInPictureMode(pictureInPictureParamsBuilder.build());
        }

    }

    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode);
    }

    @Override
    public void onBackPressed() {
        VideoDetailHorizontalActivity.this.finish();
    }


    // Api calling GetVideoGalleryData
    public void callRelatedVideoGalleryData() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), VideoDetailHorizontalActivity.this);
            return;
        }

//        Utils.showDialog(mContext);

        ApiHandler.getApiService().getBARelatedVideos(getVideoGalleryData(), new retrofit.Callback<ImageMainModel>() {
            @Override
            public void success(ImageMainModel relatedHorizontalVideoModel, Response response) {
                Utils.dismissDialog();
                if (relatedHorizontalVideoModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (relatedHorizontalVideoModel.getIsValid() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (relatedHorizontalVideoModel.getIsValid() == 0) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    return;
                }
                if (relatedHorizontalVideoModel.getIsValid() == 1) {

                    if (relatedHorizontalVideoModel.getData() != null) {
                        videoDetailModelsList = relatedHorizontalVideoModel.getData();


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
        map.put("CurrentVideoId",videoIdStr);
        map.put("PageIndex", "0");
        map.put("PageSize", "20");
        map.put("MemberId",String.valueOf(Utils.getAppUserId(mContext)));
        return map;
    }

    public void fillVideoDetailGallery() {

        relatedhorizontalVideoAdapter = new VideoDetailHorizontalVideoAdapter(mContext,VideoDetailHorizontalActivity.this, videoDetailModelsList,
                videoNameStr,videoUserNameStr,videoLike,postedDataList,videoIdStr);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        related_horizontal_video_rcyList.setLayoutManager(mLayoutManager);
        related_horizontal_video_rcyList.setItemAnimator(new DefaultItemAnimator());
        related_horizontal_video_rcyList.setAdapter(relatedhorizontalVideoAdapter);

    }

    // Api calling GetVideoGalleryData
    public void callPostedViewData() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), VideoDetailHorizontalActivity.this);
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
                    postedDataList=postedDataModel.getData();
                    shimmerFrameLayout.stopShimmerAnimation();
                    shimmerFrameLayout.setVisibility(View.GONE);
                    related_horizontal_video_rcyList.setVisibility(View.VISIBLE);

                    fillVideoDetailGallery();
                    return;
                }
                if (postedDataModel.getIsValid() == 1) {

                    if (postedDataModel.getData() != null) {
                        postedDataList=postedDataModel.getData();
                        shimmerFrameLayout.stopShimmerAnimation();
                        shimmerFrameLayout.setVisibility(View.GONE);
                        related_horizontal_video_rcyList.setVisibility(View.VISIBLE);

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
}
