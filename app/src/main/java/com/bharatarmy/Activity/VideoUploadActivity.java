package com.bharatarmy.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.bharatarmy.Models.GalleryImageModel;
import com.bharatarmy.Models.MyScreenChnagesModel;
import com.bharatarmy.R;
import com.bharatarmy.UploadService;
import com.bharatarmy.Utility.DbHandler;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityVideoUploadBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class VideoUploadActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityVideoUploadBinding activityVideoUploadBinding;
    Context mContext;
    AlertDialog alertDialog;

    public List<GalleryImageModel> galleryImageList;

    String pathStr, durationStr, sizeStr, videoTitleStr, videoDescriptionStr, videoHeightStr, videoWidthStr, photoprivacyStr;
    String privacysettingstr = "0";
    String thumbnailpath;

    DbHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityVideoUploadBinding = DataBindingUtil.setContentView(this, R.layout.activity_video_upload);
        mContext = VideoUploadActivity.this;
        EventBus.getDefault().register(this);

        init();
        setListiner();
    }

    public void init() {
        dbHandler = new DbHandler(mContext);


        galleryImageList = new ArrayList<>();

        pathStr = getIntent().getStringExtra("videoPath");
        durationStr = getIntent().getStringExtra("videoDuratiion");
        sizeStr = getIntent().getStringExtra("videoSize");
        videoHeightStr = getIntent().getStringExtra("videoheight");
        videoWidthStr = getIntent().getStringExtra("videowidth");

        if (!pathStr.equalsIgnoreCase("")) {
            if (Utils.createThumbnailAtTime(pathStr) != null) {
                thumbnailpath = Utils.saveToInternalStorage(Utils.createThumbnailAtTime(pathStr)).toString();
                activityVideoUploadBinding.chooseVideo.setImageBitmap(Utils.createThumbnailAtTime(pathStr));
            } else {
                Utils.ping(mContext, getResources().getString(R.string.video_error));
            }

        }
    }


    @Subscribe
    public void customEventReceived(MyScreenChnagesModel event) {
        Log.d("imageId :", event.getPrivacyname());
        if (!event.getPrivacyname().equalsIgnoreCase("")) {
            activityVideoUploadBinding.privacyOptionTxt.setText(event.getPrivacyname());
            if (event.getPrivacyname().equalsIgnoreCase("Public")) {
                activityVideoUploadBinding.selectedOptionImg.setImageDrawable(mContext.getDrawable(R.drawable.ic_aboutus));
                activityVideoUploadBinding.privacySubTxt.setText(getResources().getString(R.string.photo_public_option_videosub_txt));
                privacysettingstr = "0";
            } else {
                activityVideoUploadBinding.selectedOptionImg.setImageDrawable(mContext.getDrawable(R.drawable.ic_private_user));
                activityVideoUploadBinding.privacySubTxt.setText(getResources().getString(R.string.photo_private_option_videosub_txt));
                privacysettingstr = "1";
            }

        }

    }

    public void setListiner() {
        Utils.setPref(mContext, "image/video", "video");
        activityVideoUploadBinding.submitLinear.setOnClickListener(this);
        activityVideoUploadBinding.backImg.setOnClickListener(this);
        activityVideoUploadBinding.pictureChooseLinear.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.videoTitle_edt:
                activityVideoUploadBinding.videoUploadScrollView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        View lastChild = activityVideoUploadBinding.videoUploadScrollView.getChildAt(activityVideoUploadBinding.videoUploadScrollView.getChildCount() - 1);
                        int bottom = lastChild.getBottom() + activityVideoUploadBinding.videoUploadScrollView.getPaddingBottom();
                        int sy = activityVideoUploadBinding.videoUploadScrollView.getScrollY();
                        int sh = activityVideoUploadBinding.videoUploadScrollView.getHeight();
                        int delta = bottom - (sy + sh);
                        activityVideoUploadBinding.videoUploadScrollView.smoothScrollBy(0, delta);
                    }
                }, 200);
                break;
            case R.id.back_img:
                finish();
                break;

            case R.id.submit_linear:
                Utils.handleClickEvent(mContext, activityVideoUploadBinding.submitLinear);
                videoTitleStr = activityVideoUploadBinding.videoTitleEdt.getText().toString();
                videoDescriptionStr = activityVideoUploadBinding.videodescEdt.getText().toString();


                if (!videoTitleStr.equalsIgnoreCase("")) {
                    if (!videoDescriptionStr.equalsIgnoreCase("")) {
                        if (!videoHeightStr.equalsIgnoreCase("") && !videoHeightStr.equalsIgnoreCase("0")) {
                            if (!videoWidthStr.equalsIgnoreCase("") && !videoWidthStr.equalsIgnoreCase("0")) {

//                        if (Utils.createVideoThumbNail(pathStr) != null) {
                                galleryImageList.add(new GalleryImageModel(pathStr, sizeStr, "0", durationStr, "2", videoTitleStr, videoDescriptionStr, videoHeightStr, videoWidthStr, privacysettingstr/*,thumbnailpath*/));
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
                                                    galleryImageList.get(i).getVideoDesc(),
                                                    galleryImageList.get(i).getVideoHeight(),
                                                    galleryImageList.get(i).getVideoWidth(),
                                                    galleryImageList.get(i).getPrivacySetting(),
                                                    /*  galleryImageList.get(i).getThumbnail(),*/
                                                    mContext);
                                        }
                                        Intent intent = new Intent(mContext, UploadService.class);
                                        startService(intent);
                                        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(VideoUploadActivity.this);
                                        LayoutInflater inflater = getLayoutInflater();
                                        View dialogView = inflater.inflate(R.layout.thankyou_dialog_item, null);
                                        dialogBuilder.setView(dialogView);
                                        alertDialog = dialogBuilder.create();
                                        alertDialog.setCanceledOnTouchOutside(false);
                                        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                        TextView dialog_headertxt = (TextView) dialogView.findViewById(R.id.dialog_headertxt);
                                        TextView dialog_descriptiontxt = (TextView) dialogView.findViewById(R.id.dialog_descriptiontxt);
                                        TextView hometxt = (TextView) dialogView.findViewById(R.id.home_txt);


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
                                                    if (alertDialog != null && alertDialog.isShowing()) {
                                                        alertDialog.dismiss();
                                                    }
                                                    Intent dashboardIntent = new Intent(mContext, DashboardActivity.class);
                                                    dashboardIntent.putExtra("whichPageRun", "2");
                                                    startActivity(dashboardIntent);
                                                    finish();
                                                } catch (Exception e) {

                                                }
                                            }
                                        });
                                        alertDialog.show();

                                    } else {
                                        Utils.ping(mContext, "Please select video");
                                    }
                                } else {
                                    Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), VideoUploadActivity.this);
                                }
                            }else {
                                Utils.ping(mContext, "video can't support");
                            }
                        } else {
                            Utils.ping(mContext, "video can't support");
                        }
                    } else {
                        activityVideoUploadBinding.videodescEdt.setError("please enter video description");
                    }
                } else {
                    activityVideoUploadBinding.videoTitleEdt.setError("please enter video title");
                }
                break;
            case R.id.picture_choose_linear:
                photoprivacyStr = activityVideoUploadBinding.privacyOptionTxt.getText().toString();
                Intent privacyIntent = new Intent(mContext, ImageVideoPrivacyActivity.class);
                privacyIntent.putExtra("privacytxt", photoprivacyStr);
                privacyIntent.putExtra("privacytype", "Video");
                startActivity(privacyIntent);
                break;
        }
    }


}