package com.bharatarmy.Activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.media.MediaPlayer;
import android.net.Uri;
import android.widget.MediaController;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityVideoDetailBinding;
import com.bumptech.glide.Glide;

public class VideoDetailActivity extends BaseActivity implements View.OnClickListener {
    ActivityVideoDetailBinding videoDetailBinding;
    Context mContext;
    String videoUrlStr,videoNameStr,whereToComeStr;
    MediaController mediaControls;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        videoDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_video_detail);

        mContext = VideoDetailActivity.this;
        setDataValue();
        setTitleText(videoNameStr);
        setBackButton(VideoDetailActivity.this);
//        setListiner();
    }

    public void setDataValue() {
        videoUrlStr = getIntent().getStringExtra("videoData");
        videoNameStr=getIntent().getStringExtra("videoName");
        whereToComeStr=getIntent().getStringExtra("WhereToVideoCome");

//        videoDetailBinding.toolbarTitleTxt.setText(videoNameStr);
        if (mediaControls == null) {
            // create an object of media controller class
            mediaControls = new MediaController(mContext);
            mediaControls.setAnchorView(videoDetailBinding.videoView);
        }
        // set the media controller for video view
        videoDetailBinding.videoView.setMediaController(mediaControls);
        // set the uri for the video view
        videoDetailBinding.videoView.setVideoPath(videoUrlStr);
//        String path = "android.resource://" + getPackageName() + "/" + R.raw.samplevideo_1280x720_10mb;
//        videoDetailBinding.videoView.setVideoURI(Uri.parse(path));

//        videoDetailBinding.progressbar.setVisibility(View.VISIBLE);
        videoDetailBinding.image.setVisibility(View.VISIBLE);
        Glide.with(mContext).load(R.drawable.logo_white).into(videoDetailBinding.image);
        videoDetailBinding.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                // TODO Auto-generated method stub
                mp.start();
                mp.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mp, int arg1,
                                                   int arg2) {
                        // TODO Auto-generated method stub
//                        videoDetailBinding.progressbar.setVisibility(View.GONE);
                        videoDetailBinding.image.setVisibility(View.GONE);
                        // start a video
                        videoDetailBinding.videoView.start();
                        mp.start();
                    }
                });
            }
        });
        // implement on completion listener on video view
        videoDetailBinding.videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Utils.ping(mContext, "Thank you"); // display a toast when an video is completed
            }
        });
        videoDetailBinding.videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Utils.ping(mContext, "Oops An Error Occur While Playing Video...!!!"); // display a toast when an error is occured while playing an video
                return false;
            }
        });
    }

//    public void setListiner() {
//        videoDetailBinding.backImg.setOnClickListener(this);
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                VideoDetailActivity.this.finish();
                break;
        }
    }
}
