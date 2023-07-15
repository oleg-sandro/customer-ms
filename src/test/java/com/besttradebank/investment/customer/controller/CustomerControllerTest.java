package com.besttradebank.investment.customer.controller;

import static com.besttradebank.investment.customer.util.TestConstants.CUSTOMER_EMAIL;
import static com.besttradebank.investment.customer.util.TestConstants.CUSTOMER_PASSWORD;
import static com.besttradebank.investment.customer.util.TestConstants.CUSTOMER_USERNAME;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.besttradebank.investment.customer.dto.request.SignUpCustomerRequest;
import com.besttradebank.investment.customer.dto.response.SignUpCustomerResponse;
import com.besttradebank.investment.customer.service.CustomerService;
import jakarta.validation.Valid;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    @InjectMocks
    private CustomerController controller;

    @Mock
    private CustomerService service;

    @Test
    void givenSignUp_whenInvoke_thenSuccess() {
        // given
        SignUpCustomerRequest request = new SignUpCustomerRequest(CUSTOMER_USERNAME, CUSTOMER_EMAIL, CUSTOMER_PASSWORD);

        // when
        controller.signUp(request);

        //then
        verify(service, times(1)).signUp(any(SignUpCustomerRequest.class));
    }

    @Test
    void givenActivate_whenInvoke_thenSuccess() {
        // when
        controller.activate(UUID.randomUUID());

        //then
        verify(service, times(1)).activate(any(UUID.class));
    }
}