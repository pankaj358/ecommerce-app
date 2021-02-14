package com.ecommerce.app.model;

import lombok.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

/**
 * @author  Pankaj Tirpude [tirpudepankaj@gmail.com]
 */

@Data
@NoArgsConstructor
@EqualsAndHashCode
@Builder(toBuilder = true)
@AllArgsConstructor
public class OrderModel implements Serializable {

    private UUID orderId;

    private UUID userId;

    private UUID shoppingCartId;

    private Instant orderDate;

    private Instant deliveryDate;

    private String orderStatus;

    private PaymentDetailsModel paymentDetails;

}
