package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Interfaces.MorestoryClick;
import com.bharatarmy.Models.HomeTemplateDetailModel;
import com.bharatarmy.Models.RegisterIntrestFilterDataModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.RegisterInterestchildItemBinding;
import com.bharatarmy.databinding.RegisterInteresttitleItemBinding;
import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.ArrayList;
import java.util.List;

public class RegisterIntrestAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int HEADER = 0;
    private static final int ITEM = 1;

    Context mContext;
    List<HomeTemplateDetailModel> tournamentDetailModelList;
    String titleNameStr, nooft20Str, noofodiStr, nooftestStr;
    MorestoryClick morestoryClick;
    private ArrayList<String> dataCheck = new ArrayList<String>();
    RegisterIntrestFilterDataModel registerIntrestFilterDataModel;
    RegisterIntetestCountryFlagAdapter registerIntetestCountryFlagAdapter;

    public RegisterIntrestAdapter(Context mContext, List<HomeTemplateDetailModel> tournamentDetailModel, RegisterIntrestFilterDataModel registerIntrestFilterDataModel, String titleNameStr,
                                  String nooft20Str, String noofodiStr, String nooftestStr, MorestoryClick morestoryClick) {
        this.mContext = mContext;
        this.titleNameStr = titleNameStr;
        this.tournamentDetailModelList = tournamentDetailModel;
        this.nooft20Str = nooft20Str;
        this.noofodiStr = noofodiStr;
        this.nooftestStr = nooftestStr;
        this.morestoryClick = morestoryClick;
        this.registerIntrestFilterDataModel = registerIntrestFilterDataModel;
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
            HomeTemplateDetailModel tournamentDetail = tournamentDetailModelList.get(position - 1);

            ((MyItemViewHolder) holder).registerInterestchildItemBinding.tournamenttimeTxt.setText(tournamentDetail.getStrMatchDateTime());
            ((MyItemViewHolder) holder).registerInterestchildItemBinding.tournamenttypeTxt.setText(tournamentDetail.getStrMatchType());

            if (tournamentDetail.getDbFromCountryName().equalsIgnoreCase("")) {
                ((MyItemViewHolder) holder).registerInterestchildItemBinding.fromCountryTxt.setText(
                        tournamentDetail.getObjFromCountry().getCountryName());
            } else {
                ((MyItemViewHolder) holder).registerInterestchildItemBinding.fromCountryTxt.setText(
                        tournamentDetail.getDbFromCountryName());
            }
            if (tournamentDetail.getDbToCountryName().equalsIgnoreCase("")) {
                ((MyItemViewHolder) holder).registerInterestchildItemBinding.toCountryTxt.setText(
                        tournamentDetail.getObjToCountry().getCountryName());
            } else {
                ((MyItemViewHolder) holder).registerInterestchildItemBinding.toCountryTxt.setText(
                        tournamentDetail.getDbToCountryName());
            }

            Utils.setImageInImageView(tournamentDetail.getObjFromCountry().getCountryFlagUrl(), ((MyItemViewHolder) holder).registerInterestchildItemBinding.fromCountryImg, mContext);
            Utils.setImageInImageView(tournamentDetail.getObjToCountry().getCountryFlagUrl(), ((MyItemViewHolder) holder).registerInterestchildItemBinding.toCountryImg, mContext);

            if (tournamentDetail.getMatchNo() <= 9) {
                ((MyItemViewHolder) holder).registerInterestchildItemBinding.matchnoStadiumnameTxt.setText("Match 0" +
                        tournamentDetail.getMatchNo() + " | " + tournamentDetail.getStadiumName());

            } else {
                ((MyItemViewHolder) holder).registerInterestchildItemBinding.matchnoStadiumnameTxt.setText("Match " +
                        tournamentDetail.getMatchNo() + " | " + tournamentDetail.getStadiumName());
            }
            ((MyItemViewHolder) holder).registerInterestchildItemBinding.mainContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        if (((MyItemViewHolder) holder).registerInterestchildItemBinding.selectedChk.isChecked()) {
//                            ((MyItemViewHolder) holder).registerInterestchildItemBinding.headerLinear
//                                    .setBackground(mContext.getResources().getDrawable(R.drawable.match_detail_curveshape));
                            ((MyItemViewHolder) holder).registerInterestchildItemBinding.bottomGradiantView.setVisibility(View.VISIBLE);
                            ((MyItemViewHolder) holder).registerInterestchildItemBinding.selectedChk.setChecked(false);
                            tournamentDetail.setCheck("0");
                           morestoryClick.getmorestoryClick();
                        } else {
//                            ((MyItemViewHolder) holder).registerInterestchildItemBinding.headerLinear
//                                    .setBackground(mContext.getResources().getDrawable(R.drawable.travel_match_selectedchild_curveshape));

                            ((MyItemViewHolder) holder).registerInterestchildItemBinding.bottomGradiantView.setVisibility(View.VISIBLE);
                            ((MyItemViewHolder) holder).registerInterestchildItemBinding.selectedChk.setChecked(true);
                            tournamentDetail.setCheck("1");

                            morestoryClick.getmorestoryClick();
                        }

                }
            });

            ((MyItemViewHolder) holder).registerInterestchildItemBinding.selectedChk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (((MyItemViewHolder) holder).registerInterestchildItemBinding.selectedChk.isChecked()) {
//                        ((MyItemViewHolder) holder).registerInterestchildItemBinding.headerLinear
//                                .setBackground(mContext.getResources().getDrawable(R.drawable.travel_match_selectedchild_curveshape));

                        ((MyItemViewHolder) holder).registerInterestchildItemBinding.bottomGradiantView.setVisibility(View.VISIBLE);
                        ((MyItemViewHolder) holder).registerInterestchildItemBinding.selectedChk.setChecked(true);

                        tournamentDetail.setCheck("1");
                        morestoryClick.getmorestoryClick();
                    } else {
//                        ((MyItemViewHolder) holder).registerInterestchildItemBinding.headerLinear
//                                .setBackground(mContext.getResources().getDrawable(R.drawable.match_detail_curveshape));
                        ((MyItemViewHolder) holder).registerInterestchildItemBinding.bottomGradiantView.setVisibility(View.VISIBLE);
                        ((MyItemViewHolder) holder).registerInterestchildItemBinding.selectedChk.setChecked(false);
                        tournamentDetail.setCheck("0");
                        morestoryClick.getmorestoryClick();
                    }
                }
            });
            if (tournamentDetail.getCheck().equalsIgnoreCase("1")) {
                ((MyItemViewHolder) holder).registerInterestchildItemBinding.selectedChk.setChecked(true);
            } else {
                ((MyItemViewHolder) holder).registerInterestchildItemBinding.selectedChk.setChecked(false);
            }
        } else if (holder.getItemViewType() == HEADER) {
            ((HeaderViewHolder) holder).registerInteresttitleItemBinding.titleTxtView.setText(titleNameStr);
            ((HeaderViewHolder) holder).registerInteresttitleItemBinding.tourNameTxt.setText(tournamentDetailModelList.get(position).getTourName());


            registerIntetestCountryFlagAdapter = new RegisterIntetestCountryFlagAdapter(mContext, registerIntrestFilterDataModel);
            FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(mContext);
            layoutManager.setFlexWrap(FlexWrap.WRAP);
            layoutManager.setAlignItems(AlignItems.BASELINE);
            layoutManager.setJustifyContent(JustifyContent.FLEX_START);
            ((HeaderViewHolder) holder).registerInteresttitleItemBinding.registerInterestFlagRcv.setLayoutManager(layoutManager); // set LayoutManager to RecyclerView
            ((HeaderViewHolder) holder).registerInteresttitleItemBinding.registerInterestFlagRcv.setAdapter(registerIntetestCountryFlagAdapter);

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
        return tournamentDetailModelList.size() + 1;
    }

    public List<String> getTournamentSelectedList() {
        return dataCheck;
    }

}



