package com.ecommerce.app.service;

import com.ecommerce.app.exception.ValidationException;
import com.ecommerce.app.model.*;

/**
 * @author  Pankaj Tirpude [tirpudepankaj@gmail.com]
 */


public interface ValidationService {

    public boolean isValidUser(UserModel userModel) throws ValidationException;

    public boolean isValidProduct(ProductModel productModel) throws ValidationException;

    public boolean isValidOrder(OrderModel orderModel) throws ValidationException;

    public boolean isValidPaymentDetails(PaymentDetailsModel paymentDetailsModel);

}
