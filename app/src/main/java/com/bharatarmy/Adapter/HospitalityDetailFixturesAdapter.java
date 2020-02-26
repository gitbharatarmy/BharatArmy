package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Activity.AddToCartActivity;
import com.bharatarmy.Activity.TravelMatchScheduleDetailActivity;
import com.bharatarmy.Interfaces.MorestoryClick;
import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.Models.HomeTemplateDetailModel;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.HospitalityDetailFixturesItemListBinding;

import java.util.ArrayList;
import java.util.List;

public class HospitalityDetailFixturesAdapter extends RecyclerView.Adapter<HospitalityDetailFixturesAdapter.MyItemViewHolder> {

    HospitalitySubAdapter hospitalitySubAdapter;
    List<HomeTemplateDetailModel> travelHospitalityFixturesList;
    ArrayList<TravelModel> hospitalitySubList;
    MorestoryClick morestoryClick;
    image_click image_click;
    Context mContext;
    int adapteraddcartposition;

    public HospitalityDetailFixturesAdapter(Context mContext, List<HomeTemplateDetailModel> travelHospitalityFixturesList,
                                            MorestoryClick morestoryClick, image_click image_click) {
        this.mContext = mContext;
        this.travelHospitalityFixturesList = travelHospitalityFixturesList;
        this.morestoryClick = morestoryClick;
        this.image_click = image_click;
    }


    static class MyItemViewHolder extends RecyclerView.ViewHolder {
        HospitalityDetailFixturesItemListBinding hospitalityDetailFixturesItemListBinding;


        public MyItemViewHolder(HospitalityDetailFixturesItemListBinding hospitalityDetailFixturesItemListBinding) {
            super(hospitalityDetailFixturesItemListBinding.getRoot());
            this.hospitalityDetailFixturesItemListBinding = hospitalityDetailFixturesItemListBinding;
        }
    }


    @NonNull
    @Override
    public HospitalityDetailFixturesAdapter.MyItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        HospitalityDetailFixturesItemListBinding hospitalityDetailFixturesItemListBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.hospitality_detail_fixtures_item_list, parent, false);
        return new HospitalityDetailFixturesAdapter.MyItemViewHolder(hospitalityDetailFixturesItemListBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final HospitalityDetailFixturesAdapter.MyItemViewHolder holder, int position) {
        HomeTemplateDetailModel detail = travelHospitalityFixturesList.get(position);

//holder.hospitalityDetailFixturesItemListBinding.layout2.tickethospitalitypriceTxt.priceTxt.setText("");
        holder.hospitalityDetailFixturesItemListBinding.layout2.matchTypeTagTxt.setText(detail.getTicketMatchType());
        holder.hospitalityDetailFixturesItemListBinding.layout2.firstCountryNameTxt.setText(detail.getTicketMatchFirstCountryName());
        Utils.setImageInImageView(detail.getTicketMatchFirstCountryFlag(), holder.hospitalityDetailFixturesItemListBinding.layout2.firstCountryflagImage, mContext);
        holder.hospitalityDetailFixturesItemListBinding.layout2.secondCountryNameTxt.setText(detail.getTicketMatchSecondCountryName());
        Utils.setImageInImageView(detail.getTicketMatchSecondCountryFlag(), holder.hospitalityDetailFixturesItemListBinding.layout2.secondCountryflagImage, mContext);
        holder.hospitalityDetailFixturesItemListBinding.layout2.matchGroundLocationTxt.setText(detail.getTicketMatchGroundName());
        holder.hospitalityDetailFixturesItemListBinding.layout2.matchDateTimeTxt.setText(detail.getTicketMatchTimeDate());

        holder.hospitalityDetailFixturesItemListBinding.mainGroupLiner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent scheduleDetailIntent = new Intent(mContext, TravelMatchScheduleDetailActivity.class);
                scheduleDetailIntent.putExtra("firstcountryName", holder.hospitalityDetailFixturesItemListBinding.layout2.firstCountryNameTxt.getText().toString());
                scheduleDetailIntent.putExtra("secondcountryName", holder.hospitalityDetailFixturesItemListBinding.layout2.secondCountryNameTxt.getText().toString());
                scheduleDetailIntent.putExtra("firstscountryFlag", detail.getTicketMatchFirstCountryFlag());
                scheduleDetailIntent.putExtra("secondcountryFlag", detail.getTicketMatchSecondCountryFlag());
                scheduleDetailIntent.putExtra("groundLocation", holder.hospitalityDetailFixturesItemListBinding.layout2.matchGroundLocationTxt.getText().toString());
                scheduleDetailIntent.putExtra("matchdatetime", holder.hospitalityDetailFixturesItemListBinding.layout2.matchDateTimeTxt.getText().toString());
                scheduleDetailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mContext.startActivity(scheduleDetailIntent);
            }
        });


        holder.hospitalityDetailFixturesItemListBinding.layout2.addCartLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.hospitalityDetailFixturesItemListBinding.layout2.addCartLayout.setVisibility(View.GONE);
                holder.hospitalityDetailFixturesItemListBinding.layout2.removeCartLayout.setVisibility(View.VISIBLE);
                adapteraddcartposition = position;
                morestoryClick.getmorestoryClick();
            }
        });
        holder.hospitalityDetailFixturesItemListBinding.layout2.removeCartLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.hospitalityDetailFixturesItemListBinding.layout2.addCartLayout.setVisibility(View.VISIBLE);
                holder.hospitalityDetailFixturesItemListBinding.layout2.removeCartLayout.setVisibility(View.GONE);
                image_click.image_more_click();
            }
        });
    }

    @Override
    public void onBindViewHolder(@NonNull MyItemViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (!payloads.isEmpty()) {
            for (final Object payload : payloads) {
                if (payload.toString().equalsIgnoreCase("remove")) {
                    holder.hospitalityDetailFixturesItemListBinding.layout2.addCartLayout.setVisibility(View.VISIBLE);
                    holder.hospitalityDetailFixturesItemListBinding.layout2.removeCartLayout.setVisibility(View.GONE);
                }
            }
        } else {
            // in this case regular onBindViewHolder will be called
            super.onBindViewHolder(holder, position, payloads);
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
        return travelHospitalityFixturesList.size();
    }

    public int adptercartAddPosition() {
        return adapteraddcartposition;
    }
}


