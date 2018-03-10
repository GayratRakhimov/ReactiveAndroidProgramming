package com.gayratrakhimov.reactiveandroidprogramming.coinapi;

import com.gayratrakhimov.reactiveandroidprogramming.BuildConfig;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ApiKeyInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder requestBuilder = request.newBuilder()
                .header("X-CoinAPI-Key", BuildConfig.API_KEY);
        return chain.proceed(requestBuilder.build());
    }

}