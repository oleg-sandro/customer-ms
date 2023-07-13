package com.besttradebank.investment.customer.service.impl;

import com.besttradebank.investment.customer.dto.request.SignUpCustomerRequest;
import com.besttradebank.investment.customer.dto.response.SignUpCustomerResponse;
import com.besttradebank.investment.customer.mapper.CustomerMapper;
import com.besttradebank.investment.customer.repository.CustomerRepository;
import com.besttradebank.investment.customer.service.CustomerService;
import com.besttradebank.investment.customer.entity.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerMapper mapper;

    private final CustomerRepository repository;

    @Override
    public SignUpCustomerResponse signUp(SignUpCustomerRequest request) {
        Customer customer = mapper.fromSignUpCustomerRequest(request);
        repository.save(customer);
        return mapper.toSignUpCustomerResponse(customer);
    }
}
