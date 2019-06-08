package com.bharatarmy.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import com.bharatarmy.Adapter.GalleryImageDetailAdapter;
import com.bharatarmy.Adapter.SelectedImageVideoViewAdapter;
import com.bharatarmy.R;
import com.bharatarmy.Utility.SnapHelperOneByOne;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityImageVideoUploadBinding;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImageVideoUploadActivity extends BaseActivity implements View.OnClickListener {
    ActivityImageVideoUploadBinding activityImageVideoUploadBinding;
    Context mContext;
    private static final String TAG = ImageVideoUploadActivity.class.getSimpleName();
    public static final int REQUEST_IMAGE = 100;
    public static final int REQUEST_IMAGE_CAPTURE = 0;
    public static final int REQUEST_GALLERY_IMAGE = 1;
    Uri uri;
    File file = null;
    int mFileLen;
    ProgressDialog mDialog;
    ArrayList<String> selectedurl  = new ArrayList<>();;
    SelectedImageVideoViewAdapter selectedImageVideoViewAdapter;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityImageVideoUploadBinding = DataBindingUtil.setContentView(this, R.layout.activity_image_video_upload);
        mContext = ImageVideoUploadActivity.this;
        ImagePickerActivity.clearCache(this);

        setListiner();
    }

    public void setListiner() {
        activityImageVideoUploadBinding.backImg.setOnClickListener(this);
        activityImageVideoUploadBinding.chooseLinear.setOnClickListener(this);
        activityImageVideoUploadBinding.cameraUnselectLinear.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                ImageVideoUploadActivity.this.finish();
                break;
            case R.id.camera_unselect_linear:
                Dexter.withActivity(ImageVideoUploadActivity.this)
                        .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport report) {
                                if (report.areAllPermissionsGranted()) {
                                    launchCameraIntent();
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
            case R.id.choose_linear:
                Dexter.withActivity(ImageVideoUploadActivity.this)
                        .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport report) {
                                if (report.areAllPermissionsGranted()) {
                                    launchGalleryIntent();
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
        Intent intent = new Intent(ImageVideoUploadActivity.this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_IMAGE_CAPTURE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);

        // setting maximum bitmap width and height
        intent.putExtra(ImagePickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_WIDTH, 1000);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_HEIGHT, 1000);

        startActivityForResult(intent, REQUEST_IMAGE);
    }

    private void launchGalleryIntent() {
        Intent intent = new Intent(ImageVideoUploadActivity.this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_GALLERY_IMAGE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);
        startActivityForResult(intent, REQUEST_IMAGE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                uri = data.getParcelableExtra("path");
                Log.d("path", "" + uri);

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                selectedurl.add(uri.toString());
                loadProfile(selectedurl);
            }
        }
    }

    private void loadProfile(ArrayList<String> urlArrayList) {
        Log.d(TAG, "Image cache path: " + urlArrayList);
        if (urlArrayList != null) {
            activityImageVideoUploadBinding.cameraUnselectLinear.setVisibility(View.GONE);
            activityImageVideoUploadBinding.selectedImageVideoLinear.setVisibility(View.VISIBLE);

            selectedImageVideoViewAdapter = new SelectedImageVideoViewAdapter(mContext,urlArrayList);//,onTouchListener
            linearLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
            activityImageVideoUploadBinding.selectedImagesView.setLayoutManager(linearLayoutManager);
            activityImageVideoUploadBinding.selectedImagesView.setItemAnimator(new DefaultItemAnimator());
            activityImageVideoUploadBinding.selectedImagesView.setAdapter(selectedImageVideoViewAdapter);


        }
    }
}
