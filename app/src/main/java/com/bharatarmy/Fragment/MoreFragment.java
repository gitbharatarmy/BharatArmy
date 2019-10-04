package com.bharatarmy.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.bharatarmy.Activity.ContactusActivity;
import com.bharatarmy.Activity.InquriyActivity;
import com.bharatarmy.Activity.LoginActivity;
import com.bharatarmy.Activity.MoreStoryActivity;
import com.bharatarmy.Activity.MyMediaActivity;
import com.bharatarmy.Activity.MyProfileActivity;
import com.bharatarmy.Activity.SportsInterestActivity;
import com.bharatarmy.Activity.UserEntryActivity;
import com.bharatarmy.Adapter.StoryCategoryAdapter;
import com.bharatarmy.Adapter.StoryLsitAdapter;
import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.Models.ImageDetailModel;
import com.bharatarmy.Models.ImageMainModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.ApiHandler;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.FragmentMoreBinding;
import com.bharatarmy.databinding.FragmentStoryBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.leinardi.android.speeddial.SpeedDialView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.RetrofitError;
import retrofit.client.Response;


public class MoreFragment extends Fragment implements View.OnClickListener {

    private View rootView;
    private Context mContext;

    FragmentMoreBinding fragmentMoreBinding;
    SpeedDialView speedDial;


    public MoreFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static MoreFragment newInstance(String param1, String param2) {
        MoreFragment fragment = new MoreFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentMoreBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_more, container, false);

        rootView = fragmentMoreBinding.getRoot();
        mContext = getActivity().getApplicationContext();
        speedDial = getActivity().findViewById(R.id.speedDial);
        speedDial.setVisibility(View.GONE);


        setListiner();
        return rootView;
    }

    public void setListiner() {
        if (Utils.retriveLoginData(mContext) != null) {
            fragmentMoreBinding.withloginLinear.setVisibility(View.VISIBLE);
            fragmentMoreBinding.withoutloginLinear.setVisibility(View.GONE);
            fragmentMoreBinding.header4Linear.setVisibility(View.VISIBLE);
            fragmentMoreBinding.userNametxt.setText(Utils.retriveLoginData(mContext).getName());

            if (Utils.retriveLoginData(mContext).getIsBAAdmin().equals("1")) {
                fragmentMoreBinding.header3Linear.setVisibility(View.VISIBLE);
            } else {
                fragmentMoreBinding.header3Linear.setVisibility(View.GONE);
            }


        } else {
            fragmentMoreBinding.header3Linear.setVisibility(View.GONE);
            fragmentMoreBinding.header4Linear.setVisibility(View.GONE);
            fragmentMoreBinding.withloginLinear.setVisibility(View.GONE);
            fragmentMoreBinding.withoutloginLinear.setVisibility(View.VISIBLE);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 40, 0, 120);
            fragmentMoreBinding.settingLinear.setLayoutParams(params);

        }


        fragmentMoreBinding.userprofileLinear.setOnClickListener(this);
        fragmentMoreBinding.aboutusLinear.setOnClickListener(this);
        fragmentMoreBinding.contactusLinear.setOnClickListener(this);
        fragmentMoreBinding.logoutLinear.setOnClickListener(this);
        fragmentMoreBinding.inquiryLinear.setOnClickListener(this);
        fragmentMoreBinding.mediaLinear.setOnClickListener(this);
        fragmentMoreBinding.withoutloginLinear.setOnClickListener(this);
        fragmentMoreBinding.sportsInterestLinear.setOnClickListener(this);
        fragmentMoreBinding.dataEntryLinear.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.userprofile_linear:
                Intent myProfile = new Intent(mContext, MyProfileActivity.class);
                myProfile.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(myProfile);
                break;
            case R.id.aboutus_linear:
                Intent aboutus = new Intent(mContext, MoreStoryActivity.class);
                aboutus.putExtra("Story Heading", "Ab Jeetega India");
                aboutus.putExtra("StroyUrl", "http://ajif.in/");
                aboutus.putExtra("whereTocome", "aboutus");
                aboutus.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(aboutus);
                break;
            case R.id.contactus_linear:
                Intent contactus = new Intent(mContext, ContactusActivity.class);
                contactus.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(contactus);
                break;
            case R.id.inquiry_linear:
                Intent inquriy = new Intent(mContext, InquriyActivity.class);
                inquriy.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(inquriy);
                break;
            case R.id.logout_linear:
                AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(getActivity());
                alertDialog2.setTitle("Logout Confirm");
                alertDialog2.setMessage("Are you sure you want logout?");
                alertDialog2.setIcon(R.drawable.app_logo);
                alertDialog2.setCancelable(false);
                alertDialog2.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to execute after dialog
                                Utils.removeLoginData(mContext);
                                Utils.setPref(mContext, "IsSkipLogin", "");
                                Utils.setPref(mContext, "IsLoginUser", "");
                                Utils.ping(mContext, "You are logout suceessfully");
                                Intent ilogin = new Intent(mContext, LoginActivity.class);
                                ilogin.putExtra("whereTocomeLogin", "logout");
                                startActivity(ilogin);

                            }
                        });
                alertDialog2.setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to execute after dialog

                                dialog.cancel();
                            }
                        });
                alertDialog2.show();
                break;
            case R.id.media_linear:
                Utils.setPref(mContext, "cometonotification", "menu");
                Intent media = new Intent(mContext, MyMediaActivity.class);
                media.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(media);
                break;
            case R.id.withoutlogin_linear:
                Intent intent = new Intent(mContext, LoginActivity.class);
                intent.putExtra("whereTocomeLogin", "more");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case R.id.sports_interest_linear:
                Intent sportsintent = new Intent(mContext, SportsInterestActivity.class);
                sportsintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(sportsintent);
                break;
            case R.id.data_entry_linear:
                Intent userentry = new Intent(mContext, UserEntryActivity.class);
                userentry.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(userentry);
                break;
        }
    }
}


