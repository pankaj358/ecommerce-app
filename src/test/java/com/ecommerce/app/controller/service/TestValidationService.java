package com.ecommerce.app.controller.service;

import com.ecommerce.app.contant.PaymentMethodType;
import com.ecommerce.app.contant.UserType;
import com.ecommerce.app.exception.PaymentException;
import com.ecommerce.app.exception.ValidationException;
import com.ecommerce.app.model.*;
import com.ecommerce.app.service.OrderServiceImpl;
import com.ecommerce.app.service.ShoppingCartService;
import com.ecommerce.app.service.UserServiceImpl;
import com.ecommerce.app.service.ValidationServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TestValidationService {

    @Mock
    private UserServiceImpl userService;

    @Mock
    private ShoppingCartService shoppingCartService;

    @InjectMocks
    private ValidationServiceImpl validationService;

    @Mock
    private OrderServiceImpl orderService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void test_isValidUser_throwValidationException_forNullUser() {
        Assertions.assertThrows(ValidationException.class, () -> validationService.isValidUser(null));
    }

    @Test
    public void test_isValidUser_throwValidationException_whenUserTypeIsNull() {
        Assertions.assertThrows(ValidationException.class, () -> validationService.isValidUser(userModel()));
    }

    @Test
    public void test_isValidUser() {
        UserModel mockUser = userModel();
        mockUser.setUserType(UserType.SELLER.getText());
        boolean actual = validationService.isValidUser(mockUser);
        Assertions.assertTrue(actual);
    }


    @Test
    public void test_isValidProduct_throwsValidationException() {
        Assertions.assertThrows(ValidationException.class, () -> validationService.isValidProduct(null));
    }


    @Test
    public void test_isValidProduct_when_userIsNotSeller() {
        ProductModel mockProduct = productModel();
        UserModel userModel = userModel();
        userModel.setUserType(UserType.BUYER.getText());
        Mockito.when(userService.fetch(mockProduct.getSellerId().toString())).thenReturn(userModel);
        Assertions.assertThrows(ValidationException.class, () -> validationService.isValidProduct(mockProduct));
        Mockito.verify(userService, Mockito.times(1))
                .fetch(Mockito.anyString());

    }


    @Test
    public void test_isValidProduct() {
        ProductModel mockProduct = productModel();
        UserModel mockUser = userModel();
        mockUser.setUserType(UserType.SELLER.getText());
        Mockito.when(userService.fetch(mockProduct.getSellerId().toString())).thenReturn(mockUser);
        boolean actual = validationService.isValidProduct(mockProduct);
        Assertions.assertTrue(actual);

        Mockito.verify(userService, Mockito.times(1))
                .fetch(Mockito.anyString());
    }


    @Test
    public void test_validPayment_shouldThrowPaymentExceptionFor_CheckMethod() {
        Assertions.assertThrows(PaymentException.class, () -> validationService.isValidPaymentDetails(paymentDetails()));
    }

    @Test
    public void test_valid_Payment() {
        PaymentDetailsModel mockPayment = paymentDetails();
        mockPayment.setMethodType(PaymentMethodType.DEBIT_CART.getText());
        boolean actual = validationService.isValidPaymentDetails(mockPayment);
        Assertions.assertTrue(actual);
    }

    @Test
    public void test_isValidOrder_throwsValidationException_forNullObject()
    {
       Assertions.assertThrows(ValidationException.class, ()-> validationService.isValidOrder(null));
    }

    @Test
    public void test_isValidOrder_throwsValidationException_WhenShoppingCartIsNotValid()
    {
       OrderModel mockOrder = orderModel();
       Mockito.when(shoppingCartService.fetch(mockOrder.getShoppingCartId().toString()))
               .thenReturn(null);
       Assertions.assertThrows(ValidationException.class, ()-> validationService.isValidOrder(mockOrder));
       Mockito.verify(shoppingCartService, Mockito.times(1))
               .fetch(Mockito.anyString());
    }

    @Test
    public void test_isValidOrder_throwsValidationExceptionWhenItemListIsEmpty()
    {
       OrderModel mockOrder = orderModel();
       ShoppingCartModel mockCart = shoppingCartModel();
       List<ShoppingCartItemModel> mockItemList = new ArrayList<>();
       mockCart.setItemList(mockItemList);
       Mockito.when(shoppingCartService.fetch(mockOrder.getShoppingCartId().toString()))
               .thenReturn(mockCart);

      Assertions.assertThrows(ValidationException.class, ()->validationService.isValidOrder(mockOrder));


       Mockito.verify(shoppingCartService, Mockito.times(1))
               .fetch(Mockito.anyString());

    }

    @Test
    public void test_isValidOrder_throwsValidationExceptionForDuplicateCart()
    {
        OrderModel mockOrder = orderModel();
        ShoppingCartModel mockCart = shoppingCartModel();
        List<ShoppingCartItemModel> mockItemList = new ArrayList<>();
        ShoppingCartItemModel shoppingCartItemModel = new ShoppingCartItemModel();
        mockItemList.add(shoppingCartItemModel);
        mockCart.setItemList(mockItemList);

        Optional<OrderModel> mockOrderOptional = Optional.of(mockOrder);

        Mockito.when(shoppingCartService.fetch(mockOrder.getShoppingCartId().toString()))
                .thenReturn(mockCart);
        Mockito.when(orderService.fetchOrderByCart(Mockito.anyString()))
                .thenReturn(mockOrderOptional);

        Assertions.assertThrows(ValidationException.class, ()->validationService.isValidOrder(mockOrder));
        Mockito.verify(shoppingCartService, Mockito.times(1))
                .fetch(Mockito.anyString());
        Mockito.verify(orderService, Mockito.times(1))
                .fetchOrderByCart(Mockito.anyString());

    }


    @Test
    public void test_isValidOrder()
    {
        OrderModel mockOrder = orderModel();
        ShoppingCartModel mockCart = shoppingCartModel();
        List<ShoppingCartItemModel> mockItemList = new ArrayList<>();
        ShoppingCartItemModel shoppingCartItemModel = new ShoppingCartItemModel();
        mockItemList.add(shoppingCartItemModel);
        mockCart.setItemList(mockItemList);
        Mockito.when(shoppingCartService.fetch(mockOrder.getShoppingCartId().toString()))
                .thenReturn(mockCart);
        Mockito.when(orderService.fetchOrderByCart(mockCart.getShoppingCartId().toString()))
                .thenReturn(null);

        boolean actual = validationService.isValidOrder(mockOrder);

        Assertions.assertTrue(actual);

        Mockito.verify(shoppingCartService, Mockito.times(1))
                .fetch(Mockito.anyString());
        Mockito.verify(orderService, Mockito.times(1))
                .fetchOrderByCart(Mockito.anyString());

    }


    private ShoppingCartModel shoppingCartModel()
    {
        return ShoppingCartModel.builder()
                .shoppingCartId(UUID.randomUUID())
                .build();
    }


    private PaymentDetailsModel paymentDetails() {
        return PaymentDetailsModel.builder()
                .paymentId(UUID.randomUUID())
                .methodType(PaymentMethodType.CHECK.getText())
                .build();
    }


    private UserModel userModel() {
        return UserModel.builder().userId(UUID.randomUUID())
                .build();
    }

    private ProductModel productModel() {
        return ProductModel.builder().sellerId(UUID.randomUUID()).build();
    }

    private OrderModel orderModel()
    {
        return OrderModel.builder().shoppingCartId(UUID.randomUUID()).build();
    }
}
