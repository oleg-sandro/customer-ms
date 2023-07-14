package com.besttradebank.investment.customer.controller;

import com.besttradebank.investment.customer.dto.request.SignUpCustomerRequest;
import com.besttradebank.investment.customer.dto.response.ActivateCustomerAccountResponse;
import com.besttradebank.investment.customer.dto.response.SignUpCustomerResponse;
import com.besttradebank.investment.customer.service.CustomerService;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService service;

    @PostMapping("/sign-up")
    public ResponseEntity<SignUpCustomerResponse> signUp(@Valid @RequestBody SignUpCustomerRequest request) {
        return ResponseEntity.ok(service.signUp(request));
    }

    @PutMapping("/activate/{activationId}")
    public ResponseEntity<ActivateCustomerAccountResponse> activate(@PathVariable UUID activationId) {
        return ResponseEntity.ok(service.activate(activationId));
    }
}
