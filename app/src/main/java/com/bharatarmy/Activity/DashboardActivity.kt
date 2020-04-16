package com.bharatarmy.Activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.bharatarmy.Fragment.*
import com.bharatarmy.Models.MyScreenChnagesModel
import com.bharatarmy.R
import com.bharatarmy.Utility.AppConfiguration
import com.bharatarmy.Utility.Utils
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.leinardi.android.speeddial.SpeedDialOverlayLayout
import com.leinardi.android.speeddial.SpeedDialView
import kotlinx.android.synthetic.main.app_bar_dashboard.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


// remove code 29/07/2019
// remove extra code 03/09/2019 with noon backup
// remove extra and commented code 16/01/2020 with backup of 16/01/2020
// change the 04/04/2020 with backup BAPhase1
// drawer changes remove on 09/04/2020 with backup


class DashboardActivity : AppCompatActivity(), View.OnClickListener, StoryFragment.OnItemClick,
        StoryCategoryFragment.OnItemClick, HomeFragment.OnItemClick {


    internal lateinit var mContext: Context
    private var fragment: Fragment? = null
    var viewmoreStr: String? = ""
    internal lateinit var toolbar: Toolbar
    internal lateinit var speedDial: SpeedDialView
    internal lateinit var overlay: SpeedDialOverlayLayout
    internal lateinit var cart_count_item_txt: TextView

    internal lateinit var cartLayoutRel: RelativeLayout

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
        setListiner()
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
        cart_count_item_txt = findViewById<View>(R.id.cart_count_item_txt) as TextView
        cartLayoutRel = findViewById<View>(R.id.cartLayout_rel) as RelativeLayout


        speedDial.visibility = View.GONE
        overlay.visibility = View.GONE

        Utils.addCartItemCount(mContext, cart_count_item_txt)
    }

    fun setListiner() {
        cartLayoutRel.setOnClickListener {
            val addcartItemIntent = Intent(mContext, CartItemShowActivity::class.java)
            startActivity(addcartItemIntent)
        }

    }

    // For Feedback Fragment
    /* fun bottomNavigationView() {

          bottomNavigation.add(MeowBottomNavigation.Model(1, R.drawable.ic_fans_new))
          bottomNavigation.add(MeowBottomNavigation.Model(2, R.drawable.ic_travel_new)) //ic_travel_new  ic_feedback
          bottomNavigation.add(MeowBottomNavigation.Model(3, R.drawable.ic_home_new))
  //        bottomNavigation.add(MeowBottomNavigation.Model(4, R.drawable.ic_ftp_new))
          bottomNavigation.add(MeowBottomNavigation.Model(4, R.drawable.ic_study))
          bottomNavigation.add(MeowBottomNavigation.Model(5, R.drawable.ic_more))

          bottomNavigation.setOnClickMenuListener {
              if (AppConfiguration.question3.equals("", ignoreCase = true) && AppConfiguration.question4.equals("", ignoreCase = true)
                      && AppConfiguration.question5.equals("", ignoreCase = true) && AppConfiguration.question6.equals("", ignoreCase = true)
                      && AppConfiguration.question8.equals("", ignoreCase = true) && AppConfiguration.question12.equals("", ignoreCase = true)
                      && AppConfiguration.question13.equals("", ignoreCase = true)) {
                  AppConfiguration.addtextchoice = "not fill"
              } else {
                  AppConfiguration.addtextchoice = "fill"
              }

              Utils.hideKeyboard(this@DashboardActivity)
              //            /*Feedback survey quite variable*/
              if (AppConfiguration.multichoice.equals("fill", ignoreCase = true)
                      || AppConfiguration.singlechoice.equals("fill", ignoreCase = true)
                      || AppConfiguration.imagechoice.equals("fill", ignoreCase = true)
                      || AppConfiguration.addtextchoice.equals("fill", ignoreCase = true)) {
                  Utils.setPref(mContext, "fill", "1");
              }
              if (AppConfiguration.multichoice.equals("not fill", ignoreCase = true)
                      && AppConfiguration.singlechoice.equals("not fill", ignoreCase = true)
                      && AppConfiguration.imagechoice.equals("not fill", ignoreCase = true)
                      && AppConfiguration.addtextchoice.equals("not fill", ignoreCase = true)) {
                  Utils.setPref(mContext, "fill", "0");
              }

              AppConfiguration.lastpositionofnavigation = it.id.toString()
              Log.d("selectedId :", "" + AppConfiguration.lastpositionofnavigation)


              when (it.id) {
                  1 -> {
                      if (navItemIndex.equals(2)) {
                          if (Utils.getPref(mContext, "fill") != null) {
                              if (Utils.getPref(mContext, "fill").equals("1", ignoreCase = true)) {
                                  loadPageToFeedback("click")
                              } else {
                                  navItemIndex = 1
                                  fragment = FansFragment()
                                  loadFragment(fragment as FansFragment)
                              }
                          } else {
                              navItemIndex = 1
                              fragment = FansFragment()
                              loadFragment(fragment as FansFragment)
                          }

                      } else {
                          navItemIndex = 1
                          fragment = FansFragment()
                          loadFragment(fragment as FansFragment)
                      }
                  }
                  2 -> {
                      if (Utils.isMember(mContext, "Feedback")) {
                          navItemIndex = 2
                          fragment = FeedbackFragment()
                          loadFragment(fragment as FeedbackFragment)
                      }
                  }
                  3 -> {
                      if (navItemIndex.equals(2)) {
                          if (Utils.getPref(mContext, "fill") != null) {
                              if (Utils.getPref(mContext, "fill").equals("1", ignoreCase = true)) {
                                  loadPageToFeedback("click")
                              } else {
                                  navItemIndex = 0
                                  fragment = HomeFragment()
                                  loadFragment(fragment as HomeFragment)
                              }
                          } else {
                              navItemIndex = 0
                              fragment = HomeFragment()
                              loadFragment(fragment as HomeFragment)
                          }

                      } else {
                          navItemIndex = 0
                          fragment = HomeFragment()
                          loadFragment(fragment as HomeFragment)
                      }
                  }
                  4 -> {
                      if (navItemIndex.equals(2)) {
                          if (Utils.getPref(mContext, "fill") != null) {
                              if (Utils.getPref(mContext, "fill").equals("1", ignoreCase = true)) {
                                  loadPageToFeedback("click")
                              } else {
                                  navItemIndex = 4
                                  fragment = StoryFragment()
                                  loadFragment(fragment as StoryFragment)
                              }
                          } else {
                              navItemIndex = 4
                              fragment = StoryFragment()
                              loadFragment(fragment as StoryFragment)
                          }
                      } else {
                          navItemIndex = 4
                          fragment = StoryFragment()
                          loadFragment(fragment as StoryFragment)
                      }
                  }
                  5 -> {
                      if (navItemIndex.equals(2)) {
                          if (Utils.getPref(mContext, "fill") != null) {
                              if (Utils.getPref(mContext, "fill").equals("1", ignoreCase = true)) {
                                  loadPageToFeedback("click")
                              } else {
                                  navItemIndex = 5
                                  fragment = MoreFragment()
                                  loadFragment(fragment as MoreFragment)
                              }
                          } else {
                              navItemIndex = 5
                              fragment = MoreFragment()
                              loadFragment(fragment as MoreFragment)
                          }
                      } else {
                          navItemIndex = 5
                          fragment = MoreFragment()
                          loadFragment(fragment as MoreFragment)
                      }
                  }

              }
          }


      }*/


    //    For Travel Fragment
    fun bottomNavigationView() {
        bottomNavigation.add(MeowBottomNavigation.Model(1, R.drawable.ic_home_new))
        bottomNavigation.add(MeowBottomNavigation.Model(2, R.drawable.ic_fans_new))
        bottomNavigation.add(MeowBottomNavigation.Model(3, R.drawable.ic_travel_new)) //ic_travel_new  ic_feedback

        //        bottomNavigation.add(MeowBottomNavigation.Model(4, R.drawable.ic_ftp_new))
        bottomNavigation.add(MeowBottomNavigation.Model(4, R.drawable.ic_ba_shop))
        bottomNavigation.add(MeowBottomNavigation.Model(5, R.drawable.ic_study))
        bottomNavigation.add(MeowBottomNavigation.Model(6, R.drawable.ic_more))


        bottomNavigation.setOnClickMenuListener {
            Utils.addCartItemCount(mContext, cart_count_item_txt)
            when (it.id) {
                1 -> {
                    navItemIndex = 0
                    fragment = HomeFragment()
                    loadFragment(fragment as HomeFragment)

                }
                2 -> {
                    navItemIndex = 2
                    fragment = FansFragment()
                    loadFragment(fragment as FansFragment)

                }
                3 -> {
                    navItemIndex = 3
                    fragment = NewTravelFragment()
                    loadFragment(fragment as NewTravelFragment)

                }
                4 -> {
                    navItemIndex = 4
                    fragment = BAShopFragment()
                    loadFragment(fragment as BAShopFragment)

                }
                5 -> {
                    navItemIndex = 5
                    fragment = StoryFragment()
                    loadFragment(fragment as StoryFragment)

                }
                6 -> {
                    navItemIndex = 6
                    fragment = MoreFragment()
                    loadFragment(fragment as MoreFragment)

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
                bottomNavigation.show(1, true)
            }
        } else {
            if (intent.getStringExtra("whichPageRun") != null) {
                val page = intent.getStringExtra("whichPageRun")
                if (page.equals("2", ignoreCase = true)) {
                    bottomNavigation.show(2, true)
                    intent.putExtra("whichPageRun", "")
                    navItemIndex = 2
                    fragment = FansFragment()
                    loadFragment(fragment as FansFragment)
                } else if (page.equals("5", ignoreCase = true)) {
                    bottomNavigation.show(5, true)
                    intent.putExtra("whichPageRun", "")
                    fragment = StoryFragment()
                    loadFragment(fragment as StoryFragment)
                } else if (page.equals("6", ignoreCase = true)) {
                    bottomNavigation.show(6, true)
                    intent.putExtra("whichPageRun", "")
                    fragment = MoreFragment()
                    loadFragment(fragment as MoreFragment)
                } else if (page.equals("3", ignoreCase = true)) {
                    navItemIndex = 3
                    bottomNavigation.show(3, true)
                    intent.putExtra("whichPageRun", "")
//                    fragment = FeedbackFragment()
//                    loadFragment(fragment as FeedbackFragment)
                    fragment = NewTravelFragment()
                    loadFragment(fragment as NewTravelFragment)
                } else {
                    bottomNavigation.show(1, true)
                    fragment = HomeFragment()
                    loadFragment(fragment as HomeFragment)
                }
            } else {
                bottomNavigation.show(1, true)
                fragment = HomeFragment()
                loadFragment(fragment as HomeFragment)
            }
        }


        // selecting appropriate nav menu item
//        selectNavMenu()



        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (supportFragmentManager.findFragmentByTag(CURRENT_TAG) != null) {
//            drawer.closeDrawers()

            return
        }


        // refresh toolbar menu
        invalidateOptionsMenu()
    }

    /*Feedback survey quite dialog*/
    public fun loadPageToFeedback(whichload: String) {
        val dialogBuilder = AlertDialog.Builder(mContext)
        val inflater: LayoutInflater = getLayoutInflater()
        val dialogView = inflater.inflate(R.layout.feedback_back_dialog, null)
        dialogBuilder.setView(dialogView)
        val alertDialog = dialogBuilder.create()
        alertDialog.setCanceledOnTouchOutside(false)
        alertDialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val yestxt = dialogView.findViewById<View>(R.id.yes_txt) as TextView
        val notxt = dialogView.findViewById<View>(R.id.no_txt) as TextView


        yestxt.setOnClickListener {
            try {
                alertDialog.dismiss()
                AppConfiguration.multichoice = "not fill";
                AppConfiguration.singlechoice = "not fill";
                AppConfiguration.imagechoice = "not fill";
                AppConfiguration.addtextchoice = "not fill";
                AppConfiguration.question3 = "";
                AppConfiguration.question4 = "";
                AppConfiguration.question5 = "";
                AppConfiguration.question6 = "";
                AppConfiguration.question8 = "";
                AppConfiguration.question12 = "";
                AppConfiguration.question13 = "";

                Utils.setPref(mContext, "fill", "0");
                if (whichload.equals("click", ignoreCase = true)) {
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
                } else {
                    navItemIndex = 0
                    CURRENT_TAG = TAG_HOME
                    loadHomeFragment()
                }

            } catch (e: Exception) {
            }
        }

        notxt.setOnClickListener {
            try {
                alertDialog.dismiss()
                bottomNavigation.show(2, true)
            } catch (e: java.lang.Exception) {
            }
        }

        try {
            alertDialog.show()
        } catch (e: Exception) {
        }
    }




    //    For Use Feedback Fragment
    /*override fun onBackPressed() {

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

                   /*fill the edittext variable*/
                   if (AppConfiguration.question3.equals("", ignoreCase = true) && AppConfiguration.question4.equals("", ignoreCase = true)
                           && AppConfiguration.question5.equals("", ignoreCase = true) && AppConfiguration.question6.equals("", ignoreCase = true)
                           && AppConfiguration.question8.equals("", ignoreCase = true) && AppConfiguration.question12.equals("", ignoreCase = true)
                           && AppConfiguration.question13.equals("", ignoreCase = true)) {
                       AppConfiguration.addtextchoice = "not fill"
                   } else {
                       AppConfiguration.addtextchoice = "fill"
                   }
                   //            /*Feedback survey quite variable*/
                   if (AppConfiguration.multichoice.equals("fill", ignoreCase = true)
                           || AppConfiguration.singlechoice.equals("fill", ignoreCase = true)
                           || AppConfiguration.imagechoice.equals("fill", ignoreCase = true)
                           || AppConfiguration.addtextchoice.equals("fill", ignoreCase = true)) {
                       Utils.setPref(mContext, "fill", "1");
                   }
                   if (AppConfiguration.multichoice.equals("not fill", ignoreCase = true)
                           && AppConfiguration.singlechoice.equals("not fill", ignoreCase = true)
                           && AppConfiguration.imagechoice.equals("not fill", ignoreCase = true)
                           && AppConfiguration.addtextchoice.equals("not fill", ignoreCase = true)) {
                       Utils.setPref(mContext, "fill", "0");
                   }

                   AppConfiguration.lastpositionofnavigation = navItemIndex.toString()
                   Log.d("selectedId :", "" + AppConfiguration.lastpositionofnavigation)

                   if (navItemIndex != 0) {
                       if (AppConfiguration.lastpositionofnavigation.equals("2", ignoreCase = true)) {
                           if (Utils.getPref(mContext, "fill") != null) {
                               if (Utils.getPref(mContext, "fill").equals("1", ignoreCase = true)) {
                                   loadPageToFeedback("back")
                               } else {
                                   navItemIndex = 0
                                   CURRENT_TAG = TAG_HOME
                                   loadHomeFragment()
                               }
                           } else {
                               navItemIndex = 0
                               CURRENT_TAG = TAG_HOME
                               loadHomeFragment()
                           }
                       } else {
                           navItemIndex = 0
                           CURRENT_TAG = TAG_HOME
                           loadHomeFragment()

                       }

                   } else {
                       try {
                           super.onBackPressed()
                       } catch (exp: IllegalStateException) { // can output some information here
                           finish()
                       }
                   }
               }

               //            super.onBackPressed()
           }


       }*/

    //For Use Travel Fragment
    override fun onBackPressed() {

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

                /*for travel page working then use this code*/
                if (navItemIndex != 0) {
                    if (navItemIndex != 3) {
                        if (!viewmoreStr.equals("", ignoreCase = true)) {
                            bottomNavigation.show(3, true)
                            intent.putExtra("whichPageRun", "")
                            navItemIndex = 3
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

            super.onBackPressed()
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

        navItemIndex = 5
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
            navItemIndex = 5
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
            navItemIndex = 5
            bottomNavigation.show(5, true)
            intent.putExtra("whichPageRun", "")
            fragment = StoryFragment()
            loadFragment(fragment as StoryFragment)
        }
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    override fun onResume() {
        Utils.addCartItemCount(mContext, cart_count_item_txt)
        super.onResume()
    }
}

