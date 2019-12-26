package com.bharatarmy.Activity;

import android.app.PictureInPictureParams;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Rational;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Adapter.RelatedVideoAdapter;
import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.Models.ImageDetailModel;
import com.bharatarmy.Models.ImageMainModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.ApiHandler;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.VideoModule.FullscreenVideoView;
import com.facebook.shimmer.ShimmerFrameLayout;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.RetrofitError;
import retrofit.client.Response;


//    change code and design 21/06/2019
public class VideoDetailActivity extends AppCompatActivity implements View.OnClickListener {
    // ActivityVideoDetailBinding videoDetailBinding;
    Context mContext;
    String videoUrlStr, videoNameStr,videoUserNameStr, whereToComeStr, videoLike;
    FullscreenVideoView fullscreenVideoView;
    LinearLayout backImg, picturemode_linear;
    RecyclerView related_video_rcyList;
    ShimmerFrameLayout shimmerFrameLayout;
    RelatedVideoAdapter relatedVideoAdapter;
    List<ImageDetailModel> videoDetailModelsList;
    String imageClickData;


    View mBottomLayout;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);

        mContext = VideoDetailActivity.this;


        init();
        setDataValue();

        setListiner();

    }

    public void init() {
        fullscreenVideoView = (FullscreenVideoView) findViewById(R.id.fullscreenVideoView);
        backImg = (LinearLayout) findViewById(R.id.back_img);
        related_video_rcyList = (RecyclerView) findViewById(R.id.related_video_rcyList);
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

        callVideoGalleryData();
    }

    public void setDataValue() {
        videoUrlStr = getIntent().getStringExtra("videoData");
        videoNameStr = getIntent().getStringExtra("videoName");
        videoUserNameStr=getIntent().getStringExtra("videoUserName");
        whereToComeStr = getIntent().getStringExtra("WhereToVideoCome");
        videoLike=getIntent().getStringExtra("videoLike");

        fullscreenVideoView.videoUrl(videoUrlStr)
                .enableAutoStart()
                .addSeekBackwardButton()
                .addSeekForwardButton();
    }

    public void setListiner() {
        backImg.setOnClickListener(this);
        picturemode_linear.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                VideoDetailActivity.this.finish();
                break;
            case R.id.picturemode_linear:
                pictureInPictureMode();
                break;
        }
    }

    private void pictureInPictureMode() {
        Rational aspectRatio = new Rational(fullscreenVideoView.getWidth(), fullscreenVideoView.getHeight());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            PictureInPictureParams.Builder pictureInPictureParamsBuilder =
                    new PictureInPictureParams.Builder();
            pictureInPictureParamsBuilder.setAspectRatio(aspectRatio).build();
            enterPictureInPictureMode(pictureInPictureParamsBuilder.build());
        }

    }

    @Override
    public void onBackPressed() {
           VideoDetailActivity.this.finish();
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
                        shimmerFrameLayout.stopShimmerAnimation();
                        shimmerFrameLayout.setVisibility(View.GONE);
                        related_video_rcyList.setVisibility(View.VISIBLE);
                        videoDetailModelsList = imageMainModel.getData();
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

    private Map<String, String> getVideoGalleryData() {
        Map<String, String> map = new HashMap<>();
        map.put("PageIndex", "0");
        map.put("PageSize", "20");
        map.put("MemberId", String.valueOf(Utils.getAppUserId(mContext)));
        return map;
    }

    public void fillVideoDetailGallery() {

        relatedVideoAdapter = new RelatedVideoAdapter(mContext,VideoDetailActivity.this, videoDetailModelsList,
                videoNameStr,videoUserNameStr,videoLike, new image_click() {
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
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        related_video_rcyList.setLayoutManager(mLayoutManager);
        related_video_rcyList.setItemAnimator(new DefaultItemAnimator());
        related_video_rcyList.setAdapter(relatedVideoAdapter);

    }
}
