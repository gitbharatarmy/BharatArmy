package com.bharatarmy.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bharatarmy.Fragment.FansFragment;
import com.bharatarmy.Fragment.HistoryFragment;
import com.bharatarmy.Fragment.HomeFragment;
import com.bharatarmy.Fragment.MyProfileFragment;
import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.google.android.gms.common.internal.Objects;

// pass=mynameis8672952197
public class DashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    Context mContext;
    private FragmentManager fragmentManager = null;
    private Fragment fragment;
    int myid;
    boolean first_time_trans = true;
    BottomNavigationView bottomNavigationView;

    LinearLayout logout_linear, home_linear, history_linear, profile_linear, fans_linear;
    ImageView home_img, history_img, profile_img, fans_img;
    TextView home_txt, history_txt, profile_txt, fans_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        mContext = DashboardActivity.this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        logout_linear = (LinearLayout) findViewById(R.id.logout_linear);
        home_linear = (LinearLayout) findViewById(R.id.home_linear);
        history_linear = (LinearLayout) findViewById(R.id.history_linear);
        profile_linear = (LinearLayout) findViewById(R.id.profile_linear);
        fans_linear = (LinearLayout) findViewById(R.id.fans_linear);

        home_img = (ImageView) findViewById(R.id.home_img);
        history_img = (ImageView) findViewById(R.id.history_img);
        profile_img = (ImageView) findViewById(R.id.myprofile_img);
        fans_img = (ImageView) findViewById(R.id.fan_img);

        home_txt = (TextView) findViewById(R.id.home_txt);
        history_txt = (TextView) findViewById(R.id.history_txt);
        profile_txt = (TextView) findViewById(R.id.myprofile_txt);
        fans_txt = (TextView) findViewById(R.id.fan_txt);

        setSupportActionBar(toolbar);
//        toolbar.setNavigationIcon(null);
        getSupportActionBar().setTitle("");
        if (AppConfiguration.position == 0) {
            displayView(0);
        }
//        else if (AppConfiguration.position==1){
//            profile_img.setColorFilter(ContextCompat.getColor(mContext,
//                    R.color.sign_up));
//            profile_txt.setTextColor(ContextCompat.getColor(mContext,
//                    R.color.sign_up));
//            fragment = new MyProfileFragment();
//            loadFragment(fragment);
//        } else if (AppConfiguration.position==2){
//            fragment = new HistoryFragment();
//            loadFragment(fragment);
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        hide the drawericon
        toggle.setDrawerIndicatorEnabled(false);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
//        if (AppConfiguration.position == 1) {
//            bottomNavigationView.setSelectedItemId(R.id.navigation_myprofile);
//        }
//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId()) {
//                    case R.id.navigation_home:
//                        fragment = new HomeFragment();
//                        loadFragment(fragment);
//                        return true;
//                    case R.id.navigation_history:
//                        fragment= new HistoryFragment();
//                        loadFragment(fragment);
//                        return true;
//                    case R.id.navigation_myprofile:
////                        getSupportActionBar().setTitle(" My Profile");
//                        fragment = new MyProfileFragment();
//                        loadFragment(fragment);
//                        return true;
//                    case R.id.navigation_fan:
//                        fragment=new FansFragment();
//                        loadFragment(fragment);
//                        return  true;
//
//                }
//                return false;
//            }
//        });


        logout_linear.setOnClickListener(this);
        home_linear.setOnClickListener(this);
        history_linear.setOnClickListener(this);
        profile_linear.setOnClickListener(this);
        fans_linear.setOnClickListener(this);

    }

    public void setBottomView() {

    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().setCustomAnimations(0, 0);
        transaction.replace(R.id.frame_container, fragment);
//        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }

        if (AppConfiguration.position != 0) {
            displayView(0);
        } else {
            if (!Utils.checkNetwork(mContext)) {
                Utils.dismissDialog();
                Intent a = new Intent(Intent.ACTION_MAIN);
                a.addCategory(Intent.CATEGORY_HOME);
                a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(a);
                finish();
            } else {
                Intent a = new Intent(Intent.ACTION_MAIN);
                a.addCategory(Intent.CATEGORY_HOME);
                a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(a);
                finish();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.navigation_home) {


            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void displayView(int position) {
        switch (position) {
            case 0:

                home_img.setColorFilter(ContextCompat.getColor(mContext,
                        R.color.sign_up));
                home_txt.setTextColor(ContextCompat.getColor(mContext,
                        R.color.sign_up));
                home_linear.performClick();
                fragment = new HomeFragment();
                myid = fragment.getId();
                break;
            case 1:
                profile_linear.performClick();
                fragment = new MyProfileFragment();
                myid = fragment.getId();
                break;
            case 2:
                history_linear.performClick();
                fragment = new HistoryFragment();
                myid = fragment.getId();
                break;
            case 3:
                fans_linear.performClick();
                fragment = new FansFragment();
                myid = fragment.getId();
                break;
        }
        if (fragment != null) {

            FragmentManager fragmentManager = getSupportFragmentManager();

            if (fragment instanceof HomeFragment) {
                if (first_time_trans) {
                    first_time_trans = false;
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(0, 0)
                            .replace(R.id.frame_container, fragment).commit();

                } else {
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(0, 0)
                            .replace(R.id.frame_container, fragment).commit();
                }
            } else {
                fragmentManager.beginTransaction()
                        .setCustomAnimations(0, 0)
                        .replace(R.id.frame_container, fragment).commit();
            }

            // update selected item and title, then close the drawer
//            mDrawerList.setItemChecked(position, true);
//            mDrawerList.setSelection(position);
//            mDrawerLayout.closeDrawers();
        } else {
            // error in creating fragment
            Log.e("Dashboard", "Error in creating fragment");
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.logout_linear:
                AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                        DashboardActivity.this);
                alertDialog2.setTitle("Logout Confirm");
                alertDialog2.setMessage("Are you sure you want logout?");
                alertDialog2.setIcon(R.drawable.app_logo);
                alertDialog2.setCancelable(false);
                alertDialog2.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to execute after dialog
                                Utils.setPref(mContext, "LoginUserName", "");
                                Utils.setPref(mContext, "LoginEmailId", "");
                                Utils.setPref(mContext, "LoginPhoneNo", "");
                                Utils.setPref(mContext, "LoginProfilePic", "");
                                Utils.setPref(mContext, "EmailVerified", "");
                                Utils.setPref(mContext, "PhoneVerified", "");
                                Utils.setPref(mContext, "AppUserId", "");
                                Utils.setPref(mContext, "Gender", "");

                                Utils.ping(mContext, "You are logout suceessfully");
                                Intent ilogin = new Intent(mContext, LoginActivity.class);
                                startActivity(ilogin);
                                finish();

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
            case R.id.home_linear:
                home_img.setColorFilter(ContextCompat.getColor(mContext,
                        R.color.sign_up));
                home_txt.setTextColor(ContextCompat.getColor(mContext,
                        R.color.sign_up));

                history_img.setColorFilter(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color));
                history_txt.setTextColor(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color));
                profile_img.setColorFilter(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color));
                profile_txt.setTextColor(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color));
                fans_img.setColorFilter(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color));
                fans_txt.setTextColor(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color));
                fragment = new HomeFragment();
                loadFragment(fragment);
                break;
            case R.id.history_linear:
                history_img.setColorFilter(ContextCompat.getColor(mContext,
                        R.color.sign_up));
                history_txt.setTextColor(ContextCompat.getColor(mContext,
                        R.color.sign_up));

                home_img.setColorFilter(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color));
                home_txt.setTextColor(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color));

                profile_img.setColorFilter(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color));
                profile_txt.setTextColor(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color));
                fans_img.setColorFilter(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color));
                fans_txt.setTextColor(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color));
                fragment = new HistoryFragment();
                loadFragment(fragment);
                break;
            case R.id.profile_linear:
                profile_img.setColorFilter(ContextCompat.getColor(mContext,
                        R.color.sign_up));
                profile_txt.setTextColor(ContextCompat.getColor(mContext,
                        R.color.sign_up));

                home_img.setColorFilter(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color));
                home_txt.setTextColor(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color));

                history_img.setColorFilter(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color));
                history_txt.setTextColor(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color));
                fans_img.setColorFilter(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color));
                fans_txt.setTextColor(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color));
                fragment = new MyProfileFragment();
                loadFragment(fragment);
                break;
            case R.id.fans_linear:
                fans_img.setColorFilter(ContextCompat.getColor(mContext,
                        R.color.sign_up));
                fans_txt.setTextColor(ContextCompat.getColor(mContext,
                        R.color.sign_up));

                home_img.setColorFilter(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color));
                home_txt.setTextColor(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color));

                history_img.setColorFilter(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color));
                history_txt.setTextColor(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color));
                profile_img.setColorFilter(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color));
                profile_txt.setTextColor(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color));
                fragment = new FansFragment();
                loadFragment(fragment);
                break;
        }
    }
}
