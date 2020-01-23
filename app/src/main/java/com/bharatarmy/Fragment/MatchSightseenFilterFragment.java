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

import com.bharatarmy.Adapter.MatchSightseenFilterAdapter;
import com.bharatarmy.Adapter.MatchStadiumFilterAdapter;
import com.bharatarmy.Interfaces.MorestoryClick;
import com.bharatarmy.Models.RegisterIntrestFilterDataModel;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.databinding.FragmentMatchSightseenFilterBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;


public class MatchSightseenFilterFragment extends BottomSheetDialogFragment implements ViewPager.OnPageChangeListener{
    FragmentMatchSightseenFilterBinding fragmentMatchSightseenFilterBinding;
    View rootView;
    Context mContext;
    MatchSightseenFilterAdapter matchSightseenFilterAdapter;
    RegisterIntrestFilterDataModel tournamentsightseenotherDataModel;
    MorestoryClick morestoryClick;
    ArrayList<TravelModel> tournamentcitylist;

    public MatchSightseenFilterFragment(/*RegisterIntrestFilterDataModel tournamentsightseenotherDataModel*/ArrayList<TravelModel> tournamentcitylist, MorestoryClick morestoryClick) {
        this.morestoryClick=morestoryClick;
//        this.tournamentsightseenotherDataModel=tournamentsightseenotherDataModel;
        this.tournamentcitylist=tournamentcitylist;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mString = getArguments().getString("string");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentMatchSightseenFilterBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_match_sightseen_filter, container, false);

        rootView = fragmentMatchSightseenFilterBinding.getRoot();
        mContext = getActivity().getApplicationContext();

        init();
        setListiner();
        setDataValue();
        return rootView;
    }

    public void init(){

    }
    public void setDataValue() {
        fragmentMatchSightseenFilterBinding.tabLayoutSightseenfilter.addTab(fragmentMatchSightseenFilterBinding.tabLayoutSightseenfilter.newTab().setText("CITY"),true);
//        fragmentMatchSightseenFilterBinding.tabLayoutSightseenfilter.addTab(fragmentMatchSightseenFilterBinding.tabLayoutSightseenfilter.newTab().setText("VENUES"));

        fragmentMatchSightseenFilterBinding.tabLayoutSightseenfilter.setTabMode(TabLayout.MODE_FIXED);
        fragmentMatchSightseenFilterBinding.tabLayoutSightseenfilter.setTabGravity(TabLayout.GRAVITY_FILL);

        matchSightseenFilterAdapter = new MatchSightseenFilterAdapter(getChildFragmentManager(),
                fragmentMatchSightseenFilterBinding.tabLayoutSightseenfilter.getTabCount(),
                tournamentcitylist);
        fragmentMatchSightseenFilterBinding.matchsightseenfilterpager.setOffscreenPageLimit(0);
        fragmentMatchSightseenFilterBinding.matchsightseenfilterpager.setAdapter(matchSightseenFilterAdapter);

    }

    public void setListiner() {
        fragmentMatchSightseenFilterBinding.filtercloseLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        fragmentMatchSightseenFilterBinding.filterapplyLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();
                morestoryClick.getmorestoryClick();


            }
        });

        fragmentMatchSightseenFilterBinding.matchsightseenfilterpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(
                fragmentMatchSightseenFilterBinding.tabLayoutSightseenfilter));
        fragmentMatchSightseenFilterBinding.tabLayoutSightseenfilter.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                fragmentMatchSightseenFilterBinding.matchsightseenfilterpager.setCurrentItem(tab.getPosition());
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
