package com.bharatarmy.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.PagerAdapter;

import com.bharatarmy.Models.LogginModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.ApiHandler;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.meghWebView;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivitySignupNewBinding;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit.RetrofitError;
import retrofit.client.Response;


public class SignupNewActivity extends AppCompatActivity implements View.OnClickListener, meghWebView.OnBottomReachedListener {
    ActivitySignupNewBinding activitySignupNewBinding;
    Context mContext;
    String strFullName, strEmail, strCountrycode, strMobileno, strPassword, strCheck = "0",whereTocomeStr;
    AlertDialog alertDialogAndroid;
    private Handler imageSwitcherHandler;
    private ArrayList<Integer> layouts;
    meghWebView webView;
    Button agree_btn;
    TextView close_btn;
    ImageView image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        activitySignupNewBinding = DataBindingUtil.setContentView(this, R.layout.activity_signup_new);
//        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        mContext = SignupNewActivity.this;
        setDataValue();
        setListiner();
    }

    public void setDataValue() {
        whereTocomeStr=getIntent().getStringExtra("whereTocome");


//        ----------Image Switcher-----------------
        Animation in = AnimationUtils.loadAnimation(mContext, R.anim.fade_in);
        Animation out = AnimationUtils.loadAnimation(mContext, R.anim.fade_out);

        activitySignupNewBinding.imageView.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView myView = new ImageView(mContext);
                myView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                myView.setLayoutParams(new
                        ImageSwitcher.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT));
                return myView;
            }
        });
        activitySignupNewBinding.imageView.setImageResource(R.drawable.login41);
        activitySignupNewBinding.imageView.setInAnimation(in);
        activitySignupNewBinding.imageView.setOutAnimation(out);

        layouts = new ArrayList<Integer>();

        layouts.add(R.drawable.login_new_2);
        layouts.add(R.drawable.login_new_3);
        layouts.add(R.drawable.login_new_1);


        imageSwitcherHandler = new Handler();
        Runnable runnable = new Runnable() {
            int i = 0;

            public void run() {
                activitySignupNewBinding.imageView.setImageResource(layouts.get(i));
                i++;
                if (i > layouts.size() - 1) {
                    i = 0;
//                    activitySignupNewBinding.imageView.setImageResource(R.drawable.login41);
                }
                imageSwitcherHandler.postDelayed(this, 7000);  //for interval...
            }
        };
        imageSwitcherHandler.postDelayed(runnable, 7000); //for initial delay..
    }

    public void setListiner() {
        activitySignupNewBinding.ccp.setCountryForNameCode(AppConfiguration.currentCountry);
        activitySignupNewBinding.termConditionTxt.setOnClickListener(this);
        activitySignupNewBinding.signupBtn.setOnClickListener(this);
        activitySignupNewBinding.closeTxt.setOnClickListener(this);


        activitySignupNewBinding.termsChk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    strCheck = "1";
                } else {
                    strCheck = "0";
                }
            }
        });
    }

    public void getDataValue() {
        strFullName = activitySignupNewBinding.fulluserNameEdt.getText().toString();
        strEmail = activitySignupNewBinding.emailEdt.getText().toString();
        strCountrycode = activitySignupNewBinding.ccp.getSelectedCountryCode();
        strMobileno = activitySignupNewBinding.mobileEdt.getText().toString();
        strPassword = activitySignupNewBinding.userPasswordEdt.getText().toString();

        Log.d("selectedcode", strCountrycode);

        if (!strFullName.equalsIgnoreCase("")) {
            if (!strEmail.equalsIgnoreCase("")) {
                if (Utils.isValidEmailId(strEmail)) {
                    if (strCountrycode.length() > 0) {
                        if (strMobileno.length() > 0) {
                            if (Utils.isValidPhoneNumber(strMobileno)) {
                                boolean status = Utils.validateUsing_libphonenumber(mContext, strCountrycode, strMobileno);
                                if (status) {
                                    if (!strPassword.equalsIgnoreCase("")) {
                                       termconditionDialog();
                                    } else {
                                        Utils.ping(mContext, "Password is required");
                                    }
                                } else {
                                    activitySignupNewBinding.mobileEdt.setError("Invalid Phone Number");
                                }
                            } else {
                                activitySignupNewBinding.mobileEdt.setError("Invalid Phone Number");
                            }
                        } else {
                            activitySignupNewBinding.mobileEdt.setError("Phone Number is required");
                        }
                    } else {
                        Utils.ping(mContext,"Country Code is required");
                    }
                } else {
                    activitySignupNewBinding.emailEdt.setError("Invalid Email Address");
                }
            } else {
                activitySignupNewBinding.emailEdt.setError("Email Address is required");
            }
        } else {
            activitySignupNewBinding.fulluserNameEdt.setError("Full Name is required");
        }


    }

    public void termconditionDialog() {
        LayoutInflater lInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = lInflater.inflate(R.layout.term_condition, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(mContext);
        alertDialogBuilderUserInput.setView(layout);

        alertDialogAndroid = alertDialogBuilderUserInput.create();
        alertDialogAndroid.setCancelable(false);
        alertDialogAndroid.show();
        Window window = alertDialogAndroid.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        WindowManager.LayoutParams wlp = window.getAttributes();
        window.setGravity( Gravity.LEFT | Gravity.TOP );
        wlp.x = 1;
        wlp.y = 100;
        wlp.flags = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        window.setAttributes(wlp);
        alertDialogAndroid.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialogAndroid.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);

        Drawable d = new ColorDrawable(getResources().getColor(R.color.black_dialog));
//        d.setAlpha(50);
        alertDialogAndroid.getWindow().setBackgroundDrawable(d);
        alertDialogAndroid.show();

        webView = (meghWebView) layout.findViewById(R.id.webView);
        image = (ImageView) layout.findViewById(R.id.image);
        agree_btn = (Button) layout.findViewById(R.id.agree_btn);
//        close_btn = (Button) layout.findViewById(R.id.close_btn);
        close_btn = (TextView) layout.findViewById(R.id.close_btn1);
        Glide.with(mContext).load(R.drawable.logo).into(image);
        image.setVisibility(View.VISIBLE);
        agree_btn.setVisibility(View.VISIBLE);
        webView.setWebViewClient(new MyWebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(AppConfiguration.TERMSURL);
        webView.setVerticalScrollBarEnabled(true);
        webView.setOnBottomReachedListener(SignupNewActivity.this, 50);
        webView.setOnClickListener(this);


        agree_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOtpVerification();
            }
        });
        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogAndroid.dismiss();
            }
        });
    }

    @Override
    public void onBottomReached(View v) {
        agree_btn.setAlpha(1);
        agree_btn.setEnabled(true);
        agree_btn.setClickable(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.term_condition_txt:
//                Intent webviewIntent = new Intent(mContext, TermConditionActivity.class);
//                webviewIntent.putExtra("Story Heading", "Terms & Conditions");
//                webviewIntent.putExtra("StroyUrl", "https://www.bharatarmy.com/legal/termsofuse");
//                webviewIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                mContext.startActivity(webviewIntent);
                break;
            case R.id.signup_btn:
                getDataValue();
                break;
            case R.id.close_txt:
                Intent iLogin = new Intent(mContext, LoginNewActivity.class);
                startActivity(iLogin);
                finish();
                break;
        }
    }

    public void getOtpVerification() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), SignupNewActivity.this);
            return;
        }

        Utils.showDialog(mContext);
        ApiHandler.getApiService().getSendVerificationOTP(getOtpVerificationData(), new retrofit.Callback<LogginModel>() {
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
                    otpIntent.putExtra("OTPmobileno", strMobileno);
                    otpIntent.putExtra("countrycode", strCountrycode);
                    otpIntent.putExtra("wheretocome", "Signup");
                    otpIntent.putExtra("signupFullname", strFullName);
                    otpIntent.putExtra("signupEmail", strEmail);
                    otpIntent.putExtra("signupCountryCode", strCountrycode);
                    otpIntent.putExtra("signupMobileno", strMobileno);
                    otpIntent.putExtra("signupPassword", strPassword);
                    otpIntent.putExtra("signupCheck", strCheck);
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
        map.put("AppUserId", "0");
        map.put("PhoneNo", strMobileno);
        map.put("CountryCode", strCountrycode);
        return map;
    }


    public class MyWebViewClient extends WebViewClient {
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

    /**
     * View pager adapter
     */
    public class MyImageViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;
//        ImageSwitcher imageView;

        ImageView imageView;

        public MyImageViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(R.layout.login_image_slider, container, false);

            imageView = (ImageView) view.findViewById(R.id.imageView);

//            Animation inAnimation = AnimationUtils.loadAnimation(mContext, R.anim.fade_in);
//            imageView.startAnimation(inAnimation);

//            AlphaAnimation animation1 = new AlphaAnimation(0.0f, 1.0f);
//            animation1.setDuration(1000);
//            animation1.setStartOffset(50);
//            animation1.setFillAfter(true);
//            imageView.startAnimation(animation1);


            imageView.setImageResource(layouts.get(position));
//            ValueAnimator.ofFloat(0f, 500f).setDuration(3000).addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                @Override
//                public void onAnimationUpdate(ValueAnimator animation) {
//                    imageView.setScaleX(animation.getAnimatedFraction());
//                    imageView.setScaleY(animation.getAnimatedFraction());
//                    animation.start();
//                }
//            });
//


//            ValueAnimator animator = ValueAnimator.ofFloat(0, 1); // values from 0 to 1
//            animator.setDuration(5000); // 5 seconds duration from 0 to 1
//            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
//            {
//                @Override
//                public void onAnimationUpdate(ValueAnimator animation) {
//                    float value = ((Float) (animation.getAnimatedValue()))
//                            .floatValue();
//                    // Set translation of your view here. Position can be calculated
//                    // out of value. This code should move the view in a half brandcircle.
//                    imageView.setTranslationX((float)(150.0 * Math.sin(value*Math.PI)));
//                    imageView.setTranslationY((float)(150.0 * Math.cos(value*Math.PI)));
//                }
//            });
//
//            animator.start();
//
//            imageView.setScaleType(ImageView.ScaleType.CENTER);
//            imageView.animate().scaleX(0.5f).scaleY(0.5f).setDuration(5000).start();

            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return super.getItemPosition(object);
        }
    }

}
