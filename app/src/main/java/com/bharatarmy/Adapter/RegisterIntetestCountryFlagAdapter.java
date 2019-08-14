package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Models.RegisterIntrestFilterDataModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.MatchticketsItemBinding;
import com.bharatarmy.databinding.RegisterInterestCountryflagItemBinding;

import java.util.ArrayList;

public class RegisterIntetestCountryFlagAdapter extends RecyclerView.Adapter<RegisterIntetestCountryFlagAdapter.MyViewHolder> {
    Context mContext;
    RegisterIntrestFilterDataModel registerIntrestFilterDataModel;


    public RegisterIntetestCountryFlagAdapter(Context mContext, RegisterIntrestFilterDataModel registerIntrestFilterDataModel) {
        this.mContext=mContext;
        this.registerIntrestFilterDataModel=registerIntrestFilterDataModel;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        RegisterInterestCountryflagItemBinding registerInterestCountryflagItemBinding;

        public MyViewHolder(RegisterInterestCountryflagItemBinding registerInterestCountryflagItemBinding) {
            super(registerInterestCountryflagItemBinding.getRoot());

            this.registerInterestCountryflagItemBinding=registerInterestCountryflagItemBinding;

        }
    }


    @Override
    public RegisterIntetestCountryFlagAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RegisterInterestCountryflagItemBinding registerInterestCountryflagItemBinding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.register_interest_countryflag_item,parent,false);
        return new RegisterIntetestCountryFlagAdapter.MyViewHolder(registerInterestCountryflagItemBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override

    public void onBindViewHolder(RegisterIntetestCountryFlagAdapter.MyViewHolder holder, int position) {
        Utils.setImageInImageView(registerIntrestFilterDataModel.getCountries().get(position).getCountryFlagUrl(),
                ((MyViewHolder) holder).registerInterestCountryflagItemBinding.firstCountryflagImage, mContext);
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
        return registerIntrestFilterDataModel.getCountries().size();
    }


}






