package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Models.BAShopProductSpecification;
import com.bharatarmy.R;
import com.bharatarmy.databinding.BaMainShopProductColorListItemBinding;
import com.bharatarmy.databinding.BaShopProductColorListItemBinding;

import java.util.List;

public class BAMainShopProductColorAdapter extends RecyclerView.Adapter<BAMainShopProductColorAdapter.MyViewHolder> {
    Context mContext;
    List<BAShopProductSpecification> productsizeList;
    int selectedchangesposition = -1;

    public BAMainShopProductColorAdapter(Context mContext, List<BAShopProductSpecification> productsizeList) {
        this.mContext = mContext;
        this.productsizeList = productsizeList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        BaMainShopProductColorListItemBinding baMainShopProductColorListItemBinding;

        public MyViewHolder(BaMainShopProductColorListItemBinding baMainShopProductColorListItemBinding) {
            super(baMainShopProductColorListItemBinding.getRoot());

            this.baMainShopProductColorListItemBinding = baMainShopProductColorListItemBinding;

        }
    }


    @Override
    public BAMainShopProductColorAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaMainShopProductColorListItemBinding baMainShopProductColorListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.ba_main_shop_product_color_list_item, parent, false);
        return new BAMainShopProductColorAdapter.MyViewHolder(baMainShopProductColorListItemBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override

    public void onBindViewHolder(BAMainShopProductColorAdapter.MyViewHolder holder, int position) {
        BAShopProductSpecification colordetail = productsizeList.get(position);

        if (selectedchangesposition == position) {
            holder.baMainShopProductColorListItemBinding.headerLinear.setVisibility(View.VISIBLE);
            holder.baMainShopProductColorListItemBinding.productColorGridLinear.setBackground(mContext.getResources().getDrawable(R.drawable.shop_color_select_shape));
            holder.baMainShopProductColorListItemBinding.optionChk.setChecked(true);
            colordetail.setSizeSelectionStr("1");

        } else {
            holder.baMainShopProductColorListItemBinding.headerLinear.setVisibility(View.GONE);
            holder.baMainShopProductColorListItemBinding.productColorGridLinear.setBackground(mContext.getResources().getDrawable(R.drawable.shop_color_shape));
            holder.baMainShopProductColorListItemBinding.optionChk.setChecked(false);
            colordetail.setSizeSelectionStr("0");
        }

        holder.baMainShopProductColorListItemBinding.colorTxt.setBackgroundColor(Color.parseColor(colordetail.getBAShopProductColorCode()));
        holder.baMainShopProductColorListItemBinding.productColorLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (colordetail.getSizeSelectionStr().equalsIgnoreCase("1")) {
                    selectedchangesposition = position;
                    notifyDataSetChanged();
                } else {
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
        return productsizeList.size();
    }


}

