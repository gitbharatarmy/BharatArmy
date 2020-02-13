package com.bharatarmy.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.DownloadManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;

import com.bharatarmy.Adapter.AlbumImageVideoAdapter;
import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.SnapHelperOneByOne;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityAlbumImageVideoShowBinding;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static androidx.core.content.FileProvider.getUriForFile;

public class AlbumImageVideoShowActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityAlbumImageVideoShowBinding activityAlbumImageVideoShowBinding;
    Context mContext;
    ArrayList<String> albumImageUrl = new ArrayList<>();
    ArrayList<String> albumImageThumbUrl = new ArrayList<>();
    ArrayList<String> albumMediaType = new ArrayList<>();
    ArrayList<String> albumLike = new ArrayList<>();
    ArrayList<String> albumDuration = new ArrayList<>();
    ArrayList<String> albumId = new ArrayList<>();
    ArrayList<String> albumAddUser = new ArrayList<>();
    ArrayList<String> albumImageViews = new ArrayList<>();

    MediaController mediaController;
    String pathStr, mediaTypeStr, thumbStr;

    AlbumImageVideoAdapter albumImageVideoAdapter;
    LinearLayoutManager linearLayoutManager;
    String selectedItem;
    int positon;
    private String mFinalPath;
    String imageUriStr = "";
    String videoUrlStr = "", selectedmediaTypeStr = "";

    int showPositionImage;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAlbumImageVideoShowBinding = DataBindingUtil.setContentView(this, R.layout.activity_album_image_video_show);

        mContext = AlbumImageVideoShowActivity.this;
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here

        }
        init();
        setListiner();
    }

    public void init() {
        pathStr = getIntent().getStringExtra("AlbumImageVideoPath");
        mediaTypeStr = getIntent().getStringExtra("MediaType");
        thumbStr = getIntent().getStringExtra("AlbumImageThumb");
        final Bundle stringArrayList = getIntent().getExtras();
        albumImageUrl = stringArrayList.getStringArrayList("AlbumUrldata");
        albumImageThumbUrl = stringArrayList.getStringArrayList("AlbumThumbUrldata");
        albumMediaType = stringArrayList.getStringArrayList("AlbumMediaType");
        albumLike = stringArrayList.getStringArrayList("AlbumLike");
        albumImageViews = stringArrayList.getStringArrayList("AlbumViews");
        albumDuration = stringArrayList.getStringArrayList("AlbumDuration");
        albumAddUser = stringArrayList.getStringArrayList("AlbumAddUser");
        albumId = stringArrayList.getStringArrayList("AlbumId");
        selectedItem = getIntent().getStringExtra("positon");

        if (mediaTypeStr != null) {
            if (mediaTypeStr.equalsIgnoreCase("1")) {
                setImage();
            } else if (mediaTypeStr.equalsIgnoreCase("2")) {
                setVideo();
            } else if (mediaTypeStr.equalsIgnoreCase("Album")) {
                setImageandVideo();
            }
        }
    }


    public void setListiner() {
        activityAlbumImageVideoShowBinding.backImg.setOnClickListener(this);
    }

    public void setImage() {
        activityAlbumImageVideoShowBinding.showAlbumImage.setVisibility(View.VISIBLE);
        activityAlbumImageVideoShowBinding.playAlbumvideo.setVisibility(View.GONE);
        activityAlbumImageVideoShowBinding.imageProgress.setVisibility(View.GONE);
        activityAlbumImageVideoShowBinding.albumDetailRcvList.setVisibility(View.GONE);
        Utils.setImageInImageView(pathStr, activityAlbumImageVideoShowBinding.showAlbumImage, mContext);
    }

    public void setVideo() {
        activityAlbumImageVideoShowBinding.imageProgress.setVisibility(View.VISIBLE);
        activityAlbumImageVideoShowBinding.playAlbumvideo.setVisibility(View.VISIBLE);
        activityAlbumImageVideoShowBinding.showAlbumImage.setVisibility(View.GONE);
        activityAlbumImageVideoShowBinding.videoViewThumbnail.setVisibility(View.VISIBLE);
        activityAlbumImageVideoShowBinding.albumDetailRcvList.setVisibility(View.GONE);
        Utils.setImageInImageView(thumbStr, activityAlbumImageVideoShowBinding.videoViewThumbnail, mContext);

        mediaController = new MediaController(mContext);
        mediaController.setAnchorView(activityAlbumImageVideoShowBinding.playAlbumvideo);
        activityAlbumImageVideoShowBinding.playAlbumvideo.setMediaController(mediaController);
        activityAlbumImageVideoShowBinding.playAlbumvideo.requestFocus();

        activityAlbumImageVideoShowBinding.playAlbumvideo.setVideoPath(pathStr);

        activityAlbumImageVideoShowBinding.playAlbumvideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
                activityAlbumImageVideoShowBinding.videoViewThumbnail.setVisibility(View.GONE);
                activityAlbumImageVideoShowBinding.imageProgress.setVisibility(View.GONE);
                activityAlbumImageVideoShowBinding.playAlbumvideo.start();

            }
        });
    }

    public void setImageandVideo() {
        activityAlbumImageVideoShowBinding.showAlbumImage.setVisibility(View.GONE);
        activityAlbumImageVideoShowBinding.playAlbumvideo.setVisibility(View.GONE);
        activityAlbumImageVideoShowBinding.imageProgress.setVisibility(View.GONE);
        activityAlbumImageVideoShowBinding.videoViewThumbnail.setVisibility(View.GONE);
        activityAlbumImageVideoShowBinding.albumDetailRcvList.setVisibility(View.VISIBLE);

        for (int i = 0; i < albumImageUrl.size(); i++) {
            if (selectedItem.equalsIgnoreCase(albumImageUrl.get(i))) {
                positon = i;
            }
        }


        albumImageVideoAdapter = new AlbumImageVideoAdapter(mContext, AlbumImageVideoShowActivity.this, albumImageUrl,
                albumImageThumbUrl, albumMediaType, albumLike, albumImageViews, albumDuration, albumAddUser, albumId, new image_click() {
            @Override
            public void image_more_click() {
                Dexter.withActivity(AlbumImageVideoShowActivity.this)
                        .withPermissions(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport report) {
                                if (report.areAllPermissionsGranted()) {
                                    selectedmediaTypeStr = albumImageVideoAdapter.MediaTypeId();
                                    videoUrlStr = albumImageVideoAdapter.VideoUrlStr();
                                    if (selectedmediaTypeStr.equalsIgnoreCase("1")) {
                                        shareImage();
                                    } else {
                                        shareVideo();
                                    }

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
        });
        linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        LinearSnapHelper linearSnapHelper = new SnapHelperOneByOne();
        linearSnapHelper.attachToRecyclerView(activityAlbumImageVideoShowBinding.albumDetailRcvList);

        activityAlbumImageVideoShowBinding.albumDetailRcvList.setLayoutManager(linearLayoutManager);
        activityAlbumImageVideoShowBinding.albumDetailRcvList.getLayoutManager().scrollToPosition(positon);

        activityAlbumImageVideoShowBinding.albumDetailRcvList.setItemAnimator(new DefaultItemAnimator());
        activityAlbumImageVideoShowBinding.albumDetailRcvList.setAdapter(albumImageVideoAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                AlbumImageVideoShowActivity.this.finish();
                break;
        }
    }

    public void shareImage() {
        showPositionImage = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
        for (int i = 0; i < albumImageUrl.size(); i++) {
            if (showPositionImage == i) {
                imageUriStr = albumImageUrl.get(showPositionImage);
                break;
            }
        }
        Log.d("imageUriStr :", imageUriStr);
        //Use for Internal Storage file

        File myDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "BharatArmy");
        if (!myDir.exists()) myDir.mkdirs();
        String fname = "IMG_" + Utils.imagesaveDate() + "_BA" + Utils.imagesavetime() + ".jpg";
        File file = new File(myDir, fname); //Utils.camerafilesavepath()
        Log.i("file", "" + file);
        if (file.exists())
            file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            Utils.StringToBitMap(imageUriStr).compress(Bitmap.CompressFormat.JPEG, 100, out);
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

    public void shareVideo() {

        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, AppConfiguration.SHARETEXT);
        shareIntent.putExtra(Intent.EXTRA_TEXT, videoUrlStr + "\n\n" + AppConfiguration.SHARETEXT);
        shareIntent.setType("text/plain");
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(Intent.createChooser(shareIntent, "Share It"));

//        String fname = "myVideo" + Utils.imagesaveDate() + "_BA" + Utils.imagesavetime() + ".mp4";
//
//
//        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "BharatArmy");
//        if (!file.exists()) file.mkdirs();
//        File image = new File(file,"BharatArmyVideo");
//        mFinalPath =image.getPath()+File.separator;
//
//
//        DownloadManager downloadmanager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
//        Uri uri = Uri.parse("http://devenv.bharatarmy.com//Docs/Media/bharatarmy_app_bdad52bb-a687-486e-9162-c8334f46e4b1.mp4");
//
//        DownloadManager.Request request = new DownloadManager.Request(uri);
//        request.setTitle("My File");
//        request.setDescription("Downloading");
//        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//        request.setVisibleInDownloadsUi(false);
//        request.setDestinationUri(Uri.parse("file://" +mFinalPath + fname));
//
//        downloadmanager.enqueue(request);
//        File downloadfile = new File(file, fname); //Utils.camerafilesavepath()
//        Log.i("file", "" + downloadfile);
//
//        Uri myUri = getUriForFile(mContext, getPackageName() + ".provider", downloadfile);
//        Log.d("SHareVideo :", myUri.toString());
//        ContentValues content = new ContentValues(4);
//        content.put(MediaStore.Video.VideoColumns.DATE_ADDED,
//                System.currentTimeMillis() / 1000);
//        content.put(MediaStore.Video.Media.MIME_TYPE, "video/mp4");
//        content.put(MediaStore.Video.Media.DATA, String.valueOf(myUri));
//        ContentResolver resolver = getBaseContext().getContentResolver();
//        Uri videouri = resolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, content);
//
//        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
//        sharingIntent.setType("video/*");
//        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Title");
//        sharingIntent.putExtra(android.content.Intent.EXTRA_STREAM,videouri);
//        startActivity(Intent.createChooser(sharingIntent,"share:"));

    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AlbumImageVideoShowActivity.this);
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
}
