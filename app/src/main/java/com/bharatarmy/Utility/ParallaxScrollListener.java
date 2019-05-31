package com.bharatarmy.Utility;



import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Adapter.ViewPagerSliderAdapter;

public class ParallaxScrollListener extends RecyclerView.OnScrollListener {

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        ((ViewPagerSliderAdapter)recyclerView.getAdapter()).reTranslate();
    }
}
