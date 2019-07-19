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

import com.bharatarmy.Activity.ImageEditProfilePickerActivity;
import com.bharatarmy.Adapter.MatchFilterTeamAdapter;
import com.bharatarmy.Adapter.TravelMatchDetailRecyclerAdapter;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.databinding.FragmentMatchFilterTeamBinding;
import com.leinardi.android.speeddial.SpeedDialOverlayLayout;
import com.leinardi.android.speeddial.SpeedDialView;

import java.util.ArrayList;


public class MatchFilterTeamFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    FragmentMatchFilterTeamBinding fragmentMatchFilterTeamBinding;
    private View rootView;
    private Context mContext;
    ArrayList<TravelModel> matchTeamFlagList;;
    MatchFilterTeamAdapter matchFilterTeamAdapter;
    public MatchFilterTeamFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static MatchFilterTeamFragment newInstance(String param1, String param2) {
        MatchFilterTeamFragment fragment = new MatchFilterTeamFragment();
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
        fragmentMatchFilterTeamBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_match_filter_team, container, false);

        rootView = fragmentMatchFilterTeamBinding.getRoot();
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
        matchTeamFlagList = new ArrayList<TravelModel>();
        matchTeamFlagList.add(new TravelModel(R.drawable.flag_afghanistan, "Afghaanistan"));
        matchTeamFlagList.add(new TravelModel(R.drawable.flag_australia, "Australia"));
        matchTeamFlagList.add(new TravelModel(R.drawable.flag_bangladesh, "Bangladesh"));
        matchTeamFlagList.add(new TravelModel(R.drawable.flag_united_kingdom, "England"));
        matchTeamFlagList.add(new TravelModel(R.drawable.in_flag, "India"));
        matchTeamFlagList.add(new TravelModel(R.drawable.nz_flag, "New Zealand"));
        matchTeamFlagList.add(new TravelModel(R.drawable.pk_flag, "Pakistan"));
        matchTeamFlagList.add(new TravelModel(R.drawable.south_flag, "South Africa"));
        matchTeamFlagList.add(new TravelModel(R.drawable.flag_sri_lanka, "Sri Lanka"));
        matchTeamFlagList.add(new TravelModel(R.drawable.wl, "West Indies"));


        matchFilterTeamAdapter = new MatchFilterTeamAdapter(mContext, matchTeamFlagList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        fragmentMatchFilterTeamBinding.teamsRcv.setLayoutManager(mLayoutManager);
        fragmentMatchFilterTeamBinding.teamsRcv.setItemAnimator(new DefaultItemAnimator());
        fragmentMatchFilterTeamBinding.teamsRcv.setAdapter(matchFilterTeamAdapter);

    }
}
