package com.ecommerce.app.controller;

import com.ecommerce.app.contant.OrderStatus;
import com.ecommerce.app.model.OrderModel;
import com.ecommerce.app.service.OrderServiceImpl;
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

public class TestOrderController {

    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderServiceImpl orderService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void test_orderCreate() {
        Mockito.when(orderService.create(orderModel()))
                .thenReturn(orderModel());

        ResponseEntity<OrderModel> order = orderController.create(orderModel());

        Assertions.assertNotNull(order);
        Assertions.assertEquals(HttpStatus.ACCEPTED, order.getStatusCode());
        Assertions.assertEquals(orderModel(), order.getBody());

        Mockito.verify(orderService, Mockito.times(1)).create(Mockito.any(OrderModel.class));
    }

    @Test
    public void test_fetchOrder() {
        UUID orderId = UUID.randomUUID();
        OrderModel expected = orderModel();
        Mockito.when(orderService.fetch(orderId.toString())).thenReturn(expected);

        ResponseEntity<OrderModel> actual = orderController.fetch(orderId.toString());

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(HttpStatus.OK, actual.getStatusCode());
        Assertions.assertEquals(expected, actual.getBody());

        Mockito.verify(orderService).fetch(Mockito.anyString());
    }


    @Test
    public void test_fetchAllOrder() {
        List<OrderModel> expectedList = new ArrayList<>();
        OrderModel orderModel = orderModel();
        expectedList.add(orderModel());

        Mockito.when(orderService.fetchAll()).thenReturn(expectedList);

        ResponseEntity<List<OrderModel>> actual = orderController.fetchAll();

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(HttpStatus.OK, actual.getStatusCode());
        Assertions.assertEquals(expectedList.size(), actual.getBody().size());

        Mockito.verify(orderService, Mockito.times(1)).fetchAll();

    }

    @Test
    public void test_orderStatusProgress() {
        String orderId = UUID.randomUUID().toString();
        OrderModel expected = orderModel();
        expected.setOrderStatus(OrderStatus.DISPATCHED.getText());
        expected.setOrderId(UUID.fromString(orderId));
        Mockito.when(orderService.patchOrder(orderId, OrderStatus.DISPATCHED.getText())).thenReturn(expected);
        ResponseEntity<OrderModel> actual = orderController.statusProgress(orderId, OrderStatus.DISPATCHED.getText());
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(HttpStatus.OK, actual.getStatusCode());
        Assertions.assertEquals(expected, actual.getBody());

        Mockito.verify(orderService, Mockito.times(1)).patchOrder(Mockito.anyString(), Mockito.anyString());
    }


    private OrderModel orderModel() {
        OrderModel orderModel = new OrderModel();
        return orderModel;
    }

}
