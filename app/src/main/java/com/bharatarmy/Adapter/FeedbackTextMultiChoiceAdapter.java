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
import com.bharatarmy.databinding.FeedbackTextMultichoiceListItemBinding;

import java.util.ArrayList;
import java.util.List;

public class FeedbackTextMultiChoiceAdapter extends RecyclerView.Adapter<FeedbackTextMultiChoiceAdapter.MyViewHolder> {
    Context mcontext;
    List<FeedbackAnswerList> feedbacktextmultichoicelist;



    public FeedbackTextMultiChoiceAdapter(Context mContext, List<FeedbackAnswerList> feedbacktextmultichoicelist) {
        this.mcontext = mContext;
        this.feedbacktextmultichoicelist=feedbacktextmultichoicelist;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        FeedbackTextMultichoiceListItemBinding feedbackTextMultichoiceListItemBinding;

        public MyViewHolder(FeedbackTextMultichoiceListItemBinding feedbackTextMultichoiceListItemBinding) {
            super(feedbackTextMultichoiceListItemBinding.getRoot());

            this.feedbackTextMultichoiceListItemBinding = feedbackTextMultichoiceListItemBinding;

        }
    }


    @Override
    public FeedbackTextMultiChoiceAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FeedbackTextMultichoiceListItemBinding feedbackTextMultichoiceListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.feedback_text_multichoice_list_item, parent, false);
        return new FeedbackTextMultiChoiceAdapter.MyViewHolder(feedbackTextMultichoiceListItemBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(FeedbackTextMultiChoiceAdapter.MyViewHolder holder, int position) {
        FeedbackAnswerList detail = feedbacktextmultichoicelist.get(position);

        holder.feedbackTextMultichoiceListItemBinding.optionTxt.setText(detail.getQuestionAnswerText());



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
        return feedbacktextmultichoicelist.size();
    }


}



