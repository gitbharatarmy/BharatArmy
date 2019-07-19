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

import com.bharatarmy.Adapter.MatchFilterTeamAdapter;
import com.bharatarmy.Adapter.MatchFilterVenuesAdapter;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.databinding.FragmentMatchFilterTeamBinding;
import com.bharatarmy.databinding.FragmentMatchFilterVenuesBinding;

import java.util.ArrayList;


public class MatchFilterVenuesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    FragmentMatchFilterVenuesBinding fragmentMatchFilterVenuesBinding;
    private View rootView;
    private Context mContext;
    ArrayList<TravelModel> matchTeamVenueList;;
    MatchFilterVenuesAdapter matchFilterVenuesAdapter;
    
    public MatchFilterVenuesFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static MatchFilterVenuesFragment newInstance(String param1, String param2) {
        MatchFilterVenuesFragment fragment = new MatchFilterVenuesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

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
        fragmentMatchFilterVenuesBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_match_filter_venues, container, false);

        rootView = fragmentMatchFilterVenuesBinding.getRoot();
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
        matchTeamVenueList = new ArrayList<TravelModel>();
        matchTeamVenueList.add(new TravelModel(R.drawable.flag_afghanistan, "Bistol Country Ground"));
        matchTeamVenueList.add(new TravelModel(R.drawable.flag_australia, "Cardiff Wales Stadium"));
        matchTeamVenueList.add(new TravelModel(R.drawable.flag_bangladesh, "Edgbaston"));
        matchTeamVenueList.add(new TravelModel(R.drawable.flag_united_kingdom, "Emirates Riverside"));
        matchTeamVenueList.add(new TravelModel(R.drawable.in_flag, "Headingley"));
        matchTeamVenueList.add(new TravelModel(R.drawable.nz_flag, "Lord's"));
        matchTeamVenueList.add(new TravelModel(R.drawable.pk_flag, "Old Trafford"));
        matchTeamVenueList.add(new TravelModel(R.drawable.south_flag, "The Ageas Bowl"));
        matchTeamVenueList.add(new TravelModel(R.drawable.flag_sri_lanka, "The Country Ground"));
        matchTeamVenueList.add(new TravelModel(R.drawable.wl, "The Oval"));
        matchTeamVenueList.add(new TravelModel(R.drawable.wl, "Trent Bridge"));


        matchFilterVenuesAdapter = new MatchFilterVenuesAdapter(mContext, matchTeamVenueList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        fragmentMatchFilterVenuesBinding.venuesRcv.setLayoutManager(mLayoutManager);
        fragmentMatchFilterVenuesBinding.venuesRcv.setItemAnimator(new DefaultItemAnimator());
        fragmentMatchFilterVenuesBinding.venuesRcv.setAdapter(matchFilterVenuesAdapter);

    }
}
