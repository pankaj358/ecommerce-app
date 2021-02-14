package com.ecommerce.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class PaymentDetailsModel {

    private UUID paymentId;
    private BigDecimal amount;
    private String methodType;
    private String transactionStatus;
    private Instant paymentDate;

}
