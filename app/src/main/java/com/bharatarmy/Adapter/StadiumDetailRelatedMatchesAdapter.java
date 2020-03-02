package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Activity.TravelMatchScheduleDetailActivity;
import com.bharatarmy.Models.HomeTemplateDetailModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.StadiumDetailRelatedMatchesListItemBinding;

import java.util.List;

public class StadiumDetailRelatedMatchesAdapter extends RecyclerView.Adapter<StadiumDetailRelatedMatchesAdapter.MyItemViewHolder> {

    List<HomeTemplateDetailModel> travelStadiumOtherMatchesList;

    Context mContext;

    public StadiumDetailRelatedMatchesAdapter(Context mContext, List<HomeTemplateDetailModel> travelStadiumOtherMatchesList) {
        this.mContext = mContext;
        this.travelStadiumOtherMatchesList = travelStadiumOtherMatchesList;
    }


    static class MyItemViewHolder extends RecyclerView.ViewHolder {
        StadiumDetailRelatedMatchesListItemBinding stadiumDetailRelatedMatchesListItemBinding;


        public MyItemViewHolder(StadiumDetailRelatedMatchesListItemBinding stadiumDetailRelatedMatchesListItemBinding) {
            super(stadiumDetailRelatedMatchesListItemBinding.getRoot());
            this.stadiumDetailRelatedMatchesListItemBinding = stadiumDetailRelatedMatchesListItemBinding;
        }
    }


    @NonNull
    @Override
    public StadiumDetailRelatedMatchesAdapter.MyItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        StadiumDetailRelatedMatchesListItemBinding stadiumDetailRelatedMatchesListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.stadium_detail_related_matches_list_item, parent, false);
        return new StadiumDetailRelatedMatchesAdapter.MyItemViewHolder(stadiumDetailRelatedMatchesListItemBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final StadiumDetailRelatedMatchesAdapter.MyItemViewHolder holder, int position) {
        HomeTemplateDetailModel detail = travelStadiumOtherMatchesList.get(position);

        holder.stadiumDetailRelatedMatchesListItemBinding.matchTypeTagTxt.setText(detail.getTicketMatchType());
        holder.stadiumDetailRelatedMatchesListItemBinding.firstCountryNameTxt.setText(detail.getTicketMatchFirstCountryName());
        Utils.setImageInImageView(detail.getTicketMatchFirstCountryFlag(), holder.stadiumDetailRelatedMatchesListItemBinding.firstCountryflagImage, mContext);
        holder.stadiumDetailRelatedMatchesListItemBinding.secondCountryNameTxt.setText(detail.getTicketMatchSecondCountryName());
        Utils.setImageInImageView(detail.getTicketMatchSecondCountryFlag(), holder.stadiumDetailRelatedMatchesListItemBinding.secondCountryflagImage, mContext);
        holder.stadiumDetailRelatedMatchesListItemBinding.matchGroundLocationTxt.setText(detail.getTicketMatchGroundName());
        holder.stadiumDetailRelatedMatchesListItemBinding.matchDateTimeTxt.setText(detail.getTicketMatchTimeDate());

        holder.stadiumDetailRelatedMatchesListItemBinding.matchesRelatedCardLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent scheduleDetailIntent = new Intent(mContext, TravelMatchScheduleDetailActivity.class);
                scheduleDetailIntent.putExtra("firstcountryName", holder.stadiumDetailRelatedMatchesListItemBinding.firstCountryNameTxt.getText().toString());
                scheduleDetailIntent.putExtra("secondcountryName", holder.stadiumDetailRelatedMatchesListItemBinding.secondCountryNameTxt.getText().toString());
                scheduleDetailIntent.putExtra("firstscountryFlag", detail.getTicketMatchFirstCountryFlag());
                scheduleDetailIntent.putExtra("secondcountryFlag", detail.getTicketMatchSecondCountryFlag());
                scheduleDetailIntent.putExtra("groundLocation", holder.stadiumDetailRelatedMatchesListItemBinding.matchGroundLocationTxt.getText().toString());
                scheduleDetailIntent.putExtra("matchdatetime", holder.stadiumDetailRelatedMatchesListItemBinding.matchDateTimeTxt.getText().toString());
                scheduleDetailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mContext.startActivity(scheduleDetailIntent);
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
        return travelStadiumOtherMatchesList.size();
    }

}


