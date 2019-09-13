package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Activity.FTPDetailsActivity;
import com.bharatarmy.Models.UpcommingDashboardModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.SignupListItemBinding;
import com.bharatarmy.databinding.UpcomingTournamentListNewBinding;

import java.util.ArrayList;
import java.util.List;

public class SignupAdapter extends RecyclerView.Adapter<SignupAdapter.MyViewHolder> {
    Context mcontext;
    ArrayList<String> list;


    public SignupAdapter(Context mContext, ArrayList<String> list) {
        this.mcontext = mContext;
        this.list = list;


    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        SignupListItemBinding signupListItemBinding;


        public MyViewHolder(SignupListItemBinding signupListItemBinding) {
            super(signupListItemBinding.getRoot());

            this.signupListItemBinding=signupListItemBinding;

        }
    }


    @Override
    public SignupAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SignupListItemBinding signupListItemBinding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.signup_list_item,parent,false);
        return new SignupAdapter.MyViewHolder(signupListItemBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(SignupAdapter.MyViewHolder holder, int position) {


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
        return list.size();
    }
}

