package com.bharatarmy.Activity;

import android.app.PictureInPictureParams;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.util.Rational;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Adapter.RelatedVideoAdapter;
import com.bharatarmy.Adapter.VideoListAdapter;
import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.Models.ImageDetailModel;
import com.bharatarmy.Models.ImageMainModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.ApiHandler;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.VideoModule.FullscreenVideoView;
import com.bharatarmy.VideoModule.OrientationHelper;
import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.universalvideoview.UniversalMediaController;
import com.universalvideoview.UniversalVideoView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.RetrofitError;
import retrofit.client.Response;


//    change code and design 21/06/2019
public class VideoDetailActivity extends AppCompatActivity implements View.OnClickListener, UniversalVideoView.VideoViewCallback, OrientationHelper.VideoFullViewCallback {
    // ActivityVideoDetailBinding videoDetailBinding;
    Context mContext;
    String videoUrlStr, videoNameStr, whereToComeStr, theWord;
    FullscreenVideoView fullscreenVideoView;
    TextView show_video_title_txt;
    LinearLayout backImg, upr_story_comment, picturemode_linear;
    ShimmerRecyclerView related_video_rcyList;
    private static final String SEEK_POSITION_KEY = "SEEK_POSITION_KEY";

    RelatedVideoAdapter relatedVideoAdapter;
    List<ImageDetailModel> videoDetailModelsList;
    String imageClickData;

//    private final PictureInPictureParams.Builder pictureInPictureParamsBuilder =
//            new PictureInPictureParams.Builder();

    //Univarsal
    private int mSeekPosition;
    private int cachedHeight;
    private boolean isFullscreen;

    View mBottomLayout;
    View mVideoLayout;

    UniversalVideoView mVideoView;
    UniversalMediaController mMediaController;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);

        mContext = VideoDetailActivity.this;


        init();
        setDataValue();
        callVideoGalleryData();
        setListiner();

    }

    public void init() {
        fullscreenVideoView = (FullscreenVideoView) findViewById(R.id.fullscreenVideoView);
        backImg = (LinearLayout) findViewById(R.id.back_img);
        related_video_rcyList = (ShimmerRecyclerView) findViewById(R.id.related_video_rcyList);
        related_video_rcyList.showShimmerAdapter();
        picturemode_linear = (LinearLayout) findViewById(R.id.picturemode_linear);


//        mVideoLayout = findViewById(R.id.video_play_layout);
        mBottomLayout = findViewById(R.id.bottom_layout);
//        mVideoView = (UniversalVideoView) findViewById(R.id.videoView);
//        mMediaController = (UniversalMediaController) findViewById(R.id.media_controller);
//        mVideoView.setMediaController(mMediaController);
//        setVideoAreaSize();
//        mVideoView.setVideoViewCallback(this);
//
//        if (mSeekPosition > 0) {
//            mVideoView.seekTo(mSeekPosition);
//        }
//        mVideoView.start();
    }

    public void setDataValue() {
        videoUrlStr = getIntent().getStringExtra("videoData");
        videoNameStr = getIntent().getStringExtra("videoName");
        whereToComeStr = getIntent().getStringExtra("WhereToVideoCome");

        fullscreenVideoView.videoUrl(videoUrlStr)
                .enableAutoStart()
                .addSeekBackwardButton()
                .addSeekForwardButton();
    }

    public void setListiner() {
        backImg.setOnClickListener(this);
        picturemode_linear.setOnClickListener(this);

//        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mp) {
//                Log.d("VideoDetailActivity", "onCompletion ");
//            }
//        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                VideoDetailActivity.this.finish();
                break;
            case R.id.picturemode_linear:
//                pictureInPictureMode();
                break;
        }
    }

//    private void pictureInPictureMode() {
//        Rational aspectRatio = new Rational(fullscreenVideoView.getWidth(), fullscreenVideoView.getHeight());
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            pictureInPictureParamsBuilder.setAspectRatio(aspectRatio).build();
//            enterPictureInPictureMode(pictureInPictureParamsBuilder.build());
//        }
//
//    }
//
//    @Override
//    public void onUserLeaveHint() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            if (!isInPictureInPictureMode()) {
//                Rational aspectRatio = new Rational(fullscreenVideoView.getWidth(), fullscreenVideoView.getHeight());
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                    pictureInPictureParamsBuilder.setAspectRatio(aspectRatio).build();
//                    enterPictureInPictureMode(pictureInPictureParamsBuilder.build());
//                }
//
//            }
//        }
//    }

    @Override
    public void onBackPressed() {
        if (this.isFullscreen) {
//            videoDetailBinding.videoView.setFullscreen(false);
        } else {
            super.onBackPressed();
        }
    }

    private void setVideoAreaSize() {
    mVideoLayout.post(new Runnable() {
            @Override
            public void run() {
                int width = mVideoLayout.getWidth();
                cachedHeight = (int) (width * 405f / 720f);
//                cachedHeight = (int) (width * 3f / 4f);
//                cachedHeight = (int) (width * 9f / 16f);
                ViewGroup.LayoutParams videoLayoutParams = mVideoLayout.getLayoutParams();
                videoLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                videoLayoutParams.height = cachedHeight;
                mVideoLayout.setLayoutParams(videoLayoutParams);
                mVideoView.setVideoPath(videoUrlStr);
                mVideoView.requestFocus();
            }
        });
    }

//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
////        Log.d("saveVideo", "onSaveInstanceState Position=" + videoDetailBinding.videoView.getCurrentPosition());
//        outState.putInt(SEEK_POSITION_KEY, mSeekPosition);
//
//        outState.putString("videoName",videoNameStr);
////        outState.putParcelable("data",videoDetailModelsList);
//    }
//
//    @Override
//    protected void onRestoreInstanceState(Bundle outState) {
//        super.onRestoreInstanceState(outState);
//        mSeekPosition = outState.getInt(SEEK_POSITION_KEY);
//        Log.d("restoreVideo", "onRestoreInstanceState Position=" + mSeekPosition);
//        videoNameStr = outState.getString("videoName");
//    }

    @Override
    public void onScaleChange(boolean isFullscreen) {
        this.isFullscreen = isFullscreen;
        if (isFullscreen) {
            ViewGroup.LayoutParams layoutParams = mVideoLayout.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
            mVideoLayout.setLayoutParams(layoutParams);
            mBottomLayout.setVisibility(View.GONE);

        } else {
            ViewGroup.LayoutParams layoutParams = mVideoLayout.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.height = this.cachedHeight;
            mVideoLayout.setLayoutParams(layoutParams);
            mBottomLayout.setVisibility(View.VISIBLE);
        }

        switchTitleBar(!isFullscreen);
    }

    @Override
    public void onPause(MediaPlayer mediaPlayer) {

    }

    @Override
    public void onStart(MediaPlayer mediaPlayer) {

    }

    @Override
    public void onBufferingStart(MediaPlayer mediaPlayer) {

    }

    @Override
    public void onBufferingEnd(MediaPlayer mediaPlayer) {

    }

    private void switchTitleBar(boolean show) {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            if (show) {
                supportActionBar.show();
            } else {
                supportActionBar.hide();
            }
        }
    }


    @Override
    public void onFullScaleChange() {
//        if (textView.isShown()){
//            textView.setVisibility(View.GONE);
//        }else{
//            textView.setVisibility(View.VISIBLE);
//        }
    }


    // Api calling GetVideoGalleryData
    public void callVideoGalleryData() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), VideoDetailActivity.this);
            return;
        }

//        Utils.showDialog(mContext);

        ApiHandler.getApiService().getBAVideoGallery(getVideoGalleryData(), new retrofit.Callback<ImageMainModel>() {
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
                        videoDetailModelsList = imageMainModel.getData();
                        fillVideoGallery();
                        related_video_rcyList.hideShimmerAdapter();
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
        map.put("PageIndex", "0");
        map.put("PageSize", "20");
        return map;
    }

    public void fillVideoGallery() {


        relatedVideoAdapter = new RelatedVideoAdapter(mContext, videoDetailModelsList, videoNameStr, new image_click() {
            @Override
            public void image_more_click() {
                imageClickData = "";
                imageClickData = String.valueOf(relatedVideoAdapter.getData());
                imageClickData = imageClickData.replaceAll("\\[", "").replaceAll("\\]", "");
                String[] spiltvalue = imageClickData.split("\\|");


                Log.d("imageClickData :", imageClickData + " spiltvalue :" + spiltvalue[0] + "spiltvalue1:" + spiltvalue[1]);

                videoUrlStr = spiltvalue[0];
                videoNameStr = spiltvalue[1];



                fullscreenVideoView.videoUrl(videoUrlStr)
                        .enableAutoStart()
                        .addSeekBackwardButton()
                        .addSeekForwardButton();
            }
        });
        related_video_rcyList.setAdapter(relatedVideoAdapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        related_video_rcyList.setLayoutManager(mLayoutManager);
        related_video_rcyList.setItemAnimator(new DefaultItemAnimator());
        related_video_rcyList.setAdapter(relatedVideoAdapter);

    }
}
