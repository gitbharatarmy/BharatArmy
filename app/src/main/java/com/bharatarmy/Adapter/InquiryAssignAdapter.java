package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Interfaces.MorestoryClick;
import com.bharatarmy.Models.MoreDetailDataModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.databinding.InquiryAssignlistItemBinding;

import java.util.List;

public class InquiryAssignAdapter extends RecyclerView.Adapter<InquiryAssignAdapter.MyViewHolder> {
    Context mContext;
    List<MoreDetailDataModel> assignmemberlist;
    MorestoryClick morestoryClick;
    int selectedposition;

    public InquiryAssignAdapter(Context mContext, List<MoreDetailDataModel> assignmemberlist, int selectedposition, MorestoryClick morestoryClick) {
        this.mContext = mContext;
        this.assignmemberlist = assignmemberlist;
        this.morestoryClick = morestoryClick;
        this.selectedposition=selectedposition;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        InquiryAssignlistItemBinding inquiryAssignlistItemBinding;

        public MyViewHolder(InquiryAssignlistItemBinding inquiryAssignlistItemBinding) {
            super(inquiryAssignlistItemBinding.getRoot());
            this.inquiryAssignlistItemBinding = inquiryAssignlistItemBinding;
        }
    }


    @Override
    public InquiryAssignAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        InquiryAssignlistItemBinding inquiryAssignlistItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.inquiry_assignlist_item, parent, false);
        return new InquiryAssignAdapter.MyViewHolder(inquiryAssignlistItemBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(InquiryAssignAdapter.MyViewHolder holder, int position) {
        MoreDetailDataModel detailDataModel = assignmemberlist.get(position);


        holder.inquiryAssignlistItemBinding.assignnameTxt.setText(detailDataModel.getName());
        holder.inquiryAssignlistItemBinding.assignemailTxt.setText(detailDataModel.getEmail());

        if (selectedposition==position){
            holder.inquiryAssignlistItemBinding.selectedChk.setChecked(true);
            AppConfiguration.selectedposition = detailDataModel.getId();
            Log.d("selectedId : ", "" + AppConfiguration.selectedposition);
        }else
        {
            holder.inquiryAssignlistItemBinding.selectedChk.setChecked(false);
        }

        holder.inquiryAssignlistItemBinding.assignListlinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedposition=position;
                notifyDataSetChanged();
            }
        });

        holder.inquiryAssignlistItemBinding.selectedChk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedposition=position;
                notifyDataSetChanged();
            }
        });


//        if (detailDataModel.getIsSelected().equals(1)){
//            lastSelectedPosition=detailDataModel.getIsSelected();
//        }else{
//
//        }
//
//        holder.inquiryAssignlistItemBinding.selectedChk.setChecked(lastSelectedPosition == position);





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
        return assignmemberlist.size();
    }


}



