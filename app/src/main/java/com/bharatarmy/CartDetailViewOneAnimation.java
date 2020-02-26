package com.bharatarmy;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class CartDetailViewOneAnimation extends LinearLayout {

    private int[] mShopBagCoordinateOnScreen;
    private Animation mAnimation;
    private AnimatorSet mAnimatorSet;

    private float mOriginalTranslationX;
    private float mOriginalTranslationY;

    public CartDetailViewOneAnimation(Context context) {
        super(context);
        initEnterAnimation();


    }

    public CartDetailViewOneAnimation(Context context, AttributeSet attrs) {
        super(context, attrs);
        initEnterAnimation();

    }

    private void initEnterAnimation() {
        mAnimatorSet = new AnimatorSet();


        //Entry time animation
        mAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_left);
        mAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                CartDetailViewOneAnimation.this.setBackgroundColor(0x70000000);

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mOriginalTranslationX = getChildAt(0).getTranslationX();
                mOriginalTranslationY = getChildAt(0).getTranslationY();
                initExitOneAnimation();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }


    private void initExitOneAnimation() {

        //Zoom animation to zoom out to original  0.05
        final ValueAnimator scaleAnimator = ValueAnimator.ofFloat(1.0f, 0.08f);  //1.0f, 0.05f
        scaleAnimator.setDuration(800);
        scaleAnimator.setInterpolator(new LinearInterpolator());
        scaleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
                getChildAt(0).setScaleX(value);
                getChildAt(0).setScaleY(value);
            }
        });
        scaleAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                ViewGroup parent = (ViewGroup) getParent();
                if (parent != null) {
                    parent.removeView(CartDetailViewOneAnimation.this);
                }
                reverse();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                ViewGroup parent = (ViewGroup) getParent();
                if (parent != null) {
                    parent.removeView(CartDetailViewOneAnimation.this);
                }
                reverse();
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        /* use for two animation work Sequentially*/
        mAnimatorSet.playSequentially(scaleAnimator);


    }

    private void reverse() {
        getChildAt(0).setScaleX(1f);
        getChildAt(0).setScaleY(1f);
        getChildAt(0).setTranslationX(mOriginalTranslationX);
        getChildAt(0).setTranslationY(mOriginalTranslationY);
    }


    public void addWithAnimation(final ViewGroup parent) {
        if (getParent() == null) {
            parent.addView(CartDetailViewOneAnimation.this);
        }
        getChildAt(0).startAnimation(mAnimation);
    }

    public void removeWithAnimation(boolean isStartAnimation) {
        if (isStartAnimation) {
            mAnimatorSet.start();
        } else {
            ViewGroup parent = (ViewGroup) getParent();
            if (parent != null) {
                parent.removeView(this);
            }
        }

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!mAnimatorSet.isRunning()) {
            View child = getChildAt(0);
            if (child != null) {
                float y = ev.getY();
                int top = child.getTop();
                int bottom = child.getBottom();
                if (y <= top || y >= bottom) {
                    removeWithAnimation(false);
                    return true;
                }
            }
        } else {
            return true;
        }
        return super.onInterceptTouchEvent(ev);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }


    public void setShopBagCoordinate(int[] shopBagCoordinateOnScreen) {
        mShopBagCoordinateOnScreen = shopBagCoordinateOnScreen;
    }
}
