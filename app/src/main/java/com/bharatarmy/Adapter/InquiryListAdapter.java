package com.bharatarmy.Adapter;

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
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Interfaces.MorestoryClick;
import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.Models.MoreDetailDataModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.databinding.InquiryListItemBinding;
import java.util.List;

public class InquiryListAdapter extends RecyclerView.Adapter<InquiryListAdapter.MyViewHolder> {
    Context mContext;
    List<MoreDetailDataModel> moreDetailDataModelList;
    MorestoryClick morestoryClick;
    image_click image_click;


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



    @NonNull
    @Override
    public InquiryListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            InquiryListItemBinding inquiryListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.inquiry_list_item, parent, false);
            return new InquiryListAdapter.MyViewHolder(inquiryListItemBinding);

    }
    @Override
    public void onBindViewHolder(@NonNull InquiryListAdapter.MyViewHolder holder, int position) {


            MoreDetailDataModel detailDataModel = moreDetailDataModelList.get(position);

     holder.inquiryListItemBinding.inquirytypeTxt.setText(detailDataModel.getStrInquiryTypePrefix());
         holder.inquiryListItemBinding.inquiryuserNametxt.setText(detailDataModel.getCustomerName());
             holder.inquiryListItemBinding.userEmailtxt.setText(detailDataModel.getCustomerEmail());
           holder.inquiryListItemBinding.userNotxt.setText(detailDataModel.getCustomerPhoneNo());
          holder.inquiryListItemBinding.inquirystatusTxt.setText(detailDataModel.getStrOrderStatus());

            GradientDrawable bgShape = (GradientDrawable)holder.inquiryListItemBinding.inquirytypeLinear.getBackground();
            bgShape.setColor(Color.parseColor(detailDataModel.getStrInquiryTypeColor()));

            holder.inquiryListItemBinding.inquirytypeTxt.setTextColor(Color.parseColor(detailDataModel.getStrInquiryTypeFontColor()));

            holder.inquiryListItemBinding.inquiryCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppConfiguration.inquiryId = String.valueOf(detailDataModel.getOrderId());
                    morestoryClick.getmorestoryClick();
                }
            });
            holder.inquiryListItemBinding.settingLinear.setOnClickListener(new View.OnClickListener() {
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

    }

        @Override
        public int getItemViewType ( int position){
            return position;
        }


        @Override

        public int getItemCount () {
            return moreDetailDataModelList.size();
        }

        public void addMoreDataToList (List<MoreDetailDataModel> result) {
            moreDetailDataModelList.addAll(result);
            notifyDataSetChanged();
        }


    }


