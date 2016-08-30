package com.challenge.n26.nikitabitcoin.misc.ui;

import android.graphics.Canvas;

/**
 * Created by 805640 on 30.08.2016.
 */

public class LineRenderer extends BarRenderer {
    @Override
    public void renderValues(Canvas canvas) {
        translate(canvas);
        if (mRects != null) {
            for (int i = 0; i < mRects.length; i += 4) {
                canvas.drawText("" + mRects[i + 1], mRects[i], -mRects[i + 1], mGraphPaint);
                canvas.drawLine(mRects[i], -mRects[i + 1],
                        mRects[i] + mGraphStride, -mRects[i + 1], mGraphPaint);
            }
        }
    }

}
