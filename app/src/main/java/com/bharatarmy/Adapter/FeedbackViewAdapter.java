package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Models.FeedbackAnswerList;
import com.bharatarmy.Models.LoginDataModel;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.FeedbackAnsListItemBinding;
import com.bharatarmy.databinding.FeedbackViewListItemBinding;

import java.util.ArrayList;
import java.util.List;

public class FeedbackViewAdapter extends RecyclerView.Adapter<FeedbackViewAdapter.MyViewHolder> {
    Context mContext;
    List<LoginDataModel> feedbackviewanslist;
    String feedbackansimageStr;

    public FeedbackViewAdapter(Context mContext, List<LoginDataModel> feedbackviewanslist, String feedbackansimageStr) {
        this.mContext = mContext;
        this.feedbackviewanslist = feedbackviewanslist;
        this.feedbackansimageStr=feedbackansimageStr;
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
        LoginDataModel ansviewdetail = feedbackviewanslist.get(position);


        holder.feedbackViewListItemBinding.questionTxt.setText(ansviewdetail.getFeedbackQuestion());

        if (ansviewdetail.getFeedbackType().equals(1) ||
                ansviewdetail.getFeedbackType().equals(5) ||
                ansviewdetail.getFeedbackType().equals(6) ||
                ansviewdetail.getFeedbackType().equals(2)) {
            if (ansviewdetail.getAnswerValue() != null && !ansviewdetail.getAnswerValue().equalsIgnoreCase("")) {
                holder.feedbackViewListItemBinding.textAnsLinear.setVisibility(View.VISIBLE);
                holder.feedbackViewListItemBinding.ansTxt.setText(ansviewdetail.getAnswerValue().trim());
            } else {
                holder.feedbackViewListItemBinding.textAnsLinear.setVisibility(View.GONE);
            }
            holder.feedbackViewListItemBinding.ratingAnsLinear.setVisibility(View.GONE);
            holder.feedbackViewListItemBinding.imageTextLayout.setVisibility(View.GONE);
            holder.feedbackViewListItemBinding.textgridAnsLinear.setVisibility(View.GONE);

        } else if (ansviewdetail.getFeedbackType().equals(7)) {
            holder.feedbackViewListItemBinding.textAnsLinear.setVisibility(View.GONE);
            if (ansviewdetail.getAnswerValue() != null && ansviewdetail.getAnswerValue() != null) {
                if (!ansviewdetail.getAnswerValue().equalsIgnoreCase("") && !ansviewdetail.getAnswerValue().equalsIgnoreCase("")) {
                    holder.feedbackViewListItemBinding.ratingAnsLinear.setVisibility(View.VISIBLE);
                    holder.feedbackViewListItemBinding.ratingLinear.setVisibility(View.VISIBLE);
                    holder.feedbackViewListItemBinding.ratingAnsTxt.setText(ansviewdetail.getAnswerValue());
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
        } else if (ansviewdetail.getFeedbackType().equals(8)) {
            holder.feedbackViewListItemBinding.textAnsLinear.setVisibility(View.GONE);
            holder.feedbackViewListItemBinding.ratingAnsLinear.setVisibility(View.GONE);
            holder.feedbackViewListItemBinding.imageTextLayout.setVisibility(View.GONE);
            if (ansviewdetail.getAnswerValue() != null && !ansviewdetail.getAnswerValue().equalsIgnoreCase("")) {
                holder.feedbackViewListItemBinding.textgridAnsLinear.setVisibility(View.VISIBLE);
                holder.feedbackViewListItemBinding.textgridAnsTxt.setText(ansviewdetail.getAnswerValue());
            } else {
                holder.feedbackViewListItemBinding.textgridAnsLinear.setVisibility(View.GONE);
            }

        } else if (ansviewdetail.getFeedbackType().equals(4)) {
            holder.feedbackViewListItemBinding.textAnsLinear.setVisibility(View.GONE);
            holder.feedbackViewListItemBinding.ratingAnsLinear.setVisibility(View.GONE);
            holder.feedbackViewListItemBinding.textgridAnsLinear.setVisibility(View.GONE);
            if (ansviewdetail.getAnswerValue() != null && !ansviewdetail.getAnswerValue().equalsIgnoreCase("")) {
                holder.feedbackViewListItemBinding.imageTextLayout.setVisibility(View.VISIBLE);
                if (feedbackansimageStr!=null && !feedbackansimageStr.equalsIgnoreCase("")){
                    Utils.setImageInImageView(feedbackansimageStr, holder.feedbackViewListItemBinding.feedbackImage, mContext);
                }
                holder.feedbackViewListItemBinding.textviewAnsTxt.setText(ansviewdetail.getAnswerValue());
            } else {
                holder.feedbackViewListItemBinding.imageTextLayout.setVisibility(View.GONE);
            }
        }
//        else if (ansviewdetail.getFeedbackType().equals(5)){
//            if (ansviewdetail.getAnswerValue() != null && !ansviewdetail.getAnswerValue().equalsIgnoreCase("")) {
//                holder.feedbackViewListItemBinding.textAnsLinear.setVisibility(View.VISIBLE);
//                holder.feedbackViewListItemBinding.ansTxt.setText(ansviewdetail.getAnswerValue());
//            } else {
//                holder.feedbackViewListItemBinding.textAnsLinear.setVisibility(View.GONE);
//            }
//            holder.feedbackViewListItemBinding.ratingAnsLinear.setVisibility(View.GONE);
//            holder.feedbackViewListItemBinding.imageTextLayout.setVisibility(View.GONE);
//            holder.feedbackViewListItemBinding.textgridAnsLinear.setVisibility(View.GONE);
//        }else if (ansviewdetail.getFeedbackType().equals(6)){
//            if (ansviewdetail.getAnswerValue() != null && !ansviewdetail.getAnswerValue().equalsIgnoreCase("")) {
//                holder.feedbackViewListItemBinding.textAnsLinear.setVisibility(View.VISIBLE);
//                holder.feedbackViewListItemBinding.ansTxt.setText(ansviewdetail.getAnswerValue());
//            } else {
//                holder.feedbackViewListItemBinding.textAnsLinear.setVisibility(View.GONE);
//            }
//            holder.feedbackViewListItemBinding.ratingAnsLinear.setVisibility(View.GONE);
//            holder.feedbackViewListItemBinding.imageTextLayout.setVisibility(View.GONE);
//            holder.feedbackViewListItemBinding.textgridAnsLinear.setVisibility(View.GONE);
//        }else if (ansviewdetail.getFeedbackType().equals(2)){
//            if (ansviewdetail.getAnswerValue() != null && !ansviewdetail.getAnswerValue().equalsIgnoreCase("")) {
//                holder.feedbackViewListItemBinding.textAnsLinear.setVisibility(View.VISIBLE);
//                holder.feedbackViewListItemBinding.ansTxt.setText(ansviewdetail.getAnswerValue());
//            } else {
//                holder.feedbackViewListItemBinding.textAnsLinear.setVisibility(View.GONE);
//            }
//            holder.feedbackViewListItemBinding.ratingAnsLinear.setVisibility(View.GONE);
//            holder.feedbackViewListItemBinding.imageTextLayout.setVisibility(View.GONE);
//            holder.feedbackViewListItemBinding.textgridAnsLinear.setVisibility(View.GONE);
//        }



        /*rating ans display rating*/
        if (ansviewdetail.getAnswerValue().equalsIgnoreCase("5")) {
            holder.feedbackViewListItemBinding.start1Img.setVisibility(View.VISIBLE);
            holder.feedbackViewListItemBinding.start2Img.setVisibility(View.VISIBLE);
            holder.feedbackViewListItemBinding.start3Img.setVisibility(View.VISIBLE);
            holder.feedbackViewListItemBinding.start4Img.setVisibility(View.VISIBLE);
            holder.feedbackViewListItemBinding.start5Img.setVisibility(View.VISIBLE);
        } else if (ansviewdetail.getAnswerValue().equalsIgnoreCase("4")) {
            holder.feedbackViewListItemBinding.start1Img.setVisibility(View.VISIBLE);
            holder.feedbackViewListItemBinding.start2Img.setVisibility(View.VISIBLE);
            holder.feedbackViewListItemBinding.start3Img.setVisibility(View.VISIBLE);
            holder.feedbackViewListItemBinding.start4Img.setVisibility(View.VISIBLE);
            holder.feedbackViewListItemBinding.start5Img.setVisibility(View.GONE);
        } else if (ansviewdetail.getAnswerValue().equalsIgnoreCase("3")) {
            holder.feedbackViewListItemBinding.start1Img.setVisibility(View.VISIBLE);
            holder.feedbackViewListItemBinding.start2Img.setVisibility(View.VISIBLE);
            holder.feedbackViewListItemBinding.start3Img.setVisibility(View.VISIBLE);
            holder.feedbackViewListItemBinding.start4Img.setVisibility(View.GONE);
            holder.feedbackViewListItemBinding.start5Img.setVisibility(View.GONE);
        } else if (ansviewdetail.getAnswerValue().equalsIgnoreCase("2")) {
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


