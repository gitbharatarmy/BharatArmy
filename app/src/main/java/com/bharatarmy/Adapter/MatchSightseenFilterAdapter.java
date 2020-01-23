package com.bharatarmy.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.bharatarmy.Fragment.SightseenFilterSightseenFragment;
import com.bharatarmy.Fragment.SightseenFilterTeamFragment;
import com.bharatarmy.Fragment.StadiumFilterStadiumFragment;
import com.bharatarmy.Fragment.StadiumFilterTeamFragment;
import com.bharatarmy.Models.RegisterIntrestFilterDataModel;
import com.bharatarmy.Models.TravelModel;

import java.util.ArrayList;

public class MatchSightseenFilterAdapter extends FragmentStatePagerAdapter {
    //integer to count number of tabs
    int tabCount;
    RegisterIntrestFilterDataModel tournamentsightseenotherDataModel;
    ArrayList<TravelModel> tournamentcitylist;
    //Constructor to the class
    public MatchSightseenFilterAdapter(FragmentManager fm, int tabCount,ArrayList<TravelModel> tournamentcitylist) {
        super(fm);
//Initializing tab count
        this.tabCount = tabCount;
//        this.tournamentsightseenotherDataModel=tournamentsightseenotherDataModel;
        this.tournamentcitylist=tournamentcitylist;
    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
//Returning the current tabs
        switch (position) {
            case 0:
                SightseenFilterSightseenFragment tab2=new SightseenFilterSightseenFragment(tournamentcitylist);
                return tab2;
//                SightseenFilterTeamFragment tab1 = new SightseenFilterTeamFragment(tournamentsightseenotherDataModel);
//                return tab1;
//            case 1:
//                SightseenFilterSightseenFragment tab2=new SightseenFilterSightseenFragment(tournamentsightseenotherDataModel);
//                return tab2;
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




