package com.ecommerce.app.service;

import com.ecommerce.app.contant.ErrorMessages;
import com.ecommerce.app.contant.PaymentMethodType;
import com.ecommerce.app.contant.UserType;
import com.ecommerce.app.exception.PaymentException;
import com.ecommerce.app.exception.ValidationException;
import com.ecommerce.app.model.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.Optional;


/**
 * @author Pankaj Tirpude [tirpudepankaj@gmail.com]
 */


@Service
public class ValidationServiceImpl implements ValidationService {

    @Resource
    private UserService userService;

    @Resource
    private ShoppingCartService shoppingCartService;

    @Resource
    private OrderService orderService;

    @Override
    public boolean isValidUser(UserModel userModel) throws ValidationException {
        //FIXME - validate required properties
        if (null == userModel) throw new ValidationException(ErrorMessages.USER_EMPTY.getText());
        if (null == userModel.getUserType() || userModel.getUserType().trim().length() == 0)
            throw new ValidationException(ErrorMessages.USER_TYPE_VALIDATION.getText());
        return true;
    }

    @Override
    public boolean isValidProduct(ProductModel productModel) {
        //FIXME - validate required properties
        if (null == productModel || Objects.isNull(productModel))
            throw new ValidationException(ErrorMessages.PRODUCT_DETAILS_EMPTY.getText());
        UserModel userModel = userService.fetch(productModel.getSellerId().toString());

        if (null == userModel || !UserType.SELLER.getText().equalsIgnoreCase(userModel.getUserType()))
            throw new ValidationException(ErrorMessages.NOT_SELLER.getText());

        return true;

    }

    @Override
    public boolean isValidOrder(OrderModel orderModel) throws ValidationException {
        //FIXME - validate required properties
        if (null != orderModel && orderModel.getShoppingCartId() != null) {
            ShoppingCartModel shoppingCartModel = shoppingCartService.fetch(orderModel.getShoppingCartId().toString());
            if (shoppingCartModel != null && shoppingCartModel.getItemList().isEmpty() == false) {
                Optional<OrderModel> optional = orderService.fetchOrderByCart(orderModel.getShoppingCartId().toString());
                 if(optional.isPresent() == false) return true;
            }
        }
        throw new ValidationException(ErrorMessages.ORDER_VALIDATION_FAILED_FOR_UNKNOWN_REASON.getText());
    }

    @Override
    public boolean isValidPaymentDetails(PaymentDetailsModel paymentDetailsModel) {
        /**
         * Here mock the payment failure for some method type
         * may be check method type
         */

        if(paymentDetailsModel.getMethodType() == null || PaymentMethodType.CHECK.getText().equalsIgnoreCase(paymentDetailsModel.getMethodType()))
            throw new PaymentException(ErrorMessages.PAYMENT_FAILURE_MESSAGE.getText());
       return true;
    }


}
