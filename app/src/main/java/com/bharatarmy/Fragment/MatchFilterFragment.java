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

import com.bharatarmy.Adapter.FansPageAdapter;
import com.bharatarmy.Adapter.FilterPageAdapter;
import com.bharatarmy.R;
import com.bharatarmy.databinding.FragmentMatchFilterBinding;
import com.bharatarmy.databinding.OffersBottomSheetBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.tabs.TabLayout;


public class MatchFilterFragment extends BottomSheetDialogFragment implements ViewPager.OnPageChangeListener{
    FragmentMatchFilterBinding fragmentMatchFilterBinding;
    View rootView;
    Context mContext;
    FilterPageAdapter filterPageAdapter;


    static MatchFilterFragment newInstance() {
        MatchFilterFragment f = new MatchFilterFragment();
        Bundle args = new Bundle();
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mString = getArguments().getString("string");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentMatchFilterBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_match_filter, container, false);

        rootView = fragmentMatchFilterBinding.getRoot();
        mContext = getActivity().getApplicationContext();

        init();
        setListiner();
        setDataValue();
        return rootView;
    }

    public void init(){
     
    }
    public void setDataValue() {
        fragmentMatchFilterBinding.tabLayoutFilter.addTab(fragmentMatchFilterBinding.tabLayoutFilter.newTab().setText("TEAMS"),true);
        fragmentMatchFilterBinding.tabLayoutFilter.addTab(fragmentMatchFilterBinding.tabLayoutFilter.newTab().setText("VENUES"));

        fragmentMatchFilterBinding.tabLayoutFilter.setTabMode(TabLayout.MODE_FIXED);
        fragmentMatchFilterBinding.tabLayoutFilter.setTabGravity(TabLayout.GRAVITY_FILL);

        filterPageAdapter = new FilterPageAdapter(getChildFragmentManager(), fragmentMatchFilterBinding.tabLayoutFilter.getTabCount());
        fragmentMatchFilterBinding.filterpager.setOffscreenPageLimit(0);
        fragmentMatchFilterBinding.filterpager.setAdapter(filterPageAdapter);

    }

    public void setListiner() {
        fragmentMatchFilterBinding.filtercloseLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        fragmentMatchFilterBinding.filterapplyLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        fragmentMatchFilterBinding.filterpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(
                fragmentMatchFilterBinding.tabLayoutFilter));
        fragmentMatchFilterBinding.tabLayoutFilter.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                fragmentMatchFilterBinding.filterpager.setCurrentItem(tab.getPosition());
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
