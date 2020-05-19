package com.bharatarmy.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Activity.BAQuizDetailActivity;
import com.bharatarmy.Models.QuizDetailModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.BaAllQuizListItemBinding;

import java.util.List;

public class BAAllQuizListAdapter extends RecyclerView.Adapter<BAAllQuizListAdapter.ItemViewHolder> {

    Context mContext;
    List<QuizDetailModel> recommendedquizList;


    public BAAllQuizListAdapter(Context mContext, List<QuizDetailModel> recommendedquizList) {
        this.mContext = mContext;
        this.recommendedquizList = recommendedquizList;
    }

    @NonNull
    @Override
    public BAAllQuizListAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        BaAllQuizListItemBinding baAllQuizListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.ba_all_quiz_list_item, parent, false);
        return new BAAllQuizListAdapter.ItemViewHolder(baAllQuizListItemBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull BAAllQuizListAdapter.ItemViewHolder viewHolder, int position) {

        final QuizDetailModel detail = recommendedquizList.get(position);
        Utils.setImageInImageView(detail.getThumbImageUrl(), viewHolder.baAllQuizListItemBinding.allQuizMainBannerImg, mContext);
        viewHolder.baAllQuizListItemBinding.allQuizMainTitleTxt.setText(detail.getQuizHeaderText());
        viewHolder.baAllQuizListItemBinding.allQuizDateTxt.setText(detail.getStrDisplayDate());
        viewHolder.baAllQuizListItemBinding.allQuizTypeTxt.setText(detail.getBACategoryName());


        viewHolder.baAllQuizListItemBinding.cardClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent baquizIntent=new Intent(mContext, BAQuizDetailActivity.class);
                baquizIntent.putExtra("quiztitle",detail.getQuizHeaderText());
                baquizIntent.putExtra("quizid",detail.getBAQuizId());
                baquizIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(baquizIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recommendedquizList.size();
    }

    /**
     * The following method decides the type of ViewHolder to display in the RecyclerView
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return position;

    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        BaAllQuizListItemBinding baAllQuizListItemBinding;

        public ItemViewHolder(@NonNull BaAllQuizListItemBinding baAllQuizListItemBinding) {
            super(baAllQuizListItemBinding.getRoot());
            this.baAllQuizListItemBinding = baAllQuizListItemBinding;

        }
    }


}


