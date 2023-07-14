package com.besttradebank.investment.customer.service.impl;

import static com.besttradebank.investment.customer.util.ErrorMessageConstants.CUSTOMER_EMAIL_EXISTS;
import static com.besttradebank.investment.customer.util.ErrorMessageConstants.CUSTOMER_USERNAME_EXISTS;
import static com.besttradebank.investment.customer.util.ErrorMessageConstants.DIFFERENT_CUSTOMERS;
import static java.lang.String.format;

import com.besttradebank.investment.customer.dto.request.SignUpCustomerRequest;
import com.besttradebank.investment.customer.dto.response.SignUpCustomerResponse;
import com.besttradebank.investment.customer.entity.Activation;
import com.besttradebank.investment.customer.entity.Customer;
import com.besttradebank.investment.customer.enums.ActivationStatusType;
import com.besttradebank.investment.customer.enums.CustomerStatusType;
import com.besttradebank.investment.customer.exception.CustomerException;
import com.besttradebank.investment.customer.mapper.CustomerMapper;
import com.besttradebank.investment.customer.repository.ActivationRepository;
import com.besttradebank.investment.customer.repository.CustomerRepository;
import com.besttradebank.investment.customer.service.CustomerService;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerMapper mapper;

    private final CustomerRepository customerRepository;
    private final ActivationRepository activationRepository;

    @Override
    @Transactional
    public SignUpCustomerResponse signUp(SignUpCustomerRequest request) {
        Optional<Customer> customerByUsername = customerRepository.findByUsername(request.getUsername());
        if (customerByUsername.isPresent() && customerByUsername.get().getStatus() == CustomerStatusType.ACTIVATED) {
            throw new CustomerException(format(CUSTOMER_USERNAME_EXISTS, request.getUsername()));
        }

        Optional<Customer> customerByEmail = customerRepository.findByEmail(request.getEmail());
        if (customerByEmail.isPresent() && customerByEmail.get().getStatus() == CustomerStatusType.ACTIVATED) {
            throw new CustomerException(format(CUSTOMER_EMAIL_EXISTS, request.getEmail()));
        }

        Customer customer;
        if (customerByUsername.isPresent() && customerByEmail.isEmpty()) {
            customer = customerByUsername.get();
            customer.setEmail(request.getEmail());
            customer.setPassword(request.getPassword());
            activationRepository.findAllByCustomerAndStatus(customer, ActivationStatusType.VALID)
                    .forEach(activation -> activation.setStatus(ActivationStatusType.INVALID));
            activationRepository.save(buildActivation(customer));
            // TODO send email
        } else if (customerByEmail.isPresent() && customerByUsername.isEmpty()) {
            customer = customerByEmail.get();
            customer.setUsername(request.getUsername());
            customer.setPassword(request.getPassword());
            activationRepository.findAllByCustomerAndStatus(customer, ActivationStatusType.VALID)
                    .forEach(activation -> activation.setStatus(ActivationStatusType.INVALID));
            activationRepository.save(buildActivation(customer));
            // TODO send email
        } else if (customerByUsername.isPresent()) {
            customer = customerByUsername.get();
            Customer anotherCustomer = customerByEmail.get();
            if (customer.equals(anotherCustomer)) {
                customer.setPassword(request.getPassword());
                activationRepository.findAllByCustomerAndStatus(customer, ActivationStatusType.VALID)
                        .forEach(activation -> activation.setStatus(ActivationStatusType.INVALID));
                activationRepository.save(buildActivation(customer));
                // TODO send email
            } else {
                throw new CustomerException(format(DIFFERENT_CUSTOMERS, request.getUsername(), request.getEmail()));
            }
        } else {
            customer = mapper.fromSignUpCustomerRequest(request);
            customerRepository.save(customer);
            activationRepository.save(buildActivation(customer));
            // TODO send email
        }

        return mapper.toSignUpCustomerResponse(customer);
    }

    private Activation buildActivation(Customer customer) {
        return Activation.builder()
                .id(UUID.randomUUID())
                .customer(customer)
                .status(ActivationStatusType.VALID)
                .expiredAt(Instant.now().plus(1, ChronoUnit.DAYS))
                .build();
    }
}
