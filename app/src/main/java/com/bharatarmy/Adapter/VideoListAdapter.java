package com.bharatarmy.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bharatarmy.R;

import java.util.List;

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.MyViewHolder> {
    Context mcontext;
    List<String> bulletList;


    public VideoListAdapter(Context mContext, List<String> bulletList) {
        this.mcontext = mContext;
        this.bulletList = bulletList;
    }

//    private List<Movie> moviesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView fans_image;

        public MyViewHolder(View view) {
            super(view);
            fans_image=(ImageView)view.findViewById(R.id.fans_image);

        }
    }


    @Override
    public VideoListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_list, parent, false);

        return new VideoListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(VideoListAdapter.MyViewHolder holder, int position) {



//        Picasso.with(mcontext)
//                .load(bulletList.get(position)
//
//                        .getBulletImageURL())
//                .into(holder.bullet_img);
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
        return bulletList.size();
    }
}


