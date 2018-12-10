package com.tomtre.android.architecture.shoppinglistmvp.di;

import android.app.Application;

import com.tomtre.android.architecture.shoppinglistmvp.base.ShoppingListApp;
import com.tomtre.android.architecture.shoppinglistmvp.data.source.ProductsRepositoryModule;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@AppScope
@Component(modules = {AppModule.class,
        ProductsRepositoryModule.class,
        FragmentBindingModule.class,
        AndroidSupportInjectionModule.class})
public interface AppComponent extends AndroidInjector<ShoppingListApp> {

    @Component.Builder
    interface Builder {

        AppComponent build();

        @BindsInstance
        Builder application(Application application);

    }

}
