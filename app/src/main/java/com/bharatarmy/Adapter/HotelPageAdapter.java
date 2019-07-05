package com.bharatarmy.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.bharatarmy.Fragment.AlbumFragment;
import com.bharatarmy.Fragment.HotelDetailFragment;
import com.bharatarmy.Fragment.HotelReviewFragment;
import com.bharatarmy.Fragment.HotelRoomFragment;
import com.bharatarmy.Fragment.ImageFragment;
import com.bharatarmy.Fragment.VideoFragment;

public class HotelPageAdapter extends FragmentStatePagerAdapter {
    //integer to count number of tabs
    int tabCount;

    //Constructor to the class
    public HotelPageAdapter(FragmentManager fm, int tabCount) {
        super(fm);
//Initializing tab count
        this.tabCount = tabCount;
    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
//Returning the current tabs
        switch (position) {
            case 0:
                HotelDetailFragment tab1 = new HotelDetailFragment();
                return tab1;
            case 1:
                HotelRoomFragment tab2=new HotelRoomFragment();
                return tab2;
            case 2:
                HotelReviewFragment tab3=new HotelReviewFragment();
                return tab3;
            default:
                return null;
        }
    }

    //Overriden method getCount to get the number of tabs
    @Override
    public int getCount() {
        return tabCount;
    }
}