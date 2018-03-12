package com.gayratrakhimov.reactiveandroidprogramming;

import com.gayratrakhimov.reactiveandroidprogramming.coinapi.json.Rate;

import java.io.Serializable;
import java.math.BigDecimal;

import twitter4j.Status;


public class StockUpdate implements Serializable {

    private final String stockSymbol;
    private final BigDecimal price;
    private final String time;
    private final String status;
    private Integer id;

    public StockUpdate(String stockSymbol, BigDecimal price, String time, String status) {
        this.stockSymbol = stockSymbol;
        this.price = price;
        this.time = time;
        this.status = status;
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
                rate.getTime(), "");
    }

    public static StockUpdate create(Status status) {
        return new StockUpdate("", BigDecimal.ZERO, status.getCreatedAt().toString(), status.getText());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


}