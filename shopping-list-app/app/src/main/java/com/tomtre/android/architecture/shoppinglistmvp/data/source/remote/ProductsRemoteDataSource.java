package com.tomtre.android.architecture.shoppinglistmvp.data.source.remote;

import android.os.Handler;
import android.os.Looper;

import com.google.common.collect.ImmutableList;
import com.tomtre.android.architecture.shoppinglistmvp.data.Product;
import com.tomtre.android.architecture.shoppinglistmvp.data.source.ProductsDataSource;
import com.tomtre.android.architecture.shoppinglistmvp.di.AppScope;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.inject.Inject;

import static com.tomtre.android.architecture.shoppinglistmvp.util.CommonUtils.nonNull;

/**
 * Implementation of ProductsDataSource that simulate remote source (adds network latency).
 */
@AppScope
public class ProductsRemoteDataSource implements ProductsDataSource {

    private static final int SERVICE_LATENCY_IN_MILLIS = 3000;
    private final Map<String, Product> productsServiceData = new LinkedHashMap<>();
    private final Handler mainThreadHandler = new Handler(Looper.getMainLooper());

    @Inject
    public ProductsRemoteDataSource() {
        addProduct("Milk", "Without lactose", "3", "bottles");
        addProduct("Bread", "Whole grain", "5", null);
        addProduct("Orange juice", "Fresh! Buy fresh!", "3", "litres");
        addProduct("Spices for barbecue", null, null, null);
        addProduct("Flour", "Powdery", "3.5", "kg");
    }

    private void addProduct(String title, String description, String quantity, String unit) {
        Product product = new Product(title, description, quantity, unit, false);
        productsServiceData.put(product.getId(), product);
    }

    @Override
    public void getProducts(final LoadProductListCallback loadProductListCallback) {
        postDelayed(() -> loadProductListCallback.onProductsLoaded(ImmutableList.copyOf(productsServiceData.values())));
    }

    @Override
    public void removeCheckedProducts() {
        postDelayed(() -> {
            Iterator<Map.Entry<String, Product>> iterator = productsServiceData.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Product> entry = iterator.next();
                if (entry.getValue().isChecked())
                    iterator.remove();
            }
        });
    }

    @Override
    public void removeAllProducts() {
        postDelayed(productsServiceData::clear);
    }

    @Override
    public void getProduct(final String productId, final LoadProductCallback loadProductCallback) {
        final Product product = productsServiceData.get(productId);
        if (nonNull(product))
            postDelayed(() -> loadProductCallback.onProductLoaded(product));
        else
            postDelayed(loadProductCallback::onDataNotAvailable);
    }

    @Override
    public void removeProduct(final String productId) {
        postDelayed(() -> productsServiceData.remove(productId));
    }

    @Override
    public void saveProduct(final Product product) {
        postDelayed(() -> productsServiceData.put(product.getId(), product));
    }

    @Override
    public void checkProduct(final Product product) {
        Product checkedProduct = new Product(product, true);
        postDelayed(() -> productsServiceData.put(checkedProduct.getId(), checkedProduct));
    }

    @Override
    public void checkProduct(String productId) {
        if (productsServiceData.containsKey(productId)) {
            Product product = productsServiceData.get(productId);
            Product checkedProduct = new Product(product, true);
            postDelayed(() -> productsServiceData.put(productId, checkedProduct));
        }
    }

    @Override
    public void uncheckProduct(final Product product) {
        Product uncheckedProduct = new Product(product, false);
        postDelayed(() -> productsServiceData.put(uncheckedProduct.getId(), uncheckedProduct));
    }

    @Override
    public void uncheckProduct(String productId) {
        if (productsServiceData.containsKey(productId)) {
            Product product = productsServiceData.get(productId);
            Product uncheckedProduct = new Product(product, false);
            postDelayed(() -> productsServiceData.put(productId, uncheckedProduct));
        }
    }

    private void postDelayed(Runnable r) {
        mainThreadHandler.postDelayed(r, SERVICE_LATENCY_IN_MILLIS);
    }
}
