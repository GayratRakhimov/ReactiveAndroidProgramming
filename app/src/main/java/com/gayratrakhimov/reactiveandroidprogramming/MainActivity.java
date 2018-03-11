package com.gayratrakhimov.reactiveandroidprogramming;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.gayratrakhimov.reactiveandroidprogramming.coinapi.CoinApiService;
import com.gayratrakhimov.reactiveandroidprogramming.coinapi.CoinApiServiceFactory;
import com.gayratrakhimov.reactiveandroidprogramming.coinapi.json.ExchangeRatesResult;
import com.gayratrakhimov.reactiveandroidprogramming.storio.StockUpdateTable;
import com.gayratrakhimov.reactiveandroidprogramming.storio.StorIOFactory;
import com.pushtorefresh.storio.sqlite.queries.Query;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

import static hu.akarnokd.rxjava.interop.RxJavaInterop.toV2Observable;

public class MainActivity extends RxAppCompatActivity {

    @BindView(R.id.hello_world_salute)
    TextView helloText;

    @BindView(R.id.stock_updates_recycler_view)
    RecyclerView recyclerView;

    private LinearLayoutManager layoutManager;
    private StockDataAdapter stockDataAdapter;

    @BindView(R.id.no_data_available)
    TextView noDataAvailableView;

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

//        coinApiService.getExchangeRates("USD")
//                .toObservable()
//                .subscribeOn(Schedulers.io())
//                .map(r -> r.getRates())
//                .flatMap(Observable::fromIterable)
//                .map(r -> StockUpdate.create(r))
//                .observeOn(AndroidSchedulers.mainThread()) // OK
//                .subscribe(stockUpdate -> { // OK
//                    Log.d("APP", "New update " + stockUpdate.getStockSymbol());
//                    stockDataAdapter.add(stockUpdate);
//                });

//        // Convert RxJava 1 Observable to RxJava 2 Observable
//        RxJavaInterop.toV2Observable(rx.Observable.just("One", "Two", "Three"))
//                .doOnNext(i -> log("doOnNext", i))
//                .subscribe(i -> log("subscribe", i));
//
//        RxJavaInterop.toV2Flowable(rx.Observable.just("One", "Two", "Three"))
//                .doOnNext(i -> log("doOnNext", i))
//                .subscribe(i -> log("subscribe", i));

        //        Observable.interval(0, 5, TimeUnit.SECONDS)
//                .flatMap(
//                        i -> Observable.<ExchangeRatesResult>error(new
//                                RuntimeException("Oops"))
//                )
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnError(error -> {
//                    log("doOnError", "error");
//                    Toast.makeText(this, "We couldn't reach internet - falling back to local data",
//                            Toast.LENGTH_SHORT).show();
//                })
//                .observeOn(Schedulers.io())
//                .map(r -> r.getRates())
//                .flatMap(Observable::fromIterable)
//                .map(StockUpdate::create)
//                .doOnNext(this::saveStockUpdate)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(stockUpdate -> {
//                    Log.d("APP", "New update " + stockUpdate.getStockSymbol());
//                    noDataAvailableView.setVisibility(View.GONE);
//                    stockDataAdapter.add(stockUpdate);
//                }, error -> {
//                    if (stockDataAdapter.getItemCount() == 0) {
//                        noDataAvailableView.setVisibility(View.VISIBLE);
//                    }
//                });

//        Observable.<String>error(new Error("Crash!"))
//                .doOnError(ErrorHandler.get())
//                .subscribe(item -> log("subscribe", item), ErrorHandler.get());

        coinApiService.getExchangeRates("USD")
                .compose(bindToLifecycle())
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(error -> {
                    log("doOnError", "error");
                    Toast.makeText(this, "We couldn't reach internet - falling back to local data",
                            Toast.LENGTH_SHORT)
                            .show();
                })
                .observeOn(Schedulers.io())
                .map(ExchangeRatesResult::getRates)
                .flatMap(Observable::fromIterable)
                .map(StockUpdate::create)
                .doOnNext(this::saveStockUpdate)
                .onExceptionResumeNext(
                        v2(StorIOFactory.get(this)
                                .get()
                                .listOfObjects(StockUpdate.class)
                                .withQuery(Query.builder()
                                        .table(StockUpdateTable.TABLE)
                                        .orderBy("date DESC")
                                        .limit(50)
                                        .build())
                                .prepare()
                                .asRxObservable())
                                .take(1)
                                .flatMap(Observable::fromIterable)
                )
                .observeOn(AndroidSchedulers.mainThread()) // OK
                .subscribe(stockUpdate -> {
                    Log.d("APP", "New update " + stockUpdate.getStockSymbol());
                    noDataAvailableView.setVisibility(View.GONE);
                    stockDataAdapter.add(stockUpdate);
                }, error -> {
                    if (stockDataAdapter.getItemCount() == 0) {
                        noDataAvailableView.setVisibility(View.VISIBLE);
                    }
                });

    }

    private void twitter() {
        StatusListener listener = new StatusListener() {
            @Override
            public void onStatus(Status status) {
                System.out.println(status.getUser().getName() + " : " + status.getText());
            }

            @Override
            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
            }

            @Override
            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
            }

            @Override
            public void onScrubGeo(long userId, long upToStatusId) {
            }

            @Override
            public void onStallWarning(StallWarning warning) {
            }

            @Override
            public void onException(Exception ex) {
                ex.printStackTrace();
            }
        };

        final Configuration configuration = new ConfigurationBuilder()
                .setDebugEnabled(BuildConfig.DEBUG)
                .setOAuthConsumerKey("tTlvwBfqduVadKKEwMXDCmzA4")
                .setOAuthConsumerSecret("FiIOveHm9jLAtf0YSopWROeOFo3OA9VBM2CAuKwZ8AoL1gl4AK")
                .setOAuthAccessToken("195655474-QY8neLxXxqOsF8PGM8MYLsYGyQxQZA73S4qp0Sc2")
                .setOAuthAccessTokenSecret("lIiock0OTkR4TflFPb9pSMjLL8pN9JKIYKBhWMWwtxyMa")
                .build();

        TwitterStream twitterStream = new TwitterStreamFactory(configuration).getInstance();
        twitterStream.addListener(listener);
        twitterStream.filter();
        twitterStream.filter(
                new FilterQuery()
                        .track("Yahoo", "Google", "Microsoft")
                        .language("en")
        );
    }

    private void saveStockUpdate(StockUpdate stockUpdate) {
        log("saveStockUpdate", stockUpdate.getStockSymbol());
        StorIOFactory.get(this)
                .put()
                .object(stockUpdate)
                .prepare()
                .asRxSingle()
                .subscribe();
    }

    public static <T> Observable<T> v2(rx.Observable<T> source) {
        return toV2Observable(source);
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
