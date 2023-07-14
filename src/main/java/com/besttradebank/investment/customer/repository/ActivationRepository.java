package com.besttradebank.investment.customer.repository;

import com.besttradebank.investment.customer.entity.Activation;
import com.besttradebank.investment.customer.entity.Customer;
import com.besttradebank.investment.customer.enums.ActivationStatusType;
import java.util.List;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivationRepository extends CrudRepository<Activation, UUID> {

    List<Activation> findAllByCustomerAndStatus(Customer customer, ActivationStatusType valid);
}
