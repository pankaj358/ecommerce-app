package com.ecommerce.app.controller;

import com.ecommerce.app.model.PaymentDetailsModel;
import com.ecommerce.app.service.PaymentServiceImpl;
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

public class TestPaymentController {

    @InjectMocks
    private PaymentController paymentController;

    @Mock
    private PaymentServiceImpl paymentService;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void test_chargePayment() {
        PaymentDetailsModel expected = paymentDetailsModel();
        Mockito.when(paymentService.charge(expected))
                .thenReturn(expected);

        ResponseEntity<PaymentDetailsModel> actual = paymentController.charge(expected);

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(HttpStatus.ACCEPTED, actual.getStatusCode());
        Assertions.assertEquals(expected, actual.getBody());

        Mockito.verify(paymentService, Mockito.times(1)).charge(Mockito.any(PaymentDetailsModel.class));
    }


    @Test
    public void test_fetchPayment()
    {
      PaymentDetailsModel expected = paymentDetailsModel();
        UUID paymentId = UUID.randomUUID();
      Mockito.when(paymentService.fetch(paymentId.toString())).thenReturn(expected);

      ResponseEntity<PaymentDetailsModel> actual = paymentController.fetch(paymentId.toString());

      Assertions.assertNotNull(actual);
      Assertions.assertEquals(HttpStatus.OK, actual.getStatusCode());
      Assertions.assertEquals(expected, actual.getBody());

      Mockito.verify(paymentService, Mockito.times(1))
              .fetch(Mockito.anyString());

    }


    @Test
    public void test_fetchAllPayment()
    {
        List<PaymentDetailsModel> expected = new ArrayList<>();
        expected.add(paymentDetailsModel());
        Mockito.when(paymentService.fetchAll()).thenReturn(expected);

        ResponseEntity<List<PaymentDetailsModel>> actual = paymentController.fetchAll();

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(HttpStatus.OK, actual.getStatusCode());
        Assertions.assertEquals(expected.size(), actual.getBody().size());

        Mockito.verify(paymentService, Mockito.times(1))
                .fetchAll();
    }

    private PaymentDetailsModel paymentDetailsModel() {
        return PaymentDetailsModel.builder().build();
    }


}
