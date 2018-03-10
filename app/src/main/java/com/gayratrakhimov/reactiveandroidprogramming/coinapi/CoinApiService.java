package com.gayratrakhimov.reactiveandroidprogramming.coinapi;

import com.gayratrakhimov.reactiveandroidprogramming.coinapi.json.ExchangeRatesResult;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CoinApiService {

    @GET("exchangerate/{asset_id}")
    Single<ExchangeRatesResult> getExchangeRates(
            @Path("asset_id") String assetId
    );

}