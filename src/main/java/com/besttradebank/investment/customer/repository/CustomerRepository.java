package com.besttradebank.investment.customer.repository;

import com.besttradebank.investment.customer.entity.Customer;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, UUID> {

    Optional<Customer> findByUsername(String username);

    Optional<Customer> findByEmail(String email);
}
