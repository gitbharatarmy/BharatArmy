package com.bharatarmy.Utility;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

import com.bharatarmy.Activity.SignUpActivity;
import com.bharatarmy.Country;
import com.bharatarmy.CountryCodePicker;
import com.bharatarmy.Interfaces.submit_click;
import com.bharatarmy.R;
import com.bumptech.glide.Glide;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;
import java.util.regex.Pattern;

import static com.yalantis.ucrop.util.FileUtils.getDataColumn;
import static com.yalantis.ucrop.util.FileUtils.isDownloadsDocument;
import static com.yalantis.ucrop.util.FileUtils.isExternalStorageDocument;
import static com.yalantis.ucrop.util.FileUtils.isGooglePhotosUri;
import static com.yalantis.ucrop.util.FileUtils.isMediaDocument;

public class Utils {
    public static String strFullName, strEmail, strCountrycode, strMobileno, strPassword, strCheck = "0";
    public static meghWebView webView;
    public static ImageView image;
    public static Button agree_btn;
    public static TextView close_btn;

    public static Dialog dialog;

    public static final String MyPREFERENCES = "MyPrefs";
    public static SharedPreferences sharedpreferences;

    public static boolean isNetworkConnected(Context ctxt) {
        ConnectivityManager cm = (ConnectivityManager) ctxt
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null;
    }


    public static boolean checkNetwork(Context context) {
        boolean wifiAvailable = false;
        boolean mobileAvailable = false;
        ConnectivityManager conManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInfo = conManager.getAllNetworkInfo();
        for (NetworkInfo netInfo : networkInfo) {
            if (netInfo.getTypeName().equalsIgnoreCase("WIFI"))
                if (netInfo.isConnected())
                    wifiAvailable = true;
            if (netInfo.getTypeName().equalsIgnoreCase("MOBILE"))
                if (netInfo.isConnected())
                    mobileAvailable = true;
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
            Glide.with(context).load(R.drawable.logo_white).into(imageView);
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
            dialog.dismiss();
            dialog = null;

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
            String internationalFormat = phoneNumberUtil.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);
//            Toast.makeText(context, "Phone Number is Valid " + internationalFormat, Toast.LENGTH_LONG).show();
            return true;
        } else {
//            Toast.makeText(context, "Phone Number is Invalid " + phoneNumber, Toast.LENGTH_LONG).show();
            return false;
        }
    }

    public static Bitmap DrawableToBitMap(int first_match_map, Context mContext) {
        Drawable myDrawable = mContext.getResources().getDrawable(first_match_map);
        Bitmap anImage = ((BitmapDrawable) myDrawable).getBitmap();
        return anImage;
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
                .placeholder(R.drawable.loader)
                .into(view);

    }

    public static boolean isReadStorageGranted(Context context) {
        int storagePermissionGranted = ContextCompat.checkSelfPermission(context,
                Manifest.permission.READ_EXTERNAL_STORAGE);
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
        ImageView updateapp_img=(ImageView)dialog.findViewById(R.id.updateapp_img);

        Glide.with(activity)
                .load(R.drawable.logo)
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
        TextView hometxt = (TextView) dialogView.findViewById(R.id.home_txt);
        hometxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

            }
        });
        alertDialog.show();
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

        close_linear=(LinearLayout)dialogView.findViewById(R.id.close_linear);
        term_condition_txt = (TextView) dialogView.findViewById(R.id.term_condition_txt);
        terms_chk = (CheckBox) dialogView.findViewById(R.id.terms_chk);

        signup_btn = (Button) dialogView.findViewById(R.id.signup_btn);



        AppConfiguration.currentCountry = ccp.getSelectedCountryNameCode();


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
                Glide.with(activity).load(R.drawable.logo).into(image);
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
                AppConfiguration.currentCountry = ccp.getSelectedCountryNameCode();
            }
        });

        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConfiguration.registerNameStr = fulluser_name_edt.getText().toString();
                AppConfiguration.registerEmailStr = email_edt.getText().toString();
                AppConfiguration.registerCountryCodeStr = ccp.getSelectedCountryCode();
                AppConfiguration.registerMobileStr = mobile_edt.getText().toString();
                AppConfiguration.registerCountryDialcodeStr=AppConfiguration.currentCountry;
                Log.d("selectedcode", strCountrycode);
                if (!AppConfiguration.registerNameStr.equalsIgnoreCase("")) {
                    if (! AppConfiguration.registerEmailStr.equalsIgnoreCase("")) {
                        if (Utils.isValidEmailId( AppConfiguration.registerEmailStr)) {
                            if (AppConfiguration.registerCountryCodeStr.length() > 0) {
                                if (AppConfiguration.registerMobileStr .length() > 0) {
                                    if (Utils.isValidPhoneNumber(AppConfiguration.registerMobileStr )) {
                                        boolean status = Utils.validateUsing_libphonenumber(activity, AppConfiguration.registerCountryCodeStr, AppConfiguration.registerMobileStr);
                                        if (status) {
                                            if (!strCheck.equalsIgnoreCase("0")) {
                                                alertDialog.dismiss();
                                                submit_click.getsubmitClick();
                                            } else {
                                                Utils.ping(activity, "Check the privacy policy");
                                            }
                                        } else {
                                            mobile_edt.setError("Invalid Phone Number");
                                        }
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

    public static int calculateNoOfColumns(Context context, float columnWidthDp) { // For example columnWidthdp=180
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float screenWidthDp = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (screenWidthDp / columnWidthDp + 0.5); // +0.5 for correct rounding to int.
        Log.d("noofcolumns : ", ""+noOfColumns);
        return noOfColumns;
    }

}
