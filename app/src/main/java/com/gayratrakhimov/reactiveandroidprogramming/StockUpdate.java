package com.gayratrakhimov.reactiveandroidprogramming;

import com.gayratrakhimov.reactiveandroidprogramming.coinapi.json.Rate;

import java.io.Serializable;
import java.math.BigDecimal;

public class StockUpdate implements Serializable {

    private final String stockSymbol;
    private final BigDecimal price;
    private final String date;

    public StockUpdate(String stockSymbol, BigDecimal price, String date) {
        this.stockSymbol = stockSymbol;
        this.price = price;
        this.date = date;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getDate() {
        return date;
    }

    public static StockUpdate create(Rate rate) {
        return new StockUpdate(
                rate.getAssetIdQuote(),
                new BigDecimal(1 / rate.getRate()),
                rate.getTime());
    }

}