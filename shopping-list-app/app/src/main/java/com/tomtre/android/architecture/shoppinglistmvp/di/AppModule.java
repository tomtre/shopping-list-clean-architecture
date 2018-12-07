package com.tomtre.android.architecture.shoppinglistmvp.di;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
class AppModule {
    private final Context context;

    AppModule(Context context) {
        this.context = context.getApplicationContext();
    }

    @Provides
    @AppScope
    Context provideContext() {
        return context;
    }

}
