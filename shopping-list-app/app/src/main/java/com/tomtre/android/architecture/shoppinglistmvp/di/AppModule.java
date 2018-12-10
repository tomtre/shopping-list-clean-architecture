package com.tomtre.android.architecture.shoppinglistmvp.di;

import android.app.Application;
import android.content.Context;

import dagger.Binds;
import dagger.Module;

@Module
abstract class AppModule {

    @AppScope
    @Binds
    abstract Context bindContext(Application application);

}
