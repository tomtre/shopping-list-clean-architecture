package com.tomtre.android.architecture.shoppinglistmvp.data.source.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.tomtre.android.architecture.shoppinglistmvp.data.Product;

@Database(entities = {Product.class}, version = 1)
public abstract class ProductsDatabase extends RoomDatabase {

    public abstract ProductsDao productsDao();

}
