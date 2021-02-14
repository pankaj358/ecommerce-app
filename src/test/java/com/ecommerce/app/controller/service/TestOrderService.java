package com.ecommerce.app.controller.service;

import com.ecommerce.app.contant.OrderStatus;
import com.ecommerce.app.contant.PaymentMethodType;
import com.ecommerce.app.contant.TransactionStatus;
import com.ecommerce.app.contant.UserType;
import com.ecommerce.app.domain.Order;
import com.ecommerce.app.domain.ShoppingCart;
import com.ecommerce.app.domain.User;
import com.ecommerce.app.model.OrderModel;
import com.ecommerce.app.model.PaymentDetailsModel;
import com.ecommerce.app.repository.OrderRepository;
import com.ecommerce.app.repository.ShoppingCartRepository;
import com.ecommerce.app.repository.UserRepository;
import com.ecommerce.app.service.OrderServiceImpl;
import com.ecommerce.app.service.PaymentService;
import com.ecommerce.app.service.ValidationServiceImpl;
import com.ecommerce.app.util.EcommerceUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
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

public class TestOrderService {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private ValidationServiceImpl validationService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @Mock
    private EcommerceUtil ecommerceUtil;

    @Mock
    private PaymentService paymentService;


    @InjectMocks
    private OrderServiceImpl orderService;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void test_createOrder() {
        OrderModel expected = orderModel();
        Optional<User> mockUser = Optional.of(User.builder().userId(expected.getUserId()).userType(UserType.BUYER.getText()).build());
        Optional<ShoppingCart> mockCart = Optional.of(
                ShoppingCart.builder()
                        .shoppingCartId(expected.getShoppingCartId())
                        .build()
        );

        Order mockOrder = Order.builder().orderId(UUID.randomUUID()).build();

        Mockito.when(validationService.isValidOrder(expected)).thenReturn(true);
        Mockito.when(objectMapper.convertValue(expected, Order.class)).thenReturn(mockOrder);
        Mockito.when(userRepository.findById(expected.getUserId())).thenReturn(mockUser);
        Mockito.when(shoppingCartRepository.findById(expected.getShoppingCartId())).thenReturn(mockCart);
        Mockito.when(validationService.isValidPaymentDetails(expected.getPaymentDetails())).thenReturn(true);
        Mockito.when(orderRepository.save(mockOrder)).thenReturn(mockOrder);
        Mockito.when(ecommerceUtil.toOrderModel(mockOrder)).thenReturn(expected);
        OrderModel actual = orderService.create(expected);

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(expected, actual);

        Mockito.verify(validationService, Mockito.times(1)).
                isValidOrder(Mockito.any(OrderModel.class));
        Mockito.verify(userRepository, Mockito.times(1)).findById(Mockito.any(UUID.class));
        Mockito.verify(shoppingCartRepository, Mockito.times(1)).findById(Mockito.any(UUID.class));
        Mockito.verify(validationService, Mockito.times(1)).isValidPaymentDetails(Mockito.any(PaymentDetailsModel.class));
        Mockito.verify(orderRepository, Mockito.times(1)).save(Mockito.any(Order.class));
        Mockito.verify(ecommerceUtil, Mockito.times(1)).toOrderModel(Mockito.any(Order.class));
    }


    @Test
    public void test_fetchOrder() {
        OrderModel expected = orderModel();
        expected.setOrderId(UUID.randomUUID());
        Optional<Order> mockOrder = Optional.of(Order.builder().orderId(expected.getOrderId()).build());
        Mockito.when(orderRepository.findById(mockOrder.get().getOrderId())).thenReturn(mockOrder);
        Mockito.when(ecommerceUtil.toOrderModel(mockOrder.get())).thenReturn(expected);

        OrderModel actual = orderService.fetch(mockOrder.get().getOrderId().toString());
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(expected, actual);

        Mockito.verify(orderRepository, Mockito.times(1)).findById(Mockito.any(UUID.class));
        Mockito.verify(ecommerceUtil, Mockito.times(1)).toOrderModel(Mockito.any(Order.class));

    }


    @Test
    public void test_fetchAllOrder() {
        List<OrderModel> expected = new ArrayList<>();
        OrderModel orderModel = orderModel();
        expected.add(orderModel);
        orderModel.setOrderId(UUID.randomUUID());
        List<Order> mockList = new ArrayList<>();
        Order order = Order.builder().orderId(orderModel.getOrderId()).build();
        mockList.add(order);
        Mockito.when(orderRepository.findAll()).thenReturn(mockList);
        Mockito.when(ecommerceUtil.toOrderModel(order)).thenReturn(orderModel);

        List<OrderModel> actual = orderService.fetchAll();

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(expected.size(), actual.size());

        Mockito.verify(orderRepository, Mockito.times(1)).findAll();
        Mockito.verify(ecommerceUtil, Mockito.times(1)).toOrderModel(Mockito.any(Order.class));
    }


    @Test
    public void test_patchOrder() {
        OrderModel expected = orderModel();
        expected.setOrderId(UUID.randomUUID());
        expected.setOrderStatus(OrderStatus.DISPATCHED.getText());
        Order mockOrder = Order.builder().orderId(expected.getOrderId()).build();
        Mockito.when(orderRepository.findById(expected.getOrderId())).thenReturn(Optional.of(mockOrder));
        Mockito.when(orderRepository.save(mockOrder)).thenReturn(mockOrder);
        Mockito.when(objectMapper.convertValue(mockOrder, OrderModel.class)).thenReturn(expected);
        OrderModel actual = orderService.patchOrder(expected.getOrderId().toString(), OrderStatus.DISPATCHED.getText());
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(expected, actual);
        Mockito.verify(orderRepository, Mockito.times(1)).findById(Mockito.any(UUID.class));
        Mockito.verify(orderRepository, Mockito.times(1)).save(Mockito.any(Order.class));
        Mockito.verify(objectMapper, Mockito.times(1)).convertValue(mockOrder, OrderModel.class);
    }


    private OrderModel orderModel() {
        return OrderModel.builder()
                .orderStatus(OrderStatus.ACCEPTED.getText())
                .userId(UUID.randomUUID())
                .shoppingCartId(UUID.randomUUID())
                .paymentDetails(paymentDetailsModel())
                .build();
    }

    private PaymentDetailsModel paymentDetailsModel() {
        return PaymentDetailsModel.builder()
                .amount(new BigDecimal("500"))
                .methodType(PaymentMethodType.DEBIT_CART.getText())
                .transactionStatus(TransactionStatus.ACCEPTED.getText())
                .build();
    }
}
