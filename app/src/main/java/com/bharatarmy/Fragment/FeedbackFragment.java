package com.bharatarmy.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;

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
import com.bharatarmy.R;
import com.bharatarmy.Utility.ApiHandler;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.FragmentFeedbackBinding;
import com.bharatarmy.speeddialView.SpeedDialView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.RetrofitError;
import retrofit.client.Response;

// remove extra code 04/04/2020 with backup

public class FeedbackFragment extends Fragment implements View.OnClickListener {
    /*Fragment variable*/
    private View rootView;
    private Context mContext;
    FragmentFeedbackBinding fragmentFeedbackBinding;
    SpeedDialView speedDial;
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

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FeedbackFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FeedbackFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FeedbackFragment newInstance(String param1, String param2) {
        FeedbackFragment fragment = new FeedbackFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentFeedbackBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_feedback, container, false);

        rootView = fragmentFeedbackBinding.getRoot();
        mContext = getActivity().getApplicationContext();


        speedDial = getActivity().findViewById(R.id.speedDial);
        speedDial.setVisibility(View.GONE);


        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        init();
        setListiner();
    }

    public void init() {
        fragmentFeedbackBinding.shimmerViewContainer.startShimmerAnimation();
        fragmentFeedbackBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
        callQuestionAnswerList();
    }

    public void setListiner() {
        fragmentFeedbackBinding.nextLinear.setOnClickListener(this);
        fragmentFeedbackBinding.previousLinear.setOnClickListener(this);
        fragmentFeedbackBinding.editLinear.setOnClickListener(this);
        fragmentFeedbackBinding.viewFeedbackBtn.setOnClickListener(this);

        fragmentFeedbackBinding.edittextAnsTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Integer textlength1 = fragmentFeedbackBinding.edittextAnsTxt.getText().length();


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

        fragmentFeedbackBinding.edittextAnsTxt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    //do your stuff here
                    fragmentFeedbackBinding.scrollView.fullScroll(View.FOCUS_DOWN);
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
                Utils.hideKeyboard(getActivity());
                fragmentFeedbackBinding.thankyouLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.previousLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.nextLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.questionLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.scrollView.setVisibility(View.GONE);
                fragmentFeedbackBinding.edittextAnsLinear.setVisibility(View.GONE);

                fragmentFeedbackBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
                fragmentFeedbackBinding.shimmerViewContainer.startShimmerAnimation();
                feedbackAnswerValueStr = "";
                callQuestionAnswerList();
//                answerstoreLocal();


                break;
            case R.id.edit_linear:
                stayfeedbacksurvey();
                fragmentFeedbackBinding.thankyouLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.questionAnsViewLinear.setVisibility(View.GONE);
                /*Animation for visibility gone*/
                Animation animSlideoute = AnimationUtils.loadAnimation(mContext, R.anim.slide_out_left_new);
                fragmentFeedbackBinding.questionAnsViewLinear.startAnimation(animSlideoute);

                fragmentFeedbackBinding.editLinear.setVisibility(View.GONE);
//                if (count == 1) {
                fragmentFeedbackBinding.previousLinear.setVisibility(View.GONE);
//                } else {
//                    fragmentFeedbackBinding.previousLinear.setVisibility(View.VISIBLE);
//                }
                fragmentFeedbackBinding.nextLinear.setVisibility(View.VISIBLE);
                fragmentFeedbackBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
                fragmentFeedbackBinding.shimmerViewContainer.startShimmerAnimation();
                /*Animation for visibility visible*/
                Animation animSlideine = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_right_new);
                fragmentFeedbackBinding.shimmerViewContainer.startAnimation(animSlideine);
                count = 1;
                currentQuestionIndex = 1;
                nextQuestionIndex = 0;
                callFeedbackQuestionAnswerData();

                fragmentFeedbackBinding.nextImg.setImageResource(R.drawable.ic_next_question);
                break;
        }

    }


    public void setQuestionAnswer() {

        isRequired = feedbackQuestionAnswermodellist.getData().getIsRequired();

        fragmentFeedbackBinding.questionCategoryTitleTxt.setText(feedbackQuestionAnswermodellist.getData().getHeaderTypeText());
        fragmentFeedbackBinding.questionTxt.setText(feedbackQuestionAnswermodellist.getData().getFeedbackQuestion());
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
            fragmentFeedbackBinding.submitTxt.setText("0" + String.valueOf(count) + " of " + String.valueOf(totalLayout));
        } else {
            fragmentFeedbackBinding.submitTxt.setText(String.valueOf(count) + " of " + String.valueOf(totalLayout));
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
                fragmentFeedbackBinding.shimmerViewContainer.stopShimmerAnimation();
                fragmentFeedbackBinding.shimmerViewContainer.setVisibility(View.GONE);
                /*Animation for visibility gone*/
                Animation animSlideout = AnimationUtils.loadAnimation(mContext, R.anim.slide_out_left_new);
                fragmentFeedbackBinding.shimmerViewContainer.startAnimation(animSlideout);

                fragmentFeedbackBinding.questionAnsViewLinear.setVisibility(View.VISIBLE);
                fragmentFeedbackBinding.editLinear.setVisibility(View.VISIBLE);
                /*Animation for visibility visible*/
                Animation animSlidein = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_right_new);
                fragmentFeedbackBinding.questionAnsViewLinear.startAnimation(animSlidein);
                Log.d("imageStr", feedbackansimageStr);
                feedbackViewAdapter = new FeedbackViewAdapter(mContext, feedbackviewanslist, feedbackansimageStr);
                ansviewmLayout = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
                fragmentFeedbackBinding.questionAnsViewRcv.setLayoutManager(ansviewmLayout);
                fragmentFeedbackBinding.questionAnsViewRcv.setItemAnimator(new DefaultItemAnimator());
                fragmentFeedbackBinding.questionAnsViewRcv.setAdapter(feedbackViewAdapter);
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
//        fragmentFeedbackBinding.textviewAnsLinear.setVisibility(View.VISIBLE);
        String textUrl = "<html><style type='text/css'>@font-face { font-family: thesansplain; src: url('fonts/thesansplain.ttf'); } body p {font-family: thesansplain;}</style>"
                + "<body >" + "<p align=\"justify\" style=\"font-size: 22px; font-family: spqr;\">" + text + "</p> " + "</body></html>";


        fragmentFeedbackBinding.textviewAnsTxt.getSettings().setJavaScriptEnabled(true);

        fragmentFeedbackBinding.textviewAnsTxt.setVerticalScrollBarEnabled(false);
        fragmentFeedbackBinding.textviewAnsTxt.loadDataWithBaseURL("file:///android_asset/", textUrl, "text/html", "UTF-8", null);


        fragmentFeedbackBinding.textviewAnsTxt.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                return false; // for the WebView to load the URL
            }
        });

    }

    /*Set the ImageandTextSinglechoice value in layout*/
    public void setImageandTextValue() {
        feedbackansimagetextmodellist = feedbackQuestionAnswermodellist.getData().getOptions();
        fragmentFeedbackBinding.ImagewithtextsinglechoiceLinear.setVisibility(View.VISIBLE);
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
        fragmentFeedbackBinding.ImagewithtextsinglechoiceAnsRcv.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
        fragmentFeedbackBinding.ImagewithtextsinglechoiceAnsRcv.setAdapter(feedbackImagewithTextAdapter);
    }

    /*Set the Edittext value in layout*/
    public void setEditextValue() {
        fragmentFeedbackBinding.scrollView.setVisibility(View.VISIBLE);
        fragmentFeedbackBinding.edittextAnsLinear.setVisibility(View.VISIBLE);

        if (feedbackQuestionAnswermodellist.getData().getId().equals(3)) {
            if (!feedbackQuestionAnswermodellist.getData().getAnswerValue().equalsIgnoreCase("")) {
                fragmentFeedbackBinding.edittextAnsTxt.setText(feedbackQuestionAnswermodellist.getData().getAnswerValue());
            }
        } else if (feedbackQuestionAnswermodellist.getData().getId().equals(4)) {
            if (!feedbackQuestionAnswermodellist.getData().getAnswerValue().equalsIgnoreCase("")) {
                fragmentFeedbackBinding.edittextAnsTxt.setText(feedbackQuestionAnswermodellist.getData().getAnswerValue());
            }
        } else if (feedbackQuestionAnswermodellist.getData().getId().equals(5)) {
            if (!feedbackQuestionAnswermodellist.getData().getAnswerValue().equalsIgnoreCase("")) {
                fragmentFeedbackBinding.edittextAnsTxt.setText(feedbackQuestionAnswermodellist.getData().getAnswerValue());
            }
        } else if (feedbackQuestionAnswermodellist.getData().getId().equals(6)) {
            if (!feedbackQuestionAnswermodellist.getData().getAnswerValue().equalsIgnoreCase("")) {
                fragmentFeedbackBinding.edittextAnsTxt.setText(feedbackQuestionAnswermodellist.getData().getAnswerValue());
            }
        } else if (feedbackQuestionAnswermodellist.getData().getId().equals(8)) {
            if (!feedbackQuestionAnswermodellist.getData().getAnswerValue().equalsIgnoreCase("")) {
                fragmentFeedbackBinding.edittextAnsTxt.setText(feedbackQuestionAnswermodellist.getData().getAnswerValue());
            }
        } else if (feedbackQuestionAnswermodellist.getData().getId().equals(12)) {
            if (!feedbackQuestionAnswermodellist.getData().getAnswerValue().equalsIgnoreCase("")) {
                fragmentFeedbackBinding.edittextAnsTxt.setText(feedbackQuestionAnswermodellist.getData().getAnswerValue());
            }
        } else if (feedbackQuestionAnswermodellist.getData().getId().equals(13)) {
            if (!feedbackQuestionAnswermodellist.getData().getAnswerValue().equalsIgnoreCase("")) {
                fragmentFeedbackBinding.edittextAnsTxt.setText(feedbackQuestionAnswermodellist.getData().getAnswerValue());
            }
        }
    }

    /*Set the TextMultiChoice value in layout*/
    public void setTextMultiChoiceValue() {
        feedbacktextmultichoicelist = feedbackQuestionAnswermodellist.getData().getOptions();
        fragmentFeedbackBinding.textmultichoiceLinear.setVisibility(View.VISIBLE);
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
        fragmentFeedbackBinding.textmultichoiceAnsRcv.setLayoutManager(textmultichoicelinearLayoutManager);
        fragmentFeedbackBinding.textmultichoiceAnsRcv.setItemAnimator(new DefaultItemAnimator());
        fragmentFeedbackBinding.textmultichoiceAnsRcv.setAdapter(feedbackTextMultiChoiceAdapter);
    }

    /*Set the TextWithRatingSignleChoice value in layout*/
    public void setTextWithRatingSingleChoiceValue() {
        fragmentFeedbackBinding.textwithsingleratingchoiceLinear.setVisibility(View.VISIBLE);

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
        fragmentFeedbackBinding.textwithsingleratingchoiceAnsRcv.setLayoutManager(ratingsinglechoicelinearLayoutManager);
        fragmentFeedbackBinding.textwithsingleratingchoiceAnsRcv.setItemAnimator(new DefaultItemAnimator());
        fragmentFeedbackBinding.textwithsingleratingchoiceAnsRcv.setAdapter(feedbackRatingAdapter);
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

            fragmentFeedbackBinding.textsinglechoiceLinear.setVisibility(View.VISIBLE);
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
            fragmentFeedbackBinding.textsinglechoiceAnsRcv.setLayoutManager(textsinglechoicelinearLayoutManager);
            fragmentFeedbackBinding.textsinglechoiceAnsRcv.setItemAnimator(new DefaultItemAnimator());
            fragmentFeedbackBinding.textsinglechoiceAnsRcv.setAdapter(feedbackSingleChoiceAdapter);
        }
    }

    /*Set the TextGridSinglechoice value in layout*/
    public void setTextGridSingleChoiceValue() {
        fragmentFeedbackBinding.textsinglegridchoiceLinear.setVisibility(View.VISIBLE);

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
        fragmentFeedbackBinding.textsinglegridchoiceAnsRcv.setLayoutManager(textsinglegridLayoutManager); // set LayoutManager to RecyclerView
        fragmentFeedbackBinding.textsinglegridchoiceAnsRcv.setAdapter(feedbackTextSingleChoiceGridAdapter);
    }

    /*Forward Question Layout variable*/
    public void forwardLayoutvalue() {
        if (count != 13) {
            count = count + 1;
            Utils.hideKeyboard(getActivity());
            updateNextCountValueText(count);
            if (count == 2) {
                fragmentFeedbackBinding.shimmerViewContainer.startShimmerAnimation();
                fragmentFeedbackBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
                fragmentFeedbackBinding.questionLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.textviewAnsLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.nextLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.previousLinear.setVisibility(View.GONE);
                currentQuestionIndex = 1;
                nextQuestionIndex = 2;
                callFeedbackQuestionAnswerData();
            } else if (count == 3) {
                getSelectedImageandTextValue();
                fragmentFeedbackBinding.shimmerViewContainer.startShimmerAnimation();
                fragmentFeedbackBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
                fragmentFeedbackBinding.questionLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.ImagewithtextsinglechoiceLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.nextLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.previousLinear.setVisibility(View.GONE);
                currentQuestionIndex = 2;
                nextQuestionIndex = 3;
                callFeedbackQuestionAnswerData();
            } else if (count == 4) {
                fragmentFeedbackBinding.shimmerViewContainer.startShimmerAnimation();
                fragmentFeedbackBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
                fragmentFeedbackBinding.questionLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.scrollView.setVisibility(View.GONE);
                fragmentFeedbackBinding.edittextAnsLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.nextLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.previousLinear.setVisibility(View.GONE);
                question3ansStr = fragmentFeedbackBinding.edittextAnsTxt.getText().toString();
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
                fragmentFeedbackBinding.shimmerViewContainer.startShimmerAnimation();
                fragmentFeedbackBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
                fragmentFeedbackBinding.questionLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.scrollView.setVisibility(View.GONE);
                fragmentFeedbackBinding.edittextAnsLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.nextLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.previousLinear.setVisibility(View.GONE);
                question4ansStr = fragmentFeedbackBinding.edittextAnsTxt.getText().toString();
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
                fragmentFeedbackBinding.shimmerViewContainer.startShimmerAnimation();
                fragmentFeedbackBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
                fragmentFeedbackBinding.questionLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.scrollView.setVisibility(View.GONE);
                fragmentFeedbackBinding.edittextAnsLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.nextLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.previousLinear.setVisibility(View.GONE);
                question5ansStr = fragmentFeedbackBinding.edittextAnsTxt.getText().toString();
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
                fragmentFeedbackBinding.shimmerViewContainer.startShimmerAnimation();
                fragmentFeedbackBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
                fragmentFeedbackBinding.questionLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.scrollView.setVisibility(View.GONE);
                fragmentFeedbackBinding.edittextAnsLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.nextLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.previousLinear.setVisibility(View.GONE);
                question6ansStr = fragmentFeedbackBinding.edittextAnsTxt.getText().toString();
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
                fragmentFeedbackBinding.shimmerViewContainer.startShimmerAnimation();
                fragmentFeedbackBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
                fragmentFeedbackBinding.questionLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.textmultichoiceLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.nextLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.previousLinear.setVisibility(View.GONE);
                currentQuestionIndex = 7;
                nextQuestionIndex = 8;
                callFeedbackQuestionAnswerData();

            } else if (count == 9) {
                fragmentFeedbackBinding.shimmerViewContainer.startShimmerAnimation();
                fragmentFeedbackBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
                fragmentFeedbackBinding.questionLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.scrollView.setVisibility(View.GONE);
                fragmentFeedbackBinding.edittextAnsLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.nextLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.previousLinear.setVisibility(View.GONE);
                question8ansStr = fragmentFeedbackBinding.edittextAnsTxt.getText().toString();
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
                fragmentFeedbackBinding.shimmerViewContainer.startShimmerAnimation();
                fragmentFeedbackBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
                fragmentFeedbackBinding.questionLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.textwithsingleratingchoiceLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.nextLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.previousLinear.setVisibility(View.GONE);
                currentQuestionIndex = 9;
                nextQuestionIndex = 10;
                callFeedbackQuestionAnswerData();
            } else if (count == 11) {
                getSelectedTextGridSingleChoiceValue();
                fragmentFeedbackBinding.shimmerViewContainer.startShimmerAnimation();
                fragmentFeedbackBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
                fragmentFeedbackBinding.questionLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.textsinglegridchoiceLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.nextLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.previousLinear.setVisibility(View.GONE);
                currentQuestionIndex = 10;
                nextQuestionIndex = 11;
                callFeedbackQuestionAnswerData();
            } else if (count == 12) {
                getSelectedTextSingleChoiceValue();
                fragmentFeedbackBinding.shimmerViewContainer.startShimmerAnimation();
                fragmentFeedbackBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
                fragmentFeedbackBinding.questionLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.textsinglechoiceLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.nextLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.previousLinear.setVisibility(View.GONE);
                currentQuestionIndex = 11;
                nextQuestionIndex = 12;
                callFeedbackQuestionAnswerData();
            } else if (count == 13) {
                fragmentFeedbackBinding.shimmerViewContainer.startShimmerAnimation();
                fragmentFeedbackBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
                fragmentFeedbackBinding.questionLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.scrollView.setVisibility(View.GONE);
                fragmentFeedbackBinding.edittextAnsLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.nextLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.previousLinear.setVisibility(View.GONE);
                question12ansStr = fragmentFeedbackBinding.edittextAnsTxt.getText().toString();
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
            Utils.hideKeyboard(getActivity());
            question13ansStr = fragmentFeedbackBinding.edittextAnsTxt.getText().toString();
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
            Utils.hideKeyboard(getActivity());
            updateNextCountValueText(count);
            currentQuestionId = 0;
            if (count == 1) {
                getSelectedImageandTextValue();
                fragmentFeedbackBinding.shimmerViewContainer.startShimmerAnimation();
                fragmentFeedbackBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
                fragmentFeedbackBinding.questionLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.ImagewithtextsinglechoiceLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.previousLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.nextLinear.setVisibility(View.GONE);
                currentQuestionIndex = 1;
                nextQuestionIndex = 0;
                callFeedbackQuestionAnswerData();
            } else if (count == 2) {
                fragmentFeedbackBinding.shimmerViewContainer.startShimmerAnimation();
                fragmentFeedbackBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
                fragmentFeedbackBinding.questionLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.scrollView.setVisibility(View.GONE);
                fragmentFeedbackBinding.edittextAnsLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.previousLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.nextLinear.setVisibility(View.GONE);
                currentQuestionIndex = 2;
                nextQuestionIndex = 0;
                callFeedbackQuestionAnswerData();
            } else if (count == 3) {
                fragmentFeedbackBinding.shimmerViewContainer.startShimmerAnimation();
                fragmentFeedbackBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
                fragmentFeedbackBinding.questionLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.scrollView.setVisibility(View.GONE);
                fragmentFeedbackBinding.edittextAnsLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.previousLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.nextLinear.setVisibility(View.GONE);
                currentQuestionIndex = 3;
                nextQuestionIndex = 0;
                callFeedbackQuestionAnswerData();
            } else if (count == 4) {
                fragmentFeedbackBinding.shimmerViewContainer.startShimmerAnimation();
                fragmentFeedbackBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
                fragmentFeedbackBinding.questionLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.scrollView.setVisibility(View.GONE);
                fragmentFeedbackBinding.edittextAnsLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.previousLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.nextLinear.setVisibility(View.GONE);
                if (question3ansStr.trim().length() >= 1) {
                    AppConfiguration.question3 = "fill";
                } else {
                    AppConfiguration.question3 = "";
                }
                currentQuestionIndex = 4;
                nextQuestionIndex = 0;
                callFeedbackQuestionAnswerData();
            } else if (count == 5) {
                fragmentFeedbackBinding.shimmerViewContainer.startShimmerAnimation();
                fragmentFeedbackBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
                fragmentFeedbackBinding.questionLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.scrollView.setVisibility(View.GONE);
                fragmentFeedbackBinding.edittextAnsLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.previousLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.nextLinear.setVisibility(View.GONE);
                if (question4ansStr.trim().length() >= 1) {
                    AppConfiguration.question4 = "fill";
                } else {
                    AppConfiguration.question4 = "";
                }
                currentQuestionIndex = 5;
                nextQuestionIndex = 0;
                callFeedbackQuestionAnswerData();
            } else if (count == 6) {
                fragmentFeedbackBinding.shimmerViewContainer.startShimmerAnimation();
                fragmentFeedbackBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
                fragmentFeedbackBinding.questionLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.textmultichoiceLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.previousLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.nextLinear.setVisibility(View.GONE);
                if (question5ansStr.trim().length() >= 1) {
                    AppConfiguration.question5 = "fill";
                } else {
                    AppConfiguration.question5 = "";
                }
                currentQuestionIndex = 6;
                nextQuestionIndex = 0;
                callFeedbackQuestionAnswerData();
            } else if (count == 7) {
                fragmentFeedbackBinding.shimmerViewContainer.startShimmerAnimation();
                fragmentFeedbackBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
                fragmentFeedbackBinding.questionLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.scrollView.setVisibility(View.GONE);
                fragmentFeedbackBinding.edittextAnsLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.previousLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.nextLinear.setVisibility(View.GONE);
                if (question6ansStr.trim().length() >= 1) {
                    AppConfiguration.question6 = "fill";
                } else {
                    AppConfiguration.question6 = "";
                }
                currentQuestionIndex = 7;
                nextQuestionIndex = 0;
                callFeedbackQuestionAnswerData();
            } else if (count == 8) {
                fragmentFeedbackBinding.shimmerViewContainer.startShimmerAnimation();
                fragmentFeedbackBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
                fragmentFeedbackBinding.questionLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.textwithsingleratingchoiceLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.previousLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.nextLinear.setVisibility(View.GONE);
                currentQuestionIndex = 8;
                nextQuestionIndex = 0;
                callFeedbackQuestionAnswerData();
            } else if (count == 9) {
                fragmentFeedbackBinding.shimmerViewContainer.startShimmerAnimation();
                fragmentFeedbackBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
                fragmentFeedbackBinding.questionLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.textsinglegridchoiceLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.previousLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.nextLinear.setVisibility(View.GONE);
                if (question8ansStr.trim().length() >= 1) {
                    AppConfiguration.question8 = "fill";
                } else {
                    AppConfiguration.question8 = "";
                }
                currentQuestionIndex = 9;
                nextQuestionIndex = 0;
                callFeedbackQuestionAnswerData();
            } else if (count == 10) {
                fragmentFeedbackBinding.shimmerViewContainer.startShimmerAnimation();
                fragmentFeedbackBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
                fragmentFeedbackBinding.questionLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.textsinglechoiceLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.edittextAnsLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.previousLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.nextLinear.setVisibility(View.GONE);
                currentQuestionIndex = 10;
                nextQuestionIndex = 0;
                callFeedbackQuestionAnswerData();
            } else if (count == 11) {
                fragmentFeedbackBinding.shimmerViewContainer.startShimmerAnimation();
                fragmentFeedbackBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
                fragmentFeedbackBinding.questionLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.scrollView.setVisibility(View.GONE);
                fragmentFeedbackBinding.edittextAnsLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.previousLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.nextLinear.setVisibility(View.GONE);
                currentQuestionIndex = 11;
                nextQuestionIndex = 0;
                callFeedbackQuestionAnswerData();

            } else if (count == 12) {
                fragmentFeedbackBinding.shimmerViewContainer.startShimmerAnimation();
                fragmentFeedbackBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
                fragmentFeedbackBinding.questionLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.scrollView.setVisibility(View.GONE);
                fragmentFeedbackBinding.edittextAnsLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.previousLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.nextLinear.setVisibility(View.GONE);
                currentQuestionIndex = 12;
                nextQuestionIndex = 0;
                callFeedbackQuestionAnswerData();
            } else if (count == 13) {
                fragmentFeedbackBinding.shimmerViewContainer.startShimmerAnimation();
                fragmentFeedbackBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
                fragmentFeedbackBinding.questionLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.scrollView.setVisibility(View.GONE);
                fragmentFeedbackBinding.edittextAnsLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.previousLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.nextLinear.setVisibility(View.GONE);
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
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
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
                        Utils.checkupdateApplication(mContext, getActivity(), isForceUpdateAvailable, currentVersionStr);
                    }
                    if (feedbackMainModel.getData() != null) {
                        feedbackQuestionAnswermodellist = feedbackMainModel;
                        if (currentQuestionIndex == 13 && nextQuestionIndex == 0) {
                            fragmentFeedbackBinding.scrollView.setVisibility(View.GONE);
                            fragmentFeedbackBinding.edittextAnsLinear.setVisibility(View.GONE);
                            fragmentFeedbackBinding.previousLinear.setVisibility(View.GONE);
                            fragmentFeedbackBinding.nextLinear.setVisibility(View.GONE);
                            fragmentFeedbackBinding.questionLinear.setVisibility(View.GONE);
                            /*Animation for visibility gone*/
                            Animation animSlideout = AnimationUtils.loadAnimation(mContext, R.anim.slide_out_left_new);
                            fragmentFeedbackBinding.questionLinear.startAnimation(animSlideout);

                            fragmentFeedbackBinding.thankyouLinear.setVisibility(View.VISIBLE);
//                    /*Animation for visibility visible*/
                            Animation animSlidein = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_right_new);
                            fragmentFeedbackBinding.thankyouLinear.startAnimation(animSlidein);
                        } else {
                            fragmentFeedbackBinding.shimmerViewContainer.stopShimmerAnimation();
                            fragmentFeedbackBinding.shimmerViewContainer.setVisibility(View.GONE);
                            fragmentFeedbackBinding.questionLinear.setVisibility(View.VISIBLE);
                            fragmentFeedbackBinding.edittextAnsTxt.setText("");
                            totalLayout = feedbackQuestionAnswermodellist.getRecordCount();
                            currentQuestionId = feedbackQuestionAnswermodellist.getData().getId();
                            if (count == 1) {
                                fragmentFeedbackBinding.previousLinear.setVisibility(View.GONE);
                            } else {
                                fragmentFeedbackBinding.previousLinear.setVisibility(View.VISIBLE);
                            }
                            updateNextCountValueText(count);
                            fragmentFeedbackBinding.nextLinear.setVisibility(View.VISIBLE);
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
