package com.gayratrakhimov.reactiveandroidprogramming.yahoo.json;

import java.util.Date;

public class YahooStockQuery {
    private int count;
    private Date created;
    private YahooStockResults results;

    public YahooStockResults getResults() {
        return results;
    }

    public Date getCreated() {
        return created;
    }
}
