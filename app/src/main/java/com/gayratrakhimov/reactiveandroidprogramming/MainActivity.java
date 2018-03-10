package com.gayratrakhimov.reactiveandroidprogramming;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.gayratrakhimov.reactiveandroidprogramming.coinapi.CoinApiService;
import com.gayratrakhimov.reactiveandroidprogramming.coinapi.CoinApiServiceFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.hello_world_salute)
    TextView helloText;

    @BindView(R.id.stock_updates_recycler_view)
    RecyclerView recyclerView;

    private LinearLayoutManager layoutManager;
    private StockDataAdapter stockDataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        Observable.just("Hello! Please use this app responsibly!")
                .subscribe(helloText::setText);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        stockDataAdapter = new StockDataAdapter();
        recyclerView.setAdapter(stockDataAdapter);

//        Observable.just(
//                new StockUpdate("GOOGLE", 12.43, new Date()),
//                new StockUpdate("APPL", 645.1, new Date()),
//                new StockUpdate("TWTR", 1.43, new Date())
//        ).subscribe(stockUpdate -> {
//            Log.d("APP", "New update " + stockUpdate.getStockSymbol());
//            stockDataAdapter.add(stockUpdate);
//        });

//        Observable.interval(0, 5, TimeUnit.SECONDS)
//                .flatMap(
//                        i -> yahooService.yqlQuery(query, env)
//                                .toObservable()
//                )
//                .subscribeOn(Schedulers.io())
//                .map(r -> r.getQuery().getResults().getQuote())
//                .flatMap(Observable::fromIterable)
//                .map(r -> StockUpdate.create(r))
//                .observeOn(AndroidSchedulers.mainThread()) // OK
//                .subscribe(stockUpdate -> { // OK
//                    Log.d("APP", "New update " + stockUpdate.getStockSymbol());
//                    stockDataAdapter.add(stockUpdate);
//                });

        CoinApiService coinApiService = new CoinApiServiceFactory().create();

        coinApiService.getExchangeRates("USD")
                .toObservable()
                .subscribeOn(Schedulers.io())
                .map(r -> r.getRates())
                .flatMap(Observable::fromIterable)
                .map(r -> StockUpdate.create(r))
                .observeOn(AndroidSchedulers.mainThread()) // OK
                .subscribe(stockUpdate -> { // OK
                    Log.d("APP", "New update " + stockUpdate.getStockSymbol());
                    stockDataAdapter.add(stockUpdate);
                });

    }

    private void log(Throwable throwable) {
        Log.e("APP", "Error", throwable);
    }

    private void log(String stage, String item) {
        Log.d("APP", stage + ":" + Thread.currentThread().getName() + ":" + item);
    }

    private void log(String stage) {
        Log.d("APP", stage + ":" + Thread.currentThread().getName());
    }

}
