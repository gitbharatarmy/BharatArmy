package com.bharatarmy.Activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Adapter.GalleryImageDetailAdapter;
import com.bharatarmy.Fragment.ImageCommentsBottomSheetFragment;
import com.bharatarmy.Interfaces.MorestoryClick;
import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.Models.MyScreenChnagesModel;
import com.bharatarmy.OnSwipeListener;
import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.SnapHelperOneByOne;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityGalleryImageDetailBinding;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.like.LikeButton;
import com.like.OnLikeListener;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static androidx.core.content.FileProvider.getUriForFile;

// remove extra code 26/12/2019 , 10/02/2020 code backup in 26/12/2019   https://mockuphone.com/iphonexspacegrey
public class GalleryImageDetailActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityGalleryImageDetailBinding activityGalleryImageDetailBinding;
    public static Context mContext;
    String TAG = "BATOUCH";

    GalleryImageDetailAdapter galleryImageDetailAdapter;
    ArrayList<String> imageList = new ArrayList<>();
    ArrayList<String> imageAddusername = new ArrayList<>();
    ArrayList<String> imageDuration = new ArrayList<>();
    ArrayList<String> imageId = new ArrayList<>();
    ArrayList<String> imageLike = new ArrayList<>();
    ArrayList<String> imageWatchList = new ArrayList<>();
    int currentShowItemPosition;
    LinearLayoutManager linearLayoutManager;
    String selectedPosition;
    int showPositionImage;
    Uri uri;
    int positon = 0;
    String imageUriStr = "";
    //     swipe recyclerview
    GestureDetector gestureDetector;


    //    comment dialog
    ImageCommentsBottomSheetFragment imageCommentsBottomSheetFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityGalleryImageDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_gallery_image_detail);
        mContext = GalleryImageDetailActivity.this;


        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here

        }
        init();
        setDataValue();
        setListiner();
    }

    public void init() {

    }

    public void setListiner() {
//        activityGalleryImageDetailBinding.imageLinear.setOnTouchListener(this);


        activityGalleryImageDetailBinding.backImg.setOnClickListener(this);

        activityGalleryImageDetailBinding.imageDetailRcvList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    //Dragging
                } else if (newState == RecyclerView.SCROLL_STATE_IDLE) {
//                   currentShowItemPosition = linearLayoutManager.findFirstVisibleItemPosition();

                }

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                currentShowItemPosition = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
                Log.d("ImageScrollPosition :", "" + currentShowItemPosition);


                for (int j = 0; j < imageWatchList.size(); j++) {
                    if (j == currentShowItemPosition) {
                        if (imageWatchList.get(j).equalsIgnoreCase("1")) {
                            activityGalleryImageDetailBinding.watchlistBtn.setLiked(true);
                        } else {
                            activityGalleryImageDetailBinding.watchlistBtn.setLiked(false);
                        }
                    }
                }

                for (int i = 0; i < imageId.size(); i++) {
                    if (i == currentShowItemPosition) {
                        Utils.WatchListReferenceId = imageId.get(i);
                    }
                }

            }
        });


        activityGalleryImageDetailBinding.watchlistBtn.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {

                Utils.WatchListMemberId = String.valueOf(Utils.getAppUserId(mContext));
                Utils.WatchListSourceType = "1";
                Utils.WatchListStatus = "1";
                Utils.InsertWatchList(mContext, GalleryImageDetailActivity.this);
                AppConfiguration.watchlistId.add(Utils.WatchListReferenceId);
                for (int i = 0; i < imageId.size(); i++) {
                    for (int j = 0; j < AppConfiguration.watchlistId.size(); j++) {
                        if (imageId.get(i).equalsIgnoreCase(AppConfiguration.watchlistId.get(j))) {
                            imageWatchList.set(i, String.valueOf(Utils.WatchListStatus));
                        }

                    }
                }
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                Utils.WatchListMemberId = String.valueOf(Utils.getAppUserId(mContext));
                Utils.WatchListSourceType = "1";
                Utils.WatchListStatus = "0";
                Utils.InsertWatchList(mContext, GalleryImageDetailActivity.this);
                AppConfiguration.watchlistId.add(Utils.WatchListReferenceId);
                for (int i = 0; i < imageId.size(); i++) {
                    for (int j = 0; j < AppConfiguration.watchlistId.size(); j++) {
                        if (imageId.get(i).equalsIgnoreCase(AppConfiguration.watchlistId.get(j))) {
                            imageWatchList.set(i, String.valueOf(Utils.WatchListStatus));
                        }

                    }
                }
            }
        });


    }

    public void setDataValue() {
        AppConfiguration.watchlistId = new ArrayList<>();

        activityGalleryImageDetailBinding.toolbarTitleTxt.setText("Image Gallery");
        selectedPosition = getIntent().getStringExtra("positon");
        final Bundle stringArrayList = getIntent().getExtras();
        imageList = stringArrayList.getStringArrayList("data");
        imageAddusername = stringArrayList.getStringArrayList("dataName");
        imageDuration = stringArrayList.getStringArrayList("dataDuration");
        imageId = stringArrayList.getStringArrayList("dataId");
        imageLike = stringArrayList.getStringArrayList("dataLike");
        imageWatchList = stringArrayList.getStringArrayList("dataWatchList");

        Log.d("imageList", "" + imageList.size() + "imageWatchList :" + "" + imageWatchList.size());
        for (int i = 0; i < imageList.size(); i++) {
            if (selectedPosition.equalsIgnoreCase(String.valueOf(imageList.get(i)))) {
                positon = i;
            }
        }
        galleryImageDetailAdapter = new GalleryImageDetailAdapter(mContext, GalleryImageDetailActivity.this,
                imageList, imageAddusername, imageDuration, imageId, imageLike, activityGalleryImageDetailBinding.watchlistLinear,
                new MorestoryClick() {
                    @Override
                    public void getmorestoryClick() {
                        activityGalleryImageDetailBinding.watchlistLinear.setVisibility(View.VISIBLE);
                    }
                },
                new image_click() {
                    @Override
                    public void image_more_click() {
                        Dexter.withActivity(GalleryImageDetailActivity.this)
                                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                .withListener(new MultiplePermissionsListener() {
                                    @Override
                                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                                        if (report.areAllPermissionsGranted()) {
                                            shareImage();
                                        }

                                        if (report.isAnyPermissionPermanentlyDenied()) {
                                            showSettingsDialog();
                                        }
                                    }

                                    @Override
                                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                                        token.continuePermissionRequest();
                                    }
                                }).check();
                    }
                });//,onTouchListener
        linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        LinearSnapHelper linearSnapHelper = new SnapHelperOneByOne();
        linearSnapHelper.attachToRecyclerView(activityGalleryImageDetailBinding.imageDetailRcvList);

        activityGalleryImageDetailBinding.imageDetailRcvList.setLayoutManager(linearLayoutManager);
        activityGalleryImageDetailBinding.imageDetailRcvList.getLayoutManager().scrollToPosition(positon);

        activityGalleryImageDetailBinding.imageDetailRcvList.setItemAnimator(new DefaultItemAnimator());
        activityGalleryImageDetailBinding.imageDetailRcvList.setAdapter(galleryImageDetailAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                EventBus.getDefault().post(new MyScreenChnagesModel(String.valueOf(currentShowItemPosition), "watch"));
                GalleryImageDetailActivity.this.finish();
                break;
        }
    }

    public void shareImage() {

        showPositionImage = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
        for (int i = 0; i < imageList.size(); i++) {
            if (showPositionImage == i) {
                imageUriStr = imageList.get(showPositionImage);
                break;
            }
        }
        //Use for Internal Storage file
        Dexter.withActivity(GalleryImageDetailActivity.this)
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            File myDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "BharatArmy");
                            if (!myDir.exists()) myDir.mkdirs();
                            String fname = "IMG_" + Utils.imagesaveDate() + "_BA" + Utils.imagesavetime() + ".jpg";
                            File file = new File(myDir, fname); //Utils.camerafilesavepath()
                            Log.i("file", "" + file);
                            if (file.exists())
                                file.delete();
                            try {
                                FileOutputStream out = new FileOutputStream(file);
                                Utils.StringToBitMap(imageUriStr).compress(Bitmap.CompressFormat.JPEG, 90, out);
                                out.flush();
                                out.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            //share image from other application
                            Intent shareIntent = new Intent();
                            shareIntent.setAction(Intent.ACTION_SEND);
                            shareIntent.putExtra(Intent.EXTRA_TEXT, AppConfiguration.SHARETEXT);
                            shareIntent.putExtra(Intent.EXTRA_STREAM, uri = getUriForFile(mContext, getPackageName() + ".provider", file));
                            shareIntent.setType("image/*");
                            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            startActivity(Intent.createChooser(shareIntent, "Share It"));
                        }

                        if (report.isAnyPermissionPermanentlyDenied()) {
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();

    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(GalleryImageDetailActivity.this);
        builder.setTitle(getString(R.string.dialog_permission_title));
        builder.setMessage(getString(R.string.dialog_permission_message));
        builder.setPositiveButton(getString(R.string.go_to_settings), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton(getString(android.R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", mContext.getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }


    @Override
    public void onBackPressed() {
        EventBus.getDefault().post(new MyScreenChnagesModel(String.valueOf(currentShowItemPosition), "watch"));
        GalleryImageDetailActivity.this.finish();
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        super.onStop();

    }


}
