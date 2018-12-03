package com.tomtre.android.architecture.shoppinglistmvp.data;

import android.content.Context;
import android.support.annotation.NonNull;

import com.tomtre.android.architecture.shoppinglistmvp.data.source.local.ProductsDatabase;
import com.tomtre.android.architecture.shoppinglistmvp.data.source.local.ProductsLocalDataSource;
import com.tomtre.android.architecture.shoppinglistmvp.data.source.remote.ProductsRemoteDataSource;
import com.tomtre.android.architecture.shoppinglistmvp.data.source.repository.ProductsRepository;
import com.tomtre.android.architecture.shoppinglistmvp.data.source.repository.ProductsRepositoryImpl;
import com.tomtre.android.architecture.shoppinglistmvp.util.AppExecutors;

import java.util.concurrent.Executors;

import static com.google.common.base.Preconditions.checkNotNull;

public class Injection {
    public static ProductsRepository provideProductsRepository(@NonNull Context context) {
        checkNotNull(context);
        Context appContext = context.getApplicationContext();

        //The instance of ProductsRepositoryImpl is only destroyed in tests
        //so we are sure ProductsCache, ProductsRemoteDataSource, ProductsLocalDataSource
        //will be initialized only once

        ProductsCache productsCache = new ProductsCache();
        ProductsRemoteDataSource productsRemoteDataSource = new ProductsRemoteDataSource();
        AppExecutors appExecutors = new AppExecutors(Executors.newSingleThreadExecutor(), new AppExecutors.MainThreadExecutor());
        ProductsDatabase productsDatabase = ProductsDatabase.getInstance(appContext);
        ProductsLocalDataSource productsLocalDataSource = new ProductsLocalDataSource(
                appExecutors, productsDatabase.productsDao());

        return ProductsRepositoryImpl.getInstance(productsCache, productsRemoteDataSource, productsLocalDataSource);
    }
}
