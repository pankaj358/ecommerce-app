package com.ecommerce.app.repository;

import com.ecommerce.app.domain.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @author  Pankaj Tirpude [tirpudepankaj@gmail.com]
 */


@Repository
public interface UserRepository extends PagingAndSortingRepository<User, UUID> {
}
