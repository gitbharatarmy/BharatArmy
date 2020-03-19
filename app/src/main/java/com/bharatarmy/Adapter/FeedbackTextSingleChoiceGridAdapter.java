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
import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.databinding.FeedbackTextSinglechoiceGridListItemBinding;

import java.util.List;

public class FeedbackTextSingleChoiceGridAdapter extends RecyclerView.Adapter<FeedbackTextSingleChoiceGridAdapter.MyViewHolder> {
    Context mContext;
    List<FeedbackAnswerList> feedbackanstextsinglechoicegridlist;
    int selectedtextsinglegridchangesposition = -1;
    MorestoryClick morestoryClick;


    public FeedbackTextSingleChoiceGridAdapter(Context mContext, List<FeedbackAnswerList> feedbackanstextsinglechoicegridlist, int selectedtextsinglegridchangesposition, MorestoryClick morestoryClick) {
        this.mContext=mContext;
        this.feedbackanstextsinglechoicegridlist=feedbackanstextsinglechoicegridlist;
        this.morestoryClick=morestoryClick;
        this.selectedtextsinglegridchangesposition= selectedtextsinglegridchangesposition;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        FeedbackTextSinglechoiceGridListItemBinding feedbackTextSinglechoiceGridListItemBinding;

        public MyViewHolder(FeedbackTextSinglechoiceGridListItemBinding feedbackTextSinglechoiceGridListItemBinding) {
            super(feedbackTextSinglechoiceGridListItemBinding.getRoot());

            this.feedbackTextSinglechoiceGridListItemBinding = feedbackTextSinglechoiceGridListItemBinding;

        }
    }


    @Override
    public FeedbackTextSingleChoiceGridAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FeedbackTextSinglechoiceGridListItemBinding feedbackTextSinglechoiceGridListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.feedback_text_singlechoice_grid_list_item, parent, false);
        return new FeedbackTextSingleChoiceGridAdapter.MyViewHolder(feedbackTextSinglechoiceGridListItemBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(FeedbackTextSingleChoiceGridAdapter.MyViewHolder holder, int position) {
        FeedbackAnswerList detail = feedbackanstextsinglechoicegridlist.get(position);

        holder.feedbackTextSinglechoiceGridListItemBinding.textviewAnsTxt.setText(detail.getQuestionAnswerText());

        if (selectedtextsinglegridchangesposition == position) {
            holder.feedbackTextSinglechoiceGridListItemBinding.optionChk.setChecked(true);
//            holder.feedbackTextSinglechoiceGridListItemBinding.textSinglechoiceLinear.setBackground(mContext.getResources().getDrawable(R.drawable.feedback_corner_select_shape));

            AppConfiguration.singlechoice = "fill";
        } else {
//            ansdetail.setCityHotelAmenitiesName("0");
            holder.feedbackTextSinglechoiceGridListItemBinding.optionChk.setChecked(false);
//            holder.feedbackTextSinglechoiceGridListItemBinding.textSinglechoiceLinear.setBackground(mContext.getResources().getDrawable(R.drawable.feedback_corner_shape));


        }


        holder.feedbackTextSinglechoiceGridListItemBinding.textSinglechoiceLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.feedbackTextSinglechoiceGridListItemBinding.optionChk.isChecked()){
                    holder.feedbackTextSinglechoiceGridListItemBinding.optionChk.setChecked(false);
                    detail.setQuestionAnswerTextGridSingleSelect("0");
                    selectedtextsinglegridchangesposition =position;
                    notifyDataSetChanged();
                }else{
                    holder.feedbackTextSinglechoiceGridListItemBinding.optionChk.setChecked(true);
                    detail.setQuestionAnswerTextGridSingleSelect("1");
                    selectedtextsinglegridchangesposition = position;
                    notifyDataSetChanged();
                    morestoryClick.getmorestoryClick();
                }



            }
        });

        holder.feedbackTextSinglechoiceGridListItemBinding.optionChk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.feedbackTextSinglechoiceGridListItemBinding.optionChk.isChecked()){
                     detail.setQuestionAnswerTextGridSingleSelect("1");
                    selectedtextsinglegridchangesposition = position;
                    notifyDataSetChanged();
                    morestoryClick.getmorestoryClick();
                }else{
                    detail.setQuestionAnswerTextGridSingleSelect("0");
                    selectedtextsinglegridchangesposition = position;
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
        return feedbackanstextsinglechoicegridlist.size();
    }


}



