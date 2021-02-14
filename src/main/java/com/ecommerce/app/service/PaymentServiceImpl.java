package com.ecommerce.app.service;

import com.ecommerce.app.contant.ErrorMessages;
import com.ecommerce.app.contant.PaymentMethodType;
import com.ecommerce.app.domain.PaymentDetails;
import com.ecommerce.app.exception.PaymentException;
import com.ecommerce.app.exception.ResourceNotFoundException;
import com.ecommerce.app.model.PaymentDetailsModel;
import com.ecommerce.app.repository.PaymentRepository;
import com.ecommerce.app.util.EcommerceUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService{

    @Resource
    private PaymentRepository paymentRepository;

    @Resource
    private EcommerceUtil ecommerceUtil;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public PaymentDetailsModel charge(PaymentDetailsModel paymentDetailsModel) {
         PaymentDetails paymentDetails = ecommerceUtil.toPaymentDetails(paymentDetailsModel);
         paymentDetails = paymentRepository.save(paymentDetails);
         return ecommerceUtil.toPaymentDetailsModel(paymentDetails);

    }

    @Override
    public PaymentDetailsModel fetch(String paymentId) {
        Optional<PaymentDetails> optionalPaymentDetails = paymentRepository.findById(UUID.fromString(paymentId));
        if(optionalPaymentDetails.isPresent() == false)
            throw  new ResourceNotFoundException(String.format(ErrorMessages.RESOURCE_NOT_FOUND.getText(), paymentId));
        return ecommerceUtil.toPaymentDetailsModel(optionalPaymentDetails.get());
    }

    @Override
    public List<PaymentDetailsModel> fetchAll() {
        List<PaymentDetails> paymentDetailsList = (List<PaymentDetails>) paymentRepository.findAll();

        List<PaymentDetailsModel> paymentDetailsModelList
                = paymentDetailsList.stream()
                .map(paymentDetails -> ecommerceUtil.toPaymentDetailsModel(paymentDetails))
                .collect(Collectors.toList());

        return paymentDetailsModelList;
    }
}
