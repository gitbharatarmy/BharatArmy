package com.bharatarmy.Activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.bharatarmy.Fragment.*
import com.bharatarmy.Models.MyScreenChnagesModel
import com.bharatarmy.R
import com.bharatarmy.Utility.AppConfiguration
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.google.android.material.navigation.NavigationView
import com.leinardi.android.speeddial.SpeedDialOverlayLayout
import com.leinardi.android.speeddial.SpeedDialView
import kotlinx.android.synthetic.main.app_bar_dashboard.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


// remove code 29/07/2019
// remove extra code 03/09/2019 with noon backup
class DashboardActivity : AppCompatActivity(), View.OnClickListener, StoryFragment.OnItemClick, StoryCategoryFragment.OnItemClick, HomeFragment.OnItemClick {


    internal lateinit var mContext: Context
    private var fragment: Fragment? = null
    internal lateinit var drawer: DrawerLayout

    var viewmoreStr: String? = ""
    internal lateinit var user_profile_img: ImageView
    internal lateinit var proflie_linear: LinearLayout
    internal lateinit var user_name_txt: TextView
    internal lateinit var navigationView: NavigationView
    internal lateinit var toolbar: Toolbar
    private var navHeader: View? = null

    internal lateinit var speedDial: SpeedDialView
    internal lateinit var overlay: SpeedDialOverlayLayout


    //  flag to load home fragment when user presses back key
    private val shouldLoadHomeFragOnBackPress = true
    private var mHandler: Handler? = null


    val homeFragment: Fragment
        get() {
            when (navItemIndex) {
                0 -> {

                    return HomeFragment()
                }
                else -> return HomeFragment()
            }
        }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        mContext = this@DashboardActivity
        toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        mHandler = Handler()
        setSupportActionBar(toolbar)
//        toolbar.setNavigationIcon(null);
        supportActionBar!!.title = ""

        //initilize control
        init()
        bottomNavigationView()

        // load nav menu header data
        loadNavHeader()

        // initializing navigation menu
//        setUpNavigationView()

        if (savedInstanceState == null) {
            navItemIndex = 0
            CURRENT_TAG = TAG_HOME
            loadHomeFragment()
        }
    }

    fun init() {
        drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        navigationView = findViewById<View>(R.id.nav_view) as NavigationView
        navHeader = navigationView.getHeaderView(0)
        user_profile_img = navHeader!!.findViewById<View>(R.id.profile_image) as ImageView
        proflie_linear = navHeader!!.findViewById<View>(R.id.proflie_linear) as LinearLayout
        user_name_txt = navHeader!!.findViewById<View>(R.id.textView) as TextView

        AppConfiguration.firstDashStr = "true"


        overlay = findViewById<View>(R.id.overlay) as SpeedDialOverlayLayout
        speedDial = findViewById<View>(R.id.speedDial) as SpeedDialView

        speedDial.visibility = View.GONE
        overlay.visibility = View.GONE

    }




    fun bottomNavigationView() {
        bottomNavigation.add(MeowBottomNavigation.Model(1, R.drawable.ic_fans_new))
        bottomNavigation.add(MeowBottomNavigation.Model(2, R.drawable.ic_travel_new)) //ic_travel_new  ic_study
        bottomNavigation.add(MeowBottomNavigation.Model(3, R.drawable.ic_home_new))
//        bottomNavigation.add(MeowBottomNavigation.Model(4, R.drawable.ic_ftp_new))
        bottomNavigation.add(MeowBottomNavigation.Model(4, R.drawable.ic_study))
        bottomNavigation.add(MeowBottomNavigation.Model(5, R.drawable.ic_more))
        bottomNavigation.setOnClickMenuListener {
            when (it.id) {
                1 -> {
                    navItemIndex = 1
                    fragment = FansFragment()
                    loadFragment(fragment as FansFragment)
                }
                2 -> {

                    navItemIndex = 2
                    fragment = NewTravelFragment()
                    loadFragment(fragment as NewTravelFragment)
//                    fragment = TravelFragment()
//                    loadFragment(fragment as TravelFragment)
                }
                3 -> {
                    navItemIndex = 0
                    fragment = HomeFragment()
                    loadFragment(fragment as HomeFragment)
//                    loadHomeFragment()
                }
                4 -> {
                    navItemIndex = 4
                    fragment = StoryFragment()
                    loadFragment(fragment as StoryFragment)
//                    navItemIndex = 6
//                    fragment = ProfileFragment()
//                    loadFragment(fragment as ProfileFragment)
                }
                5 -> {
                    navItemIndex = 5
                    fragment = MoreFragment()
                    loadFragment(fragment as MoreFragment)

                }

            }
        }
    }

    private fun loadNavHeader() {
        // name, website
//        user_name_txt.text = Utils.retriveLoginData(mContext).name


        // Loading profile image
//        Glide.with(this)
//                .load(Utils.getPref(mContext, "LoginProfilePic"))
//                .thumbnail(0.5f)
//                .into(user_profile_img)

        user_profile_img.setOnClickListener(this)
        proflie_linear.setOnClickListener(this)
    }

    /***
     * Returns respected fragment that user
     * selected from navigation menu
     */
    private fun loadHomeFragment() {
        if (intent.getStringExtra("PageType") != null) {
            val pageType = intent.getStringExtra("PageType")
            if (pageType.equals("0", ignoreCase = true)) {
                bottomNavigation.show(3, true)
            }
        } else {
            if (intent.getStringExtra("whichPageRun") != null) {
                val page = intent.getStringExtra("whichPageRun")
                if (page.equals("1", ignoreCase = true)) {
                    bottomNavigation.show(1, true)
                    intent.putExtra("whichPageRun", "")
                    navItemIndex = 1
                    fragment = FansFragment()
                    loadFragment(fragment as FansFragment)
                } /*else if (page.equals("4", ignoreCase = true)) {
                    bottomNavigation.show(4, true)
                    intent.putExtra("whichPageRun", "")
                    navItemIndex = 7
                    fragment = ProfileFragment()
                    loadFragment(fragment as ProfileFragment)
                }*/ else if (page.equals("4", ignoreCase = true)) {
                    bottomNavigation.show(4, true)
                    intent.putExtra("whichPageRun", "")
                    fragment = StoryFragment()
                    loadFragment(fragment as StoryFragment)
                } else if (page.equals("2", ignoreCase = true)) {
                    navItemIndex = 2
                    bottomNavigation.show(2, true)
                    intent.putExtra("whichPageRun", "")
//                    fragment = TravelFragment()
//                    loadFragment(fragment as TravelFragment)
                    fragment = NewTravelFragment()
                    loadFragment(fragment as NewTravelFragment)
                } else {
                    bottomNavigation.show(3, true)
                    fragment = HomeFragment()
                    loadFragment(fragment as HomeFragment)
                }
            } else {
                bottomNavigation.show(3, true)
                fragment = HomeFragment()
                loadFragment(fragment as HomeFragment)
            }
        }


        // selecting appropriate nav menu item
//        selectNavMenu()

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
//        Handler().postDelayed({
//            //        Utils.showDialog(mContext)
//            val mPendingRunnable = Runnable {
//                // update the main content by replacing fragments
//                val fragment = homeFragment
//                val fragmentTransaction = supportFragmentManager.beginTransaction()
//                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
//                        android.R.anim.fade_out)
//                fragmentTransaction.replace(R.id.frame_container, fragment, CURRENT_TAG)
//                fragmentTransaction.commitAllowingStateLoss()
//
//            }
//            // If mPendingRunnable is not null, then add to the message queue
//            if (mPendingRunnable != null) {
//                mHandler!!.post(mPendingRunnable)
//            }
//        }, 50)


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

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers()
            return
        }
        if (speedDial.isOpen) {
            speedDial.close(true);
        } else {
            // This code loads home fragment when back key is pressed
            // when user is in other fragment than home
            if (shouldLoadHomeFragOnBackPress) {
                // checking if user is on other navigation menu
                // rather than home

                overlay.visibility = View.GONE
                speedDial.visibility = View.GONE


                if (navItemIndex != 0) {
                    if (navItemIndex != 1) {
                        if (!viewmoreStr.equals("", ignoreCase = true)) {
                            bottomNavigation.show(2, true)
                            intent.putExtra("whichPageRun", "")
                            navItemIndex = 1
                            fragment = NewTravelFragment()
                            loadFragment(fragment as NewTravelFragment)
                            return
                        } else {
                            viewmoreStr = ""
                            navItemIndex = 0
                            CURRENT_TAG = TAG_HOME
                            loadHomeFragment()
                            return
                        }
                    } else {
                        viewmoreStr = ""
                        navItemIndex = 0
                        CURRENT_TAG = TAG_HOME
                        loadHomeFragment()
                        return
                    }

                }


            }
            try {
                super.onBackPressed()
            } catch (exp: IllegalStateException) { // can output some information here
                finish()
            }
//            super.onBackPressed()
        }


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

        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data);
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
            R.id.profile_image -> {
                navItemIndex = 2
                val profileView = Intent(mContext, MyProfileActivity::class.java)
                profileView.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                mContext.startActivity(profileView)
                drawer.closeDrawers()
            }
            R.id.proflie_linear -> {
                navItemIndex = 2
                val profileView = Intent(mContext, MyProfileActivity::class.java)
                profileView.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                mContext.startActivity(profileView)
                drawer.closeDrawers()
            }
        }
    }


    override fun onStoryCategory(categoryId: String?, categoryName: String?, wheretocome: String?) {

        navItemIndex = 4
        val fragment = StoryCategoryFragment()
        val bundle = Bundle()
        bundle.putString("categoryId", categoryId)
        bundle.putString("categoryName", categoryName)
        bundle.putString("wheretocome", wheretocome)
        fragment.setArguments(bundle)
        loadCategoryFragment(fragment as StoryCategoryFragment)
    }

    override fun onStoryCategoryBack(wheretocome: String?) {
        if (wheretocome.equals("home", ignoreCase = true)) {
            navItemIndex = 0
            fragment = HomeFragment()
            val transaction = supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_out_right, R.anim.slide_in_left)
            transaction.replace(R.id.frame_container, fragment as HomeFragment)
            transaction.commit()
        } else {
            navItemIndex = 4
            fragment = StoryFragment()
            val transaction = supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_out_right, R.anim.slide_in_left)
            transaction.replace(R.id.frame_container, fragment as StoryFragment)
            transaction.commit()
        }


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

    @Subscribe
    fun customEventReceived(event: MyScreenChnagesModel) {
//        Log.d("imageId :", event.getMessage());
//        Log.d("mainmodelValue :", imageDetailModelsList.toString());
        if (!event.privacyimage.equals("", ignoreCase = true)) {
            viewmoreStr = event.privacyname;
            navItemIndex = 4
            bottomNavigation.show(4, true)
            intent.putExtra("whichPageRun", "")
            fragment = StoryFragment()
            loadFragment(fragment as StoryFragment)
        }
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }
}
