package com.tomtre.android.architecture.shoppinglistmvp.ui.productdetail;

import android.support.annotation.Nullable;

import com.tomtre.android.architecture.shoppinglistmvp.data.source.repository.ProductsRepository;
import com.tomtre.android.architecture.shoppinglistmvp.di.FragmentScope;

import dagger.Module;
import dagger.Provides;

@Module
public class ProductDetailFragmentModule {

    @Nullable
    private final String productId;
    private final ProductDetailContract.View view;


    ProductDetailFragmentModule(@Nullable String productId, ProductDetailContract.View view) {
        this.productId = productId;
        this.view = view;
    }


    @Provides
    @FragmentScope
    ProductDetailContract.Presenter provideProductDetailPresenter(ProductsRepository productsRepository) {
        return new ProductDetailPresenter(productId, productsRepository, view);
    }


}
