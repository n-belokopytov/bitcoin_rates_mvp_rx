package com.challenge.n26.nikitabitcoin.misc.ui;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * Created by 805640 on 30.08.2016.
 */

public class PoliteSwipeToRefresh extends SwipeRefreshLayout {

    private int mTouchSlop;
    private float mPrevX;

    public PoliteSwipeToRefresh(Context context, AttributeSet attrs) {
        super(context, attrs);

//        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mTouchSlop = 50;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPrevX = MotionEvent.obtain(event).getX();
                break;

            case MotionEvent.ACTION_MOVE:
                final float eventX = event.getX();
                float xDiff = Math.abs(eventX - mPrevX);

                if (xDiff > mTouchSlop) {
                    return false;
                }
        }

        return super.onInterceptTouchEvent(event);
    }
}