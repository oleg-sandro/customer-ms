package com.besttradebank.investment.customer.service;

import com.besttradebank.investment.customer.dto.request.SignUpCustomerRequest;
import com.besttradebank.investment.customer.dto.response.ActivateCustomerAccountResponse;
import com.besttradebank.investment.customer.dto.response.SignUpCustomerResponse;
import java.util.UUID;

public interface CustomerService {

    SignUpCustomerResponse signUp(SignUpCustomerRequest request);

    ActivateCustomerAccountResponse activate(UUID activationId);
}
