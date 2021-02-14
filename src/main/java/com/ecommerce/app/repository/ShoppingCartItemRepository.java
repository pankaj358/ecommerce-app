package com.ecommerce.app.repository;

import com.ecommerce.app.domain.ShoppingCartItem;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @author Pankaj Tirpude [tirpudepankaj@gmail.com]
 */


@Repository
public interface ShoppingCartItemRepository extends PagingAndSortingRepository<ShoppingCartItem, UUID> {

}
