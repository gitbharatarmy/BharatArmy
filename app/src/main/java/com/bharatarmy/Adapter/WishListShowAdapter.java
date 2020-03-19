package com.bharatarmy.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.Models.ImageDetailModel;
import com.bharatarmy.Models.WatchListDetailModel;
import com.bharatarmy.Models.WatchListModel;
import com.bharatarmy.R;
import com.bharatarmy.TargetCallback;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.AlbumDetailListBinding;
import com.bharatarmy.databinding.WishlistShowListItemBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class WishListShowAdapter extends RecyclerView.Adapter<WishListShowAdapter.ItemViewHolder> {

    List<WatchListDetailModel> wishlistshowList;
    Context mContext;
    image_click imageClick;
    private ArrayList<String> dataCheck;


    public WishListShowAdapter(Context mContext, List<WatchListDetailModel> wishlistshowList, image_click imageClick) {
        this.mContext = mContext;
        this.wishlistshowList = wishlistshowList;
        this.imageClick = imageClick;
    }

    @NonNull
    @Override
    public WishListShowAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        WishlistShowListItemBinding wishlistShowListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.wishlist_show_list_item, parent, false);
        return new WishListShowAdapter.ItemViewHolder(wishlistShowListItemBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull WishListShowAdapter.ItemViewHolder viewHolder, int position) {

        final WatchListDetailModel detail = wishlistshowList.get(position);
        Picasso.with(mContext).load(detail.getWatchGalleryThumbURL()).placeholder(R.drawable.loader_new)
                .into(viewHolder.wishlistShowListItemBinding.wishlistImage, new TargetCallback(viewHolder.wishlistShowListItemBinding.wishlistImage) {
                    @Override
                    public void onSuccess(ImageView target) {
                        if (target != null) {
                            viewHolder.wishlistShowListItemBinding.videoLinear.setVisibility(View.VISIBLE);

                            if (detail.getIsMediaTypeId().equals(2)) {
                                viewHolder.wishlistShowListItemBinding.videoImage.setVisibility(View.VISIBLE);
                            } else {
                                viewHolder.wishlistShowListItemBinding.videoImage.setVisibility(View.GONE);
                            }
                        } else {
                            viewHolder.wishlistShowListItemBinding.videoLinear.setVisibility(View.GONE);
                        }

                    }

                    @Override
                    public void onError(ImageView target) {
                        viewHolder.wishlistShowListItemBinding.videoLinear.setVisibility(View.GONE);
                    }
                });

        viewHolder.wishlistShowListItemBinding.wishlistImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataCheck = new ArrayList<String>();
                dataCheck.add(detail.getWatchGalleryURL());
                imageClick.image_more_click();
            }
        });

    }

    @Override
    public int getItemCount() {
        return wishlistshowList.size();
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

        WishlistShowListItemBinding wishlistShowListItemBinding;

        public ItemViewHolder(@NonNull WishlistShowListItemBinding wishlistShowListItemBinding) {
            super(wishlistShowListItemBinding.getRoot());
            this.wishlistShowListItemBinding = wishlistShowListItemBinding;

        }
    }

    public ArrayList<String> getData() {
        return dataCheck;
    }


}



