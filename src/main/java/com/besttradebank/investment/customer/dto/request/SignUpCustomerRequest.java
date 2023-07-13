package com.besttradebank.investment.customer.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpCustomerRequest {

    private String username;
    private String email;
    private String password;

}
