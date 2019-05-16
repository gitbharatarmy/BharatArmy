package com.bharatarmy.Adapter;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bharatarmy.Models.CollapsingRecyclerViewItem;
import com.bharatarmy.R;

import java.util.List;

public class CollapsingRecyclerViewDataAdapter extends RecyclerView.Adapter<CollapsingRecyclerViewHolder> {

    private List<CollapsingRecyclerViewItem> viewItemList;

    public CollapsingRecyclerViewDataAdapter(List<CollapsingRecyclerViewItem> viewItemList) {
        this.viewItemList = viewItemList;
    }

    @Override
    public CollapsingRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Get LayoutInflater object.
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        // Inflate the RecyclerView item layout xml.
        final View itemView = layoutInflater.inflate(R.layout.activity_collapsing_toolbar_layout_recycler_view_item, parent, false);

        final ImageView imageView = (ImageView)itemView.findViewById(R.id.collapsing_toolbar_recycler_view_item_image);
        // Popup a Snackbar at screen bottom when click image.
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar snackbar = Snackbar.make(v, "You click this image.", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });

        // Create and return collapsing Recycler View Holder object.
        CollapsingRecyclerViewHolder ret = new CollapsingRecyclerViewHolder(itemView);
        return ret;
    }

    @Override
    public void onBindViewHolder(CollapsingRecyclerViewHolder holder, int position) {
        if(viewItemList!=null) {
            // Get item dto in list.
            CollapsingRecyclerViewItem viewItem = viewItemList.get(position);

            if(viewItem != null) {
                // Set car image resource id.
                holder.getImageView().setImageResource(viewItem.getImageId());
            }
        }
    }

    @Override
    public int getItemCount() {
        int ret = 0;
        if(viewItemList!=null)
        {
            ret = viewItemList.size();
        }
        return ret;
    }
}
