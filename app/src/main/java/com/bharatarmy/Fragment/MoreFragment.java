package com.bharatarmy.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bharatarmy.Activity.AppLoginActivity;
import com.bharatarmy.Activity.ContactusActivity;
import com.bharatarmy.Activity.DisplayAddedUserActivity;
import com.bharatarmy.Activity.DisplaySFAUserActivity;
import com.bharatarmy.Activity.InquriyActivity;
import com.bharatarmy.Activity.LoginwithEmailActivity;
import com.bharatarmy.Activity.MoreStoryActivity;
import com.bharatarmy.Activity.MyMediaActivity;
import com.bharatarmy.Activity.MyProfileActivity;
import com.bharatarmy.Activity.ProfileSettingActivity;
import com.bharatarmy.Models.MyScreenChnagesModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.FragmentMoreBinding;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.leinardi.android.speeddial.SpeedDialView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.concurrent.Executor;


public class MoreFragment extends Fragment implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    private View rootView;
    private Context mContext;

    FragmentMoreBinding fragmentMoreBinding;
    SpeedDialView speedDial;

    private GoogleApiClient mGoogleApiClient;
    private GoogleSignInOptions gso;
    GoogleSignInClient mGoogleSignInClient;

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

        EventBus.getDefault().register(this);
        init();
        setListiner();
        return rootView;
    }

    public void init() {
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();


        mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                .enableAutoManage(getActivity(), this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mGoogleApiClient.connect();


        if (Utils.retriveLoginData(mContext) != null) {
            fragmentMoreBinding.withloginLinear.setVisibility(View.VISIBLE);
            fragmentMoreBinding.withoutloginLinear.setVisibility(View.GONE);
            fragmentMoreBinding.header4Linear.setVisibility(View.VISIBLE);
            if (Utils.retriveLoginData(mContext).getFirstName()!=null && Utils.retriveLoginData(mContext).getLastName()!=null){
                fragmentMoreBinding.userNametxt.setText(Utils.retriveLoginData(mContext).getFirstName()+
                                                        " " +Utils.retriveLoginData(mContext).getLastName());
            }
            if (Utils.retriveLoginData(mContext).getProfilePicUrl()!=null){
                Utils.setImageInImageView(Utils.retriveLoginData(mContext).getProfilePicUrl(), fragmentMoreBinding.profileImage, mContext);
            }
            if (Utils.retriveLoginData(mContext).getIsBAAdmin().equals("1")) {
                fragmentMoreBinding.inquiryLinear.setVisibility(View.VISIBLE);
                fragmentMoreBinding.userLinear.setVisibility(View.VISIBLE);

            } else {
                fragmentMoreBinding.inquiryLinear.setVisibility(View.GONE);
                fragmentMoreBinding.userLinear.setVisibility(View.GONE);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(0, 0, 0, 0);
                fragmentMoreBinding.sportsInterestLinear.setLayoutParams(params);
            }
        }  else {
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
    }

    public void setListiner() {
        fragmentMoreBinding.userprofileLinear.setOnClickListener(this);
        fragmentMoreBinding.aboutusLinear.setOnClickListener(this);
        fragmentMoreBinding.contactusLinear.setOnClickListener(this);
        fragmentMoreBinding.logoutLinear.setOnClickListener(this);
        fragmentMoreBinding.inquiryLinear.setOnClickListener(this);
        fragmentMoreBinding.mediaLinear.setOnClickListener(this);
        fragmentMoreBinding.withoutloginLinear.setOnClickListener(this);
        fragmentMoreBinding.sportsInterestLinear.setOnClickListener(this);
        fragmentMoreBinding.dataEntryLinear.setOnClickListener(this);
        fragmentMoreBinding.settingLinear.setOnClickListener(this);
        fragmentMoreBinding.certificateLinear.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.userprofile_linear:
                Utils.handleClickEvent(mContext, fragmentMoreBinding.userprofileLinear);
                Intent myProfile = new Intent(mContext, MyProfileActivity.class);
                myProfile.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(myProfile);
                break;
            case R.id.aboutus_linear:
                Utils.handleClickEvent(mContext, fragmentMoreBinding.aboutusLinear);
                Intent aboutus = new Intent(mContext, MoreStoryActivity.class);
                aboutus.putExtra("Story Heading", "Ab Jeetega India");
                aboutus.putExtra("StroyUrl", "http://ajif.in/");
                aboutus.putExtra("whereTocome", "aboutus");
                aboutus.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(aboutus);
                break;
            case R.id.contactus_linear:
                Utils.handleClickEvent(mContext, fragmentMoreBinding.contactusLinear);
                Intent contactus = new Intent(mContext, ContactusActivity.class);
                contactus.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(contactus);
                break;
            case R.id.inquiry_linear:
                Utils.handleClickEvent(mContext, fragmentMoreBinding.inquiryLinear);
                Intent inquriy = new Intent(mContext, InquriyActivity.class);
                inquriy.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(inquriy);
                break;
            case R.id.logout_linear:
                Utils.handleClickEvent(mContext, fragmentMoreBinding.logoutLinear);
                AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(getActivity());
                alertDialog2.setTitle("Logout Confirm");
                alertDialog2.setMessage("Are you sure you want logout?");
                alertDialog2.setIcon(R.drawable.app_logo_new);
                alertDialog2.setCancelable(false);
                alertDialog2.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (Utils.getPref(mContext, "LoginType") != null) {
                                    if (Utils.getPref(mContext, "LoginType").equalsIgnoreCase("Email")) {
                                        emailSignOut();
                                    } else if (Utils.getPref(mContext, "LoginType").equalsIgnoreCase("Facebook")) {
                                        facebookSignOut();
                                    } else if (Utils.getPref(mContext, "LoginType").equalsIgnoreCase("Gmail")) {
                                        googleSignOut();
                                    }
                                }

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
                Utils.handleClickEvent(mContext, fragmentMoreBinding.mediaLinear);
                Utils.setPref(mContext, "cometonotification", "menu");
                Intent media = new Intent(mContext, MyMediaActivity.class);
                media.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(media);
                break;
            case R.id.withoutlogin_linear:
                Utils.handleClickEvent(mContext, fragmentMoreBinding.withoutloginLinear);
                Intent intent = new Intent(mContext, AppLoginActivity.class);
                intent.putExtra("whereTocomeLogin", "more");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.sports_interest_linear:
                Utils.setPref(mContext,"entryType","1");
                Utils.handleClickEvent(mContext, fragmentMoreBinding.sportsInterestLinear);
                Intent sportsintent = new Intent(mContext, DisplaySFAUserActivity.class);
                sportsintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(sportsintent);
                break;
            case R.id.data_entry_linear:
                Utils.handleClickEvent(mContext, fragmentMoreBinding.dataEntryLinear);
                Intent displayuserentry = new Intent(mContext, DisplayAddedUserActivity.class);
                displayuserentry.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(displayuserentry);
                break;
            case R.id.setting_linear:
                Utils.handleClickEvent(mContext,fragmentMoreBinding.settingLinear);
                Intent settingeIntent = new Intent(mContext, ProfileSettingActivity.class);
                settingeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(settingeIntent);
                break;
            case R.id.certificate_linear:
                Utils.setPref(mContext,"entryType","2");
                Utils.handleClickEvent(mContext, fragmentMoreBinding.sportsInterestLinear);
                Intent certificateintent = new Intent(mContext, DisplaySFAUserActivity.class);
                certificateintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(certificateintent);
                break;
        }
    }


    @Subscribe
    public void customEventReceived(MyScreenChnagesModel event) {
//        Log.d("event :", event.getMessage());
                                                                                                                                                                                                                          if (event.getMessage().equalsIgnoreCase("change")) {
            fragmentMoreBinding.userNametxt.setText(Utils.retriveLoginData(mContext).getFirstName()+" "+
                    Utils.retriveLoginData(mContext).getLastName());
            Utils.setImageInImageView(Utils.retriveLoginData(mContext).getProfilePicUrl(), fragmentMoreBinding.profileImage, mContext);
        }

    }

    public void googleSignOut() {
        if(mGoogleApiClient.isConnected()) {
            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                    new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status status) {
//                            // Write your code here to execute after dialog
                            Utils.removeLoginData(mContext);
                            Utils.setPref(mContext, "IsSkipLogin", "");
                            Utils.setPref(mContext, "IsLoginUser", "");
                            Utils.setPref(mContext, "UserName", "");
                            Utils.setPref(mContext,"entryType","");
                            Utils.ping(mContext, "You are logout suceessfully");
                            Intent ilogin = new Intent(mContext, AppLoginActivity.class);  //LoginwithEmailActivity
                            ilogin.putExtra("whereTocomeLogin", "more");
                            ilogin.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(ilogin);
                            getActivity().finish();
                        }
                    });



        }else{
            Utils.ping(mContext,"error occured");
        }
    }


    public void facebookSignOut() {
        new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/",
                null, HttpMethod.DELETE, new GraphRequest
                .Callback() {
            @Override
            public void onCompleted(GraphResponse graphResponse) {

                LoginManager.getInstance().logOut();

                // Write your code here to execute after dialog
                Utils.removeLoginData(mContext);
                Utils.setPref(mContext, "IsSkipLogin", "");
                Utils.setPref(mContext, "IsLoginUser", "");
                Utils.setPref(mContext, "UserName", "");
                Utils.setPref(mContext,"entryType","");
                Utils.ping(mContext, "You are logout suceessfully");
                Intent ilogin = new Intent(mContext, AppLoginActivity.class);  //LoginwithEmailActivity
                ilogin.putExtra("whereTocomeLogin", "more");
                ilogin.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(ilogin);
                getActivity().finish();

            }
        }).executeAsync();
    }

    public void emailSignOut() {
        // Write your code here to execute after dialog
        Utils.removeLoginData(mContext);
        Utils.setPref(mContext, "IsSkipLogin", "");
        Utils.setPref(mContext, "IsLoginUser", "");
        Utils.setPref(mContext, "UserName", "");
        Utils.setPref(mContext,"entryType","");
        Utils.ping(mContext, "You are logout suceessfully");
        Intent ilogin = new Intent(mContext, AppLoginActivity.class);  //LoginwithEmailActivity
        ilogin.putExtra("whereTocomeLogin", "more");
        ilogin.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(ilogin);
        getActivity().finish();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onPause() {
        super.onPause();
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.stopAutoManage(getActivity());
            mGoogleApiClient.disconnect();
        }
    }
}


