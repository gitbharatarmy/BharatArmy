package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Models.FeedbackAnswerList;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.FeedbackAnsListItemBinding;
import com.bharatarmy.databinding.FeedbackViewListItemBinding;

import java.util.ArrayList;

public class FeedbackViewAdapter extends RecyclerView.Adapter<FeedbackViewAdapter.MyViewHolder> {
    Context mContext;
    ArrayList<FeedbackAnswerList> feedbackviewanslist;

    public FeedbackViewAdapter(Context mContext, ArrayList<FeedbackAnswerList> feedbackviewanslist) {
        this.mContext = mContext;
        this.feedbackviewanslist = feedbackviewanslist;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        FeedbackViewListItemBinding feedbackViewListItemBinding;

        public MyViewHolder(FeedbackViewListItemBinding feedbackViewListItemBinding) {
            super(feedbackViewListItemBinding.getRoot());

            this.feedbackViewListItemBinding = feedbackViewListItemBinding;

        }
    }


    @Override
    public FeedbackViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FeedbackViewListItemBinding feedbackViewListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.feedback_view_list_item, parent, false);
        return new FeedbackViewAdapter.MyViewHolder(feedbackViewListItemBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(FeedbackViewAdapter.MyViewHolder holder, int position) {
        FeedbackAnswerList ansviewdetail = feedbackviewanslist.get(position);


        holder.feedbackViewListItemBinding.questionTxt.setText(ansviewdetail.getQuestionStr());

        if (ansviewdetail.getQuestiontypeStr().equalsIgnoreCase("textview")) {
            if (ansviewdetail.getQuestionanswerStr() != null && !ansviewdetail.getQuestionanswerStr().equalsIgnoreCase("")) {
                holder.feedbackViewListItemBinding.textAnsLinear.setVisibility(View.VISIBLE);
                holder.feedbackViewListItemBinding.ansTxt.setText(ansviewdetail.getQuestionanswerStr());
            } else {
                holder.feedbackViewListItemBinding.textAnsLinear.setVisibility(View.GONE);
            }
            holder.feedbackViewListItemBinding.ratingAnsLinear.setVisibility(View.GONE);
            holder.feedbackViewListItemBinding.imageTextLayout.setVisibility(View.GONE);
            holder.feedbackViewListItemBinding.textgridAnsLinear.setVisibility(View.GONE);

        } else if (ansviewdetail.getQuestiontypeStr().equalsIgnoreCase("rating")) {
            holder.feedbackViewListItemBinding.textAnsLinear.setVisibility(View.GONE);
            if (ansviewdetail.getQuestionanswerStr() != null && ansviewdetail.getQuestionanswerotherStr() != null) {
                if (!ansviewdetail.getQuestionanswerStr().equalsIgnoreCase("") && !ansviewdetail.getQuestionanswerotherStr().equalsIgnoreCase("")) {
                    holder.feedbackViewListItemBinding.ratingAnsLinear.setVisibility(View.VISIBLE);
                    holder.feedbackViewListItemBinding.ratingLinear.setVisibility(View.VISIBLE);
                    holder.feedbackViewListItemBinding.ratingAnsTxt.setText(ansviewdetail.getQuestionanswerStr());
                } else {
                    holder.feedbackViewListItemBinding.ratingAnsLinear.setVisibility(View.GONE);
                    holder.feedbackViewListItemBinding.ratingLinear.setVisibility(View.GONE);
                }
            } else {
                holder.feedbackViewListItemBinding.ratingAnsLinear.setVisibility(View.GONE);
                holder.feedbackViewListItemBinding.ratingLinear.setVisibility(View.GONE);
            }
            holder.feedbackViewListItemBinding.imageTextLayout.setVisibility(View.GONE);
            holder.feedbackViewListItemBinding.textgridAnsLinear.setVisibility(View.GONE);
        } else if (ansviewdetail.getQuestiontypeStr().equalsIgnoreCase("textgrid")) {
            holder.feedbackViewListItemBinding.textAnsLinear.setVisibility(View.GONE);
            holder.feedbackViewListItemBinding.ratingAnsLinear.setVisibility(View.GONE);
            holder.feedbackViewListItemBinding.imageTextLayout.setVisibility(View.GONE);
            if (ansviewdetail.getQuestionanswerStr() != null && !ansviewdetail.getQuestionanswerStr().equalsIgnoreCase("")) {
                holder.feedbackViewListItemBinding.textgridAnsLinear.setVisibility(View.VISIBLE);
                holder.feedbackViewListItemBinding.textgridAnsTxt.setText(ansviewdetail.getQuestionanswerStr());
            } else {
                holder.feedbackViewListItemBinding.textgridAnsLinear.setVisibility(View.GONE);
            }

        } else if (ansviewdetail.getQuestiontypeStr().equalsIgnoreCase("imagetext")) {
            holder.feedbackViewListItemBinding.textAnsLinear.setVisibility(View.GONE);
            holder.feedbackViewListItemBinding.ratingAnsLinear.setVisibility(View.GONE);
            holder.feedbackViewListItemBinding.textgridAnsLinear.setVisibility(View.GONE);
            if (ansviewdetail.getQuestionanswerotherStr() != null && !ansviewdetail.getQuestionanswerotherStr().equalsIgnoreCase("")) {
                holder.feedbackViewListItemBinding.imageTextLayout.setVisibility(View.VISIBLE);
                Utils.setImageInImageView(ansviewdetail.getQuestionanswerotherStr(), holder.feedbackViewListItemBinding.feedbackImage, mContext);
                holder.feedbackViewListItemBinding.textviewAnsTxt.setText(ansviewdetail.getQuestionanswerStr());
            } else {
                holder.feedbackViewListItemBinding.imageTextLayout.setVisibility(View.GONE);
            }
        }



        /*rating ans display rating*/
        if (ansviewdetail.getQuestionanswerotherStr().equalsIgnoreCase("5")) {
            holder.feedbackViewListItemBinding.start1Img.setVisibility(View.VISIBLE);
            holder.feedbackViewListItemBinding.start2Img.setVisibility(View.VISIBLE);
            holder.feedbackViewListItemBinding.start3Img.setVisibility(View.VISIBLE);
            holder.feedbackViewListItemBinding.start4Img.setVisibility(View.VISIBLE);
            holder.feedbackViewListItemBinding.start5Img.setVisibility(View.VISIBLE);
        } else if (ansviewdetail.getQuestionanswerotherStr().equalsIgnoreCase("4")) {
            holder.feedbackViewListItemBinding.start1Img.setVisibility(View.VISIBLE);
            holder.feedbackViewListItemBinding.start2Img.setVisibility(View.VISIBLE);
            holder.feedbackViewListItemBinding.start3Img.setVisibility(View.VISIBLE);
            holder.feedbackViewListItemBinding.start4Img.setVisibility(View.VISIBLE);
            holder.feedbackViewListItemBinding.start5Img.setVisibility(View.GONE);
        } else if (ansviewdetail.getQuestionanswerotherStr().equalsIgnoreCase("3")) {
            holder.feedbackViewListItemBinding.start1Img.setVisibility(View.VISIBLE);
            holder.feedbackViewListItemBinding.start2Img.setVisibility(View.VISIBLE);
            holder.feedbackViewListItemBinding.start3Img.setVisibility(View.VISIBLE);
            holder.feedbackViewListItemBinding.start4Img.setVisibility(View.GONE);
            holder.feedbackViewListItemBinding.start5Img.setVisibility(View.GONE);
        } else if (ansviewdetail.getQuestionanswerotherStr().equalsIgnoreCase("2")) {
            holder.feedbackViewListItemBinding.start1Img.setVisibility(View.VISIBLE);
            holder.feedbackViewListItemBinding.start2Img.setVisibility(View.VISIBLE);
            holder.feedbackViewListItemBinding.start3Img.setVisibility(View.GONE);
            holder.feedbackViewListItemBinding.start4Img.setVisibility(View.GONE);
            holder.feedbackViewListItemBinding.start5Img.setVisibility(View.GONE);
        } else {
            holder.feedbackViewListItemBinding.ratingLinear.setVisibility(View.GONE);
        }

//
//        if (!ansviewdetail.getPopularcity_image_count().equalsIgnoreCase("") && !ansviewdetail.getPopularcity_name().equalsIgnoreCase("")) {
//            holder.feedbackViewListItemBinding.ansTxt.setVisibility(View.VISIBLE);
//            holder.feedbackViewListItemBinding.ansImage.setVisibility(View.VISIBLE);
//            holder.feedbackViewListItemBinding.ansTxt.setText(ansviewdetail.getPopularcity_name());
//            Utils.setImageInImageView(ansviewdetail.getPopularcity_image_count(), holder.feedbackViewListItemBinding.ansImage, mContext);
//        } else if (!ansviewdetail.getPopularcity_image_count().equalsIgnoreCase("") && ansviewdetail.getPopularcity_name().equalsIgnoreCase("")) {
//            holder.feedbackViewListItemBinding.ansTxt.setVisibility(View.GONE);
//            holder.feedbackViewListItemBinding.ansImage.setVisibility(View.VISIBLE);
//            Utils.setImageInImageView(ansviewdetail.getPopularcity_image_count(), holder.feedbackViewListItemBinding.ansImage, mContext);
//        } else {
//            holder.feedbackViewListItemBinding.ansImage.setVisibility(View.GONE);
//        }
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
        return feedbackviewanslist.size();
    }


}


