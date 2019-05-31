package com.bharatarmy.Adapter;

import android.content.Context;
import android.graphics.Matrix;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Models.WalkthroughData;
import com.bharatarmy.R;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerSliderAdapter extends RecyclerView.Adapter<ViewPagerSliderAdapter.MyViewHolder> {



    List<MyViewHolder> myViewHoldersList;
    private Context mContext;
    private int recyclerViewHeight;
    List<WalkthroughData>walkthroughDataList;
    ArrayList<Integer> layouts;
    ArrayList<Integer> imageSlider;

    public ViewPagerSliderAdapter(Context mContext, List<WalkthroughData> walkthroughDataList, int recyclerViewHeight, ArrayList<Integer> imageSlider) {
        this.mContext = mContext;
        this.walkthroughDataList = walkthroughDataList;
        myViewHoldersList = new ArrayList<>();
        this.recyclerViewHeight = recyclerViewHeight;
        this.layouts=imageSlider;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewpagerslider_list, parent, false);
        MyViewHolder holder = new MyViewHolder(itemView, recyclerViewHeight);
        myViewHoldersList.add(holder);

        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.viewpager_img.setImageResource(layouts.get(position));
    }

    @Override
    public int getItemCount() {
        return layouts.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    public void reTranslate() {
        for (MyViewHolder myViewHolder : myViewHoldersList) {
            myViewHolder.translate();
        }
    }

    @Override
    public void onViewRecycled(MyViewHolder parallaxViewHolder) {
        super.onViewRecycled(parallaxViewHolder);

        parallaxViewHolder.translate();
        // this is set manually to show to the center
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
      ImageView imageView,viewpager_img;
      TextView headertext;
        private int recyclerViewHeight;
        private View itemView;
        public MyViewHolder(View view, int recyclerViewHeight) {
            super(view);
            viewpager_img=(ImageView)view.findViewById(R.id.viewpager_img);
            imageView=(ImageView)view.findViewById(R.id.imageView);
            headertext=(TextView)view.findViewById(R.id.header_txt);
            this.recyclerViewHeight = recyclerViewHeight;
            this.itemView = view;

        }

        //month_image.getMeasuredHeight()
        public void translate() {
            float translate = -itemView.getY() * ((float) viewpager_img.getMeasuredHeight() / (float) recyclerViewHeight);
            Matrix matrix = new Matrix();
            matrix.postTranslate(0, translate);

            viewpager_img.setImageMatrix(matrix);
        }
    }

}