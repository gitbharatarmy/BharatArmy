package com.bharatarmy.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.bharatarmy.Adapter.FeedbackQuestionAnswerAdapter;
import com.bharatarmy.Adapter.FeedbackRatingAdapter;
import com.bharatarmy.Models.FeedbackModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.FragmentFeedbackNewBinding;
import com.leinardi.android.speeddial.SpeedDialView;

import java.util.ArrayList;


public class FeedbackNewFragment extends Fragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    FragmentFeedbackNewBinding fragmentFeedbackNewBinding;
    private View rootView;
    private Context mContext;

    SpeedDialView speedDial;

    FeedbackQuestionAnswerAdapter feedbackQuestionAnswerAdapter;
    ArrayList<FeedbackModel> feedbackModelArrayList;
    LinearLayoutManager questionansmLayoutManager;


    public FeedbackNewFragment() {
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
        fragmentFeedbackNewBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_feedback_new, container, false);


        rootView = fragmentFeedbackNewBinding.getRoot();

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
                fragmentFeedbackNewBinding.questionLinear.setVisibility(View.VISIBLE);
                fragmentFeedbackNewBinding.thankyouLinear.setVisibility(View.GONE);
                // Load the animation like this
                Animation animSlidein = AnimationUtils.loadAnimation(mContext,
                        R.anim.slide_in_right_new);

                // Start the animation like this
                fragmentFeedbackNewBinding.questionLinear.startAnimation(animSlidein);

            }
        } else {
            fragmentFeedbackNewBinding.questionLinear.setVisibility(View.VISIBLE);
            fragmentFeedbackNewBinding.thankyouLinear.setVisibility(View.GONE);
            // Load the animation like this
            Animation animSlidein = AnimationUtils.loadAnimation(mContext,
                    R.anim.slide_in_right_new);

            // Start the animation like this
            fragmentFeedbackNewBinding.questionLinear.startAnimation(animSlidein);

        }


    }

    public void setListiner() {
    }


    public void fillQuestionAnswerList() {
        feedbackModelArrayList = new ArrayList<>();

        feedbackModelArrayList.add(new FeedbackModel(getResources().getString(R.string.question1), getResources().getString(R.string.question_category), "1"));
        feedbackModelArrayList.add(new FeedbackModel(getResources().getString(R.string.question2), "", "5"));
        feedbackModelArrayList.add(new FeedbackModel(getResources().getString(R.string.question3), "", "2"));
        feedbackModelArrayList.add(new FeedbackModel(getResources().getString(R.string.question4), "", "4"));
        feedbackModelArrayList.add(new FeedbackModel(getResources().getString(R.string.question5), "", "2"));
        feedbackModelArrayList.add(new FeedbackModel(getResources().getString(R.string.question6), "", "2"));
        feedbackModelArrayList.add(new FeedbackModel(getResources().getString(R.string.question7), "", "1"));
        feedbackModelArrayList.add(new FeedbackModel(getResources().getString(R.string.question8), "", "3"));
        feedbackModelArrayList.add(new FeedbackModel(getResources().getString(R.string.question9), "", "3"));
        feedbackModelArrayList.add(new FeedbackModel(getResources().getString(R.string.question10), "", "2"));
        feedbackModelArrayList.add(new FeedbackModel(getResources().getString(R.string.question11), "", "5"));
        feedbackModelArrayList.add(new FeedbackModel(getResources().getString(R.string.question12), "", "5"));
        feedbackModelArrayList.add(new FeedbackModel(getResources().getString(R.string.question13), "", "5"));
        feedbackModelArrayList.add(new FeedbackModel(getResources().getString(R.string.question14), "", "5"));
        feedbackModelArrayList.add(new FeedbackModel(getResources().getString(R.string.question15), "", "5"));


        feedbackQuestionAnswerAdapter = new FeedbackQuestionAnswerAdapter(mContext, feedbackModelArrayList);
        questionansmLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        questionansmLayoutManager.canScrollHorizontally();
        fragmentFeedbackNewBinding.questionAnsRcv.setLayoutManager(questionansmLayoutManager);
        fragmentFeedbackNewBinding.questionAnsRcv.setItemAnimator(new DefaultItemAnimator());
        fragmentFeedbackNewBinding.questionAnsRcv.setAdapter(feedbackQuestionAnswerAdapter);

    }

    public void setVisibilityofLayout() {
        fragmentFeedbackNewBinding.thankyouLinear.setVisibility(View.GONE);
        /*Animation for visibility gone*/
        Animation animSlideout = AnimationUtils.loadAnimation(mContext, R.anim.slide_out_left_new);
        fragmentFeedbackNewBinding.thankyouLinear.startAnimation(animSlideout);

        fragmentFeedbackNewBinding.questionAnsViewLinear.setVisibility(View.VISIBLE);
        /*Animation for visibility visible*/
        Animation animSlidein = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_right_new);
        fragmentFeedbackNewBinding.questionAnsViewLinear.startAnimation(animSlidein);

        fragmentFeedbackNewBinding.editLinear.setVisibility(View.VISIBLE);
        fragmentFeedbackNewBinding.questionLinear.setVisibility(View.GONE);


    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Utils.dismissDialog();
    }


}
