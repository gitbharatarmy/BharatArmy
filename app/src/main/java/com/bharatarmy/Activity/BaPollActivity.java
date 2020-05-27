package com.bharatarmy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.bharatarmy.Adapter.BaPollSingleChoiceAdapter;
import com.bharatarmy.Adapter.FeedbackImagewithTextAdapter;
import com.bharatarmy.Adapter.FeedbackRatingAdapter;
import com.bharatarmy.Adapter.FeedbackSingleChoiceAdapter;
import com.bharatarmy.Adapter.FeedbackTextMultiChoiceAdapter;
import com.bharatarmy.Adapter.FeedbackTextSingleChoiceGridAdapter;
import com.bharatarmy.Adapter.FeedbackViewAdapter;
import com.bharatarmy.Interfaces.MorestoryClick;
import com.bharatarmy.Models.BAPollDatum;
import com.bharatarmy.Models.BAShopListModel;
import com.bharatarmy.Models.BAShopMainModel;
import com.bharatarmy.Models.FeedbackAnswerList;
import com.bharatarmy.Models.FeedbackMainModel;
import com.bharatarmy.Models.LoginDataModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.ApiHandler;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.Utility.WebServices;
import com.bharatarmy.databinding.ActivityBaPollBinding;
import com.bharatarmy.databinding.ActivityFeedbackBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaPollActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityBaPollBinding activityBaPollBinding;
    /*Activity variable*/
    private Context mContext;
    public String isUpdateAvailable, isForceUpdateAvailable, currentVersionStr;


    /*text Singlechoice adapter*/
    BaPollSingleChoiceAdapter baPollSingleChoiceAdapter;
    LinearLayoutManager textsinglechoicelinearLayoutManager;
    List<BAPollDatum> bapolltextsinglechoicelist;


    /*data model list*/
    List<BAPollDatum> bapollList;
    String answerTextStr="";

    boolean isKeyboardShowing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBaPollBinding = DataBindingUtil.setContentView(this, R.layout.activity_ba_poll);

        mContext = BaPollActivity.this;

        init();
        setListiner();
    }

    public void init() {
        activityBaPollBinding.toolbarTitleTxt.setText("BA Poll");
        activityBaPollBinding.shimmerViewContainer.startShimmerAnimation();
        activityBaPollBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
        callBAPOllQuestionAnswerList();
    }

    public void setListiner() {
        activityBaPollBinding.submitLinear.setOnClickListener(this);;
        activityBaPollBinding.backImg.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                backActivity();
                break;
            case R.id.submit_linear:
                getSelectedTextSingleChoiceValue();
                if (!answerTextStr.equalsIgnoreCase("")){
                    Intent pollresult = new Intent(mContext,BaPollResultActivity.class);
                    startActivity(pollresult);
                    finish();
                }
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

    /*Set the TextSingleChoice value in layout*/
    public void setDataValueInList() {
        activityBaPollBinding.shimmerViewContainer.stopShimmerAnimation();
        activityBaPollBinding.shimmerViewContainer.setVisibility(View.GONE);
        activityBaPollBinding.topGradiantView.setVisibility(View.VISIBLE);
        activityBaPollBinding.submitLinear.setVisibility(View.VISIBLE);
        activityBaPollBinding.questionLinear.setVisibility(View.VISIBLE);
        activityBaPollBinding.bapollTextsinglegridchoiceLinear.setVisibility(View.VISIBLE);


        for (int i=0;i<bapollList.size();i++){
            activityBaPollBinding.questionTxt.setText(bapollList.get(i).getBAPollQuestion());
            bapolltextsinglechoicelist= bapollList.get(i).getBAPollQuestionAnswerData();
        }


        for (int i = 0; i < bapolltextsinglechoicelist.size(); i++) {
            bapolltextsinglechoicelist.get(i).setbAPollQuestionAnswerSelected("0");


            baPollSingleChoiceAdapter = new BaPollSingleChoiceAdapter(mContext, bapolltextsinglechoicelist);
            textsinglechoicelinearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
            activityBaPollBinding.bapollTextsinglegridchoiceAnsRcv.setLayoutManager(textsinglechoicelinearLayoutManager);
            activityBaPollBinding.bapollTextsinglegridchoiceAnsRcv.setItemAnimator(new DefaultItemAnimator());
            activityBaPollBinding.bapollTextsinglegridchoiceAnsRcv.setAdapter(baPollSingleChoiceAdapter);
        }
    }

    public void getSelectedTextSingleChoiceValue() {
        for (int i = 0; i < bapolltextsinglechoicelist.size(); i++) {
            if (bapolltextsinglechoicelist.get(i).getbAPollQuestionAnswerSelected().equalsIgnoreCase("1")) {
                answerTextStr = bapolltextsinglechoicelist.get(i).getBAPollQuestionAnswer();
            }
        }

    }
    public void callBAPOllQuestionAnswerList() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), BaPollActivity.this);
            return;

        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConfiguration.BASEURL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        WebServices api = retrofit.create(WebServices.class);

        Call<BAShopMainModel> call = api.getBAPollList("http://www.mocky.io/v2/5eaa69a92d000070002685ed");

        call.enqueue(new Callback<BAShopMainModel>() {
            @Override
            public void onResponse(Call<BAShopMainModel> call, retrofit2.Response<BAShopMainModel> response) {

                if (response.body().getBAPollData() != null) {
                    bapollList = response.body().getBAPollData();
                    setDataValueInList();
                }
            }

            @Override
            public void onFailure(Call<BAShopMainModel> call, Throwable t) {
                Log.d("BAShopList Error:", t.getLocalizedMessage());
            }
        });

    }


    public void setVoteResult(){

    }
}

