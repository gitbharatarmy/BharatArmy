package com.bharatarmy.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.bharatarmy.Fragment.MatchFilterTeamFragment;
import com.bharatarmy.Fragment.MatchFilterVenuesFragment;
import com.bharatarmy.Models.RegisterIntrestFilterDataModel;

public class FilterPageAdapter extends FragmentStatePagerAdapter {
    //integer to count number of tabs
    int tabCount;
    RegisterIntrestFilterDataModel registerIntrestFilterDataMode;
    //Constructor to the class
    public FilterPageAdapter(FragmentManager fm, int tabCount, RegisterIntrestFilterDataModel registerIntrestFilterDataMode) {
        super(fm);
//Initializing tab count
        this.tabCount = tabCount;
        this.registerIntrestFilterDataMode=registerIntrestFilterDataMode;
    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
//Returning the current tabs
        switch (position) {
            case 0:
                MatchFilterTeamFragment tab1 = new MatchFilterTeamFragment(registerIntrestFilterDataMode);
                return tab1;
            case 1:
                MatchFilterVenuesFragment tab2=new MatchFilterVenuesFragment(registerIntrestFilterDataMode);
                return tab2;
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
