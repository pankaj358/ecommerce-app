package com.ecommerce.app.controller.service;

import com.ecommerce.app.contant.UserType;
import com.ecommerce.app.domain.ShoppingCart;
import com.ecommerce.app.domain.ShoppingCartItem;
import com.ecommerce.app.domain.User;
import com.ecommerce.app.model.ProductModel;
import com.ecommerce.app.model.ShoppingCartItemModel;
import com.ecommerce.app.model.ShoppingCartModel;
import com.ecommerce.app.model.UserModel;
import com.ecommerce.app.repository.ShoppingCartRepository;
import com.ecommerce.app.service.ProductService;
import com.ecommerce.app.service.ShoppingCartServiceImpl;
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

public class TestShoppingCartService {


    @InjectMocks
    private ShoppingCartServiceImpl shoppingCartService;

    @Mock
    private ProductService productService;


    @Mock
    private ValidationService validationService;

    @Mock
    private EcommerceUtil ecommerceUtil;

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void test_createShoppingCart() {
        ShoppingCartModel cart = shoppingCartModel();
        ShoppingCart shoppingCart = shoppingCart();
        shoppingCart.setUser(User.builder().userId(cart.getUserId()).build());
        List<ShoppingCartItem> itemList = new ArrayList<>();
        itemList.add(shoppingCartItem());
        ProductModel mockProduct = ProductModel.builder().productId(UUID.randomUUID()).productPrice(new BigDecimal("10000")).available(true).build();

        Mockito.when(validationService.isValidUser(UserModel.builder().userId(cart.getUserId()).userType(UserType.BUYER.getText()).build()))
                .thenReturn(true);
        Mockito.when(productService.fetch(Mockito.anyString())).thenReturn(mockProduct);
        Mockito.when(ecommerceUtil.toShoppingCart(cart))
                .thenReturn(shoppingCart);
        Mockito.when(shoppingCartRepository.save(shoppingCart)).thenReturn(shoppingCart);
        Mockito.when(ecommerceUtil.toShoppingCartModel(shoppingCart)).thenReturn(cart);
        ShoppingCartModel actual = shoppingCartService.create(cart);

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(cart, actual);
        Mockito.verify(validationService, Mockito.times(1))
                .isValidUser(Mockito.any(UserModel.class));
        Mockito.verify(productService, Mockito.times(3))
                .fetch(Mockito.anyString());
        Mockito.verify(ecommerceUtil, Mockito.times(1))
                .toShoppingCart(Mockito.any(ShoppingCartModel.class));
        Mockito.verify(ecommerceUtil, Mockito.times(1))
                .toShoppingCartModel(Mockito.any(ShoppingCart.class));
    }

    @Test
    public void test_fetchShoppingCart() {
        ShoppingCartModel expected = shoppingCartModel();
        expected.setShoppingCartId(UUID.randomUUID());

        Optional<ShoppingCart> optionalShoppingCart = Optional.of(shoppingCart());

        Mockito.when(shoppingCartRepository.findById(expected.getShoppingCartId()))
                .thenReturn(optionalShoppingCart);
        Mockito.when(ecommerceUtil.toShoppingCartModel(optionalShoppingCart.get())).thenReturn(expected);
        ShoppingCartModel actual = shoppingCartService.fetch(expected.getShoppingCartId().toString());

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(expected, actual);

        Mockito.verify(shoppingCartRepository, Mockito.times(1))
                .findById(Mockito.any(UUID.class));
        Mockito.verify(ecommerceUtil, Mockito.times(1))
                .toShoppingCartModel(Mockito.any(ShoppingCart.class));
    }

    @Test
    public void test_fetchAllShoppingCart() {
        ShoppingCartModel expected = shoppingCartModel();
        expected.setShoppingCartId(UUID.randomUUID());
        List<ShoppingCartModel> exList = new ArrayList<>();
        exList.add(expected);
        List<ShoppingCart> domainList = new ArrayList<>();
        domainList.add(shoppingCart());
        Mockito.when(shoppingCartRepository.findAll())
                .thenReturn(domainList);
        Mockito.when(ecommerceUtil.toShoppingCartModel(shoppingCart())).thenReturn(expected);
        List<ShoppingCartModel> actualList = shoppingCartService.fetchAll();

        Assertions.assertNotNull(actualList);
        Assertions.assertEquals(exList.size(), actualList.size());

        Mockito.verify(shoppingCartRepository, Mockito.times(1))
                .findAll();
        Mockito.verify(ecommerceUtil, Mockito.times(1))
                .toShoppingCartModel(Mockito.any(ShoppingCart.class));
    }

    private ShoppingCart shoppingCart() {
        return
                ShoppingCart
                        .builder()
                        .build();
    }


    private ShoppingCartItem shoppingCartItem() {
        return ShoppingCartItem.builder().build();
    }

    private ShoppingCartModel shoppingCartModel() {
        List<ShoppingCartItemModel> itemList = new ArrayList<>();
        itemList.add(shoppingCartItemModel());
        return ShoppingCartModel.builder()
                .userId(UUID.randomUUID())
                .itemList(itemList)
                .build();
    }

    private ShoppingCartItemModel shoppingCartItemModel() {
        return ShoppingCartItemModel.builder()
                .productId(UUID.randomUUID())
                .quantity(1)
                .build();
    }
}
