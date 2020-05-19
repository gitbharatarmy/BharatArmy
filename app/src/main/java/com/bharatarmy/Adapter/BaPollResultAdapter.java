package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Models.BAPollDatum;
import com.bharatarmy.R;
import com.bharatarmy.databinding.BaPollResultListItemBinding;

import java.util.List;

public class BaPollResultAdapter extends RecyclerView.Adapter<BaPollResultAdapter.MyViewHolder> {
    Context mcontext;
    List<BAPollDatum> bapolltextsinglechoicelist;
    int selectedchangesposition = -1;


    public BaPollResultAdapter(Context mContext, List<BAPollDatum> bapolltextsinglechoicelist) {
        this.mcontext = mContext;
        this.bapolltextsinglechoicelist = bapolltextsinglechoicelist;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        BaPollResultListItemBinding baPollResultListItemBinding;

        public MyViewHolder(BaPollResultListItemBinding baPollResultListItemBinding) {
            super(baPollResultListItemBinding.getRoot());

            this.baPollResultListItemBinding = baPollResultListItemBinding;

        }
    }


    @Override
    public BaPollResultAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaPollResultListItemBinding baPollResultListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.ba_poll_result_list_item, parent, false);
        return new BaPollResultAdapter.MyViewHolder(baPollResultListItemBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(BaPollResultAdapter.MyViewHolder holder, int position) {
        BAPollDatum detail = bapolltextsinglechoicelist.get(position);

        holder.baPollResultListItemBinding.voteAnserText.setText(detail.getBAPollQuestionAnswer());
        holder.baPollResultListItemBinding.votePercentCountTxt.setText(detail.getBAPollQuestionAnswerVote() + "%");
        holder.baPollResultListItemBinding.progressBar.setProgress(Integer.parseInt(detail.getBAPollQuestionAnswerVote()));

        if (!detail.getBAPollVoteCount().equals(1)){
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) holder.baPollResultListItemBinding.progressVoteImg.getLayoutParams();
            params.horizontalBias = Float.valueOf(String.valueOf(detail.getBAPollVoteCount())); // here is one modification for example. modify anything else you want :)
            holder.baPollResultListItemBinding.progressVoteImg.setLayoutParams(params); // request the view to use the new modified params

            ConstraintLayout.LayoutParams params1 = (ConstraintLayout.LayoutParams) holder.baPollResultListItemBinding.voteAnserText.getLayoutParams();
            params.horizontalWeight = Float.valueOf(String.valueOf(detail.getBAPollVoteCount()));// here is one modification for example. modify anything else you want :)
            holder.baPollResultListItemBinding.voteAnserText.setLayoutParams(params1); // request the view to use the new modified params
        }

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
        return bapolltextsinglechoicelist.size();
    }


}


