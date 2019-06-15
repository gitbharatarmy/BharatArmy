package com.bharatarmy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.bharatarmy.Fragment.OneFragment;
import com.bharatarmy.Fragment.TwoFragment;
import com.bharatarmy.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class GalleryActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    public static int selectionTitle;
    public static String title;
    public static int maxSelection;
    public static int mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_gallery);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_left_arrow2);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnResult();
            }
        });

        title=getIntent().getExtras().getString("title");
        maxSelection=getIntent().getExtras().getInt("maxSelection");
        if (maxSelection==0) maxSelection = Integer.MAX_VALUE;
        mode=getIntent().getExtras().getInt("mode");
        setTitle(title);
        selectionTitle=0;

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        OpenGalleryActivity.selected.clear();
        OpenGalleryActivity.imagesSelected.clear();

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if(selectionTitle>0){
            setTitle(String.valueOf(selectionTitle));
        }
    }

    //This method set up the tab view for images and videos
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        if(mode==1 || mode==2) {
            adapter.addFragment(new OneFragment(), "Images");
        }
        if(mode==1||mode==3)
            adapter.addFragment(new TwoFragment(), "Videos");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final ArrayList<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    private void returnResult() {
        Intent returnIntent = new Intent();
        returnIntent.putStringArrayListExtra("result",OpenGalleryActivity.imagesSelected);
        setResult(RESULT_OK, returnIntent);
        finish();
    }
}