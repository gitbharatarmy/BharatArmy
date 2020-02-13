package com.bharatarmy.Adapter;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.Models.ImageDetailModel;
import com.bharatarmy.R;
import com.bharatarmy.TargetCallback;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ImageListBinding;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.List;

public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ItemViewHolder> {

    public List<ImageDetailModel> mItemList;
    Context mContext;
    image_click image_click;
    private ArrayList<String> dataCheck;
    FragmentActivity activity;


    public ImageListAdapter(Context mContext, List<ImageDetailModel> itemList, FragmentActivity activity, image_click image_click) {
        this.mContext = mContext;
        this.mItemList = itemList;
        this.image_click = image_click;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ImageListAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ImageListBinding imageListBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.image_list, parent, false);
        return new ImageListAdapter.ItemViewHolder(imageListBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull ImageListAdapter.ItemViewHolder viewHolder, int position) {

        final ImageDetailModel detail = mItemList.get(position);
        viewHolder.imageListBinding.imageLikePrivacyLinear.setVisibility(View.GONE);
        viewHolder.imageListBinding.loveImgLinear.setVisibility(View.GONE);
        viewHolder.imageListBinding.privateImgLinear.setVisibility(View.GONE);

//        Utils.setImageInImageView(detail.getGalleryThumbURL(), viewHolder.imageListBinding.fansImage, mContext);

        Picasso.with(mContext).load(detail.getGalleryThumbURL()).placeholder(R.drawable.loader_new).into(viewHolder.imageListBinding.fansImage, new TargetCallback(viewHolder.imageListBinding.fansImage) {
            @Override
            public void onSuccess(ImageView target) {
                if (target != null) {
                    viewHolder.imageListBinding.imageLikePrivacyLinear.setVisibility(View.VISIBLE);
                    if (detail.getIsInWatchlist().equals(1)) {
                        viewHolder.imageListBinding.loveImgLinear.setVisibility(View.VISIBLE);
                    } else {
                        viewHolder.imageListBinding.loveImgLinear.setVisibility(View.GONE);
                    }

                    if (detail.getIsPrivate().equals(1)) {
                        viewHolder.imageListBinding.privateImgLinear.setVisibility(View.VISIBLE);
                    } else {
                        viewHolder.imageListBinding.privateImgLinear.setVisibility(View.GONE);
                    }
                } else {
                    viewHolder.imageListBinding.imageLikePrivacyLinear.setVisibility(View.GONE);
                }

            }

            @Override
            public void onError(ImageView target) {
                viewHolder.imageListBinding.imageLikePrivacyLinear.setVisibility(View.GONE);
            }
        });


        viewHolder.imageListBinding.fansImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.viewsMemberId = String.valueOf(Utils.getAppUserId(mContext));
                Utils.viewsReferenceId = String.valueOf(detail.getBAGalleryId());
                Utils.viewsSourceType = "1";
                Utils.viewsTokenId = Utils.getPref(mContext, "registration_id");
                Utils.InsertBAViews(mContext, activity);
                dataCheck = new ArrayList<String>();
                dataCheck.add(String.valueOf(detail.getGalleryURL()));
                image_click.image_more_click();
            }
        });

    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (!payloads.isEmpty()) {
            for (final Object payload : payloads) {
                Log.d("payload :", "" + payload);
                if (payload.toString().equalsIgnoreCase("1")) {
                    holder.imageListBinding.loveImgLinear.setVisibility(View.VISIBLE);
                } else {
                    holder.imageListBinding.loveImgLinear.setVisibility(View.GONE);
                }
            }
        } else {
            // in this case regular onBindViewHolder will be called
            super.onBindViewHolder(holder, position, payloads);
        }

    }

    @Override
    public int getItemCount() {
        return mItemList.size();
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

        ImageListBinding imageListBinding;

        public ItemViewHolder(@NonNull ImageListBinding imageListBinding) {
            super(imageListBinding.getRoot());
            this.imageListBinding = imageListBinding;

        }
    }


    public ArrayList<String> getData() {
        return dataCheck;
    }

    // Clean all elements of the recycler
    public void clear() {
        mItemList.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addMoreDataToList(List<ImageDetailModel> result) {
        mItemList.addAll(result);
        Log.d("Adapterax : ", "" + mItemList.size());
        notifyDataSetChanged();
    }
}

