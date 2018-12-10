package com.tomtre.android.architecture.shoppinglistmvp.ui.products;

import com.tomtre.android.architecture.shoppinglistmvp.di.FragmentScope;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ProductsFragmentModule {

    @FragmentScope
    @Binds
    abstract ProductsContract.View bindView(ProductsFragment productsFragment);

    @FragmentScope
    @Binds
    abstract ProductsContract.Presenter bindPresenter(ProductsPresenter productsPresenter);


}
