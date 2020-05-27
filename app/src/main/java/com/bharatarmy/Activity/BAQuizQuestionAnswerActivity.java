package com.bharatarmy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.ankushgrover.hourglass.Hourglass;
import com.bharatarmy.Adapter.BAQuizSingleChoiceAdapter;
import com.bharatarmy.Interfaces.MorestoryClick;
import com.bharatarmy.Models.BAPollDatum;
import com.bharatarmy.Models.BAShopMainModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.Utility.WebServices;
import com.bharatarmy.databinding.ActivityBAQuizQuestionAnswerBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BAQuizQuestionAnswerActivity extends AppCompatActivity implements View.OnClickListener {
    Context mContext;
    ActivityBAQuizQuestionAnswerBinding activityBAQuizQuestionAnswerBinding;
    List<BAPollDatum> baquizList;

    public int timecounter = 0;
    private long timeLeftInMillis;

    String url1, url2, url3, url4, url5, url6, url7, url8, url9, url10, answer1TextStr, quiztitle;

    /*text Singlechoice adapter*/
    BAQuizSingleChoiceAdapter baQuizSingleChoiceAdapter;
    LinearLayoutManager textsinglechoicelinearLayoutManager;
    List<BAPollDatum> baquiztextsinglechoicelist;
    int count = 1;
    int quiztime;
    String quizeResultType;
    long quizTimeresult;
    Hourglass quizTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBAQuizQuestionAnswerBinding = DataBindingUtil.setContentView(this, R.layout.activity_b_a_quiz_question_answer);
        mContext = BAQuizQuestionAnswerActivity.this;

        init();
        setListiner();
    }

    public void init() {
        quiztitle = getIntent().getStringExtra("quiztitle");
        quiztime = Integer.parseInt(getIntent().getStringExtra("quizTime"));
        quizeResultType = getIntent().getStringExtra("quizeResultType");
        activityBAQuizQuestionAnswerBinding.toolbarTitleTxt.setText(quiztitle);
        activityBAQuizQuestionAnswerBinding.shimmerViewContainer.startShimmerAnimation();
        activityBAQuizQuestionAnswerBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
        secondTomillis();

        setUrl();
        callNextBAQuiz(count);

    }

    public void secondTomillis() {
        quizTimeresult = TimeUnit.SECONDS.toMillis(quiztime);
        Log.d("millsresult", "" + quizTimeresult);
    }

    public void setListiner() {
        activityBAQuizQuestionAnswerBinding.backImg.setOnClickListener(this);
        activityBAQuizQuestionAnswerBinding.nextLinear.setOnClickListener(this);
        activityBAQuizQuestionAnswerBinding.timeProgress.setMax(quiztime+35);
        activityBAQuizQuestionAnswerBinding.timeProgress.setProgress(quiztime+35);
        activityBAQuizQuestionAnswerBinding.continueQuizBtn.setOnClickListener(this);


        quizTimer = new Hourglass(quizTimeresult, 1000) {
            @Override
            public void onTimerTick(long timeRemaining) {
                activityBAQuizQuestionAnswerBinding.timeupLinear.setVisibility(View.GONE);
                timeLeftInMillis = timeRemaining;
                activityBAQuizQuestionAnswerBinding.timeProgress.setProgress(quiztime+35 - timecounter);
                Log.d("time:", String.valueOf(timecounter));
                timecounter++;
                updateCountDownText();
            }

            @Override
            public void onTimerFinish() {
                activityBAQuizQuestionAnswerBinding.timeupLinear.setVisibility(View.VISIBLE);
                activityBAQuizQuestionAnswerBinding.timeDisplayLinear.setVisibility(View.GONE);
                activityBAQuizQuestionAnswerBinding.timeProgress.setVisibility(View.GONE);
                activityBAQuizQuestionAnswerBinding.thankyouLinear.setVisibility(View.GONE);
                activityBAQuizQuestionAnswerBinding.nextLinear.setVisibility(View.GONE);
                activityBAQuizQuestionAnswerBinding.questionLinear.setVisibility(View.GONE);
                activityBAQuizQuestionAnswerBinding.textsinglechoiceLinear.setVisibility(View.GONE);
                activityBAQuizQuestionAnswerBinding.shimmerViewContainer.setVisibility(View.GONE);
                activityBAQuizQuestionAnswerBinding.questioncountTxt.setVisibility(View.GONE);
            }
        };

        quizTimer.startTimer();
    }

    private void updateCountDownText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;
        Log.d("minutes :", "" + minutes + "seconds :" + "" + seconds);

        String timeFormatted = "Timer : " + minutes + " min " + seconds + " seconds ";
        Log.d("timeFormatted:", timeFormatted);
        activityBAQuizQuestionAnswerBinding.remainingTimeTxt.setText(timeFormatted);
    }

    public void setUrl() {
        url1 = "http://www.mocky.io/v2/5eb0140e3300004d00c68b5f";
        url2 = "http://www.mocky.io/v2/5eb014483300004d00c68b62";
        url3 = "http://www.mocky.io/v2/5eb014613300005000c68b64";
        url4 = "http://www.mocky.io/v2/5eb014943300005000c68b67";
        url5 = "http://www.mocky.io/v2/5eb014ac3300006000c68b68";
        url6 = "http://www.mocky.io/v2/5eb014c23300005c00c68b69";
        url7 = "http://www.mocky.io/v2/5eb014d63300002b00c68b6a";
        url8 = "http://www.mocky.io/v2/5eb015043300006932c68b6d";
        url9 = "http://www.mocky.io/v2/5eb015183300007400c68b6e";
        url10 = "http://www.mocky.io/v2/5eb0152d3300006100c68b6f";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                whereToback();
                break;
            case R.id.continue_quiz_btn:
                unfreezeLayout();
                goToNext();
                quizTimer.resumeTimer();
                break;
        }
    }

    public void goToNext() {
        getSelectedTextSingleChoiceValue();
        if (count <= 10) {
            activityBAQuizQuestionAnswerBinding.thankyouLinear.setVisibility(View.GONE);
            activityBAQuizQuestionAnswerBinding.nextLinear.setVisibility(View.GONE);
            activityBAQuizQuestionAnswerBinding.questionLinear.setVisibility(View.GONE);
            activityBAQuizQuestionAnswerBinding.textsinglechoiceLinear.setVisibility(View.GONE);
            activityBAQuizQuestionAnswerBinding.questioncountTxt.setVisibility(View.GONE);
            activityBAQuizQuestionAnswerBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
            activityBAQuizQuestionAnswerBinding.shimmerViewContainer.startShimmerAnimation();
            callNextBAQuiz(count);
        } else {
            activityBAQuizQuestionAnswerBinding.timeProgress.setVisibility(View.GONE);
            activityBAQuizQuestionAnswerBinding.timeDisplayLinear.setVisibility(View.GONE);
            activityBAQuizQuestionAnswerBinding.nextLinear.setVisibility(View.GONE);
            activityBAQuizQuestionAnswerBinding.questionLinear.setVisibility(View.GONE);
            activityBAQuizQuestionAnswerBinding.textsinglechoiceLinear.setVisibility(View.GONE);
            activityBAQuizQuestionAnswerBinding.shimmerViewContainer.setVisibility(View.GONE);
            activityBAQuizQuestionAnswerBinding.questioncountTxt.setVisibility(View.GONE);
            activityBAQuizQuestionAnswerBinding.thankyouLinear.setVisibility(View.VISIBLE);
        }
    }

    public void callNextBAQuiz(int count) {
        if (count == 1) {
            callBAQuiz(url1);
        } else if (count == 2) {
            callBAQuiz(url2);
        } else if (count == 3) {
            callBAQuiz(url3);
        } else if (count == 4) {
            callBAQuiz(url4);
        } else if (count == 5) {
            callBAQuiz(url5);
        } else if (count == 6) {
            callBAQuiz(url6);
        } else if (count == 7) {
            callBAQuiz(url7);
        } else if (count == 8) {
            callBAQuiz(url8);
        } else if (count == 9) {
            callBAQuiz(url9);
        } else if (count == 10) {
            callBAQuiz(url10);
        }
    }

    public void whereToback() {
        finish();
    }

    public void callBAQuiz(String url) {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), BAQuizQuestionAnswerActivity.this);
            return;

        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConfiguration.BASEURL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        WebServices api = retrofit.create(WebServices.class);

        Call<BAShopMainModel> call = api.getBAQuizList(url);

        call.enqueue(new Callback<BAShopMainModel>() {
            @Override
            public void onResponse(Call<BAShopMainModel> call, retrofit2.Response<BAShopMainModel> response) {

                if (response.body().getBAQuizData() != null) {
                    baquizList = response.body().getBAQuizData();
                    setDataValueInList();
                    count++;
                }
            }

            @Override
            public void onFailure(Call<BAShopMainModel> call, Throwable t) {
                Log.d("BAQuizList Error:", t.getLocalizedMessage());
            }
        });

    }

    public void setDataValueInList() {
        activityBAQuizQuestionAnswerBinding.shimmerViewContainer.stopShimmerAnimation();
        activityBAQuizQuestionAnswerBinding.shimmerViewContainer.setVisibility(View.GONE);
        activityBAQuizQuestionAnswerBinding.nextLinear.setVisibility(View.GONE);
        activityBAQuizQuestionAnswerBinding.questionLinear.setVisibility(View.VISIBLE);
        activityBAQuizQuestionAnswerBinding.textsinglechoiceLinear.setVisibility(View.VISIBLE);
        activityBAQuizQuestionAnswerBinding.timeDisplayLinear.setVisibility(View.VISIBLE);
        activityBAQuizQuestionAnswerBinding.questioncountTxt.setVisibility(View.VISIBLE);

        activityBAQuizQuestionAnswerBinding.questioncountTxt.setText(count + "/" + "10");

        for (int i = 0; i < baquizList.size(); i++) {
            activityBAQuizQuestionAnswerBinding.questionTxt.setText(baquizList.get(i).getBAQuizQuestion1());
            baquiztextsinglechoicelist = baquizList.get(i).getBAQuizQuestionAnswerData();
        }


        for (int i = 0; i < baquiztextsinglechoicelist.size(); i++) {
            baquiztextsinglechoicelist.get(i).setbAquizQuestionAnswerSelected("0");

            baQuizSingleChoiceAdapter = new BAQuizSingleChoiceAdapter(mContext, baquiztextsinglechoicelist, new MorestoryClick() {
                @Override
                public void getmorestoryClick() {
                    getSelectedTextSingleChoiceValue();
                    if (quizeResultType.equalsIgnoreCase("2")) {
                        freezeLayout();
                        overridePendingTransition(R.anim.slide_out_down, R.anim.slide_up_dialog);
                        activityBAQuizQuestionAnswerBinding.questionResultType2.setVisibility(View.VISIBLE);

                        quizTimer.pauseTimer();
                    } else {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                goToNext();
                            }
                        }, 500);

                    }


                }
            });
            textsinglechoicelinearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
            activityBAQuizQuestionAnswerBinding.textsinglechoiceAnsRcv.setLayoutManager(textsinglechoicelinearLayoutManager);
            activityBAQuizQuestionAnswerBinding.textsinglechoiceAnsRcv.setItemAnimator(new DefaultItemAnimator());
            activityBAQuizQuestionAnswerBinding.textsinglechoiceAnsRcv.setAdapter(baQuizSingleChoiceAdapter);
        }
    }

    public void getSelectedTextSingleChoiceValue() {
        for (int i = 0; i < baquiztextsinglechoicelist.size(); i++) {
            if (baquiztextsinglechoicelist.get(i).getbAquizQuestionAnswerSelected().equalsIgnoreCase("1")) {
                answer1TextStr = baquiztextsinglechoicelist.get(i).getBAQuizQuestionAnswer();

            }
        }

    }

    public void freezeLayout() {
        activityBAQuizQuestionAnswerBinding.backImg.setEnabled(false);
        activityBAQuizQuestionAnswerBinding.backImg.setClickable(false);
//        activityBAQuizQuestionAnswerBinding.textsinglechoiceAnsRcv.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener() {
//            @Override
//            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
//                // true: consume touch event
//                // false: dispatch touch event
//                return true;
//            }
//        });
    }

    public void unfreezeLayout() {
        activityBAQuizQuestionAnswerBinding.questionResultType2.setVisibility(View.GONE);
        activityBAQuizQuestionAnswerBinding.questionTimerLinear.setAlpha(1f);
        activityBAQuizQuestionAnswerBinding.backImg.setEnabled(true);
        activityBAQuizQuestionAnswerBinding.backImg.setClickable(true);
//        activityBAQuizQuestionAnswerBinding.textsinglechoiceAnsRcv.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener() {
//            @Override
//            public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
//                return false;
//            }
//        });
    }
}
