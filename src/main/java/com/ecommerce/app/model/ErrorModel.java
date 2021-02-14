package com.ecommerce.app.model;

import lombok.*;

import java.io.Serializable;


/**
 * @author  Pankaj Tirpude [tirpudepankaj@gmail.com]
 */


@Value
@Builder
public class ErrorModel implements Serializable {

   private String message;

}
