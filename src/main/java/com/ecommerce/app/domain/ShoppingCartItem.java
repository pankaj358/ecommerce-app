package com.ecommerce.app.domain;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

/**
 * @author  Pankaj Tirpude [tirpudepankaj@gmail.com]
 */


@Data
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@AllArgsConstructor
@Builder
public class ShoppingCartItem implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false, name = "shopping_cart_item_id")
    private UUID shoppingCarItemId;

    @Column(name = "product_id")
    private UUID productId;

    @Column(name = "quantity")
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "shoppingCartId", nullable = false)
    private ShoppingCart shoppingCart;

}
