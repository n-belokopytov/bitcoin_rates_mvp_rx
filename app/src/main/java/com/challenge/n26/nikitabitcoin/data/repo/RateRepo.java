package com.challenge.n26.nikitabitcoin.data.repo;

import com.challenge.n26.nikitabitcoin.activities.rate.model.RateModel;
import com.challenge.n26.nikitabitcoin.data.network.RateAPI;

import java.util.HashMap;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Created by 805640 on 28.08.2016.
 */

public class RateRepo {

    private enum Format {
        json;
    };

    public enum Timespan {
        month("30days"),
        twoMonths("60days"),
        halfYear("180days"),
        year("1year");

        String mTimespan;

        Timespan(String s) {
            mTimespan = s;
        }
    }

    private Retrofit mRetrofit;
    private RateAPI mRateAPI;

    public RateRepo() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(RateAPI.endpoint)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        mRateAPI = mRetrofit.create(RateAPI.class);
    }

    public Observable<RateModel> getRates(Timespan timespan) {
        HashMap<String, String> query = new HashMap<>();
        query.put("timespan", timespan.mTimespan);
        query.put("format", Format.json.name());
        return mRateAPI.getCharts(query).map(RateModel::new);
    }
}
