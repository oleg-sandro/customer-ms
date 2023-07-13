package com.besttradebank.investment.customer.repository;

import com.besttradebank.investment.entity.Customer;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, UUID> {
}
