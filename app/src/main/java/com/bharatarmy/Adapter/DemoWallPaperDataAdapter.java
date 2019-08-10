package com.bharatarmy.Adapter;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.PopupMenu;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Interfaces.OnLoadMoreListener;
import com.bharatarmy.Models.MoreDetailDataModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.databinding.InquiryListItemBinding;
import com.bharatarmy.databinding.ItemLoadingBinding;

import java.util.List;

public class DemoWallPaperDataAdapter extends RecyclerView.Adapter {
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    private static List<MoreDetailDataModel> imagesList;

    // The minimum amount of items to have below your current scroll position
    // before loading more.
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private OnLoadMoreListener onLoadMoreListener;


    public DemoWallPaperDataAdapter(List<MoreDetailDataModel> imagesList1, RecyclerView recyclerView) {
        imagesList = imagesList1;

        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {

            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView
                    .getLayoutManager();


            recyclerView
                    .addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrolled(RecyclerView recyclerView,
                                               int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);

                            totalItemCount = linearLayoutManager.getItemCount();
                            lastVisibleItem = linearLayoutManager
                                    .findLastVisibleItemPosition();
                            if (!loading
                                    && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                                // End has been reached
                                // Do something
                                if (onLoadMoreListener != null) {
                                    onLoadMoreListener.onLoadMore();
                                }
                                loading = true;
                            }
                        }
                    });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return imagesList.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {

        if (viewType == VIEW_ITEM) {
            InquiryListItemBinding inquiryListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.inquiry_list_item, parent, false);
            return new DemoWallPaperDataAdapter.WallPaperViewHolder(inquiryListItemBinding);
        } else {
            ItemLoadingBinding itemLoadingBinding=DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.item_loading,parent,false);
            return new DemoWallPaperDataAdapter.ProgressViewHolder(itemLoadingBinding);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof WallPaperViewHolder) {

            MoreDetailDataModel detailDataModel = imagesList.get(position);

            ((InquiryListAdapter.MyViewHolder) holder).inquiryListItemBinding.inquirytypeTxt.setText(detailDataModel.getStrInquiryTypePrefix());
            ((InquiryListAdapter.MyViewHolder) holder).inquiryListItemBinding.inquiryuserNametxt.setText(detailDataModel.getCustomerName());
            ((InquiryListAdapter.MyViewHolder) holder).inquiryListItemBinding.userEmailtxt.setText(detailDataModel.getCustomerEmail());
            ((InquiryListAdapter.MyViewHolder) holder).inquiryListItemBinding.userNotxt.setText(detailDataModel.getCustomerPhoneNo());
            ((InquiryListAdapter.MyViewHolder) holder).inquiryListItemBinding.inquirystatusTxt.setText(detailDataModel.getStrOrderStatus());

            GradientDrawable bgShape = (GradientDrawable)((InquiryListAdapter.MyViewHolder) holder).inquiryListItemBinding.inquirytypeLinear.getBackground();
            bgShape.setColor(Color.parseColor(detailDataModel.getStrInquiryTypeColor()));

            ((InquiryListAdapter.MyViewHolder) holder).inquiryListItemBinding.inquirytypeTxt.setTextColor(Color.parseColor(detailDataModel.getStrInquiryTypeFontColor()));

            ((InquiryListAdapter.MyViewHolder) holder).inquiryListItemBinding.inquiryCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppConfiguration.inquiryId = String.valueOf(detailDataModel.getOrderId());
//                    morestoryClick.getmorestoryClick();
                }
            });
            ((InquiryListAdapter.MyViewHolder) holder).inquiryListItemBinding.settingLinear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    AppConfiguration.inquiryId = String.valueOf(detailDataModel.getOrderId());
//                    PopupMenu popup = new PopupMenu(, v, Gravity.END,0,R.style.MyPopupMenu);
//                    MenuInflater inflater = popup.getMenuInflater();
//                    inflater.inflate(R.menu.inquiry_item_menu, popup.getMenu());
//
//                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                        @Override
//                        public boolean onMenuItemClick(MenuItem item) {
//                            switch (item.getItemId()) {
//                                case R.id.view_item:
//                                    AppConfiguration.inquiryId = String.valueOf(detailDataModel.getOrderId());
//                                    morestoryClick.getmorestoryClick();
//                                    return true;
//                                case R.id.assign_item:
//                                    image_click.image_more_click();
//
//                                    return true;
//                                default:
//                                    return false;
//                            }
//                        }
//                    });
//                    popup.show();

                }
            });
        } else {
            ((ProgressViewHolder) holder).itemLoadingBinding.progressBar.setIndeterminate(true);
        }
    }

    public void setLoaded() {
        loading = false;
    }

    @Override
    public int getItemCount() {
        return imagesList.size();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }


    //
    public static class WallPaperViewHolder extends RecyclerView.ViewHolder {

        InquiryListItemBinding inquiryListItemBinding;
        public WallPaperViewHolder(InquiryListItemBinding inquiryListItemBinding) {
            super(inquiryListItemBinding.getRoot());
            this.inquiryListItemBinding = inquiryListItemBinding;

        }
    }


    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        ItemLoadingBinding itemLoadingBinding;

        public ProgressViewHolder(ItemLoadingBinding itemLoadingBinding) {
            super(itemLoadingBinding.getRoot());
            this.itemLoadingBinding = itemLoadingBinding;

        }
    }
}
