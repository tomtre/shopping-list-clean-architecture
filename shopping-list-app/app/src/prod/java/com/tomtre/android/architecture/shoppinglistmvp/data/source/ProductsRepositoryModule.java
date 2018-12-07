package com.tomtre.android.architecture.shoppinglistmvp.data.source;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.tomtre.android.architecture.shoppinglistmvp.data.ProductsCache;
import com.tomtre.android.architecture.shoppinglistmvp.data.source.local.ProductsDao;
import com.tomtre.android.architecture.shoppinglistmvp.data.source.local.ProductsDatabase;
import com.tomtre.android.architecture.shoppinglistmvp.data.source.local.ProductsLocalDataSource;
import com.tomtre.android.architecture.shoppinglistmvp.data.source.remote.ProductsRemoteDataSource;
import com.tomtre.android.architecture.shoppinglistmvp.data.source.repository.ProductsRepository;
import com.tomtre.android.architecture.shoppinglistmvp.data.source.repository.ProductsRepositoryImpl;
import com.tomtre.android.architecture.shoppinglistmvp.di.AppScope;
import com.tomtre.android.architecture.shoppinglistmvp.di.LocalQualifier;
import com.tomtre.android.architecture.shoppinglistmvp.di.RemoteQualifier;
import com.tomtre.android.architecture.shoppinglistmvp.util.AppExecutors;

import java.util.concurrent.Executors;

import dagger.Module;
import dagger.Provides;

@Module
public class ProductsRepositoryModule {
    @AppScope
    @Provides
    ProductsCache provideProductsCache() {
        return new ProductsCache();
    }

    @AppScope
    @Provides
    @RemoteQualifier
    ProductsDataSource provideProductsRemoteDataSource() {
        return new ProductsRemoteDataSource();
    }

    @AppScope
    @Provides
    ProductsDatabase provideProductsDatabase(Context context) {
        return Room.databaseBuilder(context.getApplicationContext(),
                ProductsDatabase.class, "Products.db")
                .build();
    }

    @AppScope
    @Provides
    ProductsDao provideProductsDao(ProductsDatabase productsDatabase) {
        return productsDatabase.productsDao();
    }

    @AppScope
    @Provides
    AppExecutors provideAppExecutors() {
        return new AppExecutors(Executors.newSingleThreadExecutor(), new AppExecutors.MainThreadExecutor());
    }

    @AppScope
    @Provides
    @LocalQualifier
    ProductsDataSource provideProductsLocalDataSource(AppExecutors appExecutors, ProductsDao productsDao) {
        return new ProductsLocalDataSource(appExecutors, productsDao);
    }

    @AppScope
    @Provides
    ProductsRepository provideProductsRepository(
            ProductsCache productsCache,
            @RemoteQualifier ProductsDataSource productsRemoteDataSource,
            @LocalQualifier ProductsDataSource productsLocalDataSource) {
        return new ProductsRepositoryImpl(productsCache, productsRemoteDataSource, productsLocalDataSource);
    }
}
