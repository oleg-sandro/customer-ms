package com.besttradebank.investment.customer.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpCustomerRequest {

    @Pattern(regexp = "[a-zA-Z0-9_]{4,63}", message = "Username can contain digits, latin letters and underscores." +
            " Username length has to be between 4 and 63 characters")
    private String username;

    @Email(message = "Email has to be in format email_address@email_domain")
    private String email;

    @Pattern(regexp = ".{8,63}", message = "Password length has to be between 8 and 63 characters")
    private String password;

}
