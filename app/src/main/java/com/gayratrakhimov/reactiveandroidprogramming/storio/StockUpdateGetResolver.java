package com.gayratrakhimov.reactiveandroidprogramming.storio;

import android.database.Cursor;
import android.support.annotation.NonNull;

import com.gayratrakhimov.reactiveandroidprogramming.StockUpdate;
import com.pushtorefresh.storio.sqlite.operations.get.DefaultGetResolver;

import java.math.BigDecimal;

public class StockUpdateGetResolver extends DefaultGetResolver<StockUpdate> {

    @NonNull
    @Override
    public StockUpdate mapFromCursor(@NonNull Cursor cursor) {

        final int id = cursor.getInt(cursor.getColumnIndexOrThrow(StockUpdateTable.Columns.ID));
        final String date = cursor.getString(cursor.getColumnIndexOrThrow(StockUpdateTable.Columns.DATE));
        final long priceLong = cursor.getLong(cursor.getColumnIndexOrThrow(StockUpdateTable.Columns.PRICE));
        final String stockSymbol = cursor.getString(cursor.getColumnIndexOrThrow(StockUpdateTable.Columns.STOCK_SYMBOL));

        BigDecimal price = getPrice(priceLong);

        final StockUpdate stockUpdate = new StockUpdate(stockSymbol, price, date);

        stockUpdate.setId(id);

        return stockUpdate;
    }

    private BigDecimal getPrice(long priceLong) {
        return new BigDecimal(priceLong).scaleByPowerOfTen(-4);
    }

}