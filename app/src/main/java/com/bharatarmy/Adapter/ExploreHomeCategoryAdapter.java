package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Activity.BAPollListActivity;
import com.bharatarmy.Activity.BAQuizListActivity;
import com.bharatarmy.Activity.BaPollActivity;
import com.bharatarmy.Models.ExpolringCategoryModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ExploreHomeCategoryListItemBinding;

import java.util.List;

public class ExploreHomeCategoryAdapter extends RecyclerView.Adapter<ExploreHomeCategoryAdapter.MyViewHolder> {
    Context mcontext;
    List<ExpolringCategoryModel> exploringcategorylist;

    public ExploreHomeCategoryAdapter(Context mContext, List<ExpolringCategoryModel> exploringcategorylist) {
        this.mcontext = mContext;
        this.exploringcategorylist = exploringcategorylist;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ExploreHomeCategoryListItemBinding exploreHomeCategoryListItemBinding;


        public MyViewHolder(ExploreHomeCategoryListItemBinding exploreHomeCategoryListItemBinding) {
            super(exploreHomeCategoryListItemBinding.getRoot());

            this.exploreHomeCategoryListItemBinding = exploreHomeCategoryListItemBinding;

        }
    }


    @Override
    public ExploreHomeCategoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ExploreHomeCategoryListItemBinding exploreHomeCategoryListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.explore_home_category_list_item, parent, false);
        return new ExploreHomeCategoryAdapter.MyViewHolder(exploreHomeCategoryListItemBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(ExploreHomeCategoryAdapter.MyViewHolder holder, int position) {
        final ExpolringCategoryModel exploringcategoryData = exploringcategorylist.get(position);
        holder.exploreHomeCategoryListItemBinding.exploreCategoryImg.setImageResource(exploringcategoryData.getExplorecategoryIcon());
        holder.exploreHomeCategoryListItemBinding.exploreCategoryTxt.setText(exploringcategoryData.getExplorecategoryName());
        holder.exploreHomeCategoryListItemBinding.expolreCategoryLinear.setBackgroundColor(exploringcategoryData.getExpolrecategoryBgColor());
        holder.exploreHomeCategoryListItemBinding.exploreCategorycountTxt.setText(exploringcategoryData.getExpolrecategorycount());

        holder.exploreHomeCategoryListItemBinding.expolreCategoryCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isMember(mcontext, "Home")) {
                    if (exploringcategoryData.getExplorecategoryName().equalsIgnoreCase("BA Poll")) {
                        Intent bapollIntent = new Intent(mcontext, BAPollListActivity.class);
                        bapollIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mcontext.startActivity(bapollIntent);
                    } else if (exploringcategoryData.getExplorecategoryName().equalsIgnoreCase("BA Quiz")) {
                        Intent baquizIntent = new Intent(mcontext, BAQuizListActivity.class);
                        baquizIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mcontext.startActivity(baquizIntent);
                    }
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
        return exploringcategorylist.size();
    }
}


