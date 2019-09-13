package com.bharatarmy.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bharatarmy.Adapter.SelectedImageVideoViewAdapter;
import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.Models.GalleryImageModel;
import com.bharatarmy.Models.LoginOtherDataModel;
import com.bharatarmy.R;
import com.bharatarmy.UploadService;
import com.bharatarmy.Utility.DbHandler;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.VideoTrimmer.interfaces.OnHgLVideoListener;
import com.bharatarmy.VideoTrimmer.interfaces.OnTrimVideoListener;
import com.bharatarmy.VideoTrimmer.utils.FileUtils;
import com.bharatarmy.databinding.ActivityImageVideoUploadBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static androidx.core.content.FileProvider.getUriForFile;

/* delete extra code 22/08/2019 backup in 22/08/2019*/
public class ImageVideoUploadActivity extends AppCompatActivity implements View.OnClickListener, OnTrimVideoListener, OnHgLVideoListener {
    ActivityImageVideoUploadBinding activityImageVideoUploadBinding;
    Context mContext;
    private static final String TAG = ImageVideoUploadActivity.class.getSimpleName();
    public static final int REQUEST_IMAGE = 100;
    private NotificationManager notifManager;
    final int NOTIFY_ID = 0; // ID of notification
    Uri uri, selectedUri;


    SelectedImageVideoViewAdapter selectedImageVideoViewAdapter;
    LinearLayoutManager linearLayoutManager;

    public List<GalleryImageModel> galleryImageList;
    File Camerafile;
    String imageorvideoStr = "";
    private static final int REQUEST_VIDEO_TRIMMER = 0x01;
    private static final int CUSTOM_REQUEST_CODE = 532;
    public static final int RC_PHOTO_PICKER_PERM = 123;
    private int MAX_ATTACHMENT_COUNT = 20;
    private ArrayList<String> photoPaths = new ArrayList<>();
    public String fileName;

    String path = "";
    int maxDuration = 10;
    long findsize;
    String duration;
    // Database
    DbHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityImageVideoUploadBinding = DataBindingUtil.setContentView(this, R.layout.activity_image_video_upload);
        mContext = ImageVideoUploadActivity.this;
        init();
        setListiner();
    }

    public void init() {
        dbHandler = new DbHandler(mContext);


        galleryImageList = new ArrayList<>();
    }

    public void setListiner() {
        imageorvideoStr = getIntent().getStringExtra("image/video");
        Utils.setPref(mContext, "image/video", imageorvideoStr);
        if (imageorvideoStr.equalsIgnoreCase("image")) {
            activityImageVideoUploadBinding.selectedImageVideoLinear.setVisibility(View.VISIBLE);
            activityImageVideoUploadBinding.selectedVideoLinear.setVisibility(View.GONE);

        }
        activityImageVideoUploadBinding.backImg.setOnClickListener(this);
        activityImageVideoUploadBinding.chooseFromGalleryLinear.setOnClickListener(this);
        activityImageVideoUploadBinding.chooseFromCameraLinear.setOnClickListener(this);
        activityImageVideoUploadBinding.submitLinear.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:

                ImageVideoUploadActivity.this.finish();
                break;
            case R.id.choose_from_camera_linear:
                Dexter.withActivity(ImageVideoUploadActivity.this)
                        .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport report) {
                                if (report.areAllPermissionsGranted()) {
                                    if (imageorvideoStr.equalsIgnoreCase("image")) {
                                        if (galleryImageList.size() < MAX_ATTACHMENT_COUNT) {
                                            openImageCapture();
                                        } else {
                                            Utils.ping(mContext, "max limit 20");
                                        }
                                    } else {
                                        openVideoCapture();
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
                break;
            case R.id.choose_from_gallery_linear:
                if (imageorvideoStr.equalsIgnoreCase("image")) {
                    pickPhotoClicked();
                } else {
                    pickVideoFromGallery();
                }
                break;
            case R.id.submit_linear:
                Utils.handleClickEvent(mContext, activityImageVideoUploadBinding.submitLinear);


                boolean connected = Utils.checkNetwork(mContext);


                if (connected == true) {
                    if (galleryImageList != null && galleryImageList.size() > 0) {
                        for (int i = 0; i < galleryImageList.size(); i++) {
                            dbHandler.insertImageDetails(galleryImageList.get(i).getImageUri(), galleryImageList.get(i).getImageSize(),
                                    galleryImageList.get(i).getUploadcompelet(),galleryImageList.get(i).getVideolength(),
                                    galleryImageList.get(i).getFileType(),galleryImageList.get(i).getVideoTitle(),
                                    galleryImageList.get(i).getVideoDesc(),mContext);
                        }
                        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ImageVideoUploadActivity.this);
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
                                if (Utils.retriveLoginOtherData(mContext).get(i).getMessageId().equals(1)){
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
                    Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), ImageVideoUploadActivity.this);
                }
                break;
        }

    }

    /**
     * Showing Alert Dialog with Settings option
     * Navigates user to app settings
     * NOTE: Keep proper title and message depending on your app
     */
    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ImageVideoUploadActivity.this);
        builder.setTitle(getString(R.string.dialog_permission_title));
        builder.setMessage(getString(R.string.dialog_permission_message));
        builder.setPositiveButton(getString(R.string.go_to_settings), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                ImageVideoUploadActivity.this.openSettings();
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

    @AfterPermissionGranted(RC_PHOTO_PICKER_PERM)
    public void pickPhotoClicked() {
        if (EasyPermissions.hasPermissions(this, FilePickerConst.PERMISSIONS_FILE_PICKER)) {
            onPickPhoto();
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_photo_picker),
                    RC_PHOTO_PICKER_PERM, FilePickerConst.PERMISSIONS_FILE_PICKER);
        }
    }

    public void onPickPhoto() {
        int maxCount = MAX_ATTACHMENT_COUNT;
        if ((galleryImageList.size() + photoPaths.size()) == MAX_ATTACHMENT_COUNT) {
            Toast.makeText(this, "Cannot select more than " + MAX_ATTACHMENT_COUNT + " items",
                    Toast.LENGTH_SHORT).show();
        } else {
            FilePickerBuilder.getInstance()
                    .setMaxCount(MAX_ATTACHMENT_COUNT - galleryImageList.size())
                    .setSelectedFiles(photoPaths)
                    .setActivityTheme(R.style.FilePickerTheme)
                    .setActivityTitle("")
                    .enableVideoPicker(false)
                    .enableCameraSupport(false)
                    .showGifs(true)
                    .showFolderView(true)
                    .enableSelectAll(false)
                    .enableImagePicker(true)
                    .withOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                    .pickPhoto(this, CUSTOM_REQUEST_CODE);
        }
    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_IMAGE) {
                getCameraImagePath(fileName);

            } else if (requestCode == REQUEST_VIDEO_TRIMMER) {
                selectedUri = data.getData();
                if (selectedUri != null) {
                    activityImageVideoUploadBinding.chooseLinear.setVisibility(View.GONE);
                    activityImageVideoUploadBinding.bottomView.setVisibility(View.GONE);
                    path = FileUtils.getPath(this, selectedUri);
                    maxDuration = getMediaDuration(selectedUri);
                    duration = getDuration(selectedUri);

                    Log.d("duration :", "" + duration);

                    if (path != null) {
                        activityImageVideoUploadBinding.selectedVideoLinear.setVisibility(View.VISIBLE);
                        if (activityImageVideoUploadBinding.timeLine != null) {
                            /**
                             * get total duration of video file
                             */
                            Log.e("tg", "maxDuration = " + maxDuration);
                            Log.d("Videopath", path);
                            //mVideoTrimmer.setMaxDuration(maxDuration);
                            activityImageVideoUploadBinding.timeLine.setMaxDuration(maxDuration);
                            activityImageVideoUploadBinding.timeLine.setOnTrimVideoListener(this);
                            activityImageVideoUploadBinding.timeLine.setOnHgLVideoListener(this);
                            activityImageVideoUploadBinding.timeLine.setVideoURI(Uri.parse(path));
                            activityImageVideoUploadBinding.timeLine.setVideoInformationVisibility(true);


                            File f = new File(path);
                            findsize = f.length() / 1024;
                            galleryImageList = new ArrayList<GalleryImageModel>();
                            galleryImageList.add(new GalleryImageModel(path, size((int) findsize), "0",duration,"4","",""));
                        }
                    }
                } else {
                    activityImageVideoUploadBinding.chooseLinear.setVisibility(View.VISIBLE);
                    activityImageVideoUploadBinding.bottomView.setVisibility(View.VISIBLE);
                }
            } else if (requestCode == CUSTOM_REQUEST_CODE) {
                photoPaths = new ArrayList<>();
                photoPaths.addAll(data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_MEDIA));

                addToView(photoPaths);
            }

        } else {
            Log.e("tg", "resultCode = " + resultCode + " data " + data);
        }

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

    public void addToView(ArrayList<String> imagePaths) {
        ArrayList<String> filePaths = new ArrayList<>();
        if (imagePaths != null) {
            filePaths.addAll(imagePaths);
        }

        for (int i = 0; i < filePaths.size(); i++) {
            File f = new File(filePaths.get(i));
            long findsize = f.length() / 1024;
            galleryImageList.add(new GalleryImageModel(filePaths.get(i), size((int) findsize), "0","0","2","",""));
        }
        loadProfile();
    }

    private void loadProfile() {
        activityImageVideoUploadBinding.cameraUnselectLinear.setVisibility(View.GONE);
        activityImageVideoUploadBinding.selectedImageVideoLinear.setVisibility(View.VISIBLE);


        selectedImageVideoViewAdapter = new SelectedImageVideoViewAdapter(mContext, galleryImageList, new image_click() {
            @Override
            public void image_more_click() {
                String getSelectedImageremove = String.valueOf(selectedImageVideoViewAdapter.selectedpositionRemove());
                Log.d("removePic", getSelectedImageremove);
//                getSelectedImageremove = getSelectedImageremove.substring(1, getSelectedImageremove.length() - 1);

                for (int i = 0; i < galleryImageList.size(); i++) {
                    if (i == Integer.parseInt(getSelectedImageremove)) {
                        galleryImageList.remove(i);
                        selectedImageVideoViewAdapter.notifyDataSetChanged();
                    }
                }
            }
        });//,onTouchListener
        linearLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        activityImageVideoUploadBinding.selectedImagesView.setLayoutManager(linearLayoutManager);
        activityImageVideoUploadBinding.selectedImagesView.setItemAnimator(new DefaultItemAnimator());
        activityImageVideoUploadBinding.selectedImagesView.setAdapter(selectedImageVideoViewAdapter);


    }


    @Override
    public void onTrimStarted() {
    }

    @Override
    public void getResult(Uri contentUri) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                try {
                    String path = contentUri.getPath();
                    File file = new File(path);
                    Uri uri;
                    if (Build.VERSION.SDK_INT < 24) {
                        uri = Uri.fromFile(file);
                    } else {
                        uri = Uri.parse(file.getPath()); // My work-around for new SDKs, causes ActivityNotFoundException in API 10.
                    }
                    Log.e("tg", "final_path1 = " + path + " uri1 = " + Uri.fromFile(file));

                    duration = getDuration(contentUri);
                    Log.d("trimduration :", duration + "trimpath :"+ path);

                    galleryImageList = new ArrayList<GalleryImageModel>();
                    galleryImageList.add(new GalleryImageModel(path, size((int) findsize), "0",duration,"4","",""));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }


            }
        });


    }

    @Override
    public void cancelAction() {
//        mProgressDialog.cancel();
        activityImageVideoUploadBinding.timeLine.destroy();
        finish();
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

    @Override
    public void onVideoPrepared() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // Toast.makeText(TrimmerActivity.this, "onVideoPrepared", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openVideoCapture() {
        Intent videoCapture = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(videoCapture, REQUEST_VIDEO_TRIMMER);
    }

    private void openImageCapture() {
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            fileName = System.currentTimeMillis() + ".jpg";
                            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, getCacheImagePath(fileName));
                            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                                startActivityForResult(takePictureIntent, REQUEST_IMAGE);
                            }
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    private Uri getCacheImagePath(String fileName) {
        File path = new File(getExternalCacheDir(), "camera");

        if (!path.exists()) path.mkdirs();
        File image = new File(path, fileName);

        Log.d("imageFile : ", "" + image);

        return getUriForFile(ImageVideoUploadActivity.this, getPackageName() + ".provider", image);
    }

    private void getCameraImagePath(String fileName) {
        File path = new File(getExternalCacheDir(), "camera");
        Camerafile = path;
        File image = new File(path, fileName);

        Log.d("imagegetFile : ", "" + image);
        String imageUrl = String.valueOf(image);

        long findsize = Camerafile.length() / 1024;
        Log.d("findfilesize", "" + Camerafile.length() / 1024 + "kb" + " " + Camerafile.length() / (1024 * 1024));

        galleryImageList.add(new GalleryImageModel(imageUrl, size((int) findsize), "0","0","2","",""));

        loadProfile();

    }

    private void pickVideoFromGallery() {
        Intent intent = new Intent();
        intent.setTypeAndNormalize("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent, getString(R.string.label_select_video)), REQUEST_VIDEO_TRIMMER);
    }

    private int getMediaDuration(Uri uriOfFile) {
        MediaPlayer mp = MediaPlayer.create(this, uriOfFile);
        int duration = mp.getDuration();
        return duration;

    }

    public String getDuration(Uri uriOfFile) {
        MediaPlayer mp = MediaPlayer.create(this, uriOfFile);
        int duration = mp.getDuration() / 1000;
        int hours = duration / 3600;
        int minutes = (duration / 60) - (hours * 60);
        int seconds = duration - (hours * 3600) - (minutes * 60);
        String formatted = String.format("%d:%02d:%02d", hours, minutes, seconds);

        return formatted;
    }

}
