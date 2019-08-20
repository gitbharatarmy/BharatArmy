package com.bharatarmy.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
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
import com.bharatarmy.R;
import com.bharatarmy.TinyDB;
import com.bharatarmy.UploadService;
import com.bharatarmy.Utility.AppConfiguration;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import gun0912.tedbottompicker.TedBottomPicker;
import gun0912.tedbottompicker.TedBottomSheetDialogFragment;

import static androidx.core.content.FileProvider.getUriForFile;


public class ImageVideoUploadActivity extends AppCompatActivity implements View.OnClickListener, OnTrimVideoListener, OnHgLVideoListener {
    ActivityImageVideoUploadBinding activityImageVideoUploadBinding;
    Context mContext;
    private static final String TAG = ImageVideoUploadActivity.class.getSimpleName();
    public static final int REQUEST_IMAGE = 100;
    private NotificationManager notifManager;
    final int NOTIFY_ID = 0; // ID of notification
    Uri uri, selectedUri, imageUri;
    public static final int REQUEST_IMAGE_CAPTURE = 0;
    private List<Uri> selectedUriList;

    SelectedImageVideoViewAdapter selectedImageVideoViewAdapter;
    LinearLayoutManager linearLayoutManager;

    public List<GalleryImageModel> content = new ArrayList<>();
    File Camerafile;
    String imageorvideoStr = "";
    private static final int REQUEST_VIDEO_TRIMMER = 0x01;
    private static final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 101;
    static final String EXTRA_VIDEO_PATH = "EXTRA_VIDEO_PATH";
    static final String VIDEO_TOTAL_DURATION = "VIDEO_TOTAL_DURATION";
    private ProgressDialog mProgressDialog;
    public String fileName;

    static MediaPlayer mPlayer;
    boolean paused = true;
    String path = "";
    int maxDuration = 10;
    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                int resultCode = bundle.getInt(UploadService.RESULT);

                if (resultCode == -1) {
                    notifManager.cancelAll();
                    Toast.makeText(mContext,
                            "Upload complete.",
                            Toast.LENGTH_LONG).show();


//                    setListiner();
                } else {
                    Toast.makeText(mContext, "Upload failed",
                            Toast.LENGTH_LONG).show();
                    Utils.setPref(mContext, "failedtoupload", "true");
//                    setListiner();
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityImageVideoUploadBinding = DataBindingUtil.setContentView(this, R.layout.activity_image_video_upload);
        mContext = ImageVideoUploadActivity.this;
        ImageUploadPickerActivity.clearCache(this);

        setListiner();
    }

    public void setListiner() {
//        if (Utils.getPref(mContext, "cometonotification").equalsIgnoreCase("cometonotification")) {
//            if (!Utils.getPref(mContext, "image/video").equalsIgnoreCase("")) {
//                imageorvideoStr = Utils.getPref(mContext, "image/video");
//                content=new ArrayList<>();
//                Type arrayListType = new TypeToken<ArrayList<String>>() {
//                }.getType();
//                Gson gson = new Gson();
//                List<String> yourList = gson.fromJson(Utils.getPref(mContext, "uploadcompletefile"), arrayListType);
//
//                Log.d("uploadfilearray :", "" + yourList);
//
//                Type arrayListType1 = new TypeToken<ArrayList<GalleryImageModel>>() {
//                }.getType();
//                Gson gson1 = new Gson();
//                List<GalleryImageModel> galleryimage = gson1.fromJson(Utils.getPref(mContext, "gallerylist"), arrayListType1);
//                for (int i = 0; i < yourList.size(); i++) {
//                    for (int j = 0; j < galleryimage.size(); j++) {
//                        if (yourList.get(i).equalsIgnoreCase(Utils.getFilePathFromUri(mContext, Uri.parse(galleryimage.get(j).getImageUri())))) {
//                                galleryimage.get(j).setUploadcompelet("1");
//                            content.add(galleryimage.get(j));
//                            Log.d("updategallerylist :", content.toString());
//                        }else{
//                            galleryimage.get(j).setUploadcompelet("0");
//                            content.add(galleryimage.get(j));
//                            Log.d("updategallerylist :", content.toString());
//                        }
//
//                    }
//                }
//
//                if (content != null) {
//                    selectedImageVideoViewAdapter = new SelectedImageVideoViewAdapter(mContext, content, new image_click() {
//                        @Override
//                        public void image_more_click() {
//                            String getSelectedImageremove = selectedImageVideoViewAdapter.getDatas().toString();
//                            Log.d("removePic", getSelectedImageremove);
//                            getSelectedImageremove = getSelectedImageremove.substring(1, getSelectedImageremove.length() - 1);
//
//                            for (int i = 0; i < content.size(); i++) {
//                                if (content.get(i).getImageUri().equalsIgnoreCase(getSelectedImageremove)) {
//                                    content.remove(i);
//                                    selectedImageVideoViewAdapter.notifyDataSetChanged();
//                                }
//                            }
//                        }
//                    });//,onTouchListener
//                    linearLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
//                    activityImageVideoUploadBinding.selectedImagesView.setLayoutManager(linearLayoutManager);
//                    activityImageVideoUploadBinding.selectedImagesView.setItemAnimator(new DefaultItemAnimator());
//                    activityImageVideoUploadBinding.selectedImagesView.setAdapter(selectedImageVideoViewAdapter);
//                    selectedImageVideoViewAdapter.notifyDataSetChanged();
//                }
//
//            }
//        } else if (Utils.getPref(mContext, "cometonotification").equalsIgnoreCase("service")) {
//            imageorvideoStr = Utils.getPref(mContext, "image/video");
//            content = new ArrayList<>();
//            Type arrayListType = new TypeToken<ArrayList<String>>() {
//            }.getType();
//            Gson gson = new Gson();
//            List<String> yourList = gson.fromJson(Utils.getPref(mContext, "uploadcompletefile"), arrayListType);
//
//            Log.d("uploadfilearray :", "" + yourList);
//
//            Type arrayListType1 = new TypeToken<ArrayList<GalleryImageModel>>() {
//            }.getType();
//            Gson gson1 = new Gson();
//            List<GalleryImageModel> galleryimage = gson1.fromJson(Utils.getPref(mContext, "gallerylist"), arrayListType1);
//            Log.d("galleryimagelist :", "" + galleryimage);
//            for (int i = 0; i < yourList.size(); i++) {
//                for (int j = 0; j < galleryimage.size(); j++) {
////                    if (yourList.get(i).equalsIgnoreCase(Utils.getFilePathFromUri(mContext, Uri.parse(galleryimage.get(j).getImageUri())))) {
////                        galleryimage.get(j).setUploadcompelet("1");
////                        content.add(galleryimage.get(j));
////
////                        Log.d("updategallerylist :", content.toString());
////                    }
//                    if (yourList.get(i).equalsIgnoreCase(Utils.getFilePathFromUri(mContext, Uri.parse(galleryimage.get(j).getImageUri())))) {
//                        galleryimage.get(j).setUploadcompelet("1");
//                        content.add(galleryimage.get(j));
//                        Log.d("updategallerylist :", content.toString());
//                    }else{
//                        galleryimage.get(j).setUploadcompelet("0");
//                        content.add(galleryimage.get(j));
//                        Log.d("updategallerylist :", content.toString());
//                    }
//                }
//            }
//
//            if (content != null) {
//                selectedImageVideoViewAdapter = new SelectedImageVideoViewAdapter(mContext, content, new image_click() {
//                    @Override
//                    public void image_more_click() {
//                        String getSelectedImageremove = selectedImageVideoViewAdapter.getDatas().toString();
//                        Log.d("removePic", getSelectedImageremove);
//                        getSelectedImageremove = getSelectedImageremove.substring(1, getSelectedImageremove.length() - 1);
//
//                        for (int i = 0; i < content.size(); i++) {
//                            if (content.get(i).getImageUri().equalsIgnoreCase(getSelectedImageremove)) {
//                                content.remove(i);
//                                selectedImageVideoViewAdapter.notifyDataSetChanged();
//                            }
//                        }
//                    }
//                });//,onTouchListener
//                linearLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
//                activityImageVideoUploadBinding.selectedImagesView.setLayoutManager(linearLayoutManager);
//                activityImageVideoUploadBinding.selectedImagesView.setItemAnimator(new DefaultItemAnimator());
//                activityImageVideoUploadBinding.selectedImagesView.setAdapter(selectedImageVideoViewAdapter);
//                selectedImageVideoViewAdapter.notifyDataSetChanged();
//            }
//        } else {
        imageorvideoStr = getIntent().getStringExtra("image/video");
        Utils.setPref(mContext, "image/video", imageorvideoStr);
//        }


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
//                                        launchCameraIntent();
                                        if (content.size() < 10) {
                                            openImageCapture();
                                        } else {
                                            Utils.ping(mContext, "max limit 10");
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
                Dexter.withActivity(ImageVideoUploadActivity.this)
                        .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport report) {
                                if (report.areAllPermissionsGranted()) {
                                    if (imageorvideoStr.equalsIgnoreCase("image")) {
                                        if (content.size() < 10) {
                                            TedBottomPicker.with(ImageVideoUploadActivity.this)
                                                    .setPeekHeight(1600)
                                                    .showTitle(false)
                                                    .setCompleteButtonText("Done")
                                                    .setEmptySelectionText("No Select")
                                                    .setSelectedUriList(selectedUriList)
                                                    .setSelectMinCount(1)
                                                    .setSelectMaxCount(10 - content.size())
                                                    .showCameraTile(false)
                                                    .setTitle("Select Images")
                                                    .setTitleBackgroundResId(R.color.heading_bg)
                                                    .setSelectedForeground(R.drawable.ic_checked)
                                                    .showMultiImage(new TedBottomSheetDialogFragment.OnMultiImageSelectedListener() {
                                                        @Override
                                                        public void onImagesSelected(List<Uri> uriList) {
                                                            // here is selected image uri list
//                                                    loadProfile(uriList);
//                                                        content = new ArrayList<GalleryImageModel>();
                                                            for (int i = 0; i < uriList.size(); i++) {
                                                                File f = new File(uriList.get(i).getPath());
                                                                long findsize = f.length() / 1024;
                                                                content.add(new GalleryImageModel(uriList.get(i).toString(), size((int) findsize), "", ""));
                                                            }
                                                            loadProfile();
                                                        }
                                                    });
                                        } else {
                                            Utils.ping(mContext, "max limit 10");
                                        }
                                    } else {
                                        pickFromGallery();
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
            case R.id.submit_linear:
                final ArrayList<Uri> files = new ArrayList<>(); //These are the uris for the files to be uploaded
                AppConfiguration.files = new ArrayList<>();
                Utils.setPref(mContext, "gallerylist", "");
                Utils.setPref(mContext, "uploadcompletefile", "");
                Uri filepath;
                int counter = 0;
                boolean connected = Utils.checkNetwork(mContext);
                if (connected == true) {
                    if (content != null) {
                        for (int i = 0; i < content.size(); i++) {
                            AppConfiguration.files.add(Uri.parse(content.get(i).getImageUri()));
                        }
                        Gson gson = new Gson();
                        String valuesString = gson.toJson(content);
                        Utils.setPref(mContext, "gallerylist", valuesString);
                        Log.d("gallerylist", valuesString.toString());
                        Utils.setPref(getApplicationContext(), "uploadcompletefile", "");

                        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ImageVideoUploadActivity.this);
                        LayoutInflater inflater = getLayoutInflater();
                        View dialogView = inflater.inflate(R.layout.thankyou_dialog_item, null);
                        dialogBuilder.setView(dialogView);
                        AlertDialog alertDialog = dialogBuilder.create();
                        alertDialog.setCanceledOnTouchOutside(false);
                        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        TextView hometxt = (TextView) dialogView.findViewById(R.id.home_txt);
                        hometxt.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

//                                finish();

                                try {
                                    alertDialog.dismiss();
                                    finish();
                                } catch (Exception e) {

                                }
                            }
                        });
                        try {
                            alertDialog.show();
                        } catch (Exception e) {

                        }

                        Intent intent = new Intent(this, UploadService.class);
                        startService(intent);

                        createNotification("Upload Image", mContext, 1);


                    } else {
                        Utils.ping(mContext, "Please select image");
                    }

                } else {
                    Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), ImageVideoUploadActivity.this);
                }

//                createNotification("Upload Image", mContext);
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


    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }


    private void launchCameraIntent() {
        Intent intent = new Intent(ImageVideoUploadActivity.this, ImageUploadPickerActivity.class);
        intent.putExtra(ImageUploadPickerActivity.INTENT_IMAGE_PICKER_OPTION, ImageUploadPickerActivity.REQUEST_IMAGE_CAPTURE);

        // setting aspect ratio
        intent.putExtra(ImageUploadPickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImageUploadPickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImageUploadPickerActivity.INTENT_ASPECT_RATIO_Y, 1);

        // setting maximum bitmap width and height
        intent.putExtra(ImageUploadPickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true);
        intent.putExtra(ImageUploadPickerActivity.INTENT_BITMAP_MAX_WIDTH, 1000);
        intent.putExtra(ImageUploadPickerActivity.INTENT_BITMAP_MAX_HEIGHT, 1000);

        startActivityForResult(intent, REQUEST_IMAGE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_IMAGE) {
//                getImageUri(mContext, (Bitmap) data.getExtras().get("data"));

//                uri = data.getParcelableExtra("path");
//

                imageUri = Uri.fromFile(getCacheFileImagePath(fileName));
                Log.d("URI", imageUri.toString());
                long findsize = Camerafile.length() / 1024;
                Log.d("findfilesize", "" + Camerafile.length() / 1024 + "kb" + " " + Camerafile.length() / (1024 * 1024));
//                try {
//                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                content = new ArrayList<GalleryImageModel>();
                content.add(new GalleryImageModel(imageUri.toString(), size((int) findsize), "", ""));

                loadProfile();
                Log.d("FInalImageSize", "" + size((int) findsize));

            } else if (requestCode == REQUEST_VIDEO_TRIMMER) {
                selectedUri = data.getData();
                if (selectedUri != null) {
                    activityImageVideoUploadBinding.chooseLinear.setVisibility(View.GONE);
                    activityImageVideoUploadBinding.bottomView.setVisibility(View.GONE);
                    path = FileUtils.getPath(this, selectedUri);
                    maxDuration = getMediaDuration(selectedUri);
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
                            //mVideoTrimmer.setDestinationPath("/storage/emulated/0/DCIM/CameraCustom/");
                            activityImageVideoUploadBinding.timeLine.setVideoURI(Uri.parse(path));
                            activityImageVideoUploadBinding.timeLine.setVideoInformationVisibility(true);


                            File f = new File(path);
                            long findsize = f.length() / 1024;
                            content = new ArrayList<GalleryImageModel>();
                            content.add(new GalleryImageModel(path, size((int) findsize), "", ""));
                        }
                    }
                } else {
                    activityImageVideoUploadBinding.chooseLinear.setVisibility(View.VISIBLE);
                    activityImageVideoUploadBinding.bottomView.setVisibility(View.VISIBLE);
                }
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

    private void loadProfile() {
        activityImageVideoUploadBinding.cameraUnselectLinear.setVisibility(View.GONE);
        activityImageVideoUploadBinding.selectedImageVideoLinear.setVisibility(View.VISIBLE);


        selectedImageVideoViewAdapter = new SelectedImageVideoViewAdapter(mContext, content, new image_click() {
            @Override
            public void image_more_click() {
                String getSelectedImageremove = selectedImageVideoViewAdapter.getDatas().toString();
                Log.d("removePic", getSelectedImageremove);
                getSelectedImageremove = getSelectedImageremove.substring(1, getSelectedImageremove.length() - 1);

                for (int i = 0; i < content.size(); i++) {
                    if (content.get(i).getImageUri().equalsIgnoreCase(getSelectedImageremove)) {
                        content.remove(i);
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
//        mProgressDialog.show();

//        Utils.showDialog(mContext);
    }

    @Override
    public void getResult(Uri contentUri) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // Toast.makeText(TrimmerActivity.this, getString(R.string.video_saved_at, contentUri.getPath()), Toast.LENGTH_SHORT).show();

            }
        });

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

          /*   this code for visible for trimming video in gallery
            Intent intent = new Intent(Intent.ACTION_VIEW,uri );//Uri.fromFile(file)
            intent.setDataAndType(uri, "video/*"); //Uri.fromFile(file)
            startActivity(intent);
            finish();*/

        } catch (Exception ex) {
            ex.printStackTrace();
        }
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

    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(ImageVideoUploadActivity.this, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }


    private void playUriOnVLC(Uri uri) {

        int vlcRequestCode = 42;
        Intent vlcIntent = new Intent(Intent.ACTION_VIEW);
        vlcIntent.setPackage("org.videolan.vlc");
        vlcIntent.setDataAndTypeAndNormalize(uri, "video/*");
        vlcIntent.putExtra("title", "Kung Fury");
        vlcIntent.putExtra("from_start", false);
        vlcIntent.putExtra("position", 90000l);
        startActivityForResult(vlcIntent, vlcRequestCode);
    }


    private void openVideoCapture() {
        Intent videoCapture = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(videoCapture, REQUEST_VIDEO_TRIMMER);
    }


    //    file:///data/user/0/com.bharatarmy/cache/1566033242675.jpg
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

    private File getCacheFileImagePath(String fileName) {
        File path = new File(getExternalCacheDir(), "camera");
        Camerafile = path;
        File image = new File(path, fileName);

        Log.d("imageFile : ", "" + image);
        return image;
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);

        return Uri.parse(path);
    }

    private void pickFromGallery() {
        Intent intent = new Intent();
        intent.setTypeAndNormalize("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent, getString(R.string.label_select_video)), REQUEST_VIDEO_TRIMMER);
    }

    private void startTrimActivity(@NonNull Uri uri) {
        Intent intent = new Intent(this, ImageVideoUploadActivity.class);
        intent.putExtra(EXTRA_VIDEO_PATH, FileUtils.getPath(this, uri));
        intent.putExtra(VIDEO_TOTAL_DURATION, getMediaDuration(uri));
        startActivity(intent);
    }

    private int getMediaDuration(Uri uriOfFile) {
        MediaPlayer mp = MediaPlayer.create(this, uriOfFile);
        int duration = mp.getDuration();
        return duration;
    }


    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, new IntentFilter(
                UploadService.NOTIFICATION));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    public void createNotification(String aMessage, Context context, int progress) {

        String id = context.getString(R.string.default_notification_channel_id); // default_channel_id
        String title = context.getString(R.string.default_notification_channel_title); // Default Channel
        Intent intent;
        PendingIntent pendingIntent;
        NotificationCompat.Builder builder;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.proflie);

        if (notifManager == null) {
            notifManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = notifManager.getNotificationChannel(id);
            if (mChannel == null) {
                mChannel = new NotificationChannel(id, title, importance);
                mChannel.enableVibration(false);
                mChannel.setVibrationPattern(new long[]{-1});
                notifManager.createNotificationChannel(mChannel);
            }
            builder = new NotificationCompat.Builder(context, id);
            intent = new Intent(context, MyMediaActivity.class);
            Utils.setPref(mContext, "cometonotification", "cometonotification");
            intent.putExtra("image/video", Utils.getPref(mContext, "image/video"));
            intent.putExtra("cometonotification", Utils.getPref(mContext, "cometonotification"));
            pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentTitle(aMessage)                            // required
                    .setSmallIcon(R.drawable.app_logo)   // required
                    .setContentText(context.getString(R.string.app_name)) // required
                    .setDefaults(Notification.DEFAULT_VIBRATE)
                    .setVibrate(new long[]{-1}) //new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400}
                    .setOngoing(true)
                    .setAutoCancel(false)
                    .setContentIntent(pendingIntent)
                    .setTicker(aMessage)
//                    .setLargeIcon(bitmap)
//                    .setStyle(new NotificationCompat.BigPictureStyle()
//                            .bigPicture(bitmap)
//                            .bigLargeIcon(bitmap))
                    .setProgress(100, progress, false)

                    .setPriority(Notification.PRIORITY_HIGH);

//                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        } else {
            builder = new NotificationCompat.Builder(context, id);
            intent = new Intent(context, MyMediaActivity.class);
            Utils.setPref(mContext, "cometonotification", "cometonotification");
            intent.putExtra("image/video", Utils.getPref(mContext, "image/video"));
            intent.putExtra("cometonotification", Utils.getPref(mContext, "cometonotification"));
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            builder.setContentTitle(aMessage)                            // required
                    .setSmallIcon(R.drawable.app_logo)   // required
                    .setContentText(context.getString(R.string.app_name)) // required
                    .setDefaults(Notification.DEFAULT_VIBRATE)
                    .setVibrate(new long[]{-1}) //new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400}
                    .setOngoing(true)
                    .setAutoCancel(false)
                    .setContentIntent(pendingIntent)
                    .setTicker(aMessage)
//                    .setLargeIcon(bitmap)
//                    .setStyle(new NotificationCompat.BigPictureStyle()
//                            .bigPicture(bitmap)
//                            .bigLargeIcon(bitmap))
                    .setProgress(100, progress, false)
                    .setPriority(Notification.PRIORITY_HIGH);
        }
        Notification notification = builder.build();

        notification.flags |= Notification.FLAG_AUTO_CANCEL | Notification.FLAG_ONGOING_EVENT;//Notification.FLAG_AUTO_CANCEL |
        notifManager.notify(NOTIFY_ID, notification);

//        new Handler().postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//                notifManager.cancel(NOTIFY_ID);
//            }
//        }, 60000);
    }
}
