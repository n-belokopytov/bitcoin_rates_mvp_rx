package com.challenge.n26.nikitabitcoin.misc.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.os.Parcelable;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import com.challenge.n26.nikitabitcoin.R;
import com.challenge.n26.nikitabitcoin.misc.util.ResUtils;

import java.util.List;

/**
 * Created by 805640 on 28.08.2016.
 */

public class GraphView extends View implements GestureDetector.OnGestureListener {

    static final int BAR = 1;
    static final int LINES = 2;

    private int mLabelColor;
    private int mLabelSize;
    private int mGraphColor;

    BarRenderer mRenderer;

    private GestureDetectorCompat mDetector;
    private ScaleGestureDetector mScaleDetector;

    public GraphView(Context context) {
        super(context);
        init(null);
    }

    public GraphView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public GraphView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    void init(AttributeSet attrs) {
        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.GraphView,
                0, 0);

        try {
            int renderMode = a.getInteger(R.styleable.GraphView_renderMode, 1);
            switch (renderMode) {
                case BAR:
                    mRenderer = new BarRenderer();
                    break;
                case LINES:
                    mRenderer = new LineRenderer();
            }

//            int background = a.getInteger(R.styleable.GraphView_labelColor, ResUtils.getThemeColor(getContext(), R.attr.background));
//            mRenderer.setBackgroundColor(background);
            mLabelColor = a.getInteger(R.styleable.GraphView_labelColor, ResUtils.getThemeColor(getContext(), R.attr.colorPrimary));
            mRenderer.setLabelColor(mLabelColor);
            mLabelSize = a.getDimensionPixelSize(R.styleable.GraphView_labelTextSize, 20);
            mRenderer.setLabelSize(mLabelSize);
            mGraphColor = a.getInteger(R.styleable.GraphView_graphColor, ResUtils.getThemeColor(getContext(), R.attr.colorAccent));
            mRenderer.setGraphColor(mGraphColor);
        } finally {
            a.recycle();
        }
        mDetector = new GestureDetectorCompat(getContext(), this);
        mScaleDetector = new ScaleGestureDetector(getContext(), new ScaleListener());
    }

    public void setValues(List<PointF> values) {
        if (mRenderer != null) {
            mRenderer.setValues(values);
            invalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //mBackgroundRenderer.renderValues(canvas);
        //mShadowRenderer.renderValues(canvas);
        if (mRenderer != null) {
            mRenderer.renderValues(canvas);
        }
    }

    public void setRenderer(BarRenderer renderer) {
        mRenderer = renderer;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRenderer.setViewportSize(w, h);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int desiredWidth = 100;
        int desiredHeight = 100;

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        //Measure Width
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            width = Math.min(desiredWidth, widthSize);
        } else {
            width = desiredWidth;
        }

        //Measure Height
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            height = Math.min(desiredHeight, heightSize);
        } else {
            height = desiredHeight;
        }

        if (mRenderer != null) {
            mRenderer.setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight(), getPaddingBottom());
        }

        setMeasuredDimension(width, height);
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        if (mRenderer != null) {
            mRenderer.translate(-(int)v, 0);
        }
        invalidate();
        return true;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.mDetector.onTouchEvent(event);
        this.mScaleDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }


    private class ScaleListener
            extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mRenderer.setScale(detector.getScaleFactor());
            invalidate();
            return true;
        }
    }
}
