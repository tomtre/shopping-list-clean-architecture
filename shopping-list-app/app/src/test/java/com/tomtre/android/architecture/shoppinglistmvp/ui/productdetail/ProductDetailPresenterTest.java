package com.tomtre.android.architecture.shoppinglistmvp.ui.productdetail;

import android.app.Activity;

import com.tomtre.android.architecture.shoppinglistmvp.data.Product;
import com.tomtre.android.architecture.shoppinglistmvp.data.source.repository.ProductsRepository;
import com.tomtre.android.architecture.shoppinglistmvp.data.source.repository.ProductsRepositoryImpl;
import com.tomtre.android.architecture.shoppinglistmvp.util.RequestCodes;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static com.tomtre.android.architecture.shoppinglistmvp.util.ProductsTestUtils.createCheckedProduct;
import static com.tomtre.android.architecture.shoppinglistmvp.util.ProductsTestUtils.createUncheckedProduct;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@RunWith(MockitoJUnitRunner.class)
public class ProductDetailPresenterTest {

    private static final Product UNCHECKED_PRODUCT = createUncheckedProduct();
    private static final Product CHECKED_PRODUCT = createCheckedProduct();
    private static final String INVALID_PRODUCT_ID = "";

    @Mock
    private ProductsRepositoryImpl productsRepositoryImpl;

    @Mock
    private ProductDetailContract.View productDetailView;

    private ProductDetailPresenter productDetailPresenter;

    @Before
    public void setUp() {
        given(productDetailView.isActive()).willReturn(true);
    }

    @Test
    public void shouldShowLoadingIndicatorWhenLoadProduct() {
        //given
        productDetailPresenter = new ProductDetailPresenter(UNCHECKED_PRODUCT.getId(), productsRepositoryImpl, productDetailView);

        //when
        productDetailPresenter.start();

        //then
        then(productDetailView).should().setLoadingIndicator(true);
    }

    @Test
    public void shouldGetProductWhenPresenterStarts() {
        //given
        productDetailPresenter = new ProductDetailPresenter(UNCHECKED_PRODUCT.getId(), productsRepositoryImpl, productDetailView);

        //when
        productDetailPresenter.start();

        //then
        then(productsRepositoryImpl).should().getProduct(eq(UNCHECKED_PRODUCT.getId()), any(ProductsRepository.LoadProductCallback.class));
    }

    @Test
    public void shouldShowMissingProductWhenLoadProductAndIdIsInvalid() {
        //given
        productDetailPresenter = new ProductDetailPresenter(INVALID_PRODUCT_ID, productsRepositoryImpl, productDetailView);

        //when
        productDetailPresenter.start();

        //then
        then(productDetailView).should().showMissingProduct();
    }

    @Test
    public void shouldShowProductInViewWhenProcessUncheckedProduct() {
        //given
        productDetailPresenter = new ProductDetailPresenter(UNCHECKED_PRODUCT.getId(), productsRepositoryImpl, productDetailView);

        //when
        productDetailPresenter.processProduct(UNCHECKED_PRODUCT);

        //then
        verifyProductIsShownInView(UNCHECKED_PRODUCT);
        then(productDetailView).should().showCheckedStatus(false);
    }


    @Test
    public void shouldShowProductInViewWhenProcessCheckedProduct() {
        //given
        productDetailPresenter = new ProductDetailPresenter(CHECKED_PRODUCT.getId(), productsRepositoryImpl, productDetailView);

        //when
        productDetailPresenter.processProduct(CHECKED_PRODUCT);

        //then
        verifyProductIsShownInView(CHECKED_PRODUCT);
        then(productDetailView).should().showCheckedStatus(true);

    }

    @Test
    public void shouldPerformHidingProductViewsActionsWhenProcessProductWithEmptyAttributes() {
        //given
        Product product = new Product("Title", null, null, null, false);
        productDetailPresenter = new ProductDetailPresenter(product.getId(), productsRepositoryImpl, productDetailView);

        //when
        productDetailPresenter.processProduct(product);

        //then
        then(productDetailView).should().hideDescription();
        then(productDetailView).should().hideQuantity();
        then(productDetailView).should().hideUnit();
    }

    @Test
    public void shouldDelegateShowMissingProductToViewWhenShowMissingProduct() {
        //given
        productDetailPresenter = new ProductDetailPresenter(UNCHECKED_PRODUCT.getId(), productsRepositoryImpl, productDetailView);

        //when
        productDetailPresenter.showMissingProductInView();

        //then
        then(productDetailView).should().showMissingProduct();
    }

    @Test
    public void shouldDelegateSetLoadingIndicatorToViewWhenSetInactiveLoadingIndicator() {
        //given
        productDetailPresenter = new ProductDetailPresenter(CHECKED_PRODUCT.getId(), productsRepositoryImpl, productDetailView);

        //when
        productDetailPresenter.setInactiveLoadingIndicatorInView();

        //then
        then(productDetailView).should().setLoadingIndicator(false);
    }

    @Test
    public void shouldRemoveProductInRepositoryWhenRemoveProduct() {
        //given
        productDetailPresenter = new ProductDetailPresenter(UNCHECKED_PRODUCT.getId(), productsRepositoryImpl, productDetailView);

        //when
        productDetailPresenter.removeProduct();

        //then
        then(productsRepositoryImpl).should().removeProduct(UNCHECKED_PRODUCT.getId());
    }

    @Test
    public void shouldShowProductRemovedWhenRemoveProduct() {
        //given
        productDetailPresenter = new ProductDetailPresenter(UNCHECKED_PRODUCT.getId(), productsRepositoryImpl, productDetailView);

        //when
        productDetailPresenter.removeProduct();

        //then
        then(productDetailView).should().showProductRemoved();
    }

    @Test
    public void shouldShowMissingProductWhenRemoveProductAndIdIsInvalid() {
        //given
        productDetailPresenter = new ProductDetailPresenter(INVALID_PRODUCT_ID, productsRepositoryImpl, productDetailView);

        //when
        productDetailPresenter.removeProduct();

        //then
        then(productDetailView).should().showMissingProduct();
    }

    @Test
    public void shouldCheckProductInRepositoryWhenCheckProduct() {
        //given
        productDetailPresenter = new ProductDetailPresenter(UNCHECKED_PRODUCT.getId(), productsRepositoryImpl, productDetailView);

        //when
        productDetailPresenter.checkProduct();

        //then
        then(productsRepositoryImpl).should().checkProduct(UNCHECKED_PRODUCT.getId());
    }

    @Test
    public void shouldShowProductMarkedAsCheckedWhenCheckProduct() {
        //given
        productDetailPresenter = new ProductDetailPresenter(UNCHECKED_PRODUCT.getId(), productsRepositoryImpl, productDetailView);

        //when
        productDetailPresenter.checkProduct();

        //then
        then(productDetailView).should().showProductMarkedAsChecked();
    }

    @Test
    public void shouldShowMissingProductWhenCheckProductAndIdIsInvalid() {
        //given
        productDetailPresenter = new ProductDetailPresenter(INVALID_PRODUCT_ID, productsRepositoryImpl, productDetailView);

        //when
        productDetailPresenter.checkProduct();

        //then
        then(productDetailView).should().showMissingProduct();
    }

    @Test
    public void shouldUncheckProductInRepositoryWhenUncheckProduct() {
        //given
        productDetailPresenter = new ProductDetailPresenter(CHECKED_PRODUCT.getId(), productsRepositoryImpl, productDetailView);

        //when
        productDetailPresenter.uncheckProduct();

        //then
        then(productsRepositoryImpl).should().uncheckProduct(CHECKED_PRODUCT.getId());
    }

    @Test
    public void shouldShowProductMarkedAsUncheckedWhenUncheckProduct() {
        //given
        productDetailPresenter = new ProductDetailPresenter(CHECKED_PRODUCT.getId(), productsRepositoryImpl, productDetailView);

        //when
        productDetailPresenter.uncheckProduct();

        //then
        then(productDetailView).should().showProductMarkedAsUnchecked();
    }

    @Test
    public void shouldShowMissingProductWhenUncheckProductAndIdIsInvalid() {
        //given
        productDetailPresenter = new ProductDetailPresenter(INVALID_PRODUCT_ID, productsRepositoryImpl, productDetailView);

        //when
        productDetailPresenter.uncheckProduct();

        //then
        then(productDetailView).should().showMissingProduct();
    }

    @Test
    public void shouldShowEditProductUiWhenEditProduct() {
        //given
        productDetailPresenter = new ProductDetailPresenter(UNCHECKED_PRODUCT.getId(), productsRepositoryImpl, productDetailView);

        //when
        productDetailPresenter.editProduct();

        //then
        then(productDetailView).should().showEditProductUI(UNCHECKED_PRODUCT.getId());
    }

    @Test
    public void shouldShowMissingProductWhenEditProductAndIdIsInvalid() {
        //given
        productDetailPresenter = new ProductDetailPresenter(INVALID_PRODUCT_ID, productsRepositoryImpl, productDetailView);

        //when
        productDetailPresenter.editProduct();

        //then
        then(productDetailView).should().showMissingProduct();
    }

    @Test
    public void shouldShowProductEditedWhenCallActivityResult() {
        //given
        productDetailPresenter = new ProductDetailPresenter(UNCHECKED_PRODUCT.getId(), productsRepositoryImpl, productDetailView);
        int requestCode = RequestCodes.EDIT_PRODUCT;
        int resultCode = Activity.RESULT_OK;

        //when
        productDetailPresenter.activityResult(requestCode, resultCode);

        //then
        then(productDetailView).should().showProductEdited();
    }

    private void verifyProductIsShownInView(Product product) {
        then(productDetailView).should().showTitle(product.getTitle());
        then(productDetailView).should().showDescription(product.getDescription());
        then(productDetailView).should().showQuantity(product.getQuantity());
        then(productDetailView).should().showUnit(product.getUnit());
    }
}