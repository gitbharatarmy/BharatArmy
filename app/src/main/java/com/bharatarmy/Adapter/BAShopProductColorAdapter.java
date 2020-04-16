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
import com.bharatarmy.databinding.BaShopProductColorListItemBinding;
import com.bharatarmy.databinding.BaShopProductSizeListItemBinding;

import java.util.List;

public class BAShopProductColorAdapter extends RecyclerView.Adapter<BAShopProductColorAdapter.MyViewHolder> {
    Context mContext;
    List<BAShopProductSpecification> productsizeList;
    int selectedchangesposition = -1;

    public BAShopProductColorAdapter(Context mContext, List<BAShopProductSpecification> productsizeList) {
        this.mContext = mContext;
        this.productsizeList = productsizeList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        BaShopProductColorListItemBinding baShopProductColorListItemBinding;

        public MyViewHolder(BaShopProductColorListItemBinding baShopProductColorListItemBinding) {
            super(baShopProductColorListItemBinding.getRoot());

            this.baShopProductColorListItemBinding = baShopProductColorListItemBinding;

        }
    }


    @Override
    public BAShopProductColorAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaShopProductColorListItemBinding baShopProductColorListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.ba_shop_product_color_list_item, parent, false);
        return new BAShopProductColorAdapter.MyViewHolder(baShopProductColorListItemBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override

    public void onBindViewHolder(BAShopProductColorAdapter.MyViewHolder holder, int position) {
        BAShopProductSpecification colordetail = productsizeList.get(position);

        if (selectedchangesposition == position) {
            holder.baShopProductColorListItemBinding.headerLinear.setVisibility(View.VISIBLE);
            holder.baShopProductColorListItemBinding.productColorGridLinear.setBackground(mContext.getResources().getDrawable(R.drawable.shop_color_select_shape));
            holder.baShopProductColorListItemBinding.optionChk.setChecked(true);
            colordetail.setSizeSelectionStr("1");

        } else {
            holder.baShopProductColorListItemBinding.headerLinear.setVisibility(View.GONE);
            holder.baShopProductColorListItemBinding.productColorGridLinear.setBackground(mContext.getResources().getDrawable(R.drawable.shop_color_shape));
            holder.baShopProductColorListItemBinding.optionChk.setChecked(false);
            colordetail.setSizeSelectionStr("0");
        }

        holder.baShopProductColorListItemBinding.colorTxt.setBackgroundColor(Color.parseColor(colordetail.getBAShopProductColorCode()));
        holder.baShopProductColorListItemBinding.productColorLinear.setOnClickListener(new View.OnClickListener() {
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
