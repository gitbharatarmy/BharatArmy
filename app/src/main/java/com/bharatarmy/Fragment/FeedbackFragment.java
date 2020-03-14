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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.bharatarmy.Adapter.FeedbackImagewithTextAdapter;
import com.bharatarmy.Adapter.FeedbackRatingAdapter;
import com.bharatarmy.Adapter.FeedbackSingleChoiceAdapter;
import com.bharatarmy.Adapter.FeedbackTextMultiChoiceAdapter;
import com.bharatarmy.Adapter.FeedbackTextSingleChoiceGridAdapter;
import com.bharatarmy.Models.FeedbackAnswerList;
import com.bharatarmy.Models.FeedbackMainModel;
import com.bharatarmy.Models.FeedbackQuestionAnswerDatum;
import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.Utility.WebServices;
import com.bharatarmy.databinding.FragmentFeedbackBinding;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.leinardi.android.speeddial.SpeedDialView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class FeedbackFragment extends Fragment implements View.OnClickListener {
    /*Fragment variable*/
    private View rootView;
    private Context mContext;
    FragmentFeedbackBinding fragmentFeedbackBinding;
    SpeedDialView speedDial;

    /*Question Answer url variable*/
    String question1Str = "http://www.mocky.io/v2/5e6a16332d000054005fa04f";
    String question2Str = "http://www.mocky.io/v2/5e6cab842e0000af000eea15";
    String question3Str = "http://www.mocky.io/v2/5e6a176e2d000054005fa05c";
    String question4Str = "http://www.mocky.io/v2/5e6a18272d0000b6565fa060";
    String question5Str = "http://www.mocky.io/v2/5e6a186f2d000093005fa064";
    String question6Str = "http://www.mocky.io/v2/5e6a18a12d000076005fa066";
    String question7Str = "http://www.mocky.io/v2/5e6cb0472e0000af000eea21";
    String question8Str = "http://www.mocky.io/v2/5e6a19542d0000aa005fa070";
    String question9Str = "http://www.mocky.io/v2/5e6cb8572e000084000eea2d";
    String question10Str = "http://www.mocky.io/v2/5e6cc7422e000084000eea4f";
    String question11Str = "http://www.mocky.io/v2/5e6cbb2c2e000084000eea3f";
    String question12Str = "http://www.mocky.io/v2/5e6a1b112d00004b005fa097";
    String question13Str = "http://www.mocky.io/v2/5e6a1b442d00004b005fa09c";

    /*Array list of feedback question and answer list*/
    List<FeedbackQuestionAnswerDatum> feedbackquestionanswerlistmodel;

    /*Image with text adapter*/
    FeedbackImagewithTextAdapter feedbackImagewithTextAdapter;
    GridLayoutManager gridLayoutManager;
    List<FeedbackAnswerList> feedbackansimagetextlist;

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

    /*Question count*/
    int totalLayout = 13;
    int count = 1;


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
        GetQuestionAnswerList(question1Str);
    }

    public void setListiner() {
        fragmentFeedbackBinding.nextLinear.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next_linear:
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
                        GetQuestionAnswerList(question2Str);
                    } else if (count == 3) {
                        fragmentFeedbackBinding.shimmerViewContainer.startShimmerAnimation();
                        fragmentFeedbackBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
                        fragmentFeedbackBinding.questionLinear.setVisibility(View.GONE);
                        fragmentFeedbackBinding.ImagewithtextsinglechoiceLinear.setVisibility(View.GONE);
                        fragmentFeedbackBinding.nextLinear.setVisibility(View.GONE);
                        GetQuestionAnswerList(question3Str);
                    } else if (count == 4) {
                        fragmentFeedbackBinding.shimmerViewContainer.startShimmerAnimation();
                        fragmentFeedbackBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
                        fragmentFeedbackBinding.questionLinear.setVisibility(View.GONE);
                        fragmentFeedbackBinding.edittextAnsLinear.setVisibility(View.GONE);
                        fragmentFeedbackBinding.nextLinear.setVisibility(View.GONE);
                        GetQuestionAnswerList(question4Str);
                    } else if (count == 5) {
                        fragmentFeedbackBinding.shimmerViewContainer.startShimmerAnimation();
                        fragmentFeedbackBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
                        fragmentFeedbackBinding.questionLinear.setVisibility(View.GONE);
                        fragmentFeedbackBinding.edittextAnsLinear.setVisibility(View.GONE);
                        fragmentFeedbackBinding.nextLinear.setVisibility(View.GONE);
                        GetQuestionAnswerList(question5Str);
                    } else if (count == 6) {
                        fragmentFeedbackBinding.shimmerViewContainer.startShimmerAnimation();
                        fragmentFeedbackBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
                        fragmentFeedbackBinding.questionLinear.setVisibility(View.GONE);
                        fragmentFeedbackBinding.edittextAnsLinear.setVisibility(View.GONE);
                        fragmentFeedbackBinding.nextLinear.setVisibility(View.GONE);
                        GetQuestionAnswerList(question6Str);
                    } else if (count == 7) {
                        fragmentFeedbackBinding.shimmerViewContainer.startShimmerAnimation();
                        fragmentFeedbackBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
                        fragmentFeedbackBinding.questionLinear.setVisibility(View.GONE);
                        fragmentFeedbackBinding.edittextAnsLinear.setVisibility(View.GONE);
                        fragmentFeedbackBinding.nextLinear.setVisibility(View.GONE);
                        GetQuestionAnswerList(question7Str);
                    } else if (count == 8) {
                        fragmentFeedbackBinding.shimmerViewContainer.startShimmerAnimation();
                        fragmentFeedbackBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
                        fragmentFeedbackBinding.questionLinear.setVisibility(View.GONE);
                        fragmentFeedbackBinding.textmultichoiceLinear.setVisibility(View.GONE);
                        fragmentFeedbackBinding.nextLinear.setVisibility(View.GONE);
                        GetQuestionAnswerList(question8Str);
                    } else if (count == 9) {
                        fragmentFeedbackBinding.shimmerViewContainer.startShimmerAnimation();
                        fragmentFeedbackBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
                        fragmentFeedbackBinding.questionLinear.setVisibility(View.GONE);
                        fragmentFeedbackBinding.edittextAnsLinear.setVisibility(View.GONE);
                        fragmentFeedbackBinding.nextLinear.setVisibility(View.GONE);
                        GetQuestionAnswerList(question9Str);
                    } else if (count == 10) {
                        fragmentFeedbackBinding.shimmerViewContainer.startShimmerAnimation();
                        fragmentFeedbackBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
                        fragmentFeedbackBinding.questionLinear.setVisibility(View.GONE);
                        fragmentFeedbackBinding.textwithsingleratingchoiceLinear.setVisibility(View.GONE);
                        fragmentFeedbackBinding.nextLinear.setVisibility(View.GONE);
                        GetQuestionAnswerList(question10Str);
                    }else if (count == 11) {
                        fragmentFeedbackBinding.shimmerViewContainer.startShimmerAnimation();
                        fragmentFeedbackBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
                        fragmentFeedbackBinding.questionLinear.setVisibility(View.GONE);
                        fragmentFeedbackBinding.textsinglegridchoiceLinear.setVisibility(View.GONE);
                        fragmentFeedbackBinding.nextLinear.setVisibility(View.GONE);
                        GetQuestionAnswerList(question11Str);
                    }else if (count == 12) {
                        fragmentFeedbackBinding.shimmerViewContainer.startShimmerAnimation();
                        fragmentFeedbackBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
                        fragmentFeedbackBinding.questionLinear.setVisibility(View.GONE);
                        fragmentFeedbackBinding.textsinglechoiceLinear.setVisibility(View.GONE);
                        fragmentFeedbackBinding.nextLinear.setVisibility(View.GONE);
                        GetQuestionAnswerList(question12Str);
                    }else if (count == 13) {
                        fragmentFeedbackBinding.shimmerViewContainer.startShimmerAnimation();
                        fragmentFeedbackBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
                        fragmentFeedbackBinding.questionLinear.setVisibility(View.GONE);
                        fragmentFeedbackBinding.edittextAnsLinear.setVisibility(View.GONE);
                        fragmentFeedbackBinding.nextLinear.setVisibility(View.GONE);
                        GetQuestionAnswerList(question13Str);
                    }
                } else {
                    Utils.hideKeyboard(getActivity());
                    Utils.setPref(mContext, "feedbackgiveflag", "1");
                    fragmentFeedbackBinding.edittextAnsLinear.setVisibility(View.GONE);
                    fragmentFeedbackBinding.nextLinear.setVisibility(View.GONE);
                    fragmentFeedbackBinding.questionLinear.setVisibility(View.GONE);
                    /*Animation for visibility gone*/
                    Animation animSlideout = AnimationUtils.loadAnimation(mContext, R.anim.slide_out_left_new);
                    fragmentFeedbackBinding.questionLinear.startAnimation(animSlideout);

                    fragmentFeedbackBinding.thankyouLinear.setVisibility(View.VISIBLE);
//                    /*Animation for visibility visible*/
                    Animation animSlidein = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_right_new);
                    fragmentFeedbackBinding.thankyouLinear.startAnimation(animSlidein);
                }

                break;

        }

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
                    if (feedbackquestionanswerlistmodel != null && feedbackquestionanswerlistmodel.size() != 0) {
                        fragmentFeedbackBinding.shimmerViewContainer.stopShimmerAnimation();
                        fragmentFeedbackBinding.shimmerViewContainer.setVisibility(View.GONE);
                        fragmentFeedbackBinding.questionLinear.setVisibility(View.VISIBLE);
                        fragmentFeedbackBinding.nextLinear.setVisibility(View.VISIBLE);
                        setQuestionAnswer();
                    }

                }
            }

            @Override
            public void onFailure(Call<FeedbackMainModel> call, Throwable t) {
                Log.d("WatchList Error:", t.getLocalizedMessage());
            }
        });

    }

    public void setQuestionAnswer() {
        for (int i = 0; i < feedbackquestionanswerlistmodel.size(); i++) {
            fragmentFeedbackBinding.questionCategoryTitleTxt.setText(feedbackquestionanswerlistmodel.get(i).getQuestionHeader());
            fragmentFeedbackBinding.questionTxt.setText(feedbackquestionanswerlistmodel.get(i).getQuestionTitle());
            if (feedbackquestionanswerlistmodel.get(i).getQuestionType().equalsIgnoreCase("TextView")) {
                fragmentFeedbackBinding.textviewAnsLinear.setVisibility(View.VISIBLE);
            } else if (feedbackquestionanswerlistmodel.get(i).getQuestionType().equalsIgnoreCase("ImageWithTextSingleChoice")) {
                fragmentFeedbackBinding.ImagewithtextsinglechoiceLinear.setVisibility(View.VISIBLE);
                feedbackansimagetextlist = feedbackquestionanswerlistmodel.get(i).getAnswerList();
                Log.d("Imagetext :", "" + feedbackansimagetextlist.size());
                feedbackImagewithTextAdapter = new FeedbackImagewithTextAdapter(mContext, feedbackansimagetextlist);
                gridLayoutManager = new GridLayoutManager(mContext, 3);
                gridLayoutManager.setOrientation(RecyclerView.VERTICAL); // set Horizontal Orientation
                fragmentFeedbackBinding.ImagewithtextsinglechoiceAnsRcv.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
                fragmentFeedbackBinding.ImagewithtextsinglechoiceAnsRcv.setAdapter(feedbackImagewithTextAdapter);
            } else if (feedbackquestionanswerlistmodel.get(i).getQuestionType().equalsIgnoreCase("EditText")) {
                fragmentFeedbackBinding.edittextAnsLinear.setVisibility(View.VISIBLE);
            } else if (feedbackquestionanswerlistmodel.get(i).getQuestionType().equalsIgnoreCase("TextMultiChoice")) {
                fragmentFeedbackBinding.textmultichoiceLinear.setVisibility(View.VISIBLE);
                feedbacktextmultichoicelist = feedbackquestionanswerlistmodel.get(i).getAnswerList();
                feedbackTextMultiChoiceAdapter = new FeedbackTextMultiChoiceAdapter(mContext, feedbacktextmultichoicelist);
                textmultichoicelinearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
                fragmentFeedbackBinding.textmultichoiceAnsRcv.setLayoutManager(textmultichoicelinearLayoutManager);
                fragmentFeedbackBinding.textmultichoiceAnsRcv.setItemAnimator(new DefaultItemAnimator());
                fragmentFeedbackBinding.textmultichoiceAnsRcv.setAdapter(feedbackTextMultiChoiceAdapter);
            } else if (feedbackquestionanswerlistmodel.get(i).getQuestionType().equalsIgnoreCase("TextWithRatingSingleChoice")) {
                fragmentFeedbackBinding.textwithsingleratingchoiceLinear.setVisibility(View.VISIBLE);
                feedbackratingsinglechoicelist = feedbackquestionanswerlistmodel.get(i).getAnswerList();
                feedbackRatingAdapter = new FeedbackRatingAdapter(mContext, feedbackratingsinglechoicelist);
                ratingsinglechoicelinearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
                fragmentFeedbackBinding.textwithsingleratingchoiceAnsRcv.setLayoutManager(ratingsinglechoicelinearLayoutManager);
                fragmentFeedbackBinding.textwithsingleratingchoiceAnsRcv.setItemAnimator(new DefaultItemAnimator());
                fragmentFeedbackBinding.textwithsingleratingchoiceAnsRcv.setAdapter(feedbackRatingAdapter);
            } else if (feedbackquestionanswerlistmodel.get(i).getQuestionType().equalsIgnoreCase("TextSingleChoice")) {
                fragmentFeedbackBinding.textsinglechoiceLinear.setVisibility(View.VISIBLE);
                feedbacktextsinglechoicelist = feedbackquestionanswerlistmodel.get(i).getAnswerList();
                feedbackSingleChoiceAdapter = new FeedbackSingleChoiceAdapter(mContext, feedbacktextsinglechoicelist);
                textsinglechoicelinearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
                fragmentFeedbackBinding.textsinglechoiceAnsRcv.setLayoutManager(textsinglechoicelinearLayoutManager);
                fragmentFeedbackBinding.textsinglechoiceAnsRcv.setItemAnimator(new DefaultItemAnimator());
                fragmentFeedbackBinding.textsinglechoiceAnsRcv.setAdapter(feedbackSingleChoiceAdapter);
            } else if(feedbackquestionanswerlistmodel.get(i).getQuestionType().equalsIgnoreCase("TextGridSingleChoice")){
                fragmentFeedbackBinding.textsinglegridchoiceLinear.setVisibility(View.VISIBLE);
                feedbackanstextsinglechoicegridlist = feedbackquestionanswerlistmodel.get(i).getAnswerList();
                Log.d("Imagetext :", "" + feedbackanstextsinglechoicegridlist.size());
                feedbackTextSingleChoiceGridAdapter = new FeedbackTextSingleChoiceGridAdapter(mContext, feedbackanstextsinglechoicegridlist);
                textsinglegridLayoutManager = new GridLayoutManager(mContext, 3);
                gridLayoutManager.setOrientation(RecyclerView.VERTICAL); // set Horizontal Orientation
                fragmentFeedbackBinding.textsinglegridchoiceAnsRcv.setLayoutManager(textsinglegridLayoutManager); // set LayoutManager to RecyclerView
                fragmentFeedbackBinding.textsinglegridchoiceAnsRcv.setAdapter(feedbackTextSingleChoiceGridAdapter);
            }

        }
    }

    public void updateNextCountValueText(int count){
        if (count <= 9) {
            fragmentFeedbackBinding.submitTxt.setText("0" + String.valueOf(count) + " of " + String.valueOf(totalLayout));
        } else {
            fragmentFeedbackBinding.submitTxt.setText(String.valueOf(count) + " of " + String.valueOf(totalLayout));
        }
    }
}
