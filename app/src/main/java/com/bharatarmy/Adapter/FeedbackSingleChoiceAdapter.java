package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Interfaces.MorestoryClick;
import com.bharatarmy.Interfaces.buttonclick_result;
import com.bharatarmy.Models.FeedbackAnswerList;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.databinding.FeedbackSingleChoiceListItemBinding;

import java.util.ArrayList;
import java.util.List;

public class FeedbackSingleChoiceAdapter extends RecyclerView.Adapter<FeedbackSingleChoiceAdapter.MyViewHolder> {
    Context mcontext;
    List<FeedbackAnswerList> feedbacktextsinglechoicelist;
    int selectedtextsinglechangesposition = -1;
    String selectedAnsStr;
    MorestoryClick morestoryClick;



    public FeedbackSingleChoiceAdapter(Context mContext, List<FeedbackAnswerList> feedbacktextsinglechoicelist, int selectedtextsinglechangesposition, MorestoryClick morestoryClick) {
        this.mcontext = mContext;
        this.feedbacktextsinglechoicelist=feedbacktextsinglechoicelist;
        this.morestoryClick=morestoryClick;
        this.selectedtextsinglechangesposition=selectedtextsinglechangesposition;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        FeedbackSingleChoiceListItemBinding feedbackSingleChoiceListItemBinding;

        public MyViewHolder(FeedbackSingleChoiceListItemBinding feedbackSingleChoiceListItemBinding) {
            super(feedbackSingleChoiceListItemBinding.getRoot());

            this.feedbackSingleChoiceListItemBinding = feedbackSingleChoiceListItemBinding;

        }
    }


    @Override
    public FeedbackSingleChoiceAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FeedbackSingleChoiceListItemBinding feedbackSingleChoiceListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.feedback_single_choice_list_item, parent, false);
        return new FeedbackSingleChoiceAdapter.MyViewHolder(feedbackSingleChoiceListItemBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(FeedbackSingleChoiceAdapter.MyViewHolder holder, int position) {
        FeedbackAnswerList ansdetail = feedbacktextsinglechoicelist.get(position);

        holder.feedbackSingleChoiceListItemBinding.question3Option1Txt.setText(ansdetail.getQuestionAnswerText());

        if (selectedtextsinglechangesposition == position) {
            holder.feedbackSingleChoiceListItemBinding.question3Option1Chk.setChecked(true);
//            holder.feedbackSingleChoiceListItemBinding.question3Option1Btn.setBackground(mcontext.getResources().getDrawable(R.drawable.feedback_corner_select_shape));

            AppConfiguration.singlechoice = "fill";
        } else {
//            ansdetail.setCityHotelAmenitiesName("0");
            holder.feedbackSingleChoiceListItemBinding.question3Option1Chk.setChecked(false);
//            holder.feedbackSingleChoiceListItemBinding.question3Option1Btn.setBackground(mcontext.getResources().getDrawable(R.drawable.feedback_corner_shape));


        }


        holder.feedbackSingleChoiceListItemBinding.question3Option1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (holder.feedbackSingleChoiceListItemBinding.question3Option1Chk.isChecked()){
                    holder.feedbackSingleChoiceListItemBinding.question3Option1Chk.setChecked(false);
                    ansdetail.setQuestionAnswerTextSingleSelect("0");
                    selectedtextsinglechangesposition =position;
                    notifyDataSetChanged();
                }else{
                    holder.feedbackSingleChoiceListItemBinding.question3Option1Chk.setChecked(true);
                    ansdetail.setQuestionAnswerTextSingleSelect("1");
                    selectedtextsinglechangesposition = position;
                    notifyDataSetChanged();
                    morestoryClick.getmorestoryClick();
                }


            }
        });

        holder.feedbackSingleChoiceListItemBinding.question3Option1Chk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.feedbackSingleChoiceListItemBinding.question3Option1Chk.isChecked()){
                    ansdetail.setQuestionAnswerTextSingleSelect("1");
                    selectedtextsinglechangesposition = position;
                    notifyDataSetChanged();
                    morestoryClick.getmorestoryClick();
                }else{
                    ansdetail.setQuestionAnswerTextSingleSelect("0");
                    selectedtextsinglechangesposition = position;
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
        return feedbacktextsinglechoicelist.size();
    }

    public String getSelectedANS() {
        return selectedAnsStr;
    }
}




