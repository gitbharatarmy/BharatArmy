package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Activity.TravelMatchRoomDetailActivity;
import com.bharatarmy.Interfaces.MorestoryClick;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.TravelHotelAddRoomListItemBinding;

import java.util.ArrayList;

public class TravelHotelAddRoomAdapter extends RecyclerView.Adapter<TravelHotelAddRoomAdapter.MyViewHolder> {
    Context mContext;
    ArrayList<TravelModel> allroomList;
    MorestoryClick morestoryClick;
    int selectedroomforchangesposition=-1,mainposition;
    private ArrayList<String> dataCheck = new ArrayList<String>();
    int last;

    
    public TravelHotelAddRoomAdapter(Context mContext, ArrayList<TravelModel> allroomList, int selectedroomforchangesposition) {
    this.mContext=mContext;
    this.allroomList=allroomList;
    this.selectedroomforchangesposition=selectedroomforchangesposition;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TravelHotelAddRoomListItemBinding travelHotelAddRoomListItemBinding;

        public MyViewHolder(TravelHotelAddRoomListItemBinding travelHotelAddRoomListItemBinding) {
            super(travelHotelAddRoomListItemBinding.getRoot());

            this.travelHotelAddRoomListItemBinding = travelHotelAddRoomListItemBinding;


        }
    }


    @Override
    public TravelHotelAddRoomAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        TravelHotelAddRoomListItemBinding travelHotelAddRoomListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.travel_hotel_add_room_list_item, parent, false);
        return new TravelHotelAddRoomAdapter.MyViewHolder(travelHotelAddRoomListItemBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override

    public void onBindViewHolder(TravelHotelAddRoomAdapter.MyViewHolder holder, int position) {
        TravelModel roomDetail=allroomList.get(position);
        
        Utils.setImageInImageView(roomDetail.getRoom_image(), holder.travelHotelAddRoomListItemBinding.selectedRoomimg, mContext);
        holder.travelHotelAddRoomListItemBinding.roomTitleTxt.setText(roomDetail.getMatchroom_name());

        holder.travelHotelAddRoomListItemBinding.roomPriceTxt.setText(roomDetail.getRoom_price());


        if (selectedroomforchangesposition == position) {
            holder.travelHotelAddRoomListItemBinding.roomscartAddimage.setImageResource(R.drawable.fill_selected_checkbox);
            holder.travelHotelAddRoomListItemBinding.roomLinear.setBackground(mContext.getResources().getDrawable(R.drawable.travel_match_selectedchild_curveshape));
        }else{
            holder.travelHotelAddRoomListItemBinding.roomscartAddimage.setImageResource(R.drawable.ic_blank_check);
            holder.travelHotelAddRoomListItemBinding.roomLinear.setBackground(mContext.getResources().getDrawable(R.drawable.travel_match_child_curveshape));
        }
        holder.travelHotelAddRoomListItemBinding.viewMoreLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent roomDetailIntent =new Intent(mContext, TravelMatchRoomDetailActivity.class);
                roomDetailIntent.putExtra("roomName", roomDetail.getMatchroom_name());
                roomDetailIntent.putExtra("rooImage",roomDetail.getRoom_image());
                roomDetailIntent.putExtra("roomPrice",roomDetail.getRoom_price());
                roomDetailIntent.putExtra("clickposition",String.valueOf(mainposition));
                roomDetailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(roomDetailIntent);
            }
        });
        holder.travelHotelAddRoomListItemBinding.roomLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roomDetail.setOffers("1");
                selectedroomforchangesposition = position;
                notifyDataSetChanged();
//
//                dataCheck = new ArrayList<>();
//                dataCheck.add(roomDetail.getRoom_image() + "|" + roomDetail.getMatchroom_name());
//                morestoryClick.getmorestoryClick();
            }
        });

//        holder.travelHotelAddRoomListItemBinding.roomTitleTxt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent roomDetailIntent =new Intent(mContext, TravelMatchRoomDetailActivity.class);
//                roomDetailIntent.putExtra("roomName", roomList.get(position).getMatchroom_name());
//                roomDetailIntent.putExtra("rooImage",roomList.get(position).getRoom_image());
//                roomDetailIntent.putExtra("roomPrice",roomList.get(position).getRoom_price());
//                roomDetailIntent.putExtra("clickposition",String.valueOf(mainposition));
//                roomDetailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                mContext.startActivity(roomDetailIntent);
//                ((Activity)mContext).finish();
//            }
//        });
//
//        holder.travelHotelAddRoomListItemBinding.descRoomTxt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent roomDetailIntent =new Intent(mContext, TravelMatchRoomDetailActivity.class);
//                roomDetailIntent.putExtra("roomName", roomList.get(position).getMatchroom_name());
//                roomDetailIntent.putExtra("rooImage",roomList.get(position).getRoom_image());
//                roomDetailIntent.putExtra("roomPrice",roomList.get(position).getRoom_price());
//                roomDetailIntent.putExtra("clickposition",String.valueOf(mainposition));
//                roomDetailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                mContext.startActivity(roomDetailIntent);
//                ((Activity)mContext).finish();
//            }
//        });


    }

    @Override
    public long getItemId(int position) {
// return specific item's id here
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return allroomList.size();
    }

    public ArrayList<String> getDatas() {
        return dataCheck;
    }
}




