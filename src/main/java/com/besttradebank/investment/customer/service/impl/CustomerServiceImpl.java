package com.besttradebank.investment.customer.service.impl;

import com.besttradebank.investment.customer.dto.request.SignUpCustomerRequest;
import com.besttradebank.investment.customer.dto.response.SignUpCustomerResponse;
import com.besttradebank.investment.customer.service.CustomerService;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Override
    public SignUpCustomerResponse signUp(SignUpCustomerRequest request) {
        return new SignUpCustomerResponse(UUID.randomUUID(), request.getUsername(), request.getEmail());
    }
}
