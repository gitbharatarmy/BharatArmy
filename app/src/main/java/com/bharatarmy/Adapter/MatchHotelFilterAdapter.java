package com.bharatarmy.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.bharatarmy.Fragment.HotelFilterCityFragment;
import com.bharatarmy.Fragment.HotelFilterRatingFragment;
import com.bharatarmy.Fragment.HotelFilterTeamFragment;
import com.bharatarmy.Fragment.TickeFiltertStadiumFragment;
import com.bharatarmy.Fragment.TicketFilterTeamFragment;
import com.bharatarmy.Models.RegisterIntrestFilterDataModel;
import com.bharatarmy.Models.TravelModel;

import java.util.ArrayList;

public class MatchHotelFilterAdapter extends FragmentStatePagerAdapter {
    //integer to count number of tabs
    int tabCount;
    RegisterIntrestFilterDataModel tournamenthotelotherDataModel;
    ArrayList<TravelModel> tournamnetcitylist;
    ArrayList<TravelModel> tournamnetratinglist;
    ArrayList<TravelModel> tournamnetbyteamlist;

    public MatchHotelFilterAdapter(FragmentManager fm, int tabCount,
                                   RegisterIntrestFilterDataModel tournamenthotelotherDataModel,
                                   ArrayList<TravelModel> tournamnetcitylist,
                                   ArrayList<TravelModel> tournamnetratinglist,
                                   ArrayList<TravelModel> tournamnetbyteamlist) {
        super(fm);
//Initializing tab count
        this.tabCount = tabCount;
        this.tournamenthotelotherDataModel=tournamenthotelotherDataModel;
        this.tournamnetcitylist=tournamnetcitylist;
        this.tournamnetratinglist=tournamnetratinglist;
        this.tournamnetbyteamlist=tournamnetbyteamlist;
    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
//Returning the current tabs
        switch (position) {
            case 0:
                HotelFilterRatingFragment tab1=new HotelFilterRatingFragment(tournamnetratinglist);//tournamenthotelotherDataModel
                return tab1;
            case 1:
                HotelFilterTeamFragment tab2=new HotelFilterTeamFragment(tournamnetbyteamlist);//tournamenthotelotherDataModel
                return tab2;
            case 2:
                HotelFilterCityFragment tab3 = new HotelFilterCityFragment(tournamnetcitylist);//tournamenthotelotherDataModel
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


