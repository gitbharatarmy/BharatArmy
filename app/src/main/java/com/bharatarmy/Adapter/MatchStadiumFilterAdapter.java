package com.bharatarmy.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.bharatarmy.Fragment.StadiumFilterStadiumFragment;
import com.bharatarmy.Fragment.StadiumFilterTeamFragment;
import com.bharatarmy.Fragment.TickeFiltertStadiumFragment;
import com.bharatarmy.Fragment.TicketFilterTeamFragment;
import com.bharatarmy.Models.RegisterIntrestFilterDataModel;

public class MatchStadiumFilterAdapter extends FragmentStatePagerAdapter {
    //integer to count number of tabs
    int tabCount;
    RegisterIntrestFilterDataModel tournamentstadiumotherDataModel;
    //Constructor to the class
    public MatchStadiumFilterAdapter(FragmentManager fm, int tabCount, RegisterIntrestFilterDataModel tournamentstadiumotherDataModel) {
        super(fm);
//Initializing tab count
        this.tabCount = tabCount;
        this.tournamentstadiumotherDataModel=tournamentstadiumotherDataModel;
    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
//Returning the current tabs
        switch (position) {
            case 0:
                StadiumFilterTeamFragment tab1 = new StadiumFilterTeamFragment(tournamentstadiumotherDataModel);
                return tab1;
            case 1:
                StadiumFilterStadiumFragment tab2=new StadiumFilterStadiumFragment(tournamentstadiumotherDataModel);
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



