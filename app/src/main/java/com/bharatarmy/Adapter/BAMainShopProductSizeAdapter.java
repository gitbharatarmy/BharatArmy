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
import com.bharatarmy.databinding.BaMainShopProductSizeListItemBinding;
import com.bharatarmy.databinding.BaShopProductSizeListItemBinding;

import java.util.List;

public class BAMainShopProductSizeAdapter extends RecyclerView.Adapter<BAMainShopProductSizeAdapter.MyViewHolder> {
    Context mContext;
    List<BAShopProductSpecification> productsizeList;
    int selectedchangesposition = -1;

    public BAMainShopProductSizeAdapter(Context mContext, List<BAShopProductSpecification> productsizeList) {
        this.mContext = mContext;
        this.productsizeList = productsizeList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        BaMainShopProductSizeListItemBinding baMainShopProductSizeListItemBinding;

        public MyViewHolder(BaMainShopProductSizeListItemBinding baMainShopProductSizeListItemBinding) {
            super(baMainShopProductSizeListItemBinding.getRoot());

            this.baMainShopProductSizeListItemBinding = baMainShopProductSizeListItemBinding;

        }
    }


    @Override
    public BAMainShopProductSizeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaMainShopProductSizeListItemBinding baMainShopProductSizeListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.ba_main_shop_product_size_list_item, parent, false);
        return new BAMainShopProductSizeAdapter.MyViewHolder(baMainShopProductSizeListItemBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override

    public void onBindViewHolder(BAMainShopProductSizeAdapter.MyViewHolder holder, int position) {
        BAShopProductSpecification sizedetail = productsizeList.get(position);

        if (selectedchangesposition == position) {
            holder.baMainShopProductSizeListItemBinding.headerLinear.setVisibility(View.VISIBLE);
            holder.baMainShopProductSizeListItemBinding.productSizeGridLinear.setBackground(mContext.getResources().getDrawable(R.drawable.select_reactangle_line));
            holder.baMainShopProductSizeListItemBinding.optionChk.setChecked(true);
            sizedetail.setSizeSelectionStr("1");

        } else {
            holder.baMainShopProductSizeListItemBinding.headerLinear.setVisibility(View.GONE);
            holder.baMainShopProductSizeListItemBinding.productSizeGridLinear.setBackground(mContext.getResources().getDrawable(R.drawable.shop_product_reactangle_shape));
            holder.baMainShopProductSizeListItemBinding.optionChk.setChecked(false);
            sizedetail.setSizeSelectionStr("0");
        }

        holder.baMainShopProductSizeListItemBinding.sizeTxt.setText(sizedetail.getSize());

        holder.baMainShopProductSizeListItemBinding.productSizeLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sizedetail.getSizeSelectionStr().equalsIgnoreCase("1")) {
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








