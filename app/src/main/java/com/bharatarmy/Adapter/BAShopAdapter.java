package com.bharatarmy.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Activity.BAShopDetailActivity;
import com.bharatarmy.Interfaces.MorestoryClick;
import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.Models.BAShopListModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.BaShopListItemBinding;


import java.util.ArrayList;
import java.util.List;

public class BAShopAdapter extends RecyclerView.Adapter<BAShopAdapter.ItemViewHolder> {

    List<BAShopListModel> baShopListModelsList;
    Context mContext;
    image_click imageClick;
    private ArrayList<String> dataCheck;
    int selectedposition, adapteraddcartposition;
    MorestoryClick morestoryClick;

    public BAShopAdapter(Context mContext, List<BAShopListModel> baShopListModelsList, image_click imageClick,
                         MorestoryClick morestoryClick) {
        this.mContext = mContext;
        this.baShopListModelsList = baShopListModelsList;
        this.imageClick = imageClick;
        this.morestoryClick=morestoryClick;
    }

    @NonNull
    @Override
    public BAShopAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        BaShopListItemBinding baShopListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.ba_shop_list_item, parent, false);
        return new BAShopAdapter.ItemViewHolder(baShopListItemBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull BAShopAdapter.ItemViewHolder viewHolder, int position) {

        final BAShopListModel detail = baShopListModelsList.get(position);

        Utils.setImageInImageView(detail.getBAShopProductImage(), viewHolder.baShopListItemBinding.shoplistProductImg, mContext);
        viewHolder.baShopListItemBinding.shoplistProductNameTxt.setText(detail.getBAShopProductName());
        viewHolder.baShopListItemBinding.tickethospitalitypriceTxt.priceTxt.setText(detail.getBAShopProductPrice());
        viewHolder.baShopListItemBinding.sshoplistProductDescriptionTxt.setText(detail.getBAShopProductDescription());

        viewHolder.baShopListItemBinding.shoplistProductImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent productdetailIntent = new Intent(mContext, BAShopDetailActivity.class);
                productdetailIntent.putExtra("selectedProductId",String.valueOf(detail.getBAShopListId()));
                productdetailIntent.putExtra("selectedProductName",detail.getBAShopProductName());
                productdetailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(productdetailIntent);
            }
        });
        viewHolder.baShopListItemBinding.shoplistProductNameTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent productdetailIntent = new Intent(mContext, BAShopDetailActivity.class);
                productdetailIntent.putExtra("selectedProductId",String.valueOf(detail.getBAShopListId()));
                productdetailIntent.putExtra("selectedProductName",detail.getBAShopProductName());
                productdetailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(productdetailIntent);
            }
        });
        viewHolder.baShopListItemBinding.sshoplistProductDescriptionTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent productdetailIntent = new Intent(mContext, BAShopDetailActivity.class);
                productdetailIntent.putExtra("selectedProductId",String.valueOf(detail.getBAShopListId()));
                productdetailIntent.putExtra("selectedProductName",detail.getBAShopProductName());
                productdetailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(productdetailIntent);
            }
        });
        viewHolder.baShopListItemBinding.addCartLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isMember(mContext,"BAShop")){
                    viewHolder.baShopListItemBinding.addCartLayout.setVisibility(View.GONE);
                    viewHolder.baShopListItemBinding.removeCartLayout.setVisibility(View.VISIBLE);
                    adapteraddcartposition = position;
                    morestoryClick.getmorestoryClick();
                }

            }
        });
        viewHolder.baShopListItemBinding.removeCartLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.baShopListItemBinding.addCartLayout.setVisibility(View.VISIBLE);
                viewHolder.baShopListItemBinding.removeCartLayout.setVisibility(View.GONE);
                imageClick.image_more_click();
            }
        });
    }

    @Override
    public int getItemCount() {
        return baShopListModelsList.size();
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

        BaShopListItemBinding baShopListItemBinding;

        public ItemViewHolder(@NonNull BaShopListItemBinding baShopListItemBinding) {
            super(baShopListItemBinding.getRoot());
            this.baShopListItemBinding = baShopListItemBinding;

        }
    }

    public ArrayList<String> getData() {
        return dataCheck;
    }
    public int adptercartAddPosition() {
        return adapteraddcartposition;
    }

}




