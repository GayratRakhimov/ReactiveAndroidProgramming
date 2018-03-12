package com.gayratrakhimov.reactiveandroidprogramming;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StockUpdateViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.stock_item_symbol)
    TextView stockSymbol;

    @BindView(R.id.stock_item_date)
    TextView date;

    @BindView(R.id.stock_item_price)
    TextView price;

    @BindView(R.id.stock_item_twitter_status)
    TextView twitterStatus;

    public StockUpdateViewHolder(View v) {
        super(v);
        ButterKnife.bind(this, v);
    }

    private static final NumberFormat PRICE_FORMAT = new DecimalFormat("#0.00");

    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol.setText(stockSymbol);
    }

    public void setPrice(BigDecimal price) {
        this.price.setText(PRICE_FORMAT.format(price.floatValue()));
    }

    public void setDate(String date) {
        this.date.setText(date);
    }

    public void setTwitterStatus(String twitterStatus) {
        this.twitterStatus.setText(twitterStatus);
    }

    public void setIsStatusUpdate(boolean twitterStatusUpdate) {
        if (twitterStatusUpdate) {
            this.twitterStatus.setVisibility(View.VISIBLE);
            this.price.setVisibility(View.GONE);
            this.stockSymbol.setVisibility(View.GONE);
        } else {
            this.twitterStatus.setVisibility(View.GONE);
            this.price.setVisibility(View.VISIBLE);
            this.stockSymbol.setVisibility(View.VISIBLE);
        }
    }

}