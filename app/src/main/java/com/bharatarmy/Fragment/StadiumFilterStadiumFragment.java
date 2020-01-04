package com.bharatarmy.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bharatarmy.Adapter.MatchFilterVenuesAdapter;
import com.bharatarmy.Adapter.StadiumFilterStadiumAdapter;
import com.bharatarmy.Models.InquiryStatusModel;
import com.bharatarmy.Models.RegisterIntrestFilterDataModel;
import com.bharatarmy.R;
import com.bharatarmy.databinding.FragmentStadiumFilterStadiumBinding;

import java.util.List;


public class StadiumFilterStadiumFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    FragmentStadiumFilterStadiumBinding fragmentStadiumFilterStadiumBinding;
    private View rootView;
    private Context mContext;

    StadiumFilterStadiumAdapter stadiumFilterStadiumAdapter;
    RegisterIntrestFilterDataModel registerIntrestFilterDataMode;
    List<InquiryStatusModel> teamVenuelist;


    public StadiumFilterStadiumFragment(RegisterIntrestFilterDataModel registerIntrestFilterDataMode) {
        // Required empty public constructor
        this.registerIntrestFilterDataMode=registerIntrestFilterDataMode;
    }


//    // TODO: Rename and change types and number of parameters
//    public static StadiumFilterStadiumFragment newInstance(String param1, String param2) {
//        StadiumFilterStadiumFragment fragment = new StadiumFilterStadiumFragment(registerIntrestFilterDataMode);
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentStadiumFilterStadiumBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_stadium_filter_stadium, container, false);

        rootView = fragmentStadiumFilterStadiumBinding.getRoot();
        mContext = getActivity().getApplicationContext();


        setUserVisibleHint(true);
        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && rootView != null) {
            // Refresh your fragment here
            setDataValue();
        }
    }

    public void setDataValue(){

        teamVenuelist=registerIntrestFilterDataMode.getStadiums();
        for (int i=0;i<teamVenuelist.size();i++){
            if (teamVenuelist.get(i).getVenueSelected()!=null){
                if (teamVenuelist.get(i).getVenueSelected().equalsIgnoreCase("1")){
                    teamVenuelist.get(i).setVenueSelected("1");
                }else{
                    teamVenuelist.get(i).setVenueSelected("0");
                }
            }else{
                teamVenuelist.get(i).setVenueSelected("0");
            }

        }
        teamVenuelist=registerIntrestFilterDataMode.getStadiums();
        stadiumFilterStadiumAdapter = new StadiumFilterStadiumAdapter(mContext, teamVenuelist);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        fragmentStadiumFilterStadiumBinding.venuesRcv.setLayoutManager(mLayoutManager);
        fragmentStadiumFilterStadiumBinding.venuesRcv.setItemAnimator(new DefaultItemAnimator());
        fragmentStadiumFilterStadiumBinding.venuesRcv.setAdapter(stadiumFilterStadiumAdapter);

    }
}
