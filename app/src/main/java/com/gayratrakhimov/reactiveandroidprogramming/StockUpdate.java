package com.gayratrakhimov.reactiveandroidprogramming;

import com.gayratrakhimov.reactiveandroidprogramming.coinapi.json.Rate;

import java.io.Serializable;
import java.math.BigDecimal;


public class StockUpdate implements Serializable {

    private final String stockSymbol;
    private final BigDecimal price;
    private final String time;
    private Integer id;

    public StockUpdate(String stockSymbol, BigDecimal price, String time) {
        this.stockSymbol = stockSymbol;
        this.price = price;
        this.time = time;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getTime() {
        return time;
    }

    public static StockUpdate create(Rate rate) {
        return new StockUpdate(
                rate.getAssetIdQuote(),
                new BigDecimal(1 / rate.getRate()),
                rate.getTime());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}