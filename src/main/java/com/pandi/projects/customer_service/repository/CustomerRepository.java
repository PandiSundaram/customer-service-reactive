package com.pandi.projects.customer_service.repository;

import com.pandi.projects.customer_service.entity.Customer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends ReactiveCrudRepository<Customer,Integer> {
}
