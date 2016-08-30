package com.challenge.n26.nikitabitcoin.activities.rate.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 805640 on 28.08.2016.
 */

public class RateNetworkModel {
    List<RateNetworkPointModel> values = new ArrayList<>();

    public int getSize() {
        return values.size();
    }

    public long getPointTimestamp(int i) {
        return values.get(i).x;
    }

    public float getPointRate(int i) {
        return values.get(i).y;
    }


    private class RateNetworkPointModel {
        long x;
        float y;
    }
}
