package com.bharatarmy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bharatarmy.Adapter.FeedbackImagewithTextAdapter;
import com.bharatarmy.Adapter.FeedbackRatingAdapter;
import com.bharatarmy.Adapter.FeedbackSingleChoiceAdapter;
import com.bharatarmy.Adapter.FeedbackTextMultiChoiceAdapter;
import com.bharatarmy.Adapter.FeedbackTextSingleChoiceGridAdapter;
import com.bharatarmy.Adapter.FeedbackViewAdapter;
import com.bharatarmy.Interfaces.MorestoryClick;
import com.bharatarmy.Models.FeedbackAnswerList;
import com.bharatarmy.Models.FeedbackMainModel;
import com.bharatarmy.Models.LoginDataModel;
import com.bharatarmy.Models.MyScreenChnagesModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.ApiHandler;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityFeedbackBinding;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class FeedbackActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityFeedbackBinding activityFeedbackBinding;
    /*Activity variable*/
    private Context mContext;
    public String isUpdateAvailable, isForceUpdateAvailable, currentVersionStr;

    /*Image with text adapter*/
    FeedbackImagewithTextAdapter feedbackImagewithTextAdapter;
    GridLayoutManager gridLayoutManager;
    List<FeedbackAnswerList> feedbackansimagetextmodellist;

    /*Text MultiChoice adapter*/
    FeedbackTextMultiChoiceAdapter feedbackTextMultiChoiceAdapter;
    LinearLayoutManager textmultichoicelinearLayoutManager;
    List<FeedbackAnswerList> feedbacktextmultichoicelist;

    /*Rating Singlechoice adapter*/
    FeedbackRatingAdapter feedbackRatingAdapter;
    LinearLayoutManager ratingsinglechoicelinearLayoutManager;
    List<FeedbackAnswerList> feedbackratingsinglechoicelist;

    /*text Singlechoice adapter*/
    FeedbackSingleChoiceAdapter feedbackSingleChoiceAdapter;
    LinearLayoutManager textsinglechoicelinearLayoutManager;
    List<FeedbackAnswerList> feedbacktextsinglechoicelist;

    /*text singlechoice grid adapter*/
    FeedbackTextSingleChoiceGridAdapter feedbackTextSingleChoiceGridAdapter;
    GridLayoutManager textsinglegridLayoutManager;
    List<FeedbackAnswerList> feedbackanstextsinglechoicegridlist;

    /*question answer view adapter*/
    FeedbackViewAdapter feedbackViewAdapter;
    List<LoginDataModel> feedbackviewanslist;
    LinearLayoutManager ansviewmLayout;

    /*question answer string*/
    String question2ansStr = "", question2ansimageStr = "", question10ansStr = "", question11ansStr = "", question12ansStr = "",
            question13ansStr = "", question1ansStr = "", question3ansStr = "", question4ansStr = "", question5ansStr = "",
            question6ansStr = "", question7ansStr = "", question8ansStr = "", question9ansStr = "", question9ansratingStr = "",
            feedbackansimageStr = "", feedbackansratingStr = "";


    /*Question count*/
    int totalLayout;
    int count = 1;

    /*next previous question count variable*/
    int nextQuestionIndex = 0;
    int currentQuestionIndex = 0;
    String feedbackAnswerValueStr;
    int currentQuestionId = 0;
    int isRequired;
    int selectedimageandtextchangesposition = -1;
    int selectedratingchangeposition = -1;
    int selectedtextsinglechangesposition = -1;
    int selectedtextsinglegridchangesposition = -1;


    /*data model list*/
    FeedbackMainModel feedbackQuestionAnswermodellist;


    boolean isKeyboardShowing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityFeedbackBinding = DataBindingUtil.setContentView(this, R.layout.activity_feedback);

        mContext = FeedbackActivity.this;

        init();
        setListiner();
    }

    public void init() {
        activityFeedbackBinding.toolbarTitleTxt.setText("Feedback");
        activityFeedbackBinding.shimmerViewContainer.startShimmerAnimation();
        activityFeedbackBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
        callQuestionAnswerList();
    }
//    void onKeyboardVisibilityChanged(boolean opened) {
//       if (opened ==true){
//           RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
//                  getResources().getDimensionPixelOffset(R.dimen._70sdp),
//                   getResources().getDimensionPixelOffset(R.dimen._35sdp)
//           );
//           params.setMargins(getResources().getDimensionPixelOffset(R.dimen._10sdp), 0, getResources().getDimensionPixelOffset(R.dimen._10sdp), getResources().getDimensionPixelOffset(R.dimen._10sdp));
//           activityFeedbackBinding.nextLinear.setLayoutParams(params);
//           activityFeedbackBinding.previousLinear.setLayoutParams(params);
//       }else{
//           RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
//                   getResources().getDimensionPixelOffset(R.dimen._70sdp),
//                   getResources().getDimensionPixelOffset(R.dimen._35sdp)
//           );
//           params.setMargins(getResources().getDimensionPixelOffset(R.dimen._10sdp), 0, getResources().getDimensionPixelOffset(R.dimen._10sdp), getResources().getDimensionPixelOffset(R.dimen._50sdp));
//           activityFeedbackBinding.nextLinear.setLayoutParams(params);
//           activityFeedbackBinding.previousLinear.setLayoutParams(params);
//       }
//    }
    public void setListiner() {
        activityFeedbackBinding.nextLinear.setOnClickListener(this);
        activityFeedbackBinding.previousLinear.setOnClickListener(this);
        activityFeedbackBinding.editLinear.setOnClickListener(this);
        activityFeedbackBinding.viewFeedbackBtn.setOnClickListener(this);
        activityFeedbackBinding.edittextAnsTxt.setOnClickListener(this);
        activityFeedbackBinding.backImg.setOnClickListener(this);

        activityFeedbackBinding.edittextAnsTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Integer textlength1 = activityFeedbackBinding.edittextAnsTxt.getText().length();


            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().length() >= 1) {
                    isRequired = 0;
                    AppConfiguration.addtextchoice = "fill";
                } else {
                    isRequired = 1;
                    AppConfiguration.addtextchoice = "not fill";
                }


            }
        });

// ContentView is the root view of the layout of this activity/fragment
//        activityFeedbackBinding.feedbackScroll.getViewTreeObserver().addOnGlobalLayoutListener(
//                new ViewTreeObserver.OnGlobalLayoutListener() {
//                    @Override
//                    public void onGlobalLayout() {
//
//                        Rect r = new Rect();
//                        activityFeedbackBinding.feedbackScroll.getWindowVisibleDisplayFrame(r);
//                        int screenHeight = activityFeedbackBinding.feedbackScroll.getRootView().getHeight();
//
//                        // r.bottom is the position above soft keypad or device button.
//                        // if keypad is shown, the r.bottom is smaller than that before.
//                        int keypadHeight = screenHeight - r.bottom;
//
//                        Log.d("Height", "keypadHeight = " + keypadHeight);
//
//                        if (keypadHeight > screenHeight * 0.15) { // 0.15 ratio is perhaps enough to determine keypad height.
//                            // keyboard is opened
//                            if (!isKeyboardShowing) {
//                                isKeyboardShowing = true;
//                                onKeyboardVisibilityChanged(true);
//                            }
//                        }
//                        else {
//                            // keyboard is closed
//                            if (isKeyboardShowing) {
//                                isKeyboardShowing = false;
//                                onKeyboardVisibilityChanged(false);
//                            }
//                        }
//                    }
//                });

        activityFeedbackBinding.edittextAnsTxt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    //do your stuff here
                    activityFeedbackBinding.feedbackScroll.fullScroll(View.FOCUS_DOWN);
                }
                return false;
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next_linear:
                if (isRequired != 1) {
                    forwardLayoutvalue();
                } else {
                    Utils.ping(mContext, "answer required");
                }
                break;
            case R.id.previous_linear:
                previousLayoutvalue();
                break;
            case R.id.view_feedback_btn:
                Utils.hideKeyboard(FeedbackActivity.this);
                activityFeedbackBinding.thankyouLinear.setVisibility(View.GONE);
                activityFeedbackBinding.previousLinear.setVisibility(View.GONE);
                activityFeedbackBinding.nextLinear.setVisibility(View.GONE);
                activityFeedbackBinding.questionLinear.setVisibility(View.GONE);
                activityFeedbackBinding.feedbackScroll.setVisibility(View.GONE);
                activityFeedbackBinding.edittextAnsLinear.setVisibility(View.GONE);

                activityFeedbackBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
                activityFeedbackBinding.shimmerViewContainer.startShimmerAnimation();
                feedbackAnswerValueStr = "";
                callQuestionAnswerList();
//                answerstoreLocal();


                break;
            case R.id.edit_linear:
                stayfeedbacksurvey();
                activityFeedbackBinding.thankyouLinear.setVisibility(View.GONE);
                activityFeedbackBinding.questionAnsViewLinear.setVisibility(View.GONE);
                /*Animation for visibility gone*/
                Animation animSlideoute = AnimationUtils.loadAnimation(mContext, R.anim.slide_out_left_new);
                activityFeedbackBinding.questionAnsViewLinear.startAnimation(animSlideoute);

                activityFeedbackBinding.editLinear.setVisibility(View.GONE);
//                if (count == 1) {
                activityFeedbackBinding.previousLinear.setVisibility(View.GONE);
//                } else {
//                    activityFeedbackBinding.previousLinear.setVisibility(View.VISIBLE);
//                }
                activityFeedbackBinding.nextLinear.setVisibility(View.VISIBLE);
                activityFeedbackBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
                activityFeedbackBinding.shimmerViewContainer.startShimmerAnimation();
                /*Animation for visibility visible*/
                Animation animSlideine = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_right_new);
                activityFeedbackBinding.shimmerViewContainer.startAnimation(animSlideine);
                count = 1;
                currentQuestionIndex = 1;
                nextQuestionIndex = 0;
                callFeedbackQuestionAnswerData();

                activityFeedbackBinding.nextImg.setImageResource(R.drawable.ic_next_question);
                break;
            case R.id.edittext_ans_txt:
                Utils.scrollScreen(activityFeedbackBinding.feedbackScroll);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                break;
            case R.id.back_img:
                backActivity();
                break;
        }

    }

    public void backActivity() {
        finish();
    }

    @Override
    public void onBackPressed() {
        backActivity();
        super.onBackPressed();
    }

    public void setQuestionAnswer() {

        isRequired = feedbackQuestionAnswermodellist.getData().getIsRequired();

        activityFeedbackBinding.questionCategoryTitleTxt.setText(feedbackQuestionAnswermodellist.getData().getHeaderTypeText());
        activityFeedbackBinding.questionTxt.setText(feedbackQuestionAnswermodellist.getData().getFeedbackQuestion());
        if (feedbackQuestionAnswermodellist.getData().getFeedbackType().equals(1)) {

            setValueTextView(String.valueOf(feedbackQuestionAnswermodellist.getData().getFeedbackDescription()));
        } else if (feedbackQuestionAnswermodellist.getData().getFeedbackType().equals(4)) {

            setImageandTextValue();
        } else if (feedbackQuestionAnswermodellist.getData().getFeedbackType().equals(5)) {

            setEditextValue();
        } else if (feedbackQuestionAnswermodellist.getData().getFeedbackType().equals(6)) {

            setTextMultiChoiceValue();
        } else if (feedbackQuestionAnswermodellist.getData().getFeedbackType().equals(7)) {

            setTextWithRatingSingleChoiceValue();
        } else if (feedbackQuestionAnswermodellist.getData().getFeedbackType().equals(2)) {

            setTextSingleChoiceValue();
        } else if (feedbackQuestionAnswermodellist.getData().getFeedbackType().equals(8)) {

            setTextGridSingleChoiceValue();
        }


    }

    public void updateNextCountValueText(int count) {
        if (count <= 9) {
            activityFeedbackBinding.submitTxt.setText("0" + String.valueOf(count) + " of " + String.valueOf(totalLayout));
        } else {
            activityFeedbackBinding.submitTxt.setText(String.valueOf(count) + " of " + String.valueOf(totalLayout));
        }
    }

    /*View Question and Answer of Feedback User*/
    public void setSelectedValueInList() {
        quitefeedbacksurvey();
//        answerretriveLocal();

        for (int i = 0; i < feedbackviewanslist.size(); i++) {
            if (!feedbackviewanslist.get(i).getAnswerValue().equalsIgnoreCase("")) {
                Utils.setPref(mContext, "feedbackgiveflag", "1");
                break;
            } else {
                Utils.setPref(mContext, "feedbackgiveflag", "0");
            }
        }
        for (int i = 0; i < feedbackviewanslist.size(); i++) {
            if (feedbackviewanslist.get(i).getFeedbackType().equals(4)) {
                for (int j = 0; j < feedbackviewanslist.get(i).getOptions().size(); j++) {
                    if (feedbackviewanslist.get(i).getAnswerValue().equalsIgnoreCase(feedbackviewanslist.get(i).getOptions().get(j).getText())) {
                        feedbackansimageStr = feedbackviewanslist.get(i).getOptions().get(j).getImageUrl();
                    }
                }
            }


        }
        if (Utils.getPref(mContext, "feedbackgiveflag") != null) {
            if (Utils.getPref(mContext, "feedbackgiveflag").equalsIgnoreCase("1")) {
                activityFeedbackBinding.shimmerViewContainer.stopShimmerAnimation();
                activityFeedbackBinding.shimmerViewContainer.setVisibility(View.GONE);
                /*Animation for visibility gone*/
                Animation animSlideout = AnimationUtils.loadAnimation(mContext, R.anim.slide_out_left_new);
                activityFeedbackBinding.shimmerViewContainer.startAnimation(animSlideout);

                activityFeedbackBinding.questionAnsViewLinear.setVisibility(View.VISIBLE);
                activityFeedbackBinding.editLinear.setVisibility(View.VISIBLE);
                /*Animation for visibility visible*/
                Animation animSlidein = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_right_new);
                activityFeedbackBinding.questionAnsViewLinear.startAnimation(animSlidein);
                Log.d("imageStr", feedbackansimageStr);
                feedbackViewAdapter = new FeedbackViewAdapter(mContext, feedbackviewanslist, feedbackansimageStr);
                ansviewmLayout = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
                activityFeedbackBinding.questionAnsViewRcv.setLayoutManager(ansviewmLayout);
                activityFeedbackBinding.questionAnsViewRcv.setItemAnimator(new DefaultItemAnimator());
                activityFeedbackBinding.questionAnsViewRcv.setAdapter(feedbackViewAdapter);
            } else {
                currentQuestionIndex = 1;
                nextQuestionIndex = 0;
                currentQuestionId = 0;
                count = 1;
                callFeedbackQuestionAnswerData();
            }
        }

    }


    /*Set the textview value in layout*/
    public void setValueTextView(String text) {
//        activityFeedbackBinding.textviewAnsLinear.setVisibility(View.VISIBLE);
        String textUrl = "<html><style type='text/css'>@font-face { font-family: thesansplain; src: url('fonts/thesansplain.ttf'); } body p {font-family: thesansplain;}</style>"
                + "<body >" + "<p align=\"justify\" style=\"font-size: 22px; font-family: spqr;\">" + text + "</p> " + "</body></html>";


        activityFeedbackBinding.textviewAnsTxt.getSettings().setJavaScriptEnabled(true);

        activityFeedbackBinding.textviewAnsTxt.setVerticalScrollBarEnabled(false);
        activityFeedbackBinding.textviewAnsTxt.loadDataWithBaseURL("file:///android_asset/", textUrl, "text/html", "UTF-8", null);


        activityFeedbackBinding.textviewAnsTxt.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                return false; // for the WebView to load the URL
            }
        });

    }

    /*Set the ImageandTextSinglechoice value in layout*/
    public void setImageandTextValue() {
        feedbackansimagetextmodellist = feedbackQuestionAnswermodellist.getData().getOptions();
        activityFeedbackBinding.ImagewithtextsinglechoiceLinear.setVisibility(View.VISIBLE);
        for (int i = 0; i < feedbackansimagetextmodellist.size(); i++) {
            feedbackansimagetextmodellist.get(i).setQuestionAnswerImagewithTextSelect("0");
            if (!feedbackQuestionAnswermodellist.getData().getAnswerValue().equalsIgnoreCase("")) {
                if (feedbackQuestionAnswermodellist.getData().getAnswerValue().equalsIgnoreCase(feedbackansimagetextmodellist.get(i).getText())) {
                    selectedimageandtextchangesposition = i;
                    isRequired = 0;
                }
            }
        }
        Log.d("Imagetext :", "" + feedbackansimagetextmodellist.get(0).getQuestionAnswerImage());
        feedbackImagewithTextAdapter = new FeedbackImagewithTextAdapter(mContext, feedbackansimagetextmodellist, selectedimageandtextchangesposition, new MorestoryClick() {
            @Override
            public void getmorestoryClick() {
                for (int i = 0; i < feedbackansimagetextmodellist.size(); i++) {
                    if (feedbackansimagetextmodellist.get(i).getQuestionAnswerImagewithTextSelect().equalsIgnoreCase("1")) {
                        AppConfiguration.singlechoice = "fill";
                        isRequired = 0;
                        break;
                    } else {
                        AppConfiguration.singlechoice = "not fill";
                    }
                }
            }
        });
        gridLayoutManager = new GridLayoutManager(mContext, 3);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL); // set Horizontal Orientation
        activityFeedbackBinding.ImagewithtextsinglechoiceAnsRcv.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
        activityFeedbackBinding.ImagewithtextsinglechoiceAnsRcv.setAdapter(feedbackImagewithTextAdapter);
    }

    /*Set the Edittext value in layout*/
    public void setEditextValue() {
        activityFeedbackBinding.feedbackScroll.setVisibility(View.VISIBLE);
        activityFeedbackBinding.edittextAnsLinear.setVisibility(View.VISIBLE);

        if (feedbackQuestionAnswermodellist.getData().getId().equals(3)) {
            if (!feedbackQuestionAnswermodellist.getData().getAnswerValue().equalsIgnoreCase("")) {
                activityFeedbackBinding.edittextAnsTxt.setText(feedbackQuestionAnswermodellist.getData().getAnswerValue());
            }
        } else if (feedbackQuestionAnswermodellist.getData().getId().equals(4)) {
            if (!feedbackQuestionAnswermodellist.getData().getAnswerValue().equalsIgnoreCase("")) {
                activityFeedbackBinding.edittextAnsTxt.setText(feedbackQuestionAnswermodellist.getData().getAnswerValue());
            }
        } else if (feedbackQuestionAnswermodellist.getData().getId().equals(5)) {
            if (!feedbackQuestionAnswermodellist.getData().getAnswerValue().equalsIgnoreCase("")) {
                activityFeedbackBinding.edittextAnsTxt.setText(feedbackQuestionAnswermodellist.getData().getAnswerValue());
            }
        } else if (feedbackQuestionAnswermodellist.getData().getId().equals(6)) {
            if (!feedbackQuestionAnswermodellist.getData().getAnswerValue().equalsIgnoreCase("")) {
                activityFeedbackBinding.edittextAnsTxt.setText(feedbackQuestionAnswermodellist.getData().getAnswerValue());
            }
        } else if (feedbackQuestionAnswermodellist.getData().getId().equals(8)) {
            if (!feedbackQuestionAnswermodellist.getData().getAnswerValue().equalsIgnoreCase("")) {
                activityFeedbackBinding.edittextAnsTxt.setText(feedbackQuestionAnswermodellist.getData().getAnswerValue());
            }
        } else if (feedbackQuestionAnswermodellist.getData().getId().equals(12)) {
            if (!feedbackQuestionAnswermodellist.getData().getAnswerValue().equalsIgnoreCase("")) {
                activityFeedbackBinding.edittextAnsTxt.setText(feedbackQuestionAnswermodellist.getData().getAnswerValue());
            }
        } else if (feedbackQuestionAnswermodellist.getData().getId().equals(13)) {
            if (!feedbackQuestionAnswermodellist.getData().getAnswerValue().equalsIgnoreCase("")) {
                activityFeedbackBinding.edittextAnsTxt.setText(feedbackQuestionAnswermodellist.getData().getAnswerValue());
            }
        }
    }

    /*Set the TextMultiChoice value in layout*/
    public void setTextMultiChoiceValue() {
        feedbacktextmultichoicelist = feedbackQuestionAnswermodellist.getData().getOptions();
        activityFeedbackBinding.textmultichoiceLinear.setVisibility(View.VISIBLE);
        for (int i = 0; i < feedbacktextmultichoicelist.size(); i++) {
            feedbacktextmultichoicelist.get(i).setQuestionAnswerTextMultiSelect("0");
            if (!feedbackQuestionAnswermodellist.getData().getAnswerValue().equalsIgnoreCase("")) {
                List<String> items = Arrays.asList(feedbackQuestionAnswermodellist.getData().getAnswerValue().split("\\s*,\\s*"));

                for (int k = 0; k < items.size(); k++) {
                    if (items.get(k).trim().equalsIgnoreCase(feedbacktextmultichoicelist.get(i).getText())) {
                        feedbacktextmultichoicelist.get(i).setQuestionAnswerTextMultiSelect("1");
                        isRequired = 0;
                    }
                }
            }
        }
        feedbackTextMultiChoiceAdapter = new FeedbackTextMultiChoiceAdapter(mContext, feedbacktextmultichoicelist, new MorestoryClick() {
            @Override
            public void getmorestoryClick() {

                for (int i = 0; i < feedbacktextmultichoicelist.size(); i++) {
                    if (feedbacktextmultichoicelist.get(i).getQuestionAnswerTextMultiSelect().equalsIgnoreCase("1")) {
                        AppConfiguration.multichoice = "fill";
                        break;
                    } else {
                        AppConfiguration.multichoice = "not fill";
                    }
                }
            }
        });
        textmultichoicelinearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        activityFeedbackBinding.textmultichoiceAnsRcv.setLayoutManager(textmultichoicelinearLayoutManager);
        activityFeedbackBinding.textmultichoiceAnsRcv.setItemAnimator(new DefaultItemAnimator());
        activityFeedbackBinding.textmultichoiceAnsRcv.setAdapter(feedbackTextMultiChoiceAdapter);
    }

    /*Set the TextWithRatingSignleChoice value in layout*/
    public void setTextWithRatingSingleChoiceValue() {
        activityFeedbackBinding.textwithsingleratingchoiceLinear.setVisibility(View.VISIBLE);

        feedbackratingsinglechoicelist = new ArrayList<>();

        feedbackratingsinglechoicelist.add(new FeedbackAnswerList("5 STAR", "5"));
        feedbackratingsinglechoicelist.add(new FeedbackAnswerList("4 STAR", "4"));
        feedbackratingsinglechoicelist.add(new FeedbackAnswerList("3 STAR", "3"));
        feedbackratingsinglechoicelist.add(new FeedbackAnswerList("2 STAR", "2"));


        for (int i = 0; i < feedbackratingsinglechoicelist.size(); i++) {
            feedbackratingsinglechoicelist.get(i).setQuestionAnswerRatingSelect("0");
            if (!feedbackQuestionAnswermodellist.getData().getAnswerValue().equalsIgnoreCase("")) {
                if (feedbackQuestionAnswermodellist.getData().getAnswerValue().equalsIgnoreCase(feedbackratingsinglechoicelist.get(i).getQuestionAnswerRating())) {
                    selectedratingchangeposition = i;
                    isRequired = 0;
                }
            }

        }
        feedbackRatingAdapter = new FeedbackRatingAdapter(mContext, feedbackratingsinglechoicelist, selectedratingchangeposition, new MorestoryClick() {
            @Override
            public void getmorestoryClick() {
                for (int i = 0; i < feedbackratingsinglechoicelist.size(); i++) {
                    if (feedbackratingsinglechoicelist.get(i).getQuestionAnswerRatingSelect().equalsIgnoreCase("1")) {
                        AppConfiguration.singlechoice = "fill";
                        isRequired = 0;
                        break;
                    } else {
                        AppConfiguration.singlechoice = "not fill";
                    }
                }
            }
        });
        ratingsinglechoicelinearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        activityFeedbackBinding.textwithsingleratingchoiceAnsRcv.setLayoutManager(ratingsinglechoicelinearLayoutManager);
        activityFeedbackBinding.textwithsingleratingchoiceAnsRcv.setItemAnimator(new DefaultItemAnimator());
        activityFeedbackBinding.textwithsingleratingchoiceAnsRcv.setAdapter(feedbackRatingAdapter);
    }

    /*Set the TextSingleChoice value in layout*/
    public void setTextSingleChoiceValue() {
        feedbacktextsinglechoicelist = new ArrayList<>();

        feedbacktextsinglechoicelist.add(new FeedbackAnswerList("Yes", ""));
        feedbacktextsinglechoicelist.add(new FeedbackAnswerList("No", ""));

        for (int i = 0; i < feedbacktextsinglechoicelist.size(); i++) {
            feedbacktextsinglechoicelist.get(i).setQuestionAnswerTextSingleSelect("0");
            if (!feedbackQuestionAnswermodellist.getData().getAnswerValue().equalsIgnoreCase("")) {
                if (feedbackQuestionAnswermodellist.getData().getAnswerValue().equalsIgnoreCase(feedbacktextsinglechoicelist.get(i).getQuestionAnswerText())) {
                    selectedtextsinglechangesposition = i;
                    isRequired = 0;
                }

            }

            activityFeedbackBinding.textsinglechoiceLinear.setVisibility(View.VISIBLE);
            feedbackSingleChoiceAdapter = new FeedbackSingleChoiceAdapter(mContext, feedbacktextsinglechoicelist, selectedtextsinglechangesposition, new MorestoryClick() {
                @Override
                public void getmorestoryClick() {
                    for (int i = 0; i < feedbacktextsinglechoicelist.size(); i++) {
                        if (feedbacktextsinglechoicelist.get(i).getQuestionAnswerTextSingleSelect().equalsIgnoreCase("1")) {
                            AppConfiguration.singlechoice = "fill";
                            isRequired = 0;
                            break;
                        } else {
                            AppConfiguration.singlechoice = "not fill";
                        }
                    }
                }
            });
            textsinglechoicelinearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
            activityFeedbackBinding.textsinglechoiceAnsRcv.setLayoutManager(textsinglechoicelinearLayoutManager);
            activityFeedbackBinding.textsinglechoiceAnsRcv.setItemAnimator(new DefaultItemAnimator());
            activityFeedbackBinding.textsinglechoiceAnsRcv.setAdapter(feedbackSingleChoiceAdapter);
        }
    }

    /*Set the TextGridSinglechoice value in layout*/
    public void setTextGridSingleChoiceValue() {
        activityFeedbackBinding.textsinglegridchoiceLinear.setVisibility(View.VISIBLE);

        feedbackanstextsinglechoicegridlist = new ArrayList<>();
        feedbackanstextsinglechoicegridlist.add(new FeedbackAnswerList("10", ""));
        feedbackanstextsinglechoicegridlist.add(new FeedbackAnswerList("9", ""));
        feedbackanstextsinglechoicegridlist.add(new FeedbackAnswerList("8", ""));
        feedbackanstextsinglechoicegridlist.add(new FeedbackAnswerList("7", ""));
        feedbackanstextsinglechoicegridlist.add(new FeedbackAnswerList("6", ""));
        feedbackanstextsinglechoicegridlist.add(new FeedbackAnswerList("5", ""));
        feedbackanstextsinglechoicegridlist.add(new FeedbackAnswerList("4", ""));
        feedbackanstextsinglechoicegridlist.add(new FeedbackAnswerList("3", ""));
        feedbackanstextsinglechoicegridlist.add(new FeedbackAnswerList("2", ""));
        feedbackanstextsinglechoicegridlist.add(new FeedbackAnswerList("1", ""));

        for (int i = 0; i < feedbackanstextsinglechoicegridlist.size(); i++) {
            feedbackanstextsinglechoicegridlist.get(i).setQuestionAnswerTextGridSingleSelect("0");
            if (!feedbackQuestionAnswermodellist.getData().getAnswerValue().equalsIgnoreCase("")) {
                if (feedbackQuestionAnswermodellist.getData().getAnswerValue().equalsIgnoreCase(feedbackanstextsinglechoicegridlist.get(i).getQuestionAnswerText())) {
                    selectedtextsinglegridchangesposition = i;
                    isRequired = 0;
                }
            }

        }
        Log.d("Imagetext :", "" + feedbackanstextsinglechoicegridlist.size());
        feedbackTextSingleChoiceGridAdapter = new FeedbackTextSingleChoiceGridAdapter(mContext,
                feedbackanstextsinglechoicegridlist, selectedtextsinglegridchangesposition, new MorestoryClick() {
            @Override
            public void getmorestoryClick() {
                for (int i = 0; i < feedbackanstextsinglechoicegridlist.size(); i++) {
                    if (feedbackanstextsinglechoicegridlist.get(i).getQuestionAnswerTextGridSingleSelect().equalsIgnoreCase("1")) {
                        AppConfiguration.singlechoice = "fill";
                        isRequired = 0;
                        break;
                    } else {
                        isRequired = 1;
                        AppConfiguration.singlechoice = "not fill";
                    }
                }
            }
        });
        textsinglegridLayoutManager = new GridLayoutManager(mContext, 2);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL); // set Horizontal Orientation
        activityFeedbackBinding.textsinglegridchoiceAnsRcv.setLayoutManager(textsinglegridLayoutManager); // set LayoutManager to RecyclerView
        activityFeedbackBinding.textsinglegridchoiceAnsRcv.setAdapter(feedbackTextSingleChoiceGridAdapter);
    }

    /*Forward Question Layout variable*/
    public void forwardLayoutvalue() {
        if (count != 13) {
            count = count + 1;
            Utils.hideKeyboard(FeedbackActivity.this);
            updateNextCountValueText(count);
            if (count == 2) {
                activityFeedbackBinding.shimmerViewContainer.startShimmerAnimation();
                activityFeedbackBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
                activityFeedbackBinding.questionLinear.setVisibility(View.GONE);
                activityFeedbackBinding.textviewAnsLinear.setVisibility(View.GONE);
                activityFeedbackBinding.nextLinear.setVisibility(View.GONE);
                activityFeedbackBinding.previousLinear.setVisibility(View.GONE);
                currentQuestionIndex = 1;
                nextQuestionIndex = 2;
                callFeedbackQuestionAnswerData();
            } else if (count == 3) {
                getSelectedImageandTextValue();
                activityFeedbackBinding.shimmerViewContainer.startShimmerAnimation();
                activityFeedbackBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
                activityFeedbackBinding.questionLinear.setVisibility(View.GONE);
                activityFeedbackBinding.ImagewithtextsinglechoiceLinear.setVisibility(View.GONE);
                activityFeedbackBinding.nextLinear.setVisibility(View.GONE);
                activityFeedbackBinding.previousLinear.setVisibility(View.GONE);
                currentQuestionIndex = 2;
                nextQuestionIndex = 3;
                callFeedbackQuestionAnswerData();
            } else if (count == 4) {
                activityFeedbackBinding.shimmerViewContainer.startShimmerAnimation();
                activityFeedbackBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
                activityFeedbackBinding.questionLinear.setVisibility(View.GONE);
                activityFeedbackBinding.feedbackScroll.setVisibility(View.GONE);
                activityFeedbackBinding.edittextAnsLinear.setVisibility(View.GONE);
                activityFeedbackBinding.nextLinear.setVisibility(View.GONE);
                activityFeedbackBinding.previousLinear.setVisibility(View.GONE);
                question3ansStr = activityFeedbackBinding.edittextAnsTxt.getText().toString();
                if (question3ansStr.trim().length() >= 1) {
                    AppConfiguration.question3 = "fill";
                    isRequired = 0;
                    feedbackAnswerValueStr = question3ansStr;
                } else {
                    AppConfiguration.question3 = "";
                    feedbackAnswerValueStr = "";
                    isRequired = 1;
                }
                currentQuestionIndex = 3;
                nextQuestionIndex = 4;
                callFeedbackQuestionAnswerData();
            } else if (count == 5) {
                activityFeedbackBinding.shimmerViewContainer.startShimmerAnimation();
                activityFeedbackBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
                activityFeedbackBinding.questionLinear.setVisibility(View.GONE);
                activityFeedbackBinding.feedbackScroll.setVisibility(View.GONE);
                activityFeedbackBinding.edittextAnsLinear.setVisibility(View.GONE);
                activityFeedbackBinding.nextLinear.setVisibility(View.GONE);
                activityFeedbackBinding.previousLinear.setVisibility(View.GONE);
                question4ansStr = activityFeedbackBinding.edittextAnsTxt.getText().toString();
                if (question4ansStr.trim().length() >= 1) {
                    AppConfiguration.question4 = "fill";
                    feedbackAnswerValueStr = question4ansStr.trim();
                    isRequired = 0;
                } else {
                    AppConfiguration.question4 = "";
                    feedbackAnswerValueStr = "";
                    isRequired = 1;
                }
                currentQuestionIndex = 4;
                nextQuestionIndex = 5;
                callFeedbackQuestionAnswerData();
            } else if (count == 6) {
                activityFeedbackBinding.shimmerViewContainer.startShimmerAnimation();
                activityFeedbackBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
                activityFeedbackBinding.questionLinear.setVisibility(View.GONE);
                activityFeedbackBinding.feedbackScroll.setVisibility(View.GONE);
                activityFeedbackBinding.edittextAnsLinear.setVisibility(View.GONE);
                activityFeedbackBinding.nextLinear.setVisibility(View.GONE);
                activityFeedbackBinding.previousLinear.setVisibility(View.GONE);
                question5ansStr = activityFeedbackBinding.edittextAnsTxt.getText().toString();
                if (question5ansStr.trim().length() >= 1) {
                    AppConfiguration.question5 = "fill";
                    isRequired = 0;
                    feedbackAnswerValueStr = question5ansStr;
                } else {
                    AppConfiguration.question5 = "";
                    isRequired = 1;
                    feedbackAnswerValueStr = "";
                }
                currentQuestionIndex = 5;
                nextQuestionIndex = 6;
                callFeedbackQuestionAnswerData();
            } else if (count == 7) {
                activityFeedbackBinding.shimmerViewContainer.startShimmerAnimation();
                activityFeedbackBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
                activityFeedbackBinding.questionLinear.setVisibility(View.GONE);
                activityFeedbackBinding.feedbackScroll.setVisibility(View.GONE);
                activityFeedbackBinding.edittextAnsLinear.setVisibility(View.GONE);
                activityFeedbackBinding.nextLinear.setVisibility(View.GONE);
                activityFeedbackBinding.previousLinear.setVisibility(View.GONE);
                question6ansStr = activityFeedbackBinding.edittextAnsTxt.getText().toString();
                if (question6ansStr.trim().length() >= 1) {
                    AppConfiguration.question6 = "fill";
                    isRequired = 0;
                    feedbackAnswerValueStr = question6ansStr;
                } else {
                    AppConfiguration.question6 = "";
                    isRequired = 1;
                    feedbackAnswerValueStr = "";
                }
                currentQuestionIndex = 6;
                nextQuestionIndex = 7;
                callFeedbackQuestionAnswerData();
            } else if (count == 8) {
                getSelectedTextMultiChoiceValue();
                activityFeedbackBinding.shimmerViewContainer.startShimmerAnimation();
                activityFeedbackBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
                activityFeedbackBinding.questionLinear.setVisibility(View.GONE);
                activityFeedbackBinding.textmultichoiceLinear.setVisibility(View.GONE);
                activityFeedbackBinding.nextLinear.setVisibility(View.GONE);
                activityFeedbackBinding.previousLinear.setVisibility(View.GONE);
                currentQuestionIndex = 7;
                nextQuestionIndex = 8;
                callFeedbackQuestionAnswerData();

            } else if (count == 9) {
                activityFeedbackBinding.shimmerViewContainer.startShimmerAnimation();
                activityFeedbackBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
                activityFeedbackBinding.questionLinear.setVisibility(View.GONE);
                activityFeedbackBinding.feedbackScroll.setVisibility(View.GONE);
                activityFeedbackBinding.edittextAnsLinear.setVisibility(View.GONE);
                activityFeedbackBinding.nextLinear.setVisibility(View.GONE);
                activityFeedbackBinding.previousLinear.setVisibility(View.GONE);
                question8ansStr = activityFeedbackBinding.edittextAnsTxt.getText().toString();
                if (question8ansStr.trim().length() >= 1) {
                    AppConfiguration.question8 = "fill";
                    isRequired = 0;
                    feedbackAnswerValueStr = question8ansStr;
                } else {
                    AppConfiguration.question8 = "";
                    isRequired = 1;
                    feedbackAnswerValueStr = "";
                }
                currentQuestionIndex = 8;
                nextQuestionIndex = 9;
                callFeedbackQuestionAnswerData();
            } else if (count == 10) {
                getSelectedRatingSingleChoiceValue();
                activityFeedbackBinding.shimmerViewContainer.startShimmerAnimation();
                activityFeedbackBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
                activityFeedbackBinding.questionLinear.setVisibility(View.GONE);
                activityFeedbackBinding.textwithsingleratingchoiceLinear.setVisibility(View.GONE);
                activityFeedbackBinding.nextLinear.setVisibility(View.GONE);
                activityFeedbackBinding.previousLinear.setVisibility(View.GONE);
                currentQuestionIndex = 9;
                nextQuestionIndex = 10;
                callFeedbackQuestionAnswerData();
            } else if (count == 11) {
                getSelectedTextGridSingleChoiceValue();
                activityFeedbackBinding.shimmerViewContainer.startShimmerAnimation();
                activityFeedbackBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
                activityFeedbackBinding.questionLinear.setVisibility(View.GONE);
                activityFeedbackBinding.textsinglegridchoiceLinear.setVisibility(View.GONE);
                activityFeedbackBinding.nextLinear.setVisibility(View.GONE);
                activityFeedbackBinding.previousLinear.setVisibility(View.GONE);
                currentQuestionIndex = 10;
                nextQuestionIndex = 11;
                callFeedbackQuestionAnswerData();
            } else if (count == 12) {
                getSelectedTextSingleChoiceValue();
                activityFeedbackBinding.shimmerViewContainer.startShimmerAnimation();
                activityFeedbackBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
                activityFeedbackBinding.questionLinear.setVisibility(View.GONE);
                activityFeedbackBinding.textsinglechoiceLinear.setVisibility(View.GONE);
                activityFeedbackBinding.nextLinear.setVisibility(View.GONE);
                activityFeedbackBinding.previousLinear.setVisibility(View.GONE);
                currentQuestionIndex = 11;
                nextQuestionIndex = 12;
                callFeedbackQuestionAnswerData();
            } else if (count == 13) {
                activityFeedbackBinding.shimmerViewContainer.startShimmerAnimation();
                activityFeedbackBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
                activityFeedbackBinding.questionLinear.setVisibility(View.GONE);
                activityFeedbackBinding.feedbackScroll.setVisibility(View.GONE);
                activityFeedbackBinding.edittextAnsLinear.setVisibility(View.GONE);
                activityFeedbackBinding.nextLinear.setVisibility(View.GONE);
                activityFeedbackBinding.previousLinear.setVisibility(View.GONE);
                question12ansStr = activityFeedbackBinding.edittextAnsTxt.getText().toString();
                if (question12ansStr.trim().length() >= 1) {
                    AppConfiguration.question12 = "fill";
                    isRequired = 0;
                    feedbackAnswerValueStr = question12ansStr;
                } else {
                    AppConfiguration.question12 = "";
                    isRequired = 1;
                    feedbackAnswerValueStr = "";
                }
                currentQuestionIndex = 12;
                nextQuestionIndex = 13;
                callFeedbackQuestionAnswerData();
            }
        } else {
            Utils.hideKeyboard(FeedbackActivity.this);
            question13ansStr = activityFeedbackBinding.edittextAnsTxt.getText().toString();
            if (question13ansStr.trim().length() >= 1) {
                AppConfiguration.question13 = "fill";
                isRequired = 0;
                feedbackAnswerValueStr = question13ansStr;
            } else {
                AppConfiguration.question13 = "";
                isRequired = 1;
                feedbackAnswerValueStr = "";
            }
            currentQuestionIndex = 13;
            nextQuestionIndex = 0;
            callFeedbackQuestionAnswerData();

        }

    }

    /*Previous Question Layout variable*/
    public void previousLayoutvalue() {
        if (count != 1) {
            count = count - 1;
            Utils.hideKeyboard(FeedbackActivity.this);
            updateNextCountValueText(count);
            currentQuestionId = 0;
            if (count == 1) {
                getSelectedImageandTextValue();
                activityFeedbackBinding.shimmerViewContainer.startShimmerAnimation();
                activityFeedbackBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
                activityFeedbackBinding.questionLinear.setVisibility(View.GONE);
                activityFeedbackBinding.ImagewithtextsinglechoiceLinear.setVisibility(View.GONE);
                activityFeedbackBinding.previousLinear.setVisibility(View.GONE);
                activityFeedbackBinding.nextLinear.setVisibility(View.GONE);
                currentQuestionIndex = 1;
                nextQuestionIndex = 0;
                callFeedbackQuestionAnswerData();
            } else if (count == 2) {
                activityFeedbackBinding.shimmerViewContainer.startShimmerAnimation();
                activityFeedbackBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
                activityFeedbackBinding.questionLinear.setVisibility(View.GONE);
                activityFeedbackBinding.feedbackScroll.setVisibility(View.GONE);
                activityFeedbackBinding.edittextAnsLinear.setVisibility(View.GONE);
                activityFeedbackBinding.previousLinear.setVisibility(View.GONE);
                activityFeedbackBinding.nextLinear.setVisibility(View.GONE);
                currentQuestionIndex = 2;
                nextQuestionIndex = 0;
                callFeedbackQuestionAnswerData();
            } else if (count == 3) {
                activityFeedbackBinding.shimmerViewContainer.startShimmerAnimation();
                activityFeedbackBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
                activityFeedbackBinding.questionLinear.setVisibility(View.GONE);
                activityFeedbackBinding.feedbackScroll.setVisibility(View.GONE);
                activityFeedbackBinding.edittextAnsLinear.setVisibility(View.GONE);
                activityFeedbackBinding.previousLinear.setVisibility(View.GONE);
                activityFeedbackBinding.nextLinear.setVisibility(View.GONE);
                currentQuestionIndex = 3;
                nextQuestionIndex = 0;
                callFeedbackQuestionAnswerData();
            } else if (count == 4) {
                activityFeedbackBinding.shimmerViewContainer.startShimmerAnimation();
                activityFeedbackBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
                activityFeedbackBinding.questionLinear.setVisibility(View.GONE);
                activityFeedbackBinding.feedbackScroll.setVisibility(View.GONE);
                activityFeedbackBinding.edittextAnsLinear.setVisibility(View.GONE);
                activityFeedbackBinding.previousLinear.setVisibility(View.GONE);
                activityFeedbackBinding.nextLinear.setVisibility(View.GONE);
                if (question3ansStr.trim().length() >= 1) {
                    AppConfiguration.question3 = "fill";
                } else {
                    AppConfiguration.question3 = "";
                }
                currentQuestionIndex = 4;
                nextQuestionIndex = 0;
                callFeedbackQuestionAnswerData();
            } else if (count == 5) {
                activityFeedbackBinding.shimmerViewContainer.startShimmerAnimation();
                activityFeedbackBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
                activityFeedbackBinding.questionLinear.setVisibility(View.GONE);
                activityFeedbackBinding.feedbackScroll.setVisibility(View.GONE);
                activityFeedbackBinding.edittextAnsLinear.setVisibility(View.GONE);
                activityFeedbackBinding.previousLinear.setVisibility(View.GONE);
                activityFeedbackBinding.nextLinear.setVisibility(View.GONE);
                if (question4ansStr.trim().length() >= 1) {
                    AppConfiguration.question4 = "fill";
                } else {
                    AppConfiguration.question4 = "";
                }
                currentQuestionIndex = 5;
                nextQuestionIndex = 0;
                callFeedbackQuestionAnswerData();
            } else if (count == 6) {
                activityFeedbackBinding.shimmerViewContainer.startShimmerAnimation();
                activityFeedbackBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
                activityFeedbackBinding.questionLinear.setVisibility(View.GONE);
                activityFeedbackBinding.textmultichoiceLinear.setVisibility(View.GONE);
                activityFeedbackBinding.previousLinear.setVisibility(View.GONE);
                activityFeedbackBinding.nextLinear.setVisibility(View.GONE);
                if (question5ansStr.trim().length() >= 1) {
                    AppConfiguration.question5 = "fill";
                } else {
                    AppConfiguration.question5 = "";
                }
                currentQuestionIndex = 6;
                nextQuestionIndex = 0;
                callFeedbackQuestionAnswerData();
            } else if (count == 7) {
                activityFeedbackBinding.shimmerViewContainer.startShimmerAnimation();
                activityFeedbackBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
                activityFeedbackBinding.questionLinear.setVisibility(View.GONE);
                activityFeedbackBinding.feedbackScroll.setVisibility(View.GONE);
                activityFeedbackBinding.edittextAnsLinear.setVisibility(View.GONE);
                activityFeedbackBinding.previousLinear.setVisibility(View.GONE);
                activityFeedbackBinding.nextLinear.setVisibility(View.GONE);
                if (question6ansStr.trim().length() >= 1) {
                    AppConfiguration.question6 = "fill";
                } else {
                    AppConfiguration.question6 = "";
                }
                currentQuestionIndex = 7;
                nextQuestionIndex = 0;
                callFeedbackQuestionAnswerData();
            } else if (count == 8) {
                activityFeedbackBinding.shimmerViewContainer.startShimmerAnimation();
                activityFeedbackBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
                activityFeedbackBinding.questionLinear.setVisibility(View.GONE);
                activityFeedbackBinding.textwithsingleratingchoiceLinear.setVisibility(View.GONE);
                activityFeedbackBinding.previousLinear.setVisibility(View.GONE);
                activityFeedbackBinding.nextLinear.setVisibility(View.GONE);
                currentQuestionIndex = 8;
                nextQuestionIndex = 0;
                callFeedbackQuestionAnswerData();
            } else if (count == 9) {
                activityFeedbackBinding.shimmerViewContainer.startShimmerAnimation();
                activityFeedbackBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
                activityFeedbackBinding.questionLinear.setVisibility(View.GONE);
                activityFeedbackBinding.textsinglegridchoiceLinear.setVisibility(View.GONE);
                activityFeedbackBinding.previousLinear.setVisibility(View.GONE);
                activityFeedbackBinding.nextLinear.setVisibility(View.GONE);
                if (question8ansStr.trim().length() >= 1) {
                    AppConfiguration.question8 = "fill";
                } else {
                    AppConfiguration.question8 = "";
                }
                currentQuestionIndex = 9;
                nextQuestionIndex = 0;
                callFeedbackQuestionAnswerData();
            } else if (count == 10) {
                activityFeedbackBinding.shimmerViewContainer.startShimmerAnimation();
                activityFeedbackBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
                activityFeedbackBinding.questionLinear.setVisibility(View.GONE);
                activityFeedbackBinding.textsinglechoiceLinear.setVisibility(View.GONE);
                activityFeedbackBinding.edittextAnsLinear.setVisibility(View.GONE);
                activityFeedbackBinding.previousLinear.setVisibility(View.GONE);
                activityFeedbackBinding.nextLinear.setVisibility(View.GONE);
                currentQuestionIndex = 10;
                nextQuestionIndex = 0;
                callFeedbackQuestionAnswerData();
            } else if (count == 11) {
                activityFeedbackBinding.shimmerViewContainer.startShimmerAnimation();
                activityFeedbackBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
                activityFeedbackBinding.questionLinear.setVisibility(View.GONE);
                activityFeedbackBinding.feedbackScroll.setVisibility(View.GONE);
                activityFeedbackBinding.edittextAnsLinear.setVisibility(View.GONE);
                activityFeedbackBinding.previousLinear.setVisibility(View.GONE);
                activityFeedbackBinding.nextLinear.setVisibility(View.GONE);
                currentQuestionIndex = 11;
                nextQuestionIndex = 0;
                callFeedbackQuestionAnswerData();

            } else if (count == 12) {
                activityFeedbackBinding.shimmerViewContainer.startShimmerAnimation();
                activityFeedbackBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
                activityFeedbackBinding.questionLinear.setVisibility(View.GONE);
                activityFeedbackBinding.feedbackScroll.setVisibility(View.GONE);
                activityFeedbackBinding.edittextAnsLinear.setVisibility(View.GONE);
                activityFeedbackBinding.previousLinear.setVisibility(View.GONE);
                activityFeedbackBinding.nextLinear.setVisibility(View.GONE);
                currentQuestionIndex = 12;
                nextQuestionIndex = 0;
                callFeedbackQuestionAnswerData();
            } else if (count == 13) {
                activityFeedbackBinding.shimmerViewContainer.startShimmerAnimation();
                activityFeedbackBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
                activityFeedbackBinding.questionLinear.setVisibility(View.GONE);
                activityFeedbackBinding.feedbackScroll.setVisibility(View.GONE);
                activityFeedbackBinding.edittextAnsLinear.setVisibility(View.GONE);
                activityFeedbackBinding.previousLinear.setVisibility(View.GONE);
                activityFeedbackBinding.nextLinear.setVisibility(View.GONE);
                if (question12ansStr.trim().length() >= 1) {
                    AppConfiguration.question12 = "fill";
                } else {
                    AppConfiguration.question12 = "";
                }
                currentQuestionIndex = 13;
                nextQuestionIndex = 0;
                callFeedbackQuestionAnswerData();
            }
        } else {


        }
    }

    /*Get the selected value in list*/
    public void getSelectedImageandTextValue() {
        for (int i = 0; i < feedbackansimagetextmodellist.size(); i++) {
            if (feedbackansimagetextmodellist.get(i).getQuestionAnswerImagewithTextSelect().equalsIgnoreCase("1")) {
                question2ansStr = feedbackansimagetextmodellist.get(i).getText();
                question2ansimageStr = feedbackansimagetextmodellist.get(i).getImageUrl();
                isRequired = 0;
                feedbackAnswerValueStr = question2ansStr;
//                break;
            }
        }
    }

    public void getSelectedTextMultiChoiceValue() {
        ArrayList<String> selectedquestionanslist = new ArrayList<>();
        for (int i = 0; i < feedbacktextmultichoicelist.size(); i++) {
            if (feedbacktextmultichoicelist.get(i).getQuestionAnswerTextMultiSelect().equalsIgnoreCase("1")) {
                selectedquestionanslist.add(feedbacktextmultichoicelist.get(i).getText());
                isRequired = 0;
            }
        }
        question7ansStr = "";
        if (selectedquestionanslist.size() != 0) {
            for (String s : selectedquestionanslist) {
                question7ansStr = question7ansStr + ", " + s;
            }
            Log.d("question7ansStr", question7ansStr);
            question7ansStr = question7ansStr.substring(1, question7ansStr.length());
            Log.d("finalansweransStr", question7ansStr);

            feedbackAnswerValueStr = question7ansStr;
        }
    }

    public void getSelectedRatingSingleChoiceValue() {
        for (int i = 0; i < feedbackratingsinglechoicelist.size(); i++) {
            if (feedbackratingsinglechoicelist.get(i).getQuestionAnswerRatingSelect().equalsIgnoreCase("1")) {
                question9ansStr = feedbackratingsinglechoicelist.get(i).getQuestionAnswerText();
                question9ansratingStr = feedbackratingsinglechoicelist.get(i).getQuestionAnswerRating();
                isRequired = 0;
                feedbackAnswerValueStr = question9ansratingStr;
//                break;
            }
        }
    }

    public void getSelectedTextSingleChoiceValue() {
        for (int i = 0; i < feedbacktextsinglechoicelist.size(); i++) {
            if (feedbacktextsinglechoicelist.get(i).getQuestionAnswerTextSingleSelect().equalsIgnoreCase("1")) {
                question11ansStr = feedbacktextsinglechoicelist.get(i).getQuestionAnswerText();
                isRequired = 0;
                feedbackAnswerValueStr = question11ansStr;
//                break;
            }
        }
    }

    public void getSelectedTextGridSingleChoiceValue() {
        for (int i = 0; i < feedbackanstextsinglechoicegridlist.size(); i++) {
            if (feedbackanstextsinglechoicegridlist.get(i).getQuestionAnswerTextGridSingleSelect().equalsIgnoreCase("1")) {
                question10ansStr = feedbackanstextsinglechoicegridlist.get(i).getQuestionAnswerText();
                isRequired = 0;
                feedbackAnswerValueStr = question10ansStr;
//                break;
            }
        }
    }

    /* Call the question answer list*/
    public void callFeedbackQuestionAnswerData() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), FeedbackActivity.this);
            return;
        }

//        Utils.showDialog(mContext);

        ApiHandler.getApiService().getFeedback(getquestionanswerDetailData(), new retrofit.Callback<FeedbackMainModel>() {
            @Override
            public void success(FeedbackMainModel feedbackMainModel, Response response) {
                Utils.dismissDialog();
                if (feedbackMainModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (feedbackMainModel.getIsValid() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (feedbackMainModel.getIsValid() == 0) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    return;
                }
                if (feedbackMainModel.getIsValid() == 1) {
                    isUpdateAvailable = String.valueOf(feedbackMainModel.getIsUpdateAvailable());
                    isForceUpdateAvailable = String.valueOf(feedbackMainModel.getIsForceUpdate());
//                    isForceUpdateAvailable = "0";
                    currentVersionStr = String.valueOf(feedbackMainModel.getCurrentVersion());
                    if (isUpdateAvailable.equalsIgnoreCase("1")) {
                        Utils.checkupdateApplication(mContext, FeedbackActivity.this, isForceUpdateAvailable, currentVersionStr);
                    }
                    if (feedbackMainModel.getData() != null) {
                        feedbackQuestionAnswermodellist = feedbackMainModel;
                        if (currentQuestionIndex == 13 && nextQuestionIndex == 0) {
                            activityFeedbackBinding.feedbackScroll.setVisibility(View.GONE);
                            activityFeedbackBinding.edittextAnsLinear.setVisibility(View.GONE);
                            activityFeedbackBinding.previousLinear.setVisibility(View.GONE);
                            activityFeedbackBinding.nextLinear.setVisibility(View.GONE);
                            activityFeedbackBinding.questionLinear.setVisibility(View.GONE);
                            /*Animation for visibility gone*/
                            Animation animSlideout = AnimationUtils.loadAnimation(mContext, R.anim.slide_out_left_new);
                            activityFeedbackBinding.questionLinear.startAnimation(animSlideout);

                            activityFeedbackBinding.thankyouLinear.setVisibility(View.VISIBLE);
//                    /*Animation for visibility visible*/
                            Animation animSlidein = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_right_new);
                            activityFeedbackBinding.thankyouLinear.startAnimation(animSlidein);
                        } else {
                            activityFeedbackBinding.shimmerViewContainer.stopShimmerAnimation();
                            activityFeedbackBinding.shimmerViewContainer.setVisibility(View.GONE);
                            activityFeedbackBinding.feedbackScroll.setVisibility(View.VISIBLE);
                            activityFeedbackBinding.questionLinear.setVisibility(View.VISIBLE);
                            activityFeedbackBinding.edittextAnsTxt.setText("");
                            totalLayout = feedbackQuestionAnswermodellist.getRecordCount();
                            currentQuestionId = feedbackQuestionAnswermodellist.getData().getId();
                            if (count == 1) {
                                activityFeedbackBinding.previousLinear.setVisibility(View.GONE);
                            } else {
                                activityFeedbackBinding.previousLinear.setVisibility(View.VISIBLE);
                            }
                            updateNextCountValueText(count);
                            activityFeedbackBinding.nextLinear.setVisibility(View.VISIBLE);
                            setQuestionAnswer();
                        }
                    } else if (feedbackMainModel.getOtherData() != null) {
                        if (feedbackMainModel.getOtherData().size() != 0) {
                            feedbackviewanslist = feedbackMainModel.getOtherData();
                            setSelectedValueInList();

                        }
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

    private Map<String, String> getquestionanswerDetailData() {
        Map<String, String> map = new HashMap<>();
        map.put("CurrentIndex", String.valueOf(currentQuestionIndex));
        map.put("NextIndex", String.valueOf(nextQuestionIndex));
        map.put("FeedbackValue", feedbackAnswerValueStr);
        map.put("AppUserId", String.valueOf(Utils.getAppUserId(mContext)));
        map.put("CurrentId", String.valueOf(currentQuestionId));

        return map;
    }


    /*quite feedback survey*/
    public void quitefeedbacksurvey() {
        AppConfiguration.question3 = "";
        AppConfiguration.question4 = "";
        AppConfiguration.question5 = "";
        AppConfiguration.question6 = "";
        AppConfiguration.question8 = "";
        AppConfiguration.question12 = "";
        AppConfiguration.question13 = "";

        AppConfiguration.multichoice = "not fill";
        AppConfiguration.singlechoice = "not fill";
        AppConfiguration.imagechoice = "not fill";
        AppConfiguration.addtextchoice = "not fill";

    }

    /*stay feedback survey*/
    public void stayfeedbacksurvey() {
        AppConfiguration.question3 = "1";
        AppConfiguration.question4 = "1";
        AppConfiguration.question5 = "1";
        AppConfiguration.question6 = "1";
        AppConfiguration.question8 = "1";
        AppConfiguration.question12 = "1";
        AppConfiguration.question13 = "1";

        AppConfiguration.multichoice = "fill";
        AppConfiguration.singlechoice = "fill";
        AppConfiguration.imagechoice = "fill";
        AppConfiguration.addtextchoice = "fill";

    }


    public void callQuestionAnswerList() {
        currentQuestionIndex = -1;
        nextQuestionIndex = 0;
        currentQuestionId = 0;
        callFeedbackQuestionAnswerData();
    }
}
