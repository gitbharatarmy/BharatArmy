package com.bharatarmy.Fragment;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bharatarmy.Adapter.MatchHotelFilterAdapter;
import com.bharatarmy.Adapter.MatchTicketFilterAdapter;
import com.bharatarmy.Interfaces.MorestoryClick;
import com.bharatarmy.Models.RegisterIntrestFilterDataModel;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.databinding.FragmentMatchHotelFilterBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;


public class MatchHotelFilterFragment extends BottomSheetDialogFragment implements ViewPager.OnPageChangeListener{
    FragmentMatchHotelFilterBinding fragmentMatchHotelFilterBinding;
    View rootView;
    Context mContext;
    MatchHotelFilterAdapter matchHotelFilterAdapter;
    RegisterIntrestFilterDataModel tournamenthotelotherDataModel;
    MorestoryClick morestoryClick;
    ArrayList<TravelModel> tournamnetcitylist;
    ArrayList<TravelModel> tournamnetratinglist;
    ArrayList<TravelModel> tournamnetbyteamlist;



    public MatchHotelFilterFragment(RegisterIntrestFilterDataModel tournamenthotelotherDataModel, MorestoryClick morestoryClick) {
        this.morestoryClick=morestoryClick;
        this.tournamenthotelotherDataModel=tournamenthotelotherDataModel;
    }

    public MatchHotelFilterFragment(RegisterIntrestFilterDataModel tournamenthotelotherDataModel,
                                    ArrayList<TravelModel> tournamnetcitylist,
                                    ArrayList<TravelModel> tournamnetratinglist,
                                    ArrayList<TravelModel> tournamnetbyteamlist,
                                    MorestoryClick morestoryClick) {

        this.morestoryClick=morestoryClick;
        this.tournamenthotelotherDataModel=tournamenthotelotherDataModel;
        this.tournamnetcitylist =tournamnetcitylist;
        this.tournamnetratinglist=tournamnetratinglist;
        this.tournamnetbyteamlist=tournamnetbyteamlist;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mString = getArguments().getString("string");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentMatchHotelFilterBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_match_hotel_filter, container, false);

        rootView = fragmentMatchHotelFilterBinding.getRoot();
        mContext = getActivity().getApplicationContext();

        init();
        setListiner();
        setDataValue();
        return rootView;
    }

    public void init(){

    }
    public void setDataValue() {
        fragmentMatchHotelFilterBinding.tabLayoutHotelfilter.addTab(fragmentMatchHotelFilterBinding.tabLayoutHotelfilter.newTab().setText("RATING"),true);
        fragmentMatchHotelFilterBinding.tabLayoutHotelfilter.addTab(fragmentMatchHotelFilterBinding.tabLayoutHotelfilter.newTab().setText("TEAM"));
        fragmentMatchHotelFilterBinding.tabLayoutHotelfilter.addTab(fragmentMatchHotelFilterBinding.tabLayoutHotelfilter.newTab().setText("CITY"));

        fragmentMatchHotelFilterBinding.tabLayoutHotelfilter.setTabMode(TabLayout.MODE_FIXED);
        fragmentMatchHotelFilterBinding.tabLayoutHotelfilter.setTabGravity(TabLayout.GRAVITY_FILL);

        matchHotelFilterAdapter = new MatchHotelFilterAdapter(getChildFragmentManager(),
                fragmentMatchHotelFilterBinding.tabLayoutHotelfilter.getTabCount(),
                tournamenthotelotherDataModel,tournamnetcitylist,tournamnetratinglist,tournamnetbyteamlist);
        fragmentMatchHotelFilterBinding.matchhotelfilterpager.setOffscreenPageLimit(3);
        fragmentMatchHotelFilterBinding.matchhotelfilterpager.setAdapter(matchHotelFilterAdapter);

    }

    public void setListiner() {
        fragmentMatchHotelFilterBinding.filtercloseLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        fragmentMatchHotelFilterBinding.filterapplyLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();
                morestoryClick.getmorestoryClick();


            }
        });

        fragmentMatchHotelFilterBinding.matchhotelfilterpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(
                fragmentMatchHotelFilterBinding.tabLayoutHotelfilter));
        fragmentMatchHotelFilterBinding.tabLayoutHotelfilter.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                fragmentMatchHotelFilterBinding.matchhotelfilterpager.setCurrentItem(tab.getPosition());
                Spannable wordtoSpan = new SpannableString(String.valueOf(tab.getText()));
                wordtoSpan.setSpan(new StyleSpan(Typeface.BOLD), 0, wordtoSpan.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                tab.setText(wordtoSpan);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Spannable wordtoSpan = new SpannableString(String.valueOf(tab.getText()));
                wordtoSpan.setSpan(new StyleSpan(Typeface.NORMAL), 0, wordtoSpan.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                tab.setText(wordtoSpan);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

}
