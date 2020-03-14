package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Models.FeedbackAnswerList;
import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.databinding.FeedbackTextSinglechoiceGridListItemBinding;

import java.util.List;

public class FeedbackTextSingleChoiceGridAdapter extends RecyclerView.Adapter<FeedbackTextSingleChoiceGridAdapter.MyViewHolder> {
    Context mContext;
    List<FeedbackAnswerList> feedbackansimagetextlist;
    int selectedroomforchangesposition = -1;




    public FeedbackTextSingleChoiceGridAdapter(Context mContext, List<FeedbackAnswerList> feedbackansimagetextlist) {
        this.mContext=mContext;
        this.feedbackansimagetextlist=feedbackansimagetextlist;
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
        FeedbackAnswerList detail = feedbackansimagetextlist.get(position);

        holder.feedbackTextSinglechoiceGridListItemBinding.textviewAnsTxt.setText(detail.getQuestionAnswerText());

        if (selectedroomforchangesposition == position) {
            holder.feedbackTextSinglechoiceGridListItemBinding.optionChk.setChecked(true);
            holder.feedbackTextSinglechoiceGridListItemBinding.textSinglechoiceLinear.setBackground(mContext.getResources().getDrawable(R.drawable.feedback_corner_select_shape));

            AppConfiguration.singlechoice = "fill";
        } else {
//            ansdetail.setCityHotelAmenitiesName("0");
            holder.feedbackTextSinglechoiceGridListItemBinding.optionChk.setChecked(false);
            holder.feedbackTextSinglechoiceGridListItemBinding.textSinglechoiceLinear.setBackground(mContext.getResources().getDrawable(R.drawable.feedback_corner_shape));


        }


        holder.feedbackTextSinglechoiceGridListItemBinding.textSinglechoiceLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ansdetail.setCityHotelAmenitiesName("1");
                selectedroomforchangesposition = position;
                notifyDataSetChanged();


            }
        });

        holder.feedbackTextSinglechoiceGridListItemBinding.textSinglechoiceLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ansdetail.setCityHotelAmenitiesName("1");
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
        return feedbackansimagetextlist.size();
    }


}



