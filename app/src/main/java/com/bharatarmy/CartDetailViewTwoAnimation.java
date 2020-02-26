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

public class CartDetailViewTwoAnimation extends LinearLayout {

    private int[] mShopBagCoordinateOnScreen;
    private Animation mAnimation;
    private AnimatorSet mAnimatorSet;

    private float mOriginalTranslationX;
    private float mOriginalTranslationY;
    ArrayList<Animator> animatorArrayList = new ArrayList<>();

    public CartDetailViewTwoAnimation(Context context) {
        super(context);
        initEnterAnimation();


    }

    public CartDetailViewTwoAnimation(Context context, AttributeSet attrs) {
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
                CartDetailViewTwoAnimation.this.setBackgroundColor(0x70000000);

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mOriginalTranslationX = getChildAt(0).getTranslationX();
                mOriginalTranslationY = getChildAt(0).getTranslationY();
                initExitTwoAnimation();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    private void initExitTwoAnimation() {

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

        //Bezier animation
        int[] startCoordinateOnScreen = new int[2];
        this.getLocationOnScreen(startCoordinateOnScreen);
        //0.475 Is calculated based on the reduction ratio
        float startX = (float) (startCoordinateOnScreen[0] + getWidth() * 0.475);
        float startY = (float) (startCoordinateOnScreen[1] + getHeight() * 0.475);

// Subtract startX because the coordinates need to be calculated relative to the coordinate dots of the View
        // The reason for getWidth () * 0.05 / 2 is to make the two centers coincide at the midpoint of the flight
        float endX = (float) (mShopBagCoordinateOnScreen[0] - startX - getWidth() * 0.05 / 2);
        float endY = (float) (mShopBagCoordinateOnScreen[1] - startY - getHeight() * 0.033 / 2);


        Path path = new Path();
        path.moveTo(0, 0);
        path.quadTo(0, (0 + endY) / 2, endX, endY);

        final PathMeasure pathMeasure = new PathMeasure(path, false);
        final ValueAnimator bezierAnimator = ValueAnimator.ofFloat(0, pathMeasure.getLength());
        bezierAnimator.setInterpolator(new AccelerateInterpolator());
        bezierAnimator.setDuration(800);
        bezierAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
                float currentPos[] = new float[2];
                pathMeasure.getPosTan(value, currentPos, null);
                getChildAt(0).setTranslationX(currentPos[0]);
                getChildAt(0).setTranslationY(currentPos[1]);
            }
        });
        bezierAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
//                GoodsDetailView.this.setBackgroundColor(Color.TRANSPARENT);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                ViewGroup parent = (ViewGroup) getParent();
                if (parent != null) {
                    parent.removeView(CartDetailViewTwoAnimation.this);
                }
                reverse();

            }

            @Override
            public void onAnimationCancel(Animator animation) {
                ViewGroup parent = (ViewGroup) getParent();
                if (parent != null) {
                    parent.removeView(CartDetailViewTwoAnimation.this);
                }
                reverse();

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        /* use for two animation work Sequentially*/
//        mAnimatorSet.playSequentially(scaleAnimator, bezierAnimator);

        /* use for two animation work simultaneously */
        animatorArrayList.add(scaleAnimator);
        animatorArrayList.add(bezierAnimator);
        mAnimatorSet.playTogether(animatorArrayList);

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
                  parent.removeView(CartDetailViewTwoAnimation.this);
              }
              reverse();
          }

          @Override
          public void onAnimationCancel(Animator animation) {
              ViewGroup parent = (ViewGroup) getParent();
              if (parent != null) {
                  parent.removeView(CartDetailViewTwoAnimation.this);
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
            parent.addView(CartDetailViewTwoAnimation.this);
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
