package com.ecommerce.app.domain;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * @author  Pankaj Tirpude [tirpudepankaj@gmail.com]
 */


@Data
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@AllArgsConstructor
@Builder(toBuilder = true)
public class ShoppingCart implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "shopping_cart_id", updatable = false, nullable = false)
    private UUID shoppingCartId;



    @Column(name = "cart_price")
    private BigDecimal cartPrice;


    @OneToMany(mappedBy = "shoppingCart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ShoppingCartItem> shoppingCartItemList;

    @OneToOne(mappedBy = "shoppingCart",  orphanRemoval = true,
            cascade = CascadeType.ALL)
    private Order order;

    @ManyToOne
    private User user;

}
