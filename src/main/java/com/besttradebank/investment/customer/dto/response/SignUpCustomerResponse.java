package com.besttradebank.investment.customer.dto.response;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpCustomerResponse {

    private UUID id;
    private String username;
    private String email;

}
