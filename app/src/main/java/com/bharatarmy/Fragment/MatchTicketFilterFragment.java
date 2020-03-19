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

import com.bharatarmy.Adapter.MatchTicketFilterAdapter;
import com.bharatarmy.Interfaces.MorestoryClick;
import com.bharatarmy.Models.RegisterIntrestFilterDataModel;
import com.bharatarmy.R;
import com.bharatarmy.databinding.FragmentMatchTicketFilterBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.tabs.TabLayout;

public class MatchTicketFilterFragment extends BottomSheetDialogFragment implements ViewPager.OnPageChangeListener{
    FragmentMatchTicketFilterBinding fragmentMatchTicketFilterBinding;
    View rootView;
    Context mContext;
    MatchTicketFilterAdapter matchTicketFilterAdapter;
    RegisterIntrestFilterDataModel tournamentticketotherDataModel;
    MorestoryClick morestoryClick;

    public MatchTicketFilterFragment(RegisterIntrestFilterDataModel tournamentticketotherDataModel, MorestoryClick morestoryClick) {
        this.morestoryClick=morestoryClick;
        this.tournamentticketotherDataModel=tournamentticketotherDataModel;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mString = getArguments().getString("string");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentMatchTicketFilterBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_match_ticket_filter, container, false);

        rootView = fragmentMatchTicketFilterBinding.getRoot();
        mContext = getActivity().getApplicationContext();

        init();
        setListiner();
        setDataValue();
        return rootView;
    }

    public void init(){

    }
    public void setDataValue() {
        fragmentMatchTicketFilterBinding.tabLayoutTicketfilter.addTab(fragmentMatchTicketFilterBinding.tabLayoutTicketfilter.newTab().setText("TEAMS"),true);
        fragmentMatchTicketFilterBinding.tabLayoutTicketfilter.addTab(fragmentMatchTicketFilterBinding.tabLayoutTicketfilter.newTab().setText("VENUES"));

        fragmentMatchTicketFilterBinding.tabLayoutTicketfilter.setTabMode(TabLayout.MODE_FIXED);
        fragmentMatchTicketFilterBinding.tabLayoutTicketfilter.setTabGravity(TabLayout.GRAVITY_FILL);

        matchTicketFilterAdapter = new MatchTicketFilterAdapter(getChildFragmentManager(),
                fragmentMatchTicketFilterBinding.tabLayoutTicketfilter.getTabCount(),
                tournamentticketotherDataModel);
        fragmentMatchTicketFilterBinding.matchticketfilterpager.setOffscreenPageLimit(0);
        fragmentMatchTicketFilterBinding.matchticketfilterpager.setAdapter(matchTicketFilterAdapter);

    }

    public void setListiner() {
        fragmentMatchTicketFilterBinding.filtercloseLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        fragmentMatchTicketFilterBinding.filterapplyLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();
                morestoryClick.getmorestoryClick();


            }
        });

        fragmentMatchTicketFilterBinding.matchticketfilterpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(
                fragmentMatchTicketFilterBinding.tabLayoutTicketfilter));
        fragmentMatchTicketFilterBinding.tabLayoutTicketfilter.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                fragmentMatchTicketFilterBinding.matchticketfilterpager.setCurrentItem(tab.getPosition());
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
