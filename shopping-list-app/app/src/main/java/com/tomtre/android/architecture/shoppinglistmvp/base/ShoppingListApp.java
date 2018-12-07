package com.tomtre.android.architecture.shoppinglistmvp.base;

import android.app.Application;
import android.os.StrictMode;
import android.support.annotation.VisibleForTesting;

import com.squareup.leakcanary.LeakCanary;
import com.tomtre.android.architecture.shoppinglistmvp.BuildConfig;
import com.tomtre.android.architecture.shoppinglistmvp.data.source.repository.ProductsRepository;
import com.tomtre.android.architecture.shoppinglistmvp.di.DependencyInjector;

import javax.inject.Inject;

import timber.log.Timber;

public class ShoppingListApp extends Application {

    //we need it for UI tests
    @Inject
    ProductsRepository productsRepository;

    @Override
    public void onCreate() {
        super.onCreate();

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        //no need to add different versions for debug and release because of debugImplementation
        //and releaseImplementation in dependencies
        LeakCanary.install(this);

        enableStrictMode();

        if (BuildConfig.DEBUG)
            Timber.plant(new Timber.DebugTree());

        initDependencyInjector();
        DependencyInjector.appComponent().inject(this);
    }

    private void enableStrictMode() {
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .penaltyDeath()
                    .build());
        }
    }

    private void initDependencyInjector() {
        DependencyInjector.initialize(this);
    }

    //we need it for UI tests
    @VisibleForTesting
    public ProductsRepository getProductsRepository() {
        return productsRepository;
    }
}
