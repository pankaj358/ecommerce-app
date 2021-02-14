package com.ecommerce.app.controller.service;

import com.ecommerce.app.domain.PaymentDetails;
import com.ecommerce.app.model.PaymentDetailsModel;
import com.ecommerce.app.repository.PaymentRepository;
import com.ecommerce.app.service.PaymentServiceImpl;
import com.ecommerce.app.util.EcommerceUtil;
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

public class TestPaymentService {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private EcommerceUtil ecommerceUtil;


    @InjectMocks
    private PaymentServiceImpl paymentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void test_chargePayment() {
        PaymentDetailsModel mockModel = paymentDetailsModel();
        PaymentDetails mockDomain = paymentDetails();
        mockModel.setPaymentId(mockDomain.getPaymentId());

        Mockito.when(ecommerceUtil.toPaymentDetails(mockModel))
                .thenReturn(mockDomain);
        Mockito.when(paymentRepository.save(mockDomain)).thenReturn(mockDomain);
        Mockito.when(ecommerceUtil.toPaymentDetailsModel(mockDomain)).thenReturn(mockModel);
        PaymentDetailsModel actual = paymentService.charge(mockModel);
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(mockModel, actual);

        Mockito.verify(ecommerceUtil, Mockito.times(1)).toPaymentDetails(Mockito.any(PaymentDetailsModel.class));
        Mockito.verify(ecommerceUtil, Mockito.times(1))
                .toPaymentDetailsModel(Mockito.any(PaymentDetails.class));

        Mockito.verify(paymentRepository, Mockito.times(1))
                .save(Mockito.any(PaymentDetails.class));
    }


    @Test
    public void test_fetchPayment()
    {
        UUID paymentId = UUID.randomUUID();
        PaymentDetailsModel mockModel = paymentDetailsModel();
        Optional<PaymentDetails> mockDomain = Optional.of(paymentDetails());
        Mockito.when(paymentRepository.findById(paymentId))
                .thenReturn(mockDomain);
        Mockito.when(ecommerceUtil.toPaymentDetailsModel(mockDomain.get()))
                .thenReturn(mockModel);
        PaymentDetailsModel actual = paymentService.fetch(paymentId.toString());

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(mockModel, actual);
        Mockito.verify(paymentRepository, Mockito.times(1))
                .findById(Mockito.any(UUID.class));
        Mockito.verify(ecommerceUtil, Mockito.times(1))
                .toPaymentDetailsModel(Mockito.any(PaymentDetails.class));
    }


    @Test
    public void test_fetchAll()
    {
        PaymentDetails mockDomain = paymentDetails();
        List<PaymentDetails> mockDomainList = new ArrayList<>();
        mockDomainList.add(mockDomain);
        PaymentDetailsModel mockModel = paymentDetailsModel();
        Mockito.when(paymentRepository.findAll()).thenReturn(mockDomainList);
        Mockito.when(ecommerceUtil.toPaymentDetailsModel(mockDomain))
                .thenReturn(mockModel);

        List<PaymentDetailsModel> actual = paymentService.fetchAll();
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(mockDomainList.size(), actual.size());

        Mockito.verify(ecommerceUtil, Mockito.times(mockDomainList.size())).toPaymentDetailsModel(Mockito.any(PaymentDetails.class));
        Mockito.verify(paymentRepository, Mockito.times(1))
                .findAll();
    }

    private PaymentDetails paymentDetails() {
        return PaymentDetails.builder().paymentId(UUID.randomUUID()).build();
    }

    private PaymentDetailsModel paymentDetailsModel() {
        return PaymentDetailsModel.builder().build();
    }
}
