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
import com.bharatarmy.databinding.TravelMatchHotelRoomTypeListItemBinding;

import java.util.ArrayList;

public class TravelMatchHotelRoomTypeAdapter extends RecyclerView.Adapter<TravelMatchHotelRoomTypeAdapter.MyViewHolder> {
    Context mContext;
    ArrayList<TravelModel> roomList;
    MorestoryClick morestoryClick;
    int selectedposition,mainposition;
    private ArrayList<String> dataCheck = new ArrayList<String>();

    public TravelMatchHotelRoomTypeAdapter(Context mContext, ArrayList<TravelModel> roomList, int selectedposition, int mainposition, MorestoryClick morestoryClick) {
        this.mContext = mContext;
        this.roomList = roomList;
        this.selectedposition = selectedposition;
        this.morestoryClick = morestoryClick;
        this.mainposition=mainposition;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TravelMatchHotelRoomTypeListItemBinding travelMatchHotelRoomTypeListItemBinding;

        public MyViewHolder(TravelMatchHotelRoomTypeListItemBinding travelMatchHotelRoomTypeListItemBinding) {
            super(travelMatchHotelRoomTypeListItemBinding.getRoot());

            this.travelMatchHotelRoomTypeListItemBinding = travelMatchHotelRoomTypeListItemBinding;


        }
    }


    @Override
    public TravelMatchHotelRoomTypeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        TravelMatchHotelRoomTypeListItemBinding travelMatchHotelRoomTypeListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.travel_match_hotel_room_type_list_item, parent, false);
        return new TravelMatchHotelRoomTypeAdapter.MyViewHolder(travelMatchHotelRoomTypeListItemBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override

    public void onBindViewHolder(TravelMatchHotelRoomTypeAdapter.MyViewHolder holder, int position) {
        Utils.setImageInImageView(roomList.get(position).getRoom_image(), holder.travelMatchHotelRoomTypeListItemBinding.selectedRoomimg, mContext);
        holder.travelMatchHotelRoomTypeListItemBinding.roomTitleTxt.setText(roomList.get(position).getMatchroom_name());

        holder.travelMatchHotelRoomTypeListItemBinding.roomPriceTxt.setText(roomList.get(position).getRoom_price());


        if (selectedposition == position) {
            holder.travelMatchHotelRoomTypeListItemBinding.roomscartAddimage.setImageResource(R.drawable.fill_selected_checkbox);
            holder.travelMatchHotelRoomTypeListItemBinding.roomLinear.setBackground(mContext.getResources().getDrawable(R.drawable.travel_match_selectedchild_curveshape));
        }else{
            holder.travelMatchHotelRoomTypeListItemBinding.roomscartAddimage.setImageResource(R.drawable.ic_blank_check);
            holder.travelMatchHotelRoomTypeListItemBinding.roomLinear.setBackground(mContext.getResources().getDrawable(R.drawable.travel_match_child_curveshape));
        }

        holder.travelMatchHotelRoomTypeListItemBinding.roomscartAddimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roomList.get(position).setOffers("1");
                selectedposition = position;
                notifyDataSetChanged();

                dataCheck = new ArrayList<>();
                dataCheck.add(roomList.get(position).getRoom_image() + "|" + roomList.get(position).getMatchroom_name());
                morestoryClick.getmorestoryClick();
            }
        });

//        holder.travelMatchHotelRoomTypeListItemBinding.roomTitleTxt.setOnClickListener(new View.OnClickListener() {
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
//        holder.travelMatchHotelRoomTypeListItemBinding.descRoomTxt.setOnClickListener(new View.OnClickListener() {
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

        holder.travelMatchHotelRoomTypeListItemBinding.viewMoreLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent roomDetailIntent =new Intent(mContext, TravelMatchRoomDetailActivity.class);
                roomDetailIntent.putExtra("roomName", roomList.get(position).getMatchroom_name());
                roomDetailIntent.putExtra("rooImage",roomList.get(position).getRoom_image());
                roomDetailIntent.putExtra("roomPrice",roomList.get(position).getRoom_price());
                roomDetailIntent.putExtra("clickposition",String.valueOf(mainposition));
                roomDetailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(roomDetailIntent);
                ((Activity)mContext).finish();
            }
        });
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
        return roomList.size();
    }

    public ArrayList<String> getDatas() {
        return dataCheck;
    }
}




