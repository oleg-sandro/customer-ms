package com.besttradebank.investment.customer.service;

import com.besttradebank.investment.customer.dto.request.SignUpCustomerRequest;
import com.besttradebank.investment.customer.dto.response.SignUpCustomerResponse;

public interface CustomerService {

    SignUpCustomerResponse signUp(SignUpCustomerRequest request);
}
