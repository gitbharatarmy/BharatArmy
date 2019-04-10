package com.bharatarmy.Activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bharatarmy.Adapter.HistoryMoreDetailListAdapter;
import com.bharatarmy.Models.HistoryModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.OrderStatus;
import com.bharatarmy.databinding.ActivityHistoryDetailBinding;

import java.util.ArrayList;



public class HistoryDetailActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityHistoryDetailBinding activityHistoryDetailBinding;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityHistoryDetailBinding = DataBindingUtil.setContentView(this,R.layout.activity_history_detail);

        mContext = HistoryDetailActivity.this;
        setListiner();
        setDataValue();
    }

    public void setListiner(){
        activityHistoryDetailBinding.backImg.setOnClickListener(this);

    }
//https://github.com/Adms1/Bhadaj_Teacher/blob/master/app/src/main/java/anandniketan/com/anbcteacher/Adapter/ProfileAdapter.java
//    https://github.com/Adms1/Bhadaj_Teacher/blob/master/app/src/main/java/anandniketan/com/anbcteacher/Fragment/SettingFragment.java
    public void setDataValue(){
        ArrayList<HistoryModel> list= new ArrayList();
        list.add(new HistoryModel(HistoryModel.MAIN_BANNER_IMAGE_TYPE,"Hello. This is the Text-only View Type. Nice to meet you",0,OrderStatus.COMPLETED));
        list.add(new HistoryModel(HistoryModel.TICKET_BOOKING_TYPE,"Hi. I display a cool image too besides the omnipresent TextView.",0, OrderStatus.INACTIVE));
        list.add(new HistoryModel(HistoryModel.AIRPORT_TRANSFER_TYPE,"Hey. Pressing the FAB button will playback an audio file on loop.",0, OrderStatus.ACTIVE));
        list.add(new HistoryModel(HistoryModel.HOTEL_BOOKING_TYPE,"Hey. Pressing the FAB button will playback an audio file on loop.",0, OrderStatus.ACTIVE));
        list.add(new HistoryModel(HistoryModel.TRANSFER_HOTEL_TO_STADIUM_TYPE,"Hello. This is the Text-only View Type. Nice to meet you",0, OrderStatus.INACTIVE));
        list.add(new HistoryModel(HistoryModel.TRANSFER_STADIUM_TO_HOTEL,"Hi. I display a cool image too besides the omnipresent TextView.",0, OrderStatus.INACTIVE));
        list.add(new HistoryModel(HistoryModel.HOTEL_CHECKOUT_TYPE,"Hey. Pressing the FAB button will playback an audio file on loop.",0, OrderStatus.INACTIVE));
        list.add(new HistoryModel(HistoryModel.MATCH_BOOKING_TYPE,"Hey. Pressing the FAB button will playback an audio file on loop.",0, OrderStatus.INACTIVE));
//
        HistoryMoreDetailListAdapter adapter = new HistoryMoreDetailListAdapter(list,mContext);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, OrientationHelper.VERTICAL, false);

        activityHistoryDetailBinding.bookingHistoryDetailRcyList.setLayoutManager(linearLayoutManager);
        activityHistoryDetailBinding.bookingHistoryDetailRcyList.setItemAnimator(new DefaultItemAnimator());
        activityHistoryDetailBinding.bookingHistoryDetailRcyList.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_img:
                HistoryDetailActivity.this.finish();
                break;
        }
    }
}
