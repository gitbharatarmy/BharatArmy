package com.bharatarmy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bharatarmy.Adapter.BaPollResultAdapter;
import com.bharatarmy.Adapter.BaPollSingleChoiceAdapter;
import com.bharatarmy.Models.BAPollDatum;
import com.bharatarmy.Models.BAShopMainModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.Utility.WebServices;
import com.bharatarmy.databinding.ActivityBaPollResultBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaPollResultActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityBaPollResultBinding activityBaPollResultBinding;
    /*Activity variable*/
    private Context mContext;
    public String isUpdateAvailable, isForceUpdateAvailable, currentVersionStr;


    /*text Singlechoice adapter*/
    BaPollResultAdapter baPollResultAdapter;
    LinearLayoutManager linearLayoutManager;
    List<BAPollDatum> bapollresultsinglechoicelist;


    /*data model list*/
    List<BAPollDatum> bapollresultList;
    String answerTextStr;

    boolean isKeyboardShowing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBaPollResultBinding = DataBindingUtil.setContentView(this, R.layout.activity_ba_poll_result);

        mContext = BaPollResultActivity.this;

        init();
        setListiner();
    }

    public void init() {
        activityBaPollResultBinding.toolbarTitleTxt.setText("BA Poll");
        activityBaPollResultBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
        activityBaPollResultBinding.shimmerViewContainer.startShimmerAnimation();
        activityBaPollResultBinding.questionLinear.setVisibility(View.GONE);
        activityBaPollResultBinding.bapollTextsinglegridchoiceLinear.setVisibility(View.GONE);
        callBAPOllQuestionAnswerResultList();
    }

    public void setListiner() {
        activityBaPollResultBinding.backImg.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                backActivity();
                break;
            case R.id.submit_linear:
                getSelectedTextSingleChoiceValue();
                Utils.ping(mContext,"Thank you for your vote! ");
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
        activityBaPollResultBinding.shimmerViewContainer.stopShimmerAnimation();
        activityBaPollResultBinding.shimmerViewContainer.setVisibility(View.GONE);
        activityBaPollResultBinding.questionLinear.setVisibility(View.VISIBLE);
        activityBaPollResultBinding.bapollTextsinglegridchoiceLinear.setVisibility(View.VISIBLE);


        for (int i=0;i<bapollresultList.size();i++){
            activityBaPollResultBinding.questionTxt.setText(bapollresultList.get(i).getBAPollQuestion());
            bapollresultsinglechoicelist= bapollresultList.get(i).getBAPollQuestionAnswerData();
        }


        for (int i = 0; i < bapollresultsinglechoicelist.size(); i++) {
            bapollresultsinglechoicelist.get(i).setbAPollQuestionAnswerSelected("0");


            baPollResultAdapter = new BaPollResultAdapter(mContext, bapollresultsinglechoicelist);
            linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
            activityBaPollResultBinding.bapollTextsinglegridchoiceAnsRcv.setLayoutManager(linearLayoutManager);
            activityBaPollResultBinding.bapollTextsinglegridchoiceAnsRcv.setItemAnimator(new DefaultItemAnimator());
            activityBaPollResultBinding.bapollTextsinglegridchoiceAnsRcv.setAdapter(baPollResultAdapter);
        }
    }

    public void getSelectedTextSingleChoiceValue() {
        for (int i = 0; i < bapollresultsinglechoicelist.size(); i++) {
            if (bapollresultsinglechoicelist.get(i).getbAPollQuestionAnswerSelected().equalsIgnoreCase("1")) {
                answerTextStr = bapollresultsinglechoicelist.get(i).getBAPollQuestionAnswer();
            }
        }

    }





    public void callBAPOllQuestionAnswerResultList() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), BaPollResultActivity.this);
            return;

        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConfiguration.BASEURL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        WebServices api = retrofit.create(WebServices.class);

        Call<BAShopMainModel> call = api.getBAPollResultList("http://www.mocky.io/v2/5ebe71923100008400c5d302");

        call.enqueue(new Callback<BAShopMainModel>() {
            @Override
            public void onResponse(Call<BAShopMainModel> call, retrofit2.Response<BAShopMainModel> response) {

                if (response.body().getBAPollData() != null) {
                    bapollresultList = response.body().getBAPollData();
                    setDataValueInList();
                }
            }

            @Override
            public void onFailure(Call<BAShopMainModel> call, Throwable t) {
                Log.d("BAShopList Error:", t.getLocalizedMessage());
            }
        });

    }
}

