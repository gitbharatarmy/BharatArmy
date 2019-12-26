package com.bharatarmy.Fragment;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.bharatarmy.Adapter.FansPageAdapter;
import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.databinding.FragmentFansBinding;
import com.google.android.material.tabs.TabLayout;



public class FansFragment extends Fragment implements ViewPager.OnPageChangeListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View rootView;
    private Context mContext;
    FragmentFansBinding fragmentFansBinding;
    FansPageAdapter adapter;


    public FansFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FansFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FansFragment newInstance(String param1, String param2) {
        FansFragment fragment = new FansFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentFansBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_fans, container, false);

        rootView = fragmentFansBinding.getRoot();
        mContext = getActivity().getApplicationContext();
        AppConfiguration.position = 3;
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setListiner();
        setDataValue();
    }

    public void setDataValue() {

        fragmentFansBinding.tabLayoutFans.addTab(fragmentFansBinding.tabLayoutFans.newTab().setText("IMAGE"),true);
        fragmentFansBinding.tabLayoutFans.addTab(fragmentFansBinding.tabLayoutFans.newTab().setText("VIDEO"));
        fragmentFansBinding.tabLayoutFans.addTab(fragmentFansBinding.tabLayoutFans.newTab().setText("ALBUMS"));

        fragmentFansBinding.tabLayoutFans.getTabAt(0).setIcon(R.drawable.ic_image_icon);
        fragmentFansBinding.tabLayoutFans.getTabAt(1).setIcon(R.drawable.ic_video_icon);
        fragmentFansBinding.tabLayoutFans.getTabAt(2).setIcon(R.drawable.ic_album_icon);
        fragmentFansBinding.tabLayoutFans.setTabMode(TabLayout.MODE_FIXED);
        fragmentFansBinding.tabLayoutFans.setTabGravity(TabLayout.GRAVITY_FILL);

        adapter = new FansPageAdapter(getFragmentManager(), fragmentFansBinding.tabLayoutFans.getTabCount());
//Adding adapter to pager
        fragmentFansBinding.pager.setOffscreenPageLimit(2);
        fragmentFansBinding.pager.setAdapter(adapter);
fragmentFansBinding.pager.getAdapter().notifyDataSetChanged();

    }

    public void setListiner() {
        fragmentFansBinding.pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(
                fragmentFansBinding.tabLayoutFans));
        fragmentFansBinding.tabLayoutFans.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                fragmentFansBinding.pager.setCurrentItem(tab.getPosition());
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
