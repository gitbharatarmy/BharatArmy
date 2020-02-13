package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.databinding.FeedbackRatingListItemBinding;
import com.bharatarmy.databinding.FeedbackSingleChoiceListItemBinding;

import java.util.ArrayList;

public class FeedbackRatingAdapter extends RecyclerView.Adapter<FeedbackRatingAdapter.MyViewHolder> {
    Context mcontext;
    ArrayList<TravelModel> feedbackratinglist;
    int selectedroomforchangesposition = -1;

    public FeedbackRatingAdapter(Context mContext, ArrayList<TravelModel> feedbackratinglist) {
        this.mcontext = mContext;
        this.feedbackratinglist = feedbackratinglist;
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
        TravelModel ansratingdetail = feedbackratinglist.get(position);

        holder.feedbackRatingListItemBinding.question3starOption1Txt.setText(ansratingdetail.getPopularcity_image());


        if (ansratingdetail.getPopularcity_image_count().equalsIgnoreCase("5")) {
            holder.feedbackRatingListItemBinding.start1Img.setVisibility(View.VISIBLE);
            holder.feedbackRatingListItemBinding.start2Img.setVisibility(View.VISIBLE);
            holder.feedbackRatingListItemBinding.start3Img.setVisibility(View.VISIBLE);
            holder.feedbackRatingListItemBinding.start4Img.setVisibility(View.VISIBLE);
            holder.feedbackRatingListItemBinding.start5Img.setVisibility(View.VISIBLE);
        }else if(ansratingdetail.getPopularcity_image_count().equalsIgnoreCase("4")){
            holder.feedbackRatingListItemBinding.start1Img.setVisibility(View.VISIBLE);
            holder.feedbackRatingListItemBinding.start2Img.setVisibility(View.VISIBLE);
            holder.feedbackRatingListItemBinding.start3Img.setVisibility(View.VISIBLE);
            holder.feedbackRatingListItemBinding.start4Img.setVisibility(View.VISIBLE);
            holder.feedbackRatingListItemBinding.start5Img.setVisibility(View.GONE);
        }else if(ansratingdetail.getPopularcity_image_count().equalsIgnoreCase("3")){
            holder.feedbackRatingListItemBinding.start1Img.setVisibility(View.VISIBLE);
            holder.feedbackRatingListItemBinding.start2Img.setVisibility(View.VISIBLE);
            holder.feedbackRatingListItemBinding.start3Img.setVisibility(View.VISIBLE);
            holder.feedbackRatingListItemBinding.start4Img.setVisibility(View.GONE);
            holder.feedbackRatingListItemBinding.start5Img.setVisibility(View.GONE);
        }else if(ansratingdetail.getPopularcity_image_count().equalsIgnoreCase("2")){
            holder.feedbackRatingListItemBinding.start1Img.setVisibility(View.VISIBLE);
            holder.feedbackRatingListItemBinding.start2Img.setVisibility(View.VISIBLE);
            holder.feedbackRatingListItemBinding.start3Img.setVisibility(View.GONE);
            holder.feedbackRatingListItemBinding.start4Img.setVisibility(View.GONE);
            holder.feedbackRatingListItemBinding.start5Img.setVisibility(View.GONE);
        }else{
            holder.feedbackRatingListItemBinding.ratingLinear.setVisibility(View.GONE);
        }


        if (selectedroomforchangesposition == position) {
            holder.feedbackRatingListItemBinding.question3starOption1Chk.setChecked(true);
            holder.feedbackRatingListItemBinding.question3Option1Btn.setBackground(mcontext.getResources().getDrawable(R.drawable.feedback_corner_select_shape));

            AppConfiguration.singlechoice = "fill";
        } else {
            holder.feedbackRatingListItemBinding.question3starOption1Chk.setChecked(false);
            holder.feedbackRatingListItemBinding.question3Option1Btn.setBackground(mcontext.getResources().getDrawable(R.drawable.feedback_corner_shape));


        }


        holder.feedbackRatingListItemBinding.question3Option1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ansratingdetail.setPopularcity_name("1");
                selectedroomforchangesposition = position;
                notifyDataSetChanged();

            }
        });

        holder.feedbackRatingListItemBinding.question3starOption1Chk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ansratingdetail.setPopularcity_name("1");
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
        return feedbackratinglist.size();
    }


}





