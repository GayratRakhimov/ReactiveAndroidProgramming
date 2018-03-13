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

import java.util.concurrent.TimeUnit;

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

//        coinApiService.getExchangeRates("USD")
//                .compose(bindToLifecycle())
//                .toObservable()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnError(ErrorHandler.get())
//                .observeOn(Schedulers.io())
//                .map(ExchangeRatesResult::getRates)
//                .flatMap(Observable::fromIterable)
//                .map(StockUpdate::create)
//                .doOnNext(this::saveStockUpdate)
//                .onExceptionResumeNext(
//                        v2(StorIOFactory.get(this)
//                                .get()
//                                .listOfObjects(StockUpdate.class)
//                                .withQuery(Query.builder()
//                                        .table(StockUpdateTable.TABLE)
//                                        .orderBy("date DESC")
//                                        .limit(50)
//                                        .build())
//                                .prepare()
//                                .asRxObservable())
//                                .take(1)
//                                .flatMap(Observable::fromIterable)
//                )
//                .observeOn(AndroidSchedulers.mainThread()) // OK
//                .subscribe(stockUpdate -> {
//                    Log.d("APP", "New update " + stockUpdate.getStockSymbol());
//                    noDataAvailableView.setVisibility(View.GONE);
//                    stockDataAdapter.add(stockUpdate);
//                }, error -> {
//                    if (stockDataAdapter.getItemCount() == 0) {
//                        noDataAvailableView.setVisibility(View.VISIBLE);
//                    }
//                });


        final Configuration configuration = new ConfigurationBuilder()
                .setDebugEnabled(BuildConfig.DEBUG)
                .setOAuthConsumerKey("pWfKxSWn4Dj0dEhbcouFhwMeN")
                .setOAuthConsumerSecret("OXlL55fGXc1hqHhjkkHPYTKAY7Emdmf4DjdziGb82Su8cxxnUf")
                .setOAuthAccessToken("195655474-BmqYZkQasiqO5fJu5mK08brpdPmSdpogM1Z24nfw")
                .setOAuthAccessTokenSecret("En6ouDbYITOMc7r9lMMh0YvZzq2c9yIpY43YYL0cjHp1Q")
                .build();

        final FilterQuery filterQuery = new FilterQuery()
                .track("BTC","BTH")
                .language("en");

        final String[] trackingKeywords = {"Yahoo", "Google", "Microsoft"};

        Observable.merge(
                coinApiService.getExchangeRates("USD")
                        .toObservable()
                        .map(ExchangeRatesResult::getRates)
                        .flatMap(Observable::fromIterable)
                        .map(StockUpdate::create),
                observeTwitterStream(configuration, filterQuery)
                        .sample(5000, TimeUnit.MILLISECONDS)
                        .map(StockUpdate::create)
                        .flatMapMaybe(update -> Observable.fromArray(trackingKeywords)
                                .filter(keyword -> update.getStatus().toLowerCase().contains(keyword.toLowerCase()))
                                .map(keyword -> update)
                                .firstElement()
                        )
        )
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .doOnError(ErrorHandler.get())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(error -> {
                    Toast.makeText(this, "We couldn't reach internet - falling back to local data",
                            Toast.LENGTH_SHORT)
                            .show();
                })
                .observeOn(Schedulers.io())
                .doOnNext(this::saveStockUpdate)
                .doOnError(ErrorHandler.get())
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
                .observeOn(AndroidSchedulers.mainThread())
                .filter(update -> !stockDataAdapter.contains(update))
                .subscribe(stockUpdate -> {
                    Log.d("APP", "New update " + stockUpdate.getStockSymbol());
                    noDataAvailableView.setVisibility(View.GONE);
                    stockDataAdapter.add(stockUpdate);
                    recyclerView.smoothScrollToPosition(0);
                }, error -> {
                    Log.e("APP", "Error", error);
                    if (stockDataAdapter.getItemCount() == 0) {
                        noDataAvailableView.setVisibility(View.VISIBLE);
                    }
                });

    }

    Observable<Status> observeTwitterStream(Configuration configuration, FilterQuery filterQuery) {
        return Observable.create(emitter -> {
            final TwitterStream twitterStream = new TwitterStreamFactory(configuration).getInstance();

            emitter.setCancellable(() -> {
                Schedulers.io().scheduleDirect(() -> twitterStream.cleanUp());
            });

            StatusListener listener = new StatusListener() {
                @Override
                public void onStatus(Status status) {
                    emitter.onNext(status);
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
                    emitter.onError(ex);
                }
            };

            twitterStream.addListener(listener);
            twitterStream.filter(filterQuery);
        });
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
