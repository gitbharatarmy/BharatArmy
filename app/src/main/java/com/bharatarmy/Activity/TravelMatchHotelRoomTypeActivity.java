package com.bharatarmy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bharatarmy.Adapter.TravelMatchHotelRoomTypeAdapter;
import com.bharatarmy.Fragment.TravelHotelAddRoomBottomSheetDialogFragment;
import com.bharatarmy.Interfaces.MorestoryClick;
import com.bharatarmy.Models.GalleryImageModel;
import com.bharatarmy.Models.MyScreenChnagesModel;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityTravelMatchHotelRoomTypeBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

//change the design and development 09/01/2020 backup in 09/01/2020
public class TravelMatchHotelRoomTypeActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityTravelMatchHotelRoomTypeBinding activityTravelMatchHotelRoomTypeBinding;
    Context mContext;
    TravelMatchHotelRoomTypeAdapter travelMatchHotelRoomTypeAdapter;
    ArrayList<TravelModel> roomList;
    ArrayList<TravelModel> roomFilterList;
    ArrayList<TravelModel> allroomList;
    int selectedposition = -1;
    int selectedroomforchangesposition = -1;
String titleNameStr;

    //    add room in bottom dialog
    BottomSheetDialogFragment bottomSheetDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTravelMatchHotelRoomTypeBinding = DataBindingUtil.setContentView(this, R.layout.activity_travel_match_hotel_room_type);
        mContext = TravelMatchHotelRoomTypeActivity.this;

        init();
        setListiner();
    }

    public void init() {
        roomFilterList= new ArrayList<>();
        activityTravelMatchHotelRoomTypeBinding.oldpriceTxt.setPaintFlags(activityTravelMatchHotelRoomTypeBinding.oldpriceTxt.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        activityTravelMatchHotelRoomTypeBinding.shimmerViewContainer.setVisibility(View.GONE);
        roomList = new ArrayList<>();

        if (getIntent().getStringExtra("roomName") != null) {
            if (getIntent().getStringExtra("roomName").equalsIgnoreCase("Delux Room")) {
                roomList.add(new TravelModel(getIntent().getStringExtra("roomName"), "x 2", "", "₹ 700",
                        AppConfiguration.IMAGE_URL + "d_hotelroom2.jpg", "0"));
            } else {
                roomList.add(new TravelModel(getIntent().getStringExtra("roomName"), "x 2", "", "₹ 700",
                        AppConfiguration.IMAGE_URL + "d_hotelroom3.jpg", "0"));
            }

        }

        allroomList = new ArrayList<>();

        allroomList.add(new TravelModel("Delux Room", "x 2", "", "₹ 700",
                AppConfiguration.IMAGE_URL + "d_hotelroom2.jpg", "0"));
        allroomList.add(new TravelModel("Premium Room", "x 3", "x 1", "₹ 500",
                AppConfiguration.IMAGE_URL + "d_hotelroom3.jpg", "0"));


        if (getIntent().getStringExtra("roomName") != null) {
            for (int i = 0; i < roomList.size(); i++) {
                if (roomList.get(i).getMatchroom_name().equalsIgnoreCase(getIntent().getStringExtra("roomName"))) {
                    roomList.get(i).setOffers("1");
                    selectedroomforchangesposition = i;
                }
            }
        }
//
//        if (getIntent().getStringExtra("clickposition") != null) {
//            Log.d("roomtypeposition :", getIntent().getStringExtra("clickposition"));
//            mainposition = Integer.parseInt(getIntent().getStringExtra("clickposition"));
//        }

        travelMatchHotelRoomTypeAdapter = new TravelMatchHotelRoomTypeAdapter(mContext, roomList, new MorestoryClick() {
            @Override
            public void getmorestoryClick() {

                for (int i = 0; i < roomList.size(); i++) {
                    if (roomList.get(i).getOffers().equalsIgnoreCase("1")) {
                        selectedroomforchangesposition = i;
                    }
                }
////                Log.d("hotelclickpositon : ",getIntent().getStringExtra("clickposition"));
//                String dataValue = travelMatchHotelRoomTypeAdapter.getDatas().toString();
//                dataValue = dataValue.substring(1, dataValue.length() - 1);
//                String[] splitValue = dataValue.split("\\|");
////                Log.d("position 111:",getIntent().getStringExtra("clickposition"));
//                EventBus.getDefault().post(new MyScreenChnagesModel(splitValue[1], splitValue[0], getIntent().getStringExtra("clickposition")));
//                finish();

                Utils.handleClickEvent(mContext, activityTravelMatchHotelRoomTypeBinding.addRoomLinear);
                bottomSheetDialogFragment = new TravelHotelAddRoomBottomSheetDialogFragment(allroomList, selectedroomforchangesposition, new MorestoryClick() {
                    @Override
                    public void getmorestoryClick() {
                        for (int i = 0; i < allroomList.size(); i++) {
                            if (allroomList.get(i).getOffers().equalsIgnoreCase("1")) {
                                roomList.add(allroomList.get(i));


                            }
                        }
                        Set<TravelModel> unique = new LinkedHashSet<TravelModel>(roomList);
                        roomList = new ArrayList<TravelModel>(unique);
                        travelMatchHotelRoomTypeAdapter.notifyDataSetChanged();
                    }
                });
                //show it
                bottomSheetDialogFragment.setCancelable(false);
                bottomSheetDialogFragment.show(getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        activityTravelMatchHotelRoomTypeBinding.roomTypeRcv.setLayoutManager(mLayoutManager);
        activityTravelMatchHotelRoomTypeBinding.roomTypeRcv.setItemAnimator(new DefaultItemAnimator());
        activityTravelMatchHotelRoomTypeBinding.roomTypeRcv.setAdapter(travelMatchHotelRoomTypeAdapter);
    }

    public void setListiner() {
        activityTravelMatchHotelRoomTypeBinding.backImg.setOnClickListener(this);
        activityTravelMatchHotelRoomTypeBinding.addRoomLinear.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                EventBus.getDefault().post(new MyScreenChnagesModel(getIntent().getStringExtra("roomName"), getIntent().getStringExtra("roomName"), getIntent().getStringExtra("clickposition")));
                finish();
                break;
            case R.id.add_room_linear:
                Utils.handleClickEvent(mContext, activityTravelMatchHotelRoomTypeBinding.addRoomLinear);
                bottomSheetDialogFragment = new TravelHotelAddRoomBottomSheetDialogFragment(allroomList, selectedroomforchangesposition, new MorestoryClick() {
                    @Override
                    public void getmorestoryClick() {
                        for (int i = 0; i < allroomList.size(); i++) {
                            if (allroomList.get(i).getOffers().equalsIgnoreCase("1")) {
                                roomList.add(allroomList.get(i));


                            }
                        }
                        Set<TravelModel> unique = new LinkedHashSet<TravelModel>(roomList);
                        roomList = new ArrayList<TravelModel>(unique);
                        Log.d("roomfilter :",""+roomFilterList.size());
                        travelMatchHotelRoomTypeAdapter.notifyDataSetChanged();
                    }
                });
                //show it
                bottomSheetDialogFragment.setCancelable(false);
                bottomSheetDialogFragment.show(getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
                break;
        }
    }
}
