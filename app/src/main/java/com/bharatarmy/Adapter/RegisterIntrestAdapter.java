package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.RegisterInterestchildItemBinding;
import com.bharatarmy.databinding.RegisterInteresttitleItemBinding;
import com.cooltechworks.views.shimmer.ShimmerRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RegisterIntrestAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int HEADER = 0;
    private static final int ITEM = 1;

    boolean firstClick = true;
    Context mContext;
    List<String> listDataHeader;
    String titleNameStr;

    public RegisterIntrestAdapter(Context mContext, List<String> listDataHeader, String titleNameStr) {
        this.mContext = mContext;
        this.listDataHeader = listDataHeader;
        this.titleNameStr = titleNameStr;

    }

    static class MyItemViewHolder extends RecyclerView.ViewHolder {
        RegisterInterestchildItemBinding registerInterestchildItemBinding;

        public MyItemViewHolder(RegisterInterestchildItemBinding registerInterestchildItemBinding) {
            super(registerInterestchildItemBinding.getRoot());
            this.registerInterestchildItemBinding = registerInterestchildItemBinding;
        }
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder {

        RegisterInteresttitleItemBinding registerInteresttitleItemBinding;

        HeaderViewHolder(RegisterInteresttitleItemBinding registerInteresttitleItemBinding) {
            super(registerInteresttitleItemBinding.getRoot());
            this.registerInteresttitleItemBinding = registerInteresttitleItemBinding;

        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = null;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case HEADER:
                RegisterInteresttitleItemBinding registerInteresttitleItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.register_interesttitle_item, parent, false);
                return new RegisterIntrestAdapter.HeaderViewHolder(registerInteresttitleItemBinding);
            default:
                RegisterInterestchildItemBinding registerInterestchildItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.register_interestchild_item, parent, false);
                return new RegisterIntrestAdapter.MyItemViewHolder(registerInterestchildItemBinding);
        }
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == ITEM) {


            ((MyItemViewHolder) holder).registerInterestchildItemBinding.mainContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (((MyItemViewHolder) holder).registerInterestchildItemBinding.selectedChk.isChecked()){
                        ((MyItemViewHolder) holder).registerInterestchildItemBinding.mainContainer
                                .setBackground(mContext.getResources().getDrawable(R.drawable.match_groupdetail_curveshape));
                        ((MyItemViewHolder)holder).registerInterestchildItemBinding.bottomGradiantView.setVisibility(View.VISIBLE);
                        ((MyItemViewHolder) holder).registerInterestchildItemBinding.selectedChk.setChecked(false);
                    }else{
                        ((MyItemViewHolder) holder).registerInterestchildItemBinding.mainContainer
                                .setBackground(mContext.getResources().getDrawable(R.drawable.travel_match_selectedchild_curveshape));

                        ((MyItemViewHolder)holder).registerInterestchildItemBinding.bottomGradiantView.setVisibility(View.GONE);
                        ((MyItemViewHolder) holder).registerInterestchildItemBinding.selectedChk.setChecked(true);
                    }

                }
            });

        } else if (holder.getItemViewType() == HEADER) {
            Utils.setImageInImageView("https://www.bharatarmy.com/Content/images/flags-mini/in.png", ((HeaderViewHolder) holder).registerInteresttitleItemBinding.firstCountryflagImage, mContext);
            Utils.setImageInImageView("https://www.bharatarmy.com/Content/images/flags-mini/sou.png", ((HeaderViewHolder) holder).registerInteresttitleItemBinding.secondCountryflagImage, mContext);
            ((HeaderViewHolder) holder).registerInteresttitleItemBinding.titleTxtView.setText(titleNameStr);
        }

    }

    @Override

    public long getItemId(int position) {
// return specific item's id here
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? HEADER : ITEM;
    }

    @Override
    public int getItemCount() {
        return listDataHeader.size() + 1;
    }


}



