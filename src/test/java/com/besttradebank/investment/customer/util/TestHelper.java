package com.besttradebank.investment.customer.util;

import static com.besttradebank.investment.customer.util.TestConstants.ACTIVATION_ID;
import static com.besttradebank.investment.customer.util.TestConstants.CUSTOMER_EMAIL;
import static com.besttradebank.investment.customer.util.TestConstants.CUSTOMER_ID;
import static com.besttradebank.investment.customer.util.TestConstants.CUSTOMER_PASSWORD;
import static com.besttradebank.investment.customer.util.TestConstants.CUSTOMER_USERNAME;
import static com.besttradebank.investment.customer.util.TestConstants.EXPIRED_AT;

import com.besttradebank.investment.customer.entity.Activation;
import com.besttradebank.investment.customer.entity.Customer;
import com.besttradebank.investment.customer.enums.ActivationStatusType;
import com.besttradebank.investment.customer.enums.CustomerStatusType;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class TestHelper {

    public static Customer buildCustomer() {
        return Customer.builder()
                .id(CUSTOMER_ID)
                .username(CUSTOMER_USERNAME)
                .email(CUSTOMER_EMAIL)
                .password(CUSTOMER_PASSWORD)
                .status(CustomerStatusType.NOT_ACTIVATED)
                .build();
    }

    public static Activation buildActivation() {
        return Activation.builder()
                .id(ACTIVATION_ID)
                .customer(buildCustomer())
                .status(ActivationStatusType.VALID)
                .expiredAt(EXPIRED_AT)
                .build();
    }
}
