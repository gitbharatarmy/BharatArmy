package com.bharatarmy.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;

import com.bharatarmy.Fragment.MyProfileFragment;
import com.bharatarmy.Models.LogginModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.ApiHandler;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.NetworkClient;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.Utility.WebServices;
import com.bharatarmy.databinding.ActivityEditProfileBinding;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.rilixtech.Country;
import com.rilixtech.CountryCodePicker;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = EditProfileActivity.class.getSimpleName();
    public static final int REQUEST_IMAGE = 100;

    ActivityEditProfileBinding activityEditProfileBinding;
    Context mContext;
    String fullNameStr="", countryCodeStr="", phoneNoStr="", genderStr="";
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityEditProfileBinding = DataBindingUtil.setContentView(this, R.layout.activity_edit_profile);
        mContext = EditProfileActivity.this;


        ImagePickerActivity.clearCache(this);
        setDataValue();
        setListiner();
        activityEditProfileBinding.backImg.setOnClickListener(this);
    }

    public void setDataValue() {
        activityEditProfileBinding.usernameTitleTxt.setText(Utils.getPref(mContext, "LoginUserName"));
        activityEditProfileBinding.userNameEdt.setText(Utils.getPref(mContext, "LoginUserName"));
        activityEditProfileBinding.emailEdt.setText(Utils.getPref(mContext, "LoginEmailId"));
        activityEditProfileBinding.phoneNoEdt.setText(Utils.getPref(mContext, "LoginPhoneNo"));
//        activityEditProfileBinding.ccp.setCountryForNameCode("US");
    }

    public void setListiner() {
        activityEditProfileBinding.ccp.registerPhoneNumberTextView(activityEditProfileBinding.phoneNoEdt);
        activityEditProfileBinding.uploadTxt.setOnClickListener(this);
        activityEditProfileBinding.saveBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                EditProfileActivity.this.finish();
                break;
            case R.id.upload_txt:
                Dexter.withActivity(EditProfileActivity.this)
                        .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport report) {
                                if (report.areAllPermissionsGranted()) {
                                    showImagePickerOptions();
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
            case R.id.save_btn:
                getUpdateData();
                break;
        }
    }

    private void showImagePickerOptions() {
        ImagePickerActivity.showImagePickerOptions(this, new ImagePickerActivity.PickerOptionListener() {
            @Override
            public void onTakeCameraSelected() {
                launchCameraIntent();
            }

            @Override
            public void onChooseGallerySelected() {
                launchGalleryIntent();
            }
        });
    }

    private void launchCameraIntent() {
        Intent intent = new Intent(EditProfileActivity.this, ImagePickerActivity.class);
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
        Intent intent = new Intent(EditProfileActivity.this, ImagePickerActivity.class);
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
                try {
                    // You can update this bitmap to your server
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);

                    // loading profile image from local cache
                    loadProfile(uri.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Showing Alert Dialog with Settings option
     * Navigates user to app settings
     * NOTE: Keep proper title and message depending on your app
     */
    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(EditProfileActivity.this);
        builder.setTitle(getString(R.string.dialog_permission_title));
        builder.setMessage(getString(R.string.dialog_permission_message));
        builder.setPositiveButton(getString(R.string.go_to_settings), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                EditProfileActivity.this.openSettings();
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

    private void loadProfile(String url) {
        Log.d(TAG, "Image cache path: " + url);
        Picasso.with(mContext).load(url).into(activityEditProfileBinding.profileImage);
    }

    /*use for Update profile*/
    public void getUpdateProfile() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), EditProfileActivity.this);
            return;
        }


        Retrofit retrofit = NetworkClient.getRetrofitClient(this);
        WebServices uploadAPIs = retrofit.create(WebServices.class);


        //Create a file object using file path
        File file = new File(String.valueOf(uri));
        // Create a request body with file and image media type
        RequestBody fileReqBody = RequestBody.create(MediaType.parse("*/*"), file);
        // Create MultipartBody.Part using file request-body,file name and part name
        MultipartBody.Part part = MultipartBody.Part.createFormData("uploaded_file", file.getName(), fileReqBody);
        //Create request body with text description and text media type
        RequestBody description = RequestBody.create(MediaType.parse("text/plain"), file.getName());
        //
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);

//        RequestBody fullNameStr = RequestBody.create(MediaType.parse("text/plain"),fullNameStr);
//        RequestBody countryCodeStr = RequestBody.create(MediaType.parse("text/plain"), countryCodeStr);
//        RequestBody phoneNoStr = RequestBody.create(MediaType.parse("text/plain"),phoneNoStr);
//        RequestBody genderStr = RequestBody.create(MediaType.parse("text/plain"), "1");
//        RequestBody appid = RequestBody.create(MediaType.parse("text/plain"), Utils.getPref(mContext, "AppUserId"));
//
//        Map<String, RequestBody> map = new HashMap<>();
//        map.put("AppUserId",appid );
//        map.put("FullName", fullNameStr);
//        map.put("CountryCode", countryCodeStr);
//        map.put("PhoneNo", phoneNoStr);
//        map.put("Gender", genderStr);

//        Log.d("path",""+part+map);
//        Call call = uploadAPIs.uploadImage(part,description, map);
        Utils.showDialog(mContext);
        Call call = uploadAPIs.uploadImage(requestFile);
        Log.d("call",""+call) ;
        call.enqueue(new Callback() {

            @Override
            public void onResponse(Call call, retrofit2.Response response) {
                Utils.dismissDialog();
                Log.d("upload image",response.toString());

            }

            @Override
            public void onFailure(Call call, Throwable t) {
            }
        });
    }

//    private Map<String, String> getUpdateProfileData() {
//        Map<String, String> map = new HashMap<>();
//        map.put("AppUserId", Utils.getPref(mContext,"AppUserId"));
//        map.put("FullName", fullNameStr);
//        map.put("CountryCode", countryCodeStr);
//        map.put("PhoneNo",phoneNoStr);
//        map.put("Gender",genderStr);
//        return map;
//    }


    public void getUpdateData() {
        fullNameStr = activityEditProfileBinding.userNameEdt.getText().toString();
        countryCodeStr = activityEditProfileBinding.ccp.getSelectedCountryCode();
        phoneNoStr = activityEditProfileBinding.phoneNoEdt.getText().toString();

        activityEditProfileBinding.genderRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.male_rb:
                        genderStr = "1";
                        break;
                    case R.id.female_rb:
                        genderStr = "2";
                        break;
                }
            }
        });

        getUpdateProfile();
//        if (!fullNameStr.equalsIgnoreCase("")){
//            if (!countryCodeStr.equalsIgnoreCase("")){
//                if (!phoneNoStr.equalsIgnoreCase("")){
//                    if (!genderStr.equalsIgnoreCase("")){
//                        getUpdateProfile();
//                    }else {
//                        Utils.ping(mContext,"Please select gender");
//                    }
//                }else{
//                    activityEditProfileBinding.phoneNoEdt.setError("Please enter phonenumber");
//                }
//            }else{
//                Utils.ping(mContext,"Please select country code");
//            }
//        }else {
//            activityEditProfileBinding.userNameEdt.setError("Please enter fullname");
//        }
    }
}
