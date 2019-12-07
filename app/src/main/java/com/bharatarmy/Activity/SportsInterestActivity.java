package com.bharatarmy.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bharatarmy.Adapter.DisplaySFAUserAdapter;
import com.bharatarmy.Country;
import com.bharatarmy.CountryCodePicker;
import com.bharatarmy.Models.GalleryImageModel;
import com.bharatarmy.Models.GetSchoolNameModel;
import com.bharatarmy.Models.ImageDetailModel;
import com.bharatarmy.Models.ImageMainModel;
import com.bharatarmy.Models.LogginModel;
import com.bharatarmy.Models.MultiSelectModel;
import com.bharatarmy.MultiSportsSelectDialog;
import com.bharatarmy.R;
import com.bharatarmy.Utility.ApiHandler;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.NetworkClient;
import com.bharatarmy.Utility.ProgressRequestBody;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.Utility.WebServices;
import com.bharatarmy.databinding.ActivitySportsInterestBinding;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

import static androidx.core.content.FileProvider.getUriForFile;

public class SportsInterestActivity extends AppCompatActivity implements View.OnClickListener, ProgressRequestBody.UploadCallbacks {
    private static final String TAG = SportsInterestActivity.class.getSimpleName();
    ActivitySportsInterestBinding activitySportsInterestBinding;
    Context mContext;
    public static final int REQUEST_IMAGE = 100;
    //    Uri uri;
    File file = null;
    MultiSportsSelectDialog sportsSelectDialog;
    String nameStr, genderStr = "0", emailStr, phonenoStr, sportsStr,
            countryCodeStr, countryISOcodeStr = "", sportsIdStr = "",
            appIdStr, schoolnameStr = "", usertypeStr = "1",
            entryTypeStr = "", addedemailStr = "", addednameStr = "",
            filePath,fileName;//,fileName;
    ProgressDialog mDialog;
    List<ImageDetailModel> sportsList;
    ArrayList<Integer> alreadySelectedSports;
    ArrayList<String> schoolNamearray;

    File Camerafile;
    private static final int CUSTOM_REQUEST_CODE = 532;
    public static final int RC_PHOTO_PICKER_PERM = 123;
    private int MAX_ATTACHMENT_COUNT =1 ;
    private ArrayList<String> photoPaths = new ArrayList<>();
    public ArrayList<String> cameraphotopaths=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySportsInterestBinding = DataBindingUtil.setContentView(this, R.layout.activity_sports_interest);

        mContext = SportsInterestActivity.this;

        init();
        setLisitner();
    }

    public void init() {
        activitySportsInterestBinding.toolbarTitleTxt.setText("SFA Data Entry");
        addedemailStr = Utils.getAppUserEmail(mContext);
        addednameStr = Utils.getAppUserName(mContext);
        if (Utils.getPref(mContext, "entryType") != null) {
            entryTypeStr = Utils.getPref(mContext, "entryType");
        }
        if (entryTypeStr.equalsIgnoreCase("1")) {
            activitySportsInterestBinding.toolbarTitleTxt.setText("SFA Data Entry");
        } else {
            activitySportsInterestBinding.toolbarTitleTxt.setText("Certification");
        }

        callSchoolNameData();


        callSportsDetailData();
    }

    public void setLisitner() {
        activitySportsInterestBinding.sportsEdt.setOnClickListener(this);
        activitySportsInterestBinding.imageuploadImage.setOnClickListener(this);
        activitySportsInterestBinding.sportsSelectionImg.setOnClickListener(this);
        activitySportsInterestBinding.saveBtn.setOnClickListener(this);
        activitySportsInterestBinding.backImg.setOnClickListener(this);
        activitySportsInterestBinding.logoutImg.setOnClickListener(this);

        activitySportsInterestBinding.genderRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.male_rb) {
                    genderStr = "1";
                } else if (checkedId == R.id.female_rb) {
                    genderStr = "2";
                }
            }
        });

        activitySportsInterestBinding.userTypeRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.student_rb) {
                    usertypeStr = "1";
                } else if (checkedId == R.id.coach_rb) {
                    usertypeStr = "2";
                }
            }
        });

        activitySportsInterestBinding.ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected(Country selectedCountry) {

                AppConfiguration.currentCountryISOCode = activitySportsInterestBinding.ccp.getSelectedCountryNameCode();

            }
        });

        activitySportsInterestBinding.phoneNoEdt.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    Utils.hideKeyboard(SportsInterestActivity.this);
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageupload_image:
                Utils.handleClickEvent(mContext, activitySportsInterestBinding.imageuploadImage);


                Dexter.withActivity(SportsInterestActivity.this)
                        .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport report) {
                                if (report.areAllPermissionsGranted()) {
//                                    showImagePickerOptions();
                                    pickPhotoClicked();
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
            case R.id.sports_edt:
//                Utils.ping(mContext,"hello");
                openSportsSelectionDialog();

                break;
            case R.id.sports_selection_img:
                openSportsSelectionDialog();
                break;
            case R.id.save_btn:
                getSubmitData();
                break;
            case R.id.back_img:
                Intent userListIntent = new Intent(mContext, DisplaySFAUserActivity.class);
                userListIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(userListIntent);
                finish();
                break;

        }
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
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

    //  for pic photo and click photo
    private void showImagePickerOptions() {
        ImageEditProfilePickerActivity.showImagePickerOptions(this, new ImageEditProfilePickerActivity.PickerOptionListener() {
            @Override
            public void onTakeCameraSelected() {
                openImageCapture();
            }

            @Override
            public void onChooseGallerySelected() {
                pickPhotoClicked();
            }
        });
    }
    private void openImageCapture() {
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
//                            fileName = System.currentTimeMillis() + ".jpg";
//                            fileName = String.format("Profile-Images-%d.jpg", System.currentTimeMillis());
                            fileName ="IMG_"+Utils.imagesaveDate()+"_BA"+Utils.imagesavetime()+".jpg";
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
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "BharatArmy");
        if (!file.exists()) file.mkdirs();
        File image = new File(file, fileName);

        return getUriForFile(SportsInterestActivity.this, getPackageName() + ".provider", image);
    }
    private void getCameraImagePath(String fileName) {
//        File path = new File(getExternalCacheDir(), "camera");
        File path = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "BharatArmy");
        Camerafile = path;
        File image = new File(path, fileName);

        Log.d("imagegetFile : ", "" + image);
        String imageUrl = String.valueOf(image);
        cameraphotopaths = new ArrayList<>();
        cameraphotopaths.addAll(Collections.singleton(imageUrl));

        addToView(cameraphotopaths);

    }





//    choose from gallery
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
        FilePickerBuilder.getInstance()
                .setMaxCount(MAX_ATTACHMENT_COUNT)
                .setSelectedFiles(photoPaths)
                .setActivityTheme(R.style.FilePickerTheme)
                .setActivityTitle("")
                .enableVideoPicker(false)
                .enableCameraSupport(true)
                .showGifs(false)
                .showFolderView(true)
                .enableSelectAll(false)
                .enableImagePicker(true)
                .withOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .pickPhoto(this, CUSTOM_REQUEST_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_IMAGE){
                getCameraImagePath(fileName);
            } else if (requestCode == CUSTOM_REQUEST_CODE) {
                photoPaths = new ArrayList<>();
                photoPaths.addAll(data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_MEDIA));

                filePath = photoPaths.get(0);
                addToView(photoPaths);
            }

        } else {
            Log.e("tg", "resultCode = " + resultCode + " data " + data);
        }
    }

    public void addToView(ArrayList<String> imagePaths) {
        Log.d("image pSth :=", imagePaths.get(0));
        Utils.setGalleryImageInImageView(imagePaths.get(0), activitySportsInterestBinding.imageuploadImage, mContext);
    }


    public void openSportsSelectionDialog() {
        alreadySelectedSports = new ArrayList<>();
        if (sportsList != null && sportsList.size() > 0) {
            if (!sportsIdStr.equalsIgnoreCase("")) {
                if (sportsIdStr.contains(",")) {
                    String[] spiltvalue = sportsIdStr.split(",");
                    for (int i = 0; i < spiltvalue.length; i++) {
                        alreadySelectedSports.add(Integer.valueOf(spiltvalue[i]));
                    }
                } else {
                    alreadySelectedSports.add(Integer.valueOf(sportsIdStr));
                }

            }
            //MultiSelectModel
            sportsSelectDialog = new MultiSportsSelectDialog()
                    .title("Sports") //setting title for dialog
                    .titleSize(16)
                    .positiveText("Done")
                    .negativeText("Cancel")
                    .setMinSelectionLimit(0)
                    .setMaxSelectionLimit(sportsList.size())
                    .preSelectIDsList(alreadySelectedSports) //List of ids that you need to be selected
                    .multiSelectList((ArrayList<ImageDetailModel>) sportsList) // the multi select model list with ids and name
                    .onSubmit(new MultiSportsSelectDialog.SubmitCallbackListener() {
                        @Override
                        public void onSelected(ArrayList<Integer> selectedIds, ArrayList<String> selectedNames, String dataString, String dataIdString) {
                            //will return list of selected IDS

                            Log.d("string :", dataString);
                            if (!dataString.equalsIgnoreCase("")) {
                                sportsIdStr = dataIdString.trim();
                                activitySportsInterestBinding.sportsEdt.setText(dataString);
                            } else {
                                sportsIdStr = "";
                                activitySportsInterestBinding.sportsEdt.setText("");
                                activitySportsInterestBinding.sportsEdt.setHint(getResources().getString(R.string.select));
                            }


                        }

                        @Override
                        public void onCancel() {
                            Log.d(TAG, "Dialog cancelled");

                        }
                    });
            sportsSelectDialog.show(getSupportFragmentManager(), "sportsmultiselectdialog");
        } else {
            activitySportsInterestBinding.sportsEdt.setText("Select");
        }
    }


    public void getSubmitData() {
        nameStr = activitySportsInterestBinding.nameEdt.getText().toString();
        countryCodeStr = activitySportsInterestBinding.ccp.getSelectedCountryCode();
        phonenoStr = activitySportsInterestBinding.phoneNoEdt.getText().toString();
        emailStr = activitySportsInterestBinding.emailEdt.getText().toString();
        sportsStr = activitySportsInterestBinding.sportsEdt.getText().toString();
        countryISOcodeStr = AppConfiguration.currentCountryISOCode;
        schoolnameStr = activitySportsInterestBinding.schoolNameTxt.getText().toString();


        Log.d("DataValue", "Name :" + nameStr + "countrycode:" + countryCodeStr
                + "phone number:" + phonenoStr + "gender:" + genderStr + "sportsId:" + sportsIdStr
                + "email:" + emailStr + "CountryNAmeCode: " + AppConfiguration.currentCountryISOCode
                + "schoolname:" + schoolnameStr + "userType:" + usertypeStr + "addedemail: " + addedemailStr
                + "addedname: " + addednameStr + "entrytype:" + entryTypeStr);

        if (!emailStr.equalsIgnoreCase("") && !phonenoStr.equalsIgnoreCase("")) {
            if (!nameStr.equalsIgnoreCase("")) {
                if (!emailStr.equalsIgnoreCase("")) {
                    if (Utils.isValidEmailId(emailStr)) {
                        if (countryCodeStr.length() > 0) {
                            if (phonenoStr.length() > 0) {
                                if (Utils.isValidPhoneNumber(phonenoStr)) {
                                    callsubmitIntrestData();
                                } else {
                                    activitySportsInterestBinding.phoneNoEdt.setError("Invalid Phone Number");
                                }
                            } else {
                                activitySportsInterestBinding.phoneNoEdt.setError("Phone Number is required");
                            }
                        } else {
                            Utils.ping(mContext, "Country Code is required");
                        }
                    } else {
                        activitySportsInterestBinding.emailEdt.setError("Invalid Email Address");
                    }
                } else {
                    activitySportsInterestBinding.emailEdt.setError("Email Address is required");
                }
            } else {
                activitySportsInterestBinding.nameEdt.setError("Name is required");
            }
        } else if (emailStr.equalsIgnoreCase("") && phonenoStr.equalsIgnoreCase("")) {
            countryCodeStr = "";
            countryISOcodeStr = "";
            if (!nameStr.equalsIgnoreCase("")) {
                callsubmitIntrestData();
            } else {
                activitySportsInterestBinding.nameEdt.setError("Name is required");
            }
        } else if (emailStr.equalsIgnoreCase("") && !phonenoStr.equalsIgnoreCase("")) {
            if (!nameStr.equalsIgnoreCase("")) {
                if (countryCodeStr.length() > 0) {
                    if (phonenoStr.length() > 0) {
                        if (Utils.isValidPhoneNumber(phonenoStr)) {

                            callsubmitIntrestData();
                        } else {
                            activitySportsInterestBinding.phoneNoEdt.setError("Invalid Phone Number");
                        }
                    } else {
                        activitySportsInterestBinding.phoneNoEdt.setError("Phone Number is required");
                    }
                } else {
                    Utils.ping(mContext, "Country Code is required");
                }
            } else {
                activitySportsInterestBinding.nameEdt.setError("Name is required");
            }
        } else if (!emailStr.equalsIgnoreCase("") && phonenoStr.equalsIgnoreCase("")) {
            countryCodeStr = "";
            countryISOcodeStr = "";
            if (!nameStr.equalsIgnoreCase("")) {
                if (!emailStr.equalsIgnoreCase("")) {
                    if (Utils.isValidEmailId(emailStr)) {
                        callsubmitIntrestData();
                    } else {
                        activitySportsInterestBinding.emailEdt.setError("Invalid Email Address");
                    }
                } else {
                    activitySportsInterestBinding.emailEdt.setError("Email Address is required");
                }
            } else {
                activitySportsInterestBinding.nameEdt.setError("Name is required");
            }
        }


    }

    /*use callsubmitIntrestData */
    public void callsubmitIntrestData() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), SportsInterestActivity.this);
            return;
        }
        MultipartBody.Part body = null;
        if (filePath != null && filePath.toString().length() > 0) {

//            String filePath = Utils.getFilePathFromUri(mContext, uri);

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

        appIdStr = String.valueOf(Utils.getAppUserId(mContext));
        RequestBody appId = RequestBody.create(MediaType.parse("text/plain"), appIdStr);
        RequestBody fullname = RequestBody.create(MediaType.parse("text/plain"), nameStr);
        RequestBody countryISOCode = RequestBody.create(MediaType.parse("text/plain"), countryISOcodeStr);
        RequestBody countycode = RequestBody.create(MediaType.parse("text/plain"), countryCodeStr);
        RequestBody phoneno = RequestBody.create(MediaType.parse("text/plain"), phonenoStr);
        RequestBody gender = RequestBody.create(MediaType.parse("text/plain"), genderStr);
        RequestBody email = RequestBody.create(MediaType.parse("text/plain"), emailStr);
        RequestBody sports = RequestBody.create(MediaType.parse("text/plain"), sportsIdStr);
        RequestBody schoolname = RequestBody.create(MediaType.parse("text/plain"), schoolnameStr);
        RequestBody usertype = RequestBody.create(MediaType.parse("text/plain"), usertypeStr);
        RequestBody entryType = RequestBody.create(MediaType.parse("text/plain"), entryTypeStr);
        RequestBody addedbyemail = RequestBody.create(MediaType.parse("text/plain"), addedemailStr);
        RequestBody addedbyname = RequestBody.create(MediaType.parse("text/plain"), addednameStr);


//        ShowProgressDialog();
        Call<LogginModel> responseBodyCall = uploadAPIs.insertData(appId, fullname, countryISOCode,
                countycode, phoneno, gender, email, sports, schoolname, usertype, entryType, addedbyemail, addedbyname, body);
        Log.d("File", "" + responseBodyCall);
        responseBodyCall.enqueue(new Callback<LogginModel>() {

            @Override
            public void onResponse(Call<LogginModel> call, retrofit2.Response<LogginModel> response) {
                if (filePath != null && filePath.toString().length() > 0) {
                    mDialog.dismiss();
                } else {
                    Utils.dismissDialog();
                }
                if (response.body().getIsValid() == 1) {
//                    showThanyouDialog();
                    Utils.ping(mContext, "Added Successfully");
                    activitySportsInterestBinding.imageuploadImage.setImageResource(R.drawable.proflie);
                    activitySportsInterestBinding.nameEdt.setText("");
                    activitySportsInterestBinding.emailEdt.setText("");
                    activitySportsInterestBinding.phoneNoEdt.setText("");
                    activitySportsInterestBinding.sportsEdt.setText("");
                    activitySportsInterestBinding.schoolNameTxt.setText("");
                    activitySportsInterestBinding.genderRg.clearCheck();
                    activitySportsInterestBinding.studentRb.setChecked(true);
                    usertypeStr = "1";
                    nameStr = "";
                    genderStr = "0";
                    emailStr = "";
                    phonenoStr = "";
                    sportsStr = "";
                    countryCodeStr = "";
                    sportsIdStr = "";
                    appIdStr = "";
                    schoolnameStr = "";

                } else {
                    Utils.ping(mContext, response.body().getMessage());
                }


            }

            @Override
            public void onFailure(Call<LogginModel> call, Throwable t) {
                Log.d("failure", "message = " + t.getMessage());
                Log.d("failure", "cause = " + t.getCause());
                if (filePath != null) {
                    mDialog.dismiss();
                } else {
                    Utils.dismissDialog();
                }
            }
        });


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


    // Api calling GetSportsDetailData
    public void callSportsDetailData() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), SportsInterestActivity.this);
            return;
        }

//        Utils.showDialog(mContext);

        ApiHandler.getApiService().getSportsList(getSportsDetailData(), new retrofit.Callback<ImageMainModel>() {
            @Override
            public void success(ImageMainModel sportsMainModel, Response response) {
                Utils.dismissDialog();
                if (sportsMainModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (sportsMainModel.getIsValid() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (sportsMainModel.getIsValid() == 0) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    return;
                }
                if (sportsMainModel.getIsValid() == 1) {

                    if (sportsMainModel.getData() != null) {
                        sportsList = sportsMainModel.getData();
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

    private Map<String, String> getSportsDetailData() {
        Map<String, String> map = new HashMap<>();
        return map;
    }


    @Override
    public void onBackPressed() {
        Intent userListIntent = new Intent(mContext, DisplaySFAUserActivity.class);
        userListIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(userListIntent);
        finish();
    }

    //    Calling Get the schoolName
    public void callSchoolNameData() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), SportsInterestActivity.this);
            return;
        }

//        Utils.showDialog(mContext);
        ApiHandler.getApiService().getSchoolNameFromEntry(getSchoolData(), new retrofit.Callback<GetSchoolNameModel>() {
            @Override
            public void success(GetSchoolNameModel schoolNameModel, Response response) {
                Utils.dismissDialog();

                if (schoolNameModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (schoolNameModel.getIsValid() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (schoolNameModel.getIsValid() == 0) {
                    Utils.ping(mContext, mContext.getString(R.string.false_msg));
                    return;
                }
                if (schoolNameModel.getIsValid() == 1) {
                    if (schoolNameModel.getData() != null && schoolNameModel.getData().size() > 0) {
                        schoolNamearray = new ArrayList<>();

                        for (int i = 0; i < schoolNameModel.getData().size(); i++) {
                            schoolNamearray.add(schoolNameModel.getData().get(i));
                        }
                        fillSchoolData();
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

    private Map<String, String> getSchoolData() {
        Map<String, String> map = new HashMap<>();
        return map;
    }

    public void fillSchoolData() {

        ArrayAdapter adapter = new
                ArrayAdapter(this, android.R.layout.simple_list_item_1, schoolNamearray);

        activitySportsInterestBinding.schoolNameTxt.setThreshold(1);
        activitySportsInterestBinding.schoolNameTxt.setAdapter(adapter);
    }
}
