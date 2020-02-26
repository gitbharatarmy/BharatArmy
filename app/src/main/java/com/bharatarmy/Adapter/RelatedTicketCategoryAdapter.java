package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Activity.TravelMatchSelectHotelActivity;
import com.bharatarmy.Activity.TravelMatchTicketAndHosipitalityticketdetailActivity;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.RelatedCategoryTicketListItemBinding;

import java.util.ArrayList;

public class RelatedTicketCategoryAdapter extends RecyclerView.Adapter<RelatedTicketCategoryAdapter.MyViewHolder> {
    Context mContext;
    ArrayList<TravelModel> relatedticketCategorylist;
    private ArrayList<String> dataCheck = new ArrayList<String>();
    int selectedposition;



    public RelatedTicketCategoryAdapter(Context mContext, ArrayList<TravelModel> relatedticketCategorylist, int selectedposition) {
        this.mContext = mContext;
        this.relatedticketCategorylist = relatedticketCategorylist;
        this.selectedposition = selectedposition;

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        RelatedCategoryTicketListItemBinding relatedCategoryTicketListItemBinding;

        public MyViewHolder(RelatedCategoryTicketListItemBinding relatedCategoryTicketListItemBinding) {
            super(relatedCategoryTicketListItemBinding.getRoot());

            this.relatedCategoryTicketListItemBinding = relatedCategoryTicketListItemBinding;

        }
    }


    @Override
    public RelatedTicketCategoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RelatedCategoryTicketListItemBinding relatedCategoryTicketListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.related_category_ticket_list_item, parent, false);
        return new RelatedTicketCategoryAdapter.MyViewHolder(relatedCategoryTicketListItemBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(RelatedTicketCategoryAdapter.MyViewHolder holder, int position) {
        TravelModel detail = relatedticketCategorylist.get(position);


        holder.relatedCategoryTicketListItemBinding.tickethospitalitypriceTxt.priceTxt.setText(detail.getTicket_hospitality_price());
        Utils.setImageInImageView(detail.getTicket_hospitality_bannerImage(), holder.relatedCategoryTicketListItemBinding.travelTicketHospitalityBannerImg, mContext);
        holder.relatedCategoryTicketListItemBinding.categoryNameTxt.setText(detail.getTicket_hospitality_namecategory());
        holder.relatedCategoryTicketListItemBinding.showPackageTourDescriptionTxt.setText(detail.getTicket_hospitality_desc());

        if (!detail.getTicket_hospitality_offers().equalsIgnoreCase("")) {
            holder.relatedCategoryTicketListItemBinding.offersLinear.setVisibility(View.VISIBLE);
            holder.relatedCategoryTicketListItemBinding.offersTxt.setText(detail.getTicket_hospitality_offers());
        } else {
            holder.relatedCategoryTicketListItemBinding.offersLinear.setVisibility(View.GONE);
        }

        holder.relatedCategoryTicketListItemBinding.offersLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.handleClickEvent(mContext, holder.relatedCategoryTicketListItemBinding.offersLinear);
                Intent selecthotelIntent = new Intent(mContext, TravelMatchSelectHotelActivity.class);
                selecthotelIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mContext.startActivity(selecthotelIntent);
            }
        });


        holder.relatedCategoryTicketListItemBinding.tickethospitalityCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent categoryIntent = new Intent(mContext, TravelMatchTicketAndHosipitalityticketdetailActivity.class);
                categoryIntent.putExtra("categoryName", detail.getTicket_hospitality_namecategory());
                categoryIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(categoryIntent);

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
        return relatedticketCategorylist.size();
    }


    public ArrayList<String> getDatas() {
        return dataCheck;
    }
}








