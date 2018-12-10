package com.tomtre.android.architecture.shoppinglistmvp.data.source.remote;

import com.google.common.collect.ImmutableList;
import com.tomtre.android.architecture.shoppinglistmvp.data.Product;
import com.tomtre.android.architecture.shoppinglistmvp.data.source.ProductsDataSource;
import com.tomtre.android.architecture.shoppinglistmvp.di.AppScope;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.inject.Inject;

import static com.tomtre.android.architecture.shoppinglistmvp.util.CommonUtils.nonNull;

@AppScope
public class FakeProductsRemoteDataSource implements ProductsDataSource {

    private final Map<String, Product> productsServiceData = new LinkedHashMap<>();

    @Inject
    FakeProductsRemoteDataSource() {
    }

    @Override
    public void getProducts(final LoadProductListCallback loadProductListCallback) {
        loadProductListCallback.onProductsLoaded(ImmutableList.copyOf(productsServiceData.values()));
    }

    @Override
    public void removeCheckedProducts() {
        Iterator<Map.Entry<String, Product>> iterator = productsServiceData.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Product> entry = iterator.next();
            if (entry.getValue().isChecked())
                iterator.remove();
        }
    }

    @Override
    public void removeAllProducts() {
        productsServiceData.clear();
    }

    @Override
    public void getProduct(final String productId, final LoadProductCallback loadProductCallback) {
        final Product product = productsServiceData.get(productId);
        if (nonNull(product))
            loadProductCallback.onProductLoaded(product);
        else
            loadProductCallback.onDataNotAvailable();
    }

    @Override
    public void removeProduct(final String productId) {
        productsServiceData.remove(productId);
    }

    @Override
    public void saveProduct(final Product product) {
        productsServiceData.put(product.getId(), product);
    }

    @Override
    public void checkProduct(final Product product) {
        Product checkedProduct = new Product(product, true);
        productsServiceData.put(checkedProduct.getId(), checkedProduct);
    }

    @Override
    public void checkProduct(String productId) {
        if (productsServiceData.containsKey(productId)) {
            Product product = productsServiceData.get(productId);
            Product checkedProduct = new Product(product, true);
            productsServiceData.put(productId, checkedProduct);
        }
    }

    @Override
    public void uncheckProduct(final Product product) {
        Product uncheckedProduct = new Product(product, false);
        productsServiceData.put(uncheckedProduct.getId(), uncheckedProduct);
    }

    @Override
    public void uncheckProduct(String productId) {
        if (productsServiceData.containsKey(productId)) {
            Product product = productsServiceData.get(productId);
            Product uncheckedProduct = new Product(product, false);
            productsServiceData.put(productId, uncheckedProduct);
        }
    }
}
