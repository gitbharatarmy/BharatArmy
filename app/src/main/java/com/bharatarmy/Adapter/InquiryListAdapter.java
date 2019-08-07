package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Activity.InquriyActivity;
import com.bharatarmy.Fragment.InquiryChildInformationFragment;
import com.bharatarmy.Interfaces.MorestoryClick;
import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.Models.MoreDetailDataModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.databinding.InquiryListItemBinding;
import com.bharatarmy.databinding.ItemLoadingBinding;
import com.bharatarmy.databinding.StoryItemListBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

public class InquiryListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context mContext;
    List<MoreDetailDataModel> moreDetailDataModelList;
    BottomSheetDialogFragment bottomSheetDialogFragment, bottomSheet1DialogFragment;
    MorestoryClick morestoryClick;
    image_click image_click;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    public InquiryListAdapter(Context mContext, List<MoreDetailDataModel> moreDetailDataModelList, image_click image_click, MorestoryClick morestoryClick) {
        this.mContext = mContext;
        this.moreDetailDataModelList = moreDetailDataModelList;
        this.morestoryClick = morestoryClick;
        this.image_click = image_click;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        InquiryListItemBinding inquiryListItemBinding;

        public MyViewHolder(InquiryListItemBinding inquiryListItemBinding) {
            super(inquiryListItemBinding.getRoot());
            this.inquiryListItemBinding = inquiryListItemBinding;
        }
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {

        ItemLoadingBinding itemLoadingBinding;
        public LoadingViewHolder(@NonNull ItemLoadingBinding itemLoadingBinding) {
            super(itemLoadingBinding.getRoot());

            this.itemLoadingBinding=itemLoadingBinding;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            InquiryListItemBinding inquiryListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.inquiry_list_item, parent, false);
            return new InquiryListAdapter.MyViewHolder(inquiryListItemBinding);
        } else {
            ItemLoadingBinding itemLoadingBinding=DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.item_loading,parent,false);
            return new InquiryListAdapter.LoadingViewHolder(itemLoadingBinding);
        }
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof MyViewHolder) {
            MoreDetailDataModel detailDataModel = moreDetailDataModelList.get(position);

         ((MyViewHolder) holder).inquiryListItemBinding.inquirytypeTxt.setText(detailDataModel.getStrInquiryTypePrefix());
            ((MyViewHolder) holder).inquiryListItemBinding.inquiryuserNametxt.setText(detailDataModel.getCustomerName());
            ((MyViewHolder) holder).inquiryListItemBinding.userEmailtxt.setText(detailDataModel.getCustomerEmail());
            ((MyViewHolder) holder).inquiryListItemBinding.userNotxt.setText(detailDataModel.getCustomerPhoneNo());
            ((MyViewHolder) holder).inquiryListItemBinding.inquirystatusTxt.setText(detailDataModel.getStrOrderStatus());

            GradientDrawable bgShape = (GradientDrawable)((MyViewHolder) holder).inquiryListItemBinding.inquirytypeLinear.getBackground();
            bgShape.setColor(Color.parseColor(detailDataModel.getStrInquiryTypeColor()));

            ((MyViewHolder) holder).inquiryListItemBinding.inquirytypeTxt.setTextColor(Color.parseColor(detailDataModel.getStrInquiryTypeFontColor()));

            ((MyViewHolder) holder).inquiryListItemBinding.inquiryCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppConfiguration.inquiryId = String.valueOf(detailDataModel.getOrderId());
                    morestoryClick.getmorestoryClick();
                }
            });
            ((MyViewHolder) holder).inquiryListItemBinding.settingLinear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppConfiguration.inquiryId = String.valueOf(detailDataModel.getOrderId());
                    PopupMenu popup = new PopupMenu(mContext, v, Gravity.END,0,R.style.MyPopupMenu);
                    MenuInflater inflater = popup.getMenuInflater();
                    inflater.inflate(R.menu.inquiry_item_menu, popup.getMenu());

                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.view_item:
                                    AppConfiguration.inquiryId = String.valueOf(detailDataModel.getOrderId());
                                    morestoryClick.getmorestoryClick();
                                    return true;
                                case R.id.assign_item:
                                    image_click.image_more_click();

                                    return true;
                                default:
                                    return false;
                            }
                        }
                    });
                    popup.show();

                }
            });
        } else if (holder instanceof LoadingViewHolder) {

        }

    }
    public void onBindViewHolder(InquiryListAdapter.MyViewHolder holder, int position) {

        }



        @Override
        public int getItemViewType ( int position){
            return moreDetailDataModelList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
        }


        @Override

        public int getItemCount () {
            return moreDetailDataModelList == null ? 0 : moreDetailDataModelList.size();
        }

        public void addMoreDataToList (List<MoreDetailDataModel> result) {
            moreDetailDataModelList.addAll(result);
            notifyDataSetChanged();
        }


    }


