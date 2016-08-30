package com.challenge.n26.nikitabitcoin.activities.rate.model;

import android.graphics.PointF;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 805640 on 28.08.2016.
 */

public class RateModel {

    List<PointF> mPoints;

    public List<PointF> getPoints() {
        return mPoints;
    }

    public RateModel(RateNetworkModel rateNetworkModel) {
        mPoints = new ArrayList<>();

        for(int i = 0; i < rateNetworkModel.getSize(); i++) {
            mPoints.add(new PointF(rateNetworkModel.getPointTimestamp(i), rateNetworkModel.getPointRate(i)));
        }
    }
}
