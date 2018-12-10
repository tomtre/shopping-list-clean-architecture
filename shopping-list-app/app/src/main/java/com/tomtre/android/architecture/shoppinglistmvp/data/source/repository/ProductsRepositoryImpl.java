package com.tomtre.android.architecture.shoppinglistmvp.data.source.repository;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.tomtre.android.architecture.shoppinglistmvp.data.Product;
import com.tomtre.android.architecture.shoppinglistmvp.data.ProductsCache;
import com.tomtre.android.architecture.shoppinglistmvp.data.source.ProductsDataSource;
import com.tomtre.android.architecture.shoppinglistmvp.di.AppScope;
import com.tomtre.android.architecture.shoppinglistmvp.di.LocalQualifier;
import com.tomtre.android.architecture.shoppinglistmvp.di.RemoteQualifier;

import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;

import static com.google.common.base.Preconditions.checkNotNull;


@SuppressWarnings("Guava")
@AppScope
public class ProductsRepositoryImpl implements ProductsRepository {

    private final ProductsCache productsCache;
    private final ProductsDataSource productsRemoteDataSource;
    private final ProductsDataSource productsLocalDataSource;

    @Inject
    ProductsRepositoryImpl(ProductsCache productsCache,
                           @RemoteQualifier ProductsDataSource productsRemoteDataSource,
                           @LocalQualifier ProductsDataSource productsLocalDataSource) {
        this.productsCache = productsCache;
        this.productsRemoteDataSource = productsRemoteDataSource;
        this.productsLocalDataSource = productsLocalDataSource;
    }

    @Override
    public void getProducts(final LoadProductListCallback repositoryLoadProductListCallback) {
        Timber.d("getProducts");
        checkNotNull(repositoryLoadProductListCallback);
        List<Product> productsFromCache = getProductsFromCache();
        if (!productsFromCache.isEmpty()) {
            Timber.d("Cache - products: %s", productsFromCache);
            repositoryLoadProductListCallback.onProductsLoaded(productsFromCache);
        } else {
            Timber.d("Cache is empty");
            getProductsFromLocalDataSource(repositoryLoadProductListCallback);
        }
    }

    @Override
    public void saveProduct(Product product) {
        Timber.d("saveProduct: %s", product);
        checkNotNull(product);
        productsRemoteDataSource.saveProduct(product);
        productsLocalDataSource.saveProduct(product);
        productsCache.save(product);
    }

    @Override
    public void checkProduct(Product product) {
        Timber.d("checkProduct: %s", product);
        checkNotNull(product);
        productsRemoteDataSource.checkProduct(product);
        productsLocalDataSource.checkProduct(product);
        Product checkedProduct = new Product(product, true);
        productsCache.save(checkedProduct);
    }

    @Override
    public void checkProduct(String productId) {
        Timber.d("checkProduct - id: %s", productId);
        checkNotNull(productId);
        productsRemoteDataSource.checkProduct(productId);
        productsLocalDataSource.checkProduct(productId);
        Optional<Product> productOptional = productsCache.getProduct(productId);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            Product checkedProduct = new Product(product, true);
            productsCache.save(checkedProduct);
        }
    }

    @Override
    public void uncheckProduct(Product product) {
        Timber.d("uncheckProduct: %s", product);
        checkNotNull(product);
        productsRemoteDataSource.uncheckProduct(product);
        productsLocalDataSource.uncheckProduct(product);
        Product uncheckedProduct = new Product(product, false);
        productsCache.save(uncheckedProduct);
    }

    @Override
    public void uncheckProduct(String productId) {
        Timber.d("uncheckProduct - id: %s", productId);
        checkNotNull(productId);
        productsRemoteDataSource.uncheckProduct(productId);
        productsLocalDataSource.uncheckProduct(productId);
        Optional<Product> productOptional = productsCache.getProduct(productId);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            Product uncheckedProduct = new Product(product, false);
            productsCache.save(uncheckedProduct);
        }
    }

    @Override
    public void removeCheckedProducts() {
        Timber.d("removeCheckedProducts");
        productsRemoteDataSource.removeCheckedProducts();
        productsLocalDataSource.removeCheckedProducts();
        productsCache.removeAllIf(Product::isChecked);
    }

    @Override
    public void getProduct(final String productId, final LoadProductCallback repositoryLoadProductCallback) {
        Timber.d("getProduct");
        checkNotNull(productId);
        checkNotNull(repositoryLoadProductCallback);
        Optional<Product> productOptional = productsCache.getProduct(productId);
        if (productOptional.isPresent()) {
            Timber.d("Cache - product: %s", productOptional.get());
            repositoryLoadProductCallback.onProductLoaded(productOptional.get());
        } else {
            Timber.d("Cache is empty");
            getProductFromLocalDataSource(productId, repositoryLoadProductCallback);
        }
    }

    @Override
    public void removeAllProducts() {
        Timber.d("removeAllProducts");
        productsRemoteDataSource.removeAllProducts();
        productsLocalDataSource.removeAllProducts();
        productsCache.clear();
    }


    @Override
    public void removeProduct(String productId) {
        Timber.d("removeProduct - id: %s", productId);
        checkNotNull(productId);
        productsRemoteDataSource.removeProduct(productId);
        productsLocalDataSource.removeProduct(productId);
        productsCache.remove(productId);
    }

    public void forceToLoadFromRemoteNextCall() {
        Timber.d("forceToLoadFromRemoteNextCall");
        productsLocalDataSource.removeAllProducts();
        productsCache.clear();
    }

    void refreshLocalDataSource(List<Product> products) {
        Timber.d("refreshLocalDataSource");
        productsLocalDataSource.removeAllProducts();
        for (Product product : products) {
            productsLocalDataSource.saveProduct(product);
        }
    }

    void refreshLocalDataSource(Product product) {
        Timber.d("refreshLocalDataSource:");
        productsLocalDataSource.saveProduct(product);
    }

    void refreshCache(List<Product> products) {
        Timber.d("refreshCache");
        productsCache.clear();
        for (Product product : products) {
            productsCache.save(product);
        }
    }

    void refreshCache(Product product) {
        Timber.d("refreshCache");
        productsCache.save(product);
    }

    void getProductsFromRemoteDataSource(final LoadProductListCallback loadProductListCallback) {
        Timber.d("getProductsFromRemoteDataSource");
        productsRemoteDataSource.getProducts(new RepositoryInnerCallbacks.ProductListRemoteCallback(this, loadProductListCallback));
    }

    void getProductFromRemoteDataSource(final String productId, final LoadProductCallback loadProductCallback) {
        Timber.d("getProductFromRemoteDataSource - id: %s", productId);
        productsRemoteDataSource.getProduct(productId, new RepositoryInnerCallbacks.ProductRemoteCallback(this, loadProductCallback));
    }

    private void getProductsFromLocalDataSource(final LoadProductListCallback loadProductListCallback) {
        Timber.d("getProductsFromLocalDataSource");
        productsLocalDataSource.getProducts(new RepositoryInnerCallbacks.ProductListLocalCallback(this, loadProductListCallback));
    }

    private void getProductFromLocalDataSource(final String productId, final LoadProductCallback loadProductCallback) {
        Timber.d("getProductFromLocalDataSource - id: %s", productId);
        productsLocalDataSource.getProduct(productId, new RepositoryInnerCallbacks.ProductLocalCallback(this, productId, loadProductCallback));
    }

    private List<Product> getProductsFromCache() {
        return ImmutableList.copyOf(productsCache.getProducts());
    }
}
