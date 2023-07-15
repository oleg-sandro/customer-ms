package com.besttradebank.investment.customer.mapper;

import static com.besttradebank.investment.customer.util.TestConstants.CUSTOMER_EMAIL;
import static com.besttradebank.investment.customer.util.TestConstants.CUSTOMER_PASSWORD;
import static com.besttradebank.investment.customer.util.TestConstants.CUSTOMER_USERNAME;
import static com.besttradebank.investment.customer.util.TestHelper.buildCustomer;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.besttradebank.investment.customer.dto.request.SignUpCustomerRequest;
import com.besttradebank.investment.customer.dto.response.ActivateCustomerAccountResponse;
import com.besttradebank.investment.customer.dto.response.SignUpCustomerResponse;
import com.besttradebank.investment.customer.entity.Customer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CustomerMapperTest {

    @InjectMocks
    private CustomerMapperImpl mapper;

    @Test
    void givenFromSignUpCustomerRequest_whenConvert_thenSuccess() {
        // given
        SignUpCustomerRequest request = new SignUpCustomerRequest(CUSTOMER_USERNAME, CUSTOMER_EMAIL, CUSTOMER_PASSWORD);
        Customer expected = buildCustomer();

        // when
        Customer actual = mapper.fromSignUpCustomerRequest(request);

        // then
        assertNotNull(actual);
        assertEquals(expected.getUsername(), actual.getUsername());
        assertEquals(expected.getEmail(), actual.getEmail());
        assertEquals(expected.getPassword(), actual.getPassword());
        assertEquals(expected.getStatus(), actual.getStatus());
    }

    @Test
    void givenToSignUpCustomerResponse_whenConvert_thenSuccess() {
        // given
        Customer customer = buildCustomer();
        SignUpCustomerResponse expected =
                new SignUpCustomerResponse(customer.getId(), CUSTOMER_USERNAME, CUSTOMER_EMAIL);

        // when
        SignUpCustomerResponse actual = mapper.toSignUpCustomerResponse(customer);

        // then
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void givenToActivateCustomerAccountResponse_whenConvert_thenSuccess() {
        // given
        Customer customer = buildCustomer();
        ActivateCustomerAccountResponse expected =
                new ActivateCustomerAccountResponse(customer.getId(), CUSTOMER_USERNAME, CUSTOMER_EMAIL);

        // when
        ActivateCustomerAccountResponse actual = mapper.toActivateCustomerAccountResponse(customer);

        // then
        assertNotNull(actual);
        assertEquals(expected, actual);
    }
}
