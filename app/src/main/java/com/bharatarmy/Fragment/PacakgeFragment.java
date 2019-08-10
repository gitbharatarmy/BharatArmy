package com.bharatarmy.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bharatarmy.AlphaPageTransformer;
import com.bharatarmy.R;

public class PacakgeFragment extends Fragment {

    final static String LAYOUT_ID = "layoutid";

    ViewPager cardviewPager;

    public static PacakgeFragment newInstance(int layoutId) { //int layoutId
        PacakgeFragment pane = new PacakgeFragment();
        Bundle args = new Bundle();
        args.putInt(LAYOUT_ID, layoutId);
        pane.setArguments(args);
        return pane;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(getArguments().getInt(LAYOUT_ID, -1), container, false);
        cardviewPager=(ViewPager)rootView.findViewById(R.id.cardViewPager);

        cardviewPager.setOffscreenPageLimit(3);
        cardviewPager.setPageMargin(40);
        cardviewPager.setPageTransformer(true,new AlphaPageTransformer());
        cardviewPager.setAdapter(new MyPagerAdapter());
        return rootView;
    }

    public class MyPagerAdapter extends PagerAdapter {

//        private List<ViewPagerItemBean> mData;
        private Context mContext;

        public MyPagerAdapter() {
//            mData = data;
//            mContext = context;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View inflate = LayoutInflater.from(container.getContext()).inflate(R.layout.pacakge_fragment2, container, false);

            container.addView(inflate);
            return inflate;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(((View) object));
        }
    }
}