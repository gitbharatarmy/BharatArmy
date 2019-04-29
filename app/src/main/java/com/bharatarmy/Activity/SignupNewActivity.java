package com.bharatarmy.Activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.transition.ChangeBounds;
import android.support.transition.ChangeImageTransform;
import android.support.transition.Fade;
import android.support.transition.TransitionManager;
import android.support.transition.TransitionSet;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.bharatarmy.Models.LogginModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.ApiHandler;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.FadeOutTransformation;
import com.bharatarmy.Utility.FixedSpeedScroller;
import com.bharatarmy.Utility.SimpleTransformation;
import com.bharatarmy.Utility.meghWebView;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivitySignupNewBinding;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import retrofit.RetrofitError;
import retrofit.client.Response;

import static android.support.v4.app.FrameMetricsAggregator.ANIMATION_DURATION;

public class SignupNewActivity extends AppCompatActivity implements View.OnClickListener, meghWebView.OnBottomReachedListener {
    ActivitySignupNewBinding activitySignupNewBinding;
    Context mContext;
    String strFullName, strEmail, strCountrycode, strMobileno, strPassword, strCheck = "0";
    AlertDialog alertDialogAndroid;
    private Handler imageSwitcherHandler;
    private ArrayList<Integer> layouts;
    private MyImageViewPagerAdapter myViewPagerAdapter;
    int currentPage = 0;
    Timer timer = new Timer();
    ;
    final long DELAY_MS = 0;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 6000;
    meghWebView webView;
    //     WebView webView;
    Button agree_btn;
    TextView close_btn;
    ImageView image;
    String termStr = "<section>\n" +
            "    <div class=\"container\">\n" +
            "        <div class=\"col-md-12 col-sm-12 col-xs-12\">\n" +
            "            <div class=\"dynamicpageshtml\">\n" +
            "                                                        <span id=\"docs-internal-guid-8e99efde-7fff-3975-9a67-c20012abb7ca\"><h2 dir=\"ltr\" ><span >INTRODUCTION</span></h2><p dir=\"ltr\" ><span >This website (\"Website\") is owned and provided by The Bharat Army. The Bharat Army Limited. is a member of the Big 3 Entertainment Group of companies (registered in England and Wales with company number 10929757) whose registered address is at 71-75 Shelton Street, Covent Garden, London, England, WC2H 9JQ.</span></p><br><p dir=\"ltr\" ><span >In these Terms of Use, \"we\", \"our\" and \"us\" means The Bharat Army and \"you\" and \"your\" means any person who accesses and uses this Website.</span></p><h2 dir=\"ltr\" ><span >GENERAL</span></h2><p dir=\"ltr\" ><span >Access to and use of this Website is subject to these Terms of Use and our Privacy Policy. By accessing and using this Website you agree to be bound by and to act in accordance with these Terms of Use and our Privacy Policy. If you do not agree to these Terms of Use or our Privacy Policy, you are not permitted to access and use this Website and you should cease such access and/or use immediately. If you breach any term of these Terms of Use, your right to access and use this Website shall cease immediately.</span></p><p dir=\"ltr\" ><span >We reserve the right to amend these Terms of Use from time to time without notice by amending this page. The amended Terms of Use will be effective from the date they are posted on this Website. As these Terms of Use may be amended from time to time, you should check them whenever you visit this Website. Your continued use of this Website will constitute your acceptance of the amended Terms of Use.</span></p><h2 dir=\"ltr\" ><span >OUR SERVICE</span></h2><p dir=\"ltr\" ><span >We aim to provide uninterrupted access to this Website but we give no warranty as to the uninterrupted availability of this Website. We reserve the right to suspend, restrict or terminate your access to this Website at any time.</span></p><br><p dir=\"ltr\" ><span >It is very important before you purchase any product or service that you carefully read the terms and conditions of the product or service and any other documentation that applies to the product or service. You must familiarise yourself with all the details of the product or service, for example, the minimum check in times, cancellations or modifications of bookings, refunds, visa or passport requirements, vaccination requirements, restrictions, exclusions, conditions and obligations which apply to the product or service. It is your responsibility to ensure that the product or service matches your requirements and that you agree to the terms and conditions of the product or service before you purchase it. We accept no responsibility or liability whatsoever for any loss or damage you may suffer or incur in the event that any product or service you purchase does not meet your requirements or is not suitable for you.</span></p><h2 dir=\"ltr\" ><span >LIMITS TO OUR LIABILITY</span></h2><p dir=\"ltr\" ><span >Nothing in these Terms of Use excludes or limits our liability for death or personal injury caused by our negligence or for our fraud or fraudulent misrepresentation.</span></p><br><p dir=\"ltr\" ><span >We are not responsible or liable for any loss or damage you may suffer or incur in connection with your use of this Website which is caused by any event beyond our reasonable control including the electronic transmission of information, content, material and data over the internet and the interception or decryption of it by others.</span></p><br><p dir=\"ltr\" ><span >We are not responsible or liable for any indirect loss or damage you may suffer or incur in connection with your use of this Website or for any loss or damage you may suffer or incur in connection with your use of this Website which was not foreseeable by us when you used this Website.</span></p><p dir=\"ltr\" ><span >We are not responsible or liable for any loss or damage you may suffer or incur in connection with any inability to access and use this Website for any reason.</span></p><h2 dir=\"ltr\" ><span >YOUR RESPONSIBILITIES</span></h2><p dir=\"ltr\" ><span >You must take all reasonable precautions (including using appropriate virus checking software) to ensure that any information, content, material or data you provide &nbsp;is free from viruses, spyware, malicious software, trojans, worms, logic bombs and anything else which may have a contaminating, harmful or destructive effect on any part of this Website or any other technology.</span></p><br><p dir=\"ltr\" ><span >You may complete a registration process as part of your use of this Website which may include the creation of a username, password and/or other identification information. Any username, password and/or other identification information must be kept confidential by you and must not be disclosed to, or shared with, anyone. Where you do disclose to or share with anyone your username, password and/or other identification information, you are solely responsible for all activities undertaken on this Website using your username, password and/or other identification information.</span></p><br><p dir=\"ltr\" ><span >You must check and ensure that all information, content, material or data you provide on this Website is correct, complete, accurate and not misleading and that you disclose all relevant facts. We do not accept any responsibility or liability for any loss or damage you may suffer or incur if any information, content, material or data you provide on this Website is not correct, complete and accurate or if it is misleading or if you fail to disclose all relevant facts.</span></p><h2 dir=\"ltr\" ><span >COMPLAINTS</span></h2><p dir=\"ltr\" ><span >Our aim is at all times to provide you with an excellent service. If you are unhappy with our service for any reason, please contact us via our Contact Us page or by writing to us at The Bharat Army, 144 Ewell Road, Surbiton, Surrey, KT6 6HE.</span></p><p dir=\"ltr\" ><span >We will aim to resolve your complaint within 5 working days. If we are not able to do so, we will provide you with an acknowledgement. After we have had an opportunity to investigate your concerns, we will issue you with a final response.</span></p><h2 dir=\"ltr\" ><span >MISCELLANEOUS</span></h2><p dir=\"ltr\" ><span >If any provision of these Terms of Use is held to be unlawful, invalid or unenforceable, that provision shall be deemed deleted from these Terms of Use and the validity and enforceability of the remaining provisions of these Terms of Use shall not be affected.</span></p><br><p dir=\"ltr\" ><span >These Terms of Use constitute the entire agreement between you and us relating to your access to and use of this Website and supersedes any prior agreements (including any previous terms of use of this Website).</span></p><br><p dir=\"ltr\" ><span >No failure or delay by us in exercising any right under these Terms of Use will operate as a waiver of that right nor will any single or partial exercise by us of any right preclude any further exercise of any right.</span></p><h2 dir=\"ltr\" ><span >GOVERNING LAW</span></h2><p dir=\"ltr\" ><span >This Website has been designed for use within the United Kingdom. These Terms of Use and access to and use of this Website shall be governed by and interpreted in accordance with English law. Each of you and us submit to the exclusive jurisdiction of the courts of England and Wales in connection with these Terms of Use and your access to and use of this Website (including any non-contractual claims or disputes).</span></p><h2 dir=\"ltr\" ><span >NON-COMMERCIAL USE ONLY</span></h2><p dir=\"ltr\" ><span >You may only use this Website for your own personal, non-commercial use (which will at all times be reasonable and not abusive) or for purposes legitimately connected with the purchasing of such products and services.</span></p><br><p dir=\"ltr\" ><span >You are not allowed to access, use or copy any material or information on this Website for any commercial purpose or for any purposes which are unlawful. In particular, you are not allowed to copy (whether by printing off, storing on disk or in any other way), distribute (including distributing copies), alter or tamper with in any way or use any material contained in this Website except that you may print off any individual page for your own personal use.</span></p><h2 dir=\"ltr\" ><span >OWNERSHIP AND USE OF MATERIAL AND INFORMATION ON OUR WEBSITE</span></h2><p dir=\"ltr\" ><span >This Website displays brands, trademarks and registered trademarks which are registered in the UK and/or other countries and which belong to us and third parties and any such brand, trademarks, mentioned on this Website are proprietary to their respective owners. You are not licensed to use any of the marks on our Website unless written permission is granted from the relevant owner of the mark, and you may not meta tag any of these marks.</span></p><p dir=\"ltr\" ><span >Unless otherwise stated, we own (or are licensed to use) the intellectual property rights in the content and information in this Website, including (without limitation) all text, sound, photographs, images, logos, videos, 360° maps, podcasts, blogs, customer reviews, graphics, design, underlying source code and software. Subject to the \"Non-Commercial Use Only\" section above, material and information, either in whole or in part, from this Website may not be reproduced, copied, republished, downloaded, posted, broadcast or transmitted in any form or medium without our and/or the appropriate owner's prior written permission.</span></p><div><span ><br></span></div></span>\n" +
            "                                    \n" +
            "            </div>\n" +
            "        </div>\n" +
            "    </div>\n" +
            "</section>\n";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
// Hide both the navigation bar and the status bar.
// SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
// a general rule, you should design your app to hide the status bar whenever you
// hide the navigation bar.
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        activitySignupNewBinding = DataBindingUtil.setContentView(this, R.layout.activity_signup_new);

        mContext = SignupNewActivity.this;
        setDataValue();
        setListiner();
    }

    public void setDataValue() {
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

//        Animation fadeIn = new AlphaAnimation(0, 1);
//        fadeIn.setInterpolator(new LinearInterpolator()); //add this
//        fadeIn.setDuration(5000);
//
//        Animation fadeOut = new AlphaAnimation(1, 0);
//        fadeOut.setInterpolator(new LinearInterpolator()); //and this
//        fadeOut.setStartOffset(1000);
//        fadeOut.setDuration(5000);
//
//        AnimationSet animation = new AnimationSet(false); //change to false
//        animation.addAnimation(fadeIn);
//        animation.addAnimation(fadeOut);
//        activitySignupNewBinding.imageView.setAnimation(animation);

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
                if (i > layouts.size()-1) {
                    i = 0;
//                    activitySignupNewBinding.imageView.setImageResource(R.drawable.login41);
                }
                imageSwitcherHandler.postDelayed(this, 7000);  //for interval...
            }
        };
        imageSwitcherHandler.postDelayed(runnable, 7000); //for initial delay..


//        --------View Pager-----------------
//        activitySignupNewBinding.viewPager.setPageTransformer(true, new FadeOutTransformation());
//        activitySignupNewBinding.viewPager.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return true;
//            }
//        });
//        layouts = new ArrayList<>();
//        layouts.add(R.drawable.login41);
//        layouts.add(R.drawable.login_new_2);
//        layouts.add(R.drawable.login_new_3);
//
//        myViewPagerAdapter = new MyImageViewPagerAdapter();
//        activitySignupNewBinding.viewPager.setAdapter(myViewPagerAdapter);
//        try {
//            Field mScroller;
//            mScroller = ViewPager.class.getDeclaredField("mScroller");
//            mScroller.setAccessible(true);
//            FixedSpeedScroller scroller = new FixedSpeedScroller(activitySignupNewBinding.viewPager.getContext());
//            // scroller.setFixedDuration(5000);
//            mScroller.set(activitySignupNewBinding.viewPager, scroller);
//        } catch (NoSuchFieldException e) {
//        } catch (IllegalArgumentException e) {
//        } catch (IllegalAccessException e) {
//        }
//
//        final Handler handler = new Handler();
//        final Runnable Update = new Runnable() {
//            public void run() {
//                if (currentPage == layouts.size()) {
//                    currentPage = 0;
//                }
//                activitySignupNewBinding.viewPager.setCurrentItem(currentPage++, true);
//            }
//        };
//
//        timer = new Timer(); // This will create a new Thread
//
//        timer.schedule(new TimerTask() { // task to be scheduled
//            @Override
//            public void run() {
//                handler.post(Update);
//            }
//        }, DELAY_MS, PERIOD_MS);


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
                    if (!strMobileno.equalsIgnoreCase("")) {
//                        if (strMobileno.length()==10) {
                        if (!strPassword.equalsIgnoreCase("")) {
                            termconditionDialog();
//                            if (!strCheck.equalsIgnoreCase("0")) {
//                                getOtpVerification();
//                            } else {
//                                Utils.ping(mContext, "Please accept terms & conditions");
//                            }
                        } else {
                            activitySignupNewBinding.userPasswordEdt.setError("Please enter password");
                        }
//                        }else{
//                            activitySignupNewBinding.mobileEdt.setError("Please enter 10 digit mobile no");
//                        }
                    } else {
                        activitySignupNewBinding.mobileEdt.setError("Please enter mobile no");
                    }
                } else {
                    activitySignupNewBinding.emailEdt.setError("Please enter valid email address");
                }
            } else {
                activitySignupNewBinding.emailEdt.setError("Please enter email address");
            }
        } else {
            activitySignupNewBinding.fulluserNameEdt.setError("Please enter fullname");
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

        wlp.gravity = Gravity.CENTER_HORIZONTAL;
        wlp.flags = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        window.setAttributes(wlp);
        alertDialogAndroid.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Drawable d = new ColorDrawable(Color.BLACK);
        d.setAlpha(50);
        alertDialogAndroid.show();

        webView = (meghWebView) layout.findViewById(R.id.webView);
        image = (ImageView) layout.findViewById(R.id.image);
        agree_btn = (Button) layout.findViewById(R.id.agree_btn);
//        close_btn = (Button) layout.findViewById(R.id.close_btn);
        close_btn = (TextView) layout.findViewById(R.id.close_btn1);
        Glide.with(mContext).load(R.drawable.logo).into(image);
        image.setVisibility(View.VISIBLE);

        webView.setWebViewClient(new MyWebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadDataWithBaseURL("", termStr, "text/html", "UTF-8", "");
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
                Intent webviewIntent = new Intent(mContext, TermConditionActivity.class);
                webviewIntent.putExtra("Story Heading", "Terms & Conditions");
                webviewIntent.putExtra("StroyUrl", "https://www.bharatarmy.com/legal/termsofuse");
                webviewIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(webviewIntent);
                break;
            case R.id.signup_btn:
                getDataValue();
//                termconditionDialog();
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
//                    // out of value. This code should move the view in a half circle.
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
