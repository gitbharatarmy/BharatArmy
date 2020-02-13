package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Activity.FTPDetailsActivity;
import com.bharatarmy.Models.FeedbackModel;
import com.bharatarmy.Models.UpcommingDashboardModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.FeedbackQuestionAnswerListItemBinding;
import com.bharatarmy.databinding.UpcomingTournamentListNewBinding;

import java.util.ArrayList;
import java.util.List;

public class FeedbackQuestionAnswerAdapter extends RecyclerView.Adapter<FeedbackQuestionAnswerAdapter.MyViewHolder> {
    Context mContext;
    ArrayList<FeedbackModel> feedbackquestionArrayList;

    public FeedbackQuestionAnswerAdapter(Context mContext, ArrayList<FeedbackModel> feedbackModelArrayList) {
        this.mContext = mContext;
        this.feedbackquestionArrayList = feedbackModelArrayList;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        FeedbackQuestionAnswerListItemBinding feedbackQuestionAnswerListItemBinding;


        public MyViewHolder(FeedbackQuestionAnswerListItemBinding feedbackQuestionAnswerListItemBinding) {
            super(feedbackQuestionAnswerListItemBinding.getRoot());

            this.feedbackQuestionAnswerListItemBinding = feedbackQuestionAnswerListItemBinding;

        }
    }


    @Override
    public FeedbackQuestionAnswerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FeedbackQuestionAnswerListItemBinding feedbackQuestionAnswerListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.feedback_question_answer_list_item, parent, false);
        return new FeedbackQuestionAnswerAdapter.MyViewHolder(feedbackQuestionAnswerListItemBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(FeedbackQuestionAnswerAdapter.MyViewHolder holder, int position) {
        FeedbackModel questionDetailList = feedbackquestionArrayList.get(position);
        /*question type
         * 1= Multichoice
         * 2= Singlechoice
         * 3= Singlechoicerating
         * 4= Singlechoiceimage
         * 5= Edittext*/

        holder.feedbackQuestionAnswerListItemBinding.questionTxt.setText(questionDetailList.getFeedbackQuestionStr());
        if (!questionDetailList.getFeedbackQuestionCategoryStr().equalsIgnoreCase("")) {
            holder.feedbackQuestionAnswerListItemBinding.questionCategoryTitleTxt.setText(questionDetailList.getFeedbackQuestionCategoryStr());
        } else {
            holder.feedbackQuestionAnswerListItemBinding.questionCategoryTitleTxt.setVisibility(View.GONE);
        }

        if (questionDetailList.getFeedbackQuestionTypeStr().equalsIgnoreCase("1")){
            holder.feedbackQuestionAnswerListItemBinding.multichoiceLinear.setVisibility(View.VISIBLE);
            holder.feedbackQuestionAnswerListItemBinding.singlechoiceLinear.setVisibility(View.GONE);
            holder.feedbackQuestionAnswerListItemBinding.singleimagechoiceLinear.setVisibility(View.GONE);
            holder.feedbackQuestionAnswerListItemBinding.singleratingchoiceLinear.setVisibility(View.GONE);
            holder.feedbackQuestionAnswerListItemBinding.edittextAnsLinear.setVisibility(View.GONE);
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
        return feedbackquestionArrayList.size();
    }
}


