package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Interfaces.MorestoryClick;
import com.bharatarmy.Models.ImageDetailModel;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.FeedbackAnsListItemBinding;

import java.util.ArrayList;
import java.util.List;

public class FeedbackAnsAdapter extends RecyclerView.Adapter<FeedbackAnsAdapter.MyViewHolder> {
    Context mcontext;
    ArrayList<TravelModel> feedbackanslist;
    MorestoryClick morestoryClick;

    public FeedbackAnsAdapter(Context mContext, ArrayList<TravelModel> feedbackanslist, MorestoryClick morestoryClick) {
        this.mcontext = mContext;
        this.feedbackanslist = feedbackanslist;
        this.morestoryClick=morestoryClick;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        FeedbackAnsListItemBinding feedbackAnsListItemBinding;

        public MyViewHolder(FeedbackAnsListItemBinding feedbackAnsListItemBinding) {
            super(feedbackAnsListItemBinding.getRoot());

            this.feedbackAnsListItemBinding = feedbackAnsListItemBinding;

        }
    }


    @Override
    public FeedbackAnsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FeedbackAnsListItemBinding feedbackAnsListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.feedback_ans_list_item, parent, false);
        return new FeedbackAnsAdapter.MyViewHolder(feedbackAnsListItemBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(FeedbackAnsAdapter.MyViewHolder holder, int position) {
        TravelModel ansdetail = feedbackanslist.get(position);

        holder.feedbackAnsListItemBinding.question3Option1Txt.setText(ansdetail.getCityHotelAmenitiesImage());

        holder.feedbackAnsListItemBinding.question3Option1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.feedbackAnsListItemBinding.question3Option1Chk.isChecked()) {
                    holder.feedbackAnsListItemBinding.question3Option1Chk.setChecked(false);
                    ansdetail.setCityHotelAmenitiesName("0");
                    holder.feedbackAnsListItemBinding.question3Option1Btn.setBackground(mcontext.getResources().getDrawable(R.drawable.feedback_que_ans_shape));
                    morestoryClick.getmorestoryClick();
                } else {
                    holder.feedbackAnsListItemBinding.question3Option1Chk.setChecked(true);
                    ansdetail.setCityHotelAmenitiesName("1");
                    holder.feedbackAnsListItemBinding.question3Option1Btn.setBackground(mcontext.getResources().getDrawable(R.drawable.feedback_selected_shape));
                    morestoryClick.getmorestoryClick();
                }
            }
        });

        holder.feedbackAnsListItemBinding.question3Option1Chk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.feedbackAnsListItemBinding.question3Option1Chk.isChecked()) {
                    holder.feedbackAnsListItemBinding.question3Option1Chk.setChecked(true);
                    ansdetail.setCityHotelAmenitiesName("1");
                    holder.feedbackAnsListItemBinding.question3Option1Btn.setBackground(mcontext.getResources().getDrawable(R.drawable.feedback_selected_shape));
                    morestoryClick.getmorestoryClick();
                } else {
                    holder.feedbackAnsListItemBinding.question3Option1Chk.setChecked(false);
                    ansdetail.setCityHotelAmenitiesName("0");
                    holder.feedbackAnsListItemBinding.question3Option1Btn.setBackground(mcontext.getResources().getDrawable(R.drawable.feedback_que_ans_shape));
                    morestoryClick.getmorestoryClick();

                }

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
        return feedbackanslist.size();
    }


}


