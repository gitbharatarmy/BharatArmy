package com.bharatarmy.Fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bharatarmy.Adapter.FansPageAdapter;
import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.FragmentFansBinding;


public class FansFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View rootView;
    private Context mContext;
    FragmentFansBinding fragmentFansBinding;

    public FansFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FansFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FansFragment newInstance(String param1, String param2) {
        FansFragment fragment = new FansFragment();
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
        // Inflate the layout for this fragment
        fragmentFansBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_fans, container, false);

        rootView = fragmentFansBinding.getRoot();
        mContext = getActivity().getApplicationContext();
        AppConfiguration.position=3;

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setListiner();
        setDataValue();
    }

    public void setDataValue() {

        fragmentFansBinding.tabLayoutFans.addTab( fragmentFansBinding.tabLayoutFans.newTab().setText("IMAGE"), true);
        fragmentFansBinding.tabLayoutFans.addTab( fragmentFansBinding.tabLayoutFans.newTab().setText("VIDEO"));
        fragmentFansBinding.tabLayoutFans.setTabMode(TabLayout.MODE_FIXED);
        fragmentFansBinding.tabLayoutFans.setTabGravity(TabLayout.GRAVITY_FILL);

        FansPageAdapter adapter = new FansPageAdapter(getFragmentManager(),  fragmentFansBinding.tabLayoutFans.getTabCount());
//Adding adapter to pager
        fragmentFansBinding.pager.setAdapter(adapter);
// fragmentFansBinding.tabLayoutFans.setupWithViewPager(fragmentFansBinding.pager);

//        if (android.os.Build.VERSION.SDK_INT >= 21) {
//            fragmentFansBinding.view.setVisibility(View.GONE);
//        } else {
//            fragmentFansBinding.view.setVisibility(View.VISIBLE);
//        }
    }

    public void setListiner() {
        fragmentFansBinding.pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(
                fragmentFansBinding.tabLayoutFans));
        fragmentFansBinding.tabLayoutFans.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                fragmentFansBinding.pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
