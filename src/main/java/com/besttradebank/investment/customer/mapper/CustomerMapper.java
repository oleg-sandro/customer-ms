package com.besttradebank.investment.customer.mapper;

import com.besttradebank.investment.customer.dto.request.SignUpCustomerRequest;
import com.besttradebank.investment.customer.dto.response.SignUpCustomerResponse;
import com.besttradebank.investment.entity.Customer;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = {UUID.class})
public interface CustomerMapper {

    @Mapping(target = "id", expression = "java(UUID.randomUUID())")
    @Mapping(target = "status", constant = "NOT_ACTIVATED")
    Customer fromSignUpCustomerRequest(SignUpCustomerRequest request);

    SignUpCustomerResponse toSignUpCustomerResponse(Customer customer);
}
