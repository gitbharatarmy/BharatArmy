package com.bharatarmy.Activity

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.bharatarmy.Fragment.*
import com.bharatarmy.Models.MyScreenChnagesModel
import com.bharatarmy.R
import com.bharatarmy.Utility.AppConfiguration
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.leinardi.android.speeddial.SpeedDialOverlayLayout
import com.leinardi.android.speeddial.SpeedDialView
import kotlinx.android.synthetic.main.app_bar_dashboard.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


// remove code 29/07/2019
// remove extra code 03/09/2019 with noon backup
// remove extra and commented code 16/01/2020 with backup of 16/01/2020
class DashboardActivity : AppCompatActivity(), View.OnClickListener, StoryFragment.OnItemClick, StoryCategoryFragment.OnItemClick, HomeFragment.OnItemClick {


    internal lateinit var mContext: Context
    private var fragment: Fragment? = null
    var viewmoreStr: String? = ""
    internal lateinit var toolbar: Toolbar
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

        // initializing navigation menu
//        setUpNavigationView()

        if (savedInstanceState == null) {
            navItemIndex = 0
            CURRENT_TAG = TAG_HOME
            loadHomeFragment()
        }
    }

    fun init() {
        AppConfiguration.firstDashStr = "true"


        overlay = findViewById<View>(R.id.overlay) as SpeedDialOverlayLayout
        speedDial = findViewById<View>(R.id.speedDial) as SpeedDialView

        speedDial.visibility = View.GONE
        overlay.visibility = View.GONE

    }


    fun bottomNavigationView() {

        bottomNavigation.add(MeowBottomNavigation.Model(1, R.drawable.ic_fans_new))
        bottomNavigation.add(MeowBottomNavigation.Model(2, R.drawable.ic_feedback)) //ic_travel_new
        bottomNavigation.add(MeowBottomNavigation.Model(3, R.drawable.ic_home_new))
//        bottomNavigation.add(MeowBottomNavigation.Model(4, R.drawable.ic_ftp_new))
        bottomNavigation.add(MeowBottomNavigation.Model(4, R.drawable.ic_study))
        bottomNavigation.add(MeowBottomNavigation.Model(5, R.drawable.ic_more))

        bottomNavigation.setOnClickMenuListener {
            AppConfiguration.lastpositionofnavigation = it.id.toString()
            Log.d("selectedId :", "" + AppConfiguration.lastpositionofnavigation)
            when (it.id) {
                1 -> {
                    if (navItemIndex.equals(2)) {
                        loadPageToFeedback()
                    } else {
                        navItemIndex = 1
                        fragment = FansFragment()
                        loadFragment(fragment as FansFragment)
                    }
                }
                2 -> {

                    navItemIndex = 2
                    fragment = FeedbackFragment()
                    loadFragment(fragment as FeedbackFragment)

                    /*  fragment = NewTravelFragment()
                      loadFragment(fragment as NewTravelFragment)*/

                }
                3 -> {
                    if (navItemIndex.equals(2)) {
                        loadPageToFeedback()
                    } else {
                        navItemIndex = 0
                        fragment = HomeFragment()
                        loadFragment(fragment as HomeFragment)
                    }
                }
                4 -> {
                    if (navItemIndex.equals(2)) {
                        loadPageToFeedback()
                    } else {
                        navItemIndex = 4
                        fragment = StoryFragment()
                        loadFragment(fragment as StoryFragment)
                    }
                }
                5 -> {
                    if (navItemIndex.equals(2)) {
                        loadPageToFeedback()
                    } else {
                        navItemIndex = 5
                        fragment = MoreFragment()
                        loadFragment(fragment as MoreFragment)
                    }
                }

            }
        }


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
                } else if (page.equals("4", ignoreCase = true)) {
                    bottomNavigation.show(4, true)
                    intent.putExtra("whichPageRun", "")
                    fragment = StoryFragment()
                    loadFragment(fragment as StoryFragment)
                } else if (page.equals("5", ignoreCase = true)) {
                    bottomNavigation.show(5, true)
                    intent.putExtra("whichPageRun", "")
                    fragment = MoreFragment()
                    loadFragment(fragment as MoreFragment)
                } else if (page.equals("2", ignoreCase = true)) {
                    navItemIndex = 2
                    bottomNavigation.show(2, true)
                    intent.putExtra("whichPageRun", "")
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
//            drawer.closeDrawers()

            return
        }


        // refresh toolbar menu
        invalidateOptionsMenu()
    }

    public fun loadPageToFeedback() {
        val alertDialog2 = AlertDialog.Builder(mContext)
        alertDialog2.setTitle("Feedback Confirm")
        alertDialog2.setMessage("Are you sure you want go other page")
        alertDialog2.setIcon(R.drawable.app_logo_new)
        alertDialog2.setCancelable(false)
        alertDialog2.setPositiveButton("YES"
        ) { dialog, which ->
            dialog.cancel()
            if (AppConfiguration.lastpositionofnavigation.equals("1", ignoreCase = true)) {
                navItemIndex = 1
                fragment = FansFragment()
                loadFragment(fragment as FansFragment)
            } else if (AppConfiguration.lastpositionofnavigation.equals("3", ignoreCase = true)) {
                navItemIndex = 0
                fragment = HomeFragment()
                loadFragment(fragment as HomeFragment)
            } else if (AppConfiguration.lastpositionofnavigation.equals("4", ignoreCase = true)) {
                navItemIndex = 4
                fragment = StoryFragment()
                loadFragment(fragment as StoryFragment)
            } else if (AppConfiguration.lastpositionofnavigation.equals("5", ignoreCase = true)) {
                navItemIndex = 5
                fragment = MoreFragment()
                loadFragment(fragment as MoreFragment)
            }

        }
        alertDialog2.setNegativeButton("NO"
        ) { dialog, which ->
            // Write your code here to execute after dialog
            dialog.cancel()
            bottomNavigation.show(2, true)
        }

            alertDialog2.show();
}

    private fun setToolbarTitle() {
        //        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }

    override fun onBackPressed() {
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawers()
//            return
//        }
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

                /*if (navItemIndex == 2) {
                    AppConfiguration.lastpositionofnavigation="3";
                    loadPageToFeedback()
                } else {*/
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
//                drawer.closeDrawers()
            }
            R.id.proflie_linear -> {
                navItemIndex = 2
                val profileView = Intent(mContext, MyProfileActivity::class.java)
                profileView.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                mContext.startActivity(profileView)
//                drawer.closeDrawers()
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
