package com.ecommerce.app.controller;

import com.ecommerce.app.model.ProductModel;
import com.ecommerce.app.service.ProductServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public class TestProductController {

    @Mock
    private ProductServiceImpl productService;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void test_createProduct() {
        ProductModel expected = productModel();
        Mockito.when(productService.create(expected))
                .thenReturn(expected);

        ResponseEntity<ProductModel> actual = productController.create(expected);

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(HttpStatus.ACCEPTED, actual.getStatusCode());
        Assertions.assertEquals(expected, actual.getBody());

        Mockito.verify(productService, Mockito.times(1))
                .create(Mockito.any(ProductModel.class));
    }

    @Test
    public void test_fetchProduct() {
        String productId = UUID.randomUUID().toString();
        ProductModel expected = productModel();
        Mockito.when(productService.fetch(productId))
                .thenReturn(expected);

        ResponseEntity<ProductModel> actual = productController.fetch(productId);

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(HttpStatus.OK, actual.getStatusCode());
        Assertions.assertEquals(expected, actual.getBody());

        Mockito.verify(productService).fetch(Mockito.anyString());

    }


    private ProductModel productModel() {
        return new ProductModel();
    }

}
