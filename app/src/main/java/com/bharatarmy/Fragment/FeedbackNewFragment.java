package com.bharatarmy.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.bharatarmy.Activity.TravelMatchStadiumDetailActivity;
import com.bharatarmy.Adapter.FeedbackQuestionAnswerAdapter;
import com.bharatarmy.Adapter.FeedbackRatingAdapter;
import com.bharatarmy.Models.FeedbackMainModel;
import com.bharatarmy.Models.FeedbackModel;
import com.bharatarmy.Models.FeedbackQuestionAnswerDatum;
import com.bharatarmy.Models.TravelDataModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.Utility.WebServices;
import com.bharatarmy.databinding.FragmentFeedbackNewBinding;
import com.leinardi.android.speeddial.SpeedDialView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


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


    /*Question Answer url variable*/
    String question1Str = "http://www.mocky.io/v2/5e6a16332d000054005fa04f";
    String question2Str = "http://www.mocky.io/v2/5e6a16f82d000076005fa059";
    String question3Str = "http://www.mocky.io/v2/5e6a176e2d000054005fa05c";
    String question4Str = "http://www.mocky.io/v2/5e6a18272d0000b6565fa060";
    String question5Str = "http://www.mocky.io/v2/5e6a186f2d000093005fa064";
    String question6Str = "http://www.mocky.io/v2/5e6a18a12d000076005fa066";
    String question7Str = "http://www.mocky.io/v2/5e6a1f4e2d000054005fa0bf";
    String question8Str = "http://www.mocky.io/v2/5e6a19542d0000aa005fa070";
    String question9Str = "http://www.mocky.io/v2/5e6a1f142d00007a005fa0bb";
    String question10Str = "http://www.mocky.io/v2/5e6a1ef02d000059005fa0b9";
    String question11Str = "http://www.mocky.io/v2/5e6a1ab32d00004b005fa090";
    String question12Str = "http://www.mocky.io/v2/5e6a1b112d00004b005fa097";

    /*Array list of feedback question and answer list*/
    List<FeedbackQuestionAnswerDatum> feedbackquestionanswerlistmodel;


    /*Question count*/
    int totalLayout = 12;
    int count = 1;


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
    public static FeedbackNewFragment newInstance(String param1, String param2) {
        FeedbackNewFragment fragment = new FeedbackNewFragment();
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

            GetQuestionAnswerList("1");

        }


    }

    public void setListiner() {

    }

    public void GetQuestionAnswerList(String urlStr) {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;

        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConfiguration.BASEURL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        WebServices api = retrofit.create(WebServices.class);

        Call<FeedbackMainModel> call = api.getFeedbackQuestionANswerList(urlStr);

        call.enqueue(new Callback<FeedbackMainModel>() {
            @Override
            public void onResponse(Call<FeedbackMainModel> call, retrofit2.Response<FeedbackMainModel> response) {

                if (response.body().getFeedbackQuestionAnswerData() != null) {
                    feedbackquestionanswerlistmodel = response.body().getFeedbackQuestionAnswerData();
//                    setDataList();
                }
            }

            @Override
            public void onFailure(Call<FeedbackMainModel> call, Throwable t) {
                Log.d("WatchList Error:", t.getLocalizedMessage());
            }
        });

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

    public void setQuestionAnswer() {
        for (int i = 0; i < feedbackquestionanswerlistmodel.size(); i++) {
            if (feedbackquestionanswerlistmodel.get(i).getQuestionType().equalsIgnoreCase("TextView")){
                fragmentFeedbackNewBinding.questionCategoryTitleTxt.setText(feedbackquestionanswerlistmodel.get(i).getQuestionHeader());
                fragmentFeedbackNewBinding.questionTxt.setText(feedbackquestionanswerlistmodel.get(i).getQuestionTitle());
            }

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next_linear:
                if (count != 15) {
                    count = count + 1;
                    Utils.hideKeyboard(getActivity());
                    GetQuestionAnswerList(String.valueOf(count));

                } else {
                    Utils.hideKeyboard(getActivity());
                    Utils.setPref(mContext, "feedbackgiveflag", "1");
                    fragmentFeedbackNewBinding.nextLinear.setVisibility(View.GONE);
                    fragmentFeedbackNewBinding.questionLinear.setVisibility(View.GONE);
                    /*Animation for visibility gone*/
                    Animation animSlideout = AnimationUtils.loadAnimation(mContext, R.anim.slide_out_left_new);
                    fragmentFeedbackNewBinding.questionLinear.startAnimation(animSlideout);

                    fragmentFeedbackNewBinding.thankyouLinear.setVisibility(View.VISIBLE);
                    /*Animation for visibility visible*/
                    Animation animSlidein = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_right_new);
                    fragmentFeedbackNewBinding.thankyouLinear.startAnimation(animSlidein);
                }

                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Utils.dismissDialog();
    }


}
