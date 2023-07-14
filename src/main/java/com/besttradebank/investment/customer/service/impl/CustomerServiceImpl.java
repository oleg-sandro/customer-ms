package com.besttradebank.investment.customer.service.impl;

import static com.besttradebank.investment.customer.util.ErrorMessageConstants.CUSTOMER_EMAIL_EXISTS;
import static com.besttradebank.investment.customer.util.ErrorMessageConstants.CUSTOMER_USERNAME_EXISTS;
import static com.besttradebank.investment.customer.util.ErrorMessageConstants.DIFFERENT_CUSTOMERS;
import static java.lang.String.format;

import com.besttradebank.investment.customer.dto.request.SignUpCustomerRequest;
import com.besttradebank.investment.customer.dto.response.SignUpCustomerResponse;
import com.besttradebank.investment.customer.entity.Customer;
import com.besttradebank.investment.customer.enums.CustomerStatusType;
import com.besttradebank.investment.customer.exception.CustomerException;
import com.besttradebank.investment.customer.mapper.CustomerMapper;
import com.besttradebank.investment.customer.repository.CustomerRepository;
import com.besttradebank.investment.customer.service.CustomerService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerMapper mapper;

    private final CustomerRepository repository;

    @Override
    @Transactional
    public SignUpCustomerResponse signUp(SignUpCustomerRequest request) {
        Optional<Customer> customerByUsername = repository.findByUsername(request.getUsername());
        if (customerByUsername.isPresent() && customerByUsername.get().getStatus() == CustomerStatusType.ACTIVATED) {
            throw new CustomerException(format(CUSTOMER_USERNAME_EXISTS, request.getUsername()));
        }

        Optional<Customer> customerByEmail = repository.findByEmail(request.getEmail());
        if (customerByEmail.isPresent() && customerByEmail.get().getStatus() == CustomerStatusType.ACTIVATED) {
            throw new CustomerException(format(CUSTOMER_EMAIL_EXISTS, request.getEmail()));
        }

        Customer customer;
        if (customerByUsername.isPresent() && customerByEmail.isEmpty()) {
            customer = customerByUsername.get();
            customer.setEmail(request.getEmail());
            // deactivate previous activation link
            // activate new activation link
            // TODO send email
        } else if (customerByEmail.isPresent() && customerByUsername.isEmpty()) {
            customer = customerByEmail.get();
            customer.setUsername(request.getUsername());
            // deactivate previous activation link
            // activate new activation link
            // TODO send email
        } else if (customerByUsername.isPresent()) {
            customer = customerByUsername.get();
            Customer anotherCustomer = customerByEmail.get();
            if (customer.equals(anotherCustomer)) {
                // deactivate previous activation link
                // activate new activation link
                // TODO send email
            } else {
                throw new CustomerException(format(DIFFERENT_CUSTOMERS, request.getUsername(), request.getEmail()));
            }
        } else {
            customer = mapper.fromSignUpCustomerRequest(request);
            repository.save(customer);
            // activate new activation link
            // TODO send email
        }

        return mapper.toSignUpCustomerResponse(customer);
    }
}
