package com.tomtre.android.architecture.shoppinglistmvp.di;

import com.tomtre.android.architecture.shoppinglistmvp.base.ShoppingListApp;
import com.tomtre.android.architecture.shoppinglistmvp.data.source.ProductsRepositoryModule;
import com.tomtre.android.architecture.shoppinglistmvp.ui.addeditproduct.AddEditProductFragmentComponent;
import com.tomtre.android.architecture.shoppinglistmvp.ui.addeditproduct.AddEditProductFragmentModule;
import com.tomtre.android.architecture.shoppinglistmvp.ui.productdetail.ProductDetailFragmentComponent;
import com.tomtre.android.architecture.shoppinglistmvp.ui.productdetail.ProductDetailFragmentModule;
import com.tomtre.android.architecture.shoppinglistmvp.ui.products.ProductsFragmentComponent;
import com.tomtre.android.architecture.shoppinglistmvp.ui.products.ProductsFragmentModule;

import dagger.Component;

@AppScope
@Component(modules = {AppModule.class, ProductsRepositoryModule.class})
public interface AppComponent {

    ProductsFragmentComponent plusProductsFragmentComponent(ProductsFragmentModule productsFragmentModule);

    ProductDetailFragmentComponent plusProductDetailFragmentComponent(ProductDetailFragmentModule productDetailFragmentModule);

    AddEditProductFragmentComponent plusAddEditProductFragmentComponent(AddEditProductFragmentModule addEditProductFragmentModule);

    void inject(ShoppingListApp app);

}
