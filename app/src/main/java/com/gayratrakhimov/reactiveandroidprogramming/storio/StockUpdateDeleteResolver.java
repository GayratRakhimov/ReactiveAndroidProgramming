package com.gayratrakhimov.reactiveandroidprogramming.storio;

import android.support.annotation.NonNull;

import com.gayratrakhimov.reactiveandroidprogramming.StockUpdate;
import com.pushtorefresh.storio.sqlite.operations.delete.DefaultDeleteResolver;
import com.pushtorefresh.storio.sqlite.queries.DeleteQuery;

public class StockUpdateDeleteResolver extends DefaultDeleteResolver<StockUpdate> {
    @NonNull
    @Override
    protected DeleteQuery mapToDeleteQuery(@NonNull StockUpdate object) {
        return DeleteQuery.builder()
                .table(StockUpdateTable.TABLE)
                .where(StockUpdateTable.Columns.ID + " = ?")
                .whereArgs(object.getId())
                .build();
    }
}