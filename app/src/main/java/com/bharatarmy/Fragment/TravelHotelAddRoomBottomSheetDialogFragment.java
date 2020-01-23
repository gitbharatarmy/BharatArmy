package com.bharatarmy.Fragment;

import android.app.Dialog;
import android.content.Context;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.bharatarmy.Adapter.TravelHotelAddRoomAdapter;
import com.bharatarmy.Interfaces.MorestoryClick;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.databinding.FragmentTravelHotelAddRoomBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;


public class TravelHotelAddRoomBottomSheetDialogFragment extends BottomSheetDialogFragment implements View.OnClickListener {
    Context mContext;
    ArrayList<TravelModel> allroomList;
    MorestoryClick morestoryClick;
    FragmentTravelHotelAddRoomBinding fragmentTravelHotelAddRoomBinding;
    TravelHotelAddRoomAdapter travelHotelAddRoomAdapter;
    int adultcount=0;
    int childcount=0;
    int selectedroomforchangesposition=-1;
    
    
    public TravelHotelAddRoomBottomSheetDialogFragment(ArrayList<TravelModel> allroomList, int selectedroomforchangesposition, MorestoryClick morestoryClick) {
        this.allroomList = allroomList;
        this.morestoryClick = morestoryClick;
        this.selectedroomforchangesposition=selectedroomforchangesposition;
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        fragmentTravelHotelAddRoomBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),
                R.layout.fragment_travel_hotel_add_room, null, false);
        dialog.setContentView(fragmentTravelHotelAddRoomBinding.getRoot());
        mContext = getActivity().getApplicationContext();

//        BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) dialog;
//        bottomSheetDialog.setContentView(R.layout.fragment_travel_hotel_add_room);

        init();
        setListiner();
    }

    public void init() {

        for(int i=0;i<allroomList.size();i++){
            if (allroomList.get(i).getOffers().equalsIgnoreCase("1")){
                selectedroomforchangesposition=i;
            }
        }
        travelHotelAddRoomAdapter = new TravelHotelAddRoomAdapter(mContext, allroomList,selectedroomforchangesposition);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        fragmentTravelHotelAddRoomBinding.roomSelectionTypeRcv.setLayoutManager(mLayoutManager);
        fragmentTravelHotelAddRoomBinding.roomSelectionTypeRcv.setItemAnimator(new DefaultItemAnimator());
        fragmentTravelHotelAddRoomBinding.roomSelectionTypeRcv.setAdapter(travelHotelAddRoomAdapter);
    }

    public void setListiner() {
        fragmentTravelHotelAddRoomBinding.backImg.setOnClickListener(this);
        fragmentTravelHotelAddRoomBinding.doneLinear.setOnClickListener(this);
        fragmentTravelHotelAddRoomBinding.adultremoveTxt.setOnClickListener(this);
        fragmentTravelHotelAddRoomBinding.adultaddTxt.setOnClickListener(this);
        fragmentTravelHotelAddRoomBinding.childremoveTxt.setOnClickListener(this);
        fragmentTravelHotelAddRoomBinding.childaddTxt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                dismiss();
                break;
            case R.id.done_Linear:
                dismiss();
                morestoryClick.getmorestoryClick();
                break;
            case R.id.adultremove_txt:
                Log.d("adultcount :",""+adultcount);
                if (adultcount!=0){
                    adultcount=adultcount-1;
                    if (adultcount<=9){
                        fragmentTravelHotelAddRoomBinding.adultcountTxt.setText("0"+String.valueOf(adultcount));
                    }else{
                        fragmentTravelHotelAddRoomBinding.adultcountTxt.setText(String.valueOf(adultcount));
                    }

                }else{
                    fragmentTravelHotelAddRoomBinding.adultremoveTxt.setClickable(false);
                }

                break;
            case R.id.adultadd_txt:
                fragmentTravelHotelAddRoomBinding.adultremoveTxt.setClickable(true);
                adultcount=adultcount+1;
                if (adultcount<=9){
                    fragmentTravelHotelAddRoomBinding.adultcountTxt.setText("0"+String.valueOf(adultcount));
                }else{
                    fragmentTravelHotelAddRoomBinding.adultcountTxt.setText(String.valueOf(adultcount));
                }

                break;
            case R.id.childremove_txt:
                if (childcount!=0){
                    childcount=childcount-1;
                    if (childcount<=9){
                        fragmentTravelHotelAddRoomBinding.childcountTxt.setText("0"+String.valueOf(childcount));
                    }else{
                        fragmentTravelHotelAddRoomBinding.childcountTxt.setText(String.valueOf(childcount));
                    }

                }else{
                    fragmentTravelHotelAddRoomBinding.childremoveTxt.setClickable(false);
                }
                break;
            case R.id.childadd_txt:
                fragmentTravelHotelAddRoomBinding.childremoveTxt.setClickable(true);
                childcount=childcount+1;
                if (childcount<=9){
                    fragmentTravelHotelAddRoomBinding.childcountTxt.setText("0"+String.valueOf(childcount));
                }else{
                    fragmentTravelHotelAddRoomBinding.childcountTxt.setText(String.valueOf(childcount));
                }
                break;
        }
    }
}

