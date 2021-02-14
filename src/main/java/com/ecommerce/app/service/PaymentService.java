package com.ecommerce.app.service;

import com.ecommerce.app.model.PaymentDetailsModel;

import java.util.List;

public interface PaymentService {

    public PaymentDetailsModel charge(PaymentDetailsModel paymentDetailsModel);

    public PaymentDetailsModel fetch(String paymentId);

    public List<PaymentDetailsModel> fetchAll();
}
