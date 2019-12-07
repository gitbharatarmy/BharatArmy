package com.bharatarmy.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Settings;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bharatarmy.Country;
import com.bharatarmy.CountryCodePicker;
import com.bharatarmy.Models.ImageDetailModel;
import com.bharatarmy.Models.ImageMainModel;
import com.bharatarmy.Models.LogginModel;
import com.bharatarmy.Models.MyScreenChnagesModel;
import com.bharatarmy.MultiSelectDialog;
import com.bharatarmy.R;
import com.bharatarmy.Utility.ApiHandler;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.NetworkClient;
import com.bharatarmy.Utility.ProgressRequestBody;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.Utility.WebServices;
import com.bharatarmy.databinding.ActivityEditProfileBinding;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener, ProgressRequestBody.UploadCallbacks {

    private static final String TAG = EditProfileActivity.class.getSimpleName();
    public static final int REQUEST_IMAGE = 100;

    ActivityEditProfileBinding activityEditProfileBinding;
    Context mContext;
    String emailStr = "", firstNameStr = "", lastNameStr = "", countryCodeStr = "", phoneNoStr = "", genderStr = "0", appUser = "",
            fileStr = "", countryISOCodeStr = "", countryPhoneNoStr = "", statesIdStr = "", citiesIdStr = "",
            addressLine1Str = "", addressLine2Str = "", areaStr = "", stateNameStr = "", cityNameStr = "",
            pincodeStr = "", facebookprofileStr = "", twitterprofileStr = "", linkedinprofileStr = "", instagramprofileStr = "";
    Uri uri;
    File file = null;
    int mFileLen;
    ProgressDialog mDialog;
    MultiSelectDialog multiSelectstateDialog, multiSelectcitiesDialog;
    List<ImageDetailModel> statesList;
    List<ImageDetailModel> citiesList;
    ArrayList<Integer> alreadySelectedState = new ArrayList<>();
    ArrayList<Integer> alreadySelectedCities = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityEditProfileBinding = DataBindingUtil.setContentView(this, R.layout.activity_edit_profile);
        mContext = EditProfileActivity.this;


        init();
        setListiner();

    }

    public void init() {
        if (Utils.retriveLoginData(mContext).getCountryISOCode() == null ||
                Utils.retriveLoginData(mContext).getCountryISOCode().equalsIgnoreCase("")) {
            AppConfiguration.currentCountryISOCode = Utils.retriveCurrentLocationData(mContext).getIsoCode();
            Log.d("ISOCOde", AppConfiguration.currentCountryISOCode);
        } else {
            AppConfiguration.currentCountryISOCode = Utils.retriveLoginData(mContext).getCountryISOCode();
        }
        setDataValue();
    }

    public void setDataValue() {
//        activityEditProfileBinding.ccp.setCountryForNameCode(AppConfiguration.currentCountryISOCode);
        activityEditProfileBinding.toolbarTitleTxt.setText("Edit Member Profile");
        if (Utils.retriveLoginData(mContext) != null) {
            if (Utils.retriveLoginData(mContext).getCountryPhoneNo() != null &&
                    !Utils.retriveLoginData(mContext).getCountryPhoneNo().equalsIgnoreCase("")) {
                activityEditProfileBinding.ccp.setCountryForNameCode(Utils.retriveLoginData(mContext).getCountryPhoneNo());
            }
            if (Utils.retriveLoginData(mContext).getFirstName() != null) {
                activityEditProfileBinding.userFnameEdt.setText(Utils.retriveLoginData(mContext).getFirstName());
            }
            if (Utils.retriveLoginData(mContext).getLastName() != null) {
                activityEditProfileBinding.userLnameEdt.setText(Utils.retriveLoginData(mContext).getLastName());
            }
            if (Utils.retriveLoginData(mContext).getEmail() != null) {
                activityEditProfileBinding.emailEdt.setText(Utils.retriveLoginData(mContext).getEmail());
            }
            if (Utils.retriveLoginData(mContext).getPhoneNo() != null &&
                    !Utils.retriveLoginData(mContext).getPhoneNo().equalsIgnoreCase("")) {
                activityEditProfileBinding.phoneNoEdt.setText(Utils.retriveLoginData(mContext).getPhoneNo());
            } else {
                activityEditProfileBinding.phoneNoEdt.setHint(getResources().getString(R.string.phoneno));
            }
            if (Utils.retriveLoginData(mContext).getCountryISOCode() != null) {
                if (!Utils.retriveLoginData(mContext).getCountryISOCode().equalsIgnoreCase("")) {
                    AppConfiguration.currentCountryISOCode = Utils.retriveLoginData(mContext).getCountryISOCode();
                } else {
                    AppConfiguration.currentCountryISOCode = Utils.retriveCurrentLocationData(mContext).getIsoCode();
                }
            }
            if (Utils.retriveLoginData(mContext).getGender() != null) {
                if (Utils.retriveLoginData(mContext).getGender().equals(0)) {
                    activityEditProfileBinding.maleRb.setChecked(false);
                    activityEditProfileBinding.femaleRb.setChecked(false);
                } else if (Utils.retriveLoginData(mContext).getGender().equals(1)) {
                    activityEditProfileBinding.maleRb.setChecked(true);
                    genderStr = "1";
                } else if (Utils.retriveLoginData(mContext).getGender().equals(2)) {
                    activityEditProfileBinding.femaleRb.setChecked(true);
                    genderStr = "2";
                }
            }
            if (Utils.retriveLoginData(mContext).getProfilePicUrl() != null) {
                Picasso.with(mContext)
                        .load(Utils.retriveLoginData(mContext).getProfilePicUrl())
                        .placeholder(R.drawable.progress_animation)
                        .into(activityEditProfileBinding.profileImage);
            }
            if (Utils.retriveLoginData(mContext).getAddressline1() != null) {
                if (!Utils.retriveLoginData(mContext).getAddressline1().equalsIgnoreCase("")) {
                    activityEditProfileBinding.address1Edt.setText(Utils.retriveLoginData(mContext).getAddressline1());
                }
            }
            if (Utils.retriveLoginData(mContext).getAddressline2() != null) {
                if (!Utils.retriveLoginData(mContext).getAddressline2().equalsIgnoreCase("")) {
                    activityEditProfileBinding.address2Edt.setText(Utils.retriveLoginData(mContext).getAddressline2());
                }
            }

            if (Utils.retriveLoginData(mContext).getArea() != null) {
                if (!Utils.retriveLoginData(mContext).getArea().equalsIgnoreCase("")) {
                    activityEditProfileBinding.areaEdt.setText(Utils.retriveLoginData(mContext).getArea());
                }
            }
            if (Utils.retriveLoginData(mContext).getStrState() != null) {
                if (!Utils.retriveLoginData(mContext).getStrState().equalsIgnoreCase("")) {
                    activityEditProfileBinding.statesEdt.setText(Utils.retriveLoginData(mContext).getStrState());

                }
            }
            if (Utils.retriveLoginData(mContext).getStateId() != null) {
                if (!Utils.retriveLoginData(mContext).getStateId().equals(0)) {
                    statesIdStr = String.valueOf(Utils.retriveLoginData(mContext).getStateId());
                    alreadySelectedState.add(Utils.retriveLoginData(mContext).getStateId());
                    callCitiesDetailData();
                }
            }
            if (Utils.retriveLoginData(mContext).getStrCityName() != null) {
                if (!Utils.retriveLoginData(mContext).getStrCityName().equalsIgnoreCase("")) {
                    activityEditProfileBinding.cityEdt.setText(Utils.retriveLoginData(mContext).getStrCityName());
                }
            }
            if (Utils.retriveLoginData(mContext).getCityId() != null) {
                if (!Utils.retriveLoginData(mContext).getCityId().equals(0)) {
                    citiesIdStr = String.valueOf(Utils.retriveLoginData(mContext).getCityId());
                    alreadySelectedCities.add(Utils.retriveLoginData(mContext).getCityId());
                }
            }
            if (Utils.retriveLoginData(mContext).getPincode() != null) {
                if (!Utils.retriveLoginData(mContext).getPincode().equalsIgnoreCase("")) {
                    activityEditProfileBinding.pincodeEdt.setText(Utils.retriveLoginData(mContext).getPincode());
                }
            }
        }
        callStatesDetailData();
    }

    public void setListiner() {
        Log.d("CCCP CountryNameCode", activityEditProfileBinding.ccp.getSelectedCountryName());
        Log.d("countryName :", activityEditProfileBinding.ccp.getSelectedCountryNameCode());

        activityEditProfileBinding.ccp.registerPhoneNumberTextView(activityEditProfileBinding.phoneNoEdt);
        activityEditProfileBinding.saveBtn.setOnClickListener(this);
        activityEditProfileBinding.cancelBtn.setOnClickListener(this);
        activityEditProfileBinding.profileImage.setOnClickListener(this);
        activityEditProfileBinding.statesSelectionImg.setOnClickListener(this);
        activityEditProfileBinding.citySelectionImg.setOnClickListener(this);
        activityEditProfileBinding.cityEdt.setOnClickListener(this);
        activityEditProfileBinding.statesEdt.setOnClickListener(this);
        activityEditProfileBinding.backImg.setOnClickListener(this);

        activityEditProfileBinding.genderRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.male_rb) {
                    genderStr = "1";
                } else if (checkedId == R.id.female_rb) {
                    genderStr = "2";
                }
            }
        });

        activityEditProfileBinding.phoneNoEdt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
//                    getUpdateData();
                    Utils.hideKeyboard(EditProfileActivity.this);
                }
                return false;
            }
        });
        activityEditProfileBinding.ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected(Country selectedCountry) {
                activityEditProfileBinding.statesEdt.setText("");
                activityEditProfileBinding.cityEdt.setText("");
                activityEditProfileBinding.statesEdt.setHint("Select");
                activityEditProfileBinding.cityEdt.setHint("Select");
                statesIdStr = "";
                citiesIdStr = "";
                alreadySelectedState.clear();
                alreadySelectedCities.clear();
                AppConfiguration.currentCountryISOCode = activityEditProfileBinding.ccp.getSelectedCountryNameCode();
                callStatesDetailData();
            }
        });

        activityEditProfileBinding.facebookUserNameEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                activityEditProfileBinding.facebookHandleInfoTxt.setText(mContext.getResources().getString(R.string.facebook_txt_hint) + activityEditProfileBinding.facebookUserNameEdt.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        activityEditProfileBinding.twitterUserNameEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                activityEditProfileBinding.twitterHandleInfoTxt.setText(mContext.getResources().getString(R.string.twitter_txt_hint) + activityEditProfileBinding.twitterUserNameEdt.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        activityEditProfileBinding.linkedinUserNameEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                activityEditProfileBinding.linkedinHandleInfoTxt.setText(mContext.getResources().getString(R.string.linkedin_txt_hint) + activityEditProfileBinding.linkedinUserNameEdt.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        activityEditProfileBinding.instagramUserNameEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                activityEditProfileBinding.instagramHandleInfoTxt.setText(mContext.getResources().getString(R.string.instagram_txt_hint) + activityEditProfileBinding.instagramUserNameEdt.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                backActivity();
                break;
            case R.id.save_btn:
//                Utils.handleClickEvent(mContext, activityEditProfileBinding.saveBtn);
                getUpdateData();
                break;
            case R.id.cancel_btn:
                backActivity();
                break;
            case R.id.profile_image:
                Utils.handleClickEvent(mContext, activityEditProfileBinding.profileImage);
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
            case R.id.states_selection_img:
                setstateValue();
                break;
            case R.id.city_selection_img:
                setcityValue();
                break;
            case R.id.states_edt:
                setstateValue();
                break;
            case R.id.city_edt:
                setcityValue();
                break;

        }
    }

    public void backActivity() {
//        Intent myprofileIntent = new Intent(mContext, MyProfileActivity.class);
//        startActivity(myprofileIntent);
        EventBus.getDefault().post(new MyScreenChnagesModel("change"));
        finish();
    }

    private void showImagePickerOptions() {
        ImageEditProfilePickerActivity.showImagePickerOptions(this, new ImageEditProfilePickerActivity.PickerOptionListener() {
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
        Intent intent = new Intent(EditProfileActivity.this, ImageEditProfilePickerActivity.class);
        intent.putExtra(ImageEditProfilePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImageEditProfilePickerActivity.REQUEST_IMAGE_CAPTURE);

        // setting aspect ratio
        intent.putExtra(ImageEditProfilePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImageEditProfilePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImageEditProfilePickerActivity.INTENT_ASPECT_RATIO_Y, 1);

        // setting maximum bitmap width and height
        intent.putExtra(ImageEditProfilePickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true);
        intent.putExtra(ImageEditProfilePickerActivity.INTENT_BITMAP_MAX_WIDTH, 1000);
        intent.putExtra(ImageEditProfilePickerActivity.INTENT_BITMAP_MAX_HEIGHT, 1000);

        startActivityForResult(intent, REQUEST_IMAGE);
    }

    private void launchGalleryIntent() {
        Intent intent = new Intent(EditProfileActivity.this, ImageEditProfilePickerActivity.class);
        intent.putExtra(ImageEditProfilePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImageEditProfilePickerActivity.REQUEST_GALLERY_IMAGE);

        // setting aspect ratio
        intent.putExtra(ImageEditProfilePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImageEditProfilePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImageEditProfilePickerActivity.INTENT_ASPECT_RATIO_Y, 1);
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
        Utils.setImageInImageView(url, activityEditProfileBinding.profileImage, mContext);
//        Picasso.with(mContext).load(url).into(activityEditProfileBinding.profileImage);
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
        RequestBody firstname = RequestBody.create(MediaType.parse("text/plain"), firstNameStr);
        RequestBody lastname = RequestBody.create(MediaType.parse("text/plain"), lastNameStr);
        RequestBody email = RequestBody.create(MediaType.parse("text/plain"), emailStr);
        RequestBody countryISOCode = RequestBody.create(MediaType.parse("text/plain"), AppConfiguration.currentCountryISOCode);
        RequestBody countycode = RequestBody.create(MediaType.parse("text/plain"), countryCodeStr);
        RequestBody phoneno = RequestBody.create(MediaType.parse("text/plain"), phoneNoStr);
        RequestBody gender = RequestBody.create(MediaType.parse("text/plain"), genderStr);
        RequestBody otptext = RequestBody.create(MediaType.parse("text/plain"), "0");
        RequestBody smssentId = RequestBody.create(MediaType.parse("text/plain"), "0");
        RequestBody addressLine1 = RequestBody.create(MediaType.parse("text/plain"), addressLine1Str);
        RequestBody addressLine2 = RequestBody.create(MediaType.parse("text/plain"), addressLine2Str);
        RequestBody area = RequestBody.create(MediaType.parse("text/plain"), areaStr);
        RequestBody state = RequestBody.create(MediaType.parse("text/plain"), stateNameStr);
        RequestBody city = RequestBody.create(MediaType.parse("text/plain"), cityNameStr);
        RequestBody stateId = RequestBody.create(MediaType.parse("text/plain"), statesIdStr);
        RequestBody citiesId = RequestBody.create(MediaType.parse("text/plain"), citiesIdStr);
        RequestBody pincode = RequestBody.create(MediaType.parse("text/plain"), pincodeStr);
        RequestBody facebookprofile = RequestBody.create(MediaType.parse("text/plain"), facebookprofileStr);
        RequestBody twitterprofile = RequestBody.create(MediaType.parse("text/plain"), twitterprofileStr);
        RequestBody linkedinprofile = RequestBody.create(MediaType.parse("text/plain"), linkedinprofileStr);
        RequestBody instagramprofile = RequestBody.create(MediaType.parse("text/plain"), instagramprofileStr);

//        ShowProgressDialog();
        Call<LogginModel> responseBodyCall = uploadAPIs.updateprofile(appuserId, firstname, lastname, email, countryISOCode,
                countycode, phoneno, gender, otptext, smssentId, addressLine1, addressLine2, area, stateId,
                state, citiesId, city, pincode, facebookprofile, twitterprofile, linkedinprofile, instagramprofile, body);
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

                    Utils.storeLoginData(response.body().getData(), mContext);
                    Utils.storeCurrentLocationData(response.body().getCurrentLocation(), mContext);
                    Log.d("profilepic", Utils.retriveLoginData(mContext).getProfilePicUrl());
                    Utils.ping(mContext, "Profile Updated Successfully");
                    AppConfiguration.position = 1;
                    backActivity();
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

    public void MobileNoUpdate() {
        // call the Otp Verification service and get the otp
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), EditProfileActivity.this);
            return;
        }

        Utils.showDialog(mContext);
        ApiHandler.getApiService().getValidatedBAMember(getOtpVerificationData(), new retrofit.Callback<LogginModel>() {
            @Override
            public void success(LogginModel loginModel, Response response) {
                Utils.dismissDialog();
                if (loginModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (loginModel.getIsValid() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (loginModel.getIsValid() == 0) {
                    Utils.ping(mContext, loginModel.getMessage());
                    return;
                }
                if (loginModel.getIsValid() == 1) {
                    Intent otpIntent = new Intent(mContext, OTPActivity.class);
                    otpIntent.putExtra("OTP", loginModel.getData().getOTP());
                    otpIntent.putExtra("wheretocome", "EditProfile");
                    otpIntent.putExtra("EditFirstName", firstNameStr);
                    otpIntent.putExtra("EditLastName", lastNameStr);
                    otpIntent.putExtra("Email", emailStr);
                    otpIntent.putExtra("NewPhoneNumber", phoneNoStr);
                    otpIntent.putExtra("countryCode", countryCodeStr);
                    otpIntent.putExtra("gender", genderStr);
                    otpIntent.putExtra("addressLine1", addressLine1Str);
                    otpIntent.putExtra("addressLine2", addressLine2Str);
                    otpIntent.putExtra("area", areaStr);
                    otpIntent.putExtra("stateName", stateNameStr);
                    otpIntent.putExtra("stateId", statesIdStr);
                    otpIntent.putExtra("cityName", cityNameStr);
                    otpIntent.putExtra("cityId", citiesIdStr);
                    otpIntent.putExtra("pincode", pincodeStr);
                    otpIntent.putExtra("facebookprofile", facebookprofileStr);
                    otpIntent.putExtra("twitterprofile", twitterprofileStr);
                    otpIntent.putExtra("linkedinprofile", linkedinprofileStr);
                    otpIntent.putExtra("instagramprofile", instagramprofileStr);

                    if (uri != null) {
                        otpIntent.setData(uri);
                    } else {
                        uri = Uri.parse("1");
                        otpIntent.setData(uri);
                    }

                    startActivity(otpIntent);
                    finish();
                }
            }

            @Override

            public void failure(RetrofitError error) {
                Utils.dismissDialog();
                error.printStackTrace();
                error.getMessage();
                Utils.ping(mContext, getString(R.string.something_wrong));
            }
        });


    }

    private Map<String, String> getOtpVerificationData() {
        Map<String, String> map = new HashMap<>();
        map.put("AppUserId", appUser);
        map.put("Email", emailStr);
        map.put("PhoneNo", phoneNoStr);
        map.put("CountryPhoneNo", countryCodeStr);
        return map;
    }

    public void getUpdateData() {
        emailStr = activityEditProfileBinding.emailEdt.getText().toString();
        firstNameStr = activityEditProfileBinding.userFnameEdt.getText().toString();
        lastNameStr = activityEditProfileBinding.userLnameEdt.getText().toString();
        countryCodeStr = activityEditProfileBinding.ccp.getSelectedCountryCode();
        phoneNoStr = activityEditProfileBinding.phoneNoEdt.getText().toString();
        appUser = String.valueOf(Utils.getAppUserId(mContext));
        addressLine1Str = activityEditProfileBinding.address1Edt.getText().toString();
        addressLine2Str = activityEditProfileBinding.address2Edt.getText().toString();
        areaStr = activityEditProfileBinding.areaEdt.getText().toString();
        stateNameStr = activityEditProfileBinding.statesEdt.getText().toString();
        cityNameStr = activityEditProfileBinding.cityEdt.getText().toString();
        pincodeStr = activityEditProfileBinding.pincodeEdt.getText().toString();
        facebookprofileStr = activityEditProfileBinding.facebookHandleInfoTxt.getText().toString();
        twitterprofileStr = activityEditProfileBinding.twitterHandleInfoTxt.getText().toString();
        linkedinprofileStr = activityEditProfileBinding.linkedinHandleInfoTxt.getText().toString();
        instagramprofileStr = activityEditProfileBinding.instagramHandleInfoTxt.getText().toString();

        Log.d("DataValue", "Email:" + emailStr + "FirstName :" + firstNameStr + "LastName :" + lastNameStr + "countrycode:" + countryCodeStr
                + "phone number:" + phoneNoStr + "userid:" + appUser + "gender:" + genderStr
                + "CountryNAmeCode: " + AppConfiguration.currentCountryISOCode + "addressLine1:" + addressLine1Str
                + "addressLine2:" + addressLine2Str + "area:" + areaStr + "stateName:" + stateNameStr
                + "cityName:" + cityNameStr + "pincode:" + pincodeStr + "cityId:" + citiesIdStr
                + "stateId:" + statesIdStr + "facebook:" + facebookprofileStr + "twitter:" + twitterprofileStr
                + "linkedin:" + linkedinprofileStr + "instagram:" + instagramprofileStr);

        if (!firstNameStr.equalsIgnoreCase("")) {
            if (!lastNameStr.equalsIgnoreCase("")) {
                if (!countryCodeStr.equalsIgnoreCase("")) {
                    if (!phoneNoStr.equalsIgnoreCase("")) {
                        if (!genderStr.equalsIgnoreCase("0")) {
                            if (Utils.retriveLoginData(mContext).getPhoneNo().equalsIgnoreCase(phoneNoStr)) {
                                getUpdateProfile();
                            } else {
                                AppConfiguration.currentCountryISOCode = activityEditProfileBinding.ccp.getSelectedCountryNameCode();
                                MobileNoUpdate();
                            }
                        } else {
                            Utils.ping(mContext, "Please select gender");
                            activityEditProfileBinding.editScroll.scrollTo(0, activityEditProfileBinding.genderRg.getTop());

                        }
                    } else {
                        activityEditProfileBinding.phoneNoEdt.setError("Please enter phonenumber");
                        activityEditProfileBinding.editScroll.scrollTo(0, activityEditProfileBinding.phoneNoEdt.getTop());
                        activityEditProfileBinding.phoneNoEdt.requestFocus();
                    }
                } else {
                    Utils.ping(mContext, "Please select country code");
                    activityEditProfileBinding.editScroll.scrollTo(0, activityEditProfileBinding.ccp.getTop());
                    activityEditProfileBinding.ccp.requestFocus();
                }
            } else {
                activityEditProfileBinding.userLnameEdt.setError("Please enter lastname");
                activityEditProfileBinding.editScroll.scrollTo(0, activityEditProfileBinding.userLnameEdt.getTop());
                activityEditProfileBinding.userLnameEdt.requestFocus();
            }
        } else {
            activityEditProfileBinding.userFnameEdt.setError("Please enter firstname");
            activityEditProfileBinding.editScroll.scrollTo(0, activityEditProfileBinding.userFnameEdt.getTop());
            activityEditProfileBinding.userFnameEdt.requestFocus();
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


    @Override
    public void onBackPressed() {
        backActivity();
        super.onBackPressed();
    }

    public void setstateValue() {

        if (statesList != null && statesList.size() > 0) {
            if (!statesIdStr.equalsIgnoreCase("")) {
                alreadySelectedState.add(Integer.valueOf(statesIdStr));
            }


            //MultiSelectModel
            multiSelectstateDialog = new MultiSelectDialog()
                    .title("Select States") //setting title for dialog
                    .titleSize(16)
                    .positiveText("")
                    .negativeText("")
                    .setMinSelectionLimit(1)
                    .setMaxSelectionLimit(1)
                    .preSelectIDsList(alreadySelectedState) //List of ids that you need to be selected
                    .multiSelectList((ArrayList<ImageDetailModel>) statesList) // the multi select model list with ids and name
                    .onSubmit(new MultiSelectDialog.SubmitCallbackListener() {
                        @Override
                        public void onSelected(ArrayList<Integer> selectedIds, ArrayList<String> selectedNames, String dataString) {
                            //will return list of selected IDS
                            for (int i = 0; i < selectedIds.size(); i++) {
                                activityEditProfileBinding.statesEdt.setText(selectedNames.get(i));

                                statesIdStr = String.valueOf(selectedIds.get(i));
                                activityEditProfileBinding.cityEdt.setText("");
                                activityEditProfileBinding.cityEdt.setHint("Select");
                                Utils.hideKeyboard(EditProfileActivity.this);
                                callCitiesDetailData();
                            }


                        }

                        @Override
                        public void onCancel() {
                            Log.d(TAG, "Dialog cancelled");

                        }
                    });
            multiSelectstateDialog.show(getSupportFragmentManager(), "statemultiselectdialog");
        } else {
            activityEditProfileBinding.statesEdt.setHint("Select");
        }


    }

    public void setcityValue() {
        if (citiesList != null && citiesList.size() > 0) {
            if (!citiesIdStr.equalsIgnoreCase("")) {
                alreadySelectedCities.add(Integer.valueOf(citiesIdStr));
            }
            //MultiSelectModel
            multiSelectcitiesDialog = new MultiSelectDialog()
                    .title("Select Cities") //setting title for dialog
                    .titleSize(16)
                    .positiveText(" ")
                    .negativeText("")
                    .setMinSelectionLimit(1)
                    .setMaxSelectionLimit(1)
                    .preSelectIDsList(alreadySelectedCities) //List of ids that you need to be selected
                    .multiSelectList((ArrayList<ImageDetailModel>) citiesList) // the multi select model list with ids and name
                    .onSubmit(new MultiSelectDialog.SubmitCallbackListener() {
                        @Override
                        public void onSelected(ArrayList<Integer> selectedIds, ArrayList<String> selectedNames, String dataString) {
                            //will return list of selected IDS
                            for (int i = 0; i < selectedIds.size(); i++) {
                                citiesIdStr = String.valueOf(selectedIds.get(i));
                                activityEditProfileBinding.cityEdt.setText(selectedNames.get(i));

                            }


                        }

                        @Override
                        public void onCancel() {
                            Log.d(TAG, "Dialog cancelled");

                        }
                    });
            multiSelectcitiesDialog.show(getSupportFragmentManager(), "citiesmultiselectdialog");
        } else {
            activityEditProfileBinding.cityEdt.setHint("Select");
        }


    }


    // Api calling GetStatesDetailData
    public void callStatesDetailData() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), EditProfileActivity.this);
            return;
        }

//        Utils.showDialog(mContext);

        ApiHandler.getApiService().getStatesFromCountry(getStatesDetailData(), new retrofit.Callback<ImageMainModel>() {
            @Override
            public void success(ImageMainModel statesMainModel, Response response) {
                Utils.dismissDialog();
                if (statesMainModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (statesMainModel.getIsValid() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (statesMainModel.getIsValid() == 0) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    return;
                }
                if (statesMainModel.getIsValid() == 1) {

                    if (statesMainModel.getData() != null) {
                        statesList = statesMainModel.getData();

                    }

                }
            }

            @Override
            public void failure(RetrofitError error) {
                Utils.dismissDialog();
                error.printStackTrace();
                error.getMessage();
                Utils.ping(mContext, getString(R.string.something_wrong));
            }
        });


    }

    private Map<String, String> getStatesDetailData() {
        Map<String, String> map = new HashMap<>();
        map.put("CountryISOCode", AppConfiguration.currentCountryISOCode);
        map.put("MemberId", String.valueOf(Utils.getAppUserId(mContext)));
        return map;
    }


    // Api calling GetStatesDetailData
    public void callCitiesDetailData() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), EditProfileActivity.this);
            return;
        }

//        Utils.showDialog(mContext);

        ApiHandler.getApiService().getCitiesFromState(getCitiesDetailData(), new retrofit.Callback<ImageMainModel>() {
            @Override
            public void success(ImageMainModel citiesMainModel, Response response) {
                Utils.dismissDialog();
                if (citiesMainModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (citiesMainModel.getIsValid() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (citiesMainModel.getIsValid() == 0) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    return;
                }
                if (citiesMainModel.getIsValid() == 1) {

                    if (citiesMainModel.getData() != null) {
                        citiesList = citiesMainModel.getData();
                    }

                }
            }

            @Override
            public void failure(RetrofitError error) {
                Utils.dismissDialog();
                error.printStackTrace();
                error.getMessage();
                Utils.ping(mContext, getString(R.string.something_wrong));
            }
        });


    }

    private Map<String, String> getCitiesDetailData() {
        Map<String, String> map = new HashMap<>();
        map.put("StateId", statesIdStr);
        map.put("MemberId", String.valueOf(Utils.getAppUserId(mContext)));
        return map;
    }

    /*use for ApplicationUse*/
    public void callApplicationUsesDetail(String go) {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), EditProfileActivity.this);
            return;
        }

//        Utils.showDialog(mContext);
        ApiHandler.getApiService().getApplicationUsage(getapplicationuseData(), new retrofit.Callback<LogginModel>() {
            @Override
            public void success(LogginModel applicationuseModel, Response response) {
                Utils.dismissDialog();
                if (applicationuseModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (applicationuseModel.getIsValid() == null) {
                    return;
                }
                if (applicationuseModel.getIsValid() == 0) {
                    return;
                }
                if (applicationuseModel.getIsValid() == 1) {
                    AppConfiguration.currentCountryISOCode = applicationuseModel.getCurrentLocation().getIsoCode();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Utils.dismissDialog();
                error.printStackTrace();
                error.getMessage();
                Utils.ping(mContext, getString(R.string.something_wrong));
            }
        });

    }

    private Map<String, String> getapplicationuseData() {
        Map<String, String> map = new HashMap<>();
        map.put("TokenId", Utils.getPref(mContext, "registration_id"));
        map.put("ModelName", Utils.getDeviceName());
        map.put("MemberId", String.valueOf(Utils.getAppUserId(mContext)));
        return map;
    }
}
