package com.bharatarmy.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bharatarmy.Models.GalleryImageModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.VideoTrimmer.interfaces.OnHgLVideoListener;
import com.bharatarmy.VideoTrimmer.interfaces.OnTrimVideoListener;
import com.bharatarmy.VideoTrimmer.utils.FileUtils;
import com.bharatarmy.databinding.ActivityVideoTrimBinding;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class VideoTrimActivity extends AppCompatActivity implements View.OnClickListener, OnTrimVideoListener, OnHgLVideoListener {

    ActivityVideoTrimBinding activityVideoTrimBinding;
    Context mContext;
    Uri selectedUri;
    private static final int REQUEST_VIDEO_TRIMMER = 0x01;
    private ProgressDialog mProgressDialog;
    String videoPath;
    String path = "";
    int maxDuration = 10;
    long findsize;
    String duration, videoHeight, videoWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityVideoTrimBinding = DataBindingUtil.setContentView(this, R.layout.activity_video_trim);

        mContext = VideoTrimActivity.this;

        init();
        setListiner();
    }

    public void init() {
        videoPath = getIntent().getStringExtra("videoPath");
        selectedUri = Uri.parse(videoPath);
        if (selectedUri != null) {

            path = FileUtils.getPath(this, selectedUri);
            maxDuration = getMediaDuration(selectedUri);
            duration = getDuration(selectedUri);
//            Log.d("duration :", "" + duration);
//            Log.d("path :", "" + path);
            if (path != null) {

                if (activityVideoTrimBinding.timeLine != null) {
                    /**
                     * get total duration of video file
                     */
                    Log.e("tg", "maxDuration = " + maxDuration);
                    Log.d("Videopath", path);
                    //mVideoTrimmer.setMaxDuration(maxDuration);
                    activityVideoTrimBinding.timeLine.setMaxDuration(maxDuration);
                    activityVideoTrimBinding.timeLine.setOnTrimVideoListener(this);
                    activityVideoTrimBinding.timeLine.setOnHgLVideoListener(this);
                    activityVideoTrimBinding.timeLine.setVideoURI(Uri.parse(path));
                    activityVideoTrimBinding.timeLine.setVideoInformationVisibility(true);


                }
            } else {
                Utils.ping(mContext, "please select proper video");
            }
        }

    }

    public void setListiner() {

        activityVideoTrimBinding.submitLinear.setOnClickListener(this);

        activityVideoTrimBinding.backImg.setOnClickListener(this);
    }


    @Override
    public void onVideoPrepared() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // Toast.makeText(TrimmerActivity.this, "onVideoPrepared", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onTrimStarted() {

    }

    @Override
    public void getResult(Uri contentUri, int height, int width) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                try {
                    path = contentUri.getPath();
                    duration = getDuration(contentUri);
                    videoHeight = String.valueOf(height);
                    videoWidth = String.valueOf(width);
                    Log.d("trimduration :", duration + "trimpath :" + path + "height :" + height + "width :" + width);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }


            }
        });


    }

    @Override
    public void cancelAction() {
        activityVideoTrimBinding.timeLine.destroy();
    }

    @Override
    public void onError(String message) {
//        mProgressDialog.cancel();

//        Utils.dismissDialog();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // Toast.makeText(TrimmerActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private int getMediaDuration(Uri uriOfFile) {
        MediaPlayer mp = MediaPlayer.create(this, uriOfFile);
        int duration=0;
        if (mp!=null){
            duration = mp.getDuration();
       }
//


        return duration;

    }

    public String getDuration(Uri uriOfFile) {
        MediaPlayer mp = MediaPlayer.create(mContext, uriOfFile);
        int duration = mp.getDuration() / 1000;
        int hours = duration / 3600;
        int minutes = (duration / 60) - (hours * 60);
        int seconds = duration - (hours * 3600) - (minutes * 60);
        String formatted = String.format("%d:%02d:%02d", hours, minutes, seconds);

        return formatted;
    }

    public String size(int size) {
        String hrSize = "";
        double m = size / 1024.0;
        DecimalFormat dec = new DecimalFormat("0");

        if (m > 1) {
            hrSize = dec.format(m).concat(" MB");
        } else {
            hrSize = dec.format(size).concat(" KB");
        }
        return hrSize;
    }

    //    height :848width :480
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit_linear:
                showProgressDialog();
//                Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.loading), VideoTrimActivity.this);
//                Utils.handleClickEvent(mContext,activityVideoTrimBinding.submitLinear);
                File f = new File(path);
                findsize = f.length() / 1024;
                Log.d("Videopath", path);
                Intent intentVideoUpload = new Intent(mContext, VideoUploadActivity.class);
                intentVideoUpload.putExtra("videoPath", path);
                intentVideoUpload.putExtra("videoDuratiion", duration);
                intentVideoUpload.putExtra("videoSize", size((int) findsize));
                intentVideoUpload.putExtra("videoheight", videoHeight);
                intentVideoUpload.putExtra("videowidth", videoWidth);
                startActivity(intentVideoUpload);
//                Utils.dismissDialog();
                finish();
                break;

            case R.id.back_img:
                VideoTrimActivity.this.finish();
                break;
        }
    }


    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }
}
