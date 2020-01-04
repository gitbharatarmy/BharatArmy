package com.bharatarmy.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.bharatarmy.Adapter.OtherMatchShowAdapter;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityTravelMatchTicketAndHosipitalityticketdetailBinding;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

// remove extra code 25/10/2019 backup on 25/10/2019
public class TravelMatchTicketAndHosipitalityticketdetailActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityTravelMatchTicketAndHosipitalityticketdetailBinding activityTravelMatchTicketAndHosipitalityticketdetailBinding;
    Context mContext;
    OtherMatchShowAdapter otherMatchShowAdapter;
    ArrayList<String> othermatchshowList;
    LinearLayoutManager linearLayoutManager;

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

    }

    public void setListiner() {
        activityTravelMatchTicketAndHosipitalityticketdetailBinding.backImg.setOnClickListener(this);
        activityTravelMatchTicketAndHosipitalityticketdetailBinding.cartView.setOnClickListener(this);

    }

    public void setDataValue() {
                Picasso.with(mContext)
                .load("http://devenv.bharatarmy.com/docs/stadium_map.jpg")
                .placeholder(R.drawable.loader_new)
                .resize(Resources.getSystem().getDisplayMetrics().widthPixels,activityTravelMatchTicketAndHosipitalityticketdetailBinding.webView.getHeight())
       .into(activityTravelMatchTicketAndHosipitalityticketdetailBinding.webView);
        activityTravelMatchTicketAndHosipitalityticketdetailBinding.shimmerViewContainer.setVisibility(View.GONE);
        othermatchshowList = new ArrayList<>();
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
            case R.id.cart_view:
                Utils.handleClickEvent(mContext,activityTravelMatchTicketAndHosipitalityticketdetailBinding.cartView);
                if (Utils.isMember(mContext,"Ticket Detail")){
                Intent cartIntent=new Intent(mContext,AddToCartActivity.class);
                cartIntent.putExtra("bookingItemName",activityTravelMatchTicketAndHosipitalityticketdetailBinding.toolbarTitleTxt.getText().toString());
                cartIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(cartIntent);
                }

                break;
        }
    }
}
