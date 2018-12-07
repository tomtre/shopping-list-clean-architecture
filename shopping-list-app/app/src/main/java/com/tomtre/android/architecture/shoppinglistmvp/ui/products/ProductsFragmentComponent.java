package com.tomtre.android.architecture.shoppinglistmvp.ui.products;

import com.tomtre.android.architecture.shoppinglistmvp.di.FragmentScope;

import dagger.Subcomponent;

@Subcomponent(modules = ProductsFragmentModule.class)
@FragmentScope
public interface ProductsFragmentComponent {

    void inject(ProductsFragment productsFragment);

}
