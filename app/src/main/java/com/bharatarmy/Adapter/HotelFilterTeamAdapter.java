package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Models.InquiryStatusModel;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.HotelFilterTeamListItemBinding;
import com.bharatarmy.databinding.TicketFilterTeamItemBinding;

import java.util.ArrayList;
import java.util.List;

public class HotelFilterTeamAdapter extends RecyclerView.Adapter<HotelFilterTeamAdapter.MyViewHolder> {
    Context mContext;
//    List<InquiryStatusModel> matchTeamList;
    ArrayList<TravelModel> matchTeamList;
    private ArrayList<String> dataCheck = new ArrayList<String>();


    public HotelFilterTeamAdapter(Context mContext, ArrayList<TravelModel> matchTeamList) {
        this.mContext=mContext;
        this.matchTeamList=matchTeamList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        HotelFilterTeamListItemBinding hotelFilterTeamListItemBinding;

        public MyViewHolder(HotelFilterTeamListItemBinding hotelFilterTeamListItemBinding) {
            super(hotelFilterTeamListItemBinding.getRoot());

            this.hotelFilterTeamListItemBinding=hotelFilterTeamListItemBinding;
        }
    }


    @Override
    public HotelFilterTeamAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        HotelFilterTeamListItemBinding hotelFilterTeamListItemBinding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.hotel_filter_team_list_item,parent,false);
        return new HotelFilterTeamAdapter.MyViewHolder(hotelFilterTeamListItemBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(HotelFilterTeamAdapter.MyViewHolder holder, int position) {

//        final InquiryStatusModel teamdetail = matchTeamList.get(position);
 final  TravelModel teamdetail = matchTeamList.get(position);

        Utils.setImageInImageView(teamdetail.getPopularcity_image(),holder.hotelFilterTeamListItemBinding.teamFlag,mContext);
        holder.hotelFilterTeamListItemBinding.matchTeamTxt.setText(teamdetail.getPopularcity_name());


        holder.hotelFilterTeamListItemBinding.teamselectedLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.hotelFilterTeamListItemBinding.selectedChk.isChecked()){
                    holder.hotelFilterTeamListItemBinding.selectedChk.setChecked(false);
                    teamdetail.setPopularcity_image_count("0");
                }else{
                    holder.hotelFilterTeamListItemBinding.selectedChk.setChecked(true);
                    teamdetail.setPopularcity_image_count("1");

                }

            }
        });

        holder.hotelFilterTeamListItemBinding.selectedChk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.hotelFilterTeamListItemBinding.selectedChk.isChecked()){
                    holder.hotelFilterTeamListItemBinding.selectedChk.setChecked(true);
                    teamdetail.setPopularcity_image_count("1");
                }else{
                    holder.hotelFilterTeamListItemBinding.selectedChk.setChecked(false);
                    teamdetail.setPopularcity_image_count("0");

                }

            }
        });

        if (teamdetail.getPopularcity_image_count().equalsIgnoreCase("1")){
            holder.hotelFilterTeamListItemBinding.selectedChk.setChecked(true);
        }else{
            holder.hotelFilterTeamListItemBinding.selectedChk.setChecked(false);
        }

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
        return matchTeamList.size();
    }


    public ArrayList<String> getDatas() {
        return dataCheck;
    }
}




