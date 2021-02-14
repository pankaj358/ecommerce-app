package com.ecommerce.app.domain;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

/**
 * @author Pankaj Tirpude [tirpudepankaj@gmail.com]
 */

@NoArgsConstructor
@Data
@EqualsAndHashCode
@Entity
@AllArgsConstructor
@Builder
public class Order implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "order_id", nullable = false, updatable = false)
    private UUID orderId;


    @Column(name = "order_date", nullable = false, updatable = false)
    private Instant orderDate;

    @Column(name = "delivery_date", nullable = false)
    private Instant deliveryDate;

    @Column(name = "order_status", nullable = false)
    private String orderStatus;

    @Column(name = "processing_notes")
    private String processingNotes;

    @OneToOne
    @JoinColumn(name = "shoppingCartId")
    private ShoppingCart shoppingCart;


    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "paymentId")
    private PaymentDetails paymentDetails;
}
