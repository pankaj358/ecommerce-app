package com.ecommerce.app.controller.service;

import com.ecommerce.app.contant.UserType;
import com.ecommerce.app.domain.Product;
import com.ecommerce.app.domain.User;
import com.ecommerce.app.model.ProductModel;
import com.ecommerce.app.repository.ProductRepository;
import com.ecommerce.app.service.ProductServiceImpl;
import com.ecommerce.app.service.ValidationService;
import com.ecommerce.app.util.EcommerceUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TestProductService {

    @Mock
    private EcommerceUtil ecommerceUtil;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ValidationService validationService;

    @InjectMocks
    private ProductServiceImpl productService;


    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void test_createProduct() {
        ProductModel productModel = productModel();
        Mockito.when(validationService.isValidProduct(productModel))
                .thenReturn(true);

        Product product = product();
        product.setProductId(UUID.randomUUID());
        product.setSeller(User.builder().userType(UserType.SELLER.getText()).userId(productModel.getSellerId()).build());
        product.setProductPrice(productModel.getProductPrice());
        product.setAvailable(true);
        product.setProductName(productModel.getProductName());
        Mockito.when(ecommerceUtil.toProduct(productModel)).thenReturn(product);
        Mockito.when(productRepository.save(product)).thenReturn(product);
        Mockito.when(ecommerceUtil.toProductModel(product)).thenReturn(productModel);

        ProductModel actual = productService.create(productModel);

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(productModel, actual);

        Mockito.verify(validationService, Mockito.times(1))
                .isValidProduct(Mockito.any(ProductModel.class));

        Mockito.verify(ecommerceUtil, Mockito.times(1))
                .toProduct(Mockito.any(ProductModel.class));
        Mockito.verify(ecommerceUtil, Mockito.times(1))
                .toProductModel(Mockito.any(Product.class));
    }

    @Test
    public void test_fetchProduct() {
        UUID productId = UUID.randomUUID();
        Product product = product();
        ProductModel productModel = productModel();
        Mockito.when(productRepository.findById(productId))
                .thenReturn(Optional.of(product));
        Mockito.when(ecommerceUtil.toProductModel(product)).thenReturn(productModel);
        ProductModel actual = productService.fetch(productId.toString());
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(productModel, actual);

        Mockito.verify(productRepository, Mockito.times(1)).findById(Mockito.any(UUID.class));
        Mockito.verify(ecommerceUtil, Mockito.times(1))
                .toProductModel(Mockito.any(Product.class));

    }


    @Test
    public void test_fetchAllProduct() {

        Product product = product();
        List<Product> list = new ArrayList<>();
        list.add(product);
        ProductModel productModel = productModel();
        List<ProductModel> expected = new ArrayList<>();
        expected.add(productModel);
        Mockito.when(productRepository.findAll())
                .thenReturn(list);
        Mockito.when(ecommerceUtil.toProductModel(product)).thenReturn(productModel);
        List<ProductModel> actual = productService.fetchAll();

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(expected.size(), actual.size());

        Mockito.verify(productRepository, Mockito.times(1)).findAll();
        Mockito.verify(ecommerceUtil, Mockito.times(expected.size()))
                .toProductModel(Mockito.any(Product.class));

    }

    private ProductModel productModel() {
        return ProductModel.builder()
                .sellerId(UUID.randomUUID())
                .productName("Iphone11")
                .available(true)
                .productPrice(new BigDecimal("100000"))
                .build();
    }

    private Product product() {
        return Product.builder()
                .build();
    }
}
