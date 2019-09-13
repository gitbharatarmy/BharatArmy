package com.bharatarmy.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.bharatarmy.Models.GalleryImageModel;
import com.bharatarmy.R;
import com.bharatarmy.UploadService;
import com.bharatarmy.Utility.DbHandler;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityVideoUploadBinding;

import java.util.ArrayList;
import java.util.List;

public class VideoUploadActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityVideoUploadBinding activityVideoUploadBinding;
    Context mContext;


    public List<GalleryImageModel> galleryImageList;

    String pathStr, durationStr, sizeStr, videoTitleStr, videoDescriptionStr;


    DbHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityVideoUploadBinding = DataBindingUtil.setContentView(this, R.layout.activity_video_upload);
        mContext = VideoUploadActivity.this;
        init();
        setListiner();
    }

    public void init() {
        dbHandler = new DbHandler(mContext);


        galleryImageList = new ArrayList<>();

        pathStr = getIntent().getStringExtra("videoPath");
        durationStr = getIntent().getStringExtra("videoDuratiion");
        sizeStr = getIntent().getStringExtra("videoSize");


        if (!pathStr.equalsIgnoreCase("")) {
            activityVideoUploadBinding.chooseVideo.setImageBitmap(Utils.createVideoThumbNail(pathStr));
        }
    }

    public void setListiner() {
        Utils.setPref(mContext, "image/video", "video");
        activityVideoUploadBinding.submitLinear.setOnClickListener(this);
        activityVideoUploadBinding.backImg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.videoTitle_edt:
                activityVideoUploadBinding.videoUploadScrollView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        View lastChild =  activityVideoUploadBinding.videoUploadScrollView.getChildAt(activityVideoUploadBinding.videoUploadScrollView.getChildCount() - 1);
                        int bottom = lastChild.getBottom() +  activityVideoUploadBinding.videoUploadScrollView.getPaddingBottom();
                        int sy =  activityVideoUploadBinding.videoUploadScrollView.getScrollY();
                        int sh =  activityVideoUploadBinding.videoUploadScrollView.getHeight();
                        int delta = bottom - (sy + sh);
                        activityVideoUploadBinding.videoUploadScrollView.smoothScrollBy(0, delta);
                    }
                }, 200);
                break;
            case R.id.back_img:
                    Intent dashboardIntent = new Intent(mContext, DashboardActivity.class);
                    dashboardIntent.putExtra("whichPageRun", "1");
                    startActivity(dashboardIntent);
                    finish();
                break;

            case R.id.submit_linear:
                videoTitleStr = activityVideoUploadBinding.videoTitleEdt.getText().toString();
                videoDescriptionStr = activityVideoUploadBinding.videodescEdt.getText().toString();


                if (!videoTitleStr.equalsIgnoreCase("")) {

                        galleryImageList.add(new GalleryImageModel(pathStr, sizeStr, "0", durationStr, "4",videoTitleStr,videoDescriptionStr));
                        boolean connected = Utils.checkNetwork(mContext);

                        if (connected == true) {
                            if (galleryImageList != null && galleryImageList.size() > 0) {
                                for (int i = 0; i < galleryImageList.size(); i++) {
                                    dbHandler.insertImageDetails(galleryImageList.get(i).getImageUri(),
                                            galleryImageList.get(i).getImageSize(),
                                            galleryImageList.get(i).getUploadcompelet(),
                                            galleryImageList.get(i).getVideolength(),
                                            galleryImageList.get(i).getFileType(),
                                            galleryImageList.get(i).getVideoTitle(),
                                            galleryImageList.get(i).getVideoDesc(),mContext);
                                }
                                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(VideoUploadActivity.this);
                                LayoutInflater inflater = getLayoutInflater();
                                View dialogView = inflater.inflate(R.layout.thankyou_dialog_item, null);
                                dialogBuilder.setView(dialogView);
                                AlertDialog alertDialog = dialogBuilder.create();
                                alertDialog.setCanceledOnTouchOutside(false);
                                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                TextView dialog_headertxt = (TextView) dialogView.findViewById(R.id.dialog_headertxt);
                                TextView dialog_descriptiontxt = (TextView) dialogView.findViewById(R.id.dialog_descriptiontxt);
                                TextView hometxt = (TextView) dialogView.findViewById(R.id.home_txt);

                                Log.d("messageList :", Utils.retriveLoginOtherData(mContext).toString());
                                if (Utils.retriveLoginOtherData(mContext) != null) {
                                    for (int i = 0; i < Utils.retriveLoginOtherData(mContext).size(); i++) {
                                        if (Utils.retriveLoginOtherData(mContext).get(i).getMessageId().equals(1)) {
                                            dialog_headertxt.setText(Utils.retriveLoginOtherData(mContext).get(i).getMessageHeaderText());
                                            dialog_descriptiontxt.setText(Utils.retriveLoginOtherData(mContext).get(i).getMessageDescription());
                                        }

                                    }
                                }

                                hometxt.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        try {
                                            alertDialog.dismiss();
                                            Intent dashboardIntent = new Intent(mContext, DashboardActivity.class);
                                            dashboardIntent.putExtra("whichPageRun", "1");
                                            startActivity(dashboardIntent);
                                            finish();
                                        } catch (Exception e) {

                                        }
                                    }
                                });
                                try {
                                    alertDialog.show();
                                    final Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        public void run() {

                                            Intent intent = new Intent(mContext, UploadService.class);
                                            startService(intent);

                                        }
                                    }, 100);


                                } catch (Exception e) {
                                }
                            } else {
                                Utils.ping(mContext, "Please select image");
                            }
                        } else {
                            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), VideoUploadActivity.this);
                        }
                } else {
                    activityVideoUploadBinding.videoTitleEdt.setError("please enter video title");
                }
                break;
        }
    }


}