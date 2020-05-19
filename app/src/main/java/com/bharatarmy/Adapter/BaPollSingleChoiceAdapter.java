package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Interfaces.MorestoryClick;
import com.bharatarmy.Models.BAPollDatum;
import com.bharatarmy.Models.FeedbackAnswerList;
import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.BaPollTextsingleChoiceAnsListItemBinding;
import com.bharatarmy.databinding.FeedbackImageWithTextChoiceListItemBinding;

import java.util.List;

public class BaPollSingleChoiceAdapter extends RecyclerView.Adapter<BaPollSingleChoiceAdapter.MyViewHolder> {
    Context mcontext;
    List<BAPollDatum> bapolltextsinglechoicelist;
    int selectedchangesposition = -1;


    public BaPollSingleChoiceAdapter(Context mContext, List<BAPollDatum> bapolltextsinglechoicelist) {
        this.mcontext = mContext;
        this.bapolltextsinglechoicelist=bapolltextsinglechoicelist;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        BaPollTextsingleChoiceAnsListItemBinding baPollTextsingleChoiceAnsListItemBinding;

        public MyViewHolder(BaPollTextsingleChoiceAnsListItemBinding baPollTextsingleChoiceAnsListItemBinding) {
            super(baPollTextsingleChoiceAnsListItemBinding.getRoot());

            this.baPollTextsingleChoiceAnsListItemBinding = baPollTextsingleChoiceAnsListItemBinding;

        }
    }


    @Override
    public BaPollSingleChoiceAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaPollTextsingleChoiceAnsListItemBinding baPollTextsingleChoiceAnsListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.ba_poll_textsingle_choice_ans_list_item, parent, false);
        return new BaPollSingleChoiceAdapter.MyViewHolder(baPollTextsingleChoiceAnsListItemBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(BaPollSingleChoiceAdapter.MyViewHolder holder, int position) {
        BAPollDatum detail = bapolltextsinglechoicelist.get(position);

        if (selectedchangesposition == position) {
            holder.baPollTextsingleChoiceAnsListItemBinding.optionChk.setChecked(true);
            holder.baPollTextsingleChoiceAnsListItemBinding.headerLinear.setVisibility(View.VISIBLE);
            detail.setbAPollQuestionAnswerSelected("1");
        } else {
            holder.baPollTextsingleChoiceAnsListItemBinding.optionChk.setChecked(false);
            holder.baPollTextsingleChoiceAnsListItemBinding.headerLinear.setVisibility(View.GONE);
            detail.setbAPollQuestionAnswerSelected("0");
        }


        holder.baPollTextsingleChoiceAnsListItemBinding.baPollTextviewAnsTxt.setText(detail.getBAPollQuestionAnswer());


        holder.baPollTextsingleChoiceAnsListItemBinding.baPollHeaderBannerRcv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.baPollTextsingleChoiceAnsListItemBinding.headerLinear.isShown()){
                    holder.baPollTextsingleChoiceAnsListItemBinding.headerLinear.setVisibility(View.GONE);
                    holder.baPollTextsingleChoiceAnsListItemBinding.optionChk.setChecked(false);
                    detail.setbAPollQuestionAnswerSelected("0");
                    selectedchangesposition = position;
                    notifyDataSetChanged();
                }else{
                    holder.baPollTextsingleChoiceAnsListItemBinding.headerLinear.setVisibility(View.VISIBLE);
                    holder.baPollTextsingleChoiceAnsListItemBinding.optionChk.setChecked(true);
                    detail.setbAPollQuestionAnswerSelected("1");
                    selectedchangesposition = position;
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
        return bapolltextsinglechoicelist.size();
    }


}


