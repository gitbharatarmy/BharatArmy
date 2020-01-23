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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bharatarmy.Adapter.FeedbackAnsAdapter;
import com.bharatarmy.Adapter.FeedbackAnsGridImageAdapter;
import com.bharatarmy.Adapter.FeedbackSingleChoiceAdapter;
import com.bharatarmy.Adapter.FeedbackViewAdapter;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.FragmentFeedbackBinding;
import com.leinardi.android.speeddial.SpeedDialView;

import java.util.ArrayList;


public class FeedbackFragment extends Fragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    FragmentFeedbackBinding fragmentFeedbackBinding;
    private View rootView;
    private Context mContext;

    SpeedDialView speedDial;
    FeedbackAnsGridImageAdapter feedbackAnsGridImageAdapter;
    GridLayoutManager gridLayoutManager;
    ArrayList<TravelModel> feedbackansimagelist;

    FeedbackSingleChoiceAdapter feedbackSingleChoiceAdapter;
    LinearLayoutManager singlemLayoutmanager;
    ArrayList<TravelModel> feedbacksinglechoiceanslist;


    FeedbackAnsAdapter feedbackAnsAdapter;
    ArrayList<TravelModel> feedbackanslist;
    LinearLayoutManager feedbackmLayout;

    FeedbackViewAdapter feedbackViewAdapter;
    ArrayList<TravelModel> feedbackviewanslist;
    LinearLayoutManager ansviewmLayout;

    int totalLayout = 3;
    int count = 1;

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
        if (Utils.getPref(mContext, "feedbackgiveflag") != null) {
            if (Utils.getPref(mContext, "feedbackgiveflag").equalsIgnoreCase("1")) {
                setVisibilityofLayout();
            } else {
                fragmentFeedbackBinding.question1Linear.setVisibility(View.VISIBLE);
                fragmentFeedbackBinding.nextLinear.setVisibility(View.VISIBLE);
                fragmentFeedbackBinding.thankyouLinear.setVisibility(View.GONE);
                // Load the animation like this
                Animation animSlidein = AnimationUtils.loadAnimation(mContext,
                        R.anim.slide_in_right_new);

                // Start the animation like this
                fragmentFeedbackBinding.question1Linear.startAnimation(animSlidein);

            }
        } else {
            fragmentFeedbackBinding.question1Linear.setVisibility(View.VISIBLE);
            fragmentFeedbackBinding.nextLinear.setVisibility(View.VISIBLE);
            fragmentFeedbackBinding.thankyouLinear.setVisibility(View.GONE);
            // Load the animation like this
            Animation animSlidein = AnimationUtils.loadAnimation(mContext,
                    R.anim.slide_in_right_new);

            // Start the animation like this
            fragmentFeedbackBinding.question1Linear.startAnimation(animSlidein);

        }


        /*Multi choice Question ans list*/
        feedbackanslist = new ArrayList<>();
        feedbackanslist.add(new TravelModel(getResources().getString(R.string.question1_ans1), "0"));
        feedbackanslist.add(new TravelModel(getResources().getString(R.string.question1_ans2), "0"));
        feedbackanslist.add(new TravelModel(getResources().getString(R.string.question1_ans3), "0"));
        feedbackanslist.add(new TravelModel(getResources().getString(R.string.question1_ans4), "0"));


        feedbackAnsAdapter = new FeedbackAnsAdapter(mContext, feedbackanslist);
        feedbackmLayout = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        fragmentFeedbackBinding.questionAnsRcv.setLayoutManager(feedbackmLayout);
        fragmentFeedbackBinding.questionAnsRcv.setItemAnimator(new DefaultItemAnimator());
        fragmentFeedbackBinding.questionAnsRcv.setAdapter(feedbackAnsAdapter);

        /*Single choice Question ans list*/
        feedbacksinglechoiceanslist = new ArrayList<>();
        feedbacksinglechoiceanslist.add(new TravelModel(getResources().getString(R.string.question3_ans1), "0"));
        feedbacksinglechoiceanslist.add(new TravelModel(getResources().getString(R.string.question3_ans2), "0"));

        feedbackSingleChoiceAdapter = new FeedbackSingleChoiceAdapter(mContext, feedbacksinglechoiceanslist);
        singlemLayoutmanager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        fragmentFeedbackBinding.question4TxtAnsRcv.setLayoutManager(singlemLayoutmanager);
        fragmentFeedbackBinding.question4TxtAnsRcv.setItemAnimator(new DefaultItemAnimator());
        fragmentFeedbackBinding.question4TxtAnsRcv.setAdapter(feedbackSingleChoiceAdapter);

        feedbackSingleChoiceAdapter = new FeedbackSingleChoiceAdapter(mContext, feedbacksinglechoiceanslist);
        singlemLayoutmanager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        fragmentFeedbackBinding.question3TxtAnsRcv.setLayoutManager(singlemLayoutmanager);
        fragmentFeedbackBinding.question3TxtAnsRcv.setItemAnimator(new DefaultItemAnimator());
        fragmentFeedbackBinding.question3TxtAnsRcv.setAdapter(feedbackSingleChoiceAdapter);

        /*Image Recyclerview*/
        feedbackansimagelist = new ArrayList<>();
        feedbackansimagelist.add(new TravelModel(R.drawable.adult_icon, "0", "512*512"));
        feedbackansimagelist.add(new TravelModel(R.drawable.australia_tour, "0", ""));
        feedbackansimagelist.add(new TravelModel(R.drawable.child_icon, "0", "512*512"));
        feedbackansimagelist.add(new TravelModel(R.drawable.dinner, "0", "512*512"));


        feedbackAnsGridImageAdapter = new FeedbackAnsGridImageAdapter(mContext, feedbackansimagelist);
        gridLayoutManager = new GridLayoutManager(mContext, 2);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL); // set Horizontal Orientation
        fragmentFeedbackBinding.question4ImageAnsRcv.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
        fragmentFeedbackBinding.question4ImageAnsRcv.setAdapter(feedbackAnsGridImageAdapter);


        /*Question ans view list*/
        feedbackviewanslist = new ArrayList<>();
        feedbackviewanslist.add(new TravelModel(getResources().getString(R.string.question1), getResources().getString(R.string.question1_ans1), getResources().getString(R.string.question1_ans3)));
        feedbackviewanslist.add(new TravelModel(getResources().getString(R.string.question3), getResources().getString(R.string.question3_ans1), ""));
        feedbackviewanslist.add(new TravelModel(getResources().getString(R.string.question4), getResources().getString(R.string.question4_ans2), ""));


        feedbackViewAdapter = new FeedbackViewAdapter(mContext, feedbackviewanslist);
        ansviewmLayout = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        fragmentFeedbackBinding.questionAnsViewRcv.setLayoutManager(ansviewmLayout);
        fragmentFeedbackBinding.questionAnsViewRcv.setItemAnimator(new DefaultItemAnimator());
        fragmentFeedbackBinding.questionAnsViewRcv.setAdapter(feedbackViewAdapter);
    }


    public void setListiner() {
        fragmentFeedbackBinding.nextLinear.setOnClickListener(this);
        fragmentFeedbackBinding.viewFeedbackBtn.setOnClickListener(this);
        fragmentFeedbackBinding.editLinear.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next_linear:
                if (count != 3) {
                    count = count + 1;
                    setLayoutVisibility(count);
                } else {
                    Utils.setPref(mContext, "feedbackgiveflag", "1");
                    fragmentFeedbackBinding.nextLinear.setVisibility(View.GONE);
                    fragmentFeedbackBinding.questionLinear.setVisibility(View.GONE);
                    /*Animation for visibility gone*/
                    Animation animSlideout = AnimationUtils.loadAnimation(mContext, R.anim.slide_out_left_new);
                    fragmentFeedbackBinding.questionLinear.startAnimation(animSlideout);

                    fragmentFeedbackBinding.thankyouLinear.setVisibility(View.VISIBLE);
                    /*Animation for visibility visible*/
                    Animation animSlidein = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_right_new);
                    fragmentFeedbackBinding.thankyouLinear.startAnimation(animSlidein);
                }
                break;
            case R.id.view_feedback_btn:
                fragmentFeedbackBinding.thankyouLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.nextLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.questionLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.editLinear.setVisibility(View.VISIBLE);

                /*Animation for visibility gone*/
                Animation animSlideout = AnimationUtils.loadAnimation(mContext, R.anim.slide_out_left_new);
                fragmentFeedbackBinding.thankyouLinear.startAnimation(animSlideout);

                fragmentFeedbackBinding.questionAnsViewLinear.setVisibility(View.VISIBLE);
                /*Animation for visibility visible*/
                Animation animSlidein = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_right_new);
                fragmentFeedbackBinding.questionAnsViewLinear.startAnimation(animSlidein);

                break;
            case R.id.edit_linear:
                fragmentFeedbackBinding.thankyouLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.questionAnsViewLinear.setVisibility(View.GONE);
                /*Animation for visibility gone*/
                Animation animSlideoute = AnimationUtils.loadAnimation(mContext, R.anim.slide_out_left_new);
                fragmentFeedbackBinding.questionAnsViewLinear.startAnimation(animSlideoute);

                fragmentFeedbackBinding.editLinear.setVisibility(View.GONE);
                fragmentFeedbackBinding.nextLinear.setVisibility(View.VISIBLE);
                fragmentFeedbackBinding.questionLinear.setVisibility(View.VISIBLE);
                /*Animation for visibility visible*/
                Animation animSlideine = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_right_new);
                fragmentFeedbackBinding.questionLinear.startAnimation(animSlideine);
                fragmentFeedbackBinding.question4Linear.setVisibility(View.GONE);
                fragmentFeedbackBinding.question1Linear.setVisibility(View.VISIBLE);
                count = 1;
                fragmentFeedbackBinding.submitTxt.setText(String.valueOf(count) + " of " + String.valueOf(totalLayout));
                fragmentFeedbackBinding.nextImg.setImageResource(R.drawable.ic_next_question);
                break;
        }
    }


    public void setLayoutVisibility(int visibleView) {
        if (visibleView == 2) {
            fragmentFeedbackBinding.question1Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question2Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question3Linear.setVisibility(View.VISIBLE);
            fragmentFeedbackBinding.question3ImageAnsRcv.setVisibility(View.GONE);
            fragmentFeedbackBinding.question3TxtAnsRcv.setVisibility(View.VISIBLE);
            fragmentFeedbackBinding.question4Linear.setVisibility(View.GONE);
            Animation animSlideout = AnimationUtils.loadAnimation(mContext, R.anim.slide_out_left_new);

            fragmentFeedbackBinding.question1Linear.startAnimation(animSlideout);
            // Load the animation like this
            Animation animSlidein = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_right_new);
// Start the animation like this
            fragmentFeedbackBinding.question3Linear.startAnimation(animSlidein);
            fragmentFeedbackBinding.submitTxt.setText(String.valueOf(count) + " of " + String.valueOf(totalLayout));

        } else if (visibleView == 3) {
            fragmentFeedbackBinding.question1Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question2Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question3Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question4Linear.setVisibility(View.VISIBLE);
            fragmentFeedbackBinding.question4ImageAnsRcv.setVisibility(View.VISIBLE);
            fragmentFeedbackBinding.question4TxtAnsRcv.setVisibility(View.GONE);

            Animation animSlideout = AnimationUtils.loadAnimation(mContext, R.anim.slide_out_left_new);

            fragmentFeedbackBinding.question3Linear.startAnimation(animSlideout);

            // Load the animation like this
            Animation animSlidein = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_right_new);
// Start the animation like this
            fragmentFeedbackBinding.question4Linear.startAnimation(animSlidein);

            fragmentFeedbackBinding.submitTxt.setText(String.valueOf(count) + " of " + String.valueOf(totalLayout));
//            fragmentFeedbackBinding.nextImg.setImageResource(R.drawable.ic_feedback_save);


        }

    }

    public void setVisibilityofLayout() {
        fragmentFeedbackBinding.thankyouLinear.setVisibility(View.GONE);
        /*Animation for visibility gone*/
        Animation animSlideout = AnimationUtils.loadAnimation(mContext, R.anim.slide_out_left_new);
        fragmentFeedbackBinding.thankyouLinear.startAnimation(animSlideout);

        fragmentFeedbackBinding.questionAnsViewLinear.setVisibility(View.VISIBLE);
        /*Animation for visibility visible*/
        Animation animSlidein = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_right_new);
        fragmentFeedbackBinding.questionAnsViewLinear.startAnimation(animSlidein);

        fragmentFeedbackBinding.editLinear.setVisibility(View.VISIBLE);
        fragmentFeedbackBinding.nextLinear.setVisibility(View.GONE);
        fragmentFeedbackBinding.questionLinear.setVisibility(View.GONE);
        fragmentFeedbackBinding.question4Linear.setVisibility(View.GONE);
        fragmentFeedbackBinding.question1Linear.setVisibility(View.GONE);


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Utils.dismissDialog();
    }
}
