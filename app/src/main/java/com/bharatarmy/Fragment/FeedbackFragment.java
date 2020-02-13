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
import android.widget.ScrollView;

import com.bharatarmy.Adapter.FeedbackAnsAdapter;
import com.bharatarmy.Adapter.FeedbackAnsGridImageAdapter;
import com.bharatarmy.Adapter.FeedbackRatingAdapter;
import com.bharatarmy.Adapter.FeedbackSingleChoiceAdapter;
import com.bharatarmy.Adapter.FeedbackViewAdapter;
import com.bharatarmy.Interfaces.MorestoryClick;
import com.bharatarmy.Models.FeedbackModel;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.FragmentFeedbackBinding;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.leinardi.android.speeddial.SpeedDialView;

import java.util.ArrayList;

// remove extra code 03-02-2020
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
    ArrayList<FeedbackModel> feedbackansimagelist;

    FeedbackSingleChoiceAdapter feedbackSingleChoiceAdapter;
    LinearLayoutManager singlemLayoutmanager;
    ArrayList<TravelModel> feedbacksinglechoiceans3list;
    ArrayList<TravelModel> feedbacksinglechoiceans5list;
    ArrayList<TravelModel> feedbacksinglechoiceans6list;

    FeedbackAnsAdapter feedbackAnsAdapter;
    ArrayList<TravelModel> feedbackanslist;
    ArrayList<TravelModel> feedbackansfeaturelist;
    LinearLayoutManager feedbackmLayout;

    FeedbackRatingAdapter feedbackratingAdapter;
    ArrayList<TravelModel> feedbackratinglist;
    ArrayList<TravelModel> feedbacknewfeatureratinglist;
    LinearLayoutManager feedbackratingmLayout;

    FeedbackViewAdapter feedbackViewAdapter;
    ArrayList<TravelModel> feedbackviewanslist;
    LinearLayoutManager ansviewmLayout;


    MeowBottomNavigation bottomNavigation;

    int totalLayout = 15;
    int count = 1;

    String textquestionfillornotStr = "", question2ansStr = "", question10ansStr = "", question11ansStr = "", question12ansStr = "", question13ansStr = "", question14ansStr = "", question15ansStr = "",
            question1ansStr = "", question3ansStr = "", question4ansStr = "", question4imageStr = "", question5ansStr = "", question6ansStr = "", question7ansStr = "", question8ansStr = "", question9ansStr = "";

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
//        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        rootView = fragmentFeedbackBinding.getRoot();

        mContext = getActivity().getApplicationContext();
        bottomNavigation = getActivity().findViewById(R.id.bottomNavigation);
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


        feedbackAnsAdapter = new FeedbackAnsAdapter(mContext, feedbackanslist, new MorestoryClick() {
            @Override
            public void getmorestoryClick() {
                for (int i = 0; i < feedbackanslist.size(); i++) {
                    if (feedbackanslist.get(i).getCityHotelAmenitiesName().equalsIgnoreCase("1")) {
                        AppConfiguration.multichoice = "fill";
                        break;
                    } else {
                        AppConfiguration.multichoice = "not fill";
                    }
                }
            }
        });
        feedbackmLayout = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        fragmentFeedbackBinding.questionAnsRcv.setLayoutManager(feedbackmLayout);
        fragmentFeedbackBinding.questionAnsRcv.setItemAnimator(new DefaultItemAnimator());
        fragmentFeedbackBinding.questionAnsRcv.setAdapter(feedbackAnsAdapter);


        /*Multi choice feature use list*/
        feedbackansfeaturelist = new ArrayList<>();
        feedbackansfeaturelist.add(new TravelModel(getResources().getString(R.string.question7_ans1), "0"));
        feedbackansfeaturelist.add(new TravelModel(getResources().getString(R.string.question7_ans2), "0"));
        feedbackansfeaturelist.add(new TravelModel(getResources().getString(R.string.question7_ans3), "0"));
        feedbackansfeaturelist.add(new TravelModel(getResources().getString(R.string.question7_ans4), "0"));


        feedbackAnsAdapter = new FeedbackAnsAdapter(mContext, feedbackansfeaturelist, new MorestoryClick() {
            @Override
            public void getmorestoryClick() {
                for (int i = 0; i < feedbackansfeaturelist.size(); i++) {
                    if (feedbackansfeaturelist.get(i).getCityHotelAmenitiesName().equalsIgnoreCase("1")) {
                        AppConfiguration.multichoice = "fill";
                        break;
                    } else {
                        AppConfiguration.multichoice = "not fill";
                    }
                }

            }
        });
        feedbackmLayout = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        fragmentFeedbackBinding.question7TxtAnsRcv.setLayoutManager(feedbackmLayout);
        fragmentFeedbackBinding.question7TxtAnsRcv.setItemAnimator(new DefaultItemAnimator());
        fragmentFeedbackBinding.question7TxtAnsRcv.setAdapter(feedbackAnsAdapter);


        /*Single choice rating list*/
        feedbackratinglist = new ArrayList<>();
        feedbackratinglist.add(new TravelModel(getResources().getString(R.string.question8_ans1), "0", "5"));
        feedbackratinglist.add(new TravelModel(getResources().getString(R.string.question8_ans2), "0", "4"));
        feedbackratinglist.add(new TravelModel(getResources().getString(R.string.question8_ans3), "0", "3"));
        feedbackratinglist.add(new TravelModel(getResources().getString(R.string.question8_ans4), "0", "2"));


        feedbackratingAdapter = new FeedbackRatingAdapter(mContext, feedbackratinglist);
        feedbackratingmLayout = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        fragmentFeedbackBinding.question8TxtAnsRcv.setLayoutManager(feedbackratingmLayout);
        fragmentFeedbackBinding.question8TxtAnsRcv.setItemAnimator(new DefaultItemAnimator());
        fragmentFeedbackBinding.question8TxtAnsRcv.setAdapter(feedbackratingAdapter);


        /* Single choice new feature rating list*/
        feedbacknewfeatureratinglist = new ArrayList<>();
        feedbacknewfeatureratinglist.add(new TravelModel(getResources().getString(R.string.question8_ans1), "0", "5"));
        feedbacknewfeatureratinglist.add(new TravelModel(getResources().getString(R.string.question8_ans2), "0", "4"));
        feedbacknewfeatureratinglist.add(new TravelModel(getResources().getString(R.string.question8_ans3), "0", "3"));
        feedbacknewfeatureratinglist.add(new TravelModel(getResources().getString(R.string.question8_ans4), "0", "2"));


        feedbackratingAdapter = new FeedbackRatingAdapter(mContext, feedbacknewfeatureratinglist);
        feedbackratingmLayout = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        fragmentFeedbackBinding.question9TxtAnsRcv.setLayoutManager(feedbackratingmLayout);
        fragmentFeedbackBinding.question9TxtAnsRcv.setItemAnimator(new DefaultItemAnimator());
        fragmentFeedbackBinding.question9TxtAnsRcv.setAdapter(feedbackratingAdapter);

        /*Single choice Question ans list*/
        feedbacksinglechoiceans3list = new ArrayList<>();
        feedbacksinglechoiceans3list.add(new TravelModel(getResources().getString(R.string.question_ansyes), "0"));
        feedbacksinglechoiceans3list.add(new TravelModel(getResources().getString(R.string.question_ansno), "0"));

//        feedbackSingleChoiceAdapter = new FeedbackSingleChoiceAdapter(mContext, feedbacksinglechoiceanslist);
//        singlemLayoutmanager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
//        fragmentFeedbackBinding.question4TxtAnsRcv.setLayoutManager(singlemLayoutmanager);
//        fragmentFeedbackBinding.question4TxtAnsRcv.setItemAnimator(new DefaultItemAnimator());
//        fragmentFeedbackBinding.question4TxtAnsRcv.setAdapter(feedbackSingleChoiceAdapter);

        feedbackSingleChoiceAdapter = new FeedbackSingleChoiceAdapter(mContext, feedbacksinglechoiceans3list);
        singlemLayoutmanager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        fragmentFeedbackBinding.question3TxtAnsRcv.setLayoutManager(singlemLayoutmanager);
        fragmentFeedbackBinding.question3TxtAnsRcv.setItemAnimator(new DefaultItemAnimator());
        fragmentFeedbackBinding.question3TxtAnsRcv.setAdapter(feedbackSingleChoiceAdapter);

        feedbacksinglechoiceans5list = new ArrayList<>();
        feedbacksinglechoiceans5list.add(new TravelModel(getResources().getString(R.string.question_ansyes), "0"));
        feedbacksinglechoiceans5list.add(new TravelModel(getResources().getString(R.string.question_ansno), "0"));
        feedbackSingleChoiceAdapter = new FeedbackSingleChoiceAdapter(mContext, feedbacksinglechoiceans5list);
        singlemLayoutmanager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        fragmentFeedbackBinding.question5TxtAnsRcv.setLayoutManager(singlemLayoutmanager);
        fragmentFeedbackBinding.question5TxtAnsRcv.setItemAnimator(new DefaultItemAnimator());
        fragmentFeedbackBinding.question5TxtAnsRcv.setAdapter(feedbackSingleChoiceAdapter);


        feedbacksinglechoiceans6list = new ArrayList<>();
        feedbacksinglechoiceans6list.add(new TravelModel(getResources().getString(R.string.question_ansyes), "0"));
        feedbacksinglechoiceans6list.add(new TravelModel(getResources().getString(R.string.question_ansno), "0"));
        feedbackSingleChoiceAdapter = new FeedbackSingleChoiceAdapter(mContext, feedbacksinglechoiceans6list);
        singlemLayoutmanager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        fragmentFeedbackBinding.question6TxtAnsRcv.setLayoutManager(singlemLayoutmanager);
        fragmentFeedbackBinding.question6TxtAnsRcv.setItemAnimator(new DefaultItemAnimator());
        fragmentFeedbackBinding.question6TxtAnsRcv.setAdapter(feedbackSingleChoiceAdapter);



        /*Image Recyclerview*/
        feedbackansimagelist = new ArrayList<>();
        feedbackansimagelist.add(new FeedbackModel(R.drawable.adult_icon, "0", "512*512"));
        feedbackansimagelist.add(new FeedbackModel(R.drawable.australia_tour, "0", ""));
        feedbackansimagelist.add(new FeedbackModel(R.drawable.child_icon, "0", "512*512"));
        feedbackansimagelist.add(new FeedbackModel(R.drawable.dinner, "0", "512*512"));


        feedbackAnsGridImageAdapter = new FeedbackAnsGridImageAdapter(mContext, feedbackansimagelist);
        gridLayoutManager = new GridLayoutManager(mContext, 2);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL); // set Horizontal Orientation
        fragmentFeedbackBinding.question4ImageAnsRcv.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
        fragmentFeedbackBinding.question4ImageAnsRcv.setAdapter(feedbackAnsGridImageAdapter);

        setSelectedValueInList();
    }


    public void setListiner() {

        fragmentFeedbackBinding.nextLinear.setOnClickListener(this);
        fragmentFeedbackBinding.viewFeedbackBtn.setOnClickListener(this);
        fragmentFeedbackBinding.editLinear.setOnClickListener(this);

        fragmentFeedbackBinding.question2AnsTxt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    //do your stuff here

                    fragmentFeedbackBinding.scrollView2.fullScroll(ScrollView.FOCUS_DOWN);

                }
                return false;
            }
        });
        fragmentFeedbackBinding.question10AnsTxt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    //do your stuff here
                    fragmentFeedbackBinding.scrollView10.fullScroll(View.FOCUS_DOWN);
                }
                return false;
            }
        });
        fragmentFeedbackBinding.question12AnsTxt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    //do your stuff here
                    fragmentFeedbackBinding.scrollView12.fullScroll(View.FOCUS_DOWN);
                }
                return false;
            }
        });
        fragmentFeedbackBinding.question13AnsTxt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    //do your stuff here
                    fragmentFeedbackBinding.scrollView13.fullScroll(View.FOCUS_DOWN);
                }
                return false;
            }
        });
        fragmentFeedbackBinding.question14AnsTxt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    //do your stuff here
                    fragmentFeedbackBinding.scrollView14.fullScroll(View.FOCUS_DOWN);
                }
                return false;
            }
        });
        fragmentFeedbackBinding.question15AnsTxt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    //do your stuff here
                    fragmentFeedbackBinding.scrollView15.fullScroll(View.FOCUS_DOWN);
                }
                return false;
            }
        });

        fragmentFeedbackBinding.question2AnsTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Integer textlength1 = fragmentFeedbackBinding.question2AnsTxt.getText().length();


            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().length() >= 1) {
                    AppConfiguration.question2 = "fill";
                } else {
                    AppConfiguration.question2 = "";
                }
                bottomNavigation.setVisibility(View.VISIBLE);
                Log.d("BATEXTSTRING :", AppConfiguration.question2);
            }
        });
        fragmentFeedbackBinding.question10AnsTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Integer textlength1 = fragmentFeedbackBinding.question2AnsTxt.getText().length();


            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().length() >= 1) {
                    AppConfiguration.question10 = "fill";
                } else {
                    AppConfiguration.question10 = "";
                }
                Log.d("BATEXTSTRING :", AppConfiguration.question10);

            }
        });
        fragmentFeedbackBinding.question11AnsTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Integer textlength1 = fragmentFeedbackBinding.question2AnsTxt.getText().length();


            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().length() >= 1) {
                    AppConfiguration.question11 = "fill";
                } else {
                    AppConfiguration.question11 = "";
                }
                Log.d("BATEXTSTRING :", AppConfiguration.question11);
            }
        });
        fragmentFeedbackBinding.question12AnsTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Integer textlength1 = fragmentFeedbackBinding.question2AnsTxt.getText().length();


            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().length() >= 1) {
                    AppConfiguration.question12 = "fill";
                } else {
                    AppConfiguration.question12 = "";
                }
                Log.d("BATEXTSTRING :", AppConfiguration.question12);
            }
        });
        fragmentFeedbackBinding.question13AnsTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Integer textlength1 = fragmentFeedbackBinding.question2AnsTxt.getText().length();


            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().length() >= 1) {
                    AppConfiguration.question13 = "fill";
                } else {
                    AppConfiguration.question13 = "";
                }
                Log.d("BATEXTSTRING :", AppConfiguration.question13);
            }
        });
        fragmentFeedbackBinding.question14AnsTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Integer textlength1 = fragmentFeedbackBinding.question2AnsTxt.getText().length();


            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().length() >= 1) {
                    AppConfiguration.question14 = "fill";
                } else {
                    AppConfiguration.question14 = "";
                }
                Log.d("BATEXTSTRING :", AppConfiguration.question14);
            }
        });
        fragmentFeedbackBinding.question15AnsTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Integer textlength1 = fragmentFeedbackBinding.question2AnsTxt.getText().length();


            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().length() >= 1) {
                    AppConfiguration.question15 = "fill";
                } else {
                    AppConfiguration.question15 = "";
                }
                Log.d("BATEXTSTRING :", AppConfiguration.question15);
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next_linear:
                if (count != 15) {
                    count = count + 1;
                    Utils.hideKeyboard(getActivity());
                    getEdittextValue();
                    setLayoutVisibility(count);

                } else {
                    Utils.hideKeyboard(getActivity());
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
                Utils.hideKeyboard(getActivity());
                setSelectedValueInList();
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
                fragmentFeedbackBinding.question15Linear.setVisibility(View.GONE);
                fragmentFeedbackBinding.question1Linear.setVisibility(View.VISIBLE);
                count = 1;
                if (count <= 9) {
                    fragmentFeedbackBinding.submitTxt.setText("0" + String.valueOf(count) + " of " + String.valueOf(totalLayout));
                } else {
                    fragmentFeedbackBinding.submitTxt.setText(String.valueOf(count) + " of " + String.valueOf(totalLayout));
                }
                fragmentFeedbackBinding.nextImg.setImageResource(R.drawable.ic_next_question);
                break;
            case R.id.question2_ans_txt:
//                Utils.scrollScreen(fragmentFeedbackBinding.scrollView);
                bottomNavigation.setVisibility(View.GONE);
                break;
        }
    }


    public void setLayoutVisibility(int visibleView) {
        if (visibleView == 2) {
            fragmentFeedbackBinding.question1Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView2.setVisibility(View.GONE);
            fragmentFeedbackBinding.question2Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question3Linear.setVisibility(View.VISIBLE);
            fragmentFeedbackBinding.question3ImageAnsRcv.setVisibility(View.GONE);
            fragmentFeedbackBinding.question3TxtAnsRcv.setVisibility(View.VISIBLE);
            fragmentFeedbackBinding.question4Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question5Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question6Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question7Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question8Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question9Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView10.setVisibility(View.GONE);
            fragmentFeedbackBinding.question10Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView11.setVisibility(View.GONE);
            fragmentFeedbackBinding.question11Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView12.setVisibility(View.GONE);
            fragmentFeedbackBinding.question12Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView13.setVisibility(View.GONE);
            fragmentFeedbackBinding.question13Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView14.setVisibility(View.GONE);
            fragmentFeedbackBinding.question14Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView15.setVisibility(View.GONE);
            fragmentFeedbackBinding.question15Linear.setVisibility(View.GONE);
            Animation animSlideout = AnimationUtils.loadAnimation(mContext, R.anim.slide_out_left_new);

            fragmentFeedbackBinding.question1Linear.startAnimation(animSlideout);
            // Load the animation like this
            Animation animSlidein = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_right_new);
// Start the animation like this
            fragmentFeedbackBinding.question3Linear.startAnimation(animSlidein);
            if (count <= 9) {
                fragmentFeedbackBinding.submitTxt.setText("0" + String.valueOf(count) + " of " + String.valueOf(totalLayout));
            } else {
                fragmentFeedbackBinding.submitTxt.setText(String.valueOf(count) + " of " + String.valueOf(totalLayout));
            }

        } else if (visibleView == 3) {
            fragmentFeedbackBinding.question4Linear.setVisibility(View.VISIBLE);
            fragmentFeedbackBinding.question4ImageAnsRcv.setVisibility(View.VISIBLE);
            fragmentFeedbackBinding.question4TxtAnsRcv.setVisibility(View.GONE);

            fragmentFeedbackBinding.question1Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView2.setVisibility(View.GONE);
            fragmentFeedbackBinding.question2Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question3Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question5Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question6Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question7Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question8Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question9Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView10.setVisibility(View.GONE);
            fragmentFeedbackBinding.question10Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView11.setVisibility(View.GONE);
            fragmentFeedbackBinding.question11Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView12.setVisibility(View.GONE);
            fragmentFeedbackBinding.question12Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView13.setVisibility(View.GONE);
            fragmentFeedbackBinding.question13Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView14.setVisibility(View.GONE);
            fragmentFeedbackBinding.question14Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView15.setVisibility(View.GONE);
            fragmentFeedbackBinding.question15Linear.setVisibility(View.GONE);

            Animation animSlideout = AnimationUtils.loadAnimation(mContext, R.anim.slide_out_left_new);

            fragmentFeedbackBinding.question3Linear.startAnimation(animSlideout);

            // Load the animation like this
            Animation animSlidein = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_right_new);
// Start the animation like this
            fragmentFeedbackBinding.question4Linear.startAnimation(animSlidein);

            if (count <= 9) {
                fragmentFeedbackBinding.submitTxt.setText("0" + String.valueOf(count) + " of " + String.valueOf(totalLayout));
            } else {
                fragmentFeedbackBinding.submitTxt.setText(String.valueOf(count) + " of " + String.valueOf(totalLayout));
            }


        } else if (visibleView == 4) {
            fragmentFeedbackBinding.question1Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView2.setVisibility(View.GONE);
            fragmentFeedbackBinding.question2Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question3Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question4Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question5Linear.setVisibility(View.VISIBLE);
            fragmentFeedbackBinding.question5ImageAnsRcv.setVisibility(View.GONE);
            fragmentFeedbackBinding.question5TxtAnsRcv.setVisibility(View.VISIBLE);
            fragmentFeedbackBinding.question6Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question7Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question8Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question9Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView10.setVisibility(View.GONE);
            fragmentFeedbackBinding.question10Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView11.setVisibility(View.GONE);
            fragmentFeedbackBinding.question11Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView12.setVisibility(View.GONE);
            fragmentFeedbackBinding.question12Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView13.setVisibility(View.GONE);
            fragmentFeedbackBinding.question13Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView14.setVisibility(View.GONE);
            fragmentFeedbackBinding.question14Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView15.setVisibility(View.GONE);
            fragmentFeedbackBinding.question15Linear.setVisibility(View.GONE);

            Animation animSlideout = AnimationUtils.loadAnimation(mContext, R.anim.slide_out_left_new);

            fragmentFeedbackBinding.question4Linear.startAnimation(animSlideout);

            // Load the animation like this
            Animation animSlidein = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_right_new);
// Start the animation like this
            fragmentFeedbackBinding.question5Linear.startAnimation(animSlidein);

            if (count <= 9) {
                fragmentFeedbackBinding.submitTxt.setText("0" + String.valueOf(count) + " of " + String.valueOf(totalLayout));
            } else {
                fragmentFeedbackBinding.submitTxt.setText(String.valueOf(count) + " of " + String.valueOf(totalLayout));
            }


        } else if (visibleView == 5) {
            fragmentFeedbackBinding.question1Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView2.setVisibility(View.GONE);
            fragmentFeedbackBinding.question2Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question3Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question4Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question5Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question6Linear.setVisibility(View.VISIBLE);
            fragmentFeedbackBinding.question6ImageAnsRcv.setVisibility(View.GONE);
            fragmentFeedbackBinding.question6TxtAnsRcv.setVisibility(View.VISIBLE);
            fragmentFeedbackBinding.question7Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question8Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question9Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView10.setVisibility(View.GONE);
            fragmentFeedbackBinding.question10Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView11.setVisibility(View.GONE);
            fragmentFeedbackBinding.question11Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView12.setVisibility(View.GONE);
            fragmentFeedbackBinding.question12Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView13.setVisibility(View.GONE);
            fragmentFeedbackBinding.question13Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView14.setVisibility(View.GONE);
            fragmentFeedbackBinding.question14Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView15.setVisibility(View.GONE);
            fragmentFeedbackBinding.question15Linear.setVisibility(View.GONE);
            Animation animSlideout = AnimationUtils.loadAnimation(mContext, R.anim.slide_out_left_new);

            fragmentFeedbackBinding.question5Linear.startAnimation(animSlideout);

            // Load the animation like this
            Animation animSlidein = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_right_new);
// Start the animation like this
            fragmentFeedbackBinding.question6Linear.startAnimation(animSlidein);

            if (count <= 9) {
                fragmentFeedbackBinding.submitTxt.setText("0" + String.valueOf(count) + " of " + String.valueOf(totalLayout));
            } else {
                fragmentFeedbackBinding.submitTxt.setText(String.valueOf(count) + " of " + String.valueOf(totalLayout));
            }


        } else if (visibleView == 6) {
            fragmentFeedbackBinding.question1Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView2.setVisibility(View.GONE);
            fragmentFeedbackBinding.question2Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question3Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question4Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question5Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question6Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question7Linear.setVisibility(View.VISIBLE);
            fragmentFeedbackBinding.question7ImageAnsRcv.setVisibility(View.GONE);
            fragmentFeedbackBinding.question7TxtAnsRcv.setVisibility(View.VISIBLE);
            fragmentFeedbackBinding.question8Linear.setVisibility(View.GONE);

            fragmentFeedbackBinding.question9Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView10.setVisibility(View.GONE);
            fragmentFeedbackBinding.question10Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView11.setVisibility(View.GONE);
            fragmentFeedbackBinding.question11Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView12.setVisibility(View.GONE);
            fragmentFeedbackBinding.question12Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView13.setVisibility(View.GONE);
            fragmentFeedbackBinding.question13Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView14.setVisibility(View.GONE);
            fragmentFeedbackBinding.question14Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView15.setVisibility(View.GONE);
            fragmentFeedbackBinding.question15Linear.setVisibility(View.GONE);

            Animation animSlideout = AnimationUtils.loadAnimation(mContext, R.anim.slide_out_left_new);
            fragmentFeedbackBinding.question6Linear.startAnimation(animSlideout);

            // Load the animation like this
            Animation animSlidein = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_right_new);
// Start the animation like this
            fragmentFeedbackBinding.question7Linear.startAnimation(animSlidein);

            if (count <= 9) {
                fragmentFeedbackBinding.submitTxt.setText("0" + String.valueOf(count) + " of " + String.valueOf(totalLayout));
            } else {
                fragmentFeedbackBinding.submitTxt.setText(String.valueOf(count) + " of " + String.valueOf(totalLayout));
            }


        } else if (visibleView == 7) {
            fragmentFeedbackBinding.question1Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView2.setVisibility(View.GONE);
            fragmentFeedbackBinding.question2Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question3Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question4Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question5Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question6Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question7Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question8Linear.setVisibility(View.VISIBLE);
            fragmentFeedbackBinding.question8ImageAnsRcv.setVisibility(View.GONE);
            fragmentFeedbackBinding.question8TxtAnsRcv.setVisibility(View.VISIBLE);
            fragmentFeedbackBinding.question9Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView10.setVisibility(View.GONE);
            fragmentFeedbackBinding.question10Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView11.setVisibility(View.GONE);
            fragmentFeedbackBinding.question11Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView12.setVisibility(View.GONE);
            fragmentFeedbackBinding.question12Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView13.setVisibility(View.GONE);
            fragmentFeedbackBinding.question13Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView14.setVisibility(View.GONE);
            fragmentFeedbackBinding.question14Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView15.setVisibility(View.GONE);
            fragmentFeedbackBinding.question15Linear.setVisibility(View.GONE);

            Animation animSlideout = AnimationUtils.loadAnimation(mContext, R.anim.slide_out_left_new);

            fragmentFeedbackBinding.question7Linear.startAnimation(animSlideout);

            // Load the animation like this
            Animation animSlidein = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_right_new);
// Start the animation like this
            fragmentFeedbackBinding.question8Linear.startAnimation(animSlidein);

            if (count <= 9) {
                fragmentFeedbackBinding.submitTxt.setText("0" + String.valueOf(count) + " of " + String.valueOf(totalLayout));
            } else {
                fragmentFeedbackBinding.submitTxt.setText(String.valueOf(count) + " of " + String.valueOf(totalLayout));
            }


        } else if (visibleView == 8) {
            fragmentFeedbackBinding.question1Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView2.setVisibility(View.VISIBLE);
            fragmentFeedbackBinding.question2Linear.setVisibility(View.VISIBLE);
            fragmentFeedbackBinding.question3Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question4Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question5Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question6Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question7Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question8Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question9Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView10.setVisibility(View.GONE);
            fragmentFeedbackBinding.question10Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView11.setVisibility(View.GONE);
            fragmentFeedbackBinding.question11Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView12.setVisibility(View.GONE);
            fragmentFeedbackBinding.question12Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView13.setVisibility(View.GONE);
            fragmentFeedbackBinding.question13Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView14.setVisibility(View.GONE);
            fragmentFeedbackBinding.question14Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView15.setVisibility(View.GONE);
            fragmentFeedbackBinding.question15Linear.setVisibility(View.GONE);

            Animation animSlideout = AnimationUtils.loadAnimation(mContext, R.anim.slide_out_left_new);

            fragmentFeedbackBinding.question8Linear.startAnimation(animSlideout);

            // Load the animation like this
            Animation animSlidein = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_right_new);
// Start the animation like this
            fragmentFeedbackBinding.question2Linear.startAnimation(animSlidein);

            if (count <= 9) {
                fragmentFeedbackBinding.submitTxt.setText("0" + String.valueOf(count) + " of " + String.valueOf(totalLayout));
            } else {
                fragmentFeedbackBinding.submitTxt.setText(String.valueOf(count) + " of " + String.valueOf(totalLayout));
            }


        } else if (visibleView == 9) {
            fragmentFeedbackBinding.question1Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView2.setVisibility(View.GONE);
            fragmentFeedbackBinding.question2Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question3Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question4Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question5Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question6Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question7Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question8Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question9Linear.setVisibility(View.VISIBLE);
            fragmentFeedbackBinding.question9ImageAnsRcv.setVisibility(View.GONE);
            fragmentFeedbackBinding.question9TxtAnsRcv.setVisibility(View.VISIBLE);
            fragmentFeedbackBinding.scrollView10.setVisibility(View.GONE);
            fragmentFeedbackBinding.question10Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView11.setVisibility(View.GONE);
            fragmentFeedbackBinding.question11Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView12.setVisibility(View.GONE);
            fragmentFeedbackBinding.question12Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView13.setVisibility(View.GONE);
            fragmentFeedbackBinding.question13Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView14.setVisibility(View.GONE);
            fragmentFeedbackBinding.question14Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView15.setVisibility(View.GONE);
            fragmentFeedbackBinding.question15Linear.setVisibility(View.GONE);
            Animation animSlideout = AnimationUtils.loadAnimation(mContext, R.anim.slide_out_left_new);

            fragmentFeedbackBinding.question2Linear.startAnimation(animSlideout);

            // Load the animation like this
            Animation animSlidein = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_right_new);
// Start the animation like this
            fragmentFeedbackBinding.question9Linear.startAnimation(animSlidein);

            if (count <= 9) {
                fragmentFeedbackBinding.submitTxt.setText("0" + String.valueOf(count) + " of " + String.valueOf(totalLayout));
            } else {
                fragmentFeedbackBinding.submitTxt.setText(String.valueOf(count) + " of " + String.valueOf(totalLayout));
            }


        } else if (visibleView == 10) {
            fragmentFeedbackBinding.question1Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView2.setVisibility(View.GONE);
            fragmentFeedbackBinding.question2Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question3Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question4Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question5Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question6Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question7Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question8Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question9Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView10.setVisibility(View.VISIBLE);
            fragmentFeedbackBinding.question10Linear.setVisibility(View.VISIBLE);
            fragmentFeedbackBinding.scrollView11.setVisibility(View.GONE);
            fragmentFeedbackBinding.question11Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView12.setVisibility(View.GONE);
            fragmentFeedbackBinding.question12Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView13.setVisibility(View.GONE);
            fragmentFeedbackBinding.question13Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView14.setVisibility(View.GONE);
            fragmentFeedbackBinding.question14Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView15.setVisibility(View.GONE);
            fragmentFeedbackBinding.question15Linear.setVisibility(View.GONE);

            Animation animSlideout = AnimationUtils.loadAnimation(mContext, R.anim.slide_out_left_new);

            fragmentFeedbackBinding.question9Linear.startAnimation(animSlideout);

            // Load the animation like this
            Animation animSlidein = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_right_new);
// Start the animation like this
            fragmentFeedbackBinding.question10Linear.startAnimation(animSlidein);

            if (count <= 9) {
                fragmentFeedbackBinding.submitTxt.setText("0" + String.valueOf(count) + " of " + String.valueOf(totalLayout));
            } else {
                fragmentFeedbackBinding.submitTxt.setText(String.valueOf(count) + " of " + String.valueOf(totalLayout));
            }


        } else if (visibleView == 11) {
            fragmentFeedbackBinding.question1Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView2.setVisibility(View.GONE);
            fragmentFeedbackBinding.question2Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question3Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question4Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question5Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question6Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question7Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question8Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question9Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView10.setVisibility(View.GONE);
            fragmentFeedbackBinding.question10Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView11.setVisibility(View.VISIBLE);
            fragmentFeedbackBinding.question11Linear.setVisibility(View.VISIBLE);
            fragmentFeedbackBinding.scrollView12.setVisibility(View.GONE);
            fragmentFeedbackBinding.question12Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView13.setVisibility(View.GONE);
            fragmentFeedbackBinding.question13Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView14.setVisibility(View.GONE);
            fragmentFeedbackBinding.question14Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView15.setVisibility(View.GONE);
            fragmentFeedbackBinding.question15Linear.setVisibility(View.GONE);

            Animation animSlideout = AnimationUtils.loadAnimation(mContext, R.anim.slide_out_left_new);

            fragmentFeedbackBinding.question10Linear.startAnimation(animSlideout);

            // Load the animation like this
            Animation animSlidein = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_right_new);
// Start the animation like this
            fragmentFeedbackBinding.question11Linear.startAnimation(animSlidein);

            if (count <= 9) {
                fragmentFeedbackBinding.submitTxt.setText("0" + String.valueOf(count) + " of " + String.valueOf(totalLayout));
            } else {
                fragmentFeedbackBinding.submitTxt.setText(String.valueOf(count) + " of " + String.valueOf(totalLayout));
            }


        } else if (visibleView == 12) {
            fragmentFeedbackBinding.question1Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView2.setVisibility(View.GONE);
            fragmentFeedbackBinding.question2Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question3Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question4Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question5Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question6Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question7Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question8Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question9Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView10.setVisibility(View.GONE);
            fragmentFeedbackBinding.question10Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView11.setVisibility(View.GONE);
            fragmentFeedbackBinding.question11Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView12.setVisibility(View.VISIBLE);
            fragmentFeedbackBinding.question12Linear.setVisibility(View.VISIBLE);
            fragmentFeedbackBinding.scrollView13.setVisibility(View.GONE);
            fragmentFeedbackBinding.question13Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView14.setVisibility(View.GONE);
            fragmentFeedbackBinding.question14Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView15.setVisibility(View.GONE);
            fragmentFeedbackBinding.question15Linear.setVisibility(View.GONE);

            Animation animSlideout = AnimationUtils.loadAnimation(mContext, R.anim.slide_out_left_new);

            fragmentFeedbackBinding.question11Linear.startAnimation(animSlideout);

            // Load the animation like this
            Animation animSlidein = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_right_new);
// Start the animation like this
            fragmentFeedbackBinding.question12Linear.startAnimation(animSlidein);

            if (count <= 9) {
                fragmentFeedbackBinding.submitTxt.setText("0" + String.valueOf(count) + " of " + String.valueOf(totalLayout));
            } else {
                fragmentFeedbackBinding.submitTxt.setText(String.valueOf(count) + " of " + String.valueOf(totalLayout));
            }


        } else if (visibleView == 13) {
            fragmentFeedbackBinding.question1Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView2.setVisibility(View.GONE);
            fragmentFeedbackBinding.question2Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question3Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question4Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question5Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question6Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question7Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question8Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question9Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView10.setVisibility(View.GONE);
            fragmentFeedbackBinding.question10Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView11.setVisibility(View.GONE);
            fragmentFeedbackBinding.question11Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView12.setVisibility(View.GONE);
            fragmentFeedbackBinding.question12Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView13.setVisibility(View.VISIBLE);
            fragmentFeedbackBinding.question13Linear.setVisibility(View.VISIBLE);
            fragmentFeedbackBinding.scrollView14.setVisibility(View.GONE);
            fragmentFeedbackBinding.question14Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView15.setVisibility(View.GONE);
            fragmentFeedbackBinding.question15Linear.setVisibility(View.GONE);
            Animation animSlideout = AnimationUtils.loadAnimation(mContext, R.anim.slide_out_left_new);

            fragmentFeedbackBinding.question12Linear.startAnimation(animSlideout);

            // Load the animation like this
            Animation animSlidein = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_right_new);
// Start the animation like this
            fragmentFeedbackBinding.question13Linear.startAnimation(animSlidein);

            if (count <= 9) {
                fragmentFeedbackBinding.submitTxt.setText("0" + String.valueOf(count) + " of " + String.valueOf(totalLayout));
            } else {
                fragmentFeedbackBinding.submitTxt.setText(String.valueOf(count) + " of " + String.valueOf(totalLayout));
            }


        } else if (visibleView == 14) {
            fragmentFeedbackBinding.question1Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView2.setVisibility(View.GONE);
            fragmentFeedbackBinding.question2Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question3Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question4Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question5Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question6Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question7Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question8Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question9Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView10.setVisibility(View.GONE);
            fragmentFeedbackBinding.question10Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView11.setVisibility(View.GONE);
            fragmentFeedbackBinding.question11Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView12.setVisibility(View.GONE);
            fragmentFeedbackBinding.question12Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView13.setVisibility(View.GONE);
            fragmentFeedbackBinding.question13Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView14.setVisibility(View.VISIBLE);
            fragmentFeedbackBinding.question14Linear.setVisibility(View.VISIBLE);
            fragmentFeedbackBinding.scrollView15.setVisibility(View.GONE);
            fragmentFeedbackBinding.question15Linear.setVisibility(View.GONE);

            Animation animSlideout = AnimationUtils.loadAnimation(mContext, R.anim.slide_out_left_new);

            fragmentFeedbackBinding.question13Linear.startAnimation(animSlideout);

            // Load the animation like this
            Animation animSlidein = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_right_new);
// Start the animation like this
            fragmentFeedbackBinding.question14Linear.startAnimation(animSlidein);

            if (count <= 9) {
                fragmentFeedbackBinding.submitTxt.setText("0" + String.valueOf(count) + " of " + String.valueOf(totalLayout));
            } else {
                fragmentFeedbackBinding.submitTxt.setText(String.valueOf(count) + " of " + String.valueOf(totalLayout));
            }


        } else if (visibleView == 15) {
            fragmentFeedbackBinding.question1Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView2.setVisibility(View.GONE);
            fragmentFeedbackBinding.question2Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question3Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question4Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question5Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question6Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question7Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question8Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.question9Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView10.setVisibility(View.GONE);
            fragmentFeedbackBinding.question10Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView11.setVisibility(View.GONE);
            fragmentFeedbackBinding.question11Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView12.setVisibility(View.GONE);
            fragmentFeedbackBinding.question12Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView13.setVisibility(View.GONE);
            fragmentFeedbackBinding.question13Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView14.setVisibility(View.GONE);
            fragmentFeedbackBinding.question14Linear.setVisibility(View.GONE);
            fragmentFeedbackBinding.scrollView15.setVisibility(View.VISIBLE);
            fragmentFeedbackBinding.question15Linear.setVisibility(View.VISIBLE);

            Animation animSlideout = AnimationUtils.loadAnimation(mContext, R.anim.slide_out_left_new);

            fragmentFeedbackBinding.question14Linear.startAnimation(animSlideout);

            // Load the animation like this
            Animation animSlidein = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_right_new);
// Start the animation like this
            fragmentFeedbackBinding.question15Linear.startAnimation(animSlidein);

            if (count <= 9) {
                fragmentFeedbackBinding.submitTxt.setText("0" + String.valueOf(count) + " of " + String.valueOf(totalLayout));
            } else {
                fragmentFeedbackBinding.submitTxt.setText(String.valueOf(count) + " of " + String.valueOf(totalLayout));
            }


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
        fragmentFeedbackBinding.scrollView15.setVisibility(View.GONE);
        fragmentFeedbackBinding.question15Linear.setVisibility(View.GONE);
        fragmentFeedbackBinding.question1Linear.setVisibility(View.GONE);


    }

    public void setSelectedValueInList() {
        /*Get the value of Edittext*/
        getEdittextValue();

        /*get question1 ans*/
        ArrayList<String> selectedquestion1anslist = new ArrayList<>();
        for (int i = 0; i < feedbackanslist.size(); i++) {
            if (feedbackanslist.get(i).getCityHotelAmenitiesName().equalsIgnoreCase("1")) {
                selectedquestion1anslist.add(feedbackanslist.get(i).getCityHotelAmenitiesImage());
                Log.d("selectedfeautre :", selectedquestion1anslist.toString());
            }
        }

        question1ansStr = "";
        if (selectedquestion1anslist.size() != 0) {
            for (String s : selectedquestion1anslist) {
                question1ansStr = question1ansStr + ", " + s;
            }
            Log.d("question1ansStr", question1ansStr);
            question1ansStr = question1ansStr.substring(1, question1ansStr.length());
            Log.d("finalquestion1ansStr", question1ansStr);
        }

        /*get question3 ans*/
        ArrayList<String> selectedquestion3anslist = new ArrayList<>();
        for (int i = 0; i < feedbacksinglechoiceans3list.size(); i++) {
            if (feedbacksinglechoiceans3list.get(i).getCityHotelAmenitiesName().equalsIgnoreCase("1")) {
                selectedquestion3anslist.add(feedbacksinglechoiceans3list.get(i).getCityHotelAmenitiesImage());
                Log.d("selectednewfeautre :", selectedquestion3anslist.toString());
            }
        }

        question3ansStr = "";
        if (selectedquestion3anslist.size() != 0) {
            for (String s : selectedquestion3anslist) {
                question3ansStr = question3ansStr + "," + s;
            }
            Log.d("question3ansStr", question3ansStr);
            question3ansStr = question3ansStr.substring(1, question3ansStr.length());
            Log.d("finalquestion3ansStr", question3ansStr);
        }

        /*get question4 ans*/
        ArrayList<String> selectedquestion4anslist = new ArrayList<>();
        for (int i = 0; i < feedbackansimagelist.size(); i++) {
            if (feedbackansimagelist.get(i).getFeedbackOptionselectedStr().equalsIgnoreCase("1")) {
                selectedquestion4anslist.add(String.valueOf(feedbackansimagelist.get(i).getFeedbackIconImage()));
                Log.d("goal :", selectedquestion4anslist.toString());
            }
        }

        question4ansStr = "";
        if (selectedquestion4anslist.size() != 0) {
            for (String s : selectedquestion4anslist) {
                question4ansStr = question4ansStr + "," + s;
            }
            Log.d("question4ansStr", question4ansStr);
            question4ansStr = question4ansStr.substring(1, question4ansStr.length());
            Log.d("finalquestion4ansStr", question4ansStr);
        }


        /*get question5 ans*/
        ArrayList<String> selectedquestion5anslist = new ArrayList<>();
        for (int i = 0; i < feedbacksinglechoiceans5list.size(); i++) {
            if (feedbacksinglechoiceans5list.get(i).getCityHotelAmenitiesName().equalsIgnoreCase("1")) {
                selectedquestion5anslist.add(feedbacksinglechoiceans5list.get(i).getCityHotelAmenitiesImage());
                Log.d("friends recommend :", selectedquestion5anslist.toString());
            }
        }

        question5ansStr = "";
        if (selectedquestion5anslist.size() != 0) {
            for (String s : selectedquestion5anslist) {
                question5ansStr = question5ansStr + "," + s;
            }
            Log.d("question5ansStr", question5ansStr);
            question5ansStr = question5ansStr.substring(1, question5ansStr.length());
            Log.d("finalquestion5ansStr", question5ansStr);
        }

        /*get question7 ans*/
        ArrayList<String> selectedquestion6anslist = new ArrayList<>();
        for (int i = 0; i < feedbacksinglechoiceans6list.size(); i++) {
            if (feedbacksinglechoiceans6list.get(i).getCityHotelAmenitiesName().equalsIgnoreCase("1")) {
                selectedquestion6anslist.add(feedbacksinglechoiceans6list.get(i).getCityHotelAmenitiesImage());
                Log.d("review give :", selectedquestion6anslist.toString());
            }
        }

        question6ansStr = "";
        if (selectedquestion6anslist.size() != 0) {
            for (String s : selectedquestion6anslist) {
                question6ansStr = question6ansStr + "," + s;
            }
            Log.d("question6ansStr", question6ansStr);
            question6ansStr = question6ansStr.substring(1, question6ansStr.length());
            Log.d("finalquestion6ansStr", question6ansStr);
        }

        /*get question8 ans*/
        ArrayList<String> selectedquestion7anslist = new ArrayList<>();
        for (int i = 0; i < feedbackansfeaturelist.size(); i++) {
            if (feedbackansfeaturelist.get(i).getCityHotelAmenitiesName().equalsIgnoreCase("1")) {
                selectedquestion7anslist.add(feedbackansfeaturelist.get(i).getCityHotelAmenitiesImage());
                Log.d("feature use :", selectedquestion7anslist.toString());
            }
        }

        question7ansStr = "";
        if (selectedquestion7anslist.size() != 0) {
            for (String s : selectedquestion7anslist) {
                question7ansStr = question7ansStr + ", " + s;
            }
            Log.d("question7ansStr", question7ansStr);
            question7ansStr = question7ansStr.substring(1, question7ansStr.length());
            Log.d("finalquestion7ansStr", question7ansStr);
        }

        /*get question9 ans*/
        ArrayList<String> selectedquestion8anslist = new ArrayList<>();
        for (int i = 0; i < feedbackratinglist.size(); i++) {
            if (feedbackratinglist.get(i).getPopularcity_name().equalsIgnoreCase("1")) {
                selectedquestion8anslist.add(feedbackratinglist.get(i).getPopularcity_image());
                Log.d("app rating :", selectedquestion8anslist.toString());
            }
        }

        question8ansStr = "";
        if (selectedquestion8anslist.size() != 0) {
            for (String s : selectedquestion8anslist) {
                question8ansStr = question8ansStr + "," + s;
            }
            Log.d("question8ansStr", question8ansStr);
            question8ansStr = question8ansStr.substring(1, question8ansStr.length());
            Log.d("finalquestion8ansStr", question8ansStr);
        }


        /*get question10 ans*/
        ArrayList<String> selectedquestion9anslist = new ArrayList<>();
        for (int i = 0; i < feedbacknewfeatureratinglist.size(); i++) {
            if (feedbacknewfeatureratinglist.get(i).getPopularcity_name().equalsIgnoreCase("1")) {
                selectedquestion9anslist.add(feedbacknewfeatureratinglist.get(i).getPopularcity_image());
                Log.d("new feature rating :", selectedquestion9anslist.toString());
            }
        }

        question9ansStr = "";
        if (selectedquestion9anslist.size() != 0) {
            for (String s : selectedquestion9anslist) {
                question9ansStr = question9ansStr + "," + s;
            }
            Log.d("question9ansStr", question9ansStr);
            question9ansStr = question9ansStr.substring(1, question9ansStr.length());
            Log.d("finalquestion9ansStr", question9ansStr);
        }
        if (!question4ansStr.equalsIgnoreCase("")) {
            question4imageStr = "1";
        }

        /*Question ans view list*/
        feedbackviewanslist = new ArrayList<>();
        feedbackviewanslist.add(new TravelModel(getResources().getString(R.string.question1), question1ansStr, ""));
        feedbackviewanslist.add(new TravelModel(getResources().getString(R.string.question3), question3ansStr, ""));
        feedbackviewanslist.add(new TravelModel(getResources().getString(R.string.question4), question4ansStr, question4imageStr));
        feedbackviewanslist.add(new TravelModel(getResources().getString(R.string.question5), question5ansStr, ""));
        feedbackviewanslist.add(new TravelModel(getResources().getString(R.string.question6), question6ansStr, ""));
        feedbackviewanslist.add(new TravelModel(getResources().getString(R.string.question7), question7ansStr, ""));
        feedbackviewanslist.add(new TravelModel(getResources().getString(R.string.question8), question8ansStr, ""));
        feedbackviewanslist.add(new TravelModel(getResources().getString(R.string.question2), question2ansStr, ""));
        feedbackviewanslist.add(new TravelModel(getResources().getString(R.string.question9), question9ansStr, ""));
        feedbackviewanslist.add(new TravelModel(getResources().getString(R.string.question10), question10ansStr, ""));
        feedbackviewanslist.add(new TravelModel(getResources().getString(R.string.question11), question11ansStr, ""));
        feedbackviewanslist.add(new TravelModel(getResources().getString(R.string.question12), question12ansStr, ""));
        feedbackviewanslist.add(new TravelModel(getResources().getString(R.string.question13), question13ansStr, ""));
        feedbackviewanslist.add(new TravelModel(getResources().getString(R.string.question14), question14ansStr, ""));
        feedbackviewanslist.add(new TravelModel(getResources().getString(R.string.question15), question15ansStr, ""));

        feedbackViewAdapter = new FeedbackViewAdapter(mContext, feedbackviewanslist);
        ansviewmLayout = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        fragmentFeedbackBinding.questionAnsViewRcv.setLayoutManager(ansviewmLayout);
        fragmentFeedbackBinding.questionAnsViewRcv.setItemAnimator(new DefaultItemAnimator());
        fragmentFeedbackBinding.questionAnsViewRcv.setAdapter(feedbackViewAdapter);
    }

    public void getEdittextValue() { /*get question2 ans*/
        question2ansStr = fragmentFeedbackBinding.question2AnsTxt.getText().toString();
        /*get question10 ans*/
        question10ansStr = fragmentFeedbackBinding.question10AnsTxt.getText().toString();
        /*get question11 ans*/
        question11ansStr = fragmentFeedbackBinding.question11AnsTxt.getText().toString();
        /*get question12 ans*/
        question12ansStr = fragmentFeedbackBinding.question12AnsTxt.getText().toString();
        /*get question13 ans*/
        question13ansStr = fragmentFeedbackBinding.question13AnsTxt.getText().toString();
        /*get question14 ans*/
        question14ansStr = fragmentFeedbackBinding.question14AnsTxt.getText().toString();
        /*get question15 ans*/
        question15ansStr = fragmentFeedbackBinding.question15AnsTxt.getText().toString();



    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Utils.dismissDialog();
    }
}
