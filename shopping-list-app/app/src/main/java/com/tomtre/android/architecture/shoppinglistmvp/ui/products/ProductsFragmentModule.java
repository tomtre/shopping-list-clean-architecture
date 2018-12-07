package com.tomtre.android.architecture.shoppinglistmvp.ui.products;

import com.tomtre.android.architecture.shoppinglistmvp.data.source.repository.ProductsRepository;
import com.tomtre.android.architecture.shoppinglistmvp.di.FragmentScope;

import dagger.Module;
import dagger.Provides;

@Module
public class ProductsFragmentModule {

    private final ProductsContract.View view;

    ProductsFragmentModule(ProductsContract.View view) {
        this.view = view;
    }

    @Provides
    @FragmentScope
    ProductsContract.Presenter provideProductsPresenter(ProductsRepository productsRepository) {
        return new ProductsPresenter(productsRepository, view);
    }

}
