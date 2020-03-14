package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Models.FeedbackAnswerList;
import com.bharatarmy.Models.FeedbackModel;
import com.bharatarmy.Models.FeedbackQuestionAnswerDatum;
import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.FeedbackImageWithTextChoiceListItemBinding;

import java.util.ArrayList;
import java.util.List;

public class FeedbackImagewithTextAdapter extends RecyclerView.Adapter<FeedbackImagewithTextAdapter.MyViewHolder> {
    Context mcontext;
    List<FeedbackAnswerList> feedbackansimagetextlist;
    int selectedroomforchangesposition = -1;


    public FeedbackImagewithTextAdapter(Context mContext, List<FeedbackAnswerList> feedbackansimagetextlist) {
        this.mcontext = mContext;
        this.feedbackansimagetextlist = feedbackansimagetextlist;
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

        if (selectedroomforchangesposition == position) {
            holder.feedbackImageWithTextChoiceListItemBinding.optionChk.setChecked(true);
            holder.feedbackImageWithTextChoiceListItemBinding.optionChk.setBackground(mcontext.getResources().getDrawable(R.drawable.feedback_corner_select_shape));
            AppConfiguration.imagechoice = "fill";
        } else {
            holder.feedbackImageWithTextChoiceListItemBinding.optionChk.setChecked(false);
            holder.feedbackImageWithTextChoiceListItemBinding.optionChk.setBackground(mcontext.getResources().getDrawable(R.drawable.feedback_corner_shape));
        }

        Utils.setImageInImageView(detail.getQuestionAnswerImage(), holder.feedbackImageWithTextChoiceListItemBinding.feedbackImage, mcontext);
        holder.feedbackImageWithTextChoiceListItemBinding.textviewAnsTxt.setText(detail.getQuestionAnswerText());

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


