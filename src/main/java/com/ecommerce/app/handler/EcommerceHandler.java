package com.ecommerce.app.handler;


import com.ecommerce.app.exception.PaymentException;
import com.ecommerce.app.exception.ResourceNotFoundException;
import com.ecommerce.app.exception.ValidationException;
import com.ecommerce.app.model.ErrorModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author  Pankaj Tirpude [tirpudepankaj@gmail.com]
 */


@ControllerAdvice
@Slf4j
public class EcommerceHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(value = {ValidationException.class})
    public ResponseEntity<ErrorModel> handleValidationException(ValidationException exception, WebRequest webRequest) {
        log.error(exception.getLocalizedMessage(), exception);
        ErrorModel errorModel = ErrorModel.builder().message(exception.getLocalizedMessage()).build();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorModel);
    }


    @ExceptionHandler(value = {ResourceNotFoundException.class})
    public ResponseEntity<ErrorModel> handleNotFoundException(ResourceNotFoundException exception, WebRequest webRequest) {
       log.error(exception.getLocalizedMessage(), exception);
       ErrorModel errorModel = ErrorModel.builder().message(exception.getLocalizedMessage()).build();
       return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorModel);
    }

    @ExceptionHandler(value = {PaymentException.class})
    public ResponseEntity<ErrorModel> handlePaymentException(PaymentException exception, WebRequest webRequest)
    {
        log.error(exception.getLocalizedMessage(), exception);
        ErrorModel errorModel = ErrorModel.builder().message(exception.getLocalizedMessage()).build();
        return  ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body(errorModel);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ErrorModel> handleAllException(Exception exception, WebRequest webRequest)
    {
        log.error(exception.getLocalizedMessage(), exception);
        ErrorModel errorModel = ErrorModel.builder().message("Something went wrong!, Please verify your the contract and try again").build();
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errorModel);
    }


}
