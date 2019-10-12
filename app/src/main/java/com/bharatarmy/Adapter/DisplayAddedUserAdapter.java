package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Activity.UserEntryActivity;
import com.bharatarmy.Models.ImageDetailModel;
import com.bharatarmy.R;
import com.bharatarmy.databinding.BharatArmyStoriesListNewBinding;
import com.bharatarmy.databinding.DisplayUserItemListBinding;

import java.util.List;

public class DisplayAddedUserAdapter extends RecyclerView.Adapter<DisplayAddedUserAdapter.MyViewHolder> {
    Context mcontext;
    List<ImageDetailModel> addedusermodellist;

    public DisplayAddedUserAdapter(Context mContext, List<ImageDetailModel> addedusermodellist) {
        this.mcontext = mContext;
        this.addedusermodellist = addedusermodellist;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        DisplayUserItemListBinding displayUserItemListBinding;

        public MyViewHolder(DisplayUserItemListBinding displayUserItemListBinding) {
            super(displayUserItemListBinding.getRoot());

            this.displayUserItemListBinding = displayUserItemListBinding;

        }
    }


    @Override
    public DisplayAddedUserAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        DisplayUserItemListBinding displayUserItemListBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.display_user_item_list, parent, false);
        return new DisplayAddedUserAdapter.MyViewHolder(displayUserItemListBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(DisplayAddedUserAdapter.MyViewHolder holder, int position) {
        ImageDetailModel detail = addedusermodellist.get(position);

        holder.displayUserItemListBinding.displayUserNameTxt.setText(detail.getMemberName());
        holder.displayUserItemListBinding.displayUserEmailTxt.setText(detail.getMemberEmail());

        holder.displayUserItemListBinding.userLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent userentry = new Intent(mcontext, UserEntryActivity.class);
                userentry.putExtra("userName",detail.getMemberName());
                userentry.putExtra("userEmail",detail.getMemberEmail());
                userentry.putExtra("userId",String.valueOf(detail.getBAMemberId()));
                userentry.putExtra("userEditorNew","edit");
                userentry.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mcontext.startActivity(userentry);
                ((Activity)mcontext).finish();
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
        return addedusermodellist.size();
    }


}


