package com.challenge.n26.nikitabitcoin.misc.ui;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Region;
import android.os.Bundle;
import android.view.ScaleGestureDetector;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by 805640 on 28.08.2016.
 */

public class BarRenderer implements GraphRenderer {

    private static final float GOOD_LOOK_MODIFIER_SCALE_Y = 0.75f;
    private static final float GOOL_LOOK_MODIFIER_SCALE_STRIDE = 1.2f;
    protected Paint mLabelPaint = null;
    protected Paint mGraphPaint = null;

    protected int mGraphStride = 50;

    protected float[] mRects = null;
    private float mMinX;
    private float mMaxY;

    List<String> mLabelsY = new ArrayList<>();
    List<String> mLabelsX = new ArrayList<>();

    private float mTranslationX;
    private float mTranslationY;

    private int mWidth;
    private int mHeight;

    private float mScaleY;
    private float mScaleX = 1f;

    private int mPaddingLeft;
    private int mPaddingTop;
    private int mPaddingRight;
    private int mPaddingBottom;

    public BarRenderer() {
        mGraphPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mGraphPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mGraphPaint.setColor(Color.DKGRAY);

        mLabelPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLabelPaint.setStyle(Paint.Style.STROKE);
        mLabelPaint.setColor(Color.DKGRAY);
        mLabelPaint.setTextSize(12);
    }

    @Override
    public void setLabelSize(float size) {
        mLabelPaint.setTextSize(size);
    }

    @Override
    public void setLabelColor(int color) {
        mLabelPaint.setColor(color);
    }

    @Override
    public void setGraphColor(int color) {
        mGraphPaint.setColor(color);
    }

    @Override
    public void setBackgroundColor(int background) {

    }

    @Override
    public void setStride(int pixels) {
        mGraphStride = pixels;
    }

    @Override
    public void setValues(List<PointF> values) {
        mTranslationX = 0;
        mScaleX = 1;
        generateLabelsAndMinMax(values);
        computeOptimalStride();
        generateGeometry(values);
        adjustScale();
        adjustTranslation();
    }

    private void adjustTranslation() {
        float numberOfValuesOnScreen = mWidth / GOOL_LOOK_MODIFIER_SCALE_STRIDE / mGraphStride;
        mTranslationX -= (mRects.length / 4 - numberOfValuesOnScreen) * GOOL_LOOK_MODIFIER_SCALE_STRIDE * mGraphStride;
    }

    protected void generateLabelsAndMinMax(List<PointF> values) {
        DateFormat format1 = SimpleDateFormat.getDateInstance(DateFormat.SHORT);
        mLabelsX.clear();
        mLabelsY.clear();
        for (PointF value :
                values) {
            //Normally Formatting should be done through a delegate object
            mLabelsX.add(format1.format(new Date((long)value.x * 1000)));
            mLabelsY.add(String.format("%.2f", value.y));

            mMinX = value.x < mMinX ? value.x : mMinX;
            mMaxY = value.y > mMaxY ? value.y : mMaxY;
        }
    }

    protected void generateGeometry(List<PointF> values) {
        PointF valueToAdd;
        mRects = new float[values.size() * 4];
        for (int i = 0; i < mRects.length; i += 4) {
            valueToAdd = values.get(i / 4);
            mRects[i] = i / 4 * mGraphStride * GOOL_LOOK_MODIFIER_SCALE_STRIDE;
            mRects[i + 1] = valueToAdd.y;
            mRects[i + 2] = mRects[i] + mGraphStride;
            mRects[i + 3] = 0;
        }
    }

    protected void adjustScale() {
        if (mMaxY != 0) {
            mScaleY = mHeight / mMaxY * GOOD_LOOK_MODIFIER_SCALE_Y;
        }
    }

    private void computeOptimalStride() {
        for (String label :
                mLabelsX) {
            mGraphStride = Math.max((int) mLabelPaint.measureText(label) + 1, mGraphStride);
        }

        for (String label :
                mLabelsY) {
            mGraphStride = Math.max((int) mLabelPaint.measureText(label) + 1, mGraphStride);
        }
    }

    @Override
    public void setViewportSize(int w, int h) {
        mWidth = w;
        mHeight = h;
        mTranslationY = mHeight;
    }

//    could be called in an off thread to animate arrays of values
    @Override
    public void animate() {
    }

    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        mPaddingLeft = left;
        mPaddingTop = top;
        mPaddingRight = right;
        mPaddingBottom = bottom;
    }

    @Override
    public void translate(int x, int y) {
        mTranslationX += x;
        mTranslationY -= y;
    }

    @Override
    public void renderValues(Canvas canvas) {
        translate(canvas);
        if (mRects != null) {
            for (int i = 0; i < mRects.length; i += 4) {
                //Not very pretty, but will do for now
                //For centering text on the bar this would do
                //(mRects[i] + mRects[i + 2]) / 2 - mLabelPaint.measureText(mLabelsX.get(i / 4))
                //Ideally labels will have their own geometry generation pass, I don't want to do it
                //right now and will just render based on Bar geometry
                canvas.drawText(mLabelsY.get(i / 4),
                        mRects[i], - mRects[i + 1], mLabelPaint);
                canvas.drawRect(mRects[i], -mRects[i + 1],
                        mRects[i + 2], -mRects[i + 3], mGraphPaint);
                canvas.drawText(mLabelsX.get(i / 4),
                        mRects[i],
                        - mRects[i + 3], mLabelPaint);
            }
        }
    }

    protected void translate(Canvas canvas) {
        canvas.translate(mTranslationX - mPaddingLeft, mTranslationY - mPaddingBottom);
        canvas.scale(mScaleX, mScaleY);
    }

    @Override
    public void setScale(float p0) {
        mScaleX *= p0;
        mScaleX = Math.max(0.01f, Math.min(mScaleX, 1.0f));
        mTranslationX *= p0;
    }
}
