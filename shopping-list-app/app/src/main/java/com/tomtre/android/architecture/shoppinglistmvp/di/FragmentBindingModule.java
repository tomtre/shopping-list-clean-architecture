package com.tomtre.android.architecture.shoppinglistmvp.di;

import com.tomtre.android.architecture.shoppinglistmvp.ui.addeditproduct.AddEditProductFragment;
import com.tomtre.android.architecture.shoppinglistmvp.ui.addeditproduct.AddEditProductFragmentModule;
import com.tomtre.android.architecture.shoppinglistmvp.ui.productdetail.ProductDetailFragment;
import com.tomtre.android.architecture.shoppinglistmvp.ui.productdetail.ProductDetailFragmentModule;
import com.tomtre.android.architecture.shoppinglistmvp.ui.products.ProductsFragment;
import com.tomtre.android.architecture.shoppinglistmvp.ui.products.ProductsFragmentModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class FragmentBindingModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = ProductsFragmentModule.class)
    abstract ProductsFragment productsFragment();

    @FragmentScope
    @ContributesAndroidInjector(modules = ProductDetailFragmentModule.class)
    abstract ProductDetailFragment productDetailFragment();

    @FragmentScope
    @ContributesAndroidInjector(modules = AddEditProductFragmentModule.class)
    abstract AddEditProductFragment addEditProductFragment();
}
