package com.bharatarmy.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.bharatarmy.Fragment.ScheduleCitiesFilterFragment;
import com.bharatarmy.Fragment.ScheduleMatchTypeFilterFragment;
import com.bharatarmy.Fragment.ScheduleStadiumFilterFragment;
import com.bharatarmy.Fragment.ScheduleTeamFilterFragment;
import com.bharatarmy.Models.RegisterIntrestFilterDataModel;

public class MatchScheduleAdvanceFilterAdapter extends FragmentStatePagerAdapter {
    //integer to count number of tabs
    int tabCount;
    RegisterIntrestFilterDataModel tournamentotherDataModel;

    //Constructor to the class
    public MatchScheduleAdvanceFilterAdapter(FragmentManager fm, int tabCount, RegisterIntrestFilterDataModel tournamentotherDataModel) {
        super(fm);
//Initializing tab count
        this.tabCount = tabCount;
        this.tournamentotherDataModel = tournamentotherDataModel;
    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
//Returning the current tabs
        switch (position) {
            case 0:
                ScheduleTeamFilterFragment tab1 = new ScheduleTeamFilterFragment(tournamentotherDataModel);
                return tab1;
            case 1:
                ScheduleStadiumFilterFragment tab2 = new ScheduleStadiumFilterFragment(tournamentotherDataModel);
                return tab2;
            case 2:
                ScheduleCitiesFilterFragment tab3 = new ScheduleCitiesFilterFragment(tournamentotherDataModel);
                return tab3;
//            case 3:
//                ScheduleMatchTypeFilterFragment tab4 = new ScheduleMatchTypeFilterFragment(tournamentotherDataModel);
//                return tab4;
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

