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

import com.bharatarmy.Adapter.MatchStadiumFilterAdapter;
import com.bharatarmy.Interfaces.MorestoryClick;
import com.bharatarmy.Models.RegisterIntrestFilterDataModel;
import com.bharatarmy.R;
import com.bharatarmy.databinding.FragmentMatchStadiumFilterBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.tabs.TabLayout;


public class MatchStadiumFilterFragment extends BottomSheetDialogFragment implements ViewPager.OnPageChangeListener{
    FragmentMatchStadiumFilterBinding fragmentMatchStadiumFilterBinding;
    View rootView;
    Context mContext;
    MatchStadiumFilterAdapter matchStadiumFilterAdapter;
    RegisterIntrestFilterDataModel tournamentstadiumotherDataModel;
    MorestoryClick morestoryClick;

    public MatchStadiumFilterFragment(RegisterIntrestFilterDataModel tournamentstadiumotherDataModel, MorestoryClick morestoryClick) {
        this.morestoryClick=morestoryClick;
        this.tournamentstadiumotherDataModel=tournamentstadiumotherDataModel;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mString = getArguments().getString("string");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentMatchStadiumFilterBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_match_stadium_filter, container, false);

        rootView = fragmentMatchStadiumFilterBinding.getRoot();
        mContext = getActivity().getApplicationContext();

        init();
        setListiner();
        setDataValue();
        return rootView;
    }

    public void init(){

    }
    public void setDataValue() {
        fragmentMatchStadiumFilterBinding.tabLayoutStadiumfilter.addTab(fragmentMatchStadiumFilterBinding.tabLayoutStadiumfilter.newTab().setText("TEAMS"),true);
        fragmentMatchStadiumFilterBinding.tabLayoutStadiumfilter.addTab(fragmentMatchStadiumFilterBinding.tabLayoutStadiumfilter.newTab().setText("VENUES"));

        fragmentMatchStadiumFilterBinding.tabLayoutStadiumfilter.setTabMode(TabLayout.MODE_FIXED);
        fragmentMatchStadiumFilterBinding.tabLayoutStadiumfilter.setTabGravity(TabLayout.GRAVITY_FILL);

        matchStadiumFilterAdapter = new MatchStadiumFilterAdapter(getChildFragmentManager(),
                fragmentMatchStadiumFilterBinding.tabLayoutStadiumfilter.getTabCount(),
                tournamentstadiumotherDataModel);
        fragmentMatchStadiumFilterBinding.matchstadiumfilterpager.setOffscreenPageLimit(0);
        fragmentMatchStadiumFilterBinding.matchstadiumfilterpager.setAdapter(matchStadiumFilterAdapter);

    }

    public void setListiner() {
        fragmentMatchStadiumFilterBinding.filtercloseLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        fragmentMatchStadiumFilterBinding.filterapplyLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();
                morestoryClick.getmorestoryClick();


            }
        });

        fragmentMatchStadiumFilterBinding.matchstadiumfilterpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(
                fragmentMatchStadiumFilterBinding.tabLayoutStadiumfilter));
        fragmentMatchStadiumFilterBinding.tabLayoutStadiumfilter.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                fragmentMatchStadiumFilterBinding.matchstadiumfilterpager.setCurrentItem(tab.getPosition());
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
