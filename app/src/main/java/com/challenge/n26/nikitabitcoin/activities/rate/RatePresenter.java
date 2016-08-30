package com.challenge.n26.nikitabitcoin.activities.rate;

import android.util.Log;
import android.widget.Toast;

import com.challenge.n26.nikitabitcoin.activities.rate.view.RateActivity;
import com.challenge.n26.nikitabitcoin.activities.rate.view.RateView;
import com.challenge.n26.nikitabitcoin.data.repo.RateRepo;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 805640 on 28.08.2016.
 */

public class RatePresenter {

    final private RateView mView;
    final private RateRepo mRateRepo;

    private RateRepo.Timespan mTimespan = RateRepo.Timespan.month;

    public RatePresenter(RateActivity view) {
        mRateRepo = new RateRepo();
        mView = view;
    }

    public RateView getView() {
        return mView;
    }

    public void loadRates() {
        mRateRepo.getRates(mTimespan)
                .doOnError(throwable -> getView().showError(throwable.getMessage()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> getView().showError(throwable.getMessage()))
                .subscribe(mView::refreshView);
    }

    public void setPeriod(RateRepo.Timespan timespan) {
        mTimespan = timespan;
    }
}
