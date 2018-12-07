package com.tomtre.android.architecture.shoppinglistmvp.di;

import com.tomtre.android.architecture.shoppinglistmvp.base.ShoppingListApp;

public class DependencyInjector {
    private static AppComponent appComponent;

    private DependencyInjector() {
    }

    public static void initialize(ShoppingListApp shoppingListApp) {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(shoppingListApp))
                .build();
    }

    public static AppComponent appComponent() {
        return appComponent;
    }

}
