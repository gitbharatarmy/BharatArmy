package com.bharatarmy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import com.bharatarmy.Adapter.TravelMainPageAdapter;
import com.bharatarmy.Adapter.TravelMatchDetailAdapter;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityTravelMatchDetailBinding;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;

public class TravelMatchDetailActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityTravelMatchDetailBinding travelMatchDetailBinding;
    Context mContext;
    String bgImageStr,tourMatchNameStr;
    TravelMatchDetailAdapter travelMatchDetailAdapter;
    ArrayList<TravelModel> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        travelMatchDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_travel_match_detail);

        mContext = TravelMatchDetailActivity.this;


        init();
        setListiner();
        setListValue();
    }

    public void init() {
        bgImageStr = getIntent().getStringExtra("bgImage");
        tourMatchNameStr=getIntent().getStringExtra("tourName");
        Utils.setImageInImageView("https://cdn.drivebird.com/user-content/140000000001/2017/09/627c6d094ccd59cdcf10035482d7497f.jpg",
                travelMatchDetailBinding.mainMatchBgImage, mContext);

        if (!tourMatchNameStr.equalsIgnoreCase("")){
            travelMatchDetailBinding.tourNameTxt.setText(tourMatchNameStr);
        }

        Utils.setImageInImageView("https://www.bharatarmy.com/Content/images/flags-mini/in.png",travelMatchDetailBinding.firstCountryflagImage,mContext);
        Utils.setImageInImageView("https://www.bharatarmy.com/Content/images/flags-mini/sou.png",travelMatchDetailBinding.secondCountryflagImage,mContext);

    }

    public void setListiner() {
        travelMatchDetailBinding.scheduleMainLinear.performClick();
        travelMatchDetailBinding.scheduleMainLinear.setOnClickListener(this);
        travelMatchDetailBinding.ticketsMainLinear.setOnClickListener(this);
        travelMatchDetailBinding.packageMainLinear.setOnClickListener(this);
        travelMatchDetailBinding.hotelMainLinear.setOnClickListener(this);
        travelMatchDetailBinding.siteseenMainLinear.setOnClickListener(this);
        travelMatchDetailBinding.backImg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.schedule_main_linear:
                travelMatchDetailBinding.scheduleSelectedLinear.setBackground(getResources().getDrawable(R.drawable.bg_gradiant_line));
                travelMatchDetailBinding.ticktesSelectedLinear.setBackground(getResources().getDrawable(R.drawable.line_shape));
                travelMatchDetailBinding.packageSelectedLinear.setBackground(getResources().getDrawable(R.drawable.line_shape));
                travelMatchDetailBinding.hotelSelectedLinear.setBackground(getResources().getDrawable(R.drawable.line_shape));
                travelMatchDetailBinding.siteseenSelectedLinear.setBackground(getResources().getDrawable(R.drawable.line_shape));
                break;
            case R.id.tickets_main_linear:
                travelMatchDetailBinding.ticktesSelectedLinear.setBackground(getResources().getDrawable(R.drawable.bg_gradiant_line));
                travelMatchDetailBinding.scheduleSelectedLinear.setBackground(getResources().getDrawable(R.drawable.line_shape));
                travelMatchDetailBinding.packageSelectedLinear.setBackground(getResources().getDrawable(R.drawable.line_shape));
                travelMatchDetailBinding.hotelSelectedLinear.setBackground(getResources().getDrawable(R.drawable.line_shape));
                travelMatchDetailBinding.siteseenSelectedLinear.setBackground(getResources().getDrawable(R.drawable.line_shape));
                break;
            case R.id.package_main_linear:
                travelMatchDetailBinding.packageSelectedLinear.setBackground(getResources().getDrawable(R.drawable.bg_gradiant_line));
                travelMatchDetailBinding.ticktesSelectedLinear.setBackground(getResources().getDrawable(R.drawable.line_shape));
                travelMatchDetailBinding.scheduleSelectedLinear.setBackground(getResources().getDrawable(R.drawable.line_shape));
                travelMatchDetailBinding.hotelSelectedLinear.setBackground(getResources().getDrawable(R.drawable.line_shape));
                travelMatchDetailBinding.siteseenSelectedLinear.setBackground(getResources().getDrawable(R.drawable.line_shape));
                break;
            case R.id.hotel_main_linear:
                travelMatchDetailBinding.hotelSelectedLinear.setBackground(getResources().getDrawable(R.drawable.bg_gradiant_line));
                travelMatchDetailBinding.scheduleSelectedLinear.setBackground(getResources().getDrawable(R.drawable.line_shape));
                travelMatchDetailBinding.ticktesSelectedLinear.setBackground(getResources().getDrawable(R.drawable.line_shape));
                travelMatchDetailBinding.packageSelectedLinear.setBackground(getResources().getDrawable(R.drawable.line_shape));
                travelMatchDetailBinding.siteseenSelectedLinear.setBackground(getResources().getDrawable(R.drawable.line_shape));

                break;
            case R.id.siteseen_main_linear:
                travelMatchDetailBinding.siteseenSelectedLinear.setBackground(getResources().getDrawable(R.drawable.bg_gradiant_line));
                travelMatchDetailBinding.hotelSelectedLinear.setBackground(getResources().getDrawable(R.drawable.line_shape));
                travelMatchDetailBinding.scheduleSelectedLinear.setBackground(getResources().getDrawable(R.drawable.line_shape));
                travelMatchDetailBinding.ticktesSelectedLinear.setBackground(getResources().getDrawable(R.drawable.line_shape));
                travelMatchDetailBinding.packageSelectedLinear.setBackground(getResources().getDrawable(R.drawable.line_shape));
                break;



            case R.id.back_img:
                TravelMatchDetailActivity.this.finish();
                break;

        }

    }

    public void setListValue(){
        arrayList = new ArrayList<TravelModel>();
        arrayList.add(new TravelModel(0));
        arrayList.add(new TravelModel(1));
        arrayList.add(new TravelModel(2));
        arrayList.add(new TravelModel(3));
        arrayList.add(new TravelModel(4));
        arrayList.add(new TravelModel(5));


        travelMatchDetailAdapter = new TravelMatchDetailAdapter(mContext,arrayList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false);
        travelMatchDetailBinding.matchTraveldetailRcyList.setLayoutManager(mLayoutManager);
        travelMatchDetailBinding.matchTraveldetailRcyList.setItemAnimator(new DefaultItemAnimator());
        travelMatchDetailBinding.matchTraveldetailRcyList.setAdapter(travelMatchDetailAdapter);
    }
}
