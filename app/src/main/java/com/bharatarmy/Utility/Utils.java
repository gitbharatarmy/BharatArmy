package com.bharatarmy.Utility;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

import com.bharatarmy.Activity.AppLoginActivity;
import com.bharatarmy.Activity.DashboardActivity;
import com.bharatarmy.Country;
import com.bharatarmy.CountryCodePicker;
import com.bharatarmy.Interfaces.submit_click;
import com.bharatarmy.Models.GalleryImageModel;
import com.bharatarmy.Models.ImageMainModel;
import com.bharatarmy.Models.LoginDataModel;
import com.bharatarmy.Models.LoginOtherDataModel;
import com.bharatarmy.R;
import com.bharatarmy.UploadService;
import com.bharatarmy.meghWebView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;


import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.yalantis.ucrop.util.FileUtils.getDataColumn;
import static com.yalantis.ucrop.util.FileUtils.isDownloadsDocument;
import static com.yalantis.ucrop.util.FileUtils.isExternalStorageDocument;
import static com.yalantis.ucrop.util.FileUtils.isGooglePhotosUri;
import static com.yalantis.ucrop.util.FileUtils.isMediaDocument;

public class Utils {
    public static String strCountrycode, strCheck = "0";
    public static meghWebView webView;
    public static ImageView image;
    public static Button agree_btn;
    public static TextView close_btn;
    public static boolean isValid = false;

    public static String LikeMemberId, LikeReferenceId, LikeStatus, LikeSourceType;
    public static String viewsMemberId, viewsTokenId, viewsReferenceId, viewsSourceType;
    public static Dialog dialog;

    public static final String MyPREFERENCES = "MyPrefs";
    public static SharedPreferences sharedpreferences;

    public static List<GalleryImageModel> UpladingFiles;
    public static ArrayList<String> videoFile;


    public static boolean checkNetwork(Context context) {
        boolean wifiAvailable = false;
        boolean mobileAvailable = false;
        ConnectivityManager conManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInfo = conManager.getAllNetworkInfo();
        for (NetworkInfo netInfo : networkInfo) {
            if (netInfo.getTypeName().equalsIgnoreCase("WIFI")) {
                if (netInfo.isConnected())
                    wifiAvailable = true;
            } else if (netInfo.getTypeName().equalsIgnoreCase("MOBILE")) {
                if (netInfo.isConnected())
                    mobileAvailable = true;
            }
        }
        return wifiAvailable || mobileAvailable;
    }

    public static void showCustomDialog(String title, String str, final Activity activity) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_simple_dailog_ok);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);


        Button tryagain = (Button) dialog.findViewById(R.id.try_again_btn);

        if (checkNetwork(activity)) {
            dialog.dismiss();
            activity.recreate();
        } else {
            tryagain.performClick();
            Utils.ping(activity, "Internet connection not available");
            dialog.show();
        }

        tryagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkNetwork(activity)) {
                    dialog.dismiss();
                    activity.recreate();
                } else {
                    Utils.ping(activity, "Internet connection not available");
                    dialog.show();
                }
            }
        });
        dialog.show();
    }

    public static void showDialog(Context context) {
        ImageView imageView;

        if (dialog == null) {
            dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.progressbar_dialog);
            imageView = (ImageView) dialog.findViewById(R.id.image);
            Glide.with(context).load(R.drawable.logo_white_new).into(imageView);
            dialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT,
                    WindowManager.LayoutParams.FILL_PARENT);
            Drawable d = new ColorDrawable(Color.BLACK);
            d.setAlpha(30);
            dialog.getWindow().setBackgroundDrawable(d);
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }

    }

    public static void hideKeyboard(Activity context) {
        ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow((null == context.getCurrentFocus()) ? null : context.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static void dismissDialog() {
        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();

                dialog = null;
            }


        }
    }

    public static void ping(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void pong(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void setPref(Context context, String key, String value) {
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getPref(Context context, String key) {
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String value = sharedpreferences.getString(key, "");
        return value;
    }

    public static void setIntPref(Context context, String key, Integer value) {
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static Integer getIntPref(Context context, String key) {
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        int value = sharedpreferences.getInt(key, 0);
        return value;
    }

    public static boolean isValidEmailId(String email) {

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getFilePathFromUri(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    // use for get current company
    public static String getUserCountry(Context context) {
        try {
            final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            final String simCountry = tm.getSimCountryIso();
            if (simCountry != null && simCountry.length() == 2) { // SIM country code is available
                return simCountry.toUpperCase(Locale.US);
            } else if (tm.getPhoneType() != TelephonyManager.PHONE_TYPE_CDMA) { // device is not 3G (would be unreliable)
                String networkCountry = tm.getNetworkCountryIso();
                if (networkCountry != null && networkCountry.length() == 2) { // network country code is available
                    return networkCountry.toUpperCase(Locale.US);
                }
            }
        } catch (Exception e) {
        }
        return null;
    }

    public static Bitmap StringToBitMap(String encodedString) {
        try {
            URL url = new URL(encodedString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }


    public static boolean isValidPhoneNumber(CharSequence phoneNumber) {
        if (!TextUtils.isEmpty(phoneNumber)) {
            return Patterns.PHONE.matcher(phoneNumber).matches();
        }
        return false;
    }

    public static boolean validateUsing_libphonenumber(Context context, String countryCode, String phNumber) {
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        String isoCode = phoneNumberUtil.getRegionCodeForCountryCode(Integer.parseInt(countryCode));
        Phonenumber.PhoneNumber phoneNumber = null;
        try {
            //phoneNumber = phoneNumberUtil.parse(phNumber, "IN");  //if you want to pass region code
            phoneNumber = phoneNumberUtil.parse(phNumber, isoCode);
        } catch (NumberParseException e) {
            System.err.println(e);
        }

        boolean isValid = phoneNumberUtil.isValidNumber(phoneNumber);
        if (isValid) {
            String internationalFormat = phoneNumberUtil.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.NATIONAL);
            Toast.makeText(context, "Phone Number is Valid " + internationalFormat, Toast.LENGTH_LONG).show();
            return true;
        } else {
            Toast.makeText(context, "Phone Number is Invalid " + phoneNumber, Toast.LENGTH_LONG).show();
            return false;
        }
    }

    public static Bitmap DrawableToBitMap(int first_match_map, Context mContext) {
        Drawable myDrawable = mContext.getResources().getDrawable(first_match_map);
        Bitmap anImage = ((BitmapDrawable) myDrawable).getBitmap();
        return anImage;
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }

    public static boolean appInstalledOrNot(String uri, Context mContext) {
        PackageManager pm = mContext.getPackageManager();
        boolean app_installed;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

    public static void setImageInImageView(String imageUrl, ImageView view, Context mContext) {
        Picasso.with(mContext)
                .load(imageUrl)
                .placeholder(R.drawable.loader_new)
                .into(view);

    }

    public static void setGalleryImageInImageView(String imageUrl, ImageView view, Context mContext) {
        Glide.with(mContext)
                .load(new File(imageUrl))
                .apply(RequestOptions.centerCropTransform()
                        .dontAnimate()
                        .placeholder(R.drawable.image_placeholder))
                .thumbnail(0.5f)
                .into(view);

    }

    public static boolean isBackGroundWorkAllowed(Context context) {
        int storagePermissionGranted = ContextCompat.checkSelfPermission(context,
                Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
        return storagePermissionGranted == PackageManager.PERMISSION_GRANTED;
    }

    @SuppressLint("NewApi")
    public static void checkPermission(Activity activity, String permissionString, int permissionCode) {
        if ((android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.M) || activity.getApplicationContext() == null)
            return;
        int existingPermissionStatus = ContextCompat.checkSelfPermission(activity.getApplicationContext(),
                permissionString);
        if (existingPermissionStatus == PackageManager.PERMISSION_GRANTED) return;
        activity.requestPermissions(new String[]{permissionString}, permissionCode);
    }

    public static int dpToPx(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }


    public static void showUpdateDialog(final Activity activity) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.update_application_dialog_item);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);


        Button update = (Button) dialog.findViewById(R.id.update_btn);
        TextView notnow_txt = (TextView) dialog.findViewById(R.id.notnow_txt);
        ImageView updateapp_img = (ImageView) dialog.findViewById(R.id.updateapp_img);

        Glide.with(activity)
                .load(R.drawable.logo_new)
                .into(updateapp_img);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        notnow_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public static void showThanyouDialog(final Activity activity, String wheretocome) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.thankyou_dialog_item, null);
        dialogBuilder.setView(dialogView);
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        TextView dialog_headertxt = (TextView) dialogView.findViewById(R.id.dialog_headertxt);
        TextView dialog_descriptiontxt = (TextView) dialogView.findViewById(R.id.dialog_descriptiontxt);
        TextView hometxt = (TextView) dialogView.findViewById(R.id.home_txt);

        if (wheretocome.equalsIgnoreCase("sports")) {
            dialog_headertxt.setText("Thank you for register your interest");
            dialog_descriptiontxt.setText(activity.getResources().getString(R.string.register_info));

            dialog_descriptiontxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                    activity.recreate();
                }
            });

        } else {
            if (Utils.retriveLoginOtherData(activity) != null) {
                if (wheretocome.equalsIgnoreCase("imageUpload")) {
                    for (int i = 0; i < Utils.retriveLoginOtherData(activity).size(); i++) {
                        if (Utils.retriveLoginOtherData(activity).get(i).getMessageId().equals(1)) {
                            dialog_headertxt.setText(Utils.retriveLoginOtherData(activity).get(i).getMessageHeaderText());
                            dialog_descriptiontxt.setText(Utils.retriveLoginOtherData(activity).get(i).getMessageDescription());
                        }

                    }
                } else if (wheretocome.equalsIgnoreCase("changePassword")) {
                    for (int i = 0; i < Utils.retriveLoginOtherData(activity).size(); i++) {
                        if (Utils.retriveLoginOtherData(activity).get(i).getMessageId().equals(2)) {
                            dialog_headertxt.setText(Utils.retriveLoginOtherData(activity).get(i).getMessageHeaderText());
                            dialog_descriptiontxt.setText(Utils.retriveLoginOtherData(activity).get(i).getMessageDescription());
                        }

                    }
                } else if (wheretocome.equalsIgnoreCase("changePassword|InApp")) {
                    for (int i = 0; i < Utils.retriveLoginOtherData(activity).size(); i++) {
                        if (Utils.retriveLoginOtherData(activity).get(i).getMessageId().equals(2)) {
                            dialog_headertxt.setText(Utils.retriveLoginOtherData(activity).get(i).getMessageHeaderText());
                            dialog_descriptiontxt.setText(Utils.retriveLoginOtherData(activity).get(i).getMessageDescription());
                        }

                    }
                } else if (wheretocome.equalsIgnoreCase("changePassword|finishApp")) {
                    for (int i = 0; i < Utils.retriveLoginOtherData(activity).size(); i++) {
                        if (Utils.retriveLoginOtherData(activity).get(i).getMessageId().equals(2)) {
                            dialog_headertxt.setText(Utils.retriveLoginOtherData(activity).get(i).getMessageHeaderText());
                            dialog_descriptiontxt.setText(Utils.retriveLoginOtherData(activity).get(i).getMessageDescription());
                        }

                    }
                } else if (wheretocome.equalsIgnoreCase("videoUpload")) {
                    for (int i = 0; i < Utils.retriveLoginOtherData(activity).size(); i++) {
                        if (Utils.retriveLoginOtherData(activity).get(i).getMessageId().equals(1)) {
                            dialog_headertxt.setText(Utils.retriveLoginOtherData(activity).get(i).getMessageHeaderText());
                            dialog_descriptiontxt.setText(Utils.retriveLoginOtherData(activity).get(i).getMessageDescription());
                        }
                    }
                }
            } else {
                dialog_headertxt.setText("Thank you");
                dialog_descriptiontxt.setText("");
            }
        }

        hometxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    alertDialog.dismiss();
                    if (wheretocome.equalsIgnoreCase("changePassword")) {
                        Intent intent = new Intent(activity, DashboardActivity.class);
                        activity.startActivity(intent);
                        activity.finish();
                    } else if (wheretocome.equalsIgnoreCase("changePassword|InApp")) {
                        Intent dashboardIntent = new Intent(activity, DashboardActivity.class);
                        dashboardIntent.putExtra("whichPageRun", "4");
                        activity.startActivity(dashboardIntent);
                        activity.finish();
                    } else if (wheretocome.equalsIgnoreCase("changePassword|finishApp")) {
                        activity.finish();
                    } else if (wheretocome.equalsIgnoreCase("imageUpload")) {
                        Intent intent = new Intent(activity, UploadService.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        activity.startService(intent);
                        activity.finish();
                    } else if (wheretocome.equalsIgnoreCase("videoUpload")) {
                        Intent dashboardIntent = new Intent(activity, DashboardActivity.class);
                        dashboardIntent.putExtra("whichPageRun", "1");
                        activity.startActivity(dashboardIntent);
                        activity.finish();
                    } else if (wheretocome.equalsIgnoreCase("sports")) {
                        Intent dashboardIntent = new Intent(activity, DashboardActivity.class);
                        dashboardIntent.putExtra("whichPageRun", "5");
                        activity.startActivity(dashboardIntent);
                        activity.finish();
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


    public static void showSubmitRegisterDialog(final Activity activity, String wheretocome, submit_click submit_click) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.submit_login_dialog_item, null);
        dialogBuilder.setView(dialogView);
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        EditText fulluser_name_edt, email_edt, mobile_edt;
        TextView term_condition_txt;
        CheckBox terms_chk;
        Button signup_btn;
        CountryCodePicker ccp;
        LinearLayout close_linear;

        ccp = (CountryCodePicker) dialogView.findViewById(R.id.ccp);
        fulluser_name_edt = (EditText) dialogView.findViewById(R.id.fulluser_name_edt);
        email_edt = (EditText) dialogView.findViewById(R.id.email_edt);
        mobile_edt = (EditText) dialogView.findViewById(R.id.mobile_edt);

        close_linear = (LinearLayout) dialogView.findViewById(R.id.close_linear);
        term_condition_txt = (TextView) dialogView.findViewById(R.id.term_condition_txt);
        terms_chk = (CheckBox) dialogView.findViewById(R.id.terms_chk);

        signup_btn = (Button) dialogView.findViewById(R.id.signup_btn);


        AppConfiguration.currentCountryISOCode = ccp.getSelectedCountryNameCode();


        terms_chk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    strCheck = "1";
                } else {
                    strCheck = "0";
                }
            }
        });
        term_condition_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater lInflater = (LayoutInflater) activity
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View layout = lInflater.inflate(R.layout.mobile_term_condition, null);

                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(activity);
                alertDialogBuilderUserInput.setView(layout);

                AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                alertDialogAndroid.setCancelable(false);
                alertDialogAndroid.show();
                Window window = alertDialogAndroid.getWindow();
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                WindowManager.LayoutParams wlp = window.getAttributes();
                window.setGravity(Gravity.LEFT | Gravity.TOP);
                wlp.x = 1;
                wlp.y = 100;
                wlp.flags = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
                window.setAttributes(wlp);
                alertDialogAndroid.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                alertDialogAndroid.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.MATCH_PARENT);

                Drawable d = new ColorDrawable(activity.getResources().getColor(R.color.black_dialog));
//        d.setAlpha(100);
                alertDialogAndroid.getWindow().setBackgroundDrawable(d);
                alertDialogAndroid.show();

                webView = (meghWebView) layout.findViewById(R.id.webView);
                image = (ImageView) layout.findViewById(R.id.image);
                agree_btn = (Button) layout.findViewById(R.id.agree_btn);
//        close_btn = (Button) layout.findViewById(R.id.close_btn);
                close_btn = (TextView) layout.findViewById(R.id.close_btn1);
                Glide.with(activity).load(R.drawable.logo_new).into(image);
                image.setVisibility(View.VISIBLE);

                webView.setWebViewClient(new MyWebViewClient());
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl(AppConfiguration.TERMSURL);
                webView.setVerticalScrollBarEnabled(true);
                webView.setOnClickListener(this);

                close_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialogAndroid.dismiss();
                    }
                });
            }
        });

        ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected(Country selectedCountry) {
                AppConfiguration.currentCountryISOCode = ccp.getSelectedCountryNameCode();
            }
        });

        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConfiguration.registerNameStr = fulluser_name_edt.getText().toString();
                AppConfiguration.registerEmailStr = email_edt.getText().toString();
                AppConfiguration.registerCountryCodeStr = ccp.getSelectedCountryCode();
                AppConfiguration.registerMobileStr = mobile_edt.getText().toString();
                AppConfiguration.registerCountryDialcodeStr = AppConfiguration.currentCountryISOCode;
                Log.d("selectedcode", strCountrycode);
                if (!AppConfiguration.registerNameStr.equalsIgnoreCase("")) {
                    if (!AppConfiguration.registerEmailStr.equalsIgnoreCase("")) {
                        if (Utils.isValidEmailId(AppConfiguration.registerEmailStr)) {
                            if (AppConfiguration.registerCountryCodeStr.length() > 0) {
                                if (AppConfiguration.registerMobileStr.length() > 0) {
                                    if (Utils.isValidPhoneNumber(AppConfiguration.registerMobileStr)) {
//                                        boolean status = Utils.validateUsing_libphonenumber(activity, AppConfiguration.registerCountryCodeStr, AppConfiguration.registerMobileStr);
//                                        if (status) {
                                        if (!strCheck.equalsIgnoreCase("0")) {
                                            alertDialog.dismiss();
                                            submit_click.getsubmitClick();
                                        } else {
                                            Utils.ping(activity, "Check the privacy policy");
                                        }
//                                        } else {
//                                            mobile_edt.setError("Invalid Phone Number");
//                                        }
                                    } else {
                                        mobile_edt.setError("Invalid Phone Number");
                                    }
                                } else {
                                    mobile_edt.setError("Phone Number is required");
                                }
                            } else {
                                Utils.ping(activity, "Country Code is required");
                            }
                        } else {
                            email_edt.setError("Invalid Email Address");
                        }
                    } else {
                        email_edt.setError("Email Address is required");
                    }
                } else {
                    fulluser_name_edt.setError("Full Name is required");
                }
            }
        });

        close_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }


    // use for webview adavnce facility funcation
    public static class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            image.setVisibility(View.VISIBLE);
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);
            image.setVisibility(View.GONE);
        }
    }


    public static boolean isMyServiceRunning(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if ("com.bharatarmy.UploadService".equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static Bitmap createVideoThumbNail(String path) {
        return ThumbnailUtils.createVideoThumbnail(path, MediaStore.Images.Thumbnails.FULL_SCREEN_KIND);
    }

    public static Bitmap createImageThumbNail(Bitmap bitmap) {
        return ThumbnailUtils.extractThumbnail(bitmap, 512, 512);
    }

    public static Bitmap getBitmap(String path) {
        Bitmap bitmap = null;
        try {

            File f = new File(path);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;

            bitmap = BitmapFactory.decodeStream(new FileInputStream(f), null, options);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }


    public static Bitmap createThumbnailAtTime(String filePath){
        MediaMetadataRetriever mMMR = new MediaMetadataRetriever();
        mMMR.setDataSource(filePath);
        int timeInSeconds =1;
        //api time unit is microseconds                 *1000000
        return mMMR.getFrameAtTime(timeInSeconds*1000, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
    }

    public static File saveBitmap(Bitmap bitmap, String name, Context mContext) {
        File filesDir = mContext.getFilesDir();
        File imageFile = new File(filesDir, name + ".jpg");

        return imageFile;
    }

    public static Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "videoThumbnail", null);
        return Uri.parse(path);
    }


    public static void handleClickEvent(Context mContext, View view) {
        view.setEnabled(false);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // This method will be executed once the timer is over
                view.setEnabled(true);
                Log.d("clickresult", "resend1");

            }
        }, 10000);
    }


    public static boolean doesTableExist(SQLiteDatabase db, String tableName) {
        Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + tableName + "'", null);

        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }

    public static String getCountryNameUsingCountryCode(String code) {
        Locale l = new Locale("", code);
        String country = l.getDisplayCountry();

        return country;
    }

    public static void storeLoginData(LoginDataModel result, Context mContext) {
        Gson gsonupdate = new Gson();
        String valuesString = gsonupdate.toJson(result);
        Utils.setPref(mContext, "loginData", valuesString);
        Log.d("LoginvaluesString", valuesString);
    }

    public static void storeCurrentLocationData(LoginDataModel result,Context mContext){
        Gson gsonupdate =new Gson();
        String currentLocatiobString =gsonupdate.toJson(result);
        Utils.setPref(mContext,"currentLocationData",currentLocatiobString);
        Log.d("CurrentLocationString",currentLocatiobString);
    }

    public static LoginDataModel retriveCurrentLocationData(Context mContext) {
        LoginDataModel loginList;
        Type arrayListType2 = new TypeToken<LoginDataModel>() {
        }.getType();

        Gson gson2 = new Gson();
        loginList = gson2.fromJson(Utils.getPref(mContext, "currentLocationData"), arrayListType2);
        return loginList;
    }

    public static LoginDataModel retriveLoginData(Context mContext) {
        LoginDataModel loginList;
        Type arrayListType2 = new TypeToken<LoginDataModel>() {
        }.getType();

        Gson gson2 = new Gson();
        loginList = gson2.fromJson(Utils.getPref(mContext, "loginData"), arrayListType2);
        return loginList;
    }

    public static void removeLoginData(Context mContext) {
        Utils.setPref(mContext, "loginData", "");
    }

    public static void storeLoginOtherData(List<LoginOtherDataModel> result, Context mContext) {
        Gson gsonupdate = new Gson();
        String valuesString = gsonupdate.toJson(result);
        Utils.setPref(mContext, "loginOtherData", valuesString);
        Log.d("valuesString", valuesString);
    }

    public static List<LoginOtherDataModel> retriveLoginOtherData(Context mContext) {
        List<LoginOtherDataModel> messageList = new ArrayList<>();
        Type arrayListType2 = new TypeToken<ArrayList<LoginOtherDataModel>>() {
        }.getType();
        Gson gson2 = new Gson();
        messageList = gson2.fromJson(Utils.getPref(mContext, "loginOtherData"), arrayListType2);
        return messageList;
    }

    public static boolean isMember(Context context, String whereTocome) {
        if (Utils.getAppUserId(context) == 0) {
            Intent intent = new Intent(context, AppLoginActivity.class);
            intent.putExtra("whereTocomeLogin", whereTocome);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

        } else {
            return true;
        }
        return false;
    }

    public static void goToLogin(Context mContext, String whereTocome) {
        Intent intent = new Intent(mContext, AppLoginActivity.class);
//        intent.putExtra("whereTocomeLogin", whereTocome);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

    public static int getAppUserId(Context mContext) {
        int id = 0;

        if (Utils.retriveLoginData(mContext) != null) {
            id = Utils.retriveLoginData(mContext).getId();
        } else {
            id = 0;
        }
        return id;
    }

    public static void InsertLike(Context mContext, Activity activity) { //, int MemberId, int ReferenceId, int LikeStatus, int SourceType

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(mContext.getResources().getString(R.string.internet_error), mContext.getResources().getString(R.string.internet_connection_error), activity);
            return;
        }

//        Utils.showDialog(mContext);

        ApiHandler.getApiService().getInsertBALike(Utils.getLikeData(), new retrofit.Callback<ImageMainModel>() {
            @Override
            public void success(ImageMainModel likeModel, Response response) {
                Utils.dismissDialog();
                if (likeModel == null) {
                    Utils.ping(mContext, mContext.getString(R.string.something_wrong));
                    return;
                }
                if (likeModel.getIsValid() == null) {
                    Utils.ping(mContext, mContext.getString(R.string.something_wrong));
                    return;
                }
                if (likeModel.getIsValid() == 0) {
                    Utils.ping(mContext, mContext.getString(R.string.false_msg));
                    return;
                }
                if (likeModel.getIsValid() == 1) {

                    if (likeModel.getData() != null) {

                    }

                }
            }

            @Override
            public void failure(RetrofitError error) {
                Utils.dismissDialog();
                error.printStackTrace();
                error.getMessage();
                Utils.ping(mContext, mContext.getString(R.string.something_wrong));
            }
        });


    }

    public static Map<String, String> getLikeData() {
        Map<String, String> map = new HashMap<>();
        map.put("MemberId", LikeMemberId);
        map.put("ReferenceId", LikeReferenceId);
        map.put("LikeStatus", LikeStatus);
        map.put("SourceType", LikeSourceType);

        return map;

    }

    public static void InsertBAViews(Context mContext, Activity activity) { //, int MemberId, int ReferenceId, int LikeStatus, int SourceType

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(mContext.getResources().getString(R.string.internet_error), mContext.getResources().getString(R.string.internet_connection_error), activity);
            return;
        }

//        Utils.showDialog(mContext);

        ApiHandler.getApiService().getInsertBAViews(Utils.getViewsData(), new retrofit.Callback<ImageMainModel>() {
            @Override
            public void success(ImageMainModel likeModel, Response response) {
                Utils.dismissDialog();
                if (likeModel == null) {
                    Utils.ping(mContext, mContext.getString(R.string.something_wrong));
                    return;
                }
                if (likeModel.getIsValid() == null) {
                    Utils.ping(mContext, mContext.getString(R.string.something_wrong));
                    return;
                }
                if (likeModel.getIsValid() == 0) {
                    Utils.ping(mContext, mContext.getString(R.string.false_msg));
                    return;
                }
                if (likeModel.getIsValid() == 1) {

                    if (likeModel.getData() != null) {

                    }

                }
            }

            @Override
            public void failure(RetrofitError error) {
                Utils.dismissDialog();
                error.printStackTrace();
                error.getMessage();
                Utils.ping(mContext, mContext.getString(R.string.something_wrong));
            }
        });


    }

    public static Map<String, String> getViewsData() {
        Map<String, String> map = new HashMap<>();
        map.put("MemberId", viewsMemberId);
        map.put("TokenId", viewsTokenId);
        map.put("ReferenceId", viewsReferenceId);
        map.put("SourceType", viewsSourceType);

        return map;

    }

    public static void scrollScreen(ScrollView scrollView) {
        scrollView.postDelayed(new Runnable() {
            @Override
            public void run() {
                View lastChild = scrollView.getChildAt(scrollView.getChildCount() - 1);
                int bottom = lastChild.getBottom() + scrollView.getPaddingBottom();
                int sy = scrollView.getScrollY();
                int sh = scrollView.getHeight();
                int delta = bottom - (sy + sh);
                scrollView.smoothScrollBy(0, delta);
            }
        }, 200);
    }

    public static Activity unwrap(Context context) {
        while (!(context instanceof Activity) && context instanceof ContextWrapper) {
            context = ((ContextWrapper) context).getBaseContext();
        }

        return (Activity) context;
    }

    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.toLowerCase().startsWith(manufacturer.toLowerCase())) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }

    public static String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

    public static void clearCache(Context context) {
        File path = new File(context.getExternalCacheDir(), "camera");
        if (path.exists() && path.isDirectory()) {
            for (File child : path.listFiles()) {
                child.delete();
            }
        }
    }

}

