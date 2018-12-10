package com.tomtre.android.architecture.shoppinglistmvp.data;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.tomtre.android.architecture.shoppinglistmvp.di.AppScope;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.inject.Inject;

import static com.google.common.base.Preconditions.checkNotNull;


@SuppressWarnings("Guava")
@AppScope
public class ProductsCache {

    private final Map<String, Product> products = new LinkedHashMap<>();

    @Inject
    ProductsCache() {
    }

    public Collection<Product> getProducts() {
        return products.values();
    }

    public Optional<Product> getProduct(String productId) {
        return Optional.fromNullable(products.get(productId));
    }

    public void clear() {
        products.clear();
    }

    public void removeAllIf(Predicate<Product> predicate) {
        Iterator<Map.Entry<String, Product>> iterator = products.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Product> entry = iterator.next();
            if (predicate.apply(entry.getValue()))
                iterator.remove();
        }
    }

    public void remove(String productId) {
        products.remove(productId);
    }

    public void save(Product product) {
        checkNotNull(product);
        products.put(product.getId(), product);
    }
}
