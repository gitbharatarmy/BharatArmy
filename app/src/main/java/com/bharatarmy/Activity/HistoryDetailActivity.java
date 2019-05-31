package com.bharatarmy.Activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Adapter.HistoryMoreDetailListAdapter;
import com.bharatarmy.Models.HistoryModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.OrderStatus;
import com.bharatarmy.databinding.ActivityHistoryDetailBinding;

import java.util.ArrayList;



public class HistoryDetailActivity extends BaseActivity implements View.OnClickListener {

    ActivityHistoryDetailBinding activityHistoryDetailBinding;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityHistoryDetailBinding = DataBindingUtil.setContentView(this,R.layout.activity_history_detail);

        mContext = HistoryDetailActivity.this;
        setListiner();
        setDataValue();

        setTitleText("Bharat Army Booking History");
        setBackButton(HistoryDetailActivity.this);
    }

    public void setListiner(){
//        activityHistoryDetailBinding.backImg.setOnClickListener(this);

    }
//https://github.com/Adms1/Bhadaj_Teacher/blob/master/app/src/main/java/anandniketan/com/anbcteacher/Adapter/ProfileAdapter.java
//    https://github.com/Adms1/Bhadaj_Teacher/blob/master/app/src/main/java/anandniketan/com/anbcteacher/Fragment/SettingFragment.java
    public void setDataValue(){
        ArrayList<HistoryModel> list= new ArrayList();
        list.add(new HistoryModel(HistoryModel.MAIN_BANNER_IMAGE_TYPE,"Hello. This is the Text-only View Type. Nice to meet you",0,OrderStatus.COMPLETED));
        list.add(new HistoryModel(HistoryModel.TICKET_BOOKING_TYPE,"Hi. I display a cool image too besides the omnipresent TextView.",0, OrderStatus.INACTIVE));
        list.add(new HistoryModel(HistoryModel.AIRPORT_TRANSFER_TYPE,"Hey. Pressing the FAB button will playback an audio file on loop.",0, OrderStatus.INACTIVE));
        list.add(new HistoryModel(HistoryModel.HOTEL_BOOKING_TYPE,"Hey. Pressing the FAB button will playback an audio file on loop.",0, OrderStatus.ACTIVE));
        list.add(new HistoryModel(HistoryModel.TRANSFER_HOTEL_TO_STADIUM_TYPE,"Hello. This is the Text-only View Type. Nice to meet you",0, OrderStatus.ACTIVE));
        list.add(new HistoryModel(HistoryModel.TRANSFER_STADIUM_TO_HOTEL,"Hi. I display a cool image too besides the omnipresent TextView.",0, OrderStatus.ACTIVE));
        list.add(new HistoryModel(HistoryModel.HOTEL_CHECKOUT_TYPE,"Hey. Pressing the FAB button will playback an audio file on loop.",0, OrderStatus.INACTIVE));
        list.add(new HistoryModel(HistoryModel.MATCH_BOOKING_TYPE,"Hey. Pressing the FAB button will playback an audio file on loop.",0, OrderStatus.INACTIVE));
//
        HistoryMoreDetailListAdapter adapter = new HistoryMoreDetailListAdapter(list,mContext);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        activityHistoryDetailBinding.bookingHistoryDetailRcyList.setLayoutManager(linearLayoutManager);
        activityHistoryDetailBinding.bookingHistoryDetailRcyList.setItemAnimator(new DefaultItemAnimator());
        activityHistoryDetailBinding.bookingHistoryDetailRcyList.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
//            case R.id.back_img:
//                HistoryDetailActivity.this.finish();
//                break;
        }
    }
}
