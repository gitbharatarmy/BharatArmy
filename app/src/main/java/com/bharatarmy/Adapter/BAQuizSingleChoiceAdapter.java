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
import com.bharatarmy.R;
import com.bharatarmy.databinding.BaPollTextsingleChoiceAnsListItemBinding;
import com.bharatarmy.databinding.BaQuizTextsingleChoiceListItemBinding;

import java.util.List;

public class BAQuizSingleChoiceAdapter extends RecyclerView.Adapter<BAQuizSingleChoiceAdapter.MyViewHolder> {
    Context mcontext;
    List<BAPollDatum> bapolltextsinglechoicelist;
    int selectedchangesposition = -1;
    MorestoryClick morestoryClick;

    public BAQuizSingleChoiceAdapter(Context mContext, List<BAPollDatum> bapolltextsinglechoicelist, MorestoryClick morestoryClick) {
        this.mcontext = mContext;
        this.bapolltextsinglechoicelist = bapolltextsinglechoicelist;
        this.morestoryClick = morestoryClick;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        BaQuizTextsingleChoiceListItemBinding baQuizTextsingleChoiceListItemBinding;

        public MyViewHolder(BaQuizTextsingleChoiceListItemBinding baQuizTextsingleChoiceListItemBinding) {
            super(baQuizTextsingleChoiceListItemBinding.getRoot());

            this.baQuizTextsingleChoiceListItemBinding = baQuizTextsingleChoiceListItemBinding;

        }
    }


    @Override
    public BAQuizSingleChoiceAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaQuizTextsingleChoiceListItemBinding baQuizTextsingleChoiceListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.ba_quiz_textsingle_choice_list_item, parent, false);
        return new BAQuizSingleChoiceAdapter.MyViewHolder(baQuizTextsingleChoiceListItemBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(BAQuizSingleChoiceAdapter.MyViewHolder holder, int position) {
        BAPollDatum detail = bapolltextsinglechoicelist.get(position);

        if (selectedchangesposition == position) {
            holder.baQuizTextsingleChoiceListItemBinding.optionChk.setChecked(true);
            detail.setbAquizQuestionAnswerSelected("1");
            morestoryClick.getmorestoryClick();
        } else {
            holder.baQuizTextsingleChoiceListItemBinding.optionChk.setChecked(false);
            detail.setbAquizQuestionAnswerSelected("0");
        }


        holder.baQuizTextsingleChoiceListItemBinding.baQuizTextviewAnsTxt.setText(detail.getBAQuizQuestionAnswer());


        holder.baQuizTextsingleChoiceListItemBinding.baQuizHeaderBannerRcv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.baQuizTextsingleChoiceListItemBinding.headerLinear.isShown()) {
//                    holder.baQuizTextsingleChoiceListItemBinding.headerLinear.setVisibility(View.GONE);
                    holder.baQuizTextsingleChoiceListItemBinding.optionChk.setChecked(false);
                    detail.setbAquizQuestionAnswerSelected("0");
                    selectedchangesposition = position;
                    notifyDataSetChanged();
                } else {
//                    holder.baQuizTextsingleChoiceListItemBinding.headerLinear.setVisibility(View.VISIBLE);
                    holder.baQuizTextsingleChoiceListItemBinding.optionChk.setChecked(true);
                    detail.setbAquizQuestionAnswerSelected("1");
                    selectedchangesposition = position;
                    notifyDataSetChanged();

                }

            }
        });

        holder.baQuizTextsingleChoiceListItemBinding.optionChk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.baQuizTextsingleChoiceListItemBinding.optionChk.isChecked()){
                    detail.setbAquizQuestionAnswerSelected("1");
                    selectedchangesposition = position;
                    notifyDataSetChanged();
                }else{
                    detail.setbAquizQuestionAnswerSelected("0");
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


