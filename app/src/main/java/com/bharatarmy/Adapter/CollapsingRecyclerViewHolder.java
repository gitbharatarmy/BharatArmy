package com.bharatarmy.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bharatarmy.R;

public class CollapsingRecyclerViewHolder extends RecyclerView.ViewHolder {

    private ImageView imageView = null;

    public CollapsingRecyclerViewHolder(View itemView) {
        super(itemView);

        if(itemView != null)
        {
            imageView = (ImageView)itemView.findViewById(R.id.collapsing_toolbar_recycler_view_item_image);
        }
    }

    public ImageView getImageView() {
        return imageView;
    }
}
