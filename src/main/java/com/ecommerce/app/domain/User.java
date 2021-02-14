package com.ecommerce.app.domain;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;


/**
 * @author  Pankaj Tirpude [tirpudepankaj@gmail.com]
 */


@NoArgsConstructor
@Entity
@EqualsAndHashCode
@Data
@AllArgsConstructor
@Builder(toBuilder = true)
public class User implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name="UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID userId;

    @Column(name = "first_name", nullable = false, length = 30)
    private String firstName;

    @Column(name = "last_name", length = 30)
    private String lastName;

    @Column(name = "user_type", updatable = false, nullable = false, length = 20)
    private String userType;

    @Column(name = "country", updatable = false, nullable = false, length = 3)
    private String country;

    @Column(name = "email", nullable = false, length = 30)
    private String email;

    @Column(name = "phone_number", length = 10)
    private String phoneNumber;


    @OneToMany(mappedBy = "user")
    private List<Order> orderList;


    @OneToMany(mappedBy = "seller")
    private List<Product> productList;

    @OneToMany(mappedBy = "user")
    private List<ShoppingCart> shoppingCarts;

}
