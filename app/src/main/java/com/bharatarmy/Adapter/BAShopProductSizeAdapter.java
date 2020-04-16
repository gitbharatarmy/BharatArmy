package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Models.BAShopProductSpecification;
import com.bharatarmy.R;
import com.bharatarmy.databinding.BaShopProductSizeListItemBinding;


import java.util.List;

public class BAShopProductSizeAdapter extends RecyclerView.Adapter<BAShopProductSizeAdapter.MyViewHolder> {
    Context mContext;
    List<BAShopProductSpecification> productsizeList;
    int selectedchangesposition = -1;

    public BAShopProductSizeAdapter(Context mContext, List<BAShopProductSpecification> productsizeList) {
        this.mContext = mContext;
        this.productsizeList = productsizeList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        BaShopProductSizeListItemBinding baShopProductSizeListItemBinding;

        public MyViewHolder(BaShopProductSizeListItemBinding baShopProductSizeListItemBinding) {
            super(baShopProductSizeListItemBinding.getRoot());

            this.baShopProductSizeListItemBinding = baShopProductSizeListItemBinding;

        }
    }


    @Override
    public BAShopProductSizeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaShopProductSizeListItemBinding baShopProductSizeListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.ba_shop_product_size_list_item, parent, false);
        return new BAShopProductSizeAdapter.MyViewHolder(baShopProductSizeListItemBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override

    public void onBindViewHolder(BAShopProductSizeAdapter.MyViewHolder holder, int position) {
        BAShopProductSpecification sizedetail = productsizeList.get(position);

        if (selectedchangesposition == position) {
            holder.baShopProductSizeListItemBinding.headerLinear.setVisibility(View.VISIBLE);
            holder.baShopProductSizeListItemBinding.productSizeGridLinear.setBackground(mContext.getResources().getDrawable(R.drawable.select_reactangle_line));
            holder.baShopProductSizeListItemBinding.optionChk.setChecked(true);
            sizedetail.setSizeSelectionStr("1");

        } else {
            holder.baShopProductSizeListItemBinding.headerLinear.setVisibility(View.GONE);
            holder.baShopProductSizeListItemBinding.productSizeGridLinear.setBackground(mContext.getResources().getDrawable(R.drawable.shop_product_reactangle_shape));
            holder.baShopProductSizeListItemBinding.optionChk.setChecked(false);
            sizedetail.setSizeSelectionStr("0");
        }

        holder.baShopProductSizeListItemBinding.sizeTxt.setText(sizedetail.getSize());

        holder.baShopProductSizeListItemBinding.productSizeLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sizedetail.getSizeSelectionStr().equalsIgnoreCase("1")) {
                    selectedchangesposition = position;
                    notifyDataSetChanged();
                } else {
                    selectedchangesposition = position;
                    notifyDataSetChanged();
                }

//             if (holder.baShopProductSizeListItemBinding.optionChk.isChecked()){
//                 holder.baShopProductSizeListItemBinding.optionChk.setChecked(false);
//                 holder.baShopProductSizeListItemBinding.productSizeGridLinear.setBackground(mContext.getResources().getDrawable(R.drawable.rectangle_line));
//                 sizedetail.setSizeSelectionStr("0");
//                 selectedchangesposition = position;
//                 notifyDataSetChanged();
////                 morestoryClick.getmorestoryClick();
//             }else{
//                 holder.baShopProductSizeListItemBinding.optionChk.setChecked(true);
//                 holder.baShopProductSizeListItemBinding.productSizeGridLinear.setBackground(mContext.getResources().getDrawable(R.drawable.select_reactangle_line));
//                 sizedetail.setSizeSelectionStr("1");
//                 selectedchangesposition = position;
//                 notifyDataSetChanged();
////                 morestoryClick.getmorestoryClick();
//             }
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







