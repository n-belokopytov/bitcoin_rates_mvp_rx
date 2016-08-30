package com.challenge.n26.nikitabitcoin.data.network;

import com.challenge.n26.nikitabitcoin.activities.rate.model.RateNetworkModel;

import java.util.HashMap;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by 805640 on 28.08.2016.
 */

public interface RateAPI {

    String endpoint = "https://blockchain.info";


    @GET("charts/market-price")
    Observable<RateNetworkModel> getCharts(@QueryMap HashMap<String, String> queryMap);
//    "https://blockchain.info/charts/$chart-type?format=json"


}
