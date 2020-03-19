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
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.FeedbackImageWithTextChoiceListItemBinding;

import java.util.List;

public class FeedbackImagewithTextAdapter extends RecyclerView.Adapter<FeedbackImagewithTextAdapter.MyViewHolder> {
    Context mcontext;
    List<FeedbackAnswerList> feedbackansimagetextlist;
    int selectedchangesposition = -1;
    MorestoryClick morestoryClick;


    public FeedbackImagewithTextAdapter(Context mContext, List<FeedbackAnswerList> feedbackansimagetextlist, int selectedchangesposition, MorestoryClick morestoryClick) {
        this.mcontext = mContext;
        this.feedbackansimagetextlist = feedbackansimagetextlist;
        this.morestoryClick = morestoryClick;
        this.selectedchangesposition = selectedchangesposition;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        FeedbackImageWithTextChoiceListItemBinding feedbackImageWithTextChoiceListItemBinding;

        public MyViewHolder(FeedbackImageWithTextChoiceListItemBinding feedbackImageWithTextChoiceListItemBinding) {
            super(feedbackImageWithTextChoiceListItemBinding.getRoot());

            this.feedbackImageWithTextChoiceListItemBinding = feedbackImageWithTextChoiceListItemBinding;

        }
    }


    @Override
    public FeedbackImagewithTextAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FeedbackImageWithTextChoiceListItemBinding feedbackImageWithTextChoiceListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.feedback_image_with_text_choice_list_item, parent, false);
        return new FeedbackImagewithTextAdapter.MyViewHolder(feedbackImageWithTextChoiceListItemBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(FeedbackImagewithTextAdapter.MyViewHolder holder, int position) {
        FeedbackAnswerList detail = feedbackansimagetextlist.get(position);

        if (selectedchangesposition == position) {
            holder.feedbackImageWithTextChoiceListItemBinding.optionChk.setChecked(true);
//            holder.feedbackImageWithTextChoiceListItemBinding.optionChk.setBackground(mcontext.getResources().getDrawable(R.drawable.feedback_corner_select_shape));
            AppConfiguration.imagechoice = "fill";
        } else {
            holder.feedbackImageWithTextChoiceListItemBinding.optionChk.setChecked(false);
//            holder.feedbackImageWithTextChoiceListItemBinding.optionChk.setBackground(mcontext.getResources().getDrawable(R.drawable.feedback_corner_shape));
        }

        Utils.setImageInImageView(detail.getImageUrl(), holder.feedbackImageWithTextChoiceListItemBinding.feedbackImage, mcontext);
        holder.feedbackImageWithTextChoiceListItemBinding.textviewAnsTxt.setText(detail.getText());


        holder.feedbackImageWithTextChoiceListItemBinding.imageTextChoiceLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.feedbackImageWithTextChoiceListItemBinding.optionChk.isChecked()){
                    holder.feedbackImageWithTextChoiceListItemBinding.optionChk.setChecked(false);
                    detail.setQuestionAnswerImagewithTextSelect("0");
                    selectedchangesposition = position;
                    notifyDataSetChanged();
                    morestoryClick.getmorestoryClick();
                }else{
                    holder.feedbackImageWithTextChoiceListItemBinding.optionChk.setChecked(true);
                    detail.setQuestionAnswerImagewithTextSelect("1");
                    selectedchangesposition = position;
                    notifyDataSetChanged();
                    morestoryClick.getmorestoryClick();
                }

            }
        });

        holder.feedbackImageWithTextChoiceListItemBinding.optionChk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.feedbackImageWithTextChoiceListItemBinding.optionChk.isChecked()){
                    detail.setQuestionAnswerImagewithTextSelect("1");
                    selectedchangesposition = position;
                    notifyDataSetChanged();
                    morestoryClick.getmorestoryClick();
                }else{
                    detail.setQuestionAnswerImagewithTextSelect("0");
                    selectedchangesposition = position;
                    notifyDataSetChanged();
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
        return feedbackansimagetextlist.size();
    }


}


