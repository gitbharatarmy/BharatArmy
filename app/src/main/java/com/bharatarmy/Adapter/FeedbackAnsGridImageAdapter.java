package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Models.FeedbackModel;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.FeedbackAnsGridImageListItemBinding;
import com.bharatarmy.databinding.FeedbackAnsListItemBinding;

import java.util.ArrayList;

public class FeedbackAnsGridImageAdapter extends RecyclerView.Adapter<FeedbackAnsGridImageAdapter.MyViewHolder> {
    Context mcontext;
    ArrayList<FeedbackModel> feedbackanslist;
    int selectedroomforchangesposition = -1;

    public FeedbackAnsGridImageAdapter(Context mContext, ArrayList<FeedbackModel> feedbackanslist) {
        this.mcontext = mContext;
        this.feedbackanslist = feedbackanslist;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        FeedbackAnsGridImageListItemBinding feedbackAnsGridImageListItemBinding;

        public MyViewHolder(FeedbackAnsGridImageListItemBinding feedbackAnsGridImageListItemBinding) {
            super(feedbackAnsGridImageListItemBinding.getRoot());

            this.feedbackAnsGridImageListItemBinding = feedbackAnsGridImageListItemBinding;

        }
    }


    @Override
    public FeedbackAnsGridImageAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FeedbackAnsGridImageListItemBinding feedbackAnsGridImageListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.feedback_ans_grid_image_list_item, parent, false);
        return new FeedbackAnsGridImageAdapter.MyViewHolder(feedbackAnsGridImageListItemBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(FeedbackAnsGridImageAdapter.MyViewHolder holder, int position) {
        FeedbackModel ansdetail = feedbackanslist.get(position);

        if (selectedroomforchangesposition == position) {
            holder.feedbackAnsGridImageListItemBinding.question3Option1Chk.setChecked(true);
            holder.feedbackAnsGridImageListItemBinding.question3Option1Btn.setBackground(mcontext.getResources().getDrawable(R.drawable.feedback_corner_select_shape));
            AppConfiguration.imagechoice = "fill";
        } else {
            holder.feedbackAnsGridImageListItemBinding.question3Option1Chk.setChecked(false);
            holder.feedbackAnsGridImageListItemBinding.question3Option1Btn.setBackground(mcontext.getResources().getDrawable(R.drawable.feedback_corner_shape));

        }

        if (!ansdetail.getFeedbackIconSize().equalsIgnoreCase("512*512")) {
            RelativeLayout.LayoutParams shadowParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            holder.feedbackAnsGridImageListItemBinding.feedbackImage.setLayoutParams(shadowParams);
            holder.feedbackAnsGridImageListItemBinding.feedbackImage.setScaleType(ImageView.ScaleType.FIT_XY);
        }


//        Utils.setImageInImageView(ansdetail.getCityHotelAmenitiesImage(), holder.feedbackAnsGridImageListItemBinding.feedbackImage, mcontext);
        holder.feedbackAnsGridImageListItemBinding.feedbackImage.setImageResource(ansdetail.getFeedbackIconImage());
        if (position == 3) {
            holder.feedbackAnsGridImageListItemBinding.feedbackImage.setColorFilter(mcontext.getResources().getColor(R.color.red));
        }


        holder.feedbackAnsGridImageListItemBinding.question3Option1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ansdetail.setFeedbackOptionselectedStr("1");
                selectedroomforchangesposition = position;
                notifyDataSetChanged();

            }
        });

        holder.feedbackAnsGridImageListItemBinding.question3Option1Chk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ansdetail.setFeedbackOptionselectedStr("1");
                selectedroomforchangesposition = position;
                notifyDataSetChanged();
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



