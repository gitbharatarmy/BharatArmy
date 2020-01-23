package com.bharatarmy.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bharatarmy.Adapter.OtherMatchShowAdapter;
import com.bharatarmy.Adapter.RelatedTicketCategoryAdapter;
import com.bharatarmy.Interfaces.MorestoryClick;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityTravelMatchTicketAndHosipitalityticketdetailBinding;
import com.squareup.picasso.Picasso;


import java.text.DecimalFormat;
import java.util.ArrayList;

// remove extra code 25/10/2019 backup on 25/10/2019
public class TravelMatchTicketAndHosipitalityticketdetailActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityTravelMatchTicketAndHosipitalityticketdetailBinding activityTravelMatchTicketAndHosipitalityticketdetailBinding;
    Context mContext;
    String toolbarTitleStr;

    //    related mathces list
    OtherMatchShowAdapter otherMatchShowAdapter;
    ArrayList<String> othermatchshowList;
    LinearLayoutManager linearLayoutManager;

    //    related ticket category list
    RelatedTicketCategoryAdapter relatedTicketCategoryAdapter;
    ArrayList<TravelModel> relatedticketCategorylist;
    ArrayList<TravelModel> relatedcategoryfinalList;
    LinearLayoutManager categorylinearLayoutManager;
    int selectedposition = -1;

    //    use for ticket price
    int ticketcount = 1;
    double totalamount = 0;

    //    use for show hide addTocart
    Rect scrollBounds;


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
        scrollBounds = new Rect();
        activityTravelMatchTicketAndHosipitalityticketdetailBinding.scrollViewTicketdetail.getHitRect(scrollBounds);


        if (getIntent().getStringExtra("categoryName") != null) {
            toolbarTitleStr = getIntent().getStringExtra("categoryName");
        }
        /*else if(Utils.getPref(mContext,"toolbarTitle")!=null){
            toolbarTitleStr=Utils.getPref(mContext,"toolbarTitle");
        }
Log.d("toolbartitle ",toolbarTitleStr);*/
        activityTravelMatchTicketAndHosipitalityticketdetailBinding.matchTicketTypeTagTxt.setText(toolbarTitleStr);


    }


    @SuppressLint("NewApi")
    public void setListiner() {
        activityTravelMatchTicketAndHosipitalityticketdetailBinding.backImg.setOnClickListener(this);
        activityTravelMatchTicketAndHosipitalityticketdetailBinding.bottomCartView.setOnClickListener(this);
        activityTravelMatchTicketAndHosipitalityticketdetailBinding.ticketPlusLayout.setOnClickListener(this);
        activityTravelMatchTicketAndHosipitalityticketdetailBinding.ticketMinusLayout.setOnClickListener(this);

//        activityTravelMatchTicketAndHosipitalityticketdetailBinding.scrollViewTicketdetail.setOnScrollChangeListener(new View.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                if (activityTravelMatchTicketAndHosipitalityticketdetailBinding.bottomCartView != null) {
//
//                    if (activityTravelMatchTicketAndHosipitalityticketdetailBinding.bottomCartView.getLocalVisibleRect(scrollBounds)) {
//                        if (!activityTravelMatchTicketAndHosipitalityticketdetailBinding.bottomCartView.getLocalVisibleRect(scrollBounds)
//                                || scrollBounds.height() < activityTravelMatchTicketAndHosipitalityticketdetailBinding.bottomCartView.getHeight()) {
//                            activityTravelMatchTicketAndHosipitalityticketdetailBinding.cartView.setVisibility(View.GONE);
//                        } else {
//                            activityTravelMatchTicketAndHosipitalityticketdetailBinding.cartView.setVisibility(View.GONE);
//                        }
//                    } else {
//                        activityTravelMatchTicketAndHosipitalityticketdetailBinding.cartView.setVisibility(View.VISIBLE);
//                    }
//                }
//            }
//        });
    }

    public void setDataValue() {
//        display stadium image
        Picasso.with(mContext)
                .load("http://devenv.bharatarmy.com/docs/stadium_map.jpg")
                .placeholder(R.drawable.loader_new)
                .resize(Resources.getSystem().getDisplayMetrics().widthPixels, activityTravelMatchTicketAndHosipitalityticketdetailBinding.webView.getHeight())
                .into(activityTravelMatchTicketAndHosipitalityticketdetailBinding.webView);
        activityTravelMatchTicketAndHosipitalityticketdetailBinding.shimmerViewContainer.setVisibility(View.GONE);

//        fill realeted matches list
        othermatchshowList = new ArrayList<>();
        othermatchshowList.add("1");
        othermatchshowList.add("2");
        othermatchshowList.add("3");

        otherMatchShowAdapter = new OtherMatchShowAdapter(mContext, othermatchshowList);
        linearLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        activityTravelMatchTicketAndHosipitalityticketdetailBinding.otherMatchRcv.setLayoutManager(linearLayoutManager);
        activityTravelMatchTicketAndHosipitalityticketdetailBinding.otherMatchRcv.setItemAnimator(new DefaultItemAnimator());
        activityTravelMatchTicketAndHosipitalityticketdetailBinding.otherMatchRcv.setAdapter(otherMatchShowAdapter);

//        fill related category list
        relatedticketCategorylist = new ArrayList<>();
        relatedcategoryfinalList = new ArrayList<>();

        relatedticketCategorylist.add(new TravelModel("http://devenv.bharatarmy.com/Docs/Mobile/25cf4087-b.jpg", "Ticket",
                "Category A", "Lorem Ipsum is simply dummy text. Lorem Ipsum is simply dummy text of the printing.", "Extra 10% off* with Hotel.",
                "₹ 500", "1", "ticket", "0"));

        relatedticketCategorylist.add(new TravelModel("http://devenv.bharatarmy.com//Docs/e35eee60-7.jpg", "",
                "Category B", "Lorem Ipsum is simply dummy text of the printing.Lorem Ipsum is simply dummy text.", "",
                "₹ 450", "1", "ticket", "0"));

        relatedticketCategorylist.add(new TravelModel("http://devenv.bharatarmy.com//Docs/5c6783ff-d.jpg", "",
                "Category C", "Lorem Ipsum is simply dummy text of the printing.Lorem Ipsum is simply dummy text.", "Extra 20% off* with Hotel.",
                "₹ 350", "3", "ticket", "0"));

        relatedticketCategorylist.add(new TravelModel("http://devenv.bharatarmy.com/Docs/Mobile/25cf4087-b.jpg", "Ticket",
                "Category D", "Lorem Ipsum is simply dummy text.Lorem Ipsum is simply dummy text of the printing.", "Extra 10% off* with Hotel.",
                "₹ 500", "1", "ticket", "0"));

        relatedticketCategorylist.add(new TravelModel("http://devenv.bharatarmy.com//Docs/e35eee60-7.jpg", "",
                "Category E", "Lorem Ipsum is simply dummy text of the printing.", "",
                "₹ 450", "1", "ticket", "0"));


        for (int i = 0; i < relatedticketCategorylist.size(); i++) {
            if (!relatedticketCategorylist.get(i).getTicket_hospitality_namecategory().trim().equalsIgnoreCase(activityTravelMatchTicketAndHosipitalityticketdetailBinding.matchTicketTypeTagTxt.getText().toString().trim())) {
                relatedcategoryfinalList.add(relatedticketCategorylist.get(i));
            }
        }

        for (int i = 0; i < relatedcategoryfinalList.size(); i++) {
            if (relatedcategoryfinalList.get(i).getTicket_hospitality_selected().equalsIgnoreCase("1")) {
                selectedposition = i;
            }
        }

        relatedTicketCategoryAdapter = new RelatedTicketCategoryAdapter(mContext, relatedcategoryfinalList, selectedposition);
        categorylinearLayoutManager = new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false);
        activityTravelMatchTicketAndHosipitalityticketdetailBinding.otherTicketCategoryRcv.setLayoutManager(categorylinearLayoutManager);
        activityTravelMatchTicketAndHosipitalityticketdetailBinding.otherTicketCategoryRcv.setItemAnimator(new DefaultItemAnimator());
        activityTravelMatchTicketAndHosipitalityticketdetailBinding.otherTicketCategoryRcv.setAdapter(relatedTicketCategoryAdapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                finish();
                break;
            case R.id.bottom_cart_view:
                Utils.handleClickEvent(mContext, activityTravelMatchTicketAndHosipitalityticketdetailBinding.bottomCartView);
                if (Utils.isMember(mContext, "Ticket Detail")) {
                    Intent cartIntent = new Intent(mContext, AddToCartActivity.class);
                    cartIntent.putExtra("bookingItemName", activityTravelMatchTicketAndHosipitalityticketdetailBinding.matchTicketTypeTagTxt.getText().toString());
                    cartIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(cartIntent);
                }
                break;
            case R.id.ticket_minus_layout:
                if (ticketcount != 1) {
                    ticketcount = ticketcount - 1;
//                    if (ticketcount <= 9) {
//                        activityTravelMatchTicketAndHosipitalityticketdetailBinding.countOfItem.setText("0" + String.valueOf(ticketcount));
//
//                    } else {
                    activityTravelMatchTicketAndHosipitalityticketdetailBinding.countOfItem.setText(String.valueOf(ticketcount));
//                    }

                    totalamount = 500 * ticketcount;
                    activityTravelMatchTicketAndHosipitalityticketdetailBinding.priceTxt.setText(String.valueOf(roundTwoDecimals(totalamount)));
                } else {
                    activityTravelMatchTicketAndHosipitalityticketdetailBinding.ticketMinusImg.setClickable(false);
                }

                break;
            case R.id.ticket_plus_layout:
                activityTravelMatchTicketAndHosipitalityticketdetailBinding.ticketMinusImg.setClickable(true);
                ticketcount = ticketcount + 1;
//                if (ticketcount <= 9) {
//                    activityTravelMatchTicketAndHosipitalityticketdetailBinding.countOfItem.setText("0" + String.valueOf(ticketcount));
//                } else {
                totalamount = 500 * ticketcount;
                activityTravelMatchTicketAndHosipitalityticketdetailBinding.priceTxt.setText(String.valueOf(roundTwoDecimals(totalamount)));
                activityTravelMatchTicketAndHosipitalityticketdetailBinding.countOfItem.setText(String.valueOf(ticketcount));
//                }
                break;
        }
    }

    // use for ticket price round figure
    double roundTwoDecimals(double d) {
        DecimalFormat twoDForm = new DecimalFormat("#.##");
        return Double.valueOf(twoDForm.format(d));
    }
}
