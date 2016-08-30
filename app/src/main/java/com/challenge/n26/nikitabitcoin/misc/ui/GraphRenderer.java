package com.challenge.n26.nikitabitcoin.misc.ui;

import android.graphics.Canvas;
import android.graphics.PointF;

import java.util.List;

/**
 * Created by 805640 on 28.08.2016.
 */
public interface GraphRenderer {
    void setLabelSize(float size);
    void setLabelColor(int color);
    void setGraphColor(int color);
    void setBackgroundColor(int background);
    void setStride(int pixels);
    void setPadding(int left, int top, int right, int bottom);
    void translate(int x, int y);
    void setValues(List<PointF> values);
    void setViewportSize(int w, int h);
    void animate();
    void renderValues(Canvas canvas);
    void setScale(float p0);
}
