package com.tomtre.android.architecture.shoppinglistmvp.ui.productdetail;

import com.tomtre.android.architecture.shoppinglistmvp.di.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class ProductDetailFragmentModule {

    @FragmentScope
    @Binds
    abstract ProductDetailContract.View bindView(ProductDetailFragment productDetailFragment);

    @FragmentScope
    @Binds
    abstract ProductDetailContract.Presenter bindPresenter(ProductDetailPresenter productDetailPresenter);

    @FragmentScope
    @Provides
    static String provideProductId(ProductDetailFragment productDetailFragment) {
        return productDetailFragment.getArguments().getString(ProductDetailFragment.KEY_PRODUCT_ID);
    }
}
