package com.tomtre.android.architecture.shoppinglistmvp.data.source.repository;

import android.support.annotation.Nullable;

import com.google.common.collect.ImmutableList;
import com.tomtre.android.architecture.shoppinglistmvp.data.Product;
import com.tomtre.android.architecture.shoppinglistmvp.data.source.ProductsDataSource;

import java.util.List;

import timber.log.Timber;

class RepositoryInnerCallbacks {

    public static class ProductListLocalCallback implements ProductsDataSource.LoadProductListCallback {

        private final ProductsRepositoryImpl productsRepositoryImpl;
        private final ProductsRepository.LoadProductListCallback repositoryLoadProductListCallback;

        ProductListLocalCallback(ProductsRepositoryImpl productsRepositoryImpl, ProductsRepository.LoadProductListCallback repositoryLoadProductListCallback) {
            this.productsRepositoryImpl = productsRepositoryImpl;
            this.repositoryLoadProductListCallback = repositoryLoadProductListCallback;
        }

        @Override
        public void onProductsLoaded(List<Product> products) {
            Timber.d("Local data source - onProductsLoaded: %s", products);
            productsRepositoryImpl.refreshCache(products);
            repositoryLoadProductListCallback.onProductsLoaded(ImmutableList.copyOf(products));
        }

        @Override
        public void onDataNotAvailable() {
            Timber.d("Local data source - onDataNotAvailable");
            productsRepositoryImpl.getProductsFromRemoteDataSource(repositoryLoadProductListCallback);
        }
    }

    public static class ProductListRemoteCallback implements ProductsDataSource.LoadProductListCallback {

        private final ProductsRepositoryImpl productsRepositoryImpl;
        private final ProductsRepository.LoadProductListCallback repositoryLoadProductListCallback;

        ProductListRemoteCallback(ProductsRepositoryImpl productsRepositoryImpl, ProductsRepository.LoadProductListCallback repositoryLoadProductListCallback) {
            this.productsRepositoryImpl = productsRepositoryImpl;
            this.repositoryLoadProductListCallback = repositoryLoadProductListCallback;
        }

        @Override
        public void onProductsLoaded(List<Product> products) {
            Timber.d("Remote data source - onProductsLoaded: %s", products);
            productsRepositoryImpl.refreshCache(products);
            productsRepositoryImpl.refreshLocalDataSource(products);
            repositoryLoadProductListCallback.onProductsLoaded(products);
        }

        @Override
        public void onDataNotAvailable() {
            Timber.d("Remote data source - onDataNotAvailable");
            repositoryLoadProductListCallback.onDataNotAvailable();
        }
    }

    public static class ProductLocalCallback implements ProductsDataSource.LoadProductCallback {

        private final ProductsRepositoryImpl productsRepositoryImpl;
        private final String productId;
        private final ProductsRepository.LoadProductCallback repositoryLoadProductCallback;

        ProductLocalCallback(ProductsRepositoryImpl productsRepositoryImpl, String productId, ProductsRepository.LoadProductCallback repositoryLoadProductCallback) {
            this.productsRepositoryImpl = productsRepositoryImpl;
            this.productId = productId;
            this.repositoryLoadProductCallback = repositoryLoadProductCallback;
        }

        @Override
        public void onProductLoaded(Product product) {
            productsRepositoryImpl.refreshCache(product);
            repositoryLoadProductCallback.onProductLoaded(product);
        }

        @Override
        public void onDataNotAvailable() {
            productsRepositoryImpl.getProductFromRemoteDataSource(productId, repositoryLoadProductCallback);
        }
    }

    public static class ProductRemoteCallback implements ProductsDataSource.LoadProductCallback {

        private final ProductsRepositoryImpl productsRepositoryImpl;
        private final ProductsRepository.LoadProductCallback repositoryLoadProductCallback;

        ProductRemoteCallback(ProductsRepositoryImpl productsRepositoryImpl, ProductsRepository.LoadProductCallback repositoryLoadProductCallback) {
            this.productsRepositoryImpl = productsRepositoryImpl;
            this.repositoryLoadProductCallback = repositoryLoadProductCallback;
        }

        @Override
        public void onProductLoaded(@Nullable Product product) {
            productsRepositoryImpl.refreshCache(product);
            productsRepositoryImpl.refreshLocalDataSource(product);
            repositoryLoadProductCallback.onProductLoaded(product);
        }

        @Override
        public void onDataNotAvailable() {
            repositoryLoadProductCallback.onDataNotAvailable();
        }

    }
}
