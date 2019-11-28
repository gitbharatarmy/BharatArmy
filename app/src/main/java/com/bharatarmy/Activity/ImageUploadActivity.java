package com.bharatarmy.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bharatarmy.Adapter.SelectedImageVideoViewAdapter;
import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.Models.GalleryImageModel;
import com.bharatarmy.Models.MyScreenChnagesModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.DbHandler;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityImageUploadBinding;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static androidx.core.content.FileProvider.getUriForFile;

/* delete extra code 22/08/2019 backup in 22/08/2019
 *  remove video code 17/09/2019 backup in 17/09/2019 morning*/
public class ImageUploadActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityImageUploadBinding activityImageUploadBinding;
    Context mContext;
    public static final int REQUEST_IMAGE = 100;
    Uri uri, selectedUri;

    SelectedImageVideoViewAdapter selectedImageVideoViewAdapter;
    LinearLayoutManager linearLayoutManager;

    public List<GalleryImageModel> galleryImageList;
    File Camerafile;
    String imageorvideoStr = "";
    private static final int CUSTOM_REQUEST_CODE = 532;
    public static final int RC_PHOTO_PICKER_PERM = 123;
    private int MAX_ATTACHMENT_COUNT = 20;
    private ArrayList<String> photoPaths = new ArrayList<>();
    public String fileName,photoprivacyStr;


    // Database
    DbHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityImageUploadBinding = DataBindingUtil.setContentView(this, R.layout.activity_image_upload);
        mContext = ImageUploadActivity.this;

        EventBus.getDefault().register(this);
        init();
        setListiner();
    }

    public void init() {
        dbHandler = new DbHandler(mContext);
        galleryImageList = new ArrayList<>();



    }
    @Subscribe
    public void customEventReceived(MyScreenChnagesModel event) {
        Log.d("imageId :", event.getPrivacyname());
        if (!event.getPrivacyname().equalsIgnoreCase("")) {{
            if (event.getPrivacyname().equalsIgnoreCase("Public")){
                activityImageUploadBinding.privacyTxt.setText(event.getPrivacyname());
                activityImageUploadBinding.privacyImage.setImageDrawable(getDrawable(R.drawable.ic_aboutus));
            }else if (event.getPrivacyname().equalsIgnoreCase("Private")){
                activityImageUploadBinding.privacyImage.setImageDrawable(getDrawable(R.drawable.ic_private_user));
                activityImageUploadBinding.privacyTxt.setText(event.getPrivacyname());
            }
        }

        }

    }
    public void setListiner() {
        imageorvideoStr = getIntent().getStringExtra("image/video");
        Utils.setPref(mContext, "image/video", imageorvideoStr);
        if (imageorvideoStr.equalsIgnoreCase("image")) {
            activityImageUploadBinding.selectedImageVideoLinear.setVisibility(View.VISIBLE);
        }
        activityImageUploadBinding.backImg.setOnClickListener(this);
        activityImageUploadBinding.chooseFromGalleryLinear.setOnClickListener(this);
        activityImageUploadBinding.chooseFromCameraLinear.setOnClickListener(this);
        activityImageUploadBinding.submitLinear.setOnClickListener(this);
        activityImageUploadBinding.pictureChooseLinear.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                ImageUploadActivity.this.finish();
                break;
            case R.id.choose_from_camera_linear:
                Utils.handleClickEvent(mContext,activityImageUploadBinding.chooseFromCameraLinear);
                if (imageorvideoStr.equalsIgnoreCase("image")) {
                    if (galleryImageList.size() < MAX_ATTACHMENT_COUNT) {
                        openImageCapture();
                    } else {
                        Utils.ping(mContext, "max limit 20");
                    }
                }
                break;
            case R.id.choose_from_gallery_linear:
                Utils.handleClickEvent(mContext,activityImageUploadBinding.chooseFromGalleryLinear);
                if (imageorvideoStr.equalsIgnoreCase("image")) {
                    pickPhotoClicked();
                }
                break;
            case R.id.submit_linear:
                Utils.handleClickEvent(mContext, activityImageUploadBinding.submitLinear);

                boolean connected = Utils.checkNetwork(mContext);


                if (connected == true) {
                    if (galleryImageList != null && galleryImageList.size() > 0) {
                        for (int i = 0; i < galleryImageList.size(); i++) {
                            dbHandler.insertImageDetails(galleryImageList.get(i).getImageUri(), galleryImageList.get(i).getImageSize(),
                                    galleryImageList.get(i).getUploadcompelet(), galleryImageList.get(i).getVideolength(),
                                    galleryImageList.get(i).getFileType(), galleryImageList.get(i).getVideoTitle(),
                                    galleryImageList.get(i).getVideoDesc(), galleryImageList.get(i).getVideoHeight(),
                                    galleryImageList.get(i).getVideoWidth(), mContext);
                        }

                        Utils.showThanyouDialog(ImageUploadActivity.this, "imageUpload");

                    } else {
                        Utils.ping(mContext, "Please select image");
                    }

                } else {
                    Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), ImageUploadActivity.this);
                }
                break;
            case R.id.picture_choose_linear:
                photoprivacyStr =activityImageUploadBinding.privacyTxt.getText().toString();
                Intent privacyIntent = new Intent(mContext, ImageVideoPrivacyActivity.class);
                privacyIntent.putExtra("privacytxt",photoprivacyStr);
                startActivity(privacyIntent);
                break;
        }

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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_IMAGE) {
                getCameraImagePath(fileName);
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
            galleryImageList.add(new GalleryImageModel(filePaths.get(i), size((int) findsize), "0", "0", "1", "", "", "", ""));
        }
        loadProfile();
    }

    private void loadProfile() {
        if (galleryImageList.size()>0){
            activityImageUploadBinding.pictureMainLinear.setVisibility(View.VISIBLE);
        }else{
            activityImageUploadBinding.pictureMainLinear.setVisibility(View.GONE);
        }
        activityImageUploadBinding.selectedImageVideoLinear.setVisibility(View.VISIBLE);
        selectedImageVideoViewAdapter = new SelectedImageVideoViewAdapter(mContext, galleryImageList, new image_click() {
            @Override
            public void image_more_click() {
                String getSelectedImageremove = String.valueOf(selectedImageVideoViewAdapter.selectedpositionRemove());
                Log.d("removePic", getSelectedImageremove);

                for (int i = 0; i < galleryImageList.size(); i++) {
                    if (i == Integer.parseInt(getSelectedImageremove)) {
                        galleryImageList.remove(i);
                        selectedImageVideoViewAdapter.notifyDataSetChanged();
                    }
                }
                if (galleryImageList.size()>0){
                    activityImageUploadBinding.pictureMainLinear.setVisibility(View.VISIBLE);
                }else{
                    activityImageUploadBinding.pictureMainLinear.setVisibility(View.GONE);
                }
            }
        });//,onTouchListener
        linearLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        activityImageUploadBinding.selectedImagesView.setLayoutManager(linearLayoutManager);
        activityImageUploadBinding.selectedImagesView.setItemAnimator(new DefaultItemAnimator());
        activityImageUploadBinding.selectedImagesView.setAdapter(selectedImageVideoViewAdapter);


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

        return getUriForFile(ImageUploadActivity.this, getPackageName() + ".provider", image);
    }

    private void getCameraImagePath(String fileName) {
        File path = new File(getExternalCacheDir(), "camera");
        Camerafile = path;
        File image = new File(path, fileName);

        Log.d("imagegetFile : ", "" + image);
        String imageUrl = String.valueOf(image);

        long findsize = Camerafile.length() / 1024;
        Log.d("findfilesize", "" + Camerafile.length() / 1024 + "kb" + " " + Camerafile.length() / (1024 * 1024));

        galleryImageList.add(new GalleryImageModel(imageUrl, size((int) findsize), "0", "0", "1", "", "", "", ""));

        loadProfile();

    }
    //Inside the activity that makes a connection to the helper class
    @Override
    protected void onDestroy () {
        super.onDestroy();
        //call close() of the helper class
        dbHandler.close();
    }
}
