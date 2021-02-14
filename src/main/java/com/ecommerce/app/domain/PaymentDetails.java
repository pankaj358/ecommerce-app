package com.ecommerce.app.domain;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
@Builder(toBuilder = true)
@Entity
public class PaymentDetails {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "payment_id", nullable = false, updatable = false)
    private UUID paymentId;

    @Column(name ="amount", nullable = false, updatable = false)
    private BigDecimal amount;

    @Column(name = "method_type", nullable = false, updatable = false)
    private String methodType;

    @Column(name = "payment_date", nullable = false, updatable = false)
    private Instant paymentDate;

    @Column(name = "payment_status", nullable = false)
    private String paymentStatus;

    @OneToOne(mappedBy ="paymentDetails",  orphanRemoval = true,
            cascade = CascadeType.ALL )
    private Order order;


}
