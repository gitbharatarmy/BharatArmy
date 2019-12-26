package com.bharatarmy.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.bharatarmy.Fragment.MatchFilterTeamFragment;
import com.bharatarmy.Fragment.MatchFilterVenuesFragment;
import com.bharatarmy.Fragment.ScheduleStadiumFragment;
import com.bharatarmy.Fragment.ScheduleTeamFragment;
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
        this.tournamentotherDataModel=tournamentotherDataModel;
    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
//Returning the current tabs
        switch (position) {
            case 0:
                ScheduleTeamFragment tab1 = new ScheduleTeamFragment(tournamentotherDataModel);
                return tab1;
            case 1:
                ScheduleStadiumFragment tab2=new ScheduleStadiumFragment(tournamentotherDataModel);
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

