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

import java.util.ArrayList;
import java.util.List;

public class InquiryAssignAdapter extends RecyclerView.Adapter<InquiryAssignAdapter.MyViewHolder> {
    Context mContext;
    List<MoreDetailDataModel> assignmemberlist;
    MorestoryClick morestoryClick;
    private int lastSelectedPosition = -1;

    public InquiryAssignAdapter(Context mContext, List<MoreDetailDataModel> assignmemberlist, MorestoryClick morestoryClick) {
        this.mContext=mContext;
        this.assignmemberlist=assignmemberlist;
        this.morestoryClick=morestoryClick;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        InquiryAssignlistItemBinding inquiryAssignlistItemBinding;

        public MyViewHolder(InquiryAssignlistItemBinding inquiryAssignlistItemBinding) {
            super(inquiryAssignlistItemBinding.getRoot());
            this.inquiryAssignlistItemBinding = inquiryAssignlistItemBinding;

            inquiryAssignlistItemBinding.assignListlinear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lastSelectedPosition = getAdapterPosition();
                    Log.d("selectedposition : ",""+lastSelectedPosition);
                    notifyDataSetChanged();
                    AppConfiguration.selectedposition= lastSelectedPosition;

                    for (int i = 0; i < assignmemberlist.size(); i++) {
                        if (lastSelectedPosition==i) {
                            AppConfiguration.selectedposition=assignmemberlist.get(i).getId();
                        }

                    }
                }
            });
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

        holder.inquiryAssignlistItemBinding.selectedChk.setChecked(lastSelectedPosition == position);



//        holder.inquiryAssignlistItemBinding.selectedChk.setChecked(assignmemberlist.get(position).isSelected());
//        holder.checkBox.setTag(new Integer(position));
//
//        //for default check in first item
//        if(position == 0 && fonts.get(0).isSelected() && holder.checkBox.isChecked())
//        {
//            lastChecked = holder.checkBox;
//            lastCheckedPos = 0;
//        }
//
//        holder.checkBox.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                CheckBox cb = (CheckBox)v;
//                int clickedPos = ((Integer)cb.getTag()).intValue();
//
//                if(cb.isChecked)
//                {
//                    if(lastChecked != null)
//                    {
//                        lastChecked.setChecked(false);
//                        fonts.get(lastCheckedPos).setSelected(false);
//                    }
//
//                    lastChecked = cb;
//                    lastCheckedPos = clickedPos;
//                }
//                else
//                    lastChecked = null;
//
//                fonts.get(clickedPos).setSelected(cb.isChecked);
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
        return assignmemberlist.size();
    }


}



