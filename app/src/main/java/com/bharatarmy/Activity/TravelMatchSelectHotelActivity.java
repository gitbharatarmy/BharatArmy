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

import com.bharatarmy.Adapter.TravelMatchSelectHotelAdapter;
import com.bharatarmy.Models.MyScreenChnagesModel;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.databinding.ActivityTravelMatchSelectHotelBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

public class TravelMatchSelectHotelActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityTravelMatchSelectHotelBinding activityTravelMatchSelectHotelBinding;
    Context mContext;
    TravelMatchSelectHotelAdapter travelMatchSelectHotelAdapter;
    ArrayList<TravelModel> hotelList;
    String selectedroomImageStr="",selectedroomNameStr="",selectedposition="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTravelMatchSelectHotelBinding = DataBindingUtil.setContentView(this, R.layout.activity_travel_match_select_hotel);
        mContext = TravelMatchSelectHotelActivity.this;
        EventBus.getDefault().register(this);
        init();
        setListiner();
    }

    public void init() {
        activityTravelMatchSelectHotelBinding.toolbarTitleTxt.setText("Select Your Hotel");
        activityTravelMatchSelectHotelBinding.shimmerViewContainer.setVisibility(View.GONE);
        hotelList = new ArrayList<>();
        hotelList.add(new TravelModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSCtpFvQulc666pbmJhIdodJxCSFhPlACieZOemcK3qb5w95acjRQ&s", "PAN PACIFIC PERTH", "Bharat Army Experience Package with Accommodation Stay.", 4, "₹ 10000"));

        hotelList.add(new TravelModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQDXmTnFIhLvlcFmZzc4BXBQURhFFV5J8bKoCsIK3e6QM74BnEj&s", "FOUR POINTS BY SHERATON PERTH", "Bharat Army Experience Package with Accommodation Stay.",
                3, "₹ 9000"));






        travelMatchSelectHotelAdapter = new TravelMatchSelectHotelAdapter(mContext,hotelList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        activityTravelMatchSelectHotelBinding.hotelTypeRcv.setLayoutManager(mLayoutManager);
        activityTravelMatchSelectHotelBinding.hotelTypeRcv.setItemAnimator(new DefaultItemAnimator());
        activityTravelMatchSelectHotelBinding.hotelTypeRcv.setAdapter(travelMatchSelectHotelAdapter);
    }

    public void setListiner() {
        activityTravelMatchSelectHotelBinding.backImg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                finish();
                break;
        }
    }


    @Subscribe
    public void customEventReceived(MyScreenChnagesModel event) {
        Log.d("event :",event.getRoomName());
        if (!event.getRoomName().equalsIgnoreCase("")) {
            selectedroomNameStr=event.getRoomName();
            selectedroomImageStr=event.getRoomImage();
            selectedposition=event.getPosition();

            if (travelMatchSelectHotelAdapter!=null) {
                travelMatchSelectHotelAdapter.notifyItemChanged(Integer.parseInt(selectedposition), selectedposition+"|"+selectedroomNameStr+"|"+selectedroomImageStr);//
            }

        }

    }
}
