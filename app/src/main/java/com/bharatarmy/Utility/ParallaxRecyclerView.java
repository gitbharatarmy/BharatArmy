package com.bharatarmy.Utility;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

public class ParallaxRecyclerView extends RecyclerView {

    public ParallaxRecyclerView(Context context) {
        super(context);
    }

    public ParallaxRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ParallaxRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    public void setupParallax(Context context) {
        setLayoutManager(new LinearLayoutManager(context));
        ParallaxScrollListener parallaxScrollListener = new ParallaxScrollListener();
        addOnScrollListener(parallaxScrollListener);
    }

}
