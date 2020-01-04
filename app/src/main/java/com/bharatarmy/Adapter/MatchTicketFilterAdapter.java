package com.bharatarmy.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.bharatarmy.Fragment.TickeFiltertStadiumFragment;
import com.bharatarmy.Fragment.TicketFilterTeamFragment;
import com.bharatarmy.Models.RegisterIntrestFilterDataModel;

public class MatchTicketFilterAdapter extends FragmentStatePagerAdapter {
    //integer to count number of tabs
    int tabCount;
    RegisterIntrestFilterDataModel tournamentticketotherDataModel;
    //Constructor to the class
    public MatchTicketFilterAdapter(FragmentManager fm, int tabCount, RegisterIntrestFilterDataModel tournamentticketotherDataModel) {
        super(fm);
//Initializing tab count
        this.tabCount = tabCount;
        this.tournamentticketotherDataModel=tournamentticketotherDataModel;
    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
//Returning the current tabs
        switch (position) {
            case 0:
                TicketFilterTeamFragment tab1 = new TicketFilterTeamFragment(tournamentticketotherDataModel);
                return tab1;
            case 1:
                TickeFiltertStadiumFragment tab2=new TickeFiltertStadiumFragment(tournamentticketotherDataModel);
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


