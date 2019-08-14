package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Interfaces.MorestoryClick;
import com.bharatarmy.Models.HomeTemplateDetailModel;
import com.bharatarmy.Models.RegisterIntrestFilterDataModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.RegisterInterestchildItemBinding;
import com.bharatarmy.databinding.RegisterInteresttitleItemBinding;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.ArrayList;
import java.util.List;

public class RegisterInterestFilterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int HEADER = 0;
    private static final int ITEM = 1;

    Context mContext;
    List<HomeTemplateDetailModel> tournamentDetailModelList;
    String titleNameStr, nooft20Str, noofodiStr, nooftestStr;
    MorestoryClick morestoryClick;
    private ArrayList<String> dataCheck = new ArrayList<String>();
    RegisterIntrestFilterDataModel registerIntrestFilterDataModel;
    RegisterIntetestCountryFlagAdapter registerIntetestCountryFlagAdapter;
    
    public RegisterInterestFilterAdapter(Context mContext, List<HomeTemplateDetailModel> tournamentDetailModel, RegisterIntrestFilterDataModel registerIntrestFilterDataModel, String titleNameStr,
                                         String nooft20Str, String noofodiStr, String nooftestStr, MorestoryClick morestoryClick) {
        this.mContext = mContext;
        this.titleNameStr = titleNameStr;
        this.tournamentDetailModelList = tournamentDetailModel;
        this.nooft20Str = nooft20Str;
        this.noofodiStr = noofodiStr;
        this.nooftestStr = nooftestStr;
        this.morestoryClick = morestoryClick;
        this.registerIntrestFilterDataModel =registerIntrestFilterDataModel;
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
                return new RegisterInterestFilterAdapter.HeaderViewHolder(registerInteresttitleItemBinding);
            default:
                RegisterInterestchildItemBinding registerInterestchildItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.register_interestchild_item, parent, false);
                return new RegisterInterestFilterAdapter.MyItemViewHolder(registerInterestchildItemBinding);
        }
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == ITEM) {
            HomeTemplateDetailModel tournamentDetail = tournamentDetailModelList.get(position - 1);

            ((RegisterInterestFilterAdapter.MyItemViewHolder) holder).registerInterestchildItemBinding.tournamenttimeTxt.setText(tournamentDetail.getStrMatchDateTime());
            ((RegisterInterestFilterAdapter.MyItemViewHolder) holder).registerInterestchildItemBinding.tournamenttypeTxt.setText(tournamentDetail.getStrMatchType());

            if (tournamentDetail.getDbFromCountryName().equalsIgnoreCase("")) {
                ((RegisterInterestFilterAdapter.MyItemViewHolder) holder).registerInterestchildItemBinding.fromCountryTxt.setText(
                        tournamentDetail.getObjFromCountry().getCountryName());
            } else {
                ((RegisterInterestFilterAdapter.MyItemViewHolder) holder).registerInterestchildItemBinding.fromCountryTxt.setText(
                        tournamentDetail.getDbFromCountryName());
            }
            if (tournamentDetail.getDbToCountryName().equalsIgnoreCase("")) {
                ((RegisterInterestFilterAdapter.MyItemViewHolder) holder).registerInterestchildItemBinding.toCountryTxt.setText(
                        tournamentDetail.getObjToCountry().getCountryName());
            } else {
                ((RegisterInterestFilterAdapter.MyItemViewHolder) holder).registerInterestchildItemBinding.toCountryTxt.setText(
                        tournamentDetail.getDbToCountryName());
            }

            Utils.setImageInImageView(tournamentDetail.getObjFromCountry().getCountryFlagUrl(), ((RegisterInterestFilterAdapter.MyItemViewHolder) holder).registerInterestchildItemBinding.fromCountryImg, mContext);
            Utils.setImageInImageView(tournamentDetail.getObjToCountry().getCountryFlagUrl(), ((RegisterInterestFilterAdapter.MyItemViewHolder) holder).registerInterestchildItemBinding.toCountryImg, mContext);

            if (tournamentDetail.getMatchNo() <= 9) {
                ((RegisterInterestFilterAdapter.MyItemViewHolder) holder).registerInterestchildItemBinding.matchnoStadiumnameTxt.setText("Match 0" +
                        tournamentDetail.getMatchNo() + " | " + tournamentDetail.getStadiumName());

            } else {
                ((RegisterInterestFilterAdapter.MyItemViewHolder) holder).registerInterestchildItemBinding.matchnoStadiumnameTxt.setText("Match " +
                        tournamentDetail.getMatchNo() + " | " + tournamentDetail.getStadiumName());
            }

            ((RegisterInterestFilterAdapter.MyItemViewHolder) holder).registerInterestchildItemBinding.mainContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (((RegisterInterestFilterAdapter.MyItemViewHolder) holder).registerInterestchildItemBinding.selectedChk.isChecked()) {
                        ((RegisterInterestFilterAdapter.MyItemViewHolder) holder).registerInterestchildItemBinding.mainContainer
                                .setBackground(mContext.getResources().getDrawable(R.drawable.match_groupdetail_curveshape));
                        ((RegisterInterestFilterAdapter.MyItemViewHolder) holder).registerInterestchildItemBinding.bottomGradiantView.setVisibility(View.VISIBLE);
                        ((RegisterInterestFilterAdapter.MyItemViewHolder) holder).registerInterestchildItemBinding.selectedChk.setChecked(false);
                        tournamentDetail.setCheck("0");

                    } else {
                        ((RegisterInterestFilterAdapter.MyItemViewHolder) holder).registerInterestchildItemBinding.mainContainer
                                .setBackground(mContext.getResources().getDrawable(R.drawable.travel_match_selectedchild_curveshape));

                        ((RegisterInterestFilterAdapter.MyItemViewHolder) holder).registerInterestchildItemBinding.bottomGradiantView.setVisibility(View.GONE);
                        ((RegisterInterestFilterAdapter.MyItemViewHolder) holder).registerInterestchildItemBinding.selectedChk.setChecked(true);
                        tournamentDetail.setCheck("1");
                        dataCheck.add(String.valueOf(tournamentDetail.getTournamentId()));
                    }

                }
            });

            ((RegisterInterestFilterAdapter.MyItemViewHolder) holder).registerInterestchildItemBinding.selectedChk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (((RegisterInterestFilterAdapter.MyItemViewHolder) holder).registerInterestchildItemBinding.selectedChk.isChecked()) {
                        ((RegisterInterestFilterAdapter.MyItemViewHolder) holder).registerInterestchildItemBinding.mainContainer
                                .setBackground(mContext.getResources().getDrawable(R.drawable.travel_match_selectedchild_curveshape));

                        ((RegisterInterestFilterAdapter.MyItemViewHolder) holder).registerInterestchildItemBinding.bottomGradiantView.setVisibility(View.GONE);
                        ((RegisterInterestFilterAdapter.MyItemViewHolder) holder).registerInterestchildItemBinding.selectedChk.setChecked(true);

                        tournamentDetail.setCheck("1");
                        dataCheck.add(String.valueOf(tournamentDetail.getTournamentId()));
                    } else {
                        ((RegisterInterestFilterAdapter.MyItemViewHolder) holder).registerInterestchildItemBinding.mainContainer
                                .setBackground(mContext.getResources().getDrawable(R.drawable.match_groupdetail_curveshape));
                        ((RegisterInterestFilterAdapter.MyItemViewHolder) holder).registerInterestchildItemBinding.bottomGradiantView.setVisibility(View.VISIBLE);
                        ((RegisterInterestFilterAdapter.MyItemViewHolder) holder).registerInterestchildItemBinding.selectedChk.setChecked(false);
                        tournamentDetail.setCheck("0");
                    }
                }
            });
            if (tournamentDetail.getCheck().equalsIgnoreCase("1")) {
                ((RegisterInterestFilterAdapter.MyItemViewHolder) holder).registerInterestchildItemBinding.selectedChk.setChecked(true);
            } else {
                ((RegisterInterestFilterAdapter.MyItemViewHolder) holder).registerInterestchildItemBinding.selectedChk.setChecked(false);
            }
        } else if (holder.getItemViewType() == HEADER) {
//            Utils.setImageInImageView("https://www.bharatarmy.com/Content/images/flags-mini/in.png", ((RegisterInterestFilterAdapter.HeaderViewHolder) holder).registerInteresttitleItemBinding.firstCountryflagImage, mContext);
//            Utils.setImageInImageView("https://www.bharatarmy.com/Content/images/flags-mini/sou.png", ((RegisterInterestFilterAdapter.HeaderViewHolder) holder).registerInteresttitleItemBinding.secondCountryflagImage, mContext);
            ((RegisterInterestFilterAdapter.HeaderViewHolder) holder).registerInteresttitleItemBinding.titleTxtView.setText(titleNameStr);
            ((RegisterInterestFilterAdapter.HeaderViewHolder) holder).registerInteresttitleItemBinding.tourNameTxt.setText(tournamentDetailModelList.get(position).getTourName());
            registerIntetestCountryFlagAdapter = new RegisterIntetestCountryFlagAdapter(mContext, registerIntrestFilterDataModel);
            FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(mContext);
            layoutManager.setFlexWrap(FlexWrap.WRAP);
            layoutManager.setAlignItems(AlignItems.BASELINE);
            layoutManager.setJustifyContent(JustifyContent.FLEX_START);
            ((RegisterInterestFilterAdapter.HeaderViewHolder) holder).registerInteresttitleItemBinding.registerInterestFlagRcv.setLayoutManager(layoutManager); // set LayoutManager to RecyclerView
            ((RegisterInterestFilterAdapter.HeaderViewHolder) holder).registerInteresttitleItemBinding.registerInterestFlagRcv.setAdapter(registerIntetestCountryFlagAdapter);

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



