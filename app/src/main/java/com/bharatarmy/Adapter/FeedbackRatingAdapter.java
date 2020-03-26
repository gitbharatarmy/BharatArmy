package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Interfaces.MorestoryClick;
import com.bharatarmy.Models.FeedbackAnswerList;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.databinding.FeedbackRatingListItemBinding;
import com.bharatarmy.databinding.FeedbackSingleChoiceListItemBinding;

import java.util.ArrayList;
import java.util.List;

public class FeedbackRatingAdapter extends RecyclerView.Adapter<FeedbackRatingAdapter.MyViewHolder> {
    Context mcontext;
    List<FeedbackAnswerList> feedbackratingsinglechoicelist;
    int selectedratingchangeposition = -1;
    MorestoryClick morestoryClick;



    public FeedbackRatingAdapter(Context mContext, List<FeedbackAnswerList> feedbackratingsinglechoicelist, int selectedratingchangeposition, MorestoryClick morestoryClick) {
        this.mcontext = mContext;
        this.feedbackratingsinglechoicelist=feedbackratingsinglechoicelist;
        this.morestoryClick=morestoryClick;
        this.selectedratingchangeposition = selectedratingchangeposition;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        FeedbackRatingListItemBinding feedbackRatingListItemBinding;

        public MyViewHolder(FeedbackRatingListItemBinding feedbackRatingListItemBinding) {
            super(feedbackRatingListItemBinding.getRoot());

            this.feedbackRatingListItemBinding = feedbackRatingListItemBinding;

        }
    }


    @Override
    public FeedbackRatingAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FeedbackRatingListItemBinding feedbackRatingListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.feedback_rating_list_item, parent, false);
        return new FeedbackRatingAdapter.MyViewHolder(feedbackRatingListItemBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(FeedbackRatingAdapter.MyViewHolder holder, int position) {
        FeedbackAnswerList ansratingdetail = feedbackratingsinglechoicelist.get(position);

        holder.feedbackRatingListItemBinding.textratingSinglechoiceOptionTxt.setText(ansratingdetail.getQuestionAnswerText());


        if (ansratingdetail.getQuestionAnswerRating().equalsIgnoreCase("5")) {
            holder.feedbackRatingListItemBinding.start1Img.setVisibility(View.VISIBLE);
            holder.feedbackRatingListItemBinding.start2Img.setVisibility(View.VISIBLE);
            holder.feedbackRatingListItemBinding.start3Img.setVisibility(View.VISIBLE);
            holder.feedbackRatingListItemBinding.start4Img.setVisibility(View.VISIBLE);
            holder.feedbackRatingListItemBinding.start5Img.setVisibility(View.VISIBLE);
        }else if(ansratingdetail.getQuestionAnswerRating().equalsIgnoreCase("4")){
            holder.feedbackRatingListItemBinding.start1Img.setVisibility(View.VISIBLE);
            holder.feedbackRatingListItemBinding.start2Img.setVisibility(View.VISIBLE);
            holder.feedbackRatingListItemBinding.start3Img.setVisibility(View.VISIBLE);
            holder.feedbackRatingListItemBinding.start4Img.setVisibility(View.VISIBLE);
            holder.feedbackRatingListItemBinding.start5Img.setVisibility(View.GONE);
        }else if(ansratingdetail.getQuestionAnswerRating().equalsIgnoreCase("3")){
            holder.feedbackRatingListItemBinding.start1Img.setVisibility(View.VISIBLE);
            holder.feedbackRatingListItemBinding.start2Img.setVisibility(View.VISIBLE);
            holder.feedbackRatingListItemBinding.start3Img.setVisibility(View.VISIBLE);
            holder.feedbackRatingListItemBinding.start4Img.setVisibility(View.GONE);
            holder.feedbackRatingListItemBinding.start5Img.setVisibility(View.GONE);
        }else if(ansratingdetail.getQuestionAnswerRating().equalsIgnoreCase("2")){
            holder.feedbackRatingListItemBinding.start1Img.setVisibility(View.VISIBLE);
            holder.feedbackRatingListItemBinding.start2Img.setVisibility(View.VISIBLE);
            holder.feedbackRatingListItemBinding.start3Img.setVisibility(View.GONE);
            holder.feedbackRatingListItemBinding.start4Img.setVisibility(View.GONE);
            holder.feedbackRatingListItemBinding.start5Img.setVisibility(View.GONE);
        }else{
            holder.feedbackRatingListItemBinding.ratingLinear.setVisibility(View.GONE);
        }


        if (selectedratingchangeposition == position) {
            holder.feedbackRatingListItemBinding.textratingSinglechoiceOptionChk.setChecked(true);
            ansratingdetail.setQuestionAnswerRatingSelect("1");
            AppConfiguration.singlechoice = "fill";
        } else {
            holder.feedbackRatingListItemBinding.textratingSinglechoiceOptionChk.setChecked(false);
            ansratingdetail.setQuestionAnswerRatingSelect("0");


        }


        holder.feedbackRatingListItemBinding.textratingSinglechoiceLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.feedbackRatingListItemBinding.textratingSinglechoiceOptionChk.isChecked()){
                    holder.feedbackRatingListItemBinding.textratingSinglechoiceOptionChk.setChecked(false);
                    ansratingdetail.setQuestionAnswerRatingSelect("0");
                    selectedratingchangeposition =position;
                    notifyDataSetChanged();
                }else{
                    holder.feedbackRatingListItemBinding.textratingSinglechoiceOptionChk.setChecked(true);
                    ansratingdetail.setQuestionAnswerRatingSelect("1");
                    selectedratingchangeposition = position;
                    notifyDataSetChanged();
                    morestoryClick.getmorestoryClick();
                }

            }
        });

        holder.feedbackRatingListItemBinding.textratingSinglechoiceOptionChk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.feedbackRatingListItemBinding.textratingSinglechoiceOptionChk.isChecked()){
                    ansratingdetail.setQuestionAnswerRatingSelect("1");
                    selectedratingchangeposition = position;
                    notifyDataSetChanged();
                    morestoryClick.getmorestoryClick();
                }else{
                    ansratingdetail.setQuestionAnswerRatingSelect("0");
                    selectedratingchangeposition = position;
                    notifyDataSetChanged();
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
        return feedbackratingsinglechoicelist.size();
    }


}





