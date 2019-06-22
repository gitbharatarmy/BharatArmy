package com.bharatarmy.Activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView

import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.bharatarmy.Fragment.*

import com.bharatarmy.R
import com.bharatarmy.Utility.AppConfiguration
import com.bharatarmy.Utility.Utils
import com.bumptech.glide.Glide
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.leinardi.android.speeddial.SpeedDialOverlayLayout
import com.leinardi.android.speeddial.SpeedDialView
import kotlinx.android.synthetic.main.app_bar_dashboard.*
import kotlinx.android.synthetic.main.fragment_story_category.*

class DashboardActivity : AppCompatActivity(), View.OnClickListener, TravelFragment.OnItemClick, StoryFragment.OnItemClick, StoryCategoryFragment.OnItemClick {


    internal lateinit var mContext: Context
    private var fragment: Fragment? = null
    internal lateinit var drawer: DrawerLayout
    internal lateinit var home1_linear: LinearLayout
    internal lateinit var home_linear: LinearLayout
    internal lateinit var history_linear: LinearLayout
    internal lateinit var profile_linear: LinearLayout
    internal lateinit var fan_linear: LinearLayout
    internal lateinit var fans_linear: LinearLayout
    internal lateinit var story_linear: LinearLayout
    internal lateinit var ftp_linear: LinearLayout
    internal lateinit var travel_linear: LinearLayout
    internal lateinit var home_img: ImageView
    internal lateinit var history_img: ImageView
    internal lateinit var profile_img: ImageView
    internal lateinit var fans_img: ImageView
    internal lateinit var fan_img: ImageView
    internal lateinit var user_profile_img: ImageView
    internal lateinit var proflie_linear:LinearLayout
    internal lateinit var story_img: ImageView
    internal lateinit var ftp_img: ImageView
    internal lateinit var travel_img: ImageView
    internal lateinit var home_txt: TextView
    internal lateinit var history_txt: TextView
    internal lateinit var profile_txt: TextView
    internal lateinit var fan_txt: TextView
    internal lateinit var fans_txt: TextView
    internal lateinit var user_name_txt: TextView
    internal lateinit var story_txt: TextView
    internal lateinit var ftp_txt: TextView
    internal lateinit var travel_txt: TextView
    internal lateinit var navigationView: NavigationView
    internal lateinit var toolbar: Toolbar
    private var navHeader: View? = null
    internal lateinit var old_menu: CardView
    internal lateinit var new_menu: CardView
    internal lateinit var filter_fab: FloatingActionButton
    internal lateinit var speedDial: SpeedDialView
    internal lateinit var overlay: SpeedDialOverlayLayout
    internal  lateinit var main_page_scrollview:ScrollView


    //  flag to load home fragment when user presses back key
    private val shouldLoadHomeFragOnBackPress = true
    private var mHandler: Handler? = null


    private// home
    val homeFragment: Fragment
        get() {
            when (navItemIndex) {
                0 -> {

                    return HomeFragment()
                }
                else -> return HomeFragment()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        mContext = this@DashboardActivity
        toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        mHandler = Handler()
        setSupportActionBar(toolbar)
        //toolbar.setNavigationIcon(null);
        supportActionBar!!.title = ""

        //initilize control
        init()
        bottomNavigationView()

        // load nav menu header data
        loadNavHeader()

        setListiner()
        // initializing navigation menu
        setUpNavigationView()

        if (savedInstanceState == null) {
            navItemIndex = 0
            CURRENT_TAG = TAG_HOME
            loadHomeFragment()
        }

        //        showHide();
    }

    fun init() {
        drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        navigationView = findViewById<View>(R.id.nav_view) as NavigationView
        navHeader = navigationView.getHeaderView(0)
        user_profile_img = navHeader!!.findViewById<View>(R.id.profile_image) as ImageView
        proflie_linear=navHeader!!.findViewById<View>(R.id.proflie_linear)as LinearLayout
        user_name_txt = navHeader!!.findViewById<View>(R.id.textView) as TextView



        home1_linear = findViewById<View>(R.id.home1_linear) as LinearLayout
        home_linear = findViewById<View>(R.id.home_linear) as LinearLayout
        history_linear = findViewById<View>(R.id.history_linear) as LinearLayout
        profile_linear = findViewById<View>(R.id.profile_linear) as LinearLayout
        fan_linear = findViewById<View>(R.id.fan_linear) as LinearLayout

        old_menu = findViewById<View>(R.id.old_menu) as CardView
        new_menu = findViewById<View>(R.id.new_menu) as CardView

        fans_linear = findViewById<View>(R.id.fans_linear) as LinearLayout
        fans_img = findViewById<View>(R.id.fans_img) as ImageView
        fans_txt = findViewById<View>(R.id.fans_txt) as TextView

        travel_linear = findViewById<View>(R.id.travel_linear) as LinearLayout
        travel_img = findViewById<View>(R.id.travel_img) as ImageView
        travel_txt = findViewById<View>(R.id.travel_txt) as TextView

        story_linear = findViewById<View>(R.id.story_linear) as LinearLayout
        story_img = findViewById<View>(R.id.story_img) as ImageView
        story_txt = findViewById<View>(R.id.story_txt) as TextView

        ftp_linear = findViewById<View>(R.id.ftp_linear) as LinearLayout
        ftp_img = findViewById<View>(R.id.ftp_img) as ImageView
        ftp_txt = findViewById<View>(R.id.ftp_txt) as TextView

        home_img = findViewById<View>(R.id.home_img) as ImageView
        history_img = findViewById<View>(R.id.history_img) as ImageView
        profile_img = findViewById<View>(R.id.myprofile_img) as ImageView
        fan_img = findViewById<View>(R.id.fan_img) as ImageView

        home_txt = findViewById<View>(R.id.home_txt) as TextView
        history_txt = findViewById<View>(R.id.history_txt) as TextView
        profile_txt = findViewById<View>(R.id.myprofile_txt) as TextView
        fan_txt = findViewById<View>(R.id.fan_txt) as TextView

        filter_fab = findViewById<View>(R.id.fab) as FloatingActionButton
        filter_fab.hide()
        AppConfiguration.firstDashStr = "true"


        overlay = findViewById<View>(R.id.overlay) as SpeedDialOverlayLayout
        speedDial = findViewById<View>(R.id.speedDial) as SpeedDialView

        speedDial.visibility=View.GONE
        overlay.visibility=View.GONE


        main_page_scrollview=findViewById<View>(R.id.main_page_scrollview)as ScrollView


    }

    fun bottomNavigationView() {
        bottomNavigation.add(MeowBottomNavigation.Model(1, R.drawable.ic_fans_new))
        bottomNavigation.add(MeowBottomNavigation.Model(2, R.drawable.ic_travel_new))
        bottomNavigation.add(MeowBottomNavigation.Model(3, R.drawable.ic_home_new))
        bottomNavigation.add(MeowBottomNavigation.Model(4, R.drawable.ic_ftp_new))
        bottomNavigation.add(MeowBottomNavigation.Model(5, R.drawable.ic_study))

//        bottomNavigation.show(1, true)

        bottomNavigation.setOnClickMenuListener {
            when (it.id) {
                1 -> {
                    fans_img.setColorFilter(ContextCompat.getColor(mContext,
                            R.color.heading_bg))
                    fans_txt.setTextColor(ContextCompat.getColor(mContext,
                            R.color.heading_bg))
                    story_img.setColorFilter(ContextCompat.getColor(mContext,
                            R.color.unselected_icon_color))
                    story_txt.setTextColor(ContextCompat.getColor(mContext,
                            R.color.unselected_icon_color))
                    ftp_img.setColorFilter(ContextCompat.getColor(mContext,
                            R.color.unselected_icon_color))
                    ftp_txt.setTextColor(ContextCompat.getColor(mContext,
                            R.color.unselected_icon_color))
                    travel_img.setColorFilter(ContextCompat.getColor(mContext,
                            R.color.unselected_icon_color))
                    travel_txt.setTextColor(ContextCompat.getColor(mContext,
                            R.color.unselected_icon_color))
                    navItemIndex = 3
                    fragment = FansFragment()
                    loadFragment(fragment as FansFragment)
                    fans_linear.isClickable = false
                    story_linear.isClickable = true
                    ftp_linear.isClickable = true
                    travel_linear.isClickable = true
                    home1_linear.isClickable = true
                }
                2 -> {
                    travel_img.setColorFilter(ContextCompat.getColor(mContext,
                            R.color.heading_bg))
                    travel_txt.setTextColor(ContextCompat.getColor(mContext,
                            R.color.heading_bg))
                    fans_img.setColorFilter(ContextCompat.getColor(mContext,
                            R.color.unselected_icon_color))
                    fans_txt.setTextColor(ContextCompat.getColor(mContext,
                            R.color.unselected_icon_color))
                    story_img.setColorFilter(ContextCompat.getColor(mContext,
                            R.color.unselected_icon_color))
                    story_txt.setTextColor(ContextCompat.getColor(mContext,
                            R.color.unselected_icon_color))
                    ftp_img.setColorFilter(ContextCompat.getColor(mContext,
                            R.color.unselected_icon_color))
                    ftp_txt.setTextColor(ContextCompat.getColor(mContext,
                            R.color.unselected_icon_color))
                    navItemIndex = 8
                    fragment = TravelFragment()
                    loadFragment(fragment as TravelFragment)
                    fans_linear.isClickable = true
                    travel_linear.isClickable = false
                    story_linear.isClickable = true
                    ftp_linear.isClickable = true
                    home1_linear.isClickable = true
                }
                3 -> {
                    fans_img.setColorFilter(ContextCompat.getColor(mContext,
                            R.color.unselected_view))
                    fans_txt.setTextColor(ContextCompat.getColor(mContext,
                            R.color.unselected_view))
                    story_img.setColorFilter(ContextCompat.getColor(mContext,
                            R.color.unselected_view))
                    story_txt.setTextColor(ContextCompat.getColor(mContext,
                            R.color.unselected_view))

                    ftp_img.setColorFilter(ContextCompat.getColor(mContext,
                            R.color.unselected_view))
                    ftp_txt.setTextColor(ContextCompat.getColor(mContext,
                            R.color.unselected_view))
                    travel_img.setColorFilter(ContextCompat.getColor(mContext,
                            R.color.unselected_icon_color))
                    travel_txt.setTextColor(ContextCompat.getColor(mContext,
                            R.color.unselected_icon_color))
                    navItemIndex = 0
                    CURRENT_TAG = TAG_HOME
                    loadHomeFragment()
                    fans_linear.isClickable = true
                    story_linear.isClickable = true
                    ftp_linear.isClickable = true
                    travel_linear.isClickable = true
                    home1_linear.isClickable = false
                }
                4 -> {
                    ftp_img.setColorFilter(ContextCompat.getColor(mContext,
                            R.color.heading_bg))
                    ftp_txt.setTextColor(ContextCompat.getColor(mContext,
                            R.color.heading_bg))

                    story_img.setColorFilter(ContextCompat.getColor(mContext,
                            R.color.unselected_icon_color))
                    story_txt.setTextColor(ContextCompat.getColor(mContext,
                            R.color.unselected_icon_color))

                    fans_img.setColorFilter(ContextCompat.getColor(mContext,
                            R.color.unselected_icon_color))
                    fans_txt.setTextColor(ContextCompat.getColor(mContext,
                            R.color.unselected_icon_color))
                    travel_img.setColorFilter(ContextCompat.getColor(mContext,
                            R.color.unselected_icon_color))
                    travel_txt.setTextColor(ContextCompat.getColor(mContext,
                            R.color.unselected_icon_color))
                    navItemIndex = 7
                    fragment = FTPFragment()
                    loadFragment(fragment as FTPFragment)
                    fans_linear.isClickable = true
                    story_linear.isClickable = true
                    ftp_linear.isClickable = false
                    home1_linear.isClickable = true
                    travel_linear.isClickable = true
                }
                5 -> {
                    story_img.setColorFilter(ContextCompat.getColor(mContext,
                            R.color.heading_bg))
                    story_txt.setTextColor(ContextCompat.getColor(mContext,
                            R.color.heading_bg))

                    fans_img.setColorFilter(ContextCompat.getColor(mContext,
                            R.color.unselected_icon_color))
                    fans_txt.setTextColor(ContextCompat.getColor(mContext,
                            R.color.unselected_icon_color))

                    ftp_img.setColorFilter(ContextCompat.getColor(mContext,
                            R.color.unselected_icon_color))
                    ftp_txt.setTextColor(ContextCompat.getColor(mContext,
                            R.color.unselected_icon_color))
                    travel_img.setColorFilter(ContextCompat.getColor(mContext,
                            R.color.unselected_icon_color))
                    travel_txt.setTextColor(ContextCompat.getColor(mContext,
                            R.color.unselected_icon_color))
                    navItemIndex = 6
                    fragment = StoryFragment()
                    loadFragment(fragment as StoryFragment)
                    fans_linear.isClickable = true
                    story_linear.isClickable = false
                    ftp_linear.isClickable = true
                    travel_linear.isClickable = true
                    home1_linear.isClickable = true
                }

            }
//            Toast.makeText(this, "$name is clicked", Toast.LENGTH_SHORT).show()
        }
    }

    fun setListiner() {
        home1_linear.setOnClickListener(this)
        home_linear.setOnClickListener(this)
        history_linear.setOnClickListener(this)
        profile_linear.setOnClickListener(this)
        fan_linear.setOnClickListener(this)
        fans_linear.setOnClickListener(this)
        story_linear.setOnClickListener(this)
        ftp_linear.setOnClickListener(this)
        travel_linear.setOnClickListener(this)

        main_page_scrollview.setOnTouchListener { v, event -> false }
    }

    fun showHide() {
        if (AppConfiguration.firstDashStr.equals("false", ignoreCase = true)) {
            old_menu.visibility = View.VISIBLE
            new_menu.visibility = View.GONE
        } else {
            new_menu.visibility = View.VISIBLE
            old_menu.visibility = View.GONE
        }
    }

    private fun loadNavHeader() {
        // name, website
        user_name_txt.text = Utils.getPref(mContext, "LoginUserName")


        // Loading profile image
        Glide.with(this)
                .load(Utils.getPref(mContext, "LoginProfilePic"))
                .thumbnail(0.5f)
                .into(user_profile_img)

        user_profile_img.setOnClickListener(this)
        proflie_linear.setOnClickListener(this)

        // showing dot next to notifications label
        //        navigationView.getMenu().getItem(3).setActionView(R.layout.menu_dot);
    }

    /***
     * Returns respected fragment that user
     * selected from navigation menu
     */
    private fun loadHomeFragment() {

        bottomNavigation.show(3, true)

        // selecting appropriate nav menu item
        selectNavMenu()

        // set toolbar title
        setToolbarTitle()

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (supportFragmentManager.findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers()

            return
        }

        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        Handler().postDelayed({
//        Utils.showDialog(mContext)
            val mPendingRunnable = Runnable {
                // update the main content by replacing fragments
                val fragment = homeFragment
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out)
                fragmentTransaction.replace(R.id.frame_container, fragment, CURRENT_TAG)
                fragmentTransaction.commitAllowingStateLoss()

            }
            // If mPendingRunnable is not null, then add to the message queue
            if (mPendingRunnable != null) {
                mHandler!!.post(mPendingRunnable)
            }
        }, 50)
//        Utils.dismissDialog()


        //Closing drawer on item click
        drawer.closeDrawers()

        // refresh toolbar menu
        invalidateOptionsMenu()
    }

    /***
     * Returns respected fragment that user
     * selected from navigation menu
     */
    private fun selectNavMenu() {
        navigationView.menu.getItem(navItemIndex).isChecked = true
    }

    private fun setToolbarTitle() {
        //        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }

    private fun setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener { menuItem ->
            // This method will trigger on item Click of navigation menu
            //Check to see which item was being clicked and perform appropriate action
            when (menuItem.itemId) {
                //Replacing the main content with ContentFragment Which is our Inbox View;
                R.id.nav_history -> {
                    val fragment = HistoryFragment()
                    val fragmentTransaction = supportFragmentManager.beginTransaction()
                    fragmentTransaction.setCustomAnimations(0, 0)
                    fragmentTransaction.replace(R.id.frame_container, fragment)
                    fragmentTransaction.commit()
                    drawer.closeDrawers()
                    navItemIndex = 1
                    home1_linear.isClickable = true
                }
                R.id.nav_aboutus -> {
                    navItemIndex = 3
                    // launch new intent instead of loading fragment
                    val webviewIntent = Intent(mContext, MoreStoryActivity::class.java)
                    webviewIntent.putExtra("Story Heading", "Ab Jeetega India")
                    webviewIntent.putExtra("StroyUrl", "http://ajif.in/")
                    webviewIntent.putExtra("whereTocome", "aboutus")
                    webviewIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    mContext.startActivity(webviewIntent)
                    drawer.closeDrawers()
                    home1_linear.isClickable = true
                }
                R.id.nav_contactus -> {
                    navItemIndex = 4
                    val contactusIntent = Intent(mContext, ContactusActivity::class.java)
                    mContext.startActivity(contactusIntent)
                    drawer.closeDrawers()
                    home1_linear.isClickable = true
                }
                R.id.nav_rateus -> {
                    navItemIndex = 5
                    drawer.closeDrawers()
                }
                R.id.nav_logout -> {
                    val alertDialog2 = AlertDialog.Builder(
                            this@DashboardActivity)
                    alertDialog2.setTitle("Logout Confirm")
                    alertDialog2.setMessage("Are you sure you want logout?")
                    alertDialog2.setIcon(R.drawable.app_logo)
                    alertDialog2.setCancelable(false)
                    alertDialog2.setPositiveButton("YES"
                    ) { dialog, which ->
                        // Write your code here to execute after dialog
                        Utils.setPref(mContext, "LoginUserName", "")
                        Utils.setPref(mContext, "LoginEmailId", "")
                        Utils.setPref(mContext, "LoginPhoneNo", "")
                        Utils.setPref(mContext, "LoginProfilePic", "")
                        Utils.setPref(mContext, "EmailVerified", "")
                        Utils.setPref(mContext, "PhoneVerified", "")
                        Utils.setPref(mContext, "AppUserId", "")
                        Utils.setPref(mContext, "Gender", "")

                        Utils.ping(mContext, "You are logout suceessfully")
                        val ilogin = Intent(mContext, LoginActivity::class.java)
                        startActivity(ilogin)
                        finish()
                    }
                    alertDialog2.setNegativeButton("NO"
                    ) { dialog, which ->
                        // Write your code here to execute after dialog

                        dialog.cancel()
                    }
                    alertDialog2.show()
                    drawer.closeDrawers()
                }
                else -> navItemIndex = 0
            }

            //Checking if the item is in checked state or not, if not make it in checked state
            if (menuItem.isChecked) {
                menuItem.isChecked = false
            } else {
                menuItem.isChecked = true
            }
            menuItem.isChecked = true

            //                loadHomeFragment();

            true
        }


        val actionBarDrawerToggle = object : ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            override fun onDrawerClosed(drawerView: View) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView)
            }

            override fun onDrawerOpened(drawerView: View) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView)
            }
        }
        actionBarDrawerToggle.drawerArrowDrawable.color = resources.getColor(R.color.splash_bg_color)
        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle)
        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState()
    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers()
            return
        }

        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
        if (shouldLoadHomeFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than home

            overlay.visibility=View.GONE
            speedDial.visibility=View.GONE
            if (navItemIndex != 0) {
                fans_img.setColorFilter(ContextCompat.getColor(mContext,
                        R.color.unselected_view))
                fans_txt.setTextColor(ContextCompat.getColor(mContext,
                        R.color.unselected_view))
                ftp_img.setColorFilter(ContextCompat.getColor(mContext,
                        R.color.unselected_view))
                ftp_txt.setTextColor(ContextCompat.getColor(mContext,
                        R.color.unselected_view))
                story_img.setColorFilter(ContextCompat.getColor(mContext,
                        R.color.unselected_view))
                story_txt.setTextColor(ContextCompat.getColor(mContext,
                        R.color.unselected_view))
                travel_img.setColorFilter(ContextCompat.getColor(mContext,
                        R.color.unselected_view))
                travel_txt.setTextColor(ContextCompat.getColor(mContext,
                        R.color.unselected_view))
                navItemIndex = 0
                CURRENT_TAG = TAG_HOME
                loadHomeFragment()
                return
            }
        }

        super.onBackPressed()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


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

        return super.onOptionsItemSelected(item)
    }

    private fun loadFragment(fragment: Fragment) {
        // load fragment
        val transaction = supportFragmentManager.beginTransaction().setCustomAnimations(0, 0)
        transaction.replace(R.id.frame_container, fragment)
        transaction.commit()
    }

    @SuppressLint("RestrictedApi")
    override fun onClick(v: View) {
        when (v.id) {
            R.id.home_linear -> {

                new_menu.visibility = View.VISIBLE
                old_menu.visibility = View.GONE
            }
            R.id.history_linear -> {
                history_img.setColorFilter(ContextCompat.getColor(mContext,
                        R.color.sign_up))
                history_txt.setTextColor(ContextCompat.getColor(mContext,
                        R.color.sign_up))

                home_img.setColorFilter(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color))
                home_txt.setTextColor(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color))

                profile_img.setColorFilter(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color))
                profile_txt.setTextColor(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color))
                fan_img.setColorFilter(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color))
                fan_txt.setTextColor(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color))
                navItemIndex = 1
                fragment = HistoryFragment()
                loadFragment(fragment as HistoryFragment)
            }
            R.id.profile_linear -> {
                profile_img.setColorFilter(ContextCompat.getColor(mContext,
                        R.color.sign_up))
                profile_txt.setTextColor(ContextCompat.getColor(mContext,
                        R.color.sign_up))

                home_img.setColorFilter(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color))
                home_txt.setTextColor(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color))

                history_img.setColorFilter(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color))
                history_txt.setTextColor(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color))
                fan_img.setColorFilter(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color))
                fan_txt.setTextColor(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color))
                navItemIndex = 2
                fragment = MyProfileFragment()
                loadFragment(fragment as MyProfileFragment)
            }
            R.id.fan_linear -> {
                fan_img.setColorFilter(ContextCompat.getColor(mContext,
                        R.color.sign_up))
                fan_txt.setTextColor(ContextCompat.getColor(mContext,
                        R.color.sign_up))

                home_img.setColorFilter(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color))
                home_txt.setTextColor(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color))

                history_img.setColorFilter(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color))
                history_txt.setTextColor(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color))
                profile_img.setColorFilter(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color))
                profile_txt.setTextColor(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color))
                navItemIndex = 3
                fragment = FansFragment()
                loadFragment(fragment as FansFragment)
            }
            R.id.fans_linear -> {

                fans_img.setColorFilter(ContextCompat.getColor(mContext,
                        R.color.heading_bg))
                fans_txt.setTextColor(ContextCompat.getColor(mContext,
                        R.color.heading_bg))
                story_img.setColorFilter(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color))
                story_txt.setTextColor(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color))
                ftp_img.setColorFilter(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color))
                ftp_txt.setTextColor(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color))
                travel_img.setColorFilter(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color))
                travel_txt.setTextColor(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color))
                navItemIndex = 3
                fragment = FansFragment()
                loadFragment(fragment as FansFragment)
                fans_linear.isClickable = false
                story_linear.isClickable = true
                ftp_linear.isClickable = true
                travel_linear.isClickable = true
                home1_linear.isClickable = true
            }
            R.id.profile_image -> {
                navItemIndex = 2
//                home1_linear.isClickable = true
//                fragment = MyProfileFragment()
//                loadFragment(fragment as MyProfileFragment)
                val profileView = Intent(mContext, MyProfileActivity::class.java)
                profileView.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                mContext.startActivity(profileView)
                drawer.closeDrawers()
            }
            R.id.proflie_linear->{
                navItemIndex = 2
//                home1_linear.isClickable = true
//                fragment = MyProfileFragment()
//                loadFragment(fragment as MyProfileFragment)
                val profileView = Intent(mContext, MyProfileActivity::class.java)
                profileView.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                mContext.startActivity(profileView)
                drawer.closeDrawers()
            }
            R.id.home1_linear -> {
                fans_img.setColorFilter(ContextCompat.getColor(mContext,
                        R.color.unselected_view))
                fans_txt.setTextColor(ContextCompat.getColor(mContext,
                        R.color.unselected_view))
                story_img.setColorFilter(ContextCompat.getColor(mContext,
                        R.color.unselected_view))
                story_txt.setTextColor(ContextCompat.getColor(mContext,
                        R.color.unselected_view))

                ftp_img.setColorFilter(ContextCompat.getColor(mContext,
                        R.color.unselected_view))
                ftp_txt.setTextColor(ContextCompat.getColor(mContext,
                        R.color.unselected_view))
                travel_img.setColorFilter(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color))
                travel_txt.setTextColor(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color))
                navItemIndex = 0
                CURRENT_TAG = TAG_HOME
                loadHomeFragment()
                fans_linear.isClickable = true
                story_linear.isClickable = true
                ftp_linear.isClickable = true
                travel_linear.isClickable = true
                home1_linear.isClickable = false
            }
            R.id.story_linear -> {
                story_img.setColorFilter(ContextCompat.getColor(mContext,
                        R.color.heading_bg))
                story_txt.setTextColor(ContextCompat.getColor(mContext,
                        R.color.heading_bg))

                fans_img.setColorFilter(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color))
                fans_txt.setTextColor(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color))

                ftp_img.setColorFilter(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color))
                ftp_txt.setTextColor(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color))
                travel_img.setColorFilter(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color))
                travel_txt.setTextColor(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color))
                navItemIndex = 6
                fragment = StoryFragment()
                loadFragment(fragment as StoryFragment)
                fans_linear.isClickable = true
                story_linear.isClickable = false
                ftp_linear.isClickable = true
                travel_linear.isClickable = true
                home1_linear.isClickable = true
            }
            R.id.ftp_linear -> {
                ftp_img.setColorFilter(ContextCompat.getColor(mContext,
                        R.color.heading_bg))
                ftp_txt.setTextColor(ContextCompat.getColor(mContext,
                        R.color.heading_bg))

                story_img.setColorFilter(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color))
                story_txt.setTextColor(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color))

                fans_img.setColorFilter(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color))
                fans_txt.setTextColor(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color))
                travel_img.setColorFilter(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color))
                travel_txt.setTextColor(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color))
                navItemIndex = 7
                fragment = FTPFragment()
                loadFragment(fragment as FTPFragment)
                fans_linear.isClickable = true
                story_linear.isClickable = true
                ftp_linear.isClickable = false
                home1_linear.isClickable = true
                travel_linear.isClickable = true
            }
            R.id.travel_linear -> {
                travel_img.setColorFilter(ContextCompat.getColor(mContext,
                        R.color.heading_bg))
                travel_txt.setTextColor(ContextCompat.getColor(mContext,
                        R.color.heading_bg))
                fans_img.setColorFilter(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color))
                fans_txt.setTextColor(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color))
                story_img.setColorFilter(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color))
                story_txt.setTextColor(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color))
                ftp_img.setColorFilter(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color))
                ftp_txt.setTextColor(ContextCompat.getColor(mContext,
                        R.color.unselected_icon_color))
                navItemIndex = 8
                fragment = TravelFragment()
                loadFragment(fragment as TravelFragment)
                fans_linear.isClickable = true
                travel_linear.isClickable = false
                story_linear.isClickable = true
                ftp_linear.isClickable = true
                home1_linear.isClickable = true
            }
        }
    }

    override fun onTrave() {
        if (fragment != null) {
            old_menu.visibility = View.VISIBLE
            new_menu.visibility = View.GONE
        }
    }

    override fun onStoryCategory(categoryId: String?, categoryName: String?) {
        navItemIndex = 3
//        fragment = StoryCategoryFragment()
//        val bundle = Bundle()
//        bundle.putString("categoryId", categoryId)
//        bundle.putString("categoryName", categoryName)
//        val addCar = StoryCategoryFragment()
//       Utils.ping(mContext,"categoryId"+categoryId+"categoryName"+categoryName)
//        addCar.arguments = bundle


        val fragment = StoryCategoryFragment()
        val bundle = Bundle()
        bundle.putString("categoryId", categoryId)
        bundle.putString("categoryName", categoryName)
        fragment.setArguments(bundle)
        loadCategoryFragment(fragment as StoryCategoryFragment)
    }

    override fun onStoryCategoryBack() {
        navItemIndex = 3
        fragment = StoryFragment()
        val transaction = supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_out_right, R.anim.slide_in_left)
        transaction.replace(R.id.frame_container, fragment as StoryFragment)
        transaction.commit()
    }

    companion object {
        // index to identify current nav menu item
        var navItemIndex = 0

        // tags used to attach the fragments
        private val TAG_HOME = "home"
        var CURRENT_TAG = TAG_HOME
    }

    private fun loadCategoryFragment(fragment: Fragment) {
        // load fragment

        val transaction = supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_out_right, R.anim.slide_in_left)

        transaction.replace(R.id.frame_container, fragment)
        transaction.commit()
    }

}
