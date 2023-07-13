package com.besttradebank.investment.customer.controller;

import com.besttradebank.investment.customer.dto.request.SignUpCustomerRequest;
import com.besttradebank.investment.customer.dto.response.SignUpCustomerResponse;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {

    @PostMapping("/sign-up")
    public ResponseEntity<SignUpCustomerResponse> signup(@Valid @RequestBody SignUpCustomerRequest request) {
        SignUpCustomerResponse response =
                new SignUpCustomerResponse(UUID.randomUUID(), request.getUsername(), request.getEmail());
        return ResponseEntity.ok(response);
    }
}
