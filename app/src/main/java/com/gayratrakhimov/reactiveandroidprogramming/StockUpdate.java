package com.gayratrakhimov.reactiveandroidprogramming;

import com.gayratrakhimov.reactiveandroidprogramming.coinapi.json.Rate;

import java.io.Serializable;
import java.math.BigDecimal;

import twitter4j.Status;


public class StockUpdate implements Serializable {

    private final String stockSymbol;
    private final BigDecimal price;
    private final String time;
    private final String twitterStatus;
    private Integer id;

    public StockUpdate(String stockSymbol, BigDecimal price, String time,
                       String twitterStatus) {
        if (stockSymbol == null) {
            stockSymbol = "";
        }
        if (twitterStatus == null) {
            twitterStatus = "";
        }
        this.stockSymbol = stockSymbol;
        this.price = price;
        this.time = time;
        this.twitterStatus = twitterStatus;
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

    public boolean isTwitterStatusUpdate() {
        return !twitterStatus.isEmpty();
    }

    public String getTwitterStatus() {
        return twitterStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StockUpdate that = (StockUpdate) o;

        if (stockSymbol != null ? !stockSymbol.equals(that.stockSymbol) : that.stockSymbol != null)
            return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        if (time != null ? !time.equals(that.time) : that.time != null) return false;
        if (twitterStatus != null ? !twitterStatus.equals(that.twitterStatus) : that.twitterStatus != null)
            return false;
        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        int result = stockSymbol != null ? stockSymbol.hashCode() : 0;
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (twitterStatus != null ? twitterStatus.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }
}