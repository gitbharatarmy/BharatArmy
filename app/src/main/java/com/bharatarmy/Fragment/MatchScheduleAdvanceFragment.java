package com.bharatarmy.Fragment;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bharatarmy.Adapter.FilterPageAdapter;
import com.bharatarmy.Adapter.MatchScheduleAdvanceFilterAdapter;
import com.bharatarmy.Interfaces.MorestoryClick;
import com.bharatarmy.Models.RegisterIntrestFilterDataModel;
import com.bharatarmy.R;
import com.bharatarmy.databinding.FragmentMatchScheduleAdvanceBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.tabs.TabLayout;


public class MatchScheduleAdvanceFragment extends BottomSheetDialogFragment implements ViewPager.OnPageChangeListener{
    FragmentMatchScheduleAdvanceBinding fragmentMatchScheduleAdvanceBinding;
    View rootView;
    Context mContext;
    MatchScheduleAdvanceFilterAdapter scheduleAdvanceFilterAdapter;
    RegisterIntrestFilterDataModel tournamentotherDataModel;
    MorestoryClick morestoryClick;

    public MatchScheduleAdvanceFragment(RegisterIntrestFilterDataModel tournamentotherDataModel, MorestoryClick morestoryClick) {
        this.morestoryClick=morestoryClick;
        this.tournamentotherDataModel=tournamentotherDataModel;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mString = getArguments().getString("string");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentMatchScheduleAdvanceBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_match_schedule_advance, container, false);

        rootView = fragmentMatchScheduleAdvanceBinding.getRoot();
        mContext = getActivity().getApplicationContext();

        init();
        setListiner();
        setDataValue();
        return rootView;
    }

    public void init(){

    }
    public void setDataValue() {
        fragmentMatchScheduleAdvanceBinding.tabLayoutSchedulefilter.addTab(fragmentMatchScheduleAdvanceBinding.tabLayoutSchedulefilter.newTab().setText("TEAMS"),true);
        fragmentMatchScheduleAdvanceBinding.tabLayoutSchedulefilter.addTab(fragmentMatchScheduleAdvanceBinding.tabLayoutSchedulefilter.newTab().setText("VENUES"));

        fragmentMatchScheduleAdvanceBinding.tabLayoutSchedulefilter.setTabMode(TabLayout.MODE_FIXED);
        fragmentMatchScheduleAdvanceBinding.tabLayoutSchedulefilter.setTabGravity(TabLayout.GRAVITY_FILL);

        scheduleAdvanceFilterAdapter = new MatchScheduleAdvanceFilterAdapter(getChildFragmentManager(),
                fragmentMatchScheduleAdvanceBinding.tabLayoutSchedulefilter.getTabCount(),
                tournamentotherDataModel);
        fragmentMatchScheduleAdvanceBinding.matchschedulefilterpager.setOffscreenPageLimit(0);
        fragmentMatchScheduleAdvanceBinding.matchschedulefilterpager.setAdapter(scheduleAdvanceFilterAdapter);

    }

    public void setListiner() {
        fragmentMatchScheduleAdvanceBinding.filtercloseLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        fragmentMatchScheduleAdvanceBinding.filterapplyLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();
                morestoryClick.getmorestoryClick();


            }
        });

        fragmentMatchScheduleAdvanceBinding.matchschedulefilterpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(
                fragmentMatchScheduleAdvanceBinding.tabLayoutSchedulefilter));
        fragmentMatchScheduleAdvanceBinding.tabLayoutSchedulefilter.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                fragmentMatchScheduleAdvanceBinding.matchschedulefilterpager.setCurrentItem(tab.getPosition());
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
