package com.ecommerce.app.repository;

import com.ecommerce.app.domain.PaymentDetails;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PaymentRepository extends PagingAndSortingRepository<PaymentDetails, UUID> {
}
