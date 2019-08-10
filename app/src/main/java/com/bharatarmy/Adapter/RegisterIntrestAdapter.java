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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Interfaces.MorestoryClick;
import com.bharatarmy.Models.HomeTemplateDetailModel;
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

    Context mContext;
    List<HomeTemplateDetailModel> tournamentDetailModelList;
    String titleNameStr, nooft20Str, noofodiStr, nooftestStr;
    MorestoryClick morestoryClick;
    private ArrayList<String> dataCheck = new ArrayList<String>();
    public RegisterIntrestAdapter(Context mContext, List<HomeTemplateDetailModel> tournamentDetailModel, String titleNameStr,
                                  String nooft20Str, String noofodiStr, String nooftestStr, MorestoryClick morestoryClick) {
        this.mContext = mContext;
        this.titleNameStr = titleNameStr;
        this.tournamentDetailModelList = tournamentDetailModel;
        this.nooft20Str = nooft20Str;
        this.noofodiStr = noofodiStr;
        this.nooftestStr = nooftestStr;
        this.morestoryClick = morestoryClick;
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
                        ((MyItemViewHolder) holder).registerInterestchildItemBinding.mainContainer
                                .setBackground(mContext.getResources().getDrawable(R.drawable.match_groupdetail_curveshape));
                        ((MyItemViewHolder) holder).registerInterestchildItemBinding.bottomGradiantView.setVisibility(View.VISIBLE);
                        ((MyItemViewHolder) holder).registerInterestchildItemBinding.selectedChk.setChecked(false);
                        tournamentDetail.setCheck("0");

                    } else {
                        ((MyItemViewHolder) holder).registerInterestchildItemBinding.mainContainer
                                .setBackground(mContext.getResources().getDrawable(R.drawable.travel_match_selectedchild_curveshape));

                        ((MyItemViewHolder) holder).registerInterestchildItemBinding.bottomGradiantView.setVisibility(View.GONE);
                        ((MyItemViewHolder) holder).registerInterestchildItemBinding.selectedChk.setChecked(true);
                        tournamentDetail.setCheck("1");
                        dataCheck.add(String.valueOf(tournamentDetail.getTournamentId()));
                    }

                }
            });

            ((MyItemViewHolder) holder).registerInterestchildItemBinding.selectedChk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (((MyItemViewHolder) holder).registerInterestchildItemBinding.selectedChk.isChecked()) {
                        ((MyItemViewHolder) holder).registerInterestchildItemBinding.mainContainer
                                .setBackground(mContext.getResources().getDrawable(R.drawable.travel_match_selectedchild_curveshape));

                        ((MyItemViewHolder) holder).registerInterestchildItemBinding.bottomGradiantView.setVisibility(View.GONE);
                        ((MyItemViewHolder) holder).registerInterestchildItemBinding.selectedChk.setChecked(true);

                        tournamentDetail.setCheck("1");
                        dataCheck.add(String.valueOf(tournamentDetail.getTournamentId()));
                    } else {
                        ((MyItemViewHolder) holder).registerInterestchildItemBinding.mainContainer
                                .setBackground(mContext.getResources().getDrawable(R.drawable.match_groupdetail_curveshape));
                        ((MyItemViewHolder) holder).registerInterestchildItemBinding.bottomGradiantView.setVisibility(View.VISIBLE);
                        ((MyItemViewHolder) holder).registerInterestchildItemBinding.selectedChk.setChecked(false);
                        tournamentDetail.setCheck("0");
                    }
                }
            });
            if (tournamentDetail.getCheck().equalsIgnoreCase("1")) {
                ((MyItemViewHolder) holder).registerInterestchildItemBinding.selectedChk.setChecked(true);
            } else {
                ((MyItemViewHolder) holder).registerInterestchildItemBinding.selectedChk.setChecked(false);
            }
        } else if (holder.getItemViewType() == HEADER) {
            Utils.setImageInImageView("https://www.bharatarmy.com/Content/images/flags-mini/in.png", ((HeaderViewHolder) holder).registerInteresttitleItemBinding.firstCountryflagImage, mContext);
            Utils.setImageInImageView("https://www.bharatarmy.com/Content/images/flags-mini/sou.png", ((HeaderViewHolder) holder).registerInteresttitleItemBinding.secondCountryflagImage, mContext);
            ((HeaderViewHolder) holder).registerInteresttitleItemBinding.titleTxtView.setText(titleNameStr);
            ((HeaderViewHolder) holder).registerInteresttitleItemBinding.tourNameTxt.setText(tournamentDetailModelList.get(position).getTourName());

//            if (!noofodiStr.equalsIgnoreCase("0")) {
//                ((HeaderViewHolder) holder).registerInteresttitleItemBinding.matchODIcount1Txt.setText(noofodiStr+" ODI,");
//            } else {
//                ((HeaderViewHolder) holder).registerInteresttitleItemBinding.matchODIcount1Txt.setVisibility(View.GONE);
//            }
//            if (!nooft20Str.equalsIgnoreCase("0")) {
//                ((HeaderViewHolder) holder).registerInteresttitleItemBinding.matchT20countTxt.setText(nooft20Str+" T20");
//            } else {
//                ((HeaderViewHolder) holder).registerInteresttitleItemBinding.matchODIcount1Txt.setVisibility(View.GONE);
//            }
//
//            if (!nooftestStr.equalsIgnoreCase("0")) {
//                ((HeaderViewHolder) holder).registerInteresttitleItemBinding.matchTESTcount2Txt.setText(nooftestStr+" TEST,");
//            } else {
//                ((HeaderViewHolder) holder).registerInteresttitleItemBinding.matchTESTcount2Txt.setVisibility(View.GONE);
//            }
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



