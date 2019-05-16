package com.bharatarmy.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bharatarmy.Fragment.MyProfileFragment;
import com.bharatarmy.Models.LogginModel;
import com.bharatarmy.Models.LoginDataModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.ApiHandler;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.NetworkClient;
import com.bharatarmy.Utility.ProgressRequestBody;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.Utility.WebServices;
import com.bharatarmy.databinding.ActivityEditProfileBinding;
import com.google.android.gms.dynamic.IFragmentWrapper;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.rilixtech.Country;
import com.rilixtech.CountryCodePicker;
import com.squareup.picasso.Picasso;
//import com.yalantis.ucrop.util.FileUtils;


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

public class EditProfileActivity extends BaseActivity implements View.OnClickListener, ProgressRequestBody.UploadCallbacks {

    private static final String TAG = EditProfileActivity.class.getSimpleName();
    public static final int REQUEST_IMAGE = 100;

    ActivityEditProfileBinding activityEditProfileBinding;
    Context mContext;
    String fullNameStr = "", countryCodeStr = "", phoneNoStr = "", genderStr = "", appUser = "", fileStr = "";
    Uri uri;
    File file = null;
    int mFileLen;
    ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityEditProfileBinding = DataBindingUtil.setContentView(this, R.layout.activity_edit_profile);
        mContext = EditProfileActivity.this;

        ImagePickerActivity.clearCache(this);
        setDataValue();
        setListiner();

        setTitleText("Member Profile");
        setBackButton(EditProfileActivity.this);
//        activityEditProfileBinding.backImg.setOnClickListener(this);
    }

    public void setDataValue() {
        activityEditProfileBinding.ccp.setCountryForNameCode(AppConfiguration.currentCountry);
        activityEditProfileBinding.usernameTitleTxt.setText(Utils.getPref(mContext, "LoginUserName"));
        activityEditProfileBinding.userNameEdt.setText(Utils.getPref(mContext, "LoginUserName"));
        activityEditProfileBinding.emailEdt.setText(Utils.getPref(mContext, "LoginEmailId"));
        activityEditProfileBinding.phoneNoEdt.setText(Utils.getPref(mContext, "LoginPhoneNo"));
        if (Utils.getPref(mContext, "Gender").equalsIgnoreCase("1")) {
            activityEditProfileBinding.maleRb.setChecked(true);
        } else {
            activityEditProfileBinding.femaleRb.setChecked(true);
        }
        Picasso.with(mContext)
                .load(Utils.getPref(mContext, "LoginProfilePic"))
                .placeholder(R.drawable.progress_animation)
                .into(activityEditProfileBinding.profileImage);
    }

    public void setListiner() {
        activityEditProfileBinding.ccp.registerPhoneNumberTextView(activityEditProfileBinding.phoneNoEdt);
        activityEditProfileBinding.uploadTxt.setOnClickListener(this);
        activityEditProfileBinding.saveBtn.setOnClickListener(this);
        activityEditProfileBinding.cancelBtn.setOnClickListener(this);

        activityEditProfileBinding.genderRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.male_rb) {
                    Utils.setPref(mContext, "Gender", "1");
                } else  if (checkedId == R.id.female_rb) {
                    Utils.setPref(mContext, "Gender", "2");
                }
//                switch (checkedId) {
//                    case R.id.male_rb:
//                        Utils.setPref(mContext, "Gender", "1");
//                        break;
//                    case R.id.female_rb:
//                        Utils.setPref(mContext, "Gender", "2");
//                        break;
//                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.back_img:
//                EditProfileActivity.this.finish();
//                break;
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
            case R.id.cancel_btn:
                EditProfileActivity.this.finish();
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
                Log.d("path", "" + uri);

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                loadProfile(uri.toString());

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
        MultipartBody.Part body = null;
        if (uri != null) {

            String filePath = Utils.getFilePathFromUri(mContext, uri);

            mDialog = new ProgressDialog(mContext);
            mDialog.setCancelable(false);
            mDialog.setMessage("Uploading Profile Picture");
            mDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mDialog.setIndeterminate(false);
            mDialog.show();
            try {
                file = new File(filePath);

            } catch (Exception e) {
                e.printStackTrace();
            }
            if (file != null) {
                if (file.exists()) {
                    filePath = file.getAbsolutePath();
                }
            }

            if (file != null) {
                if (file.exists()) {

//                    RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//                    body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
                    ProgressRequestBody fileBody = new ProgressRequestBody(file, this);
                    body = MultipartBody.Part.createFormData("image", file.getName(), fileBody);

                }
            }
        } else {
            Utils.showDialog(mContext);
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), "");

            body = MultipartBody.Part.createFormData("file", "", requestFile);
        }
        Retrofit retrofit = NetworkClient.getRetrofitClient(this);
        WebServices uploadAPIs = retrofit.create(WebServices.class);

        RequestBody appuserId = RequestBody.create(MediaType.parse("text/plain"), appUser);
        RequestBody fullname = RequestBody.create(MediaType.parse("text/plain"), fullNameStr);
        RequestBody countycode = RequestBody.create(MediaType.parse("text/plain"), countryCodeStr);
        RequestBody phoneno = RequestBody.create(MediaType.parse("text/plain"), phoneNoStr);
        RequestBody gender = RequestBody.create(MediaType.parse("text/plain"), genderStr);

//        ShowProgressDialog();
        Call<LogginModel> responseBodyCall = uploadAPIs.updateprofile(appuserId, fullname,
                countycode, phoneno, gender, body);
        Log.d("File", "" + responseBodyCall);
        responseBodyCall.enqueue(new Callback<LogginModel>() {

            @Override
            public void onResponse(Call<LogginModel> call, retrofit2.Response<LogginModel> response) {
                if (uri != null) {
                    mDialog.dismiss();
                } else {
                    Utils.dismissDialog();
                }
                if (response.body().getIsValid() == 1) {
                    Utils.setPref(mContext, "LoginUserName", response.body().getData().getName());
                    Utils.setPref(mContext, "LoginEmailId", response.body().getData().getEmail());
                    Utils.setPref(mContext, "LoginPhoneNo", response.body().getData().getPhoneNo());
                    Utils.setPref(mContext, "LoginProfilePic", response.body().getData().getProfilePicUrl());
                    Utils.setPref(mContext, "EmailVerified", String.valueOf(response.body().getData().getIsEmailVerified()));
                    Utils.setPref(mContext, "PhoneVerified", String.valueOf(response.body().getData().getIsNumberVerified()));
                    Utils.setPref(mContext, "AppUserId", String.valueOf(response.body().getData().getId()));
                    Utils.setPref(mContext, "Gender", String.valueOf(response.body().getData().getGender()));

                    Utils.ping(mContext, "Profile Updated Successfully");
                    AppConfiguration.position = 1;
//                    DashboardActivity.navItemIndex=2;
//                    Intent iDash = new Intent(mContext, DashboardActivity.class);
//                    startActivity(iDash);
                    finish();
                } else {
                    Utils.ping(mContext, response.body().getMessage());
                }


            }

            @Override
            public void onFailure(Call<LogginModel> call, Throwable t) {
                Log.d("failure", "message = " + t.getMessage());
                Log.d("failure", "cause = " + t.getCause());
                 if (uri != null) {
                    mDialog.dismiss();
                } else {
                    Utils.dismissDialog();
                }
            }
        });


    }


    public void getUpdateData() {
        fullNameStr = activityEditProfileBinding.userNameEdt.getText().toString();
        countryCodeStr = activityEditProfileBinding.ccp.getSelectedCountryCode();
        phoneNoStr = activityEditProfileBinding.phoneNoEdt.getText().toString();
        appUser = Utils.getPref(mContext, "AppUserId");

        genderStr = Utils.getPref(mContext, "Gender");
        Log.d("DataValue", "Name :" + fullNameStr + "countrycode:" + countryCodeStr
                + "phone number:" + phoneNoStr + "userid:" + appUser + "gender:" + genderStr);

        if (!fullNameStr.equalsIgnoreCase("")) {
            if (!countryCodeStr.equalsIgnoreCase("")) {
                if (!phoneNoStr.equalsIgnoreCase("")) {
                    if (!genderStr.equalsIgnoreCase("0")) {
                        getUpdateProfile();
                    } else {
                        Utils.ping(mContext, "Please select gender");
                    }
                } else {
                    activityEditProfileBinding.phoneNoEdt.setError("Please enter phonenumber");
                }
            } else {
                Utils.ping(mContext, "Please select country code");
            }
        } else {
            activityEditProfileBinding.userNameEdt.setError("Please enter fullname");
        }
    }


    @Override
    public void onProgressUpdate(int percentage) {
        mDialog.setProgress(percentage);
    }

    @Override
    public void onError() {
        mDialog.dismiss();
        Utils.ping(mContext, "Try Again");
    }

    @Override
    public void onFinish() {
        mDialog.setProgress(100);
    }
}
