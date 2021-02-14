package com.ecommerce.app.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;


/**
 * @author  Pankaj Tirpude [tirpudepankaj@gmail.com]
 */


@Data
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder(toBuilder = true)
public class ProductModel implements Serializable {

    private UUID productId;

    private String productName;

    private BigDecimal productPrice;

    private boolean available;

    private UUID sellerId;

}
