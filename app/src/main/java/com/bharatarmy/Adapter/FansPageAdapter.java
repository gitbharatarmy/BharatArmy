package com.bharatarmy.Adapter;




import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.bharatarmy.Fragment.AlbumFragment;
import com.bharatarmy.Fragment.ImageFragment;
import com.bharatarmy.Fragment.VideoFragment;

import java.util.ArrayList;
import java.util.List;


public class FansPageAdapter extends FragmentStatePagerAdapter {
    //integer to count number of tabs
    int tabCount;

    //Constructor to the class
    public FansPageAdapter(FragmentManager fm, int tabCount) {
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
                ImageFragment tab1 = new ImageFragment();
                return tab1;
            case 1:
                VideoFragment tab2=new VideoFragment();
                return tab2;
            case 2:
                AlbumFragment tab3=new AlbumFragment();
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