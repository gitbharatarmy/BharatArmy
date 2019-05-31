package com.bharatarmy.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Models.BulletsPoint;
import com.bharatarmy.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BulletAdapter extends RecyclerView.Adapter<BulletAdapter.MyViewHolder> {
    Context mcontext;
    List<BulletsPoint> bulletList;
    private final static int FADE_DURATION = 1000;


    public BulletAdapter(Context mContext, List<BulletsPoint> bulletList) {
        this.mcontext=mContext;
        this.bulletList=bulletList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView bullet_txt;
        ImageView bullet_img;

        public MyViewHolder(View view) {
            super(view);
            bullet_txt = (TextView) view.findViewById(R.id.bullet_txt);
            bullet_img=(ImageView) view.findViewById(R.id.bullet_img);
        }
    }



    @Override
    public BulletAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bullet_list, parent, false);

        return new BulletAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BulletAdapter.MyViewHolder holder, int position) {
        // Set the view to fade in
//        setScaleAnimation(holder.itemView);
        holder.bullet_txt.setText(bulletList.get(position).getBulletName());
        Picasso.with(mcontext)
                .load(bulletList.get(position).getBulletImageURL())
                .into(holder.bullet_img);
    }

    @Override
    public int getItemCount() {
        return bulletList.size();
    }

    private void setScaleAnimation(View view) {
        ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }
}
