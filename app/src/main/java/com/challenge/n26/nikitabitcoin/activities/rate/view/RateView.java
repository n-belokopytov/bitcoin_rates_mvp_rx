package com.challenge.n26.nikitabitcoin.activities.rate.view;

import com.challenge.n26.nikitabitcoin.activities.rate.model.RateModel;

/**
 * Created by 805640 on 28.08.2016.
 */

public interface RateView {

    void refreshView(RateModel rateModel);
    void showError(String message);
}
