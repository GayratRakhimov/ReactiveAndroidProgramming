package com.gayratrakhimov.reactiveandroidprogramming.coinapi;

import com.gayratrakhimov.reactiveandroidprogramming.coinapi.json.ExchangeRatesResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CoinApiService {

    @GET("exchangerate/{asset_id}")
    Observable<ExchangeRatesResult> getExchangeRates(
            @Path("asset_id") String assetId
    );

}