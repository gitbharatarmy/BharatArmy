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
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
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

public class SportsInterestActivity extends AppCompatActivity implements View.OnClickListener, ProgressRequestBody.UploadCallbacks {
    private static final String TAG = SportsInterestActivity.class.getSimpleName();
    ActivitySportsInterestBinding activitySportsInterestBinding;
    Context mContext;
    public static final int REQUEST_IMAGE = 100;
    Uri uri;
    File file = null;
    MultiSportsSelectDialog sportsSelectDialog;
    String nameStr, genderStr = "0", emailStr, phonenoStr, sportsStr,
            countryCodeStr,countryISOcodeStr="", sportsIdStr = "", appIdStr,schoolnameStr="",usertypeStr="1";
    ProgressDialog mDialog;
    List<ImageDetailModel> sportsList;
    ArrayList<Integer> alreadySelectedSports;
    ArrayList<String> schoolNamearray;
    ArrayList<GetSchoolNameModel> schoolNameList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySportsInterestBinding = DataBindingUtil.setContentView(this, R.layout.activity_sports_interest);

        mContext = SportsInterestActivity.this;

        ImageEditProfilePickerActivity.clearCache(this);


        init();
        setLisitner();
    }

    public void init() {
        activitySportsInterestBinding.toolbarTitleTxt.setText("SFA Data Entry");

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
                    usertypeStr="1";
                }else if (checkedId==R.id.coach_rb){
                    usertypeStr="2";
                }
            }
        });

        activitySportsInterestBinding.ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected(Country selectedCountry) {

                AppConfiguration.currentCountry = activitySportsInterestBinding.ccp.getSelectedCountryNameCode();

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
                Dexter.withActivity(SportsInterestActivity.this)
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
                Intent userListIntent=new Intent(mContext,DisplaySFAUserActivity.class);
                userListIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(userListIntent);
                finish();
                break;

        }
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
        Intent intent = new Intent(SportsInterestActivity.this, ImageEditProfilePickerActivity.class);
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
        Intent intent = new Intent(SportsInterestActivity.this, ImageEditProfilePickerActivity.class);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(SportsInterestActivity.this);
        builder.setTitle(getString(R.string.dialog_permission_title));
        builder.setMessage(getString(R.string.dialog_permission_message));
        builder.setPositiveButton(getString(R.string.go_to_settings), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                SportsInterestActivity.this.openSettings();
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
        Utils.setImageInImageView(url, activitySportsInterestBinding.imageuploadImage, mContext);
//        Picasso.with(mContext).load(url).into(activityEditProfileBinding.profileImage);
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
        countryISOcodeStr=AppConfiguration.currentCountry;
        schoolnameStr=activitySportsInterestBinding.schoolNameTxt.getText().toString();

        Log.d("DataValue", "Name :" + nameStr + "countrycode:" + countryCodeStr
                + "phone number:" + phonenoStr + "gender:" + genderStr + "sportsId:" + sportsIdStr
                + "email:" + emailStr + "CountryNAmeCode: " + AppConfiguration.currentCountry
                + "schoolname:" +schoolnameStr + "userType:"+usertypeStr);

        if (!emailStr.equalsIgnoreCase("") && !phonenoStr.equalsIgnoreCase("")) {
            if (!nameStr.equalsIgnoreCase("")) {
                if (!emailStr.equalsIgnoreCase("")) {
                    if (Utils.isValidEmailId(emailStr)) {
                        if (countryCodeStr.length() > 0) {
                            if (phonenoStr.length() > 0) {
                                if (Utils.isValidPhoneNumber(phonenoStr)) {
//                                    boolean status = Utils.validateUsing_libphonenumber(mContext, countryCodeStr, phonenoStr);
//                                    if (status) {
                                        callsubmitIntrestData();
//                                    } else {
//                                        activitySportsInterestBinding.phoneNoEdt.setError("Invalid Phone Number");
//                                    }
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
            countryCodeStr="";
            countryISOcodeStr="";
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
//                            boolean status = Utils.validateUsing_libphonenumber(mContext, countryCodeStr, phonenoStr);
//                            if (status) {
                                callsubmitIntrestData();
//                            } else {
//                                activitySportsInterestBinding.phoneNoEdt.setError("Invalid Phone Number");
//                            }
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
            countryCodeStr="";
            countryISOcodeStr="";
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
        if (uri != null && uri.toString().length() > 0) {

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

        appIdStr = String.valueOf(Utils.getAppUserId(mContext));
        RequestBody appId = RequestBody.create(MediaType.parse("text/plain"), appIdStr);
        RequestBody fullname = RequestBody.create(MediaType.parse("text/plain"), nameStr);
        RequestBody countryISOCode = RequestBody.create(MediaType.parse("text/plain"),countryISOcodeStr);
        RequestBody countycode = RequestBody.create(MediaType.parse("text/plain"), countryCodeStr);
        RequestBody phoneno = RequestBody.create(MediaType.parse("text/plain"), phonenoStr);
        RequestBody gender = RequestBody.create(MediaType.parse("text/plain"), genderStr);
        RequestBody email = RequestBody.create(MediaType.parse("text/plain"), emailStr);
        RequestBody sports = RequestBody.create(MediaType.parse("text/plaim"), sportsIdStr);
        RequestBody schoolname = RequestBody.create(MediaType.parse("text/plain"), schoolnameStr);
        RequestBody usertype = RequestBody.create(MediaType.parse("text/plaim"), usertypeStr);

//        ShowProgressDialog();
        Call<LogginModel> responseBodyCall = uploadAPIs.insertData(appId, fullname, countryISOCode,
                countycode, phoneno, gender, email, sports,schoolname,usertype, body);
        Log.d("File", "" + responseBodyCall);
        responseBodyCall.enqueue(new Callback<LogginModel>() {

            @Override
            public void onResponse(Call<LogginModel> call, retrofit2.Response<LogginModel> response) {
                if (uri != null && uri.toString().length() > 0) {
                    mDialog.dismiss();
                } else {
                    Utils.dismissDialog();
                }
                if (response.body().getIsValid() == 1) {
//                    showThanyouDialog();
                    Utils.ping(mContext,"Added Successfully");
                    activitySportsInterestBinding.imageuploadImage.setImageResource(R.drawable.proflie);
                    activitySportsInterestBinding.nameEdt.setText("");
                    activitySportsInterestBinding.emailEdt.setText("");
                    activitySportsInterestBinding.phoneNoEdt.setText("");
                    activitySportsInterestBinding.sportsEdt.setText("");
                    activitySportsInterestBinding.schoolNameTxt.setText("");
                    activitySportsInterestBinding.genderRg.clearCheck();
                    activitySportsInterestBinding.studentRb.setChecked(true);
                    usertypeStr="1";
                    nameStr = "";
                    genderStr = "0";
                    emailStr = "";
                    phonenoStr = "";
                    sportsStr = "";
                    countryCodeStr = "";
                    sportsIdStr = "";
                    appIdStr = "";
                    schoolnameStr="";

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

    public void showThanyouDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.thankyou_dialog_sports_item, null);
        dialogBuilder.setView(dialogView);
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        TextView dialog_headertxt = (TextView) dialogView.findViewById(R.id.dialog_headertxt);
        TextView dialog_descriptiontxt = (TextView) dialogView.findViewById(R.id.dialog_descriptiontxt);
        TextView yestxt = (TextView) dialogView.findViewById(R.id.yes_txt);
        TextView notxt = (TextView) dialogView.findViewById(R.id.no_txt);

        dialog_headertxt.setText("Thank you for register your interest");
        dialog_descriptiontxt.setText(getResources().getString(R.string.register_info));


        yestxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uri = Uri.parse("");
                Log.d("retrun uri", uri.toString());
                activitySportsInterestBinding.imageuploadImage.setImageResource(R.drawable.proflie);
                activitySportsInterestBinding.nameEdt.setText("");
                activitySportsInterestBinding.emailEdt.setText("");
                activitySportsInterestBinding.phoneNoEdt.setText("");
                activitySportsInterestBinding.sportsEdt.setText("");
                activitySportsInterestBinding.schoolNameTxt.setText("");
                activitySportsInterestBinding.genderRg.clearCheck();
                activitySportsInterestBinding.studentRb.setChecked(true);
                usertypeStr="1";
                nameStr = "";
                genderStr = "0";
                emailStr = "";
                phonenoStr = "";
                sportsStr = "";
                countryCodeStr = "";
                sportsIdStr = "";
                appIdStr = "";
                schoolnameStr="";

                alertDialog.dismiss();

                if (Utils.retriveLoginData(mContext) != null) {
                    if (Utils.retriveLoginData(mContext).getMemberType().equalsIgnoreCase(",3,")) {
                        finish();
                    } else {
                        finish();
                    }
                }
            }
        });
        notxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    alertDialog.dismiss();
                    if (Utils.retriveLoginData(mContext) != null) {
                        Intent userListIntent=new Intent(mContext,DisplaySFAUserActivity.class);
                        userListIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(userListIntent);
                        finish();
                    }
                } catch (Exception e) {

                }
            }
        });
        try {
            alertDialog.show();
        } catch (Exception e) {

        }

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
        Intent userListIntent=new Intent(mContext,DisplaySFAUserActivity.class);
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
                    if (schoolNameModel.getData()!=null && schoolNameModel.getData().size()>0){
                        schoolNamearray=new ArrayList<>();

                        for (int i=0;i<schoolNameModel.getData().size();i++){
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
                ArrayAdapter(this,android.R.layout.simple_list_item_1,schoolNamearray);

        activitySportsInterestBinding.schoolNameTxt.setThreshold(1);
        activitySportsInterestBinding.schoolNameTxt.setAdapter(adapter);
    }
}
