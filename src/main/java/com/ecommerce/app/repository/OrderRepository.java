package com.ecommerce.app.repository;

import com.ecommerce.app.domain.Order;
import com.ecommerce.app.domain.ShoppingCart;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


/**
 * @author  Pankaj Tirpude [tirpudepankaj@gmail.com]
 */


@Repository
public interface OrderRepository extends PagingAndSortingRepository<Order, UUID> {


    public Optional<Order> findByShoppingCart(ShoppingCart shoppingCart);

}
