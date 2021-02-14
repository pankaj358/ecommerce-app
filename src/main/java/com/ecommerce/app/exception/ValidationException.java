package com.ecommerce.app.exception;

/**
 * @author  Pankaj Tirpude [tirpudepankaj@gmail.com]
 */

public class ValidationException extends RuntimeException{

    public ValidationException(String cause)
    {
        super(cause);
    }

    public ValidationException()
    {

    }

}
