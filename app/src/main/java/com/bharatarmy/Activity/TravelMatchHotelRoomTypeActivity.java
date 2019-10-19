package com.bharatarmy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bharatarmy.Adapter.TravelMatchHotelRoomTypeAdapter;
import com.bharatarmy.Interfaces.MorestoryClick;
import com.bharatarmy.Models.MyScreenChnagesModel;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.databinding.ActivityTravelMatchHotelRoomTypeBinding;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

public class TravelMatchHotelRoomTypeActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityTravelMatchHotelRoomTypeBinding activityTravelMatchHotelRoomTypeBinding;
    Context mContext;
    TravelMatchHotelRoomTypeAdapter travelMatchHotelRoomTypeAdapter;
    ArrayList<TravelModel> roomList;
    int selectedposition = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTravelMatchHotelRoomTypeBinding = DataBindingUtil.setContentView(this, R.layout.activity_travel_match_hotel_room_type);
        mContext = TravelMatchHotelRoomTypeActivity.this;

        init();
        setListiner();
    }

    public void init() {
        activityTravelMatchHotelRoomTypeBinding.toolbarTitleTxt.setText("Select Your Room");
        activityTravelMatchHotelRoomTypeBinding.shimmerViewContainer.setVisibility(View.GONE);
        roomList = new ArrayList<>();
        roomList.add(new TravelModel("Delux Room","x 2","","₹ 700",
                AppConfiguration.IMAGE_URL+"d_hotelroom2.jpg","0"));
        roomList.add(new TravelModel("Premium Room","x 3","x 1","₹ 500",
                AppConfiguration.IMAGE_URL+"d_hotelroom3.jpg","0"));


        if (getIntent().getStringExtra("roomName")!=null){
            for (int i=0;i<roomList.size();i++){
                if (roomList.get(i).getMatchroom_name().equalsIgnoreCase(getIntent().getStringExtra("roomName"))){
                    roomList.get(i).setOffers("1");
                    selectedposition=i;
                }
            }
        }

        travelMatchHotelRoomTypeAdapter = new TravelMatchHotelRoomTypeAdapter(mContext,roomList,selectedposition, new MorestoryClick() {
            @Override
            public void getmorestoryClick() {
                String dataValue=travelMatchHotelRoomTypeAdapter.getDatas().toString();
                dataValue= dataValue.substring(1, dataValue.length()-1);
                String[]splitValue=dataValue.split("\\|");
//                Log.d("position 111:",getIntent().getStringExtra("clickposition"));
                EventBus.getDefault().post(new MyScreenChnagesModel(splitValue[1],splitValue[0],getIntent().getStringExtra("clickposition")));
                finish();
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        activityTravelMatchHotelRoomTypeBinding.roomTypeRcv.setLayoutManager(mLayoutManager);
        activityTravelMatchHotelRoomTypeBinding.roomTypeRcv.setItemAnimator(new DefaultItemAnimator());
        activityTravelMatchHotelRoomTypeBinding.roomTypeRcv.setAdapter(travelMatchHotelRoomTypeAdapter);
    }

    public void setListiner() {
        activityTravelMatchHotelRoomTypeBinding.backImg.setOnClickListener(this);
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
