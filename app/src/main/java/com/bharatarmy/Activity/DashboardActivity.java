package com.bharatarmy.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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

import com.bharatarmy.Fragment.FTPFragment;
import com.bharatarmy.Fragment.FansFragment;
import com.bharatarmy.Fragment.HistoryFragment;
import com.bharatarmy.Fragment.HomeFragment;
import com.bharatarmy.Fragment.MyProfileFragment;
import com.bharatarmy.Fragment.StoryFragment;
import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.common.internal.Objects;

// pass=mynameis8672952197
public class DashboardActivity extends AppCompatActivity implements View.OnClickListener {
    Context mContext;
    private Fragment fragment;
    DrawerLayout drawer;
    LinearLayout home1_linear, home_linear, history_linear, profile_linear, fan_linear, fans_linear, story_linear, ftp_linear;
    ImageView home_img, history_img, profile_img, fans_img, fan_img, user_profile_img, story_img, ftp_img;
    TextView home_txt, history_txt, profile_txt, fan_txt, fans_txt, user_name_txt, story_txt, ftp_txt;
    NavigationView navigationView;
    Toolbar toolbar;
    private View navHeader;
    // index to identify current nav menu item
    public static int navItemIndex = 0;

    // tags used to attach the fragments
    private static final String TAG_HOME = "home";
    private static final String TAG_FANS = "fans";
    private static final String TAG_TRAVEL = "travel";
    private static final String TAG_EVENT = "event";
    private static final String TAG_FTP = "ftp";
    private static final String TAG_STORY = "story";
    private static final String TAG_HISTORY = "history";
    private static final String TAG_ABOUTUS = "aboutus";
    private static final String TAG_CONTACTUS = "contactus";
    private static final String TAG_RATEUS = "rateus";
    private static final String TAG_LOGOUT = "logout";
    public static String CURRENT_TAG = TAG_HOME;


    //  flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        mContext = DashboardActivity.this;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mHandler = new Handler();
        setSupportActionBar(toolbar);
        //toolbar.setNavigationIcon(null);
        getSupportActionBar().setTitle("");

        //initilize control
        init();

        // load nav menu header data
        loadNavHeader();

        setListiner();
        // initializing navigation menu
        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
        }
//        if (AppConfiguration.position == 0) {
//            displayView(0);
//        } else if (AppConfiguration.position == 1) {
//            displayView(AppConfiguration.position);
//        } else if (AppConfiguration.position == 2) {
//            displayView(AppConfiguration.position);
//        } else if (AppConfiguration.position == 3) {
//            displayView(AppConfiguration.position);
//        }


//        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
//        toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
////        hide the drawericon
////        toggle.setDrawerIndicatorEnabled(true);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();
    }

    public void init() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navHeader = navigationView.getHeaderView(0);
        user_profile_img = (ImageView) navHeader.findViewById(R.id.profile_image);
        user_name_txt = (TextView) navHeader.findViewById(R.id.textView);

        home1_linear = (LinearLayout) findViewById(R.id.home1_linear);
        home_linear = (LinearLayout) findViewById(R.id.home_linear);
        history_linear = (LinearLayout) findViewById(R.id.history_linear);
        profile_linear = (LinearLayout) findViewById(R.id.profile_linear);
        fan_linear = (LinearLayout) findViewById(R.id.fan_linear);

        fans_linear = (LinearLayout) findViewById(R.id.fans_linear);
        fans_img = (ImageView) findViewById(R.id.fans_img);
        fans_txt = (TextView) findViewById(R.id.fans_txt);

        story_linear = (LinearLayout) findViewById(R.id.story_linear);
        story_img = (ImageView) findViewById(R.id.story_img);
        story_txt = (TextView) findViewById(R.id.story_txt);

        ftp_linear = (LinearLayout) findViewById(R.id.ftp_linear);
        ftp_img = (ImageView) findViewById(R.id.ftp_img);
        ftp_txt = (TextView) findViewById(R.id.ftp_txt);

        home_img = (ImageView) findViewById(R.id.home_img);
        history_img = (ImageView) findViewById(R.id.history_img);
        profile_img = (ImageView) findViewById(R.id.myprofile_img);
        fan_img = (ImageView) findViewById(R.id.fan_img);

        home_txt = (TextView) findViewById(R.id.home_txt);
        history_txt = (TextView) findViewById(R.id.history_txt);
        profile_txt = (TextView) findViewById(R.id.myprofile_txt);
        fan_txt = (TextView) findViewById(R.id.fan_txt);
    }

    public void setListiner() {
        home1_linear.setOnClickListener(this);
        home_linear.setOnClickListener(this);
        history_linear.setOnClickListener(this);
        profile_linear.setOnClickListener(this);
        fan_linear.setOnClickListener(this);
        fans_linear.setOnClickListener(this);
        story_linear.setOnClickListener(this);
        ftp_linear.setOnClickListener(this);
    }

    private void loadNavHeader() {
        // name, website
        user_name_txt.setText(Utils.getPref(mContext, "LoginUserName"));


        // Loading profile image
        Glide.with(this)
                .load(Utils.getPref(mContext, "LoginProfilePic"))
                .thumbnail(0.5f)
                .into(user_profile_img);

        user_profile_img.setOnClickListener(this);

        // showing dot next to notifications label
//        navigationView.getMenu().getItem(3).setActionView(R.layout.menu_dot);
    }

    /***
     * Returns respected fragment that user
     * selected from navigation menu
     */
    private void loadHomeFragment() {
        // selecting appropriate nav menu item
        selectNavMenu();

        // set toolbar title
        setToolbarTitle();

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();

            return;
        }

        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame_container, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }


        //Closing drawer on item click
        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                // home
                HomeFragment homeFragment = new HomeFragment();
                return homeFragment;
//            case 1:
//                // FANS
//                FansFragment fansFragment = new FansFragment();
//                return fansFragment;
//            case 6:
//                //  HISTORY fragment
//                HistoryFragment historyFragment = new HistoryFragment();
//                return historyFragment;
//            case 3:
//                // notifications fragment
//                NotificationsFragment notificationsFragment = new NotificationsFragment();
//                return notificationsFragment;
//
//            case 4:
//                // settings fragment
//                SettingsFragment settingsFragment = new SettingsFragment();
//                return settingsFragment;
            default:
                return new HomeFragment();
        }
    }

    /***
     * Returns respected fragment that user
     * selected from navigation menu
     */
    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void setToolbarTitle() {
//        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }

    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
//                    case R.id.nav_home:
//                        navItemIndex = 0;
//                        CURRENT_TAG = TAG_HOME;
//                        break;
//                    case R.id.nav_fan:
//                        navItemIndex = 1;
//                        CURRENT_TAG = TAG_FANS;
//                        break;
//                    case R.id.nav_travel:
//                        navItemIndex = 2;
//                        CURRENT_TAG = TAG_TRAVEL;
//                        break;
//                    case R.id.nav_event:
//                        navItemIndex = 3;
//                        CURRENT_TAG = TAG_EVENT;
//                        break;
//                    case R.id.nav_ftp:
//                        navItemIndex = 4;
//                        CURRENT_TAG = TAG_FTP;
//                        break;
//                    case R.id.nav_story:
//                        navItemIndex = 5;
//                        CURRENT_TAG = TAG_STORY;
//                        break;
                    case R.id.nav_history:
                        Fragment fragment = new HistoryFragment();
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.setCustomAnimations(0, 0);
                        fragmentTransaction.replace(R.id.frame_container, fragment);
                        fragmentTransaction.commit();
                        drawer.closeDrawers();
                        navItemIndex = 1;
                        break;
                    case R.id.nav_aboutus:
                        navItemIndex = 3;
                        // launch new intent instead of loading fragment
                        Intent webviewIntent = new Intent(mContext, MoreStoryActivity.class);
                        webviewIntent.putExtra("Story Heading", "Ab Jeetega India");
                        webviewIntent.putExtra("StroyUrl", "http://ajif.in/");
                        webviewIntent.putExtra("whereTocome","aboutus");
                        webviewIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(webviewIntent);
                        drawer.closeDrawers();
                        break;
                    case R.id.nav_contactus:
                        navItemIndex = 4;
                        // launch new intent instead of loading fragment
//                        startActivity(new Intent(MainActivity.this, PrivacyPolicyActivity.class));
                        drawer.closeDrawers();
                        break;
                    case R.id.nav_rateus:
                        navItemIndex = 5;
                        // launch new intent instead of loading fragment
//                        startActivity(new Intent(MainActivity.this, PrivacyPolicyActivity.class));
                        drawer.closeDrawers();
                        break;
                    case R.id.nav_logout:
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
                                        Intent ilogin = new Intent(mContext, LoginNewActivity.class);
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
                        drawer.closeDrawers();
                        break;
                    default:
                        navItemIndex = 0;
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

//                loadHomeFragment();

                return true;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.splash_bg_color));
        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);
        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }

        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
        if (shouldLoadHomeFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than home
            if (navItemIndex != 0) {
                fans_img.setColorFilter(ContextCompat.getColor(mContext,
                        R.color.unselected_view));
                fans_txt.setTextColor(ContextCompat.getColor(mContext,
                        R.color.unselected_view));
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                loadHomeFragment();
                return;
            }
        }

        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        // show menu only when home fragment is selected
//        if (navItemIndex == 0) {
//            getMenuInflater().inflate(R.menu.main, menu);
//        }
//
//        // when fragment is notifications, load the menu created for notifications
//        if (navItemIndex == 3) {
//            getMenuInflater().inflate(R.menu.notifications, menu);
//        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_logout) {
//            Toast.makeText(getApplicationContext(), "Logout user!", Toast.LENGTH_LONG).show();
//            return true;
//        }
//
//        // user is in notifications fragment
//        // and selected 'Mark all as Read'
//        if (id == R.id.action_mark_all_read) {
//            Toast.makeText(getApplicationContext(), "All notifications marked as read!", Toast.LENGTH_LONG).show();
//        }
//
//        // user is in notifications fragment
//        // and selected 'Clear All'
//        if (id == R.id.action_clear_notifications) {
//            Toast.makeText(getApplicationContext(), "Clear all notifications!", Toast.LENGTH_LONG).show();
//        }

        return super.onOptionsItemSelected(item);
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().setCustomAnimations(0, 0);
        transaction.replace(R.id.frame_container, fragment);
        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
                fan_img.setColorFilter(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color));
                fan_txt.setTextColor(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color));
                navItemIndex = 0;
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
                fan_img.setColorFilter(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color));
                fan_txt.setTextColor(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color));
                navItemIndex = 1;
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
                fan_img.setColorFilter(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color));
                fan_txt.setTextColor(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color));
                navItemIndex = 2;
                fragment = new MyProfileFragment();
                loadFragment(fragment);
                break;
            case R.id.fan_linear:
                fan_img.setColorFilter(ContextCompat.getColor(mContext,
                        R.color.sign_up));
                fan_txt.setTextColor(ContextCompat.getColor(mContext,
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
                navItemIndex = 3;
                fragment = new FansFragment();
                loadFragment(fragment);
                break;
            case R.id.fans_linear:
                fans_img.setColorFilter(ContextCompat.getColor(mContext,
                        R.color.heading_bg));
                fans_txt.setTextColor(ContextCompat.getColor(mContext,
                        R.color.heading_bg));
                story_img.setColorFilter(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color));
                story_txt.setTextColor(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color));
                ftp_img.setColorFilter(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color));
                ftp_txt.setTextColor(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color));
                navItemIndex = 3;
                fragment = new FansFragment();
                loadFragment(fragment);
                break;
            case R.id.profile_image:
                navItemIndex = 2;
                fragment = new MyProfileFragment();
                loadFragment(fragment);
                drawer.closeDrawers();
                break;

            case R.id.home1_linear:
                fans_img.setColorFilter(ContextCompat.getColor(mContext,
                        R.color.unselected_view));
                fans_txt.setTextColor(ContextCompat.getColor(mContext,
                        R.color.unselected_view));
                story_img.setColorFilter(ContextCompat.getColor(mContext,
                        R.color.unselected_view));
                story_txt.setTextColor(ContextCompat.getColor(mContext,
                        R.color.unselected_view));

                ftp_img.setColorFilter(ContextCompat.getColor(mContext,
                        R.color.unselected_view));
                ftp_txt.setTextColor(ContextCompat.getColor(mContext,
                        R.color.unselected_view));

                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                loadHomeFragment();
                break;
            case R.id.story_linear:
                story_img.setColorFilter(ContextCompat.getColor(mContext,
                        R.color.heading_bg));
                story_txt.setTextColor(ContextCompat.getColor(mContext,
                        R.color.heading_bg));

                fans_img.setColorFilter(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color));
                fans_txt.setTextColor(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color));

                ftp_img.setColorFilter(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color));
                ftp_txt.setTextColor(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color));

                navItemIndex = 6;
                fragment = new StoryFragment();
                loadFragment(fragment);
                break;
            case R.id.ftp_linear:
                ftp_img.setColorFilter(ContextCompat.getColor(mContext,
                        R.color.heading_bg));
                ftp_txt.setTextColor(ContextCompat.getColor(mContext,
                        R.color.heading_bg));

                story_img.setColorFilter(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color));
                story_txt.setTextColor(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color));

                fans_img.setColorFilter(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color));
                fans_txt.setTextColor(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color));
                navItemIndex = 7;
                fragment = new FTPFragment();
                loadFragment(fragment);
                break;
        }
    }

}
