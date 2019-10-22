package com.bharatarmy.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.bharatarmy.Adapter.OtherMatchShowAdapter;
import com.bharatarmy.R;
import com.bharatarmy.databinding.ActivityTravelMatchTicketAndHosipitalityticketdetailBinding;
import com.bharatarmy.listener.HidingScrollListener;

import java.util.ArrayList;


public class TravelMatchTicketAndHosipitalityticketdetailActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityTravelMatchTicketAndHosipitalityticketdetailBinding activityTravelMatchTicketAndHosipitalityticketdetailBinding;
    Context mContext;
    OtherMatchShowAdapter otherMatchShowAdapter;
    ArrayList<String> othermatchshowList;
    private boolean isUserScrolling = false;
    private boolean isListGoingUp = true;
    LinearLayoutManager linearLayoutManager;
    int findfirstposition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTravelMatchTicketAndHosipitalityticketdetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_travel_match_ticket_and_hosipitalityticketdetail);
        mContext = TravelMatchTicketAndHosipitalityticketdetailActivity.this;

        init();
        setListiner();
        setDataValue();
    }

    public void init() {

        if (getIntent().getStringExtra("categoryName") != null) {
            activityTravelMatchTicketAndHosipitalityticketdetailBinding.toolbarTitleTxt.setText(getIntent().getStringExtra("categoryName"));
        }

//        mWebView.getSettings().setJavaScriptEnabled(true);
//        mWebView.getSettings().setDomStorageEnabled(true);
//        mWebView.getSettings().setDatabaseEnabled(true);
//        mWebView.getSettings().setDatabasePath(dbpath); //check the documentation for info about dbpath
//        mWebView.getSettings().setMinimumFontSize(1);
//        mWebView.getSettings().setMinimumLogicalFontSize(1);
    }

    public void setListiner() {
        activityTravelMatchTicketAndHosipitalityticketdetailBinding.backImg.setOnClickListener(this);

//        activityTravelMatchTicketAndHosipitalityticketdetailBinding.otherMatchRcv.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                if(newState ==  RecyclerView.SCROLL_STATE_DRAGGING){
//                    LinearLayoutManager layoutManager = LinearLayoutManager.class.cast(recyclerView.getLayoutManager());
//                    isUserScrolling = true;
//                    if(isListGoingUp){
//                        //my recycler view is actually inverted so I have to write this condition instead
//                        if(layoutManager.findLastCompletelyVisibleItemPosition() + 1 == othermatchshowList.size()+1){
//                            Handler handler = new Handler();
//                            handler.postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    if(isListGoingUp) {
//                                        if (layoutManager.findLastCompletelyVisibleItemPosition() + 1 == othermatchshowList.size()+1) {
////                                            Toast.makeText(getContext(),"exeute something", Toast.LENGTH_SHORT).show();
//                                        }
//                                    }
//                                }
//                            },50);
//                            //waiting for 50ms because when scrolling down from top, the variable isListGoingUp is still true until the onScrolled method is executed
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                if(isUserScrolling){
//                    Log.d("dy :" ,""+dy);
//                    if(dy > 0){
//                        //means user finger is moving up but the list is going down
//                        isListGoingUp = false;
//
//                        activityTravelMatchTicketAndHosipitalityticketdetailBinding.cartView.setVisibility(View.VISIBLE);
//                    }
//                    else{
//                        //means user finger is moving down but the list is going up
//                        isListGoingUp = true;
//
//                        activityTravelMatchTicketAndHosipitalityticketdetailBinding.cartView.setVisibility(View.GONE);
//
//                    }
//                }
//            }
//        });



//        activityTravelMatchTicketAndHosipitalityticketdetailBinding.otherMatchRcv.addOnScrollListener(new HidingScrollListener() {
//            @Override
//            public void onHide() {
//                activityTravelMatchTicketAndHosipitalityticketdetailBinding.cartView.setVisibility(View.GONE);
//            }
//
//            @Override
//            public void onShow() {
//                activityTravelMatchTicketAndHosipitalityticketdetailBinding.cartView.setVisibility(View.VISIBLE);
//            }
//        });


        activityTravelMatchTicketAndHosipitalityticketdetailBinding.otherMatchRcv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                findfirstposition=linearLayoutManager.findFirstCompletelyVisibleItemPosition();
                if (findfirstposition == -1){
                    findfirstposition=findfirstposition+1;
                }
                Log.d("findfirstposition :",""+findfirstposition);
                if (findfirstposition > 0 && findfirstposition > 1 ){
                    activityTravelMatchTicketAndHosipitalityticketdetailBinding.cartView.setVisibility(View.VISIBLE);
                }else{
                    activityTravelMatchTicketAndHosipitalityticketdetailBinding.cartView.setVisibility(View.GONE);
                }
            }
        });
    }

    public void setDataValue() {
        activityTravelMatchTicketAndHosipitalityticketdetailBinding.shimmerViewContainer.setVisibility(View.GONE);
        othermatchshowList = new ArrayList<>();
        othermatchshowList.add("1");
        othermatchshowList.add("2");
        othermatchshowList.add("3");
        othermatchshowList.add("1");
        othermatchshowList.add("2");
        othermatchshowList.add("3");
        otherMatchShowAdapter = new OtherMatchShowAdapter(mContext, othermatchshowList);
        linearLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        activityTravelMatchTicketAndHosipitalityticketdetailBinding.otherMatchRcv.setLayoutManager(linearLayoutManager);
        activityTravelMatchTicketAndHosipitalityticketdetailBinding.otherMatchRcv.setItemAnimator(new DefaultItemAnimator());
        activityTravelMatchTicketAndHosipitalityticketdetailBinding.otherMatchRcv.setAdapter(otherMatchShowAdapter);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                finish();
                break;
        }
    }
}
