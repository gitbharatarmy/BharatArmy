package com.bharatarmy.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.bharatarmy.Fragment.PackageTabFragment;
import com.bharatarmy.Models.TravelDetailModel;

import java.util.List;

public class PackagePageAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    Fragment fragment = null;
    List<TravelDetailModel> travelPacakgeTabList;

    public PackagePageAdapter(FragmentManager fm, int NumOfTabs, List<TravelDetailModel> travelPacakgeTabList) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.travelPacakgeTabList=travelPacakgeTabList;
    }

    @Override
    public Fragment getItem(int position) {

        for (int i = 0; i < mNumOfTabs ; i++) {
            if (i == position) {
                fragment =PackageTabFragment.newInstance(travelPacakgeTabList);
                break;
            }
        }
        return fragment;

    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }
}