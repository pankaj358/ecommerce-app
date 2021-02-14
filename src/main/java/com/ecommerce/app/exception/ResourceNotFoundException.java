package com.ecommerce.app.exception;

/**
 * @author  Pankaj Tirpude [tirpudepankaj@gmail.com]
 */

public class ResourceNotFoundException extends RuntimeException {


    public ResourceNotFoundException(String message)
    {
        super(message);
    }

}
