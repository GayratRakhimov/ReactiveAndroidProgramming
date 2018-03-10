package com.gayratrakhimov.reactiveandroidprogramming.coinapi;

import com.gayratrakhimov.reactiveandroidprogramming.BuildConfig;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CoinApiServiceFactory {

    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY);

    OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(new ApiKeyInterceptor())
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build();

    Retrofit retrofit = new Retrofit.Builder()
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.API_ENDPOINT)
            .build();

    public CoinApiService create() {
        return retrofit.create(CoinApiService.class);
    }

}