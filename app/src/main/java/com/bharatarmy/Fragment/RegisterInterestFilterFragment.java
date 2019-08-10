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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bharatarmy.Adapter.FilterPageAdapter;
import com.bharatarmy.Interfaces.MorestoryClick;
import com.bharatarmy.Models.RegisterIntrestFilterDataModel;
import com.bharatarmy.R;
import com.bharatarmy.databinding.FragmentRegisterInterestFilterBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;


public class RegisterInterestFilterFragment extends BottomSheetDialogFragment implements ViewPager.OnPageChangeListener{
    FragmentRegisterInterestFilterBinding fragmentRegisterInterestFilterBinding;
    View rootView;
    Context mContext;
    FilterPageAdapter filterPageAdapter;
    RegisterIntrestFilterDataModel registerIntrestFilterDataMode;
    MorestoryClick morestoryClick;

    public RegisterInterestFilterFragment(RegisterIntrestFilterDataModel registerIntrestFilterDataModel, MorestoryClick morestoryClick) {
        this.morestoryClick=morestoryClick;
        this.registerIntrestFilterDataMode=registerIntrestFilterDataModel;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mString = getArguments().getString("string");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentRegisterInterestFilterBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_register_interest_filter, container, false);

        rootView = fragmentRegisterInterestFilterBinding.getRoot();
        mContext = getActivity().getApplicationContext();

        init();
        setListiner();
        setDataValue();
        return rootView;
    }

    public void init(){

    }
    public void setDataValue() {
        fragmentRegisterInterestFilterBinding.tabLayoutRegisterfilter.addTab(fragmentRegisterInterestFilterBinding.tabLayoutRegisterfilter.newTab().setText("TEAMS"),true);
        fragmentRegisterInterestFilterBinding.tabLayoutRegisterfilter.addTab(fragmentRegisterInterestFilterBinding.tabLayoutRegisterfilter.newTab().setText("VENUES"));

        fragmentRegisterInterestFilterBinding.tabLayoutRegisterfilter.setTabMode(TabLayout.MODE_FIXED);
        fragmentRegisterInterestFilterBinding.tabLayoutRegisterfilter.setTabGravity(TabLayout.GRAVITY_FILL);

        filterPageAdapter = new FilterPageAdapter(getChildFragmentManager(),
                fragmentRegisterInterestFilterBinding.tabLayoutRegisterfilter.getTabCount(),
                registerIntrestFilterDataMode);
        fragmentRegisterInterestFilterBinding.filterpager.setOffscreenPageLimit(0);
        fragmentRegisterInterestFilterBinding.filterpager.setAdapter(filterPageAdapter);

    }

    public void setListiner() {
        fragmentRegisterInterestFilterBinding.filtercloseLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        fragmentRegisterInterestFilterBinding.filterapplyLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
morestoryClick.getmorestoryClick();


            }
        });

        fragmentRegisterInterestFilterBinding.filterpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(
                fragmentRegisterInterestFilterBinding.tabLayoutRegisterfilter));
        fragmentRegisterInterestFilterBinding.tabLayoutRegisterfilter.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                fragmentRegisterInterestFilterBinding.filterpager.setCurrentItem(tab.getPosition());
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
