package com.tomtre.android.architecture.shoppinglistmvp.data.source;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.tomtre.android.architecture.shoppinglistmvp.data.ProductsCache;
import com.tomtre.android.architecture.shoppinglistmvp.data.source.local.ProductsDao;
import com.tomtre.android.architecture.shoppinglistmvp.data.source.local.ProductsDatabase;
import com.tomtre.android.architecture.shoppinglistmvp.data.source.local.ProductsLocalDataSource;
import com.tomtre.android.architecture.shoppinglistmvp.data.source.remote.FakeProductsRemoteDataSource;
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
    public ProductsCache provideProductsCache() {
        return new ProductsCache();
    }

    @AppScope
    @Provides
    @RemoteQualifier
    public ProductsDataSource provideProductsRemoteDataSource() {
        return new FakeProductsRemoteDataSource();
    }

    @AppScope
    @Provides
    public ProductsDatabase provideProductsDatabase(Context context) {
        return Room.databaseBuilder(context.getApplicationContext(),
                ProductsDatabase.class, "Products.db")
                .build();
    }

    @AppScope
    @Provides
    public ProductsDao provideProductsDao(ProductsDatabase productsDatabase) {
        return productsDatabase.productsDao();
    }

    @AppScope
    @Provides
    public AppExecutors provideAppExecutors() {
        return new AppExecutors(Executors.newSingleThreadExecutor(), new AppExecutors.MainThreadExecutor());
    }

    @AppScope
    @Provides
    @LocalQualifier
    public ProductsDataSource provideProductsLocalDataSource(AppExecutors appExecutors, ProductsDao productsDao) {
        return new ProductsLocalDataSource(appExecutors, productsDao);
    }

    @AppScope
    @Provides
    public ProductsRepository provideProductsRepository(
            ProductsCache productsCache,
            @RemoteQualifier ProductsDataSource productsRemoteDataSource,
            @LocalQualifier ProductsDataSource productsLocalDataSource) {
        return new ProductsRepositoryImpl(productsCache, productsRemoteDataSource, productsLocalDataSource);
    }
}
