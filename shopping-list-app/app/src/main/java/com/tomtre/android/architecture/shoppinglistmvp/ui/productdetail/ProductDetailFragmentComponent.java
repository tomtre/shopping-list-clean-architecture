package com.tomtre.android.architecture.shoppinglistmvp.ui.productdetail;

import com.tomtre.android.architecture.shoppinglistmvp.di.FragmentScope;

import dagger.Subcomponent;

@Subcomponent(modules = ProductDetailFragmentModule.class)
@FragmentScope
public interface ProductDetailFragmentComponent {

    void inject(ProductDetailFragment productDetailFragment);

}
