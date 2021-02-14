package com.ecommerce.app.controller;

import com.ecommerce.app.model.ShoppingCartModel;
import com.ecommerce.app.service.ShoppingCartServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TestShoppingCartController {

    @Mock
    private ShoppingCartServiceImpl shoppingCartService;

    @InjectMocks
    private ShoppingCartController shoppingCartController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void test_createShoppingCart() {
        ShoppingCartModel expected = shoppingCartModel();
        Mockito.when(shoppingCartService.create(expected))
                .thenReturn(expected);

        ResponseEntity<ShoppingCartModel> actual = shoppingCartController.create(expected);

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(HttpStatus.ACCEPTED, actual.getStatusCode());
        Assertions.assertEquals(expected, actual.getBody());

        Mockito.verify(shoppingCartService, Mockito.times(1))
                .create(Mockito.any(ShoppingCartModel.class));
    }


    @Test
    public void test_fetchShoppingCart() {
        ShoppingCartModel expected = shoppingCartModel();
        UUID shoppingCardId = UUID.randomUUID();
        Mockito.when(shoppingCartService.fetch(shoppingCardId.toString()))
                .thenReturn(expected);

        ResponseEntity<ShoppingCartModel> actual = shoppingCartController.fetch(shoppingCardId.toString());

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(HttpStatus.OK, actual.getStatusCode());
        Assertions.assertEquals(expected, actual.getBody());

        Mockito.verify(shoppingCartService, Mockito.times(1))
                .fetch(Mockito.anyString());
    }

    @Test
    public void test_fetchAllShoppingCart()
    {
        List<ShoppingCartModel> expected = new ArrayList<>();
        expected.add(shoppingCartModel());
        Mockito.when(shoppingCartService.fetchAll()).thenReturn(expected);
        ResponseEntity<List<ShoppingCartModel>> actual = shoppingCartController.fetchAll();

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(HttpStatus.OK, actual.getStatusCode());
        Assertions.assertEquals(expected.size(), actual.getBody().size());

        Mockito.verify(shoppingCartService, Mockito.times(1))
                .fetchAll();
    }


    private ShoppingCartModel shoppingCartModel() {
        return new ShoppingCartModel();
    }


}
